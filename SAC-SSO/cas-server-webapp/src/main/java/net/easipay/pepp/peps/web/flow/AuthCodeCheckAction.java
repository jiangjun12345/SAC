/**
 * 
 */
package net.easipay.pepp.peps.web.flow;

import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.easipay.pepp.peps.authentication.principal.EnhanceUsernamePasswordCredentials;
import net.easipay.pepp.peps.util.AuthCodeImageUtil;

import org.jasig.cas.web.support.WebUtils;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.util.StringUtils;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

/**
 * @author Administrator
 * 验证码相关操作
 */
public class AuthCodeCheckAction extends AbstractAction {
	
	/**
	 * 设置验证次数
	 */
	private int authNumber;

	/* (non-Javadoc)
	 * @see org.springframework.webflow.action.AbstractAction#doExecute(org.springframework.webflow.execution.RequestContext)
	 */
	@Override
	protected Event doExecute(RequestContext context) throws Exception {
		HttpServletRequest request = WebUtils.getHttpServletRequest(context);
		HttpSession session = request.getSession();
		AtomicInteger atomic = (AtomicInteger)session.getAttribute("_SESSION_ATOMIC_");
		if(null == atomic){
			atomic = new AtomicInteger(0);
			session.setAttribute("_SESSION_ATOMIC_", atomic);
		}
		if(atomic.incrementAndGet() > authNumber){
			context.getFlowScope().put("authCodeShow", true);
			String authCode = (String)session.getAttribute(AuthCodeImageUtil.AUTH_CODE_SESSION_KEY);
			if(StringUtils.hasText(authCode)){
				EnhanceUsernamePasswordCredentials credentials = (EnhanceUsernamePasswordCredentials)context.getFlowScope().get("credentials");
			    if(authCode.equals(credentials.getAuthCode())){
			    	session.removeAttribute(AuthCodeImageUtil.AUTH_CODE_SESSION_KEY);
			    	return success();
			    } else {
			    	context.getMessageContext().addMessage(new MessageBuilder().error().code("error.authentiaction.authCode").defaultText("authCode error").build());
			    	return error();
			    }
			}
		} else {
			context.getFlowScope().put("authCodeShow", false);
		}
		return success();
	}

	public int getAuthNumber() {
		return authNumber;
	}

	public void setAuthNumber(int authNumber) {
		if(authNumber < 0){
			this.authNumber = 3;
		} else {
			this.authNumber = authNumber;
		}
	}

}
