package net.easipay.cbp.dao;

import java.util.List;
import java.util.Map;
import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.SacRefundBatch;

public interface ISacRefundBatchDao extends GenericDao<SacRefundBatch, Long> {

  // 根据条件分页查询B2B退款批次
  public List<SacRefundBatch> b2bRefundBatchForPaging(Map<String, Object> paramMap);

  // 根据条件查询B2B退款批次数量
  public int b2bRefundBatchCounts(Map<String, Object> paramMap);;

  // 根据条件查询B2B退款批次
  public List<SacRefundBatch> b2bRefundBatchInfo(Map<String, Object> paramMap);

  // 批量更新B2B退款交易指令
  public void batchUpdateB2bRefundBatchInfo(Map<String, Object> paramMap);

  // 根据条件分页查询B2B退款批次
  public SacRefundBatch b2bRefundBatchInfoForOnly(Map<String, Object> paramMap);

  //根据条件删除B2B退款批次
  public void b2bRefundBatchInfoForDelete(Map<String, Object> paramMap);
}
