package net.easipay.cbp.model;

public class Person implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
    
    private String personName;
    
    private String personState;
    
    private String identifyType;
    
    private String identifyCode;
    
    private String identifyVerifyFlag;
    
    private String customerCode;
    
    private String mobile;
    
    private String mobileVerifyFlag;
    
    private String email;
    
    private String emailVerifyFlag;
    
	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPersonState() {
		return personState;
	}

	public void setPersonState(String personState) {
		this.personState = personState;
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
    
}
