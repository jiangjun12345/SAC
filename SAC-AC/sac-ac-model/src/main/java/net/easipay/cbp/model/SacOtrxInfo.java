package net.easipay.cbp.model;

import java.math.BigDecimal;
import java.util.Date;

public class SacOtrxInfo implements java.io.Serializable
{
    private static final long serialVersionUID = -2922429917705296479L;
    private Long id; // 交易ID
    private String cusBillNo; // 商户订单号
    private String platBillNo; // 平台订单号
    private String trxSerialNo; // 交易流水号
    private String otrxSerialNo; // 原交易流水号
    private String etrxSerialNo; // 外部交易流水号
    private String trxState; // 交易状态
    private String trxStateDesc;
    private String craccCusCode; // 收款方客户号
    private String craccCusName; // 收款方客户名称
    private String craccCusType; // 收款方客户类型
    private String craccCardId; // 收款方证件号
    private String craccNo; // 收款方帐号
    private String craccName; // 收款账户名称
    private String craccNodeCode; // 收款方银行节点代码
    private String craccBankName; // 收款方银行名称
    private String craccAreaCode; // 收款方地区码
    private String draccCusCode; // 付款方客户号
    private String draccCusName; // 收款方客户名称
    private String draccCusType; // 付款方客户类型
    private String draccCardId; // 付款方证件号
    private String draccNo; // 付款方账号
    private String draccName; // 付款账户名称
    private String draccNodeCode; // 付款方银行节点代码
    private String draccBankName; // 付款方银行名称
    private String draccAreaCode; // 付款方地区码
    private String payCurrency; // 支付币种
    private BigDecimal exRate; // 汇率
    private BigDecimal payAmount; // 支付金额
    private String sacCurrency; // 购结汇币种
    private BigDecimal sacAmount; // 购结汇金额
    private String bussType; // 创建时间
    private String trxType; // 更新时间
    private String payWay; // 业务类型
    private String sacFlag; // 日结标志
    private String innConFlag; // 内部对账标志
    private String innConStatus; // 支付方式
    private String payconType; // 支付渠道类型
    private String chaConFlag; //
    private String chaConStatus; // 内部对账状态
    private String accountStatus;
    private String trxBatchNo;
    private Date trxTime;
    private Date createTime;
    private Date updateTime;
    private String reversalStatus; // 冲正状态 N：未冲正；S：已冲正
    private String memo;
    private String trxErrDealType;
    private BigDecimal taxAmount;// 税金额
    private BigDecimal transportExpenses;// 运费
    private BigDecimal channelCost;
    private BigDecimal cusCharge;
    private Date trxSuccTime;
    private String remittanceBatchId; // 付汇批次号
    public Long getId()
    {
        return id;
    }
    public void setId(Long id)
    {
        this.id = id;
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
    public String getEtrxSerialNo()
    {
        return etrxSerialNo;
    }
    public void setEtrxSerialNo(String etrxSerialNo)
    {
        this.etrxSerialNo = etrxSerialNo;
    }
    public String getTrxState()
    {
        return trxState;
    }
    public void setTrxState(String trxState)
    {
        this.trxState = trxState;
    }
    public String getTrxStateDesc()
    {
        return trxStateDesc;
    }
    public void setTrxStateDesc(String trxStateDesc)
    {
        this.trxStateDesc = trxStateDesc;
    }
    public String getCraccCusCode()
    {
        return craccCusCode;
    }
    public void setCraccCusCode(String craccCusCode)
    {
        this.craccCusCode = craccCusCode;
    }
    public String getCraccCusName()
    {
        return craccCusName;
    }
    public void setCraccCusName(String craccCusName)
    {
        this.craccCusName = craccCusName;
    }
    public String getCraccCusType()
    {
        return craccCusType;
    }
    public void setCraccCusType(String craccCusType)
    {
        this.craccCusType = craccCusType;
    }
    public String getCraccCardId()
    {
        return craccCardId;
    }
    public void setCraccCardId(String craccCardId)
    {
        this.craccCardId = craccCardId;
    }
    public String getCraccNo()
    {
        return craccNo;
    }
    public void setCraccNo(String craccNo)
    {
        this.craccNo = craccNo;
    }
    public String getCraccName()
    {
        return craccName;
    }
    public void setCraccName(String craccName)
    {
        this.craccName = craccName;
    }
    public String getCraccNodeCode()
    {
        return craccNodeCode;
    }
    public void setCraccNodeCode(String craccNodeCode)
    {
        this.craccNodeCode = craccNodeCode;
    }
    public String getCraccBankName()
    {
        return craccBankName;
    }
    public void setCraccBankName(String craccBankName)
    {
        this.craccBankName = craccBankName;
    }
    public String getDraccCusCode()
    {
        return draccCusCode;
    }
    public void setDraccCusCode(String draccCusCode)
    {
        this.draccCusCode = draccCusCode;
    }
    public String getDraccCusName()
    {
        return draccCusName;
    }
    public void setDraccCusName(String draccCusName)
    {
        this.draccCusName = draccCusName;
    }
    public String getDraccCusType()
    {
        return draccCusType;
    }
    public void setDraccCusType(String draccCusType)
    {
        this.draccCusType = draccCusType;
    }
    public String getDraccCardId()
    {
        return draccCardId;
    }
    public void setDraccCardId(String draccCardId)
    {
        this.draccCardId = draccCardId;
    }
    public String getDraccNo()
    {
        return draccNo;
    }
    public void setDraccNo(String draccNo)
    {
        this.draccNo = draccNo;
    }
    public String getDraccName()
    {
        return draccName;
    }
    public void setDraccName(String draccName)
    {
        this.draccName = draccName;
    }
    public String getDraccNodeCode()
    {
        return draccNodeCode;
    }
    public void setDraccNodeCode(String draccNodeCode)
    {
        this.draccNodeCode = draccNodeCode;
    }
    public String getDraccBankName()
    {
        return draccBankName;
    }
    public void setDraccBankName(String draccBankName)
    {
        this.draccBankName = draccBankName;
    }
    public String getPayCurrency()
    {
        return payCurrency;
    }
    public void setPayCurrency(String payCurrency)
    {
        this.payCurrency = payCurrency;
    }
    public BigDecimal getExRate()
    {
        return exRate;
    }
    public void setExRate(BigDecimal exRate)
    {
        this.exRate = exRate;
    }
    public BigDecimal getPayAmount()
    {
        return payAmount;
    }
    public void setPayAmount(BigDecimal payAmount)
    {
        this.payAmount = payAmount;
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
    public String getPayWay()
    {
        return payWay;
    }
    public void setPayWay(String payWay)
    {
        this.payWay = payWay;
    }
    public String getSacFlag()
    {
        return sacFlag;
    }
    public void setSacFlag(String sacFlag)
    {
        this.sacFlag = sacFlag;
    }
    public String getInnConFlag()
    {
        return innConFlag;
    }
    public void setInnConFlag(String innConFlag)
    {
        this.innConFlag = innConFlag;
    }
    public String getInnConStatus()
    {
        return innConStatus;
    }
    public void setInnConStatus(String innConStatus)
    {
        this.innConStatus = innConStatus;
    }
    public String getPayconType()
    {
        return payconType;
    }
    public void setPayconType(String payconType)
    {
        this.payconType = payconType;
    }
    public String getChaConFlag()
    {
        return chaConFlag;
    }
    public void setChaConFlag(String chaConFlag)
    {
        this.chaConFlag = chaConFlag;
    }
    public String getChaConStatus()
    {
        return chaConStatus;
    }
    public void setChaConStatus(String chaConStatus)
    {
        this.chaConStatus = chaConStatus;
    }
    public String getAccountStatus()
    {
        return accountStatus;
    }
    public void setAccountStatus(String accountStatus)
    {
        this.accountStatus = accountStatus;
    }
    public String getTrxBatchNo()
    {
        return trxBatchNo;
    }
    public void setTrxBatchNo(String trxBatchNo)
    {
        this.trxBatchNo = trxBatchNo;
    }
    public Date getTrxTime()
    {
        return trxTime;
    }
    public void setTrxTime(Date trxTime)
    {
        this.trxTime = trxTime;
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
    public String getReversalStatus()
    {
        return reversalStatus;
    }
    public void setReversalStatus(String reversalStatus)
    {
        this.reversalStatus = reversalStatus;
    }
    public String getMemo()
    {
        return memo;
    }
    public void setMemo(String memo)
    {
        this.memo = memo;
    }
    public String getTrxErrDealType()
    {
        return trxErrDealType;
    }
    public void setTrxErrDealType(String trxErrDealType)
    {
        this.trxErrDealType = trxErrDealType;
    }
    public BigDecimal getTaxAmount()
    {
        return taxAmount;
    }
    public void setTaxAmount(BigDecimal taxAmount)
    {
        this.taxAmount = taxAmount;
    }
    public BigDecimal getTransportExpenses()
    {
        return transportExpenses;
    }
    public void setTransportExpenses(BigDecimal transportExpenses)
    {
        this.transportExpenses = transportExpenses;
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
    public String getRemittanceBatchId()
    {
        return remittanceBatchId;
    }
    public void setRemittanceBatchId(String remittanceBatchId)
    {
        this.remittanceBatchId = remittanceBatchId;
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
