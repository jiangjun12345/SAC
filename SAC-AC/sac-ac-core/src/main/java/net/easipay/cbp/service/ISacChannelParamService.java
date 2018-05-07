package net.easipay.cbp.service;

import java.util.List;

import net.easipay.cbp.exception.AcException;
import net.easipay.cbp.form.SacChannelParamHandleForm;
import net.easipay.cbp.model.SacChannelParam;


public interface ISacChannelParamService
{
    /**
     * 
     * @param form
     * @throws AcException
     */
    public void receiveSacChannelParam(SacChannelParamHandleForm form) throws AcException;
    
    /**
     * 
     * @param chnCode
     * @param chnType
     * @param currencyType
     * @param isValidFlag
     * @return
     */
    public List<SacChannelParam> listSacChannelParam(String chnCode, String chnType, String currencyType,String isValidFlag) throws AcException;
}
