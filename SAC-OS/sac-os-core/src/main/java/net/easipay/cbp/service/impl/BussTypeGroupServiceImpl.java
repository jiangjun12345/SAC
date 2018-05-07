package net.easipay.cbp.service.impl;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.IBussTypeGroupDao;
import net.easipay.cbp.model.BussTypeGroup;
import net.easipay.cbp.service.IBussTypeGroupService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BussTypeGroupServiceImpl implements IBussTypeGroupService
{

	public static final Logger logger = Logger.getLogger(BussTypeGroupServiceImpl.class);

	@Autowired
	IBussTypeGroupDao bussTypeGroupDao;

	@Override
	public List<BussTypeGroup> getBussTypeGroup(Map<String, Object> paramMap) {
		return bussTypeGroupDao.getBussTypeGroup(paramMap);
	}

	@Override
	public int getBussTypeGroupCount(Map<String, Object> paramMap) {
		return bussTypeGroupDao.getBussTypeGroupCount(paramMap);
	}
	
	
	
}
