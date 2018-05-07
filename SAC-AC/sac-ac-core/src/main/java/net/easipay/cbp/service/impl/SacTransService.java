package net.easipay.cbp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.easipay.cbp.AcGenerator;
import net.easipay.cbp.AcLogger;
import net.easipay.cbp.AcTrxCodeRuler;
import net.easipay.cbp.constant.Exceptions;
import net.easipay.cbp.constant.SacConstants;
import net.easipay.cbp.dao.ISacOtrxInfoDao;
import net.easipay.cbp.dao.ISacTrxDetailDao;
import net.easipay.cbp.exception.AcException;
import net.easipay.cbp.form.SacRemittanceBatchIdForm;
import net.easipay.cbp.form.SacTransationReceiveForm;
import net.easipay.cbp.model.SacOtrxInfo;
import net.easipay.cbp.model.SacTrxDetail;
import net.easipay.cbp.sequence.SequenceCreatorUtil;
import net.easipay.cbp.service.IFinTaskService;
import net.easipay.cbp.service.ISacTransService;
import net.easipay.cbp.util.DateUtil;
import net.easipay.cbp.util.ExceptionUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service("sacTransService")
public class SacTransService implements ISacTransService
{
    @Autowired
    private ISacTrxDetailDao sacTransDao;
    @Autowired
    private ISacOtrxInfoDao sacOtrxInfoDao;
    @Autowired
    private IFinTaskService finTaskService;

    public void receiveSacTransationDetails(List<SacTransationReceiveForm> forms) throws AcException
    {
	List<SacOtrxInfo> sacOtrxInfos = insertSacOtrxInfos(forms);

	List<SacTrxDetail> sacTrxDetails = insertSacTrxDetailDetails(sacOtrxInfos);

	finTaskService.insertInterFinTasksForRule(sacOtrxInfos);
	
	AcLogger.info(String.format("请求交易总笔数： %s， 插入原始交易表总笔数： %s", forms.size(), sacOtrxInfos.size()));
	AcLogger.info(String.format("插入交易明细表总笔数： %s", sacTrxDetails.size()));
	AcLogger.info("接收交易记账执行成功...");
    }

    private List<SacOtrxInfo> insertSacOtrxInfos(List<SacTransationReceiveForm> forms)
    {
	List<SacOtrxInfo> sacOtrxInfos = new ArrayList<SacOtrxInfo>();
	SacOtrxInfo sacOtrxInfo = null;
	for (SacTransationReceiveForm form : forms) {
	    sacOtrxInfo = new SacOtrxInfo();
	    sacOtrxInfo.setId(SequenceCreatorUtil.getTableId());
	    sacOtrxInfo.setCusBillNo(form.getCusBillNo());
	    sacOtrxInfo.setPlatBillNo(form.getPlatBillNo());
	    sacOtrxInfo.setTrxSerialNo(form.getTrxSerialNo());
	    sacOtrxInfo.setOtrxSerialNo(form.getOtrxSerialNo());
	    sacOtrxInfo.setEtrxSerialNo(form.getEtrxSerialNo());
	    sacOtrxInfo.setTrxState(form.getTrxState());
	    sacOtrxInfo.setTrxStateDesc(form.getTrxStateDesc());
	    sacOtrxInfo.setCraccCusCode(AcGenerator.generateCusCode(form.getCraccCusType(), form.getCraccCardId()));
	    sacOtrxInfo.setCraccCusName(form.getCraccCusName());
	    sacOtrxInfo.setCraccCusType(form.getCraccCusType());
	    sacOtrxInfo.setCraccCardId(form.getCraccCardId());
	    sacOtrxInfo.setCraccNo(form.getCraccNo());
	    sacOtrxInfo.setCraccName(form.getCraccName());
	    sacOtrxInfo.setCraccNodeCode(form.getCraccNodeCode());
	    sacOtrxInfo.setCraccBankName(form.getCraccBankName());
	    sacOtrxInfo.setDraccCusCode(AcGenerator.generateCusCode(form.getDraccCusType(), form.getDraccCardId()));
	    sacOtrxInfo.setDraccCusType(form.getDraccCusType());
	    sacOtrxInfo.setDraccCardId(form.getDraccCardId());
	    sacOtrxInfo.setDraccCusName(form.getDraccCusName());
	    sacOtrxInfo.setDraccNo(form.getDraccNo());
	    sacOtrxInfo.setDraccName(form.getDraccName());
	    sacOtrxInfo.setDraccNodeCode(form.getDraccNodeCode());
	    sacOtrxInfo.setDraccBankName(form.getDraccBankName());
	    sacOtrxInfo.setPayCurrency(form.getPayCurrency());
	    sacOtrxInfo.setExRate(form.getExRate());
	    sacOtrxInfo.setPayAmount(form.getPayAmount());
	    sacOtrxInfo.setSacCurrency(form.getSacCurrency());
	    sacOtrxInfo.setSacAmount(form.getSacAmount());
	    sacOtrxInfo.setBussType(form.getBussType());
	    sacOtrxInfo.setTrxType(form.getTrxType());
	    sacOtrxInfo.setPayWay(form.getPayWay());
	    sacOtrxInfo.setSacFlag(SacConstants.STATE.NEW);
	    sacOtrxInfo.setInnConFlag(SacConstants.STATE.NEW);
	    sacOtrxInfo.setInnConStatus(SacConstants.STATE.NEW);
	    sacOtrxInfo.setPayconType(form.getPayconType());
	    sacOtrxInfo.setChaConFlag(SacConstants.STATE.NEW);
	    sacOtrxInfo.setChaConStatus(SacConstants.STATE.NEW);
	    sacOtrxInfo.setAccountStatus(SacConstants.STATE.NEW);
	    sacOtrxInfo.setTrxBatchNo(form.getTrxBatchNo());
	    sacOtrxInfo.setTrxTime(form.getTrxTime());
	    sacOtrxInfo.setUpdateTime(DateUtil.currentDate());
	    sacOtrxInfo.setCreateTime(DateUtil.currentDate());
	    sacOtrxInfo.setReversalStatus(SacConstants.STATE.NEW);
	    sacOtrxInfo.setMemo(form.getMemo());
	    sacOtrxInfo.setTrxErrDealType(form.getTrxErrDealType());
	    sacOtrxInfo.setTaxAmount(form.getTaxAmount());
	    sacOtrxInfo.setTransportExpenses(form.getTransportExpenses());
	    sacOtrxInfo.setChannelCost(form.getChannelCost());
	    sacOtrxInfo.setCusCharge(form.getCusCharge());
	    sacOtrxInfo.setTrxSuccTime(form.getTrxSuccTime());
	    sacOtrxInfo.setRemittanceBatchId(form.getRemittanceBatchId());
	    sacOtrxInfo.setCraccAreaCode(form.getCraccAreaCode());
	    sacOtrxInfo.setDraccAreaCode(form.getDraccAreaCode());

	    try {
		sacOtrxInfoDao.insertSacOtrxInfo(sacOtrxInfo);
		sacOtrxInfos.add(sacOtrxInfo);
	    } catch ( DuplicateKeyException e ) {
		AcLogger.error(String.format("Do not send duplicate sacOtrxInfo data : %s [%s]", sacOtrxInfo.getTrxSerialNo(), ExceptionUtil.getOriginalCause(e).getMessage()));
	    }
	}

	return sacOtrxInfos;
    }

