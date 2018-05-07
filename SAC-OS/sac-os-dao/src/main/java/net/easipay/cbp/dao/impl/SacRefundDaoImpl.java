/**
 * 
 */
package net.easipay.cbp.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import net.easipay.cbp.dao.ISacRefundDao;
import net.easipay.cbp.model.SacRefund;
import net.easipay.cbp.util.BeanUtils;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
/**
 * @author Administrator
 *
 */

@Repository("sacRefund")
public class SacRefundDaoImpl extends GenericDaoiBatis<SacRefund,Long> implements ISacRefundDao{
	private static final Logger logger = Logger.getLogger(SacRefundDaoImpl.class);
	
	public SacRefundDaoImpl(){
		super(SacRefund.class);
	}
	
	public SacRefundDaoImpl(Class<SacRefund> persistentClass) {
		super(persistentClass);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SacRefund> selectSacRefund(
			SacRefund sacRefund,int pageNo,int pageSize) {
		int start = (pageNo-1)*pageSize;
		int end = pageNo*pageSize;
		Map<String,Object> queryMap =null;
		queryMap = BeanUtils.transBean2Map(sacRefund);
		/*Date refundTime = sacRefund.getRefundTime();
		if(refundTime!=null){
			String refundTimeStr = DateUtil.getFomateDate(refundTime,"yyyy-MM-dd");
			queryMap.put("refundTime", refundTimeStr);
		}
		Date otrxDate = sacRefund.getOtrxDate();
		if(otrxDate!=null){
			String otrxDateStr = DateUtil.getFomateDate(otrxDate,"yyyy-MM-dd");
			queryMap.put("otrxDate", otrxDateStr);
		}*/
		queryMap.put("start", start);
		queryMap.put("end", end);
		return  getSqlMapClientTemplate().queryForList("listSplitSacRefund",queryMap);
	}

	@Override
	public int selectSacRefundCounts(SacRefund sacRefund) {
		Map<String,Object> queryMap =null;
		queryMap = BeanUtils.transBean2Map(sacRefund);
		/*Date refundTime = sacRefund.getRefundTime();
		if(refundTime!=null){
			String refundTimeStr = DateUtil.getFomateDate(refundTime,"yyyy-MM-dd");
			queryMap.put("refundTime", refundTimeStr);
		}
		Date otrxDate = sacRefund.getOtrxDate();
		if(otrxDate!=null){
			String otrxDateStr = DateUtil.getFomateDate(otrxDate,"yyyy-MM-dd");
			queryMap.put("otrxDate", otrxDateStr);
		}*/
		return (Integer)getSqlMapClientTemplate().queryForObject("getSacRefundCount", queryMap);
	}

	@Override
	public SacRefund selectSacRefundById(SacRefund sacRefund) {
		return (SacRefund)getSqlMapClientTemplate().queryForObject("getSacRefundById", sacRefund);
	}

	@Override
	public void updateSacRefund(SacRefund sacRefund) {
		getSqlMapClientTemplate().update("updateSacRefund", sacRefund);
		
	}


	
}
