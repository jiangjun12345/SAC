package net.easipay.cbp.view;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

/**
 * @Description: TODO(用一句话描述该文件做什么)
 * @author dell (Cyrus)
 * @date 2015-7-20 下午01:41:38
 * @version V1.0
 * @jdk v1.6
 * @tomcat v7.0
 */
@RunWith(SpringJUnit4ClassRunner.class)  
@WebAppConfiguration(value = "sac-ac-page/src/main/webapp")
@ContextHierarchy({ @ContextConfiguration(name = "parent", locations = "classpath:applicationContext.xml") })
public class AbstractJuintTest
{
    @Autowired
    protected WebApplicationContext wac;

}
