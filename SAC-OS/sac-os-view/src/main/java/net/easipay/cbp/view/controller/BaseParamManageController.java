package net.easipay.cbp.view.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.easipay.cbp.cache.CacheUtil;
import net.easipay.cbp.constant.Constants;
import net.easipay.cbp.controller.BaseController;
import net.easipay.cbp.model.SacChannelParam;
import net.easipay.cbp.model.SacChannelParamCmd;
import net.easipay.cbp.model.SacCusParameter;
import net.easipay.cbp.model.form.SacCusParameterHandleForm;
import net.easipay.cbp.service.ISacChannelParamService;
import net.easipay.cbp.service.ISacCusParameterService;
import net.easipay.cbp.util.ConstantParams;
import net.easipay.cbp.util.Utils;
import net.easipay.dsfc.UnifiedConfig;
import net.easipay.dsfc.UnifiedConfigSimple;
import net.easipay.dsfc.ws.jws.JwsClient;
import net.easipay.dsfc.ws.jws.JwsResult;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;



/**
 * 客户信息管理
 * @author jjiang
 *
 */

@Controller
public class BaseParamManageController extends BaseController{ 
	
    @Autowired
    private ISacCusParameterService sacCusParameterService;
    
    @Autowired
    private ISacChannelParamService sacChannelParamService;
    
    /**  
     * 客户参数录入初始化
     */
    @ResponseBody
    @RequestMapping(value="/addSacCusParamterInit", method = RequestMethod.GET) 
    public ModelAndView addSacCusParamterInit(HttpServletRequest request, HttpServletResponse response)  
    {
    	ModelAndView mav = new ModelAndView("baseParamManage/addSacCusParamterInit");
    	List<UnifiedConfig> currencyList = UnifiedConfigSimple.getDicTypeConfig(Constants.CURRENCY_TYPE);
    	List<UnifiedConfig> bussTypeList = UnifiedConfigSimple.getDicTypeConfig(Constants.BUSS_TYPE);
    	mav.addObject("currencyList", currencyList);//币种
    	mav.addObject("bussTypeList", bussTypeList);
		return mav;
    } 
    
    /**  
     * 客户参数录入
     */
    @ResponseBody
    @RequestMapping(value="/addSacCusParamter", method = RequestMethod.POST) 
    public ModelAndView addSacCusParamter(HttpServletRequest request, HttpServletResponse response)  
    {
    	String cusName = request.getParameter("cusName");
    	String orgCardId = request.getParameter("orgCardId");
    	String bussType = request.getParameter("bussType");
    	String sacCurrency = request.getParameter("sacCurrency");
    	
    	SacCusParameterHandleForm sacCusParameter = new SacCusParameterHandleForm();
    	sacCusParameter.setCusName(cusName);
    	sacCusParameter.setOrgCardId(orgCardId);
    	sacCusParameter.setBussType(bussType);
    	sacCusParameter.setSacCurrency(sacCurrency);  	
    	sacCusParameter.setCusType("1");
    	sacCusParameter.setRefundFlag("1");
    	sacCusParameter.setAccName(cusName);
    	sacCusParameter.setSacType("1");
    	sacCusParameter.setIsVaildFlag("1");
    	
    	
    	ModelAndView mav = new ModelAndView("baseParamManage/addSacCusParamterInit");
    	JwsClient jwsClient = new JwsClient("SAC-AC-0004");
    	JwsResult jwsResult = jwsClient.putParam("sacCusParameter", sacCusParameter).call();
    	if(jwsResult != null && jwsResult.getCode().equals("000000")){
    		mav.addObject("message", "录入成功！");
    	}else{
    		mav.addObject("message", "录入失败！");
    	}
    	List<UnifiedConfig> currencyList = UnifiedConfigSimple.getDicTypeConfig(Constants.CURRENCY_TYPE);
    	List<UnifiedConfig> bussTypeList = UnifiedConfigSimple.getDicTypeConfig(Constants.BUSS_TYPE);
    	mav.addObject("currencyList", currencyList);//币种
    	mav.addObject("bussTypeList", bussTypeList);
		return mav;
    }
    
