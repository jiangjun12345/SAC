package net.easipay.cbp.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import net.easipay.cbp.AcFinInsRuler;
import net.easipay.cbp.cache.CacheHandleInit;
import net.easipay.cbp.constant.SacConstants;
import net.easipay.cbp.dao.IFinTaskDao;
import net.easipay.cbp.dao.ISacOtrxInfoDao;
import net.easipay.cbp.exception.AcException;
import net.easipay.cbp.form.FinRuleTaskJoinForm;
import net.easipay.cbp.form.FinTaskQueryForm;
import net.easipay.cbp.form.FinTaskReceiveForm;
import net.easipay.cbp.form.FinTaskSendForm;
import net.easipay.cbp.model.FinTask;
import net.easipay.cbp.model.SacFinInsRule;
import net.easipay.cbp.model.SacOtrxInfo;
import net.easipay.cbp.sequence.SequenceCreatorUtil;
import net.easipay.cbp.service.IFinTaskService;
import net.easipay.dsfc.cache.CacheManager;
import net.easipay.dsfc.ws.jws.JwsClient;
import net.easipay.dsfc.ws.jws.JwsResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("finTaskService")
public class FinTaskService implements IFinTaskService
{
    @Autowired
    private IFinTaskDao finTaskDao;
    @Autowired
    private ISacOtrxInfoDao sacOtrxInfoDao;

    /*
     * @Autowired private JmsTemplate jmsTemplate;
     */

    /**
     * 提供对外记账接口
     */
    public void insertForeignFinTasksForRule(List<FinRuleTaskJoinForm> forms)
    {
	List<FinTask> generateFinTasks = new ArrayList<FinTask>();
	List<FinTask> generateRealTimeFinTasks = new ArrayList<FinTask>();
	List<FinTask> generateMQFinTasks = new ArrayList<FinTask>();
	for (FinRuleTaskJoinForm form : forms) {
	    SacOtrxInfo sacOtrxInfo = sacOtrxInfoDao.getSacOtrxInfo(form.getTrxSerialNo());
	    if (sacOtrxInfo == null) {
		throw new AcException(SacConstants.RTN_FAIL, "交易不存在");
	    }
	    if (!sacOtrxInfo.getTrxType().equals(form.getTrxCode())) {
		throw new AcException(SacConstants.RTN_FAIL, "交易代码不一致");
	    }

	    generateFinTasks(form.getTrxCode(), form.getTrxState(), sacOtrxInfo, generateRealTimeFinTasks, generateFinTasks, generateMQFinTasks);

	}

	if (generateRealTimeFinTasks.size() != 0) {
	    sendBatchRealTimeMessage(generateRealTimeFinTasks);
	}

	if (generateFinTasks.size() != 0) {
	    //finTaskDao.insertFinTasks(generateFinTasks);
	    sendChargeAccountASync(generateFinTasks);
	}

	if (generateMQFinTasks.size() != 0) {
	    for (FinTask mqFinTask : generateMQFinTasks) {
		sendMQMessage(mqFinTask);
	    }
	}
    }

    /**
     * 提供内部使用的记账接口
     */
    public void insertInterFinTasksForRule(List<SacOtrxInfo> sacOtrxInfos)
    {
	List<FinTask> generateFinTasks = new ArrayList<FinTask>();
	List<FinTask> generateRealTimeFinTasks = new ArrayList<FinTask>();
	List<FinTask> generateMQFinTasks = new ArrayList<FinTask>();
	for (SacOtrxInfo sacOtrxInfo : sacOtrxInfos) {
	    if (!"3411".equals(sacOtrxInfo.getTrxType()) && !"3803".equals(sacOtrxInfo.getTrxType())) generateFinTasks(sacOtrxInfo.getTrxType(), sacOtrxInfo.getTrxState(), sacOtrxInfo, generateRealTimeFinTasks, generateFinTasks, generateMQFinTasks);
	}

	if (generateRealTimeFinTasks.size() != 0) {
	    sendBatchRealTimeMessage(generateRealTimeFinTasks);
	}

	if (generateFinTasks.size() != 0) {
	    // finTaskDao.insertFinTasks(generateFinTasks);
	    sendChargeAccountASync(generateFinTasks);
	}

	if (generateMQFinTasks.size() != 0) {
	    for (FinTask mqFinTask : generateMQFinTasks) {
		sendMQMessage(mqFinTask);
	    }
	}
    }

    @Deprecated
    private void sendMQMessage(final FinTask finTask)
    {
	try {
	    /*
	     * jmsTemplate.send(new MessageCreator() { public Message
	     * createMessage(Session session) throws JMSException { return
	     * session.createObjectMessage(finTask); } });
	     */
	} catch ( Exception e ) {
	    throw new AcException(SacConstants.RTN_FAIL, e.getMessage());
	}
    }

    private void sendBatchRealTimeMessage(List<FinTask> finTasks)
    {
	List<FinTaskSendForm> realTimeFinTasks = tranformFinTaskToFinTaskSendForm(finTasks);

	JwsClient jwsClient = new JwsClient("SAC-FA-0003");
	jwsClient.putParam("finTaskForms", realTimeFinTasks).call();
    }

