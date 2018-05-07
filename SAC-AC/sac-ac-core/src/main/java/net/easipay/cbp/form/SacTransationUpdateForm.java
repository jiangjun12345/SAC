/**
 * 
 */
package net.easipay.cbp.form;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

public class SacTransationUpdateForm implements Serializable
{
    private static final long serialVersionUID = 2379978358898038516L;
    @NotBlank(message = "交易流水号不能为空")
    private String trxSerialNo;

    private String etrxSerialNo;

    @NotBlank(message = "交易状态不能为空")
    @Pattern(regexp = "^N|S|F$", message = "交易状态必须为 N：待支付,S 交易成功,F：支付失败 ")
    private String trxState;
    
    private String trxStateDesc;
    
    @NotNull(message = "银行交易成功时间不能为空")
    private Date trxSuccTime;

    public String getTrxSerialNo()
    {
	return trxSerialNo;
    }

    public void setTrxSerialNo(String trxSerialNo)
    {
	this.trxSerialNo = trxSerialNo;
    }

    public String getEtrxSerialNo()
    {
	return etrxSerialNo;
    }

    public void setEtrxSerialNo(String etrxSerialNo)
    {
	this.etrxSerialNo = etrxSerialNo;
    }

    public String getTrxState()
    {
	return trxState;
    }

    public void setTrxState(String trxState)
    {
	this.trxState = trxState;
    }

    public Date getTrxSuccTime()
    {
        return trxSuccTime;
    }

    public void setTrxSuccTime(Date trxSuccTime)
    {
        this.trxSuccTime = trxSuccTime;
    }

    public String getTrxStateDesc()
    {
        return trxStateDesc;
    }

    public void setTrxStateDesc(String trxStateDesc)
    {
        this.trxStateDesc = trxStateDesc;
    }
    
}
