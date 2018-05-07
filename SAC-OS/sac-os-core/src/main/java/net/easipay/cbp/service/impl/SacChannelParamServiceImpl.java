package net.easipay.cbp.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.easipay.cbp.cache.CacheUtil;
import net.easipay.cbp.dao.ISacChannelParamCmdDao;
import net.easipay.cbp.dao.ISacChannelParamDao;
import net.easipay.cbp.form.SacChannelParamForm;
import net.easipay.cbp.model.SacChannelParam;
import net.easipay.cbp.model.SacChannelParamCmd;
import net.easipay.cbp.sequence.SequenceCreatorUtil;
import net.easipay.cbp.service.ISacChannelParamService;
import net.easipay.cbp.util.ConstantParams;
import net.easipay.dsfc.ws.jws.JwsClient;
import net.easipay.dsfc.ws.jws.JwsResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sacChannelParamService")
public class SacChannelParamServiceImpl implements ISacChannelParamService {

	@Autowired
	private ISacChannelParamDao sacChannelParamDao;

	@Autowired
	private ISacChannelParamCmdDao sacChannelParamCmdDao;
	
	@Override
	public List<SacChannelParam> selectAllSacChannelParam() {
		
		return sacChannelParamDao.queryAllSacChannelParam();
	}
	
	@Override
	public List<SacChannelParam> selectUniqueSacChannelParamOfAcc() {
		
		return sacChannelParamDao.selectUniqueSacChannelParamOfAcc();
	}

	@Override
	public int selectSacChannelParamCounts(SacChannelParam sacChannelParam) {
		return sacChannelParamDao.selectSacChannelParamCounts(sacChannelParam);
	}

	@Override
	public SacChannelParam selectSacChannelParamById(SacChannelParam sacChannelParam) {
		return sacChannelParamDao.selectSacChannelParamById(sacChannelParam);
	}
	
	@Override
	public List<SacChannelParam> selectSacChannelParamForSplit(SacChannelParam sacChannelParam,int pageNo,int pageSize) {
		return sacChannelParamDao.selectSacChannelParamForSplit(sacChannelParam,pageNo,pageSize);
	}
	/*
	@Override
	public void updateSacChannelParam(SacChannelParam sacChannelParam) {
		sacChannelParamDao.updateSacChannelParam(sacChannelParam);
	}*/

	@Override
	public SacChannelParam selectSacChannelParamByAcc(String accNo) {
		return sacChannelParamDao.selectSacChannelParamByAcc(accNo);
	}

	@Override
	public List<SacChannelParam> selectUniqueSacChannelParamOfChnNo() {
		return sacChannelParamDao.selectUniqueSacChannelParamOfChnNo();
	}

	@Override
	public List<Map<String, Object>> preparedFundRealTimeQuery(Map<String, Object> paramMap)
	{
		return sacChannelParamDao.preparedFundRealTimeQuery(paramMap);
	}

	@Override
	public int getPreparedFundRealTimeCount(Map<String, Object> paramMap)
	{
		return sacChannelParamDao.getPreparedFundRealTimeCount(paramMap);
	}

	@Override
	public List<Map<String, Object>> simplePreparedFundRealTimeQuery(Map<String, Object> paramMap)
	{
		return sacChannelParamDao.simplePreparedFundRealTimeQuery(paramMap);
	}

	@Override
	public int addChannelParam(SacChannelParamCmd sacChannelParamCmd){
		
		sacChannelParamCmd.setCmdState("1");//待审核
		sacChannelParamCmd.setCreateTime(new Date());
		sacChannelParamCmd.setId(SequenceCreatorUtil.getTableId());
		Object addResult =  sacChannelParamCmdDao.addSacChannelParamCmd(sacChannelParamCmd);
		if(addResult==null){
			return 1;
		}else{
			return 0;
		}
	}

