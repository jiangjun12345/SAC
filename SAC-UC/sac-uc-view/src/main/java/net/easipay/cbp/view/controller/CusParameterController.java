package net.easipay.cbp.view.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.easipay.cbp.model.UcOperator;
import net.easipay.cbp.sequence.SequenceCreatorUtil;
import net.easipay.cbp.service.OperatorService;
import net.easipay.cbp.util.MD5Util;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**

 **/
public class CusParameterController {
	private static final Logger logger = Logger.getLogger(CusParameterController.class);
	
	@Autowired
	private OperatorService operatorService;
	
	public void init(){
		Thread thread = new Thread() {
			public void run() {
				List<Map<String,Object>> cusList = operatorService.getSacCusParameterFromTemp();
				for(Map<String,Object> cusParam : cusList){
					UcOperator ucOperator = new UcOperator();
					int count = operatorService.validateRepeatCus(cusParam);
					if(count>0){
						continue;
					}
					String cardId = (String)cusParam.get("orgCardId");
					ucOperator.setId(SequenceCreatorUtil.getTableId());
					ucOperator.setLoginName((String)cusParam.get("cusName"));
					ucOperator.setLoginNameCh((String)cusParam.get("cusName"));
					ucOperator.setCreateTime(new Date());
					ucOperator.setCreateUserId(99L);
					ucOperator.setRoleId(99999L);
					ucOperator.setOrgId(cardId);
					ucOperator.setStatus("Y");
					ucOperator.setUserType("02");//商户
					ucOperator.setPassword(MD5Util.getMD5By32bit(cardId, "UTF-8"));
					operatorService.insertUcOperator(ucOperator);
				}
				logger.info("初始化商户权限信息结束");
				}
			};
			thread.start();
	}
			
	
}

