/**
 * 
 */
package net.easipay.cbp.form;

import java.util.Date;

/**
 * @Description: TODO(用一句话描述该文件做什么)
 * @author sun (作者英文名称)
 * @date 2015-7-2 上午10:47:30
 * @version V1.0
 * @jdk v1.6
 * @tomcat v7.0
 */

public class UpdateSacTransationForm {
	private static final long serialVersionUID = -7334361719575685036L;
	private String trxState;
	private String trxSerialNo;
	private String etrxSerialNo;
	private Date trxSuccTime;

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

	public String getEtrxSerialNo() {
		return etrxSerialNo;
	}

	public void setEtrxSerialNo(String etrxSerialNo) {
		this.etrxSerialNo = etrxSerialNo;
	}

	public Date getTrxSuccTime() {
		return trxSuccTime;
	}

	public void setTrxSuccTime(Date trxSuccTime) {
		this.trxSuccTime = trxSuccTime;
	}

}
