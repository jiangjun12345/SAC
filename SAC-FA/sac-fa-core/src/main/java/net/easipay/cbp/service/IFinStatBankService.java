package net.easipay.cbp.service;

import net.easipay.cbp.model.FinMx;
import net.easipay.cbp.service.base.GenericManager;

public interface IFinStatBankService extends GenericManager<FinMx, Long>
{
    public void autoFinDailyStatBank();
}
