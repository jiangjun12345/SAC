/**
 * 
 */
package net.easipay.cbp.dao;

import java.util.List;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.SysDic;

/**
 * @author Administrator
 *
 */
public interface SysDicDao extends GenericDao<SysDic,Long> {
	
	public List<SysDic> selectSysDic(SysDic sysDic);

	public void insertSysDic(SysDic sysDic);
}
