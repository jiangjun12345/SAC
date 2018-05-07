package net.easipay.cbp.util;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;



public class ResultToJsonUtil {

	public static JSONObject StringToJson(String rtnCode,String rtnDesc,Object result)
	  {
		Map<String,Object> map =new HashMap<String,Object>();
		map.put("rtnCode", rtnCode);
        map.put("rtnDesc", rtnDesc);
        map.put("result", result);
        return JSONObject.fromObject(map);
	  }

}
