package net.easipay.cbp.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.easipay.cbp.cas.users.SecurityOperator;
import net.easipay.cbp.constant.EnumConstants.FundGiveOFLConstants.OflCmdState;
import net.easipay.cbp.dao.ISacOflCommandDao;
import net.easipay.cbp.form.CashLineForm;
import net.easipay.cbp.model.SacCusParameter;
import net.easipay.cbp.model.SacOflCommand;
import net.easipay.cbp.model.form.NotifyOflCommandResultForm;
import net.easipay.cbp.sequence.SequenceCreatorUtil;
import net.easipay.cbp.service.INotifyOperResultToB2BService;
import net.easipay.cbp.service.ISacCusParameterService;
import net.easipay.cbp.service.ISacOflCommandService;
import net.easipay.cbp.service.ITransactionService;
import net.easipay.cbp.util.PersonUtil;
import net.easipay.dsfc.ws.jws.JwsResult;
import net.easipay.dsfc.ws.xws.XwsClient;
import net.easipay.dsfc.ws.xws.XwsMsgHeader;
import net.easipay.dsfc.ws.xws.XwsResult;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sacOflCommandService")
public class SacOflCommandServiceImpl implements ISacOflCommandService
{

	public static final Logger logger = Logger.getLogger(SacOflCommandServiceImpl.class);
	
	@Autowired
	private ISacCusParameterService sacCusParameterService;
	
	@Autowired
	private ISacOflCommandDao sacOflCommandDao;
	
	@Autowired
	private INotifyOperResultToB2BService notifyOperResultToB2BService;
	
	@Autowired
	private ITransactionService transactionService;

	@Override
	public JwsResult confirmTransferCommand(SacOflCommand oflCmd, String bussType,String draccAreaCode) {
		String draccNodeCode = oflCmd.getDraccNodeCode();
		String draccBankName = oflCmd.getDraccBankName();
		BigDecimal payAmount = oflCmd.getPayAmount();
		String payCurrency = oflCmd.getPayCurrency();
		String cusNo = oflCmd.getCusNo();
		String craccNo = oflCmd.getCraccNo();
		String orgCardId = oflCmd.getCrtCode();//组织机构代码
		String craccBankName = oflCmd.getCraccBankName();
		String craccNodeCode = oflCmd.getCraccNodeCode();
		String draccNo = oflCmd.getDraccNo();//平台账号
		String etrxSerialNo = oflCmd.getEtrxSerialNo();
		
		//确认出款之前查询该商户是否有出款中的交易 如果有则不允许出款
		//因为现在都是实时的 无需校验
		/*Map<String,Object> queryMap = new HashMap<String,Object>();
		queryMap.put("crtCode", orgCardId);
		queryMap.put("payCurrency", payCurrency);
		queryMap.put("bussType", bussType);
		queryMap.put("state", OflCmdState.QequestSuccess.code());
		
		List<SacOflCommand> cmdList = sacOflCommandDao.getOflCommandByParam(queryMap);
		if(cmdList!=null&&cmdList.size()>0){
			throw new SacException("999990", "有记录正在出款中,请稍后再试!");
		}*/
		
    	
		SacCusParameter param = new SacCusParameter();
		param.setOrgCardId(orgCardId);
		param.setCusNo(cusNo);
		param.setSacCurrency(payCurrency);
		List<SacCusParameter> paramList = sacCusParameterService.selectAllSacCusParameter(param, 1, Integer.MAX_VALUE);
		SacCusParameter sacCusParameter = paramList.get(0);
		String trxSerialNo = SequenceCreatorUtil.generateTrxSerialNo();
		oflCmd.setId(SequenceCreatorUtil.getTableId());
		oflCmd.setTrxSerialNo(trxSerialNo);
		oflCmd.setCraccBankName(craccBankName);
		oflCmd.setCraccName(sacCusParameter.getAccName());
		oflCmd.setCraccNo(craccNo);//平台账户
		oflCmd.setCraccNodeCode(craccNodeCode);
		oflCmd.setCrtCode(orgCardId);
		oflCmd.setCusNo(cusNo);
		oflCmd.setDraccNodeCode(draccNodeCode);
		oflCmd.setDraccBankName(draccBankName);
		oflCmd.setPayAmount(payAmount);
		oflCmd.setPayCurrency(payCurrency);
		oflCmd.setBussType(bussType);
		SecurityOperator user = PersonUtil.getUser();
		oflCmd.setOperName(user.getEmail());
		Date date = new Date();
		oflCmd.setOperTime(date);
		
		
		
		
		
		NotifyOflCommandResultForm cmd = new NotifyOflCommandResultForm();
		
		cmd.setBankSerialNo(etrxSerialNo);
		cmd.setPayAmount(payAmount+"");
		cmd.setPyeeAccId(craccNo);
		cmd.setPyeeAccNm(oflCmd.getCraccName());
		cmd.setPyeeBnkCd(craccNodeCode);
		cmd.setPyeeBnkNm(craccBankName);
		cmd.setPyeeIdNo(orgCardId);
		cmd.setPyerBnkCd(draccNodeCode);
		cmd.setPyerBnkNm(draccBankName);
		cmd.setTrxSerialNo(trxSerialNo);
		cmd.setMsgSndrSysNdCd("SAC0000");
		cmd.setTrxBizCd(bussType);
		cmd.setPyerBankAreaCode(draccAreaCode);
    	JwsResult rt = notifyOperResultToB2BService.notifyFundGiveOffline(cmd);
    	
    	if(rt.isSuccess()){
    		oflCmd.setCreateTime(new Date());
    		oflCmd.setLastUpdateTime(new Date());
			oflCmd.setRtnCode(rt.getCode());
			oflCmd.setRtnInfo(rt.getMessage());
			oflCmd.setState(OflCmdState.Success.code());
    	}else{
    		oflCmd.setRtnCode(rt.getCode());
			oflCmd.setRtnInfo(rt.getMessage());
			oflCmd.setState(OflCmdState.Failue.code());
			oflCmd.setCreateTime(new Date());
    		oflCmd.setLastUpdateTime(new Date());
    	}
    	sacOflCommandDao.save(oflCmd);
    	return rt;
		
	}
	
	
	@Override
	public String NotifyDFFCmdOfl(Map<String,String> handleMap) {
		CashLineForm form = new CashLineForm();
		XwsClient xwsClient = new XwsClient("EP-ORD-0009");
		XwsMsgHeader xwsMsgHeader = XwsMsgHeader.createXwsMsgHeader("00", "SAC00000", "SAC0000", "ORD0001", null);
		form.setTrxSerialNo(handleMap.get("otrxSerialNo"));//送预扣交易流水号
		form.setErrorDesc(handleMap.get("rejectArea"));
		form.setEtrxSerialNo(handleMap.get("etrxSerialNo"));
		form.setPyeeAccId(handleMap.get("craccNo"));
		form.setPyerBnkAreaCode(handleMap.get("draccAreaCode"));
		form.setPyerBnkCd(handleMap.get("draccNodeCode"));
		form.setPyerBnkNm(handleMap.get("draccBankName"));
		form.setState(handleMap.get("state"));
		String msg = "";
		try {
			XwsResult result = xwsClient.putHeaderAndBody(xwsMsgHeader, form).call(true);
			String code = result.getCode();
			if("000000".equals(code)){
			}else{
				msg = "调用失败："+result.getMessage();
			}
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return msg;
	}


	
}
