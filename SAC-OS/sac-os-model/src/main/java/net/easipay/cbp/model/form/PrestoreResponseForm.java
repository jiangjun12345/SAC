package net.easipay.cbp.model.form;


public class PrestoreResponseForm implements java.io.Serializable {
    

	/**
	 * 
	 */
	private static final long serialVersionUID = 6644156930649024650L;

	private String oflDepositSerialNo;
    
    private String trxSerialNo;
    
    private String rtnCode;
    
    private String rtnInfo;

	public String getOflDepositSerialNo() {
		return oflDepositSerialNo;
	}

	public void setOflDepositSerialNo(String oflDepositSerialNo) {
		this.oflDepositSerialNo = oflDepositSerialNo;
	}

	public String getTrxSerialNo() {
		return trxSerialNo;
	}

	public void setTrxSerialNo(String trxSerialNo) {
		this.trxSerialNo = trxSerialNo;
	}

	public String getRtnCode() {
		return rtnCode;
	}

	public void setRtnCode(String rtnCode) {
		this.rtnCode = rtnCode;
	}

	public String getRtnInfo() {
		return rtnInfo;
	}

	public void setRtnInfo(String rtnInfo) {
		this.rtnInfo = rtnInfo;
	}
    
    
}
