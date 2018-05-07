package net.easipay.cbp.view.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.easipay.cbp.cas.users.SecurityOperator;
import net.easipay.cbp.model.SysDic;
import net.easipay.cbp.model.UcOperator;
import net.easipay.cbp.model.UcRole;
import net.easipay.cbp.service.OperatorService;
import net.easipay.cbp.service.RoleService;
import net.easipay.cbp.util.ConstantParams;
import net.easipay.cbp.util.MD5Util;
import net.easipay.cbp.util.StringUtil;
import net.easipay.cbp.util.Utils;
import net.easipay.dsfc.UnifiedConfig;
import net.easipay.dsfc.UnifiedConfigSimple;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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

 **/
@Controller
public class OperatorController {
	private static final Logger logger = Logger.getLogger(OperatorController.class);
	@Autowired
	private OperatorService operatorService;
	
	@Autowired
	private RoleService roleService;
	
	private static List<UnifiedConfig> listUnifiedConfigOper;
	
	private static List<UnifiedConfig> listUnifiedConfigStatus;
	
	private static String create_user_id = null;
	
	private static String email = null;
	
	public static void initListUnifiedConfig(){
		listUnifiedConfigOper = UnifiedConfigSimple.getDicTypeConfig(ConstantParams.DIC_OPERATOR_TYPE); 
		listUnifiedConfigStatus = UnifiedConfigSimple.getDicTypeConfig(ConstantParams.DIC_OPERATOR_STATUS);
		
		SecurityOperator oper = (SecurityOperator)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        create_user_id = (oper==null)?null:oper.getOperId();
        email = (oper==null)?null:oper.getEmail();
       
        logger.debug("create_user_id="+create_user_id);
       // ucRole.setCreateUserId(Long.valueOf((StringUtil.isEmptyString(create_user_id)==true)?"0":create_user_id));
	}

	/**
	 * 操作员管理
	 * @param request
	 * @param respons
	 * @param ucOperator
	 * @param errors
	 * @return
	 */
	@RequestMapping(value = "/operManageInit", method = RequestMethod.GET)
	public ModelAndView operManageInit(HttpServletRequest request,
			HttpServletResponse respons, @Valid UcOperator ucOperator,
			BindingResult errors){
		
		Model model = new ExtendedModelMap();
		
		String pageNoStr = Utils.trim(request.getParameter("pageNo"));
        int pageNo = Utils.parseInt(pageNoStr, 1);
        
        Integer count = operatorService.selectUcOperatorCounts(ucOperator);

        model.addAttribute("pageSize", ConstantParams.ADMIN_PAGE_SIZE);
        model.addAttribute("count", count.intValue());
        model.addAttribute("pageNo", pageNo);
    	initListUnifiedConfig();
    	
        if(!ConstantParams.SUPERADMIN.equalsIgnoreCase(email)){
        	ucOperator.setCreateUserId(Long.valueOf((StringUtil.isEmptyString(create_user_id)==true)?"0":create_user_id));
        }
		List<UcOperator> ucOperInfoList = operatorService.selectUcOperatorByParameter(ucOperator,pageNo);
		
	
		
		model.addAttribute("sysDicStatusList", listUnifiedConfigStatus);
		
		model.addAttribute("ucOperInfoList", ucOperInfoList);		
		
		return new ModelAndView("/operatorInfo/operManageInit", model.asMap());
	}
	
	/**
	 * 新增操作员信息 初始化下拉列表
	 * @param request
	 * @param respons
	 * @return
	 */
	@RequestMapping(value = "/addOperInfoInit", method = RequestMethod.GET)
	public ModelAndView addOperInfoInit(HttpServletRequest request,
			HttpServletResponse respons){
		
		Model model = new ExtendedModelMap();
		
		model.addAttribute("ucOperator", new UcOperator());		// 获得系统下拉框列表
		SysDic sysDic = new SysDic();
		
		sysDic.setDicType(ConstantParams.DIC_OPERATOR_TYPE);
		
        initListUnifiedConfig();
		
	//	model.addAttribute("sysDicStatusList", listUnifiedConfigStatus);
		
		int maxNo = roleService.selectUcRoleTotal();
		
		List<UcRole> ucRoleList = roleService.selectUcRoles(maxNo+1,0);
		
		model.addAttribute("ucRoleList", ucRoleList);
		
		model.addAttribute("sysDicOperTypeList", listUnifiedConfigOper);
		
		model.addAttribute("sysDicStatusList", listUnifiedConfigStatus);

		return new ModelAndView("/operatorInfo/addOperInfoInit", model.asMap());
	}
	
