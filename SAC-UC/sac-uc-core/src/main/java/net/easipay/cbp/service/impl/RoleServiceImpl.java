package net.easipay.cbp.service.impl;

import java.util.Date;
import java.util.List;

import net.easipay.cbp.dao.RoleDao;
import net.easipay.cbp.model.UcRole;
import net.easipay.cbp.service.RoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;

	/*
	 * 查找所有角色列表
	 */
	@Override
	public List<UcRole> selectUcRoles(int rownum,int rn) {
       
		return roleDao.selectUcRoles(rownum,rn);
	}

	public void insertUcRole(UcRole ucRole){
		Date d = new Date();
		ucRole.setCreateTime(d);
		roleDao.insertUcRole(ucRole);
	}
	
	public void updateUcRole(UcRole ucRole){
		roleDao.updateUcRole(ucRole);
	}

	public UcRole selectUcRolesById(Long id){
		return roleDao.selectUcRolesById(id);
	}
	
	public Integer selectUcRoleTotal(){
		return roleDao.selectUcRoleTotal();
	}

}
