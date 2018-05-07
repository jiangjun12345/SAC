package net.easipay.cbp.dao.impl;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.IBussTypeGroupDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.BussTypeGroup;

import org.springframework.stereotype.Repository;

@Repository
public class BussTypeGroupDaoImpl extends GenericDaoiBatis<BussTypeGroup,Long> implements IBussTypeGroupDao{

	@Override
	public List<BussTypeGroup> getBussTypeGroup(Map<String, Object> paramMap) {
		return this.getSqlMapClientTemplate().queryForList("getBussTypeGroupList", paramMap);
	}

	@Override
	public int getBussTypeGroupCount(Map<String, Object> paramMap) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject("getBussTypeGroupCount", paramMap);
        return count.intValue();
	}
}
