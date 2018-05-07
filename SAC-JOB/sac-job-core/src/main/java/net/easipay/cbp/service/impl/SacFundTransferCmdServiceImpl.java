package net.easipay.cbp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.ISacFundTransferCmdDao;
import net.easipay.cbp.dao.ISacOtrxInfoDao;
import net.easipay.cbp.model.SacFundTransferCmd;
import net.easipay.cbp.model.SacOtrxInfo;
import net.easipay.cbp.service.ISacFundTransferCmdService;
import net.easipay.cbp.util.Utils;

import org.apache.commons.httpclient.util.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author sydai 
 */
@Service
public class SacFundTransferCmdServiceImpl implements ISacFundTransferCmdService{

	private static final Logger logger = Logger.getLogger(SacFundTransferCmdServiceImpl.class);
	
	@Autowired
	private ISacOtrxInfoDao otrxInfoDao;
	
	@Autowired
    private ISacFundTransferCmdDao sacFundTransferCmdDao;

	@SuppressWarnings("unchecked")
	@Override
	public void updateSacFundTransferCmd() {
		//设置扫描时间
		String beginDate = Utils.convertDate(new Date(), -30);//T-1
		String endDate = DateUtil.formatDate(new Date(),"yyyyMMdd");//T
		//扫描指令表
		Map paramMap = new HashMap();
		paramMap.put("beginDate", beginDate);
		paramMap.put("endDate", endDate);
		paramMap.put("cmdState", "4");//审核处理中
		List<SacFundTransferCmd> fundTransferCmdList = sacFundTransferCmdDao.selectSacFundTransferCmdList(paramMap);
		if(fundTransferCmdList!=null&&fundTransferCmdList.size()>0){
			//扫描原始交易表
			paramMap.put("trxType", "1701");//资金调拨
			List<String> trxSerialNoList = new ArrayList<String>();
			for(SacFundTransferCmd cmd:fundTransferCmdList){
				trxSerialNoList.add(cmd.getTrxSerialNo());
			}
			paramMap.put("trxSerialNoList", trxSerialNoList);
			paramMap.remove("beginDate");
			paramMap.remove("endDate");
			List<SacOtrxInfo> otrxInfoList = otrxInfoDao.selectOtrxInfoList(paramMap);
			if(otrxInfoList!=null && otrxInfoList.size()>0){
				for(SacOtrxInfo otrxInfo:otrxInfoList){
					try{
						sacFundTransferCmdDao.updateSacFundTransferCmd(installSacFundTransferCmd(otrxInfo));
					}catch(Exception e){
						logger.error("资金调拨指令更新操作执行出错！流水号："+otrxInfo.getTrxSerialNo()+"。报错信息："+e.getMessage());
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	//组装资金调拨指令信息
	private SacFundTransferCmd installSacFundTransferCmd(SacOtrxInfo otrxInfo){
		SacFundTransferCmd fundTransferCmd = new SacFundTransferCmd();
		fundTransferCmd.setTrxSerialNo(otrxInfo.getTrxSerialNo());
		fundTransferCmd.setUpdateTime(new Date());
		if(otrxInfo.getTrxState().equals("S")){//交易成功
			fundTransferCmd.setCmdState("2");//成功
		}else if(otrxInfo.getTrxState().equals("F")){//交易失败
			fundTransferCmd.setCmdState("6");//失败
		}
		return fundTransferCmd;
	}

}

