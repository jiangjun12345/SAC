package net.easipay.cbp.dao.impl;

import net.easipay.cbp.dao.FinPzNoSequenceDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.FinPzNoSequence;

import org.springframework.stereotype.Repository;

@Repository("finPzNoSequenceIbatis")
public class FinPzNoSequenceIbatis extends GenericDaoiBatis<FinPzNoSequence, Integer> implements FinPzNoSequenceDao{

	@Override
	public FinPzNoSequence getFinPzNoSequence(Integer key) throws Exception {
		FinPzNoSequence sequence = null;
		if (this.exists(key)){
			sequence = this.get(key);
		}else{
			sequence = new FinPzNoSequence();
			sequence.setKey(key);
			sequence.setValue(0);
			this.save(sequence);
		}
		return sequence;
	}
	
}
