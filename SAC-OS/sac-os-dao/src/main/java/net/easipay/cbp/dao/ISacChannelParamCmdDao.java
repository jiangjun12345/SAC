package net.easipay.cbp.dao;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.SacChannelParam;
import net.easipay.cbp.model.SacChannelParamCmd;

public interface ISacChannelParamCmdDao extends GenericDao<SacChannelParamCmd,Long>
{
	/**
	 * 增加渠道参数录入指令
	 * @param channelParamCmd
	 * @return
	 */
	public Object addSacChannelParamCmd(SacChannelParamCmd channelParamCmd);
	
	/**
	 * 更新渠道参数指令
	 * @param channelParamCmd
	 * @return
	 */
	public int updateSacChannelParamCmd(SacChannelParamCmd channelParamCmd);
	
	/**
	 * 删除指令
	 * @param channelParamCmd
	 * @return
	 */
	public int deleteSacChannelParamCmd(SacChannelParamCmd channelParamCmd);
	
	/**
	 * 分页查询渠道参数指令
	 * @param paramMap
	 * @return
	 */
	public List<SacChannelParamCmd> selectSacChannelParamCmd(Map<String,Object> paramMap);
	
	/**
	 * 查询渠道参数指令总数
	 * @param paramMap
	 * @return
	 */
	public int selectSacChannelParamCmdCount(Map<String,Object> paramMap);
	
	/**
	 * 根据id查询渠道参数指令
	 * @param id
	 * @return
	 */
	public SacChannelParamCmd selectSacChannelParamCmdById(long id);

	public List<SacChannelParam> selectSacChannelParamForB2C(
			Map<String, Object> queryChannelMap);
}
