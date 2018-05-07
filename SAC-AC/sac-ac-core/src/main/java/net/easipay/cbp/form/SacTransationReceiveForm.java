/**
 * 
 */
package net.easipay.cbp.form;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.ScriptAssert;
import org.hibernate.validator.constraints.ScriptAssert.List;

@List(value = {
    @ScriptAssert(message = "购结汇币种或者购结汇金额不能为空", alias = "_this", lang = "javascript", script = "!(_this.trxType == '3411' && ( _this.sacCurrency == null || _this.sacCurrency == '' || _this.sacAmount == null))"),
    @ScriptAssert(message = "交易为成功状态，银行交易成功时间不能为空", alias = "_this", lang = "javascript", script = "!(_this.trxState == 'S' && (_this.trxSuccTime == null || _this.trxSuccTime == ''))"),
    @ScriptAssert(message = "地区代码不能为空", alias = "_this", lang = "javascript", script = "!(_this.trxType.length() >4  && (_this.craccAreaCode == null || _this.craccAreaCode == ''||_this.draccAreaCode == null || _this.draccAreaCode == ''))"),
    @ScriptAssert(message = "地区代码长度必须是6位", alias = "_this", lang = "javascript", script = "!(_this.trxType.length() >4 && (_this.craccAreaCode.length() !=6 || _this.draccAreaCode.length() !=6))")
})

@ScriptAssert(alias = "_this", lang = "javascript", script = "true")
public class SacTransationReceiveForm implements Serializable
{

    private static final long serialVersionUID = 6143828172247545655L;
    private String cusBillNo;

    private String platBillNo;

    @NotBlank(message = "交易流水号不能为空")
    private String trxSerialNo;

    private String otrxSerialNo;

    private String etrxSerialNo;

/*    @NotBlank(message = "收款方客户号不能为空")
    @Length(min = 19, max = 19, message = "收款方客户号长度定长19位")
    private String craccCusCode;*/
    
    @NotBlank(message = "收款方证件号不能为空")
    @Length(max = 18, message = "收款方证件号最大长度18位")
    private String craccCardId;

    @NotBlank(message = "收款方客户类型不能为空")
    @Pattern(regexp = "^1|2$", message = "收款方客户类型必须为1 企业，2 个人")
    private String craccCusType;

    @NotBlank(message = "收款方客户名称不能为空")
    private String craccCusName;

    @NotBlank(message = "收款方帐号不能为空")
    private String craccNo;

    @NotBlank(message = "收款账户名称不能为空")
    private String craccName;

    @NotBlank(message = "收款方银行节点代码不能为空")
    private String craccNodeCode;

    @NotBlank(message = "收款方银行名称不能为空")
    private String craccBankName;
    
    private String craccAreaCode;

/*    @NotBlank(message = "付款方客户号不能为空")
    @Length(min = 19, max = 19, message = "付款方客户号长度定长19位")
    private String draccCusCode;*/
    
    @NotBlank(message = "付款方证件号不能为空")
    @Length(max = 18, message = "付款方证件号最大长度18位")
    private String draccCardId;

    @NotBlank(message = "付款方客户类型不能为空")
    @Pattern(regexp = "^1|2$", message = "付款方客户类型必须为1 企业，2 个人")
    private String draccCusType;

    @NotBlank(message = "付款方客户名称不能为空")
    private String draccCusName;

    @NotBlank(message = "付款方帐号不能为空")
    private String draccNo;

    @NotBlank(message = "付款账户名称不能为空")
    private String draccName;

    @NotBlank(message = "付款方银行节点代码不能为空")
    private String draccNodeCode;

    @NotBlank(message = "付款方银行名称不能为空")
    private String draccBankName;
    
    private String draccAreaCode;

    @NotBlank(message = "支付币种不能为空")
    private String payCurrency;

    @NotNull(message = "支付金额不能为空")
    @DecimalMin(value = "0.00", message = "支付金额非法")
    private BigDecimal payAmount;

    private String sacCurrency;

    private BigDecimal sacAmount;

    @NotNull(message = "交易时间不能为空")
    private Date trxTime;

    @NotBlank(message = "业务类型不能为空")
    @Pattern(regexp = "^00|20|21|22|23|30|40|41|50|60|70|71|80|90$", message = "业务类型必须为00默认,20航付通,21航付通专项 EIR放箱,22航付通专项 放箱打单费,23新航付通专项,30跨境B2C,40代付,41代收,50外汇通,60清算,70速结汇,71速汇通,80关税代付,90行邮税")
    private String bussType;

