package net.easipay.cbp.dao;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.SacB2BCommand;

public interface ISacB2BCommandDao extends GenericDao<SacB2BCommand, Long> {

	@SuppressWarnings("rawtypes")
	public List<SacB2BCommand> selectB2BCommandList(Map paramMap);
	
	public void updateB2BCommand(SacB2BCommand b2bCommand);
  
}
