/**
 * 
 */
package net.easipay.pepp.peps.person;

/**
 * @author Administrator
 * 用户
 */
public class Person {
	
	/**
	 * 登录的用户名
	 */
	public String username;
	
	/**
	 * 登录的ip地址
	 */
	public String ip;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
