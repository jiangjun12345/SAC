/**
 * 
 */
package net.easipay.cbp.form;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author jj
 * @date 2015-12-11
 */

public class SacAccountingDailyYueQueryForm implements Serializable
{
    private static final long serialVersionUID = 5183565587873823641L;

    @NotBlank(message = "客户类型")
    @Pattern(regexp = "^|1|2$", message = "客户类型必须为1企业,2个人")
	private String cusType;
	
	@NotBlank(message = "组织机构号不能为空")
    private String orgCode;

    @NotBlank(message = "币种不能为空")
    private String currencyType;

    @NotBlank(message = "业务类型不能为空")
    @Pattern(regexp = "^000|00|20|21|22|23|30|40|41|50|60|70|71|80|90$", message = "业务类型必须为000全部,00东方付,20航付通,21航付通专项 EIR放箱,22航付通专项 放箱打单费,23新航付通专项,30跨境B2C,40代付,41代收,50外汇通,60清算,70速结汇,71速汇通,80关税代付,90行邮税")
    private String bussType;
    
    @NotBlank(message = "子账户类型不能为空")
    @Pattern(regexp = "^000|00|01|02|03|04|05|06|07|08|09|10|11$", message = "子账户类型必须为000全部,00默认,01一般计收账户,02流动资金账户,03海关监管账户,04即期结售汇,05定向专用账户,06保证金账户,07退款专用账户,08付款账户,09冻结账户,10收付专用账户,11结算账户")
    private String subAccountType;
    
    @NotBlank(message = "查询日期不能为空")
    @Pattern(regexp = "^([1-9]\\d{3})(([0][1-9])|([1][0-2]))([0-2]\\d|3[01])$", message = "日期格式必须为yyyyMMdd")
    private String queryDate;
    

	public String getCusType() {
		return cusType;
	}

	public void setCusType(String cusType) {
		this.cusType = cusType;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
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

	public String getQueryDate() {
		return queryDate;
	}

	public void setQueryDate(String queryDate) {
		this.queryDate = queryDate;
	}


	public String getSubAccountType() {
		return subAccountType;
	}

	public void setSubAccountType(String subAccountType) {
		this.subAccountType = subAccountType;
	}
	
	
	
    

}
