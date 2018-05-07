/**
 * 
 */
package net.easipay.cbp.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import net.easipay.cbp.model.SacChannelParam;
import net.easipay.cbp.model.SacChannelParamCmd;


/**
 * @author Administrator
 *
 */
public interface ISacChannelParamService {
	
	public List<SacChannelParam> selectAllSacChannelParam(); 
	
	public SacChannelParam selectSacChannelParamByAcc(String accNo); 
	
	public List<SacChannelParam> selectUniqueSacChannelParamOfAcc() ;
	
	public List<SacChannelParam> selectUniqueSacChannelParamOfChnNo() ;
	
	public SacChannelParam selectSacChannelParamById(SacChannelParam sacChannelParam); 
	
	public List<SacChannelParam> selectSacChannelParamForSplit(SacChannelParam sacChannelParam,int pageNo,int pageSize); 

	public int selectSacChannelParamCounts(SacChannelParam sacChannelParam);
	
	public Map<String,String> selectAllSacBank();
	
	public List<Map<String,String>> selectAllBankAccByBankNodeCode(String bankNodeCode);

//	public void updateSacChannelParam(SacChannelParam sacChannelParam);
	
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
	
	/**
	 * 渠道参数录入
	 */
	public int addChannelParam(SacChannelParamCmd sacChannelParamCmd);
	
	/**
	 * 渠道参数指令查询
	 * @param paramMap
	 * @return
	 */
	public List<SacChannelParamCmd> selectSacChannelParamCmd(Map<String, Object> paramMap);
	
	/**
	 * 渠道参数指令查询总数
	 * @param paramMap
	 * @return
	 */
	public int selectSacChannelParamCmdCount(Map<String, Object> paramMap);
	
	/**
	 * 渠道参数录入审核通过
	 * @param id
	 * @return
	 */
	public String channelParamCheckSucc(long id);
	
	/**
	 * 渠道参数录入审核不通过
	 * @param id
	 * @return
	 */
	public String channelParamCheckFail(long id);

	public List<SacChannelParam> selectSacChannelParamForB2C(
			Map<String, Object> queryChannelMap);

	public Boolean validateBankAvalibleBal(String bankNodeCode,
			String currencyType, BigDecimal payAmount);
}
