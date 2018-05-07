package net.easipay.cbp.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;


/**
 * @author sydai 
 */
@Entity
public class SacFundTransferCmd implements Serializable {
        
		private static final long serialVersionUID = 5128216952725189129L;
	
		private long id;

        private String trxSerialNo;

        private String craccNo;

        private String craccNodeCode;

        private String draccNo;

        private String draccNodeCode;

        private String payCurrency;

        private BigDecimal payAmount;

        private String payWay;

        private String memo;

        private Date createTime;

        private Date updateTime;

        private String cmdState;

        private String cmdMemo;
        
        private String draccAreaCode;
        
        private String craccAreaCode;


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTrxSerialNo() {
		return this.trxSerialNo;
	}

	public void setTrxSerialNo(String trxSerialNo) {
		this.trxSerialNo = trxSerialNo;
	}

	public String getCraccNo() {
		return this.craccNo;
	}

	public void setCraccNo(String craccNo) {
		this.craccNo = craccNo;
	}

	public String getCraccNodeCode() {
		return this.craccNodeCode;
	}

	public void setCraccNodeCode(String craccNodeCode) {
		this.craccNodeCode = craccNodeCode;
	}

	public String getDraccNo() {
		return this.draccNo;
	}

	public void setDraccNo(String draccNo) {
		this.draccNo = draccNo;
	}

	public String getDraccNodeCode() {
		return this.draccNodeCode;
	}

	public void setDraccNodeCode(String draccNodeCode) {
		this.draccNodeCode = draccNodeCode;
	}

	public String getPayCurrency() {
		return this.payCurrency;
	}

	public void setPayCurrency(String payCurrency) {
		this.payCurrency = payCurrency;
	}

	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	public String getPayWay() {
		return this.payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getCmdState() {
		return this.cmdState;
	}

	public void setCmdState(String cmdState) {
		this.cmdState = cmdState;
	}

	public String getCmdMemo() {
		return this.cmdMemo;
	}

	public void setCmdMemo(String cmdMemo) {
		this.cmdMemo = cmdMemo;
	}

	public String getDraccAreaCode() {
		return draccAreaCode;
	}

	public void setDraccAreaCode(String draccAreaCode) {
		this.draccAreaCode = draccAreaCode;
	}

	public String getCraccAreaCode() {
		return craccAreaCode;
	}

	public void setCraccAreaCode(String craccAreaCode) {
		this.craccAreaCode = craccAreaCode;
	}
	

}
