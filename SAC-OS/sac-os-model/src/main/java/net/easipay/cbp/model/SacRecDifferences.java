package net.easipay.cbp.model;

// Generated 2015-7-6 15:57:25 by Hibernate Tools 3.2.2.GA

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * SacRecDifferences generated by hbm2java
 */
@Entity
@Table(name = "SAC_REC_DIFFERENCES", schema = "SAC_SYN")
public class SacRecDifferences implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1935819764447098046L;
	private Long id;
	private Long recBatchId;
	private Long recDetailId;
	private String trxSerialNo;
	private Date trxTime;
	private BigDecimal payAmount;
	private String currencyType;
	private String bankSerialNo;
	private String chnCode;
	private String payconType;
	private String recDiffType;
	private String recDiffDesc;
	private String status;
	private String dealType;
	private String dealOper;
	private Date createTime;
	private Date updateTime;
	private String memo;
	private String oriTrxState;
	private BigDecimal oriInnConAmount;
	private BigDecimal oriChaConAmount;
	private String recOper;
	private Date recStartDate;
	private Date recEndDate;
	private String trxCode;
	private String supplementFlag;
	
	public String getTrxCode()
	{
		return trxCode;
	}

	public void setTrxCode(String trxCode)
	{
		this.trxCode = trxCode;
	}

	public SacRecDifferences() {
	}

	public SacRecDifferences(Long id) {
		this.id = id;
	}

	public SacRecDifferences(Long id, Long recDetailId,
			String recStartDate,String recEndDate, String trxSerialNo, String recDiffType,
			String recDiffDesc, String chnCode, String status,
			String dealType, String dealOper, Date createTime, String memo,
			Long recBatchId, Date trxTime,
			BigDecimal payAmount, String bankSerialNo, String payconType,
			Date updateTime) {
		this.id = id;
		this.recDetailId = recDetailId;
		this.trxSerialNo = trxSerialNo;
		this.recDiffType = recDiffType;
		this.recDiffDesc = recDiffDesc;
		this.chnCode = chnCode;
		this.status = status;
		this.dealType = dealType;
		this.dealOper = dealOper;
		this.createTime = createTime;
		this.memo = memo;
		this.recBatchId = recBatchId;
		this.trxTime = trxTime;
		this.payAmount = payAmount;
		this.bankSerialNo = bankSerialNo;
		this.payconType = payconType;
		this.updateTime = updateTime;
	}

	@Id
	@Column(name = "ID", unique = true, nullable = false, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "REC_DETAIL_ID", scale = 0)
	public Long getRecDetailId() {
		return this.recDetailId;
	}

	public void setRecDetailId(Long recDetailId) {
		this.recDetailId = recDetailId;
	}


	@Column(name = "TRX_SERIAL_NO", length = 32)
	public String getTrxSerialNo() {
		return this.trxSerialNo;
	}

	public void setTrxSerialNo(String trxSerialNo) {
		this.trxSerialNo = trxSerialNo;
	}

	@Column(name = "REC_DIFF_TYPE", length = 4)
	public String getRecDiffType() {
		return this.recDiffType;
	}

	public void setRecDiffType(String recDiffType) {
		this.recDiffType = recDiffType;
	}

	@Column(name = "REC_DIFF_DESC", length = 128)
	public String getRecDiffDesc() {
		return this.recDiffDesc;
	}

	public void setRecDiffDesc(String recDiffDesc) {
		this.recDiffDesc = recDiffDesc;
	}

	@Column(name = "STATUS", length = 2)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "DEAL_TYPE", length = 4)
	public String getDealType() {
		return this.dealType;
	}

	public void setDealType(String dealType) {
		this.dealType = dealType;
	}

	@Column(name = "DEAL_OPER", length = 20)
	public String getDealOper() {
		return this.dealOper;
	}

	public void setDealOper(String dealOper) {
		this.dealOper = dealOper;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME", length = 11)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "MEMO", length = 100)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Column(name = "REC_BATCH_ID", scale = 0)
	public Long getRecBatchId() {
		return this.recBatchId;
	}

	public void setRecBatchId(Long recBatchId) {
		this.recBatchId = recBatchId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TRX_TIME", length = 11)
	public Date getTrxTime() {
		return this.trxTime;
	}

	public void setTrxTime(Date trxTime) {
		this.trxTime = trxTime;
	}

	@Column(name = "PAY_AMOUNT")
	public BigDecimal getPayAmount() {
		return this.payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	@Column(name = "BANK_SERIAL_NO", length = 32)
	public String getBankSerialNo() {
		return this.bankSerialNo;
	}

	public void setBankSerialNo(String bankSerialNo) {
		this.bankSerialNo = bankSerialNo;
	}

	@Column(name = "PAYCON_TYPE", length = 4)
	public String getPayconType() {
		return this.payconType;
	}

	public void setPayconType(String payconType) {
		this.payconType = payconType;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATE_TIME", length = 11)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getChnCode()
	{
		return chnCode;
	}

	public void setChnCode(String chnCode)
	{
		this.chnCode = chnCode;
	}

	public String getOriTrxState()
	{
		return oriTrxState;
	}

	public void setOriTrxState(String oriTrxState)
	{
		this.oriTrxState = oriTrxState;
	}

	public BigDecimal getOriInnConAmount()
	{
		return oriInnConAmount;
	}

	public void setOriInnConAmount(BigDecimal oriInnConAmount)
	{
		this.oriInnConAmount = oriInnConAmount;
	}

	public BigDecimal getOriChaConAmount()
	{
		return oriChaConAmount;
	}

	public void setOriChaConAmount(BigDecimal oriChaConAmount)
	{
		this.oriChaConAmount = oriChaConAmount;
	}

	public String getRecOper()
	{
		return recOper;
	}

	public void setRecOper(String recOper)
	{
		this.recOper = recOper;
	}

	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	public Date getRecStartDate() {
		return recStartDate;
	}

	public void setRecStartDate(Date recStartDate) {
		this.recStartDate = recStartDate;
	}

	public Date getRecEndDate() {
		return recEndDate;
	}

	public void setRecEndDate(Date recEndDate) {
		this.recEndDate = recEndDate;
	}

	public String getSupplementFlag() {
		return supplementFlag;
	}

	public void setSupplementFlag(String supplementFlag) {
		this.supplementFlag = supplementFlag;
	}
	
	

}