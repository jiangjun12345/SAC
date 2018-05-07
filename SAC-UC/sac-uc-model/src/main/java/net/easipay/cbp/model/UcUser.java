package net.easipay.cbp.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Entity
@Table(name = "UC_USER", schema = "CBP_UC")
public class UcUser implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private Long id;
	@Size(max=32,message="{filed.length.error}")
	private String personName;
	private String personState;
	private String loginName;
	private String identifyType;
	@Size(max=20,message="{filed.length.error}")
	private String identifyCode;
	private String identifyVerifyFlag;
	private String customerCode;
	@Pattern(regexp = "^[1]+\\d{10}", message="{filed.phone.error}")
	private String mobile;
	private String mobileVerifyFlag;
	private String countryCode;
	@Pattern(regexp = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$", message="{filed.email.error}")
	private String email;
	private String emailVerifyFlag;
	private String address;
	private String queryPassword;
	private String payPassword;
	private String sex;
	private String createUserId;
	private Date createTime;
	private String updateUserId;
	private Date updateTime;
	private Date lastLoginTime;
	private String certifyChannel;
	private String certificationId;
	private String memo;
	private String identifyPath;
	private String grantCode;
	
	public UcUser() {
	}

	public UcUser(Long id, String personState, String identifyType,
			String identifyCode, String identifyVerifyFlag, String mobile,
			String mobileVerifyFlag, String email, String emailVerifyFlag,String grantCode) {
		this.id = id;
		this.personState = personState;
		this.identifyType = identifyType;
		this.identifyCode = identifyCode;
		this.identifyVerifyFlag = identifyVerifyFlag;
		this.mobile = mobile;
		this.mobileVerifyFlag = mobileVerifyFlag;
		this.email = email;
		this.emailVerifyFlag = emailVerifyFlag;
		this.grantCode = grantCode;
	}

	public UcUser(Long id, String personName, String personState,
			String loginName, String identifyType, String identifyCode,
			String identifyVerifyFlag, String customerCode, String mobile,
			String mobileVerifyFlag, String countryCode, String email,
			String emailVerifyFlag, String address, String queryPassword,
			String payPassword, String sex, String createUserId,
			Date createTime, String updateUserId, Date updateTime,
			Date lastLoginTime, String certifyChannel, String certificationId,
			String memo, String identifyPath,String grantCode) {
		this.id = id;
		this.personName = personName;
		this.personState = personState;
		this.loginName = loginName;
		this.identifyType = identifyType;
		this.identifyCode = identifyCode;
		this.identifyVerifyFlag = identifyVerifyFlag;
		this.customerCode = customerCode;
		this.mobile = mobile;
		this.mobileVerifyFlag = mobileVerifyFlag;
		this.countryCode = countryCode;
		this.email = email;
		this.emailVerifyFlag = emailVerifyFlag;
		this.address = address;
		this.queryPassword = queryPassword;
		this.payPassword = payPassword;
		this.sex = sex;
		this.createUserId = createUserId;
		this.createTime = createTime;
		this.updateUserId = updateUserId;
		this.updateTime = updateTime;
		this.lastLoginTime = lastLoginTime;
		this.certifyChannel = certifyChannel;
		this.certificationId = certificationId;
		this.memo = memo;
		this.identifyPath = identifyPath;
		this.grantCode = grantCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getPersonState() {
		return personState;
	}

	public void setPersonState(String personState) {
		this.personState = personState;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getIdentifyType() {
		return identifyType;
	}

	public void setIdentifyType(String identifyType) {
		this.identifyType = identifyType;
	}

	public String getIdentifyCode() {
		return identifyCode;
	}

	public void setIdentifyCode(String identifyCode) {
		this.identifyCode = identifyCode;
	}

	public String getIdentifyVerifyFlag() {
		return identifyVerifyFlag;
	}

	public void setIdentifyVerifyFlag(String identifyVerifyFlag) {
		this.identifyVerifyFlag = identifyVerifyFlag;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMobileVerifyFlag() {
		return mobileVerifyFlag;
	}

	public void setMobileVerifyFlag(String mobileVerifyFlag) {
		this.mobileVerifyFlag = mobileVerifyFlag;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmailVerifyFlag() {
		return emailVerifyFlag;
	}

	public void setEmailVerifyFlag(String emailVerifyFlag) {
		this.emailVerifyFlag = emailVerifyFlag;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getQueryPassword() {
		return queryPassword;
	}

	public void setQueryPassword(String queryPassword) {
		this.queryPassword = queryPassword;
	}

	public String getPayPassword() {
		return payPassword;
	}

	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getCertifyChannel() {
		return certifyChannel;
	}

	public void setCertifyChannel(String certifyChannel) {
		this.certifyChannel = certifyChannel;
	}

	public String getCertificationId() {
		return certificationId;
	}

	public void setCertificationId(String certificationId) {
		this.certificationId = certificationId;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getIdentifyPath() {
		return identifyPath;
	}

	public void setIdentifyPath(String identifyPath) {
		this.identifyPath = identifyPath;
	}

	public String getGrantCode() {
		return grantCode;
	}

	public void setGrantCode(String grantCode) {
		this.grantCode = grantCode;
	}
	
	
}