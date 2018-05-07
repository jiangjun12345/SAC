package net.easipay.cbp.view.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.easipay.cbp.cas.auth.Menu;
import net.easipay.cbp.cas.auth.UserAuthoritiesMenu;
import net.easipay.cbp.model.ResourceInfo;
import net.easipay.cbp.model.SysDic;
import net.easipay.cbp.model.UcRole;
import net.easipay.cbp.service.OperatorService;
import net.easipay.cbp.service.ResourceInfoService;
import net.easipay.cbp.service.RoleService;
import net.easipay.cbp.service.SysDicService;
import net.easipay.cbp.util.ConstantParams;
import net.easipay.cbp.util.StringUtil;
import net.easipay.dsfc.UnifiedConfig;
import net.easipay.dsfc.UnifiedConfigSimple;
import net.easipay.dsfc.ws.jws.JwsHttpRequest;
import net.easipay.dsfc.ws.jws.JwsHttpResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;





/**
  *                           RESTFUL STYLE
  *  /users           	HTTP GET		=> index()    		列表或分页显示
  *  /user/editNew      HTTP GET		=> _new()     		进入新增页面
  *  /user/{id}      	HTTP GET		=> show()     		根据ID号显示
  *  /user/{id}/edit 	HTTP GET  		=> edit()     		根据ID号进入更新页面
  *  /user       		HTTP POST 		=> create()   		新增实体
  *  /user       		HTTP PUT 		=> update()   		更新实体
  *  /user/{id}  		HTTP DELETE  	=> delete()   		根据ID号删除实体
  *  /users       		HTTP DELETE  	=> batchDelete()  	批量删除实体
 **/
@Controller
public class ResourceInfoController{ 
	private static final Logger logger = Logger.getLogger(ResourceInfoController.class);
	@Autowired
	private ResourceInfoService resourceInfoService;
	@Autowired
	private SysDicService sysDicService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private OperatorService operatorService;
	
	
	private static List<UnifiedConfig> listUnifiedConfigTypeSys;
	
	private static List<UnifiedConfig> listUnifiedConfigTypeMenu;
	
	public static void initListUnifiedConfig(){
		listUnifiedConfigTypeSys = UnifiedConfigSimple.getDicTypeConfig(ConstantParams.DIC_TYPE_SYS); 
		listUnifiedConfigTypeMenu = UnifiedConfigSimple.getDicTypeConfig(ConstantParams.DIC_TYPE_MENU);
	}
	

    
    
    /** 
     * 列表显示
     * */  
    @RequestMapping(value="/index", method = RequestMethod.GET) 
    public ModelAndView index(HttpServletRequest request, HttpServletResponse respons)throws Exception  
    {  
    	 Model model = new ExtendedModelMap();
         
         return new ModelAndView("/index", model.asMap()); 
    }  
    
    /** 
     * 列表显示
     * */  
    @RequestMapping(value="/resourceManage", method = RequestMethod.GET) 
    public ModelAndView resourceManage(HttpServletRequest request, HttpServletResponse respons)throws Exception  
    {  
    	 Model model = new ExtendedModelMap();
        
         //查询所有菜单以及权限列表
        List<ResourceInfo> resourceList = resourceInfoService.getResourceInfos();
        
        //将list转化成所需菜单
         StringBuffer sbf = new StringBuffer();
         sbf.append("var zNodes =[ ");
         String str = "";
         if (resourceList != null && resourceList.size() > 0) {
             for (ResourceInfo resourceInfo : resourceList) {
               //  String dicCode = resourceInfo.getDicCode();
                 Long resourceId = resourceInfo.getResourceId();
                 String resourceName = resourceInfo.getResourceName();
                 Long parentId = resourceInfo.getParentId();


                 sbf.append("{id:'").append(resourceId).append("',pId:'").append(parentId).append("', name:'").append(resourceName).append("', open:true").append("},");

             }

             str = sbf.substring(0, sbf.length() - 1);
         }
         str += "];";
         model.addAttribute("TreeMenu", str);
         
         logger.debug("str="+str);
        
         return new ModelAndView("/resourceList", model.asMap()); 
    } 
    /** 
     * 列表显示
     * */  
    @RequestMapping(value="/addNodeInit", method = RequestMethod.GET) 
    public ModelAndView addNodeInit(HttpServletRequest request, HttpServletResponse respons)throws Exception  
    {  
    	 Model model = new ExtendedModelMap();
         
         //获得节点ID
         String id = request.getParameter("id");
         
         ResourceInfo resourceInfo = resourceInfoService.getResourceInfosById(new Long(id));
         initListUnifiedConfig();
         model.addAttribute("resourceInfo", new ResourceInfo());
         model.addAttribute("sysDicSysList", listUnifiedConfigTypeSys);
         model.addAttribute("sysDicMenuList", listUnifiedConfigTypeMenu);
         model.addAttribute("resourceInfoFa", resourceInfo);
        
         return new ModelAndView("/addNodeInit", model.asMap()); 
    }
    
