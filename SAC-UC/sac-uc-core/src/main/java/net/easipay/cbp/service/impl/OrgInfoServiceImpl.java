package net.easipay.cbp.service.impl;

import java.util.List;

import net.easipay.cbp.cas.users.OrgnizationInfo;
import net.easipay.cbp.dao.OrgInfoDao;
import net.easipay.cbp.model.UcOperator;
import net.easipay.cbp.model.UcOrgInfo;
import net.easipay.cbp.service.OrgInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("orgInfoService")
public class OrgInfoServiceImpl implements OrgInfoService {

	@Autowired
	private OrgInfoDao orgInfoDao;

	@Override
	public UcOrgInfo selectOrgInfoById(Long id) {
		
		return orgInfoDao.selectOrgInfoById(id);
	}

	@Override
	public void insertOrgInfo(UcOrgInfo orgInfo) {
		orgInfoDao.insertOrgInfo(orgInfo);
		
	}

	@Override
	public void updateOrgInfo(UcOrgInfo orgInfo) {
		orgInfoDao.updateOrgInfo(orgInfo);
		
	}
	@Override
	public List<UcOrgInfo> selectAllUcOrgInfo(int pageNo){
		return orgInfoDao.selectAllUcOrgInfo(pageNo);
	}
   @Override
	public Integer selectUcOrgInfoTotal(){
		return orgInfoDao.selectUcOrgInfoTotal();
	}

	@Override
	public List<UcOrgInfo> selectOrgInfoByParameter(UcOrgInfo orgInfo) {
		return orgInfoDao.selectOrgInfoByParameter(orgInfo);
	}
	@Override
	public OrgnizationInfo transFromUcOrgInfo(UcOrgInfo ucOrgInfo){
		OrgnizationInfo orgnizationInfo = new OrgnizationInfo();
		if(ucOrgInfo!=null){
		orgnizationInfo.setAddress(ucOrgInfo.getAddress());
		orgnizationInfo.setCorporation(ucOrgInfo.getCorporation());
		orgnizationInfo.setCustomerCode(ucOrgInfo.getCustomerCode());
		orgnizationInfo.setDutyLicenseFile(ucOrgInfo.getDutyLicenseFile());
		orgnizationInfo.setDutyScope(ucOrgInfo.getDutyScope());
		orgnizationInfo.setEmail(ucOrgInfo.getEmail());
		orgnizationInfo.setEngName(ucOrgInfo.getEngName());
		orgnizationInfo.setFax(ucOrgInfo.getFax());
		orgnizationInfo.setLawman(ucOrgInfo.getLawman());
		orgnizationInfo.setLawmanPasscode(ucOrgInfo.getLawmanPasscode());
		orgnizationInfo.setLawmanPasstype(ucOrgInfo.getLawmanPasstype());
		orgnizationInfo.setLinkman(ucOrgInfo.getLinkman());
		orgnizationInfo.setLocCountry(ucOrgInfo.getLocCountry());
		orgnizationInfo.setMemo(ucOrgInfo.getMemo());
		orgnizationInfo.setMerchantNcode(ucOrgInfo.getMerchantNcode());
		orgnizationInfo.setMobilePhone(ucOrgInfo.getMobilePhone());
		orgnizationInfo.setOrgBranch(ucOrgInfo.getOrgBranch());
		orgnizationInfo.setOrgCode(ucOrgInfo.getOrgCode());
		orgnizationInfo.setOrgId(ucOrgInfo.getOrgId().toString());
		orgnizationInfo.setOrgLicenseFile(ucOrgInfo.getOrgLicenseFile());
		orgnizationInfo.setOrgName(ucOrgInfo.getOrgName());
		orgnizationInfo.setOrgPhone(ucOrgInfo.getOrgPhone());
		orgnizationInfo.setOrgType(ucOrgInfo.getOrgType());
		orgnizationInfo.setPhone(ucOrgInfo.getPhone());
		orgnizationInfo.setRegAddress(ucOrgInfo.getRegAddress());
		orgnizationInfo.setRegCountry(ucOrgInfo.getRegCountry());
		orgnizationInfo.setShortName(ucOrgInfo.getShortName());
		orgnizationInfo.setStatus(ucOrgInfo.getStatus());
		orgnizationInfo.setTaxyRegLicenseFile(ucOrgInfo.getTaxyRegLicenseFile());
		orgnizationInfo.setTitle(ucOrgInfo.getTitle());
		orgnizationInfo.setZip(ucOrgInfo.getZip());
		}
		return orgnizationInfo;
	}
}
