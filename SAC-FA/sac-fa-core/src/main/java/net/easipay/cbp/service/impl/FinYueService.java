package net.easipay.cbp.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.FinYeDao;
import net.easipay.cbp.form.FinBalanceQueryForm;
import net.easipay.cbp.form.FinBalanceRtnForm;
import net.easipay.cbp.form.FinDailyYueQueryForm;
import net.easipay.cbp.form.FinYueQueryForm;
import net.easipay.cbp.model.FinDailyBalance;
import net.easipay.cbp.model.FinYe;
import net.easipay.cbp.service.IFinYueService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author mchen
 * @date 2016-5-10
 */
@Service("finYueService")
public class FinYueService implements IFinYueService
{
    @Autowired
    public FinYeDao finYeDao;

    @Override
    public String queryYue(FinYueQueryForm form)
    {
	FinYe ye = finYeDao.getYe(form.toCodeId());
	if (ye == null) return "0";
	return ye.getTotalAmount().toString();
    }

	@Override
	public List<FinBalanceRtnForm> queryBalance(FinBalanceQueryForm form) {
		String cusCode = form.getCusCode();
		String bussType = form.getBussType();
		String currency = form.getCurrency();
		String balType = form.getBalType();
		StringBuffer bf = new StringBuffer("220203");
		bf.append(cusCode)
		  .append(currency);
		StringBuffer bf1 = new StringBuffer("220203");
		bf1.append(cusCode)
		.append(currency);
		Map<String,String> queryMap = new HashMap<String,String>();
		if("000".equals(bussType)){
			bf1.append("__");
			//查询所有的业务余额
			if("00".equals(balType)){
				
			}else{
				bf1.append("02");
			}
			queryMap.put("allBizBal", bf1.toString());
		}else{
			bf.append(bussType);
			if("00".equals(balType)){
			
			}else{
				bf.append("02");
			}
			queryMap.put("singleBizBal", bf.toString());
		}
		List<FinYe> yeList = finYeDao.getBalanceByParam(queryMap);
		
		List<FinBalanceRtnForm> rtnList = new ArrayList<FinBalanceRtnForm>();
		if("000".equals(bussType)){
			String bussTypeAll = "000";
			BigDecimal balanceAll = new BigDecimal("0.00");
			for(FinYe ye : yeList){
				FinBalanceRtnForm ff = new FinBalanceRtnForm();
				String bussType1 = ye.getYeId().substring(27, 29);
				BigDecimal balance1 = ye.getTotalAmount();
				ff.setPlatAccount(ye.getYeId().substring(0,29));
				ff.setBalance(balance1.toString());
				ff.setBussType(bussType1);
				balanceAll = balanceAll.add(balance1);
				rtnList.add(ff);
			}
			FinBalanceRtnForm ff = new FinBalanceRtnForm();
			ff.setPlatAccount(bf1.substring(0,27));
			ff.setBalance(balanceAll.toString());
			ff.setBussType(bussTypeAll);
			rtnList.add(ff);
		}else{
			String balance = "0.00";
			if(yeList.size()>0){
				balance = yeList.get(0).getTotalAmount().toString();
			}
			FinBalanceRtnForm ff = new FinBalanceRtnForm();
			ff.setPlatAccount(bf.substring(0,29));
			ff.setBalance(balance);
			ff.setBussType(bussType);
			rtnList.add(ff);
		}
		return rtnList;
	}

	@Override
	public FinDailyBalance queryDailyBalance(FinDailyYueQueryForm form) {
		Map<String,String> param = new HashMap<String, String>();
		String bussType = form.getBussType();
		String subAccountType = form.getSubAccountType();
		String currencyType = form.getCurrencyType();
		String orgCode = form.getOrgCode();
		String queryDate = form.getQueryDate();
		param.put("queryDate", queryDate);
		param.put("orgCardId", orgCode);
		param.put("currency", currencyType);
		if(!"000".equals(bussType)){
			param.put("bussType", bussType);
		}
		if(!"000".equals(subAccountType)){
			param.put("childAccType",subAccountType);
		}
		FinDailyBalance ye = finYeDao.getDailyYe(param);
		return ye;
	}

}
