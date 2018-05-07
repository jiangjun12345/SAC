package net.easipay.cbp.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import net.easipay.cbp.AcGenerator;
import net.easipay.cbp.constant.SacConstants;
import net.easipay.cbp.dao.ISacRecBatchDao;
import net.easipay.cbp.dao.ISacRecDetailDao;
import net.easipay.cbp.exception.AcException;
import net.easipay.cbp.form.SacReconBankReceiveForm;
import net.easipay.cbp.form.SacReconDifBankReceiveForm;
import net.easipay.cbp.form.SacReconInternalReceiveForm;
import net.easipay.cbp.model.SacRecBatch;
import net.easipay.cbp.model.SacRecDetail;
import net.easipay.cbp.sequence.SequenceCreatorUtil;
import net.easipay.cbp.service.ISacReconFileService;
import net.easipay.cbp.util.DateUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sacReconFileService")
public class SacReconFileService implements ISacReconFileService
{
    private Log log = LogFactory.getLog(SacReconFileService.class);
    @Autowired
    private ISacRecBatchDao sacRecBatchDao;
    @Autowired
    private ISacRecDetailDao sacRecDetailDao;

    public void receiveInternalReconFile(List<SacReconInternalReceiveForm> forms) throws Exception
    {
	log.info("内部对账文件开始上传...");
	if (forms == null || forms.size() == 0) {
	    throw new AcException(SacConstants.RTN_FAIL, "对账文件不能为空，请重新上传");
	}
	SacRecBatch sacRecBatch = new SacRecBatch();
	sacRecBatch.setRecBatchId(SequenceCreatorUtil.getTableId());
	sacRecBatch.setRecType(SacConstants.RECTYPE.TRX);
	sacRecBatch.setRecStartDate(forms.get(0).getRecStartDate());
	sacRecBatch.setRecEndDate(forms.get(0).getRecEndDate());
	sacRecBatch.setRecOper(forms.get(0).getRecOper());
	sacRecBatch.setRecCount(forms.get(0).getRecCount());
	sacRecBatch.setRecStatus(SacConstants.RECSTATUS.INIT);
	sacRecBatch.setChnCode("");
	sacRecBatch.setPayconType(forms.get(0).getPayconType());
	sacRecBatch.setCreateTime(DateUtil.currentDate());
	sacRecBatch.setUpdateTime(DateUtil.currentDate());
	sacRecBatch.setRecUnionId(AcGenerator.generateUnionId(sacRecBatch.getRecType() + sacRecBatch.getRecStartDate() + sacRecBatch.getRecEndDate() + sacRecBatch.getRecBatchId() + sacRecBatch.getPayconType()));
	sacRecBatch.setMemo("");
	sacRecBatchDao.insertSacRecBatch(sacRecBatch);

	
	log.info(String.format("内部对账文件批次号 - %s", sacRecBatch.getRecBatchId()));
	log.info(String.format("内部对账文件批次操作员 - %s", sacRecBatch.getRecOper()));
	log.info(String.format("内部对账文件批次详情 - [PayconType - %s, ChnCode - %s, RecCount - %d]", sacRecBatch.getPayconType(),sacRecBatch.getChnCode(),sacRecBatch.getRecCount()));
	log.info(String.format("内部对账文件批次交易时间范围 - [RecStartDate - %s, RecEndDate - %s]", DateUtil.format(sacRecBatch.getRecStartDate(), "yyyyMMddHHmmss"),DateUtil.format(sacRecBatch.getRecEndDate(), "yyyyMMddHHmmss")));
	
	if (forms.get(0).getRecCount() == 0) return;

	if (forms.size() != forms.get(0).getRecCount()) {
	    throw new AcException(SacConstants.RTN_FAIL, "对账文件对账数据总笔数不匹配，请重新上传");
	}

	List<SacRecDetail> listSacRecDetail = new ArrayList<SacRecDetail>();
	for (SacReconInternalReceiveForm form : forms) {
	    SacRecDetail sacRecDetail = new SacRecDetail();
	    sacRecDetail.setId(SequenceCreatorUtil.getTableId());
	    sacRecDetail.setRecBatchId(sacRecBatch.getRecBatchId());
	    sacRecDetail.setBusiType("");
	    sacRecDetail.setRecStartDate(form.getRecStartDate());
	    sacRecDetail.setRecEndDate(form.getRecEndDate());
	    sacRecDetail.setTrxSerialNo(form.getTrxSerialNo());
	    sacRecDetail.setTrxTime(form.getTrxTime());
	    sacRecDetail.setCurrencyType(form.getCurrencyType());
	    sacRecDetail.setPayAmount(new BigDecimal(form.getPayAmount()));
	    sacRecDetail.setBankSerialNo(form.getEtrxSerialNo());
	    sacRecDetail.setChnCode("");
	    sacRecDetail.setPayconType(form.getPayconType());
	    sacRecDetail.setRecOper(form.getRecOper());
	    sacRecDetail.setTrxCode(form.getTrxCode());
	    sacRecDetail.setPrivDomain(form.getPrivDomain());
	    sacRecDetail.setMemo("");
	    listSacRecDetail.add(sacRecDetail);
	}

	sacRecDetailDao.insertRecDetail(listSacRecDetail);
	
	log.info("内部对账文件上传结束...");
    }

