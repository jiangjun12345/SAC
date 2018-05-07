package net.easipay.cbp.dao.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.ISacOtrxInfoDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.SacOtrxInfo;
import net.easipay.cbp.sequence.SequenceCreatorUtil;
import net.easipay.cbp.util.DateUtil;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapExecutor;

@SuppressWarnings("deprecation")
@Repository("sacOtrxInfoDao")
public class SacOtrxInfoDao extends GenericDaoiBatis<SacOtrxInfo, Long> implements ISacOtrxInfoDao
{

    public void insertSacOtrxInfo(final List<SacOtrxInfo> sacOtrxInfos)
    {
	getSqlMapClientTemplate().execute(new SqlMapClientCallback<Object>() {

	    public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException
	    {
		executor.startBatch();
		for (SacOtrxInfo sacOtrxInfo : sacOtrxInfos) {
		    getSqlMapClientTemplate().insert("insertSacOtrxInfo", sacOtrxInfo);
		}
		executor.executeBatch();
		return null;
	    }
	});
    }

    public void insertSacOtrxInfo(SacOtrxInfo sacOtrxInfo)
    {
	getSqlMapClientTemplate().insert("insertSacOtrxInfo", sacOtrxInfo);
    }

    public void updateSacOtrxInfoState(String trxSerialNo, String etrxSerialNo, String trxState, String trxStateDesc, Date trxSuccTime)
    {
	Map<String, Object> param = new HashMap<String, Object>();
	param.put("trxSerialNo", trxSerialNo);
	param.put("etrxSerialNo", etrxSerialNo);
	param.put("trxState", trxState);
	param.put("trxStateDesc", trxStateDesc);
	param.put("updateTime", DateUtil.currentDate());
	param.put("trxSuccTime", trxSuccTime);
	getSqlMapClientTemplate().update("updateSacOtrxInfoState", param);
    }

    public SacOtrxInfo getSacOtrxInfo(String trxSerialNo)
    {
	return (SacOtrxInfo) getSqlMapClientTemplate().queryForObject("getSacOtrxInfo", trxSerialNo);
    }

    public void deleteSacOtrxInfo(String trxSerialNo, String trxState)
    {
	Map<String, Object> param = new HashMap<String, Object>();
	param.put("trxSerialNo", trxSerialNo);
	param.put("trxState", trxState);
	getSqlMapClientTemplate().delete("deleteSacOtrxInfo", param);
    }

    public void updateSacOtrxRemittanceBatchId(final String[] trxSerialNos, final String[] remittanceBatchIds)
    {
    	
	getSqlMapClientTemplate().execute(new SqlMapClientCallback<Object>() {

	    public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException
	    {
		executor.startBatch();
		for (int i = 0; i < trxSerialNos.length; i++) {
		    Map<String, Object> param = new HashMap<String, Object>();
		    param.put("trxSerialNo", trxSerialNos[i]);
		    param.put("remittanceBatchId", remittanceBatchIds[i]);
		    getSqlMapClientTemplate().update("updateSacOtrxRemittanceBatchId", param);
		}
		executor.executeBatch();
		return null;
	    }
	});
    }
    
    @Override
    public void insertSacTrxRemittance(final String[] trxSerialNos, final String[] remittanceBatchIds){
    	
    	final Long tableId = SequenceCreatorUtil.getTableId();
		
		getSqlMapClientTemplate().execute(new SqlMapClientCallback<Object>() {

		    public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException
		    {
			executor.startBatch();
			for (int i = 0; i < trxSerialNos.length; i++) {
				Long id = SequenceCreatorUtil.getTableId();
			    Map<String, Object> param = new HashMap<String, Object>();
			    param.put("id", id);
			    param.put("trxSerialNo", trxSerialNos[i]);
			    param.put("remittanceBatchId", remittanceBatchIds[i]);
			    param.put("dealFlag", "N");//默认未处理
			    param.put("batchId", tableId);
			    param.put("createTime", new Date());
			    param.put("updateTime", new Date());
			    getSqlMapClientTemplate().insert("insertSacTrxRemittance", param);
			}
			executor.executeBatch();
			return null;
		    }
		});
    }

}
