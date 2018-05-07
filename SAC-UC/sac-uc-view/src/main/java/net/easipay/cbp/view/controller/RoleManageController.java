package net.easipay.cbp.view.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.easipay.cbp.cas.users.SecurityOperator;
import net.easipay.cbp.model.ResourceInfo;
import net.easipay.cbp.model.UcRole;
import net.easipay.cbp.service.ResourceInfoService;
import net.easipay.cbp.service.RoleResourceService;
import net.easipay.cbp.service.RoleService;
import net.easipay.cbp.util.ConstantParams;
import net.easipay.cbp.util.StringUtil;
import net.easipay.cbp.util.Utils;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
//@SessionAttributes(value={"ucRole"})
public class RoleManageController{ 
	private static final Logger logger = Logger.getLogger(RoleManageController.class);
	@Autowired
	private ResourceInfoService resourceInfoService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private RoleResourceService roleResourceService;
   
    /** 
     * 列表显示
     * */  
    @RequestMapping(value="/roleManage", method = RequestMethod.GET) 
    public ModelAndView roleManage(HttpServletRequest request, HttpServletResponse respons)throws Exception  
    {  
    	 Model model = new ExtendedModelMap();
   
    	 String pageNoStr = Utils.trim(request.getParameter("pageNo"));
         int pageNo = Utils.parseInt(pageNoStr, 1);
         int rownum = pageNo*ConstantParams.ADMIN_PAGE_SIZE+1;
         int rn = (pageNo-1)*ConstantParams.ADMIN_PAGE_SIZE;
         Integer count = roleService.selectUcRoleTotal();
 		
         List<UcRole> ucRoleList = roleService.selectUcRoles(rownum,rn);

         model.addAttribute("ucRoleList", ucRoleList);
         model.addAttribute("pageSize", ConstantParams.ADMIN_PAGE_SIZE);
         model.addAttribute("count", count.intValue());
         model.addAttribute("pageNo", pageNo);
         return new ModelAndView("/role/roleList", model.asMap()); 
    } 
    
    @RequestMapping(value="/menuRoleTree", method = RequestMethod.GET) 
    public ModelAndView menuRoleTree(HttpServletRequest request, HttpServletResponse respons)throws Exception  
    {  
    	 Model model = new ExtendedModelMap();
        
         StringBuffer sbf = new StringBuffer();
         sbf.append("var zNodes =[ ");
         String str = "";
         String roleId = request.getParameter("roleId");
         SecurityOperator oper = (SecurityOperator)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
         String email = (oper==null)?null:oper.getEmail();
         String roleIdOper = (oper==null)?null:oper.getRole().getRoleId();
         
         logger.debug("roleId="+roleId);
         logger.debug("email="+email);
         
         if(StringUtil.isEmptyString(roleId)||StringUtil.isEmptyString(email)||StringUtil.isEmptyString(roleIdOper)) throw new Exception("roleId || email ||roleIdOper为空");

         List<ResourceInfo> resourceInfoList = resourceInfoService.selectResourceByRoleId(new Long(roleId), null, null);
         List<Long> rolecodes = new ArrayList<Long>();
         for (int i = 0; i < resourceInfoList.size(); i++) {
             rolecodes.add(resourceInfoList.get(i).getResourceId());
         }
         if(ConstantParams.SUPERADMIN.equalsIgnoreCase(email)){
             List<ResourceInfo> resourceInfoListAll = resourceInfoService.getResourceInfos();
          //   List<ResourceInfo> resourceInfoList = resourceInfoService.selectResourceByRoleId(new Long(roleId), null, null);                        
             if (resourceInfoListAll != null && resourceInfoListAll.size() > 0) {
                 for (ResourceInfo backInnerBaseResourceInfo : resourceInfoListAll) {
                	 Long resourceId = backInnerBaseResourceInfo.getResourceId();
                     String resourceName = backInnerBaseResourceInfo.getResourceName();
                     Long parentId = backInnerBaseResourceInfo.getParentId();

                     sbf.append("{id:'").append(resourceId).append("',pId:'").append(parentId).append("', name:'").append(resourceName).append("', open:true");
                     if (rolecodes.contains(resourceId)) {
                         sbf.append(",checked:true},");
                     } else {
                    		 sbf.append("},");
                     }
                 }
             }
         }else{
        		 if(roleId.equalsIgnoreCase(roleIdOper)){
        			 if (resourceInfoList != null && resourceInfoList.size() > 0) {
                         for (ResourceInfo backInnerBaseResourceInfo : resourceInfoList) {
                        	 Long resourceId = backInnerBaseResourceInfo.getResourceId();
                             String resourceName = backInnerBaseResourceInfo.getResourceName();
                             Long parentId = backInnerBaseResourceInfo.getParentId();
                             sbf.append("{id:'").append(resourceId).append("',pId:'").append(parentId).append("', name:'").append(resourceName).append("', open:true").append(",checked:true,doCheck:false},");
                         }
                     }
        		 }else{
        			 List<ResourceInfo> resourceInfoListOper = resourceInfoService.selectResourceByRoleId(new Long(roleIdOper), null, null);
        			  if (resourceInfoListOper != null && resourceInfoListOper.size() > 0) {
        	                 for (ResourceInfo backInnerBaseResourceInfo : resourceInfoListOper) {
        	                	 Long resourceId = backInnerBaseResourceInfo.getResourceId();
        	                     String resourceName = backInnerBaseResourceInfo.getResourceName();
        	                     Long parentId = backInnerBaseResourceInfo.getParentId();

        	                     sbf.append("{id:'").append(resourceId).append("',pId:'").append(parentId).append("', name:'").append(resourceName).append("', open:true");
        	                     if (rolecodes.contains(resourceId)) {
        	                         sbf.append(",checked:true},");
        	                     } else {
        	                         sbf.append("},");
        	                     }
        	                 }
        	             }
        		 }
        		 
        		 
        	 }
/*         
         //将已有的菜单以及权限拼成树结构
         List<ResourceInfo> resourceInfoListAll = resourceInfoService.getResourceInfos();
         List<ResourceInfo> resourceInfoList = resourceInfoService.selectResourceByRoleId(new Long(roleId), null, null);
         
         List<Long> rolecodes = new ArrayList<Long>();
         for (int i = 0; i < resourceInfoList.size(); i++) {
             rolecodes.add(resourceInfoList.get(i).getResourceId());
         }
         
         StringBuffer sbf = new StringBuffer();
         sbf.append("var zNodes =[ ");
         String str = "";
         if (resourceInfoListAll != null && resourceInfoListAll.size() > 0) {
             for (ResourceInfo backInnerBaseResourceInfo : resourceInfoListAll) {
            	 Long resourceId = backInnerBaseResourceInfo.getResourceId();
                 String resourceName = backInnerBaseResourceInfo.getResourceName();
                 Long parentId = backInnerBaseResourceInfo.getParentId();

                 sbf.append("{id:'").append(resourceId).append("',pId:'").append(parentId).append("', name:'").append(resourceName).append("', open:true");
                 if (rolecodes.contains(resourceId)) {
                     sbf.append(",checked:true},");
                 } else {
                	 if(!StringUtil.isEmptyString(email) && "bc@qq.com".equalsIgnoreCase(email)){
                		 sbf.append("},");
                	 }else{
                		 sbf.append(",doCheck:false},");
                	 }
                     
                 }

             }*/

         str = sbf.substring(0, sbf.length() - 1);
         str += "];";
         model.addAttribute("TreeMenu", str);
         model.addAttribute("roleId", roleId);
         
         return new ModelAndView("/role/menuRoleTree", model.asMap()); 
    }  
    
