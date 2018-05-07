package net.easipay.cbp.view.controller;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.easipay.cbp.cas.users.OrgnizationInfo;
import net.easipay.cbp.model.SysDic;
import net.easipay.cbp.model.UcOrgInfo;
import net.easipay.cbp.service.OrgInfoService;
import net.easipay.cbp.service.SysDicService;
import net.easipay.cbp.service.SysParamService;
import net.easipay.cbp.util.ConstantParams;
import net.easipay.cbp.util.DownLoadUtil;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * RESTFUL STYLE /users HTTP GET => index() 列表或分页显示 /user/editNew HTTP GET =>
 * _new() 进入新增页面 /user/{id} HTTP GET => show() 根据ID号显示 /user/{id}/edit HTTP GET
 * => edit() 根据ID号进入更新页面 /user HTTP POST => create() 新增实体 /user HTTP PUT =>
 * update() 更新实体 /user/{id} HTTP DELETE => delete() 根据ID号删除实体 /users HTTP DELETE
 * => batchDelete() 批量删除实体
 **/
@Controller
public class OrgInfoController {
	private static final Logger logger = Logger
			.getLogger(OrgInfoController.class);
	@Autowired
	private OrgInfoService orgInfoService;
	@Autowired
	private SysDicService sysDicService;
	@Autowired
	private SysParamService sysParamService;

    private static List<UnifiedConfig> listUnifiedConfigOrgType;
	
	private static List<UnifiedConfig> listUnifiedConfigOrgStatus;
	
	private static List<UnifiedConfig> listUnifiedConfigPassType;
	
	public static void initListUnifiedConfig(){
		listUnifiedConfigOrgType = UnifiedConfigSimple.getDicTypeConfig(ConstantParams.DIC_ORG_TYPE); 
		listUnifiedConfigOrgStatus = UnifiedConfigSimple.getDicTypeConfig(ConstantParams.DIC_ORG_STATUS);
		listUnifiedConfigPassType = UnifiedConfigSimple.getDicTypeConfig(ConstantParams.DIC_LAWMAN_PASSTYPE);
	}
	/**
	 * 列表显示
	 * */
	@RequestMapping(value = "/addOrgInfoInit", method = RequestMethod.GET)
	public ModelAndView addOrgInfoInit(HttpServletRequest request,
			HttpServletResponse respons) {
		Model model = new ExtendedModelMap();
		model.addAttribute("ucOrgInfo", new UcOrgInfo());
		initListUnifiedConfig();
		model.addAttribute("sysDicOrgTypeList", listUnifiedConfigOrgType);
		model.addAttribute("sysDicStatusList", listUnifiedConfigOrgStatus);
		model.addAttribute("sysDicLawanPasstypeList", listUnifiedConfigPassType);

		return new ModelAndView("/orgInfo/addOrgInfoInit", model.asMap());
	}

