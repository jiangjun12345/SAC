package net.easipay.cbp.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
* @Description: 查询余额服务
* @author DELL (作者英文名称) 
* @date 2015-12-11 下午05:32:48
* @version V1.0  
* @jdk v1.6
* @tomcat v7.0
 */
public interface ICusBalanceService
{

	//根据条件分页查询客户余额
	public List<Map<String, Object>> queryCusBalance(Map<String, Object> paramMap);

	//根据条件查询客户余额总数
	public int getCusBalanceCount(Map<String, Object> paramMap);
	
	//根据条件查询客户每日余额总数
	public int queryCusBalanceFundDayCount(Map<String, Object> paramMap);
	
	//根据条件查询客户每日余额总数
	public int queryCusBalanceFundDayCount2(Map<String, Object> paramMap);
	
	//根据条件不分页查询客户余额
	public List<Map<String, Object>> simpleQueryCusBalance(Map<String, Object> paramMap);
	
	//调用会计核算系统(FA)
	public Map<String, Object> queryFinMxList(Map<String, Object> paramMap);
	
	//处理账户余额信息
	public List<Map<String,Object>> handleCusBalanceList(List<Map<String,Object>> cusBalanceList);
	
	//处理账户余额信息
	public List<Map<String,Object>> handleCusBalanceList1(List<Map<String,Object>> cusBalanceList);
	
	//获取子账户类型
	public Map<String,Object> getChileAccTypeMap();
	
	//余额明细下载
	public String cusBalanceDetailDownloadContent(int i,Map<String, Object> paramMap);
	
	public Boolean validateCusAvalibleBal(String crtCode, BigDecimal amount,
			String batchCur);

	//根据条件分页查询客户每日余额（org表）
	public List<Map<String, Object>> queryCusBalanceFundDay(Map<String, Object> paramMap);
	
	//根据条件分页查询客户每日余额（mx表）
	public List<Map<String, Object>> queryCusBalanceFundDay2(Map<String, Object> paramMap);
	
	//根据cusname查询所有的商户名称
	public List<String> selectlistSacCusParameterByCusName(Map<String, Object> paramMap);
	
	//根据cusname cusno，币种，业务类型，子业务类型查询codeids
	public List<String> getCodeIdsByCusparamMap(Map<String, Object> paramMap);
	
}
