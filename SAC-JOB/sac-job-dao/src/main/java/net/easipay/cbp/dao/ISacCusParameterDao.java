/**
 * 
 */
package net.easipay.cbp.dao;

import java.util.List;
import java.util.Map;
import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.SacCusParameter;

/**
 * @author Administrator
 */
public interface ISacCusParameterDao extends GenericDao<SacCusParameter, Long> {

  public SacCusParameter selectSacCusParameterById(SacCusParameter sacCusParameter);

  public List<SacCusParameter> queryAllSacCusParameter(Map<String, Object> paramMap);

}