	@RequestMapping(value = "/addOrgInfo", method = RequestMethod.POST)
	public ModelAndView addOrgInfo(MultipartFile dutyLicenseFileParam,MultipartFile orgLicenseFileParam,MultipartFile taxyRegLicenseFileParam ,MultipartFile lawManLicenseFileParam, HttpServletRequest request,
			HttpServletResponse respons, @Valid UcOrgInfo ucOrgInfo,BindingResult errors) throws Exception {
		Model model = new ExtendedModelMap();
		//字段验证，错误处理
		if (errors.hasErrors()) {
			List<ObjectError> errorList = errors.getAllErrors();
			for (ObjectError objectError : errorList) {
				logger.debug("objectError=" + objectError.toString());
			}
			initListUnifiedConfig();
			model.addAttribute("sysDicOrgTypeList", listUnifiedConfigOrgType);
			model.addAttribute("sysDicStatusList", listUnifiedConfigOrgStatus);
			return new ModelAndView("/orgInfo/addOrgInfoInit", model.asMap());
		}
        //从参数表中获得路径
		String path = sysParamService.getSysParamValue(ConstantParams.FILE_PATH, ConstantParams.MESSAGE_MAGIC_TYPE);
		String orgCode = ucOrgInfo.getOrgCode();

		// String path =
		// request.getSession().getServletContext().getRealPath("upload");

		// File.separator
		if (!StringUtil.isEmptyString(path)&& !StringUtil.isEmptyString(orgCode)) {
			//根据企业组织机构码来创建路径
			path = path + orgCode + File.separator;
			logger.debug(path);
			if(!dutyLicenseFileParam.isEmpty()){
			String dutyLicenseFilePath = fileToPath(dutyLicenseFileParam, path,"01");
			ucOrgInfo.setDutyLicenseFile(dutyLicenseFilePath);
			}else{
				logger.info("未选泽营业执照扫描件上传");
			}
			if(!orgLicenseFileParam.isEmpty()){
			String orgLicenseFilePath = fileToPath(orgLicenseFileParam, path,"02");
			ucOrgInfo.setOrgLicenseFile(orgLicenseFilePath);
			}else{
				logger.info("为选择机构代码证扫描件上传");
			}
			if(!taxyRegLicenseFileParam.isEmpty()){
				String taxyRegLicenseFilePath = fileToPath(taxyRegLicenseFileParam, path,"03");
				ucOrgInfo.setTaxyRegLicenseFile(taxyRegLicenseFilePath);
				}else{
					logger.info("为选择税务登记证上传");
				}
			
			if(!lawManLicenseFileParam.isEmpty()){
				String lawManLicenseFilePath = fileToPath(lawManLicenseFileParam, path,"04");
				ucOrgInfo.setLawManLicenseFile(lawManLicenseFilePath);
				}else{
					logger.info("为选择法人身份证上传");
				}
			
			ucOrgInfo.setCreateTime(new Date());
			orgInfoService.insertOrgInfo(ucOrgInfo);
			model.addAttribute("successMessage", "添加商户成功!");
		} else {
			logger.error("path+orgCode=" + path + File.separator + orgCode);
			throw new Exception("获取路径信息失败!");			
		}
		return new ModelAndView("redirect:/forwardSuccess", model.asMap());
	}

	

	
/*
	// 解析接口中content參數
	public UcOrgInfo transContentToOrgInfoId(HttpServletRequest request,
			HttpServletResponse respons,JSONObject result) throws IOException {
		// 解析参数
		 String content = request.getParameter("content");
		//String content = "{'orgId':'45'}";
		UcOrgInfo ucOrgInfo = new UcOrgInfo();
		if (!StringUtil.isEmptyString(content)) {
			// 解析json参数
			JSONObject dataJson = JSONObject.fromObject(content);

				if (!StringUtil.isEmptyString(dataJson.toString())&& !StringUtil.isEmptyString(dataJson.getString("orgId"))) {
					ucOrgInfo.setOrgId(Long.valueOf(dataJson.getString("orgId")));
				} else {
					result = ResultToJsonUtil.StringToJson(ConstantParams.RTN_CODE_PARAM_IS_NULL, "orgId参数为空!", "");
 	             }
			        
						    }else{
						   	 result = ResultToJsonUtil.StringToJson(ConstantParams.RTN_CODE_PARAM_IS_NULL, "content参数为空!", "");
								}
		logger.debug("transContentToOrgInfoId解析参数的结果result="+result);
		logger.debug("解析后參數bean="+ ToStringBuilder.reflectionToString(ucOrgInfo));
		return ucOrgInfo;
	}

	// 解析接口中content參數
	public UcOrgInfo transContentToOrgInfoBean(HttpServletRequest request,
			HttpServletResponse respons,JSONObject result) throws IOException {
		// 解析参数
		 String content = request.getParameter("content");
		//String content = "{'orgId':'45','update':{'orgCode':'lmcTestt'}}";
		UcOrgInfo ucOrgInfo = new UcOrgInfo();
		if (!StringUtil.isEmptyString(content)) {
			// 解析json参数
			JSONObject dataJson = JSONObject.fromObject(content);
			JSONObject updates = dataJson.getJSONObject("update");

			if (!StringUtil.isEmptyString(updates.toString())
					&& !StringUtil.isEmptyString(dataJson.toString())
					&& !StringUtil.isEmptyString(dataJson.getString("orgId"))) {
				ucOrgInfo.setOrgId(new Long(dataJson.getString("orgId")));
				ucOrgInfo.setOrgCode(updates.has("orgCode") ? updates.getString("orgCode") : null);
				ucOrgInfo.setOrgName(updates.has("orgName") ? updates.getString("orgName") : null);
			} else {
				result = ResultToJsonUtil.StringToJson(ConstantParams.RTN_CODE_PARAM_IS_NULL, "orgId参数为空!", "");
			}

		} else {
			result = ResultToJsonUtil.StringToJson(ConstantParams.RTN_CODE_PARAM_IS_NULL, "content参数为空!", "");
		}
		logger.debug("transContentToOrgInfoBean解析参数的结果result="+result);
		logger.debug("解析后參數bean="+ ToStringBuilder.reflectionToString(ucOrgInfo));
		return ucOrgInfo;
	}
*/
	// 将文件存放到指定的路径
	private String fileToPath(MultipartFile file, String path,String type) {
		//给图片重命名
		
		String fileNameOrg = file.getOriginalFilename();
		//获取文件类型
		String typeJpg = fileNameOrg.indexOf(".") != -1 ? fileNameOrg.substring(fileNameOrg.lastIndexOf("."), fileNameOrg.length()):null;
		//给文件重新命名，新名称：上传时间+文件类型（01：营业执照扫描件）+ 文件类型（.jpg or .png）
		String fileName = StringUtil.dateToStr()+type+typeJpg;
		String fileUrl = null;
		File targetFile = new File(path, fileName);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		// 保存
		try {
			file.transferTo(targetFile);
			fileUrl = path + fileName;
		} catch (Exception e) {
			logger.error("fileUrl=" + fileUrl);
			e.printStackTrace();
		}
		return fileUrl;
	}

