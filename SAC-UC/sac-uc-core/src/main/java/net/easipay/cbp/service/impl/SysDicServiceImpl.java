package net.easipay.cbp.service.impl;

import java.util.List;

import net.easipay.cbp.dao.SysDicDao;
import net.easipay.cbp.model.SysDic;
import net.easipay.cbp.service.SysDicService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sysDicService")
public class SysDicServiceImpl implements SysDicService {

	@Autowired
	private SysDicDao sysDicDao;

	/*@Override
	public List<SysDic> selectSysDic(SysDic sysDic) {
		return sysDicDao.selectSysDic(sysDic);
	}*/
	@Override
	public void insertSysDic(SysDic sysDic){
		 sysDicDao.insertSysDic(sysDic);
	}
	
}
