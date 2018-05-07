package net.easipay.cbp.dao.impl;

import net.easipay.cbp.dao.ISacChargeApplyDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.FinTask;
import net.easipay.cbp.model.SacChargeApply;

import org.springframework.stereotype.Repository;

@Repository("sacChargeApplyDao")
public class SacChargeApplyDao extends GenericDaoiBatis<FinTask, Long> implements ISacChargeApplyDao
{

    @SuppressWarnings("deprecation")
    @Override
    public void insertSacChargeApply(SacChargeApply sacChargeApply)
    {
	getSqlMapClientTemplate().insert("insertSacChargeApply", sacChargeApply);
    }

}
