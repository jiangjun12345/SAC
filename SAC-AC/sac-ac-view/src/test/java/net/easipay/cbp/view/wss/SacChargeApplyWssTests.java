package net.easipay.cbp.view.wss;

import java.math.BigDecimal;

import net.easipay.cbp.form.SacChargeApplyForm;
import net.easipay.cbp.view.AbstractAccountCentralServiceTest;
import net.easipay.dsfc.ws.jws.JwsClient;
import net.easipay.dsfc.ws.jws.JwsResult;

import org.junit.Assert;
import org.junit.Test;

public class SacChargeApplyWssTests extends AbstractAccountCentralServiceTest
{
    @Test
    public void testReceiveSacChargeApply() throws Exception
    {
	SacChargeApplyForm form = new SacChargeApplyForm();
	form.setApplyOrgId(123871213L);
	form.setApplyDbtCode("1238791231");
	form.setApplyOrgName("东方");
	form.setApplyCode("zhgajsa1s");
	form.setPayCurrency("01");
	form.setPayAmount(new BigDecimal(1000));
	form.setCraccNo("612378918723987123123");
	form.setCraccName("张三");
	form.setCraccNodeCode("BOC0000");
	form.setCraccBankName("中国银行");
	form.setDraccNo("");
	form.setDraccName("李斯");
	form.setApplyMemo("");
	form.setBussType("20");
	JwsClient jwsClient = new JwsClient("SAC-AC-0012");
	JwsResult jwsResult = jwsClient.putParam("SacChargeApplyForm",form).call();
	Assert.assertEquals(jwsResult.getMessage(), "000000", jwsResult.getCode());
    }


}
