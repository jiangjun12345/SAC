package net.easipay.cbp.service;

import java.util.Map;

import net.easipay.cbp.model.SacOflCommand;
import net.easipay.dsfc.ws.jws.JwsResult;


/**
 * jj
* ClassName: ISacOflCommandService <br/> 
* Function: TODO ADD FUNCTION. <br/> 
* Reason: TODO ADD REASON(可选). <br/> 
* date: 2016年3月11日 上午9:52:29 <br/> 
* 
* @author Administrator 
* @version  
* @since JDK 1.6
 */
public interface ISacOflCommandService
{

	public JwsResult confirmTransferCommand(SacOflCommand oflCmd, String bussType,String draccAreaCode);

	public String NotifyDFFCmdOfl(Map<String,String> handleMap);

	
}
