/**
 * 
 */
package net.easipay.cbp.dao;

import net.easipay.cbp.model.Person;

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
public class PersonDaoTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private PersonDao personDao;
	
	@Test
	@Rollback(false)
	public void testSavePerson(){
		Person p = new Person();
		p.setPersonName("123456");
		p.setPersonState("1");
		personDao.save(p);
	}
	
	@Test
	@Rollback(false)
	public void getPerson(){
		personDao.getAll();
	}

}