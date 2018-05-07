package net.easipay.cbp.wss;

import net.easipay.cbp.constant.ReturnCode;
import net.easipay.cbp.form.AccountNoticeForm;
import net.easipay.cbp.service.RecDiffManager;
import net.easipay.cbp.util.SpringServiceFactory;
import net.easipay.dsfc.ws.WsResult;
import net.easipay.dsfc.ws.jws.JwsHttpRequest;
import net.easipay.dsfc.ws.jws.JwsResult;
import net.easipay.dsfc.ws.wss.WssRequestMapping;
import net.easipay.dsfc.ws.wss.WssRequestMethod;

public class ReceiveChannelDiffWss extends BaseWss {

	/**
	 * 接收账务系统关于渠道差错处理的通知
	 * 
	 * @param request
	 * @param response
	 * @param trxJson
	 * @throws Exception
	 */
	@WssRequestMapping(value = "noticeSacRecDiff", service = "SAC-REC-0001", method = WssRequestMethod.POST, desc = "接收渠道差错处理通知接口")
	public WsResult joinFinTaskByRule(JwsHttpRequest jwsHttpRequest) {
		JwsResult jwsResult = new JwsResult();

		AccountNoticeForm noticeform = null;
		try {
			// Json对象转到VO类
			noticeform = jwsHttpRequest.toBean(AccountNoticeForm.class);
		} catch (Exception e) {
			e.printStackTrace();
			jwsResult.setError(ReturnCode.RETURN_FAILURE_CODE,
					ReturnCode.JSON_FORMAT_ERROR);
			return jwsResult;
		}
		try {
			SpringServiceFactory.getBean(RecDiffManager.class)
					.processChannelRecDiff(noticeform);
			jwsResult.setSuccess(ReturnCode.RETURN_SUCCESS_CODE,
					ReturnCode.RETURN_SUCCESS_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			jwsResult.setError(ReturnCode.RETURN_FAILURE_CODE,
					ReturnCode.RETURN_FAILURE_INFO);
		}
		return jwsResult;
	}

}
