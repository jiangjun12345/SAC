package net.easipay.cbp.service;

import net.easipay.cbp.exception.AcException;
import net.easipay.cbp.form.SacDffOflCommandReceiveForm;
import net.easipay.cbp.form.SacTransferCommandReceiveForm;


public interface ISacTransferCommandService
{
    public void receiveSacTransferCommand(SacTransferCommandReceiveForm form)throws AcException;
    
    public void receiveSacDffOflCommand(SacDffOflCommandReceiveForm form)throws AcException;
}
