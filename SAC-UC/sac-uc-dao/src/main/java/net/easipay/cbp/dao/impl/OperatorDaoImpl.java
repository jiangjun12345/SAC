/**
 * 
 */
package net.easipay.cbp.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.OperatorDao;
import net.easipay.cbp.dao.base.GenericDaoiBatis;
import net.easipay.cbp.model.UcOperator;
import net.easipay.cbp.sequence.SequenceCreatorUtil;
import net.easipay.cbp.util.ConstantParams;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 *
 */

@Repository("ucOperator")
public class OperatorDaoImpl extends GenericDaoiBatis<UcOperator,Long> implements OperatorDao{
	private static final Logger logger = Logger.getLogger(OperatorDaoImpl.class);
	
	public OperatorDaoImpl(){
		super(UcOperator.class);
	}
	
	public OperatorDaoImpl(Class<UcOperator> persistentClass) {
		super(persistentClass);
	}


	@Override
    public void insertUcOperator(UcOperator ucOperator){
		ucOperator.setId(SequenceCreatorUtil.getTableId());
		getSqlMapClientTemplate().insert("insertUcOperator",ucOperator);
	}
	@Override
	public void updateUcOperator(UcOperator ucOperator){
		getSqlMapClientTemplate().update("updateUcOperator", ucOperator);
	}
	@Override
	public UcOperator selectUcOperatorById(Long id){
		return  (UcOperator) getSqlMapClientTemplate().queryForObject("selectUcOperatorById", id);
	}

	/* (non-Javadoc)
	 * @see net.easipay.cbp.dao.OperatorDao#selectAllUcOperator()
	 */
	@Override
	public List<UcOperator> selectAllUcOperator(int pageNo) {
		int minNo = (pageNo-1)*ConstantParams.ADMIN_PAGE_SIZE;
		int maxNo = pageNo*ConstantParams.ADMIN_PAGE_SIZE+1;
		HashMap<String,Object> queryMap = new HashMap<String,Object>();
		queryMap.put("minNo", minNo);
		queryMap.put("maxNo", maxNo);
		//queryMap.put("status", ConstantParams.UC_OPERATOR_STATUS_ENABLED);
		return  getSqlMapClientTemplate().queryForList("selectAllUcOperator",queryMap);
	}

	/* (non-Javadoc)
	 * @see net.easipay.cbp.dao.OperatorDao#selectUcOperatorByParameter(net.easipay.cbp.model.UcOperator)
	 */
	@Override
	public List<UcOperator> selectUcOperatorByParameter(UcOperator ucOperator,int pageNo) {
		int minNo = (pageNo-1)*ConstantParams.ADMIN_PAGE_SIZE;
		int maxNo = pageNo*ConstantParams.ADMIN_PAGE_SIZE+1;
		String loginName = ucOperator.getLoginName();
		String loginNameCh = ucOperator.getLoginNameCh();
		Long createUserId = ucOperator.getCreateUserId();
		String status = ucOperator.getStatus();
		HashMap<String,Object> queryMap = new HashMap<String,Object>();
		queryMap.put("minNo", minNo);
		queryMap.put("maxNo", maxNo);
		queryMap.put("loginName", loginName);
		queryMap.put("loginNameCh", loginNameCh);
		queryMap.put("status", status);
		queryMap.put("createUserId",createUserId);
		return  getSqlMapClientTemplate().queryForList("selectUcOperatorByParameter",queryMap);
	}

	/* (non-Javadoc)
	 * @see net.easipay.cbp.dao.OperatorDao#selectUcOperatorCountsByParameter(net.easipay.cbp.model.UcOperator)
	 */
	@Override
	public int selectUcOperatorCountsByParameter(
			UcOperator ucOperator) {
		return  (Integer)getSqlMapClientTemplate().queryForObject("selectUcOperatorCountsByParameter",ucOperator);
	}
	@Override
	public UcOperator selectUcOperatorByEmailAndPass(String email,String password){
		HashMap<String,Object> queryMap = new HashMap<String,Object>();
		queryMap.put("email", email);
		queryMap.put("password", password);
		return  (UcOperator) getSqlMapClientTemplate().queryForObject("selectUcOperatorByEmailAndPass",queryMap);
	}

	@Override
	public int selectUcOperatorCountsForValidate(UcOperator ucOperator) {
		return (Integer)getSqlMapClientTemplate().queryForObject("selectUcOperatorCountsByLoginName", ucOperator);
	}

	@Override
	public List<Map<String,Object>> getSacCusParameterFromTemp() {
		return getSqlMapClientTemplate().queryForList("getSacCusParameterTemp");
	}

	@Override
	public int validateRepeatCus(Map<String, Object> cusParam) {
		return (Integer)getSqlMapClientTemplate().queryForObject("validRepeatCus", cusParam);
	}
	
}
