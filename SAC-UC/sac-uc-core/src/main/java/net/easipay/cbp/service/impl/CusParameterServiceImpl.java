package net.easipay.cbp.service.impl;

import net.easipay.cbp.dao.CusParameterDao;
import net.easipay.cbp.model.UcCusParameter;
import net.easipay.cbp.service.CusParameterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("CusParameterService")
public class CusParameterServiceImpl implements CusParameterService {

	@Autowired
	private CusParameterDao cusParameterDao;

	  public void insertUcCusParameter(UcCusParameter ucCusParameter){
		  cusParameterDao.insertUcCusParameter(ucCusParameter);
	  }
		
	public void updateUcCusParameter(UcCusParameter ucCusParameter){
		cusParameterDao.updateUcCusParameter(ucCusParameter);
	}

	
}
