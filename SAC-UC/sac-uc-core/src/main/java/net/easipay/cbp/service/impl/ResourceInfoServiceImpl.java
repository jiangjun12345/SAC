package net.easipay.cbp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.easipay.cbp.cas.auth.Authority;
import net.easipay.cbp.cas.auth.Menu;
import net.easipay.cbp.cas.auth.Role;
import net.easipay.cbp.cas.auth.UserAuthoritiesMenu;
import net.easipay.cbp.dao.OperatorDao;
import net.easipay.cbp.dao.ResourceInfoDao;
import net.easipay.cbp.dao.RoleDao;
import net.easipay.cbp.model.ResourceInfo;
import net.easipay.cbp.model.UcOperator;
import net.easipay.cbp.model.UcRole;
import net.easipay.cbp.service.ResourceInfoService;
import net.easipay.cbp.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("resourceInfoService")
public class ResourceInfoServiceImpl implements ResourceInfoService {

	@Autowired
	private ResourceInfoDao resourceInfoDao;
	@Autowired
	private OperatorDao operatorDao;
	@Autowired
	private RoleDao roleDao;

	/*
	 * 通过用户找菜单 参数operId ： 用户Id ；isNeedAuth 是否包含权限菜单 isNeedAuth ： null 不包含 "1" 包含
	 * 返回 菜单List
	 */

	@Override
	public List<ResourceInfo> selectResourceByOperId(Long operId,
			String isNeedAuth,String dicCode) {
		return resourceInfoDao.selectResourceByOperId(operId, isNeedAuth,dicCode);
	}

	/*
	 * 通过角色找菜单 参数roleId ： 用户角色roleId ；isNeedAuth 是否包含权限菜单 isNeedAuth ： null 不包含
	 * "1" 包含 返回 菜单List
	 */
	@Override
	public List<ResourceInfo> selectResourceByRoleId(Long roleId,
			String isNeedAuth,String dicCode) {
		List<ResourceInfo> list = resourceInfoDao.selectResourceByRoleId(roleId, isNeedAuth,dicCode);
		
		//List<ResourceInfo> resultList = new ArrayList<ResourceInfo>();
		//changeList(list,resultList);
		
		return list;
	}

	/*
	 * 通过角色找权限 参数roleId ： 用户角色roleId ； 返回 权限List
	 */

	@Override
	public List<ResourceInfo> selectAuthByRoleId(Long roleId,String dicCode) {
		return resourceInfoDao.selectAuthByRoleId(roleId,dicCode);
	}

	/*
	 * 通过菜单ID找菜单 参数id ： 菜单Id； 返回 菜单BEAN
	 */

	@Override
	public ResourceInfo getResourceInfosById(Long id) {
		return resourceInfoDao.get(id);
	}
	/*
	 * 新建插入菜单表，主键ID在Dao层生成
	 */
	@Override
	public void insertResourceInfos(ResourceInfo resourceInfo) {
		resourceInfoDao.insertResourceInfo(resourceInfo);
	}
	/*
	 * 获得系统所有的有效菜单
	 */
	@Override
	public List<ResourceInfo> getResourceInfos() {
		return resourceInfoDao.getResourceInfos();
	};
	/*
	 * 根据Id更新菜单
	 */
	@Override
	public void updateResourceInfo(ResourceInfo resourceInfo){
		resourceInfo.setUpdateTime(new Date());
		resourceInfoDao.updateResourceInfo(resourceInfo);
	}

	/*
	 * 拼成json格式
	 */
	public List<ResourceInfo> changeList(List<ResourceInfo> resourceInfoList,List<ResourceInfo> resultTrees){
		if(resourceInfoList != null && resourceInfoList.size()>0){
			
			for(ResourceInfo resourceInfo : resourceInfoList){
				if(resourceInfo.getResourceType().equals("1")){
					ResourceInfo resourceTemp = new ResourceInfo();
					List<ResourceInfo> list = new ArrayList<ResourceInfo>();
					resourceTemp = resourceInfo;
					for(ResourceInfo resourceInfoChild : resourceInfoList){
						if(resourceInfoChild.getResourceType().equals("2") && resourceTemp.getResourceId().equals(resourceInfoChild.getParentId())){
							list.add(resourceInfoChild);
						}
					}
					resourceTemp.setChilds(list);
					resultTrees.add(resourceTemp);
				} 
				
				
			}
			
		}
		return resultTrees;
	}
	
