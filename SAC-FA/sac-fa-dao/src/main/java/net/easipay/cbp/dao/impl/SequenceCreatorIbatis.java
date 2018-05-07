/**
 * 
 */
package net.easipay.cbp.dao.impl;

import net.easipay.cbp.dao.SequenceCreatorDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.FinTask;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
/**
 * @author Administrator
 *
 */
/**
 * @author Administrator
 *
 */

@Repository("sequenceCreatorIbatis")
public class SequenceCreatorIbatis  extends GenericDaoiBatis<FinTask, Long> implements SequenceCreatorDao{
	private static final Logger logger = Logger.getLogger(SequenceCreatorIbatis.class);
	
	
	@SuppressWarnings("deprecation")
	public Long getSequenceNoBySeqName(String seq){
		return (Long)getSqlMapClientTemplate().queryForObject("getNextValBySeqName",seq);
	}
	
}
