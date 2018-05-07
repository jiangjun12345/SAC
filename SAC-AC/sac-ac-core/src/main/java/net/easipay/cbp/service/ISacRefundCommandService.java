package net.easipay.cbp.service;

import net.easipay.cbp.exception.AcException;
import net.easipay.cbp.form.SacTransationReceiveForm;

@Deprecated
public interface ISacRefundCommandService
{
    public void insertSacRefundCommand(SacTransationReceiveForm form) throws AcException;

}
