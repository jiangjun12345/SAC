/**
 * 
 */
package net.easipay.cbp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.RecBatchDetailDao;
import net.easipay.cbp.model.SacRecDetail;
import net.easipay.cbp.service.RecBatchDetailManager;
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
@Service("recBatchDetailManager")
public class RecBatchDetailManagerImpl extends
		GenericManagerImpl<SacRecDetail, Long> implements RecBatchDetailManager {
	@Autowired
	RecBatchDetailDao recBatchDetailDao;
	private static final Logger logger = Logger
			.getLogger(RecBatchDetailManagerImpl.class);

	@Override
	public SacRecDetail selectRecBatchDetail(Long id) throws Exception {
		// TODO Auto-generated method stub
		Map<String, String> paramBatchDeatilId = new HashMap<String, String>();
		paramBatchDeatilId.put("id", String.valueOf(id));
		SacRecDetail sacRecDetail = recBatchDetailDao
				.selectRecBatchDetail(paramBatchDeatilId);
		return sacRecDetail;
	}

	@Override
	public List<SacRecDetail> selectRecBatchDetailList(SacRecDetail sacRecDetail)
			throws Exception {
		// TODO Auto-generated method stub
		Map<String, String> paramBatchId = new HashMap<String, String>();
		Long recBatchId = sacRecDetail.getRecBatchId();
		paramBatchId.put("recBatchId", String.valueOf(recBatchId));
		List<SacRecDetail> sacRecDetaillist = recBatchDetailDao
				.selectRecBatchDetailList(paramBatchId);
		return sacRecDetaillist;
	}

}
