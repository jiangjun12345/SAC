package net.easipay.cbp.service.impl;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.OperatorDao;
import net.easipay.cbp.model.UcOperator;
import net.easipay.cbp.service.OperatorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("operatorService")
public class OperatorServiceImpl implements OperatorService {

	@Autowired
	private OperatorDao operatorDao;

	@Override
	public UcOperator selectUcOperatorById(Long id) {
		return operatorDao.selectUcOperatorById(id);
	}

	@Override
	public void insertUcOperator(UcOperator ucOperator) {
		operatorDao.insertUcOperator(ucOperator);
		
	}

	@Override
	public void updateUcOperator(UcOperator ucOperator) {
		operatorDao.updateUcOperator(ucOperator);
		
	}

	/* (non-Javadoc)
	 * @see net.easipay.cbp.service.OperatorService#selectAllUcOperator()
	 */
	@Override
	public List<UcOperator> selectAllUcOperator(int pageNo) {
		return operatorDao.selectAllUcOperator(pageNo);
	}

	/* (non-Javadoc)
	 * @see net.easipay.cbp.service.OperatorService#selectUcOperatorByParamter()
	 */
	@Override
	public List<UcOperator> selectUcOperatorByParameter(UcOperator ucOperator,int pageNo) {
		return operatorDao.selectUcOperatorByParameter(ucOperator,pageNo);
	}

	/* (non-Javadoc)
	 * @see net.easipay.cbp.service.OperatorService#selectUcOperatorCounts()
	 */
	@Override
	public int selectUcOperatorCounts(UcOperator ucOperator) {
		return operatorDao.selectUcOperatorCountsByParameter(ucOperator); 
	}

	public UcOperator selectUcOperatorByEmailAndPass(String email,String password){
		return operatorDao.selectUcOperatorByEmailAndPass(email, password);
	}

	@Override
	public int selectUcOperatorCountsForValidate(UcOperator ucOperator) {
		return operatorDao.selectUcOperatorCountsForValidate(ucOperator);
	}

	@Override
	public List<Map<String,Object>> getSacCusParameterFromTemp() {
		return operatorDao.getSacCusParameterFromTemp();
	}

	@Override
	public int validateRepeatCus(Map<String, Object> cusParam) {
		return operatorDao.validateRepeatCus(cusParam);
	}
}
