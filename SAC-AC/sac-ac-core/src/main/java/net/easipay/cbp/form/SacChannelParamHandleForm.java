/**
 * 
 */
package net.easipay.cbp.form;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

public class SacChannelParamHandleForm implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = -1127272793721496745L;
    @NotBlank(message = "渠道节点代码不能为空")
    private String chnCode;
    
    @NotBlank(message = "清算行节点代码不能为空")
    private String bankNodeCode;
    
    @NotBlank(message = "渠道类型不能为空")
    @Pattern(regexp = "^1|2|3|4|5|6$", message = "渠道类型必须为1 B2B支付 2 B2C支付 3其他  4代收付 5 购付汇  6 外汇通 ")
    private String chnType;
    
    @NotBlank(message = "渠道名称不能为空")
    private String chnName;

    @NotBlank(message = "清算行名称不能为空")
    private String sacBankName;

    @NotBlank(message = "开户名称不能为空")
    private String accountName;

    @NotBlank(message = "银行帐号不能为空")
    private String bankAcc;

    @NotBlank(message = "联行号不能为空")
    private String craccBankCode;

    @NotBlank(message = "币种不能为空")
    private String currencyType;

    @NotBlank(message = "清算周期不能为空")
    private String sacPeriod;

    @NotBlank(message = "成本费率不能为空")
    private String costRate;

    private String costComWay;

    private String costSacWay;

    @NotBlank(message = "有效标志不能为空")
    @Pattern(regexp = "^0|1$", message = "有效标志必须为1：有效 0：无效")
    private String isValidFlag;

    private String memo;

    public String getBankNodeCode()
    {
	return bankNodeCode;
    }

    public void setBankNodeCode(String bankNodeCode)
    {
	this.bankNodeCode = bankNodeCode;
    }

    public String getChnType()
    {
	return chnType;
    }

    public void setChnType(String chnType)
    {
	this.chnType = chnType;
    }

    public String getChnName()
    {
	return chnName;
    }

    public void setChnName(String chnName)
    {
	this.chnName = chnName;
    }

    public String getSacBankName()
    {
	return sacBankName;
    }

    public void setSacBankName(String sacBankName)
    {
	this.sacBankName = sacBankName;
    }

    public String getAccountName()
    {
	return accountName;
    }

    public void setAccountName(String accountName)
    {
	this.accountName = accountName;
    }

    public String getBankAcc()
    {
	return bankAcc;
    }

    public void setBankAcc(String bankAcc)
    {
	this.bankAcc = bankAcc;
    }

    public String getCraccBankCode()
    {
	return craccBankCode;
    }

    public void setCraccBankCode(String craccBankCode)
    {
	this.craccBankCode = craccBankCode;
    }

    public String getCurrencyType()
    {
	return currencyType;
    }

    public void setCurrencyType(String currencyType)
    {
	this.currencyType = currencyType;
    }

    public String getSacPeriod()
    {
	return sacPeriod;
    }

    public void setSacPeriod(String sacPeriod)
    {
	this.sacPeriod = sacPeriod;
    }

    public String getCostRate()
    {
	return costRate;
    }

    public void setCostRate(String costRate)
    {
	this.costRate = costRate;
    }

    public String getCostComWay()
    {
	return costComWay;
    }

    public void setCostComWay(String costComWay)
    {
	this.costComWay = costComWay;
    }

    public String getCostSacWay()
    {
	return costSacWay;
    }

    public void setCostSacWay(String costSacWay)
    {
	this.costSacWay = costSacWay;
    }

    public String getIsValidFlag() {
		return isValidFlag;
	}

	public void setIsValidFlag(String isValidFlag) {
		this.isValidFlag = isValidFlag;
	}

	public String getMemo()
    {
	return memo;
    }

    public void setMemo(String memo)
    {
	this.memo = memo;
    }

    public String getChnCode()
    {
	return chnCode;
    }

    public void setChnCode(String chnCode)
    {
	this.chnCode = chnCode;
    }
}
