package net.easipay.cbp.view.wss;

import java.util.List;

import net.easipay.cbp.form.FinDailyBalanceRtnForm;
import net.easipay.cbp.form.FinMxRtnForm;
import net.easipay.cbp.form.SacAccountingDailyYueQueryForm;
import net.easipay.cbp.form.SacAccountingYueQueryForm;
import net.easipay.cbp.form.SacBalanceQueryForm;
import net.easipay.cbp.form.SacBalanceRtnForm;
import net.easipay.cbp.form.SacFinDetailQueryForm;
import net.easipay.cbp.view.AbstractAccountCentralServiceTest;
import net.easipay.dsfc.ws.jws.JwsClient;
import net.easipay.dsfc.ws.jws.JwsResult;

import org.junit.Assert;
import org.junit.Test;

public class SacAccountingWssTests extends AbstractAccountCentralServiceTest
{
    @Test
    public void testQueryYue() throws Exception
    {
	SacAccountingYueQueryForm form = new SacAccountingYueQueryForm();
	form.setCusType("0");
	form.setCusId("BOC0000");
	form.setCurrencyType("CNY");
	form.setBussType("00");
	JwsClient jwsClient = new JwsClient("SAC-AC-0017");
	JwsResult jwsResult = jwsClient.putAllParam(form).call();
	Assert.assertEquals(jwsResult.getMessage(), "000000", jwsResult.getCode());
    }
    
    
    @Test
    public void testQueryYue1() throws Exception
    {
	SacAccountingYueQueryForm form = new SacAccountingYueQueryForm();
	form.setCusType("1");
	form.setCusId("057625829");
	form.setCurrencyType("CNY");
	form.setBussType("90");
	JwsClient jwsClient = new JwsClient("SAC-AC-0017");
	JwsResult jwsResult = jwsClient.putAllParam(form).call();
	Assert.assertEquals(jwsResult.getMessage(), "000000", jwsResult.getCode());
    }
    
    @Test
    public void testQueryDailyYue() throws Exception
    {
    	SacAccountingDailyYueQueryForm form = new SacAccountingDailyYueQueryForm();
    	form.setOrgCode("301587482");
    	form.setCurrencyType("CNY");
    	form.setBussType("000");
    	form.setSubAccountType("000");
    	form.setQueryDate("20160728");
    	form.setCusType("1");
    	JwsClient jwsClient = new JwsClient("SAC-AC-0018");
    	JwsResult jwsResult = jwsClient.putAllParam(form).call();
    	Assert.assertEquals(jwsResult.getMessage(), "000000", jwsResult.getCode());
    	Assert.assertEquals(jwsResult.getMessage(), "000000", jwsResult.getBean("dailyBalanceForm", FinDailyBalanceRtnForm.class));
    }
    @Test
    public void testBalance() throws Exception
    {
    	SacBalanceQueryForm form = new SacBalanceQueryForm();
    	form.setCusType("1");
    	form.setCurrency("01");
    	form.setBussType("71");
    	form.setBalType("01");
    	form.setOrgCode("398788992");
    	JwsClient jwsClient = new JwsClient("SAC-AC-0022");
    	JwsResult jwsResult = jwsClient.putAllParam(form).call();
    	List<SacBalanceRtnForm> list = jwsResult.getList("balanceList", SacBalanceRtnForm.class);
    	System.out.println(list.size());
    	Assert.assertEquals(jwsResult.getMessage(), "000000", jwsResult.getCode());
    }
    
    @Test
    public void testFinDetail() throws Exception
    {
    	SacFinDetailQueryForm form = new SacFinDetailQueryForm();
    	form.setBeginDate("2016-07-28");
    	form.setEndDate("2016-07-30"); 
    	form.setBussType("90");
    	form.setCurrency("01");
    	form.setCusType("1");
    	form.setEnd(Integer.MAX_VALUE+"");
    	form.setOrgCode("301587482");
    	form.setStart("0");
    	JwsClient jwsClient = new JwsClient("SAC-AC-0023");
    	JwsResult jwsResult = jwsClient.putAllParam(form).call();
    	List<FinMxRtnForm> list = jwsResult.getList("finMxList", FinMxRtnForm.class);
    	System.out.println(list.size());
    	Assert.assertEquals(jwsResult.getMessage(), "000000", jwsResult.getCode());
    }

}
