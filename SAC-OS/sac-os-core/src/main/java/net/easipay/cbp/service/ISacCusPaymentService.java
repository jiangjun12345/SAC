/**
 * 
 */
package net.easipay.cbp.service;

import java.util.List;

import net.easipay.cbp.model.SacCusPayment;


/**
 * @author Administrator
 *
 */
public interface ISacCusPaymentService {
	
	public List<SacCusPayment> selectSacCusPaymentByParameter(SacCusPayment sacCusPayment); 
	
}
