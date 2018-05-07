package net.easipay.cbp.dao;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.SacOflCommand;

public interface ISacOflCommandDao extends GenericDao<SacOflCommand,Long> {
	
	public List<SacOflCommand> selectSacOflCommandList(Map param);
	
	public void updateSacOflCommand(SacOflCommand sacOflCommand);
	
}
