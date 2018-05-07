/**
 * 
 */
package net.easipay.cbp.dao.impl;

import java.util.List;
import java.util.Map;
import net.easipay.cbp.dao.ISacCusParameterDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.SacCusParameter;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 */

@Repository("sacCusParameter")
public class SacCusParameterDaoImpl extends GenericDaoiBatis<SacCusParameter, Long> implements ISacCusParameterDao {
  private static final Logger logger = Logger.getLogger(SacCusParameterDaoImpl.class);

  public SacCusParameterDaoImpl() {
    super(SacCusParameter.class);
  }

  public SacCusParameterDaoImpl(Class<SacCusParameter> persistentClass) {
    super(persistentClass);
  }

  @Override
  public SacCusParameter selectSacCusParameterById(SacCusParameter sacCusParameter) {
    return (SacCusParameter) getSqlMapClientTemplate().queryForObject("getSacCusParameter", sacCusParameter);
  }

  @Override
  public List<SacCusParameter> queryAllSacCusParameter(Map<String, Object> paramMap) {
    return getSqlMapClientTemplate().queryForList("listSacCusParameter", paramMap);
  }

}
