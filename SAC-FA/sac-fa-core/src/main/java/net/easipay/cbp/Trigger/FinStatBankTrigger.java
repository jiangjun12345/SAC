package net.easipay.cbp.Trigger;

import net.easipay.cbp.service.IFinStatBankService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 银行数据日统计
 * 
 * @author Administrator
 * 
 */
@Deprecated
public class FinStatBankTrigger
{
    @Autowired
    private IFinStatBankService finStatBankService;

    private Log logger = LogFactory.getLog(FinAccountingTrigger.class);
    
    public void onProcessFinDailyStatBank()
    {
	logger.info("To start FinStatBankTrigger.onProcessFinDailyStatBank");
	try {
	    finStatBankService.autoFinDailyStatBank();
	    logger.info("FinStatBankTrigger.onProcessFinDailyStatBank success.");
	} catch ( Exception e ) {
	    logger.error("FinStatBankTrigger.onProcessFinDailyStatBank throw exception", e);
	}
    }
}
