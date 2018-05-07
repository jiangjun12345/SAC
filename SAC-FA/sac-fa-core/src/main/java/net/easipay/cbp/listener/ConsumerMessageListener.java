package net.easipay.cbp.listener;

import java.util.ArrayList;
import java.util.List;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import net.easipay.cbp.model.FinTask;
import net.easipay.cbp.service.IFinAccountingService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author mchen
 * @date 2016-2-29
 */
@Component("consumerMessageListener")
public class ConsumerMessageListener implements MessageListener
{
    private Log logger = LogFactory.getLog(ConsumerMessageListener.class);

    @Autowired
    private IFinAccountingService finAccountingService;
    
    public void onMessage(Message message)
    {
	logger.info("接收到记账数据...");
	ObjectMessage textMsg = (ObjectMessage) message;
	try {
	    FinTask finTask = (FinTask) textMsg.getObject();
	    List<FinTask> tasks = new ArrayList<FinTask>();
	    tasks.add(finTask);
	    finAccountingService.chargeAccount(tasks);
	    logger.info("接收到记账数据成功...");
	} catch ( Exception e ) {
	    logger.info("处理记账数据异常...", e);
	}
    }
}