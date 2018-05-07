package net.easipay.cbp.model.form;


public class NotifyOflCommandResultForm implements java.io.Serializable {
    


	/**
	 * 
	 */
	private static final long serialVersionUID = 2471338372888739553L;

	private String msgSndrSysNdCd;//发送方系统节点代码
	
    private String trxSerialNo;//划款指令流水号
    
    private String pyerBnkCd;//付款银行节点代码
    
    private String pyerBnkNm;//付款银行名称
    
    private String pyeeIdNo;//企业代码
    
    private String payAmount;//金额
    
    private String pyeeAccId;//收款方银行账号
    
    private String pyeeAccNm;//收款方账户名称
    
    private String pyeeBnkCd;//收款银行节点代码
    
    private String pyeeBnkNm;//收款方银行名称
    
    private String bankSerialNo;//银行交易流水号
    
    private String trxBizCd;//业务类型
    
    private String pyerBankAreaCode;//付款方银行分行

	public String getTrxSerialNo() {
		return trxSerialNo;
	}

	public void setTrxSerialNo(String trxSerialNo) {
		this.trxSerialNo = trxSerialNo;
	}

	public String getPyerBnkCd() {
		return pyerBnkCd;
	}

	public void setPyerBnkCd(String pyerBnkCd) {
		this.pyerBnkCd = pyerBnkCd;
	}

	public String getPyerBnkNm() {
		return pyerBnkNm;
	}

	public void setPyerBnkNm(String pyerBnkNm) {
		this.pyerBnkNm = pyerBnkNm;
	}

	public String getPyeeIdNo() {
		return pyeeIdNo;
	}

	public void setPyeeIdNo(String pyeeIdNo) {
		this.pyeeIdNo = pyeeIdNo;
	}

	public String getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}

	public String getPyeeAccId() {
		return pyeeAccId;
	}

	public void setPyeeAccId(String pyeeAccId) {
		this.pyeeAccId = pyeeAccId;
	}

	public String getPyeeAccNm() {
		return pyeeAccNm;
	}

	public void setPyeeAccNm(String pyeeAccNm) {
		this.pyeeAccNm = pyeeAccNm;
	}

	public String getPyeeBnkCd() {
		return pyeeBnkCd;
	}

	public void setPyeeBnkCd(String pyeeBnkCd) {
		this.pyeeBnkCd = pyeeBnkCd;
	}

	public String getPyeeBnkNm() {
		return pyeeBnkNm;
	}

	public void setPyeeBnkNm(String pyeeBnkNm) {
		this.pyeeBnkNm = pyeeBnkNm;
	}

	public String getBankSerialNo() {
		return bankSerialNo;
	}

	public void setBankSerialNo(String bankSerialNo) {
		this.bankSerialNo = bankSerialNo;
	}

	public String getMsgSndrSysNdCd() {
		return msgSndrSysNdCd;
	}

	public void setMsgSndrSysNdCd(String msgSndrSysNdCd) {
		this.msgSndrSysNdCd = msgSndrSysNdCd;
	}

	public String getTrxBizCd() {
		return trxBizCd;
	}

	public void setTrxBizCd(String trxBizCd) {
		this.trxBizCd = trxBizCd;
	}

	public String getPyerBankAreaCode() {
		return pyerBankAreaCode;
	}

	public void setPyerBankAreaCode(String pyerBankAreaCode) {
		this.pyerBankAreaCode = pyerBankAreaCode;
	}
	
	
	
	
    
  
    
	
    
}
