/**
 * 
 */
package net.easipay.cbp.dao;

import net.easipay.cbp.model.UcChannelParam;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * @author Administrator
 *
 */

@ContextConfiguration(locations={"classpath:applicationContext_composite.xml"})
@TransactionConfiguration(transactionManager="transactionManager",defaultRollback=false)
public class ChannelParamDaoTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private ChannelParamDao channelParam;
	
	@Test
	@Rollback(false)
	public void insertUcChannelParam(){
		UcChannelParam ucChannelParam = new UcChannelParam();
		ucChannelParam.setAccountName("lmc");
		ucChannelParam.setChnNo("chnNo");
		ucChannelParam.setChnName("chnName");
		channelParam.insertUcChannelParam(ucChannelParam);
	}
	

}