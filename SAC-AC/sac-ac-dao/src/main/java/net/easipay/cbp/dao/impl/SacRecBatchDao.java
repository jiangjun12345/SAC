package net.easipay.cbp.dao.impl;

import net.easipay.cbp.dao.ISacRecBatchDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.SacRecBatch;

import org.springframework.stereotype.Repository;

@SuppressWarnings("deprecation")
@Repository("sacRecBatchDao")
public class SacRecBatchDao extends GenericDaoiBatis<SacRecBatch, Long> implements ISacRecBatchDao
{
    public void insertSacRecBatch(SacRecBatch sacRecBatch)
    {
	getSqlMapClientTemplate().insert("insertSacRecBatch", sacRecBatch);
    }

}
