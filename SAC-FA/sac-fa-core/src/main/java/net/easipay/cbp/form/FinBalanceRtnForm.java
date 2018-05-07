/**
 * 
 */
package net.easipay.cbp.form;

import java.io.Serializable;

/**
 * @author jjiang
 * @date 2017-03-20
 */

public class FinBalanceRtnForm implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9086162178412273920L;

	private String bussType;
    
    private String balance;
    
    private String platAccount;

	public String getBussType() {
		return bussType;
	}

	public void setBussType(String bussType) {
		this.bussType = bussType;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getPlatAccount() {
		return platAccount;
	}

	public void setPlatAccount(String platAccount) {
		this.platAccount = platAccount;
	}
	
    

}
