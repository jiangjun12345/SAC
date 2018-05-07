package net.easipay.cbp.dao;

import java.util.List;
import java.util.Map;
import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.SacCheckInfo;

public interface ISacCheckInfoDao extends GenericDao<SacCheckInfo, Long> {

  /**
   * 根据条件分页查询原交易信息
   */
  public List<SacCheckInfo> querySacCheckInfo(Map<String, Object> paramMap);

  /**
   * 根据条件查询原交易信息总数
   */
  public int getCheckInfoCount(Map<String, Object> paramMap);
  /**
   * 根据ID查询交易数据
   */
  public SacCheckInfo selectSacCheckInfoById(Long id);

  public void insertSacCheckInfo(SacCheckInfo sacCheckInfo);
  public void updateSacCheckInfo(SacCheckInfo sacCheckInfo);

  
}
