package net.easipay.cbp.view.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.easipay.cbp.cache.CacheUtil;
import net.easipay.cbp.constant.Constants;
import net.easipay.cbp.constant.EnumConstants.FundGiveOLConstants.CmdState;
import net.easipay.cbp.exception.SacException;
import net.easipay.cbp.model.SacCheckInfo;
import net.easipay.cbp.model.SacCusParameter;
import net.easipay.cbp.model.SacDffOflCommand;
import net.easipay.cbp.model.SacOflCommand;
import net.easipay.cbp.model.SacOtrxInfo;
import net.easipay.cbp.service.ICusBalanceService;
import net.easipay.cbp.service.ISacCheckInfoService;
import net.easipay.cbp.service.ISacCommandService;
import net.easipay.cbp.service.ISacCusParameterService;
import net.easipay.cbp.service.ISacDffOflCommandService;
import net.easipay.cbp.service.ISacOflCommandService;
import net.easipay.cbp.service.ISacOperHistoryService;
import net.easipay.cbp.util.ConstantParams;
import net.easipay.cbp.util.DateUtil;
import net.easipay.cbp.util.Utils;
import net.easipay.dsfc.UnifiedConfig;
import net.easipay.dsfc.UnifiedConfigSimple;
import net.easipay.dsfc.ws.jws.JwsResult;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 线下出款
* ClassName: FundGiveOnlineController <br/> 
* Function: TODO ADD FUNCTION. <br/> 
* Reason: TODO ADD REASON(可选). <br/> 
* date: 2016年3月7日 下午8:42:19 <br/> 
* 
* @author Administrator 
* @version  
* @since JDK 1.6
 */
@Controller
public class FundGiveOfflineController extends BaseDataController{

	
	@Autowired
	public ICusBalanceService cusBalanceService;
	
	@Autowired
	public ISacCusParameterService sacCusParameterService;
	
	@Autowired
	public ISacOflCommandService sacOflCommandService;
	
	@Autowired
	private ISacOperHistoryService sacOperHistoryService;
	
    @Autowired
    private ISacCheckInfoService sacCheckInfoService;
    
    @Autowired
    private ISacCommandService sacCommandService;
    
