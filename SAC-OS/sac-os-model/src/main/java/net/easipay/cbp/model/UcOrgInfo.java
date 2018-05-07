package net.easipay.cbp.model;

import java.util.Date;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UcOrgInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long orgId;
	@Size(min=1,max=16,message="{ucOrgInfo.null。length.error}")
	private String orgCode;
	@Size(min=1,max=40,message="{ucOrgInfo.null。length.error}")
	private String orgName;
	@Size(max=40,message="{ucOrgInfo.length.error}")
	private String shortName;
	@Size(max=40,message="{ucOrgInfo.length.error}")
	private String engName;
	@Size(max=40,message="{ucOrgInfo.length.error}")
	private String regCountry;
	@Size(max=40,message="{ucOrgInfo.length.error}")
	private String locCountry;
	@Size(max=20,message="{ucOrgInfo.length.error}")
	private String corporation;
	@Pattern(regexp = "^[1-9]\\d{5}$", message="{ucOrgInfo.zip.error}")
	@Size(max=6,message="{ucOrgInfo.length.error}")
	private String zip;
	@Size(max=80,message="{ucOrgInfo.length.error}")
	private String address;
	@Size(max=20,message="{ucOrgInfo.length.error}")
	private String fax;
	@Size(max=20,message="{ucOrgInfo.length.error}")
	private String linkman;
	@Size(max=32,message="{ucOrgInfo.length.error}")
	private String phone;
	@Pattern(regexp = "^(?=\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$).{0,24}$", message="{ucOrgInfo.email.error}")
	@Size(max=24,message="{ucOrgInfo.length.error}")
	private String email;
	private Date createTime;
	private Date updateTime;
	private String orgType;
	@Size(max=10,message="{ucOrgInfo.length.error}")
	private String customerCode;
	@Size(max=10,message="{ucOrgInfo.length.error}")
	private String title;
	private String status;
	@Size(max=170,message="{ucOrgInfo.length.error}")
	private String dutyScope;
	@Size(max=20,message="{ucOrgInfo.length.error}")
	private String lawman;
	@Size(max=10,message="{ucOrgInfo.length.error}")
	private String lawmanPasstype;
	@Size(max=32,message="{ucOrgInfo.length.error}")
	private String lawmanPasscode;
	@Size(max=10,message="{ucOrgInfo.length.error}")
	private String auditUser;
	private String approvaledStatus;
	@Size(max=10,message="{ucOrgInfo.length.error}")
	private String approvaledNotes;
	private Date approvaledDate;
	private String dutyLicenseFile;
	private String orgLicenseFile;
	private String taxyRegLicenseFile;
	private String lawManLicenseFile;
	@Size(max=50,message="{ucOrgInfo.length.error}")
	private String orgPhone;
	@Size(max=80,message="{ucOrgInfo.length.error}")
	private String regAddress;
	@Size(max=100,message="{ucOrgInfo.length.error}")
	private String mobilePhone;
	@Size(max=80,message="{ucOrgInfo.length.error}")
	private String orgBranch;
	@Size(max=20,message="{ucOrgInfo.length.error}")
	private String updateUserName;
	private Long updateUserId;
	@Size(max=80,message="{ucOrgInfo.length.error}")
	private String memo;
	@Size(max=8,message="{ucOrgInfo.length.error}")
	private String merchantNcode;

	public UcOrgInfo() {
	}

	public UcOrgInfo(Long orgId, String orgCode, String orgName,
			Date createTime, String status) {
		this.orgId = orgId;
		this.orgCode = orgCode;
		this.orgName = orgName;
		this.createTime = createTime;
		this.status = status;
	}

	public UcOrgInfo(Long orgId, String orgCode, String orgName,
			String shortName, String engName, String regCountry,
			String locCountry, String corporation, String zip, String address,
			String fax, String linkman, String phone, String email,
			Date createTime, Date updateTime, String orgType,
			String customerCode, String title, String status, String dutyScope,
			String lawman, String lawmanPasstype, String lawmanPasscode,
			String auditUser, String approvaledStatus, String approvaledNotes,
			Date approvaledDate, String dutyLicenseFile, String orgLicenseFile,
			String taxyRegLicenseFile, String lawManLicenseFile,
			String orgPhone, String regAddress, String mobilePhone,
			String orgBranch, String updateUserName,
			Long updateUserId, String memo, String merchantNcode) {
		this.orgId = orgId;
		this.orgCode = orgCode;
		this.orgName = orgName;
		this.shortName = shortName;
		this.engName = engName;
		this.regCountry = regCountry;
		this.locCountry = locCountry;
		this.corporation = corporation;
		this.zip = zip;
		this.address = address;
		this.fax = fax;
		this.linkman = linkman;
		this.phone = phone;
		this.email = email;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.orgType = orgType;
		this.customerCode = customerCode;
		this.title = title;
		this.status = status;
		this.dutyScope = dutyScope;
		this.lawman = lawman;
		this.lawmanPasstype = lawmanPasstype;
		this.lawmanPasscode = lawmanPasscode;
		this.auditUser = auditUser;
		this.approvaledStatus = approvaledStatus;
		this.approvaledNotes = approvaledNotes;
		this.approvaledDate = approvaledDate;
		this.dutyLicenseFile = dutyLicenseFile;
		this.orgLicenseFile = orgLicenseFile;
		this.taxyRegLicenseFile = taxyRegLicenseFile;
		this.lawManLicenseFile = lawManLicenseFile;
		this.orgPhone = orgPhone;
		this.regAddress = regAddress;
		this.mobilePhone = mobilePhone;
		this.orgBranch = orgBranch;
		this.updateUserName = updateUserName;
		this.updateUserId = updateUserId;
		this.memo = memo;
		this.merchantNcode = merchantNcode;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getEngName() {
		return engName;
	}

	public void setEngName(String engName) {
		this.engName = engName;
	}

	public String getRegCountry() {
		return regCountry;
	}

	public void setRegCountry(String regCountry) {
		this.regCountry = regCountry;
	}

	public String getLocCountry() {
		return locCountry;
	}

	public void setLocCountry(String locCountry) {
		this.locCountry = locCountry;
	}

	public String getCorporation() {
		return corporation;
	}

	public void setCorporation(String corporation) {
		this.corporation = corporation;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDutyScope() {
		return dutyScope;
	}

	public void setDutyScope(String dutyScope) {
		this.dutyScope = dutyScope;
	}

	public String getLawman() {
		return lawman;
	}

	public void setLawman(String lawman) {
		this.lawman = lawman;
	}

	public String getLawmanPasstype() {
		return lawmanPasstype;
	}

	public void setLawmanPasstype(String lawmanPasstype) {
		this.lawmanPasstype = lawmanPasstype;
	}

	public String getLawmanPasscode() {
		return lawmanPasscode;
	}

	public void setLawmanPasscode(String lawmanPasscode) {
		this.lawmanPasscode = lawmanPasscode;
	}

	public String getAuditUser() {
		return auditUser;
	}

	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}

	public String getApprovaledStatus() {
		return approvaledStatus;
	}

	public void setApprovaledStatus(String approvaledStatus) {
		this.approvaledStatus = approvaledStatus;
	}

	public String getApprovaledNotes() {
		return approvaledNotes;
	}

	public void setApprovaledNotes(String approvaledNotes) {
		this.approvaledNotes = approvaledNotes;
	}

	public Date getApprovaledDate() {
		return approvaledDate;
	}

	public void setApprovaledDate(Date approvaledDate) {
		this.approvaledDate = approvaledDate;
	}

	public String getDutyLicenseFile() {
		return dutyLicenseFile;
	}

	public void setDutyLicenseFile(String dutyLicenseFile) {
		this.dutyLicenseFile = dutyLicenseFile;
	}

	public String getOrgLicenseFile() {
		return orgLicenseFile;
	}

	public void setOrgLicenseFile(String orgLicenseFile) {
		this.orgLicenseFile = orgLicenseFile;
	}

	public String getTaxyRegLicenseFile() {
		return taxyRegLicenseFile;
	}

	public void setTaxyRegLicenseFile(String taxyRegLicenseFile) {
		this.taxyRegLicenseFile = taxyRegLicenseFile;
	}

	public String getLawManLicenseFile() {
		return lawManLicenseFile;
	}

	public void setLawManLicenseFile(String lawManLicenseFile) {
		this.lawManLicenseFile = lawManLicenseFile;
	}





	public String getRegAddress() {
		return regAddress;
	}

	public void setRegAddress(String regAddress) {
		this.regAddress = regAddress;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}


	public String getOrgPhone() {
		return orgPhone;
	}

	public void setOrgPhone(String orgPhone) {
		this.orgPhone = orgPhone;
	}

	public String getOrgBranch() {
		return orgBranch;
	}

	public void setOrgBranch(String orgBranch) {
		this.orgBranch = orgBranch;
	}

	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	public Long getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(Long updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getMerchantNcode() {
		return merchantNcode;
	}

	public void setMerchantNcode(String merchantNcode) {
		this.merchantNcode = merchantNcode;
	}
	
}