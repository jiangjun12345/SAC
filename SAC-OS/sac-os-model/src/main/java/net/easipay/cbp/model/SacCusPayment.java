package net.easipay.cbp.model;

import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "SAC_CUS_PAYMENT", schema = "SAC_SYN")
public class SacCusPayment implements Serializable {
        
	/**
	 * 
	 */
	private static final long serialVersionUID = -1975551287119934553L;
	private Long id;
    private String cusNo;
    private String cusName;
    private String trxDate;
    private String sacDate;
    private String bussType;
    private String trxType;
    private int totalNum;
    private BigDecimal totalAmount;
    private String currencyType;
    private int fees;
    private BigDecimal sacAmount;
    private Date createTime;
    private Date updateTime;
    private String memo;
    private String sacSign;
    private String cusBatchNo;
    private String setBatchNo;

	public SacCusPayment()
	{
	}

	public SacCusPayment(Long id, String cusNo, String cusName, String trxDate, String sacDate, String bussType, String trxType, int totalNum, BigDecimal totalAmount, String currencyType, int fees,
			BigDecimal sacAmount, Date createTime, String memo, Date updateTime, String sacSign, String cusBatchNo, String setBatchNo)
	{
		this.id = id;
		this.cusNo = cusNo;
		this.cusName = cusName;
		this.trxDate = trxDate;
		this.sacDate = sacDate;
		this.bussType = bussType;
		this.trxType = trxType;
		this.totalNum = totalNum;
		this.totalAmount = totalAmount;
		this.currencyType = currencyType;
		this.fees = fees;
		this.sacAmount = sacAmount;
		this.createTime = createTime;
		this.memo = memo;
		this.updateTime = updateTime;
		this.sacSign = sacSign;
		this.cusBatchNo = cusBatchNo;
		this.setBatchNo = setBatchNo;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getCusNo() {
		return this.cusNo;
	}

	public void setCusNo(String cusNo) {
		this.cusNo = cusNo;
	}

	public String getCusName() {
		return this.cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public String getTrxDate() {
		return this.trxDate;
	}

	public void setTrxDate(String trxDate) {
		this.trxDate = trxDate;
	}

	public String getSacDate() {
		return this.sacDate;
	}

	public void setSacDate(String sacDate) {
		this.sacDate = sacDate;
	}

	public String getBussType() {
		return this.bussType;
	}

	public void setBussType(String bussType) {
		this.bussType = bussType;
	}

	public String getTrxType() {
		return this.trxType;
	}

	public void setTrxType(String trxType) {
		this.trxType = trxType;
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

	public int getFees() {
		return this.fees;
	}

	public void setFees(int fees) {
		this.fees = fees;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getSacSign() {
		return this.sacSign;
	}

	public void setSacSign(String sacSign) {
		this.sacSign = sacSign;
	}

	public String getCusBatchNo() {
		return this.cusBatchNo;
	}

	public void setCusBatchNo(String cusBatchNo) {
		this.cusBatchNo = cusBatchNo;
	}

	public String getSetBatchNo() {
		return this.setBatchNo;
	}

	public void setSetBatchNo(String setBatchNo) {
		this.setBatchNo = setBatchNo;
	}

	public BigDecimal getTotalAmount()
	{
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount)
	{
		this.totalAmount = totalAmount;
	}

	public BigDecimal getSacAmount()
	{
		return sacAmount;
	}

	public void setSacAmount(BigDecimal sacAmount)
	{
		this.sacAmount = sacAmount;
	}
    
}
