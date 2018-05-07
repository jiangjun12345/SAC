/**
 * 
 */
package net.easipay.cbp.form;

import java.io.Serializable;

public class SacChannelParamQueryForm implements Serializable
{
    private static final long serialVersionUID = -1127272793721496745L;
    private String chnCode;
    private String chnType;
    private String currencyType;
    private String isValidFlag;

    public String getChnCode()
    {
        return chnCode;
    }

    public void setChnCode(String chnCode)
    {
        this.chnCode = chnCode;
    }

    public String getChnType()
    {
        return chnType;
    }

    public void setChnType(String chnType)
    {
        this.chnType = chnType;
    }

    public String getCurrencyType()
    {
        return currencyType;
    }

    public void setCurrencyType(String currencyType)
    {
        this.currencyType = currencyType;
    }

    public String getIsValidFlag()
    {
        return isValidFlag;
    }

    public void setIsValidFlag(String isValidFlag)
    {
        this.isValidFlag = isValidFlag;
    }

}
