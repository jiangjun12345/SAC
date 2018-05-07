/**
 * 
 */
package net.easipay.cbp.dao.impl;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.ISacCmdBatchDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.SacB2bCmdBatch;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
/**
 * @author Administrator
 *
 */

@Repository("sacB2bCmdBatch")
public class SacCmdBatchDaoImpl extends GenericDaoiBatis<SacB2bCmdBatch,Long> implements ISacCmdBatchDao{
	private static final Logger logger = Logger.getLogger(SacCmdBatchDaoImpl.class);
	
	public SacCmdBatchDaoImpl(){
		super(SacB2bCmdBatch.class);
	}
	
	public SacCmdBatchDaoImpl(Class<SacB2bCmdBatch> persistentClass) {
		super(persistentClass);
	}

	@SuppressWarnings("deprecation")
	@Override
	public Integer getCmdBatchCounts(Map<String, Object> queryMap) {
		return (Integer)getSqlMapClientTemplate().queryForObject("getSacB2bCmdBatchCount", queryMap);
		
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<SacB2bCmdBatch> getCmdBatchByPaging(Map<String, Object> queryMap) {
		return getSqlMapClientTemplate().queryForList("listSplitSacB2bCmdBatch", queryMap);
	}

	@Override
	public List<SacB2bCmdBatch> getSacB2bCmdBatch(Map<String, Object> queryMap) {
		return getSqlMapClientTemplate().queryForList("getSacB2bCmdBatch", queryMap);
	}

}