    public void receiveBankReconFile(List<SacReconBankReceiveForm> forms) throws Exception
    {
	log.info("渠道对账文件开始上传...");
	if (forms == null || forms.size() == 0) {
	    throw new AcException(SacConstants.RTN_FAIL, "渠道对账文件不能为空，请重新上传");
	}

	SacRecBatch sacRecBatch = new SacRecBatch();
	sacRecBatch.setRecBatchId(SequenceCreatorUtil.getTableId());
	sacRecBatch.setRecType(SacConstants.RECTYPE.BANK);
	sacRecBatch.setRecStartDate(forms.get(0).getRecStartDate());
	sacRecBatch.setRecEndDate(forms.get(0).getRecEndDate());
	sacRecBatch.setRecOper(forms.get(0).getRecOper());
	sacRecBatch.setRecCount(forms.get(0).getRecCount());
	sacRecBatch.setRecStatus(SacConstants.RECSTATUS.INIT);
	sacRecBatch.setChnCode(forms.get(0).getChnCode());
	sacRecBatch.setPayconType(forms.get(0).getPayconType());
	sacRecBatch.setCreateTime(DateUtil.currentDate());
	sacRecBatch.setUpdateTime(DateUtil.currentDate());
	sacRecBatch.setRecUnionId(AcGenerator.generateUnionId(sacRecBatch.getRecType() + sacRecBatch.getRecStartDate() + sacRecBatch.getRecEndDate() + sacRecBatch.getChnCode() + sacRecBatch.getPayconType()));
	sacRecBatch.setMemo("");
	sacRecBatchDao.insertSacRecBatch(sacRecBatch);

	log.info(String.format("渠道对账文件批次号 - %s", sacRecBatch.getRecBatchId()));
	log.info(String.format("渠道对账文件批次操作员 - %s", sacRecBatch.getRecOper()));
	log.info(String.format("渠道对账文件批次详情 - [PayconType - %s, ChnCode - %s, RecCount - %d]", sacRecBatch.getPayconType(),sacRecBatch.getChnCode(),sacRecBatch.getRecCount()));
	log.info(String.format("渠道对账文件批次交易时间范围 - [RecStartDate - %s, RecEndDate - %s]", DateUtil.format(sacRecBatch.getRecStartDate(), "yyyyMMddHHmmss"),DateUtil.format(sacRecBatch.getRecEndDate(), "yyyyMMddHHmmss")));
	
	if (forms.get(0).getRecCount() == 0) return;
	
	if (forms.size() != forms.get(0).getRecCount()) {
	    throw new AcException(SacConstants.RTN_FAIL, "渠道对账文件对账数据总笔数不匹配，请重新上传");
	}

	List<SacRecDetail> listSacRecDetail = new ArrayList<SacRecDetail>();
	for (SacReconBankReceiveForm form : forms) {
	    SacRecDetail sacRecDetail = new SacRecDetail();
	    sacRecDetail.setId(SequenceCreatorUtil.getTableId());
	    sacRecDetail.setRecBatchId(sacRecBatch.getRecBatchId());
	    sacRecDetail.setBusiType(form.getBusiType());
	    sacRecDetail.setRecStartDate(form.getRecStartDate());
	    sacRecDetail.setRecEndDate(form.getRecEndDate());
	    sacRecDetail.setTrxSerialNo("");
	    sacRecDetail.setTrxTime(form.getTrxTime());
	    sacRecDetail.setCurrencyType(form.getCurrencyType());
	    sacRecDetail.setPayAmount(new BigDecimal(form.getPayAmount()));
	    sacRecDetail.setBankSerialNo(form.getBankSerialNo());
	    sacRecDetail.setChnCode(form.getChnCode());
	    sacRecDetail.setPayconType(form.getPayconType());
	    sacRecDetail.setRecOper(form.getRecOper());
	    sacRecDetail.setTrxSerialNo(form.getTrxSerialNo());
	    sacRecDetail.setTrxCode(form.getTrxCode());
	    sacRecDetail.setPrivDomain(form.getPrivDomain());
	    sacRecDetail.setMemo("");
	    listSacRecDetail.add(sacRecDetail);
	}
	sacRecDetailDao.insertRecDetail(listSacRecDetail);
	log.info("渠道对账文件上传结束...");
    }

