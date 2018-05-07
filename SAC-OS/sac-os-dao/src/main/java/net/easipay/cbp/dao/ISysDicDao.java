/**
 * 
 */
package net.easipay.cbp.dao;

import java.util.List;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.SacSysDic;

/**
 * @author Administrator
 *
 */
public interface ISysDicDao extends GenericDao<SacSysDic,Long> {
	
	public List<SacSysDic> selectSysDic(SacSysDic sysDic);

	public List<SacSysDic> selectChildAccType();
	
	public List<SacSysDic> selectCode1();
	
	public List<SacSysDic> selectCode2();
}
