package net.easipay.cbp.form;

public class SacFundTransferCmdForm implements java.io.Serializable
{

	private static final long serialVersionUID = -7741715833392679745L;
	private String amount;//调拨金额
	private String frBankCode;//付款方编号
	private String toBankCode;//收款方编号
	private String trxSerNo;//流水号
	private String merTrxDate;//平台申请时间
	private String remark;//备注
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getFrBankCode() {
		return frBankCode;
	}
	public void setFrBankCode(String frBankCode) {
		this.frBankCode = frBankCode;
	}
	public String getToBankCode() {
		return toBankCode;
	}
	public void setToBankCode(String toBankCode) {
		this.toBankCode = toBankCode;
	}
	public String getTrxSerNo() {
		return trxSerNo;
	}
	public void setTrxSerNo(String trxSerNo) {
		this.trxSerNo = trxSerNo;
	}
	public String getMerTrxDate() {
		return merTrxDate;
	}
	public void setMerTrxDate(String merTrxDate) {
		this.merTrxDate = merTrxDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
