package net.easipay.cbp.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.easipay.cbp.constant.Constants;
import net.easipay.cbp.constant.EnumConstants.FundGiveOLConstants.CmdState;
import net.easipay.cbp.constant.EnumConstants.PrestoreOFLConstants.OFLDepositDealState;
import net.easipay.cbp.dao.ISacCommandDao;
import net.easipay.cbp.dao.ISacOflCommandDao;
import net.easipay.cbp.form.CommondReqForm;
import net.easipay.cbp.model.SacB2BCommand;
import net.easipay.cbp.model.SacB2bCmdBatch;
import net.easipay.cbp.model.SacChargeApply;
import net.easipay.cbp.model.SacDepositBatch;
import net.easipay.cbp.model.SacDepositDetail;
import net.easipay.cbp.model.SacFundTransferCmd;
import net.easipay.cbp.model.form.NotifyOflCommandResultForm;
import net.easipay.cbp.model.form.NotifyPrestoreResultForm;
import net.easipay.cbp.service.INotifyOperResultToB2BService;
import net.easipay.cbp.service.ISequenceCreatorService;
import net.easipay.cbp.util.DateUtil;
import net.easipay.cbp.util.UrlUtil;
import net.easipay.dsfc.ws.jws.JwsClient;
import net.easipay.dsfc.ws.jws.JwsResult;
import net.easipay.dsfc.ws.xws.XwsClient;
import net.easipay.dsfc.ws.xws.XwsMsgHeader;
import net.easipay.dsfc.ws.xws.XwsResult;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotifyOperResultToB2BServiceImpl implements INotifyOperResultToB2BService
{

	public static final Logger logger = Logger.getLogger(NotifyOperResultToB2BServiceImpl.class);

	@Autowired
	private ISequenceCreatorService sequenceCreatorService;
	
	@Autowired
	private ISacCommandDao sacCommandDao;
	
	@Autowired
	private ISacOflCommandDao sacOflCommandDao;
	
	private String ONL_FUND_GIVE_PASS_URL;
	private String FUND_ALLOT_URL;
	private String PRESTORE_CHECK_PASS_URL;
	private String OFL_PRESTORE_MANUAL_CHECK_PASS_URL;
	
	/**
	 * 线下预存审核通知东方付新系统
	 */
	@Override
	public JwsResult notifyPreStoreResultDff(List<NotifyPrestoreResultForm> detailList) {
		
		JwsClient client = new JwsClient("EP-ORD-0044");
		
		JwsResult result = client.putParam("offlineRechargeConfirmRequest", detailList).call(true);
		
		return result;
		
	}
	/**
	 * 线下预存审核通知B2B老系统
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String,String> notifyPreStoreResult(SacDepositDetail detail,SacDepositBatch batch,SacChargeApply apply,int countDff,BigDecimal amountDff) {
		Map<String,String> params = new HashMap<String,String>();
		params.put("batchSerialNo",detail.getOflDepositBatchId()+"");
		params.put("batchTicount",(batch.getBatchTcount()-countDff)+"");
		params.put("batchTamount",(batch.getBatchTamount().subtract(amountDff))+"");
		params.put("craccNodeCode",batch.getCraccNodeCode());
		params.put("oflDepositSerialNo",detail.getOflDepositId()+"");
		if(OFLDepositDealState.InitSuc.code().equals(detail.getDealState())){
		params.put("applyCode",detail.getApplyCode());
		params.put("dbtCode",apply.getApplyDbtCode());
		params.put("dealState","01");
		}else if(OFLDepositDealState.InitFail.code().equals(detail.getDealState())){
			if(StringUtils.isNotBlank(detail.getChargeApplyId()+"")){
				params.put("applyCode",detail.getApplyCode());
			}
			params.put("dealState","03");//自动匹配失败
		}
		params.put("payTamt",detail.getPayAmount()+"");
		params.put("draccNo",detail.getDraccNo());
		params.put("draccName",detail.getDraccName());
		params.put("draccBankName",detail.getDraccBankName());
		params.put("bankSerialNo",detail.getBankSerialNo());
		params.put("bankTrxDate",DateUtil.getFomateDate(detail.getBankTrxDate(), "yyyyMMdd"));
		params.put("dealMemo",detail.getDealMemo());
		String url = PRESTORE_CHECK_PASS_URL;
		//String url = "http://10.68.30.94:8080/stt/ofl/depositBatch/OFLdepositCheckPassNotice.do";
		logger.info("请求URL："+url);
		logger.info("请求参数："+params.toString());
		JSONObject reqJson = JSONObject.fromObject(params);
		Map<String,String> reqMap = new HashMap<String, String>();
		reqMap.put("msg", reqJson.toString());
		String response = UrlUtil.methodPost(url, reqMap);
		logger.info("响应信息："+response);
		JSONObject fromObject = JSONObject.fromObject(response);
		Map<String,String> map = (Map<String,String>)fromObject;
		return map;
		
	}

	/**
	 * 线下预存人工核销审核通知
	 */
	@Override
	public Map<String, String> notifyPreStoreResultForMunualMatch(
			SacDepositDetail detail, SacChargeApply apply) {
		Map<String,String> params = new HashMap<String,String>();
		params.put("oflDepositSerialNo",detail.getOflDepositId()+"");
		params.put("payTamt",detail.getPayAmount()+"");
		params.put("dbtCode",apply.getApplyDbtCode());
		String url = OFL_PRESTORE_MANUAL_CHECK_PASS_URL;
		//String url = "http://10.68.30.94:8080/stt/manOff/manOffNotice.do";
		logger.info("请求URL："+url);
		logger.info("请求参数："+params.toString());
		JSONObject reqJson = JSONObject.fromObject(params);
		Map<String,String> reqMap = new HashMap<String, String>();
		reqMap.put("msg", reqJson.toString());
		String response = UrlUtil.methodPost(url, reqMap);
		logger.info("响应信息："+response);
		JSONObject fromObject = JSONObject.fromObject(response);
		Map<String,String> map = (Map<String,String>)fromObject;
		return map;
	}

	
	/**
	 * 线上取回
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void notifyFundGiveOnline(SacB2BCommand cmd, SacB2bCmdBatch batch) {
		if("ORD0001".equals(cmd.getReqMemo())){
			//东方付发起的提现预扣
			NotifyDFFCmdOl(cmd,batch);
		}else{
			Map<String,String> params = new HashMap<String,String>();
			params.put("batchSerialNo",batch.getCmdBatchId()+"");
			params.put("batchTcount",batch.getBatchCount()+"");
			params.put("batchTamount",batch.getBatchAmount()+"");
			params.put("vldDate",batch.getVldDate()+"");
			params.put("cmdSerialNo",cmd.getCmdSerialNo());
			params.put("payAmount",cmd.getPayAmount()+"");
			params.put("bankNodeCode",batch.getMsgReceiver());
			cmd.setReqTime(new Date());
			String url = ONL_FUND_GIVE_PASS_URL;
			//String url = "http://10.68.30.94:8080/stt/command/batch/review/cmdReviewNotice.do";
			logger.info("请求URL："+url);
			logger.info("请求参数："+params.toString());
			JSONObject reqJson = JSONObject.fromObject(params);
			Map<String,String> reqMap = new HashMap<String, String>();
			reqMap.put("msg", reqJson.toString());
			
			
			Map<String,String> map = null;
			if("0".equals(Constants.INTERFACE_SWITCH)){
				//测试环境
				map = new HashMap<String, String>();
				map.put("rtnCode", "000000");
				map.put("rtnInfo", "成功");
				map.put("trxSerialNo", "12345678");
				map.put("rdoTime", DateUtil.getDateTime("yyyyMMddHHmmss", new Date()));
			}else if("1".equals(Constants.INTERFACE_SWITCH)){
				//生产
				String response = UrlUtil.methodPost(url, reqMap);
				logger.info("响应信息："+response);
				JSONObject fromObject = JSONObject.fromObject(response);
				map= (Map<String,String>)fromObject;
			}
			
			String rtnCode = map.get("rtnCode");
			if("999999".equals(rtnCode)||"999990".equals(rtnCode)){
				//可能是商户余额不足
				cmd.setErtnCode(rtnCode);
				cmd.setRtnCode(rtnCode);
				cmd.setErtnInfo(map.get("rtnInfo"));
				cmd.setCmdState(CmdState.TransationFail.code());
				cmd.setRdoTime(new Date());
			}else if("000000".equals(rtnCode)){
				cmd.setCmdState(CmdState.RequestSuccess.code());
				Date rdoDate;
				try {
					rdoDate = DateUtil.convertStringToDate("yyyyMMddHHmmss", map.get("rdoTime"));
				} catch (ParseException e) {
					rdoDate = new Date();
					e.printStackTrace();
				}
				cmd.setRdoTime(rdoDate);
				cmd.setRtnCode(rtnCode);
			}
		}
		sacCommandDao.update(cmd);
		
	}
	
	public void NotifyDFFCmdOl(SacB2BCommand cmd, SacB2bCmdBatch batch) {
		CommondReqForm form = new CommondReqForm();
		XwsClient xwsClient = new XwsClient("EP-ORD-0001");
		XwsMsgHeader xwsMsgHeader = XwsMsgHeader.createXwsMsgHeader("00", "SAC00000", "SAC0000", "ORD0001", null);
		form.setBatchCount(batch.getBatchCount()+"");
		form.setBatchSerialNo(batch.getBatchSerialNo());
		form.setTrxSerialNo(cmd.getOtrxSerialNo());
		form.setPayAmount(cmd.getPayAmount()+"");
		form.setPayCurrency(cmd.getPayCurrency());
		form.setValTime(DateUtil.getFomateDate(cmd.getVldDate(), "yyyyMMddHHmmss"));
		
		try {
			XwsResult result = xwsClient.putHeaderAndBody(xwsMsgHeader, form).call(true);
			String code = result.getCode();
			if("000000".equals(code)){
				cmd.setCmdState(CmdState.RequestSuccess.code());
				cmd.setRdoTime(new Date());
				cmd.setRtnCode(code);
			}else{
				cmd.setErtnCode(code);
				cmd.setRtnCode(code);
				cmd.setErtnInfo(result.getMessage());
				cmd.setCmdState(CmdState.TransationFail.code());
				cmd.setRdoTime(new Date());
			}
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 线下资金取回接口
	 */
	@Override
	public JwsResult notifyFundGiveOffline(NotifyOflCommandResultForm cmd) {
		JwsClient client = new JwsClient("EP-ORD-0045");
		
		JwsResult result = client.putParam("offlineCashConfirmRequest",cmd).call(true);
		
		return result;
		
	}
	
	/**
	 * 资金调拨通知B2B
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void notifyFundAllot(SacFundTransferCmd fundTransferCmd) {
		try {
			Map<String,String> params = new HashMap<String,String>();
			params.put("trxSerialNo",fundTransferCmd.getTrxSerialNo());
			params.put("dbtBankNodeCode",fundTransferCmd.getDraccNodeCode());
			params.put("crtBankNodeCode",fundTransferCmd.getCraccNodeCode());
			params.put("payAmount",fundTransferCmd.getPayAmount()+"");
			String url = FUND_ALLOT_URL;
			//String url = "http://10.68.30.94:8080/stt/takeback/takebackNotice.do";
			logger.info("请求URL："+url);
			logger.info("请求参数："+params.toString());
			JSONObject reqJson = JSONObject.fromObject(params);
			Map<String,String> reqMap = new HashMap<String, String>();
			reqMap.put("msg", reqJson.toString());
			String response = UrlUtil.methodPost(url, reqMap);
			logger.info("响应信息："+response);
			JSONObject fromObject = JSONObject.fromObject(response);
			Map<String,String> map = (Map<String,String>)fromObject;
			if(null!=map){
				String rtnCode = map.get("rtnCode");
				if(!"000000".equals(rtnCode)){
					logger.error("资金调拨审核通知B2B失败!");
				}
			}
		} catch (Exception e) {
			logger.error("资金调拨审核通知B2B失败!");
		}
		
	}



	public void setONL_FUND_GIVE_PASS_URL(String oNL_FUND_GIVE_PASS_URL) {
		ONL_FUND_GIVE_PASS_URL = oNL_FUND_GIVE_PASS_URL;
	}

	public void setFUND_ALLOT_URL(String fUND_ALLOT_URL) {
		FUND_ALLOT_URL = fUND_ALLOT_URL;
	}
	public void setPRESTORE_CHECK_PASS_URL(String pRESTORE_CHECK_PASS_URL) {
		PRESTORE_CHECK_PASS_URL = pRESTORE_CHECK_PASS_URL;
	}
	public void setOFL_PRESTORE_MANUAL_CHECK_PASS_URL(
			String oFL_PRESTORE_MANUAL_CHECK_PASS_URL) {
		OFL_PRESTORE_MANUAL_CHECK_PASS_URL = oFL_PRESTORE_MANUAL_CHECK_PASS_URL;
	}
	
	
	
	

	
}
