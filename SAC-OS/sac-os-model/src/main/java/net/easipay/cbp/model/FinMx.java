package net.easipay.cbp.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "FIN_MX", schema = "SAC_SYN")
public class FinMx implements java.io.Serializable {

	private static final long serialVersionUID = -3247099678432951122L;
	private Long mxId;
	private Long pzId;
	private String codeId;
	private Integer direction;
	private BigDecimal amount;
	private String digest;
	private Date mxTime;
	private BigDecimal rate;
	private Date tradeTime;
	private String busiType;
	private String serno;
	private BigDecimal openBal;
	private BigDecimal closeBal;
	private BigDecimal fDebit;
	private BigDecimal fCredit;
	private String pzNo;
	private Integer isShow;
	private Long taskId;
	private String trxCode;
	private Integer totCnt;
	private BigDecimal gains; 
	
	public FinMx() {
	}

	public FinMx(Long mxId) {
		this.mxId = mxId;
	}

	public FinMx(Long mxId, Long pzId, String codeId, Integer direction,
			BigDecimal amount, String digest, Date mxTime, BigDecimal rate,
			Date tradeTime, String busiType, String serno, BigDecimal openBal,
			BigDecimal closeBal, BigDecimal fDebit, BigDecimal fCredit,
			String pzNo, Integer isShow, Long taskId, String trxCode) {
		super();
		this.mxId = mxId;
		this.pzId = pzId;
		this.codeId = codeId;
		this.direction = direction;
		this.amount = amount;
		this.digest = digest;
		this.mxTime = mxTime;
		this.rate = rate;
		this.tradeTime = tradeTime;
		this.busiType = busiType;
		this.serno = serno;
		this.openBal = openBal;
		this.closeBal = closeBal;
		this.fDebit = fDebit;
		this.fCredit = fCredit;
		this.pzNo = pzNo;
		this.isShow = isShow;
		this.taskId = taskId;
		this.trxCode = trxCode;
	}

	@Id
	@Column(name = "MX_ID", unique = true, nullable = false, scale = 0)
	public Long getMxId() {
		return this.mxId;
	}

	public void setMxId(Long mxId) {
		this.mxId = mxId;
	}

	@Column(name = "PZ_ID", scale = 0)
	public Long getPzId() {
		return this.pzId;
	}

	public void setPzId(Long pzId) {
		this.pzId = pzId;
	}

	@Column(name = "CODE_ID", length = 31)
	public String getCodeId() {
		return this.codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	@Column(name = "TRX_CODE", length = 4)
	public String getTrxCode() {
		return trxCode;
	}

	public void setTrxCode(String trxCode) {
		this.trxCode = trxCode;
	}

	@Column(name = "DIRECTION", precision = 1, scale = 0)
	public Integer getDirection() {
		return this.direction;
	}

	public void setDirection(Integer direction) {
		this.direction = direction;
	}

	@Column(name = "AMOUNT", precision = 18)
	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Column(name = "DIGEST", length = 500)
	public String getDigest() {
		return this.digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}
	

	public Date getMxTime() {
		return mxTime;
	}

	public void setMxTime(Date mxTime) {
		this.mxTime = mxTime;
	}

	@Column(name = "RATE", precision = 18, scale = 6)
	public BigDecimal getRate() {
		return this.rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TRADE_TIME", length = 11)
	public Date getTradeTime() {
		return this.tradeTime;
	}

	public void setTradeTime(Date tradeTime) {
		this.tradeTime = tradeTime;
	}

	@Column(name = "BUSI_TYPE", length = 4)
	public String getBusiType() {
		return this.busiType;
	}

	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}

	@Column(name = "SERNO", length = 40)
	public String getSerno() {
		return this.serno;
	}

	public void setSerno(String serno) {
		this.serno = serno;
	}

	@Column(name = "OPEN_BAL", precision = 18, scale = 2)
	public BigDecimal getOpenBal() {
		return openBal;
	}

	@Column(name = "PZ_NO", precision = 12, scale = 0)
	public String getPzNo()
	{
		return pzNo;
	}

	public void setPzNo(String pzNo)
	{
		this.pzNo = pzNo;
	}

	public void setOpenBal(BigDecimal openBal) {
		this.openBal = openBal;
	}

	@Column(name = "CLOSE_BAL", precision = 18, scale = 2)
	public BigDecimal getCloseBal() {
		return closeBal;
	}

	public void setCloseBal(BigDecimal closeBal) {
		this.closeBal = closeBal;
	}

	@Column(name = "FDEBIT", precision = 18, scale = 2)
	public BigDecimal getfDebit() {
		return fDebit;
	}

	public void setfDebit(BigDecimal fDebit) {
		this.fDebit = fDebit;
	}

	@Column(name = "FCREDIT", precision = 18, scale = 2)
	public BigDecimal getfCredit() {
		return fCredit;
	}

	public void setfCredit(BigDecimal fCredit) {
		this.fCredit = fCredit;
	}

	@Column(name = "IS_SHOW", precision = 1, scale = 0)
	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	@Column(name = "TASK_ID")
	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Integer getTotCnt() {
		return totCnt;
	}

	public void setTotCnt(Integer totCnt) {
		this.totCnt = totCnt;
	}

	public BigDecimal getGains()
	{
		return gains;
	}

	public void setGains(BigDecimal gains)
	{
		this.gains = gains;
	}

}
