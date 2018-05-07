package net.easipay.cbp.dao.impl;

import java.util.List;
import java.util.Map;
import net.easipay.cbp.dao.ISacB2cexRefundApplyDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.SacB2cExrefundApply;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@SuppressWarnings({"deprecation", "unchecked"})
@Repository
public class SacB2cexRefundApplyDaoImpl extends GenericDaoiBatis<SacB2cExrefundApply, Long> implements ISacB2cexRefundApplyDao {

  public static final Logger logger = Logger.getLogger(SacB2cexRefundApplyDaoImpl.class);

  @Override
  public List<SacB2cExrefundApply> b2cRefundTrxInfoForPaging(Map<String, Object> paramMap) {
    return this.getSqlMapClientTemplate().queryForList("listSplitSacB2cExrefundApply", paramMap);
  }

  @Override
  public int b2cRefundTrxCounts(Map<String, Object> paramMap) {
    // TODO Auto-generated method stub
    return (Integer) this.getSqlMapClientTemplate().queryForObject("getSacB2cExrefundApplyCount", paramMap);

  }

  @Override
  public List<SacB2cExrefundApply> b2cRefundTrxInfo(Map<String, Object> paramMap) {
    // TODO Auto-generated method stub
    return this.getSqlMapClientTemplate().queryForList("listSacB2cExrefundApply", paramMap);
  }

  @Override
  public void batchUpdateB2CRefundTrxInfo(Map<String, Object> paramMap) {
    getSqlMapClientTemplate().update("batchUpdateSacB2cExrefundApply", paramMap);

  }

  @Override
  public SacB2cExrefundApply b2cRefundTrxInfoForOnly(Map<String, Object> paramMap) {
    return (SacB2cExrefundApply) this.getSqlMapClientTemplate().queryForObject("getSacB2cExrefundApply", paramMap);
  }
}
