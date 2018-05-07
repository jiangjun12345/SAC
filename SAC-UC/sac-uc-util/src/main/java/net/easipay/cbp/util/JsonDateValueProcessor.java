package net.easipay.cbp.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.easipay.cbp.model.UcOrgInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class JsonDateValueProcessor implements JsonValueProcessor {  
    private String format ="yyyyMMddHHmmss";  
      
    public JsonDateValueProcessor() {  
        super();  
    }  
      
    public JsonDateValueProcessor(String format) {  
        super();  
        this.format = format;  
    }  
  
    @Override  
    public Object processArrayValue(Object paramObject,  
            JsonConfig paramJsonConfig) {  
        return process(paramObject);  
    }  
  
    @Override  
    public Object processObjectValue(String paramString, Object paramObject,  
            JsonConfig paramJsonConfig) {  
        return process(paramObject);  
    }  
      
      
    private Object process(Object value){  
        if(value instanceof Date){    
            SimpleDateFormat sdf = new SimpleDateFormat(format,Locale.CHINA);    
            return sdf.format(value);  
        }    
        return value == null ? "" : value.toString();    
    }  
    
    public JSONObject TransToJson(Object object){
        
        //将角色拼成json;       
        JsonConfig jsonConfigOper = new JsonConfig(); 
        jsonConfigOper.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());	
       // jsonConfigRole.setIgnoreDefaultExcludes(false);  //设置默认忽略
       // jsonConfigRole.setExcludes(new String[]{"id","status","description","createUserId","createTime","updateUserId","memo", "updateTime"});    
        JSONObject jsobjOper = JSONObject.fromObject(object,jsonConfigOper);
        //将各个json合并成一个总得json
      //  Map<String,Object> map =new HashMap<String,Object>();
       // map.put("menu", jsobjOper);

      return jsobjOper;
    }
   public String ListTransToJson(List<UcOrgInfo> list){
        
        //将角色拼成json;       
        JsonConfig jsonConfigOper = new JsonConfig(); 
        JSONArray json = new JSONArray();
        jsonConfigOper.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());	
       // jsonConfigRole.setIgnoreDefaultExcludes(false);  //设置默认忽略
       // jsonConfigRole.setExcludes(new String[]{"id","status","description","createUserId","createTime","updateUserId","memo", "updateTime"});    
        for(UcOrgInfo object :list){
        	JSONObject jsobjOper = JSONObject.fromObject(object,jsonConfigOper);
        	json.add(jsobjOper);
        }
        //将各个json合并成一个总得json
      //  Map<String,Object> map =new HashMap<String,Object>();
       // map.put("menu", jsobjOper);

      return json.toString();
   }
  
}  
