package net.easipay.cbp.view.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.easipay.cbp.cache.CacheUtil;
import net.easipay.cbp.constant.Constants;
import net.easipay.cbp.controller.BaseController;
import net.easipay.cbp.model.SacChannelParam;
import net.easipay.cbp.model.SacCheckInfo;
import net.easipay.cbp.model.SacChnSettlement;
import net.easipay.cbp.model.SacCusParameter;
import net.easipay.cbp.model.SacCusSettlement;
import net.easipay.cbp.model.SacFundTransferCmd;
import net.easipay.cbp.model.SacOtrxInfo;
import net.easipay.cbp.model.SacTrxDetail;
import net.easipay.cbp.model.form.PrestoreDetailReceiveForm;
import net.easipay.cbp.sequence.SequenceCreatorUtil;
import net.easipay.cbp.service.INotifyOperResultToB2BService;
import net.easipay.cbp.service.ISacChannelParamService;
import net.easipay.cbp.service.ISacCheckInfoService;
import net.easipay.cbp.service.ISacChnSettlementService;
import net.easipay.cbp.service.ISacCusParameterService;
import net.easipay.cbp.service.ISacCusSettlementService;
import net.easipay.cbp.service.ISacFundTransferCmdService;
import net.easipay.cbp.service.ISacOperHistoryService;
import net.easipay.cbp.service.ISacOtrxInfoService;
import net.easipay.cbp.service.ISacTrxDetailService;
import net.easipay.cbp.service.ISysDicService;
import net.easipay.cbp.service.ITransactionService;
import net.easipay.cbp.util.BeanUtils;
import net.easipay.cbp.util.ConstantParams;
import net.easipay.cbp.util.DateUtil;
import net.easipay.cbp.util.Utils;
import net.easipay.dsfc.UnifiedConfig;
import net.easipay.dsfc.UnifiedConfigSimple;
import net.easipay.dsfc.ws.jws.JwsResult;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
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
 * 资金管理
 * @author jjiang
 *
 */
@Controller
public class FundManageController extends BaseController { 
	
	private static final Logger logger = Logger.getLogger(FundManageController.class);
	
	@Autowired
	private ISacChnSettlementService sacChnSettlementService;
	
	@Autowired
	private ISacCusSettlementService sacCusSettlementService;
    
	@Autowired
	private ISacOtrxInfoService sacOtrxInfoService;
	
	@Autowired
	private ISacChannelParamService sacChannelParamService;
	
	@Autowired
	private ITransactionService transactionService;
	
	@Autowired
	private ISacOperHistoryService sacOperHistoryService;

	@Autowired
	private ISacFundTransferCmdService fundTransferCmdService;
	
	@Autowired
	private ISysDicService sysDicService;
	
    @Autowired
    private ISacCusParameterService sacCusParameterService;
    
    @Autowired
    private ISacCheckInfoService sacCheckInfoService;
    
    @Autowired
	private ISacTrxDetailService sacTrxDetailService;
    
    @Autowired
    private INotifyOperResultToB2BService notifyOperResultToB2BService;
	
    
	
	/**
	 * 实收勾兑始化页面
	 * @param request
	 * @param response
	 * @param sacChnSettlement
	 * @return
	 * @throws Exception
	 */
    @RequestMapping(value="/realReceiveWipeInit", method = RequestMethod.GET) 
    public ModelAndView realReceiveWipeInit(HttpServletRequest request, HttpServletResponse response ,SacChnSettlement sacChnSettlement)throws Exception  
    {
    	
    	Model model = new ExtendedModelMap();
    	
 		String pageNoStr = Utils.trim(request.getParameter("pageNo"));
 		
        int pageNo = Utils.parseInt(pageNoStr, 1);
        
        String pageSizeStr = Utils.trim(request.getParameter("pageSize"));
        
        String startSacDate = request.getParameter("startSacDate");
        
        int pageSize = Utils.parseInt(pageSizeStr, 10);
        
        sacChnSettlement.setType("N");//正常的
        
        if(StringUtils.isBlank(startSacDate)){
        	startSacDate = DateUtil.formatCurrentDate(Calendar.DATE, -7, "yyyyMMdd");
        }
        
        Integer count = sacChnSettlementService.selectSacChnSettlementCounts(sacChnSettlement);
        
        List<SacChnSettlement> sacChnSettlementList = sacChnSettlementService.selectSacChnSettlement(sacChnSettlement, pageNo, pageSize);
        
        Iterator<SacChnSettlement> it = sacChnSettlementList.iterator();

        while(it.hasNext()){
        	SacChnSettlement cs = it.next();
        	String bankNodeCode = cs.getBankNodeCode();
        	Boolean flag = "CMBC000".equals(bankNodeCode)||"CMB0000".equals(bankNodeCode)||"CCB0000".equals(bankNodeCode)||"BOC0000".equals(bankNodeCode);
        	if(flag){
        		
        		BigDecimal realTotAmount = cs.getRealTotAmount();
        		
        		String transDate = cs.getTransDate();
        		
        		String finSign = cs.getFinSign();
        		
        		if(realTotAmount!=null&&realTotAmount.compareTo(BigDecimal.ZERO)==0&&"N".equals(finSign)){
        			
        			Map<String,Object> queryMap = new HashMap<String, Object>();
        			
        			queryMap.put("startDate",transDate);
        	    	
        			queryMap.put("endDate", transDate);
        	    	
        			String bankCode = "";
        			if("CMBC000".equals(bankNodeCode)){
        				bankCode = "0305";//民生
        			}else if("CMB0000".equals(bankNodeCode)){
        				bankCode = "0308";//招商
        			}else if("CCB0000".equals(bankNodeCode)){
        				bankCode = "0105";//建行
        			}else if("BOC0000".equals(bankNodeCode)){
        				bankCode = "0104";//中行
        			}
        			queryMap.put("bankCode", bankCode);

        			queryMap.put("direction", "1");//来款
        			BigDecimal amount = new BigDecimal("0.00");
        			try {
        				JwsResult result = transactionService.getPrestoreTrxFromDSF(queryMap);
        				List<PrestoreDetailReceiveForm> detailList = result.getList("requestData", PrestoreDetailReceiveForm.class);
            	    	for(PrestoreDetailReceiveForm pr : detailList){
            	    		amount = amount.add(new BigDecimal(pr.getAmount()));
            	    	}
					} catch (Exception e) {
						logger.error("调用代收付报错",e);
					}
        			cs.setRealTotAmount(amount);
        		}
        	}
        	
        	
        }
        
        List<UnifiedConfig> sysDicStatusList = UnifiedConfigSimple.getDicTypeConfig(Constants.WIPE_STATE);
        
 		List<UnifiedConfig> currencyTypeList = UnifiedConfigSimple.getDicTypeConfig(Constants.CURRENCY_TYPE);
 		
        model.addAttribute("count", count.intValue());
        
        model.addAttribute("pageNo", pageNo);
        
        model.addAttribute("startSacDate", startSacDate);
 		
 		model.addAttribute("sacChnSettlementList", sacChnSettlementList);
 		
 		model.addAttribute("currencyTypeList", currencyTypeList);
 		
 		model.addAttribute("sysDicStatusList", sysDicStatusList);
    	 
        return new ModelAndView("/fundManage/realReceiveWipeInit", model.asMap()); 
    }  
    
    /**  
     * 实收勾兑查询
     * @param request
     * @param response
     * @param sacRecDifferences
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/realReceiveWipeInit", method = RequestMethod.POST) 
    public ModelAndView realReceiveWipeQueryInit(HttpServletRequest request, HttpServletResponse response ,SacChnSettlement sacChnSettlement)throws Exception  
    {
    	
    	Model model = new ExtendedModelMap();
    	
 		String pageNoStr = Utils.trim(request.getParameter("pageNo"));
 		
 		String pageSizeStr = Utils.trim(request.getParameter("pageSize"));
 		
 	    String startSacDate = request.getParameter("startSacDate");
 	    
        int pageNo = Utils.parseInt(pageNoStr, 1);
        
        int pageSize = Utils.parseInt(pageSizeStr, 10);
        
        if(StringUtils.isBlank(startSacDate)){
        	startSacDate = DateUtil.formatCurrentDate(Calendar.DATE, -7, "yyyyMMdd");
        }
        
        sacChnSettlement.setType("N");//正常的
        
        Integer count = sacChnSettlementService.selectSacChnSettlementCounts(sacChnSettlement);
        
        List<SacChnSettlement> sacChnSettlementList = sacChnSettlementService.selectSacChnSettlement(sacChnSettlement, pageNo,pageSize);
        
        Iterator<SacChnSettlement> it = sacChnSettlementList.iterator();

        while(it.hasNext()){
        	SacChnSettlement cs = it.next();
        	String bankNodeCode = cs.getBankNodeCode();
        	Boolean flag = "CMBC000".equals(bankNodeCode)||"CMB0000".equals(bankNodeCode)||"CCB0000".equals(bankNodeCode)||"BOC0000".equals(bankNodeCode);
        	if(flag){
        		
        		BigDecimal realTotAmount = cs.getRealTotAmount();
        		
        		String transDate = cs.getTransDate();
        		
        		String finSign = cs.getFinSign();
        		
        		if(realTotAmount!=null&&realTotAmount.compareTo(BigDecimal.ZERO)==0&&"N".equals(finSign)){
        			
        			Map<String,Object> queryMap = new HashMap<String, Object>();
        			
        			queryMap.put("startDate",transDate);
        	    	
        			queryMap.put("endDate", transDate);
        	    	
        			String bankCode = "";
        			if("CMBC000".equals(bankNodeCode)){
        				bankCode = "0305";//民生
        			}else if("CMB0000".equals(bankNodeCode)){
        				bankCode = "0308";//招商
        			}else if("CCB0000".equals(bankNodeCode)){
        				bankCode = "0105";//建行
        			}else if("BOC0000".equals(bankNodeCode)){
        				bankCode = "0104";//中行
        			}
        			queryMap.put("bankCode", bankCode);

        			queryMap.put("direction", "1");//来款
        			BigDecimal amount = new BigDecimal("0.00");
        			try {
        				JwsResult result = transactionService.getPrestoreTrxFromDSF(queryMap);
        				List<PrestoreDetailReceiveForm> detailList = result.getList("requestData", PrestoreDetailReceiveForm.class);
            	    	for(PrestoreDetailReceiveForm pr : detailList){
            	    		amount = amount.add(new BigDecimal(pr.getAmount()));
            	    	}
					} catch (Exception e) {
						logger.error("调用代收付报错",e);
					}
        			cs.setRealTotAmount(amount);
        		}
        	}
        	
        	
        }

        List<UnifiedConfig> sysDicStatusList = UnifiedConfigSimple.getDicTypeConfig(Constants.WIPE_STATE);
        
 		List<UnifiedConfig> currencyTypeList = UnifiedConfigSimple.getDicTypeConfig(Constants.CURRENCY_TYPE);
 		
        model.addAttribute("pageSize", pageSize);
        
        model.addAttribute("count", count.intValue());
        
        model.addAttribute("pageNo", pageNo);
        
        model.addAttribute("startSacDate", startSacDate);
 		
 		model.addAttribute("sacChnSettlementList", sacChnSettlementList);
 		
 		model.addAttribute("currencyTypeList", currencyTypeList);
 		
 		model.addAttribute("sysDicStatusList", sysDicStatusList);
    	 
        return new ModelAndView("/fundManage/realReceiveWipeInit", model.asMap()); 
    }  
    
  
    	/**
    	 * 勾兑处理
    	 * @param request
    	 * @param response
    	 * @param sacRefund
    	 * @return
    	 * @throws Exception
    	 */
		@RequestMapping(value="/realReceiveWipe",method = RequestMethod.POST)
	    public void realReceiveWipe(HttpServletRequest request,
                HttpServletResponse response,SacChnSettlement sacChnSettlement)
			throws Exception {
			
			BigDecimal realTotAmount = sacChnSettlement.getRealTotAmount();
			
			sacChnSettlement = sacChnSettlementService.selectSacChnSettlementById(sacChnSettlement);
			String finSign = sacChnSettlement.getFinSign();
			if("N".equals(finSign)){
				sacChnSettlementService.dealRealReceiveWipe(sacChnSettlement,realTotAmount);
				sacOperHistoryService.insertSacOperHistory(Constants.OPER_REAL_RECEIVE,request);//实收勾兑
				response.getWriter().write("{\"success\":true}");
			}
			
			
		}
		/**
		 * 批量勾兑处理
		 * @param request
		 * @param response
		 * @param sacRefund
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value="/realReceiveWipeByBatch",method = RequestMethod.POST)
		public void realReceiveWipeByBatch(HttpServletRequest request,
				HttpServletResponse response)
						throws Exception {
			String batchWipeStr = request.getParameter("batchWipeStr");
			String[] wipeStr = batchWipeStr.split("\\|");
			for(int i=0;i<wipeStr.length;i++){
				SacChnSettlement settlement = new SacChnSettlement(); 
				String wipeObj = wipeStr[i];
				String[] wipe = wipeObj.split("_");
				String idStr = wipe[0];
				String amountStr = wipe[1];
				Long id = Long.parseLong(idStr);
				BigDecimal realTotAmount =new BigDecimal(amountStr);
				settlement.setId(id);
				settlement = sacChnSettlementService.selectSacChnSettlementById(settlement);
				String finSign = settlement.getFinSign();
				if("N".equals(finSign)){
					sacChnSettlementService.dealRealReceiveWipe(settlement,realTotAmount);
				}
			
			}
			sacOperHistoryService.insertSacOperHistory(Constants.OPER_REAL_RECEIVE,request);//实收勾兑
			response.getWriter().write("{\"success\":true}");
		}
  
		
		/**
		 * 实付勾兑始化页面
		 * @param request
		 * @param response
		 * @param sacChnSettlement
		 * @return
		 * @throws Exception
		 */
	    @RequestMapping(value="/realPayWipeInit", method = RequestMethod.GET) 
	    public ModelAndView realPayWipeInit(HttpServletRequest request, HttpServletResponse response ,SacCusSettlement sacCusSettlement)throws Exception  
	    {
	    	
	    	Model model = new ExtendedModelMap();
	    	
	 		String pageNoStr = Utils.trim(request.getParameter("pageNo"));
	 		
	        int pageNo = Utils.parseInt(pageNoStr, 1);
	        
	        String pageSizeStr = Utils.trim(request.getParameter("pageSize"));
	        
	        int pageSize = Utils.parseInt(pageSizeStr, 10);
	        
	        Integer count = sacCusSettlementService.selectSacCusSettlementCounts(sacCusSettlement);
	        
	        List<SacCusSettlement> sacCusSettlementList = sacCusSettlementService.selectSacCusSettlement(sacCusSettlement, pageNo, pageSize);

	        List<UnifiedConfig> wipeList = UnifiedConfigSimple.getDicTypeConfig(Constants.WIPE_STATE);
	        
	 		List<UnifiedConfig> settlementList = UnifiedConfigSimple.getDicTypeConfig(Constants.SETTLEMENT_STATE);
	 		
	        model.addAttribute("count", count.intValue());
	        
	        model.addAttribute("pageNo", pageNo);
	 		
	 		model.addAttribute("sacCusSettlementList", sacCusSettlementList);
	 		
	 		model.addAttribute("settlementList", settlementList);
	 		
	 		model.addAttribute("wipeList", wipeList);
	    	 
	        return new ModelAndView("/fundManage/realPayWipeInit", model.asMap()); 
	    }  
	    
