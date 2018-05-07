package net.easipay.cbp.dao.impl;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.ISacDepositDetailDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.SacDepositDetail;

import org.springframework.stereotype.Repository;

@Repository
public class SacDepositDetailDaoImpl extends GenericDaoiBatis<SacDepositDetail,Long> implements ISacDepositDetailDao{

	@Override
	public List<SacDepositDetail> selectSacDepositDetailList(Map<String, Object> paramMap) {
		return getSqlMapClientTemplate().queryForList("listSacDepositDetail", paramMap);
	}

	@Override
	public void updateSacDepositDetail(SacDepositDetail detail) {
		getSqlMapClientTemplate().update("updateSacDepositDetail", detail);
	}
}
