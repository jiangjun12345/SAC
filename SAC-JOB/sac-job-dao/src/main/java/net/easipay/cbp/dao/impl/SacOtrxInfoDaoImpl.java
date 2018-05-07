package net.easipay.cbp.dao.impl;

import java.util.List;
import java.util.Map;
import net.easipay.cbp.dao.ISacOtrxInfoDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.SacOtrxInfo;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@SuppressWarnings({ "deprecation", "unchecked" })
@Repository
public class SacOtrxInfoDaoImpl extends GenericDaoiBatis<SacOtrxInfo, Long> implements ISacOtrxInfoDao {

	public static final Logger logger = Logger.getLogger(SacOtrxInfoDaoImpl.class);

	@Override
	public List<SacOtrxInfo> selectOtrxInfoList(Map<String, Object> paramMap) {
		return getSqlMapClientTemplate().queryForList("listSacOtrxInfo", paramMap);
	}

	
}
