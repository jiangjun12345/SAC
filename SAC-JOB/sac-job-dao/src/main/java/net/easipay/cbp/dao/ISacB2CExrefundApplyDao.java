package net.easipay.cbp.dao;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.SacB2CExrefundApply;

public interface ISacB2CExrefundApplyDao extends GenericDao<SacB2CExrefundApply, Long> {

	@SuppressWarnings("rawtypes")
	public List<SacB2CExrefundApply> selectSacB2CExrefundApplyList(Map paramMap);
	
	public void insertB2CExrefundApply(SacB2CExrefundApply b2cExrefundApply);
}
