package net.easipay.cbp.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import net.easipay.cbp.dao.IExceptMonitorDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.SacExceptMonitor;
@Repository
public class ExceptMonitorDaoImpl extends GenericDaoiBatis<SacExceptMonitor, Long> implements IExceptMonitorDao {

	@Override
	public List<SacExceptMonitor> getSacRecordLogInfo() {
		// TODO Auto-generated method stub
		return this.getSqlMapClientTemplate().queryForList("getSacRecordLogInfo");
	}

	@Override
	public List<SacExceptMonitor> getDsfsAlarmInfo() {
		// TODO Auto-generated method stub
		return this.getSqlMapClientTemplate().queryForList("getDsfsAlarmInfo");

	}

	@Override
	public List<SacExceptMonitor> getSacRecDifferenceInfo() {
		// TODO Auto-generated method stub
		return this.getSqlMapClientTemplate().queryForList("getSacRecDifferenceInfo");
	}

	@Override
	public List<SacExceptMonitor> getSacRecJobLogInfo() {
		// TODO Auto-generated method stub
		return this.getSqlMapClientTemplate().queryForList("getSacRecJobLogInfo");
	}

	@Override
	public List<SacExceptMonitor> getFinTaskInfo() {
		return this.getSqlMapClientTemplate().queryForList("getFinTaskInfo");
	}

	@Override
	public void insertSacexceptMonitor(List<SacExceptMonitor> param) {
		for(SacExceptMonitor sem : param){
			this.getSqlMapClientTemplate().insert("insertSacExceptMonitor", sem);
		}
	}

	@Override
	public List<SacExceptMonitor> getSacExcetMonitorList(
			Map<String, Object> queryMap) {
		return this.getSqlMapClientTemplate().queryForList("getSacExcetMonitorList",queryMap);
	}
	

}
