package net.easipay.cbp.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.SacB2BCommand;

public interface ISacOnlCommandDao extends GenericDao<SacB2BCommand,Long> {

	public List<Map<String,Object>> getSumAmountForBatch(
			Map<String, Object> queryMap);

	public void updateCommandForBatch(Map<String, Object> updateMap);
	
	
}
