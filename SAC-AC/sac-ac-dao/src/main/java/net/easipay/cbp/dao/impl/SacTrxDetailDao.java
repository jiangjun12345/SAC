package net.easipay.cbp.dao.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.ISacTrxDetailDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.SacTrxDetail;
import net.easipay.cbp.util.DateUtil;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapExecutor;

@SuppressWarnings("deprecation")
@Repository("sacTrxDetailDao")
public class SacTrxDetailDao extends GenericDaoiBatis<SacTrxDetail, Long> implements ISacTrxDetailDao
{

    public void insertSacTrxDetailDetail(final List<SacTrxDetail> sacTrxDetais)
    {
	getSqlMapClientTemplate().execute(new SqlMapClientCallback<Object>() {

	    public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException
	    {
		executor.startBatch();
		for (SacTrxDetail sacTrxDetail : sacTrxDetais) {
		    getSqlMapClientTemplate().insert("insertSacTrxDetail", sacTrxDetail);
		}
		executor.executeBatch();
		return null;
	    }
	});
    }

    public void insertSacTrxDetailDetail(SacTrxDetail sacTrxDetai)
    {
	getSqlMapClientTemplate().insert("insertSacTrxDetail", sacTrxDetai);
    }

    public void updateSacTrxDetailState(String trxSerialNo, String trxState, Date trxSuccTime)
    {
	Map<String, Object> param = new HashMap<String, Object>();
	param.put("trxSerialNo", trxSerialNo);
	param.put("trxState", trxState);
	param.put("updateTime", DateUtil.currentDate());
	param.put("updateTime", trxSuccTime);
	getSqlMapClientTemplate().update("updateSacTrxDetailState", param);
    }

    public void deleteSacTrxDetail(String trxSerialNo, String trxState)
    {
	Map<String, Object> param = new HashMap<String, Object>();
	param.put("trxSerialNo", trxSerialNo);
	param.put("trxState", trxState);
	getSqlMapClientTemplate().delete("deleteSacTrxDetail", param);
    }

}
