package net.easipay.cbp.dao.impl;

import java.util.List;
import java.util.Map;
import net.easipay.cbp.dao.ISacOtrxInfoDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.SacOtrxInfo;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@SuppressWarnings({"deprecation", "unchecked"})
@Repository
public class SacOtrxInfoDaoImpl extends GenericDaoiBatis<SacOtrxInfo, Long> implements ISacOtrxInfoDao {

  public static final Logger logger = Logger.getLogger(SacOtrxInfoDaoImpl.class);

  @Override
  public List<SacOtrxInfo> queryTrxInfo(Map<String, Object> paramMap) {
    return this.getSqlMapClientTemplate().queryForList("newListSplitSacOtrxInfo", paramMap);
  }

  @Override
  public Map getTrxInfoAmountCount(Map<String, Object> paramMap) {
    return this.getSqlMapClientTemplate().queryForMap("getAmountCount", paramMap, "payCurrency", "payAmountSum");
  }

  @Override
  public List<SacOtrxInfo> simpleQueryTrxInfo(Map<String, Object> paramMap) {
    return this.getSqlMapClientTemplate().queryForList("newListSacOtrxInfo", paramMap);
  }

  @Override
  public int newGetTrxInfoCount(Map<String, Object> paramMap) {
    return (Integer) this.getSqlMapClientTemplate().queryForObject("newGetSacOtrxInfoCount", paramMap);
  }

  @Override
  public int getTrxInfoCount(Map<String, Object> paramMap) {
    return (Integer) this.getSqlMapClientTemplate().queryForObject("getSacOtrxInfoCount", paramMap);
  }

  @Override
  public List<SacOtrxInfo> queryPayAndGetInfo(Map<String, Object> paramMap) {
    return this.getSqlMapClientTemplate().queryForList("listSplitPayAndGetInfo", paramMap);
  }

  @Override
  public List<SacOtrxInfo> simpleQueryPayAndGetInfo(Map<String, Object> paramMap) {
    return this.getSqlMapClientTemplate().queryForList("listPayAndGetInfo", paramMap);
  }

  @Override
  public int getPayAndGetInfoCount(Map<String, Object> paramMap) {
    return (Integer) this.getSqlMapClientTemplate().queryForObject("getPayAndGetInfoCount", paramMap);
  }

  @Override
  public SacOtrxInfo selectSacOtrxInfoById(SacOtrxInfo sacOtrxInfo) {
    return (SacOtrxInfo) this.getSqlMapClientTemplate().queryForObject("getSacOtrxInfo", sacOtrxInfo);
  }

  @Override
  public List<SacOtrxInfo> selectOtrxInfo(Map<String, Object> paramMap) {
    return this.getSqlMapClientTemplate().queryForList("listSplitSacOtrxInfo", paramMap);
  }

  @Override
  public void updateTrxInfoBySerialNo(SacOtrxInfo sacOtrxInfo) {
    this.getSqlMapClientTemplate().update("updateSacOtrxInfoBySerialNo", sacOtrxInfo);

  }

  @Override
  public List<SacOtrxInfo> selectOtrxInfoByCondition(Map<String, Object> paramMap) {
    return this.getSqlMapClientTemplate().queryForList("listSacOtrxInfo", paramMap);
  }

}
