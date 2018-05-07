/**
 * 
 */
package net.easipay.pepp.peps.web.sysLog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * @author Administrator
 * 系统日志登录系统
 */
public class SysLogManager {
	
	/**
	 * 数据源
	 */
	private DataSource dataSource;
	
	/**
	 * 保存日志对象
	 * @param log 
	 * 
	 */
	public void saveLog(SystemLog log){
		if(null != log){
			Connection conn = null;
			PreparedStatement stat = null;
			try {
				conn = dataSource.getConnection();
				StringBuilder sb = new StringBuilder();
				sb.append("insert into peps_system_log(SYSTEM_LOG_ID,LOG_TYPE,LOG_SOURCE,LOG_MODE,LOG_INFO,LOG_TIME,SESSION_ID,USER_NAME,TRX_SERIAL_NO,USER_CONTEXT)")
				  .append(" values(seq_system_log.nextval,?,?,?,?,?,?,?,?,?)");
				stat = conn.prepareStatement(sb.toString());
				stat.setString(1, log.getLogType());
				stat.setString(2, log.getLogSource());
				stat.setString(3, log.getLogMode());
				stat.setString(4, log.getLogInfo());
				stat.setDate(5, new java.sql.Date(log.getLogTime().getTime()));
				stat.setString(6, log.getSessionId());
				stat.setString(7, log.getUserName());
				stat.setString(8, log.getTrxSerialNo());
				stat.setString(9, log.getUserContext());
				stat.execute();
				conn.commit();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
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
	}
	
	/**
	 * 删除指定ID的日志
	 * @param logId
	 */
	public void removeLog(long logId){
		Connection conn = null;
		PreparedStatement stat = null;
		try {
			conn = dataSource.getConnection();
			StringBuilder sb = new StringBuilder();
			sb.append("delete from peps_system_log t where t.SYSTEM_LOG_ID = ?");
			stat = conn.prepareStatement(sb.toString());
			stat.setLong(1, logId);
			stat.execute();
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
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

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

}
