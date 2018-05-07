package net.easipay.cbp.form;

import org.hibernate.validator.constraints.NotBlank;


public class FinTaskQueryForm implements java.io.Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3897063305432788358L;

	@NotBlank(message = "交易流水号不能为空")
    private String serno;
	
	private String status;

	public String getSerno() {
		return serno;
	}

	public void setSerno(String serno) {
		this.serno = serno;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
