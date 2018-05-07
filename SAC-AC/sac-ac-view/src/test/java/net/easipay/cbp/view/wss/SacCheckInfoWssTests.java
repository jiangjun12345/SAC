package net.easipay.cbp.view.wss;

import java.math.BigDecimal;

import net.easipay.cbp.form.SacReplacePayReceiveForm;
import net.easipay.cbp.view.AbstractAccountCentralServiceTest;
import net.easipay.dsfc.ws.jws.JwsClient;
import net.easipay.dsfc.ws.jws.JwsResult;

import org.junit.Assert;
import org.junit.Test;

public class SacCheckInfoWssTests extends AbstractAccountCentralServiceTest
{
    @Test
    public void testReceiveSacTransationDetail() throws Exception
    {

		SacReplacePayReceiveForm form = new SacReplacePayReceiveForm();
		form.setTrxSerialNo("T1555325552233331113153");
		form.setCraccCusName("sm");
		form.setCraccNodeCode("ICBC000");
		form.setDraccCardId("000000000000050184");
		form.setDraccNodeCode("CMBC000");
		form.setDraccBankName("民生银行");
		form.setPayCurrency("CNY");
		form.setPayAmount(new BigDecimal(1890.00));
		form.setCusCharge(new BigDecimal(0));
		form.setTrxType("222222");
		
	    JwsClient jwsClient = new JwsClient("SAC-AC-0019");
	    JwsResult jwsResult = jwsClient.putParam("sacReplacePaycheckInfo", form).call();
	    Assert.assertEquals(jwsResult.getMessage(), "000000", jwsResult.getCode());

    }
}
