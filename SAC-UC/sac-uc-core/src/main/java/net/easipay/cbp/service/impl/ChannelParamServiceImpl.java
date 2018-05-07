package net.easipay.cbp.service.impl;

import net.easipay.cbp.dao.ChannelParamDao;
import net.easipay.cbp.model.UcChannelParam;
import net.easipay.cbp.service.ChannelParamService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("channelParamService")
public class ChannelParamServiceImpl implements ChannelParamService {

	@Autowired
	private ChannelParamDao channelParamDao;

	@Override
	public void insertUcChannelParam(UcChannelParam ucChannelParam) {
		channelParamDao.insertUcChannelParam(ucChannelParam);
	}
	
	public void updateUcChannelParam(UcChannelParam ucChannelParam){
		channelParamDao.updateUcChannelParam(ucChannelParam);
	}

	
}
