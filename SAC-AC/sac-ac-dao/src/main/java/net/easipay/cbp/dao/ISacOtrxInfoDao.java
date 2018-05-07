package net.easipay.cbp.dao;

import java.util.Date;
import java.util.List;

import net.easipay.cbp.model.SacOtrxInfo;

public interface ISacOtrxInfoDao
{
    public void insertSacOtrxInfo(SacOtrxInfo sacOtrxInfo);

    public void insertSacOtrxInfo(List<SacOtrxInfo> sacOtrxInfos);

    public void updateSacOtrxInfoState(String trxSerialNo, String etrxSerialNo, String trxState, String trxStateDesc, Date trxSuccTime);

    public SacOtrxInfo getSacOtrxInfo(String trxSerialNo);

    public void deleteSacOtrxInfo(String trxSerialNo, String trxState);

    public void updateSacOtrxRemittanceBatchId(String[] trxSerialNos, String[] remittanceBatchIds);
    
    public void insertSacTrxRemittance(String[] trxSerialNos, String[] remittanceBatchIds);
}
