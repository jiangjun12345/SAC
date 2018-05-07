package net.easipay.cbp.service;

import java.util.List;

import net.easipay.cbp.form.FinDailyBalanceRtnForm;
import net.easipay.cbp.form.SacAccountingDailyYueQueryForm;
import net.easipay.cbp.form.SacAccountingYueQueryForm;
import net.easipay.cbp.form.SacBalanceQueryForm;
import net.easipay.cbp.form.SacBalanceRtnForm;

public interface ISacAccountingService
{
    public String queryYue(SacAccountingYueQueryForm form);
    
    public FinDailyBalanceRtnForm queryDailyYue(SacAccountingDailyYueQueryForm form);
    
    public List<SacBalanceRtnForm> queryBalance(SacBalanceQueryForm form);
}
