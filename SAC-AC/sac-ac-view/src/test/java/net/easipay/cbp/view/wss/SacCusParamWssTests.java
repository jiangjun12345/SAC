package net.easipay.cbp.view.wss;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.easipay.cbp.form.SacCusParameterHandleForm;
import net.easipay.cbp.form.SacCusParameterValidateForm;
import net.easipay.cbp.view.AbstractAccountCentralServiceTest;
import net.easipay.dsfc.ws.jws.JwsClient;
import net.easipay.dsfc.ws.jws.JwsResult;

import org.junit.Assert;
import org.junit.Test;

public class SacCusParamWssTests extends AbstractAccountCentralServiceTest
{
    @Test
    public void testReceiveSacCusParameter() throws Exception
    {
	SacCusParameterHandleForm form = new SacCusParameterHandleForm();
	form.setOrgCardId("768712681231X");
	form.setCusName("一号店");
	form.setCusType("1");
	form.setMerchantNcode("ASD3123");
	form.setRefundFlag("1");
	form.setSacBankAcc("6226098765432");
	form.setAccName("测试银行账号名称1");
	form.setDepositBank("招商");
	form.setCraccBankCode("1");
	form.setFeeRate("0.1");
	form.setFeeComWay("0");
	form.setFeeSacWay("0");
	form.setSacType("1");
	form.setSacPeriod("2");
	form.setIntervalNumber("0");
	form.setSacCurrency("CNY");
	form.setSacStartAmount("0");
	form.setTrxUpLim("0");
	form.setIsVaildFlag("1");
	form.setCusFeeFlag("");
	form.setBussType("20");
	form.setMemo("");
	JwsClient jwsClient = new JwsClient("SAC-AC-0004");
	JwsResult jwsResult = jwsClient.putParam("sacCusParameter", form).call();
	Assert.assertEquals(jwsResult.getMessage(),"000000", jwsResult.getCode());
    }
    
    @SuppressWarnings("unchecked")
	@Test
    public void testValidateSacCusParameter() throws Exception
    {
    List<SacCusParameterValidateForm> list = new ArrayList<SacCusParameterValidateForm>();
	SacCusParameterValidateForm form = new SacCusParameterValidateForm();
	form.setOrgCardId("EP0000010");
	form.setBussType("20");
	form.setCurrency("CNY");
	
	SacCusParameterValidateForm form1 = new SacCusParameterValidateForm();
	form1.setOrgCardId("EP0000010");
	form1.setBussType("90");
	form1.setCurrency("USD");
	
	list.add(form);
	list.add(form1);
	JwsClient jwsClient = new JwsClient("SAC-AC-0020");
	JwsResult jwsResult = jwsClient.putParam("sacCusParamsValidates", list).call();
	Map<String,Boolean> bean = jwsResult.getBean("validateResults", java.util.Map.class);
	Boolean flag = bean.get(form.getOrgCardId()+"_"+form.getCurrency()+"_"+form.getBussType());
	System.out.println(flag);
	Assert.assertEquals(jwsResult.getMessage(),"000000", jwsResult.getCode());
    }


}
