package net.easipay.cbp.service.impl;

import net.easipay.cbp.dao.SysParamDao;
import net.easipay.cbp.service.SysParamService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sysParamService")
public class SysParamServiceImpl implements SysParamService 
{

	@Autowired
	private SysParamDao sysParamDao;
	
	@Override
	public String getSysParamValue(String paramKey, String magicType)
	{
		return sysParamDao.getSysParamValue(paramKey, magicType);
	}


}
