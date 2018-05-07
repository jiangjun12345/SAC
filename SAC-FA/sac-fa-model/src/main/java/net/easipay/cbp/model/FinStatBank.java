package net.easipay.cbp.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FIN_STAT_BANK", schema = "SAC_SYN")
public class FinStatBank implements java.io.Serializable {

	private static final long serialVersionUID = 2914250697776908539L;

	private Long statBankId;
	private String codeId;
	private BigDecimal openBal;
	private BigDecimal fDebit;
	private BigDecimal fCredit;
	private BigDecimal closeBal;
	private BigDecimal amount;
	private Integer direction;
	private Date statTime;

	public FinStatBank() {
		super();
	}

	public FinStatBank(Long statBankId, String codeId, BigDecimal openBal,
			BigDecimal fDebit, BigDecimal fCredit, BigDecimal closeBal,
			BigDecimal amount, Integer direction, Date statTime) {
		super();
		this.statBankId = statBankId;
		this.codeId = codeId;
		this.openBal = openBal;
		this.fDebit = fDebit;
		this.fCredit = fCredit;
		this.closeBal = closeBal;
		this.amount = amount;
		this.direction = direction;
		this.statTime = statTime;
	}

	@Id
	@Column(name = "STAT_BANK_ID", unique = true, nullable = false, scale = 0)
	public Long getStatBankId() {
		return statBankId;
	}

	public void setStatBankId(Long statBankId) {
		this.statBankId = statBankId;
	}
	
	@Column(name = "CODE_ID", length = 31)
	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}
	@Column(name = "OPEN_BAL", precision = 18)
	public BigDecimal getOpenBal() {
		return openBal;
	}

	public void setOpenBal(BigDecimal openBal) {
		this.openBal = openBal;
	}
	@Column(name = "FDEBIT", precision = 18)
	public BigDecimal getfDebit() {
		return fDebit;
	}

	public void setfDebit(BigDecimal fDebit) {
		this.fDebit = fDebit;
	}
	
	@Column(name = "FCREDIT", precision = 18)
	public BigDecimal getfCredit() {
		return fCredit;
	}

	public void setfCredit(BigDecimal fCredit) {
		this.fCredit = fCredit;
	}
	
	@Column(name = "CLOSE_BAL", precision = 18)
	public BigDecimal getCloseBal() {
		return closeBal;
	}

	public void setCloseBal(BigDecimal closeBal) {
		this.closeBal = closeBal;
	}
	@Column(name = "AMOUNT", precision = 18)
	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	@Column(name = "DIRECTION", precision = 18)
	public Integer getDirection() {
		return direction;
	}

	public void setDirection(Integer direction) {
		this.direction = direction;
	}
	
	@Column(name = "STAT_TIME", precision = 18)
	public Date getStatTime() {
		return statTime;
	}

	public void setStatTime(Date statTime) {
		this.statTime = statTime;
	}

}
