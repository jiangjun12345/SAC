package net.easipay.cbp.dao;

import java.util.List;
import java.util.Map;
import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.SacOtrxInfo;

public interface ISacOtrxInfoDao extends GenericDao<SacOtrxInfo, Long> {

  /**
   * 根据条件分页查询原交易信息
   */
  public List<SacOtrxInfo> queryTrxInfo(Map<String, Object> paramMap);

  /**
   * 查询金额汇总
   */
  public Map<String, Object> getTrxInfoAmountCount(Map<String, Object> paramMap);

  /**
   * 根据条件查询原交易信息
   */
  public List<SacOtrxInfo> simpleQueryTrxInfo(Map<String, Object> paramMap);

  /**
   * 根据条件查询原交易信息总数
   */
  public int getTrxInfoCount(Map<String, Object> paramMap);

  /**
   * 根据条件查询原交易信息总数(交易明细查询)
   */
  public int newGetTrxInfoCount(Map<String, Object> paramMap);

  /**
   * 根据条件查询购付汇明细总数
   */
  public int getPayAndGetInfoCount(Map<String, Object> paramMap);

  /**
   * 根据条件分页查询购付汇明细
   */
  public List<SacOtrxInfo> queryPayAndGetInfo(Map<String, Object> paramMap);

  /**
   * 根据条件查询购付汇明细
   */
  public List<SacOtrxInfo> simpleQueryPayAndGetInfo(Map<String, Object> paramMap);

  /**
   * 根据ID查询交易数据
   */
  public SacOtrxInfo selectSacOtrxInfoById(SacOtrxInfo sacOtrxInfo);

  public List<SacOtrxInfo> selectOtrxInfo(Map<String, Object> paramMap);

  public void updateTrxInfoBySerialNo(SacOtrxInfo sacOtrxInfo);

  /**
   * 根据条件查询交易数据
   */
  public List<SacOtrxInfo> selectOtrxInfoByCondition(Map<String, Object> paramMap);
}
