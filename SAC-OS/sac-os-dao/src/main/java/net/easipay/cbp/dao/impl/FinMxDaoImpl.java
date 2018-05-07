package net.easipay.cbp.dao.impl;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.IFinMxDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.FinMx;

import org.springframework.stereotype.Repository;

@Repository
public class FinMxDaoImpl extends GenericDaoiBatis<FinMx,Long> implements IFinMxDao{

	@Override
	public FinMx getFinMx(Map param) {
		return (FinMx) this.getSqlMapClientTemplate().queryForObject("getFinMx",param);
	}

	@Override
	public List<FinMx> listFinMx(Map param) {
		return this.getSqlMapClientTemplate().queryForList("listFinMx", param);
	}


	@Override
    public int getFinMxCount(Map param) {
        Integer count = (Integer) getSqlMapClientTemplate().queryForObject("getFinMxCount", param);
        return count.intValue();
    }

	@Override
    public List<FinMx> listSplitFinMx(Map param) {
        return this.getSqlMapClientTemplate().queryForList("listSplitFinMx", param);
      
    }

	@Override
	public List<FinMx> listSplitFinMxCollect(Map param) {
		return this.getSqlMapClientTemplate().queryForList("listSplitFinMxCollect", param);
	}

	@Override
	public int getFinMxCollectCount(Map param) {
		return (Integer) getSqlMapClientTemplate().queryForObject("getFinMxCollectCount", param);
	}
}
