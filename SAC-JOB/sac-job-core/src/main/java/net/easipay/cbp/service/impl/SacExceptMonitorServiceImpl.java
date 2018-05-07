package net.easipay.cbp.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.IExceptMonitorDao;
import net.easipay.cbp.model.SacExceptMonitor;
import net.easipay.cbp.sequence.SequenceCreatorUtil;
import net.easipay.cbp.service.ISacExceptMonitorService;
import net.easipay.cbp.util.ConstantParams;
import net.easipay.cbp.util.DateUtil;
import net.easipay.dsfc.ws.jws.JwsClient;
import net.easipay.dsfc.ws.jws.JwsException;
import net.easipay.dsfc.ws.jws.JwsResult;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SacExceptMonitorServiceImpl implements ISacExceptMonitorService {
	
	private static final Logger logger = Logger.getLogger(SacDepositDetailCmdServiceImpl.class);
	@Autowired
	private IExceptMonitorDao monitorDao;
	@Override
	public void registerExceptDetail() {
		List<SacExceptMonitor> sacExceptMonitorList = new ArrayList<SacExceptMonitor>();
		
		Map<String,Object> queryMap = new HashMap<String, Object>();
		
		queryMap.put("createTime", new Date());
		
		//查询50分钟内的所有异常数据
		List<SacExceptMonitor> mtList = monitorDao.getSacExcetMonitorList(queryMap);
		
		List<SacExceptMonitor> recordLogList = monitorDao.getSacRecordLogInfo();
		List<SacExceptMonitor> recdiffList = monitorDao.getSacRecDifferenceInfo();
		List<SacExceptMonitor> sacRecjobList = monitorDao.getSacRecJobLogInfo();
		List<SacExceptMonitor> monitorInfoList = getDsfsAlarmInfo();
		
		List<SacExceptMonitor> finTaskList = monitorDao.getFinTaskInfo();
		sacExceptMonitorList.addAll(recordLogList);
		sacExceptMonitorList.addAll(recdiffList);
		sacExceptMonitorList.addAll(sacRecjobList);
		sacExceptMonitorList.addAll(monitorInfoList);
		sacExceptMonitorList.addAll(finTaskList);
		
		Iterator<SacExceptMonitor> it = sacExceptMonitorList.iterator();
		while(it.hasNext()){
			SacExceptMonitor nt = it.next();
			String tableId2 = nt.getTableId();
			String operateType2 = nt.getOperateType();
			for(SacExceptMonitor mt : mtList){
				String tableId = mt.getTableId();
				String operateType = mt.getOperateType();
				if(tableId.equals(tableId2)&&operateType.equals(operateType2)){
					it.remove();
				}
			}
		}

		if(null!= sacExceptMonitorList && sacExceptMonitorList.size()>0){
			logger.info("****已扫描到异常信息监控任务 "+sacExceptMonitorList.size()+"条****");
			for (int i = 0; i < sacExceptMonitorList.size(); i++) {
				sacExceptMonitorList.get(i).setId(SequenceCreatorUtil.getTableId());
				sacExceptMonitorList.get(i).setStatus("01");
			}
			try {
				monitorDao.insertSacexceptMonitor(sacExceptMonitorList);
			} catch (Exception e) {
				// TODO Auto-generated catch block
	            logger.error("异常监控定时录入任务 执行出错！流水号：" + "。报错信息：" + e.getMessage());
				e.printStackTrace();
			}
		}

	}
	private List<SacExceptMonitor> getDsfsAlarmInfo() {
		JwsResult result = null;
		try{
			JwsClient jc = new JwsClient("SAC-DSFS-0010");
			result = jc.call();
			if(result.isSuccess()&&result.getCode().equals(ConstantParams.RTN_CODE_SUCCESSS)){
				List<SacExceptMonitor> list = result.getList("monitorInfoList", SacExceptMonitor.class);
				for(SacExceptMonitor em : list){
					em.setOperateType("01");//接口调用
				}
				return list;
			}
		}catch(JwsException e){
			logger.error("request DSFS exception :"+e.getMessage());
		}
		return null;
	}

}
