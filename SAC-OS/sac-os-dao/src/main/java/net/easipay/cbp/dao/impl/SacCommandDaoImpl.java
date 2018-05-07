/**
 * 
 */
package net.easipay.cbp.dao.impl;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.ISacCommandDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.SacB2BCommand;

import org.springframework.stereotype.Repository;
/**
 * @author Administrator
 *
 */

@Repository("sacB2bCommand")
public class SacCommandDaoImpl extends GenericDaoiBatis<SacB2BCommand,Long> implements ISacCommandDao{
	
	public SacCommandDaoImpl(){
		super(SacB2BCommand.class);
	}
	
	public SacCommandDaoImpl(Class<SacB2BCommand> persistentClass) {
		super(persistentClass);
	}

	@SuppressWarnings("deprecation")
	@Override
	public Integer getCommandDetailCounts(Map<String, Object> queryMap) {
		return (Integer)getSqlMapClientTemplate().queryForObject("getSacB2BCommandCount", queryMap);
		
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<SacB2BCommand> getCommandDetailByPaging(Map<String, Object> queryMap) {
		return getSqlMapClientTemplate().queryForList("listSplitSacB2BCommand", queryMap);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void updateCommand(SacB2BCommand command) {
		getSqlMapClientTemplate().update("updateCommandState", command);
		
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void updateOflCommandState(SacB2BCommand command) {
		getSqlMapClientTemplate().update("updateOflCommandState", command);
		
	}

}
