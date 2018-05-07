package net.easipay.cbp.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.easipay.cbp.cache.CacheUtil;
import net.easipay.cbp.dao.ISacCusParameterDao;
import net.easipay.cbp.model.SacCusParameter;
import net.easipay.cbp.service.ISacCusParameterService;
import net.easipay.cbp.util.ConstantParams;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sacCusParameterService")
public class SacCusParameterServiceImpl implements ISacCusParameterService {

	@Autowired
	private ISacCusParameterDao sacCusParameterDao;

	@Override
	public List<SacCusParameter> selectAllSacCusParameter(SacCusParameter sacCusParameter,int pageNo,int pageSize){
		return sacCusParameterDao.selectAllSacCusParameter(sacCusParameter,pageNo,pageSize);
	}
	
   @Override
	public Integer selectSacCusParameterTotal(SacCusParameter sacCusParameter){
		return sacCusParameterDao.selectSacCusParameterTotal(sacCusParameter);
	}
   
	@Override
	public SacCusParameter selectSacCusParameterById(SacCusParameter sacCusParameter) {
		return sacCusParameterDao.selectSacCusParameterById(sacCusParameter);
	}

	@Override
	public List<SacCusParameter> handleSacCusParameterList(List<SacCusParameter> sacCusParameterList){
		Map<String,Object> bussTypeMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_BUSS_TYPE);
		 List<SacCusParameter> list = new ArrayList<SacCusParameter>();
		if (sacCusParameterList != null && sacCusParameterList.size() > 0) {
			for (SacCusParameter sacCusParameter : sacCusParameterList) {
				Object bussType = bussTypeMap.get(sacCusParameter.getBussType());
				sacCusParameter.setBussType(bussType!=null?bussType+"("+sacCusParameter.getBussType()+")":"-");
				list.add(sacCusParameter);
			}
		}
		return list;
	}
}
