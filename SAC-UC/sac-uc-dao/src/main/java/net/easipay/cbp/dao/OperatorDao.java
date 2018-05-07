/**
 * 
 */
package net.easipay.cbp.dao;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.UcOperator;
/**
 * @author Administrator
 *
 */
public interface OperatorDao extends GenericDao<UcOperator,Long> {
	
	public UcOperator selectUcOperatorById(Long id);

	public List<UcOperator> selectAllUcOperator(int pageNo);
	
	public List<UcOperator> selectUcOperatorByParameter(UcOperator ucOperator,int pageNo);
	
	public void insertUcOperator(UcOperator ucOperator);
	
	public void updateUcOperator(UcOperator ucOperator);
	
	public int selectUcOperatorCountsByParameter(UcOperator ucOperator);
	
	public UcOperator selectUcOperatorByEmailAndPass(String email,String password);
	
	public int selectUcOperatorCountsForValidate(UcOperator ucOperator);

	public List<Map<String,Object>> getSacCusParameterFromTemp();

	public int validateRepeatCus(Map<String, Object> cusParam);
}