	    /**  
	     * 实付勾兑查询
	     * @param request
	     * @param response
	     * @param sacRecDifferences
	     * @return
	     * @throws Exception
	     */
	    @RequestMapping(value="/realPayWipeInit", method = RequestMethod.POST) 
	    public ModelAndView realPayWipeQueryInit(HttpServletRequest request, HttpServletResponse response ,SacCusSettlement sacCusSettlement)throws Exception  
	    {
	    	
	    	Model model = new ExtendedModelMap();
	    	
	 		String pageNoStr = Utils.trim(request.getParameter("pageNo"));
	 		
	 		String pageSizeStr = Utils.trim(request.getParameter("pageSize"));
	 		
	        int pageNo = Utils.parseInt(pageNoStr, 1);
	        
	        int pageSize = Utils.parseInt(pageSizeStr, 10);
	        
	        Integer count = sacCusSettlementService.selectSacCusSettlementCounts(sacCusSettlement);
	        
	        List<SacCusSettlement> sacCusSettlementList = sacCusSettlementService.selectSacCusSettlement(sacCusSettlement, pageNo,pageSize);

	        List<UnifiedConfig> wipeList = UnifiedConfigSimple.getDicTypeConfig(Constants.WIPE_STATE);
	        
	 		List<UnifiedConfig> settlementList = UnifiedConfigSimple.getDicTypeConfig(Constants.SETTLEMENT_STATE);
	        
	        model.addAttribute("pageSize", pageSize);
	        
	        model.addAttribute("count", count.intValue());
	        
	        model.addAttribute("pageNo", pageNo);
	        
	        model.addAttribute("settlementList", settlementList);
	 		
	 		model.addAttribute("sacCusSettlementList", sacCusSettlementList);
	 		
	 		model.addAttribute("wipeList", wipeList);
	    	 
	        return new ModelAndView("/fundManage/realPayWipeInit", model.asMap()); 
	    }  
	    
	  
	    	/**
	    	 * 实付勾兑处理
	    	 * @param request
	    	 * @param response
	    	 * @param sacRefund
	    	 * @return
	    	 * @throws Exception
	    	 */
			@RequestMapping(value="/realPayWipe",method = RequestMethod.POST)
		    public void realPayWipe(HttpServletRequest request,
	                HttpServletResponse response,SacCusSettlement sacCusSettlement)
				throws Exception {
				
				sacCusSettlement = sacCusSettlementService.selectSacCusSettlementById(sacCusSettlement);
				String finSign = sacCusSettlement.getFinSign();
				String transferStatus = sacCusSettlement.getTransferStatus();
				String ccy = sacCusSettlement.getCurrencyType();
				if("N".equals(finSign)&&"Y".equals(transferStatus)&&!"CNY".equals(ccy)){
					sacCusSettlementService.dealRealPayWipe(sacCusSettlement);//实付勾兑处理
					response.getWriter().write("{\"success\":true}");
				}
			}
			
			/**
			 * 结算划款始化页面
			 * @param request
			 * @param response
			 * @param sacChnSettlement
			 * @return
			 * @throws Exception
			 */
		    @RequestMapping(value="/settleAllotInit", method = RequestMethod.GET) 
		    public ModelAndView settleAllotInit(HttpServletRequest request, HttpServletResponse response ,SacCusSettlement sacCusSettlement)throws Exception  
		    {
		    	
		    	Model model = new ExtendedModelMap();
		    	
		 		String pageNoStr = Utils.trim(request.getParameter("pageNo"));
		 		
		        int pageNo = Utils.parseInt(pageNoStr, 1);
		        
		        String pageSizeStr = Utils.trim(request.getParameter("pageSize"));
		        
		        int pageSize = Utils.parseInt(pageSizeStr, 10);
		        
		        Integer count = sacCusSettlementService.selectSacCusSettlementCounts(sacCusSettlement);
		        
		        List<SacCusSettlement> sacCusSettlementList = sacCusSettlementService.selectSacCusSettlement(sacCusSettlement, pageNo, pageSize);
		        
		        Map<String,Object> trxTypeMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_TRX_TYPE);
		        
