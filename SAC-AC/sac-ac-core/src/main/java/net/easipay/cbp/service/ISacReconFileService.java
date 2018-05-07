package net.easipay.cbp.service;

import java.util.List;

import net.easipay.cbp.form.SacReconBankReceiveForm;
import net.easipay.cbp.form.SacReconDifBankReceiveForm;
import net.easipay.cbp.form.SacReconInternalReceiveForm;


public interface ISacReconFileService
{
    /**
     * 
     * @param forms
     */
    public void receiveInternalReconFile(List<SacReconInternalReceiveForm> forms) throws Exception;

    /**
     * 
     * @param forms
     */
    public void receiveBankReconFile(List<SacReconBankReceiveForm> forms) throws Exception;
    
    /**
     * 
     * @param forms
     */
    public void receiveBankDifReconFile(List<SacReconDifBankReceiveForm> forms) throws Exception;
}
