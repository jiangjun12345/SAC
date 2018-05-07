/**
 * 
 */
package net.easipay.cbp.dao;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.SacB2BCommand;
import net.easipay.cbp.model.SacDffOflCommand;

/**
 * @author Administrator
 *
 */
public interface ISacDffOflCommandDao extends GenericDao<SacDffOflCommand,Long> {

	public Integer getCommandDetailCounts(Map<String, Object> queryMap);

	public List<SacDffOflCommand> getCommandDetailByPaging(Map<String, Object> queryMap);

	public void updateCommand(SacB2BCommand command);
	
	public void updateOflCommandState(SacDffOflCommand command);
	
}
