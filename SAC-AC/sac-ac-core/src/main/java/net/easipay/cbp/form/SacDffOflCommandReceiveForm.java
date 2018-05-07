/**
 * 
 */
package net.easipay.cbp.form;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

public class SacDffOflCommandReceiveForm implements Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -8743130265612927905L;

	@NotBlank(message = "预扣交易流水号不能为空")
    private String ykSerialNo;// 预扣交易流水号

    @NotBlank(message = "实扣交易流水号不能为空")
    private String skSerialNo;// 实扣交易流水号

    @NotBlank(message = "指令类型不能为空")
    @Pattern(regexp = "^00|01$", message = "指令类型必须为00 线上转线下出款；01 纯线下出款")
    private String cmdType;

    @NotBlank(message = "业务类型不能为空")
    @Pattern(regexp = "^00|20|21|22|23|30|40|41|50|60|70|71|80|90$", message = "业务类型必须为00默认,20航付通,21航付通专项 EIR放箱,22航付通专项 放箱打单费,23新航付通专项,30跨境B2C,40代付,41代收,50外汇通,60清算,70速结汇,71速汇通,80关税代付,90行邮税")
    private String bussType;// 付款方代码，即企业的组织机构代码

    @NotNull(message = "支付金额不能为空")
    @DecimalMin(value = "0.00", message = "支付金额非法")
    private BigDecimal payAmount = new BigDecimal(0L);// 支付金额

    @NotBlank(message = "支付币种不能为空")
    private String payCurrency;// 支付币种
    
    @NotBlank(message = "收款方代码")
    private String craccCardId;// 收款方代码，即企业的组织机构代码

    @NotBlank(message = "收款方账号不能为空")
    private String craccNo;// 收款方账号

    @NotBlank(message = "收款账户名称不能为空")
    private String craccName;// 收款账户名称

    @NotBlank(message = "收款方银行节点代码不能为空")
    private String craccNodeCode;// 收款账户开户行名称
    
    @NotBlank(message = "收款银行名称不能为空")
    private String craccBankName;// 收款账户开户行名称
    
    private String craccBranchCode;// 收款账户开户行联行号
    
    private String draccNodeCode;//付款方银行节点代码
    
    private String draccBankName;//付款方银行名称

	public String getYkSerialNo() {
		return ykSerialNo;
	}

	public void setYkSerialNo(String ykSerialNo) {
		this.ykSerialNo = ykSerialNo;
	}

	public String getSkSerialNo() {
		return skSerialNo;
	}

	public void setSkSerialNo(String skSerialNo) {
		this.skSerialNo = skSerialNo;
	}

	public String getCmdType() {
		return cmdType;
	}

	public void setCmdType(String cmdType) {
		this.cmdType = cmdType;
	}

	public String getBussType() {
		return bussType;
	}

	public void setBussType(String bussType) {
		this.bussType = bussType;
	}

	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	public String getPayCurrency() {
		return payCurrency;
	}

	public void setPayCurrency(String payCurrency) {
		this.payCurrency = payCurrency;
	}

	public String getCraccCardId() {
		return craccCardId;
	}

	public void setCraccCardId(String craccCardId) {
		this.craccCardId = craccCardId;
	}

	public String getCraccNo() {
		return craccNo;
	}

	public void setCraccNo(String craccNo) {
		this.craccNo = craccNo;
	}

	public String getCraccName() {
		return craccName;
	}

	public void setCraccName(String craccName) {
		this.craccName = craccName;
	}

	public String getCraccNodeCode() {
		return craccNodeCode;
	}

	public void setCraccNodeCode(String craccNodeCode) {
		this.craccNodeCode = craccNodeCode;
	}

	public String getCraccBankName() {
		return craccBankName;
	}

	public void setCraccBankName(String craccBankName) {
		this.craccBankName = craccBankName;
	}

	public String getCraccBranchCode() {
		return craccBranchCode;
	}

	public void setCraccBranchCode(String craccBranchCode) {
		this.craccBranchCode = craccBranchCode;
	}

	public String getDraccNodeCode() {
		return draccNodeCode;
	}

	public void setDraccNodeCode(String draccNodeCode) {
		this.draccNodeCode = draccNodeCode;
	}

	public String getDraccBankName() {
		return draccBankName;
	}

	public void setDraccBankName(String draccBankName) {
		this.draccBankName = draccBankName;
	}
 

}
