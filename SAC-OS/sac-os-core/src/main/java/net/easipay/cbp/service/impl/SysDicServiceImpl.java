package net.easipay.cbp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.ISysDicDao;
import net.easipay.cbp.model.SacSysDic;
import net.easipay.cbp.service.ISysDicService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sysDicService")
public class SysDicServiceImpl implements ISysDicService {

	@Autowired
	private ISysDicDao sysDicDao;

	@Override
	public List<SacSysDic> selectSysDic(SacSysDic sysDic) {
		return sysDicDao.selectSysDic(sysDic);
	}
	
	@Override
	public Map<String,Object> getChileAccTypeMap(){
		Map<String,Object> childAccTypeMap = new HashMap<String, Object>();
		List<SacSysDic> sysDicList = sysDicDao.selectChildAccType();
		for(SacSysDic sysDic:sysDicList){
			childAccTypeMap.put(sysDic.getDicCode(), sysDic.getDicDesc());
		}
		childAccTypeMap.remove("00");
		return childAccTypeMap;
	}
	
	@Override
	public Map<String,Object> getCode1Map(){
		Map<String,Object> childAccTypeMap = new HashMap<String, Object>();
		List<SacSysDic> sysDicList = sysDicDao.selectCode1();
		for(SacSysDic sysDic:sysDicList){
			childAccTypeMap.put(sysDic.getDicCode(), sysDic.getDicDesc());
		}
		return childAccTypeMap;
	}
	
	@Override
	public Map<String,Object> getCode2Map(){
		Map<String,Object> childAccTypeMap = new HashMap<String, Object>();
		List<SacSysDic> sysDicList = sysDicDao.selectCode2();
		for(SacSysDic sysDic:sysDicList){
			childAccTypeMap.put(sysDic.getDicCode(), sysDic.getDicDesc());
		}
		return childAccTypeMap;
	}
	
}
