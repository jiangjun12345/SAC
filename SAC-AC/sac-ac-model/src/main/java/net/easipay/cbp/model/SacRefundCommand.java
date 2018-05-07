/*
 * SacRefundDetail.java, Created on  2016-02-29
 * Title: PAY_GW <br/>
 * Description: <br/>
 * Copyright: Copyright (c)  2016 <br/>
 * @author tangjun
 * @version Revision: 1.0, Date: 2016-02-29  17:10:44 
 */
package net.easipay.cbp.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author tangjun
 */
public class SacRefundCommand implements Serializable
{

    /**
	 * 
	 */
    private static final long serialVersionUID = -2922429917705296479L;
    private Long wpRefundId;
    private String trxSerialNo;
    private String otrxSerialNo;
    private java.util.Date createTime;
    private BigDecimal payAmount;
    private String crtCode;
    private String bankNodeCode;
    private String trxState;
    private String rtrxSerialNo;
    private String auditState;
    private java.util.Date lastUpdateTime;
    private String craccNo;
    private String craccName;
    private String craccBankBranch;
    private String payCurrency;
    private String expBatch;

    public Long getWpRefundId()
    {
	return wpRefundId;
    }

    public void setWpRefundId(Long wpRefundId)
    {
	this.wpRefundId = wpRefundId;
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

    public java.util.Date getCreateTime()
    {
	return createTime;
    }

    public void setCreateTime(java.util.Date createTime)
    {
	this.createTime = createTime;
    }

    public BigDecimal getPayAmount()
    {
	return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount)
    {
	this.payAmount = payAmount;
    }

    public String getCrtCode()
    {
	return crtCode;
    }

    public void setCrtCode(String crtCode)
    {
	this.crtCode = crtCode;
    }

    public String getBankNodeCode()
    {
	return bankNodeCode;
    }

    public void setBankNodeCode(String bankNodeCode)
    {
	this.bankNodeCode = bankNodeCode;
    }

    public String getTrxState()
    {
	return trxState;
    }

    public void setTrxState(String trxState)
    {
	this.trxState = trxState;
    }

    public String getRtrxSerialNo()
    {
	return rtrxSerialNo;
    }

    public void setRtrxSerialNo(String rtrxSerialNo)
    {
	this.rtrxSerialNo = rtrxSerialNo;
    }

    public String getAuditState()
    {
	return auditState;
    }

    public void setAuditState(String auditState)
    {
	this.auditState = auditState;
    }

    public java.util.Date getLastUpdateTime()
    {
	return lastUpdateTime;
    }

    public void setLastUpdateTime(java.util.Date lastUpdateTime)
    {
	this.lastUpdateTime = lastUpdateTime;
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

    public String getCraccBankBranch()
    {
	return craccBankBranch;
    }

    public void setCraccBankBranch(String craccBankBranch)
    {
	this.craccBankBranch = craccBankBranch;
    }

    public String getPayCurrency()
    {
	return payCurrency;
    }

    public void setPayCurrency(String payCurrency)
    {
	this.payCurrency = payCurrency;
    }

    public String getExpBatch()
    {
	return expBatch;
    }

    public void setExpBatch(String expBatch)
    {
	this.expBatch = expBatch;
    }


}
