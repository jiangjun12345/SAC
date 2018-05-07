package net.easipay.cbp.service;

import net.easipay.cbp.exception.AcException;
import net.easipay.cbp.form.SacChargeApplyForm;


public interface ISacChargeApplyService
{
    /**
     * 
     * @param form
     * @throws AcException
     */
    public void receiveSacChargeApply(SacChargeApplyForm form) throws AcException;

}
