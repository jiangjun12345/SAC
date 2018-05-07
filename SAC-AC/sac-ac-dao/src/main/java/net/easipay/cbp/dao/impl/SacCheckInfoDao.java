package net.easipay.cbp.dao.impl;

import net.easipay.cbp.dao.ISacCheckInfoDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.SacCheckInfo;

import org.springframework.stereotype.Repository;

@Repository("sacCheckInfoDao")
public class SacCheckInfoDao extends GenericDaoiBatis<SacCheckInfo, Long> implements ISacCheckInfoDao
{

	@SuppressWarnings("deprecation")
	@Override
	public void insertSacCheckInfo(SacCheckInfo info) {
		getSqlMapClientTemplate().insert("insertSacCheckInfo", info);
		
	}

   
}