	public Integer selectResourceInfoCountByParam(ResourceInfo resourceInfo){
		return resourceInfoDao.selectResourceInfoCountByParam(resourceInfo);
	}

	public UserAuthoritiesMenu selectResourceManageTree(Map<String,String> map){
		UserAuthoritiesMenu result = new UserAuthoritiesMenu();
		if(map != null){
			 if(!StringUtil.isEmptyString(map.get("userId"))){
	    		 
		    		//查找该用户下有效菜单列表
		 	        List<ResourceInfo> resultList = selectResourceByOperId(Long.valueOf(map.get("userId")), null,map.get("dicCode"));
		 	        //查找该角色下权限列表
		 	        UcOperator ucOperator = operatorDao.selectUcOperatorById(Long.valueOf(map.get("userId")));
		 	        if(ucOperator!=null){
		 	        	//将权限列表组装 
		 	        	List<ResourceInfo> authList = selectAuthByRoleId(ucOperator.getRoleId(), null);
		 	        	
		 	        	List<Authority> authorities = new ArrayList<Authority>();
		 	        	   for(ResourceInfo a:authList){
		 	        		  Authority authority = new Authority();
		 	        		 authority.setDescription(a.getDescription());
		 	        		 authority.setResourceCode(a.getResourceCode());
		 	        		 authority.setResourceName(a.getResourceName());
		 	        		 authority.setResourceUrl(a.getResourceUrl());
		 	        		 authorities.add(authority);
		 	        	   }
		 	        	

		 	        	     List<ResourceInfo> menuList = new ArrayList<ResourceInfo>();
				 	        //将菜单进行转换
				 	          changeList(resultList,menuList);
				 	          
				 	         List<Menu> menuL = new ArrayList<Menu>();
				 	         for(ResourceInfo r:menuList){
				 	        	Menu menu = new Menu();
				 	        	menu.setCode(r.getResourceCode());
				 	        	menu.setDescription(r.getDescription());
				 	        	menu.setResourceName(r.getResourceName());
				 	        	menu.setResourceType(r.getResourceType());
				 	        	menu.setResourceUrl(r.getResourceUrl());
				 	        	
				 	        	List<Menu> menuL1 = new ArrayList<Menu>();
				 	            for(ResourceInfo r1: r.getChilds()){
				 	            	Menu menu1 = new Menu();
				 	            	menu1.setCode(r1.getResourceCode());
					 	        	menu1.setDescription(r1.getDescription());
					 	        	menu1.setResourceName(r1.getResourceName());
					 	        	menu1.setResourceType(r1.getResourceType());
					 	        	menu1.setResourceUrl(r1.getResourceUrl());
					 	        	menuL1.add(menu1);
				 	            }
				 	        	menu.setChilds(menuL1);
				 	        	menuL.add(menu);
				 	         }
				 	          
					          //查找角色信息
					          UcRole ucRole = roleDao.selectUcRolesById(ucOperator.getRoleId());
					          Role role = new Role();
					          if(ucRole != null){
							        role.setDescription(ucRole.getDescription());
							        role.setRoleCode(ucRole.getRoleCode());
							        role.setRoleId("");
							        role.setRoleName(ucRole.getRoleName());
					          }
					      

					     	  List<Menu> meList = new ArrayList<Menu>();
					          List<ResourceInfo> list = selectResourceByRoleId(Long.valueOf(ucOperator.getRoleId()), null, null);
					    		 if(list!=null){  			 
					    			  for(ResourceInfo r : list){
					    				  if("0".equals(r.getResourceType())){
					    					  Menu m = new Menu();
					    					  m.setResourceName(r.getResourceName());
					    					  m.setDescription(r.getDescription());
					    					  m.setResourceType(r.getResourceType());
					    					  m.setResourceUrl(r.getResourceUrl());
					    					  meList.add(m);
					    				  }
					    			  }
					    			 
					    		 }
					    		 result.setAuthorities(authorities);
					    		 result.setMenu(menuL);
					    		 result.setProjectName(meList);
					    		 result.setRole(role);
		 	        }
			          
		 	         
		}

	}
		return result;
  }
	
	
	public static void main(String[] args) {
		Menu menu = new Menu();
		menu.setCode("asdasd");
		System.out.println(menu.getCode());
	}
}
