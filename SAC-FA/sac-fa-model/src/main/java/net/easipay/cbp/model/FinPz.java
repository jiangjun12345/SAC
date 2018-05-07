package net.easipay.cbp.model;

import java.math.BigDecimal;
import java.util.Date;

public class FinPz implements java.io.Serializable
{
    private static final long serialVersionUID = 2231540884231313731L;
    private Long pzId;
    private String codeId;
    private Integer direction;
    private BigDecimal amount;
    private String digest;
    private Date pzTime;
    private Date tradeTime;
    private String busiType;
    private String serno;
    private String pzNo;
    private Long taskId;
    private String trxCode;
    private String pzKey;

    public Long getPzId()
    {
	return pzId;
    }

    public void setPzId(Long pzId)
    {
	this.pzId = pzId;
    }

    public String getCodeId()
    {
	return codeId;
    }

    public void setCodeId(String codeId)
    {
	this.codeId = codeId;
    }

    public Integer getDirection()
    {
	return direction;
    }

    public void setDirection(Integer direction)
    {
	this.direction = direction;
    }

    public BigDecimal getAmount()
    {
	return amount;
    }

    public void setAmount(BigDecimal amount)
    {
	this.amount = amount;
    }

    public String getDigest()
    {
	return digest;
    }

    public void setDigest(String digest)
    {
	this.digest = digest;
    }

    public Date getPzTime()
    {
	return pzTime;
    }

    public void setPzTime(Date pzTime)
    {
	this.pzTime = pzTime;
    }

    public Date getTradeTime()
    {
	return tradeTime;
    }

    public void setTradeTime(Date tradeTime)
    {
	this.tradeTime = tradeTime;
    }

    public String getBusiType()
    {
	return busiType;
    }

    public void setBusiType(String busiType)
    {
	this.busiType = busiType;
    }

    public String getSerno()
    {
	return serno;
    }

    public void setSerno(String serno)
    {
	this.serno = serno;
    }

    public String getPzNo()
    {
	return pzNo;
    }

    public void setPzNo(String pzNo)
    {
	this.pzNo = pzNo;
    }

    public Long getTaskId()
    {
	return taskId;
    }

    public void setTaskId(Long taskId)
    {
	this.taskId = taskId;
    }

    public String getTrxCode()
    {
	return trxCode;
    }

    public void setTrxCode(String trxCode)
    {
	this.trxCode = trxCode;
    }

    public String getPzKey()
    {
	return pzKey;
    }

    public void setPzKey(String pzKey)
    {
	this.pzKey = pzKey;
    }

}
