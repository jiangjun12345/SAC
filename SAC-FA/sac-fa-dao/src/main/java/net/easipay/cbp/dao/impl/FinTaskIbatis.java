package net.easipay.cbp.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.FinTaskDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.dao.base.ibatis.iBatisDaoUtils;
import net.easipay.cbp.model.FinTask;

import org.springframework.stereotype.Repository;
import org.springframework.util.ClassUtils;

@Repository("finTaskIbatis")
public class FinTaskIbatis extends GenericDaoiBatis<FinTask, Long> implements FinTaskDao
{

    @SuppressWarnings({ "unchecked", "deprecation" })
    @Override
    public List<FinTask> getTaskList(int status, int maxSize)
    {
	Map<String, Object> params = new HashMap<String, Object>();
	params.put("status", status);
	params.put("maxSize", maxSize);
	return getSqlMapClientTemplate().queryForList(iBatisDaoUtils.getSelectListQuery(ClassUtils.getShortName(FinTask.class)), params);
    }

    @SuppressWarnings({ "unchecked", "deprecation" })
	@Override
    public List<FinTask> getFinTasks(String serno, String status)
    {
	Map<String, Object> param = new HashMap<String, Object>();
	param.put("serno", serno);
	param.put("status", status);
	return getSqlMapClientTemplate().queryForList("getFinTasksBySerno", param);
    }
}