		        Map<String,Object> bussTypeMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_BUSS_TYPE);
		        
		        List<Map<String,Object>> cusSettlementMapList = new ArrayList<Map<String,Object>>();
		        
		        for(SacCusSettlement cusSettlement : sacCusSettlementList){
		        	String trxType = cusSettlement.getTrxType();
		        	String trxTypeDesc = trxType;
		        	
		        	String bussType = cusSettlement.getBussType();
		        	String bussTypeDesc = bussType;
		        	if(trxTypeMap.get(trxType)!=null){
		        		trxTypeDesc = (String)trxTypeMap.get(trxType);
		        	}
		        	if(bussTypeMap.get(bussType)!=null){
		        		bussTypeDesc = (String)bussTypeMap.get(bussType);
		        	}
		        	Map<String, Object> cusSettlementMap = BeanUtils.transBean2Map(cusSettlement);
		        	cusSettlementMap.put("trxTypeDesc", trxTypeDesc);
		        	cusSettlementMap.put("bussTypeDesc", bussTypeDesc);
		        	cusSettlementMapList.add(cusSettlementMap);
		        }

		        List<UnifiedConfig> bussTypeList = UnifiedConfigSimple.getDicTypeConfig(Constants.BUSS_TYPE);
		        
		 		List<UnifiedConfig> transactionList = UnifiedConfigSimple.getDicTypeConfig(Constants.TRANSACTION_STATE);
		 		
		 		List<UnifiedConfig> customerTypeList = UnifiedConfigSimple.getDicTypeConfig(Constants.CUSTOMER_TYPE);
		 		
		 		List<UnifiedConfig> payTypeList = UnifiedConfigSimple.getDicTypeConfig(Constants.PAY_TYPE);
		 		
		 		List<UnifiedConfig> currencyTypeList = UnifiedConfigSimple.getDicTypeConfig(Constants.CURRENCY_TYPE);
		 		
		 		List<UnifiedConfig> trxTypeList = UnifiedConfigSimple.getDicTypeConfig(Constants.TRX_TYPE);
		 		
		 		/*List<SacChannelParam> sacChannelParamList = sacChannelParamService.selectAllSacChannelParam();
		 		if(sacChannelParamList.size()>0){
		 			Map<String,Object> bankNameMap = new HashMap<String,Object>();
			 		for(Iterator<SacChannelParam> it = sacChannelParamList.iterator(); it.hasNext();){
			 			SacChannelParam sacChannelParam = (SacChannelParam)it.next();
			 			String bankCode = sacChannelParam.getBankNodeCode();//银行节点代码
			 			String bankName = sacChannelParam.getChnName();//银行名称
			 			if(bankNameMap.containsKey(bankCode)){
			 				it.remove();
			 				continue;
			 			}
			 			bankNameMap.put(bankCode, bankName);
			 			
			 		}
		 		}
		 		
		 		model.addAttribute("sacChannelParamList", sacChannelParamList);*/
		 		
		 		model.addAttribute("payTypeList", payTypeList);
		 		
		 		model.addAttribute("currencyTypeList", currencyTypeList);
		 		
		 		model.addAttribute("transactionList", transactionList);
		 		
		 		model.addAttribute("customerTypeList", customerTypeList);
		 		
		        model.addAttribute("count", count.intValue());
		        
		        model.addAttribute("pageNo", pageNo);
		 		
		 		model.addAttribute("sacCusSettlementList", cusSettlementMapList);
		 		
		 		model.addAttribute("trxTypeList", trxTypeList);
		 		
		 		model.addAttribute("bussTypeList", bussTypeList);
		    	 
		        return new ModelAndView("/fundManage/settleAllotInit", model.asMap()); 
		    }  
		    
		    /**  
		     * 结算划款查询
		     * @param request
		     * @param response
		     * @param sacRecDifferences
		     * @return
		     * @throws Exception
		     */
		    @RequestMapping(value="/settleAllotInit", method = RequestMethod.POST) 
		    public ModelAndView settleAllotQueryInit(HttpServletRequest request, HttpServletResponse response ,SacCusSettlement sacCusSettlement)throws Exception  
		    {
		    	
		    	Model model = new ExtendedModelMap();
		    	
		 		String pageNoStr = Utils.trim(request.getParameter("pageNo"));
		 		
		 		String pageSizeStr = Utils.trim(request.getParameter("pageSize"));
		 		
		        int pageNo = Utils.parseInt(pageNoStr, 1);
		        
		        int pageSize = Utils.parseInt(pageSizeStr, 10);
		        
		        Integer count = sacCusSettlementService.selectSacCusSettlementCounts(sacCusSettlement);
		        
		        List<SacCusSettlement> sacCusSettlementList = sacCusSettlementService.selectSacCusSettlement(sacCusSettlement, pageNo,pageSize);
		        
		        Map<String,Object> trxTypeMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_TRX_TYPE);
		        
		        Map<String,Object> bussTypeMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_BUSS_TYPE);
		        
		        List<Map<String,Object>> cusSettlementMapList = new ArrayList<Map<String,Object>>();
		        for(SacCusSettlement cusSettlement : sacCusSettlementList){
		        	String trxType = cusSettlement.getTrxType();
		        	String trxTypeDesc = trxType;
		        	
		        	String bussType = cusSettlement.getBussType();
		        	String bussTypeDesc = bussType;
		        	if(trxTypeMap.get(trxType)!=null){
		        		trxTypeDesc = (String)trxTypeMap.get(trxType);
		        	}
		        	if(bussTypeMap.get(bussType)!=null){
		        		bussTypeDesc = (String)bussTypeMap.get(bussType);
		        	}
		        	Map<String, Object> cusSettlementMap = BeanUtils.transBean2Map(cusSettlement);
		        	cusSettlementMap.put("trxTypeDesc", trxTypeDesc);
		        	cusSettlementMap.put("bussTypeDesc", bussTypeDesc);
		        	cusSettlementMapList.add(cusSettlementMap);
		        }
		 		
		        List<UnifiedConfig> bussTypeList = UnifiedConfigSimple.getDicTypeConfig(Constants.BUSS_TYPE);
		        
		 		List<UnifiedConfig> transactionList = UnifiedConfigSimple.getDicTypeConfig(Constants.TRANSACTION_STATE);
		 		
		 		List<UnifiedConfig> customerTypeList = UnifiedConfigSimple.getDicTypeConfig(Constants.CUSTOMER_TYPE);
		 		
		 		List<UnifiedConfig> payTypeList = UnifiedConfigSimple.getDicTypeConfig(Constants.PAY_TYPE);
		 		
		 		List<UnifiedConfig> currencyTypeList = UnifiedConfigSimple.getDicTypeConfig(Constants.CURRENCY_TYPE);
		 		
		 		List<UnifiedConfig> trxTypeList = UnifiedConfigSimple.getDicTypeConfig(Constants.TRX_TYPE);
		 		
		 		/*List<SacChannelParam> sacChannelParamList = sacChannelParamService.selectAllSacChannelParam();
		 		if(sacChannelParamList.size()>0){
		 			Map<String,Object> bankNameMap = new HashMap<String,Object>();
			 		for(Iterator<SacChannelParam> it = sacChannelParamList.iterator(); it.hasNext();){
			 			SacChannelParam sacChannelParam = (SacChannelParam)it.next();
			 			String bankCode = sacChannelParam.getBankNodeCode();//银行节点代码
			 			String bankName = sacChannelParam.getChnName();//银行名称
			 			if(bankNameMap.containsKey(bankCode)){
			 				it.remove();
			 				continue;
			 			}
			 			bankNameMap.put(bankCode, bankName);
			 			
			 		}
		 		}
		 		
		 		model.addAttribute("sacChannelParamList", sacChannelParamList);*/
		 		
		 		model.addAttribute("payTypeList", payTypeList);
		 		
		 		model.addAttribute("currencyTypeList", currencyTypeList);
		 		
		 		model.addAttribute("transactionList", transactionList);
		 		
		 		model.addAttribute("customerTypeList", customerTypeList);
		 		
		        model.addAttribute("pageSize", pageSize);
		        
		        model.addAttribute("count", count.intValue());
		        
		        model.addAttribute("pageNo", pageNo);
		        
		        model.addAttribute("trxTypeList", trxTypeList);
		 		
		 		model.addAttribute("sacCusSettlementList", cusSettlementMapList);
		 		
		 		model.addAttribute("bussTypeList", bussTypeList);
		    	 
		        return new ModelAndView("/fundManage/settleAllotInit", model.asMap()); 
		    }  
		    
		  
	    	/**
	    	 * RMB划款处理
	    	 * @param request
	    	 * @param response
	    	 * @param sacRefund
	    	 * @return
	    	 * @throws Exception
	    	 */
			@RequestMapping(value="/settleAllot",method = RequestMethod.POST)
		    public void settleAllot(HttpServletRequest request,
	                HttpServletResponse response,SacCusSettlement sacCusSettlement)
				throws Exception {
				
				sacCusSettlement = sacCusSettlementService.selectSacCusSettlementById(sacCusSettlement);
				
				String finSign = sacCusSettlement.getFinSign();
				
				String transferStatus = sacCusSettlement.getTransferStatus();
				
				String ccy = sacCusSettlement.getCurrencyType();
				
				if("N".equals(finSign)&&"N".equals(transferStatus)&&"CNY".equals(ccy)){
					
					sacCusSettlementService.dealSettleAllot(sacCusSettlement);
					
					response.getWriter().write("{\"success\":true}");
				}
				
				
			}
			
	    	/**
	    	 * 划款响应接口CNY
	    	 * @param request
	    	 * @param response
	    	 * @param sacRefund
	    	 * @return
	    	 * @throws Exception
	    	 */
			@RequestMapping(value="/settleAllotResponse",method = RequestMethod.POST)
		    public void settleAllotResponse(HttpServletRequest request,
	                HttpServletResponse response,SacCusSettlement sacCusSettlement)
				throws Exception {
				
				sacCusSettlement = sacCusSettlementService.selectSacCusSettlementById(sacCusSettlement);
				String finSign = sacCusSettlement.getFinSign();
				String transferStatus = sacCusSettlement.getTransferStatus();
				String ccy = sacCusSettlement.getCurrencyType();
				if("N".equals(finSign)&&"W".equals(transferStatus)&&"CNY".equals(ccy)){
					sacCusSettlementService.dealSettleAllotResponse(sacCusSettlement);
					
					response.getWriter().write("{\"success\":true}");
				}
				
				
			}
			
			/**  
		     * 非人民币划款录入页面初始化
		     * @param request
		     * @param response
		     * @param sacRecDifferences
		     * @return
		     * @throws Exception
		     */
		    @RequestMapping(value="/settleAllotAddDetail", method = RequestMethod.POST) 
		    public ModelAndView settleAllotAddDetail(HttpServletRequest request, HttpServletResponse response ,SacOtrxInfo sacOtrxInfo)throws Exception  
		    {
		    	
		    	Model model = new ExtendedModelMap();
		    	
		        return new ModelAndView("/fundManage/settleDetail", model.asMap()); 
		    }  
		    
		    /**  
		     * 非人民币划款录入
		     * @param request
		     * @param response
		     * @param sacRecDifferences
		     * @return
		     * @throws Exception
		     */
		    @RequestMapping(value="/settleConfirm", method = RequestMethod.POST) 
		    public void settleConfirm(HttpServletRequest request, HttpServletResponse response ,@Valid SacOtrxInfo sacOtrxInfo,BindingResult errors)throws Exception  
		    {
		    	
		    	if (errors.hasErrors()) {
					List<ObjectError> errorList = errors.getAllErrors();
					for (ObjectError objectError : errorList) {
						logger.debug("objectError=" + objectError.toString());
					}
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("message", errorList.get(0).getDefaultMessage());
					map.put("success", false);
					map.put("filed", errorList.get(0).getArguments());
					map.put("valid", true);
					JSONObject jct = JSONObject.fromObject(map);
					response.getWriter().write(jct.toString());
					return;
				}
		    	//TODO 调用账务系统交易接口创建待支付（待复核）交易
		    	sacOtrxInfo.setTrxType(Constants.SETTLE_ALLOT);//结算划款交易类型
		    	sacOtrxInfo.setTrxSerialNo(SequenceCreatorUtil.generateTrxSerialNo());
				sacOtrxInfo.setBussType("20");//业务类型
				sacOtrxInfo.setPayconType("1");//支付渠道
		    	//transactionService.transactionDeal(sacOtrxInfo);
		    	response.getWriter().write("{\"success\":true}");
		    	
		    }
		    
		    /**
			 *结算划款复核始化页面
			 * @param request
			 * @param response
			 * @param sacChnSettlement
			 * @return
			 * @throws Exception
			 */
		    @RequestMapping(value="/settleRecheckInit", method = RequestMethod.GET) 
		    public ModelAndView settleRecheckInit(HttpServletRequest request, HttpServletResponse response ,SacOtrxInfo sacOtrxInfo)throws Exception  
		    {
		    	
		    	Model model = new ExtendedModelMap();
		    	
		 		String pageNoStr = Utils.trim(request.getParameter("pageNo"));
		 		
		        int pageNo = Utils.parseInt(pageNoStr, 1);
		        
		        String pageSizeStr = Utils.trim(request.getParameter("pageSize"));
		        
		        int pageSize = Utils.parseInt(pageSizeStr, 10);
		        
		        Integer count = sacOtrxInfoService.selectSacOtrxInfoCounts(sacOtrxInfo);
		        
		        sacOtrxInfo.setTrxType(Constants.SETTLE_ALLOT);//结算划款
		        
		        List<SacOtrxInfo> sacOtrxInfoList = sacOtrxInfoService.selectSacOtrxInfoByParam(sacOtrxInfo, pageNo, pageSize);

		 		List<UnifiedConfig> customerTypeList = UnifiedConfigSimple.getDicTypeConfig(Constants.CUSTOMER_TYPE);
		 		
		 		List<UnifiedConfig> currencyTypeList = UnifiedConfigSimple.getDicTypeConfig(Constants.CURRENCY_TYPE);
		 		
		 		List<UnifiedConfig> transactionList = UnifiedConfigSimple.getDicTypeConfig(Constants.TRANSACTION_STATE);
		 		
		 		List<SacChannelParam> sacChannelParamList = sacChannelParamService.selectAllSacChannelParam();
		 		if(sacChannelParamList.size()>0){
		 			Map<String,Object> bankNameMap = new HashMap<String,Object>();
			 		for(Iterator<SacChannelParam> it = sacChannelParamList.iterator(); it.hasNext();){
			 			SacChannelParam sacChannelParam = (SacChannelParam)it.next();
			 			String bankCode = sacChannelParam.getBankNodeCode();//银行节点代码
			 			String bankName = sacChannelParam.getChnName();//银行名称
			 			if(bankNameMap.containsKey(bankCode)){
			 				it.remove();
			 				continue;
			 			}
			 			bankNameMap.put(bankCode, bankName);
			 			
			 		}
		 		}
		 		
		 		model.addAttribute("sacChannelParamList", sacChannelParamList);
		 		
		 		model.addAttribute("customerTypeList", customerTypeList);
		 		
		        model.addAttribute("count", count.intValue());
		        
		        model.addAttribute("pageNo", pageNo);
		 		
		 		model.addAttribute("sacOtrxInfoList", sacOtrxInfoList);
		 		
		 		model.addAttribute("transactionList", transactionList);
		 		
		 		model.addAttribute("currencyList", currencyTypeList);
		    	 
		        return new ModelAndView("/fundManage/settleRecheckInit", model.asMap()); 
		    }  
		    
		    /**  
		     * 结算划款复核信息查询
		     * @param request
		     * @param response
		     * @param sacRecDifferences
		     * @return
		     * @throws Exception
		     */
		    @RequestMapping(value="/settleRecheckInit", method = RequestMethod.POST) 
		    public ModelAndView settleRecheckQueryInit(HttpServletRequest request, HttpServletResponse response ,SacOtrxInfo sacOtrxInfo)throws Exception  
		    {
		    	
		    	Model model = new ExtendedModelMap();
		    	
		 		String pageNoStr = Utils.trim(request.getParameter("pageNo"));
		 		
		        int pageNo = Utils.parseInt(pageNoStr, 1);
		        
		        String pageSizeStr = Utils.trim(request.getParameter("pageSize"));
		        
		        int pageSize = Utils.parseInt(pageSizeStr, 10);
		        
		        sacOtrxInfo.setTrxType(Constants.SETTLE_ALLOT);//结算划款
		        
		        Integer count = sacOtrxInfoService.selectSacOtrxInfoCounts(sacOtrxInfo);
		        
		        List<SacOtrxInfo> sacOtrxInfoList = sacOtrxInfoService.selectSacOtrxInfoByParam(sacOtrxInfo, pageNo, pageSize);

		        List<UnifiedConfig> customerTypeList = UnifiedConfigSimple.getDicTypeConfig(Constants.CUSTOMER_TYPE);
		 		
		 		List<UnifiedConfig> currencyTypeList = UnifiedConfigSimple.getDicTypeConfig(Constants.CURRENCY_TYPE);
		 		
		 		List<UnifiedConfig> transactionList = UnifiedConfigSimple.getDicTypeConfig(Constants.TRANSACTION_STATE);
		 		
		 		List<SacChannelParam> sacChannelParamList = sacChannelParamService.selectAllSacChannelParam();
		 		if(sacChannelParamList.size()>0){
		 			Map<String,Object> bankNameMap = new HashMap<String,Object>();
			 		for(Iterator<SacChannelParam> it = sacChannelParamList.iterator(); it.hasNext();){
			 			SacChannelParam sacChannelParam = (SacChannelParam)it.next();
			 			String bankCode = sacChannelParam.getBankNodeCode();//银行节点代码
			 			String bankName = sacChannelParam.getChnName();//银行名称
			 			if(bankNameMap.containsKey(bankCode)){
			 				it.remove();
			 				continue;
			 			}
			 			bankNameMap.put(bankCode, bankName);
			 			
			 		}
		 		}
		 		
		 		model.addAttribute("sacChannelParamList", sacChannelParamList);
		 		
		 		model.addAttribute("customerTypeList", customerTypeList);
		 		
		        model.addAttribute("count", count.intValue());
		        
		        model.addAttribute("pageNo", pageNo);
		 		
		 		model.addAttribute("sacOtrxInfoList", sacOtrxInfoList);
		 		
		 		model.addAttribute("transactionList", transactionList);
		 		
		 		model.addAttribute("currencyList", currencyTypeList);
		    	 
		        return new ModelAndView("/fundManage/settleRecheckInit", model.asMap());  
		    }  
		    
		    /**  
		     * 非RMB结算复核详细页面初始化
		     * @param request
		     * @param response
		     * @param sacRecDifferences
		     * @return
		     * @throws Exception
		     */
		    @RequestMapping(value="/querySettleDetailById", method = RequestMethod.POST) 
		    @ResponseBody
		    public String querySettleDetailById(HttpServletRequest request, HttpServletResponse response ,SacOtrxInfo sacOtrxInfo)throws Exception  
		    {
		    	
		    	sacOtrxInfo = sacOtrxInfoService.selectSacOtrxInfoById(sacOtrxInfo);
		    	
		    	String content = JSONObject.fromObject(sacOtrxInfo).toString();
				
				return content;
		    	
		    } 
		    
		    
			/**
	    	 * 非人民币结算划款复核
	    	 * @param request
	    	 * @param response
	    	 * @param sacRefund
	    	 * @return
	    	 * @throws Exception
	    	 */
		    @RequestMapping(value="/settleRecheck", method = RequestMethod.POST) 
		    public void settleRecheck(HttpServletRequest request, HttpServletResponse response ,SacOtrxInfo sacOtrxInfo)throws Exception  
		    {
		    	//TODO 调用账务系统代收付状态更新接口记客户账
		    	//transactionService.updateTransaction(sacOtrxInfo);
		 		response.getWriter().write("{\"success\":true}");
		 		
		    }  
			
		    /**  
		     * 调拨录入详细页面初始化
		     * @param request
		     * @param response
		     * @param sacRecDifferences
		     * @return
		     * @throws Exception
		     */
		    @RequestMapping(value="/addDetail", method = {RequestMethod.POST,RequestMethod.GET}) 
		    public ModelAndView addDetail(HttpServletRequest request, HttpServletResponse response)throws Exception  {
		    	Model model = new ExtendedModelMap();
		    	List<UnifiedConfig> payTypeList = UnifiedConfigSimple.getDicTypeConfig(Constants.PAY_TYPE);//支付方式
		 		List<UnifiedConfig> currencyTypeList = UnifiedConfigSimple.getDicTypeConfig(Constants.CURRENCY_TYPE);//币种
		 		Map<String,String> channelBankMap = sacChannelParamService.selectAllSacBank();//银行列表
				List<UnifiedConfig> branchList = UnifiedConfigSimple.getDicTypeConfig(Constants.BRANCH_TYPE);
		 		model.addAttribute("channelBankMap", channelBankMap);
		 		model.addAttribute("branchList", branchList);
		 		model.addAttribute("payTypeList", payTypeList);
		 		model.addAttribute("currencyList", currencyTypeList);
		 		
		        return new ModelAndView("/fundManage/fundDetail", model.asMap()); 
		    }  
		    
		    @RequestMapping(value="/getBankAccListByBankNodeCode", method = {RequestMethod.GET}) 
		    public void getBankAccListByBankNodeCode(HttpServletRequest request, HttpServletResponse response)throws Exception {
		    	String bankNodeCode = request.getParameter("bankNodeCode");
		    	List<Map<String, String>> bankAccList = sacChannelParamService.selectAllBankAccByBankNodeCode(bankNodeCode);
		    	JSONObject o = new JSONObject();
		    	o.put("success", true);
		    	o.put("bankAccList", bankAccList);
		    	o.put("ccyMap", CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_CCY));
		    	response.getWriter().write(o.toString());
		    }
		    
		    /**  
		     * 资金调拨录入确认
		     * @param request
		     * @param response
		     * @param sacRecDifferences
		     * @return
		     * @throws Exception
		     */
		    @RequestMapping(value="/addConfirm", method = RequestMethod.POST) 
		    @ResponseBody
		    public void addConfirm(HttpServletRequest request, HttpServletResponse response ,@Valid SacOtrxInfo sacOtrxInfo,BindingResult errors)throws Exception  
		    {
		    	if (errors.hasErrors()) {
					List<ObjectError> errorList = errors.getAllErrors();
					for (ObjectError objectError : errorList) {
						logger.debug("objectError=" + objectError.toString());
					}
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("message", errorList.get(0).getDefaultMessage());
					map.put("success", false);
					map.put("filed", errorList.get(0).getArguments());
					map.put("valid", true);
					JSONObject jct = JSONObject.fromObject(map);
					response.getWriter().write(jct.toString());
					return;
				}
		    	//TODO 调用账务系统交易接口创建待支付（待复核）交易
		    	String craccNo = sacOtrxInfo.getCraccNo();//收款方
		    	String draccNo = sacOtrxInfo.getDraccNo();//付款方
		    	//根据银行账号查询客户号，银行信息 排除银联信息
		    	SacChannelParam crSacChannelParam = sacChannelParamService.selectSacChannelParamByAcc(craccNo);
		    	SacChannelParam drSacChannelParam = sacChannelParamService.selectSacChannelParamByAcc(draccNo);
		    	String crCCY = crSacChannelParam.getCurrencyType();
		    	String drCCY = drSacChannelParam.getCurrencyType();
		    	String payCurrency = sacOtrxInfo.getPayCurrency();
		    	if(!payCurrency.equals(crCCY)||!payCurrency.equals(drCCY)){
		    		Map<String,Object> map = new HashMap<String,Object>();
					map.put("message", "请选择相同币种的账户进行调拨");
					map.put("success", false);
					map.put("filed", "craccNo");
					map.put("valid", true);
					JSONObject jct = JSONObject.fromObject(map);
					response.getWriter().write(jct.toString());
					return;
		    	}
		    	//添加到资金调拨指令表
		    	SacFundTransferCmd fundTransferCmd = new SacFundTransferCmd();
		    	fundTransferCmd.setCmdState("1");//待审核
		    	fundTransferCmd.setCraccNo(craccNo);
		    	fundTransferCmd.setCraccNodeCode(crSacChannelParam.getBankNodeCode());
		    	fundTransferCmd.setCreateTime(new Date());
		    	fundTransferCmd.setDraccNo(draccNo);
		    	fundTransferCmd.setDraccNodeCode(drSacChannelParam.getBankNodeCode());
		    	fundTransferCmd.setId(SequenceCreatorUtil.getTableId());
		    	fundTransferCmd.setMemo(sacOtrxInfo.getMemo());
		    	fundTransferCmd.setPayAmount(sacOtrxInfo.getPayAmount());
		    	fundTransferCmd.setPayCurrency(sacOtrxInfo.getPayCurrency());
		    	fundTransferCmd.setPayWay(sacOtrxInfo.getPayWay());
		    	fundTransferCmd.setDraccAreaCode(sacOtrxInfo.getDraccAreaCode());
		    	fundTransferCmd.setCraccAreaCode(sacOtrxInfo.getCraccAreaCode());
		    	//fundTransferCmd.setTrxSerialNo(SequenceCreatorUtil.generateTrxSerialNo());
		    	fundTransferCmd.setUpdateTime(new Date());
		    	int insertNum = fundTransferCmdService.insertSacFundTransferCmd(fundTransferCmd);
		    	if(insertNum==1){//处理成功
		    		response.getWriter().write("{\"success\":true}");
		    	}else{
		    		response.getWriter().write("{\"success\":false}");
		    	}
		    }  
		    
		    /**  
		     * 资金调拨审核查询
		     * @param request
		     * @param response
		     * @param sacRecDifferences
		     * @return
		     * @throws Exception
		     */
		    @RequestMapping(value="/fundAllotInit", method = {RequestMethod.POST,RequestMethod.GET}) 
		    public ModelAndView fundAllotQueryInit(HttpServletRequest request, HttpServletResponse response ,SacFundTransferCmd fundTransferCmd)throws Exception  
		    {
		    	ModelAndView mav = new ModelAndView("fundManage/fundAllotInit");
		    	// 页码处理
				Integer pageNo = request.getParameter("pageNo") != null?Integer.parseInt(request.getParameter("pageNo")):1;// 默认为第1页
				Integer	pageSize = request.getParameter("pageSize") != null?Integer.parseInt(request.getParameter("pageSize")):10;
				Map<String,Object> paramMap = BeanUtils.transBean2Map(fundTransferCmd);
				List<UnifiedConfig> branchList = UnifiedConfigSimple.getDicTypeConfig(Constants.BRANCH_TYPE);
				paramMap.remove("id");
		    	paramMap.put("start", (pageNo-1)*pageSize);//分页起始
				paramMap.put("end", pageSize*pageNo);//分页结束
		    	paramMap.put("queryDate", request.getParameter("queryDate"));
		    	mav.addObject("fundTransferCmdList", fundTransferCmdService.selectSacFundTransferCmd(paramMap));
		    	mav.addObject("count", fundTransferCmdService.selectSacFundTransferCmdCount(paramMap));
		    	mav.addObject("queryDate", request.getParameter("queryDate"));
		    	mav.addObject("pageNo", pageNo);
				mav.addObject("pageSize", pageSize);
				mav.addObject("currencyList", UnifiedConfigSimple.getDicTypeConfig(Constants.CURRENCY_TYPE));
				mav.addObject("sacChannelParamList", sacChannelParamService.selectUniqueSacChannelParamOfAcc());
				mav.addObject("fundTransferCmd", fundTransferCmd);
				mav.addObject("channelBankMap", sacChannelParamService.selectAllSacBank());//银行列表
				mav.addObject("branchList", branchList);//分行列表
		    	return mav;
		    }  
		    
		    /**
		     * 资金调拨复核
		     * @param request
		     * @param response
		     * @throws Exception
		     */
		    @RequestMapping(value="/recheckForFundAllot", method = RequestMethod.POST) 
		    public void recheckForFundAllot(HttpServletRequest request, HttpServletResponse response)throws Exception  
		    {
		    	
		    	String result = request.getParameter("result");//审批结果，通过/不通过
		    	String flag = request.getParameter("flag");//线上线下标志
		    	String id = request.getParameter("cmdId");//指令ID
		    	String etrxSerialNo = request.getParameter("etrxSerialNo");//银行流水号
		    	//根据ID获取指令信息
		    	Map<String,Object> paramMap = new HashMap<String,Object>();
		    	paramMap.put("id", id);
		    	List<SacFundTransferCmd> fundTransferCmdList = fundTransferCmdService.selectAllSacFundTransferCmd(paramMap);
		    	SacFundTransferCmd fundTransferCmd = fundTransferCmdList.get(0);
		    	try{
		    		String resultMsg = "";
		    		if("success".equals(result)&&"dsf".equals(flag)){//审核通过，走代收付
		    			//调代收付接口
		    			String resultDSF = fundTransferCmdService.fundTransferCmdToDSF(fundTransferCmd);
		    			if(resultDSF!=null&&resultDSF.startsWith("null")){//处理成功
		    				//更新指令状态为审核处理中
			    			fundTransferCmd.setCmdState("4");//审核处理中
			    			fundTransferCmd.setTrxSerialNo(resultDSF.substring(4));
			    			resultMsg = "代收付已接收请求！";
		    			}else{
		    				//更新指令状态为审核处理失败
			    			fundTransferCmd.setCmdState("5");//线上审核处理失败
			    			resultMsg = resultDSF;
		    			}
			    	}
		    		if("success".equals(result)&&"ac".equals(flag)){//审核通过，走AC
		    			//调AC接口
		    			List<SacOtrxInfo> trxList = fundTransferCmdService.installSacOtrxInfo(fundTransferCmd, etrxSerialNo);
				    	JwsResult jwsResult = transactionService.transactionDealNewInterface(trxList);
				    	if(jwsResult.getCode().equals("000000")){
				    		//更新指令状态为审核通过
		    				fundTransferCmd.setCmdState("2");//审核通过
		    				fundTransferCmd.setTrxSerialNo(trxList.get(0).getTrxSerialNo());
		    				//资金调拨 通知B2B系统 暂时不上线
		    				notifyOperResultToB2BService.notifyFundAllot(fundTransferCmd);
				    	}else{
				    		fundTransferCmd.setCmdState("6");//审核处理失败
				    		resultMsg = jwsResult.getMessage();
				    	}
			    	}
		    		if(result.equals("fail")){//审核不通过
			    		fundTransferCmd.setCmdState("3");
			    	}
		    		//更新指令表状态和审核时间
			    	fundTransferCmd.setUpdateTime(new Date());
		    		//更新指令表状态和审核时间
		    		int updateNum = fundTransferCmdService.updateSacFundTransferCmd(fundTransferCmd);
		    		if(updateNum==1){
		    			response.getWriter().write("{\"success\":true,\"message\":\""+resultMsg+"\"}");
		    		}else{
		    			response.getWriter().write("{\"message\":\"系统处理失败！更新数据异常~\"}");
		    		}
		    	}catch(Exception e){
		    		logger.error(e.getMessage());
		    		response.getWriter().write("{\"message\":\"系统处理失败！系统异常~\"}");
		    	}
		    }  
		    
		    
			/**
			 * 冲正始化页面
			 * @param request
			 * @param response
			 * @param sacChnSettlement
			 * @return
			 * @throws Exception
			 */
		    @RequestMapping(value="/reversalDealInit", method = {RequestMethod.POST,RequestMethod.GET}) 
		    public ModelAndView reversalDealInit(HttpServletRequest request, HttpServletResponse response ,SacOtrxInfo sacOtrxInfo)throws Exception  
		    {
		    	
		    	Model model = new ExtendedModelMap();
		    	
		 		String pageNoStr = Utils.trim(request.getParameter("pageNo"));
		 		
		        int pageNo = Utils.parseInt(pageNoStr, 1);
		        
		        String pageSizeStr = Utils.trim(request.getParameter("pageSize"));
		        
		        int pageSize = Utils.parseInt(pageSizeStr, 10);
		        
		        if(sacOtrxInfo.getCreateTime()==null){
		        	Date currentTime = new Date();
		        	//获取昨天时间
		        	Date backupTime=DateUtils.addDays(currentTime, -1);
		        	sacOtrxInfo.setCreateTime(backupTime);
		        }
		        
		        Integer count = sacOtrxInfoService.selectSacOtrxInfoCounts(sacOtrxInfo);
		        
		        List<SacOtrxInfo> sacOtrxInfoList = sacOtrxInfoService.selectSacOtrxInfoByParam(sacOtrxInfo, pageNo, pageSize);
		        
		        sacOtrxInfoList = dealList(sacOtrxInfoList);
		        
		 		List<UnifiedConfig> currencyTypeList = UnifiedConfigSimple.getDicTypeConfig(Constants.CURRENCY_TYPE);
		 		
		 		List<UnifiedConfig> trxStateList = UnifiedConfigSimple.getDicTypeConfig(Constants.TRANSACTION_STATE);
		 		
		 		List<UnifiedConfig> bussTypeList = UnifiedConfigSimple.getDicTypeConfig(Constants.BUSS_TYPE);
		 		
		 		List<UnifiedConfig> trxTypeList = UnifiedConfigSimple.getDicTypeConfig(Constants.TRX_TYPE);
		 		
		 		model.addAttribute("trxTypeList", trxTypeList);
		 		
		 		model.addAttribute("bussTypeList", bussTypeList);
		 		
		        model.addAttribute("count", count.intValue());
		        
		        model.addAttribute("pageNo", pageNo);
		 		
		 		model.addAttribute("sacOtrxInfoList", sacOtrxInfoList);
		 		
		 		model.addAttribute("trxStateList", trxStateList);
		 		
		 		model.addAttribute("currencyList", currencyTypeList);
		 		
		        return new ModelAndView("/fundManage/reversalDealInit", model.asMap()); 
		    }  
		    
		    private List<SacOtrxInfo> dealList(List<SacOtrxInfo> sacOtrxInfoList) {
		    	Map<String,Object> bussTypeMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_BUSS_TYPE);
		    	Map<String,Object> trxTypeMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_TRX_TYPE);
		    	for(SacOtrxInfo sacOtrxInfo:sacOtrxInfoList){
					//交易类型
					sacOtrxInfo.setTrxType(trxTypeMap.get(sacOtrxInfo.getTrxType())==null?"-":trxTypeMap.get(sacOtrxInfo.getTrxType()).toString());
					//业务类型
					sacOtrxInfo.setBussType(bussTypeMap.get(sacOtrxInfo.getBussType())==null?"-":bussTypeMap.get(sacOtrxInfo.getBussType())+"("+sacOtrxInfo.getBussType()+")");
				}
		    	return sacOtrxInfoList;
				
			}
		    

			/**  
		     * 冲正查询
		     * @param request
		     * @param response
		     * @param sacRecDifferences
		     * @return
		     * @throws Exception
		     */
		    @RequestMapping(value="/reversalDealCheck", method = {RequestMethod.POST,RequestMethod.GET}) 
		    public ModelAndView reversalDealQueryInit(HttpServletRequest request, HttpServletResponse response ,SacOtrxInfo sacOtrxInfo)throws Exception  
		    {
		    	
		    	Model model = new ExtendedModelMap();
		    	
		 		String pageNoStr = Utils.trim(request.getParameter("pageNo"));
		 		
		        int pageNo = Utils.parseInt(pageNoStr, 1);
		        
		        String pageSizeStr = Utils.trim(request.getParameter("pageSize"));
		        
		        int pageSize = Utils.parseInt(pageSizeStr, 10);
		        
		        if(sacOtrxInfo.getCreateTime()==null){
		        	Date currentTime = new Date();
		        	//获取昨天时间
		        	Date backupTime=DateUtils.addDays(currentTime, -1);
		        	sacOtrxInfo.setCreateTime(backupTime);
		        }
		        sacOtrxInfo.setReversalStatus("D");//只查询待审核的交易
		        
		        Integer count = sacOtrxInfoService.selectSacOtrxInfoCounts(sacOtrxInfo);
		        
		        List<SacOtrxInfo> sacOtrxInfoList = sacOtrxInfoService.selectSacOtrxInfoByParam(sacOtrxInfo, pageNo, pageSize);
		        
		        sacOtrxInfoList = dealList(sacOtrxInfoList);
		        
		        List<UnifiedConfig> currencyTypeList = UnifiedConfigSimple.getDicTypeConfig(Constants.CURRENCY_TYPE);
		 		
		 		List<UnifiedConfig> trxStateList = UnifiedConfigSimple.getDicTypeConfig(Constants.TRANSACTION_STATE);
		 		
		 		List<UnifiedConfig> bussTypeList = UnifiedConfigSimple.getDicTypeConfig(Constants.BUSS_TYPE);
		 		
		 		List<UnifiedConfig> trxTypeList = UnifiedConfigSimple.getDicTypeConfig(Constants.TRX_TYPE);
		 		
		 		model.addAttribute("trxTypeList", trxTypeList);
		 		
		 		model.addAttribute("bussTypeList", bussTypeList);
		 		
		        model.addAttribute("count", count.intValue());
		        
		        model.addAttribute("pageNo", pageNo);
		 		
		 		model.addAttribute("sacOtrxInfoList", sacOtrxInfoList);
		 		
		 		model.addAttribute("trxStateList", trxStateList);
		 		
		 		model.addAttribute("currencyList", currencyTypeList);
		 		
		        return new ModelAndView("/fundManage/reversalDealCheck", model.asMap()); 
		    }  
		    
		    /**  
		     * 冲正录入详细页面初始化
		     * @param request
		     * @param response
		     * @param sacRecDifferences
		     * @return
		     * @throws Exception
		     */
		    
		    @ResponseBody
			@RequestMapping(value="/reversalDetailQuery",method = RequestMethod.POST)
			public String reversalDealDetail(HttpServletRequest request, HttpServletResponse response,SacOtrxInfo sacOtrxInfo){
		    	
		    	Map<String, Object> currencyTypeMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_CCY);
		    	
		    	Map<String, Object> payWayMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_PAY_WAY);
		    	
		    	//交易类型Map
		    	Map<String,Object> trxTypeMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_TRX_TYPE);
		    	
		    	//获得业务类型列表
		    	Map<String,Object> bussTypeMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_BUSS_TYPE);
		    	
		    	BigDecimal bd = new BigDecimal(0.00);
				
				sacOtrxInfo = sacOtrxInfoService.selectSacOtrxInfoById(sacOtrxInfo);
				//业务类型
				sacOtrxInfo.setBussType(bussTypeMap.get(sacOtrxInfo.getBussType())==null?"":bussTypeMap.get(sacOtrxInfo.getBussType()).toString());
				//交易类型
				sacOtrxInfo.setTrxType(trxTypeMap.get(sacOtrxInfo.getTrxType())==null?"":trxTypeMap.get(sacOtrxInfo.getTrxType()).toString());
				//支付币种
				sacOtrxInfo.setPayCurrency(currencyTypeMap.get(sacOtrxInfo.getPayCurrency())==null?"":currencyTypeMap.get(sacOtrxInfo.getPayCurrency()).toString());
				//购付汇币种
				sacOtrxInfo.setSacCurrency(currencyTypeMap.get(sacOtrxInfo.getSacCurrency())==null?"":currencyTypeMap.get(sacOtrxInfo.getSacCurrency()).toString());
				//支付方式
				sacOtrxInfo.setPayWay(payWayMap.get(sacOtrxInfo.getPayWay())==null?"":payWayMap.get(sacOtrxInfo.getPayWay()).toString());
				//交易状态
				sacOtrxInfo.setTrxState("S".equals(sacOtrxInfo.getTrxState())?"交易成功":"待支付");
				//冲正状态
				String status = "";
				if("S".equals(sacOtrxInfo.getReversalStatus())){
					status = "已冲正";
				}else if("D".equals(sacOtrxInfo.getReversalStatus())){
					status = "待审核";
				}else{
					status = "未冲正";
				}
				sacOtrxInfo.setReversalStatus(status);
				//sacOtrxInfo.setReversalStatus("S".equals(sacOtrxInfo.getReversalStatus())?"已冲正":"未冲正");
				//支付渠道类型
				sacOtrxInfo.setPayconType("1".equals(sacOtrxInfo.getPayconType())?"B2B支付":"B2C支付");
				//平台订单编号
				sacOtrxInfo.setPlatBillNo(sacOtrxInfo.getPlatBillNo()==null?"":sacOtrxInfo.getPlatBillNo());
				//订单号/退款申请编号/购结汇批次号
				sacOtrxInfo.setCusBillNo(sacOtrxInfo.getCusBillNo()==null?"":sacOtrxInfo.getCusBillNo());
				//购结汇金额
				sacOtrxInfo.setSacAmount(sacOtrxInfo.getSacAmount()==null?bd:sacOtrxInfo.getSacAmount());

		        String content = JSONObject.fromObject(sacOtrxInfo).toString();
				
				return content;
				
			}
		    
		    @RequestMapping(value="/reversalConfirmInit", method = RequestMethod.POST) 
		    @ResponseBody
		    public void reversalConfirmInit(HttpServletRequest request, HttpServletResponse response ,SacOtrxInfo sacOtrxInfo)throws Exception  
		    {
		    	String trxSerialNo = request.getParameter("trxSerialNo");
		    	sacOtrxInfo.setTrxSerialNo(trxSerialNo);
		    	List<SacOtrxInfo> sacOtrxInfoList = sacOtrxInfoService.selectSacOtrxInfoByParam(sacOtrxInfo, 1, 10);
		    	sacOtrxInfo = sacOtrxInfoList.get(0);
		    	sacOtrxInfo.setReversalStatus("D");//冲正审核
				Date date = new Date();
				sacOtrxInfo.setUpdateTime(date);
				sacOtrxInfoService.updateSacOtrxInfoForDB(sacOtrxInfo);
				
				SacTrxDetail sacTrxDetail =  new SacTrxDetail();
				sacTrxDetail.setTrxSerialNo(sacOtrxInfo.getTrxSerialNo());
				sacTrxDetail.setReversalStatus("D");//冲正审核
				sacTrxDetail.setUpdateTime(date);
				sacTrxDetailService.updateTrxDetailForDB(sacTrxDetail);
		    	
		    	sacOperHistoryService.insertSacOperHistory(Constants.OPER_REVERSAL_WIPE,request);

				
		    	response.getWriter().write("{\"success\":true}");
		 		
		 		
		    }  
		    
		    /**  
		     * 冲正录入确认
		     * @param request
		     * @param response
		     * @param sacRecDifferences
		     * @return
		     * @throws Exception
		     */
		    @RequestMapping(value="/reversalConfirm", method = RequestMethod.POST) 
		    @ResponseBody
		    public void reversalConfirm(HttpServletRequest request, HttpServletResponse response ,SacOtrxInfo sacOtrxInfo)throws Exception  
		    {
		    	
		    	String rtrxSerialNo = SequenceCreatorUtil.generateTrxSerialNo();
		    	
		    	String trxSerialNo = request.getParameter("trxSerialNo");
		    	
		    	if(StringUtils.isBlank(trxSerialNo)){
		    		response.getWriter().write("{\"success\":false,\"message\":\"交易流水号为空,请刷新浏览器!\"}");
		    		return;
		    	}
		    	
		    	String reversalStatusLmc = request.getParameter("reversalStatusLmc");
		    	
		    	if(reversalStatusLmc!=null && "N".equals(reversalStatusLmc)){
		    		sacOtrxInfo.setTrxSerialNo(trxSerialNo);
			    	List<SacOtrxInfo> sacOtrxInfoList = sacOtrxInfoService.selectSacOtrxInfoByParam(sacOtrxInfo, 1, 10);
			    	sacOtrxInfo = sacOtrxInfoList.get(0);
			    	sacOtrxInfo.setReversalStatus("N");//冲正审核
					Date date = new Date();
					sacOtrxInfo.setUpdateTime(date);
					sacOtrxInfoService.updateSacOtrxInfoForDB(sacOtrxInfo);
					
					SacTrxDetail sacTrxDetail =  new SacTrxDetail();
					sacTrxDetail.setTrxSerialNo(sacOtrxInfo.getTrxSerialNo());
					sacTrxDetail.setReversalStatus("N");//冲正审核
					sacTrxDetail.setUpdateTime(date);
					sacTrxDetailService.updateTrxDetailForDB(sacTrxDetail);
		    	}else{
		    	
		    /*	sacOtrxInfo.setTrxSerialNo(rtrxSerialNo);
		    	
		    	List<SacOtrxInfo> sacOtrxInfoList2 = sacOtrxInfoService.selectSacOtrxInfoByParam(sacOtrxInfo, 1, 10);
		    	
		    	if(sacOtrxInfoList2!=null&&sacOtrxInfoList2.size()>0){
					response.getWriter().write("{\"success\":false,\"message\":\"该冲正流水号已存在!\"}");
					return;
				}*/
		    	
		    	sacOtrxInfo.setTrxSerialNo(trxSerialNo);
		    	
		    	List<SacOtrxInfo> sacOtrxInfoList = sacOtrxInfoService.selectSacOtrxInfoByParam(sacOtrxInfo, 1, 10);
		    	
		    	sacOtrxInfo = sacOtrxInfoList.get(0);
		    	
		    	SacOtrxInfo sacOtrxInfo2 = (SacOtrxInfo)org.apache.commons.beanutils.BeanUtils.cloneBean(sacOtrxInfo);
		    	
		    	List<SacOtrxInfo> trxList = assembleTransaction(sacOtrxInfo,trxSerialNo,rtrxSerialNo);
		    	
		    	sacOperHistoryService.insertSacOperHistory(Constants.OPER_REVERSAL_WIPE,request);
		    	
		    	//冲正 事物
		    	sacOtrxInfoService.reversalTransaction(trxList,sacOtrxInfo2);
		    	}
		    	response.getWriter().write("{\"success\":true}");
		 		
		 		
		    }  
		    
		    /**
		     * 组装交易
		     * @param sacOtrxInfo
		     * @param rtrxSerialNo
		     * @param rtrxSerialNo2
		     * @return
		     */
		    private List<SacOtrxInfo> assembleTransaction(
					SacOtrxInfo sacOtrxInfo, String trxSerialNo,
					String rtrxSerialNo) {
		    	List<SacOtrxInfo> trxList = new ArrayList<SacOtrxInfo>();
		    	sacOtrxInfo.setTrxType(Constants.REVERSAL_WIPE);
		    	sacOtrxInfo.setTrxSerialNo(rtrxSerialNo);
		    	//sacOtrxInfo.setTrxSerialNo(serialNoService.generateTrxSerialNo());
		    	sacOtrxInfo.setOtrxSerialNo(trxSerialNo);
		    	String craccBankName = sacOtrxInfo.getCraccBankName();
		    	String craccCusCode = sacOtrxInfo.getCraccCusCode();
		    	String craccCusName = sacOtrxInfo.getCraccCusName();
		    	String craccCusType = sacOtrxInfo.getCraccCusType();
		    	String craccName = sacOtrxInfo.getCraccName();
		    	String craccNo = sacOtrxInfo.getCraccNo();
		    	String craccNodeCode = sacOtrxInfo.getCraccNodeCode();
		    	String craccCardId = sacOtrxInfo.getCraccCardId();
		    	
		    	String draccBankName = sacOtrxInfo.getDraccBankName();
		    	String draccCusName = sacOtrxInfo.getDraccCusName();
		    	String draccCusType = sacOtrxInfo.getDraccCusType();
		    	String draccName = sacOtrxInfo.getDraccName();
		    	String draccNo = sacOtrxInfo.getDraccNo();
		    	String draccNodeCode = sacOtrxInfo.getDraccNodeCode();
		    	String draccCardId = sacOtrxInfo.getDraccCardId();
		    	
		    	sacOtrxInfo.setCraccCardId(draccCardId);
		    	sacOtrxInfo.setCraccBankName(draccBankName);
		    	sacOtrxInfo.setCraccCusName(draccCusName);
		    	sacOtrxInfo.setCraccCusType(draccCusType);
		    	sacOtrxInfo.setCraccName(draccName);
		    	sacOtrxInfo.setCraccNo(draccNo);
		    	sacOtrxInfo.setCraccNodeCode(draccNodeCode);
		    	
		    	sacOtrxInfo.setDraccCardId(craccCardId);
		    	sacOtrxInfo.setDraccBankName(craccBankName);
		    	sacOtrxInfo.setDraccCusCode(craccCusCode);
		    	sacOtrxInfo.setDraccCusName(craccCusName);
		    	sacOtrxInfo.setDraccCusType(craccCusType);
		    	sacOtrxInfo.setDraccName(craccName);
		    	sacOtrxInfo.setDraccNo(craccNo);
		    	sacOtrxInfo.setDraccNodeCode(craccNodeCode);
		    	
		    	sacOtrxInfo.setTrxTime(new Date());
		    	
		    	sacOtrxInfo.setTrxSuccTime(new Date());
		    	
		    	trxList.add(sacOtrxInfo);
		    	
				return trxList;
			}

			/**  
		     * 冲正复核详细页面初始化
		     * @param request
		     * @param response
		     * @param sacRecDifferences
		     * @return
		     * @throws Exception
		     */
		    @RequestMapping(value="/queryReversalDetailById", method = RequestMethod.POST) 
		    @ResponseBody
		    public String queryReversalDetailById(HttpServletRequest request, HttpServletResponse response ,SacOtrxInfo sacOtrxInfo)throws Exception  
		    {
		    	
		    	sacOtrxInfo = sacOtrxInfoService.selectSacOtrxInfoById(sacOtrxInfo);
		    	
		    	String content = JSONObject.fromObject(sacOtrxInfo).toString();
				
				return content;
		    	
		    } 
		    
		    
		    /**  
		     * 冲正复核
		     * @param request
		     * @param response
		     * @param sacRecDifferences
		     * @return
		     * @throws Exception
		     */
		    @RequestMapping(value="/recheckForReversalDeal", method = RequestMethod.POST) 
		    public void recheckForReversalDeal(HttpServletRequest request, HttpServletResponse response ,SacOtrxInfo sacOtrxInfo)throws Exception  
		    {
		    	//TODO 调用账务系统交易更新接口更新交易为已成功 并记会计账
		    	transactionService.updateTransactionStateInterface(sacOtrxInfo);
		    	sacOperHistoryService.insertSacOperHistory(Constants.OPER_REVERSAL_WIPE_CHECK,request);
		 		response.getWriter().write("{\"success\":true}");
		 		
		    }  
		    
		    /**  
		     * 调账初始化
		     * @param request
		     * @param response
		     * @param sacRecDifferences
		     * @return
		     * @throws Exception
		     */
		    @RequestMapping(value="/reconciliationAccountInit", method = RequestMethod.GET) 
		    public ModelAndView reconciliationAccountInit(HttpServletRequest request, HttpServletResponse response ,SacOtrxInfo sacOtrxInfo)throws Exception  
		    {
		    	
		    	Model model = new ExtendedModelMap();
		    	
		    	List<UnifiedConfig> currencyTypeList = UnifiedConfigSimple.getDicTypeConfig(Constants.CURRENCY_TYPE);
		    	
		    	List<UnifiedConfig> bussTypeList = UnifiedConfigSimple.getDicTypeConfig(Constants.BUSS_TYPE);
		    	
				List<UnifiedConfig> branchList = UnifiedConfigSimple.getDicTypeConfig(Constants.BRANCH_TYPE);

		    	Map<String, Object> childAccTypeMap = sysDicService.getChileAccTypeMap();
		    	
		    	Map<String, Object> code1Map = sysDicService.getCode1Map();
		    	
		    	Map<String, Object> code2Map = sysDicService.getCode2Map();
		    	
		    	model.addAttribute("currencyTypeList", currencyTypeList);
		    	
		    	model.addAttribute("bussTypeList", bussTypeList);
		    	
		    	model.addAttribute("childAccTypeMap", childAccTypeMap);
		    	
		    	model.addAttribute("branchList", branchList);
		    	
		    	model.addAttribute("code1Map", code1Map);
		    	
		    	model.addAttribute("code2Map", code2Map);
		    	
		        return new ModelAndView("/fundManage/reconciliationAccountInit", model.asMap());  
		 		
		    }  
		    
		    /**  
		     * 调账确认
		     * @param request
		     * @param response
		     * @param sacRecDifferences
		     * @return
		     * @throws Exception
		     */
		    @RequestMapping(value="/reconliciationAccountConfirm", method = RequestMethod.POST) 
		    @ResponseBody
		    public void reconliciationAccountConfirm(HttpServletRequest request, HttpServletResponse response )throws Exception  
		    {
		    	String CODE3D = request.getParameter("CODE3D").trim();//应收和银存查渠道,其余查客户
		    	String CODE3C = request.getParameter("CODE3C").trim();//应收和银存查渠道,其余查客户
		    	
		    	String CODE1D = request.getParameter("CODE1D");//应收和银存查渠道,其余查客户
		    	String CODE1C = request.getParameter("CODE1C");//应收和银存查渠道,其余查客户
		    	
		    	//1122 应收账款 1002 银行存款 1221 其他应收款 应收查询渠道参数  应付查询客户参数
		    	Boolean flagD = CODE1D.equals("1122")||CODE1D.equals("1002")||CODE1D.equals("1221");
		    	Boolean flagC = CODE1C.equals("1122")||CODE1C.equals("1002")||CODE1C.equals("1221");
		    	
		    	SacChannelParam chnParamD = null;
		    	SacChannelParam chnParamC = null;
		    	SacCusParameter cusParamD = null;
		    	SacCusParameter cusParamC = null;
		    	if(flagD){
		    		//查询渠道参数表 根据渠道号
		    		SacChannelParam chnParam = new SacChannelParam();
		    		chnParam.setChnNo(CODE3D);
		    		List<SacChannelParam> chnList = sacChannelParamService.selectSacChannelParamForSplit(chnParam, 1, Integer.MAX_VALUE);
		    		if(chnList==null||chnList.size()<=0){
		    			response.getWriter().write(
		    					"{\"success\":false,\"message\":\"不支持的CODE3!\",\"filed\":\"CODE3D\",\"valid\":true}");
		        		return;
		    		}
		    		chnParamD = chnList.get(0);
		    	}else{
		    		//查询客户参数表 根据客户号
		    		SacCusParameter cusParam = new SacCusParameter();
		    		cusParam.setCusNo(CODE3D);
		    		List<SacCusParameter> cusList = sacCusParameterService.selectAllSacCusParameter(cusParam, 1, Integer.MAX_VALUE);
		    		if(cusList==null||cusList.size()<=0){
		    			response.getWriter().write(
		    					"{\"success\":false,\"message\":\"不支持的CODE3!\",\"filed\":\"CODE3D\",\"valid\":true}");
		        		return;
		    		}
		    		cusParamD = cusList.get(0);
		    	}
		    	
		    	if(flagC){
		    		//查询渠道参数表 根据渠道号
		    		SacChannelParam chnParam = new SacChannelParam();
		    		chnParam.setChnNo(CODE3C);
		    		List<SacChannelParam> chnList = sacChannelParamService.selectSacChannelParamForSplit(chnParam, 1, Integer.MAX_VALUE);
		    		if(chnList==null||chnList.size()<=0){
		    			response.getWriter().write(
		    					"{\"success\":false,\"message\":\"不支持的CODE3!\",\"filed\":\"CODE3C\",\"valid\":true}");
		        		return;
		    		}
		    		chnParamC = chnList.get(0);
		    	}else{
		    		//查询客户参数表 根据客户号
		    		SacCusParameter cusParam = new SacCusParameter();
		    		cusParam.setCusNo(CODE3C);
		    		List<SacCusParameter> cusList = sacCusParameterService.selectAllSacCusParameter(cusParam, 1, Integer.MAX_VALUE);
		    		if(cusList==null||cusList.size()<=0){
		    			response.getWriter().write(
		    					"{\"success\":false,\"message\":\"不支持的CODE3!\",\"filed\":\"CODE3C\",\"valid\":true}");
		        		return;
		    		}
		    		cusParamC = cusList.get(0);
		    	}
		    	
		    	transactionService.reconliciationAccount(request,chnParamD,chnParamC,cusParamD,cusParamC);
		    	
		    	//插入日志库
		    	sacOperHistoryService.insertSacOperHistory(Constants.OPER_MANUAL_RECONLICIATION_ACCOUNT,request);
		    	
		    	response.getWriter().write("{\"success\":true}");
		    	
		    } 
		    

		    @RequestMapping(value="/interrestDealInit", method = RequestMethod.GET) 
		    public ModelAndView interrestDealInit(HttpServletRequest request, HttpServletResponse response)throws Exception  {
		    	Model model = new ExtendedModelMap();
		 		List<UnifiedConfig> currencyTypeList = UnifiedConfigSimple.getDicTypeConfig(Constants.CURRENCY_TYPE);//币种
		 		Map<String,String> channelBankMap = sacChannelParamService.selectAllSacBank();//银行列表
		 		model.addAttribute("channelBankMap", channelBankMap);
		 		model.addAttribute("currencyList", currencyTypeList);
		        return new ModelAndView("/fundManage/interrestDealInit", model.asMap()); 
		    }                       
		    @RequestMapping(value="/interrestDealHandle", method = RequestMethod.POST) 
		    public void interrestDealHandle(HttpServletRequest request, HttpServletResponse response)throws Exception  {
		    	
		    	String craccBankNodeCode = request.getParameter("craccBankNodeCode");		    	
		    	String craccNo = request.getParameter("craccNo");		    	
		    	String payAmount = request.getParameter("payAmount");		    			    	
		    	String payCurrency = request.getParameter("payCurrency"); 			    	
		    	String trxType = "4401";
		    	HashMap<String, Object> map = new HashMap<String, Object>();
		    	map.put("craccBankNodeCode", craccBankNodeCode);
		    	map.put("craccNo", craccNo);
		    	map.put("payAmount", payAmount);
		    	map.put("trxType", trxType);
		    	map.put("payCurrency", payCurrency);
		    	String digst = sacCheckInfoService.objectFromMap(map);
		    	SacCheckInfo sacCheckInfo = new SacCheckInfo();		
		    	sacCheckInfo.setTrxType(trxType);
		    	sacCheckInfo.setDigst(digst);
		    	sacCheckInfo.setCheckStatus("2");
		    	sacCheckInfo.setCreateTime(new Date());
		    	sacCheckInfo.setUpdateTime(new Date());
		    	sacCheckInfoService.insertSacCheckInfo(sacCheckInfo);
		    	response.getWriter().write("{\"success\":true}");
		    } 
		    
		    @SuppressWarnings("unchecked")
			@RequestMapping(value="/interrestDealInCheck", method = {RequestMethod.POST,RequestMethod.GET}) 
		    public ModelAndView interrestDealInCheck(HttpServletRequest request, HttpServletResponse response )throws Exception  
		    {
		    	
		    	ModelAndView mav = new ModelAndView("fundManage/interrestDealInCheck");
		    	String checkStatus = request.getParameter("checkStatus");
		    	// 页码处理
		    	List<SacCheckInfo> sacCheckInfoList = new ArrayList<SacCheckInfo>();
				Integer pageNo = request.getParameter("pageNo") != null?Integer.parseInt(request.getParameter("pageNo")):1;// 默认为第1页
				Integer	pageSize = request.getParameter("pageSize") != null?Integer.parseInt(request.getParameter("pageSize")):10;
				SacCheckInfo sacCheckInfo = new SacCheckInfo();
				String startDate = request.getParameter("startDate");
				if(StringUtils.isBlank(startDate)){
					startDate = DateUtil.formatCurrentDate(Calendar.DAY_OF_YEAR, -7, "yyyyMMdd");
		        }
				String endDate = request.getParameter("endDate");
				if(StringUtils.isBlank(endDate)){
					endDate = DateUtil.formatCurrentDate(Calendar.DAY_OF_YEAR, 0, "yyyyMMdd");
		        }
				sacCheckInfo.setStartDate(startDate);
				sacCheckInfo.setEndDate(endDate);
				sacCheckInfo.setCheckStatus(checkStatus);
				sacCheckInfo.setTrxType("4401");
				sacCheckInfoList = sacCheckInfoService.querySacCheckInfo(sacCheckInfo, pageNo, pageSize);
				List<SacOtrxInfo> sacOtrxInfoList = (List<SacOtrxInfo>) sacCheckInfoService.objectFromList(sacCheckInfoList);
		    	mav.addObject("sacOtrxInfoList", sacOtrxInfoList);
		    	mav.addObject("count", sacCheckInfoService.getCheckInfoCount(sacCheckInfo));
		    	mav.addObject("startDate", startDate);
		    	mav.addObject("endDate", endDate);
		    	mav.addObject("pageNo", pageNo);
				mav.addObject("pageSize", pageSize);
				mav.addObject("checkStatus", request.getParameter("checkStatus"));//
		    	return mav;
		    } 
		    
		    @RequestMapping(value="/interrestDeal", method = RequestMethod.POST) 
		    @ResponseBody
		    public void interrestDeal(HttpServletRequest request, HttpServletResponse response )throws Exception  
		    {
		    	
		    	String id = request.getParameter("id");
		    	String checkStatus = request.getParameter("checkStatus");
		    	if(StringUtils.isEmpty(id)||StringUtils.isEmpty(checkStatus)){
		    		response.getWriter().write("{\"success\":false,\"message\":\"id为空或者审核状态为空\"}");
					return;
		    	}
		    	if("1".equals(checkStatus)){
		    	String craccBankNodeCode = request.getParameter("craccNodeCode");
		    	
		    	String craccNo = request.getParameter("craccNo");
		    	
		    	String payAmount = request.getParameter("payAmount");
		    	
		    	List<SacOtrxInfo> trxList = new ArrayList<SacOtrxInfo>();
		    	
		    	//收款信息
		    	SacChannelParam chnParam = sacChannelParamService.selectSacChannelParamByAcc(craccNo);
		    	
		    	SacOtrxInfo trx = new SacOtrxInfo();
		    	trx.setTrxSerialNo(SequenceCreatorUtil.generateTrxSerialNo());
		    	
		    	trx.setCraccCardId(Constants.EASIPAY_CARD_ID);
		    	trx.setCraccCusType("1");
		    	trx.setCraccCusName("东方电子支付");
		    	trx.setCraccBankName(chnParam.getSacBankName());
		    	trx.setCraccName(chnParam.getAccountName());
		    	trx.setCraccNo(craccNo);
		    	trx.setCraccNodeCode(craccBankNodeCode);
		    	
		    	trx.setDraccCardId(Constants.EASIPAY_CARD_ID);
		    	trx.setDraccCusType("1");
		    	trx.setDraccCusName("东方电子支付");
		    	trx.setDraccBankName(chnParam.getSacBankName());
		    	trx.setDraccName("东方电子支付利息收入流动资金账户");
		    	trx.setDraccNo("0");
		    	trx.setDraccNodeCode(craccBankNodeCode);
		    	
		    	trx.setPayCurrency(chnParam.getCurrencyType());
		    	trx.setPayAmount(new BigDecimal(payAmount));
		    	trx.setBussType("60");//清算
		    	trx.setTrxType(Constants.INTERREST_IN);
		    	trx.setTrxState("S");
		    	trx.setPayconType("3");
		    	Date date = new Date();
		    	trx.setTrxSuccTime(date);
		    	trx.setPayWay("2");
		    	trx.setTrxTime(date);
		    	trxList.add(trx);
		    	
		    	transactionService.transactionDealNewInterface(trxList);
		    	//插入日志库
		    	sacOperHistoryService.insertSacOperHistory(Constants.OPER_MANUAL_INTERREST_IN,request);
		    	}
		    	SacCheckInfo sacCheckInfoTemp = sacCheckInfoService.selectSacCheckInfoById(new Long(id));
		    	if(sacCheckInfoTemp == null){
		    		response.getWriter().write("{\"success\":false,\"message\":\"更新状态失败\"}");
					return;
		    	}
		    	sacCheckInfoTemp.setCheckStatus(checkStatus);
		    	sacCheckInfoService.updateSacCheckInfo(sacCheckInfoTemp);
		    	response.getWriter().write("{\"success\":true}");
		    	
		    } 
		    
		    
		    @RequestMapping(value="/interrestDealOutInit", method = RequestMethod.GET) 
		    public ModelAndView interrestDealOutInit(HttpServletRequest request, HttpServletResponse response)throws Exception  {
		    	Model model = new ExtendedModelMap();
		 		List<UnifiedConfig> currencyTypeList = UnifiedConfigSimple.getDicTypeConfig(Constants.CURRENCY_TYPE);//币种
		 		Map<String,String> channelBankMap = sacChannelParamService.selectAllSacBank();//银行列表
		 		model.addAttribute("channelBankMap", channelBankMap);
		 		model.addAttribute("currencyList", currencyTypeList);
		        return new ModelAndView("/fundManage/interrestDealOutInit", model.asMap()); 
		    } 
		    
		    @RequestMapping(value="/interrestDealOutHandle", method = RequestMethod.POST) 
		    public void interrestDealOutHandle(HttpServletRequest request, HttpServletResponse response)throws Exception  {
		    	
		    	String draccBankNodeCode = request.getParameter("draccBankNodeCode");		    	
		    	String draccNo = request.getParameter("draccNo");		    	
		    	String payAmount = request.getParameter("payAmount");		    			    	
		    	String payCurrency = request.getParameter("payCurrency");
		    	String trxType = "4402";
		    	HashMap<String, Object> map = new HashMap<String, Object>();
		    	map.put("draccBankNodeCode", draccBankNodeCode);
		    	map.put("draccNo", draccNo);
		    	map.put("payAmount", payAmount);
		    	map.put("trxType", trxType);
		    	map.put("payCurrency", payCurrency);
		    	String digst = sacCheckInfoService.objectFromMap(map);
		    	SacCheckInfo sacCheckInfo = new SacCheckInfo();		
		    	sacCheckInfo.setTrxType(trxType);
		    	sacCheckInfo.setDigst(digst);
		    	sacCheckInfo.setCheckStatus("2");
		    	sacCheckInfo.setCreateTime(new Date());
		    	sacCheckInfo.setUpdateTime(new Date());
		    	sacCheckInfoService.insertSacCheckInfo(sacCheckInfo);
		    	response.getWriter().write("{\"success\":true}");
		    } 
		    
		    @SuppressWarnings("unchecked")
			@RequestMapping(value="/interrestDealOutCheck", method = {RequestMethod.POST,RequestMethod.GET}) 
		    public ModelAndView interrestDealOutCheck(HttpServletRequest request, HttpServletResponse response )throws Exception  
		    {
		    	
		    	ModelAndView mav = new ModelAndView("fundManage/interrestDealOutCheck");
		    	String checkStatus = request.getParameter("checkStatus");
		    	// 页码处理
		    	List<SacCheckInfo> sacCheckInfoList = new ArrayList<SacCheckInfo>();
				Integer pageNo = request.getParameter("pageNo") != null?Integer.parseInt(request.getParameter("pageNo")):1;// 默认为第1页
				Integer	pageSize = request.getParameter("pageSize") != null?Integer.parseInt(request.getParameter("pageSize")):10;
				SacCheckInfo sacCheckInfo = new SacCheckInfo();
				String startDate = request.getParameter("startDate");
				if(StringUtils.isBlank(startDate)){
					startDate = DateUtil.formatCurrentDate(Calendar.DAY_OF_YEAR, -7, "yyyyMMdd");
		        }
				String endDate = request.getParameter("endDate");
				if(StringUtils.isBlank(endDate)){
					endDate = DateUtil.formatCurrentDate(Calendar.DAY_OF_YEAR, 0, "yyyyMMdd");
		        }
				sacCheckInfo.setStartDate(startDate);
				sacCheckInfo.setEndDate(endDate);
				sacCheckInfo.setCheckStatus(checkStatus);
				sacCheckInfo.setTrxType("4402");
				sacCheckInfoList = sacCheckInfoService.querySacCheckInfo(sacCheckInfo, pageNo, pageSize);
				List<SacOtrxInfo> sacOtrxInfoList = (List<SacOtrxInfo>) sacCheckInfoService.objectFromList(sacCheckInfoList);
		    	mav.addObject("sacOtrxInfoList", sacOtrxInfoList);
		    	mav.addObject("count", sacCheckInfoService.getCheckInfoCount(sacCheckInfo));
		    	mav.addObject("startDate", startDate);
		    	mav.addObject("endDate", endDate);
		    	mav.addObject("pageNo", pageNo);
				mav.addObject("pageSize", pageSize);
				mav.addObject("checkStatus", request.getParameter("checkStatus"));//
		    	return mav;
		    } 
		    
		    
		    
		    @RequestMapping(value="/interrestDealOut", method = RequestMethod.POST) 
		    @ResponseBody
		    public void interrestDealOut(HttpServletRequest request, HttpServletResponse response )throws Exception  
		    {
		    	
		    	String id = request.getParameter("id");
		    	String checkStatus = request.getParameter("checkStatus");
		    	if(StringUtils.isEmpty(id)||StringUtils.isEmpty(checkStatus)){
		    		response.getWriter().write("{\"success\":false,\"message\":\"id为空或者审核状态为空\"}");
					return;
		    	}
		    	if("1".equals(checkStatus)){
		    	String draccBankNodeCode = request.getParameter("draccBankNodeCode");
		    	
		    	String draccNo = request.getParameter("draccNo");
		    	
		    	String payAmount = request.getParameter("payAmount");
		    	
		    	List<SacOtrxInfo> trxList = new ArrayList<SacOtrxInfo>();
		    	
		    	//收款信息
		    	SacChannelParam chnParam = sacChannelParamService.selectSacChannelParamByAcc(draccNo);
		    	
		    	SacOtrxInfo trx = new SacOtrxInfo();
		    	trx.setTrxSerialNo(SequenceCreatorUtil.generateTrxSerialNo());
		    	
		    	trx.setCraccCardId(Constants.EASIPAY_CARD_ID);
		    	trx.setCraccCusType("1");
		    	trx.setCraccCusName("东方电子支付");
		    	trx.setCraccBankName(chnParam.getSacBankName());
		    	trx.setCraccName("东方电子支付利息收入流动资金账户");
		    	trx.setCraccNo("0");
		    	trx.setCraccNodeCode(draccBankNodeCode);
		    	
		    	trx.setDraccCardId(Constants.EASIPAY_CARD_ID);//东方支付组织机构号
		    	trx.setDraccCusType("1");
		    	trx.setDraccCusName("东方电子支付");
		    	trx.setDraccBankName(chnParam.getSacBankName());
		    	trx.setDraccName(chnParam.getAccountName());
		    	trx.setDraccNo(draccNo);
		    	trx.setDraccNodeCode(draccBankNodeCode);
		    	
		    	
		    	trx.setPayCurrency(chnParam.getCurrencyType());
		    	trx.setPayAmount(new BigDecimal(payAmount));
		    	trx.setBussType("60");//清算
		    	trx.setTrxType(Constants.INTERREST_OUT);
		    	trx.setTrxState("S");
		    	trx.setPayconType("3");
		    	Date date = new Date();
		    	trx.setTrxSuccTime(date);
		    	trx.setPayWay("2");
		    	trx.setTrxTime(date);
		    	trxList.add(trx);
		    	
		    	transactionService.transactionDealNewInterface(trxList);
		    	//插入日志库
		    	sacOperHistoryService.insertSacOperHistory(Constants.OPER_MANUAL_INTERREST_OUT,request);
		    	
		    	}
		    	SacCheckInfo sacCheckInfoTemp = sacCheckInfoService.selectSacCheckInfoById(new Long(id));
		    	if(sacCheckInfoTemp == null){
		    		response.getWriter().write("{\"success\":false,\"message\":\"更新状态失败\"}");
					return;
		    	}
		    	sacCheckInfoTemp.setCheckStatus(checkStatus);
		    	sacCheckInfoService.updateSacCheckInfo(sacCheckInfoTemp);
		    	response.getWriter().write("{\"success\":true}");
		    	
		    } 
		    
		    @RequestMapping(value="/supplementAccountInInit", method = RequestMethod.GET) 
		    public ModelAndView supplementAccountInInit(HttpServletRequest request, HttpServletResponse response)throws Exception  {
		    	Model model = new ExtendedModelMap();
		 		List<UnifiedConfig> currencyTypeList = UnifiedConfigSimple.getDicTypeConfig(Constants.CURRENCY_TYPE);//币种
		 		List<UnifiedConfig> bussTypeList = UnifiedConfigSimple.getDicTypeConfig(Constants.BUSS_TYPE);
		 		Map<String,String> channelBankMap = sacChannelParamService.selectAllSacBank();//银行列表
		 		model.addAttribute("bussTypeList", bussTypeList);
		 		model.addAttribute("channelBankMap", channelBankMap);
		 		model.addAttribute("currencyList", currencyTypeList);
		        return new ModelAndView("/fundManage/supplementAccountInInit", model.asMap()); 
		    } 
		    @RequestMapping(value="/supplementAccountInHandle", method = RequestMethod.POST) 
		    public void supplementAccountInHandle(HttpServletRequest request, HttpServletResponse response)throws Exception  {
                String craccBankNodeCode = request.getParameter("craccBankNodeCode");		    	
		    	String craccNo = request.getParameter("craccNo");		    	
		    	String ccy = request.getParameter("ccy");		    	
		    	String payAmount = request.getParameter("payAmount");		    	
		    	String craccCardId = request.getParameter("craccCardId");		    	
		    	String bussType = request.getParameter("bussType");
		    	String draccNo = request.getParameter("draccNo");
		    	String etrxSerialNo = request.getParameter("etrxSerialNo");
		    	HashMap<String, Object> map = new HashMap<String, Object>();
		    	map.put("craccBankNodeCode", craccBankNodeCode);
		    	map.put("craccNo", craccNo);
		    	map.put("ccy", ccy);
		    	map.put("payAmount", payAmount);
		    	map.put("craccCardId", craccCardId);
		    	map.put("bussType", bussType);
		    	map.put("draccNo", draccNo);
		    	map.put("etrxSerialNo", etrxSerialNo);
		    	map.put("trxType", "4411");
		    	String digst = sacCheckInfoService.objectFromMap(map);
		    	SacCheckInfo sacCheckInfo = new SacCheckInfo();
		
		    	sacCheckInfo.setTrxType("4411");
		    	sacCheckInfo.setDigst(digst);
		    	sacCheckInfo.setCheckStatus("2");
		    	sacCheckInfo.setCreateTime(new Date());
		    	sacCheckInfo.setUpdateTime(new Date());
		    	sacCheckInfoService.insertSacCheckInfo(sacCheckInfo);
		    	response.getWriter().write("{\"success\":true}");
		    } 
		    
			@SuppressWarnings("unchecked")
			@RequestMapping(value="/accountInCheck", method = {RequestMethod.POST,RequestMethod.GET}) 
		    public ModelAndView accountInCheck(HttpServletRequest request, HttpServletResponse response )throws Exception  
		    {
		    	ModelAndView mav = new ModelAndView("fundManage/supplementAccountInCheck");
		    	// 页码处理
		    	String checkStatus = request.getParameter("checkStatus");
		    	
		    	List<SacCheckInfo> sacCheckInfoList = new ArrayList<SacCheckInfo>();
				Integer pageNo = request.getParameter("pageNo") != null?Integer.parseInt(request.getParameter("pageNo")):1;// 默认为第1页
				Integer	pageSize = request.getParameter("pageSize") != null?Integer.parseInt(request.getParameter("pageSize")):10;
				SacCheckInfo sacCheckInfo = new SacCheckInfo();
				String createTime = request.getParameter("createTime");
				if(StringUtils.isNotBlank(createTime)){
					sacCheckInfo.setCreateTime(DateUtils.parseDate(createTime, new String[]{"yyyyMMdd"}));
		        }

				sacCheckInfo.setCheckStatus(checkStatus);
				sacCheckInfo.setTrxType("4411");
				sacCheckInfoList = sacCheckInfoService.querySacCheckInfo(sacCheckInfo, pageNo, pageSize);
				List<SacOtrxInfo> sacOtrxInfoList = (List<SacOtrxInfo>) sacCheckInfoService.objectFromList(sacCheckInfoList);
		    	mav.addObject("sacOtrxInfoList", sacOtrxInfoList);
		    	mav.addObject("count", sacCheckInfoService.getCheckInfoCount(sacCheckInfo));
		    	mav.addObject("createTime", createTime);
		    	mav.addObject("pageNo", pageNo);
				mav.addObject("pageSize", pageSize);
				mav.addObject("checkStatus", request.getParameter("checkStatus"));//
		    	return mav;
		    }  
		    
		    @RequestMapping(value="/supplementAccountIn", method = RequestMethod.POST) 
		    @ResponseBody
		    public void supplementAccountIn(HttpServletRequest request, HttpServletResponse response )throws Exception  
		    {
		    	
		    	String id = request.getParameter("id");
		    	String checkStatus = request.getParameter("checkStatus");
		    	if(StringUtils.isEmpty(id)||StringUtils.isEmpty(checkStatus)){
		    		response.getWriter().write("{\"success\":false,\"message\":\"id为空或者审核状态为空\"}");
					return;
		    	}
		    	if("1".equals(checkStatus)){
		    	String craccBankNodeCode = request.getParameter("craccNodeCode");
		    	
		    	String craccNo = request.getParameter("craccNo");
		    	
		    	String payAmount = request.getParameter("payAmount");
		    	
		    	String craccCardId = request.getParameter("craccCardId");
		    	
		    	String bussType = request.getParameter("bussType");
		    	
		    	String etrxSerialNo = request.getParameter("etrxSerialNo");
		    	
		    	String draccNo = request.getParameter("draccNo");
		    	
		    	SacCusParameter sacCusParameter = new SacCusParameter();

		    	//收款信息
		    	SacChannelParam chnParam = sacChannelParamService.selectSacChannelParamByAcc(craccNo);
		    	
		    	String payCurrency = chnParam.getCurrencyType();
				
				sacCusParameter.setOrgCardId(craccCardId);
				
				sacCusParameter.setSacCurrency(payCurrency);
				
				List<SacCusParameter> paramList = sacCusParameterService.selectAllSacCusParameter(sacCusParameter, 1, Integer.MAX_VALUE);
				
				if(paramList==null||paramList.size()<=0){
					response.getWriter().write("{\"success\":false,\"message\":\"组织机构代码错误\"}");
					return;
				}
				
				sacCusParameter = paramList.get(0);
		    	
		    	List<SacOtrxInfo> trxList = new ArrayList<SacOtrxInfo>();
		    	
		    	SacOtrxInfo trx = new SacOtrxInfo();
		    	
		    	trx.setTrxSerialNo(SequenceCreatorUtil.generateTrxSerialNo());
		    	
		    	trx.setCraccCardId(craccCardId);
		    	trx.setCraccCusType("1");
		    	trx.setCraccCusName(sacCusParameter.getCusName());
		    	trx.setCraccBankName(chnParam.getSacBankName());
		    	trx.setCraccName(chnParam.getAccountName());
		    	trx.setCraccNo(craccNo);
		    	trx.setCraccNodeCode(craccBankNodeCode);
		    	
		    	trx.setDraccCardId(craccCardId);
		    	trx.setDraccCusType("1");
		    	trx.setDraccCusName(sacCusParameter.getCusName());
		    	trx.setDraccBankName(chnParam.getSacBankName());
		    	trx.setDraccName(chnParam.getAccountName());
		    	trx.setDraccNodeCode(craccBankNodeCode);
		    	
		    	trx.setPayCurrency(chnParam.getCurrencyType());
		    	trx.setPayAmount(new BigDecimal(payAmount));
		    	trx.setBussType(bussType);//清算
		    	trx.setTrxType(Constants.SUPPLEMENT_ACCOUNT_IN);
		    	trx.setTrxState("S");
		    	trx.setPayconType("3");
		    	Date date = new Date();
		    	trx.setTrxSuccTime(date);
		    	trx.setPayWay("2");
		    	trx.setTrxTime(date);
		    	trx.setEtrxSerialNo(etrxSerialNo);
		    	trx.setDraccNo(draccNo);
		    	trxList.add(trx);
		    	
		    	transactionService.transactionDealNewInterface(trxList);
		    	//插入日志库
		    	sacOperHistoryService.insertSacOperHistory(Constants.OPER_MANUAL_SUPPLEMENT_IN,request);
		    	}
		    	SacCheckInfo sacCheckInfoTemp = sacCheckInfoService.selectSacCheckInfoById(new Long(id));
		    	if(sacCheckInfoTemp == null){
		    		response.getWriter().write("{\"success\":false,\"message\":\"更新状态失败\"}");
					return;
		    	}
		    	sacCheckInfoTemp.setCheckStatus(checkStatus);
		    	sacCheckInfoService.updateSacCheckInfo(sacCheckInfoTemp);
		    	response.getWriter().write("{\"success\":true}");
		    	
		    } 
		    
		    
		    @RequestMapping(value="/supplementAccountOutInit", method = RequestMethod.GET) 
		    public ModelAndView supplementAccountOutInit(HttpServletRequest request, HttpServletResponse response)throws Exception  {
		    	Model model = new ExtendedModelMap();
		 		List<UnifiedConfig> currencyTypeList = UnifiedConfigSimple.getDicTypeConfig(Constants.CURRENCY_TYPE);//币种
		 		List<UnifiedConfig> bussTypeList = UnifiedConfigSimple.getDicTypeConfig(Constants.BUSS_TYPE);
		 		Map<String,String> channelBankMap = sacChannelParamService.selectAllSacBank();//银行列表
		 		model.addAttribute("channelBankMap", channelBankMap);
		 		model.addAttribute("bussTypeList", bussTypeList);
		 		model.addAttribute("currencyList", currencyTypeList);
		        return new ModelAndView("/fundManage/supplementAccountOutInit", model.asMap()); 
		    } 
		    
		    @RequestMapping(value="/supplementAccountOutHandle", method = RequestMethod.POST) 
		    public void supplementAccountOutHandle(HttpServletRequest request, HttpServletResponse response)throws Exception  {
                String draccBankNodeCode = request.getParameter("draccBankNodeCode");		    	
		    	String draccNo = request.getParameter("draccNo");		    	
		    	String payAmount = request.getParameter("payAmount");		    	
		    	String ccy = request.getParameter("ccy");		    	
		    	String draccCardId = request.getParameter("draccCardId");		    	
		    	String bussType = request.getParameter("bussType");
		    	String trxType = request.getParameter("trxType");
		    	String craccNo = request.getParameter("craccNo");
		    	String etrxSerialNo = request.getParameter("etrxSerialNo");
		    	HashMap<String, Object> map = new HashMap<String, Object>();
		    	map.put("craccNo", craccNo);
		    	map.put("etrxSerialNo", etrxSerialNo);
		    	map.put("draccBankNodeCode", draccBankNodeCode);
		    	map.put("draccNo", draccNo);
		    	map.put("payAmount", payAmount);
		    	map.put("ccy", ccy);
		    	map.put("draccCardId", draccCardId);
		    	map.put("bussType", bussType);
		    	map.put("trxType", trxType);
		    	String digst = sacCheckInfoService.objectFromMap(map);
		    	SacCheckInfo sacCheckInfo = new SacCheckInfo();
		
		    	sacCheckInfo.setTrxType(trxType);
		    	sacCheckInfo.setDigst(digst);
		    	sacCheckInfo.setCheckStatus("2");
		    	sacCheckInfo.setCreateTime(new Date());
		    	sacCheckInfo.setUpdateTime(new Date());
		    	sacCheckInfoService.insertSacCheckInfo(sacCheckInfo);
		    	response.getWriter().write("{\"success\":true}");
		    } 
		    
		    @SuppressWarnings("unchecked")
			@RequestMapping(value="/accountOutCheck", method = {RequestMethod.POST,RequestMethod.GET}) 
		    public ModelAndView accountOutCheck(HttpServletRequest request, HttpServletResponse response )throws Exception  
		    {
		    	
		    	ModelAndView mav = new ModelAndView("fundManage/supplementAccountOutCheck");
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
				sacCheckInfo.setCheckStatus(checkStatus);
				sacCheckInfo.setTrxType("4412");
				sacCheckInfoList = sacCheckInfoService.querySacCheckInfo(sacCheckInfo, pageNo, pageSize);
				List<SacOtrxInfo> sacOtrxInfoList = (List<SacOtrxInfo>) sacCheckInfoService.objectFromList(sacCheckInfoList);
		    	mav.addObject("sacOtrxInfoList", sacOtrxInfoList);
		    	mav.addObject("count", sacCheckInfoService.getCheckInfoCount(sacCheckInfo));
		    	mav.addObject("createTime", request.getParameter("createTime"));
		    	mav.addObject("pageNo", pageNo);
				mav.addObject("pageSize", pageSize);
				mav.addObject("checkStatus", request.getParameter("checkStatus"));//
		    	return mav;
		    } 
		    
		    @RequestMapping(value="/supplementAccountOut", method = RequestMethod.POST) 
		    @ResponseBody
		    public void supplementAccountOut(HttpServletRequest request, HttpServletResponse response )throws Exception  
		    {
		    	
		    	String id = request.getParameter("id");
		    	String checkStatus = request.getParameter("checkStatus");
		    	String craccNo = request.getParameter("craccNo");
		    	String etrxSerialNo = request.getParameter("etrxSerialNo");
		    	if(StringUtils.isEmpty(id)||StringUtils.isEmpty(checkStatus)){
		    		response.getWriter().write("{\"success\":false,\"message\":\"id为空或者审核状态为空\"}");
					return;
		    	}
		    	//if(StringUtils.isEmpty(craccNo)||StringUtils.isEmpty(etrxSerialNo)){
		    	//	response.getWriter().write("{\"success\":false,\"message\":\"银行流水号为空或者收款方账号为空\"}");
				//	return;
		    	//}
		    	if("1".equals(checkStatus)){
		    	String draccBankNodeCode = request.getParameter("draccNodeCode");
		    	
		    	String draccNo = request.getParameter("draccNo");
		    	
		    	String payAmount = request.getParameter("payAmount");
		    	
		    	String draccCardId = request.getParameter("draccCardId");
		    	
		    	String bussType = request.getParameter("bussType");
		    	
		    	SacCusParameter sacCusParameter = new SacCusParameter();

		    	//收款信息
		    	SacChannelParam chnParam = sacChannelParamService.selectSacChannelParamByAcc(draccNo);
		    	
		    	String payCurrency = chnParam.getCurrencyType();
				
				sacCusParameter.setOrgCardId(draccCardId);
				
				sacCusParameter.setSacCurrency(payCurrency);
				
				List<SacCusParameter> paramList = sacCusParameterService.selectAllSacCusParameter(sacCusParameter, 1, Integer.MAX_VALUE);
				
				if(paramList==null||paramList.size()<=0){
					response.getWriter().write("{\"success\":false,\"message\":\"组织机构代码错误\"}");
					return;
				}
				
				sacCusParameter = paramList.get(0);
		    	
		    	List<SacOtrxInfo> trxList = new ArrayList<SacOtrxInfo>();
		    	
		    	SacOtrxInfo trx = new SacOtrxInfo();
		    	
		    	trx.setTrxSerialNo(SequenceCreatorUtil.generateTrxSerialNo());
		    	
		    	trx.setDraccCardId(draccCardId);
		    	trx.setDraccCusType("1");
		    	trx.setDraccCusName(sacCusParameter.getCusName());
		    	trx.setDraccBankName(chnParam.getSacBankName());
		    	trx.setDraccName(chnParam.getAccountName());
		    	trx.setDraccNo(draccNo);
		    	trx.setDraccNodeCode(draccBankNodeCode);
		    	
		    	trx.setCraccCardId(draccCardId);
		    	trx.setCraccCusType("1");
		    	trx.setCraccCusName(sacCusParameter.getCusName());
		    	trx.setCraccBankName(chnParam.getSacBankName());
		    	trx.setCraccName(chnParam.getAccountName());
		    	trx.setCraccNo("0");
		    	trx.setCraccNodeCode(draccBankNodeCode);
		    	
		    	trx.setPayCurrency(chnParam.getCurrencyType());
		    	trx.setPayAmount(new BigDecimal(payAmount));
		    	trx.setBussType(bussType);//清算
		    	trx.setTrxType(Constants.SUPPLEMENT_ACCOUNT_OUT);
		    	trx.setTrxState("S");
		    	trx.setPayconType("3");
		    	Date date = new Date();
		    	trx.setTrxSuccTime(date);
		    	trx.setPayWay("2");
		    	trx.setTrxTime(date);
		    	trx.setCraccNo(craccNo);
		    	trx.setEtrxSerialNo(etrxSerialNo);
		    	trxList.add(trx);
		    	
		    	transactionService.transactionDealNewInterface(trxList);
		    	//插入日志库
		    	sacOperHistoryService.insertSacOperHistory(Constants.OPER_MANUAL_SUPPLEMENT_OUT,request);
		    	
		    	}
		    	SacCheckInfo sacCheckInfoTemp = sacCheckInfoService.selectSacCheckInfoById(new Long(id));
		    	if(sacCheckInfoTemp == null){
		    		response.getWriter().write("{\"success\":false,\"message\":\"更新状态失败\"}");
					return;
		    	}
		    	sacCheckInfoTemp.setCheckStatus(checkStatus);
		    	sacCheckInfoService.updateSacCheckInfo(sacCheckInfoTemp);
		    	response.getWriter().write("{\"success\":true}");
		    	
		    } 
		    
		    
		    public static void main(String[] args) throws ParseException {
		    	System.out.println(DateUtils.parseDate("20160616", new String[]{"yyyyMMdd"}));
			}
		    
		    
		    
		    @RequestMapping(value="/feeInInit", method = RequestMethod.GET) 
		    public ModelAndView feeInInit(HttpServletRequest request, HttpServletResponse response)throws Exception  {
		    	Model model = new ExtendedModelMap();
		 		Map<String,String> channelBankMap = sacChannelParamService.selectAllSacBank();//银行列表
		 		model.addAttribute("channelBankMap", channelBankMap);
		        return new ModelAndView("/fundManage/feeInInit", model.asMap()); 
		    } 
		    @RequestMapping(value="/feeInInitHandle", method = RequestMethod.POST) 
		    public void feeInInitHandle(HttpServletRequest request, HttpServletResponse response)throws Exception  {
                String draccBankNodeCode = request.getParameter("draccBankNodeCode");		    	
                String draccBankName = request.getParameter("draccBankName");		    	
		    	String payAmount = request.getParameter("payAmount");		    	
		    	HashMap<String, Object> map = new HashMap<String, Object>();
		    	map.put("draccBankNodeCode", draccBankNodeCode);
		    	map.put("payAmount", payAmount);
		    	map.put("draccBankName", draccBankName);
		    	String digst = sacCheckInfoService.objectFromMap(map);
		    	SacCheckInfo sacCheckInfo = new SacCheckInfo();
		
		    	sacCheckInfo.setTrxType(Constants.FEE_EXPENSE_IN);//费用支出录入
		    	sacCheckInfo.setDigst(digst);
		    	sacCheckInfo.setCheckStatus("2");
		    	sacCheckInfo.setCreateTime(new Date());
		    	sacCheckInfo.setUpdateTime(new Date());
		    	sacCheckInfoService.insertSacCheckInfo(sacCheckInfo);
		    	response.getWriter().write("{\"success\":true}");
		    } 
		    
			@SuppressWarnings("unchecked")
			@RequestMapping(value="/feeInCheckInit", method = {RequestMethod.POST,RequestMethod.GET}) 
		    public ModelAndView feeInCheckInit(HttpServletRequest request, HttpServletResponse response )throws Exception  
		    {
		    	ModelAndView mav = new ModelAndView("fundManage/feeInCheckInit");
		    	// 页码处理
		    	String checkStatus = request.getParameter("checkStatus");
		    	
		    	List<SacCheckInfo> sacCheckInfoList = new ArrayList<SacCheckInfo>();
				Integer pageNo = request.getParameter("pageNo") != null?Integer.parseInt(request.getParameter("pageNo")):1;// 默认为第1页
				Integer	pageSize = request.getParameter("pageSize") != null?Integer.parseInt(request.getParameter("pageSize")):10;
				SacCheckInfo sacCheckInfo = new SacCheckInfo();
				String startDate = request.getParameter("startDate");
				if(StringUtils.isBlank(startDate)){
					startDate = DateUtil.formatCurrentDate(Calendar.DAY_OF_YEAR, -7, "yyyyMMdd");
		        }
				String endDate = request.getParameter("endDate");
				if(StringUtils.isBlank(endDate)){
					endDate = DateUtil.formatCurrentDate(Calendar.DAY_OF_YEAR, 0, "yyyyMMdd");
		        }
				sacCheckInfo.setStartDate(startDate);
				sacCheckInfo.setEndDate(endDate);
				sacCheckInfo.setCheckStatus(checkStatus);
				sacCheckInfo.setTrxType(Constants.FEE_EXPENSE_IN);
				sacCheckInfoList = sacCheckInfoService.querySacCheckInfo(sacCheckInfo, pageNo, pageSize);
				List<SacOtrxInfo> sacOtrxInfoList = (List<SacOtrxInfo>) sacCheckInfoService.objectFromList(sacCheckInfoList);
		    	mav.addObject("sacOtrxInfoList", sacOtrxInfoList);
		    	mav.addObject("count", sacCheckInfoService.getCheckInfoCount(sacCheckInfo));
		    	mav.addObject("startDate", startDate);
		    	mav.addObject("endDate", endDate);
		    	mav.addObject("pageNo", pageNo);
				mav.addObject("pageSize", pageSize);
				mav.addObject("checkStatus", request.getParameter("checkStatus"));
		    	return mav;
		    }  
		    
		    @RequestMapping(value="/feeInCheck", method = RequestMethod.POST) 
		    @ResponseBody
		    public void feeInCheck(HttpServletRequest request, HttpServletResponse response )throws Exception  
		    {

		    	String id = request.getParameter("id");
		    	String draccBankName = request.getParameter("draccBankName");
		    	String draccBankNodeCode = request.getParameter("draccBankNodeCode");
		    	String checkStatus = request.getParameter("checkStatus");
		    	if(StringUtils.isBlank(id)||StringUtils.isEmpty(checkStatus)){
		    		response.getWriter().write("{\"success\":false,\"message\":\"id为空或者审核状态为空\"}");
					return;
		    	}
		    	
		    	SacCheckInfo sacCheckInfoTemp = sacCheckInfoService.selectSacCheckInfoById(new Long(id));
		    	if(sacCheckInfoTemp == null){
		    		response.getWriter().write("{\"success\":false,\"message\":\"该数据异常!\"}");
					return;
		    	}
		    	
		    	if("1".equals(checkStatus)){
		    	//审核通过
		    	
		    	String payAmount = request.getParameter("payAmount");
		    	
		    	String draccCardId = request.getParameter("draccCardId");
		    	
		    	String draccCusName = request.getParameter("draccCusName");
		    	
		    	String bussType = "60";//清算
		    	
		    	List<SacOtrxInfo> trxList = new ArrayList<SacOtrxInfo>();
		    	
		    	SacOtrxInfo trx = new SacOtrxInfo();
		    	
		    	trx.setTrxSerialNo(SequenceCreatorUtil.generateTrxSerialNo());
		    	
		    	trx.setCraccCardId(draccCardId);
		    	trx.setCraccCusType("1");
		    	trx.setCraccCusName(draccCusName);
		    	trx.setCraccBankName(draccBankName);
		    	trx.setCraccName(draccBankName);
		    	trx.setCraccNo("0");
		    	trx.setCraccNodeCode(draccBankNodeCode);
		    	
		    	trx.setDraccCardId(draccCardId);
		    	trx.setDraccCusType("1");
		    	trx.setDraccCusName(draccCusName);
		    	trx.setDraccBankName(draccBankName);
		    	trx.setDraccName(draccBankName);
		    	trx.setDraccNo("0");
		    	trx.setDraccNodeCode(draccBankNodeCode);
		    	
		    	trx.setPayCurrency("CNY");
		    	trx.setPayAmount(new BigDecimal(payAmount));
		    	trx.setBussType(bussType);//清算
		    	trx.setTrxType(Constants.FEE_EXPENSE_IN);
		    	trx.setTrxState("S");
		    	trx.setPayconType("3");
		    	Date date = new Date();
		    	trx.setTrxSuccTime(date);
		    	trx.setPayWay("2");
		    	trx.setTrxTime(date);
		    	trxList.add(trx);
		    	
		    	transactionService.transactionDealNewInterface(trxList);
		    	//插入日志库
		    	sacOperHistoryService.insertSacOperHistory(Constants.FEE_IN_CHECK,request);
		    	}
		    	
		    	sacCheckInfoTemp.setCheckStatus(checkStatus);
		    	sacCheckInfoService.updateSacCheckInfo(sacCheckInfoTemp);
		    	response.getWriter().write("{\"success\":true}");
		    	
		    } 
		    
		    
		    @RequestMapping(value="/feeOutInit", method = RequestMethod.GET) 
		    public ModelAndView feeOutInit(HttpServletRequest request, HttpServletResponse response)throws Exception  {
		    	Model model = new ExtendedModelMap();
		 		Map<String,String> channelBankMap = sacChannelParamService.selectAllSacBank();//银行列表
		 		model.addAttribute("channelBankMap", channelBankMap);
		        return new ModelAndView("/fundManage/feeOutInit", model.asMap()); 
		    } 
		    @RequestMapping(value="/feeOutInitHandle", method = RequestMethod.POST) 
		    public void feeOutInitHandle(HttpServletRequest request, HttpServletResponse response)throws Exception  {
                String craccBankNodeCode = request.getParameter("craccBankNodeCode");		    	
                String craccBankName = request.getParameter("craccBankName");		    	
		    	String payAmount = request.getParameter("payAmount");		    	
		    	HashMap<String, Object> map = new HashMap<String, Object>();
		    	map.put("craccBankNodeCode", craccBankNodeCode);
		    	map.put("payAmount", payAmount);
		    	map.put("craccBankName", craccBankName);
		    	String digst = sacCheckInfoService.objectFromMap(map);
		    	SacCheckInfo sacCheckInfo = new SacCheckInfo();
		
		    	sacCheckInfo.setTrxType(Constants.FEE_EXPENSE_FORWARD);//费用支出录入
		    	sacCheckInfo.setDigst(digst);
		    	sacCheckInfo.setCheckStatus("2");
		    	sacCheckInfo.setCreateTime(new Date());
		    	sacCheckInfo.setUpdateTime(new Date());
		    	sacCheckInfoService.insertSacCheckInfo(sacCheckInfo);
		    	response.getWriter().write("{\"success\":true}");
		    } 
		    
			@SuppressWarnings("unchecked")
			@RequestMapping(value="/feeOutCheckInit", method = {RequestMethod.POST,RequestMethod.GET}) 
		    public ModelAndView feeOutCheckInit(HttpServletRequest request, HttpServletResponse response )throws Exception  
		    {
		    	ModelAndView mav = new ModelAndView("fundManage/feeOutCheckInit");
		    	// 页码处理
		    	String checkStatus = request.getParameter("checkStatus");
		    	
		    	List<SacCheckInfo> sacCheckInfoList = new ArrayList<SacCheckInfo>();
				Integer pageNo = request.getParameter("pageNo") != null?Integer.parseInt(request.getParameter("pageNo")):1;// 默认为第1页
				Integer	pageSize = request.getParameter("pageSize") != null?Integer.parseInt(request.getParameter("pageSize")):10;
				SacCheckInfo sacCheckInfo = new SacCheckInfo();
				String startDate = request.getParameter("startDate");
				if(StringUtils.isBlank(startDate)){
					startDate = DateUtil.formatCurrentDate(Calendar.DAY_OF_YEAR, -7, "yyyyMMdd");
		        }
				String endDate = request.getParameter("endDate");
				if(StringUtils.isBlank(endDate)){
					endDate = DateUtil.formatCurrentDate(Calendar.DAY_OF_YEAR, 0, "yyyyMMdd");
		        }
				sacCheckInfo.setStartDate(startDate);
				sacCheckInfo.setEndDate(endDate);				sacCheckInfo.setCheckStatus(checkStatus);
				sacCheckInfo.setTrxType(Constants.FEE_EXPENSE_FORWARD);
				sacCheckInfoList = sacCheckInfoService.querySacCheckInfo(sacCheckInfo, pageNo, pageSize);
				List<SacOtrxInfo> sacOtrxInfoList = (List<SacOtrxInfo>) sacCheckInfoService.objectFromList(sacCheckInfoList);
		    	mav.addObject("sacOtrxInfoList", sacOtrxInfoList);
		    	mav.addObject("count", sacCheckInfoService.getCheckInfoCount(sacCheckInfo));
		    	mav.addObject("startDate", startDate);
		    	mav.addObject("endDate", endDate);
		    	mav.addObject("pageNo", pageNo);
				mav.addObject("pageSize", pageSize);
				mav.addObject("checkStatus", request.getParameter("checkStatus"));
		    	return mav;
		    }  
		    
		    @RequestMapping(value="/feeOutCheck", method = RequestMethod.POST) 
		    @ResponseBody
		    public void feeOutCheck(HttpServletRequest request, HttpServletResponse response )throws Exception  
		    {
		    	
		    	String id = request.getParameter("id");
		    	String craccBankName = request.getParameter("craccBankName");
		    	String craccBankNodeCode = request.getParameter("craccBankNodeCode");
		    	String checkStatus = request.getParameter("checkStatus");
		    	if(StringUtils.isBlank(id)||StringUtils.isEmpty(checkStatus)){
		    		response.getWriter().write("{\"success\":false,\"message\":\"id为空或者审核状态为空\"}");
					return;
		    	}
		    	
		    	SacCheckInfo sacCheckInfoTemp = sacCheckInfoService.selectSacCheckInfoById(new Long(id));
		    	if(sacCheckInfoTemp == null){
		    		response.getWriter().write("{\"success\":false,\"message\":\"该数据异常!\"}");
					return;
		    	}
		    	
		    	if("1".equals(checkStatus)){
		    	//审核通过
		    	
		    	String payAmount = request.getParameter("payAmount");
		    	
		    	String craccCardId = request.getParameter("craccCardId");
		    	
		    	String craccCusName = request.getParameter("craccCusName");
		    	
		    	String bussType = "60";//清算
		    	
		    	List<SacOtrxInfo> trxList = new ArrayList<SacOtrxInfo>();
		    	
		    	SacOtrxInfo trx = new SacOtrxInfo();
		    	
		    	trx.setTrxSerialNo(SequenceCreatorUtil.generateTrxSerialNo());
		    	
		    	trx.setCraccCardId(craccCardId);
		    	trx.setCraccCusType("1");
		    	trx.setCraccCusName(craccCusName);
		    	trx.setCraccBankName(craccBankName);
		    	trx.setCraccName(craccBankName);
		    	trx.setCraccNo("0");
		    	trx.setCraccNodeCode(craccBankNodeCode);
		    	
		    	trx.setDraccCardId(craccCardId);
		    	trx.setDraccCusType("1");
		    	trx.setDraccCusName(craccCusName);
		    	trx.setDraccBankName(craccBankName);
		    	trx.setDraccName(craccBankName);
		    	trx.setDraccNo("0");
		    	trx.setDraccNodeCode(craccBankNodeCode);
		    	
		    	trx.setPayCurrency("CNY");
		    	trx.setPayAmount(new BigDecimal(payAmount));
		    	trx.setBussType(bussType);//清算
		    	trx.setTrxType(Constants.FEE_EXPENSE_FORWARD);
		    	trx.setTrxState("S");
		    	trx.setPayconType("3");
		    	Date date = new Date();
		    	trx.setTrxSuccTime(date);
		    	trx.setPayWay("2");
		    	trx.setTrxTime(date);
		    	trxList.add(trx);
		    	
		    	transactionService.transactionDealNewInterface(trxList);
		    	//插入日志库
		    	sacOperHistoryService.insertSacOperHistory(Constants.FEE_OUT_CHECK,request);
		    	}
		    	
		    	sacCheckInfoTemp.setCheckStatus(checkStatus);
		    	sacCheckInfoService.updateSacCheckInfo(sacCheckInfoTemp);
		    	response.getWriter().write("{\"success\":true}");
		    	
		    } 
		    
		    
		    @SuppressWarnings("unchecked")
			@RequestMapping(value="/replacePayCheckInit", method = {RequestMethod.POST,RequestMethod.GET}) 
		    public ModelAndView replacePayCheckInit(HttpServletRequest request, HttpServletResponse response )throws Exception  
		    {
		    	ModelAndView mav = new ModelAndView("fundManage/replacePayCheckInit");
		    	// 页码处理
		    	String checkStatus = request.getParameter("checkStatus");
		    	
		    	List<SacCheckInfo> sacCheckInfoList = new ArrayList<SacCheckInfo>();
				Integer pageNo = request.getParameter("pageNo") != null?Integer.parseInt(request.getParameter("pageNo")):1;// 默认为第1页
				Integer	pageSize = request.getParameter("pageSize") != null?Integer.parseInt(request.getParameter("pageSize")):10;
				SacCheckInfo sacCheckInfo = new SacCheckInfo();
				String createTime = request.getParameter("createTime");
				if(StringUtils.isNotBlank(createTime)){
					sacCheckInfo.setCreateTime(DateUtils.parseDate(createTime, new String[]{"yyyyMMdd"}));
		        }
				
				if(StringUtils.isBlank(checkStatus)){
					checkStatus = "2";
					sacCheckInfo.setCheckStatus(checkStatus);
		        }

				sacCheckInfo.setCheckStatus(checkStatus);
				sacCheckInfo.setTrxType(Constants.REPLACE_PAY);
				sacCheckInfoList = sacCheckInfoService.querySacCheckInfo(sacCheckInfo, pageNo, pageSize);
				List<SacOtrxInfo> sacOtrxInfoList = (List<SacOtrxInfo>) sacCheckInfoService.objectFromList(sacCheckInfoList);
		    	mav.addObject("sacOtrxInfoList", sacOtrxInfoList);
		    	mav.addObject("count", sacCheckInfoService.getCheckInfoCount(sacCheckInfo));
		    	mav.addObject("createTime", createTime);
		    	mav.addObject("pageNo", pageNo);
				mav.addObject("pageSize", pageSize);
				mav.addObject("checkStatus", checkStatus);
		    	return mav;
		    }  
		    
		    @RequestMapping(value="/replacePayCheck", method = RequestMethod.POST) 
		    @ResponseBody
		    public void replacePayCheck(HttpServletRequest request, HttpServletResponse response )throws Exception  
		    {

		    	String id = request.getParameter("id");
		    	String trxSerialNo = request.getParameter("trxSerialNo");
		    	String checkStatus = request.getParameter("checkStatus");
		    	if(StringUtils.isBlank(id)||StringUtils.isEmpty(checkStatus)){
		    		response.getWriter().write("{\"success\":false,\"message\":\"id为空或者审核状态为空\"}");
					return;
		    	}
		    	
		    	SacCheckInfo sacCheckInfoTemp = sacCheckInfoService.selectSacCheckInfoById(new Long(id));
		    	
		    	if(sacCheckInfoTemp == null){
		    		response.getWriter().write("{\"success\":false,\"message\":\"该数据异常!\"}");
					return;
		    	}
		    	
		    	Boolean flag = transactionService.notifyReplacePayCheckResultToDSF(trxSerialNo,checkStatus,sacCheckInfoTemp);
		    	//插入日志库
		    	sacOperHistoryService.insertSacOperHistory(Constants.REPLACE_PAY_CHECK,request);
		    	
		    	if(flag){
		    		sacCheckInfoTemp.setCheckStatus(checkStatus);
			    	sacCheckInfoService.updateSacCheckInfo(sacCheckInfoTemp);
			    	response.getWriter().write("{\"success\":true}");
		    	}else{
		    		response.getWriter().write("{\"success\":false,\"message\":\"审核结果通知代收付系统返回失败!\"}");
		    	}
		    	
		    	
		    } 
		    
    
}
