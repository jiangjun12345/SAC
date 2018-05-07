/**
 * 
 */
package net.easipay.cbp.form;

import java.io.Serializable;

/**
 * @author jjiang
 * @date 2017-03-20
 */

public class FinBalanceQueryForm implements Serializable
{


    /**
	 * 
	 */
	private static final long serialVersionUID = -5152734850850046798L;

	private String cusCode;

    private String currency;

    private String bussType;
    
    private String balType;


	public String getCusCode() {
		return cusCode;
	}

	public void setCusCode(String cusCode) {
		this.cusCode = cusCode;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getBussType() {
		return bussType;
	}

	public void setBussType(String bussType) {
		this.bussType = bussType;
	}

	public String getBalType() {
		return balType;
	}

	public void setBalType(String balType) {
		this.balType = balType;
	}
    
    

}
