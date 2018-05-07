package net.easipay.cbp.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.easipay.cbp.FaException;
import net.easipay.cbp.FinGenerator;
import net.easipay.cbp.FinTaskHelper;
import net.easipay.cbp.cache.CacheContextHandler;
import net.easipay.cbp.dao.ExchangeGainsDao;
import net.easipay.cbp.dao.FinCode3Dao;
import net.easipay.cbp.dao.FinCodeDao;
import net.easipay.cbp.dao.FinMxDao;
import net.easipay.cbp.dao.FinPzDao;
import net.easipay.cbp.dao.FinTaskDao;
import net.easipay.cbp.dao.FinYeDao;
import net.easipay.cbp.dao.SequenceCreatorDao;
import net.easipay.cbp.form.FinTaskQueryForm;
import net.easipay.cbp.form.FinTaskReceiveForm;
import net.easipay.cbp.form.PzParamsForm;
import net.easipay.cbp.model.FinCode;
import net.easipay.cbp.model.FinCode1;
import net.easipay.cbp.model.FinCode3;
import net.easipay.cbp.model.FinMx;
import net.easipay.cbp.model.FinPz;
import net.easipay.cbp.model.FinTask;
import net.easipay.cbp.model.FinYe;
import net.easipay.cbp.sequence.SequenceCreatorUtil;
import net.easipay.cbp.service.IFinAccountingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 1.初始化fincode 新用户 保存fincode3 fincode finYeDao
 * 
 * 2.保存凭证
 * 
 * 3.更新余额，添加余额明细
 * 
 * 
 * @author mchen
 * @date 2015-12-14
 */

@Service("finAccountingService")
public class FinAccountingService implements IFinAccountingService
{
    @Autowired
    public FinTaskDao finTaskDao;

    @Autowired
    public FinCodeDao finCodeDao;

    @Autowired
    public FinCode3Dao finCode3Dao;

    @Autowired
    public FinYeDao finYeDao;

    @Autowired
    public FinPzDao finPzDao;

    @Autowired
    public FinMxDao finMxDao;

    @Autowired
    public ExchangeGainsDao exchangeGainsDao;
    
    @Autowired
    public SequenceCreatorDao sequenceCreatorDao;

    public List<FinTask> getUndoTaskList(int status, int maxSize)
    {
	return finTaskDao.getTaskList(status, maxSize);
    }

    public void updateUndoTask(long taskId, int status, String errDesc)
    {
	FinTask finTask = new FinTask();
	finTask.setTaskId(taskId);
	finTask.setStatus(status);
	finTask.setErrDesc(errDesc);
	finTaskDao.update(finTask);
    }

    public void autoChargeAccount(FinTask finTask) throws Exception
    {
	List<FinTask> tasks = new ArrayList<FinTask>();
	tasks.add(finTask);
	chargeAccount(tasks);
	updateUndoTask(finTask.getTaskId(), 1, "成功");
    }

    public void batchChargeAccountActualTime(List<FinTaskReceiveForm> forms) throws Exception
    {
	List<FinTask> tasks = new ArrayList<FinTask>();
	for (FinTaskReceiveForm form : forms) {
		Long sequence = sequenceCreatorDao.getSequenceNoBySeqName("SAC_FIN_TASK_SEQ");
	    FinTask finTask = new FinTask();
	    finTask.setTaskId(sequence);
	    finTask.setTrxCode(form.getTrxCode());
	    finTask.setBusiType(form.getBusiType());
	    finTask.setStep(form.getStep());
	    finTask.setParams(form.getParams());
	    finTask.setSerno(form.getSerno());
	    finTask.setTradeTime(form.getTradeTime());
	    finTask.setDigest(form.getDigest());
	    finTask.setStatus(1);
	    tasks.add(finTask);
	}

	chargeAccount(tasks);

	for (FinTask _finTask : tasks) {
	    finTaskDao.save(_finTask);
	}
    }
    
    
    @Override
    public void chargeAccountASync(List<FinTaskReceiveForm> forms) throws Exception
    {
	List<FinTask> tasks = new ArrayList<FinTask>();
	for (FinTaskReceiveForm form : forms) {
	    FinTask finTask = new FinTask();
	    Long sequence = sequenceCreatorDao.getSequenceNoBySeqName("SAC_FIN_TASK_SEQ");
	    finTask.setTrxCode(form.getTrxCode());
	    finTask.setBusiType(form.getBusiType());
	    finTask.setStep(form.getStep());
	    finTask.setParams(form.getParams());
	    finTask.setSerno(form.getSerno());
	    finTask.setTradeTime(form.getTradeTime());
	    finTask.setDigest(form.getDigest());
	    finTask.setStatus(0);
	    finTask.setTaskId(sequence);
	    tasks.add(finTask);
	}

	for (FinTask _finTask : tasks) {
	    finTaskDao.save(_finTask);
	}
	
    }

