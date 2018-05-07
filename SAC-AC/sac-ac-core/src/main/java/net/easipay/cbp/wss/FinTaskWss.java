package net.easipay.cbp.wss;

import java.util.List;

import net.easipay.cbp.constant.SacConstants;
import net.easipay.cbp.exception.AcException;
import net.easipay.cbp.form.FinRuleTaskJoinForm;
import net.easipay.cbp.service.IFinTaskService;
import net.easipay.cbp.wss.interceptor.WssRecordLogInterceptor;
import net.easipay.dsfc.ws.WsResult;
import net.easipay.dsfc.ws.jws.JwsHttpRequest;
import net.easipay.dsfc.ws.jws.JwsResult;
import net.easipay.dsfc.ws.support.SpringServiceFactory;
import net.easipay.dsfc.ws.wss.WssRequestMapping;
import net.easipay.dsfc.ws.wss.WssRequestMethod;
import net.easipay.dsfc.ws.wss.aop.Before;

public class FinTaskWss extends BaseWss
{
    @Before({WssRecordLogInterceptor.class})
    @WssRequestMapping(value = "joinRuleFinTask", service = "SAC-AC-0010", method = WssRequestMethod.POST, desc = "通知记账接口")
    public WsResult joinFinTaskByRule(JwsHttpRequest jwsHttpRequest)
    {
	JwsResult jwsResult = new JwsResult();
	try {
	    List<FinRuleTaskJoinForm> forms = jwsHttpRequest.getList("finRuleTasks", FinRuleTaskJoinForm.class);
	    jwsHttpRequest.validateList(forms);
	    SpringServiceFactory.getBean(IFinTaskService.class).insertForeignFinTasksForRule(forms);
	    jwsResult.setSuccess(SacConstants.RTN_SUCCESS, "成功");
	} catch ( AcException e ) {
	    log.error("FinTaskWss.joinFinTaskByRule throw AcException", e);
	    jwsResult.setError(e.getCode(), e.getMessage());
	} catch ( Exception e ) {
	    log.error("FinTaskWss.joinFinTaskByRule throw Exception", e);
	    jwsResult.setError(SacConstants.RTN_FAIL, e.getMessage());
	}
	return jwsResult;
    }

}
