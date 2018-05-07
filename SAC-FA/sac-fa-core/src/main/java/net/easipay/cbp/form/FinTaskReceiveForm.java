package net.easipay.cbp.form;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class FinTaskReceiveForm implements java.io.Serializable
{

    private static final long serialVersionUID = 4954009158363914026L;
    @NotBlank(message = "交易代码不能为空")
    @Length(min = 4, max = 8, message = "交易代码不符合长度为4-8位的规则")
    private String trxCode;
    
    @NotBlank(message = "业务类型不能为空")
    private String busiType;
    
    @NotNull(message="step不能为空")
    private Integer step;
    
    @NotBlank(message = "params不能为空")
    private String params;
    
    @NotBlank(message = "摘要不能为空")
    private String digest;
    
    @NotBlank(message = "交易流水号不能为空")
    private String serno;
    
    @NotNull(message="tradeTime不能为空")
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
