package net.easipay.cbp.service.impl;

import java.util.List;

import net.easipay.cbp.AcGenerator;
import net.easipay.cbp.constant.Exceptions;
import net.easipay.cbp.constant.SacConstants;
import net.easipay.cbp.dao.ISacChannelParamDao;
import net.easipay.cbp.dao.ISacCusParamDao;
import net.easipay.cbp.exception.AcException;
import net.easipay.cbp.form.FinBalanceQueryForm;
import net.easipay.cbp.form.FinDailyBalanceRtnForm;
import net.easipay.cbp.form.FinYueQueryForm;
import net.easipay.cbp.form.SacAccountingDailyYueQueryForm;
import net.easipay.cbp.form.SacAccountingYueQueryForm;
import net.easipay.cbp.form.SacBalanceQueryForm;
import net.easipay.cbp.form.SacBalanceRtnForm;
import net.easipay.cbp.model.SacChannelParam;
import net.easipay.cbp.model.SacCusParameter;
import net.easipay.cbp.service.ISacAccountingService;
import net.easipay.dsfc.ws.jws.JwsClient;
import net.easipay.dsfc.ws.jws.JwsResult;

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

@Service("sacAccountingService")
public class SacAccountingService implements ISacAccountingService
{
    @Autowired
    private ISacCusParamDao sacCusParamDao;

    @Autowired
    private ISacChannelParamDao sacChannelParamDao;

    @Override
    public String queryYue(SacAccountingYueQueryForm form)
    {
	FinYueQueryForm finYueQueryForm = new FinYueQueryForm();
	finYueQueryForm.setCurrencyType(AcGenerator.transformCurrencyNumberType(form.getCurrencyType()));

	//银存
	if ("0".equals(form.getCusType())) {
	    finYueQueryForm.setAccountId("1002");
	    finYueQueryForm.setAccountType("00");
	    finYueQueryForm.setBussType("00");
	    finYueQueryForm.setSubAccountType("00");
	    List<SacChannelParam> listSacChannelParam = sacChannelParamDao.listSacChannelParam(form.getCusId(), SacConstants.CHN_TYPE.DEPOSIT_BANK, form.getCurrencyType(), SacConstants.IS_VALID_FLAG.YES);
	    if (listSacChannelParam == null || listSacChannelParam.size() != 1) throw new AcException(Exceptions.EXP_100001, "银行存款不存在，请稍后再试");
	    finYueQueryForm.setCusNo(listSacChannelParam.get(0).getChnNo());
	}
	//客户
	else {
	    finYueQueryForm.setAccountId("2202");
	    finYueQueryForm.setAccountType("03");
	    finYueQueryForm.setBussType(form.getBussType());
	    finYueQueryForm.setSubAccountType("02");

	    SacCusParameter sacCusParameter = sacCusParamDao.querySacCusParameter("", form.getCusId(), form.getCurrencyType(), form.getBussType());
	    if (sacCusParameter == null) throw new AcException(Exceptions.EXP_100001, "客户账户不存在，请稍后再试");
	    finYueQueryForm.setCusNo(sacCusParameter.getCusNo());
	}

	JwsClient jwsClient = new JwsClient("SAC-FA-0004");
	JwsResult result = jwsClient.putAllParam(finYueQueryForm).call();
	return result.getStringValue("totalAmount");
    }

	@Override
	public FinDailyBalanceRtnForm queryDailyYue(SacAccountingDailyYueQueryForm form) {

		//form.setCurrencyType(AcGenerator.transformCurrencyNumberType(form.getCurrencyType()));
		if(!"000".equals(form.getBussType())){
			SacCusParameter sacCusParameter = sacCusParamDao.querySacCusParameter("", form.getOrgCode(), form.getCurrencyType(), form.getBussType());
			if (sacCusParameter == null) throw new AcException(Exceptions.EXP_100001, "客户账户不存在");
			
		}
	
		JwsClient jwsClient = new JwsClient("SAC-FA-0008");
		JwsResult result = jwsClient.putAllParam(form).call();
		return result.getBean("finDailyBalance",FinDailyBalanceRtnForm.class);
	    
	}

	@Override
	public List<SacBalanceRtnForm> queryBalance(SacBalanceQueryForm form) {
		String orgCode = form.getOrgCode();
		String cusType = form.getCusType();
		String cusNodeCode = AcGenerator.generateCusCode(cusType, orgCode);
		
		FinBalanceQueryForm balForm = new FinBalanceQueryForm();
		balForm.setCusCode(cusNodeCode);
		balForm.setBalType(form.getBalType());
		balForm.setBussType(form.getBussType());
		balForm.setCurrency(form.getCurrency());
		JwsClient jwsClient = new JwsClient("SAC-FA-0009");
		JwsResult result = jwsClient.putAllParam(balForm).call();
		return result.getList("balanceList", SacBalanceRtnForm.class);
	}

}
