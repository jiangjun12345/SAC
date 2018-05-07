package net.easipay.cbp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.ISacDepositDetailDao;
import net.easipay.cbp.dao.ISacOtrxInfoDao;
import net.easipay.cbp.model.SacDepositDetail;
import net.easipay.cbp.model.SacOtrxInfo;
import net.easipay.cbp.service.ISacDepositDetailCmdService;
import net.easipay.cbp.util.Utils;

import org.apache.commons.httpclient.util.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author sydai 
 */
@Service
public class SacDepositDetailCmdServiceImpl implements ISacDepositDetailCmdService{
	
	private static final Logger logger = Logger.getLogger(SacDepositDetailCmdServiceImpl.class);
	
	@Autowired
	private ISacOtrxInfoDao otrxInfoDao;
	
	@Autowired
	private ISacDepositDetailDao depositCmdDao;
	
	@SuppressWarnings("unchecked")
	@Override
	public void updateDepositDetailCmd() {
		//设置扫描时间
		String beginDate = Utils.convertDate(new Date(), -1);//T-1
		String endDate = DateUtil.formatDate(new Date(),"yyyyMMdd");//T
		//扫描B2B划款指令表
		Map paramMap = new HashMap();
		paramMap.put("beginDate", beginDate);
		paramMap.put("endDate", endDate);
		paramMap.put("checkState", "00");//未核销
		List<SacDepositDetail> depositDetailList = depositCmdDao.selectSacDepositDetailList(paramMap);
		if(depositDetailList!=null&&depositDetailList.size()>0){
			//扫描原始交易表
			List<String> trxTypeList = new ArrayList<String>();
			trxTypeList.add("1621");
			trxTypeList.add("1622");
			paramMap.put("trxTypeList", trxTypeList);
			List<String> trxSerialNoList = new ArrayList<String>();
			for(SacDepositDetail depositCmd:depositDetailList){
				if(depositCmd.getTrxSerialNo()!=null && depositCmd.getTrxSerialNo().trim().length()>0){
					trxSerialNoList.add(depositCmd.getTrxSerialNo());
				}
			}
			paramMap.put("trxSerialNoList", trxSerialNoList);
			paramMap.remove("beginDate");
			paramMap.remove("endDate");
			List<SacOtrxInfo> otrxInfoList = otrxInfoDao.selectOtrxInfoList(paramMap);
			if(otrxInfoList!=null && otrxInfoList.size()>0){
				for(SacOtrxInfo otrxInfo:otrxInfoList){
					try{
						depositCmdDao.updateSacDepositDetail(installSacDepositDetail(otrxInfo));
					}catch(Exception e){
						logger.error("线下预存指令更新操作执行出错！流水号："+otrxInfo.getTrxSerialNo()+"。报错信息："+e.getMessage());
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	//组装线下预存指令信息
	private SacDepositDetail installSacDepositDetail(SacOtrxInfo otrxInfo){
		SacDepositDetail depositDetailCmd = new SacDepositDetail();
		depositDetailCmd.setTrxSerialNo(otrxInfo.getTrxSerialNo());
		depositDetailCmd.setCheckState("10");
		return depositDetailCmd;
	}

}

