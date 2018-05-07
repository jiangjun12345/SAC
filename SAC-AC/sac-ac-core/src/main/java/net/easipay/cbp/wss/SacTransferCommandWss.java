package net.easipay.cbp.wss;

import net.easipay.cbp.constant.SacConstants;
import net.easipay.cbp.exception.AcException;
import net.easipay.cbp.form.SacDffOflCommandReceiveForm;
import net.easipay.cbp.form.SacTransferCommandReceiveForm;
import net.easipay.cbp.service.ISacTransferCommandService;
import net.easipay.cbp.wss.interceptor.WssRecordLogInterceptor;
import net.easipay.dsfc.ws.WsResult;
import net.easipay.dsfc.ws.jws.JwsHttpRequest;
import net.easipay.dsfc.ws.jws.JwsResult;
import net.easipay.dsfc.ws.support.SpringServiceFactory;
import net.easipay.dsfc.ws.wss.WssRequestMapping;
import net.easipay.dsfc.ws.wss.WssRequestMethod;
import net.easipay.dsfc.ws.wss.aop.Before;

public class SacTransferCommandWss extends BaseWss
{
    @Before({WssRecordLogInterceptor.class})
    @WssRequestMapping(value = "receiveSacTransferCommand", service = "SAC-AC-0016", method = WssRequestMethod.POST, desc = "接收划款指令")
    public WsResult receiveSacTransferCommand(JwsHttpRequest jwsHttpRequest)
    {
	JwsResult jwsResult = new JwsResult();
	try {
	    SacTransferCommandReceiveForm form = jwsHttpRequest.getBean("sacTransferCommandReceiveForm", SacTransferCommandReceiveForm.class);
	    jwsHttpRequest.validate(form);
	    SpringServiceFactory.getBean(ISacTransferCommandService.class).receiveSacTransferCommand(form);
	    jwsResult.setSuccess(SacConstants.RTN_SUCCESS, "成功");
	} catch ( AcException e ) {
	    log.error("SacTransferCommandWss.receiveSacTransferCommand throw AcException", e);
	    jwsResult.setError(e.getCode(), e.getMessage());
	} catch ( Exception e ) {
	    log.error("SacTransferCommandWss.receiveSacTransferCommand throw Exception", e);
	    jwsResult.setError(SacConstants.RTN_FAIL, e.getMessage());
	}
	return jwsResult;
    }
    
    
    @Before({WssRecordLogInterceptor.class})
    @WssRequestMapping(value = "receiveSacDffOflCommand", service = "SAC-AC-0025", method = WssRequestMethod.POST, desc = "接收线下出款指令")
    public WsResult receiveOflCommand(JwsHttpRequest jwsHttpRequest)
    {
	JwsResult jwsResult = new JwsResult();
	try {
	    SacDffOflCommandReceiveForm form = jwsHttpRequest.getBean("sacDffOflCommand", SacDffOflCommandReceiveForm.class);
	    jwsHttpRequest.validate(form);
	    SpringServiceFactory.getBean(ISacTransferCommandService.class).receiveSacDffOflCommand(form);
	    jwsResult.setSuccess(SacConstants.RTN_SUCCESS, "成功");
	} catch ( AcException e ) {
	    log.error("SacTransferCommandWss.receiveSacDffOflCommand throw AcException", e);
	    jwsResult.setError(e.getCode(), e.getMessage());
	} catch ( Exception e ) {
	    log.error("SacTransferCommandWss.receiveSacDffOflCommand throw Exception", e);
	    jwsResult.setError(SacConstants.RTN_FAIL, e.getMessage());
	}
	return jwsResult;
    }
    

}
