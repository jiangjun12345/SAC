/**
 * 
 */
package net.easipay.cbp.service;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.model.UcOperator;


/**
 * @author Administrator
 *
 */
public interface OperatorService {

	public UcOperator selectUcOperatorById(Long id);
	
	/**
	 * 查询所有操作员
	 * @return
	 */
	public List<UcOperator> selectAllUcOperator(int pageNo);
	
	/**
	 * 根据参数查询操作员(分页)
	 * @return
	 */
	public List<UcOperator> selectUcOperatorByParameter(UcOperator ucOperator,int pageNo);

	public void insertUcOperator(UcOperator ucOperator);
	
	public void updateUcOperator(UcOperator ucOperator);
	
	/**
	 * 根据参数查询所有操作员数量
	 * @return
	 */
	public int selectUcOperatorCounts(UcOperator ucOperator);
	
	public UcOperator selectUcOperatorByEmailAndPass(String email,String password);
	
	/**
	 * 根据登录名查询操作员信息 
	 */
	public int selectUcOperatorCountsForValidate(UcOperator ucOperator);

	public List<Map<String,Object>> getSacCusParameterFromTemp();

	public int validateRepeatCus(Map<String, Object> cusParam);
	
	
}
