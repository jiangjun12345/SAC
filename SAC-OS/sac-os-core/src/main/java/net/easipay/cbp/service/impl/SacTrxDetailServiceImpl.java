package net.easipay.cbp.service.impl;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.ISacTrxDetailDao;
import net.easipay.cbp.model.SacTrxDetail;
import net.easipay.cbp.service.ISacTrxDetailService;
import net.easipay.cbp.util.BeanUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sacTrxDetailService")
public class SacTrxDetailServiceImpl implements ISacTrxDetailService {

	@Autowired
	private ISacTrxDetailDao sacTrxDetailDao;

	@Override
	public List<SacTrxDetail> selectSacTrxDetailByParameter(
			SacTrxDetail sacTrxDetail) {
		Map<String,Object> queryMap = BeanUtils.transBean2Map(sacTrxDetail);
		return sacTrxDetailDao.simpleQueryTrxDetail(queryMap);
	}

	@Override
	public void updateTrxDetailForDB(SacTrxDetail sacTrxDetail) {
		sacTrxDetailDao.updateTrxDetailBySerialNo(sacTrxDetail);
		
	}



	
}
