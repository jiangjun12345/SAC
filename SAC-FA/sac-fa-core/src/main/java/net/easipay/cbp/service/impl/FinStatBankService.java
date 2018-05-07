package net.easipay.cbp.service.impl;

import java.util.Map;

import net.easipay.cbp.DateUtil;
import net.easipay.cbp.dao.FinStatBankDao;
import net.easipay.cbp.model.FinMx;
import net.easipay.cbp.service.IFinStatBankService;
import net.easipay.cbp.service.base.impl.GenericManagerImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("finStatBankService")
public class FinStatBankService extends GenericManagerImpl<FinMx, Long> implements IFinStatBankService
{

    @Autowired
    private FinStatBankDao finStatBankDao;

    @Override
    public void autoFinDailyStatBank()
    {
	String statDate = DateUtil.getYesterday(DateUtil.currentDate(), "yyyyMMdd");
	Map<String, String> result = finStatBankDao.callSpFaStatBank(statDate);
	String code = result.get("err_num");
	if(!"0".equals(code)){
	    throw new RuntimeException(String.valueOf(result.get("err_msg")));
	}
    }


}
