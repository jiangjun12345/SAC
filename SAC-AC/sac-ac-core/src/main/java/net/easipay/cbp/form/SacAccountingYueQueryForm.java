/**
 * 
 */
package net.easipay.cbp.form;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author mchen
 * @date 2015-12-11
 */

public class SacAccountingYueQueryForm implements Serializable
{
    private static final long serialVersionUID = 5183565587873823641L;

    @NotBlank(message = "账户类型不能为空")
    @Pattern(regexp = "^0|1$", message = "账户类型必须为0 银行存款，1 客户")
    private String cusType;

    @NotBlank(message = "客户编码不能为空")
    private String cusId;

    @NotBlank(message = "币种不能为空")
    private String currencyType;

    @NotBlank(message = "业务类型不能为空")
    @Pattern(regexp = "^00|20|21|22|23|30|40|41|50|60|70|71|80|90$", message = "业务类型必须为00默认,20航付通,21航付通专项 EIR放箱,22航付通专项 放箱打单费,23新航付通专项,30跨境B2C,40代付,41代收,50外汇通,60清算,70速结汇,71速汇通,80关税代付,90行邮税")    
    private String bussType;

    public String getCusType()
    {
	return cusType;
    }

    public void setCusType(String cusType)
    {
	this.cusType = cusType;
    }

    public String getCusId()
    {
	return cusId;
    }

    public void setCusId(String cusId)
    {
	this.cusId = cusId;
    }

    public String getCurrencyType()
    {
	return currencyType;
    }

    public void setCurrencyType(String currencyType)
    {
	this.currencyType = currencyType;
    }

    public String getBussType()
    {
	return bussType;
    }

    public void setBussType(String bussType)
    {
	this.bussType = bussType;
    }

}
