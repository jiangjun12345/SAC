package net.easipay.cbp.dao.impl;

import java.util.List;
import java.util.Map;
import net.easipay.cbp.dao.ISacRefundBatchDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.SacRefundBatch;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@SuppressWarnings({"deprecation", "unchecked"})
@Repository
public class SacRefundBatchDaoImpl extends GenericDaoiBatis<SacRefundBatch, Long> implements ISacRefundBatchDao {

  public static final Logger logger = Logger.getLogger(SacRefundBatchDaoImpl.class);

  @Override
  public List<SacRefundBatch> b2bRefundBatchForPaging(Map<String, Object> paramMap) {
    return this.getSqlMapClientTemplate().queryForList("listSplitSacRefundBatch", paramMap);
  }

  @Override
  public int b2bRefundBatchCounts(Map<String, Object> paramMap) {
    // TODO Auto-generated method stub
    return (Integer) this.getSqlMapClientTemplate().queryForObject("getSacRefundBatchCount", paramMap);

  }

  @Override
  public List<SacRefundBatch> b2bRefundBatchInfo(Map<String, Object> paramMap) {
    // TODO Auto-generated method stub
    return this.getSqlMapClientTemplate().queryForList("listSacRefundBatch", paramMap);
  }

  @Override
  public void batchUpdateB2bRefundBatchInfo(Map<String, Object> paramMap) {
    getSqlMapClientTemplate().update("batchUpdateSacRefundBatch", paramMap);

  }

  @Override
  public SacRefundBatch b2bRefundBatchInfoForOnly(Map<String, Object> paramMap) {
    return (SacRefundBatch) this.getSqlMapClientTemplate().queryForObject("getSacRefundBatch", paramMap);
  }

  @Override
  public void b2bRefundBatchInfoForDelete(Map<String, Object> paramMap) {
    // TODO Auto-generated method stub
    this.getSqlMapClientTemplate().delete("deleteSacRefundBatch", paramMap);
  }
}
