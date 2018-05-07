package net.easipay.cbp.Trigger;

import java.util.List;

import net.easipay.cbp.FaException;
import net.easipay.cbp.model.FinTask;
import net.easipay.cbp.service.IFinAccountingService;
import net.easipay.dsfc.StringUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author mchen
 * @date 2015-12-16
 */
@Deprecated
public class FinAccountingTrigger
{
    @Autowired
    private IFinAccountingService finAccountingService;

    private Log logger = LogFactory.getLog(FinAccountingTrigger.class);

    private int maxSize = 1000;

    public void onProcessFinTask()
    {
	logger.info("To start FinAccountingTrigger.onProcessFinTask.");

	List<FinTask> finTaskList = finAccountingService.getUndoTaskList(0, maxSize);

	logger.info("FinTask number is " + finTaskList.size());
	logger.info("FFinAccountingTrigger is taking the first " + maxSize);

	for (FinTask finTask : finTaskList) {
	    try {
		finAccountingService.autoChargeAccount(finTask);
		logger.info("FinAccountingTrigger.onProcessFinTask success. taskId is " + finTask.getTaskId());
	    } catch ( Exception e ) {
		if (e instanceof FaException) {
		    finAccountingService.updateUndoTask(finTask.getTaskId(), -2, ((FaException) e).getCode() + "|" + e.getMessage());
		}
		else {
		    finAccountingService.updateUndoTask(finTask.getTaskId(), -1, StringUtils.skipsString(e.getMessage(), 250));
		}
		logger.error("FinAccountingTrigger.onProcessFinTask throw exception. taskId is" + finTask.getTaskId(), e);
	    }
	}
    }
}
