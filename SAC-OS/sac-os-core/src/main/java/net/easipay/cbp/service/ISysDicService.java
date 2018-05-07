/**
 * 
 */
package net.easipay.cbp.service;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.model.SacSysDic;


/**
 * @author Administrator
 *
 */
public interface ISysDicService {

	public List<SacSysDic> selectSysDic(SacSysDic sysDic);
	
	public Map<String,Object> getChileAccTypeMap();
	
	public Map<String,Object> getCode1Map();
	
	public Map<String,Object> getCode2Map();
}
