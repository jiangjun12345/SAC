/**
 * 
 */
package net.easipay.cbp.form;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

public class SacCusParameterValidateForm implements Serializable
{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 8120308996633386181L;

	@NotBlank(message = "组织机构号不能为空")
    private String orgCardId;
    
    @NotBlank(message = "业务类型不能为空")
    @Pattern(regexp = "^00|20|21|22|23|30|40|41|50|60|70|71|80|90$", message = "业务类型必须为00默认,20航付通,21航付通专项 EIR放箱,22航付通专项 放箱打单费,23新航付通专项,30跨境B2C,40代付,41代收,50外汇通,60清算,70速结汇,71速汇通,80关税代付,90行邮税")
    private String bussType;
    
    @NotBlank(message = "币种不能为空")
    private String currency;

	public String getOrgCardId() {
		return orgCardId;
	}

	public void setOrgCardId(String orgCardId) {
		this.orgCardId = orgCardId;
	}

	public String getBussType() {
		return bussType;
	}

	public void setBussType(String bussType) {
		this.bussType = bussType;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

 
}
