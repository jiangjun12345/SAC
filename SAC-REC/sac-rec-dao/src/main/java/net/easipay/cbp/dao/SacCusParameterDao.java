/**
 * 
 */
package net.easipay.cbp.dao;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.SacCusParameter;
/**
 * @author Administrator
 *
 */
public interface SacCusParameterDao extends GenericDao<SacCusParameter,Long> {
	
	public List<SacCusParameter> getSacCusParameterByParam(Map<String,Object> queryMap);
	
}
