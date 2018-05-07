package net.easipay.cbp.dao.impl;

import java.sql.SQLException;
import java.util.List;

import net.easipay.cbp.dao.ISacRecDetailDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.SacRecBatch;
import net.easipay.cbp.model.SacRecDetail;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapExecutor;

@SuppressWarnings("deprecation")
@Repository("sacRecDetailDao")
public class SacRecDetailDao extends GenericDaoiBatis<SacRecBatch, Long> implements ISacRecDetailDao
{
    public void insertRecDetail(final List<SacRecDetail> listSacRecDetail)
    {
	getSqlMapClientTemplate().execute(new SqlMapClientCallback<Object>() {

	    public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException
	    {
		executor.startBatch();
		for (SacRecDetail sacRecDetail : listSacRecDetail) {
		    executor.insert("insertSacRecDetail", sacRecDetail);
		}
		executor.executeBatch();
		return null;
	    }
	});
    }

}
