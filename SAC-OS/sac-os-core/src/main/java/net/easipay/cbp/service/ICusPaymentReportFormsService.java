package net.easipay.cbp.service;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.model.SacCancelVerify;
import net.easipay.cbp.model.SacCusPayment;
import net.easipay.cbp.model.SacCusSettlement;

public interface ICusPaymentReportFormsService
{

	/**
	 * 商户结算应付款查询分页
	 * @param paramMap
	 * @return
	 */
	public List<SacCusSettlement> queryCusPayment(Map<String,Object> paramMap);
	
	/**
	 * 商户结算应付款查询不分页
	 * @param paramMap
	 * @return
	 */
	public List<SacCusSettlement> simpleQueryCusPayment(Map<String,Object> paramMap);
	
	/**
	 * 商户结算应付款总数查询
	 * @param paramMap
	 * @return
	 */
	public int queryCusPaymentCount(Map<String,Object> paramMap);
	
	/**
	 * 商户结算应付款金额统计
	 * @param paramMap
	 * @return
	 */
	public List<Map<String,Object>> countCusSettlementAmount(Map<String, Object> paramMap);
	
	/**
	 * 商户结算应付款下载内容
	 * @param i
	 * @param paramMap
	 * @return
	 */
	public String cusPaymentDownloadContent(int i,Map<String,Object> paramMap);
	
	/**
	 * 商户结算应付款明细查询分页
	 * @param paramMap
	 * @return
	 */
	public List<SacCusPayment> queryCusPaymentDetail(Map<String,Object> paramMap);
	
	/**
	 * 商户结算应付款明细查询不分页
	 * @param paramMap
	 * @return
	 */
	public List<SacCusPayment> simpleQueryCusPaymentDetail(Map<String,Object> paramMap);
	
	/**
	 * 商户结算应付款明细总数查询
	 * @param paramMap
	 * @return
	 */
	public int queryCusPaymentDetailCount(Map<String,Object> paramMap);
	
	/**
	 * 商户结算应付款金额统计
	 * @param paramMap
	 * @return
	 */
	public List<Map<String,Object>> countCusPaymentAmount(Map<String, Object> paramMap);
	
	/**
	 * 商户结算应付款明细下载内容
	 * @param i
	 * @param paramMap
	 * @return
	 */
	public String cusPaymentDetailDownloadContent(int i,Map<String,Object> paramMap);
	
	/**
	 * 对商户应付查询结果枚举值处理
	 * @param otrxInfoList
	 */
	public List<SacCusSettlement> handleSacCusSettlementList(List<SacCusSettlement> sacCusSettlementList);
	
	/**
	 * 对商户应付明细查询结果枚举值处理
	 * @param otrxInfoList
	 */
	public List<SacCusPayment> handleSacCusPaymentList(List<SacCusPayment> cusPaymentDetailList);
	
	
	/**
	 * 核销信息查询
	 * @param suspendedAccinfoQuery
	 */
	public List<SacCancelVerify> getSuspendedAccinfoList(Map<String,Object> queryMap);
	
	public String suspendedAccInfoDownload(int i, Map<String, Object> paramMap);

	public int getSuspendedAccinfoListCount(Map<String, Object> paramMap);
	
	
}
