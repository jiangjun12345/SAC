/**
 * 
 */
package net.easipay.cbp.dao;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.FinStatBank;
import net.easipay.cbp.model.FinStatBankForm;
import net.easipay.cbp.model.SacOtrxInfo;

/**
 * @author Administrator
 *
 */
public interface IFinStatBankDao extends GenericDao<FinStatBank,Long> {
	
	public List<FinStatBankForm> selectFinStatBankForSplit(Map<String,Object> finStatBankMap,int pageNo,int pageSize);
	
	public int selectFinStatBankCounts(Map<String,Object> finStatBankMap);

}
