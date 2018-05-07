package net.easipay.cbp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.ISacOflCommandDao;
import net.easipay.cbp.dao.ISacOtrxInfoDao;
import net.easipay.cbp.model.SacOflCommand;
import net.easipay.cbp.model.SacOtrxInfo;
import net.easipay.cbp.service.ISacOflCommandService;
import net.easipay.cbp.util.Utils;

import org.apache.commons.httpclient.util.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author sydai 
 */
@Service
public class SacOflCommandServiceImpl implements ISacOflCommandService{

	private static final Logger logger = Logger.getLogger(SacOflCommandServiceImpl.class);
	
	@Autowired
	private ISacOtrxInfoDao otrxInfoDao;
	
	@Autowired
    private ISacOflCommandDao sacOflCommandDao;

	@SuppressWarnings("unchecked")
	@Override
	public void updateSacOflCommand() {
		//设置扫描时间
		String beginDate = Utils.convertDate(new Date(), -2);//T-1
		String endDate = DateUtil.formatDate(new Date(),"yyyyMMdd");//T
		//扫描指令表
		Map paramMap = new HashMap();
		paramMap.put("beginDate", beginDate);
		paramMap.put("endDate", endDate);
		paramMap.put("state", "01");//审核处理中
		List<SacOflCommand> oflCommandList = sacOflCommandDao.selectSacOflCommandList(paramMap);
		if(oflCommandList!=null&&oflCommandList.size()>0){
			//扫描原始交易表
			paramMap.put("trxType", "1612");
			List<String> trxSerialNoList = new ArrayList<String>();
			for(SacOflCommand cmd:oflCommandList){
				if(cmd.getTrxSerialNo()!=null && cmd.getTrxSerialNo().trim().length()>0){
					trxSerialNoList.add(cmd.getTrxSerialNo());
				}
			}
			paramMap.put("trxSerialNoList", trxSerialNoList);
			paramMap.remove("beginDate");
			paramMap.remove("endDate");
			List<SacOtrxInfo> otrxInfoList = otrxInfoDao.selectOtrxInfoList(paramMap);
			if(otrxInfoList!=null && otrxInfoList.size()>0){
				for(SacOtrxInfo otrxInfo:otrxInfoList){
					try{
						sacOflCommandDao.updateSacOflCommand(installSacOflCommand(otrxInfo));
					}catch(Exception e){
						logger.error("B2B线下出款指令更新操作执行出错！流水号："+otrxInfo.getTrxSerialNo()+"。报错信息："+e.getMessage());
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	//组装B2B线下出款指令信息
	private SacOflCommand installSacOflCommand(SacOtrxInfo otrxInfo){
		SacOflCommand sacOflCommand = new SacOflCommand();
		sacOflCommand.setTrxSerialNo(otrxInfo.getTrxSerialNo());
		sacOflCommand.setLastUpdateTime(new Date());
		sacOflCommand.setState("02");
		return sacOflCommand;
	}
}

