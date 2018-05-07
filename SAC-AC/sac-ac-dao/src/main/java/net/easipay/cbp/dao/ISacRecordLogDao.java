package net.easipay.cbp.dao;

import java.util.List;

import net.easipay.cbp.model.SacRecordLog;

public interface ISacRecordLogDao
{
    public void insertSacRecordLog(List<SacRecordLog> sacRecordLogs);
}
