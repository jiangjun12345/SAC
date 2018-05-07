package net.easipay.cbp.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;

@Entity
public class SacCancelVerify implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 332598415945770648L;
	private String trxSerialNo;
	private BigDecimal PayAmount;
	private BigDecimal yhxAmount;
	private BigDecimal whxAmount;
	private Date createTime;
	private Date trxSuccTime;
	private String cavType;
	public String getTrxSerialNo() {
		return trxSerialNo;
	}
	public void setTrxSerialNo(String trxSerialNo) {
		this.trxSerialNo = trxSerialNo;
	}
	public BigDecimal getPayAmount() {
		return PayAmount;
	}
	public void setPayAmount(BigDecimal payAmount) {
		PayAmount = payAmount;
	}
	public BigDecimal getYhxAmount() {
		return yhxAmount;
	}
	public void setYhxAmount(BigDecimal yhxAmount) {
		this.yhxAmount = yhxAmount;
	}
	public BigDecimal getWhxAmount() {
		return whxAmount;
	}
	public void setWhxAmount(BigDecimal whxAmount) {
		this.whxAmount = whxAmount;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getTrxSuccTime() {
		return trxSuccTime;
	}
	public void setTrxSuccTime(Date trxSuccTime) {
		this.trxSuccTime = trxSuccTime;
	}
	public String getCavType() {
		return cavType;
	}
	public void setCavType(String cavType) {
		this.cavType = cavType;
	}
	
	
}
