/**
 * 
 */
package net.easipay.cbp.dao;

import java.util.List;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.UcOrgInfo;
/**
 * @author Administrator
 *
 */
public interface OrgInfoDao extends GenericDao<UcOrgInfo,Long> {
	
	public UcOrgInfo selectOrgInfoById(Long id);

	public void insertOrgInfo(UcOrgInfo orgInfo);
	
	public void updateOrgInfo(UcOrgInfo orgInfo);
	
	public List<UcOrgInfo> selectAllUcOrgInfo(int pageNo);
	
	public Integer selectUcOrgInfoTotal();
	
	public List<UcOrgInfo> selectOrgInfoByParameter(UcOrgInfo orgInfo);
}