	@Override
	public List<SacChannelParamCmd> selectSacChannelParamCmd(Map<String, Object> paramMap) {
		Map<String,Object> ccyMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_CCY);
		List<SacChannelParamCmd> channelParamCmdList = sacChannelParamCmdDao.selectSacChannelParamCmd(paramMap);
		for(SacChannelParamCmd cmd:channelParamCmdList){
			cmd.setCurrencyType(ccyMap.get(cmd.getCurrencyType())==null?"":ccyMap.get(cmd.getCurrencyType()).toString());
		}
		return channelParamCmdList;
	}

	@Override
	public int selectSacChannelParamCmdCount(Map<String, Object> paramMap) {
		return sacChannelParamCmdDao.selectSacChannelParamCmdCount(paramMap);
	}

	@Override
	public String channelParamCheckSucc(long id) {
		String result = null;
		SacChannelParamCmd cpCmd = sacChannelParamCmdDao.selectSacChannelParamCmdById(id);
		SacChannelParamForm form = new SacChannelParamForm();
		form.setAccountName(cpCmd.getAccountName());
		form.setBankAcc(cpCmd.getBankAcc());
		form.setBankNodeCode(cpCmd.getBankNodeCode());
		form.setChnCode(cpCmd.getChnCode());
		form.setChnName(cpCmd.getChnName());
		form.setChnType(cpCmd.getChnType());
		form.setCostComWay(cpCmd.getCostComWay());
		form.setCostRate(cpCmd.getCostRate()!=null?cpCmd.getCostRate().toString():"0");
		form.setCostSacWay(cpCmd.getCostSacWay());
		form.setCraccBankCode(cpCmd.getCraccBankCode());
		form.setCurrencyType(cpCmd.getCurrencyType());
		form.setIsValidFlag(cpCmd.getIsValidFlag());
		form.setMemo(cpCmd.getMemo());
		form.setSacBankName(cpCmd.getSacBankName());
		form.setSacPeriod(cpCmd.getSacPeriod()!=null?cpCmd.getSacPeriod().toString():"0");
		try{
			JwsClient jwsClient = new JwsClient("SAC-AC-0003");
			JwsResult jwsResult = jwsClient.putParam("sacChannelParam",form).call();
			String resultCode = jwsResult.getCode();
			if("000000".equals(resultCode)){
				result = "审核通过处理成功！";
			}else{
				result = jwsResult.getMessage();
			}
			cpCmd.setUpdateTime(new Date());
			cpCmd.setCmdState("2");
			sacChannelParamCmdDao.update(cpCmd);//更新状态
		}catch(Exception e){
			return e.getMessage();
		}
		return result;
	}

	@Override
	public String channelParamCheckFail(long id) {
		SacChannelParamCmd cpCmd = sacChannelParamCmdDao.selectSacChannelParamCmdById(id);
		cpCmd.setUpdateTime(new Date());
		cpCmd.setCmdState("3");
		sacChannelParamCmdDao.update(cpCmd);//更新状态
		return "审核不通过处理成功！";
	}

	@Override
	public Map<String, String> selectAllSacBank() {
		return sacChannelParamDao.selectAllSacBank();
	}

	@Override
	public List<Map<String, String>> selectAllBankAccByBankNodeCode(String bankNodeCode) {
		return  sacChannelParamDao.selectAllBankAccByBankNodeCode(bankNodeCode);
	}
	public List<SacChannelParam> selectSacChannelParamForB2C(
			Map<String, Object> queryChannelMap) {
		return sacChannelParamCmdDao.selectSacChannelParamForB2C(queryChannelMap);
	}

	@Override
	public Boolean validateBankAvalibleBal(String bankNodeCode,
			String currencyType, BigDecimal payAmount) {
		Map<String,Object> queryMap = new HashMap<String, Object>();
		queryMap.put("bankNodeCode", bankNodeCode);
		queryMap.put("currencyType", currencyType);
		List<Map<String,Object>> resultList = sacChannelParamDao.getBankAvalibleBal(queryMap);
		BigDecimal totalAmount = new BigDecimal("0.00");
		if(resultList!=null&&resultList.size()>0){
			Map<String,Object> resultMap = resultList.get(0);
			totalAmount = (BigDecimal)resultMap.get("totalAmount");
		}
		if(payAmount.compareTo(totalAmount)<=0){
			return true;
		}
		return false;
	}
}
