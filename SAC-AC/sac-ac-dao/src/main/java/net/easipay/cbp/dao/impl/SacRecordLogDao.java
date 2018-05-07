package net.easipay.cbp.dao.impl;

import java.sql.SQLException;
import java.util.List;

import net.easipay.cbp.dao.ISacRecordLogDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.SacRecordLog;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapExecutor;

@SuppressWarnings("deprecation")
@Repository("sacRecordLogDao")
public class SacRecordLogDao extends GenericDaoiBatis<SacRecordLog, Long> implements ISacRecordLogDao
{

    public void insertSacRecordLog(final List<SacRecordLog> sacRecordLogs)
    {
	getSqlMapClientTemplate().execute(new SqlMapClientCallback<Object>() {

	    public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException
	    {
		executor.startBatch();
		for (SacRecordLog sacRecordLog : sacRecordLogs) {
		    getSqlMapClientTemplate().insert("insertSacRecordLog", sacRecordLog);
		}
		executor.executeBatch();
		return null;
	    }
	});
    }
}
