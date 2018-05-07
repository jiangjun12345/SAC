package net.easipay.cbp.form;

// Generated 2015-7-2 9:35:55 by Hibernate Tools 3.2.2.GA

import java.math.BigDecimal;
import java.util.Date;

public class FinMxRtnForm implements java.io.Serializable
{

    private static final long serialVersionUID = -3247099678432951122L;
	private String codeId;
    private Date mxTime;
    private Date tradeTime;
    private String serno;
    private BigDecimal openBal;
    private BigDecimal closeBal;
    private BigDecimal fDebit;
    private BigDecimal fCredit;
    private Integer totCnt;
	public String getCodeId() {
		return codeId;
	}
	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}
	public Date getMxTime() {
		return mxTime;
	}
	public void setMxTime(Date mxTime) {
		this.mxTime = mxTime;
	}
	public Date getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(Date tradeTime) {
		this.tradeTime = tradeTime;
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
	public Integer getTotCnt() {
		return totCnt;
	}
	public void setTotCnt(Integer totCnt) {
		this.totCnt = totCnt;
	}
    
}
