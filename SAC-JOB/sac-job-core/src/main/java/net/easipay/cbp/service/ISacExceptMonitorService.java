package net.easipay.cbp.service;

import net.easipay.cbp.model.SacExceptMonitor;

/**
 * 
 * @Description: 异常监控定时录入任务服务层
 * @author xuying
 * @date 2016-6-27 下午01:29:15
 * @version V1.0
 * @jdk v1.6
 */
public interface ISacExceptMonitorService {
	public void registerExceptDetail();
}
