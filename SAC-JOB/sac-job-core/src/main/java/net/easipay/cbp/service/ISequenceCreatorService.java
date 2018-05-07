package net.easipay.cbp.service;


/**
 * 
 * @Description: 运营系统渠道对账业务层(用一句话描述该文件做什么)
 * @author DELL (作者英文名称)
 * @date 2015-7-14 下午02:16:41
 * @version V1.0
 * @jdk v1.6
 * @tomcat v7.0
 */
public interface ISequenceCreatorService
{
	public String getSerialNo(String sequenceName,int length);
	
	public String getNextVal(String sequenceName);
}
