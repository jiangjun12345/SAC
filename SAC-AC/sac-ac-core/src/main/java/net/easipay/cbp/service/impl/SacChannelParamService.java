package net.easipay.cbp.service.impl;

import java.math.BigDecimal;
import java.util.List;

import net.easipay.cbp.AcGenerator;
import net.easipay.cbp.constant.SacConstants;
import net.easipay.cbp.dao.ISacChannelParamDao;
import net.easipay.cbp.exception.AcException;
import net.easipay.cbp.form.SacChannelParamHandleForm;
import net.easipay.cbp.model.SacChannelParam;
import net.easipay.cbp.sequence.SequenceCreatorUtil;
import net.easipay.cbp.service.ISacChannelParamService;
import net.easipay.cbp.util.DateUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sacChannelParamService")
public class SacChannelParamService implements ISacChannelParamService
{
    @Autowired
    private ISacChannelParamDao sacChannelParamDao;

    public void receiveSacChannelParam(SacChannelParamHandleForm form) throws AcException
    {
	List<SacChannelParam> listSacChannelParam = sacChannelParamDao.listSacChannelParam(form.getChnCode(), form.getChnType(), form.getCurrencyType(),  null);

	SacChannelParam sacChannelParam = new SacChannelParam();
	
	sacChannelParam.setChnNo(AcGenerator.generateChnNo(form.getChnType(), form.getChnCode()));
	sacChannelParam.setBankNodeCode(form.getBankNodeCode());
	sacChannelParam.setChnCode(form.getChnCode());
	sacChannelParam.setChnType(form.getChnType());
	sacChannelParam.setChnName(form.getChnName());
	sacChannelParam.setSacBankName(form.getSacBankName());
	sacChannelParam.setAccountName(form.getAccountName());
	sacChannelParam.setBankAcc(form.getBankAcc());
	sacChannelParam.setCraccBankCode(form.getCraccBankCode());
	sacChannelParam.setCurrencyType(form.getCurrencyType());
	sacChannelParam.setSacPeriod(Short.valueOf(form.getSacPeriod()));
	sacChannelParam.setCostRate(new BigDecimal(form.getCostRate()));
	sacChannelParam.setCostComWay(form.getCostComWay());
	sacChannelParam.setCostSacWay(form.getCostSacWay());
	sacChannelParam.setCreateTime(DateUtil.currentDate());
	sacChannelParam.setUpdateTime(DateUtil.currentDate());
	sacChannelParam.setIsValidFlag(form.getIsValidFlag());
	sacChannelParam.setMemo(form.getMemo());
	
	if (listSacChannelParam.size() == 0) {
	    sacChannelParam.setId(SequenceCreatorUtil.getTableId());
	    sacChannelParamDao.insertSacChannelParam(sacChannelParam);
	}
	else {
	    sacChannelParam.setId(listSacChannelParam.get(0).getId());
	    sacChannelParamDao.updateSacChannelParam(sacChannelParam);
	}

	if(!sacChannelParam.getBankNodeCode().equals(sacChannelParam.getChnCode())){
	    return;
	}
	
	List<SacChannelParam> _listSacChannelParam = sacChannelParamDao.listSacChannelParam(form.getChnCode(), SacConstants.CHN_TYPE.DEPOSIT_BANK, form.getCurrencyType(),  null);
	sacChannelParam.setId(SequenceCreatorUtil.getTableId());
	sacChannelParam.setChnNo(AcGenerator.generateChnNo(SacConstants.CHN_TYPE.DEPOSIT_BANK, form.getChnCode()));
	sacChannelParam.setChnType(SacConstants.CHN_TYPE.DEPOSIT_BANK);
	if (_listSacChannelParam.size() == 0) {
	    sacChannelParamDao.insertSacChannelParam(sacChannelParam);
	}
	else {
	    sacChannelParamDao.updateSacChannelParam(sacChannelParam);
	}
	
    }

    @Override
    public List<SacChannelParam> listSacChannelParam(String chnCode, String chnType, String currencyType, String isValidFlag) throws AcException
    {
	return sacChannelParamDao.listSacChannelParam(chnCode, chnType, currencyType, isValidFlag);
    }
}
