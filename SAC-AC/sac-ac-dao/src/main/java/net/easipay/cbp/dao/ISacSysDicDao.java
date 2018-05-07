package net.easipay.cbp.dao;

import java.util.List;

import net.easipay.cbp.model.SacSysDic;


public interface ISacSysDicDao
{
    public List<SacSysDic> getSacSysDic(String dicType);
}
