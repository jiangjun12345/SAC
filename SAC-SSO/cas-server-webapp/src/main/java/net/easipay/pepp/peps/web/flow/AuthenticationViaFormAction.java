/*
 * Copyright 2007 The JA-SIG Collaborative. All rights reserved. See license
 * distributed with this file and available online at
 * http://www.ja-sig.org/products/cas/overview/license/
 */
package net.easipay.pepp.peps.web.flow;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import net.easipay.pepp.peps.person.Person;
import net.easipay.pepp.peps.util.IpUtil;
import net.easipay.pepp.peps.web.sysLog.SystemLog;

import org.jasig.cas.CentralAuthenticationService;
import org.jasig.cas.authentication.handler.AuthenticationException;
import org.jasig.cas.authentication.principal.Credentials;
import org.jasig.cas.authentication.principal.Service;
import org.jasig.cas.authentication.principal.UsernamePasswordCredentials;
import org.jasig.cas.ticket.TicketException;
import org.jasig.cas.web.bind.CredentialsBinder;
import org.jasig.cas.web.support.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.util.StringUtils;
import org.springframework.web.util.CookieGenerator;
import org.springframework.webflow.execution.RequestContext;

/**
 * Action to authenticate credentials and retrieve a TicketGrantingTicket for
 * those credentials. If there is a request for renew, then it also generates
 * the Service Ticket required.
 * 
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 3.0.4
 */
@SuppressWarnings("deprecation")
public class AuthenticationViaFormAction {

    /**
     * Binder that allows additional binding of form object beyond Spring
     * defaults.
     */

    private CredentialsBinder credentialsBinder;

    /** Core we delegate to for handling all ticket related tasks. */
    @NotNull
    private CentralAuthenticationService centralAuthenticationService;

    @NotNull
    private CookieGenerator warnCookieGenerator;
    
//    @NotNull
//    private SysLogManager sysLogManager;
//    
//    @NotNull
//    private PersonManager personManager;

    protected Logger logger = LoggerFactory.getLogger(getClass());

	public final void doBind(final RequestContext context, final Credentials credentials) throws Exception {
        final HttpServletRequest request = WebUtils.getHttpServletRequest(context);

        if (this.credentialsBinder != null && this.credentialsBinder.supports(credentials.getClass())) {
            this.credentialsBinder.bind(request, credentials);
        }
    }
    
    public final String submit(final RequestContext context, final Credentials credentials, final MessageContext messageContext) throws Exception {
        // Validate login ticket
        final String authoritativeLoginTicket = WebUtils.getLoginTicketFromFlowScope(context);
        final String providedLoginTicket = WebUtils.getLoginTicketFromRequest(context);
        if (!authoritativeLoginTicket.equals(providedLoginTicket)) {
            this.logger.warn("Invalid login ticket " + providedLoginTicket);
            final String code = "INVALID_TICKET";
            messageContext.addMessage(
                new MessageBuilder().error().code(code).arg(providedLoginTicket).defaultText(code).build());
            return "error";
        }
    
        final String ticketGrantingTicketId = WebUtils.getTicketGrantingTicketId(context);
        final Service service = WebUtils.getService(context);
        if (StringUtils.hasText(context.getRequestParameters().get("renew")) && ticketGrantingTicketId != null && service != null) {

            try {
                final String serviceTicketId = this.centralAuthenticationService.grantServiceTicket(ticketGrantingTicketId, service, credentials);
                WebUtils.putServiceTicketInRequestScope(context, serviceTicketId);
                putWarnCookieIfRequestParameterPresent(context);
                return "warn";
            } catch (final TicketException e) {
                if (e.getCause() != null && AuthenticationException.class.isAssignableFrom(e.getCause().getClass())) {
                    populateErrorsInstance(e, messageContext);
                    return "error";
                }
                this.centralAuthenticationService.destroyTicketGrantingTicket(ticketGrantingTicketId);
                if (logger.isDebugEnabled()) {
                    logger.debug("Attempted to generate a ServiceTicket using renew=true with different credentials", e);
                }
            }
        }

        try { 
            WebUtils.putTicketGrantingTicketInRequestScope(context, this.centralAuthenticationService.createTicketGrantingTicket(credentials));
            putWarnCookieIfRequestParameterPresent(context);
            //afterProcess(context,credentials);
            return "success";
        } catch (final TicketException e) {
            populateErrorsInstance(e, messageContext);
            return "error";
        }
    }
    
    private void afterProcess(RequestContext context,Credentials credentials){
    	HttpServletRequest request = WebUtils.getHttpServletRequest(context);
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
//    	personManager.updatePerson(p);
//    	logger.info("=====用户状态更新成功=====");
//    	
//    	logger.debug("=====用户日志入库=======");
//    	sysLogManager.saveLog(log);
//    	logger.debug("=====用户日志入库成功=======");
    }


    private void populateErrorsInstance(final TicketException e, final MessageContext messageContext) {

        try {
            messageContext.addMessage(new MessageBuilder().error().code(e.getCode()).defaultText(e.getCode()).build());
        } catch (final Exception fe) {
            logger.error(fe.getMessage(), fe);
        }
    }

    private void putWarnCookieIfRequestParameterPresent(final RequestContext context) {
        final HttpServletResponse response = WebUtils.getHttpServletResponse(context);

        if (StringUtils.hasText(context.getExternalContext().getRequestParameterMap().get("warn"))) {
            this.warnCookieGenerator.addCookie(response, "true");
        } else {
            this.warnCookieGenerator.removeCookie(response);
        }
    }

    public final void setCentralAuthenticationService(final CentralAuthenticationService centralAuthenticationService) {
        this.centralAuthenticationService = centralAuthenticationService;
    }

    /**
     * Set a CredentialsBinder for additional binding of the HttpServletRequest
     * to the Credentials instance, beyond our default binding of the
     * Credentials as a Form Object in Spring WebMVC parlance. By the time we
     * invoke this CredentialsBinder, we have already engaged in default binding
     * such that for each HttpServletRequest parameter, if there was a JavaBean
     * property of the Credentials implementation of the same name, we have set
     * that property to be the value of the corresponding request parameter.
     * This CredentialsBinder plugin point exists to allow consideration of
     * things other than HttpServletRequest parameters in populating the
     * Credentials (or more sophisticated consideration of the
     * HttpServletRequest parameters).
     *
     * @param credentialsBinder the credentials binder to set.
     */
    public final void setCredentialsBinder(final CredentialsBinder credentialsBinder) {
        this.credentialsBinder = credentialsBinder;
    }
    
    public final void setWarnCookieGenerator(final CookieGenerator warnCookieGenerator) {
        this.warnCookieGenerator = warnCookieGenerator;
    }

//	public SysLogManager getSysLogManager() {
//		return sysLogManager;
//	}
//
//	public void setSysLogManager(SysLogManager sysLogManager) {
//		this.sysLogManager = sysLogManager;
//	}
//
//	public void setPersonManager(PersonManager personManager) {
//		this.personManager = personManager;
//	}
	
}