    @Autowired
    private ISacDffOflCommandService sacDffOflCommandService;
	
	
	
	
	/**
	 * 客户余额总查询操作
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/fundCusBalanceQueryInit",method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView fundCusBalanceQueryInit(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("fundGiveOFLManage/fundCusBalanceQueryInit");
		//组装查询参数
		Map<String,Object> paramMap = new HashMap<String,Object>();
		String bussType = request.getParameter("bussType");
		handlePageAndDateRange(request,paramMap,mav,7,10);
		paramMap.put("cusName", request.getParameter("cusName"));//客户名称
		paramMap.put("cusNo", request.getParameter("cusNo"));//客户号
		paramMap.put("orgCardId", request.getParameter("orgCardId"));//组织机构代码
		paramMap.put("bussType", bussType);//组织机构代码
		paramMap.put("childAccType", "02");//流动资金账户
		paramMap.put("amountFlag", "01");//不显示余额为0的数据
		
		List<Map<String,Object>> cusBalanceList = cusBalanceService.queryCusBalance(paramMap);
		
		List<UnifiedConfig> bussTypeList = UnifiedConfigSimple.getDicTypeConfig(Constants.BUSS_TYPE);
		
		//准备初始化参数
		mav.addObject("cusName", request.getParameter("cusName"));//客户名称
		mav.addObject("cusNo", request.getParameter("cusNo"));//客户号
		mav.addObject("orgCardId", request.getParameter("orgCardId"));//客户号
		mav.addObject("totalCount", cusBalanceService.getCusBalanceCount(paramMap));
		mav.addObject("cusBalanceList", handleCusBalanceList(cusBalanceList));
		mav.addObject("bussTypeList", bussTypeList);
		mav.addObject("bussType",bussType);
		mav.addObject("ccyMap", CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_CCY));
		return mav;
	}
	
	
	@RequestMapping(value="/getFundDetailByCusNo",method=RequestMethod.GET)
	public ModelAndView getFundDetailByCusNo(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("fundGiveOFLManage/fundTransferConfirmDetail");
		//组装查询参数
		
		SacCusParameter sacCusParameter = new SacCusParameter();
		String cusNo = request.getParameter("cusNo");
		String ccy = request.getParameter("sacCurrency");
		String bussType = request.getParameter("bussType");
		String craccNo = request.getParameter("craccNo");
		String etrxSerialNo = request.getParameter("etrxSerialNo");
		sacCusParameter.setCusNo(cusNo);
		sacCusParameter.setSacCurrency(ccy);
		List<SacCusParameter> paramList = sacCusParameterService.selectAllSacCusParameter(sacCusParameter, 1, Integer.MAX_VALUE);
		
		if(paramList==null||paramList.size()<=0){
			throw new SacException("999999", "客户号错误!");
		}
		sacCusParameter = paramList.get(0);
		
		List<UnifiedConfig> bankList = UnifiedConfigSimple.getDicTypeConfig(Constants.BANK_TYPE);
		
		List<UnifiedConfig> branchList = UnifiedConfigSimple.getDicTypeConfig(Constants.BRANCH_TYPE);
		
		Map<String, Object> currencyTypeMap = CacheUtil.getCacheByTypeToMap(Constants.CURRENCY_TYPE);
		String sacCurrency = (String)currencyTypeMap.get(sacCusParameter.getSacCurrency());
		//准备初始化参数
		mav.addObject("cusNo", sacCusParameter.getCusNo());//
		mav.addObject("cusName", sacCusParameter.getCusName());//客户名称
		mav.addObject("payCurrencyName", sacCurrency);//
		mav.addObject("payCurrency", ccy);//
		mav.addObject("bussType", bussType);
		mav.addObject("cusPlatAcc", sacCusParameter.getCusPlatAcc());//
		mav.addObject("orgCardId", sacCusParameter.getOrgCardId());//
		mav.addObject("merchantNcode", sacCusParameter.getMerchantNcode());//
		mav.addObject("bankList", bankList);//
		mav.addObject("craccNo", craccNo);
		mav.addObject("etrxSerialNo",etrxSerialNo);
		mav.addObject("branchList",branchList);
		return mav;
	}
	
	@RequestMapping(value="/fundTransferConfirmHandle", method = RequestMethod.POST) 
    public void fundTransferConfirmInit(HttpServletRequest request, HttpServletResponse response,SacOflCommand oflCmd)throws Exception  
    {
	
	String cusNo = request.getParameter("cusNo");
	String cusName = request.getParameter("cusName");
	String cusPlatAcc = request.getParameter("cusPlatAcc");
	String payCurrency = request.getParameter("payCurrency");
	String draccBankName = request.getParameter("draccBankName");
	String draccNodeCode = request.getParameter("draccNodeCode");	
	String draccAreaCode = request.getParameter("draccAreaCode");	
	String craccBankName = request.getParameter("craccBankName");
	String craccNodeCode = request.getParameter("craccNodeCode");	
	String payAmount = request.getParameter("payAmount");
	String orgCardId = request.getParameter("orgCardId");
	String bussType = request.getParameter("bussType");
	String etrxSerialNo = request.getParameter("etrxSerialNo");
	String craccNo = request.getParameter("craccNo");
	if(StringUtils.isBlank(bussType)){
		return;
	}
	
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("cusNo", cusNo);
		map.put("cusName", cusName);
		map.put("cusPlatAcc", cusPlatAcc);
		map.put("payCurrency", payCurrency);	
    	map.put("draccBankName", draccBankName);
    	map.put("draccNodeCode", draccNodeCode);
    	map.put("draccAreaCode", draccAreaCode);
    	map.put("craccBankName", craccBankName);
    	map.put("craccNodeCode", craccNodeCode);
    	map.put("payAmount", payAmount);
    	map.put("orgCardId", orgCardId);
    	map.put("trxType", "1612");  //取回
    	map.put("bussType", bussType);  //业务类型
    	map.put("etrxSerialNo", etrxSerialNo);
    	map.put("craccNo", craccNo);
    	
		String digst = sacCheckInfoService.objectFromMap(map);
		SacCheckInfo sacCheckInfo = new SacCheckInfo();

		sacCheckInfo.setTrxType("1612");
		sacCheckInfo.setDigst(digst);
		sacCheckInfo.setCheckStatus("2");
		sacCheckInfo.setCreateTime(new Date());
		sacCheckInfo.setUpdateTime(new Date());
		sacCheckInfoService.insertSacCheckInfo(sacCheckInfo);
		response.getWriter().write("{\"success\":true}");
    }
	

	@RequestMapping(value="/fundTransferConfirmInit", method = {RequestMethod.POST,RequestMethod.GET}) 
    public ModelAndView fundTransferConfirmInit(HttpServletRequest request, HttpServletResponse response )throws Exception  
    {
    	ModelAndView mav = new ModelAndView("fundGiveOFLManage/fundTransferConfirmInit");
    	String checkStatus = request.getParameter("checkStatus");
    	// 页码处理
    	List<SacCheckInfo> sacCheckInfoList = new ArrayList<SacCheckInfo>();
		Integer pageNo = request.getParameter("pageNo") != null?Integer.parseInt(request.getParameter("pageNo")):1;// 默认为第1页
		Integer	pageSize = request.getParameter("pageSize") != null?Integer.parseInt(request.getParameter("pageSize")):10;
		SacCheckInfo sacCheckInfo = new SacCheckInfo();
		String createTime = request.getParameter("createTime");
		if(StringUtils.isNotBlank(createTime)){
			sacCheckInfo.setCreateTime(DateUtils.parseDate(createTime, new String[]{"yyyyMMdd"}));
        }
		sacCheckInfo.setTrxType("1612");
		sacCheckInfo.setCheckStatus(checkStatus);
		sacCheckInfoList = sacCheckInfoService.querySacCheckInfo(sacCheckInfo, pageNo, pageSize);
		List<SacOtrxInfo> sacOtrxInfoList = (List<SacOtrxInfo>) sacCheckInfoService.objectFromList(sacCheckInfoList);
		handleSacOtrxInfoList(sacOtrxInfoList);
    	mav.addObject("sacOtrxInfoList", sacOtrxInfoList);
    	mav.addObject("count", sacCheckInfoService.getCheckInfoCount(sacCheckInfo));
    	mav.addObject("createTime", request.getParameter("createTime"));
    	mav.addObject("pageNo", pageNo);
		mav.addObject("pageSize", pageSize);
		mav.addObject("checkStatus", request.getParameter("checkStatus"));
    	return mav;
    }  
  


	@RequestMapping(value = "/fundTransferConfirm", method = RequestMethod.POST)
	public void fundTransferConfirm(HttpServletRequest request,
			HttpServletResponse response, SacOflCommand oflCmd)
			throws Exception {
		String id = request.getParameter("id");
		String draccAreaCode = request.getParameter("draccAreaCode");
		String checkStatus = request.getParameter("checkStatus");

		if (StringUtils.isEmpty(id) || StringUtils.isEmpty(checkStatus)) {
			response.getWriter().write("{\"success\":false,\"message\":\"id为空或者审核状态为空\"}");
			return;
		}
		JwsResult rt = null;
		if ("1".equals(checkStatus)) {
			String bussType = request.getParameter("bussType");
			if(StringUtils.isBlank(bussType)){
				response.getWriter().write("{\"success\":false,\"message\":\"业务类型不能为空\"}");
				return;
			}
			rt = sacOflCommandService.confirmTransferCommand(oflCmd,bussType,draccAreaCode);
		}
		sacOperHistoryService.insertSacOperHistory(Constants.OPER_FUND_OFF_GIVE_CHECK, request);
		SacCheckInfo sacCheckInfoTemp = sacCheckInfoService.selectSacCheckInfoById(new Long(id));
		if (sacCheckInfoTemp == null) {
			response.getWriter().write("{\"success\":false,\"message\":\"更新状态失败\"}");
			return;
		}
		
		if (rt ==null || rt.isSuccess()) {
			sacCheckInfoTemp.setCheckStatus(checkStatus);
			sacCheckInfoService.updateSacCheckInfo(sacCheckInfoTemp);
			response.getWriter().write("{\"success\":true}");
		} else {
			response.getWriter().write("{\"success\":false,\"message\":\"" + rt.getMessage() + "\"}");
		}
	}
   
	public List<Map<String,Object>> handleCusBalanceList(List<Map<String,Object>> cusBalanceList){
		if(cusBalanceList==null||cusBalanceList.size()==0){
			return cusBalanceList;
		}
		Map<String,Object> ccyMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_CCY);
		Map<String,Object> bussTypeMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_BUSS_TYPE);
		Iterator<Map<String, Object>> it = cusBalanceList.iterator();
		while(it.hasNext()){
			Map<String, Object> cusBalance = it.next();

			//处理币种
			Object ccyValue = ccyMap.get(cusBalance.get("sacCurrency"));
			String bussType = (String)cusBalance.get("bussType");
			Object bussTypeValue = bussTypeMap.get(bussType);
			cusBalance.put("sacCurrencyValue", cusBalance.get("sacCurrency"));
			cusBalance.put("sacCurrency", ccyValue==null?"-":ccyValue);
			cusBalance.put("bussTypeValue", bussTypeValue==null?"-":bussTypeValue+"("+bussType+")");
			//处理金额
			cusBalance.put("totalAmount", cusBalance.get("totalAmount")==null?"0.00":cusBalance.get("totalAmount"));
		
		}
		return cusBalanceList;
	}
	
	  
	
		private void handleSacOtrxInfoList(List<SacOtrxInfo> sacOtrxInfoList) {
			if(sacOtrxInfoList != null && sacOtrxInfoList.size()>0){
				Iterator<SacOtrxInfo> iterator = sacOtrxInfoList.iterator();
				Map<String,Object> bussTypeMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_BUSS_TYPE);
				while(iterator.hasNext()){
					SacOtrxInfo info = iterator.next();

					String bussType = (String)info.getBussType();
					Object bussTypeValue = bussTypeMap.get(bussType);
					info.setTrxType(bussTypeValue==null?"-":bussTypeValue+"("+bussType+")");
				}
			}
			
		}
		
		
		/**
		 * 东方付线下出款
		 * @param request
		 * @param response
		 * @return
		 */
		@RequestMapping(value="/getDffOflCommandInit",method={RequestMethod.GET,RequestMethod.POST})
		public ModelAndView getDffOflCommand(HttpServletRequest request,HttpServletResponse response){
	    	
	    	Model model = new ExtendedModelMap();
	    	
	    	String startDate = request.getParameter("startDate");
	    	
	    	String endDate = request.getParameter("endDate");
	    	
	    	String craccName = request.getParameter("craccName");
	    	
	    	String payAmount = request.getParameter("payAmount");
	    	
	    	String cmdType = request.getParameter("cmdType");
	    	
	    	String cmdState = request.getParameter("cmdState");
	    	
	    	String pageNoStr = Utils.trim(request.getParameter("pageNo"));
	    	
	    	int pageNo = Utils.parseInt(pageNoStr, 1);
	    	
	    	String pageSizeStr = Utils.trim(request.getParameter("pageSize"));
	    	
	    	int pageSize = Utils.parseInt(pageSizeStr, 10);
	    	
	    	Map<String, Object> queryMap = new HashMap<String, Object>();
		
		    if(StringUtils.isBlank(startDate)){
	        	startDate = DateUtil.formatCurrentDate(Calendar.DATE, -7, "yyyyMMdd");
	        }
	        queryMap.put("startDate", startDate);
	        if(StringUtils.isBlank(endDate)){
	       	    endDate = DateUtil.formatCurrentDate(Calendar.DATE,0, "yyyyMMdd");
	        }
	        queryMap.put("endDate", endDate);
	        
	    	queryMap.put("craccName", craccName);
	    	queryMap.put("payAmount", payAmount);
	    	queryMap.put("cmdType", cmdType);
	    	if(StringUtils.isBlank(cmdState)){
	    		queryMap.put("cmdState", CmdState.Init.code());
	    	}else{
	    		queryMap.put("cmdState",cmdState);
	    	}
	    	
	    	Integer count = sacDffOflCommandService.getCommandDetailCounts(queryMap);
	    	
	    	List<SacDffOflCommand> detailList = sacDffOflCommandService.getCommandDetailByPaging(queryMap,pageNo,pageSize);
	    	
	    	model.addAttribute("detailList",detailList);
	    	model.addAttribute("count",count);
	    	model.addAttribute("pageNo",pageNo);
	    	model.addAttribute("startDate", startDate);
	    	model.addAttribute("endDate", endDate);
	    	model.addAttribute("craccName", craccName);
	    	model.addAttribute("payAmount", payAmount);
	    	model.addAttribute("cmdState", cmdState);
	    	model.addAttribute("cmdType", cmdType);
	    	
	    	return new ModelAndView("fundGiveOFLManage/dffOflCommandQueryInit",model.asMap());  
	    }
		
