package net.easipay.cbp.dao;

import java.util.List;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.FinTask;

public interface FinTaskDao extends GenericDao<FinTask, Long>
{
    public List<FinTask> getTaskList(int status, int maxSize);
    
    public List<FinTask> getFinTasks(String serno, String status);
}
