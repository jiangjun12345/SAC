/**
 * 
 */
package net.easipay.cbp.dao;

import net.easipay.cbp.model.UcCusParameter;

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
public class CusParameterDaoTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private CusParameterDao cusParameterDao;
	
	@Test
	@Rollback(false)
	public void insertUcCusParameter(){
		UcCusParameter ucCusParameter = new UcCusParameter();
		ucCusParameter.setCusNo("lmc");
		ucCusParameter.setCusPlatAcc("chnNo");
		cusParameterDao.insertUcCusParameter(ucCusParameter);
	}
	

}