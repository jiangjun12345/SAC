package net.easipay.cbp.service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import net.easipay.cbp.cas.users.SecurityOperator;
import net.easipay.cbp.model.SacB2cExrefundApply;

/**
 * @Description: 客户交易服务层接口(用一句话描述该文件做什么)
 * @author DELL (作者英文名称)
 * @date 2015-7-8 上午09:39:43
 * @version V1.0
 * @jdk v1.6
 * @tomcat v7.0
 */
public interface IRefundB2cService {

  // 根据条件分页查询B2c退款交易
  public List<SacB2cExrefundApply> b2cRefundTrxInfoForPaging(Map<String, Object> paramMap);

  // 根据条件分页查询B2c退款交易
  public int b2cRefundTrxCounts(Map<String, Object> paramMap);

  //导出B2c退款Excel
  public ByteArrayOutputStream exportExcel(List<SacB2cExrefundApply> applyList) throws Exception;

  public List<SacB2cExrefundApply> handleSacRefundApplyList(List<SacB2cExrefundApply> sacRefundApplyList);

  //根据条件查询B2c退款交易指令
  public List<SacB2cExrefundApply> b2cRefundTrxInfo(Map<String, Object> paramMap);

  //处理B2c退款经办
  public void b2cRefundOperate(Long[] ids, SecurityOperator person) throws Exception;

  // 根据条件查询B2c退款交易指令唯一记录
  public SacB2cExrefundApply b2cRefundTrxInfoForOnly(Map<String, Object> paramMap);

  //复核B2c退款通过
  public void b2cRefundCheckPass(Long[] ids, SecurityOperator person) throws Exception;

  //  //复核退款批次不通过
  //  public void reviewB2BRefundBatchReject(String[] batchIdNos, SecurityOperator person);
}
