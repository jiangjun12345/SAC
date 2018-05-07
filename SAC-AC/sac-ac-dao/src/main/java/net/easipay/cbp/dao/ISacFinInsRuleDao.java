package net.easipay.cbp.dao;

import java.util.List;

import net.easipay.cbp.model.SacFinInsRule;


public interface ISacFinInsRuleDao
{
    /**
     * 
     * @param listFinTask
     */
    public List<SacFinInsRule> listSacFinInsRule(String trxCode, String trxState);
}
