package net.easipay.cbp.dao.impl;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.ISacChannelParamCmdDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.SacChannelParam;
import net.easipay.cbp.model.SacChannelParamCmd;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository("sacChannelParamCmd")
public class SacChannelParamCmdDaoImpl extends GenericDaoiBatis<SacChannelParamCmd,Long> implements ISacChannelParamCmdDao
{

	private static final Logger logger = Logger.getLogger(SacChannelParamCmdDaoImpl.class);

	@Override
	public Object addSacChannelParamCmd(SacChannelParamCmd channelParamCmd) {
		return getSqlMapClientTemplate().insert("insertSacChannelParamCmd", channelParamCmd);
	}

	@Override
	public int updateSacChannelParamCmd(SacChannelParamCmd channelParamCmd) {
		return getSqlMapClientTemplate().update("updateSacChannelParamCmd", channelParamCmd);
	}

	@Override
	public int deleteSacChannelParamCmd(SacChannelParamCmd channelParamCmd) {
		return getSqlMapClientTemplate().delete("deleteSacChannelParamCmd", channelParamCmd);
	}

	@Override
	public List<SacChannelParamCmd> selectSacChannelParamCmd(Map<String, Object> paramMap) {
		return getSqlMapClientTemplate().queryForList("selectSacChannelParamCmd", paramMap);
	}

	@Override
	public int selectSacChannelParamCmdCount(Map<String, Object> paramMap) {
		return (Integer) getSqlMapClientTemplate().queryForObject("selectSacChannelParamCmdCount", paramMap);
	}

	@Override
	public SacChannelParamCmd selectSacChannelParamCmdById(long id) {
		Object o = getSqlMapClientTemplate().queryForObject("selectSacChannelParamCmdById", id);
		if(o==null){
			return null;
		}else{
			return (SacChannelParamCmd) o;
		}
	}

	@Override
	public List<SacChannelParam> selectSacChannelParamForB2C(
			Map<String, Object> queryChannelMap) {
		return getSqlMapClientTemplate().queryForList("selectSacChannelParamForB2C", queryChannelMap);
	}
	
}