		@RequestMapping(value="/getDffOflCommandDetail",method=RequestMethod.GET)
		public ModelAndView getDffOflCommandDetail(HttpServletRequest request,HttpServletResponse response){
			ModelAndView mav = new ModelAndView("fundGiveOFLManage/dffOflCommandComfirmDetail");
			//组装查询参数
			
			Map<String,Object> queryMap = new HashMap<String, Object>();
			String cmdId = request.getParameter("id");
			queryMap.put("id", cmdId);
	    	List<SacDffOflCommand> detailList = sacDffOflCommandService.getCommandDetailByPaging(queryMap,1,Integer.MAX_VALUE);
			
			if(detailList==null||detailList.size()<=0){
				throw new SacException("999999", "内部错误!");
			}
			SacDffOflCommand cmd = detailList.get(0);
			
			List<UnifiedConfig> bankList = UnifiedConfigSimple.getDicTypeConfig(Constants.BANK_TYPE);
			
			List<UnifiedConfig> branchList = UnifiedConfigSimple.getDicTypeConfig(Constants.BRANCH_TYPE);
			
			Map<String, Object> currencyTypeMap = CacheUtil.getCacheByTypeToMap(Constants.CURRENCY_TYPE);
			String sacCurrency = (String)currencyTypeMap.get(cmd.getPayCurrency());
			//准备初始化参数
			mav.addObject("craccName", cmd.getCraccName());//客户名称
			mav.addObject("payCurrencyName", sacCurrency);
			mav.addObject("payCurrency", cmd.getPayCurrency());
			mav.addObject("craccCardId", cmd.getCraccCardId());
			mav.addObject("bankList", bankList);
			mav.addObject("branchList", branchList);
			mav.addObject("craccBankName", cmd.getCraccBankName());
			mav.addObject("craccNodeCode", cmd.getCraccNodeCode());
			mav.addObject("craccNo", cmd.getCraccNo());
			mav.addObject("ykSerialNo",cmd.getYkSerialNo());
			mav.addObject("skSerialNo",cmd.getSkSerialNo());
			mav.addObject("payAmount",cmd.getPayAmount());
			mav.addObject("id",cmd.getId());
			return mav;
		}
		
