/**
 * 
 */
package net.easipay.cbp.dao;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.UcChannelParam;
/**
 * @author Administrator
 *
 */
public interface ChannelParamDao extends GenericDao<UcChannelParam,Long> {
	
	public void insertUcChannelParam(UcChannelParam ucChannelParam);
	
	public void updateUcChannelParam(UcChannelParam ucChannelParam);
	
}
