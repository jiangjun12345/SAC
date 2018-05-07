/**
 * 
 */
package net.easipay.cbp.dao;

import java.util.List;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.UcRole;

/**
 * @author Administrator
 *
 */
public interface RoleDao extends GenericDao<UcRole,Long> {
	
	public List<UcRole> selectUcRoles(int rownum,int rn);
	
	public void insertUcRole(UcRole ucRole);
	
	public void updateUcRole(UcRole ucRole);
	
	public UcRole selectUcRolesById(Long id);
	
	public Integer selectUcRoleTotal();
}
