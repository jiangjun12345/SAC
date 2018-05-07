package net.easipay.cbp.wss;

import java.util.List;

import net.easipay.cbp.constant.SacConstants;
import net.easipay.cbp.exception.AcException;
import net.easipay.cbp.form.FinDailyBalanceRtnForm;
import net.easipay.cbp.form.SacAccountingDailyYueQueryForm;
import net.easipay.cbp.form.SacAccountingYueQueryForm;
import net.easipay.cbp.form.SacBalanceQueryForm;
import net.easipay.cbp.form.SacBalanceRtnForm;
import net.easipay.cbp.service.ISacAccountingService;
import net.easipay.cbp.util.DateUtil;
import net.easipay.cbp.wss.interceptor.WssRecordLogInterceptor;
import net.easipay.dsfc.ws.WsResult;
import net.easipay.dsfc.ws.jws.JwsHttpRequest;
import net.easipay.dsfc.ws.jws.JwsResult;
import net.easipay.dsfc.ws.support.SpringServiceFactory;
import net.easipay.dsfc.ws.wss.WssRequestMapping;
import net.easipay.dsfc.ws.wss.WssRequestMethod;
import net.easipay.dsfc.ws.wss.aop.Before;

public class SacAccountingWss extends BaseWss
{
    @Before({ WssRecordLogInterceptor.class })
    @WssRequestMapping(value = "queryYue", service = "SAC-AC-0017", method = WssRequestMethod.POST, desc = "余额查询接口")
    public WsResult queryYue(JwsHttpRequest jwsHttpRequest)
    {
	JwsResult jwsResult = new JwsResult();
	try {
	    SacAccountingYueQueryForm form = jwsHttpRequest.toBean(SacAccountingYueQueryForm.class);
	    jwsHttpRequest.validate(form);
	    String totalAmount = SpringServiceFactory.getBean(ISacAccountingService.class).queryYue(form);

	    jwsResult.setValue("totalAmount", totalAmount);
	    jwsResult.setSuccess(SacConstants.RTN_SUCCESS, "成功");
	} catch ( AcException e ) {
	    log.error("SacAccountingWss.queryYue throw AcException", e);
	    jwsResult.setError(e.getCode(), e.getMessage());
	} catch ( Exception e ) {
	    log.error("SacAccountingWss.queryYue throw Exception", e);
	    jwsResult.setError(SacConstants.RTN_FAIL, e.getMessage());
	}
	return jwsResult;
    }
    
    
    @Before({ WssRecordLogInterceptor.class })
    @WssRequestMapping(value = "queryDailyYue", service = "SAC-AC-0018", method = WssRequestMethod.POST, desc = "客户每日余额查询接口")
    public WsResult queryDailyYue(JwsHttpRequest jwsHttpRequest)
    {
	JwsResult jwsResult = new JwsResult();
	try {
	    SacAccountingDailyYueQueryForm form = jwsHttpRequest.toBean(SacAccountingDailyYueQueryForm.class);
	    jwsHttpRequest.validate(form);
	    
	    String queryDate = form.getQueryDate();
	    String formatCurrentDate = DateUtil.formatCurrentDate("yyyyMMdd");
	    if(queryDate.compareTo(formatCurrentDate)>=0){
	    	throw new AcException("999990", "查询日期必须小于当前日期");
	    }
	    
	    FinDailyBalanceRtnForm rtnForm = SpringServiceFactory.getBean(ISacAccountingService.class).queryDailyYue(form);

	    jwsResult.setValue("dailyBalance",rtnForm);
	    jwsResult.setSuccess(SacConstants.RTN_SUCCESS, "成功");
	} catch ( AcException e ) {
	    log.error("SacAccountingWss.queryDailyYue throw AcException", e);
	    jwsResult.setError(e.getCode(), e.getMessage());
	} catch ( Exception e ) {
	    log.error("SacAccountingWss.queryDailyYue throw Exception", e);
	    jwsResult.setError(SacConstants.RTN_FAIL, e.getMessage());
	}
	return jwsResult;
    }

    
    @Before({ WssRecordLogInterceptor.class })
    @WssRequestMapping(value = "queryBalance", service = "SAC-AC-0022", method = WssRequestMethod.POST, desc = "东方付客户余额查询接口")
    public WsResult queryBalance(JwsHttpRequest jwsHttpRequest)
    {
	JwsResult jwsResult = new JwsResult();
	try {
	    SacBalanceQueryForm form = jwsHttpRequest.toBean(SacBalanceQueryForm.class);
	    jwsHttpRequest.validate(form);
	    
	    List<SacBalanceRtnForm> balanceList = SpringServiceFactory.getBean(ISacAccountingService.class).queryBalance(form);

	    jwsResult.setValue("balanceList", balanceList);
	    jwsResult.setSuccess(SacConstants.RTN_SUCCESS, "成功");
	} catch ( AcException e ) {
	    log.error("SacAccountingWss.queryBalance throw AcException", e);
	    jwsResult.setError(e.getCode(), e.getMessage());
	} catch ( Exception e ) {
	    log.error("SacAccountingWss.queryBalance throw Exception", e);
	    jwsResult.setError(SacConstants.RTN_FAIL, e.getMessage());
	}
	return jwsResult;
    }

}
