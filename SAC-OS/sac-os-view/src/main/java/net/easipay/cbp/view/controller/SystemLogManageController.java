package net.easipay.cbp.view.controller;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.easipay.cbp.cache.CacheUtil;
import net.easipay.cbp.constant.Constants;
import net.easipay.cbp.controller.BaseController;
import net.easipay.cbp.model.SacOperHistory;
import net.easipay.cbp.model.SacRecordLog;
import net.easipay.cbp.service.ISacOperHistoryService;
import net.easipay.cbp.service.ISacRecordLogService;
import net.easipay.cbp.service.ISysDicService;
import net.easipay.cbp.util.DateUtil;
import net.easipay.cbp.util.Utils;
import net.easipay.dsfc.UnifiedConfig;
import net.easipay.dsfc.UnifiedConfigSimple;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
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
 * 系统日志管理
 * @author jjiang
 *
 */

@Controller
public class SystemLogManageController extends BaseController{ 
	private static final Logger logger = Logger.getLogger(SystemLogManageController.class);
    
    @Autowired
    private ISacOperHistoryService sacOperHistoryService;
    
    @Autowired
    private ISysDicService sysDicService;
    
    @Autowired
    private ISacRecordLogService sacRecordLogService;
    
    
    /**  
     * 操作日志初始化查询
     * @param request
     * @param response
     * @param sacRecDifferences
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/operHistoryManageInit", method = RequestMethod.GET) 
    public ModelAndView orgManageInit(HttpServletRequest request, HttpServletResponse response ,SacOperHistory sacOperHistory)throws Exception  
    {
    	Model model = new ExtendedModelMap();
    	
 		String pageNoStr = Utils.trim(request.getParameter("pageNo"));
 		
        int pageNo = Utils.parseInt(pageNoStr, 1);
        
        String pageSizeStr = Utils.trim(request.getParameter("pageSize"));
        
        int pageSize = Utils.parseInt(pageSizeStr, 10);
        
        Integer count = sacOperHistoryService.selectSacOperHistoryCounts(sacOperHistory);
        
        List<SacOperHistory> sacOperHistoryList = sacOperHistoryService.selectSacOperHistoryByParam(sacOperHistory, pageNo, pageSize);
        
        List<UnifiedConfig> operTypeList = UnifiedConfigSimple.getDicTypeConfig(Constants.OPER_TYPE);
 		
        dealOperType(sacOperHistoryList);
        
 		model.addAttribute("operTypeList", operTypeList);
 		
        model.addAttribute("count", count.intValue());
        
        model.addAttribute("pageNo", pageNo);
 		
 		model.addAttribute("sacOperHistoryList", sacOperHistoryList);
    	 
        return new ModelAndView("/systemManage/operHistoryManageInit", model.asMap()); 
    }  
    
    private void dealOperType(List<SacOperHistory> operList) {
    	
    	 Map<String, Object> cacheByTypeToMap = CacheUtil.getCacheByTypeToMap(Constants.OPER_TYPE);
    	 
    	 for(SacOperHistory oper : operList){
    		 String operType = oper.getOperType();
    		 String desc = (String)cacheByTypeToMap.get(operType);
    		 oper.setOperType(desc);
    	 }
		
	}

	/**  
     * 操作日志初始化查询
     * @param request
     * @param response
     * @param sacRecDifferences
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/operHistoryManageInit", method = RequestMethod.POST) 
    public ModelAndView orgManageQueryInit(HttpServletRequest request, HttpServletResponse response ,@Valid SacOperHistory sacOperHistory,BindingResult errors)throws Exception  
    {
    	Model model = new ExtendedModelMap();
    	
    	List<UnifiedConfig> operTypeList = UnifiedConfigSimple.getDicTypeConfig(Constants.OPER_TYPE);
    	
    	if (errors.hasErrors()) {
			List<ObjectError> errorList = errors.getAllErrors();
			for (ObjectError objectError : errorList) {
				logger.debug("objectError=" + objectError.toString());
			}
	 		
	 		model.addAttribute("operTypeList", operTypeList);
	 		
	        return new ModelAndView("/systemManage/operHistoryManageInit", model.asMap()); 
		}
    	
 		String pageNoStr = Utils.trim(request.getParameter("pageNo"));
 		
        int pageNo = Utils.parseInt(pageNoStr, 1);
        
        String pageSizeStr = Utils.trim(request.getParameter("pageSize"));
        
        int pageSize = Utils.parseInt(pageSizeStr, 10);
        
        Integer count = sacOperHistoryService.selectSacOperHistoryCounts(sacOperHistory);
        
        List<SacOperHistory> sacOperHistoryList = sacOperHistoryService.selectSacOperHistoryByParam(sacOperHistory, pageNo, pageSize);
        
        dealOperType(sacOperHistoryList);
        
 		model.addAttribute("operTypeList", operTypeList);

        model.addAttribute("count", count.intValue());
        
        model.addAttribute("pageNo", pageNo);
 		
 		model.addAttribute("sacOperHistoryList", sacOperHistoryList);
    	 
        return new ModelAndView("/systemManage/operHistoryManageInit", model.asMap()); 
    }  
    
    
    /**  
     * 操作日志初始化查询
     * @param request
     * @param response
     * @param sacRecDifferences
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/interfaceLogManageInit", method = {RequestMethod.GET,RequestMethod.POST}) 
    public ModelAndView interfaceLogManageInit(HttpServletRequest request, HttpServletResponse response ,SacRecordLog sacRecordLog)throws Exception  
    {
    	Model model = new ExtendedModelMap();
    	
 		String pageNoStr = Utils.trim(request.getParameter("pageNo"));
 		
        int pageNo = Utils.parseInt(pageNoStr, 1);
        
        String pageSizeStr = Utils.trim(request.getParameter("pageSize"));
        
        int pageSize = Utils.parseInt(pageSizeStr, 10);
        
        String startDate = request.getParameter("startDate");
        if(StringUtils.isBlank(startDate)){
        	startDate = DateUtil.formatCurrentDate(Calendar.DATE, -7, "yyyyMMdd");
        	sacRecordLog.setStartDate(startDate);
        }
        
        Integer count = sacRecordLogService.selectSacRecordLogCounts(sacRecordLog);
        
        List<SacRecordLog> sacRecordLogList = sacRecordLogService.selectSacRecordLogByParam(sacRecordLog, pageNo, pageSize);
        
        model.addAttribute("count", count.intValue());
        
        model.addAttribute("pageNo", pageNo);
 		
 		model.addAttribute("sacRecordLogList", sacRecordLogList);
 		
 		model.addAttribute("sacRecordLog", sacRecordLog);
 		
 		model.addAttribute("startDate", startDate);
 		
 		model.addAttribute("endDate", sacRecordLog.getEndDate());
 		
        return new ModelAndView("/systemManage/interfaceLogManageInit", model.asMap()); 
    }
    
    @ResponseBody
    @RequestMapping(value="/interfaceLogDetailInit", method = RequestMethod.POST) 
    public String orgManageDetailInit(HttpServletRequest request, HttpServletResponse response ,SacRecordLog sacRecordLog)throws Exception  
    {
    	
    	sacRecordLog = sacRecordLogService.selectSacRecordLogById(sacRecordLog);
        
	    String content = JSONObject.fromObject(sacRecordLog).toString();
			
		return content;
    } 
    
   
}