    public void chargeAccount(List<FinTask> tasks)
    {
	List<FinPz> listFinPz = new ArrayList<FinPz>();
	Map<String, Boolean> mapShowFinYeMx = new HashMap<String, Boolean>();
	for (FinTask task : tasks) {

	    List<PzParamsForm> pzParamsForms = FinTaskHelper.toPzParamsForms(task.getParams());

	    String pzNo = FinGenerator.generatePzId();

	    for (PzParamsForm form : pzParamsForms) {
		createFinCodeAndUserBalanceByCodeId(form);
		FinPz finPz = createFinPz(pzNo, form, task);
		listFinPz.add(finPz);
	    }

	    mapShowFinYeMx.put(pzNo, FinTaskHelper.isShowFinYeMx(pzParamsForms));
	}

	List<FinYe> finYes = lockFinYe(listFinPz);

	updateFinYeAndCreateFinYeMx(listFinPz, finYes, mapShowFinYeMx);
    }

    public void createFinCodeAndUserBalanceByCodeId(PzParamsForm form)
    {
	String codeId = form.getCodeId();
	FinCode finCode = finCodeDao.getFinCode(codeId);
	if (finCode == null) {
	    finCode = new FinCode();
	    finCode.setCodeId(codeId);
	    finCode.setCode1Id(FinTaskHelper.getFinCode1(codeId));
	    finCode.setCode2Id(FinTaskHelper.getFinCode2(codeId));
	    finCode.setCode3Id(FinTaskHelper.getFinCode3(codeId));
	    finCode.setCode4Id(FinTaskHelper.getFinCode4(codeId));
	    finCode.setCode5Id(FinTaskHelper.getFinCode5(codeId));
	    finCode.setCode6Id(FinTaskHelper.getFinCode6(codeId));
	    finCodeDao.save(finCode);

	    if (finCode3Dao.getFinCode3(form.getCode3Id()) == null) {
		FinCode3 finCode3 = new FinCode3();
		finCode3.setCode3Id(form.getCode3Id());
		finCode3.setCodeName(form.getCode3Name());
		finCode3Dao.save(finCode3);
	    }

	    createUserBalanceByAccountId(codeId);
	    createUserBalanceByAccountId(FinTaskHelper.getAccountCode1(codeId));
	    createUserBalanceByAccountId(FinTaskHelper.getAccountCode2(codeId));
	}

    }

    public void createUserBalanceByAccountId(String accountId)
    {
	if (finYeDao.getYe(accountId) != null) return;
	FinYe ye = new FinYe();
	ye.setYeId(accountId);
	ye.setTotalAmount(new BigDecimal(0));
	finYeDao.save(ye);
    }

    public FinPz createFinPz(String pzNo, PzParamsForm pzForm, FinTask finTask)
    {
	FinPz pz = new FinPz();
	pz.setPzId(SequenceCreatorUtil.getTableId());
	pz.setCodeId(pzForm.getCodeId());
	pz.setDirection(pzForm.getDirection());
	pz.setDigest(finTask.getDigest() == null ? "" : finTask.getDigest());
	pz.setPzTime(new Date());
	pz.setAmount(new BigDecimal(pzForm.getAmount()));
	pz.setTradeTime(finTask.getTradeTime());
	pz.setBusiType(finTask.getBusiType());
	pz.setSerno(finTask.getSerno());
	pz.setPzNo(pzNo);
	pz.setTaskId(finTask.getTaskId());
	pz.setTrxCode(finTask.getTrxCode());
	pz.setPzKey(finTask.getTaskId() + pzForm.getCodeId() + (pzForm.getDirection() + 1));

	finPzDao.save(pz);
	return pz;
    }

