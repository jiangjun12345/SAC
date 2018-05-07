/**
 * 
 */
package net.easipay.cbp.dao;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.SacB2BCommand;

/**
 * @author Administrator
 *
 */
public interface ISacCommandDao extends GenericDao<SacB2BCommand,Long> {

	public Integer getCommandDetailCounts(Map<String, Object> queryMap);

	public List<SacB2BCommand> getCommandDetailByPaging(Map<String, Object> queryMap);

	public void updateCommand(SacB2BCommand command);
	
	public void updateOflCommandState(SacB2BCommand command);
	
}
