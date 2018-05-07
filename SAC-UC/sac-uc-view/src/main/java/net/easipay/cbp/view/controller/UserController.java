package net.easipay.cbp.view.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.easipay.cbp.model.SysDic;
import net.easipay.cbp.model.UcUser;
import net.easipay.cbp.service.SysDicService;
import net.easipay.cbp.service.UcUserService;
import net.easipay.cbp.util.ConstantParams;
import net.easipay.cbp.util.MD5Util;
import net.easipay.cbp.util.StringUtil;
import net.easipay.cbp.util.Utils;
import net.easipay.dsfc.UnifiedConfig;
import net.easipay.dsfc.UnifiedConfigSimple;
import net.easipay.dsfc.ws.jws.JwsHttpRequest;
import net.easipay.dsfc.ws.jws.JwsHttpResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
public class UserController { 
	private static final Logger logger = Logger.getLogger(UserController.class);
	@Autowired
	private UcUserService ucUserService;
	
	@Autowired
	private SysDicService sysDicService;
	
	
    private static List<UnifiedConfig> listUnifiedConfigUserStatus;
	
	public static void initListUnifiedConfig(){
		listUnifiedConfigUserStatus = UnifiedConfigSimple.getDicTypeConfig(ConstantParams.DIC_USER_STATUS); 
	}
   
	/**
	 * 用户管理初始化页面
	 * @param request
	 * @param response
	 * @param ucUser
	 * @return
	 */
	@RequestMapping(value="/userManageInit",method=RequestMethod.GET)
	public ModelAndView userManageInit(HttpServletRequest request,HttpServletResponse response, UcUser ucUser){
		
		Model model = new ExtendedModelMap();
		
		String pageNoStr = Utils.trim(request.getParameter("pageNo"));
		
        int pageNo = Utils.parseInt(pageNoStr, 1);
        
        Integer count = ucUserService.selectUcUserCountsByParameter(ucUser);
        
        int startNo = (pageNo-1)*ConstantParams.ADMIN_PAGE_SIZE;
        
        int endNo = pageNo*ConstantParams.ADMIN_PAGE_SIZE+1;
        
		List<UcUser> ucUserInfoList = ucUserService.selectUcUserByParameter(ucUser, startNo, endNo);
		
		model.addAttribute("pageSize", ConstantParams.ADMIN_PAGE_SIZE);
		
        model.addAttribute("count", count.intValue());
        
        model.addAttribute("pageNo", pageNo);
        
		model.addAttribute("ucUserInfoList",ucUserInfoList);
		
		return new ModelAndView("/userInfo/userManageInit",model.asMap());
	}
	
	/**
	 * 根据参数查询用户信息
	 * @param request
	 * @param response
	 * @param ucUser
	 * @return
	 */
	@RequestMapping(value="/selectUcUserByParamter",method=RequestMethod.POST)
	public ModelAndView selectUcUserByParamter(HttpServletRequest request,HttpServletResponse response, UcUser ucUser){
		
		Model model = new ExtendedModelMap();
		
		String pageNoStr = Utils.trim(request.getParameter("pageNo"));
		
        int pageNo = Utils.parseInt(pageNoStr, 1);
        
        Integer count = ucUserService.selectUcUserCountsByParameter(ucUser);
        
        int startNo = (pageNo-1)*ConstantParams.ADMIN_PAGE_SIZE;
        
        int endNo = pageNo*ConstantParams.ADMIN_PAGE_SIZE+1;
        
		List<UcUser> ucUserInfoList = ucUserService.selectUcUserByParameter(ucUser, startNo, endNo);
		
		model.addAttribute("pageSize", ConstantParams.ADMIN_PAGE_SIZE);
		
        model.addAttribute("count", count.intValue());
        
        model.addAttribute("pageNo", pageNo);
        
		model.addAttribute("ucUserInfoList",ucUserInfoList);
		
		return new ModelAndView("/userInfo/userManageInit",model.asMap());
	}
	
