package net.easipay.cbp.service;

import java.util.List;

import net.easipay.cbp.form.FinBalanceQueryForm;
import net.easipay.cbp.form.FinBalanceRtnForm;
import net.easipay.cbp.form.FinDailyYueQueryForm;
import net.easipay.cbp.form.FinYueQueryForm;
import net.easipay.cbp.model.FinDailyBalance;

public interface IFinYueService
{
    public String queryYue(FinYueQueryForm form);
    
    public List<FinBalanceRtnForm> queryBalance(FinBalanceQueryForm form);
    
    public FinDailyBalance queryDailyBalance(FinDailyYueQueryForm form);
}
