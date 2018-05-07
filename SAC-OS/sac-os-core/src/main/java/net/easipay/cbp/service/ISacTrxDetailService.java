/**
 * 
 */
package net.easipay.cbp.service;

import java.util.List;

import net.easipay.cbp.model.SacTrxDetail;


/**
 * @author Administrator
 *
 */
public interface ISacTrxDetailService {
	
	public List<SacTrxDetail> selectSacTrxDetailByParameter(SacTrxDetail sacTrxDetail);

	public void updateTrxDetailForDB(SacTrxDetail sacTrxDetail); 
	
}
