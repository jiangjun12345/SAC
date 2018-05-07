package net.easipay.cbp.wss;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.easipay.cbp.constant.SacConstants;
import net.easipay.cbp.exception.AcException;
import net.easipay.cbp.form.FinMxForm;
import net.easipay.cbp.form.FinMxRtnForm;
import net.easipay.cbp.form.SacFinDetailQueryForm;
import net.easipay.cbp.service.ISacFinDetailService;
import net.easipay.cbp.wss.interceptor.WssRecordLogInterceptor;
import net.easipay.dsfc.ws.WsResult;
import net.easipay.dsfc.ws.jws.JwsHttpRequest;
import net.easipay.dsfc.ws.jws.JwsResult;
import net.easipay.dsfc.ws.support.SpringServiceFactory;
import net.easipay.dsfc.ws.wss.WssRequestMapping;
import net.easipay.dsfc.ws.wss.WssRequestMethod;
import net.easipay.dsfc.ws.wss.aop.Before;

public class SacFinDetailWss extends BaseWss
{
    
    @SuppressWarnings("unchecked")
	@Before({ WssRecordLogInterceptor.class })
    @WssRequestMapping(value = "queryFinDetail", service = "SAC-AC-0023", method = WssRequestMethod.POST, desc = "资金明细查询接口")
    public WsResult queryFinDetail(JwsHttpRequest jwsHttpRequest)
    {
	JwsResult jwsResult = new JwsResult();
	try {
		SacFinDetailQueryForm form = jwsHttpRequest.toBean(SacFinDetailQueryForm.class);
	    jwsHttpRequest.validate(form);
	    
	    long a = System.currentTimeMillis();
	    
	    Map<String, Object> rtnMap = SpringServiceFactory.getBean(ISacFinDetailService.class).queryFinDetail(form);

	    if(log.isDebugEnabled()){
	    	log.debug("####"+(System.currentTimeMillis()-a)+"###");
	    } 
	    
	    long b = System.currentTimeMillis();
	    
	    List<FinMxForm> list = (List<FinMxForm>)rtnMap.get("mxList");
	    
	    List<FinMxRtnForm> rtnList = new ArrayList<FinMxRtnForm>();
    	 for(FinMxForm mx : list){
 	    	FinMxRtnForm rtnForm = new FinMxRtnForm();
 	    	rtnForm.setCloseBal(mx.getCloseBal());
 	    	rtnForm.setCodeId(mx.getCodeId());
 	    	rtnForm.setfCredit(mx.getfCredit());
 	    	rtnForm.setfDebit(mx.getfDebit());
 	    	rtnForm.setMxTime(mx.getMxTime());
 	    	rtnForm.setOpenBal(mx.getOpenBal());
 	    	rtnForm.setSerno(mx.getSerno());
 	    	rtnForm.setTotCnt(mx.getTotCnt());
 	    	rtnForm.setTradeTime(mx.getTradeTime());
 	    	rtnList.add(rtnForm);
 	    }
    	 
 	    if(log.isDebugEnabled()){
	    	log.debug("####"+(System.currentTimeMillis()-b)+"###");
	    } 

	    Integer totalCount = 0;// 查询记录总数
	    
	    if (list.size() != 0) {
		totalCount = list.get(0).getTotCnt();
	    }
	    jwsResult.setValue("totalCount", totalCount);
	    jwsResult.setValue("finMxList", rtnList);
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
