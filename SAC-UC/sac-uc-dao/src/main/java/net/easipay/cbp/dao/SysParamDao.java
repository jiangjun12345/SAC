/**
 * 
 */
package net.easipay.cbp.dao;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.SysParam;


/**
 * @author Administrator
 *
 */
public interface SysParamDao extends GenericDao<SysParam,Long> {

	public String getSysParamValue(String paramKey, String magicType);

}