    @NotBlank(message = "交易类型不能为空")
    private String trxType;

    @NotBlank(message = "交易状态不能为空")
    @Pattern(regexp = "^N|S|F$", message = "交易状态必须为N：代支付  ,S :交易成功 ,F : 支付失败 ")
    private String trxState;
    
    private String trxStateDesc;

    @NotBlank(message = "支付渠道类型不能为空")
    @Pattern(regexp = "^1|2|3|4|5|6$", message = "支付渠道类型必须为1 B2B支付 2 B2C支付 3其他  4代收付 5 购付汇  6 外汇通 ")
    private String payconType;

    @NotBlank(message = "支付方式不能为空")
    @Pattern(regexp = "^1|2|3|4|5|9$", message = "支付方式必须为1 快捷 2 网银 3 代收  4 代付  5 余额  9 其他")
    private String payWay;

    private String trxBatchNo;

    private BigDecimal channelCost;

    private BigDecimal cusCharge;

    private Date trxSuccTime;

    private BigDecimal exRate;

    private String issuingBank;

    @Pattern(regexp = "^|1|2$", message = "交易差错处理类型选填，退款时必须为1：商户退款 2：系统差错退款")
    private String trxErrDealType;

    private BigDecimal taxAmount;// 税金额

    private BigDecimal transportExpenses;// 运费

    private String memo;
    
    private String remittanceBatchId; //付汇批次号

    public String getCusBillNo()
    {
        return cusBillNo;
    }

    public void setCusBillNo(String cusBillNo)
    {
        this.cusBillNo = cusBillNo;
    }

    public String getPlatBillNo()
    {
        return platBillNo;
    }

    public void setPlatBillNo(String platBillNo)
    {
        this.platBillNo = platBillNo;
    }

    public String getTrxSerialNo()
    {
        return trxSerialNo;
    }

    public void setTrxSerialNo(String trxSerialNo)
    {
        this.trxSerialNo = trxSerialNo;
    }

    public String getOtrxSerialNo()
    {
        return otrxSerialNo;
    }

    public void setOtrxSerialNo(String otrxSerialNo)
    {
        this.otrxSerialNo = otrxSerialNo;
    }

    public String getEtrxSerialNo()
    {
        return etrxSerialNo;
    }

    public void setEtrxSerialNo(String etrxSerialNo)
    {
        this.etrxSerialNo = etrxSerialNo;
    }

    public String getCraccCardId()
    {
        return craccCardId;
    }

    public void setCraccCardId(String craccCardId)
    {
        this.craccCardId = craccCardId;
    }

    public String getTrxStateDesc()
    {
        return trxStateDesc;
    }

    public void setTrxStateDesc(String trxStateDesc)
    {
        this.trxStateDesc = trxStateDesc;
    }

    public String getCraccCusType()
    {
        return craccCusType;
    }

    public void setCraccCusType(String craccCusType)
    {
        this.craccCusType = craccCusType;
    }

    public String getCraccCusName()
    {
        return craccCusName;
    }

    public void setCraccCusName(String craccCusName)
    {
        this.craccCusName = craccCusName;
    }

    public String getCraccNo()
    {
        return craccNo;
    }

    public void setCraccNo(String craccNo)
    {
        this.craccNo = craccNo;
    }

    public String getCraccName()
    {
        return craccName;
    }

    public void setCraccName(String craccName)
    {
        this.craccName = craccName;
    }

    public String getCraccNodeCode()
    {
        return craccNodeCode;
    }

    public void setCraccNodeCode(String craccNodeCode)
    {
        this.craccNodeCode = craccNodeCode;
    }

    public String getCraccBankName()
    {
        return craccBankName;
    }

    public void setCraccBankName(String craccBankName)
    {
        this.craccBankName = craccBankName;
    }

    public String getDraccCardId()
    {
        return draccCardId;
    }

    public void setDraccCardId(String draccCardId)
    {
        this.draccCardId = draccCardId;
    }

    public String getDraccCusType()
    {
        return draccCusType;
    }

    public void setDraccCusType(String draccCusType)
    {
        this.draccCusType = draccCusType;
    }

    public String getDraccCusName()
    {
        return draccCusName;
    }

    public void setDraccCusName(String draccCusName)
    {
        this.draccCusName = draccCusName;
    }

    public String getDraccNo()
    {
        return draccNo;
    }

