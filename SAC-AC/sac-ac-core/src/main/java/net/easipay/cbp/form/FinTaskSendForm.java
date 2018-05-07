package net.easipay.cbp.form;

import java.util.Date;

public class FinTaskSendForm implements java.io.Serializable
{
    private static final long serialVersionUID = -8213845150921566852L;

    private String trxCode;

    private String busiType;

    private Integer step;

    private String params;

    private String digest;

    private String serno;

    private Date tradeTime;

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
}
