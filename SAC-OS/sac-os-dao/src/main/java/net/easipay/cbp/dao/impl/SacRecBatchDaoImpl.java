package net.easipay.cbp.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import net.easipay.cbp.dao.ISacRecBatchDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.SacRecBatch;

@SuppressWarnings({ "deprecation", "unchecked" })
@Repository
public class SacRecBatchDaoImpl extends GenericDaoiBatis<SacRecBatch, Long> implements ISacRecBatchDao
{

	public static final Logger logger = Logger.getLogger(SacRecBatchDaoImpl.class);

	@Override
	public List<SacRecBatch> queryCheckAccBatch(Map<String, Object> paramMap)
	{
		return this.getSqlMapClientTemplate().queryForList("listSacRecBatch", paramMap);
	}

	@Override
	public List<Map<String, Object>> checkAccResultList(Map<String, Object> paramMap)
	{
		return this.getSqlMapClientTemplate().queryForList("checkAccResultList", paramMap);
	}
	
	@Override
	public int checkAccResultListCount(Map<String, Object> paramMap)
	{
		return (Integer) this.getSqlMapClientTemplate().queryForObject("sacRecBatchCount", paramMap);
	}

}
