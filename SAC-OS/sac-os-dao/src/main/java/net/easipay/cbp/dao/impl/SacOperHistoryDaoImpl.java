/**
 * 
 */
package net.easipay.cbp.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import net.easipay.cbp.dao.ISacOperHistoryDao;
import net.easipay.cbp.dao.ISacRefundDao;
import net.easipay.cbp.model.SacOperHistory;
import net.easipay.cbp.model.SacRefund;
import net.easipay.cbp.util.BeanUtils;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
/**
 * @author Administrator
 *
 */

@Repository("sacOperHistory")
public class SacOperHistoryDaoImpl extends GenericDaoiBatis<SacOperHistory,Long> implements ISacOperHistoryDao{
	private static final Logger logger = Logger.getLogger(SacOperHistoryDaoImpl.class);
	
	public SacOperHistoryDaoImpl(){
		super(SacOperHistory.class);
	}
	
	public SacOperHistoryDaoImpl(Class<SacOperHistory> persistentClass) {
		super(persistentClass);
	}

	@Override
	public void insertSacOperHistory(SacOperHistory sacOperHistory) {
		getSqlMapClientTemplate().insert("insertSacOperHistory", sacOperHistory);
		
	}

	@Override
	public int selectSacOperHistoryCounts(SacOperHistory sacOperHistory) {
		return (Integer)getSqlMapClientTemplate().queryForObject("getSacOperHistoryCount", sacOperHistory);
	}

	@Override
	public List<SacOperHistory> selectSacOperHistoryByParam(
			SacOperHistory sacOperHistory,int pageNo,int pageSize) {
		int start = (pageNo-1)*pageSize;
		int end = pageNo*pageSize;
		Map<String,Object> queryMap =null;
		queryMap = BeanUtils.transBean2Map(sacOperHistory);
		queryMap.put("start", start);
		queryMap.put("end", end);
		return getSqlMapClientTemplate().queryForList("listSplitSacOperHistory", queryMap);
	}


	
}
