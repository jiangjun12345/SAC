package net.easipay.cbp.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import net.easipay.cbp.dao.ISacChnSetDetailDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.SacChnSetDetail;

@SuppressWarnings({"unchecked","deprecation"})
@Repository("sacChnSetDetail")
public class SacChnSetDetailDaoImpl extends GenericDaoiBatis<SacChnSetDetail,Long> implements ISacChnSetDetailDao{

	private static final Logger logger = Logger.getLogger(SacChnSetDetailDaoImpl.class);

	@Override
	public List<SacChnSetDetail> queryChnSetDetail(Map<String, Object> paramMap)
	{
		return getSqlMapClientTemplate().queryForList("listSplitSacChnSetDetail",paramMap);
	}

	@Override
	public List<SacChnSetDetail> simpleQueryChnSetDetail(Map<String, Object> paramMap)
	{
		return getSqlMapClientTemplate().queryForList("listSacChnSetDetail",paramMap);
	}

	@Override
	public int queryChnSetDetailCount(Map<String, Object> paramMap)
	{
		return (Integer)getSqlMapClientTemplate().queryForObject("getSacChnSetDetailCount", paramMap);
	}

}
