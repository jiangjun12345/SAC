package net.easipay.cbp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.FinYeDao;
import net.easipay.cbp.model.FinTask;
import net.easipay.cbp.model.FinYe;
import net.easipay.cbp.service.impl.FinAccountingService;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class WssTests extends AbstractJuintTest
{
    @Autowired
    private FinYeDao dao;
    
    @Autowired
    private FinAccountingService service;

    @Test
    public void testWss() throws Exception
    {
	List<Runnable> tasks = new ArrayList<Runnable>();
	for (int i = 0; i < 10; i++) {
	    tasks.add(new Runnable() {
		@Override
		@Transactional
		public void run()
		{
		    Map<String, String> param = new HashMap<String, String>();
		    param.put("yeId", "123123123");
		    List<FinYe> yeInList = dao.getYeInList(param);
		    System.out.println(Thread.currentThread().getName()+"query - " + yeInList.get(0).getTotalAmount());
		    long currentTimeMillis = System.currentTimeMillis();
		    System.out.println(Thread.currentThread().getName()+"update - " + currentTimeMillis);
		    FinYe ye = new FinYe();
		    ye.setYeId("123123123");
		    ye.setTotalAmount(new BigDecimal(currentTimeMillis));
		    dao.update(ye);
		}
	    });
	}
	ConcurrencyTestUtil.assertConcurrent("1024tasks", tasks, 10, 215);
    }
    
    
    @Test
    public void testChargeAccount() throws Exception
    {
	List<FinTask> tasks = new ArrayList<FinTask>();
	FinTask e = new FinTask();
	e.setBusiType("30");
	e.setDigest("asdasdasdasdasdasd");
	e.setErrDesc("");
	e.setParams("2202032000000000000050184012002,ssm,01,1890|2202032000000000000050184012002,sm,01,1890");
	e.setSerno("T2118asd082811164152712170");
	e.setStatus(1);
	e.setStep(1);
	e.setTaskId(251231217121123170L);
	e.setTradeTime(new Date());
	e.setTrxCode("3302");
	tasks.add(e );
	service.chargeAccount(tasks );
    }

}
