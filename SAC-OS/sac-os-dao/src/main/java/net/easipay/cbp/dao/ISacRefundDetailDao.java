package net.easipay.cbp.dao;

import java.util.List;
import java.util.Map;
import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.SacRefundCommand;

public interface ISacRefundDetailDao extends GenericDao<SacRefundCommand, Long> {

  // 根据条件分页查询B2B退款交易指令
  public List<SacRefundCommand> b2bRefundTrxInfoForPaging(Map<String, Object> paramMap);

  // 根据条件查询B2B退款交易指令
  public int b2bRefundTrxCounts(Map<String, Object> paramMap);;

  // 根据条件查询B2B退款交易指令
  public List<SacRefundCommand> b2bRefundTrxInfo(Map<String, Object> paramMap);

  // 批量更新B2B退款交易指令
  public void batchUpdateB2bRefundTrxInfo(Map<String, Object> paramMap);

  // 根据条件分页查询B2B退款交易指令
  public SacRefundCommand b2bRefundTrxInfoForOnly(Map<String, Object> paramMap);
}