    @RequestMapping(value="/saveMenuRole", method = RequestMethod.POST) 
    public void saveMenuRole(HttpServletRequest request, HttpServletResponse respons)throws Exception {
   	   // Model model = new ExtendedModelMap();
    	String data = "true";
    	try{ 
    	 String str = request.getParameter("str");
    	 String roleId = request.getParameter("roleId");
    	if(!StringUtil.isEmptyString(roleId)){
    	   roleResourceService.saveUcRoleIdResourceId(Long.valueOf(roleId),str);
    	}else{
    		data = "角色Id为空,请重新登录后操作!";
    	}
        } catch (Exception e) {
        	data = "操作失败,请重新操作!";
        	logger.info("data="+"处理异常：" + e.getMessage());
       }
        
        respons.setContentType("text/Xml;charset=gbk");   
        PrintWriter out = null;
        try {   
            out = respons.getWriter();
          //  String json="{\"num\":"+num+"}";//拼成Json格式字符串
            out.println(data);         
        }   
        catch (IOException ex1) {   
            ex1.printStackTrace();   
        }finally{   
            out.close();   
        }
    }
    
    @RequestMapping(value="/roleCreateInit", method = RequestMethod.GET) 
    public ModelAndView roleCreateInit(HttpServletRequest request, HttpServletResponse respons)throws Exception  
    {  
    	 Model model = new ExtendedModelMap();
         model.addAttribute("ucRole", new UcRole());
         return new ModelAndView("/role/roleCreateInit", model.asMap()); 
    } 
    
    @RequestMapping(value="/roleCreate", method = RequestMethod.POST) 
    public ModelAndView roleCreate(HttpServletRequest request, HttpServletResponse respons,@Valid UcRole ucRole, BindingResult errors)throws Exception  
    {  
    	 Model model = new ExtendedModelMap();
         if(errors.hasErrors()){
        	 return new ModelAndView("/role/roleCreateInit", model.asMap());  
         }
         SecurityOperator oper = (SecurityOperator)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
         String create_user_id = (oper==null)?null:oper.getOperId();
         logger.debug("create_user_id="+create_user_id);
         ucRole.setCreateUserId(Long.valueOf((StringUtil.isEmptyString(create_user_id)==true)?"0":create_user_id));
         roleService.insertUcRole(ucRole);
         return new ModelAndView("redirect:/forwardSuccess", model.asMap());
    } 
    
    @RequestMapping(value="/roleEditInit", method = RequestMethod.GET) 
    public ModelAndView roleEditInit(HttpServletRequest request, HttpServletResponse respons)throws Exception  
    {  
    	 Model model = new ExtendedModelMap();
         String id = request.getParameter("id");
         UcRole ucRole = roleService.selectUcRolesById(new Long(id));
         model.addAttribute("ucRole", ucRole);
         
         return new ModelAndView("/role/roleEditInit", model.asMap()); 
    } 
    
    @RequestMapping(value="/roleEdit", method = RequestMethod.POST) 
    public ModelAndView roleEdit(HttpServletRequest request, HttpServletResponse respons,@Valid UcRole ucRole, BindingResult errors)throws Exception  
    {  
    	 Model model = new ExtendedModelMap();
         if(errors.hasErrors()){
        	 return new ModelAndView("/role/roleCreateInit", model.asMap()); 
         }
         roleService.updateUcRole(ucRole);
         return new ModelAndView("redirect:/forwardSuccess", model.asMap());
    } 

}
