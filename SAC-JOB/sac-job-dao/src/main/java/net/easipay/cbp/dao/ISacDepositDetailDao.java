package net.easipay.cbp.dao;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.SacDepositDetail;

/**
 * @author Administrator
 *
 */
public interface ISacDepositDetailDao extends GenericDao<SacDepositDetail,Long> {

	public List<SacDepositDetail> selectSacDepositDetailList(Map<String, Object> paramMap);

	public void updateSacDepositDetail(SacDepositDetail detail);

}
