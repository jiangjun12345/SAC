package net.easipay.cbp.dao.impl;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.ISacRecDetailDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.SacRecDetail;

import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 *
 */
@SuppressWarnings({ "deprecation", "unchecked" })
@Repository
public class SacRecDetailDaoImpl extends GenericDaoiBatis<SacRecDetail,Long> implements ISacRecDetailDao{
	
	@Override
	public Integer queryRecDetailCount(Map<String, Object> paramMap)
	{
		return (Integer)getSqlMapClientTemplate().queryForObject("getSacRecDetailCount", paramMap);
	}

	@Override
	public List<SacRecDetail> queryRecDetail(Map<String, Object> paramMap)
	{
		return getSqlMapClientTemplate().queryForList("listSplitSacRecDetail", paramMap);
	}

	@Override
	public List<SacRecDetail> simpleQueryRecDetail(Map<String, Object> paramMap)
	{
		return getSqlMapClientTemplate().queryForList("listSacRecDetail", paramMap);
	}
}
