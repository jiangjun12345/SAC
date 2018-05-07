/**
 * 
 */
package net.easipay.cbp.model.form;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

public class SacCusParameterHandleForm implements Serializable
{
    private static final long serialVersionUID = 1259330365051791055L;
    @NotBlank(message = "客户名称不能为空")
    private String cusName;
    
    @NotBlank(message = "客户类型不能为空")
    @Pattern(regexp = "^1|2$", message = "客户类型必须为1 企业，2 个人")
    private String cusType;

    private String merchantNcode;

    private String refundFlag;

    private String sacBankAcc;

    private String accName;

    private String depositBank;

    private String craccBankCode;

    private String feeRate;

    private String feeComWay;

    private String feeSacWay;

    private String sacType;

    private String sacPeriod;

    private String intervalNumber;

    @NotBlank(message = "结算币种不能为空")
    private String sacCurrency;

    private String sacStartAmount;

    private String trxUpLim;

    @NotBlank(message = "有效标志不能为空")
    @Pattern(regexp = "^0|1$", message = "有效标志必须为1：有效 0：无效")
    private String isVaildFlag;
    
    private String cusFeeFlag;
    
    @NotBlank(message = "证件号不能为空")
    private String orgCardId;
    
    @NotBlank(message = "业务类型不能为空")
    @Pattern(regexp = "^00|20|21|22|30|40|50|60|70|71|80|90$", message = "业务类型必须为00默认,20航付通,21航付通专项 EIR放箱,22航付通专项 放箱打单费,30跨境B2C,40代收付,50外汇通,60清算,70速结汇,71购付汇,80关税代付,90行邮税")
    private String bussType;

    private String memo;

    public String getMerchantNcode()
    {
	return merchantNcode;
    }

    public void setMerchantNcode(String merchantNcode)
    {
	this.merchantNcode = merchantNcode;
    }
    
    public String getCusType()
    {
        return cusType;
    }

    public void setCusType(String cusType)
    {
        this.cusType = cusType;
    }

    public String getCusName()
    {
	return cusName;
    }

    public void setCusName(String cusName)
    {
	this.cusName = cusName;
    }

    public String getRefundFlag()
    {
	return refundFlag;
    }

    public void setRefundFlag(String refundFlag)
    {
	this.refundFlag = refundFlag;
    }

    public String getSacBankAcc()
    {
	return sacBankAcc;
    }

    public void setSacBankAcc(String sacBankAcc)
    {
	this.sacBankAcc = sacBankAcc;
    }

    public String getAccName()
    {
	return accName;
    }

    public void setAccName(String accName)
    {
	this.accName = accName;
    }

    public String getDepositBank()
    {
	return depositBank;
    }

    public void setDepositBank(String depositBank)
    {
	this.depositBank = depositBank;
    }

    public String getCraccBankCode()
    {
	return craccBankCode;
    }

    public void setCraccBankCode(String craccBankCode)
    {
	this.craccBankCode = craccBankCode;
    }

    public String getFeeRate()
    {
	return feeRate;
    }

    public void setFeeRate(String feeRate)
    {
	this.feeRate = feeRate;
    }

    public String getFeeComWay()
    {
	return feeComWay;
    }

    public void setFeeComWay(String feeComWay)
    {
	this.feeComWay = feeComWay;
    }

    public String getFeeSacWay()
    {
	return feeSacWay;
    }

    public void setFeeSacWay(String feeSacWay)
    {
	this.feeSacWay = feeSacWay;
    }

    public String getSacType()
    {
	return sacType;
    }

    public void setSacType(String sacType)
    {
	this.sacType = sacType;
    }

    public String getSacPeriod()
    {
	return sacPeriod;
    }

    public void setSacPeriod(String sacPeriod)
    {
	this.sacPeriod = sacPeriod;
    }

    public String getSacCurrency()
    {
	return sacCurrency;
    }

    public void setSacCurrency(String sacCurrency)
    {
	this.sacCurrency = sacCurrency;
    }

    public String getSacStartAmount()
    {
	return sacStartAmount;
    }

    public void setSacStartAmount(String sacStartAmount)
    {
	this.sacStartAmount = sacStartAmount;
    }

    public String getTrxUpLim()
    {
	return trxUpLim;
    }

    public void setTrxUpLim(String trxUpLim)
    {
	this.trxUpLim = trxUpLim;
    }

    public String getIsVaildFlag()
    {
	return isVaildFlag;
    }

    public void setIsVaildFlag(String isVaildFlag)
    {
	this.isVaildFlag = isVaildFlag;
    }

    public String getMemo()
    {
	return memo;
    }

    public void setMemo(String memo)
    {
	this.memo = memo;
    }

    public String getIntervalNumber()
    {
	return intervalNumber;
    }

    public void setIntervalNumber(String intervalNumber)
    {
	this.intervalNumber = intervalNumber;
    }

    public String getCusFeeFlag()
    {
        return cusFeeFlag;
    }

    public void setCusFeeFlag(String cusFeeFlag)
    {
        this.cusFeeFlag = cusFeeFlag;
    }

    public String getOrgCardId()
    {
        return orgCardId;
    }

    public void setOrgCardId(String orgCardId)
    {
        this.orgCardId = orgCardId;
    }

    public String getBussType()
    {
        return bussType;
    }

    public void setBussType(String bussType)
    {
        this.bussType = bussType;
    }
}
