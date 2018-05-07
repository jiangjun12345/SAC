package net.easipay.cbp.service.impl;

import net.easipay.cbp.dao.ISacRefundCommandDao;
import net.easipay.cbp.exception.AcException;
import net.easipay.cbp.form.SacTransationReceiveForm;
import net.easipay.cbp.model.SacRefundCommand;
import net.easipay.cbp.sequence.SequenceCreatorUtil;
import net.easipay.cbp.service.ISacRefundCommandService;
import net.easipay.cbp.util.DateUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Deprecated
@Service("sacRefundCommandService")
public class SacRefundCommandService implements ISacRefundCommandService
{
    @Autowired
    private ISacRefundCommandDao sacRefundCommandDao;

    @Override
    public void insertSacRefundCommand(SacTransationReceiveForm form) throws AcException
    {
	if (!"1316".equals(form.getTrxType()) && !"3303".equals(form.getTrxType())) {
	    return;
	}
	SacRefundCommand sacRefundCommand = new SacRefundCommand();
	sacRefundCommand.setWpRefundId(SequenceCreatorUtil.getTableId());
	sacRefundCommand.setTrxSerialNo(form.getTrxSerialNo());
	sacRefundCommand.setOtrxSerialNo(form.getOtrxSerialNo());
	sacRefundCommand.setCreateTime(DateUtil.currentDate());
	sacRefundCommand.setPayAmount(form.getPayAmount());
	sacRefundCommand.setCrtCode(form.getCraccCardId());
	sacRefundCommand.setBankNodeCode(form.getDraccNodeCode());
	sacRefundCommand.setTrxState("1");
	sacRefundCommand.setRtrxSerialNo("");
	sacRefundCommand.setAuditState("00");
	sacRefundCommand.setLastUpdateTime(DateUtil.currentDate());
	sacRefundCommand.setCraccNo(form.getCraccNo());
	sacRefundCommand.setCraccName(form.getCraccName());
	sacRefundCommand.setCraccBankBranch(form.getCraccBankName());
	sacRefundCommand.setPayCurrency(form.getPayCurrency());
	sacRefundCommand.setExpBatch("");
	sacRefundCommandDao.insertSacRefundCommand(sacRefundCommand);
    }

}
