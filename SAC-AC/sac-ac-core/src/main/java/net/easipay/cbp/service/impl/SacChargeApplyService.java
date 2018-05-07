package net.easipay.cbp.service.impl;

import net.easipay.cbp.dao.ISacChargeApplyDao;
import net.easipay.cbp.exception.AcException;
import net.easipay.cbp.form.SacChargeApplyForm;
import net.easipay.cbp.model.SacChargeApply;
import net.easipay.cbp.sequence.SequenceCreatorUtil;
import net.easipay.cbp.service.ISacChargeApplyService;
import net.easipay.cbp.util.DateUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sacChargeApplyService")
public class SacChargeApplyService implements ISacChargeApplyService
{
    @Autowired
    private ISacChargeApplyDao sacChargeApplyDao;

    public void receiveSacChargeApply(SacChargeApplyForm form) throws AcException
    {
	SacChargeApply sacChargeApply = new SacChargeApply();
	sacChargeApply.setChargeApplyId(SequenceCreatorUtil.getTableId());
	sacChargeApply.setBussType(form.getBussType());
	sacChargeApply.setApplyOrgId(form.getApplyOrgId());
	sacChargeApply.setApplyDbtCode(form.getApplyDbtCode());
	sacChargeApply.setApplyOrgName(form.getApplyOrgName());
	sacChargeApply.setApplyCode(form.getApplyCode());
	sacChargeApply.setPayCurrency(form.getPayCurrency());
	sacChargeApply.setPayAmount(form.getPayAmount());
	sacChargeApply.setApplyDate(DateUtil.currentDate());
	sacChargeApply.setExpiredDate(DateUtil.currentDate());
	sacChargeApply.setCraccNo(form.getCraccNo());
	sacChargeApply.setCraccName(form.getCraccName());
	sacChargeApply.setCraccNodeCode(form.getCraccNodeCode());
	sacChargeApply.setCraccBankName(form.getCraccBankName());
	sacChargeApply.setApplyState("0");
	sacChargeApply.setCheckedSerialNo("");
	sacChargeApply.setCreateTime(DateUtil.currentDate());
	sacChargeApply.setLastUpdateTime(DateUtil.currentDate());
	sacChargeApply.setDraccNo(form.getDraccNo());
	sacChargeApply.setDraccName(form.getDraccName());
	sacChargeApply.setApplyMemo(form.getApplyMemo());
	sacChargeApplyDao.insertSacChargeApply(sacChargeApply);
    }

}
