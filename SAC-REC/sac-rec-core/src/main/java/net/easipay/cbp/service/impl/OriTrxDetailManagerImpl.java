/**
 * 
 */
package net.easipay.cbp.service.impl;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.OriTrxDetailDao;
import net.easipay.cbp.model.SacTrxDetail;
import net.easipay.cbp.service.OriTrxDetailManager;
import net.easipay.cbp.service.base.impl.GenericManagerImpl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: TODO(用一句话描述该文件做什么)
 * @author sun (作者英文名称)
 * @date 2015-4-2 下午12:30:57
 * @version V1.0
 * @jdk v1.6
 * @tomcat v7.0
 */
@Service("oriTrxDetailManager")
public class OriTrxDetailManagerImpl extends
		GenericManagerImpl<SacTrxDetail, Long> implements OriTrxDetailManager {
	@Autowired
	OriTrxDetailDao oriTrxDetailDao;

	private static final Logger logger = Logger
			.getLogger(OriTrxDetailManagerImpl.class);

	@Override
	public List<SacTrxDetail> selectOriTrxDetailList(
			Map<String, Object> filterMap) throws Exception {
		List<SacTrxDetail> otrxDetailList = oriTrxDetailDao
				.getOriTrxDetailList(filterMap);
		if (otrxDetailList != null && !otrxDetailList.isEmpty()) {
			return otrxDetailList;
		}
		return null;
	}

	@Override
	public SacTrxDetail selectOriTrxDetail(Map<String, Object> filterMap)
			throws Exception {
		// TODO Auto-generated method stub
		SacTrxDetail sacTrxDetail = oriTrxDetailDao.getOriTrxDetail(filterMap);
		return sacTrxDetail;
	}

}
