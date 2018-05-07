package net.easipay.cbp.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import net.easipay.cbp.model.SacRecDetail;
import net.easipay.cbp.model.SacRecDifferences;
import net.easipay.cbp.model.SacRecFileParam;
import net.easipay.cbp.model.SacReceiveBankRecon;

/**
 * 
 * @Description: 运营系统渠道对账业务层(用一句话描述该文件做什么)
 * @author DELL (作者英文名称)
 * @date 2015-7-14 下午02:16:41
 * @version V1.0
 * @jdk v1.6
 * @tomcat v7.0
 */
public interface IChannelCheckAccService
{

	/**
	 * 查询对账结果
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryCheckAccResult(Map<String, Object> paramMap);
	
	/**
	 * 查询对账结果总数
	 * 
	 * @param paramMap
	 * @return
	 */
	public int queryCheckAccResultCount(Map<String, Object> paramMap);
	
	/**
	 * 查询对账差错明细(分页)
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<SacRecDifferences> queryRecDiffDetail(Map<String, Object> paramMap);
	
	/**
	 * 查询对账差错明细不分页
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<SacRecDifferences> simpleQueryRecDiffDetail(Map<String, Object> paramMap);
	
	/**
	 * 查询对账差错明细总数
	 * 
	 * @param paramMap
	 * @return
	 */
	public Integer queryRecDiffDetailCount(Map<String, Object> paramMap);
	
	/**
	 * 根据条件查询对账批次是否存在
	 * @param param
	 * @return
	 */
	public boolean recBatchIsExist(String recType,String recDate,String chnCode,String payconType);
	
	/**
	 * 查询对账文件参数列表
	 * @return
	 */
	public List<SacRecFileParam> getRecFileParamList(Map<String,Object> parmaMap);
	
	/**
	 * 对差错明细中的值转义
	 * @param diffList
	 * @return
	 */
	public List<SacRecDifferences> convertDiffList(List<SacRecDifferences> diffList);
	
	/**
	 * 根据支付渠道号获得对应的银行节点号
	 * @param chnCode
	 * @return
	 */
	public String getBankNodeCode(String chnCode);
	
	/**
	 * 校验对账日期是否匹配
	 * @param bankReconList
	 * @param checkTrxDate
	 * @return
	 */
	public boolean recDateIsRight(List<SacReceiveBankRecon> bankReconList,String checkTrxDate);
	
	/**
	 * 解析上传的对账文件
	 * @param files
	 * @return
	 */
	public List<SacReceiveBankRecon> resolveRecFile(List<MultipartFile> files,String bankNodeCode,String fileName)throws Exception;
	
	/**
	 * 查询对账批次明细
	 * @param paramMap
	 * @return
	 */
	public List<SacRecDetail> queryRecBatchDetail(Map<String,Object> paramMap);
	
	/**
	 * 查询明细总数
	 * @param paramMap
	 * @return
	 */
	public int queryRecBatchDetailCount(Map<String,Object> paramMap);
	
	public List<SacRecDetail> convertDetailList(List<SacRecDetail> detailList);
}