    /** 
     * 列表显示
     * */  
    @RequestMapping(value="/addNode", method = RequestMethod.POST) 
    public ModelAndView addNode(HttpServletRequest request, HttpServletResponse respons,@Valid ResourceInfo resourceInfo, BindingResult errors)throws Exception  
    {  
    	 Model model = new ExtendedModelMap();
    	 
    	 String resourceId = request.getParameter("resourceId");
    	 if(errors.hasErrors()){
         
         ResourceInfo resourceInfoFa = resourceInfoService.getResourceInfosById(new Long(resourceId));
         
    	 logger.info("resourceInfo.getResourceId()="+resourceInfo.getResourceId());
    	 logger.info("getResourceName()="+resourceInfo.getResourceName());
    	 initListUnifiedConfig();
         model.addAttribute("resourceInfo", resourceInfo);
         model.addAttribute("sysDicSysList", listUnifiedConfigTypeSys);
         model.addAttribute("sysDicMenuList", listUnifiedConfigTypeMenu);
         model.addAttribute("resourceInfoFa", resourceInfoFa);
    	
         return new ModelAndView("/addNodeInit", model.asMap()); 
    	 }

         model.addAttribute("id", resourceId);
         
        
         
         //将父节点的ID作为子节点的ParentId
         resourceInfo.setParentId(new Long(resourceId));
         //将节点置为有效 1
         resourceInfo.setValidFlag(ConstantParams.RES_VALID_FLAG);         
         resourceInfoService.insertResourceInfos(resourceInfo);
         return new ModelAndView("redirect:/forwardSuccess", model.asMap());
    }
    
    @RequestMapping(value="/updateNodeInit", method = RequestMethod.GET) 
    public ModelAndView updateNodeInit(HttpServletRequest request, HttpServletResponse respons)throws Exception  
    {  
    	 Model model = new ExtendedModelMap();
   
         String id = request.getParameter("id");
         
         initListUnifiedConfig();
         model.addAttribute("sysDicSysList", listUnifiedConfigTypeSys);
         model.addAttribute("sysDicMenuList", listUnifiedConfigTypeMenu);
         
         ResourceInfo resourceInfo = null;
         
         if(!StringUtil.isEmptyString(id)){
        	 resourceInfo = resourceInfoService.getResourceInfosById(new Long(id));
         }
         
         model.addAttribute("resourceInfo", resourceInfo);
       //  model.addAttribute("id", id);
        
         return new ModelAndView("/updateNodeInit", model.asMap()); 
    }
    
