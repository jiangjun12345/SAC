/**
 * 
 */
package net.easipay.cbp.service;

import java.util.List;

import net.easipay.cbp.model.UcUser;


/**
 * @author Administrator
 *
 */
public interface UcUserService {

	public UcUser selectUcUserById(Long id);

	public void insertUcUser(UcUser ucUser);
	
	public void updateUcUser(UcUser ucUser);
	
	public List<UcUser> selectUcUserByParameter(UcUser ucUser,int startNo,int endNo);
	
	public int selectUcUserCountsByParameter(UcUser ucUser);
	
	public int selectUcUserCountsForValidate(UcUser ucUser);
}
