package net.easipay.cbp.service;


/**
* @Description: 退款指令job服务层
* @author sydai  
* @date 2016-3-10 上午09:28:10
* @version V1.0  
* @jdk v1.6
* @tomcat v7.0
 */
public interface ISacRefundCmdService {
	
	//插入B2B退款指令表
	public void insertB2BRefundCmd();
	
	//插入B2C退款指令表
	public void insertB2CRefundCmd();
	
	//更新B2B退款指令表
	public void updateB2BRefundCmd();
}
