package net.easipay.cbp.service;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.exception.AcException;
import net.easipay.cbp.form.SacCusParameterHandleForm;
import net.easipay.cbp.form.SacCusParameterValidateForm;


public interface ISacCusParamService
{
    /**
     * 
     * @param form
     * @throws AcException
     */
    public void receiveSacCusParameter(SacCusParameterHandleForm form) throws AcException;
    
    /**
     * 
     * @param form
     * @throws AcException
     */
    public Map<String,Boolean> validateSacCusParameter(List<SacCusParameterValidateForm> forms) throws AcException;


}
