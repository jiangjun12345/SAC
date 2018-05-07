package net.easipay.cbp.model;

import java.math.BigDecimal;
import java.util.Date;

public class SacChannelParam implements java.io.Serializable
{
    private static final long serialVersionUID = 79616203991052226L;
    private Long id; // 主键
    private String chnNo; // 渠道号
    private String chnName; // 渠道名称
    private String bankNodeCode; // 清算行节点代码
    private String chnCode; // 渠道节点代码
    private String chnType; // 渠道类型
    private String sacBankName; // 清算行名称
    private String accountName; // 开户名称
    private String bankAcc; // 银行帐号
    private String craccBankCode; // 联行号
    private String currencyType; // 币种
    private Short sacPeriod; // 清算周期
    private BigDecimal costRate; // 成本费率
    private String costComWay; // 成本计算方式
    private String costSacWay; // 成本结算方式
    private Date createTime; // 创建时间
    private Date updateTime; // 更新时间
    private String isValidFlag; // 有效标志
    private String memo; // 备注

    public Long getId()
    {
	return id;
    }

    public void setId(Long id)
    {
	this.id = id;
    }

    public String getChnNo()
    {
	return chnNo;
    }

    public void setChnNo(String chnNo)
    {
	this.chnNo = chnNo;
    }

    public String getChnName()
    {
	return chnName;
    }

    public void setChnName(String chnName)
    {
	this.chnName = chnName;
    }

    public String getBankNodeCode()
    {
	return bankNodeCode;
    }

    public void setBankNodeCode(String bankNodeCode)
    {
	this.bankNodeCode = bankNodeCode;
    }

    public String getChnCode()
    {
	return chnCode;
    }

    public void setChnCode(String chnCode)
    {
	this.chnCode = chnCode;
    }

    public String getChnType()
    {
	return chnType;
    }

    public void setChnType(String chnType)
    {
	this.chnType = chnType;
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

    public Short getSacPeriod()
    {
	return sacPeriod;
    }

    public void setSacPeriod(Short sacPeriod)
    {
	this.sacPeriod = sacPeriod;
    }

    public BigDecimal getCostRate()
    {
	return costRate;
    }

    public void setCostRate(BigDecimal costRate)
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

    public Date getCreateTime()
    {
	return createTime;
    }

    public void setCreateTime(Date createTime)
    {
	this.createTime = createTime;
    }

    public Date getUpdateTime()
    {
	return updateTime;
    }

    public void setUpdateTime(Date updateTime)
    {
	this.updateTime = updateTime;
    }

    public String getIsValidFlag()
    {
	return isValidFlag;
    }

    public void setIsValidFlag(String isValidFlag)
    {
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

}
