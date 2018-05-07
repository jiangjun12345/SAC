/**
 * 
 */
package net.easipay.cbp.service;

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
public class PersonServiceTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private PersonService personService;
	
	@Test
	@Rollback(false)
	public void testSavePerson(){
		Person p = new Person();
		p.setPersonName("123456");
		p.setCustomerCode("11111");
		p.setEmail("lzty@ddd.com");
		p.setEmailVerifyFlag("Y");
		p.setIdentifyCode("12345678");
		p.setIdentifyType("1");
		p.setIdentifyVerifyFlag("Y");
		p.setPersonState("Y");
		p.setMobile("1234567890");
		p.setMobileVerifyFlag("Y");
		personService.save(p);
	}
}
