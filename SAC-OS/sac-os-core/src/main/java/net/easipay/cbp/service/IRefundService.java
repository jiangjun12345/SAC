package net.easipay.cbp.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import net.easipay.cbp.cas.users.SecurityOperator;
import net.easipay.cbp.model.SacRefundBatch;
import net.easipay.cbp.model.SacRefundCommand;

/**
 * @Description: 客户交易服务层接口(用一句话描述该文件做什么)
 * @author DELL (作者英文名称)
 * @date 2015-7-8 上午09:39:43
 * @version V1.0
 * @jdk v1.6
 * @tomcat v7.0
 */
public interface IRefundService {

  // 根据条件分页查询B2B退款交易
  public List<SacRefundCommand> b2bRefundTrxInfoForPaging(Map<String, Object> paramMap);

  // 根据条件分页查询B2B退款交易
  public int b2bRefundTrxCounts(Map<String, Object> paramMap);

  //导出B2B退款Excel
  ByteArrayOutputStream exportExcel(String[] wpIds, String tplName);

  //读取B2B退款
  public SacRefundBatch readOflXls(String oflExcelFileName, InputStream inputStream, SecurityOperator person) throws Exception;

  // 根据条件查询B2B退款批次
  public List<SacRefundBatch> b2bRefundBatchInfoForPaging(Map<String, Object> paramMap);

  // 根据条件查询B2B退款批次数量
  public int b2bRefundBatchCounts(Map<String, Object> paramMap);

  public List<SacRefundBatch> handleSacRefundBatchList(List<SacRefundBatch> sacRefundBacthList);

  // 根据条件查询B2B退款批次
  public SacRefundBatch b2bRefundBatchInfoForOnly(Map<String, Object> paramMap);

  //复核退款批次通过
  public void reviewB2BRefundBatchPass(String[] batchIdNos, SecurityOperator person);

  //复核退款批次不通过
  public void reviewB2BRefundBatchReject(String[] batchIdNos, SecurityOperator person);
}
