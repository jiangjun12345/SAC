/**
 * 
 */
package net.easipay.cbp.service;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.model.SacCheckInfo;


/**
 * @author Administrator
 *
 */
public interface ISacCheckInfoService {
	
	  public List<SacCheckInfo> querySacCheckInfo(SacCheckInfo sacCheckInfo, int pageNo, int pageSize);
	  
	  public List<SacCheckInfo> querySacCheckInfo(Map<String,Object> queryMap);
	  
	  public int getCheckInfoCount(SacCheckInfo sacCheckInfo);
	  public SacCheckInfo selectSacCheckInfoById(Long id);
	  public void insertSacCheckInfo(SacCheckInfo sacCheckInfo);
	  public void updateSacCheckInfo(SacCheckInfo sacCheckInfo);
	  public String objectFromMap( Map map)throws Exception;
	  public Object objectFromList(List<SacCheckInfo> sacCheckInfo);
}
