package net.easipay.cbp.view.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.easipay.cbp.cas.users.SecurityOperator;
import net.easipay.cbp.constant.Constants;
import net.easipay.cbp.model.UcOperator;
import net.easipay.cbp.util.ConstantParams;
import net.easipay.cbp.util.MD5Util;
import net.easipay.cbp.util.StringUtil;
import net.easipay.dsfc.ws.jws.JwsClient;
import net.easipay.dsfc.ws.jws.JwsResult;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
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
	

    
    
    /** 
     * 列表显示
     * */  
    @RequestMapping(value="/index", method = RequestMethod.GET) 
    public ModelAndView index(HttpServletRequest request, HttpServletResponse respons)throws Exception  
    {  
    	 Model model = new ExtendedModelMap();
    	 
    	/* String prjectName = request.getContextPath().substring(1); 
    	 SecurityOperator oper = (SecurityOperator)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	 List<Menu> listMe1 = (oper != null)?(oper.getProjectName()):null;
    	 List<Menu> listMe =new ArrayList<Menu>();
    	 for(Menu menu:listMe1){
    		 if(menu!=null && !StringUtil.isEmptyString(menu.getResourceName()) && !prjectName.equalsIgnoreCase(menu.getResourceName())){
    			 listMe.add(menu);
    		 }
    	 }
    	 request.getSession().setAttribute("listMe", listMe);*/
    	// model.addAttribute("listMe", listMe);

         return new ModelAndView("/index", model.asMap()); 
    }  
    
    @RequestMapping(value="/changePwd", method = RequestMethod.POST) 
    public void changePwd(HttpServletRequest request, HttpServletResponse respons)throws Exception  
    {  
    	
    	
    	String opwd = request.getParameter("opwd");
		String npwd = request.getParameter("npwd");
		
		if(!StringUtil.isEmptyString(opwd) && !StringUtil.isEmptyString(npwd)){
		SecurityOperator oper = (SecurityOperator)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(oper !=null && !StringUtil.isEmptyString(oper.getEmail())){
			JwsClient client = new JwsClient(Constants.SELECT_OPERATOR);
			client.putParam("email", oper.getEmail());
			client.putParam("pass",MD5Util.getMD5By32bit(opwd, ConstantParams.CODE_FORMAT_UTF8));
			JwsResult jwsResult = client.call();
			UcOperator ucOperator = jwsResult.getBean("ucOperator", UcOperator.class);
			if(ucOperator != null){
				JwsClient client1 = new JwsClient(Constants.CHANGE_PWD);
				ucOperator.setPassword(MD5Util.getMD5By32bit(npwd, ConstantParams.CODE_FORMAT_UTF8));
				client1.putParam("ucOperator", ucOperator);
				JwsResult jwsResult1 = client1.call();
				boolean flag = jwsResult1.isSuccess();
				if(flag){
					respons.getWriter().write("{\"success\":true}");	
				}
			}else{
		           respons.getWriter().write("{\"msg\":\"所填信息错误\"}");
			}

		}else{
	           respons.getWriter().write("{\"msg\":\"参数为空\"}");
		}

		}else{
			 respons.getWriter().write("{\"msg\":\"获取密码有误\"}");
		}	
    }  
    
  
    
}