	/**
	 * 修改用户初始化页面
	 * @param request
	 * @param respons
	 * @param ucOperator
	 * @param errors
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/editUserInfoInit", method = RequestMethod.GET)
	public ModelAndView editUserInfoInit(HttpServletRequest request,
			HttpServletResponse respons,UcUser ucUser) throws Exception {
		
		Model model = new ExtendedModelMap();
		
		UcUser user = ucUserService.selectUcUserById(ucUser.getId());
		
		initListUnifiedConfig();
		
		model.addAttribute("sysDicStatusList", listUnifiedConfigUserStatus);
		
		model.addAttribute("ucUser", user);
		
		return new ModelAndView("/userInfo/editUserInfoInit", model.asMap());
	}
	
	/**
	 * 修改用户
	 * @param request
	 * @param respons
	 * @param ucOperator
	 * @param errors
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/editUserInfo", method = RequestMethod.POST)
	public ModelAndView editUserInfo(HttpServletRequest request,
			HttpServletResponse respons,@Valid UcUser ucUser,BindingResult errors) throws Exception {
		
		Model model = new ExtendedModelMap();
		
		if (errors.hasErrors()) {
			
			List<ObjectError> errorList = errors.getAllErrors();
			
			for (ObjectError objectError : errorList) {
				
				logger.debug("objectError=" + objectError.toString());
				
			}
			
			model.addAttribute("ucUser", ucUser);
			
			initListUnifiedConfig();
			
			model.addAttribute("sysDicStatusList", listUnifiedConfigUserStatus);
			

			return new ModelAndView("/userInfo/editUserInfoInit", model.asMap());
			
		}
		
		ucUserService.updateUcUser(ucUser);
		return new ModelAndView("redirect:/forwardSuccess", model.asMap());
	}
	
	
	/**
	 * 重置密码
	 * @param request
	 * @param respons
	 * @param ucOperator
	 * @param errors
	 * @return
	 * @throws IOException 
	 * @throws Exception
	 */
	@RequestMapping(value = "/resetUserPassword", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> resetPassword(HttpServletRequest request,
			HttpServletResponse respons, UcUser ucUser) throws IOException{
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		ucUser.setQueryPassword(MD5Util.getMD5By32bit(ConstantParams.DEFAULT_PASSWORD, ConstantParams.CODE_FORMAT_UTF8));
        
        ucUserService.updateUcUser(ucUser);
		
        map.put("success", true);
        
       	return map;
        
	}
	
	@RequestMapping(value = "/lockUser",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> lockUser(HttpServletRequest request,
			HttpServletResponse respons, UcUser ucUser) throws IOException{
		
		String personState = ucUser.getPersonState();
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		if("Y".equals(personState)){
			
			ucUser.setPersonState("N"); 
			
		}else if("N".equals(personState)){
			
			ucUser.setPersonState("F"); 
			
		}else if("F".equals(personState)){
			
			ucUser.setPersonState("Y");
			
		}else{
			return null;
		}
		
        ucUserService.updateUcUser(ucUser);
        
        ucUser = ucUserService.selectUcUserById(ucUser.getId());
		
        map.put("success", true);
        
        map.put("ucUser", ucUser);
        
       	return map;
        
	}
	
	@RequestMapping(value = "/checkModify", method = RequestMethod.POST)
	@ResponseBody
	public void checkModify(HttpServletRequest request,
		HttpServletResponse respons) throws IOException{
		String email = request.getParameter("email");
		String mobile = request.getParameter("mobile");
		UcUser ucUser = new UcUser();
		if(!StringUtil.isEmptyString(email)){
			ucUser.setEmail(email);
			int counts = ucUserService.selectUcUserCountsForValidate(ucUser);
		    if(counts==0){
	            respons.getWriter().write("{\"email\":true}");
		    }else{
			  respons.getWriter().write("{\"email\":false}"); 
		    }
		}
		if(!StringUtil.isEmptyString(mobile)){
			ucUser.setEmail(null);
			ucUser.setMobile(mobile);
			int counts = ucUserService.selectUcUserCountsForValidate(ucUser);
		    if(counts==0){
	            respons.getWriter().write("{\"mobile\":true}");
		    }else{
			  respons.getWriter().write("{\"mobile\":false}"); 
		    }
		}
        
	}
	

}
