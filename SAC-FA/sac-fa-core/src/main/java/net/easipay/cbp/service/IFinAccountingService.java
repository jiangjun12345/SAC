package net.easipay.cbp.service;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.form.FinTaskQueryForm;
import net.easipay.cbp.form.FinTaskReceiveForm;
import net.easipay.cbp.form.PzParamsForm;
import net.easipay.cbp.model.FinPz;
import net.easipay.cbp.model.FinTask;
import net.easipay.cbp.model.FinYe;

/**
 * 1.初始化fincode 新用户 保存fincode3 fincode finYeDao
 * 
 * 2.保存凭证
 * 
 * 3.更新余额，添加余额明细
 * 
 * 
 * @author mchen
 * @date 2015-12-14
 */

public interface IFinAccountingService
{
    public List<FinTask> getUndoTaskList(int status, int maxSize);

    public void updateUndoTask(long taskId, int status, String errDesc);

    public void autoChargeAccount(FinTask finTask) throws Exception;

    public void chargeAccount(List<FinTask> tasks);

    public void batchChargeAccountActualTime(List<FinTaskReceiveForm> forms) throws Exception;
    
    public void chargeAccountASync(List<FinTaskReceiveForm> forms) throws Exception;

    public void createFinCodeAndUserBalanceByCodeId(PzParamsForm form);

    public void createUserBalanceByAccountId(String accountId);

    public FinPz createFinPz(String pzNo, PzParamsForm pzForm, FinTask finTask);

    public void updateFinYeAndCreateFinYeMx(List<FinPz> listFinPz, List<FinYe> finYes, Map<String, Boolean> mapShowFinYeMx);
    
    public List<FinTask> getFinTaskByParam(FinTaskQueryForm form);
    

}
