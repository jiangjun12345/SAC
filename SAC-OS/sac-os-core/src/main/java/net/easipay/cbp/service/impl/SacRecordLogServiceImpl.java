package net.easipay.cbp.service.impl;

import java.util.List;

import net.easipay.cbp.dao.ISacRecordLogDao;
import net.easipay.cbp.model.SacRecordLog;
import net.easipay.cbp.service.ISacRecordLogService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sacRecordLogService")
public class SacRecordLogServiceImpl implements ISacRecordLogService {
	
	private static final Logger logger = Logger.getLogger(SacRecordLogServiceImpl.class);

	@Autowired
	private ISacRecordLogDao sacRecordLogDao;

	@Override
	public int selectSacRecordLogCounts(SacRecordLog sacRecordLog) {
		return sacRecordLogDao.selectSacRecordLogCounts(sacRecordLog);
	}

	@Override
	public List<SacRecordLog> selectSacRecordLogByParam(
			SacRecordLog sacRecordLog, int pageNo, int pageSize) {
		return sacRecordLogDao.selectSacRecordLogByParam(sacRecordLog, pageNo, pageSize);
	}

	@Override
	public SacRecordLog selectSacRecordLogById(SacRecordLog sacRecordLog) {
		return sacRecordLogDao.selectSacRecordLogById(sacRecordLog);
	}


}
