/**
 * 
 */
package net.easipay.cbp.dao.impl;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.ISacDffOflCommandDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.SacB2BCommand;
import net.easipay.cbp.model.SacDffOflCommand;

import org.springframework.stereotype.Repository;
/**
 * @author Administrator
 *
 */

@Repository("sacDffOflCommand")
public class SacDffOflCommandDaoImpl extends GenericDaoiBatis<SacDffOflCommand,Long> implements ISacDffOflCommandDao{
	
	public SacDffOflCommandDaoImpl(){
		super(SacDffOflCommand.class);
	}
	
	public SacDffOflCommandDaoImpl(Class<SacDffOflCommand> persistentClass) {
		super(persistentClass);
	}

	@SuppressWarnings("deprecation")
	@Override
	public Integer getCommandDetailCounts(Map<String, Object> queryMap) {
		return (Integer)getSqlMapClientTemplate().queryForObject("getSacDffOflCommandCount", queryMap);
		
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<SacDffOflCommand> getCommandDetailByPaging(Map<String, Object> queryMap) {
		return getSqlMapClientTemplate().queryForList("listSplitSacDffOflCommand", queryMap);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void updateCommand(SacB2BCommand command) {
		getSqlMapClientTemplate().update("updateCommandState", command);
		
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void updateOflCommandState(SacDffOflCommand command) {
		getSqlMapClientTemplate().update("updateSacDffOflCommand", command);
		
	}

}
