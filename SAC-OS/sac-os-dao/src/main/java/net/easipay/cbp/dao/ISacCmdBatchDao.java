/**
 * 
 */
package net.easipay.cbp.dao;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.SacB2bCmdBatch;

/**
 * @author Administrator
 *
 */
public interface ISacCmdBatchDao extends GenericDao<SacB2bCmdBatch,Long> {

	public Integer getCmdBatchCounts(Map<String, Object> queryMap);

	public List<SacB2bCmdBatch> getCmdBatchByPaging(Map<String, Object> queryMap);

	public List<SacB2bCmdBatch> getSacB2bCmdBatch(Map<String, Object> queryMap);
	
}
