/**
 * 
 */
package net.easipay.cbp.form;

/**
 * @Description: TODO(用一句话描述该文件做什么)
 * @author sun (作者英文名称)
 * @date 2015-3-25 上午09:45:05
 * @version V1.0
 * @jdk v1.6
 * @tomcat v7.0
 */

public class SelectTransactionForm {

	private String trxCode;
	private String trxState;
	private String trxSerialNo;
	private String merchantName;
	private String trxStartString;
	private String trxEndString;
	private String draccName;
	private String etrxSerialNo;
	private String billNo;
	private String mobile;
	private String otrxSerialNo;

	private String lastUpdateTimeBegin;
	private String lastUpdateTimeEnd;

	private String identifyCode;
	private String billSerialNo;

	public String getTrxCode() {
		return trxCode;
	}

	public void setTrxCode(String trxCode) {
		this.trxCode = trxCode;
	}

	public String getLastUpdateTimeBegin() {
		return lastUpdateTimeBegin;
	}

	public void setLastUpdateTimeBegin(String lastUpdateTimeBegin) {
		this.lastUpdateTimeBegin = lastUpdateTimeBegin;
	}

	public String getLastUpdateTimeEnd() {
		return lastUpdateTimeEnd;
	}

	public void setLastUpdateTimeEnd(String lastUpdateTimeEnd) {
		this.lastUpdateTimeEnd = lastUpdateTimeEnd;
	}

	public String getTrxStartString() {
		return trxStartString;
	}

	public void setTrxStartString(String trxStartString) {
		this.trxStartString = trxStartString;
	}

	public String getTrxEndString() {
		return trxEndString;
	}

	public void setTrxEndString(String trxEndString) {
		this.trxEndString = trxEndString;
	}

	public String getTrxState() {
		return trxState;
	}

	public void setTrxState(String trxState) {
		this.trxState = trxState;
	}

	public String getTrxSerialNo() {
		return trxSerialNo;
	}

	public void setTrxSerialNo(String trxSerialNo) {
		this.trxSerialNo = trxSerialNo;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public String getOtrxSerialNo() {
		return otrxSerialNo;
	}

	public void setOtrxSerialNo(String otrxSerialNo) {
		this.otrxSerialNo = otrxSerialNo;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getIdentifyCode() {
		return identifyCode;
	}

	public String getDraccName() {
		return draccName;
	}

	public void setDraccName(String draccName) {
		this.draccName = draccName;
	}

	public void setIdentifyCode(String identifyCode) {
		this.identifyCode = identifyCode;
	}

	public String getEtrxSerialNo() {
		return etrxSerialNo;
	}

	public void setEtrxSerialNo(String etrxSerialNo) {
		this.etrxSerialNo = etrxSerialNo;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getBillSerialNo() {
		return billSerialNo;
	}

	public void setBillSerialNo(String billSerialNo) {
		this.billSerialNo = billSerialNo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
