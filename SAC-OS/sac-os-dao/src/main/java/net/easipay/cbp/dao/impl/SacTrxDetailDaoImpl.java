package net.easipay.cbp.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.util.ClassUtils;

import net.easipay.cbp.dao.ISacTrxDetailDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.dao.base.ibatis.iBatisDaoUtils;
import net.easipay.cbp.model.SacTrxDetail;

@SuppressWarnings({ "deprecation", "unchecked" })
@Repository
public class SacTrxDetailDaoImpl extends GenericDaoiBatis<SacTrxDetail, Long> implements ISacTrxDetailDao
{

	public static final Logger logger = Logger.getLogger(SacTrxDetailDaoImpl.class);

	@Override
	public List<SacTrxDetail> queryTrxDetail(Map<String, Object> paramMap)
	{
		logger.debug("trxDetailList paramMap is " + paramMap);
		return this.getSqlMapClientTemplate().queryForList("listSplitSacTrxDetail", paramMap);
	}

	@Override
	public List<SacTrxDetail> simpleQueryTrxDetail(Map<String, Object> paramMap)
	{
		logger.debug("simpleQueryTrxDetail paramMap is " + paramMap);
		return this.getSqlMapClientTemplate().queryForList("listSacTrxDetail", paramMap);
	}

	@Override
	public int getTrxDetailCount(Map<String, Object> paramMap)
	{
		logger.debug("getTrxDetailCount paramMap is " + paramMap);
		return (Integer) this.getSqlMapClientTemplate().queryForObject("getSacTrxDetailCount", paramMap);
	}

	@Override
	public void updateTrxDetailBySerialNo(SacTrxDetail sacTrxDetail) {
		this.getSqlMapClientTemplate().update("updateSacTrxDetailBySerialNo", sacTrxDetail);
		
	}
	
	@Override
  public void updateOriTrxDetailStatus(SacTrxDetail sacTrxDetail) {
    // TODO Auto-generated method stub
    getSqlMapClientTemplate().update("updateSacTrxDetailStatus", sacTrxDetail);
  }
	
	@Override
  public SacTrxDetail getOriTrxDetail(Map<String, Object> param) {
    return (SacTrxDetail) getSqlMapClientTemplate().queryForObject(
        (iBatisDaoUtils.getSelectQuery(ClassUtils
            .getShortName(SacTrxDetail.class))), param);
  }
}
