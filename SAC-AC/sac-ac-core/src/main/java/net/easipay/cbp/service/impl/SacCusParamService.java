package net.easipay.cbp.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.easipay.cbp.AcGenerator;
import net.easipay.cbp.constant.SacConstants;
import net.easipay.cbp.dao.ISacCusParamDao;
import net.easipay.cbp.exception.AcException;
import net.easipay.cbp.form.SacCusParameterHandleForm;
import net.easipay.cbp.form.SacCusParameterValidateForm;
import net.easipay.cbp.model.SacCusParameter;
import net.easipay.cbp.sequence.SequenceCreatorUtil;
import net.easipay.cbp.service.ISacCusParamService;
import net.easipay.cbp.util.DateUtil;
import net.easipay.dsfc.ws.jws.JwsClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sacCusParamService")
public class SacCusParamService implements ISacCusParamService
{
    @Autowired
    private ISacCusParamDao sacCusParamDao;

    public void receiveSacCusParameter(SacCusParameterHandleForm form) throws AcException
    {
	SacCusParameter querySacChannelParam = sacCusParamDao.querySacCusParameter("", form.getOrgCardId(), form.getSacCurrency(), form.getBussType());

	SacCusParameter sacCusParameter = new SacCusParameter();
	sacCusParameter.setCusNo(AcGenerator.generateCusCode(form.getCusType(), form.getOrgCardId()));
	sacCusParameter.setCusType(form.getCusType());
	sacCusParameter.setMerchantNcode(form.getMerchantNcode());
	sacCusParameter.setCusName(form.getCusName());
	sacCusParameter.setCusPlatAcc(AcGenerator.generateCusPlatAcc(sacCusParameter.getCusNo(), form.getSacCurrency()));
	sacCusParameter.setCusPlatAccName(AcGenerator.generateCusName(form.getCusName(), form.getSacCurrency()));
	sacCusParameter.setRefundFlag(form.getRefundFlag());
	sacCusParameter.setCurrencyType(form.getSacCurrency());
	sacCusParameter.setSacBankAcc(form.getSacBankAcc());
	sacCusParameter.setAccName(form.getAccName());
	sacCusParameter.setDepositBank(form.getDepositBank());
	sacCusParameter.setCraccBankCode(form.getCraccBankCode());
	sacCusParameter.setFeeRate(form.getFeeRate() == null ? new BigDecimal(0) : new BigDecimal(form.getFeeRate()));
	sacCusParameter.setFeeComWay(form.getFeeComWay());
	sacCusParameter.setFeeSacWay(form.getFeeSacWay());
	sacCusParameter.setSacPeriod(form.getSacPeriod());
	sacCusParameter.setTrxUpLim(form.getFeeRate() == null ? new BigDecimal(0) : new BigDecimal(form.getTrxUpLim()));
	sacCusParameter.setSacType(form.getSacType());
	sacCusParameter.setIntervalNumber(form.getIntervalNumber());
	sacCusParameter.setSacCurrency(form.getSacCurrency());
	sacCusParameter.setSacStartAmount(form.getFeeRate() == null ? new BigDecimal(0) : new BigDecimal(form.getSacStartAmount()));
	sacCusParameter.setIsValidFlag(form.getIsVaildFlag());
	sacCusParameter.setCreateTime(querySacChannelParam == null ? DateUtil.currentDate() : querySacChannelParam.getCreateTime());
	sacCusParameter.setUpdateTime(DateUtil.currentDate());
	sacCusParameter.setCusFeeFlag(form.getCusFeeFlag());
	sacCusParameter.setOrgCardId(form.getOrgCardId());
	sacCusParameter.setBussType(form.getBussType());
	sacCusParameter.setMemo(form.getMemo());

	if (querySacChannelParam == null) {
	    sacCusParameter.setId(SequenceCreatorUtil.getTableId());
	    sacCusParamDao.insertSacCusParameter(sacCusParameter);
	    if (SacConstants.CUSTYPE.M.equals(form.getCusType())) appendDefaultUserPermission(form.getOrgCardId(), form.getCusName());
	}
	else {
	    sacCusParameter.setId(querySacChannelParam.getId());
	    sacCusParamDao.updateSacCusParameter(sacCusParameter);
	}
    }

    private void appendDefaultUserPermission(String orgId, String loginName)
    {
	JwsClient jwsClient = new JwsClient("SAC-UC-0015");
	jwsClient.putParam("orgId", orgId).putParam("loginName", loginName).call();
    }
    
	@Override
	public Map<String,Boolean> validateSacCusParameter(List<SacCusParameterValidateForm> forms)
			throws AcException {
		
		Map<String,Boolean> responseMap = new HashMap<String,Boolean>();
		for(SacCusParameterValidateForm  cus :  forms){
			Boolean isExist=false;
			SacCusParameter querySacChannelParam = sacCusParamDao.querySacCusParameter("", cus.getOrgCardId(), cus.getCurrency(), cus.getBussType());
			if(querySacChannelParam!=null){
				isExist = true;
			}
			responseMap.put(cus.getOrgCardId()+"_"+cus.getCurrency()+"_"+cus.getBussType(), isExist);
		}
		return responseMap;
		
	}



}
