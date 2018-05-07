/**
 * 
 */
package net.easipay.cbp.dao.impl;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.IFinStatBankDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.FinStatBank;
import net.easipay.cbp.model.FinStatBankForm;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
/**
 * @author Administrator
 *
 */

@Repository("finStatBank")
public class FinStatBankDaoImpl extends GenericDaoiBatis<FinStatBank,Long> implements IFinStatBankDao{
	private static final Logger logger = Logger.getLogger(FinStatBankDaoImpl.class);
	
	public FinStatBankDaoImpl(){
		super(FinStatBank.class);
	}
	
	public FinStatBankDaoImpl(Class<FinStatBank> persistentClass) {
		super(persistentClass);
	}

	@Override
	public List<FinStatBankForm> selectFinStatBankForSplit(Map<String,Object> finStatBankMap,
			int pageNo, int pageSize) {
		
		finStatBankMap.put("end", pageNo*pageSize);
		finStatBankMap.put("start", ((pageNo-1)*pageSize));
		return getSqlMapClientTemplate().queryForList("listSplitFinStatBank",finStatBankMap);
	}

	@Override
	public int selectFinStatBankCounts(Map<String,Object> finStatBankMap) {
		return (Integer)getSqlMapClientTemplate().queryForObject("getFinStatBankCount", finStatBankMap);
	}

}
