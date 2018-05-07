package net.easipay.cbp.view.wss;

import net.easipay.cbp.view.AbstractAccountCentralServiceTest;
import net.easipay.dsfc.ws.jws.JwsClient;
import net.easipay.dsfc.ws.jws.JwsResult;

import org.junit.Assert;
import org.junit.Test;

public class SacBfjWssTests extends AbstractAccountCentralServiceTest
{
    @Test
    public void testJoinRuleFinTask() throws Exception
    {
	    JwsClient jwsClient = new JwsClient("SAC-AC-0013");
	    JwsResult jwsResult = jwsClient.putParam("bankNodeCode", "CMBC000").call();
	    Assert.assertEquals(jwsResult.getMessage(), "000000", jwsResult.getCode());
    }

}
