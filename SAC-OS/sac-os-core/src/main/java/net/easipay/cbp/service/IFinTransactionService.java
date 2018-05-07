/**
 * 
 */
package net.easipay.cbp.service;



import java.util.List;

import net.easipay.cbp.form.FinTasksForm;
import net.easipay.dsfc.ws.jws.JwsResult;


/**
 * @author Administrator
 *
 */
public interface IFinTransactionService {

	public JwsResult finTasksDeal(List<FinTasksForm> finTasksFormList);
	
}
