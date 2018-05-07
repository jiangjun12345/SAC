package net.easipay.cbp.form;



public class FinTaskQueryForm implements java.io.Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3897063305432788358L;

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
