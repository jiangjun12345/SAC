package net.easipay.cbp.dao;

import java.util.Map;
import java.util.List;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.SacRecFileParam;

/**
* @Description: 对账文件参数表dao层(用一句话描述该文件做什么)
* @author DELL (作者英文名称) 
* @date 2015-7-24 下午01:15:57
* @version V1.0  
* @jdk v1.6
* @tomcat v7.0
 */
public interface ISacRecFileParamDao extends GenericDao<SacRecFileParam,Long>  {

	@SuppressWarnings("rawtypes")
	public List<SacRecFileParam> listAllSacRecFileParam(Map param);
	
}
