/**
 * 
 */
package net.easipay.cbp.dao;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.SacOflCommand;

/**
 * @author Administrator
 *
 */
public interface ISacOflCommandDao extends GenericDao<SacOflCommand,Long> {

	public List<SacOflCommand> getOflCommandByParam(Map<String, Object> queryMap);
	

	
}
