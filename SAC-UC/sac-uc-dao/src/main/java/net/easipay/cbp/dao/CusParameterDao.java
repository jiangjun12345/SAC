/**
 * 
 */
package net.easipay.cbp.dao;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.UcCusParameter;
/**
 * @author Administrator
 *
 */
public interface CusParameterDao extends GenericDao<UcCusParameter,Long> {
	
	public void insertUcCusParameter(UcCusParameter ucCusParameter);
	
	public void updateUcCusParameter(UcCusParameter ucCusParameter);
	
}
