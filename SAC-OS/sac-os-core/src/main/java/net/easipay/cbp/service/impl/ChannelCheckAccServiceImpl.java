package net.easipay.cbp.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.easipay.cbp.cache.CacheUtil;
import net.easipay.cbp.dao.ISacChannelParamDao;
import net.easipay.cbp.dao.ISacRecBatchDao;
import net.easipay.cbp.dao.ISacRecDetailDao;
import net.easipay.cbp.dao.ISacRecDifferencesDao;
import net.easipay.cbp.dao.ISacRecFileParamDao;
import net.easipay.cbp.model.SacChannelParam;
import net.easipay.cbp.model.SacRecBatch;
import net.easipay.cbp.model.SacRecDetail;
import net.easipay.cbp.model.SacRecDifferences;
import net.easipay.cbp.model.SacRecFileParam;
import net.easipay.cbp.model.SacReceiveBankRecon;
import net.easipay.cbp.service.IChannelCheckAccService;
import net.easipay.cbp.util.ConstantParams;
import net.easipay.cbp.util.trxBankRecon.SelectBankDataFile;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ChannelCheckAccServiceImpl implements IChannelCheckAccService
{

	public static final Logger logger = Logger.getLogger(ChannelCheckAccServiceImpl.class);

	@Autowired
	ISacRecBatchDao recBatchDao;
	
	@Autowired
	ISacRecDifferencesDao recDiffDao;

	@Autowired
	ISacRecFileParamDao recFileParamDao;
	
	@Autowired
	ISacChannelParamDao chnParamDao;
	
	@Autowired
	ISacRecDetailDao recDetailDao;
	
	@Override
	public List<Map<String, Object>> queryCheckAccResult(Map<String, Object> paramMap)
	{
		Map<String,Object> queryMap = handleQueryRecResultParam(paramMap);
		logger.debug(queryMap);
		return recBatchDao.checkAccResultList(paramMap);
	}

	@Override
	public int queryCheckAccResultCount(Map<String, Object> paramMap)
	{
		//处理时间
		Map<String,Object> queryMap = handleQueryRecResultParam(paramMap);
		logger.debug(queryMap);
		return recBatchDao.checkAccResultListCount(paramMap);
	}
	
	@Override
	public List<SacRecDifferences> queryRecDiffDetail(Map<String, Object> paramMap)
	{
		return recDiffDao.queryRecDiffDetail(paramMap);
	}

	@Override
	public boolean recBatchIsExist(String recType,String recDate,String chnCode,String payconType)
	{
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("recType", "2");//渠道对账
		paramMap.put("recDate", recDate);//交易日期
		paramMap.put("chnCode", chnCode);//渠道号
		paramMap.put("payconType", "2");//B2C支付
		List<SacRecBatch> queryCheckAccBatch  = recBatchDao.queryCheckAccBatch(paramMap);
		if(queryCheckAccBatch!=null&&queryCheckAccBatch.size()==0){
			return false;
		}else{
			return true;
		}
	}

	@Override
	public List<SacRecDifferences> simpleQueryRecDiffDetail(Map<String, Object> paramMap)
	{
		return recDiffDao.simpleQueryRecDiffDetail(paramMap);
	}

	@Override
	public Integer queryRecDiffDetailCount(Map<String, Object> paramMap)
	{
		return recDiffDao.queryRecDiffDetailCount(paramMap);
	}

	@Override
	public List<SacRecFileParam> getRecFileParamList(Map<String,Object> paramMap){
		return recFileParamDao.listAllSacRecFileParam(paramMap);
	}
	
	/**
	 * 对差错明细中的值转义
	 * @param diffList
	 * @return
	 */
	@Override
	public List<SacRecDifferences> convertDiffList(List<SacRecDifferences> diffList){
		if(diffList==null||diffList.size()==0){
			return diffList;
		}
		Map<String,Object> diffTypeMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_CHN_PARAM);
		Map<String,Object> trxTypeMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_TRX_TYPE);
		/*List<SacRecFileParam> recFileParamList = getRecFileParamList(new HashMap<String,Object>());
		for(SacRecDifferences diff:diffList){
			for(SacRecFileParam param:recFileParamList){
				if(param.getChnCode().equals(diff.getChnCode())&&param.getPayconType().equals(diff.getPayconType())){
					//设置渠道名
					diff.setChnCode(param.getChnName());
					break;
				}
			}
			//处理状态
			diff.setStatus("S".equals(diff.getStatus())?"已处理":"未处理");
			//处理方式
			diff.setDealType("1".equals(diff.getDealType())?"人工":"系统自动");
			//处理人员
			diff.setDealOper(diff.getDealOper()==null?"-":diff.getDealOper());
			//交易流水号
			diff.setTrxSerialNo(diff.getTrxSerialNo()==null?"-":diff.getTrxSerialNo());
			//差错类型
			diff.setRecDiffType(diffTypeMap.get(diff.getRecDiffType())==null?"-":diffTypeMap.get(diff.getRecDiffType()).toString());
		}*/
		List<SacChannelParam> channelParamList = chnParamDao.queryAllSacChannelParam();
		for(SacRecDifferences diff:diffList){
			for(SacChannelParam param:channelParamList){
				if(param.getChnNo().equals(diff.getChnCode())){//批次的渠道号等于渠道参数表的渠道号
					//设置渠道名
					diff.setChnCode(param.getChnName());
					break;
				}
			}
			//处理状态
			diff.setStatus("S".equals(diff.getStatus())?"已处理":"未处理");
			//处理方式
			String dealTypeName = "";
			String dealType = diff.getDealType();
			if("1".equals(dealType)){
				dealTypeName = "人工";
			}else if("2".equals(dealType)){
				dealTypeName = "系统自动";
			}else{
				dealTypeName = "-";
			}
			diff.setDealType(dealTypeName);
			//处理人员
			diff.setDealOper(diff.getDealOper()==null?"-":diff.getDealOper());
			//交易流水号
			diff.setTrxSerialNo(diff.getTrxSerialNo()==null?"-":diff.getTrxSerialNo());
			//差错类型
			diff.setRecDiffType(diffTypeMap.get(diff.getRecDiffType())==null?"-":diffTypeMap.get(diff.getRecDiffType()).toString());
			//交易类型
			diff.setTrxCode(diff.getTrxCode()==null?"-":(trxTypeMap.get(diff.getTrxCode()).toString()+"("+diff.getTrxCode()+")"));
		}
		return diffList;
	}
	
	/**
	 * 对明细中的值转义
	 * @param diffList
	 * @return
	 */
	@Override
	public List<SacRecDetail> convertDetailList(List<SacRecDetail> detailList){
		if(detailList==null||detailList.size()==0){
			return detailList;
		}
		Map<String,Object> ccyMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_CCY);
		Map<String,Object> trxTypeMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_TRX_TYPE);
		/*List<SacRecFileParam> recFileParamList = getRecFileParamList(new HashMap<String,Object>());
		for(SacRecDetail detail:detailList){
			for(SacRecFileParam param:recFileParamList){
				if(param.getChnCode().equals(detail.getChnCode())&&param.getPayconType().equals(detail.getPayconType())){
					//设置渠道名
					detail.setChnCode(param.getChnName());
					break;
				}
			}
			//币种
			detail.setCurrencyType(ccyMap.get(detail.getCurrencyType())==null?"-":ccyMap.get(detail.getCurrencyType()).toString());
		}*/
		List<SacChannelParam> channelParamList = chnParamDao.queryAllSacChannelParam();
		for(SacRecDetail detail:detailList){
			for(SacChannelParam param:channelParamList){
				if(param.getChnNo().equals(detail.getChnCode())){
					//设置渠道名
					detail.setChnCode(param.getChnName());
					break;
				}
			}
			//币种
			detail.setCurrencyType(ccyMap.get(detail.getCurrencyType())==null?"-":ccyMap.get(detail.getCurrencyType()).toString());
			//交易类型
			detail.setTrxCode(detail.getTrxCode()==null?"-":(trxTypeMap.get(detail.getTrxCode()).toString()+"("+detail.getTrxCode()+")"));
		}
		return detailList;
	}

	@Override
	public String getBankNodeCode(String chnCode)
	{
		String bankNodeCode = null;
		Map<String,Object> recFileParamMap = new HashMap<String, Object>();
		recFileParamMap.put("recType", "2");
		recFileParamMap.put("payconType", "2");
		List<SacRecFileParam> recFileParamList = getRecFileParamList(recFileParamMap);
		for(SacRecFileParam srf:recFileParamList){
			if(srf.getChnCode().equals(chnCode)){
				bankNodeCode = srf.getBankNodeCode();
			}
		}
		return bankNodeCode;
	}

	@Override
	public boolean recDateIsRight(List<SacReceiveBankRecon> bankReconList, String checkTrxDate)
	{
		SacReceiveBankRecon sacReceiveBankRecon = new SacReceiveBankRecon();
		sacReceiveBankRecon = bankReconList.get(0);
		Date txtTimeStr = sacReceiveBankRecon.getTrxTime();
		String txtTime = new SimpleDateFormat("yyyyMMdd").format(txtTimeStr);
		if (txtTime.equals(checkTrxDate)) {
			return true;
		}
		return false;
	}

	@Override
	public List<SacReceiveBankRecon> resolveRecFile(List<MultipartFile> files,String bankNodeCode,String fileName) throws Exception
	{
		InputStream inputStream = null;
		List<SacReceiveBankRecon> bankReconList = new ArrayList<SacReceiveBankRecon>();
		List<SacReceiveBankRecon> bankReconList2 = new ArrayList<SacReceiveBankRecon>();
		try{
			if(!"CITIC00".equals(bankNodeCode)){
				inputStream = files.get(0).getInputStream();
				bankReconList = SelectBankDataFile.selectBankData(bankNodeCode, inputStream, "1", fileName);
			}else if("CITIC00".equals(bankNodeCode)){
				if(!files.get(0).isEmpty()){
					inputStream = files.get(0).getInputStream();
					bankReconList = SelectBankDataFile.selectBankData(bankNodeCode, inputStream, "1", fileName);
				}
				if(!files.get(1).isEmpty()){
					inputStream = files.get(1).getInputStream();
					bankReconList2 = SelectBankDataFile.selectBankData(bankNodeCode, inputStream, "2", fileName);
				}
				long count = bankReconList.size()+bankReconList2.size();
				bankReconList.addAll(bankReconList2);
				for(SacReceiveBankRecon srbr:bankReconList){
					srbr.setRecCount(count);
				}
			}
		}catch(Exception e){
			throw e;
		}finally{
			try
			{
				if(inputStream!=null){inputStream.close();}
			} catch (IOException e)
			{
				logger.error("uploadCheckAccFile colse IO exception……" + e.getMessage());
				throw e;
			}
		}
		return bankReconList;
	}
	
	/**
	 * 处理查询对账结果的参数
	 * @param paramMap
	 * @return
	 */
	private Map<String,Object> handleQueryRecResultParam(Map<String,Object> paramMap){
		//处理时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String startDate = paramMap.get("startDate").toString();
		String endDate = paramMap.get("endDate").toString();
		Calendar startCal = Calendar.getInstance();//起始日期
		Calendar endCal = Calendar.getInstance();//结束日期
		try
		{
			startCal.setTime(sdf.parse(startDate));
			endCal.setTime(sdf.parse(endDate));
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
		int days = endCal.get(Calendar.DAY_OF_YEAR)-startCal.get(Calendar.DAY_OF_YEAR)+1;//相差天数
		String queryDateStr = "";//存放所有查询日期
		for(int i=0;i<days;i++){
			if(i!=0){
				endCal.add(Calendar.DAY_OF_MONTH, -1);
			}
			queryDateStr += sdf.format(endCal.getTime())+",";
		}
		paramMap.put("queryDateStr", queryDateStr.substring(0, queryDateStr.length()-1));
		paramMap.put("days", days);
		return paramMap;
	}

	@Override
	public List<SacRecDetail> queryRecBatchDetail(Map<String, Object> paramMap)
	{
		return recDetailDao.queryRecDetail(paramMap);
	}

	@Override
	public int queryRecBatchDetailCount(Map<String, Object> paramMap)
	{
		return recDetailDao.queryRecDetailCount(paramMap);
	}
	
}
