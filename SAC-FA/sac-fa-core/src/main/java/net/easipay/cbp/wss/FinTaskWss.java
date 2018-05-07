package net.easipay.cbp.wss;

import java.util.ArrayList;
import java.util.List;

import net.easipay.cbp.FaException;
import net.easipay.cbp.async.AsyncExecutorService;
import net.easipay.cbp.form.FinTaskQueryForm;
import net.easipay.cbp.form.FinTaskReceiveForm;
import net.easipay.cbp.model.FinTask;
import net.easipay.cbp.service.IFinAccountingService;
import net.easipay.cbp.util.ConstantParams;
import net.easipay.dsfc.StringUtils;
import net.easipay.dsfc.ws.WsResult;
import net.easipay.dsfc.ws.jws.JwsHttpRequest;
import net.easipay.dsfc.ws.jws.JwsResult;
import net.easipay.dsfc.ws.support.SpringServiceFactory;
import net.easipay.dsfc.ws.wss.WssRequestMapping;
import net.easipay.dsfc.ws.wss.WssRequestMethod;

import org.apache.log4j.Logger;

public class FinTaskWss
{
    private static final Logger logger = Logger.getLogger(FinTaskWss.class);

    @WssRequestMapping(value = "chargeAccountActualTime", service = "SAC-FA-0002", method = WssRequestMethod.POST, desc = "实时记账")
    public WsResult chargeAccountActualTime(JwsHttpRequest jwsHttpRequest)
    {
	JwsResult jwsResult = new JwsResult();
	try {
	    FinTaskReceiveForm form = jwsHttpRequest.toBean(FinTaskReceiveForm.class);

	    jwsHttpRequest.validate(form);

	    IFinAccountingService finAccountingService = SpringServiceFactory.getBean(IFinAccountingService.class);

	    List<FinTaskReceiveForm> forms = new ArrayList<FinTaskReceiveForm>();
	    forms.add(form);
	    finAccountingService.batchChargeAccountActualTime(forms);
	    jwsResult.setSuccess(ConstantParams.RTN_CODE_SUCCESSS, "成功");
	} catch ( FaException ex ) {
	    logger.error(ex.getMessage());
	    jwsResult.setError(ex.getCode(), ex.getMessage());
	} catch ( Exception e ) {
	    logger.error(e.getMessage());
	    jwsResult.setError(ConstantParams.RTN_CODE_ERROR, e.getMessage());
	}
	return jwsResult;
    }

    @WssRequestMapping(value = "batchChargeAccountActualTime", service = "SAC-FA-0003", method = WssRequestMethod.POST, desc = "批量实时记账")
    public WsResult batchChargeAccountActualTime(JwsHttpRequest jwsHttpRequest)
    {
	JwsResult jwsResult = new JwsResult();
	try {
	    List<FinTaskReceiveForm> forms = jwsHttpRequest.getList("finTaskForms", FinTaskReceiveForm.class);

	    jwsHttpRequest.validateList(forms);

	    IFinAccountingService finAccountingService = SpringServiceFactory.getBean(IFinAccountingService.class);

	    finAccountingService.batchChargeAccountActualTime(forms);
	    jwsResult.setSuccess(ConstantParams.RTN_CODE_SUCCESSS, "成功");
	} catch ( FaException ex ) {
	    logger.error(ex.getMessage());
	    jwsResult.setError(ex.getCode(), ex.getMessage());
	} catch ( Exception e ) {
	    logger.error("异常!!!!!!!!",e);
	    jwsResult.setError(ConstantParams.RTN_CODE_ERROR, e.getMessage());
	}
	return jwsResult;
    }

