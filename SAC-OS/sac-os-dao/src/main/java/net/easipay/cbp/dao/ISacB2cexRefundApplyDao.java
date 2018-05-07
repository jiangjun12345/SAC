package net.easipay.cbp.dao;

import java.util.List;
import java.util.Map;
import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.SacB2cExrefundApply;

public interface ISacB2cexRefundApplyDao extends GenericDao<SacB2cExrefundApply, Long> {

  // 根据条件分页查询B2C退款交易指令
  public List<SacB2cExrefundApply> b2cRefundTrxInfoForPaging(Map<String, Object> paramMap);

  // 根据条件查询B2c退款交易指令
  public int b2cRefundTrxCounts(Map<String, Object> paramMap);;

  // 根据条件查询B2c退款交易指令
  public List<SacB2cExrefundApply> b2cRefundTrxInfo(Map<String, Object> paramMap);

  // 批量更新B2c退款交易指令
  public void batchUpdateB2CRefundTrxInfo(Map<String, Object> paramMap);

  // 根据条件查询B2c退款交易指令唯一记录
  public SacB2cExrefundApply b2cRefundTrxInfoForOnly(Map<String, Object> paramMap);
}
