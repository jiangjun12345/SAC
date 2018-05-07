/**
 * 
 */
package net.easipay.cbp.dao.impl;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.ISacChargeApplyDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.SacChargeApply;
import net.easipay.cbp.util.BeanUtils;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
/**
 * @author Administrator
 *
 */

@Repository("sacChargeApply")
public class SacChargeApplyDaoImpl extends GenericDaoiBatis<SacChargeApply,Long> implements ISacChargeApplyDao{
	private static final Logger logger = Logger.getLogger(SacChargeApplyDaoImpl.class);
	
	public SacChargeApplyDaoImpl(){
		super(SacChargeApply.class);
	}
	
	public SacChargeApplyDaoImpl(Class<SacChargeApply> persistentClass) {
		super(persistentClass);
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public List<SacChargeApply> selectApplyByParam(Map<String,Object> queryMap) {
		return getSqlMapClientTemplate().queryForList("listSacChargeApply", queryMap);
	}

	@SuppressWarnings("deprecation")
	@Override
	public Integer selectApplyCountsByParam(Map<String, Object> queryMap) {
		return (Integer)getSqlMapClientTemplate().queryForObject("getSacChargeApplyCount", queryMap);
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<SacChargeApply> selectApplyByPaging(Map<String, Object> queryMap) {
		return getSqlMapClientTemplate().queryForList("listSplitSacChargeApply", queryMap);
	}

	
}
