package net.easipay.cbp.service.impl;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.IFinTrialBalancingDao;
import net.easipay.cbp.model.FinTrialBalancing;
import net.easipay.cbp.service.IFinTrialBalancingService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sacTrialBalancingService")
public class FinTrialBalancingServiceImpl implements IFinTrialBalancingService {

	private static final Logger logger = Logger.getLogger(FinTrialBalancingServiceImpl.class);
	
	@Autowired
	private IFinTrialBalancingDao finTrialBalancingDao;
	

	@Override
	public List<FinTrialBalancing> getFinTrialBalancingBySplit(Map<String,Object> queryMap,int pageNo,int pageSize){
		return finTrialBalancingDao.getFinTrialBalancingBySplit(queryMap,pageNo,pageSize);
	}

	@Override
	public int getFinTrialBalancingCounts(Map<String,Object> queryMap){
		return finTrialBalancingDao.getFinTrialBalancingCounts(queryMap);
	}
}