		@RequestMapping(value="/dffOflCommandConfirmHandle", method = RequestMethod.POST) 
	    public void dffOflCommandConfirmHandle(HttpServletRequest request, HttpServletResponse response)throws Exception  
	    {
		
		String id = request.getParameter("id");
		String craccCardId = request.getParameter("craccCardId");
		String craccName = request.getParameter("craccName");
		String craccNodeCode = request.getParameter("craccNodeCode");
		String craccBankName = request.getParameter("craccBankName");
		String payCurrency = request.getParameter("payCurrency");
		String payAmount = request.getParameter("payAmount");
		String draccNodeCodeText = request.getParameter("draccNodeCodeText");
		String draccNodeCode = request.getParameter("draccNodeCode");	
		String draccAreaCodeText = request.getParameter("draccAreaCodeText");
		String draccAreaCode = request.getParameter("draccAreaCode");	
		String etrxSerialNo = request.getParameter("etrxSerialNo");
		String skSerialNo = request.getParameter("skSerialNo");
		String ykSerialNo = request.getParameter("ykSerialNo");
		String craccNo = request.getParameter("craccNo");
		
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("id", id);
			map.put("craccCardId", craccCardId);
			map.put("craccName", craccName);
			map.put("craccNodeCode", craccNodeCode);
			map.put("craccBankName", craccBankName);
			map.put("payAmount", payAmount);
			map.put("payCurrency", payCurrency);	
	    	map.put("draccNodeCodeText", draccNodeCodeText);
	    	map.put("draccNodeCode", draccNodeCode);
	    	map.put("draccAreaCodeText", draccAreaCodeText);
	    	map.put("draccAreaCode", draccAreaCode);
	    	map.put("trxType", "610341");  //东方付线下出款
	    	map.put("etrxSerialNo", etrxSerialNo);
	    	map.put("skSerialNo", skSerialNo);
	    	map.put("ykSerialNo", ykSerialNo);
	    	map.put("craccNo", craccNo);
	    	
			String digst = sacCheckInfoService.objectFromMap(map);
			SacCheckInfo sacCheckInfo = new SacCheckInfo();

			sacCheckInfo.setTrxType("610341");
			sacCheckInfo.setDigst(digst);
			sacCheckInfo.setCheckStatus("2");
			sacCheckInfo.setCreateTime(new Date());
			sacCheckInfo.setUpdateTime(new Date());
			sacCheckInfoService.insertSacCheckInfo(sacCheckInfo);
			
			SacDffOflCommand cmd = new SacDffOflCommand();
			cmd.setId(Long.parseLong(id));
			cmd.setCmdState("JS");//制作成功
			sacDffOflCommandService.updateSacB2bCommand(cmd);
			response.getWriter().write("{\"success\":true}");
	    }
		
		
		@RequestMapping(value="/dffCommandCheckInit", method = {RequestMethod.POST,RequestMethod.GET}) 
	    public ModelAndView dffCommandCheckInit(HttpServletRequest request, HttpServletResponse response )throws Exception  
	    {
	    	ModelAndView mav = new ModelAndView("fundGiveOFLManage/dffOflCommandCheckInit");
	    	String checkStatus = request.getParameter("checkStatus");
	    	// 页码处理
	    	List<SacCheckInfo> sacCheckInfoList = new ArrayList<SacCheckInfo>();
			Integer pageNo = request.getParameter("pageNo") != null?Integer.parseInt(request.getParameter("pageNo")):1;// 默认为第1页
			Integer	pageSize = request.getParameter("pageSize") != null?Integer.parseInt(request.getParameter("pageSize")):10;
			SacCheckInfo sacCheckInfo = new SacCheckInfo();
			String createTime = request.getParameter("createTime");
			if(StringUtils.isNotBlank(createTime)){
				sacCheckInfo.setCreateTime(DateUtils.parseDate(createTime, new String[]{"yyyyMMdd"}));
	        }
			sacCheckInfo.setTrxType("610341");
			sacCheckInfo.setCheckStatus(checkStatus);
			sacCheckInfoList = sacCheckInfoService.querySacCheckInfo(sacCheckInfo, pageNo, pageSize);
			List<SacOtrxInfo> sacOtrxInfoList = (List<SacOtrxInfo>) sacCheckInfoService.objectFromList(sacCheckInfoList);
			handleSacOtrxInfoList(sacOtrxInfoList);
	    	mav.addObject("sacOtrxInfoList", sacOtrxInfoList);
	    	mav.addObject("count", sacCheckInfoService.getCheckInfoCount(sacCheckInfo));
	    	mav.addObject("createTime", request.getParameter("createTime"));
	    	mav.addObject("pageNo", pageNo);
			mav.addObject("pageSize", pageSize);
			mav.addObject("checkStatus", request.getParameter("checkStatus"));
	    	return mav;
	    }  
		
		

