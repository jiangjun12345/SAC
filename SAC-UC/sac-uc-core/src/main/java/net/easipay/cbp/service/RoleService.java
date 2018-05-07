/**
 * 
 */
package net.easipay.cbp.service;

import java.util.List;

import net.easipay.cbp.model.UcRole;
import net.easipay.cbp.page.Page;


/**
 * @author Administrator
 *
 */
public interface RoleService {

	public List<UcRole> selectUcRoles(int rownum,int rn);
	
	public void insertUcRole(UcRole ucRole);
	
	public void updateUcRole(UcRole ucRole);
	
	public UcRole selectUcRolesById(Long id);
	
	
	public Integer selectUcRoleTotal();
}
