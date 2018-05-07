/**
 * 
 */
package net.easipay.cbp.dao.impl;

import net.easipay.cbp.dao.ISequenceCreatorDao;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;
/**
 * @author Administrator
 *
 */
/**
 * @author Administrator
 *
 */

@SuppressWarnings("deprecation")
@Repository
public class SequenceCreatorDaoImpl  implements ISequenceCreatorDao{
	private static final Logger logger = Logger.getLogger(SequenceCreatorDaoImpl.class);
	
	@Autowired
	private SqlMapClientTemplate sqlMapClientTemplate;
	
	public String getSequenceNoBySeqName(String seq){
		return (Integer)sqlMapClientTemplate.queryForObject("getNextValBySeqName",seq)+"";
	}
	
}
