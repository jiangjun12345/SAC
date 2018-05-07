package net.easipay.cbp.dao;

import java.util.List;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.FinCode;

public interface FinCodeDao extends GenericDao<FinCode, String>{
	
	public FinCode getFinCode(String finCodeId);
	
	public List<FinCode> getLikeFinCodes(String finCodeId);
}
