package net.easipay.cbp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.ISacB2BCommandDao;
import net.easipay.cbp.dao.ISacOtrxInfoDao;
import net.easipay.cbp.model.SacB2BCommand;
import net.easipay.cbp.model.SacOtrxInfo;
import net.easipay.cbp.service.ISacTransferCmdService;
import net.easipay.cbp.util.Utils;

import org.apache.commons.httpclient.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author sydai 
 */
@Service
public class SacTransferCmdServiceImpl implements ISacTransferCmdService{
	
	private static final Logger logger = Logger.getLogger(SacTransferCmdServiceImpl.class);
	
	@Autowired
	private ISacOtrxInfoDao otrxInfoDao;
	
	@Autowired
	private ISacB2BCommandDao b2bCommandDao;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void updateB2BRefundCmd() {
		//设置扫描时间
		String beginDate = Utils.convertDate(new Date(), -30);//T-1
		String endDate = DateUtil.formatDate(new Date(),"yyyyMMdd");//T
		//扫描B2B划款指令表
		Map paramMap = new HashMap();
		paramMap.put("beginDate", beginDate);
		paramMap.put("endDate", endDate);
		paramMap.put("cmdState", "QS");//待更新状态
		List<SacB2BCommand> b2bCommandList = b2bCommandDao.selectB2BCommandList(paramMap);
		if(b2bCommandList!=null&&b2bCommandList.size()>0){
			//扫描原始交易表
			List<String> trxTypeList = new ArrayList<String>();
			trxTypeList.add("9338");
			trxTypeList.add("9308");
			trxTypeList.add("611141");
			trxTypeList.add("615141");
			trxTypeList.add("611341");
			trxTypeList.add("615341");
			paramMap.put("trxTypeList", trxTypeList);
			List<String> trxSerialNoList = new ArrayList<String>();
			for(SacB2BCommand b2bCmd:b2bCommandList){
				trxSerialNoList.add(b2bCmd.getCmdSerialNo());
			}
			paramMap.put("trxSerialNoList", trxSerialNoList);
			paramMap.remove("beginDate");
			paramMap.remove("endDate");
			List<SacOtrxInfo> otrxInfoList = otrxInfoDao.selectOtrxInfoList(paramMap);
			if(otrxInfoList!=null && otrxInfoList.size()>0){
				for(SacOtrxInfo otrxInfo:otrxInfoList){
					try{
						b2bCommandDao.updateB2BCommand(installSacB2BCommand(otrxInfo));
					}catch(Exception e){
						logger.error("B2B划款指令更新操作执行出错！流水号："+otrxInfo.getTrxSerialNo()+"。报错信息："+e.getMessage());
						e.printStackTrace();
					}
				}
			}
			
			//扫描原始交易表
			Map queryMap = new HashMap();
			List<String> trxTypeList1 = new ArrayList<String>();
			trxTypeList1.add("1605");
			trxTypeList1.add("610142");
			trxTypeList1.add("610342");
			queryMap.put("trxTypeList", trxTypeList1);
			List<String> otrxSerialNoList = new ArrayList<String>();
			for(SacB2BCommand b2bCmd:b2bCommandList){
				otrxSerialNoList.add(b2bCmd.getOtrxSerialNo());
			}
			queryMap.put("otrxSerialNoList", otrxSerialNoList);
			List<SacOtrxInfo> trxInfoList = otrxInfoDao.selectOtrxInfoList(queryMap);
			if(trxInfoList!=null && trxInfoList.size()>0){
				for(SacOtrxInfo trxInfo:trxInfoList){
					if(StringUtils.isBlank(trxInfo.getOtrxSerialNo())){
						continue;
					}
					try{
						b2bCommandDao.updateB2BCommand(installSacB2BCommandForFailue(b2bCommandList,trxInfo));
					}catch(Exception e){
						logger.error("B2B划款指令更新操作执行出错！流水号："+trxInfo.getTrxSerialNo()+"。报错信息："+e.getMessage());
						e.printStackTrace();
					}
				}
			}
			
			
			
			
			
		}
	}
	
	//组装B2B划款指令信息
	private SacB2BCommand installSacB2BCommand(SacOtrxInfo otrxInfo){
		SacB2BCommand b2bCommand = new SacB2BCommand();
		
		if(otrxInfo.getTrxType().equals("9308")||otrxInfo.getTrxType().equals("9338")||otrxInfo.getTrxType().equals("611141")||otrxInfo.getTrxType().equals("615141")||otrxInfo.getTrxType().equals("611341")||otrxInfo.getTrxType().equals("615341")){//成功交易
			b2bCommand.setCmdSerialNo(otrxInfo.getTrxSerialNo());
			b2bCommand.setCmdState("S");
		}
		return b2bCommand;
	}
	
	//组装B2B划款指令信息
		private SacB2BCommand installSacB2BCommandForFailue(List<SacB2BCommand> b2bCommandList ,SacOtrxInfo otrxInfo){
			String otrxSerialNo = otrxInfo.getOtrxSerialNo();
			String cmdSerialNo = "";
			for(SacB2BCommand cmd : b2bCommandList){
				if(otrxSerialNo.equals(cmd.getOtrxSerialNo())){
					cmdSerialNo = cmd.getCmdSerialNo();
				}
			}
			SacB2BCommand b2bCommand = new SacB2BCommand();
			
			if(otrxInfo.getTrxType().equals("1605")||otrxInfo.getTrxType().equals("610142")||otrxInfo.getTrxType().equals("610342")){//失败交易
				b2bCommand.setCmdState("F");
				b2bCommand.setCmdSerialNo(cmdSerialNo);
			}
			return b2bCommand;
		}
}

