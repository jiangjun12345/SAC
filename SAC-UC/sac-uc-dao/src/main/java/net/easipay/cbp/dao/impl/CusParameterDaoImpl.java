/**
 * 
 */
package net.easipay.cbp.dao.impl;

import net.easipay.cbp.dao.CusParameterDao;
import net.easipay.cbp.dao.base.GenericDaoiBatis;
import net.easipay.cbp.model.UcCusParameter;
import net.easipay.cbp.sequence.SequenceCreatorUtil;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 *
 */

@Repository("ucCusParameter")
public class CusParameterDaoImpl extends GenericDaoiBatis<UcCusParameter,Long> implements CusParameterDao{
	private static final Logger logger = Logger.getLogger(CusParameterDaoImpl.class);
	
	public CusParameterDaoImpl(){
		super(UcCusParameter.class);
	}
	
	public CusParameterDaoImpl(Class<UcCusParameter> persistentClass) {
		super(persistentClass);
	}
	public void insertUcCusParameter(UcCusParameter ucCusParameter){
			ucCusParameter.setId(SequenceCreatorUtil.getTableId());
		getSqlMapClientTemplate().insert("insertUcCusParameter",ucCusParameter);
	}
	
	public void updateUcCusParameter(UcCusParameter ucCusParameter){
		getSqlMapClientTemplate().update("updateUcCusParameter", ucCusParameter);
	}
   
}
