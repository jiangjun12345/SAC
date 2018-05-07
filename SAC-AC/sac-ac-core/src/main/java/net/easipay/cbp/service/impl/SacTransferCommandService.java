package net.easipay.cbp.service.impl;

import java.util.Date;

import net.easipay.cbp.dao.ISacTransferCommandDao;
import net.easipay.cbp.exception.AcException;
import net.easipay.cbp.form.SacDffOflCommandReceiveForm;
import net.easipay.cbp.form.SacTransferCommandReceiveForm;
import net.easipay.cbp.model.SacDffOflCommand;
import net.easipay.cbp.model.SacTransferCommand;
import net.easipay.cbp.sequence.SequenceCreatorUtil;
import net.easipay.cbp.service.ISacTransferCommandService;
import net.easipay.cbp.util.DateUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sacTransferCommandService")
public class SacTransferCommandService implements ISacTransferCommandService
{
    @Autowired
    private ISacTransferCommandDao sacTransferCommandDao;

    public void receiveSacTransferCommand(SacTransferCommandReceiveForm form) throws AcException
    {
	SacTransferCommand sacTransferCommand = new SacTransferCommand();
	sacTransferCommand.setCmdId(SequenceCreatorUtil.getTableId());
	sacTransferCommand.setCmdType(form.getCmdType());
	sacTransferCommand.setCmdSerialNo(form.getCmdSerialNo());
	sacTransferCommand.setReqTime(null);
	sacTransferCommand.setCmdState("N");
	sacTransferCommand.setCreateTime(DateUtil.currentDate());
	sacTransferCommand.setRtrxSerialNo("");
	sacTransferCommand.setRtnCode("");
	sacTransferCommand.setErtnCode("");
	sacTransferCommand.setErtnInfo("");
	sacTransferCommand.setRdoTime(null);
	sacTransferCommand.setMsgReceiver(form.getMsgReceiver());
	sacTransferCommand.setDbtCode(form.getDbtCode());
	sacTransferCommand.setDraccNo(form.getDraccNo());
	sacTransferCommand.setDraccName(form.getDraccName());
	sacTransferCommand.setDraccBankCode(form.getDraccBankCode());
	sacTransferCommand.setDraccBankBranch(form.getDraccBankBranch());
	sacTransferCommand.setPayCurrency(form.getPayCurrency());
	sacTransferCommand.setPayCount(form.getPayCount());
	sacTransferCommand.setPayAmount(form.getPayAmount());
	sacTransferCommand.setPayPri(form.getPayPri());
	sacTransferCommand.setVldDate(form.getVldDate());
	sacTransferCommand.setCrtCode(form.getCrtCode());
	sacTransferCommand.setCraccNo(form.getCraccNo());
	sacTransferCommand.setCraccName(form.getCraccName());
	sacTransferCommand.setCraccBankCode(form.getCraccBankCode());
	sacTransferCommand.setCraccBankBranch(form.getCraccBankBranch());
	sacTransferCommand.setReqSpt1(form.getReqSpt1());
	sacTransferCommand.setReqSpt2(form.getReqSpt2());
	sacTransferCommand.setReqSpt3(form.getReqSpt3());
	sacTransferCommand.setReqMemo(form.getReqMemo());
	sacTransferCommand.setBatchSerialNo(form.getBatchSerialNo());
	sacTransferCommand.setOtrxSerialNo(form.getOtrxSerialNo());
	sacTransferCommand.setOrgId(form.getOrgId());
	sacTransferCommand.setFlag(form.getFlag());
	sacTransferCommandDao.insertSacTransferCommand(sacTransferCommand);
    }

	@Override
	public void receiveSacDffOflCommand(SacDffOflCommandReceiveForm form)
			throws AcException {
		SacDffOflCommand oflCommand = new SacDffOflCommand();
		oflCommand.setId(SequenceCreatorUtil.getTableId());
		oflCommand.setBussType(form.getBussType());
		oflCommand.setCmdState("N");//新建状态
		oflCommand.setCmdType(form.getCmdType());
		oflCommand.setCraccBankName(form.getCraccBankName());
		oflCommand.setCraccBranchCode(form.getCraccBranchCode());
		oflCommand.setCraccCardId(form.getCraccCardId());
		oflCommand.setCraccName(form.getCraccName());
		oflCommand.setCraccNo(form.getCraccNo());
		oflCommand.setCraccNodeCode(form.getCraccNodeCode());
		oflCommand.setCreateTime(new Date());
		oflCommand.setDraccBankName(form.getDraccBankName());
		oflCommand.setDraccNodeCode(form.getDraccNodeCode());
		oflCommand.setPayAmount(form.getPayAmount());
		oflCommand.setPayCurrency(form.getPayCurrency());
		oflCommand.setSkSerialNo(form.getSkSerialNo());
		oflCommand.setYkSerialNo(form.getYkSerialNo());
		
		sacTransferCommandDao.insertSacDffOflCommand(oflCommand);
	    }

}
