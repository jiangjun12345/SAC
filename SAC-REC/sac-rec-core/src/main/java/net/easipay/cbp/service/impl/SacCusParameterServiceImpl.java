package net.easipay.cbp.service.impl;

import java.util.List;

import net.easipay.cbp.dao.SacCusParameterDao;
import net.easipay.cbp.model.SacCusParameter;
import net.easipay.cbp.service.SacCusParameterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service("sacCusParameterService")
public class SacCusParameterServiceImpl implements SacCusParameterService {

	@Autowired
	private SacCusParameterDao sacCusParameterDao;

	@Override
	public List<SacCusParameter> getSacCusParameterByParam(Map<String,Object> queryMap){
		return sacCusParameterDao.getSacCusParameterByParam(queryMap);
	}
	
}
