/**
 * 
 */
package net.easipay.pepp.peps.person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;

/**
 * @author Administrator
 * 用户操作
 */
public class PersonManager {
	
	public static final Logger logger = Logger.getLogger(PersonManager.class);

	/**
	 * 数据源
	 */
	private DataSource dataSource;
	
	//查询语句
	private String querySql;
	
	//更新语句
	private String updateSql;
	
	/**
	 * 更新用户
	 * @param username
	 */
	public void updatePerson(Person person){
		Assert.notNull(this.querySql,"this querySql must be setted!!!");
		Assert.notNull(this.updateSql,"this updateSql must be setted!!!");
		Connection conn = null;
		ResultSet result = null;
		PreparedStatement stat = null;
		try {
			conn = dataSource.getConnection();
//			String querySql = "select p.person_id, p.login_time, p.last_login_time from PEPS_PERSON p where upper(p.email) = ? or p.mobile = ?";
			stat = conn.prepareStatement(this.querySql);
			stat.setString(1, person.getUsername().toUpperCase());
			stat.setString(2, person.getUsername());
			result = stat.executeQuery();
			int rowNum = 0;
			Long personId = null;//用户唯一键
			Timestamp loginTime = null;//用户登录时间
			boolean flag = true;
			while(result.next()){
				if(rowNum > 0){
					logger.warn("登录用户非法");
					flag = false;
					break;
				}
				personId = result.getLong(1);
				loginTime = result.getTimestamp(2);
				rowNum ++;
			}
			stat.close();
			//如果用户合法
			if(flag){
//				String updateSql = "update PEPS_PERSON p set p.login_time = ?, p.last_login_time = ?,p.login_ip = ? where p.person_id = ?";
				stat = conn.prepareStatement(this.updateSql);
				stat.setTimestamp(1, new Timestamp(new Date().getTime()));
				stat.setTimestamp(2, loginTime);
				stat.setString(3, person.getIp());
				stat.setLong(4, personId);
				stat.execute();
				conn.commit();
			}
			stat.close();
		} catch (SQLException e) {
			if(null != conn){
				try {
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		} finally {
			if(null != result){
				try {
					result.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(null != stat){
				try {
					stat.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(null != conn){
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void setQuerySql(String querySql) {
		this.querySql = querySql;
	}

	public void setUpdateSql(String updateSql) {
		this.updateSql = updateSql;
	}
}
