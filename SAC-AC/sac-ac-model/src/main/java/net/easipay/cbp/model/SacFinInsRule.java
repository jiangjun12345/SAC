package net.easipay.cbp.model;

public class SacFinInsRule implements java.io.Serializable
{
    private static final long serialVersionUID = -4379686424868310191L;
    private String id; // 主键
    private String trxCode; // 交易代码
    private String trxName; // 交易名称
    private String trxState; // 交易状态
    private Integer step; // 步骤参考交易文档
    private String paramsTempType; // 参数模板类型
    private String paramsTemp; // 参数模板
    private String digestTemp; // 摘要模板
    private String debitRule;  //借方代码规则
    private String creditRule; //贷方代码规则
    private String currencyRule; //币种规则
    private String debitTemp;  //借方科目模板
    private String creditTemp; //贷方科目模板
    private String processType ;
    private String memo; // 备注

    public String getId()
    {
	return id;
    }

    public void setId(String id)
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

    public String getTrxState()
    {
	return trxState;
    }

    public void setTrxState(String trxState)
    {
	this.trxState = trxState;
    }

    public Integer getStep()
    {
	return step;
    }

    public void setStep(Integer step)
    {
	this.step = step;
    }

    public String getParamsTempType()
    {
	return paramsTempType;
    }

    public void setParamsTempType(String paramsTempType)
    {
	this.paramsTempType = paramsTempType;
    }

    public String getParamsTemp()
    {
	return paramsTemp;
    }

    public void setParamsTemp(String paramsTemp)
    {
	this.paramsTemp = paramsTemp;
    }

    public String getDigestTemp()
    {
	return digestTemp;
    }

    public void setDigestTemp(String digestTemp)
    {
	this.digestTemp = digestTemp;
    }

    public String getMemo()
    {
	return memo;
    }

    public void setMemo(String memo)
    {
	this.memo = memo;
    }

    public String getDebitRule()
    {
	return debitRule;
    }

    public void setDebitRule(String debitRule)
    {
	this.debitRule = debitRule;
    }

    public String getCreditRule()
    {
	return creditRule;
    }

    public void setCreditRule(String creditRule)
    {
	this.creditRule = creditRule;
    }

    public String getCurrencyRule()
    {
	return currencyRule;
    }

    public void setCurrencyRule(String currencyRule)
    {
	this.currencyRule = currencyRule;
    }

    public String getDebitTemp()
    {
        return debitTemp;
    }

    public void setDebitTemp(String debitTemp)
    {
        this.debitTemp = debitTemp;
    }

    public String getCreditTemp()
    {
        return creditTemp;
    }

    public void setCreditTemp(String creditTemp)
    {
        this.creditTemp = creditTemp;
    }

    public String getProcessType()
    {
        return processType;
    }

    public void setProcessType(String processType)
    {
        this.processType = processType;
    }
}
