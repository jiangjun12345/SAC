package net.easipay.cbp.dao;

import java.util.List;
import java.util.Map;
import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.SacOtrxInfo;

public interface ISacOtrxInfoDao extends GenericDao<SacOtrxInfo, Long> {

  //根据条件查询原交易信息
  public List<SacOtrxInfo> selectOtrxInfoList(Map<String, Object> paramMap);

  
  
}
