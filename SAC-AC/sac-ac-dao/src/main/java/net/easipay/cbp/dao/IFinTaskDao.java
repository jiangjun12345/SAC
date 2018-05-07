package net.easipay.cbp.dao;

import java.util.List;

import net.easipay.cbp.model.FinTask;


public interface IFinTaskDao
{
    public void insertFinTasks(List<FinTask> listFinTask);
    
    public List<FinTask> getFinTasks(String serno, String status);
}
