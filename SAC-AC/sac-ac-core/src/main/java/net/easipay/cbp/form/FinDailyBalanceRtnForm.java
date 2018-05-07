/**
 * 
 */
package net.easipay.cbp.form;

import java.io.Serializable;

/**
 * @author jjiang
 * @date 2017-08-20
 */

public class FinDailyBalanceRtnForm implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String orgCode;
    
    private String accountDate;
    
    private String trxCount;
    
    private String debitBal;
    
    private String creditBal;
    
    private String openBal;
    
    private String closeBal;

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getAccountDate() {
		return accountDate;
	}

	public void setAccountDate(String accountDate) {
		this.accountDate = accountDate;
	}

	public String getTrxCount() {
		return trxCount;
	}

	public void setTrxCount(String trxCount) {
		this.trxCount = trxCount;
	}

	public String getDebitBal() {
		return debitBal;
	}

	public void setDebitBal(String debitBal) {
		this.debitBal = debitBal;
	}

	public String getCreditBal() {
		return creditBal;
	}

	public void setCreditBal(String creditBal) {
		this.creditBal = creditBal;
	}

	public String getOpenBal() {
		return openBal;
	}

	public void setOpenBal(String openBal) {
		this.openBal = openBal;
	}

	public String getCloseBal() {
		return closeBal;
	}

	public void setCloseBal(String closeBal) {
		this.closeBal = closeBal;
	}

    

}
