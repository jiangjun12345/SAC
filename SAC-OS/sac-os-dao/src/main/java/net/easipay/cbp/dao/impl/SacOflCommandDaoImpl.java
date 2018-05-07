/**
 * 
 */
package net.easipay.cbp.dao.impl;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.ISacOflCommandDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.SacOflCommand;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
/**
 * @author Administrator
 *
 */

@Repository("sacOflCommandDao")
public class SacOflCommandDaoImpl extends GenericDaoiBatis<SacOflCommand,Long> implements ISacOflCommandDao{
	private static final Logger logger = Logger.getLogger(SacOflCommandDaoImpl.class);
	
	public SacOflCommandDaoImpl(){
		super(SacOflCommand.class);
	}
	
	public SacOflCommandDaoImpl(Class<SacOflCommand> persistentClass) {
		super(persistentClass);
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public List<SacOflCommand> getOflCommandByParam(Map<String, Object> queryMap) {
		return getSqlMapClientTemplate().queryForList("getSacOflCommandByParam", queryMap);
		
	}


	
}
