package net.easipay.cbp.dao;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.FinPzNoSequence;

public interface FinPzNoSequenceDao extends GenericDao<FinPzNoSequence, Integer>{

	public FinPzNoSequence getFinPzNoSequence(Integer key) throws Exception;
	
}