    private List<SacTrxDetail> insertSacTrxDetailDetails(List<SacOtrxInfo> sacOtrxInfos)
    {
	List<SacTrxDetail> sacTrxDetails = new ArrayList<SacTrxDetail>();
	SacTrxDetail sacTrxDetail = null;
	for (SacOtrxInfo sacOtrxInfo : sacOtrxInfos) {

	    sacTrxDetail = new SacTrxDetail();
	    sacTrxDetail.setId(SequenceCreatorUtil.getTableId());
	    sacTrxDetail.setCusBillNo(sacOtrxInfo.getCusBillNo());
	    sacTrxDetail.setPlatBillNo(sacOtrxInfo.getPlatBillNo());
	    sacTrxDetail.setTrxBatchNo(sacOtrxInfo.getTrxBatchNo());
	    sacTrxDetail.setTrxSerialNo(sacOtrxInfo.getTrxSerialNo());
	    sacTrxDetail.setOtrxSerialNo(sacOtrxInfo.getOtrxSerialNo());
	    sacTrxDetail.setReceiverPlatAcc(AcTrxCodeRuler.generateReceiverPlatAcc(sacOtrxInfo));
	    sacTrxDetail.setPaymentPlatAcc(AcTrxCodeRuler.generatePaymentPlatAcc(sacOtrxInfo));
	    sacTrxDetail.setBussType(sacOtrxInfo.getBussType());
	    sacTrxDetail.setTrxType(sacOtrxInfo.getTrxType());
	    sacTrxDetail.setTrxAmount(sacOtrxInfo.getPayAmount());
	    sacTrxDetail.setTrxCurrencyType(sacOtrxInfo.getPayCurrency());
	    sacTrxDetail.setSacCurrency(sacOtrxInfo.getSacCurrency());
	    sacTrxDetail.setSacAmount(sacOtrxInfo.getSacAmount());
	    sacTrxDetail.setExRate(sacOtrxInfo.getExRate());
	    sacTrxDetail.setIssuingBank("");
	    sacTrxDetail.setPayconType(sacOtrxInfo.getPayconType());
	    sacTrxDetail.setPayWay(sacOtrxInfo.getPayWay());
	    sacTrxDetail.setDraccNodeCode(AcTrxCodeRuler.generateDraccNodeCode(sacOtrxInfo));
	    sacTrxDetail.setCraccNodeCode(AcTrxCodeRuler.generateCraccNodeCode(sacOtrxInfo));
	    sacTrxDetail.setReversalStatus(SacConstants.STATE.NEW);
	    sacTrxDetail.setTrxState(sacOtrxInfo.getTrxState());
	    sacTrxDetail.setChaConStatus(SacConstants.STATE.NEW);
	    sacTrxDetail.setTrxTime(sacOtrxInfo.getTrxTime());
	    sacTrxDetail.setCreateTime(DateUtil.currentDate());
	    sacTrxDetail.setUpdateTime(DateUtil.currentDate());
	    sacTrxDetail.setChnBatchNo("");
	    sacTrxDetail.setCusBatchNo("");
	    sacTrxDetail.setChannelCost(sacOtrxInfo.getChannelCost());
	    sacTrxDetail.setCusCharge(sacOtrxInfo.getCusCharge());
	    sacTrxDetail.setTrxSuccTime(sacOtrxInfo.getTrxSuccTime());
	    sacTrxDetail.setMemo(sacOtrxInfo.getMemo());
	    sacTrxDetail.setChnNo(AcTrxCodeRuler.generateChnNo(sacTrxDetail));
	    sacTrxDetail.setCraccAreaCode(sacOtrxInfo.getCraccAreaCode());
	    sacTrxDetail.setDraccAreaCode(sacOtrxInfo.getDraccAreaCode());
	    try {
		sacTransDao.insertSacTrxDetailDetail(sacTrxDetail);
		sacTrxDetails.add(sacTrxDetail);
	    } catch ( DuplicateKeyException e ) {
		AcLogger.error(String.format("Do not send duplicate sacTrxDetail data : %s [%s]", sacTrxDetail.getTrxSerialNo(), ExceptionUtil.getOriginalCause(e).getMessage()));
	    }
	}

	return sacTrxDetails;
    }

