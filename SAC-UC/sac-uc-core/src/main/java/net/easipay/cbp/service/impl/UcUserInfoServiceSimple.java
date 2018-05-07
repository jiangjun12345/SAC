/**
 * 
 */
package net.easipay.cbp.service.impl;

import java.util.HashMap;
import java.util.Map;

import net.easipay.cbp.cas.auth.UserAuthoritiesMenu;
import net.easipay.cbp.cas.message.AbstractUserInfoService;
import net.easipay.cbp.cas.users.OrgnizationInfo;
import net.easipay.cbp.model.UcOrgInfo;
import net.easipay.cbp.service.OrgInfoService;
import net.easipay.cbp.service.ResourceInfoService;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Administrator
 * @version 1.0.0
 * @date 2015年3月24日
 */
public class UcUserInfoServiceSimple extends AbstractUserInfoService{

	@Autowired
	private ResourceInfoService resourceInfoService;
	@Autowired
	private OrgInfoService orgInfoService;
	
	@Override
	public OrgnizationInfo queryOrgnizationInfo(String orgId) {
		try {
			UcOrgInfo ucOrgInfo = new UcOrgInfo();
			ucOrgInfo = orgInfoService.selectOrgInfoById(Long.valueOf(orgId));
			return orgInfoService.transFromUcOrgInfo(ucOrgInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public UserAuthoritiesMenu queryMenuAuth(String userId, String userType,
			String dicCode) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("userId", userId);
			map.put("userType", userType);
			map.put("dicCode", dicCode);
			UserAuthoritiesMenu userAuthoritiesMenu = resourceInfoService
					.selectResourceManageTree(map);
			return userAuthoritiesMenu;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

}
