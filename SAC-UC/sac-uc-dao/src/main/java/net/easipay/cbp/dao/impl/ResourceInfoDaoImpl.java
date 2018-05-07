/**
 * 
 */
package net.easipay.cbp.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import net.easipay.cbp.dao.ResourceInfoDao;
import net.easipay.cbp.dao.base.GenericDaoiBatis;
import net.easipay.cbp.model.ResourceInfo;
import net.easipay.cbp.sequence.SequenceCreatorUtil;

/**
 * @author Administrator
 *
 */

@Repository("resourceInfo")
public class ResourceInfoDaoImpl extends GenericDaoiBatis<ResourceInfo,Long> implements ResourceInfoDao{
	private static final Logger logger = Logger.getLogger(ResourceInfoDaoImpl.class);
	
	public ResourceInfoDaoImpl(){
		super(ResourceInfo.class);
	}
	
	public ResourceInfoDaoImpl(Class<ResourceInfo> persistentClass) {
		super(persistentClass);
	}

	@Override
	public List<ResourceInfo> selectResourceByOperId(Long operId,String isNeedAuth,String dicCode){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("operId", operId);
		map.put("isNeedAuth", isNeedAuth);
		map.put("dicCode", dicCode);
		 List<ResourceInfo> resourceInfoList = getSqlMapClientTemplate().queryForList("selectResourceByOperId", map);

		 return resourceInfoList; 
	}
	
	@Override
	public List<ResourceInfo> selectResourceByRoleId(Long roleId,String isNeedAuth,String dicCode){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleId", roleId);
		map.put("isNeedAuth", isNeedAuth);
		map.put("dicCode", dicCode);
		List<ResourceInfo> resourceInfoList = getSqlMapClientTemplate().queryForList("selectResourceByRoleId", map);
		for(ResourceInfo resourceInfo : resourceInfoList){
			logger.debug("before="+resourceInfo.getResourceId()+resourceInfo.getResourceName());
		}
		return resourceInfoList; 
	}


	@Override
	public List<ResourceInfo> selectAuthByRoleId(Long roleId,String dicCode){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleId", roleId);
		map.put("dicCode", dicCode);
		List<ResourceInfo> resourceInfoList = getSqlMapClientTemplate().queryForList("selectAuthByRoleId", map);
		 return resourceInfoList; 
	}


	public ResourceInfo getResourceInfosById(Long id){
		   ResourceInfo  resourceInfo = super.get(id);
		   return resourceInfo;
	}
	
	public void insertResourceInfo(ResourceInfo resourceInfo){
		resourceInfo.setResourceId(SequenceCreatorUtil.getTableId());
		getSqlMapClientTemplate().insert("insertResourceInfo", resourceInfo);
	}
	
	@Override
	public List<ResourceInfo> getResourceInfos(){
		List<ResourceInfo> resourceInfoList = getSqlMapClientTemplate().queryForList("getResourceInfos");
		 return resourceInfoList; 
	}
	@Override
	public void updateResourceInfo(ResourceInfo resourceInfo){
		getSqlMapClientTemplate().update("updateResourceInfo",resourceInfo);
	}
	@Override
	public Integer selectResourceInfoCountByParam(ResourceInfo resourceInfo){
		Integer integer = (Integer)getSqlMapClientTemplate().queryForObject("selectResourceInfoCountByParam",resourceInfo);
	    return integer;
	}
}