    private void sendChargeAccountASync(List<FinTask> finTasks)
    {
	List<FinTaskSendForm> asyncFinTasks = tranformFinTaskToFinTaskSendForm(finTasks);

	JwsClient jwsClient = new JwsClient("SAC-FA-0006");
	jwsClient.putParam("finTaskForms", asyncFinTasks).call();
    }

    private List<FinTaskSendForm> tranformFinTaskToFinTaskSendForm(List<FinTask> finTasks)
    {
	List<FinTaskSendForm> list = new ArrayList<FinTaskSendForm>();
	for (FinTask finTask : finTasks) {
	    FinTaskSendForm form = new FinTaskSendForm();
	    form.setTrxCode(finTask.getTrxCode());
	    form.setBusiType(finTask.getBusiType());
	    form.setParams(finTask.getParams());
	    form.setDigest(finTask.getDigest());
	    form.setSerno(finTask.getSerno());
	    form.setStep(finTask.getStep());
	    form.setTradeTime(finTask.getTradeTime());
	    list.add(form);
	}
	return list;
    }

    @SuppressWarnings("unchecked")
    private List<FinTask> generateFinTasks(String trxCode, String trxState, SacOtrxInfo sacOtrxInfo, List<FinTask> generateRealTimeFinTasks, List<FinTask> generateFinTasks, List<FinTask> generateMQFinTasks)
    {
	if (sacOtrxInfo.getPayAmount().compareTo(new BigDecimal(0)) != 1) {
	    return generateFinTasks;
	}
	// 冲正
	if ("4201".equals(trxCode)) {
	    generateFinTasks.addAll(generateTasksByCorrect(sacOtrxInfo));
	    return generateFinTasks;
	}

	List<SacFinInsRule> listSacFinInsRule = (List<SacFinInsRule>) CacheManager.getCache(CacheHandleInit.SAC_FIN_INS_RULE);

	List<SacFinInsRule> currlistSacFinInsRule = new ArrayList<SacFinInsRule>();
	for (SacFinInsRule sacFinInsRule : listSacFinInsRule) {
	    if (sacFinInsRule.getTrxCode().equals(sacOtrxInfo.getTrxType()) && sacFinInsRule.getTrxState().equals(trxState)) {
		currlistSacFinInsRule.add(sacFinInsRule);
	    }
	}

	for (SacFinInsRule rule : currlistSacFinInsRule) {
		
	    FinTask finTask = new FinTask();
	    finTask.setTaskId(SequenceCreatorUtil.getTableId());
	    finTask.setTrxCode(sacOtrxInfo.getTrxType());
	    finTask.setBusiType(sacOtrxInfo.getBussType());
	    finTask.setStep(rule.getStep());
	    finTask.setParams(AcFinInsRuler.generateFinInsRuleParams(sacOtrxInfo, rule));
	    finTask.setDigest(AcFinInsRuler.generateFinInsRuleDigest(sacOtrxInfo, rule));
	    finTask.setSerno(sacOtrxInfo.getTrxSerialNo());
	    finTask.setTradeTime(sacOtrxInfo.getTrxSuccTime() == null ? sacOtrxInfo.getCreateTime() : sacOtrxInfo.getTrxSuccTime());
	    finTask.setStatus(0);
	    if (SacConstants.INS_RULE_PRO_TYPE.REAL_TIME.equals(rule.getProcessType())) {
		generateRealTimeFinTasks.add(finTask);
	    }
	    else if (SacConstants.INS_RULE_PRO_TYPE.JMS.equals(rule.getProcessType())) {
		generateMQFinTasks.add(finTask);
	    }
	    else {
		generateFinTasks.add(finTask);
	    }
	}
	return generateFinTasks;
    }

    private List<FinTask> generateTasksByCorrect(SacOtrxInfo sacOtrxInfo)
    {
	//List<FinTask> finTasks = finTaskDao.getFinTasks(sacOtrxInfo.getOtrxSerialNo(), "");
    List<FinTaskReceiveForm> finTasks = getFinTaskFromFa(sacOtrxInfo.getOtrxSerialNo(), "");

    List<FinTask> taskList = new ArrayList<FinTask>();
	for (FinTaskReceiveForm finTask : finTasks) {
		FinTask task = new FinTask();
		task.setTaskId(SequenceCreatorUtil.getTableId());
		task.setParams(AcFinInsRuler.changeCorrectParams(finTask.getParams()));
		task.setDigest("冲正交易");
		task.setTradeTime(sacOtrxInfo.getTrxSuccTime() == null ? sacOtrxInfo.getCreateTime() : sacOtrxInfo.getTrxSuccTime());
		task.setStatus(0);
		task.setSerno(sacOtrxInfo.getTrxSerialNo());
		task.setTrxCode(sacOtrxInfo.getTrxType());
		task.setBusiType(finTask.getBusiType());
		task.setStep(finTask.getStep());
		taskList.add(task);
	}
	return taskList;
    }
    
    private List<FinTaskReceiveForm> getFinTaskFromFa(String serno, String status)
    {
    FinTaskQueryForm form = new FinTaskQueryForm();
    form.setSerno(serno);
    form.setStatus(status);
	JwsClient jwsClient = new JwsClient("SAC-FA-0007");
	JwsResult rt = jwsClient.putParam("finTaskQueryForms",form).call();
	
	return rt.getList("finTaskList", FinTaskReceiveForm.class);
    }

}