    public void setDraccNo(String draccNo)
    {
        this.draccNo = draccNo;
    }

    public String getDraccName()
    {
        return draccName;
    }

    public void setDraccName(String draccName)
    {
        this.draccName = draccName;
    }

    public String getDraccNodeCode()
    {
        return draccNodeCode;
    }

    public void setDraccNodeCode(String draccNodeCode)
    {
        this.draccNodeCode = draccNodeCode;
    }

    public String getDraccBankName()
    {
        return draccBankName;
    }

    public void setDraccBankName(String draccBankName)
    {
        this.draccBankName = draccBankName;
    }

    public String getPayCurrency()
    {
        return payCurrency;
    }

    public void setPayCurrency(String payCurrency)
    {
        this.payCurrency = payCurrency;
    }

    public BigDecimal getPayAmount()
    {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount)
    {
        this.payAmount = payAmount;
    }

    public String getSacCurrency()
    {
        return sacCurrency;
    }

    public void setSacCurrency(String sacCurrency)
    {
        this.sacCurrency = sacCurrency;
    }

    public BigDecimal getSacAmount()
    {
        return sacAmount;
    }

    public void setSacAmount(BigDecimal sacAmount)
    {
        this.sacAmount = sacAmount;
    }

    public Date getTrxTime()
    {
        return trxTime;
    }

    public void setTrxTime(Date trxTime)
    {
        this.trxTime = trxTime;
    }

    public String getBussType()
    {
        return bussType;
    }

    public void setBussType(String bussType)
    {
        this.bussType = bussType;
    }

    public String getTrxType()
    {
        return trxType;
    }

    public void setTrxType(String trxType)
    {
        this.trxType = trxType;
    }

    public String getTrxState()
    {
        return trxState;
    }

    public void setTrxState(String trxState)
    {
        this.trxState = trxState;
    }

    public String getPayconType()
    {
        return payconType;
    }

    public void setPayconType(String payconType)
    {
        this.payconType = payconType;
    }

    public String getPayWay()
    {
        return payWay;
    }

    public void setPayWay(String payWay)
    {
        this.payWay = payWay;
    }

    public String getTrxBatchNo()
    {
        return trxBatchNo;
    }

    public void setTrxBatchNo(String trxBatchNo)
    {
        this.trxBatchNo = trxBatchNo;
    }

    public BigDecimal getChannelCost()
    {
        return channelCost;
    }

    public void setChannelCost(BigDecimal channelCost)
    {
        this.channelCost = channelCost;
    }

    public BigDecimal getCusCharge()
    {
        return cusCharge;
    }

    public void setCusCharge(BigDecimal cusCharge)
    {
        this.cusCharge = cusCharge;
    }

    public Date getTrxSuccTime()
    {
        return trxSuccTime;
    }

    public void setTrxSuccTime(Date trxSuccTime)
    {
        this.trxSuccTime = trxSuccTime;
    }

    public BigDecimal getExRate()
    {
        return exRate;
    }

    public void setExRate(BigDecimal exRate)
    {
        this.exRate = exRate;
    }

    public String getIssuingBank()
    {
        return issuingBank;
    }

    public void setIssuingBank(String issuingBank)
    {
        this.issuingBank = issuingBank;
    }

    public String getTrxErrDealType()
    {
        return trxErrDealType;
    }

    public void setTrxErrDealType(String trxErrDealType)
    {
        this.trxErrDealType = trxErrDealType;
    }

    public BigDecimal getTaxAmount()
    {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount)
    {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getTransportExpenses()
    {
        return transportExpenses;
    }

    public void setTransportExpenses(BigDecimal transportExpenses)
    {
        this.transportExpenses = transportExpenses;
    }

    public String getMemo()
    {
        return memo;
    }

    public void setMemo(String memo)
    {
        this.memo = memo;
    }

    public String getRemittanceBatchId()
    {
        return remittanceBatchId;
    }

    public void setRemittanceBatchId(String remittanceBatchId)
    {
        this.remittanceBatchId = remittanceBatchId;
    }

	public String getCraccAreaCode() {
		return craccAreaCode;
	}

	public void setCraccAreaCode(String craccAreaCode) {
		this.craccAreaCode = craccAreaCode;
	}

	public String getDraccAreaCode() {
		return draccAreaCode;
	}

	public void setDraccAreaCode(String draccAreaCode) {
		this.draccAreaCode = draccAreaCode;
	}
    
}
