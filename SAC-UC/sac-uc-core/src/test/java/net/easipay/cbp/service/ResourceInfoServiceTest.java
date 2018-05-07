/**
 * 
 */
package net.easipay.cbp.service;

import java.util.ArrayList;
import java.util.List;

import net.easipay.cbp.model.ResourceInfo;
import net.easipay.cbp.util.TreeHelper;
import net.sf.json.JSONObject;

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
public class ResourceInfoServiceTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private ResourceInfoService resourceInfoService;
	

		@Test
		@Rollback(false)
		public void selectResorceByOperId(){
			Long operId =new Long("110");
			List<ResourceInfo> l = resourceInfoService.selectResourceByOperId(operId,null,null);
			
			List<ResourceInfo> le = resourceInfoService.selectResourceByOperId(operId,"1",null);
		}
		
		@Test
		@Rollback(false)
		public void selectResourceByRoleId(){
			Long operId =new Long("10");
			String isNeedAuth ="1";
			List<ResourceInfo> isNeedAuthR = resourceInfoService.selectResourceByRoleId(operId,isNeedAuth,null);
			
			List<ResourceInfo> isnotNeedAuthR = resourceInfoService.selectResourceByRoleId(operId,null,null);
			
			List<ResourceInfo> list = new ArrayList<ResourceInfo>();
			resourceInfoService.changeList(isnotNeedAuthR, list);
			System.out.println(list.size());
			for(ResourceInfo resourceInfo : list){
				System.out.println("1="+resourceInfo.getResourceName());
				
				
				 for (ResourceInfo resourceInfo1 : resourceInfo.getChilds()){
						System.out.println("2="+resourceInfo1.getResourceName());
						
				 }
		
			}
			
		}
		
		@Test
		@Rollback(false)
		public void selectAuthByRoleId(){
			Long roleId =new Long("10");
			
			List<ResourceInfo> isnotNeedAuthR = resourceInfoService.selectAuthByRoleId(roleId,null);

		}
		
		@Test
		@Rollback(false)
		public void getResourceInfosById(){
			Long id =new Long("11");
			ResourceInfo l = resourceInfoService.getResourceInfosById(id);

		}
		@Test
		@Rollback(false)
		public void selectResourceByOperId1(){
			Long operId =new Long("10");
			String isNeedAuth =null;
			List<ResourceInfo> treeNodeList = resourceInfoService.selectResourceByRoleId(operId,isNeedAuth,null);


		}
		
		
		@Test
		@Rollback(false)
		public void insertResourceInfos(){
			ResourceInfo resourceInfo = new ResourceInfo();
			resourceInfo.setResourceId(new Long("221"));
			resourceInfo.setDicCode("2211");
			resourceInfo.setResourceName("ceshi");
			
			resourceInfoService.insertResourceInfos(resourceInfo);

		}
		
		
		
		


		
	}
