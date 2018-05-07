package net.easipay.cbp.view.wss;

import java.math.BigDecimal;

import net.easipay.cbp.form.SacDffOflCommandReceiveForm;
import net.easipay.cbp.form.SacTransferCommandReceiveForm;
import net.easipay.cbp.model.SacDffOflCommand;
import net.easipay.cbp.util.DateUtil;
import net.easipay.cbp.view.AbstractAccountCentralServiceTest;
import net.easipay.dsfc.ws.jws.JwsClient;
import net.easipay.dsfc.ws.jws.JwsResult;

import org.junit.Assert;
import org.junit.Test;

public class SacTransferCommandWssTests extends AbstractAccountCentralServiceTest
{
    @Test
    public void testReceiveSacTransferCommand() throws Exception
    {
	SacTransferCommandReceiveForm form = new SacTransferCommandReceiveForm();
	form.setCmdType("10");
	form.setCmdSerialNo("T123097809123123");
	form.setMsgReceiver("BOC00000");
	form.setDbtCode("ICBC0000");
	form.setDraccNo("612375681231231231");
	form.setDraccName("张三");
	form.setDraccBankCode("BOC00000");
	form.setDraccBankBranch("中国银行");
	form.setPayCurrency("01");
	form.setPayCount(1L);
	form.setPayAmount(new BigDecimal(100000));
	form.setPayPri("01");
	form.setVldDate(DateUtil.currentDate());
	form.setCrtCode("8798711");
	form.setCraccNo("612938798123");
	form.setCraccName("李四");
	form.setCraccBankCode("icbc00000");
	form.setCraccBankBranch("农业银行");
	form.setBatchSerialNo("Y0123908901231");
	form.setOtrxSerialNo("123");
	form.setOrgId(1239713L);
	form.setReqSpt1("");
	form.setReqSpt2("");
	form.setReqSpt3("");
	form.setReqMemo("");
	JwsClient jwsClient = new JwsClient("SAC-AC-0016");
	JwsResult jwsResult = jwsClient.putParam("sacTransferCommandReceiveForm",form).call();
	Assert.assertEquals(jwsResult.getMessage(), "000000", jwsResult.getCode());
    }
    @Test
    public void testReceiveSacDffOflCommand() throws Exception
    {
    	SacDffOflCommandReceiveForm form = new SacDffOflCommandReceiveForm();
    	form.setBussType("20");
    	form.setCmdType("00");
    	form.setCraccBankName("BOS0000");
    	form.setCraccBranchCode("234");
    	form.setCraccCardId("123654987");
    	form.setCraccName("123");
    	form.setCraccNo("123");
    	form.setCraccNodeCode("SPDB000");
    	form.setDraccBankName("浦发银行");
    	form.setDraccNodeCode("SPDB000");
    	form.setPayAmount(new BigDecimal("22.2"));
    	form.setPayCurrency("CNY");
    	form.setSkSerialNo("12345");
    	form.setYkSerialNo("23466");
    	JwsClient jwsClient = new JwsClient("SAC-AC-0025");
    	JwsResult jwsResult = jwsClient.putParam("sacDffOflCommand",form).call();
    	Assert.assertEquals(jwsResult.getMessage(), "000000", jwsResult.getCode());
    }


}
