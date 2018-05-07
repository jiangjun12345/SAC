package net.easipay.cbp.service.impl;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.IFinMxDao;
import net.easipay.cbp.dao.IFinStatBankDao;
import net.easipay.cbp.model.FinMx;
import net.easipay.cbp.model.FinStatBankForm;
import net.easipay.cbp.service.IFinStatBankService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("finStatBankService")
public class FinStatBankServiceImpl implements IFinStatBankService {

	@Autowired
	private IFinStatBankDao finStatBankDao;
	
	@Autowired
	private IFinMxDao finMxDao;
	
	@Override
	public List<FinStatBankForm> selectFinStatBankForSplit(Map<String,Object> finStatBankMap,
			int pageNo, int pageSize) {
		return finStatBankDao.selectFinStatBankForSplit(finStatBankMap, pageNo, pageSize);
	}

	@Override
	public int selectFinStatBankCounts(Map<String,Object> finStatBankMap) {
		return finStatBankDao.selectFinStatBankCounts(finStatBankMap);
	}

	@Override
	public List<FinMx> selectPreparedFundDetail(Map<String, Object> paramMap) {
		//return finMxDao.listSplitFinMxCollect(paramMap);
		
		String currency = paramMap.get("currency").toString();
		if("CNY".equals(currency)){//人民币
			//根据交易日期和codeId查询明细表
			return finMxDao.listSplitFinMx(paramMap);
		}else{//外币
			return finMxDao.listSplitFinMxCollect(paramMap);
		}
	}

	@Override
	public int selectPreparedFundDetailCount(Map<String, Object> paramMap) {
		//return finMxDao.getFinMxCollectCount(paramMap);
		String currency = paramMap.get("currency").toString();
		if("CNY".equals(currency)){//人民币
			return finMxDao.getFinMxCount(paramMap);
		}else{//外币
			return finMxDao.getFinMxCollectCount(paramMap);
		}
	}
	
}
