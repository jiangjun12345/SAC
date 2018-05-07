/**
 * 
 */
package net.easipay.cbp.form;

/**
 * @Description: TODO(用一句话描述该文件做什么)
 * @author sun (作者英文名称)
 * @date 2015-7-2 上午10:47:30
 * @version V1.0
 * @jdk v1.6
 * @tomcat v7.0
 */

public class AccountNoticeForm {
	private static final long serialVersionUID = -7334361719575685036L;
	private String recDetailId;
	private String trxSerialNo;
	private String bankSerialNo;

	private String status;
	private String dealType;
	private String dealOper;

	public String getRecDetailId() {
		return recDetailId;
	}

	public void setRecDetailId(String recDetailId) {
		this.recDetailId = recDetailId;
	}

	public String getTrxSerialNo() {
		return trxSerialNo;
	}

	public void setTrxSerialNo(String trxSerialNo) {
		this.trxSerialNo = trxSerialNo;
	}

	public String getBankSerialNo() {
		return bankSerialNo;
	}

	public void setBankSerialNo(String bankSerialNo) {
		this.bankSerialNo = bankSerialNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDealType() {
		return dealType;
	}

	public void setDealType(String dealType) {
		this.dealType = dealType;
	}

	public String getDealOper() {
		return dealOper;
	}

	public void setDealOper(String dealOper) {
		this.dealOper = dealOper;
	}

}
