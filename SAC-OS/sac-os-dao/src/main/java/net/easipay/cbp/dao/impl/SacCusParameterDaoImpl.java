/**
 * 
 */
package net.easipay.cbp.dao.impl;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.ISacCusParameterDao;
import net.easipay.cbp.model.SacCusParameter;
import net.easipay.cbp.util.BeanUtils;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;

/**
 * @author Administrator
 *
 */

@Repository("sacCusParameter")
public class SacCusParameterDaoImpl extends GenericDaoiBatis<SacCusParameter,Long> implements ISacCusParameterDao{
	private static final Logger logger = Logger.getLogger(SacCusParameterDaoImpl.class);
	public SacCusParameterDaoImpl(){
		super(SacCusParameter.class);
	}
	
	public SacCusParameterDaoImpl(Class<SacCusParameter> persistentClass) {
		super(persistentClass);
	}

	@Override
	public List<SacCusParameter> selectAllSacCusParameter(SacCusParameter sacCusParameter,int pageNo,int pageSize){
		
		Map<String,Object> queryMap = BeanUtils.transBean2Map(sacCusParameter);
		queryMap.put("end", pageNo*pageSize);
		queryMap.put("start", ((pageNo-1)*pageSize));
		return getSqlMapClientTemplate().queryForList("listSplitSacCusParameter",queryMap);
	}
	public Integer selectSacCusParameterTotal(SacCusParameter sacCusParameter){
		return (Integer) getSqlMapClientTemplate().queryForObject("getSacCusParameterCount",sacCusParameter);
	}

	@Override
	public SacCusParameter selectSacCusParameterById(SacCusParameter sacCusParameter) {
		return (SacCusParameter)getSqlMapClientTemplate().queryForObject("getSacCusParameter", sacCusParameter);
	}

	@Override
	public List<SacCusParameter> queryAllSacCusParameter(Map<String,Object> paramMap)
	{
		return getSqlMapClientTemplate().queryForList("listSacCusParameter",paramMap);
	}

	@Override
	public List<String> selectlistSacCusParameterByCusName(Map<String,Object> paramMap)
	{
		return getSqlMapClientTemplate().queryForList("listSacCusParameterByCusName",paramMap);
	}
}
