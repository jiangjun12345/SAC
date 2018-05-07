package net.easipay.cbp.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import net.easipay.cbp.dao.ISacRecFileParamDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.SacRecFileParam;

@Repository
public class SacRecFileParamDaoImpl extends GenericDaoiBatis<SacRecFileParam, Long> implements ISacRecFileParamDao{


	public List<SacRecFileParam> listAllSacRecFileParam(Map param) {
		return this.getSqlMapClientTemplate().queryForList("listSacRecFileParam", param);
	}

}
