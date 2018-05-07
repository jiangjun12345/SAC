package net.easipay.cbp.dao;

import net.easipay.cbp.model.SacCusParameter;

public interface ISacCusParamDao
{
    public SacCusParameter querySacCusParameter(String cusNo, String orgCardId, String sacCurrency, String bussType);

    public void insertSacCusParameter(SacCusParameter sacCusParameter);

    public void updateSacCusParameter(SacCusParameter sacCusParameter);
}
