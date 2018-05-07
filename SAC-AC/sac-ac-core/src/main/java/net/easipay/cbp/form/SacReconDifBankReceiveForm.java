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

public class SacReconDifBankReceiveForm implements Serializable
{
    private static final long serialVersionUID = -7334361719575685036L;

    @NotBlank(message = "外部流水号不能为空")
    @FwsAnno(0)
    private String bankSerialNo; // 外部流水号

    @NotBlank(message = "支付渠道类型不能为空")
    @Pattern(regexp = "^1|2|3|4|5|6$", message = "支付渠道类型必须为1 B2B支付 2 B2C支付 3其他  4代收付 5 购付汇  6 外汇通 ")
    @FwsAnno(1)
    private String payconType; // 支付渠道类型1 B2B支付 2 B2C支付

    @FwsAnno(2)
    @NotBlank(message = "币种不能为空")
    private String currencyType;

    @DecimalMin(value = "0.00", message = "支付金额非法")
    @FwsAnno(3)
    private String payAmount;// 支付金额

    @NotNull(message = "交易时间不能为空")
    @FwsAnno(4)
    private Date trxTime;// 交易时间，即交易成功时间，格式如下： yyyyMMddHHmmss

    @NotBlank(message = "操作员不能为空")
    @FwsAnno(5)
    private String recOper;// 操作员

    @NotBlank(message = "渠道号不能为空")
    @FwsAnno(6)
    private String chnCode;// 渠道节点号即银行节点代码

    @NotBlank(message = "差错类型不能为空")
    @Pattern(regexp = "^1|2|3$", message = "差错类型必须为1长款.2短款.3其它")
    @FwsAnno(7)
    private String diffType;// 差错类型1长款.2短款.3其它

    @NotBlank(message = "正逆向交易标示不能为空")
    @Pattern(regexp = "^1|2$", message = "正逆向交易标示必须为1,正向交易2.逆向交易")
    @FwsAnno(8)
    private String busiType;// 正逆向交易标示

    @NotNull(message = "交易起始日期不能为空")
    @FwsAnno(9)
    private Date recStartDate;

    @NotNull(message = "交易结束日期不能为空")
    @FwsAnno(10)
    private Date recEndDate;

    @NotNull(message = "交易数量不能为空")
    @FwsAnno(11)
    private Long recCount;
    
    @FwsAnno(12)
    private String trxSerialNo;
    
    @FwsAnno(13)
    private String trxCode;

    @FwsAnno(14)
    private String privDomain;

    public String getBankSerialNo()
    {
	return bankSerialNo;
    }

    public void setBankSerialNo(String bankSerialNo)
    {
	this.bankSerialNo = bankSerialNo;
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

    public String getChnCode()
    {
	return chnCode;
    }

    public void setChnCode(String chnCode)
    {
	this.chnCode = chnCode;
    }

    public String getDiffType()
    {
	return diffType;
    }

    public void setDiffType(String diffType)
    {
	this.diffType = diffType;
    }

    public String getCurrencyType()
    {
	return currencyType;
    }

    public void setCurrencyType(String currencyType)
    {
	this.currencyType = currencyType;
    }

    public String getBusiType()
    {
	return busiType;
    }

    public void setBusiType(String busiType)
    {
	this.busiType = busiType;
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

    public String getTrxSerialNo()
    {
        return trxSerialNo;
    }

    public void setTrxSerialNo(String trxSerialNo)
    {
        this.trxSerialNo = trxSerialNo;
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
