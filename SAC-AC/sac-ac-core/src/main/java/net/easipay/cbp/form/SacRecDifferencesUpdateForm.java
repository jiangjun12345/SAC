/**
 * 
 */
package net.easipay.cbp.form;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.ScriptAssert;

@ScriptAssert(message = "交易流水号,外部流水号不能同时为空", alias = "_this", lang = "javascript", script = "!((_this.trxSerialNo == null || _this.trxSerialNo == '') && (_this.bankSerialNo == null || _this.bankSerialNo == '')) ")
public class SacRecDifferencesUpdateForm implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = -7334361719575685036L;
    @NotBlank(message = "明细ID不能为空")
    private String recDetailId;

    private String trxSerialNo;

    private String bankSerialNo;

    @NotBlank(message = "处理状态不能为空")
    @Pattern(regexp = "^N|S$", message = "处理状态必须为 N:未处理，S：已处理")
    private String status;

    @NotBlank(message = "处理方式不能为空")
    @Pattern(regexp = "^1|2$", message = "处理方式必须为 1：人工 2: 系统自动")
    private String dealType;

    @NotBlank(message = "处理人员不能为空")
    private String dealOper;

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

    public String getBankSerialNo()
    {
	return bankSerialNo;
    }

    public void setBankSerialNo(String bankSerialNo)
    {
	this.bankSerialNo = bankSerialNo;
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

}
