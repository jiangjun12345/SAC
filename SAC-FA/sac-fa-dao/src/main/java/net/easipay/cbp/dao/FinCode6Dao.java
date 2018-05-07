package net.easipay.cbp.dao;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.FinCode6;

public interface FinCode6Dao extends GenericDao<FinCode6, String>{
	/**
	 * 获取所有的Code1数据
	 * 
	 * @param param
	 * @return
	 */
	public List<FinCode6> getFinCode6List(Map<String, String> param);
}
