/**
 * 
 */
package net.easipay.cbp.dao.impl;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.SacCusParameterDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.SacCusParameter;
import net.easipay.cbp.util.BeanUtils;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 *
 */

@Repository("sacCusParameter")
public class SacCusParameterDaoImpl extends GenericDaoiBatis<SacCusParameter,Long> implements SacCusParameterDao{
	public SacCusParameterDaoImpl(){
		super(SacCusParameter.class);
	}
	
	public SacCusParameterDaoImpl(Class<SacCusParameter> persistentClass) {
		super(persistentClass);
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public List<SacCusParameter> getSacCusParameterByParam(Map<String,Object> queryMap){
		
		return getSqlMapClientTemplate().queryForList("getSacCusParameterByParam",queryMap);
	}

}
