package net.easipay.cbp.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.ISacOnlCmdBatchDao;
import net.easipay.cbp.dao.ISacOnlCommandDao;
import net.easipay.cbp.dao.ISacOtrxInfoDao;
import net.easipay.cbp.model.SacB2bCmdBatch;
import net.easipay.cbp.sequence.SequenceCreatorUtil;
import net.easipay.cbp.service.ISacOnlCommandService;
import net.easipay.cbp.service.ISequenceCreatorService;
import net.easipay.cbp.util.DateUtil;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SacOnlCommandServiceImpl implements ISacOnlCommandService{

	private static final Logger logger = Logger.getLogger(SacOnlCommandServiceImpl.class);
	
	@Autowired
	private ISacOtrxInfoDao otrxInfoDao;
	
	@Autowired
    private ISacOnlCommandDao sacOnlCommandDao;
	
	@Autowired
	private ISacOnlCmdBatchDao sacOnlCmdBatchDao;
	
	@Autowired
	private ISequenceCreatorService sequenceCreatorService;
	
	

	@Override
	public void makeCommandBatch() throws ParseException {
		String yesteday = DateUtil.formatCurrentDate(Calendar.DATE, -1, "yyyyMMdd");//昨天
		
		String today = DateUtil.formatCurrentDate(Calendar.DATE, 0, "yyyyMMdd");
    	
    	Map<String,Object> queryMap = new HashMap<String,Object>();
		
    	queryMap.put("createTime", yesteday);
    	
    	queryMap.put("cmdState", "N");
    	
    	queryMap.put("flag", "1");//找非线下的指令 0：线上 1：线下
    	
		List<Map<String,Object>> rtList = sacOnlCommandDao.getSumAmountForBatch(queryMap);
		
		for(Map<String,Object> rtMap : rtList){
			
			
			BigDecimal totalAmount = (BigDecimal)rtMap.get("totalAmount");
			String msgReceiver = (String)rtMap.get("msgReceiver");
			String payCurrency = (String)rtMap.get("payCurrency");
			String batchCount = rtMap.get("totalCounts").toString();
			SacB2bCmdBatch batch = new SacB2bCmdBatch();
			String tableIdStr = sequenceCreatorService.getSerialNo("SAC_B2B_CMD_BATCH_SEQ", 3);//日期加3位序号
			
			//Long tableId = SequenceCreatorUtil.getTableId();
			batch.setCmdBatchId(Long.parseLong(tableIdStr));
			batch.setBatchSerialNo(tableIdStr);
			batch.setVldDate(new SimpleDateFormat("yyyyMMdd").parse(today));
			batch.setMsgReceiver(msgReceiver);
			batch.setBatchState("00");//待处理
			batch.setCreateTime(new Date());
			batch.setBatchCur(payCurrency);
			batch.setBatchAmount(totalAmount);
			batch.setBatchCount(Long.parseLong(batchCount));
			sacOnlCmdBatchDao.save(batch);
			
			//更新批次号入指令明细表
			Map<String,Object> updateMap = new HashMap<String, Object>();
			updateMap.put("createTime", yesteday);
			updateMap.put("payCurrency", payCurrency);
			updateMap.put("msgReceiver", msgReceiver);
			updateMap.put("batchSerialNo", tableIdStr);
			updateMap.put("cmdState", "N");
			updateMap.put("flag", "1");//找非线下的指令 0：线上 1：线下
			sacOnlCommandDao.updateCommandForBatch(updateMap);
			
		}
	}
}

