package net.easipay.cbp.dao.impl;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.ISacB2CExrefundApplyDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.SacB2CExrefundApply;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class SacB2CExRefundApplyDaoImpl extends GenericDaoiBatis<SacB2CExrefundApply, Long> implements ISacB2CExrefundApplyDao {

	public static final Logger logger = Logger.getLogger(SacB2CExRefundApplyDaoImpl.class);

	@Override
	public List<SacB2CExrefundApply> selectSacB2CExrefundApplyList(Map paramMap) {
		return getSqlMapClientTemplate().queryForList("listSacB2cExrefundApply", paramMap);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void insertB2CExrefundApply(SacB2CExrefundApply b2cExrefundApply) {
		getSqlMapClientTemplate().insert("insertSacB2cExrefundApply", b2cExrefundApply);
	}

}
