package net.easipay.cbp.model;

import java.math.BigDecimal;
import java.util.Date;

public class SacRecDifferences implements java.io.Serializable
{
    private static final long serialVersionUID = 1935819764447098046L;
    private Long id; // 主键
    private Long recBatchId; // 对账批次ID
    private Long recDetailId; // 对账明细ID
    private Date recStartDate; // 对账日期
    private Date recEndDate; // 对账日期
    private String recOper; // 操作人
    private String trxSerialNo; // 交易流水号
    private Date trxTime; // 交易时间
    private BigDecimal payAmount; // 支付金额
    private String currencyType;
    private String bankSerialNo; // 外部流水号
    private String chnCode; // 渠道节点
    private String payconType; // 支付渠道类型
    private String recDiffType; // 差错类型
    private String recDiffDesc; // 差错原因
    private String status; // 处理状态
    private String dealType; // 处理方式
    private String dealOper; // 处理人员
    private Date createTime; // 记录创建时间
    private Date updateTime; // 更新时间
    private String memo; // 备注
    private String oriTrxState; // 原始交易表交易状态
    private BigDecimal oriInnConAmount; // 内部对账原始交易表金额
    private BigDecimal oriChaConAmount; // 渠道对账原始交易表金额
    private String trxCode;
    private String privDomain;

    public Long getId()
    {
	return id;
    }

    public void setId(Long id)
    {
	this.id = id;
    }

    public Long getRecBatchId()
    {
	return recBatchId;
    }

    public void setRecBatchId(Long recBatchId)
    {
	this.recBatchId = recBatchId;
    }

    public Long getRecDetailId()
    {
	return recDetailId;
    }

    public void setRecDetailId(Long recDetailId)
    {
	this.recDetailId = recDetailId;
    }

    public String getTrxSerialNo()
    {
	return trxSerialNo;
    }

    public void setTrxSerialNo(String trxSerialNo)
    {
	this.trxSerialNo = trxSerialNo;
    }

    public Date getTrxTime()
    {
	return trxTime;
    }

    public void setTrxTime(Date trxTime)
    {
	this.trxTime = trxTime;
    }

    public BigDecimal getPayAmount()
    {
	return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount)
    {
	this.payAmount = payAmount;
    }

    public String getBankSerialNo()
    {
	return bankSerialNo;
    }

    public void setBankSerialNo(String bankSerialNo)
    {
	this.bankSerialNo = bankSerialNo;
    }

    public String getChnCode()
    {
	return chnCode;
    }

    public void setChnCode(String chnCode)
    {
	this.chnCode = chnCode;
    }

    public String getPayconType()
    {
	return payconType;
    }

    public void setPayconType(String payconType)
    {
	this.payconType = payconType;
    }

    public String getRecDiffType()
    {
	return recDiffType;
    }

    public void setRecDiffType(String recDiffType)
    {
	this.recDiffType = recDiffType;
    }

    public String getRecDiffDesc()
    {
	return recDiffDesc;
    }

    public void setRecDiffDesc(String recDiffDesc)
    {
	this.recDiffDesc = recDiffDesc;
    }

    public String getStatus()
    {
	return status;
    }

    public void setStatus(String status)
    {
	this.status = status;
    }

    public String getDealType()
    {
	return dealType;
    }

    public void setDealType(String dealType)
    {
	this.dealType = dealType;
    }

    public String getDealOper()
    {
	return dealOper;
    }

    public void setDealOper(String dealOper)
    {
	this.dealOper = dealOper;
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

    public String getMemo()
    {
	return memo;
    }

    public void setMemo(String memo)
    {
	this.memo = memo;
    }

    public String getOriTrxState()
    {
	return oriTrxState;
    }

    public void setOriTrxState(String oriTrxState)
    {
	this.oriTrxState = oriTrxState;
    }

    public BigDecimal getOriInnConAmount()
    {
	return oriInnConAmount;
    }

    public void setOriInnConAmount(BigDecimal oriInnConAmount)
    {
	this.oriInnConAmount = oriInnConAmount;
    }

    public BigDecimal getOriChaConAmount()
    {
	return oriChaConAmount;
    }

    public void setOriChaConAmount(BigDecimal oriChaConAmount)
    {
	this.oriChaConAmount = oriChaConAmount;
    }

    public String getRecOper()
    {
	return recOper;
    }

    public void setRecOper(String recOper)
    {
	this.recOper = recOper;
    }

    public String getCurrencyType()
    {
	return currencyType;
    }

    public void setCurrencyType(String currencyType)
    {
	this.currencyType = currencyType;
    }

    public Date getRecStartDate()
    {
	return recStartDate;
    }

    public void setRecStartDate(Date recStartDate)
    {
	this.recStartDate = recStartDate;
    }

    public Date getRecEndDate()
    {
	return recEndDate;
    }

    public void setRecEndDate(Date recEndDate)
    {
	this.recEndDate = recEndDate;
    }

    public String getTrxCode()
    {
	return trxCode;
    }

    public void setTrxCode(String trxCode)
    {
	this.trxCode = trxCode;
    }

    public String getPrivDomain()
    {
	return privDomain;
    }

    public void setPrivDomain(String privDomain)
    {
	this.privDomain = privDomain;
    }

}
