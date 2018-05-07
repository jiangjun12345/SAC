package net.easipay.cbp.service.impl;

import java.util.Calendar;

import net.easipay.cbp.dao.ISequenceCreatorDao;
import net.easipay.cbp.service.ISequenceCreatorService;
import net.easipay.cbp.util.DateUtil;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SequenceCreatorServiceImpl implements ISequenceCreatorService
{
	@Autowired
	private ISequenceCreatorDao creatorDao;
	
	public static final Logger logger = Logger.getLogger(SequenceCreatorServiceImpl.class);

	@Override
	public String getSerialNo(String sequenceName,int length){
		String currentDate = DateUtil.formatCurrentDate(Calendar.DATE, 0, "yyyyMMdd");
		String serialNo = "";
		String seqVal = creatorDao.getSequenceNoBySeqName(sequenceName);
		if(seqVal.length()<=length){
			for(int i=0;i<length-seqVal.length();i++){
				serialNo = "0"+serialNo;
			}
		}
		serialNo = currentDate + serialNo + seqVal;
		return serialNo;
	}

	@Override
	public String getNextVal(String sequenceName) {
		return creatorDao.getSequenceNoBySeqName(sequenceName);
	}
	

	
}
