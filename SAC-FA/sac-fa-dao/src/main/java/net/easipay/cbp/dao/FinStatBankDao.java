package net.easipay.cbp.dao;

import java.util.Map;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.FinStatBank;

public interface FinStatBankDao extends GenericDao<FinStatBank, Long> {
    public Map<String, String> callSpFaStatBank(String sp_date);
}
