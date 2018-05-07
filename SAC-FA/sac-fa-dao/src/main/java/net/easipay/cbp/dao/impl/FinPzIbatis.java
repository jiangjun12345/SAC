package net.easipay.cbp.dao.impl;

import org.springframework.stereotype.Repository;

import net.easipay.cbp.dao.FinPzDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.FinPz;

@Repository("finPzIbatis")
public class FinPzIbatis extends GenericDaoiBatis<FinPz, Long> implements FinPzDao{

}
