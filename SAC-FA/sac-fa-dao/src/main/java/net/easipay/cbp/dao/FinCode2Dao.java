package net.easipay.cbp.dao;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.FinCode2;

public interface FinCode2Dao extends GenericDao<FinCode2, String>{
	/**
	 * 获取所有的Code2数据
	 * 
	 * @param param
	 * @return
	 */
	public List<FinCode2> getFinCode2List(Map<String, String> param);
}
