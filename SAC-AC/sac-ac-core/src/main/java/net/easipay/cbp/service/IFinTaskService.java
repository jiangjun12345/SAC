package net.easipay.cbp.service;

import java.util.List;

import net.easipay.cbp.form.FinRuleTaskJoinForm;
import net.easipay.cbp.model.SacOtrxInfo;

public interface IFinTaskService
{
    /**
     * 
     * @param sacOtrxInfos
     */
    public void insertInterFinTasksForRule(List<SacOtrxInfo> sacOtrxInfos);

    /**
     * 
     * @param forms
     */
    public void insertForeignFinTasksForRule(List<FinRuleTaskJoinForm> forms);
}
