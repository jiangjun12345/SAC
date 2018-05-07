package net.easipay.cbp.form;

// Generated 2015-7-2 9:35:55 by Hibernate Tools 3.2.2.GA

import java.math.BigDecimal;
import java.util.Date;

public class FinMxForm implements java.io.Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -6058640542328724484L;
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
	public Long getMxId() {
		return mxId;
	}
	public void setMxId(Long mxId) {
		this.mxId = mxId;
	}
	public Long getPzId() {
		return pzId;
	}
	public void setPzId(Long pzId) {
		this.pzId = pzId;
	}
	public String getCodeId() {
		return codeId;
	}
	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}
	public Integer getDirection() {
		return direction;
	}
	public void setDirection(Integer direction) {
		this.direction = direction;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getDigest() {
		return digest;
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
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	public Date getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(Date tradeTime) {
		this.tradeTime = tradeTime;
	}
	public String getBusiType() {
		return busiType;
	}
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	public String getSerno() {
		return serno;
	}
	public void setSerno(String serno) {
		this.serno = serno;
	}
	public BigDecimal getOpenBal() {
		return openBal;
	}
	public void setOpenBal(BigDecimal openBal) {
		this.openBal = openBal;
	}
	public BigDecimal getCloseBal() {
		return closeBal;
	}
	public void setCloseBal(BigDecimal closeBal) {
		this.closeBal = closeBal;
	}
	public BigDecimal getfDebit() {
		return fDebit;
	}
	public void setfDebit(BigDecimal fDebit) {
		this.fDebit = fDebit;
	}
	public BigDecimal getfCredit() {
		return fCredit;
	}
	public void setfCredit(BigDecimal fCredit) {
		this.fCredit = fCredit;
	}
	public String getPzNo() {
		return pzNo;
	}
	public void setPzNo(String pzNo) {
		this.pzNo = pzNo;
	}
	public Integer getIsShow() {
		return isShow;
	}
	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public String getTrxCode() {
		return trxCode;
	}
	public void setTrxCode(String trxCode) {
		this.trxCode = trxCode;
	}
	public Integer getTotCnt() {
		return totCnt;
	}
	public void setTotCnt(Integer totCnt) {
		this.totCnt = totCnt;
	}
	public BigDecimal getGains() {
		return gains;
	}
	public void setGains(BigDecimal gains) {
		this.gains = gains;
	}
    
    
}
