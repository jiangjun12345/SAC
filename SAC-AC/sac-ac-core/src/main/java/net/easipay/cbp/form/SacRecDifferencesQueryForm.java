/**
 * 
 */
package net.easipay.cbp.form;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

public class SacRecDifferencesQueryForm implements Serializable
{
    private static final long serialVersionUID = 1819183526944186500L;

    @NotBlank(message = "支付渠道类型不能为空")
    @Pattern(regexp = "^1|2|3|4|5|6$", message = "支付渠道类型必须为1 B2B支付 2 B2C支付 3其他  4代收付 5 购付汇  6 外汇通 ")
    private String payconType;

    @NotBlank(message = "处理人员不能为空")
    private String recOper;

    @NotNull(message = "交易起始日期不能为空")
    private Date recStartDate;

    @NotNull(message = "交易结束日期不能为空")
    private Date recEndDate;

    @NotBlank(message = "处理状态不能为空")
    @Pattern(regexp = "^N|S$", message = "处理状态必须为 N:未处理，S：已处理")
    private String status;

    public String getPayconType()
    {
	return payconType;
    }

    public void setPayconType(String payconType)
    {
	this.payconType = payconType;
    }

    public String getRecOper()
    {
	return recOper;
    }

    public void setRecOper(String recOper)
    {
	this.recOper = recOper;
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

    public String getStatus()
    {
	return status;
    }

    public void setStatus(String status)
    {
	this.status = status;
    }

}
