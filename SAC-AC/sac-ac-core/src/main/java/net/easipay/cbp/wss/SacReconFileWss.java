package net.easipay.cbp.wss;

import java.util.List;

import net.easipay.cbp.constant.SacConstants;
import net.easipay.cbp.exception.AcException;
import net.easipay.cbp.form.SacReconBankReceiveForm;
import net.easipay.cbp.form.SacReconDifBankReceiveForm;
import net.easipay.cbp.form.SacReconInternalReceiveForm;
import net.easipay.cbp.service.ISacReconFileService;
import net.easipay.cbp.wss.interceptor.WssRecordLogInterceptor;
import net.easipay.dsfc.ws.WsResult;
import net.easipay.dsfc.ws.fws.FwsHttpRequest;
import net.easipay.dsfc.ws.fws.FwsResult;
import net.easipay.dsfc.ws.support.SpringServiceFactory;
import net.easipay.dsfc.ws.wss.WssRequestMapping;
import net.easipay.dsfc.ws.wss.WssRequestMethod;
import net.easipay.dsfc.ws.wss.aop.Before;

public class SacReconFileWss extends BaseWss
{
    @Before({WssRecordLogInterceptor.class})
    @WssRequestMapping(value = "receiveInternalReconFile", service = "SAC-AC-0005", method = WssRequestMethod.POST, desc = "接收内部对账文件接口")
    public WsResult receiveInternalReconFile(FwsHttpRequest fwsHttpRequest)
    {
	FwsResult fwsResult = new FwsResult();
	try {
	    List<SacReconInternalReceiveForm> forms = fwsHttpRequest.getList(SacReconInternalReceiveForm.class);
	    fwsHttpRequest.validateList(forms);
	    SpringServiceFactory.getBean(ISacReconFileService.class).receiveInternalReconFile(forms);
	    fwsResult.setSuccess(SacConstants.RTN_SUCCESS, "成功");
	} catch ( AcException e ) {
	    log.error("SacReconFileWss.receiveInternalReconFile throw AcException", e);
	    fwsResult.setError(e.getCode(), e.getMessage());
	} catch ( Exception e ) {
	    log.error("SacReconFileWss.receiveInternalReconFile throw Exception", e);
	    fwsResult.setError(SacConstants.RTN_FAIL, e.getMessage());
	}
	return fwsResult;
    }

    @Before({WssRecordLogInterceptor.class})
    @WssRequestMapping(value = "receiveBankReconFile", service = "SAC-AC-0006", method = WssRequestMethod.POST, desc = "接收渠道对账文件接口")
    public WsResult receiveBankReconFile(FwsHttpRequest fwsHttpRequest)
    {
	FwsResult fwsResult = new FwsResult();
	try {
	    List<SacReconBankReceiveForm> forms = fwsHttpRequest.getList(SacReconBankReceiveForm.class);
	    fwsHttpRequest.validateList(forms);
	    SpringServiceFactory.getBean(ISacReconFileService.class).receiveBankReconFile(forms);
	    fwsResult.setSuccess(SacConstants.RTN_SUCCESS, "成功");
	} catch ( AcException e ) {
	    log.error("SacReconFileWss.receiveBankReconFile throw AcException", e);
	    fwsResult.setError(e.getCode(), e.getMessage());
	} catch ( Exception e ) {
	    log.error("SacReconFileWss.receiveBankReconFile throw Exception", e);
	    fwsResult.setError(SacConstants.RTN_FAIL, e.getMessage());
	}
	return fwsResult;
    }

    @Before({WssRecordLogInterceptor.class})
    @WssRequestMapping(value = "receiveBankDifReconFile", service = "SAC-AC-0007", method = WssRequestMethod.POST, desc = "接收差错数据接口")
    public WsResult receiveBankDifReconFile(FwsHttpRequest fwsHttpRequest)
    {
	FwsResult fwsResult = new FwsResult();
	try {
	    List<SacReconDifBankReceiveForm> forms = fwsHttpRequest.getList(SacReconDifBankReceiveForm.class);
	    fwsHttpRequest.validateList(forms);
	    SpringServiceFactory.getBean(ISacReconFileService.class).receiveBankDifReconFile(forms);
	    fwsResult.setSuccess(SacConstants.RTN_SUCCESS, "成功");
	} catch ( AcException e ) {
	    log.error("SacReconFileWss.receiveBankDifReconFile throw AcException", e);
	    fwsResult.setError(e.getCode(), e.getMessage());
	} catch ( Exception e ) {
	    log.error("SacReconFileWss.receiveBankDifReconFile throw Exception", e);
	    fwsResult.setError(SacConstants.RTN_FAIL, e.getMessage());
	}
	return fwsResult;
    }
}
