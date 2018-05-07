package net.easipay.cbp.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "SAC_REC_DETAIL", schema = "SAC_SYN")
public class SacRecDetail implements java.io.Serializable {

	private static final long serialVersionUID = -8500903038205043404L;
	private Long id;
	private Long recBatchId;
	private String trxSerialNo;
	private String chnCode;
	private String memo;
	private Date trxTime;
	private BigDecimal payAmount;
	private String bankSerialNo;
	private String payconType;
	private String recOper;
	private String diffType;
	private String currencyType;
	private String busiType;
	private Date recStartDate;
	private Date recEndDate;
	private String trxCode;
	public String getTrxCode()
	{
		return trxCode;
	}
	public void setTrxCode(String trxCode)
	{
		this.trxCode = trxCode;
	}
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public Long getRecBatchId()
	{
		return recBatchId;
	}
	public void setRecBatchId(Long recBatchId)
	{
		this.recBatchId = recBatchId;
	}
	public String getTrxSerialNo()
	{
		return trxSerialNo;
	}
	public void setTrxSerialNo(String trxSerialNo)
	{
		this.trxSerialNo = trxSerialNo;
	}
	public String getChnCode()
	{
		return chnCode;
	}
	public void setChnCode(String chnCode)
	{
		this.chnCode = chnCode;
	}
	public String getMemo()
	{
		return memo;
	}
	public void setMemo(String memo)
	{
		this.memo = memo;
	}
	public Date getTrxTime()
	{
		return trxTime;
	}
	public void setTrxTime(Date trxTime)
	{
		this.trxTime = trxTime;
	}
	public BigDecimal getPayAmount()
	{
		return payAmount;
	}
	public void setPayAmount(BigDecimal payAmount)
	{
		this.payAmount = payAmount;
	}
	public String getBankSerialNo()
	{
		return bankSerialNo;
	}
	public void setBankSerialNo(String bankSerialNo)
	{
		this.bankSerialNo = bankSerialNo;
	}
	public String getPayconType()
	{
		return payconType;
	}
	public void setPayconType(String payconType)
	{
		this.payconType = payconType;
	}
	public String getRecOper()
	{
		return recOper;
	}
	public void setRecOper(String recOper)
	{
		this.recOper = recOper;
	}
	public String getDiffType()
	{
		return diffType;
	}
	public void setDiffType(String diffType)
	{
		this.diffType = diffType;
	}
	public String getCurrencyType()
	{
		return currencyType;
	}
	public void setCurrencyType(String currencyType)
	{
		this.currencyType = currencyType;
	}
	public String getBusiType()
	{
		return busiType;
	}
	public void setBusiType(String busiType)
	{
		this.busiType = busiType;
	}
	public Date getRecStartDate()
	{
		return recStartDate;
	}
	public void setRecStartDate(Date recStartDate)
	{
		this.recStartDate = recStartDate;
	}
	public Date getRecEndDate()
	{
		return recEndDate;
	}
	public void setRecEndDate(Date recEndDate)
	{
		this.recEndDate = recEndDate;
	}

}
