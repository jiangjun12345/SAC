/**
 * 
 */
package net.easipay.pepp.peps.web.flow;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import net.easipay.pepp.peps.util.PepsPropertiesSupport;
import net.easipay.pepp.peps.util.ServiceUtil;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jasig.cas.authentication.principal.Service;
import org.jasig.cas.web.support.WebUtils;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

/**
 * @author Administrator
 * 确认目标服务器的登录页面
 */
public class InitServiceViewAction extends AbstractAction {
	
	public static final Logger logger = Logger.getLogger(InitServiceViewAction.class);

	private PepsPropertiesSupport propertiesSupport;
	
	/* (non-Javadoc)
	 * @see org.springframework.webflow.action.AbstractAction#doExecute(org.springframework.webflow.execution.RequestContext)
	 */
	@Override
	protected Event doExecute(RequestContext requestContext) throws Exception {
		initViewParameters(requestContext);
		return success();
	}
	
	/**
	 * 初始化页面各种参数
	 * @param requestContext
	 */
	private void initViewParameters(RequestContext requestContext){
		String defaultServiceName = "default";
		String activeId = requestContext.getActiveFlow().getId();//loginID
		Service service = (Service)requestContext.getFlowScope().get("service");
		//抓取目标Service标示符
		String targetService = ServiceUtil.fetchTargetService(service);
		String formPage = ServiceUtil.getValueByKey(targetService, "cas.loginform.", defaultServiceName, this.propertiesSupport);
		String domain = null;
		String loginViewName = null;
		if("loginB".equals(activeId)){
			loginViewName = ServiceUtil.getValueByKey(targetService, "cas.loginView.", defaultServiceName, this.propertiesSupport);
			domain = ServiceUtil.getValueByKey(targetService, "cas.domain.", defaultServiceName, this.propertiesSupport);
		} else {
			String targetServiceName = dealBillTEXT(requestContext);
			if("paymentLogin".equals(targetServiceName)){
				defaultServiceName = targetServiceName;
			}
			loginViewName = ServiceUtil.getValueByKey(defaultServiceName, "cas.loginView.", defaultServiceName, this.propertiesSupport);
			domain = ServiceUtil.getValueByKey(defaultServiceName, "cas.domain.", defaultServiceName, this.propertiesSupport);
		}
		logger.debug("loginViewName="+loginViewName);
		logger.debug("domain="+domain);
		String pepsDomain = propertiesSupport.getValue("cas.domain.pc");
		logger.debug("pepsDomain="+pepsDomain);
		requestContext.getFlowScope().put("ServiceDomain", domain);
		requestContext.getFlowScope().put("formPageName", formPage);
		requestContext.getFlowScope().put("loginViewName", loginViewName);
		requestContext.getFlowScope().put("pepsDomain", pepsDomain);
		requestContext.getFlowScope().put("targetService", targetService);
		
		HttpServletRequest request = WebUtils.getHttpServletRequest(requestContext);
		String regUrl = propertiesSupport.getValue("reg.url");
		
		String requestType = request.getParameter("requestType");
		if("innerType".equals(requestType)){
			String sep = regUrl.indexOf("?") > 0 ? "&" : "?";
			regUrl = regUrl + sep + "requestType=innerType";
		}
		logger.debug("regUrl="+regUrl);
		
		requestContext.getFlowScope().put("payRegUrl", propertiesSupport.getValue("pay.reg.url"));
		requestContext.getFlowScope().put("regUrl", regUrl);
		requestContext.getFlowScope().put("pwdUrl", propertiesSupport.getValue("pwdChange.url"));
		requestContext.getFlowScope().put("linkUs", propertiesSupport.getValue("linkUs.url"));
		requestContext.getFlowScope().put("protocol", propertiesSupport.getValue("protocol.url"));
	}
	
	/**
	 * @param requestContext
	 * @return 目标服务名称
	 */
	protected String dealBillTEXT(RequestContext requestContext){
		HttpServletRequest request = WebUtils.getHttpServletRequest(requestContext);
		String billText = request.getParameter("billText");
		if(StringUtils.isNotBlank(billText)){
			String targetService = "paymentLogin";//支付登录页面
			String deBillText = null;
			try {
				deBillText = new String(Base64.decodeBase64(billText.getBytes("UTF-8")), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logger.info("deBillText>>>>>" + deBillText);
			String[] contents = deBillText.split("\\|");
			logger.info("merchantName==" + contents[0] + ";payCurr==" + contents[2]);
			requestContext.getFlowScope().put("merchantShortName", contents[0]);
			requestContext.getFlowScope().put("payAmount", contents[1]);
			requestContext.getFlowScope().put("payCurr", contents[2]);
			requestContext.getFlowScope().put("reqTime", contents[3]);
			requestContext.getFlowScope().put("billNo", contents[4]);
			return targetService;
			
		}
		return null;
	}

	public PepsPropertiesSupport getPropertiesSupport() {
		return propertiesSupport;
	}

	public void setPropertiesSupport(PepsPropertiesSupport propertiesSupport) {
		this.propertiesSupport = propertiesSupport;
	}

}
