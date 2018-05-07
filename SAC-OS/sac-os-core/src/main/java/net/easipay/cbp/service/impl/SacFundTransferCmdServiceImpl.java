package net.easipay.cbp.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.easipay.cbp.cache.CacheUtil;
import net.easipay.cbp.constant.Constants;
import net.easipay.cbp.dao.ISacChannelParamDao;
import net.easipay.cbp.dao.ISacFundTransferCmdDao;
import net.easipay.cbp.form.SacFundTransferCmdForm;
import net.easipay.cbp.model.SacChannelParam;
import net.easipay.cbp.model.SacFundTransferCmd;
import net.easipay.cbp.model.SacOtrxInfo;
import net.easipay.cbp.sequence.SequenceCreatorUtil;
import net.easipay.cbp.service.ISacFundTransferCmdService;
import net.easipay.cbp.util.ConstantParams;
import net.easipay.dsfc.ws.jws.JwsClient;
import net.easipay.dsfc.ws.jws.JwsException;
import net.easipay.dsfc.ws.jws.JwsResult;

import org.apache.commons.httpclient.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author sydai 
 */
@Service
public class SacFundTransferCmdServiceImpl implements ISacFundTransferCmdService{

	@Autowired
    private ISacFundTransferCmdDao sacFundTransferCmdDao;

	@Autowired
	private ISacChannelParamDao sacChannelParamDao;
	
	@Override
	public int insertSacFundTransferCmd(SacFundTransferCmd sacFundTransferCmd) {
		return sacFundTransferCmdDao.insertSacFundTransferCmd(sacFundTransferCmd);
	}

