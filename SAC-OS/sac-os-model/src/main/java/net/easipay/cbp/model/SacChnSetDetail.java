/*
 * SacChnSetDetail.java, Created on  2015-09-10
 * Title: HTSC <br/>
 * Description: <br/>
 * Copyright: Copyright (c)  2015 <br/>
 * @author sydai
 * @version Revision: 1.0, Date: 2015-09-10  17:22:07 
 */
package net.easipay.cbp.model;

import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * @author sydai 
 */
@Entity
@Table(name = "SAC_CHN_SET_DETAIL")
public class SacChnSetDetail implements Serializable {
       
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		private long id;

        private String chnNo;

        private String chnName;

        private String accountNumber;

        private String sacDate;

        private String trxDate;
        
        private String transDate;

        private int totalNum;

        private BigDecimal totalSum;

        private BigDecimal trxCost;

        private String currencyType;

        private Date createTime;

        private Date updateTime;

        private String memo;

        private String payconType;

        private String chnBatchNo;

        private String busiType;

        private String bankNodeCode;

        private String type;
        
        private String sacBankName;


        
        
	public String getTransDate() {
		return transDate;
	}

	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getChnNo() {
		return this.chnNo;
	}

	public void setChnNo(String chnNo) {
		this.chnNo = chnNo;
	}
	
	public String getChnName()
	{
		return chnName;
	}

	public void setChnName(String chnName)
	{
		this.chnName = chnName;
	}

	public String getAccountNumber() {
		return this.accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getSacDate() {
		return this.sacDate;
	}

	public void setSacDate(String sacDate) {
		this.sacDate = sacDate;
	}

	public String getTrxDate() {
		return this.trxDate;
	}

	public void setTrxDate(String trxDate) {
		this.trxDate = trxDate;
	}

	public int getTotalNum() {
		return this.totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}



	public String getCurrencyType() {
		return this.currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
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

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getPayconType() {
		return this.payconType;
	}

	public void setPayconType(String payconType) {
		this.payconType = payconType;
	}

	public String getChnBatchNo() {
		return this.chnBatchNo;
	}

	public void setChnBatchNo(String chnBatchNo) {
		this.chnBatchNo = chnBatchNo;
	}

	public String getBusiType() {
		return this.busiType;
	}

	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}

	public String getBankNodeCode() {
		return this.bankNodeCode;
	}

	public void setBankNodeCode(String bankNodeCode) {
		this.bankNodeCode = bankNodeCode;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSacBankName()
	{
		return sacBankName;
	}

	public void setSacBankName(String sacBankName)
	{
		this.sacBankName = sacBankName;
	}

	public BigDecimal getTotalSum() {
		return totalSum;
	}

	public void setTotalSum(BigDecimal totalSum) {
		this.totalSum = totalSum;
	}

	public BigDecimal getTrxCost() {
		return trxCost;
	}

	public void setTrxCost(BigDecimal trxCost) {
		this.trxCost = trxCost;
	}
	
	
}
