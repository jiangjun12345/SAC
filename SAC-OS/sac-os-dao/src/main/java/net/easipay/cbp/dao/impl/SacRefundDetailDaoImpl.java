package net.easipay.cbp.dao.impl;

import java.util.List;
import java.util.Map;
import net.easipay.cbp.dao.ISacRefundDetailDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.SacRefundCommand;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@SuppressWarnings({"deprecation", "unchecked"})
@Repository
public class SacRefundDetailDaoImpl extends GenericDaoiBatis<SacRefundCommand, Long> implements ISacRefundDetailDao {

  public static final Logger logger = Logger.getLogger(SacRefundDetailDaoImpl.class);

  @Override
  public List<SacRefundCommand> b2bRefundTrxInfoForPaging(Map<String, Object> paramMap) {
    return this.getSqlMapClientTemplate().queryForList("listSplitSacRefundCommand", paramMap);
  }

  @Override
  public int b2bRefundTrxCounts(Map<String, Object> paramMap) {
    // TODO Auto-generated method stub
    return (Integer) this.getSqlMapClientTemplate().queryForObject("getSacRefundCommandCount", paramMap);

  }

  @Override
  public List<SacRefundCommand> b2bRefundTrxInfo(Map<String, Object> paramMap) {
    // TODO Auto-generated method stub
    return this.getSqlMapClientTemplate().queryForList("listSacRefundCommand", paramMap);
  }

  @Override
  public void batchUpdateB2bRefundTrxInfo(Map<String, Object> paramMap) {
    getSqlMapClientTemplate().update("batchUpdateSacRefundCommand", paramMap);

  }

  @Override
  public SacRefundCommand b2bRefundTrxInfoForOnly(Map<String, Object> paramMap) {
    return (SacRefundCommand) this.getSqlMapClientTemplate().queryForObject("getSacRefundCommand", paramMap);
  }
}
