package net.easipay.cbp.wss;

import net.easipay.cbp.constant.SacConstants;
import net.easipay.cbp.exception.AcException;
import net.easipay.cbp.service.IBfjService;
import net.easipay.cbp.wss.interceptor.WssRecordLogInterceptor;
import net.easipay.dsfc.StringUtils;
import net.easipay.dsfc.ws.WsResult;
import net.easipay.dsfc.ws.jws.JwsHttpRequest;
import net.easipay.dsfc.ws.jws.JwsResult;
import net.easipay.dsfc.ws.support.SpringServiceFactory;
import net.easipay.dsfc.ws.wss.WssRequestMapping;
import net.easipay.dsfc.ws.wss.WssRequestMethod;
import net.easipay.dsfc.ws.wss.aop.Before;

public class SacBfjWss extends BaseWss
{
    @Before({ WssRecordLogInterceptor.class })
    @WssRequestMapping(value = "queryBfjYe", service = "SAC-AC-0013", method = WssRequestMethod.POST, desc = "备付金余额查询接口")
    public WsResult queryBfjYe(JwsHttpRequest jwsHttpRequest)
    {
	JwsResult jwsResult = new JwsResult();
	try {
	    String bankNodeCode = jwsHttpRequest.getStringValue("bankNodeCode");
	    if (StringUtils.isBlank(bankNodeCode)) {
		throw new AcException(SacConstants.RTN_FAIL, "银行节点代码不能为空");
	    }
	    String avaBal = SpringServiceFactory.getBean(IBfjService.class).queryBfjYe(bankNodeCode);
	    jwsResult.setValue("avaBal", avaBal);
	    jwsResult.setSuccess(SacConstants.RTN_SUCCESS, "成功");
	} catch ( AcException e ) {
	    log.error("SacBfjWss.queryBfjYe throw AcException", e);
	    jwsResult.setError(e.getCode(), e.getMessage());
	} catch ( Exception e ) {
	    log.error("SacBfjWss.queryBfjYe throw Exception", e);
	    jwsResult.setError(SacConstants.RTN_FAIL, e.getMessage());
	}
	return jwsResult;
    }
}
