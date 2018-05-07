/**
 * 
 */
package net.easipay.cbp.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.OrgInfoDao;
import net.easipay.cbp.dao.base.GenericDaoiBatis;
import net.easipay.cbp.model.UcOrgInfo;
import net.easipay.cbp.sequence.SequenceCreatorUtil;
import net.easipay.cbp.util.ConstantParams;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 *
 */

@Repository("ucOrgInfo")
public class OrgInfoDaoImpl extends GenericDaoiBatis<UcOrgInfo,Long> implements OrgInfoDao{
	private static final Logger logger = Logger.getLogger(OrgInfoDaoImpl.class);
	
	public OrgInfoDaoImpl(){
		super(UcOrgInfo.class);
	}
	
	public OrgInfoDaoImpl(Class<UcOrgInfo> persistentClass) {
		super(persistentClass);
	}


	@Override
    public void insertOrgInfo(UcOrgInfo ucOrgInfo){
		ucOrgInfo.setOrgId(SequenceCreatorUtil.getTableId());
		getSqlMapClientTemplate().insert("insertUcOrgInfo",ucOrgInfo);
	}
	@Override
	public void updateOrgInfo(UcOrgInfo ucOrgInfo){
		getSqlMapClientTemplate().update("updateUcOrgInfo", ucOrgInfo);
	}
	@Override
	public UcOrgInfo selectOrgInfoById(Long id){
		return  (UcOrgInfo) getSqlMapClientTemplate().queryForObject("selectUcOrgInfoById", id);
	}
	@Override
	public List<UcOrgInfo> selectAllUcOrgInfo(int pageNo){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rownum", pageNo*ConstantParams.ADMIN_PAGE_SIZE+1);
		map.put("rn", ((pageNo-1)*ConstantParams.ADMIN_PAGE_SIZE));
		return getSqlMapClientTemplate().queryForList("selectAllUcOrgInfo",map);
	}
	public Integer selectUcOrgInfoTotal(){
		return (Integer) getSqlMapClientTemplate().queryForObject("selectUcOrgInfoTotal");
	}

	@Override
	public List<UcOrgInfo> selectOrgInfoByParameter(UcOrgInfo orgInfo) {
		return getSqlMapClientTemplate().queryForList("selectUcOrgInfoByParamter",orgInfo);
	}

}
