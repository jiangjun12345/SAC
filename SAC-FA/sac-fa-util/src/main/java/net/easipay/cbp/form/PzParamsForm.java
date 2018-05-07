package net.easipay.cbp.form;

public class PzParamsForm {

	private String codeId;
	private String code3Id;
	private String code3Name;
	private String currType;
	private String amount;
	private Integer direction;
	
	public String getCode3Id() {
		return code3Id;
	}
	public void setCode3Id(String code3Id) {
		this.code3Id = code3Id;
	}
	public String getCode3Name() {
		return code3Name;
	}
	public void setCode3Name(String code3Name) {
		this.code3Name = code3Name;
	}
	public String getCurrType() {
		return currType;
	}
	public void setCurrType(String currType) {
		this.currType = currType;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public Integer getDirection() {
		return direction;
	}
	public void setDirection(Integer direction) {
		this.direction = direction;
	}
	public String getCodeId() {
		return codeId;
	}
	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}
	
}
