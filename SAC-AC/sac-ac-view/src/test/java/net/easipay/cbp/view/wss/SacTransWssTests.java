package net.easipay.cbp.view.wss;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.easipay.cbp.form.SacRemittanceBatchIdForm;
import net.easipay.cbp.form.SacTransationReceiveForm;
import net.easipay.cbp.service.ISacTransService;
import net.easipay.cbp.view.AbstractAccountCentralServiceTest;
import net.easipay.dsfc.ws.jws.JwsClient;
import net.easipay.dsfc.ws.jws.JwsResult;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SacTransWssTests extends AbstractAccountCentralServiceTest
{
	@Autowired
	private ISacTransService sacTransService;
    @Test
    public void testReceiveSacTransationDetail() throws Exception
    {
	for (int j = 0; j < 1; j++) {

	    List<SacTransationReceiveForm> forms = new ArrayList<SacTransationReceiveForm>();
	    for (int i = 0; i < 1; i++) {
		SacTransationReceiveForm form = new SacTransationReceiveForm();
		form.setCusBillNo("21067603106");
		form.setPlatBillNo("");
		form.setTrxSerialNo("CBT16090923521054134552203");
		form.setOtrxSerialNo("CBT16080923521013339582901");
		form.setEtrxSerialNo("");
		form.setCraccCardId("572744849");
		form.setCraccCusType("1");
		form.setCraccCusName("东方电子支付有限公司过渡户");
		form.setCraccNo("9012241200009990012");
		form.setCraccName("东方电子支付有限公司过渡户");
		form.setCraccNodeCode("BOC0000");
		form.setCraccBankName("中国银行");
		form.setDraccCardId("572744849");
		form.setDraccCusType("1");
		form.setDraccCusName("上海琅寓实业有限公司");
		form.setDraccNo("2012241011365550010");
		form.setDraccName("上海琅寓实业有限公司");
		form.setDraccNodeCode("CMBC000");
		form.setDraccBankName("中国银行");
		form.setPayCurrency("CNY");
		form.setPayAmount(new BigDecimal(1.10));
		form.setBussType("20");
		form.setTrxType("215111");
		form.setTrxState("S");
		form.setPayconType("1");
		form.setPayWay("9");
		form.setTrxBatchNo("TRXNOTINBATCH");
		form.setIssuingBank("");
		form.setTrxErrDealType("1");
		form.setTaxAmount(null);
		form.setTransportExpenses(null);
		form.setMemo("");
		form.setTrxTime(new Date());
		form.setTrxSuccTime(new Date());
		form.setRemittanceBatchId("");
		form.setTrxStateDesc("");
		form.setChannelCost(new BigDecimal(0));
		form.setCusCharge(new BigDecimal(0));
		form.setCraccAreaCode("000000");
		form.setDraccAreaCode("000000");
		forms.add(form);
	    }
	    JwsClient jwsClient = new JwsClient("SAC-AC-0001");
	    JwsResult jwsResult = jwsClient.putParam("sacTransationDetails", forms).call();
	    Assert.assertEquals(jwsResult.getMessage(), "000000", jwsResult.getCode());

	}
    }

    @Test
    public void testUpdateSacTransationDetail() throws Exception
    {
	Map<String, Object> params = new HashMap<String, Object>();
	params.put("trxSerialNo", "T2016032516265325500910004");
	params.put("trxState", "S");
	params.put("trxStateDesc", "失败");
	params.put("trxSuccTime", new Date());
	JwsClient jwsClient = new JwsClient("SAC-AC-0002");
	JwsResult jwsResult = jwsClient.putParam(params).call();
	Assert.assertEquals(jwsResult.getMessage(), "000000", jwsResult.getCode());
    }

    @Test
    public void testUpdateSacOtrxRemittanceBatchId() throws Exception
    {
	List<SacRemittanceBatchIdForm> forms = new ArrayList<SacRemittanceBatchIdForm>();
	for (int i = 0; i < 3333; i++) {
	    SacRemittanceBatchIdForm form = new SacRemittanceBatchIdForm();
	    form.setRemittanceBatchId("1234");
	    form.setTrxSerialNo("EPASS00201606090001079"+i);
	    forms.add(form);
	    
	}
	JwsClient jwsClient = new JwsClient("SAC-AC-0015");
	JwsResult jwsResult = jwsClient.putParam("sacRemittanceBatchIdForms", forms).call();
	Assert.assertEquals(jwsResult.getMessage(), "000000", jwsResult.getCode());
    }
    

}
