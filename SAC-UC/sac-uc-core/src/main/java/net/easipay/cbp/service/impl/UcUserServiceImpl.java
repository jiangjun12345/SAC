package net.easipay.cbp.service.impl;

import java.util.List;

import net.easipay.cbp.dao.UcUserDao;
import net.easipay.cbp.model.UcUser;
import net.easipay.cbp.service.UcUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ucUserService")
public class UcUserServiceImpl implements UcUserService {

	@Autowired
	private UcUserDao ucUserDao;

	@Override
	public UcUser selectUcUserById(Long id) {
		
		return ucUserDao.selectUcUserById(id);
	}

	@Override
	public void insertUcUser(UcUser ucUser) {
		ucUserDao.insertUcUser(ucUser);
		
	}

	@Override
	public void updateUcUser(UcUser ucUser) {
		ucUserDao.updateUcUser(ucUser);
	}

	/* (non-Javadoc)
	 * @see net.easipay.cbp.service.UcUserService#selectUcUserByParameter(net.easipay.cbp.model.UcUser, int, int)
	 */
	@Override
	public List<UcUser> selectUcUserByParameter(UcUser ucUser, int startNo,
			int endNo) {
		return ucUserDao.selectUcUserByParameter(ucUser,startNo,endNo);
	}

	/* (non-Javadoc)
	 * @see net.easipay.cbp.service.UcUserService#selectUcUserCountsByParameter(net.easipay.cbp.model.UcUser, int, int)
	 */
	@Override
	public int selectUcUserCountsByParameter(UcUser ucUser) {
		return ucUserDao.selectUcUserCountsByParameter(ucUser);
	}
	
	public int selectUcUserCountsForValidate(UcUser ucUser){
		return ucUserDao.selectUcUserCountsForValidate(ucUser);
	}

	
}
