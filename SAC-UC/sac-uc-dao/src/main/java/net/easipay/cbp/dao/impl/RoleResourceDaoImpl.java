/**
 * 
 */
package net.easipay.cbp.dao.impl;


import org.springframework.stereotype.Repository;
import net.easipay.cbp.dao.RoleResourceDao;
import net.easipay.cbp.dao.base.GenericDaoiBatis;
import net.easipay.cbp.model.UcRoleResource;

/**
 * @author Administrator
 *
 */

@Repository("ucRoleResource")
public class RoleResourceDaoImpl extends GenericDaoiBatis<UcRoleResource,Long> implements RoleResourceDao{
	
	public RoleResourceDaoImpl() {
		super(UcRoleResource.class);
	}
	public RoleResourceDaoImpl(Class<UcRoleResource> persistentClass) {
		super(persistentClass);
	}

	public void deleteByUcRolesId(Long roleId){
		getSqlMapClientTemplate().delete("deleteByUcRolesId", roleId);
	}
	
	public void insertUcRoleResource(UcRoleResource ucRoleResource){
		getSqlMapClientTemplate().insert("insertUcRoleResource", ucRoleResource);
	}
}