    /**  
     * 商户信息初始化查询
     * @param request
     * @param response
     * @param sacRecDifferences
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/orgManageInit", method = RequestMethod.GET) 
    public ModelAndView orgManageInit(HttpServletRequest request, HttpServletResponse response ,SacCusParameter sacCusParameter)throws Exception  
    {
    	Model model = new ExtendedModelMap();
    	
 		String pageNoStr = Utils.trim(request.getParameter("pageNo"));
 		
        int pageNo = Utils.parseInt(pageNoStr, 1);
        
        String pageSizeStr = Utils.trim(request.getParameter("pageSize"));
        
        int pageSize = Utils.parseInt(pageSizeStr, 10);
        
        Integer count = sacCusParameterService.selectSacCusParameterTotal(sacCusParameter);
        
        List<SacCusParameter> sacCusParameterList = sacCusParameterService.selectAllSacCusParameter(sacCusParameter, pageNo, pageSize);
        
        sacCusParameterList = sacCusParameterService.handleSacCusParameterList(sacCusParameterList);
        
        List<UnifiedConfig> settlementCycleList = UnifiedConfigSimple.getDicTypeConfig(Constants.SETTLEMENT_CYCLE);
        
 		List<UnifiedConfig> currencyList = UnifiedConfigSimple.getDicTypeConfig(Constants.CURRENCY_TYPE);
 		
 		List<UnifiedConfig> enableFlagList = UnifiedConfigSimple.getDicTypeConfig(Constants.ENABLE_FLAG);
 		
 		model.addAttribute("settlementCycleList", settlementCycleList);
 		
 		model.addAttribute("currencyList", currencyList);
 		
 		model.addAttribute("enableFlagList", enableFlagList);

        model.addAttribute("count", count.intValue());
        
        model.addAttribute("pageNo", pageNo);
 		
 		model.addAttribute("sacCusParameterList", sacCusParameterList);
    	 
        return new ModelAndView("/baseParamManage/orgManageInit", model.asMap()); 
    }  
    
    /**  
     * 商户信息查询
     * @param request
     * @param response
     * @param sacRecDifferences
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/orgManageInit", method = RequestMethod.POST) 
    public ModelAndView orgManageQueryInit(HttpServletRequest request, HttpServletResponse response ,SacCusParameter sacCusParameter)throws Exception  
    {
    	Model model = new ExtendedModelMap();
    	
 		String pageNoStr = Utils.trim(request.getParameter("pageNo"));
 		
        int pageNo = Utils.parseInt(pageNoStr, 1);
        
        String pageSizeStr = Utils.trim(request.getParameter("pageSize"));
        
        int pageSize = Utils.parseInt(pageSizeStr, 10);
        
        Integer count = sacCusParameterService.selectSacCusParameterTotal(sacCusParameter);
        
        List<SacCusParameter> sacCusParameterList = sacCusParameterService.selectAllSacCusParameter(sacCusParameter, pageNo, pageSize);
 		
        sacCusParameterList = sacCusParameterService.handleSacCusParameterList(sacCusParameterList);
        
        List<UnifiedConfig> settlementCycleList = UnifiedConfigSimple.getDicTypeConfig(Constants.SETTLEMENT_CYCLE);
        
 		List<UnifiedConfig> currencyList = UnifiedConfigSimple.getDicTypeConfig(Constants.CURRENCY_TYPE);
 		
 		List<UnifiedConfig> enableFlagList = UnifiedConfigSimple.getDicTypeConfig(Constants.ENABLE_FLAG);
 		
 		model.addAttribute("settlementCycleList", settlementCycleList);
 		
 		model.addAttribute("currencyList", currencyList);
 		
 		model.addAttribute("enableFlagList", enableFlagList);

        model.addAttribute("count", count.intValue());
        
        model.addAttribute("pageNo", pageNo);
 		
 		model.addAttribute("sacCusParameterList", sacCusParameterList);
    	 
        return new ModelAndView("/baseParamManage/orgManageInit", model.asMap()); 
    }  
    
    /**  
     * 商户信息查询
     * @param request
     * @param response
     * @param sacRecDifferences
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value="/orgManageDetailInit", method = RequestMethod.POST) 
    public String orgManageDetailInit(HttpServletRequest request, HttpServletResponse response ,SacCusParameter sacCusParameter)throws Exception  
    {
    	
    	sacCusParameter = sacCusParameterService.selectSacCusParameterById(sacCusParameter);
        
    	Map<String,Object> bussTypeMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_BUSS_TYPE);
    	sacCusParameter.setBussType(bussTypeMap.get(sacCusParameter.getBussType())+"");
	    String content = JSONObject.fromObject(sacCusParameter).toString();
			
		return content;
    } 
    
    /**  
     * 商户信息初始化查询
     * @param request
     * @param response
     * @param sacRecDifferences
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/channelManageInit", method = RequestMethod.GET) 
    public ModelAndView channelManageInit(HttpServletRequest request, HttpServletResponse response ,SacChannelParam sacChannelParam)throws Exception  
    {
    	Model model = new ExtendedModelMap();
    	
 		String pageNoStr = Utils.trim(request.getParameter("pageNo"));
 		
        int pageNo = Utils.parseInt(pageNoStr, 1);
        
        String pageSizeStr = Utils.trim(request.getParameter("pageSize"));
        
        int pageSize = Utils.parseInt(pageSizeStr, 10);
        
        Integer count = sacChannelParamService.selectSacChannelParamCounts(sacChannelParam);
        
        List<SacChannelParam> sacChannelParamList = sacChannelParamService.selectSacChannelParamForSplit(sacChannelParam, pageNo, pageSize);
        
 		List<UnifiedConfig> currencyList = UnifiedConfigSimple.getDicTypeConfig(Constants.CURRENCY_TYPE);
 		
 		List<UnifiedConfig> enableFlagList = UnifiedConfigSimple.getDicTypeConfig(Constants.ENABLE_FLAG);
 		
 		model.addAttribute("enableFlagList", enableFlagList);
 		
 		model.addAttribute("currencyList", currencyList);

        model.addAttribute("count", count.intValue());
        
        model.addAttribute("pageNo", pageNo);
 		
 		model.addAttribute("sacChannelParamList", sacChannelParamList);
    	 
        return new ModelAndView("/baseParamManage/channelManageInit", model.asMap()); 
    }  
    
    /**  
     * 商户信息查询
     * @param request
     * @param response
     * @param sacRecDifferences
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/channelManageInit", method = RequestMethod.POST) 
    public ModelAndView channelManageQueryInit(HttpServletRequest request, HttpServletResponse response ,SacChannelParam sacChannelParam)throws Exception  
    {
    	Model model = new ExtendedModelMap();
    	
 		String pageNoStr = Utils.trim(request.getParameter("pageNo"));
 		
        int pageNo = Utils.parseInt(pageNoStr, 1);
        
        String pageSizeStr = Utils.trim(request.getParameter("pageSize"));
        
        int pageSize = Utils.parseInt(pageSizeStr, 10);
        
        Integer count = sacChannelParamService.selectSacChannelParamCounts(sacChannelParam);
        
        List<SacChannelParam> sacChannelParamList = sacChannelParamService.selectSacChannelParamForSplit(sacChannelParam, pageNo, pageSize);
        
 		List<UnifiedConfig> currencyList = UnifiedConfigSimple.getDicTypeConfig(Constants.CURRENCY_TYPE);
 		
 		List<UnifiedConfig> enableFlagList = UnifiedConfigSimple.getDicTypeConfig(Constants.ENABLE_FLAG);
 		
 		model.addAttribute("currencyList", currencyList);
 		
        model.addAttribute("count", count.intValue());
        
        model.addAttribute("pageNo", pageNo);
 		
 		model.addAttribute("enableFlagList", enableFlagList);
 		
 		model.addAttribute("sacChannelParam", sacChannelParam);
 		
 		model.addAttribute("sacChannelParamList", sacChannelParamList);
    	 
        return new ModelAndView("/baseParamManage/channelManageInit", model.asMap()); 
    }  
    
    /**  
     * 商户信息查询
     * @param request
     * @param response
     * @param sacRecDifferences
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value="/channelManageDetailInit", method = RequestMethod.POST) 
    public String channelManageDetailInit(HttpServletRequest request, HttpServletResponse response ,SacChannelParam sacChannelParam)throws Exception  
    {
    	
    	sacChannelParam = sacChannelParamService.selectSacChannelParamById(sacChannelParam);
        
	    String content = JSONObject.fromObject(sacChannelParam).toString();
			
		return content;
    } 
    
    /**  
     * 渠道参数录入初始化
     */
    @ResponseBody
    @RequestMapping(value="/addChannelParamInit", method = RequestMethod.GET) 
    public ModelAndView addChannelParamInit(HttpServletRequest request, HttpServletResponse response)  
    {
    	ModelAndView mav = new ModelAndView("baseParamManage/addChannelParam");
    	mav.addObject("currencyTypeMap", CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_CCY));//交易币种数据
		return mav;
    } 
    
    /**  
     * 渠道参数录入
     */
    @ResponseBody
    @RequestMapping(value="/addChannelParam", method = RequestMethod.POST) 
    public ModelAndView addChannelParam(HttpServletRequest request, HttpServletResponse response,SacChannelParamCmd sacChannelParamCmd)  
    {
    	ModelAndView mav = new ModelAndView("baseParamManage/addChannelParam");
    	int num = sacChannelParamService.addChannelParam(sacChannelParamCmd);
    	if(num==1){
    		mav.addObject("message", "录入成功，等待复核！");
    	}else{
    		mav.addObject("message", "录入失败！");
    	}
    	mav.addObject("currencyTypeMap", CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_CCY));//交易币种数据
		return mav;
    }
    
    /**  
     * 渠道参数审核查询
     */
    @ResponseBody
    @RequestMapping(value="/channelParamCheck", method = {RequestMethod.POST,RequestMethod.GET}) 
    public ModelAndView channelParamCheck(HttpServletRequest request, HttpServletResponse response)  
    {
    	ModelAndView mav = new ModelAndView("baseParamManage/channelParamCheck");
    	// 页码处理
		Integer pageNo = request.getParameter("pageNo") != null?Integer.parseInt(request.getParameter("pageNo")):1;// 默认为第1页
		Integer	pageSize = request.getParameter("pageSize") != null?Integer.parseInt(request.getParameter("pageSize")):10;
    	Map<String,Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("start", (pageNo-1)*pageSize);//分页起始
		paramMap.put("end", pageSize*pageNo);//分页结束
    	paramMap.put("startDate", request.getParameter("startDate"));
    	paramMap.put("endDate", request.getParameter("endDate"));
    	paramMap.put("cmdState", request.getParameter("cmdState")==null?"1":request.getParameter("cmdState"));
    	mav.addObject("sacChannelParamCmdList", sacChannelParamService.selectSacChannelParamCmd(paramMap));
    	mav.addObject("count", sacChannelParamService.selectSacChannelParamCmdCount(paramMap));
    	mav.addObject("startDate", request.getParameter("startDate"));
    	mav.addObject("endDate", request.getParameter("endDate"));
    	mav.addObject("cmdState", request.getParameter("cmdState")==null?"1":request.getParameter("cmdState"));
    	mav.addObject("pageNo", pageNo);
		mav.addObject("pageSize", pageSize);
		return mav;
    }
    /**  
     * 渠道参数审核结果
     * @throws IOException 
     */
    @ResponseBody
    @RequestMapping(value="/channelParamCheckResult", method = RequestMethod.GET) 
    public void channelParamCheckResult(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String responseStr = null;
        String result = request.getParameter("result");
    	String id = request.getParameter("id");
    	if("succ".equals(result)){//审核通过
    		responseStr = sacChannelParamService.channelParamCheckSucc(Long.valueOf(id));
    	}else if("fail".equals(result)){//审核不通过
    		responseStr = sacChannelParamService.channelParamCheckFail(Long.valueOf(id));
    	}else{
    		responseStr = "审核处理失败";
    	}
    	response.getWriter().write("{\"message\":\""+responseStr+"\"}");
    }
}
