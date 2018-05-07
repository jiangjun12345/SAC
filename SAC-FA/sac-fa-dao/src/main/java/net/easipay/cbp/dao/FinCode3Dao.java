package net.easipay.cbp.dao;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.FinCode3;

public interface FinCode3Dao extends GenericDao<FinCode3, String>{
	/**
	 * 获取所有的Code1数据
	 * 
	 * @param param
	 * @return
	 */
	public List<FinCode3> getFinCode3List(Map<String, String> param);
	
	public FinCode3 getFinCode3(String finCode3Id);
}
