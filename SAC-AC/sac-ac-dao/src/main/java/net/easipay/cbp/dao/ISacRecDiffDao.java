package net.easipay.cbp.dao;

import java.util.Date;
import java.util.List;

import net.easipay.cbp.model.SacRecDifferences;

public interface ISacRecDiffDao
{
    /**
     * @param payconType
     * @param recOper
     * @param recStartDate
     * @param recEndDate
     * @param status
     * @return
     */
    public List<SacRecDifferences> querySacRecDifferencesList(String payconType, String recOper, Date recStartDate, Date recEndDate, String status);

    /**
     * 
     * @param form
     * @return
     */
    public void updateSacRecDifferences(SacRecDifferences sacRecDifferences);

}
