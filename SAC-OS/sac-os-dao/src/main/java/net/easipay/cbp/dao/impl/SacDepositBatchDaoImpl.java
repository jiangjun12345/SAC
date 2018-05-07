/**
 * 
 */
package net.easipay.cbp.dao.impl;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.ISacDepositBatchDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.SacDepositBatch;
import net.easipay.cbp.util.BeanUtils;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
/**
 * @author Administrator
 *
 */

@Repository("sacDepositBatch")
public class SacDepositBatchDaoImpl extends GenericDaoiBatis<SacDepositBatch,Long> implements ISacDepositBatchDao{
	private static final Logger logger = Logger.getLogger(SacDepositBatchDaoImpl.class);
	
	public SacDepositBatchDaoImpl(){
		super(SacDepositBatch.class);
	}
	
	public SacDepositBatchDaoImpl(Class<SacDepositBatch> persistentClass) {
		super(persistentClass);
	}

	@SuppressWarnings("deprecation")
	@Override
	public int getBatchCountsByParam(Map<String,Object> queryMap) {
		return (Integer)getSqlMapClientTemplate().queryForObject("getSacDepositBatchCount", queryMap);
		
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public List<SacDepositBatch> getDepositBatchByParam(
			Map<String,Object> queryMap, int pageNo, int pageSize) {
		int start = (pageNo-1)*pageSize;
		int end = pageNo*pageSize;
		queryMap.put("start", start);
		queryMap.put("end", end);
		return getSqlMapClientTemplate().queryForList("listSplitSacDepositBatch", queryMap);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void deleteBatchByBatchId(Map<String, Object> deleteMap) {
		getSqlMapClientTemplate().delete("deleteSacDepositBatch", deleteMap);
		
	}

	
}