    @RequestMapping(value="/updateNode", method = RequestMethod.POST) 
    public ModelAndView updateNode(HttpServletRequest request, HttpServletResponse respons,@Valid ResourceInfo resourceInfo, BindingResult errors)throws Exception  
    {  
    	 Model model = new ExtendedModelMap();
         
    	 logger.info("resourceInfo.getResourceId()="+resourceInfo.getResourceId());
    	 logger.info("getResourceName()="+resourceInfo.getResourceName());
         
    	 initListUnifiedConfig();
         model.addAttribute("sysDicSysList", listUnifiedConfigTypeSys);
         model.addAttribute("sysDicMenuList", listUnifiedConfigTypeMenu);
         
         if(errors.hasErrors()){
        	 return new ModelAndView("/updateNodeInit", model.asMap()); 
         }
         
         resourceInfoService.updateResourceInfo(resourceInfo);
         return new ModelAndView("redirect:/forwardSuccess", model.asMap());
    }
    
    /*
     * 将List转化成Json
     */
    public JSONObject listTransJson(List<ResourceInfo> menuList,List<ResourceInfo> authList,UcRole role,List<Menu> projectName){
    	
    	   //将菜单拼成json;
	         JsonConfig jsonConfigMenu = new JsonConfig();  
	        // jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());	         	         
	         jsonConfigMenu.setIgnoreDefaultExcludes(false);  //设置默认忽略
	         jsonConfigMenu.setExcludes(new String[]{"resourceId","resourceCode","dicCode","parentId","validFlag","orderNum","createUser","memo", "updateTime","updateUser","createTime"}); 	         
	         JSONArray jsobjMenu = JSONArray.fromObject(menuList,jsonConfigMenu); 
	         
	       //将权限拼成json;      
	         JsonConfig jsonConfigAuth = new JsonConfig();  
	         jsonConfigAuth.setIgnoreDefaultExcludes(false);  //设置默认忽略
	         jsonConfigAuth.setExcludes(new String[]{"resourceId","dicCode","parentId","validFlag","orderNum","createUser","memo", "updateTime","updateUser","createTime","resourceType","childs"}); 
		         
	         JSONArray jsobjAuth = JSONArray.fromObject(authList,jsonConfigAuth);
	         //将角色拼成json;       
	         JsonConfig jsonConfigRole = new JsonConfig();  
	         jsonConfigRole.setIgnoreDefaultExcludes(false);  //设置默认忽略
	         jsonConfigRole.setExcludes(new String[]{"id","status","description","createUserId","createTime","updateUserId","memo", "updateTime"});    
	     
	         JSONObject jsobjRole = JSONObject.fromObject(role,jsonConfigRole);
	         
	       //将projectName拼成json;      
	         JsonConfig jsonConfigProject = new JsonConfig();  
	         jsonConfigProject.setIgnoreDefaultExcludes(false);  //设置默认忽略
	         jsonConfigProject.setExcludes(new String[]{"childs"}); 
		         
	         JSONArray jsobjProjectName = JSONArray.fromObject(projectName,jsonConfigProject);
	         
	         //将各个json合并成一个总得json
	         Map<String,Object> map =new HashMap<String,Object>();
	         map.put("menu", jsobjMenu);
	         map.put("authorities", jsobjAuth);
	         map.put("role", jsobjRole);
	         map.put("projectName", jsobjProjectName);
	     
    	logger.debug("map="+map.toString());
    	return JSONObject.fromObject(map);
    }
    //结果集处理
    
    
    @RequestMapping(value="/resourceCodeCheck",method = RequestMethod.POST) 
    public void resourceCodeCheck(HttpServletRequest request, HttpServletResponse response) throws Exception {	
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("result", true);
    	String resourceCode = request.getParameter("resourceCode");
    	String resourceName = request.getParameter("resourceName");
    	JSONObject json = null;
    	ResourceInfo resourceInfo = new ResourceInfo();
    	if(!StringUtil.isEmptyString(resourceCode) && !StringUtil.isEmptyString(resourceName)){
    		resourceInfo.setResourceCode(resourceCode);
    		resourceInfo.setResourceName(resourceName);
    		Integer integer = 0;
    		integer = resourceInfoService.selectResourceInfoCountByParam(resourceInfo);
    		if(integer > 0){
    			map.put("result", false);
    		}
    		
    	}
    	
    	json = JSONObject.fromObject(map);

 	    response.getWriter().write(json.toString());
    }
    
}
