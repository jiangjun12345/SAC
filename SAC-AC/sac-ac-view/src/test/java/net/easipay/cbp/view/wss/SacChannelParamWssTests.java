package net.easipay.cbp.view.wss;

import net.easipay.cbp.form.SacChannelParamHandleForm;
import net.easipay.cbp.form.SacChannelParamQueryForm;
import net.easipay.cbp.view.AbstractAccountCentralServiceTest;
import net.easipay.dsfc.ws.jws.JwsClient;
import net.easipay.dsfc.ws.jws.JwsResult;

import org.junit.Assert;
import org.junit.Test;

public class SacChannelParamWssTests extends AbstractAccountCentralServiceTest
{
    @Test
    public void testReceiveSacChannelParam() throws Exception
    {
	SacChannelParamHandleForm form = new SacChannelParamHandleForm();
	form.setChnCode("ICBC0003");
	form.setBankNodeCode("ICBC0003");
	form.setChnType("1");
	form.setChnName("银联");
	form.setSacBankName("中国工商银行浦东支行");
	form.setAccountName("陈萌");
	form.setBankAcc("612358123890123123345");
	form.setCraccBankCode("123456");
	form.setCurrencyType("01");
	form.setSacPeriod("30");
	form.setCostRate("1");
	form.setCostComWay("");
	form.setCostSacWay("");
	form.setIsValidFlag("1");
	form.setMemo("");
	JwsClient jwsClient = new JwsClient("SAC-AC-0003");
	JwsResult jwsResult = jwsClient.putParam("SacChannelParam", form).call();
	Assert.assertEquals(jwsResult.getMessage(),"000000", jwsResult.getCode());
    }

    
    @Test
    public void testListSacChannelParam() throws Exception
    {
	SacChannelParamQueryForm form = new SacChannelParamQueryForm();
	form.setChnCode("ICBC0003");
	form.setChnType("1");
	JwsClient jwsClient = new JwsClient("SAC-AC-0014");
	JwsResult jwsResult = jwsClient.putAllParam(form).call();
	Assert.assertEquals(jwsResult.getMessage(),"000000", jwsResult.getCode());
    }

}