	@Override
	public List<SacFundTransferCmd> selectSacFundTransferCmd(Map paramMap) {
		Map<String,Object> ccyMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_CCY);
		List<SacFundTransferCmd> fundTransferCmdList = sacFundTransferCmdDao.listSplitSacFundTransferCmd(paramMap);
		for(SacFundTransferCmd cmd:fundTransferCmdList){
			cmd.setPayCurrency(ccyMap.get(cmd.getPayCurrency())!=null?ccyMap.get(cmd.getPayCurrency()).toString():"");
		}
		return fundTransferCmdList;
	}

	@Override
	public int selectSacFundTransferCmdCount(Map paramMap) {
		return sacFundTransferCmdDao.getSacFundTransferCmdCount(paramMap);
	}

	@Override
	public int updateSacFundTransferCmd(SacFundTransferCmd sacFundTransferCmd) {
		return sacFundTransferCmdDao.updateSacFundTransferCmd(sacFundTransferCmd);
	}

	@Override
	public List<SacFundTransferCmd> selectAllSacFundTransferCmd(Map paramMap) {
		return sacFundTransferCmdDao.listSacFundTransferCmd(paramMap);
	}

	@Override
	public List<SacOtrxInfo> installSacOtrxInfo(SacFundTransferCmd sacFundTransferCmd, String etrxSerialNo) {
		String craccNo = sacFundTransferCmd.getCraccNo();
		String draccNo = sacFundTransferCmd.getDraccNo();
		SacChannelParam crSacChannelParam = sacChannelParamDao.selectSacChannelParamByAcc(craccNo);
    	SacChannelParam drSacChannelParam = sacChannelParamDao.selectSacChannelParamByAcc(draccNo);
		SacOtrxInfo sacOtrxInfo = new SacOtrxInfo();
		//收款方
    	if(crSacChannelParam!=null){
    		sacOtrxInfo.setCraccCardId("0");
    		sacOtrxInfo.setCraccCusType("2");
    		sacOtrxInfo.setCraccCusName(crSacChannelParam.getChnName());
        	sacOtrxInfo.setCraccNo(craccNo);
        	sacOtrxInfo.setCraccName(crSacChannelParam.getAccountName());
        	sacOtrxInfo.setCraccNodeCode(crSacChannelParam.getBankNodeCode());
        	sacOtrxInfo.setCraccBankName(crSacChannelParam.getSacBankName());
    		sacOtrxInfo.setCraccCusCode(crSacChannelParam.getChnNo());
    	}
    	//付款方
    	if(drSacChannelParam!=null){
    		sacOtrxInfo.setDraccCardId("0");
    		sacOtrxInfo.setDraccCusType("2");
    		sacOtrxInfo.setDraccCusName(drSacChannelParam.getChnName());
        	sacOtrxInfo.setDraccNo(draccNo);
        	sacOtrxInfo.setDraccName(drSacChannelParam.getAccountName());
        	sacOtrxInfo.setDraccNodeCode(drSacChannelParam.getBankNodeCode());
        	sacOtrxInfo.setDraccBankName(drSacChannelParam.getSacBankName());
    		sacOtrxInfo.setDraccCusCode(drSacChannelParam.getChnNo());
    	}
    	sacOtrxInfo.setTrxSerialNo(SequenceCreatorUtil.generateTrxSerialNo());
    	//sacOtrxInfo.setEtrxSerialNo(etrxSerialNo);
    	sacOtrxInfo.setPayCurrency(sacFundTransferCmd.getPayCurrency());
    	sacOtrxInfo.setPayAmount(sacFundTransferCmd.getPayAmount());
    	sacOtrxInfo.setBussType("60");//业务类型50外汇通,20航付通,30跨境B2C,40代收付60清算
    	sacOtrxInfo.setTrxType("170100");//资金调拨交易类型
    	sacOtrxInfo.setTrxState("S");
    	sacOtrxInfo.setPayconType("3");//支付渠道0 其他 1 B2B支付 2 B2C支付 3银行存款
    	sacOtrxInfo.setPayWay(sacFundTransferCmd.getPayWay());
    	sacOtrxInfo.setTrxTime(sacFundTransferCmd.getCreateTime());
		sacOtrxInfo.setCusCharge(new BigDecimal("0"));
		sacOtrxInfo.setChannelCost(new BigDecimal("0"));
		sacOtrxInfo.setTrxSuccTime(new Date());
		sacOtrxInfo.setDraccAreaCode(sacFundTransferCmd.getDraccAreaCode());
		sacOtrxInfo.setCraccAreaCode(sacFundTransferCmd.getCraccAreaCode());
		List<SacOtrxInfo> trxList = new ArrayList<SacOtrxInfo>();
		trxList.add(sacOtrxInfo);
		return trxList;
	}

	@Override
	public String fundTransferCmdToDSF(SacFundTransferCmd sacFundTransferCmd) throws Exception{
		try{
			SacFundTransferCmdForm form = new SacFundTransferCmdForm();
			form.setAmount(sacFundTransferCmd.getPayAmount().toString());
			form.setFrBankCode(sacFundTransferCmd.getDraccNodeCode());
			form.setMerTrxDate(DateUtil.formatDate(sacFundTransferCmd.getCreateTime(), "yyyy-MM-dd hh:mm:ss"));
			form.setRemark(sacFundTransferCmd.getMemo());
			form.setToBankCode(sacFundTransferCmd.getCraccNodeCode());
			form.setTrxSerNo(String.valueOf(sacFundTransferCmd.getId()));
			JwsClient client = new JwsClient("DSF-JOB-0006");
			JwsResult result = client.putAllParam(form).call(true);
			if("000000".equals(result.getCode())&&"3".equals(result.getStringValue("trxState"))){//处理成功
				String trxSerialNo = result.getStringValue("orderNo");//交易流水号
				return "null"+trxSerialNo;
			}else if("000000".equals(result.getCode())&&!"3".equals(result.getStringValue("trxState"))){//处理失败
				return result.getStringValue("chnlReturnDesc");
			}else{//处理异常
				return result.getMessage();
			}
		}catch(JwsException e){
			e.printStackTrace();
			return e.getMessage();
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
}

