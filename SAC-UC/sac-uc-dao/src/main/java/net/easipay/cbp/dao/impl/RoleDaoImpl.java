/**
 * 
 */
package net.easipay.cbp.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import net.easipay.cbp.cas.users.SecurityOperator;
import net.easipay.cbp.dao.ResourceInfoDao;
import net.easipay.cbp.dao.RoleDao;
import net.easipay.cbp.dao.base.GenericDaoiBatis;
import net.easipay.cbp.model.ResourceInfo;
import net.easipay.cbp.model.UcRole;
import net.easipay.cbp.sequence.SequenceCreatorUtil;
import net.easipay.cbp.util.ConstantParams;
import net.easipay.cbp.util.StringUtil;

/**
 * @author Administrator
 *
 */

@Repository("ucRole")
public class RoleDaoImpl extends GenericDaoiBatis<UcRole,Long> implements RoleDao{
	private static final Logger logger = Logger.getLogger(RoleDaoImpl.class);
	
	public RoleDaoImpl(){
		super(UcRole.class);
	}
	
	public RoleDaoImpl(Class<UcRole> persistentClass) {
		super(persistentClass);
	}

	@Override
	public List<UcRole> selectUcRoles(int rownum,int rn) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rownum", rownum);
		map.put("rn", rn);
		SecurityOperator oper = (SecurityOperator)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String create_user_id = (oper==null)?null:oper.getOperId();
        String email = (oper==null)?null:oper.getEmail();
        if(!ConstantParams.SUPERADMIN.equalsIgnoreCase(email)){
        	map.put("createUserId", Long.valueOf((StringUtil.isEmptyString(create_user_id)==true)?"0":create_user_id));
        }
		List<UcRole> roleList = getSqlMapClientTemplate().queryForList("selectUcRoles",map);
		return roleList;
	}
	@Override
    public void insertUcRole(UcRole ucRole){
		ucRole.setId(SequenceCreatorUtil.getTableId());
		getSqlMapClientTemplate().insert("insertUcRole",ucRole);
	}
	@Override
	public void updateUcRole(UcRole ucRole){
		getSqlMapClientTemplate().update("updateUcRole", ucRole);
	}
	@Override
	public UcRole selectUcRolesById(Long id){
		return (UcRole) getSqlMapClientTemplate().queryForObject("selectUcRolesById", id);
	}
	@Override
	public Integer selectUcRoleTotal(){
		 return (Integer) getSqlMapClientTemplate().queryForObject("selectUcRoleTotal");
	}
	
}
