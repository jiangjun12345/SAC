/*
 * SacRecFileParam.java, Created on  2015-07-24
 * Title: HTSC <br/>
 * Description: <br/>
 * Copyright: Copyright (c)  2015 <br/>
 * @author sydai
 * @version Revision: 1.0, Date: 2015-07-24  13:08:07 
 */
package net.easipay.cbp.model;

import java.util.Date;
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author sydai
 */
@Entity
@Table(name = "SAC_REC_FILE_PARAM",schema = "SAC_SYN")
public class SacRecFileParam implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1448111979727098642L;
	private Long id;
	private String chnCode;
	private String chnName;
	private String bankNodeCode;
	private String recFlag;
	private String payconType;
	private String isValidFlag;
	private Date createTime;
	private String memo;
	private String recType;

	public SacRecFileParam()
	{
	}

	public SacRecFileParam(Long id, String chnCode, String chnName, String bankNodeCode, String recFlag, String payconType, String isValidFlag, Date createTime, String memo, String recType)
	{
		this.id = id;
		this.chnCode = chnCode;
		this.chnName = chnName;
		this.bankNodeCode = bankNodeCode;
		this.recFlag = recFlag;
		this.payconType = payconType;
		this.isValidFlag = isValidFlag;
		this.createTime = createTime;
		this.memo = memo;
		this.recType = recType;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getChnCode()
	{
		return chnCode;
	}

	public void setChnCode(String chnCode)
	{
		this.chnCode = chnCode;
	}

	public String getChnName()
	{
		return this.chnName;
	}

	public void setChnName(String chnName)
	{
		this.chnName = chnName;
	}

	public String getBankNodeCode()
	{
		return this.bankNodeCode;
	}

	public void setBankNodeCode(String bankNodeCode)
	{
		this.bankNodeCode = bankNodeCode;
	}

	public String getRecFlag()
	{
		return this.recFlag;
	}

	public void setRecFlag(String recFlag)
	{
		this.recFlag = recFlag;
	}

	public String getPayconType()
	{
		return this.payconType;
	}

	public void setPayconType(String payconType)
	{
		this.payconType = payconType;
	}

	public String getIsValidFlag()
	{
		return this.isValidFlag;
	}

	public void setIsValidFlag(String isValidFlag)
	{
		this.isValidFlag = isValidFlag;
	}

	public Date getCreateTime()
	{
		return this.createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public String getMemo()
	{
		return this.memo;
	}

	public void setMemo(String memo)
	{
		this.memo = memo;
	}

	public String getRecType()
	{
		return this.recType;
	}

	public void setRecType(String recType)
	{
		this.recType = recType;
	}

}
