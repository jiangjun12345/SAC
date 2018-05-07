/**
 * 
 */
package net.easipay.cbp.form;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class SacReplacePayReceiveForm implements Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 4576898511587086633L;
	
	private String acType;//到账类型   0 T+0到账  1 T+1到账
	
	@NotBlank(message = "交易流水号不能为空")
    private String trxSerialNo;
    
    @NotBlank(message = "收款方客户名称不能为空")
    private String craccCusName;

    @NotBlank(message = "收款方银行节点代码不能为空")
    private String craccNodeCode;
    
    @NotBlank(message = "付款方证件号不能为空")
    @Length(max = 18, message = "付款方证件号最大长度18位")
    private String draccCardId;

    @NotBlank(message = "付款方银行节点代码不能为空")
    private String draccNodeCode;

    @NotBlank(message = "付款方银行名称不能为空")
    private String draccBankName;

    @NotBlank(message = "支付币种不能为空")
    private String payCurrency;

    @NotNull(message = "支付金额不能为空")
    @DecimalMin(value = "0.00", message = "支付金额非法")
    private BigDecimal payAmount;

    private BigDecimal cusCharge;
    
    private String trxType;
    
	public String getTrxSerialNo() {
		return trxSerialNo;
	}

	public void setTrxSerialNo(String trxSerialNo) {
		this.trxSerialNo = trxSerialNo;
	}

	public String getCraccCusName() {
		return craccCusName;
	}

	public void setCraccCusName(String craccCusName) {
		this.craccCusName = craccCusName;
	}

	public String getCraccNodeCode() {
		return craccNodeCode;
	}

	public void setCraccNodeCode(String craccNodeCode) {
		this.craccNodeCode = craccNodeCode;
	}

	public String getDraccCardId() {
		return draccCardId;
	}

	public void setDraccCardId(String draccCardId) {
		this.draccCardId = draccCardId;
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

	public String getPayCurrency() {
		return payCurrency;
	}

	public void setPayCurrency(String payCurrency) {
		this.payCurrency = payCurrency;
	}

	public String getPayAmount() {
		return payAmount.setScale(2,BigDecimal.ROUND_DOWN)+"";
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	public BigDecimal getCusCharge() {
		return cusCharge;
	}

	public void setCusCharge(BigDecimal cusCharge) {
		this.cusCharge = cusCharge;
	}

	public String getTrxType() {
		return trxType;
	}

	public void setTrxType(String trxType) {
		this.trxType = trxType;
	}

	public String getAcType() {
		return acType;
	}

	public void setAcType(String acType) {
		this.acType = acType;
	}
	


	

}
