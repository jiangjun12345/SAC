package net.easipay.cbp.service.impl;

import java.util.Date;

import net.easipay.cbp.dao.ISacCheckInfoDao;
import net.easipay.cbp.exception.AcException;
import net.easipay.cbp.form.SacReplacePayReceiveForm;
import net.easipay.cbp.model.SacCheckInfo;
import net.easipay.cbp.sequence.SequenceCreatorUtil;
import net.easipay.cbp.service.ISacReplacePayService;
import net.easipay.dsfc.ws.jws.JwsObjectMapper;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sacReplacePayService")
public class SacReplacePayService implements ISacReplacePayService
{	
	@Autowired
	private ISacCheckInfoDao sacCheckInfoDao;

	@Override
	public void receiveSacReplacePayCheckInfo(SacReplacePayReceiveForm form)
			throws AcException {
		String formString = JwsObjectMapper.instance.writeValueAsString(form);
		Date date = new Date();
		SacCheckInfo checkInfo = new SacCheckInfo();
		checkInfo.setId(SequenceCreatorUtil.getTableId());
		checkInfo.setCheckStatus("2");//待审核
		checkInfo.setCreateTime(date);
		checkInfo.setDigst(formString);
		checkInfo.setTrxType("5101");//代付交易
		checkInfo.setUpdateTime(date);
		if(StringUtils.isNotBlank(form.getTrxType())&&form.getTrxType().length()>4){
			checkInfo.setMemo(form.getTrxType());
		}
		
		sacCheckInfoDao.insertSacCheckInfo(checkInfo);
	}
	
    
}
