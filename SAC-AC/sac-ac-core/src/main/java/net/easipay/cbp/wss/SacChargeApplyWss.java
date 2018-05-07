package net.easipay.cbp.wss;

import net.easipay.cbp.constant.SacConstants;
import net.easipay.cbp.exception.AcException;
import net.easipay.cbp.form.SacChargeApplyForm;
import net.easipay.cbp.service.ISacChargeApplyService;
import net.easipay.cbp.wss.interceptor.WssRecordLogInterceptor;
import net.easipay.dsfc.ws.WsResult;
import net.easipay.dsfc.ws.jws.JwsHttpRequest;
import net.easipay.dsfc.ws.jws.JwsResult;
import net.easipay.dsfc.ws.support.SpringServiceFactory;
import net.easipay.dsfc.ws.wss.WssRequestMapping;
import net.easipay.dsfc.ws.wss.WssRequestMethod;
import net.easipay.dsfc.ws.wss.aop.Before;

public class SacChargeApplyWss extends BaseWss
{
    @Before({WssRecordLogInterceptor.class})
    @WssRequestMapping(value = "receiveSacChargeApply", service = "SAC-AC-0012", method = WssRequestMethod.POST, desc = "线下预存申请接口")
    public WsResult receiveSacChargeApply(JwsHttpRequest jwsHttpRequest)
    {
	JwsResult jwsResult = new JwsResult();
	try {
	    SacChargeApplyForm form = jwsHttpRequest.getBean("sacChargeApplyForm", SacChargeApplyForm.class);
	    jwsHttpRequest.validate(form);
	    SpringServiceFactory.getBean(ISacChargeApplyService.class).receiveSacChargeApply(form);
	    jwsResult.setSuccess(SacConstants.RTN_SUCCESS, "成功");
	} catch ( AcException e ) {
	    log.error("SacChargeApplyWss.receiveSacChargeApply throw AcException", e);
	    jwsResult.setError(e.getCode(), e.getMessage());
	} catch ( Exception e ) {
	    log.error("SacChargeApplyWss.receiveSacChargeApply throw Exception", e);
	    jwsResult.setError(SacConstants.RTN_FAIL, e.getMessage());
	}
	return jwsResult;
    }

}
