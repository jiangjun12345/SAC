/**
 * 
 */
package net.easipay.cbp.dao.impl;

import net.easipay.cbp.dao.ChannelParamDao;
import net.easipay.cbp.dao.base.GenericDaoiBatis;
import net.easipay.cbp.model.UcChannelParam;
import net.easipay.cbp.sequence.SequenceCreatorUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 *
 */

@Repository("ucChannelParam")
public class ChannelParamDaoImpl extends GenericDaoiBatis<UcChannelParam,Long> implements ChannelParamDao{
	private static final Logger logger = Logger.getLogger(ChannelParamDaoImpl.class);
	
	public ChannelParamDaoImpl(){
		super(UcChannelParam.class);
	}
	
	public ChannelParamDaoImpl(Class<UcChannelParam> persistentClass) {
		super(persistentClass);
	}
	public void insertUcChannelParam(UcChannelParam ucChannelParam){
		ucChannelParam.setId(SequenceCreatorUtil.getTableId());
		getSqlMapClientTemplate().insert("insertUcChannelParam",ucChannelParam);
	}
   
	
	public void updateUcChannelParam(UcChannelParam ucChannelParam){
		getSqlMapClientTemplate().update("updateUcChannelParam",ucChannelParam);
	}
}
