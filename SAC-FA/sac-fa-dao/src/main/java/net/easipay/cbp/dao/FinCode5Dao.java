package net.easipay.cbp.dao;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.FinCode5;

public interface FinCode5Dao extends GenericDao<FinCode5, String>{
	/**
	 * 获取所有的Code1数据
	 * 
	 * @param param
	 * @return
	 */
	public List<FinCode5> getFinCode5List(Map<String, String> param);
}
