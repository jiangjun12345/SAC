/**
 * 
 */
package net.easipay.cbp.dao;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.SacChargeApply;

/**
 * @author Administrator
 *
 */
public interface ISacChargeApplyDao extends GenericDao<SacChargeApply,Long> {

	public List<SacChargeApply> selectApplyByParam(Map<String,Object> queryMap);

	public Integer selectApplyCountsByParam(Map<String, Object> queryMap);

	public List<SacChargeApply> selectApplyByPaging(Map<String, Object> queryMap);
	

	
}
