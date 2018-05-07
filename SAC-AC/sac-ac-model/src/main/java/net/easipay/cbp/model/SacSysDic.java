package net.easipay.cbp.model;

// Generated 2015-7-6 15:57:25 by Hibernate Tools 3.2.2.GA

public class SacSysDic implements java.io.Serializable
{
    private static final long serialVersionUID = 5125024011141342226L;
    private String dicCode;
    private String dicDesc;
    private String dicType;
    private String dicTypeDesc;
    private String parentCode;
    private String memo;

    public String getDicDesc()
    {
	return dicDesc;
    }

    public void setDicDesc(String dicDesc)
    {
	this.dicDesc = dicDesc;
    }

    public String getDicTypeDesc()
    {
	return dicTypeDesc;
    }

    public void setDicTypeDesc(String dicTypeDesc)
    {
	this.dicTypeDesc = dicTypeDesc;
    }

    public String getParentCode()
    {
	return parentCode;
    }

    public void setParentCode(String parentCode)
    {
	this.parentCode = parentCode;
    }

    public String getMemo()
    {
	return memo;
    }

    public void setMemo(String memo)
    {
	this.memo = memo;
    }

    public String getDicCode()
    {
	return dicCode;
    }

    public void setDicCode(String dicCode)
    {
	this.dicCode = dicCode;
    }

    public String getDicType()
    {
	return dicType;
    }

    public void setDicType(String dicType)
    {
	this.dicType = dicType;
    }

}
