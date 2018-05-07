package net.easipay.cbp.dao;

import java.util.List;

import net.easipay.cbp.model.SacChannelParam;

public interface ISacChannelParamDao
{
    public List<SacChannelParam> listSacChannelParam(String chnCode, String chnType, String currencyType, String isValidFlag);

    public void insertSacChannelParam(SacChannelParam sacChannelParam);

    public void updateSacChannelParam(SacChannelParam sacChannelParam);
}
