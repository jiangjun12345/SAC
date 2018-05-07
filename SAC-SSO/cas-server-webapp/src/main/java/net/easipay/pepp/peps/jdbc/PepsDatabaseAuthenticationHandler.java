/**
 * 
 */
package net.easipay.pepp.peps.jdbc;

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import net.easipay.pepp.peps.util.Constants;

import org.apache.commons.lang.StringUtils;
import org.jasig.cas.adaptors.jdbc.AbstractJdbcUsernamePasswordAuthenticationHandler;
import org.jasig.cas.authentication.handler.AuthenticationException;
import org.jasig.cas.authentication.principal.UsernamePasswordCredentials;
import org.springframework.dao.IncorrectResultSizeDataAccessException;


/**
 * @author Administrator
 * 对私支付专用数据库查询
 */
public class PepsDatabaseAuthenticationHandler extends AbstractJdbcUsernamePasswordAuthenticationHandler {

	@NotNull
    private String sql;
	
	@Override
	protected boolean authenticateUsernamePasswordInternal(UsernamePasswordCredentials credentials) throws AuthenticationException {
		final String username = getPrincipalNameTransformer().transform(credentials.getUsername());
        final String password = this.getPasswordEncoder().encode(credentials.getPassword());
        
        try {
        	Map<String, Object> result = getJdbcTemplate().queryForMap(this.sql, StringUtils.upperCase(username), username);
        	
        	final String dbPassword = (String)result.get("PASSWORD");
        	final String status = (String)result.get("STATUS");
        	
        	boolean is_same = dbPassword.equals(password);
        	
            if(badBlacklistAuthenticated(is_same , status))
            {
            	throw BadBlacklistAuthenticationException.ERROR;
            }
            
            return is_same;
        } catch (final IncorrectResultSizeDataAccessException e) {
            e.printStackTrace();
            return false;
        }
	}
	
	private boolean badBlacklistAuthenticated(boolean is_same , String status){
		return is_same && Constants.UC_OPERATOR_STATUS_B.equals(status);
	}
	

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

}
