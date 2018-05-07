package net.easipay.cbp.view.wss;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.easipay.cbp.form.SacReconBankReceiveForm;
import net.easipay.cbp.form.SacReconDifBankReceiveForm;
import net.easipay.cbp.form.SacReconInternalReceiveForm;
import net.easipay.cbp.util.DateUtil;
import net.easipay.cbp.view.AbstractAccountCentralServiceTest;
import net.easipay.dsfc.ws.fws.FwsAnnoResolver;
import net.easipay.dsfc.ws.fws.FwsClient;
import net.easipay.dsfc.ws.fws.FwsResult;

import org.junit.Assert;
import org.junit.Test;

public class SacReconFileWssTests extends AbstractAccountCentralServiceTest
{
    @Test
    public void testReceiveInternalReconFile() throws Exception
    {
	List<SacReconInternalReceiveForm> forms = new ArrayList<SacReconInternalReceiveForm>();
	for (int i = 0; i < 10; i++) {
	    SacReconInternalReceiveForm form = new SacReconInternalReceiveForm();
	    form.setTrxSerialNo("16461321351463461321" + i);
	    form.setPayconType("1");
	    form.setCurrencyType("01");
	    form.setPayAmount("10.00");
	    form.setTrxTime(new Date());
	    form.setRecOper("0000001");
	    form.setRecStartDate(DateUtil.parse("20151010235959","yyyyMMddHHmmss"));
	    form.setRecEndDate(DateUtil.parse("20151010235959","yyyyMMddHHmmss"));
	    form.setRecCount(10L);
	    form.setTrxCode("");
	    form.setPrivDomain("");
	    forms.add(form);
	}

	String jwsParamStr = FwsAnnoResolver.serialize(forms, SacReconInternalReceiveForm.class);
	System.out.println(jwsParamStr);

	FwsClient fwsClient = new FwsClient("SAC-AC-0005");
	FwsResult fwsResult = fwsClient.putParam(forms, SacReconInternalReceiveForm.class).call();
	Assert.assertEquals(fwsResult.getMessage(),"000000", fwsResult.getCode());

    }

    @Test
    public void testReceiveBankReconFile() throws Exception
    {
	List<SacReconBankReceiveForm> forms = new ArrayList<SacReconBankReceiveForm>();
	for (int i = 0; i < 10; i++) {
	    SacReconBankReceiveForm form = new SacReconBankReceiveForm();
	    form.setBankSerialNo("1646132135463461321" + i);
	    form.setPayconType("1");
	    form.setCurrencyType("01");
	    form.setPayAmount("10.00");
	    form.setTrxTime(new Date());
	    form.setRecOper("0000001");
	    form.setChnCode("ICBC");
	    form.setBusiType("1");
	    form.setRecStartDate(DateUtil.parse("20151010235959","yyyyMMddHHmmss"));
	    form.setRecEndDate(DateUtil.parse("20151010235959","yyyyMMddHHmmss"));
	    form.setRecCount(10L);
	    form.setTrxSerialNo("1646132135463461321");
	    form.setTrxCode("3308");
	    form.setPrivDomain("asdadsasd");
	    forms.add(form);
	}

	String jwsParamStr = FwsAnnoResolver.serialize(forms, SacReconBankReceiveForm.class);
	System.out.println(jwsParamStr);

	FwsClient fwsClient = new FwsClient("SAC-AC-0006");
	FwsResult fwsResult = fwsClient.putParam(forms, SacReconBankReceiveForm.class).call();
	Assert.assertEquals(fwsResult.getMessage(),"000000", fwsResult.getCode());
    }

    @Test
    public void testReceiveBankDifReconFile() throws Exception
    {
	List<SacReconDifBankReceiveForm> forms = new ArrayList<SacReconDifBankReceiveForm>();
	for (int i = 0; i < 10; i++) {
	    SacReconDifBankReceiveForm form = new SacReconDifBankReceiveForm();
	    form.setBankSerialNo("1646132135463461321" + i);
	    form.setPayconType("1");
	    form.setCurrencyType("01");
	    form.setPayAmount("10.00");
	    form.setTrxTime(new Date());
	    form.setRecOper("0000001");
	    form.setChnCode("ICBC");
	    form.setDiffType("1");
	    form.setBusiType("1");
	    form.setRecStartDate(DateUtil.parse("20151010235959","yyyyMMddHHmmss"));
	    form.setRecEndDate(DateUtil.parse("20151010235959","yyyyMMddHHmmss"));
	    form.setRecCount(10L);
	    form.setTrxSerialNo("1646132135463461321");
	    form.setTrxCode("3308");
	    form.setPrivDomain("asdadsasd");
	    forms.add(form);
	}

	String jwsParamStr = FwsAnnoResolver.serialize(forms, SacReconDifBankReceiveForm.class);
	System.out.println(jwsParamStr);

	FwsClient fwsClient = new FwsClient("SAC-AC-0007");
	FwsResult fwsResult = fwsClient.putParam(forms, SacReconDifBankReceiveForm.class).call();
	Assert.assertEquals(fwsResult.getMessage(),"000000", fwsResult.getCode());
    }
}
