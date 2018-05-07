package net.easipay.cbp.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "SAC_OFL_COMMAND", schema = "SAC_SYN")
public class SacOflCommand implements java.io.Serializable {
	
	  private static final long serialVersionUID = 1299060788514632418L;

	  private Long id;
	
      private String trxSerialNo;

      private String otrxSerialNo;

      private String etrxSerialNo;

      private String state;

      private String draccNodeCode;
      
      private String cusNo;

      private String crtCode;

      private String craccNo;

      private String craccName;

      private String craccNodeCode;

      private String craccBankName;

      private BigDecimal payAmount;

      private String payCurrency;

      private String operName;

      private Date operTime;

      private Date createTime;

      private Date lastUpdateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTrxSerialNo() {
		return trxSerialNo;
	}

	public void setTrxSerialNo(String trxSerialNo) {
		this.trxSerialNo = trxSerialNo;
	}

	public String getOtrxSerialNo() {
		return otrxSerialNo;
	}

	public void setOtrxSerialNo(String otrxSerialNo) {
		this.otrxSerialNo = otrxSerialNo;
	}

	public String getEtrxSerialNo() {
		return etrxSerialNo;
	}

	public void setEtrxSerialNo(String etrxSerialNo) {
		this.etrxSerialNo = etrxSerialNo;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDraccNodeCode() {
		return draccNodeCode;
	}

	public void setDraccNodeCode(String draccNodeCode) {
		this.draccNodeCode = draccNodeCode;
	}

	public String getCrtCode() {
		return crtCode;
	}

	public void setCrtCode(String crtCode) {
		this.crtCode = crtCode;
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

	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}

	public Date getOperTime() {
		return operTime;
	}

	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getCusNo() {
		return cusNo;
	}

	public void setCusNo(String cusNo) {
		this.cusNo = cusNo;
	}
	


}
