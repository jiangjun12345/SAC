/*
 * SacDffOflCommand.java, Created on  2017-08-23
 * Title: b2b <br/>
 * Description: <br/>
 * Copyright: Copyright (c)  2017 <br/>
 * @author tangjun
 * @version Revision: 1.0, Date: 2017-08-23  17:05:16 
 */
package net.easipay.cbp.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * @author tangjun 
 */
@Entity
@Table(name = "tablename")
@XmlRootElement
public class SacDffOflCommand implements Serializable {
        /**
	 * 
	 */
	private static final long serialVersionUID = 5192029883635552384L;

		private Long id;

        private String ykSerialNo;

        private String skSerialNo;

        private String cmdState;

        private String cmdType;

        private String bussType;

        private BigDecimal payAmount;

        private String payCurrency;

        private String craccCardId;

        private String craccName;

        private String craccNo;

        private String craccNodeCode;

        private String craccBankName;

        private String craccBranchCode;

        private String draccNodeCode;

        private String draccBankName;

        private Date createTime;

        private String reqSpt1;

        private String reqSpt2;

        private String reqSpt3;



	public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

	public String getYkSerialNo() {
		return this.ykSerialNo;
	}

	public void setYkSerialNo(String ykSerialNo) {
		this.ykSerialNo = ykSerialNo;
	}

	public String getSkSerialNo() {
		return this.skSerialNo;
	}

	public void setSkSerialNo(String skSerialNo) {
		this.skSerialNo = skSerialNo;
	}

	public String getCmdState() {
		return this.cmdState;
	}

	public void setCmdState(String cmdState) {
		this.cmdState = cmdState;
	}

	public String getCmdType() {
		return this.cmdType;
	}

	public void setCmdType(String cmdType) {
		this.cmdType = cmdType;
	}

	public String getBussType() {
		return this.bussType;
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
		return this.payCurrency;
	}

	public void setPayCurrency(String payCurrency) {
		this.payCurrency = payCurrency;
	}

	public String getCraccCardId() {
		return this.craccCardId;
	}

	public void setCraccCardId(String craccCardId) {
		this.craccCardId = craccCardId;
	}

	public String getCraccName() {
		return this.craccName;
	}

	public void setCraccName(String craccName) {
		this.craccName = craccName;
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

	public String getCraccBankName() {
		return this.craccBankName;
	}

	public void setCraccBankName(String craccBankName) {
		this.craccBankName = craccBankName;
	}

	public String getCraccBranchCode() {
		return this.craccBranchCode;
	}

	public void setCraccBranchCode(String craccBranchCode) {
		this.craccBranchCode = craccBranchCode;
	}

	public String getDraccNodeCode() {
		return this.draccNodeCode;
	}

	public void setDraccNodeCode(String draccNodeCode) {
		this.draccNodeCode = draccNodeCode;
	}

	public String getDraccBankName() {
		return this.draccBankName;
	}

	public void setDraccBankName(String draccBankName) {
		this.draccBankName = draccBankName;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getReqSpt1() {
		return this.reqSpt1;
	}

	public void setReqSpt1(String reqSpt1) {
		this.reqSpt1 = reqSpt1;
	}

	public String getReqSpt2() {
		return this.reqSpt2;
	}

	public void setReqSpt2(String reqSpt2) {
		this.reqSpt2 = reqSpt2;
	}

	public String getReqSpt3() {
		return this.reqSpt3;
	}

	public void setReqSpt3(String reqSpt3) {
		this.reqSpt3 = reqSpt3;
	}

        
    	public boolean equals(Object o) {
    		// TODO Auto-generated method stub
    		return false;
    	}

    	public int hashCode() {
    		// TODO Auto-generated method stub
    		return 0;
    	}   
}