		@RequestMapping(value = "/dffCommandCheckComfirm", method = RequestMethod.POST)
		public void dffCommandCheckComfirm(HttpServletRequest request,
				HttpServletResponse response)
				throws Exception {
			String id = request.getParameter("id");
			String cmdId = request.getParameter("cmdId");
			String checkStatus = request.getParameter("checkStatus");
			String otrxSerialNo = request.getParameter("otrxSerialNo");
			String etrxSerialNo = request.getParameter("etrxSerialNo");
			String draccNodeCode = request.getParameter("draccNodeCode");
			String draccBankName = request.getParameter("draccBankName");
			String draccAreaCode = request.getParameter("draccAreaCode");
			String craccNo = request.getParameter("craccNo");
			String state = "";
			if ("1".equals(checkStatus)) {
				state = "S";
			}else{
				state = "F";
			}
			String rejectArea = request.getParameter("rejectArea");
			
			Map<String,String> handleMap = new HashMap<String, String>();
			handleMap.put("otrxSerialNo",otrxSerialNo);
			handleMap.put("etrxSerialNo",etrxSerialNo);
			handleMap.put("draccNodeCode",draccNodeCode);
			handleMap.put("draccBankName",draccBankName);
			handleMap.put("draccAreaCode",draccAreaCode);
			handleMap.put("craccNo",craccNo);
			handleMap.put("rejectArea",rejectArea);
			handleMap.put("state",state);

			if (StringUtils.isEmpty(id) || StringUtils.isEmpty(checkStatus)) {
				response.getWriter().write("{\"success\":false,\"message\":\"id为空或者审核状态为空\"}");
				return;
			}
			String msg = "";
			if ("1".equals(checkStatus)||"3".equals(checkStatus)) {
				try {
					msg = sacOflCommandService.NotifyDFFCmdOfl(handleMap);
				} catch (SacException e) {
					String message = e.getMessage();
					response.getWriter().write("{\"success\":false,\"message\":\"" + message + "\"}");
					return;
				}
			}
			sacOperHistoryService.insertSacOperHistory(Constants.OPER_FUND_OFF_GIVE_CHECK, request);
			SacCheckInfo sacCheckInfoTemp = sacCheckInfoService.selectSacCheckInfoById(new Long(id));
			if (sacCheckInfoTemp == null) {
				response.getWriter().write("{\"success\":false,\"message\":\"更新状态失败\"}");
				return;
			}
			
			
			if (StringUtils.isBlank(msg)) {
				SacDffOflCommand cmd = new SacDffOflCommand();
				cmd.setId(Long.parseLong(cmdId));
				cmd.setCmdState(state);//复核成功 或者复核失败或者作废
				sacDffOflCommandService.updateSacB2bCommand(cmd);
				
				sacCheckInfoTemp.setCheckStatus(checkStatus);
				sacCheckInfoService.updateSacCheckInfo(sacCheckInfoTemp);
				response.getWriter().write("{\"success\":true}");
			} else {
				response.getWriter().write("{\"success\":false,\"message\":\"" + msg + "\"}");
			}
		}
		
