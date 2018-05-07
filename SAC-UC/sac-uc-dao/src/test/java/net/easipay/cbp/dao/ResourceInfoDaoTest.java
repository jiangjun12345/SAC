/**
 * 
 */
package net.easipay.cbp.dao;

import java.util.List;
import net.easipay.cbp.model.ResourceInfo;

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
public class ResourceInfoDaoTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private ResourceInfoDao resourceInfoDao;

	@Test
	@Rollback(false)
	public void selectResorceByOperId(){
		Long operId =new Long("110");
		String isNeedAuth ="1";
		List<ResourceInfo> isNeedAuthR = resourceInfoDao.selectResourceByOperId(operId,isNeedAuth,null);
		
		List<ResourceInfo> isnotNeedAuthR = resourceInfoDao.selectResourceByOperId(operId,null,null);
		
	
	}
	
	@Test
	@Rollback(false)
	public void selectResourceByRoleId(){
		Long operId =new Long("10");
		String isNeedAuth ="1";
		List<ResourceInfo> isNeedAuthR = resourceInfoDao.selectResourceByRoleId(operId,isNeedAuth,null);
		
		List<ResourceInfo> isnotNeedAuthR = resourceInfoDao.selectResourceByRoleId(operId,null,null);
		for(ResourceInfo resourceInfo : isnotNeedAuthR){
			System.out.println(resourceInfo.getResourceName()+"--"+resourceInfo.getResourceId());
			
			
			 for (ResourceInfo resourceInfo1 : resourceInfo.getChilds()){
					System.out.println(resourceInfo1.getResourceName()+"**"+resourceInfo.getResourceId());
					
			 }
	
		}
	}
	
	@Test
	@Rollback(false)
	public void selectAuthByRoleId(){
		Long roleId =new Long("10");
		
		List<ResourceInfo> isnotNeedAuthR = resourceInfoDao.selectAuthByRoleId(roleId,null);
		
		for(ResourceInfo resourceInfo : isnotNeedAuthR){
			System.out.println(resourceInfo.getResourceName());
			
			
			 for (ResourceInfo resourceInfo1 : resourceInfo.getChilds()){
					System.out.println(resourceInfo1.getResourceName());
					
			 }
	
		}

	}
	
	@Test
	@Rollback(false)
	public void insertResourceInfo(){
		ResourceInfo resourceInfo = new ResourceInfo();
		resourceInfo.setResourceId(new Long("221"));
		
		resourceInfoDao.insertResourceInfo(resourceInfo);

	}
	
	
	
	
}
