package net.easipay.cbp.form;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class FinRuleTaskJoinForm implements java.io.Serializable
{
    private static final long serialVersionUID = 1935819764447098046L;
    @NotBlank(message = "交易代码不能为空")
    @Length(min = 4, max = 8, message = "交易代码不符合长度为4-8位的规则")
    private String trxCode;

    @NotBlank(message = "状态不能为空")
    private String trxState;

    @NotBlank(message = "交易流水号不能为空")
    private String trxSerialNo;

    public String getTrxCode()
    {
	return trxCode;
    }

    public void setTrxCode(String trxCode)
    {
	this.trxCode = trxCode;
    }

    public String getTrxSerialNo()
    {
	return trxSerialNo;
    }

    public void setTrxSerialNo(String trxSerialNo)
    {
	this.trxSerialNo = trxSerialNo;
    }

    public String getTrxState()
    {
	return trxState;
    }

    public void setTrxState(String trxState)
    {
	this.trxState = trxState;
    }
}
