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

public class SelectTrxReconForm {

	private String srcNcode;
	private String date;
	private String accountType;

	public String getSrcNcode() {
		return srcNcode;
	}

	public void setSrcNcode(String srcNcode) {
		this.srcNcode = srcNcode;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

}
