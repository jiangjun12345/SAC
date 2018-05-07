package net.easipay.cbp.dao;

import java.util.List;

import net.easipay.cbp.model.SacRecDetail;

public interface ISacRecDetailDao
{
    public void insertRecDetail(final List<SacRecDetail> listSacRecDetail);
}
