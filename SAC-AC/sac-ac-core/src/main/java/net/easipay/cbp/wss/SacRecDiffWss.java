package net.easipay.cbp.wss;

import java.util.List;

import net.easipay.cbp.constant.SacConstants;
import net.easipay.cbp.exception.AcException;
import net.easipay.cbp.form.SacRecDifferencesQueryForm;
import net.easipay.cbp.form.SacRecDifferencesQueryResult;
import net.easipay.cbp.form.SacRecDifferencesUpdateForm;
import net.easipay.cbp.service.ISacRecDiffService;
import net.easipay.cbp.wss.interceptor.WssRecordLogInterceptor;
import net.easipay.dsfc.ws.WsResult;
import net.easipay.dsfc.ws.jws.JwsHttpRequest;
import net.easipay.dsfc.ws.jws.JwsHttpResponse;
import net.easipay.dsfc.ws.jws.JwsResult;
import net.easipay.dsfc.ws.support.SpringServiceFactory;
import net.easipay.dsfc.ws.wss.WssRequestMapping;
import net.easipay.dsfc.ws.wss.WssRequestMethod;
import net.easipay.dsfc.ws.wss.aop.Before;

public class SacRecDiffWss extends BaseWss
{
    @Before({WssRecordLogInterceptor.class})
    @WssRequestMapping(value = "querySacRecDifferencesList", service = "SAC-AC-0008", method = WssRequestMethod.POST, desc = "下载差错数据接口")
    public WsResult querySacRecDifferencesList(JwsHttpRequest jwsHttpRequest)
    {
	JwsResult jwsResult = new JwsResult();
	try {
	    SacRecDifferencesQueryForm form = jwsHttpRequest.toBean(SacRecDifferencesQueryForm.class);
	    jwsHttpRequest.validate(form);
	    List<SacRecDifferencesQueryResult> sacRecDifferencesList = SpringServiceFactory.getBean(ISacRecDiffService.class).querySacRecDifferencesList(form);
	    jwsResult.setValue("sacRecDifferencesList", sacRecDifferencesList);
	    jwsResult.setSuccess(SacConstants.RTN_SUCCESS, "成功");
	} catch ( AcException e ) {
	    log.error("SacRecDiffWss.querySacRecDifferencesList throw AcException", e);
	    jwsResult.setError(e.getCode(), e.getMessage());
	} catch ( Exception e ) {
	    log.error("SacRecDiffWss.querySacRecDifferencesList throw Exception", e);
	    jwsResult.setError(SacConstants.RTN_FAIL, e.getMessage());
	}
	return jwsResult;
    }

    @Before({WssRecordLogInterceptor.class})
    @WssRequestMapping(value = "updateSacRecDifferences", service = "SAC-AC-0009", method = WssRequestMethod.POST, desc = "更新差错数据状态接口")
    public WsResult updateSacRecDifferences(JwsHttpRequest jwsHttpRequest, JwsHttpResponse jwsHttpResponse)
    {
	JwsResult jwsResult = new JwsResult();
	try {
	    SacRecDifferencesUpdateForm form = jwsHttpRequest.toBean(SacRecDifferencesUpdateForm.class);
	    jwsHttpRequest.validate(form);
	    SpringServiceFactory.getBean(ISacRecDiffService.class).updateSacRecDifferences(form);
	    jwsResult.setSuccess(SacConstants.RTN_SUCCESS, "成功");
	} catch ( AcException e ) {
	    log.error("SacRecDiffWss.updateSacRecDifferences throw AcException", e);
	    jwsResult.setError(e.getCode(), e.getMessage());
	} catch ( Exception e ) {
	    log.error("SacRecDiffWss.updateSacRecDifferences throw Exception", e);
	    jwsResult.setError(SacConstants.RTN_FAIL, e.getMessage());
	}
	return jwsResult;
    }

}
