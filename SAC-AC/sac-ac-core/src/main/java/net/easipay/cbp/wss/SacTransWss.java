package net.easipay.cbp.wss;

import java.util.List;

import net.easipay.cbp.constant.Exceptions;
import net.easipay.cbp.constant.SacConstants;
import net.easipay.cbp.exception.AcException;
import net.easipay.cbp.form.SacRemittanceBatchIdForm;
import net.easipay.cbp.form.SacTransationReceiveForm;
import net.easipay.cbp.form.SacTransationUpdateForm;
import net.easipay.cbp.service.ISacTransService;
import net.easipay.cbp.wss.interceptor.WssRecordLogInterceptor;
import net.easipay.dsfc.ws.WsResult;
import net.easipay.dsfc.ws.jws.JwsHttpRequest;
import net.easipay.dsfc.ws.jws.JwsResult;
import net.easipay.dsfc.ws.support.SpringServiceFactory;
import net.easipay.dsfc.ws.wss.WssRequestMapping;
import net.easipay.dsfc.ws.wss.WssRequestMethod;
import net.easipay.dsfc.ws.wss.aop.Before;

public class SacTransWss extends BaseWss
{
    @Before({ WssRecordLogInterceptor.class })
    @WssRequestMapping(value = "receiveSacTransationDetail", service = "SAC-AC-0001", method = WssRequestMethod.POST, desc = "接收交易数据接口")
    public WsResult receiveSacTransationDetail(JwsHttpRequest jwsHttpRequest)
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

    @Before({ WssRecordLogInterceptor.class })
    @WssRequestMapping(value = "updateSacTransationDetail", service = "SAC-AC-0002", method = WssRequestMethod.POST, desc = "更新交易数据状态接口")
    public WsResult updateSacTransationDetail(JwsHttpRequest jwsHttpRequest)
    {
	JwsResult jwsResult = new JwsResult();
	try {
	    SacTransationUpdateForm form = jwsHttpRequest.toBean(SacTransationUpdateForm.class);
	    jwsHttpRequest.validate(form);

	    SpringServiceFactory.getBean(ISacTransService.class).updateSacTransationDetail(form.getTrxSerialNo(), form.getEtrxSerialNo(), form.getTrxState(), form.getTrxStateDesc(), form.getTrxSuccTime());
	    jwsResult.setSuccess(SacConstants.RTN_SUCCESS, "成功");
	} catch ( AcException e ) {
	    log.error("SacTransController.updateSacTransationDetail throw AcException", e);
	    jwsResult.setError(e.getCode(), e.getMessage());
	} catch ( Exception e ) {
	    log.error("SacTransController.updateSacTransationDetail throw Exception", e);
	    jwsResult.setError(SacConstants.RTN_FAIL, e.getMessage());
	}
	return jwsResult;
    }

    @Before({ WssRecordLogInterceptor.class })
    @WssRequestMapping(value = "updateSacOtrxRemittanceBatchId", service = "SAC-AC-0015", method = WssRequestMethod.POST, desc = "更新付汇批次接口")
    public WsResult updateSacOtrxRemittanceBatchId(JwsHttpRequest jwsHttpRequest)
    {
	JwsResult jwsResult = new JwsResult();
	try {
	    List<SacRemittanceBatchIdForm> forms = jwsHttpRequest.getList("sacRemittanceBatchIdForms", SacRemittanceBatchIdForm.class);
	    jwsHttpRequest.validateList(forms);

	    SpringServiceFactory.getBean(ISacTransService.class).insertSacTrxRemittance(forms);
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
    
    
    @Before({ WssRecordLogInterceptor.class })
    @WssRequestMapping(value = "updateSacOtrxRemittanceBatchIdAsy", service = "SAC-AC-0021", method = WssRequestMethod.POST, desc = "异步更新付汇批次接口")
    public WsResult updateSacOtrxRemittanceBatchIdAsy(JwsHttpRequest jwsHttpRequest)
    {
	JwsResult jwsResult = new JwsResult();
	try {
	    List<SacRemittanceBatchIdForm> forms = jwsHttpRequest.getList("sacRemittanceBatchIdForms", SacRemittanceBatchIdForm.class);
	    jwsHttpRequest.validateList(forms);

	    SpringServiceFactory.getBean(ISacTransService.class).updateSacOtrxRemittanceBatchId(forms);
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
