/**
 * 
 */
package net.easipay.cbp.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import net.easipay.cbp.dao.SysDicDao;
import net.easipay.cbp.dao.base.GenericDaoiBatis;
import net.easipay.cbp.model.SysDic;

/**
 * @author Administrator
 *
 */

@Repository("sysDic")
public class SysDicDaoImpl extends GenericDaoiBatis<SysDic,Long> implements SysDicDao{
	private static final Logger logger = Logger.getLogger(SysDicDaoImpl.class);
	
	public SysDicDaoImpl(){
		super(SysDic.class);
	}
	
	public SysDicDaoImpl(Class<SysDic> persistentClass) {
		super(persistentClass);
	}


	@Override
	public List<SysDic> selectSysDic(SysDic sysDic) {
		return getSqlMapClientTemplate().queryForList("selectSysDic", sysDic);
	}
	@Override
	public void insertSysDic(SysDic sysDic){
	  getSqlMapClientTemplate().insert("insertSysDic", sysDic);
	}
}
