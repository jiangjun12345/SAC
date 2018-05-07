package net.easipay.cbp.dao;

import java.util.List;

import net.easipay.cbp.model.SacTrxCodeRule;

public interface ISacTrxCodeRuleDao
{
    public List<SacTrxCodeRule> listSacTrxCodeRule(String trxCode);
}
