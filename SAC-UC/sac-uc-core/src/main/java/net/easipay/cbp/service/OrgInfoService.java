/**
 * 
 */
package net.easipay.cbp.service;

import java.util.List;

import net.easipay.cbp.cas.users.OrgnizationInfo;
import net.easipay.cbp.model.UcOrgInfo;


/**
 * @author Administrator
 *
 */
public interface OrgInfoService {

	public UcOrgInfo selectOrgInfoById(Long id);

	public void insertOrgInfo(UcOrgInfo orgInfo);
	
	public void updateOrgInfo(UcOrgInfo orgInfo);
	
	public List<UcOrgInfo> selectAllUcOrgInfo(int pageNo);
	
	public Integer selectUcOrgInfoTotal();
	
	/**
	 * 根据参数查询前10条数据
	 * @return
	 */
	public List<UcOrgInfo> selectOrgInfoByParameter(UcOrgInfo orgInfo);
	
	public OrgnizationInfo transFromUcOrgInfo(UcOrgInfo ucOrgInfo);
}