	/**
	 * 新增操作员
	 * @param request
	 * @param respons
	 * @param ucOperator
	 * @param errors
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addUcOperator", method = RequestMethod.POST)
	public ModelAndView addOperInfo(HttpServletRequest request,
			HttpServletResponse respons, @Valid UcOperator ucOperator,
		    BindingResult errors) throws Exception {
		
		Model model = new ExtendedModelMap();
		
		if (errors.hasErrors()) {
			
			List<ObjectError> errorList = errors.getAllErrors();
			
			for (ObjectError objectError : errorList) {
				
				logger.debug("objectError=" + objectError.toString());
				
			}
			initListUnifiedConfig();
			
			model.addAttribute("sysDicStatusList", listUnifiedConfigStatus);
			
			int maxNo = roleService.selectUcRoleTotal();
			
			List<UcRole> ucRoleList = roleService.selectUcRoles(maxNo+1,0);
			
			model.addAttribute("ucRoleList", ucRoleList);
			
			model.addAttribute("sysDicOperTypeList", listUnifiedConfigOper);
			
			model.addAttribute("sysDicStatusList", listUnifiedConfigStatus);

			return new ModelAndView("/operatorInfo/addOperInfoInit", model.asMap());
			
		}
		
		Date date = new Date();
		
		ucOperator.setCreateTime(date);
		
		ucOperator.setUpdateTime(date);
		
		ucOperator.setCreateUserId(Long.valueOf((StringUtil.isEmptyString(create_user_id)==true)?"0":create_user_id));
		
		String password = "";
		
		String userType = ucOperator.getUserType();
		String orgId = ucOperator.getOrgId();
		if("01".equals(userType)){
			password = MD5Util.getMD5By32bit(ConstantParams.DEFAULT_PASSWORD, ConstantParams.CODE_FORMAT_UTF8);
		}else{
			if(StringUtils.isBlank(orgId)){
				orgId = ConstantParams.MERCHANT_PASSWORD ; 
			}
			password = MD5Util.getMD5By32bit(orgId, ConstantParams.CODE_FORMAT_UTF8);
		}
		ucOperator.setPassword(password);
		
		operatorService.insertUcOperator(ucOperator);
		
		//Model model1 = new ExtendedModelMap();
		//model.addAttribute("successMessage", "添加操作员成功!");
		return new ModelAndView("redirect:/forwardSuccess", model.asMap());
	}
	
	
	/**
	 * 查询操作员信息
	 * @param request
	 * @param respons
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectUcOperatorByParamter", method = RequestMethod.POST)
	public ModelAndView selectUcOperatorByParameter(HttpServletRequest request,HttpServletResponse respons,UcOperator ucOperator) throws Exception {
		Model model = new ExtendedModelMap();
		
		String pageNoStr = Utils.trim(request.getParameter("pageNo"));
        int pageNo = Utils.parseInt(pageNoStr, 1);
        
        Integer count = operatorService.selectUcOperatorCounts(ucOperator);
        
        initListUnifiedConfig();
        
        if(!ConstantParams.SUPERADMIN.equalsIgnoreCase(email)){
        ucOperator.setCreateUserId(Long.valueOf((StringUtil.isEmptyString(create_user_id)==true)?"0":create_user_id));
        }
        List<UcOperator> ucOperInfoList = operatorService.selectUcOperatorByParameter(ucOperator,pageNo);

      
		
		model.addAttribute("sysDicStatusList", listUnifiedConfigStatus);
		
		model.addAttribute("sysDicStatusList", listUnifiedConfigStatus);
        
        model.addAttribute("pageSize", ConstantParams.ADMIN_PAGE_SIZE);
        model.addAttribute("count", count.intValue());
        model.addAttribute("pageNo", pageNo);
		
		model.addAttribute("ucOperInfoList", ucOperInfoList);		
		
		return new ModelAndView("/operatorInfo/operManageInit", model.asMap());
	}
	
	/**
	 * 修改操作员初始化页面
	 * @param request
	 * @param respons
	 * @param ucOperator
	 * @param errors
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/editOperInfoInit", method = RequestMethod.GET)
	public ModelAndView editOperInfoInit(HttpServletRequest request,
			HttpServletResponse respons,UcOperator ucOperator) throws Exception {
		
		Model model = new ExtendedModelMap();
		
		initListUnifiedConfig();
		
		
		UcOperator ucOper = operatorService.selectUcOperatorById(ucOperator.getId());
		
		int maxNo = roleService.selectUcRoleTotal();
		
		List<UcRole> ucRoleList = roleService.selectUcRoles(maxNo+1,0);
		
		model.addAttribute("sysDicStatusList",listUnifiedConfigStatus );
		
		model.addAttribute("ucRoleList", ucRoleList);
		
		model.addAttribute("sysDicOperTypeList", listUnifiedConfigOper);
		
		model.addAttribute("ucOperator", ucOper);
		
		return new ModelAndView("/operatorInfo/editOperInfoInit", model.asMap());
	}
	
	/**
	 * 修改操作员
	 * @param request
	 * @param respons
	 * @param ucOperator
	 * @param errors
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/editUcOperator", method = RequestMethod.POST)
	public ModelAndView editUcOperator(HttpServletRequest request,
									   HttpServletResponse respons, @Valid UcOperator ucOperator, BindingResult errors) throws Exception {
		
		Model model = new ExtendedModelMap();
		if (errors.hasErrors()) {
			
			List<ObjectError> errorList = errors.getAllErrors();
			
			for (ObjectError objectError : errorList) {
				
				logger.debug("objectError=" + objectError.toString());
				
			}
           initListUnifiedConfig();
			
			model.addAttribute("sysDicStatusList", listUnifiedConfigStatus);
			
			int maxNo = roleService.selectUcRoleTotal();
			
			List<UcRole> ucRoleList = roleService.selectUcRoles(maxNo+1,0);
			
			model.addAttribute("ucRoleList", ucRoleList);
			
			model.addAttribute("sysDicOperTypeList", listUnifiedConfigOper);
			
			model.addAttribute("sysDicStatusList", listUnifiedConfigStatus);

			model.addAttribute("UcOperator", ucOperator);

			return new ModelAndView("/operatorInfo/editOperInfoInit", model.asMap());
			
		}
		
		operatorService.updateUcOperator(ucOperator);
		
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
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	//@ResponseBody
	public void resetPassword(HttpServletRequest request,
			HttpServletResponse respons, UcOperator ucOperator) throws IOException{
		
		Map<String,Object> map = new HashMap<String,Object>();
		
        ucOperator.setPassword(MD5Util.getMD5By32bit(ConstantParams.DEFAULT_PASSWORD, ConstantParams.CODE_FORMAT_UTF8));
        
        operatorService.updateUcOperator(ucOperator);
		
        //map.put("success", true);
        respons.getWriter().write("{\"success\":true}");
        
       //	return map;
        
	}
	
	@RequestMapping(value = "/changePwd", method = RequestMethod.POST)
	@ResponseBody
	public void changePwd(HttpServletRequest request,
		HttpServletResponse respons) throws IOException{
		//UcOperator ucOperator = new UcOperator();
		String opwd = request.getParameter("opwd");
		String npwd = request.getParameter("npwd");
		
		if(!StringUtil.isEmptyString(opwd) && !StringUtil.isEmptyString(npwd)){
		SecurityOperator oper = (SecurityOperator)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(oper !=null && !StringUtil.isEmptyString(oper.getEmail())){
			UcOperator ucOperator = operatorService.selectUcOperatorByEmailAndPass(oper.getEmail(), MD5Util.getMD5By32bit(opwd, ConstantParams.CODE_FORMAT_UTF8));
			  if(ucOperator !=null){
				     ucOperator.setPassword(MD5Util.getMD5By32bit(npwd, ConstantParams.CODE_FORMAT_UTF8));
			        operatorService.updateUcOperator(ucOperator);
		            respons.getWriter().write("{\"success\":true}");
			  }else{
				  respons.getWriter().write("{\"msg\":\"请输入正确的老密码\"}"); 
			  }
			
			
		}else{
	           respons.getWriter().write("{\"msg\":\"参数为空\"}");
		}

		}else{
			 respons.getWriter().write("{\"msg\":\"获取密码有误\"}");
		}
        
	}
	@RequestMapping(value = "/checkRegister", method = RequestMethod.POST)
	@ResponseBody
	public void checkRegister(HttpServletRequest request,
		HttpServletResponse respons) throws IOException{
		//UcOperator ucOperator = new UcOperator();
		String email = request.getParameter("email");
		String mobile = request.getParameter("mobile");
		UcOperator ucOperator = new UcOperator();
		if(!StringUtil.isEmptyString(email)){
			ucOperator.setEmail(email);
			int counts = operatorService.selectUcOperatorCountsForValidate(ucOperator);
		    if(counts==0){
	            respons.getWriter().write("{\"email\":true}");
		    }else{
			  respons.getWriter().write("{\"email\":false}"); 
		    }
		}
		if(!StringUtil.isEmptyString(mobile)){
			ucOperator.setEmail(null);
			ucOperator.setMobile(mobile);
			int counts = operatorService.selectUcOperatorCountsForValidate(ucOperator);
		    if(counts==0){
	            respons.getWriter().write("{\"mobile\":true}");
		    }else{
			  respons.getWriter().write("{\"mobile\":false}"); 
		    }
		}
        
	}
	
	

}
