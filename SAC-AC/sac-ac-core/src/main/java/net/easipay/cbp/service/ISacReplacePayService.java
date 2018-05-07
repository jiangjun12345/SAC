package net.easipay.cbp.service;

import net.easipay.cbp.exception.AcException;
import net.easipay.cbp.form.SacReplacePayReceiveForm;

public interface ISacReplacePayService
{
    public void receiveSacReplacePayCheckInfo(SacReplacePayReceiveForm form) throws AcException;

}
