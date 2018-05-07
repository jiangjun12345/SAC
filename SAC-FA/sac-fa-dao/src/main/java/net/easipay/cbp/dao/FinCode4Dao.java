package net.easipay.cbp.dao;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.FinCode4;

public interface FinCode4Dao extends GenericDao<FinCode4, String>{
	/**
	 * 获取所有的Code1数据
	 * 
	 * @param param
	 * @return
	 */
	public List<FinCode4> getFinCode4List(Map<String, String> param);
}
