/**
 * 
 */
package net.easipay.cbp.dao;

import java.util.List;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.UcOrgInfo;
import net.easipay.cbp.model.UcUser;
/**
 * @author Administrator
 *
 */
public interface UcUserDao extends GenericDao<UcUser,Long> {
	
	public UcUser selectUcUserById(Long id);

	public void insertUcUser(UcUser ucUser);
	
	public void updateUcUser(UcUser ucUser);
	
	/**
	 * 根据参数分页查询用户信息
	 * @param ucUser
	 * @param startNo
	 * @param endNo
	 * @return
	 */
	public List<UcUser> selectUcUserByParameter(UcUser ucUser,int startNo,int endNo);
	
	/**
	 * 根据参数分页查询用户信息数量
	 * @param ucUser
	 * @param startNo
	 * @param endNo
	 * @return
	 */
	public int selectUcUserCountsByParameter(UcUser ucUser);
	
	/**
	 * 根据参数分页查询用户信息数量
	 * @param ucUser
	 * @param startNo
	 * @param endNo
	 * @return
	 */
	public int selectUcUserCountsForValidate(UcUser ucUser);
	
}

