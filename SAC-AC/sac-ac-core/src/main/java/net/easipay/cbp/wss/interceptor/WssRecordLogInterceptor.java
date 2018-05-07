/**
 * 
 */
package net.easipay.cbp.wss.interceptor;

import java.util.ArrayList;
import java.util.List;

import net.easipay.cbp.dao.ISacRecordLogDao;
import net.easipay.cbp.model.SacRecordLog;
import net.easipay.cbp.sequence.SequenceCreatorUtil;
import net.easipay.cbp.util.DateUtil;
import net.easipay.cbp.wss.BaseWss;
import net.easipay.dsfc.CommonUtils;
import net.easipay.dsfc.StringUtils;
import net.easipay.dsfc.ws.WsResult;
import net.easipay.dsfc.ws.jws.JwsHttpRequest;
import net.easipay.dsfc.ws.support.SpringServiceFactory;
import net.easipay.dsfc.ws.wss.WssActionInvocation;
import net.easipay.dsfc.ws.wss.aop.Interceptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author mchen
 * @date 2016-1-14
 */
public class WssRecordLogInterceptor implements Interceptor
{
    protected final Log logger = LogFactory.getLog(BaseWss.class);

    @Override
    public void intercept(WssActionInvocation ai)
    {
	if (logger.isDebugEnabled()) {
	    logger.debug("Start to WssRecordLogInterceptor.intercept...");
	}

	long currentTimeMillis = System.currentTimeMillis();
	ai.invoke();

	if (ai.getResult() != null && !ai.getResult().isSuccess()) {
	    insertSacRecordLog(ai, currentTimeMillis);
	}

	if (logger.isDebugEnabled()) {
	    logger.debug("End to WssRecordLogInterceptor.intercept...");
	}
    }

    private void insertSacRecordLog(WssActionInvocation ai, long currentTimeMillis)
    {
	JwsHttpRequest jwsRequest = null;
	try {
	    Object[] args = ai.getArgs();
	    if (args != null) {
		for (Object arg : args) {
		    if (arg instanceof JwsHttpRequest) {
			jwsRequest = (JwsHttpRequest) arg;
		    }
		}
	    }

	    ISacRecordLogDao dao = SpringServiceFactory.getBean(ISacRecordLogDao.class);
	    List<SacRecordLog> sacRecordLogs = new ArrayList<SacRecordLog>();
	    SacRecordLog log = new SacRecordLog();
	    log.setId(SequenceCreatorUtil.getTableId());
	    log.setTransactionId(jwsRequest == null ? "" : jwsRequest.getTransactionId());
	    log.setChannel(ai.getRequest().getContextPath());
	    log.setOrigin(jwsRequest == null ? "" : jwsRequest.getOrigin());
	    log.setClientIp(ai.getRequest().getRemoteAddr());
	    log.setServerIp(CommonUtils.getClientIp());
	    log.setInterfaceId(ai.getWssHandlerExecutionChain().getWssRequestMapping().service());
	    log.setTimemillis(System.currentTimeMillis() - currentTimeMillis);
	    log.setUrl(StringUtils.splicingLink(ai.getRequest().getContextPath(), ai.getRequest().getPathInfo(), ai.getWssHandlerExecutionChain().getWssRequestMapping().value()));
	    log.setTrxcode("");
/*	    if(jwsRequest != null){
		int indexOf = jwsRequest.getParsePostData().indexOf("trxType");
		if(indexOf != -1){
		    log.setTrxcode(jwsRequest.getParsePostData().substring(indexOf + 10, indexOf + 14));
		}
		indexOf = jwsRequest.getParsePostData().indexOf("trxCode");
		if(indexOf != -1){
		    log.setTrxcode(jwsRequest.getParsePostData().substring(indexOf + 10, indexOf + 14));
		}
	    }*/
	    
	    log.setCode(ai.getResult().getCode());
	    log.setMessage(StringUtils.skipsString(ai.getResult().getMessage(),1500));
	    log.setData(jwsRequest == null ? "" : StringUtils.skipsString(jwsRequest.getParsePostData(), 3500));
	    log.setCreateTime(DateUtil.currentDate());
	    log.setDemo(ai.getWssHandlerExecutionChain().getWssRequestMapping().desc());
	    sacRecordLogs.add(log);
	    dao.insertSacRecordLog(sacRecordLogs);

	} catch ( Exception e ) {
	    logger.error("WssRecordLogInterceptor.insertSacRecordLog throw exception", e);
	}
    }

	@Override
	public WsResult toErrorResult(WssActionInvocation paramWssActionInvocation) {
		// TODO Auto-generated method stub
		return null;
	}

}