	@RequestMapping(value = "/orgManageInit", method = RequestMethod.GET)
	public ModelAndView orgManageInit(HttpServletRequest request,
			HttpServletResponse respons){
		Model model = new ExtendedModelMap();
		 String pageNoStr = Utils.trim(request.getParameter("pageNo"));
         int pageNo = Utils.parseInt(pageNoStr, 1);
         
        Integer count = orgInfoService.selectUcOrgInfoTotal();
        List<UcOrgInfo> ucOrgInfoList = orgInfoService.selectAllUcOrgInfo(pageNo);
        
		model.addAttribute("ucOrgInfoList", ucOrgInfoList);
         model.addAttribute("pageSize", ConstantParams.ADMIN_PAGE_SIZE);
         model.addAttribute("count", count.intValue());
         model.addAttribute("pageNo", pageNo);
         
		return new ModelAndView("/orgInfo/orgManageInit", model.asMap());
	}
	
	@RequestMapping(value = "/editOrgInfoInit", method = RequestMethod.GET)
	public ModelAndView editOrgInfoInit(HttpServletRequest request,
			HttpServletResponse respons){
		Model model = new ExtendedModelMap();
		String orgId = request.getParameter("orgId");
		
		UcOrgInfo ucOrgInfo = orgInfoService.selectOrgInfoById(Long.valueOf(orgId));
		initListUnifiedConfig();
		model.addAttribute("sysDicOrgTypeList", listUnifiedConfigOrgType);
		model.addAttribute("sysDicStatusList", listUnifiedConfigOrgStatus);
		model.addAttribute("sysDicLawanPasstypeList", listUnifiedConfigPassType);
		
		model.addAttribute("ucOrgInfo", ucOrgInfo);		
		return new ModelAndView("/orgInfo/editOrgInfoInit", model.asMap());
	}
	