		@RequestMapping(value = "/dffCommandCancel", method = RequestMethod.POST)
		public void dffCommandCancel(HttpServletRequest request,
				HttpServletResponse response)
				throws Exception {
			String cmdId = request.getParameter("cmdId");
			String checkStatus = request.getParameter("checkStatus");
			String otrxSerialNo = request.getParameter("otrxSerialNo");
			String etrxSerialNo = request.getParameter("etrxSerialNo");
			String draccNodeCode = request.getParameter("draccNodeCode");
			String draccBankName = request.getParameter("draccBankName");
			String draccAreaCode = request.getParameter("draccAreaCode");
			String craccNo = request.getParameter("craccNo");
			String state = "";
			if ("1".equals(checkStatus)) {
				state = "S";
			}else{
				state = "C";
			}
			String rejectArea = request.getParameter("rejectArea");
			
			Map<String,String> handleMap = new HashMap<String, String>();
			handleMap.put("otrxSerialNo",otrxSerialNo);
			handleMap.put("etrxSerialNo",etrxSerialNo);
			handleMap.put("draccNodeCode",draccNodeCode);
			handleMap.put("draccBankName",draccBankName);
			handleMap.put("draccAreaCode",draccAreaCode);
			handleMap.put("craccNo",craccNo);
			handleMap.put("rejectArea",rejectArea);
			handleMap.put("state",state);

			if (StringUtils.isEmpty(checkStatus)) {
				response.getWriter().write("{\"success\":false,\"message\":\"审核状态为空\"}");
				return;
			}
			String msg = "";
			if ("3".equals(checkStatus)) {
				try {
					msg = sacOflCommandService.NotifyDFFCmdOfl(handleMap);
				} catch (SacException e) {
					String message = e.getMessage();
					response.getWriter().write("{\"success\":false,\"message\":\"" + message + "\"}");
					return;
				}
			}
			sacOperHistoryService.insertSacOperHistory(Constants.OPER_FUND_OFF_GIVE_CHECK, request);
			
			if (StringUtils.isBlank(msg)) {
				SacDffOflCommand cmd = new SacDffOflCommand();
				cmd.setId(Long.parseLong(cmdId));
				cmd.setCmdState(state);//复核成功 或者复核失败或者作废
				sacDffOflCommandService.updateSacB2bCommand(cmd);
				response.getWriter().write("{\"success\":true}");
			} else {
				response.getWriter().write("{\"success\":false,\"message\":\"" + msg + "\"}");
			}
		}
    
}
