package net.easipay.cbp.service;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.model.SacChargeApply;


/**
 * 
* ClassName: ISacChargeApplyService <br/> 
* Function: TODO ADD FUNCTION. <br/> 
* Reason: TODO ADD REASON(可选). <br/> 
* date: 2016年2月26日 下午5:01:03 <br/> 
* 
* @author Administrator 
* @version  
* @since JDK 1.6
 */
public interface ISacChargeApplyService
{

	public List<SacChargeApply> selectApplyByParam(Map<String,Object> queryMap);

	public void updateChargeApply(SacChargeApply apply);

	public Integer selectApplyCountsByParam(Map<String, Object> queryMap);

	public List<SacChargeApply> selectApplyByPaging(Map<String, Object> queryMap,int pageNo,int pageSize);

	

}
