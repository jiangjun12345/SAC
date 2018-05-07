package net.easipay.cbp.model;

import java.math.BigDecimal;
import java.util.Date;

public class SacTrxDetail implements java.io.Serializable
{
    private static final long serialVersionUID = -4379686424868310191L;
    private Long id; // 主键
    private String chnNo; // 渠道节点代码
    private String cusBillNo; // 客户订单编号
    private String platBillNo; // 平台订单编号
    private String trxBatchNo; // 交易批次号
    private String trxSerialNo; // 交易流水号
    private String otrxSerialNo; // 原交易流水号
    private String receiverPlatAcc; // 收款方平台账号
    private String paymentPlatAcc; // 付款方平台账号
    private String bussType; // 业务类型
    private String trxType; // 交易类型
    private BigDecimal trxAmount; // 交易金额
    private String trxCurrencyType; // 交易币种
    private String sacCurrency; // 购结汇币种
    private BigDecimal sacAmount; // 购结汇金额
    private BigDecimal exRate; // 汇率
    private String issuingBank; // 发卡行
    private String payconType; // 支付渠道类型
    private String payWay; // 支付方式
    private String draccNodeCode; // 付款方银行节点代码
    private String craccNodeCode; // 收款方银行节点代码
    private String craccAreaCode; // 收款方地区码
    private String draccAreaCode; // 付款方地区码
    private String trxState; // 交易状态
    private String reversalStatus; // 冲正状态 N：未冲正；S：已冲正
    private String chaConStatus;
    private Date trxTime;
    private Date createTime; // 创建时间
    private Date updateTime; // 更新时间
    private String chnBatchNo; // 应收清分批次号
    private String cusBatchNo; // 应付清分批次号
    private BigDecimal channelCost;
    private BigDecimal cusCharge;
    private Date trxSuccTime;
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

    public String getReversalStatus()
    {
	return reversalStatus;
    }

    public void setReversalStatus(String reversalStatus)
    {
	this.reversalStatus = reversalStatus;
    }

    public void setChnNo(String chnNo)
    {
	this.chnNo = chnNo;
    }

    public String getCusBillNo()
    {
	return cusBillNo;
    }

    public void setCusBillNo(String cusBillNo)
    {
	this.cusBillNo = cusBillNo;
    }

    public String getPlatBillNo()
    {
	return platBillNo;
    }

    public void setPlatBillNo(String platBillNo)
    {
	this.platBillNo = platBillNo;
    }

    public String getTrxBatchNo()
    {
	return trxBatchNo;
    }

    public void setTrxBatchNo(String trxBatchNo)
    {
	this.trxBatchNo = trxBatchNo;
    }

    public String getTrxSerialNo()
    {
	return trxSerialNo;
    }

    public void setTrxSerialNo(String trxSerialNo)
    {
	this.trxSerialNo = trxSerialNo;
    }

    public String getOtrxSerialNo()
    {
	return otrxSerialNo;
    }

    public void setOtrxSerialNo(String otrxSerialNo)
    {
	this.otrxSerialNo = otrxSerialNo;
    }

    public String getReceiverPlatAcc()
    {
	return receiverPlatAcc;
    }

    public void setReceiverPlatAcc(String receiverPlatAcc)
    {
	this.receiverPlatAcc = receiverPlatAcc;
    }

    public String getPaymentPlatAcc()
    {
	return paymentPlatAcc;
    }

    public void setPaymentPlatAcc(String paymentPlatAcc)
    {
	this.paymentPlatAcc = paymentPlatAcc;
    }

    public String getBussType()
    {
	return bussType;
    }

    public void setBussType(String bussType)
    {
	this.bussType = bussType;
    }

    public String getTrxType()
    {
	return trxType;
    }

    public void setTrxType(String trxType)
    {
	this.trxType = trxType;
    }

    public BigDecimal getTrxAmount()
    {
	return trxAmount;
    }

    public void setTrxAmount(BigDecimal trxAmount)
    {
	this.trxAmount = trxAmount;
    }

    public String getTrxCurrencyType()
    {
	return trxCurrencyType;
    }

    public void setTrxCurrencyType(String trxCurrencyType)
    {
	this.trxCurrencyType = trxCurrencyType;
    }

    public String getSacCurrency()
    {
	return sacCurrency;
    }

    public void setSacCurrency(String sacCurrency)
    {
	this.sacCurrency = sacCurrency;
    }

    public BigDecimal getSacAmount()
    {
	return sacAmount;
    }

    public void setSacAmount(BigDecimal sacAmount)
    {
	this.sacAmount = sacAmount;
    }

    public BigDecimal getExRate()
    {
	return exRate;
    }

    public void setExRate(BigDecimal exRate)
    {
	this.exRate = exRate;
    }

    public String getIssuingBank()
    {
	return issuingBank;
    }

    public void setIssuingBank(String issuingBank)
    {
	this.issuingBank = issuingBank;
    }

    public String getPayconType()
    {
	return payconType;
    }

    public void setPayconType(String payconType)
    {
	this.payconType = payconType;
    }

    public String getPayWay()
    {
	return payWay;
    }

    public void setPayWay(String payWay)
    {
	this.payWay = payWay;
    }

    public String getDraccNodeCode()
    {
	return draccNodeCode;
    }

    public void setDraccNodeCode(String draccNodeCode)
    {
	this.draccNodeCode = draccNodeCode;
    }

    public String getCraccNodeCode()
    {
	return craccNodeCode;
    }

    public void setCraccNodeCode(String craccNodeCode)
    {
	this.craccNodeCode = craccNodeCode;
    }

    public String getTrxState()
    {
	return trxState;
    }

    public void setTrxState(String trxState)
    {
	this.trxState = trxState;
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

    public String getChnBatchNo()
    {
	return chnBatchNo;
    }

    public void setChnBatchNo(String chnBatchNo)
    {
	this.chnBatchNo = chnBatchNo;
    }

    public String getCusBatchNo()
    {
	return cusBatchNo;
    }

    public void setCusBatchNo(String cusBatchNo)
    {
	this.cusBatchNo = cusBatchNo;
    }

    public String getMemo()
    {
	return memo;
    }

    public void setMemo(String memo)
    {
	this.memo = memo;
    }

    public String getChaConStatus()
    {
	return chaConStatus;
    }

    public void setChaConStatus(String chaConStatus)
    {
	this.chaConStatus = chaConStatus;
    }

    public Date getTrxTime()
    {
	return trxTime;
    }

    public void setTrxTime(Date trxTime)
    {
	this.trxTime = trxTime;
    }

    public BigDecimal getChannelCost()
    {
	return channelCost;
    }

    public void setChannelCost(BigDecimal channelCost)
    {
	this.channelCost = channelCost;
    }

    public BigDecimal getCusCharge()
    {
	return cusCharge;
    }

    public void setCusCharge(BigDecimal cusCharge)
    {
	this.cusCharge = cusCharge;
    }

    public Date getTrxSuccTime()
    {
	return trxSuccTime;
    }

    public void setTrxSuccTime(Date trxSuccTime)
    {
	this.trxSuccTime = trxSuccTime;
    }

	public String getCraccAreaCode() {
		return craccAreaCode;
	}

	public void setCraccAreaCode(String craccAreaCode) {
		this.craccAreaCode = craccAreaCode;
	}

	public String getDraccAreaCode() {
		return draccAreaCode;
	}

	public void setDraccAreaCode(String draccAreaCode) {
		this.draccAreaCode = draccAreaCode;
	}
    
}
