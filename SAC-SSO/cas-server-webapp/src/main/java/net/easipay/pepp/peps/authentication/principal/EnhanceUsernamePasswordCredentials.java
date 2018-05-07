/**
 * 
 */
package net.easipay.pepp.peps.authentication.principal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jasig.cas.authentication.principal.UsernamePasswordCredentials;

/**
 * @author Administrator
 * 加入验证码的凭证
 */
public class EnhanceUsernamePasswordCredentials extends UsernamePasswordCredentials {

	/**
	 * unique ID
	 */
	private static final long serialVersionUID = 482139192033337807L;

	/**
	 * 验证码
	 */
	@NotNull
	@Size(min=1,message="required.authCode")
	private String authCode;

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
}
