package net.easipay.cbp.model;

// Generated 2015-7-6 15:57:25 by Hibernate Tools 3.2.2.GA

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Pattern;

/**
 * SacOperHistory generated by hbm2java
 */
@Entity
@Table(name = "SAC_OPER_HISTORY", schema = "SAC_SYN")
public class SacOperHistory implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8506984991570846257L;
	private Long id;
	@Pattern(regexp = "^[1]+\\d{10}+|(\\s&&[^\\f\\n\\r\\t\\v])*$", message="手机号非法")
	private String userId;
	private String userName;
	private String channel;
	private String operType;
	private Date createTime;
	private String loginIp;
	private String memo;

	public SacOperHistory() {
	}

	public SacOperHistory(Long id, String userId, String userName) {
		this.id = id;
		this.userId = userId;
		this.userName = userName;
	}

	public SacOperHistory(Long id, String userId, String userName,
			String channel, String operType, Date createTime, String loginIp,
			String memo) {
		this.id = id;
		this.userId = userId;
		this.userName = userName;
		this.channel = channel;
		this.operType = operType;
		this.createTime = createTime;
		this.loginIp = loginIp;
		this.memo = memo;
	}

	@Id
	@Column(name = "ID", unique = true, nullable = false, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "USER_ID", nullable = false, scale = 0)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "USER_NAME", nullable = false, length = 32)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "CHANNEL", length = 32)
	public String getChannel() {
		return this.channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	@Column(name = "OPER_TYPE", length = 10)
	public String getOperType() {
		return this.operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME", length = 11)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "LOGIN_IP", length = 64)
	public String getLoginIp() {
		return this.loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	@Column(name = "MEMO", length = 64)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
