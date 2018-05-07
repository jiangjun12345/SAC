package net.easipay.cbp.model;

import java.util.Date;

public class FinTask implements java.io.Serializable
{
    private static final long serialVersionUID = -4547759363916468175L;
    private Long taskId;
    private String trxCode;
    private String busiType;
    private Integer step;
    private String params;
    private String digest;
    private String serno;
    private Date tradeTime;
    private Integer status;
    private String errDesc;

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

    public String getBusiType()
    {
	return busiType;
    }

    public void setBusiType(String busiType)
    {
	this.busiType = busiType;
    }

    public Integer getStep()
    {
	return step;
    }

    public void setStep(Integer step)
    {
	this.step = step;
    }

    public String getParams()
    {
	return params;
    }

    public void setParams(String params)
    {
	this.params = params;
    }

    public String getDigest()
    {
	return digest;
    }

    public void setDigest(String digest)
    {
	this.digest = digest;
    }

    public String getSerno()
    {
	return serno;
    }

    public void setSerno(String serno)
    {
	this.serno = serno;
    }

    public Date getTradeTime()
    {
	return tradeTime;
    }

    public void setTradeTime(Date tradeTime)
    {
	this.tradeTime = tradeTime;
    }

    public Integer getStatus()
    {
	return status;
    }

    public void setStatus(Integer status)
    {
	this.status = status;
    }

    public String getErrDesc()
    {
	return errDesc;
    }

    public void setErrDesc(String errDesc)
    {
	this.errDesc = errDesc;
    }
}
