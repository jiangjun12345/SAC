package net.easipay.cbp.service.impl;
import net.easipay.cbp.dao.RoleResourceDao;
import net.easipay.cbp.model.UcRoleResource;
import net.easipay.cbp.service.RoleResourceService;
import net.easipay.cbp.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("roleResourceService")
public class RoleResourceServiceImpl implements RoleResourceService {

	@Autowired
	private RoleResourceDao roleResourceDao;

	/*
	 * 通过用户找菜单 参数operId ： 用户Id ；isNeedAuth 是否包含权限菜单 isNeedAuth ： null 不包含 "1" 包含
	 * 返回 菜单List
	 */

	@Override
	public void deleteByUcRolesId(Long roleId){
		roleResourceDao.deleteByUcRolesId(roleId);
	}
	@Override
	public void saveUcRoleIdResourceId(Long rodeId,String str){
		if(rodeId != null){
		  roleResourceDao.deleteByUcRolesId(rodeId);
		}
		if(!StringUtil.isEmptyString(str)){
			String[] checks = str.split(",");
			
			for(int i=0;i<checks.length;i++){
				UcRoleResource ucRoleResource = new UcRoleResource();
				ucRoleResource.setRoleId(rodeId);
				ucRoleResource.setResourceId(new Long(checks[i]));
				roleResourceDao.insertUcRoleResource(ucRoleResource);
				}
		}
		
	}

 
}
