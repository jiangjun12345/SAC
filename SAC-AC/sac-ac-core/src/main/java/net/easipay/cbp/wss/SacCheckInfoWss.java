package net.easipay.cbp.wss;

import net.easipay.cbp.constant.SacConstants;
import net.easipay.cbp.exception.AcException;
import net.easipay.cbp.form.SacReplacePayReceiveForm;
import net.easipay.cbp.service.ISacReplacePayService;
import net.easipay.cbp.wss.interceptor.WssRecordLogInterceptor;
import net.easipay.dsfc.ws.WsResult;
import net.easipay.dsfc.ws.jws.JwsHttpRequest;
import net.easipay.dsfc.ws.jws.JwsResult;
import net.easipay.dsfc.ws.support.SpringServiceFactory;
import net.easipay.dsfc.ws.wss.WssRequestMapping;
import net.easipay.dsfc.ws.wss.WssRequestMethod;
import net.easipay.dsfc.ws.wss.aop.Before;

public class SacCheckInfoWss extends BaseWss
{
    
    @Before({ WssRecordLogInterceptor.class })
    @WssRequestMapping(value = "receiveReplacePayCheckInfo", service = "SAC-AC-0019", method = WssRequestMethod.POST, desc = "接收代付审核信息接口")
    public WsResult receiveReplacePayCheckInfo(JwsHttpRequest jwsHttpRequest)
    {
    	JwsResult jwsResult = new JwsResult();
    	try {
    		SacReplacePayReceiveForm form = jwsHttpRequest.getBean("sacReplacePaycheckInfo", SacReplacePayReceiveForm.class);
    		jwsHttpRequest.validate(form);
    		
    		SpringServiceFactory.getBean(ISacReplacePayService.class).receiveSacReplacePayCheckInfo(form);
    		jwsResult.setSuccess(SacConstants.RTN_SUCCESS, "成功");
    	} catch ( AcException e ) {
    		log.error("SacTransController.updateSacOtrxRemittanceBatchId throw AcException", e);
    		jwsResult.setError(e.getCode(), e.getMessage());
    	} catch ( Exception e ) {
    		log.error("SacTransController.updateSacOtrxRemittanceBatchId throw Exception", e);
    		jwsResult.setError(SacConstants.RTN_FAIL, e.getMessage());
    	}
    	return jwsResult;
    }

}