    @WssRequestMapping(value = "autoOnProcessFinTask", service = "SAC-FA-0005", method = WssRequestMethod.POST, desc = "异步处理未处理的fin_task任务")
    public WsResult autoOnProcessFinTask(JwsHttpRequest jwsHttpRequest)
    {
	JwsResult jwsResult = new JwsResult();
	try {
	    final int maxSize = jwsHttpRequest.getIntValue("maxSize");

	    if (maxSize <= 0) throw new FaException(ConstantParams.RTN_CODE_ERROR, "maxSize为整数，必须大于0");

	    AsyncExecutorService.inst.submit(new Runnable() {
		@Override
		public void run()
		{
		    IFinAccountingService finAccountingService = SpringServiceFactory.getBean(IFinAccountingService.class);

		    logger.info("开始异步处理未处理的fin_task任务.");

		    List<FinTask> finTaskList = finAccountingService.getUndoTaskList(0, maxSize);

		    logger.info("FinTask number is " + finTaskList.size());
		    logger.info("It is taking the first " + maxSize);

		    for (FinTask finTask : finTaskList) {
			try {
			    finAccountingService.autoChargeAccount(finTask);
			    logger.info("成功异步处理未处理的fin_task任务. taskId is " + finTask.getTaskId());
			} catch ( Exception e ) {
			    if (e instanceof FaException) {
				finAccountingService.updateUndoTask(finTask.getTaskId(), -2, ((FaException) e).getCode() + "|" + e.getMessage());
			    }
			    else {
				finAccountingService.updateUndoTask(finTask.getTaskId(), -1, StringUtils.skipsString(e.getMessage(), 250));
			    }
			    logger.error("异步处理未处理的fin_task任务抛异常. taskId is" + finTask.getTaskId(), e);
			}
		    }
		}
	    });

	    jwsResult.setSuccess(ConstantParams.RTN_CODE_SUCCESSS, "成功");
	} catch ( FaException ex ) {
	    logger.error(ex.getMessage());
	    jwsResult.setError(ex.getCode(), ex.getMessage());
	} catch ( Exception e ) {
	    logger.error(e.getMessage());
	    jwsResult.setError(ConstantParams.RTN_CODE_ERROR, e.getMessage());
	}
	return jwsResult;
    }
    
    
    
    @WssRequestMapping(value = "chargeAccountASync", service = "SAC-FA-0006", method = WssRequestMethod.POST, desc = "异步记账")
    public WsResult chargeAccountASync(JwsHttpRequest jwsHttpRequest)
    {
	JwsResult jwsResult = new JwsResult();
	try {
	    List<FinTaskReceiveForm> forms = jwsHttpRequest.getList("finTaskForms", FinTaskReceiveForm.class);

	    jwsHttpRequest.validateList(forms);

	    IFinAccountingService finAccountingService = SpringServiceFactory.getBean(IFinAccountingService.class);

	    finAccountingService.chargeAccountASync(forms);
	    jwsResult.setSuccess(ConstantParams.RTN_CODE_SUCCESSS, "成功");
	} catch ( FaException ex ) {
	    logger.error(ex.getMessage());
	    jwsResult.setError(ex.getCode(), ex.getMessage());
	} catch ( Exception e ) {
	    logger.error(e.getMessage());
	    jwsResult.setError(ConstantParams.RTN_CODE_ERROR, e.getMessage());
	}
	return jwsResult;
    }
    
    @WssRequestMapping(value = "getFinTaskInfo", service = "SAC-FA-0007", method = WssRequestMethod.POST, desc = "获取记账任务信息")
    public WsResult getFinTaskInfo(JwsHttpRequest jwsHttpRequest)
    {
	JwsResult jwsResult = new JwsResult();
	try {
	    FinTaskQueryForm form = jwsHttpRequest.getBean("finTaskQueryForms", FinTaskQueryForm.class);

	    jwsHttpRequest.validate(form);

	    IFinAccountingService finAccountingService = SpringServiceFactory.getBean(IFinAccountingService.class);

	    List<FinTask> finTaskList = finAccountingService.getFinTaskByParam(form);
	    
	    jwsResult.setValue("finTaskList", finTaskList);
	    
	    jwsResult.setSuccess(ConstantParams.RTN_CODE_SUCCESSS, "成功");
	} catch ( FaException ex ) {
	    logger.error(ex.getMessage());
	    jwsResult.setError(ex.getCode(), ex.getMessage());
	} catch ( Exception e ) {
	    logger.error(e.getMessage());
	    jwsResult.setError(ConstantParams.RTN_CODE_ERROR, e.getMessage());
	}
	return jwsResult;
    }
}
