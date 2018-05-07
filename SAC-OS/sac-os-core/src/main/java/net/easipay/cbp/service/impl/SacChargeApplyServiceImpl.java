package net.easipay.cbp.service.impl;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.ISacChargeApplyDao;
import net.easipay.cbp.model.SacChargeApply;
import net.easipay.cbp.service.ISacChargeApplyService;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sacChargeApplyService")
public class SacChargeApplyServiceImpl implements ISacChargeApplyService
{
	
	@Autowired
	private ISacChargeApplyDao sacChargeApplyDao;
		
	public static final Logger logger = Logger.getLogger(SacChargeApplyServiceImpl.class);

	@Override
	public List<SacChargeApply> selectApplyByParam(Map<String,Object> queryMap) {
		return sacChargeApplyDao.selectApplyByParam(queryMap);
	}

	@Override
	public void updateChargeApply(SacChargeApply apply) {
		sacChargeApplyDao.update(apply);
		
	}

	@Override
	public Integer selectApplyCountsByParam(Map<String, Object> queryMap) {
		return sacChargeApplyDao.selectApplyCountsByParam(queryMap);
	}

	@Override
	public List<SacChargeApply> selectApplyByPaging(Map<String, Object> queryMap,int pageNo,int pageSize) {
		int start = (pageNo-1)*pageSize;
		int end = pageNo*pageSize;
		queryMap.put("start", start);
		queryMap.put("end", end);
		return sacChargeApplyDao.selectApplyByPaging(queryMap);
	}


	
}
