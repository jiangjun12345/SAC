package net.easipay.cbp.service.impl;

import java.util.ArrayList;
import java.util.List;

import net.easipay.cbp.dao.ISacRecDiffDao;
import net.easipay.cbp.form.SacRecDifferencesQueryForm;
import net.easipay.cbp.form.SacRecDifferencesQueryResult;
import net.easipay.cbp.form.SacRecDifferencesUpdateForm;
import net.easipay.cbp.model.SacRecDifferences;
import net.easipay.cbp.service.ISacRecDiffService;
import net.easipay.dsfc.ws.jws.JwsClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sacRecDiffService")
public class SacRecDiffService implements ISacRecDiffService
{
    @Autowired
    private ISacRecDiffDao sacRecDiffDao;

    public List<SacRecDifferencesQueryResult> querySacRecDifferencesList(SacRecDifferencesQueryForm form)
    {
	List<SacRecDifferences> listSacRecDifferences = sacRecDiffDao.querySacRecDifferencesList(form.getPayconType(), form.getRecOper(), form.getRecStartDate(),form.getRecEndDate(), form.getStatus());
	List<SacRecDifferencesQueryResult> listSacRecDifferencesResultForm = new ArrayList<SacRecDifferencesQueryResult>();
	for (SacRecDifferences sacRecDifferences : listSacRecDifferences) {
	    SacRecDifferencesQueryResult result = new SacRecDifferencesQueryResult();
	    result.setRecBatchId(String.valueOf(sacRecDifferences.getRecBatchId()));
	    result.setRecDetailId(String.valueOf(sacRecDifferences.getRecDetailId()));
	    result.setRecStartDate(sacRecDifferences.getRecStartDate());
	    result.setRecEndDate(sacRecDifferences.getRecEndDate());
	    result.setTrxSerialNo(sacRecDifferences.getTrxSerialNo());
	    result.setTrxTime(sacRecDifferences.getTrxTime());
	    result.setCurrencyType(sacRecDifferences.getCurrencyType());
	    result.setPayAmount(sacRecDifferences.getPayAmount().toString());
	    result.setBankSerialNo(sacRecDifferences.getBankSerialNo());
	    result.setChnCode(sacRecDifferences.getChnCode());
	    result.setPayconType(sacRecDifferences.getPayconType());
	    result.setRecDiffType(sacRecDifferences.getRecDiffType());
	    result.setRecDiffDesc(sacRecDifferences.getRecDiffDesc());
	    result.setStatus(sacRecDifferences.getStatus());
	    result.setDealType(sacRecDifferences.getDealType());
	    result.setDealOper(sacRecDifferences.getDealOper());
	    result.setCreateTime(sacRecDifferences.getCreateTime());
	    result.setUpdateTime(sacRecDifferences.getUpdateTime());
	    result.setTrxCode(sacRecDifferences.getTrxCode());
	    result.setPrivDomain(sacRecDifferences.getPrivDomain());
	    result.setMemo(sacRecDifferences.getMemo());
	    listSacRecDifferencesResultForm.add(result);
	}
	return listSacRecDifferencesResultForm;
    }

    public void updateSacRecDifferences(SacRecDifferencesUpdateForm form)
    {
	JwsClient jwsClient = new JwsClient("SAC-REC-0001");
	jwsClient.putAllParam(form).call();
    }

}
