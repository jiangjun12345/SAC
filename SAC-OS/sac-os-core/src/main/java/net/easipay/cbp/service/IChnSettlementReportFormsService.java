package net.easipay.cbp.service;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.model.SacChannelParam;
import net.easipay.cbp.model.SacChnSettlement;

public interface IChnSettlementReportFormsService
{

	/**
	 * 查询所有渠道参数
	 * @return
	 */
	public List<SacChannelParam> queryAllChannel();
	
	/**
	 * 渠道应收款报表查询分页
	 * @param paramMap
	 * @return
	 */
	public List<SacChnSettlement> queryChnSettlement(Map<String,Object> paramMap);
	
	/**
	 * 渠道应收款报表查询不分页
	 * @param paramMap
	 * @return
	 */
	public List<SacChnSettlement> simpleQueryChnSettlement(Map<String,Object> paramMap);
	
	/**
	 * 渠道应收款报表总数查询
	 * @param paramMap
	 * @return
	 */
	public int querySacChnSettlementCount(Map<String,Object> paramMap);
	
	/**
	 * 对渠道应收查询结果枚举值处理
	 */
	public List<SacChnSettlement> handleSacChnSettlement(List<SacChnSettlement> chnSettlementList);
	
	//渠道应收报表下载内容
	public String chnSettlementDownloadContent(int i,Map<String,Object> paramMap);
	
	/**
	 * 统计金额汇总
	 * @param paramMap
	 * @return
	 */
	public List<Map<String,Object>> countChnSettlementAmount(Map<String,Object> paramMap);
	
}
