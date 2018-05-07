/**
 * 
 */
package net.easipay.cbp.dao;

import java.util.List;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.ResourceInfo;

/**
 * @author Administrator
 *
 */
public interface ResourceInfoDao extends GenericDao<ResourceInfo,Long> {
	
	public List<ResourceInfo> selectResourceByOperId(Long operId,String isNeedAuth,String dicCode);
	
	public List<ResourceInfo> selectResourceByRoleId(Long roleId,String isNeedAuth,String dicCode);
	
	public List<ResourceInfo> selectAuthByRoleId(Long roleId,String dicCode);
	
	public void insertResourceInfo(ResourceInfo resourceInfo);
	
	public List<ResourceInfo> getResourceInfos();
	
	public void updateResourceInfo(ResourceInfo resourceInfo);
	
	public Integer selectResourceInfoCountByParam(ResourceInfo resourceInfo);
	
}