    public void updateSacTransationDetail(String trxSerialNo, String etrxSerialNo, String trxState, String trxStateDesc, Date trxSuccTime) throws AcException
    {
	SacOtrxInfo sacOtrxInfo = sacOtrxInfoDao.getSacOtrxInfo(trxSerialNo);
	if (sacOtrxInfo == null) {
	    throw new AcException(Exceptions.EXP_010204, "交易不存在");
	}
	if (sacOtrxInfo.getTrxState().equals(SacConstants.STATE.NEW)) {
	    sacTransDao.updateSacTrxDetailState(trxSerialNo, trxState, trxSuccTime);
	    sacOtrxInfoDao.updateSacOtrxInfoState(trxSerialNo, etrxSerialNo, trxState, trxStateDesc, trxSuccTime);

	    sacOtrxInfo.setTrxState(trxState);
	    sacOtrxInfo.setEtrxSerialNo(etrxSerialNo);
	    sacOtrxInfo.setTrxSuccTime(trxSuccTime);
	    sacOtrxInfo.setTrxStateDesc(trxStateDesc);
	    List<SacOtrxInfo> sacOtrxInfos = new ArrayList<SacOtrxInfo>();
	    sacOtrxInfos.add(sacOtrxInfo);
	    finTaskService.insertInterFinTasksForRule(sacOtrxInfos);
	    AcLogger.info(String.format("The tracsaction update success! [trxSerialNo - %s] ", trxSerialNo));
	}
	else {
	    AcLogger.info(String.format("The tracsaction state has been successed.Do not repeat! [trxSerialNo - %s]", trxSerialNo));
	}
    }

    @Override
    public SacOtrxInfo getSacOtrxInfo(String trxSerialNo)
    {
	return sacOtrxInfoDao.getSacOtrxInfo(trxSerialNo);
    }

    @Override
    public void updateSacOtrxRemittanceBatchId(List<SacRemittanceBatchIdForm> forms)
    {
	String[] trxSerialNos = new String[forms.size()];
	String[] remittanceBatchIds = new String[forms.size()];
	for (int i = 0; i < forms.size(); i++) {
	    trxSerialNos[i] = forms.get(i).getTrxSerialNo();
	    remittanceBatchIds[i] = forms.get(i).getRemittanceBatchId();
	}
	sacOtrxInfoDao.updateSacOtrxRemittanceBatchId(trxSerialNos, remittanceBatchIds);
    }

    
    @Override
    public void insertSacTrxRemittance(List<SacRemittanceBatchIdForm> forms)
    {
	String[] trxSerialNos = new String[forms.size()];
	String[] remittanceBatchIds = new String[forms.size()];
	for (int i = 0; i < forms.size(); i++) {
	    trxSerialNos[i] = forms.get(i).getTrxSerialNo();
	    remittanceBatchIds[i] = forms.get(i).getRemittanceBatchId();
	}
	sacOtrxInfoDao.insertSacTrxRemittance(trxSerialNos, remittanceBatchIds);
    }
}
