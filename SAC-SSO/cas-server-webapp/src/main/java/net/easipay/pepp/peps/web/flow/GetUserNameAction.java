/**
 * 
 */
package net.easipay.pepp.peps.web.flow;

import java.util.Map;

import javax.validation.constraints.NotNull;

import org.apache.log4j.Logger;
import org.jasig.cas.ticket.InvalidTicketException;
import org.jasig.cas.ticket.TicketGrantingTicket;
import org.jasig.cas.ticket.registry.TicketRegistry;
import org.jasig.cas.web.support.WebUtils;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

/**
 * @author Administrator
 * 查询用户名称
 */
public class GetUserNameAction extends AbstractAction {
	
	public static final Logger logger = Logger.getLogger(GetUserNameAction.class);
	
	@NotNull
    private TicketRegistry ticketRegistry;

	/* (non-Javadoc)
	 * @see org.springframework.webflow.action.AbstractAction#doExecute(org.springframework.webflow.execution.RequestContext)
	 */
	@Override
	protected Event doExecute(RequestContext context) throws Exception {
		String activeFlowId = context.getActiveFlow().getId();
		if("loginB".equals(activeFlowId)){
			String tgtString = WebUtils.getTicketGrantingTicketId(context);
			TicketGrantingTicket ticketGrantingTicket = (TicketGrantingTicket) this.ticketRegistry.getTicket(tgtString, TicketGrantingTicket.class);
			if (ticketGrantingTicket == null) {
	            return result("relogin");
	        }
			String id = ticketGrantingTicket.getAuthentication().getPrincipal().getId();
			Map<String,Object> attributes = ticketGrantingTicket.getAuthentication().getPrincipal().getAttributes();
			context.getFlowScope().put("loginName", id);
			return result("success");
		}
		return result("loginB");
	}

	public TicketRegistry getTicketRegistry() {
		return ticketRegistry;
	}

	public void setTicketRegistry(TicketRegistry ticketRegistry) {
		this.ticketRegistry = ticketRegistry;
	}

}
