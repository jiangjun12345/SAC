package net.easipay.cbp.model;

import java.util.Date;

public class SacRecBatch implements java.io.Serializable
{
    private static final long serialVersionUID = -4487162789590851542L;
    private Long recBatchId; // 批次ID
    private String recUnionId; // 唯一主键
    private String recType; // 对账类型
    private Date recStartDate; // 对账日期
    private Date recEndDate; // 对账日期
    private String recOper; // 操作人
    private Long recCount; // 对账数据总笔数
    private String recStatus; // 对账状态
    private String chnCode; // 渠道节点
    private String payconType; // 支付渠道类型
    private Date updateTime; // 创建时间
    private Date createTime; // 更新时间
    private String memo; // 备注

    public Long getRecBatchId()
    {
	return recBatchId;
    }

    public void setRecBatchId(Long recBatchId)
    {
	this.recBatchId = recBatchId;
    }

    public String getRecUnionId()
    {
	return recUnionId;
    }

    public void setRecUnionId(String recUnionId)
    {
	this.recUnionId = recUnionId;
    }

    public String getRecType()
    {
	return recType;
    }

    public void setRecType(String recType)
    {
	this.recType = recType;
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

    public String getRecOper()
    {
	return recOper;
    }

    public void setRecOper(String recOper)
    {
	this.recOper = recOper;
    }

    public Long getRecCount()
    {
	return recCount;
    }

    public void setRecCount(Long recCount)
    {
	this.recCount = recCount;
    }

    public String getRecStatus()
    {
	return recStatus;
    }

    public void setRecStatus(String recStatus)
    {
	this.recStatus = recStatus;
    }

    public String getChnCode()
    {
	return chnCode;
    }

    public void setChnCode(String chnCode)
    {
	this.chnCode = chnCode;
    }

    public String getPayconType()
    {
	return payconType;
    }

    public void setPayconType(String payconType)
    {
	this.payconType = payconType;
    }

    public Date getUpdateTime()
    {
	return updateTime;
    }

    public void setUpdateTime(Date updateTime)
    {
	this.updateTime = updateTime;
    }

    public Date getCreateTime()
    {
	return createTime;
    }

    public void setCreateTime(Date createTime)
    {
	this.createTime = createTime;
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
