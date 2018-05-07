package net.easipay.cbp.wss;

import java.util.ArrayList;
import java.util.List;

import net.easipay.cbp.constant.SacConstants;
import net.easipay.cbp.exception.AcException;
import net.easipay.cbp.form.SacChannelParamHandleForm;
import net.easipay.cbp.form.SacChannelParamQueryForm;
import net.easipay.cbp.model.SacChannelParam;
import net.easipay.cbp.service.ISacChannelParamService;
import net.easipay.cbp.wss.interceptor.WssRecordLogInterceptor;
import net.easipay.dsfc.ws.WsResult;
import net.easipay.dsfc.ws.jws.JwsHttpRequest;
import net.easipay.dsfc.ws.jws.JwsResult;
import net.easipay.dsfc.ws.support.SpringServiceFactory;
import net.easipay.dsfc.ws.wss.WssRequestMapping;
import net.easipay.dsfc.ws.wss.WssRequestMethod;
import net.easipay.dsfc.ws.wss.aop.Before;

public class SacChannelParamWss extends BaseWss
{
    @Before({WssRecordLogInterceptor.class})
    @WssRequestMapping(value = "receiveSacChannelParam", service = "SAC-AC-0003", method = WssRequestMethod.POST, desc = "接收渠道参数接口")
    public WsResult receiveSacChannelParam(JwsHttpRequest jwsHttpRequest)
    {
	JwsResult jwsResult = new JwsResult();
	try {
	    SacChannelParamHandleForm form = jwsHttpRequest.getBean("sacChannelParam", SacChannelParamHandleForm.class);
	    jwsHttpRequest.validate(form);
	    SpringServiceFactory.getBean(ISacChannelParamService.class).receiveSacChannelParam(form);
	    jwsResult.setSuccess(SacConstants.RTN_SUCCESS, "成功");
	} catch ( AcException e ) {
	    log.error("SacChannelParamWss.receiveSacChannelParam throw AcException", e);
	    jwsResult.setError(e.getCode(), e.getMessage());
	} catch ( Exception e ) {
	    log.error("SacChannelParamWss.receiveSacChannelParam throw Exception", e);
	    jwsResult.setError(SacConstants.RTN_FAIL, e.getMessage());
	}
	return jwsResult;
    }
    
    @WssRequestMapping(value = "listSacChannelParam", service = "SAC-AC-0014", method = WssRequestMethod.POST, desc = "查询渠道参数接口")
    public WsResult listSacChannelParam(JwsHttpRequest jwsHttpRequest)
    {
	JwsResult jwsResult = new JwsResult();
	try {
	    SacChannelParamQueryForm form = jwsHttpRequest.toBean(SacChannelParamQueryForm.class);
	    List<SacChannelParam> listSacChannelParam = SpringServiceFactory.getBean(ISacChannelParamService.class).listSacChannelParam(form.getChnCode(), form.getChnType(), form.getCurrencyType(), form.getIsValidFlag());
	    jwsResult.setValue("sacChannelParams", listSacChannelParam == null ? new ArrayList<SacChannelParam>() : listSacChannelParam);
	    jwsResult.setSuccess(SacConstants.RTN_SUCCESS, "成功");
	} catch ( AcException e ) {
	    log.error("SacChannelParamWss.listSacChannelParam throw AcException", e);
	    jwsResult.setError(e.getCode(), e.getMessage());
	} catch ( Exception e ) {
	    log.error("SacChannelParamWss.listSacChannelParam throw Exception", e);
	    jwsResult.setError(SacConstants.RTN_FAIL, e.getMessage());
	}
	return jwsResult;
    }

}
