package net.easipay.cbp.model.form;

import java.math.BigDecimal;
import java.util.Date;

/**
 *线下预存交易表(接口)
* ClassName: SacDepositDetail <br/> 
* Function: TODO ADD FUNCTION. <br/> 
* Reason: TODO ADD REASON(可选). <br/> 
* date: 2016年2月26日 下午4:42:28 <br/> 
* 
* @author Administrator 
* @version  
* @since JDK 1.6
 */

public class PrestoreDetailForm implements java.io.Serializable {
    
	private static final long serialVersionUID = -7515696829667608667L;

    private String payCurrency;// 币种
    
    private BigDecimal payAmount;// 金额
    
    private String dbtCode;// 付款方组织机构代码，预留
    
    private String draccBankName;// 付款方银行名称
    
    private String draccNo;// 付款方账号
    
    private String draccName;// 付款账户名称
    
    private String bankSerialNo;// 银行交易流水号
    
    private String bankTrxDate;// 银行交易日期，即到账日期
    
    private String memo;
    
    private String applyCode;
    
    
    
    
    public String getPayCurrency() {
		return payCurrency;
	}




	public void setPayCurrency(String payCurrency) {
		this.payCurrency = payCurrency;
	}




	public BigDecimal getPayAmount() {
		return payAmount;
	}




	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}




	public String getDbtCode() {
		return dbtCode;
	}




	public void setDbtCode(String dbtCode) {
		this.dbtCode = dbtCode;
	}




	public String getDraccBankName() {
		return draccBankName;
	}




	public void setDraccBankName(String draccBankName) {
		this.draccBankName = draccBankName;
	}




	public String getDraccNo() {
		return draccNo;
	}




	public void setDraccNo(String draccNo) {
		this.draccNo = draccNo;
	}




	public String getDraccName() {
		return draccName;
	}




	public void setDraccName(String draccName) {
		this.draccName = draccName;
	}




	public String getBankSerialNo() {
		return bankSerialNo;
	}




	public void setBankSerialNo(String bankSerialNo) {
		this.bankSerialNo = bankSerialNo;
	}





	public String getBankTrxDate() {
		return bankTrxDate;
	}




	public void setBankTrxDate(String bankTrxDate) {
		this.bankTrxDate = bankTrxDate;
	}




	public String getMemo() {
		return memo;
	}




	public void setMemo(String memo) {
		this.memo = memo;
	}




	public String getApplyCode() {
		return applyCode;
	}




	public void setApplyCode(String applyCode) {
		this.applyCode = applyCode;
	}
	
	
    
}
