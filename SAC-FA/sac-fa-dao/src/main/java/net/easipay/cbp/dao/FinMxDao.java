package net.easipay.cbp.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.FinMx;

public interface FinMxDao extends GenericDao<FinMx, Long> {

	public List<FinMx> getFinMxList(Map<String, String> param);
	
	public FinMx getFinMxFirst(Map<String, String> param);
	
	public FinMx getFinMxEnd(Map<String, String> param);
	
	public BigDecimal getFinMxSumFdebit(Map<String, String> param);
	
	public BigDecimal getFinMxSumFcredit(Map<String, String> param);

}
