package net.easipay.cbp.service;

import java.util.Date;
import java.util.List;

import net.easipay.cbp.exception.AcException;
import net.easipay.cbp.form.SacRemittanceBatchIdForm;
import net.easipay.cbp.form.SacTransationReceiveForm;
import net.easipay.cbp.model.SacOtrxInfo;

public interface ISacTransService
{
    public void receiveSacTransationDetails(List<SacTransationReceiveForm> forms) throws AcException;

    public void updateSacTransationDetail(String trxSerialNo, String etrxSerialNo, String trxState, String trxStateDesc, Date trxSuccTime) throws AcException;

    public SacOtrxInfo getSacOtrxInfo(String trxSerialNo);

    public void updateSacOtrxRemittanceBatchId(List<SacRemittanceBatchIdForm> forms);

    public void insertSacTrxRemittance(List<SacRemittanceBatchIdForm> forms);
}
