package net.easipay.cbp.dao;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.SacRefundCommand;

public interface ISacRefundCommandDao extends GenericDao<SacRefundCommand, Long> {

	
	@SuppressWarnings("rawtypes")
	public List<SacRefundCommand> selectSacRefundCommandList(Map paramMap);
	
	public void insertRefundCommand(SacRefundCommand refundCommand);
	
	public void updateSacRefundCommand(SacRefundCommand refundCommand);
  
}
