package net.easipay.cbp.view;

import net.easipay.cbp.wss.BaseWss;


public class SacTransWss extends BaseWss
{/*
    @WssRegister(value = "testFrame", service = "SAC-AC-1001", method = WssRequestMethod.POST, desc = "框架测试")
    public WsResult testFrame(JwsHttpRequest jwsHttpRequest)
    {
	JwsResult jwsResult = new JwsResult();
	try {
	    List<SacTransationReceiveForm> forms = jwsHttpRequest.getList("sacTransationDetails", SacTransationReceiveForm.class);
	    if (forms.size() == 0) {
		throw new AcException(Exceptions.EXP_010203, "数据不为空");
	    }
	    jwsHttpRequest.validateList(forms);
	    SpringServiceFactory.getBean(ISacTransService.class).receiveSacTransationDetails(forms);
	    jwsResult.setSuccess(SacConstants.RTN_SUCCESS, "成功");
	} catch ( AcException e ) {
	    log.error("SacTransController.receiveSacTransationDetail throw AcException", e);
	    jwsResult.setError(e.getCode(), e.getMessage());
	} catch ( Exception e ) {
	    log.error("SacTransController.receiveSacTransationDetail throw Exception", e);
	    jwsResult.setError(SacConstants.RTN_FAIL, e.getMessage());
	}
	return jwsResult;
    }
    
    @WssCall(value = "testFrame1",method = {WssRequestMethod.POST,WssRequestMethod.GET} )
    @WssRegister(value = "testFrame1", service = "SAC-AC-1002", method = WssRequestMethod.POST, desc = "框架测试")
    public WsResult testFrame1(JwsHttpRequest jwsHttpRequest)
    {
	JwsResult jwsResult = new JwsResult();
	try {
	    List<SacTransationReceiveForm> forms = jwsHttpRequest.getList("sacTransationDetails", SacTransationReceiveForm.class);
	    if (forms.size() == 0) {
		throw new AcException(Exceptions.EXP_010203, "数据不为空");
	    }
	    jwsHttpRequest.validateList(forms);
	    SpringServiceFactory.getBean(ISacTransService.class).receiveSacTransationDetails(forms);
	    jwsResult.setSuccess(SacConstants.RTN_SUCCESS, "成功");
	} catch ( AcException e ) {
	    log.error("SacTransController.receiveSacTransationDetail throw AcException", e);
	    jwsResult.setError(e.getCode(), e.getMessage());
	} catch ( Exception e ) {
	    log.error("SacTransController.receiveSacTransationDetail throw Exception", e);
	    jwsResult.setError(SacConstants.RTN_FAIL, e.getMessage());
	}
	return jwsResult;
    }
    
    @WssCall(value = "testFrame2",method=WssRequestMethod.GET)
    public WsResult testFrame2(JwsHttpRequest jwsHttpRequest)
    {
	JwsResult jwsResult = new JwsResult();
	try {
	    List<SacTransationReceiveForm> forms = jwsHttpRequest.getList("sacTransationDetails", SacTransationReceiveForm.class);
	    if (forms.size() == 0) {
		throw new AcException(Exceptions.EXP_010203, "数据不为空");
	    }
	    jwsHttpRequest.validateList(forms);
	    SpringServiceFactory.getBean(ISacTransService.class).receiveSacTransationDetails(forms);
	    jwsResult.setSuccess(SacConstants.RTN_SUCCESS, "成功");
	} catch ( AcException e ) {
	    log.error("SacTransController.receiveSacTransationDetail throw AcException", e);
	    jwsResult.setError(e.getCode(), e.getMessage());
	} catch ( Exception e ) {
	    log.error("SacTransController.receiveSacTransationDetail throw Exception", e);
	    jwsResult.setError(SacConstants.RTN_FAIL, e.getMessage());
	}
	return jwsResult;
    }

*/}
