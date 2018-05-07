package net.easipay.cbp.dao;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.FinDailyBalance;
import net.easipay.cbp.model.FinYe;

public interface FinYeDao extends GenericDao<FinYe, Long>
{

    public List<FinYe> getYeInList(Map<String, String> param);

    public FinYe getYe(String yeId);
    
    public List<FinYe> getBalanceByParam(Map<String, String> param);
    
    public FinDailyBalance getDailyYe(Map<String, String> param);
}
