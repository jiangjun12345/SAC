package net.easipay.cbp.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.IFinTaskDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.FinTask;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapExecutor;

@SuppressWarnings({ "unchecked", "deprecation" })
@Repository("finTaskDao")
public class FinTaskDao extends GenericDaoiBatis<FinTask, Long> implements IFinTaskDao
{

    public void insertFinTasks(final List<FinTask> listFinTask)
    {
	getSqlMapClientTemplate().execute(new SqlMapClientCallback<Object>() {

	    public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException
	    {
		executor.startBatch();
		for (FinTask finTask : listFinTask) {
		    getSqlMapClientTemplate().insert("insertFinTask", finTask);
		}
		executor.executeBatch();
		return null;
	    }
	});

    }

    
    public List<FinTask> getFinTasks(String serno, String status)
    {
	Map<String, Object> param = new HashMap<String, Object>();
	param.put("serno", serno);
	param.put("status", status);
	return getSqlMapClientTemplate().queryForList("getFinTasks", param);
    }
}
