/**
 * 
 */
package net.easipay.cbp.form;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import net.easipay.dsfc.ws.fws.FwsAnno;

import org.hibernate.validator.constraints.NotBlank;

public class SacReconInternalReceiveForm implements Serializable
{
    private static final long serialVersionUID = -7334361719575685036L;

    @NotBlank(message = "交易流水号不能为空")
    @FwsAnno(0)
    private String trxSerialNo;

    @FwsAnno(1)
    private String etrxSerialNo;

    @NotBlank(message = "支付渠道类型不能为空")
    @Pattern(regexp = "^1|2|3|4|5|6$", message = "支付渠道类型必须为1 B2B支付 2 B2C支付 3其他  4代收付 5 购付汇  6 外汇通 ")
    @FwsAnno(2)
    private String payconType;

    @FwsAnno(3)
    @NotBlank(message = "币种不能为空")
    private String currencyType;

    @DecimalMin(value = "0.00", message = "支付金额非法")
    @FwsAnno(4)
    private String payAmount;

    @NotNull(message = "交易时间不能为空")
    @FwsAnno(5)
    private Date trxTime;

    @NotBlank(message = "操作员不能为空")
    @FwsAnno(6)
    private String recOper;

    @NotNull(message = "交易起始日期不能为空")
    @FwsAnno(7)
    private Date recStartDate;

    @NotNull(message = "交易结束日期不能为空")
    @FwsAnno(8)
    private Date recEndDate;

    @NotNull(message = "交易数量不能为空")
    @FwsAnno(9)
    private Long recCount;

    @FwsAnno(10)
    private String trxCode;

    @FwsAnno(11)
    private String privDomain;

    public String getTrxSerialNo()
    {
	return trxSerialNo;
    }

    public void setTrxSerialNo(String trxSerialNo)
    {
	this.trxSerialNo = trxSerialNo;
    }

    public String getPayconType()
    {
	return payconType;
    }

    public void setPayconType(String payconType)
    {
	this.payconType = payconType;
    }

    public String getPayAmount()
    {
	return payAmount;
    }

    public void setPayAmount(String payAmount)
    {
	this.payAmount = payAmount;
    }

    public Date getTrxTime()
    {
	return trxTime;
    }

    public void setTrxTime(Date trxTime)
    {
	this.trxTime = trxTime;
    }

    public String getRecOper()
    {
	return recOper;
    }

    public void setRecOper(String recOper)
    {
	this.recOper = recOper;
    }

    public String getEtrxSerialNo()
    {
	return etrxSerialNo;
    }

    public void setEtrxSerialNo(String etrxSerialNo)
    {
	this.etrxSerialNo = etrxSerialNo;
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

    public Long getRecCount()
    {
	return recCount;
    }

    public void setRecCount(Long recCount)
    {
	this.recCount = recCount;
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
