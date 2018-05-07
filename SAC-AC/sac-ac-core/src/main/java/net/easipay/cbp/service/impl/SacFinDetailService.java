package net.easipay.cbp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.easipay.cbp.AcGenerator;
import net.easipay.cbp.form.FinMxForm;
import net.easipay.cbp.form.SacFinDetailQueryForm;
import net.easipay.cbp.service.ISacFinDetailService;
import net.easipay.dsfc.ws.jws.JwsClient;
import net.easipay.dsfc.ws.jws.JwsResult;

import org.springframework.stereotype.Service;

/**
 * 1.初始化fincode 新用户 保存fincode3 fincode finYeDao
 * 
 * 2.保存凭证
 * 
 * 3.更新余额，添加余额明细
 * 
 * 
 * @author jjiang
 * @date 2015-12-14
 */

@Service("sacFinDetailService")
public class SacFinDetailService implements ISacFinDetailService
{

	@Override
	public Map<String,Object> queryFinDetail(SacFinDetailQueryForm form) {

		String cusType = form.getCusType();
		String orgCode = form.getOrgCode();
		String cusNodeCode = AcGenerator.generateCusCode(cusType, orgCode);
		StringBuffer bf = new StringBuffer("220203");
		bf.append(cusNodeCode)
		.append(form.getCurrency());
		if(!"000".equals(form.getBussType())){
			bf.append(form.getBussType());
		}
		
		JwsResult result = new JwsResult();
		Map<String,Object> params = new HashMap<String, Object>();
	    params.put("finCode", bf.toString());
		params.put("beginDate", form.getBeginDate());
		params.put("endDate", form.getEndDate());
		params.put("start", form.getStart());
		params.put("end", form.getEnd());
		params.put("isShow", "1");//显示
	    
		Map<String,Object> rtnMap = new HashMap<String, Object>();
		JwsClient jc = new JwsClient("SAC-FA-0001");
		result = jc.putParam(params).call();
		if(result.isSuccess()){
			List<FinMxForm> list = result.getList("finMxList",FinMxForm.class);
			rtnMap.put("mxList",list );
			rtnMap.put("totalCount",result.getIntValue("totalCount") );
		}
		return rtnMap;
	}
}
