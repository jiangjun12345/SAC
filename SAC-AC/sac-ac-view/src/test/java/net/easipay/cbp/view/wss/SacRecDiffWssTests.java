package net.easipay.cbp.view.wss;

import java.util.HashMap;
import java.util.Map;

import net.easipay.cbp.util.DateUtil;
import net.easipay.cbp.view.AbstractAccountCentralServiceTest;
import net.easipay.dsfc.ws.jws.JwsClient;
import net.easipay.dsfc.ws.jws.JwsResult;

import org.junit.Assert;
import org.junit.Test;

public class SacRecDiffWssTests extends AbstractAccountCentralServiceTest
{
    @Test
    public void testQuerySacRecDifferencesList() throws Exception
    {
	Map<String, Object> params = new HashMap<String, Object>();
	params.put("payconType", "2");
	params.put("recStartDate", DateUtil.parse("20140110235959","yyyyMMddHHmmss"));
	params.put("recEndDate", DateUtil.parse("20171010235959","yyyyMMddHHmmss"));
	params.put("status", "N");
	params.put("recOper", "000001");
	
	JwsClient jwsClient = new JwsClient("SAC-AC-0008");
	JwsResult jwsResult = jwsClient.putParam(params).call();
	Assert.assertEquals(jwsResult.getMessage(),"000000", jwsResult.getCode());
    }

    @Test
    public void testUpdateSacRecDifferences() throws Exception
    {
	Map<String, Object> params = new HashMap<String, Object>();
	params.put("recDetailId","1437989515269100001");
	params.put("trxSerialNo","T20150929100258772971111079999");
	params.put("bankSerialNo","wp14999999999999992");
	params.put("recStartDate", DateUtil.parse("20141010235959","yyyyMMddHHmmss"));
	params.put("recEndDate", DateUtil.parse("20141010235959","yyyyMMddHHmmss"));
	params.put("dealType","2");
	params.put("dealOper","00001");
	params.put("status","S");
	JwsClient jwsClient = new JwsClient("SAC-AC-0009");
	JwsResult jwsResult = jwsClient.putParam(params).call();
	Assert.assertEquals(jwsResult.getMessage(),"000000", jwsResult.getCode());
    }

}
