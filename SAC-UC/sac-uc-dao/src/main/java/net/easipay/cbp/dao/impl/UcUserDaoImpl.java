/**
 * 
 */
package net.easipay.cbp.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.UcUserDao;
import net.easipay.cbp.dao.base.GenericDaoiBatis;
import net.easipay.cbp.model.UcUser;
import net.easipay.cbp.sequence.SequenceCreatorUtil;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 *
 */

@Repository("ucUser")
public class UcUserDaoImpl extends GenericDaoiBatis<UcUser,Long> implements UcUserDao{
	private static final Logger logger = Logger.getLogger(UcUserDaoImpl.class);
	
	public UcUserDaoImpl(){
		super(UcUser.class);
	}
	
	public UcUserDaoImpl(Class<UcUser> persistentClass) {
		super(persistentClass);
	}

	@Override
	public UcUser selectUcUserById(Long id) {
		
		return (UcUser) getSqlMapClientTemplate().queryForObject("selectUcUserById", id);
	}
	
	/**
	 * 根据参数分页查询用户信息
	 */
	@Override
	public List<UcUser> selectUcUserByParameter(UcUser ucUser,int startNo,int endNo) {
		Map<String,Object> queryMap = new HashMap<String,Object>();
		String email = ucUser.getEmail();
		String identifyCode = ucUser.getIdentifyCode();
		String mobile = ucUser.getMobile();
		String personName = ucUser.getPersonName();
		queryMap.put("startNo",startNo);
		queryMap.put("endNo",endNo);
		queryMap.put("email",email);
		queryMap.put("identifyCode",identifyCode);
		queryMap.put("mobile",mobile);
		queryMap.put("personName",personName);
		return getSqlMapClientTemplate().queryForList("selectUcUserByParameter", queryMap);
	}
	
	/**
	 * 根据参数分页查询用户信息数量
	 */
	@Override
	public int selectUcUserCountsByParameter(UcUser ucUser) {
		Map<String,Object> queryMap = new HashMap<String,Object>();
		String email = ucUser.getEmail();
		String identifyCode = ucUser.getIdentifyCode();
		String mobile = ucUser.getMobile();
		String personName = ucUser.getPersonName();
		queryMap.put("email",email);
		queryMap.put("identifyCode",identifyCode);
		queryMap.put("mobile",mobile);
		queryMap.put("personName",personName);
		return (Integer)getSqlMapClientTemplate().queryForObject("selectUcUserCountsByParameter", queryMap);
	}

	@Override
	public void insertUcUser(UcUser ucUser) {
		ucUser.setId(SequenceCreatorUtil.getTableId());
		getSqlMapClientTemplate().insert("insertUcUser",ucUser);
		
	}

	@Override
	public void updateUcUser(UcUser ucUser) {
		getSqlMapClientTemplate().update("updateUcUser",ucUser);
		
	}
	
	/**
	 * 根据参数分页查询用户信息数量
	 * @param ucUser
	 * @param startNo
	 * @param endNo
	 * @return
	 */
	public int selectUcUserCountsForValidate(UcUser ucUser){
		return (Integer)getSqlMapClientTemplate().queryForObject("selectUcUserCountsForValidate", ucUser);
	}


	

}
