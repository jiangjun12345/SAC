/**
 * 
 */
package net.easipay.cbp.dao.impl;


import org.springframework.stereotype.Repository;

import net.easipay.cbp.dao.SysParamDao;
import net.easipay.cbp.dao.base.GenericDaoiBatis;
import net.easipay.cbp.model.SysParam;

/**
 * @author Administrator
 *
 */

@Repository("sysParamDao")
@SuppressWarnings("deprecation")
public class SysParamDaoImpl extends GenericDaoiBatis<SysParam,Long> implements SysParamDao
{
	public SysParamDaoImpl()
	{
		super(SysParam.class);
	}
	
	public SysParamDaoImpl(Class<SysParam> persistentClass) 
	{
		super(persistentClass);
	}

	public String getSysParamValue(String paramKey, String magicType)
	{
		SysParam sysParam = new SysParam();
		sysParam.setParamKey(paramKey);
		sysParam.setMagicType(magicType);
		return (String) getSqlMapClientTemplate().queryForObject("queryValueByKey", sysParam);
	}
	

	
}
