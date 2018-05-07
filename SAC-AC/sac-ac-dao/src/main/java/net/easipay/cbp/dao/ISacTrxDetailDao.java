package net.easipay.cbp.dao;

import java.util.Date;
import java.util.List;

import net.easipay.cbp.model.SacTrxDetail;

public interface ISacTrxDetailDao
{
    public void insertSacTrxDetailDetail(List<SacTrxDetail> sacTrxDetails);

    public void updateSacTrxDetailState(String trxSerialNo, String trxState, Date trxSuccTime);

    public void deleteSacTrxDetail(String trxSerialNo, String trxState);

    public void insertSacTrxDetailDetail(SacTrxDetail sacTrxDetai);
}
