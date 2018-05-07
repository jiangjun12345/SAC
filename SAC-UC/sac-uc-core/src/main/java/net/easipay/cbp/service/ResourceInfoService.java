/**
 * 
 */
package net.easipay.cbp.service;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.cas.auth.UserAuthoritiesMenu;
import net.easipay.cbp.model.ResourceInfo;


/**
 * @author Administrator
 *
 */
public interface ResourceInfoService {

	public List<ResourceInfo> selectResourceByOperId(Long operId,String isNeedAuth,String dicCode);
	
	public List<ResourceInfo> selectResourceByRoleId(Long roleId,String isNeedAuth,String dicCode);
	
	public List<ResourceInfo> selectAuthByRoleId(Long roleId,String dicCode);
	
	public ResourceInfo getResourceInfosById(Long id);
	
	public void insertResourceInfos(ResourceInfo resourceInfo);
	
	public List<ResourceInfo> getResourceInfos();
	
	public void updateResourceInfo(ResourceInfo resourceInfo);
	
	public List<ResourceInfo> changeList(List<ResourceInfo> topChildren,List<ResourceInfo> resultTrees);
	
	public Integer selectResourceInfoCountByParam(ResourceInfo resourceInfo);
	
	public UserAuthoritiesMenu selectResourceManageTree(Map<String,String> map);
	
}
