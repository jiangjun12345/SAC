package net.easipay.cbp.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import net.easipay.cbp.model.SacOtrxInfo;

/**
 * @Description: 客户交易服务层接口(用一句话描述该文件做什么)
 * @author DELL (作者英文名称)
 * @date 2015-7-8 上午09:39:43
 * @version V1.0
 * @jdk v1.6
 * @tomcat v7.0
 */
public interface ICustomerTradeService
{
	
	//根据条件分页查询原交易明细
	public List<SacOtrxInfo> queryTrxInfo(Map<String, Object> paramMap);

	//根据条件查询原交易明细
	public List<SacOtrxInfo> simpleQueryTrxInfo(Map<String, Object> paramMap);

	//根据条件查询原交易明细总数
	public int getTrxInfoCount(Map<String, Object> paramMap);

	//对原交易中的币种汇总统计
	public Map<String, BigDecimal> trxCurrencyCount(List<SacOtrxInfo> trxInfoList);
	
	//对原始交易中金额统计
	public Map<String, Object> getTrxInfoAmountCount(Map<String, Object> paramMap);
	
	//交易明细下载内容
	public String trxDetailDownloadContent(int i,Map<String,Object> paramMap);
	
	//根据条件分页查询购付汇明细
	public List<SacOtrxInfo> queryPayAndGetInfo(Map<String, Object> paramMap);

	//根据条件查询购付汇明细
	public List<SacOtrxInfo> simpleQueryPayAndGetInfo(Map<String, Object> paramMap);

	//根据条件查询购付汇明细总数
	public int getPayAndGetInfoCount(Map<String, Object> paramMap);
	
	//处理交易明细
	public List<SacOtrxInfo> handleSacOtrxInfoList(List<SacOtrxInfo> otrxInfoList);
	
	//购付汇内容下载
	public String payAndGetInfoDownloadContent(int i,Map<String,Object> paramMap);
	
	//获取商户列表
	public Map<String,Object> getCusParamMap();
	
}
