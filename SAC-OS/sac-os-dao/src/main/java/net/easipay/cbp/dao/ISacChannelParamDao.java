package net.easipay.cbp.dao;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.SacChannelParam;

public interface ISacChannelParamDao extends GenericDao<SacChannelParam,Long>
{

	/**
	 * 查询所有渠道
	 * @return
	 */
	public List<SacChannelParam> queryAllSacChannelParam();
	
	public List<SacChannelParam> querySacChannelParamToCache();
	
	public List<SacChannelParam> selectUniqueSacChannelParamOfAcc();
	
	public Map<String, String> selectAllSacBank();
	
	public List<Map<String,String>> selectAllBankAccByBankNodeCode(String bankNodeCode);
	
	/**
	 * 根据ID查询渠道
	 * @param channel
	 * @return
	 */
	public SacChannelParam selectSacChannelParamById(SacChannelParam sacChannelParam);

	public List<SacChannelParam> selectSacChannelParamForSplit(SacChannelParam sacChannelParam,int pageNo,int pageSize);

	public int selectSacChannelParamCounts(SacChannelParam sacChannelParam);

	public SacChannelParam selectSacChannelParamByAcc(String accNo);

	public List<SacChannelParam> selectUniqueSacChannelParamOfChnNo();
	
	/**
	 * 根据条件分页查询渠道备付金实时余额
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> preparedFundRealTimeQuery(Map<String, Object> paramMap);

	/**
	 * 根据条件查询渠道备付金实时余额总数
	 * 
	 * @param paramMap
	 * @return
	 */
	public int getPreparedFundRealTimeCount(Map<String, Object> paramMap);
	
	/**
	 * 根据条件不分页查询渠道备付金实时余额
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> simplePreparedFundRealTimeQuery(Map<String, Object> paramMap);

	public List<Map<String, Object>> getBankAvalibleBal(
			Map<String, Object> queryMap);
	
}
