package net.easipay.cbp.service.impl;

import java.util.List;

import net.easipay.cbp.dao.ISacCusPaymentDao;
import net.easipay.cbp.model.SacCusPayment;
import net.easipay.cbp.service.ISacCusPaymentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sacCusPaymentService")
public class SacCusPaymentServiceImpl implements ISacCusPaymentService {

	@Autowired
	private ISacCusPaymentDao sacCusPaymentDao;

	@Override
	public List<SacCusPayment> selectSacCusPaymentByParameter(
			SacCusPayment sacCusPayment) {
		return sacCusPaymentDao.selectSacCusPaymentByParameter(sacCusPayment);
	}



	
}
