package net.easipay.cbp.wss;

import java.util.List;

import net.easipay.cbp.FaException;
import net.easipay.cbp.form.FinBalanceQueryForm;
import net.easipay.cbp.form.FinBalanceRtnForm;
import net.easipay.cbp.form.FinDailyYueQueryForm;
import net.easipay.cbp.form.FinYueQueryForm;
import net.easipay.cbp.model.FinDailyBalance;
import net.easipay.cbp.service.IFinYueService;
import net.easipay.cbp.util.ConstantParams;
import net.easipay.dsfc.ws.WsResult;
import net.easipay.dsfc.ws.jws.JwsHttpRequest;
import net.easipay.dsfc.ws.jws.JwsResult;
import net.easipay.dsfc.ws.support.SpringServiceFactory;
import net.easipay.dsfc.ws.wss.WssRequestMapping;
import net.easipay.dsfc.ws.wss.WssRequestMethod;

import org.apache.log4j.Logger;

public class FinYueWss
{
    private static final Logger logger = Logger.getLogger(FinYueWss.class);

    /**
     * 余额查询接口
     * 
     * @throws Exception
     */
    @WssRequestMapping(value = "queryYue", service = "SAC-FA-0004", method = WssRequestMethod.POST, desc = "余额查询接口")
    public WsResult queryYue(JwsHttpRequest jwsHttpRequest)
    {
	JwsResult jwsResult = new JwsResult();
	try {
	    FinYueQueryForm form = jwsHttpRequest.toBean(FinYueQueryForm.class);

	    jwsHttpRequest.validate(form);

	    IFinYueService finYueService = SpringServiceFactory.getBean(IFinYueService.class);

	    String totalAmount = finYueService.queryYue(form);

	    jwsResult.setValue("totalAmount", totalAmount);
	    jwsResult.setSuccess(ConstantParams.RTN_CODE_SUCCESSS, "成功");
	} catch ( FaException ex ) {
	    logger.error(ex.getMessage());
	    jwsResult.setError(ex.getCode(), ex.getMessage());
	} catch ( Exception e ) {
	    logger.error(e.getMessage());
	    jwsResult.setError(ConstantParams.RTN_CODE_ERROR, e.getMessage());
	}
	return jwsResult;
    }
    /**
     * 东方付余额查询接口
     * 
     * @throws Exception
     */
    @WssRequestMapping(value = "queryBalance", service = "SAC-FA-0009", method = WssRequestMethod.POST, desc = "东方付余额查询接口")
    public WsResult queryBalance(JwsHttpRequest jwsHttpRequest)
    {
    	JwsResult jwsResult = new JwsResult();
    	try {
    		FinBalanceQueryForm form = jwsHttpRequest.toBean(FinBalanceQueryForm.class);
    		
    		jwsHttpRequest.validate(form);
    		
    		IFinYueService finYueService = SpringServiceFactory.getBean(IFinYueService.class);
    		
    		List<FinBalanceRtnForm> balanceList = finYueService.queryBalance(form);
    		
    		jwsResult.setValue("balanceList", balanceList);
    		jwsResult.setSuccess(ConstantParams.RTN_CODE_SUCCESSS, "成功");
    	} catch ( FaException ex ) {
    		logger.error(ex.getMessage());
    		jwsResult.setError(ex.getCode(), ex.getMessage());
    	} catch ( Exception e ) {
    		logger.error(e.getMessage());
    		jwsResult.setError(ConstantParams.RTN_CODE_ERROR, e.getMessage());
    	}
    	return jwsResult;
    }
    /**
     * 每日余额查询接口
     * 
     * @throws Exception
     */
    @WssRequestMapping(value = "queryDailyYue", service = "SAC-FA-0008", method = WssRequestMethod.POST, desc = "客户每日余额查询接口")
    public WsResult queryDailyYue(JwsHttpRequest jwsHttpRequest)
    {
    	JwsResult jwsResult = new JwsResult();
    	try {
    		FinDailyYueQueryForm form = jwsHttpRequest.toBean(FinDailyYueQueryForm.class);
    		
    		jwsHttpRequest.validate(form);
    		
    		IFinYueService finYueService = SpringServiceFactory.getBean(IFinYueService.class);
    		
    		FinDailyBalance finDailyBalance = finYueService.queryDailyBalance(form);
    		
    		jwsResult.setValue("finDailyBalance", finDailyBalance);
    		jwsResult.setSuccess(ConstantParams.RTN_CODE_SUCCESSS, "成功");
    	} catch ( FaException ex ) {
    		logger.error(ex.getMessage());
    		jwsResult.setError(ex.getCode(), ex.getMessage());
    	} catch ( Exception e ) {
    		logger.error(e.getMessage());
    		jwsResult.setError(ConstantParams.RTN_CODE_ERROR, e.getMessage());
    	}
    	return jwsResult;
    }

}
