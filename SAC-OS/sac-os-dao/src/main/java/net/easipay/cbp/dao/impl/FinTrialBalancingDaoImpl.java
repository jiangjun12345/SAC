/**
 * 
 */
package net.easipay.cbp.dao.impl;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.IFinTrialBalancingDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.FinTrialBalancing;

import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 *
 */
@Repository("finTrialBalancing")
public class FinTrialBalancingDaoImpl extends GenericDaoiBatis<FinTrialBalancing,Long> implements IFinTrialBalancingDao{

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public List<FinTrialBalancing> getFinTrialBalancingBySplit(
			Map<String, Object> queryMap, int pageNo, int pageSize) {
		queryMap.put("end", pageNo*pageSize);
		queryMap.put("start", ((pageNo-1)*pageSize));
		Boolean flag = (Boolean)queryMap.get("flag");
		if(flag){
			//实时试算查询 查视图
			return getSqlMapClientTemplate().queryForList("gettrialBalancingBySplitV",queryMap);
		}else{
			//查询表
			return getSqlMapClientTemplate().queryForList("gettrialBalancingBySplitT",queryMap);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public int getFinTrialBalancingCounts(Map<String, Object> queryMap) {
		Boolean flag = (Boolean)queryMap.get("flag");
		if(flag){
			//实时试算查询 查视图
			return (Integer)getSqlMapClientTemplate().queryForObject("gettrialBalancingCountsV",queryMap);
		}else{
			//查询表
			return (Integer)getSqlMapClientTemplate().queryForObject("gettrialBalancingCountsT",queryMap);
		}
	}
	
}
