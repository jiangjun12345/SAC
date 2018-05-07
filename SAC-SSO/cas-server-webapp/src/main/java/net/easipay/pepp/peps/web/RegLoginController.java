/**
 * 
 */
package net.easipay.pepp.peps.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import net.easipay.pepp.peps.authentication.principal.EnhanceUsernamePasswordCredentials;
import net.easipay.pepp.peps.person.Person;
import net.easipay.pepp.peps.person.PersonManager;
import net.easipay.pepp.peps.util.IpUtil;
import net.easipay.pepp.peps.web.sysLog.SysLogManager;
import net.easipay.pepp.peps.web.sysLog.SystemLog;

import org.apache.log4j.Logger;
import org.jasig.cas.CentralAuthenticationService;
import org.jasig.cas.authentication.principal.Credentials;
import org.jasig.cas.authentication.principal.Service;
import org.jasig.cas.authentication.principal.UsernamePasswordCredentials;
import org.jasig.cas.authentication.principal.WebApplicationService;
import org.jasig.cas.ticket.TicketException;
import org.jasig.cas.web.support.ArgumentExtractor;
import org.jasig.cas.web.support.CookieRetrievingCookieGenerator;
import org.jasig.cas.web.support.WebUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

/**
 * @author Administrator
 * 注册后的登录
 */
public class RegLoginController extends SimpleFormController {
	
	public static final Logger logger = Logger.getLogger(RegLoginController.class);
	
	@NotNull
    private CentralAuthenticationService centralAuthenticationService;

	@NotNull
	private CookieRetrievingCookieGenerator ticketGrantingTicketCookieGenerator;
	
	@NotNull
    private SysLogManager sysLogManager;
	
	@NotNull
	private PersonManager personManager;
	 
	/** Extractors for finding the service. */
	@NotNull
	@Size(min=1)
	private List<ArgumentExtractor> argumentExtractors;
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		EnhanceUsernamePasswordCredentials credential = (EnhanceUsernamePasswordCredentials)command;
		Service service = WebUtils.getService(this.argumentExtractors, request);
		try {
			//生成TGT
			String tgtValue = this.centralAuthenticationService.createTicketGrantingTicket(credential);
			afterProcess(request, credential);
	        this.ticketGrantingTicketCookieGenerator.addCookie(request, response, tgtValue);
	        if(null == service){
	        	ModelAndView mv = new ModelAndView("casLoginGenericSuccessView");
	        	return mv;
	        }
	        WebApplicationService wService = (WebApplicationService)service;
	        final String serviceTicketId = this.centralAuthenticationService.grantServiceTicket(tgtValue, service);
	        String redirectUrl = wService.getResponse(serviceTicketId).getUrl();
	        String oper = redirectUrl.contains("?") ? "&" : "?";
			redirectUrl += oper + "ssid=" + request.getSession(false).getId();
			logger.info("cas's redirectUrl ==" + redirectUrl);
			ModelAndView mv = new ModelAndView("redirect:" + redirectUrl);
			return mv;
		} catch (TicketException e) {
			ModelAndView mv = new ModelAndView("viewServiceErrorView");
			return mv;
		}
		
	}
	
	private void afterProcess(HttpServletRequest request,Credentials credentials){
    	SystemLog log = new SystemLog();
    	log.setLogMode("用户登录记录");
    	log.setLogSource("U");
    	log.setLogTime(new Date());
    	log.setLogType("INFO");
    	log.setSessionId(request.getSession().getId());
    	String ip = IpUtil.getClientIp(request);
        String userAgent = request.getHeader("User-Agent");
        String userName = null;
    	log.setUserContext(ip + "," + userAgent);
    	if(credentials != null
                && UsernamePasswordCredentials.class.isAssignableFrom(credentials
                        .getClass())){
    		UsernamePasswordCredentials upCredentials = (UsernamePasswordCredentials)credentials;
    		userName = upCredentials.getUsername();
    		log.setUserName(upCredentials.getUsername());
    	}
    	log.setLogInfo("用户[" + userName + "]登录成功!!!");
    	
    	Person p = new Person();
    	p.setUsername(userName);
    	p.setIp(ip);
    	personManager.updatePerson(p);
    	logger.info("=====用户状态更新成功=====");
    	
    	logger.debug("=====用户日志入库=======");
    	sysLogManager.saveLog(log);
    	logger.debug("=====用户日志入库成功=======");
    }

	public void setCentralAuthenticationService(
			CentralAuthenticationService centralAuthenticationService) {
		this.centralAuthenticationService = centralAuthenticationService;
	}

	public void setTicketGrantingTicketCookieGenerator(
			CookieRetrievingCookieGenerator ticketGrantingTicketCookieGenerator) {
		this.ticketGrantingTicketCookieGenerator = ticketGrantingTicketCookieGenerator;
	}

	public void setArgumentExtractors(List<ArgumentExtractor> argumentExtractors) {
		this.argumentExtractors = argumentExtractors;
	}

	public void setSysLogManager(SysLogManager sysLogManager) {
		this.sysLogManager = sysLogManager;
	}

	public void setPersonManager(PersonManager personManager) {
		this.personManager = personManager;
	}

}
