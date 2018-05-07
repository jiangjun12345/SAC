package net.easipay.cbp.form;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

public class SacChargeApplyForm implements java.io.Serializable
{
    private static final long serialVersionUID = 1935819764447098046L;
    @NotBlank(message = "业务类型不能为空")
    @Pattern(regexp = "^00|20|21|22|23|30|40|41|50|60|70|71|80|90$", message = "业务类型必须为00默认,20航付通,21航付通专项 EIR放箱,22航付通专项 放箱打单费,23新航付通专项,30跨境B2C,40代付,41代收,50外汇通,60清算,70速结汇,71速汇通,80关税代付,90行邮税")
    private String bussType;
    
    @NotNull(message = "原始付款方企业ID不能为空")
    private Long applyOrgId;
    
    @NotBlank(message = "付款方代码不能为空")
    private String applyDbtCode;
    
    @NotBlank(message = "付款企业名称不能为空")
    private String applyOrgName;
    
    @NotBlank(message = "申请识别号不能为空")
    private String applyCode;
    
    @NotBlank(message = "申请币种不能为空")
    private String payCurrency;
    
    @NotNull(message = "申请金额不能为空")
    @DecimalMin(value = "0.00", message = "申请金额非法")
    private BigDecimal payAmount;
    
    private String craccNo;
    
    private String craccName;
    
    @NotBlank(message = "收款方银行节点代码不能为空")
    private String craccNodeCode;
    
    @NotBlank(message = "收款方银行名称不能为空")
    private String craccBankName;
    
    private String draccNo;
    private String draccName;
    private String applyMemo;
    
    public String getBussType()
    {
        return bussType;
    }

    public void setBussType(String bussType)
    {
        this.bussType = bussType;
    }

    public Long getApplyOrgId()
    {
	return applyOrgId;
    }

    public void setApplyOrgId(Long applyOrgId)
    {
	this.applyOrgId = applyOrgId;
    }

    public String getApplyDbtCode()
    {
	return applyDbtCode;
    }

    public void setApplyDbtCode(String applyDbtCode)
    {
	this.applyDbtCode = applyDbtCode;
    }

    public String getApplyOrgName()
    {
	return applyOrgName;
    }

    public void setApplyOrgName(String applyOrgName)
    {
	this.applyOrgName = applyOrgName;
    }

    public String getApplyCode()
    {
	return applyCode;
    }

    public void setApplyCode(String applyCode)
    {
	this.applyCode = applyCode;
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

    public String getApplyMemo()
    {
	return applyMemo;
    }

    public void setApplyMemo(String applyMemo)
    {
	this.applyMemo = applyMemo;
    }

}
