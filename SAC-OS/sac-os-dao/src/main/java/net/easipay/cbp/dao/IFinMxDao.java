package net.easipay.cbp.dao;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.FinMx;


public interface IFinMxDao extends GenericDao<FinMx,Long> {

	public FinMx getFinMx(Map param);
	public List<FinMx> listFinMx(Map param);
    public int getFinMxCount(Map param);
    public List<FinMx> listSplitFinMx(Map param);
    
    
    public List<FinMx> listSplitFinMxCollect(Map param);
    public int getFinMxCollectCount(Map param);
}