    public void receiveBankDifReconFile(List<SacReconDifBankReceiveForm> forms) throws Exception
    {
	log.info("渠道差错对账文件开始上传...");
	if (forms == null || forms.size() == 0) {
	    throw new AcException(SacConstants.RTN_FAIL, "渠道差错对账文件不能为空，请重新上传");
	}
	SacRecBatch sacRecBatch = new SacRecBatch();
	sacRecBatch.setRecBatchId(SequenceCreatorUtil.getTableId());
	sacRecBatch.setRecType(SacConstants.RECTYPE.DIF);
	sacRecBatch.setRecStartDate(forms.get(0).getRecStartDate());
	sacRecBatch.setRecEndDate(forms.get(0).getRecEndDate());
	sacRecBatch.setRecOper(forms.get(0).getRecOper());
	sacRecBatch.setRecCount(forms.get(0).getRecCount());
	sacRecBatch.setRecStatus(SacConstants.RECSTATUS.INIT);
	sacRecBatch.setChnCode(forms.get(0).getChnCode());
	sacRecBatch.setPayconType(forms.get(0).getPayconType());
	sacRecBatch.setCreateTime(DateUtil.currentDate());
	sacRecBatch.setUpdateTime(DateUtil.currentDate());
	sacRecBatch.setRecUnionId(AcGenerator.generateUnionId(sacRecBatch.getRecType() + sacRecBatch.getRecStartDate() + sacRecBatch.getRecEndDate() + sacRecBatch.getChnCode() + sacRecBatch.getPayconType()));
	sacRecBatch.setMemo("");
	sacRecBatchDao.insertSacRecBatch(sacRecBatch);

	log.info(String.format("渠道差错对账文件批次号 - %s", sacRecBatch.getRecBatchId()));
	log.info(String.format("渠道差错对账文件批次操作员 - %s", sacRecBatch.getRecOper()));
	log.info(String.format("渠道差错对账文件批次详情 - [PayconType - %s, ChnCode - %s, RecCount - %d]", sacRecBatch.getPayconType(),sacRecBatch.getChnCode(),sacRecBatch.getRecCount()));
	log.info(String.format("渠道差错对账文件批次交易时间范围 - [RecStartDate - %s, RecEndDate - %s]", DateUtil.format(sacRecBatch.getRecStartDate(), "yyyyMMddHHmmss"),DateUtil.format(sacRecBatch.getRecEndDate(), "yyyyMMddHHmmss")));
	
	if (forms.get(0).getRecCount() == 0) return;

	if (forms.size() != forms.get(0).getRecCount()) {
	    throw new AcException(SacConstants.RTN_FAIL, "渠道差错对账文件对账数据总笔数不匹配，请重新上传");
	}
	
	List<SacRecDetail> listSacRecDetail = new ArrayList<SacRecDetail>();
	for (SacReconDifBankReceiveForm form : forms) {
	    SacRecDetail sacRecDetail = new SacRecDetail();
	    sacRecDetail.setId(SequenceCreatorUtil.getTableId());
	    sacRecDetail.setRecBatchId(sacRecBatch.getRecBatchId());
	    sacRecDetail.setBusiType(form.getBusiType());
	    sacRecDetail.setDiffType(form.getDiffType());
	    sacRecDetail.setRecStartDate(form.getRecStartDate());
	    sacRecDetail.setRecEndDate(form.getRecEndDate());
	    sacRecDetail.setTrxSerialNo("");
	    sacRecDetail.setTrxTime(form.getTrxTime());
	    sacRecDetail.setCurrencyType(form.getCurrencyType());
	    sacRecDetail.setPayAmount(new BigDecimal(form.getPayAmount()));
	    sacRecDetail.setBankSerialNo(form.getBankSerialNo());
	    sacRecDetail.setChnCode(form.getChnCode());
	    sacRecDetail.setPayconType(form.getPayconType());
	    sacRecDetail.setRecOper(form.getRecOper());
	    sacRecDetail.setTrxSerialNo(form.getTrxSerialNo());
	    sacRecDetail.setTrxCode(form.getTrxCode());
	    sacRecDetail.setPrivDomain(form.getPrivDomain());
	    sacRecDetail.setMemo("");
	    listSacRecDetail.add(sacRecDetail);
	}
	sacRecDetailDao.insertRecDetail(listSacRecDetail);
	log.info("渠道差错对账文件上传结束...");
    }
}