    private List<FinYe> lockFinYe(List<FinPz> listFinPz)
    {
	StringBuffer yeIds = new StringBuffer();
	for (FinPz pz : listFinPz) {
	    yeIds.append(",").append(pz.getCodeId()).append(",").append(FinTaskHelper.getAccountCode1(pz.getCodeId())).append(",").append(FinTaskHelper.getAccountCode2(pz.getCodeId()));
	}
	Map<String, String> params = new HashMap<String, String>();
	params.put("yeId", yeIds.substring(1).toString());
	List<FinYe> yeInList = finYeDao.getYeInList(params);
	return yeInList;

    }

    public void updateFinYeAndCreateFinYeMx(List<FinPz> listFinPz, List<FinYe> finYes, Map<String, Boolean> mapShowFinYeMx)
    {

	for (FinPz pz : listFinPz) {
	    FinCode1 code1 = CacheContextHandler.getFinCode1ByCodeId1(FinTaskHelper.getFinCode1(pz.getCodeId()));

	    for (FinYe ye : finYes) {
		if (!ye.getYeId().equals(pz.getCodeId()) && !ye.getYeId().equals(FinTaskHelper.getAccountCode1(pz.getCodeId())) && !ye.getYeId().equals(FinTaskHelper.getAccountCode2(pz.getCodeId()))) {
		    continue;
		}
		FinMx mx = new FinMx();
		mx.setMxId(SequenceCreatorUtil.getTableId());
		mx.setPzId(pz.getPzId());
		mx.setCodeId(ye.getYeId());
		mx.setDirection(pz.getDirection());
		mx.setAmount(pz.getAmount());
		mx.setDigest(pz.getDigest());
		//mx.setPzTime(pz.getPzTime());
		mx.setTradeTime(pz.getTradeTime());
		mx.setBusiType(pz.getBusiType());
		mx.setOpenBal(ye.getTotalAmount());
		mx.setPzNo(pz.getPzNo());
		mx.setSerno(pz.getSerno());
		mx.setTaskId(pz.getTaskId());
		mx.setTrxCode(pz.getTrxCode());
		mx.setIsShow(ye.getYeId().length() == 31 || mapShowFinYeMx.get(pz.getPzNo()) ? 1 : 0);

		if (pz.getDirection() == 1) {
		    mx.setfDebit(pz.getAmount());
		}
		else {
		    mx.setfCredit(pz.getAmount());
		}

		if (code1.getDirection().equals(pz.getDirection())) {
		    BigDecimal yeAmount = ye.getTotalAmount().add(pz.getAmount());
		    ye.setTotalAmount(yeAmount);
		    mx.setCloseBal(yeAmount);
		}
		else {
		    BigDecimal yeAmount = ye.getTotalAmount().subtract(pz.getAmount());
		    ye.setTotalAmount(yeAmount);
		    mx.setCloseBal(yeAmount);
		}
		if (pz.getTrxCode().equals("3423") && pz.getCodeId().substring(25, 27) != "01") {
		    mx.setGains(exchangeGainsDao.getExchangeGains(pz.getSerno()));
		}
		
		if(!pz.getTrxCode().equals("4413")&&!pz.getTrxCode().equals("4414")){
			// 流动资金账户为负抛异常
			if (ye.getYeId().equals(pz.getCodeId()) && "02".equals(FinTaskHelper.getFinCode6(pz.getCodeId())) && ye.getTotalAmount().compareTo(new BigDecimal(0)) == -1) {
			    throw new FaException("010205", String.format("%s 流动资金账户不能为负", pz.getCodeId()));
			}
		}
		
		
		finMxDao.save(mx);
		finYeDao.update(ye);
	    }
	}

    }

	@Override
	public List<FinTask> getFinTaskByParam(FinTaskQueryForm form) {
		String serno = form.getSerno();
		String status = form.getStatus();
		return finTaskDao.getFinTasks(serno, status);
	}



}