	@RequestMapping(value = "/downLoadInit", method = RequestMethod.GET)
	public void downLoadInit(HttpServletRequest request,
			HttpServletResponse respons){
		String downloadFile = request.getParameter("downloadFile");
		logger.info("downloadFile="+downloadFile);
		try {
			DownLoadUtil.download(request, respons, downloadFile, null);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	
	@RequestMapping(value = "/editOrgInfo", method = RequestMethod.POST)
	public ModelAndView editOrgInfo(MultipartFile dutyLicenseFileParam,
			MultipartFile orgLicenseFileParam, MultipartFile taxyRegLicenseFileParam ,MultipartFile lawManLicenseFileParam,HttpServletRequest request,
			HttpServletResponse respons, @Valid UcOrgInfo ucOrgInfo,
			BindingResult errors) throws Exception {
		Model model = new ExtendedModelMap();
		if (errors.hasErrors()) {
			List<ObjectError> errorList = errors.getAllErrors();
			for (ObjectError objectError : errorList) {
				logger.debug("objectError=" + objectError.toString());
			}
			initListUnifiedConfig();
			model.addAttribute("sysDicOrgTypeList", listUnifiedConfigOrgType);
			model.addAttribute("sysDicStatusList", listUnifiedConfigOrgStatus);
			model.addAttribute("sysDicLawanPasstypeList", listUnifiedConfigPassType);
			return new ModelAndView("/orgInfo/editOrgInfoInit", model.asMap());
		}
       String orgIdString = request.getParameter("orgIdString");
		String path = sysParamService.getSysParamValue(
				ConstantParams.FILE_PATH, ConstantParams.MESSAGE_MAGIC_TYPE);
		String orgCode = ucOrgInfo.getOrgCode();
		ucOrgInfo.setOrgId(Long.valueOf(orgIdString));

		// String path =
		// request.getSession().getServletContext().getRealPath("upload");

		// File.separator
		if (!StringUtil.isEmptyString(path)&& !StringUtil.isEmptyString(orgCode)) {
			path = path + orgCode + File.separator;
			logger.debug(path);
			if(!dutyLicenseFileParam.isEmpty()){
				String dutyLicenseFilePath = fileToPath(dutyLicenseFileParam, path,"01");
				ucOrgInfo.setDutyLicenseFile(dutyLicenseFilePath);
				}
				if(!orgLicenseFileParam.isEmpty()){
				String orgLicenseFilePath = fileToPath(orgLicenseFileParam, path,"02");
				ucOrgInfo.setOrgLicenseFile(orgLicenseFilePath);
				}
				if(!taxyRegLicenseFileParam.isEmpty()){
				String taxyRegLicenseFilePath = fileToPath(taxyRegLicenseFileParam, path,"03");
				ucOrgInfo.setTaxyRegLicenseFile(taxyRegLicenseFilePath);
				}
				if(!lawManLicenseFileParam.isEmpty()){
				String lawManLicenseFilePath = fileToPath(lawManLicenseFileParam, path,"04");
				ucOrgInfo.setLawManLicenseFile(lawManLicenseFilePath);
				}
			ucOrgInfo.setUpdateTime(new Date());
			orgInfoService.updateOrgInfo(ucOrgInfo);
			model.addAttribute("successMessage", "添加商户成功!");
		} else {
			
			logger.error("path+orgCode=" + path + File.separator + orgCode);
			throw new Exception("获取路径信息失败");
		}
		return new ModelAndView("redirect:/forwardSuccess", model.asMap());
	}
	
	
	
	/*
	public UcOrgInfo transContentToOrgInfo(HttpServletRequest request,
			HttpServletResponse respons,JSONObject result) throws IOException {
		// 解析参数
		 String content = request.getParameter("content");
		//String content = "{'orgId':'45'}";
		UcOrgInfo ucOrgInfo = new UcOrgInfo();
		Map reqMap= null;
		if (!StringUtil.isEmptyString(content)) {
			// 解析json参数

			reqMap = parserToMap(content);
			try {
				BeanUtils.populate(ucOrgInfo, reqMap);
			} catch (IllegalAccessException e) {
				logger.error("转化错误",e);
			} catch (InvocationTargetException e) {
				logger.error("调用异常",e);
			}
		}else{
			result = ResultToJsonUtil.StringToJson(ConstantParams.RTN_CODE_PARAM_IS_NULL, "content参数为空!", "");
								}
		logger.debug("transContentToOrgInfoId解析参数的结果result="+result);
		logger.debug("解析参数后bean="+ ToStringBuilder.reflectionToString(ucOrgInfo));
		return ucOrgInfo;
	}
		
	public static Map parserToMap(String s){  
		    Map map=new HashMap();  
		    JSONObject json=JSONObject.fromObject(s);  
		    Iterator keys=json.keys();  
		    while(keys.hasNext()){  
		        String key=(String) keys.next();  
		        String value=json.get(key).toString();  
		        if(value.startsWith("{")&&value.endsWith("}")){  
		            map.put(key, parserToMap(value));  
		        }else{  
		            map.put(key, value);  
		        }  
		    }  
		    return map;  
		}  

    */
	
	
}
