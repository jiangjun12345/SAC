package net.easipay.cbp.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import net.easipay.cbp.dao.ISacChannelParamDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.SacChannelParam;
import net.easipay.cbp.util.BeanUtils;

@SuppressWarnings({ "deprecation", "unchecked" })
@Repository("sacChannelParam")
public class SacChannelParamDaoImpl extends GenericDaoiBatis<SacChannelParam,Long> implements ISacChannelParamDao
{

	private static final Logger logger = Logger.getLogger(SacChannelParamDaoImpl.class);
	
	@Override
	public List<SacChannelParam> queryAllSacChannelParam()
	{
		return getSqlMapClientTemplate().queryForList("listSacChannelParam");
	}
	
	@Override
	public List<SacChannelParam> querySacChannelParamToCache()
	{
		return getSqlMapClientTemplate().queryForList("listSacChannelParamToCache");
	}
	
	@Override
	public List<SacChannelParam> selectUniqueSacChannelParamOfAcc()
	{
		return getSqlMapClientTemplate().queryForList("listUniqueSacChannelParam");
	}

	@Override
	public SacChannelParam selectSacChannelParamById(SacChannelParam sacChannelParam)
	{
		logger.debug("querySacChannelParamById  channel  is >>>>"+sacChannelParam);
		return (SacChannelParam) getSqlMapClientTemplate().queryForObject("listSacChannelParam",sacChannelParam);
	}

	@Override
	public List<SacChannelParam> selectSacChannelParamForSplit(
			SacChannelParam sacChannelParam,int pageNo,int pageSize) {
		Map<String,Object> queryMap = BeanUtils.transBean2Map(sacChannelParam);
		queryMap.put("end", pageNo*pageSize);
		queryMap.put("start", ((pageNo-1)*pageSize));
		return getSqlMapClientTemplate().queryForList("listSplitSacChannelParam", queryMap);
	}

	@Override
	public int selectSacChannelParamCounts(SacChannelParam sacChannelParam) {
		return (Integer)getSqlMapClientTemplate().queryForObject("getSacChannelParamCount", sacChannelParam);
	}

	@Override
	public SacChannelParam selectSacChannelParamByAcc(String accNo) {
		Map<String,Object> queryMap = new HashMap<String,Object>();
		queryMap.put("bankAcc", accNo);
		return (SacChannelParam)getSqlMapClientTemplate().queryForObject("selectSacChannelParamByAcc", queryMap);
	}

	@Override
	public List<SacChannelParam> selectUniqueSacChannelParamOfChnNo() {
		return getSqlMapClientTemplate().queryForList("listUniqueSacChannelParamOfChnNo");
	}

	@Override
	public List<Map<String, Object>> preparedFundRealTimeQuery(Map<String, Object> paramMap)
	{
		return getSqlMapClientTemplate().queryForList("listSplitPreparedFund", paramMap);
	}

	@Override
	public int getPreparedFundRealTimeCount(Map<String, Object> paramMap)
	{
		return (Integer) getSqlMapClientTemplate().queryForObject("getPreparedFundCount", paramMap);
	}

	@Override
	public List<Map<String, Object>> simplePreparedFundRealTimeQuery(Map<String, Object> paramMap)
	{
		return getSqlMapClientTemplate().queryForList("listPreparedFund", paramMap);
	}

	@Override
	public Map<String, String> selectAllSacBank() {
		return getSqlMapClientTemplate().queryForMap("selectAllSacBank", null, "bankNodeCode", "sacBankName");
	}

	@Override
	public List<Map<String, String>> selectAllBankAccByBankNodeCode(String bankNodeCode) {
		return getSqlMapClientTemplate().queryForList("selectBankAccInfoByBankNodeCode",bankNodeCode);
	}

	@Override
	public List<Map<String, Object>> getBankAvalibleBal(
			Map<String, Object> queryMap) {
		return getSqlMapClientTemplate().queryForList("getPreparedAvalibleBal", queryMap);
	}
	
}
