package net.easipay.cbp.model;

public class SacTrxCodeRule implements java.io.Serializable
{
    private static final long serialVersionUID = -4379686424868310191L;
    private Long id;
    private String trxCode;
    private String trxName;
    private String currencyRule;
    private String draccRule;
    private String craccRule;
    private String memo;

    public Long getId()
    {
	return id;
    }

    public void setId(Long id)
    {
	this.id = id;
    }

    public String getTrxCode()
    {
	return trxCode;
    }

    public void setTrxCode(String trxCode)
    {
	this.trxCode = trxCode;
    }

    public String getTrxName()
    {
	return trxName;
    }

    public void setTrxName(String trxName)
    {
	this.trxName = trxName;
    }

    public String getCurrencyRule()
    {
	return currencyRule;
    }

    public void setCurrencyRule(String currencyRule)
    {
	this.currencyRule = currencyRule;
    }

    public String getDraccRule()
    {
	return draccRule;
    }

    public void setDraccRule(String draccRule)
    {
	this.draccRule = draccRule;
    }

    public String getCraccRule()
    {
	return craccRule;
    }

    public void setCraccRule(String craccRule)
    {
	this.craccRule = craccRule;
    }

    public String getMemo()
    {
	return memo;
    }

    public void setMemo(String memo)
    {
	this.memo = memo;
    }

}
