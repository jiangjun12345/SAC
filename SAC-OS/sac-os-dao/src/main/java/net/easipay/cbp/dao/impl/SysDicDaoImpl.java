/**
 * 
 */
package net.easipay.cbp.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import net.easipay.cbp.dao.ISysDicDao;
import net.easipay.cbp.model.SacSysDic;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
/**
 * @author Administrator
 *
 */

@Repository("sysDic")
public class SysDicDaoImpl extends GenericDaoiBatis<SacSysDic,Long> implements ISysDicDao{
	private static final Logger logger = Logger.getLogger(SysDicDaoImpl.class);
	
	public SysDicDaoImpl(){
		super(SacSysDic.class);
	}
	
	public SysDicDaoImpl(Class<SacSysDic> persistentClass) {
		super(persistentClass);
	}


	@Override
	public List<SacSysDic> selectSysDic(SacSysDic sysDic) {
		return getSqlMapClientTemplate().queryForList("selectSysDic", sysDic);
	}

	@Override
	public List<SacSysDic> selectChildAccType()
	{
		return getSqlMapClientTemplate().queryForList("selectChildAccType");
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<SacSysDic> selectCode1()
	{
		return getSqlMapClientTemplate().queryForList("selectCode1");
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<SacSysDic> selectCode2()
	{
		return getSqlMapClientTemplate().queryForList("selectCode2");
	}

	
}
