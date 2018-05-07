package net.easipay.cbp.wss;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.constant.SacConstants;
import net.easipay.cbp.exception.AcException;
import net.easipay.cbp.form.SacCusParameterHandleForm;
import net.easipay.cbp.form.SacCusParameterValidateForm;
import net.easipay.cbp.service.ISacCusParamService;
import net.easipay.cbp.wss.interceptor.WssRecordLogInterceptor;
import net.easipay.dsfc.ws.WsResult;
import net.easipay.dsfc.ws.jws.JwsHttpRequest;
import net.easipay.dsfc.ws.jws.JwsHttpResponse;
import net.easipay.dsfc.ws.jws.JwsResult;
import net.easipay.dsfc.ws.support.SpringServiceFactory;
import net.easipay.dsfc.ws.wss.WssRequestMapping;
import net.easipay.dsfc.ws.wss.WssRequestMethod;
import net.easipay.dsfc.ws.wss.aop.Before;

public class SacCusParamWss extends BaseWss
{
    @Before({WssRecordLogInterceptor.class})
    @WssRequestMapping(value = "receiveSacCusParameter", service = "SAC-AC-0004", method = WssRequestMethod.POST, desc = "客户参数接收接口")
    public WsResult receiveSacCusParameter(JwsHttpRequest jwsHttpRequest, JwsHttpResponse jwsHttpResponse)
    {
	JwsResult jwsResult = new JwsResult();
	try {
	    SacCusParameterHandleForm form = jwsHttpRequest.getBean("sacCusParameter", SacCusParameterHandleForm.class);
	    jwsHttpRequest.validate(form);
	    SpringServiceFactory.getBean(ISacCusParamService.class).receiveSacCusParameter(form);
	    jwsResult.setSuccess(SacConstants.RTN_SUCCESS, "成功");
	} catch ( AcException e ) {
	    log.error("SacCusParamWss.receiveSacCusParameter throw AcException", e);
	    jwsResult.setError(e.getCode(), e.getMessage());
	} catch ( Exception e ) {
	    log.error("SacCusParamWss.receiveSacCusParameter throw Exception", e);
	    jwsResult.setError(SacConstants.RTN_FAIL, e.getMessage());
	}
	return jwsResult;
    }
    
    @Before({WssRecordLogInterceptor.class})
    @WssRequestMapping(value = "validateSacCusParameter", service = "SAC-AC-0020", method = WssRequestMethod.POST, desc = "客户参数校验接口")
    public WsResult validateSacCusParameter(JwsHttpRequest jwsHttpRequest, JwsHttpResponse jwsHttpResponse)
    {
    	JwsResult jwsResult = new JwsResult();
    	try {
    		List<SacCusParameterValidateForm> forms = jwsHttpRequest.getList("sacCusParamsValidates", SacCusParameterValidateForm.class);
    		jwsHttpRequest.validateList(forms);
    		Map<String, Boolean> responseMap = SpringServiceFactory.getBean(ISacCusParamService.class).validateSacCusParameter(forms);
    		jwsResult.setValue("validateResults", responseMap);
    		jwsResult.setSuccess(SacConstants.RTN_SUCCESS, "成功");
    	} catch ( AcException e ) {
    		log.error("SacCusParamWss.receiveSacCusParameter throw AcException", e);
    		jwsResult.setError(e.getCode(), e.getMessage());
    	} catch ( Exception e ) {
    		log.error("SacCusParamWss.receiveSacCusParameter throw Exception", e);
    		jwsResult.setError(SacConstants.RTN_FAIL, e.getMessage());
    	}
    	return jwsResult;
    }

}
