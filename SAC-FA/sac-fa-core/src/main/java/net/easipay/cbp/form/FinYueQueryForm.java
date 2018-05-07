/**
 * 
 */
package net.easipay.cbp.form;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author mchen
 * @date 2015-12-11
 */

public class FinYueQueryForm implements Serializable
{
    private static final long serialVersionUID = 5183565587873823641L;

    @NotBlank(message = "会计账目号不能为空")
    private String accountId;

    @NotBlank(message = "账户类型不能为空")
    private String accountType;

    @NotBlank(message = "客户不能为空")
    private String cusNo;

    @NotBlank(message = "币种不能为空")
    private String currencyType;

    @NotBlank(message = "业务类型不能为空")
    private String bussType;

    @NotBlank(message = "子账户类型不能为空")
    private String subAccountType;

    public String toCodeId()
    {
	StringBuffer codeId = new StringBuffer();
	codeId.append(this.accountId).append(this.accountType).append(this.cusNo).append(this.currencyType).append(this.bussType).append(this.subAccountType);
	return codeId.toString();
    }

    public String getAccountId()
    {
	return accountId;
    }

    public void setAccountId(String accountId)
    {
	this.accountId = accountId;
    }

    public String getAccountType()
    {
	return accountType;
    }

    public void setAccountType(String accountType)
    {
	this.accountType = accountType;
    }

    public String getCusNo()
    {
	return cusNo;
    }

    public void setCusNo(String cusNo)
    {
	this.cusNo = cusNo;
    }

    public String getCurrencyType()
    {
	return currencyType;
    }

    public void setCurrencyType(String currencyType)
    {
	this.currencyType = currencyType;
    }

    public String getBussType()
    {
	return bussType;
    }

    public void setBussType(String bussType)
    {
	this.bussType = bussType;
    }

    public String getSubAccountType()
    {
	return subAccountType;
    }

    public void setSubAccountType(String subAccountType)
    {
	this.subAccountType = subAccountType;
    }

}
