/**
 * 
 */
package net.easipay.cbp.form;

import java.io.Serializable;
import java.util.Date;

public class SacRecDifferencesQueryResult implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1819183526944186500L;
    private String recBatchId;
    private String recDetailId;
    private Date recStartDate;
    private Date recEndDate;
    private String trxSerialNo;
    private Date trxTime;
    private String currencyType;
    private String payAmount;
    private String bankSerialNo;
    private String chnCode;
    private String payconType;
    private String recDiffType;
    private String recDiffDesc;
    private String status;
    private String dealType;
    private String dealOper;
    private Date createTime;
    private Date updateTime;
    private String trxCode;
    private String privDomain;
    private String memo;

    public String getRecBatchId()
    {
	return recBatchId;
    }

    public void setRecBatchId(String recBatchId)
    {
	this.recBatchId = recBatchId;
    }

    public String getRecDetailId()
    {
	return recDetailId;
    }

    public void setRecDetailId(String recDetailId)
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

    public String getPayAmount()
    {
	return payAmount;
    }

    public void setPayAmount(String payAmount)
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
