package net.easipay.cbp.view.wss;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.easipay.cbp.form.FinRuleTaskJoinForm;
import net.easipay.cbp.view.AbstractAccountCentralServiceTest;
import net.easipay.dsfc.ApplicationContextInitializer;
import net.easipay.dsfc.ws.jws.JwsClient;
import net.easipay.dsfc.ws.jws.JwsResult;

import org.junit.Assert;
import org.junit.Test;

public class FinTaskWssTests extends AbstractAccountCentralServiceTest
{
    @Test
    public void testJoinRuleFinTask() throws Exception
    {
	    List<FinRuleTaskJoinForm> list = new ArrayList<FinRuleTaskJoinForm>();
	    FinRuleTaskJoinForm form = new FinRuleTaskJoinForm();
	    form.setTrxCode("3302");
	    form.setTrxState("D");
	    form.setTrxSerialNo("T201607251044163098311620208");
	    list.add(form);
	    JwsClient jwsClient = new JwsClient("SAC-AC-0010");
	    JwsResult jwsResult = jwsClient.putParam("finRuleTasks", list).call();

	    Assert.assertEquals(jwsResult.getMessage(), "000000", jwsResult.getCode());
    }
    @Test
    public void testJoinRuleFinTask1() throws Exception
    {
    	JwsClient jwsClient = new JwsClient("DSF-IC-0012");
    	JwsResult jwsResult = jwsClient.putParam("bankNo", "0305").putParam("downloadDate","20180420").call();
    	
    	Assert.assertEquals(jwsResult.getMessage(), "000000", jwsResult.getCode());
    }

    public static void main(String[] args) throws Exception
    {
	ApplicationContextInitializer init = new ApplicationContextInitializer();
	Map<String, String> dsfsConfig = new HashMap<String, String>();
	dsfsConfig.put("protocol", "HTTP");
	dsfsConfig.put("ip", "10.68.5.180");
	dsfsConfig.put("port", "8083");
	dsfsConfig.put("context", "dsf");
	init.setDsfsConfig(dsfsConfig);
	init.initApplicationContext();

	List<FinRuleTaskJoinForm> list = new ArrayList<FinRuleTaskJoinForm>();
	FinRuleTaskJoinForm form = new FinRuleTaskJoinForm();
	form.setTrxCode("1301");
	form.setTrxState("S");
	form.setTrxSerialNo("T21180828164152712170");
	list.add(form);
	JwsClient jwsClient = new JwsClient("SAC-AC-0010");
	jwsClient.putParam("finRuleTasks", list).call();
    }

}
