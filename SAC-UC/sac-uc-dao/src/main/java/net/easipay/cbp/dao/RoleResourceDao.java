/**
 * 
 */
package net.easipay.cbp.dao;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.UcRoleResource;

/**
 * @author Administrator
 *
 */
public interface RoleResourceDao extends GenericDao<UcRoleResource,Long> {
	
	public void deleteByUcRolesId(Long roleId);

	public void insertUcRoleResource(UcRoleResource ucRoleResource);
	
}
