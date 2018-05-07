/**
 * 
 */
package net.easipay.cbp.dao.impl;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.ISacRecordLogDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.SacRecordLog;
import net.easipay.cbp.util.BeanUtils;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
/**
 * @author Administrator
 *
 */

@Repository("sacRecordLog")
public class SacRecordLogDaoImpl extends GenericDaoiBatis<SacRecordLog,Long> implements ISacRecordLogDao{
	private static final Logger logger = Logger.getLogger(SacRecordLogDaoImpl.class);
	
	public SacRecordLogDaoImpl(){
		super(SacRecordLog.class);
	}
	
	public SacRecordLogDaoImpl(Class<SacRecordLog> persistentClass) {
		super(persistentClass);
	}

	@SuppressWarnings("deprecation")
	@Override
	public int selectSacRecordLogCounts(SacRecordLog sacRecordLog) {
		return (Integer)getSqlMapClientTemplate().queryForObject("getSacRecordLogCount", sacRecordLog);
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public List<SacRecordLog> selectSacRecordLogByParam(
			SacRecordLog sacRecordLog, int pageNo, int pageSize) {
		int start = (pageNo-1)*pageSize;
		int end = pageNo*pageSize;
		Map<String,Object> queryMap =null;
		queryMap = BeanUtils.transBean2Map(sacRecordLog);
		queryMap.put("start", start);
		queryMap.put("end", end);
		return getSqlMapClientTemplate().queryForList("listSplitSacRecordLog", queryMap);
	}

	@SuppressWarnings("deprecation")
	@Override
	public SacRecordLog selectSacRecordLogById(SacRecordLog sacRecordLog) {
		return (SacRecordLog)getSqlMapClientTemplate().queryForObject("getSacRecordLogById", sacRecordLog);
	}

	
}
