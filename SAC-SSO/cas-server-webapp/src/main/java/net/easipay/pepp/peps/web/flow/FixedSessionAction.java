/**
 * 
 */
package net.easipay.pepp.peps.web.flow;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.jasig.cas.authentication.principal.Response;
import org.jasig.cas.web.support.WebUtils;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

/**
 * @author Administrator
 * 固定sessionID
 */
public class FixedSessionAction extends AbstractAction {

	public static final Logger logger = Logger.getLogger(FixedSessionAction.class);
	/* (non-Javadoc)
	 * @see org.springframework.webflow.action.AbstractAction#doExecute(org.springframework.webflow.execution.RequestContext)
	 */
	@Override
	protected Event doExecute(RequestContext context) throws Exception {
		HttpServletRequest request = WebUtils.getHttpServletRequest(context);
		String sessionId = request.getSession().getId();
		Response response = (Response)context.getRequestScope().get("response");
		String redirectUrl = response.getUrl();
		String oper = redirectUrl.contains("?") ? "&" : "?";
		redirectUrl += oper + "ssid=" + sessionId;
		logger.debug("cas's redirectUrl ==" + redirectUrl);
		context.getFlowScope().put("fixedSessionRedirectUrl", redirectUrl);
		return success();
	}

}
