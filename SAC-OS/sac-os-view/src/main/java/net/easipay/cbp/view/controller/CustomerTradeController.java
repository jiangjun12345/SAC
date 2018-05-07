package net.easipay.cbp.view.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.easipay.cbp.cache.CacheUtil;
import net.easipay.cbp.constant.Constants;
import net.easipay.cbp.model.SacCusBalance;
import net.easipay.cbp.model.SacOtrxInfo;
import net.easipay.cbp.service.ICusBalanceService;
import net.easipay.cbp.service.ICustomerTradeService;
import net.easipay.cbp.service.IDownLoadContent;
import net.easipay.cbp.util.BeanUtils;
import net.easipay.cbp.util.ConstantParams;
import net.easipay.cbp.util.DateUtil;
import net.easipay.dsfc.UnifiedConfig;
import net.easipay.dsfc.UnifiedConfigSimple;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
* @Description: 客户交易管理控制层
* @author dsy (作者英文名称) 
* @date 2015-7-1 下午02:31:06
* @version V1.0  
* @jdk v1.6
 */
@Controller
public class CustomerTradeController extends BaseDataController
{

	private Logger logger = LoggerFactory.getLogger(CustomerTradeController.class);
	
	@Autowired
	public ICustomerTradeService cusTradeService;
	
	@Autowired
	public ICusBalanceService cusBalanceService;
	
	/**
	 * 客户交易明细查询初始化操作
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/trxDetailQueryInit",method={RequestMethod.GET})
	public ModelAndView trxDetailQueryInit(HttpServletRequest request,HttpServletResponse response){
		SacOtrxInfo trxDetail = new SacOtrxInfo();
		return trxDetailQuery(request,response,trxDetail);
	}
	
	/**
	 * 客户交易明细查询操作
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/trxDetailQuery",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView trxDetailQuery(HttpServletRequest request,HttpServletResponse response,SacOtrxInfo trxDetail){
		ModelAndView mav = new ModelAndView("customerTrade/trxDetailQuery");
		//组装查询参数
		Map<String,Object> paramMap = BeanUtils.transBean2Map(trxDetail);
		handlePageAndDateRange(request,paramMap,mav,7,10);//页码和起止时间处理
		paramMap.put("cusNo", request.getParameter("cusNo"));//客户号(名称)
		paramMap.put("payAmountStart", request.getParameter("payAmountStart"));//支付起始金额
		paramMap.put("payAmountEnd", request.getParameter("payAmountEnd"));//支付终止金额
		paramMap.put("trxSuccStartDate", request.getParameter("trxSuccStartDate"));//交易完成时间起
		paramMap.put("trxSuccEndDate", request.getParameter("trxSuccEndDate"));//交易完成时间止
		//对当页汇总处理
		List<SacOtrxInfo> trxDetailList = cusTradeService.queryTrxInfo(paramMap);//分页查询客户交易明细
		Map<String,BigDecimal> currentMap = cusTradeService.trxCurrencyCount(trxDetailList);
		//对总结果汇总处理
		paramMap.remove("start");
		paramMap.remove("end");
		Map<String,Object> allAmountCount = cusTradeService.getTrxInfoAmountCount(paramMap);
		//返回结果
		mav.addObject("payCurrencyMap", CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_CCY));//交易币种数据
		mav.addObject("trxTypeMap", CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_TRX_TYPE));//交易类型数据
		mav.addObject("bussTypeMap", CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_BUSS_TYPE));//业务类型数据
		mav.addObject("bankMap", CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_BANK));//银行列表
		mav.addObject("trxDetailList", cusTradeService.handleSacOtrxInfoList(trxDetailList));//查询结果
		mav.addObject("totalCount", cusTradeService.getTrxInfoCount(paramMap));//总数
		mav.addObject("cusNo", request.getParameter("cusNo"));//客户号(名称)
		mav.addObject("payAmountStart", request.getParameter("payAmountStart"));//支付起始金额
		mav.addObject("payAmountEnd", request.getParameter("payAmountEnd"));//支付终止金额
		mav.addObject("trxSuccStartDate", request.getParameter("trxSuccStartDate"));//交易完成时间起
		mav.addObject("trxSuccEndDate", request.getParameter("trxSuccEndDate"));//交易完成时间止
		mav.addObject("trxDetail",trxDetail);//查询条件
		mav.addObject("currentMap",currentMap);//当前页面汇总
		mav.addObject("allTrxDetailMap",allAmountCount);//查询结果汇总
		return mav;
	}
	
	/**
	 * 客户交易明细下载
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/trxDetailDownload",method={RequestMethod.POST})
	public void trxDetailDownload(HttpServletRequest request,HttpServletResponse response,SacOtrxInfo trxDetail){
		//组装查询参数
		Map<String,Object> paramMap = BeanUtils.transBean2Map(trxDetail);
		paramMap.put("startDate", request.getParameter("startDate"));//开始日期
		paramMap.put("endDate", request.getParameter("endDate"));//结束日期
		paramMap.put("cusNo", request.getParameter("cusNo"));//客户号(名称)
		paramMap.put("payAmountStart", request.getParameter("payAmountStart"));//支付起始金额
		paramMap.put("payAmountEnd", request.getParameter("payAmountEnd"));//支付终止金额
		paramMap.put("trxSuccStartDate", request.getParameter("trxSuccStartDate"));//交易完成时间起
		paramMap.put("trxSuccEndDate", request.getParameter("trxSuccEndDate"));//交易完成时间止
		logger.info("trxDetailQuery paramMap is "+paramMap);
		String contentHead = "序号|交易创建时间|交易完成时间|商户订单号|交易流水号|外部交易流水号|交易状态|业务类型|交易类型|交易金额|交易币种|"+
		"支付方式|支付渠道类型|购结汇币种|购结汇金额|购结汇汇率|收款方客户名称|收款方银行名称|付款方客户名称|付款方银行名称\r\n";
		download(request, response, paramMap, "客户交易明细.txt", contentHead, new IDownLoadContent(){
			@Override
			public String downloadContent(int i,Map<String,Object> paramMap){
				return cusTradeService.trxDetailDownloadContent(i,paramMap);
			}
		});
	}
	
	/**
	 * 客户余额总查询操作
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/cusBalanceQueryInit",method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView cusBalanceQueryInit(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("customerTrade/cusBalanceQuery");
		//组装查询参数
		Map<String,Object> paramMap = new HashMap<String,Object>();
		handlePageAndDateRange(request,paramMap,mav,7,10);
		paramMap.put("cusName", request.getParameter("cusName"));//客户名称
		paramMap.put("cusNo", request.getParameter("cusNo"));//客户号
		paramMap.put("bussType", request.getParameter("bussType"));//业务类型
		paramMap.put("childAccType", request.getParameter("childAccType"));//子账户类型
		logger.info("cusBalanceQueryInit paramMap is "+paramMap);
		List<Map<String,Object>> cusBalanceList = cusBalanceService.queryCusBalance(paramMap);
		//准备初始化参数
		mav.addObject("cusName", request.getParameter("cusName"));//客户名称
		mav.addObject("cusNo", request.getParameter("cusNo"));//客户号
		mav.addObject("bussType", request.getParameter("bussType"));//业务类型
		mav.addObject("childAccType", request.getParameter("childAccType"));//子账户类型
		mav.addObject("totalCount", cusBalanceService.getCusBalanceCount(paramMap));
		mav.addObject("cusBalanceList", cusBalanceService.handleCusBalanceList(cusBalanceList));
		mav.addObject("ccyMap", CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_CCY));
		mav.addObject("bussTypeMap", CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_BUSS_TYPE));
		mav.addObject("childAccTypeMap", cusBalanceService.getChileAccTypeMap());
		return mav;
	}
	
	/**
	 * 客户余额总查询退回操作
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/cusBalanceQueryBack",method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView cusBalanceQueryBack(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("customerTrade/cusBalanceQuery");
		HttpSession session = request.getSession();
		//组装查询参数
		Map<String,Object> paramMap = new HashMap<String,Object>();
		Integer pageNo = Integer.valueOf(session.getAttribute("pageNo").toString());// 默认为第1页
		Integer pageSize = 10;
		paramMap.put("start", (pageNo-1)*pageSize);//分页起始
		paramMap.put("end", pageSize*pageNo);//分页结束
		paramMap.put("cusName", session.getAttribute("cusName"));//客户名称
		paramMap.put("cusNo", session.getAttribute("cusNo"));//客户号
		paramMap.put("bussType", session.getAttribute("bussType"));//业务类型
		paramMap.put("childAccType", session.getAttribute("childAccType"));//子账户类型
		logger.info("cusBalanceQueryBack paramMap is "+paramMap);
		List<Map<String,Object>> cusBalanceList = cusBalanceService.queryCusBalance(paramMap);
		//准备初始化参数
		mav.addObject("pageNo", pageNo);
		mav.addObject("pageSize", pageSize);
		mav.addObject("cusName", session.getAttribute("cusName"));//客户名称
		mav.addObject("cusNo", session.getAttribute("cusNo"));//客户号
		mav.addObject("bussType", session.getAttribute("bussType"));//业务类型
		mav.addObject("childAccType", session.getAttribute("childAccType"));//子账户类型
		mav.addObject("totalCount", cusBalanceService.getCusBalanceCount(paramMap));
		mav.addObject("cusBalanceList", cusBalanceService.handleCusBalanceList(cusBalanceList));
		mav.addObject("ccyMap", CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_CCY));
		mav.addObject("bussTypeMap", CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_BUSS_TYPE));
		mav.addObject("childAccTypeMap", cusBalanceService.getChileAccTypeMap());
		return mav;
	}
	
	/**
	 * 客户余额明细查询操作
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="/cusBalanceQuery",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView cusBalanceQuery(HttpServletRequest request,HttpServletResponse response,SacCusBalance cusBalance) throws ParseException{
		//将明细页面参数保存在session中
		if("Y".equals(request.getParameter("saveFlag"))){//需要保存
			saveFormDataToSession(request);
			return null;
		}
		ModelAndView mav = new ModelAndView("customerTrade/cusBalanceDetailQuery");
		//组装查询参数
		Map<String,Object> paramMap =  new HashMap<String,Object>();
		handlePageAndDateRange(request,paramMap,mav,3,10);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -30);
		String startDate = request.getParameter("startDate")==null?sdf.format(cal.getTime()):request.getParameter("startDate");
		Date endDate = request.getParameter("endDate")==null||"".equals(request.getParameter("endDate").trim())? new Date(): sdf.parse(request.getParameter("endDate"));
		cal.setTime(endDate);
		cal.add(Calendar.DATE, +1);//查询时间后一天
		paramMap.put("beginDate", startDate);
		paramMap.put("endDate", sdf.format(cal.getTime()));
		paramMap.put("finCode", request.getParameter("finCode")==null?"":request.getParameter("finCode"));//客户账号27位29位或31位
		paramMap.put("isShow", "1");
		paramMap.remove("startDate");
		logger.info("cusBalanceQuery paramMap is "+paramMap);
		Map<String,Object> resultMap = cusBalanceService.queryFinMxList(paramMap);
		//返回结果
		mav.addObject("cusBalanceDetailList", resultMap.get("mxList"));
		mav.addObject("totalCount", resultMap.get("totalCount"));
		mav.addObject("startDate", startDate);
		mav.addObject("endDate", request.getParameter("endDate"));
		mav.addObject("finCode", request.getParameter("finCode"));//27位客户号
		mav.addObject("trxTypeMap",CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_TRX_TYPE));//交易类型列表
		mav.addObject("bussTypeMap",CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_BUSS_TYPE));//业务类型列表
		return mav;
	}

	/**
	 * 客户余额明细下载
	 * @param request
	 * @param response
	 * @param otrxInfo
	 * @throws ParseException 
	 */
	@RequestMapping(value="/cusBalanceDownload",method={RequestMethod.POST})
	public void cusBalanceDownload(HttpServletRequest request,HttpServletResponse response) throws ParseException{
		//组装查询参数
		Map<String,Object> paramMap =  new HashMap<String,Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -3);
		String startDate = request.getParameter("startDate")==null?sdf.format(cal.getTime()):request.getParameter("startDate");
		Date endDate = request.getParameter("endDate")==null||"".equals(request.getParameter("endDate").trim())? new Date(): sdf.parse(request.getParameter("endDate"));
		cal.setTime(endDate);
		cal.add(Calendar.DATE, +1);//查询时间后一天
		paramMap.put("beginDate", startDate);
		paramMap.put("endDate", cal.getTime());
		paramMap.put("finCode", request.getParameter("finCode"));//27位客户号
		paramMap.put("start", "");//分页起始
		paramMap.put("end", "");//分页结束
		paramMap.put("isShow", "1");
		logger.info("cusBalanceDownload paramMap is "+paramMap);
		String contentHead = "序号|科目代码|凭证号|凭证时间|借贷标志|借方发生额|贷方发生额|发生额|期初余额|期末余额|结汇损益|业务类型|交易类型|交易时间|交易流水号\r\n";
		download(request, response, paramMap, "客户余额明细下载.txt", contentHead, new IDownLoadContent(){
			@Override
			public String downloadContent(int i,Map<String,Object> paramMap){
				return cusBalanceService.cusBalanceDetailDownloadContent(i, paramMap);
			}
		});
	}
	
	/**
	 * 购付汇查询初始化操作
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/purchaseAndPayQueryInit",method={RequestMethod.GET})
	public ModelAndView purchaseAndPayQueryInit(HttpServletRequest request,HttpServletResponse response){
		SacOtrxInfo otrxInfo = new SacOtrxInfo();
		return purchaseAndPayQuery(request,response,otrxInfo);
	}

	/**
	 * 购付汇查询操作
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/purchaseAndPayQuery",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView purchaseAndPayQuery(HttpServletRequest request,HttpServletResponse response,SacOtrxInfo otrxInfo){
		ModelAndView mav = new ModelAndView("customerTrade/purchaseAndPayQuery");
		//组装查询参数
		Map<String,Object> paramMap = BeanUtils.transBean2Map(otrxInfo);
		handlePageAndDateRange(request,paramMap,mav,7,10);
		logger.info("purchaseAndPayQuery paramMap is "+paramMap);
		//分页查询购付汇明细
		List<SacOtrxInfo> otrxInfoList = cusTradeService.queryPayAndGetInfo(paramMap);
		//返回结果
		mav.addObject("ccyMap", CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_CCY));//交易币种数据
		mav.addObject("bankMap", CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_BANK));//银行数据
		mav.addObject("otrxInfoList", cusTradeService.handleSacOtrxInfoList(otrxInfoList));
		mav.addObject("totalCount", cusTradeService.getPayAndGetInfoCount(paramMap));
		mav.addObject("otrxInfo",otrxInfo);//购结汇明细
		return mav;
	}

	/**
	 * 购付汇结果下载
	 * @param request
	 * @param response 
	 */
	@RequestMapping(value="/purchaseAndPayDownload",method={RequestMethod.POST})
	public void purchaseAndPayDownload(HttpServletRequest request,HttpServletResponse response,SacOtrxInfo otrxInfo){
		//组装查询参数
		Map<String,Object> paramMap = BeanUtils.transBean2Map(otrxInfo);
		paramMap.put("startDate", request.getParameter("startDate"));//开始日期
		paramMap.put("endDate", request.getParameter("endDate"));//结束日期
		logger.info("purchaseAndPayDownload paramMap is "+paramMap);
		String contentHead = "序号|购结汇日期|购结汇类型|扣款银行|扣款银行账号|扣款金额|扣款币种|收款银行|收款银行账号|收款金额|收款币种|购结汇汇率|批次号|笔数|商户号\r\n";
		download(request, response, paramMap, "购付汇结果下载.txt", contentHead, new IDownLoadContent(){
			@Override
			public String downloadContent(int i,Map<String,Object> paramMap){
				return cusTradeService.payAndGetInfoDownloadContent(i, paramMap);
			}
		});
	}
	
	/**
	 * 购付汇明细查询操作
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/purchaseAndPayDetailQuery",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView purchaseAndPayDetailQuery(HttpServletRequest request,HttpServletResponse response){
		//将明细页面参数保存在session中
		if("Y".equals(request.getParameter("saveFlag"))){//需要保存
			saveFormDataToSession(request);
			return null;
		}
		ModelAndView mav = new ModelAndView("customerTrade/purchaseAndPayDetailQuery");
		//组装查询参数
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("trxBatchNo", request.getParameter("trxBatchId"));
		handlePageAndDateRange(request,paramMap,mav,7,10);
		paramMap.remove("startDate");
		paramMap.remove("endData");
		logger.info("purchaseAndPayDetailQuery paramMap is "+paramMap);
		//分页查询购付汇明细
		List<SacOtrxInfo> trxDetailList = cusTradeService.queryTrxInfo(paramMap);//分页查询客户交易明细
		//返回结果
		mav.addObject("trxBatchId", request.getParameter("trxBatchId"));
		mav.addObject("totalCount", cusTradeService.getTrxInfoCount(paramMap));//总数
		mav.addObject("trxDetailList", cusTradeService.handleSacOtrxInfoList(trxDetailList));//查询结果
		return mav;
	}
	
	/**
	 * 购付汇明细查询退回操作
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/purchaseAndPayDetailQueryBack",method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView purchaseAndPayDetailQueryBack(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("customerTrade/purchaseAndPayQuery");
		HttpSession session = request.getSession();
		//组装查询参数
		Map<String,Object> paramMap = new HashMap<String,Object>();
		Integer pageNo = Integer.valueOf(session.getAttribute("pageNo").toString());// 默认为第1页
		Integer pageSize = 10;
		paramMap.put("start", (pageNo-1)*pageSize);//分页起始
		paramMap.put("end", pageSize*pageNo);//分页结束
		paramMap.put("startDate", session.getAttribute("startDate"));
		paramMap.put("endDate", session.getAttribute("endDate"));
		paramMap.put("trxType", session.getAttribute("trxType"));
		paramMap.put("draccCusName", session.getAttribute("draccCusName"));
		paramMap.put("sacCurrency", session.getAttribute("sacCurrency"));
		paramMap.put("payCurrency", session.getAttribute("payCurrency"));
		logger.info("purchaseAndPayDetailQueryBack paramMap is "+paramMap);
		//分页查询购付汇明细
		List<SacOtrxInfo> otrxInfoList = cusTradeService.queryPayAndGetInfo(paramMap);
		//返回结果
		mav.addObject("pageNo", pageNo);
		mav.addObject("pageSize", pageSize);
		mav.addObject("ccyMap", CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_CCY));//交易币种数据
		mav.addObject("bankMap", CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_BANK));//银行数据
		mav.addObject("otrxInfoList", cusTradeService.handleSacOtrxInfoList(otrxInfoList));
		mav.addObject("totalCount", cusTradeService.getPayAndGetInfoCount(paramMap));
		mav.addObject("startDate", session.getAttribute("startDate"));
		mav.addObject("endDate", session.getAttribute("endDate"));
		SacOtrxInfo otrxInfo = new SacOtrxInfo();
		otrxInfo.setTrxType(session.getAttribute("trxType").toString());
		otrxInfo.setDraccCusName(session.getAttribute("draccCusName").toString());
		otrxInfo.setSacCurrency(session.getAttribute("sacCurrency").toString());
		otrxInfo.setPayCurrency(session.getAttribute("payCurrency").toString());
		mav.addObject("otrxInfo", otrxInfo);
		session.removeAttribute("trxType");
		session.removeAttribute("draccCusName");
		session.removeAttribute("sacCurrency");
		session.removeAttribute("payCurrency");
		session.removeAttribute("draccCusCode");
		session.removeAttribute("saveFlag");
		session.removeAttribute("startDate");
		session.removeAttribute("endDate");
		return mav;
	}
	
	
	
	/**
	 * 每日客户余额总查询操作
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/cusBalanceFundDayQueryInit",method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView cusBalanceFundDayQueryInit(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("customerTrade/cusBalanceQueryFundDay");
		
		String startDate = null;
        startDate = request.getParameter("startDate");
        if(startDate==null||"".equals(startDate)){
        	startDate = DateUtil.formatCurrentDate(Calendar.DATE, -1, "yyyy-MM-dd");
        }
//        if(endDate==null||"".equals(endDate)){
//        	endDate = DateUtil.formatCurrentDate(Calendar.DATE, 0, "yyyy-MM-dd");
//        }
		
        List<UnifiedConfig> ccyList = UnifiedConfigSimple.getDicTypeConfig(Constants.CURRENCY_TYPE);
        
        mav.addObject("currencyList", ccyList);
		//组装查询参数
		Map<String,Object> paramMap = new HashMap<String,Object>();
		handlePageAndDateRange(request,paramMap,mav,1,10);
		paramMap.put("cusName", request.getParameter("cusName"));//客户名称
		paramMap.put("cusNo", request.getParameter("cusNo"));//客户号
		paramMap.put("bussType", request.getParameter("bussType"));//业务类型
		paramMap.put("childAccType", request.getParameter("childAccType"));//子账户类型
		paramMap.put("currency", request.getParameter("currency"));//币种
		paramMap.put("startDate", startDate);
		paramMap.put("endDate", startDate);
		logger.info("cusBalanceQueryInit paramMap is "+paramMap);
		List<Map<String,Object>> cusBalanceList = cusBalanceService.queryCusBalanceFundDay(paramMap);
		//准备初始化参数
		mav.addObject("cusName", request.getParameter("cusName"));//客户名称
		mav.addObject("cusNo", request.getParameter("cusNo"));//客户号
		mav.addObject("bussType", request.getParameter("bussType"));//业务类型
		mav.addObject("childAccType", request.getParameter("childAccType"));//子账户类型
		mav.addObject("currency", request.getParameter("currency"));
		mav.addObject("totalCount", cusBalanceService.queryCusBalanceFundDayCount(paramMap));
		mav.addObject("cusBalanceList", cusBalanceService.handleCusBalanceList1(cusBalanceList));
		mav.addObject("ccyMap", CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_CCY));
		mav.addObject("bussTypeMap", CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_BUSS_TYPE));
		mav.addObject("childAccTypeMap", cusBalanceService.getChileAccTypeMap());
		mav.addObject("startDate", startDate);
		mav.addObject("queryUrl", "/cusBalanceFundDayQueryInit");
		mav.addObject("backType", "0");
		return mav;
	}
	
	
	
	/**
	 * 每日客户余额总查询操作
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/cusBalanceFundDayQuery",method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView cusBalanceFundDayQuery(HttpServletRequest request,HttpServletResponse response){	
		
		ModelAndView mav = new ModelAndView("customerTrade/cusBalanceQueryFundDay");
		
		String startDate = null;
        startDate = request.getParameter("startDate");
        if(startDate==null||"".equals(startDate)){
        	startDate = DateUtil.formatCurrentDate(Calendar.DATE, -1, "yyyy-MM-dd");
        }
		
        List<UnifiedConfig> ccyList = UnifiedConfigSimple.getDicTypeConfig(Constants.CURRENCY_TYPE);
        
        mav.addObject("currencyList", ccyList);
        
		//组装查询参数
		Map<String,Object> paramMap = new HashMap<String,Object>();
		handlePageAndDateRange(request,paramMap,mav,1,10);
		paramMap.put("cusName", request.getParameter("cusName"));//客户名称
		paramMap.put("cusNo", request.getParameter("cusNo"));//客户号
		paramMap.put("bussType", request.getParameter("bussType"));//业务类型
		paramMap.put("childAccType", request.getParameter("childAccType"));//子账户类型
		paramMap.put("currency", request.getParameter("currency"));//币种
		paramMap.put("startDate", startDate);
		paramMap.put("endDate", startDate);
		List<String> codeIds = cusBalanceService.getCodeIdsByCusparamMap(paramMap);
		List<Map<String,Object>> cusBalanceList = new ArrayList<Map<String,Object>>();
		int totalCount = 0;
		if (codeIds != null && codeIds.size() > 0) {
			paramMap.put("codeId", codeIds);
			cusBalanceList = cusBalanceService.queryCusBalanceFundDay2(paramMap);
			totalCount = cusBalanceService.queryCusBalanceFundDayCount2(paramMap);
		}
		logger.info("cusBalanceQueryInit paramMap is "+paramMap);
		//准备初始化参数
		mav.addObject("cusName", request.getParameter("cusName"));//客户名称
		mav.addObject("cusNo", request.getParameter("cusNo"));//客户号
		mav.addObject("bussType", request.getParameter("bussType"));//业务类型
		mav.addObject("childAccType", request.getParameter("childAccType"));//子账户类型
		mav.addObject("currency", request.getParameter("currency"));
		mav.addObject("cusBalanceList", cusBalanceService.handleCusBalanceList1(cusBalanceList));
		mav.addObject("totalCount", totalCount);
		mav.addObject("ccyMap", CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_CCY));
		mav.addObject("bussTypeMap", CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_BUSS_TYPE));
		mav.addObject("childAccTypeMap", cusBalanceService.getChileAccTypeMap());
		mav.addObject("startDate", startDate);
		mav.addObject("queryUrl", "/cusBalanceFundDayQuery");
		mav.addObject("backType", "1");
		mav.addObject("pageNoBack", request.getParameter("pageNoBack"));
		return mav;
	}
	
	
	/**
	 * 客户每日余额明细查询操作
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="/cusBalanceQueryFundDayDtail",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView cusBalanceQueryFundDayDtail(HttpServletRequest request,HttpServletResponse response,SacCusBalance cusBalance) throws ParseException{
		ModelAndView mav = new ModelAndView("customerTrade/cusBalanceQueryFundDayDtail");
		
		String startDate = null;
        startDate = request.getParameter("startDate");
        if(startDate==null||"".equals(startDate)){
        	startDate = DateUtil.formatCurrentDate(Calendar.DATE, -30, "yyyy-MM-dd");
        }
        String beginDate = startDate;
        String endDates = beginDate;
        //如果有时间，那么查询该一天的数据
		if (request.getParameter("beginDate") != null && !request.getParameter("beginDate").equals("")) {
			beginDate =  request.getParameter("beginDate");
			endDates = beginDate;
			Calendar cal = Calendar.getInstance();
			cal.setTime(DateUtil.convertStringToDate("yyyy-MM-dd", request.getParameter("beginDate")));
			cal.add(Calendar.DATE, +1);
			endDates = DateUtil.getDateTime("yyyy-MM-dd", cal.getTime());
		}
		//组装查询参数
		Map<String,Object> paramMap =  new HashMap<String,Object>();
		handlePageAndDateRange(request,paramMap,mav,3,10);
		paramMap.put("beginDate", beginDate);
		paramMap.put("endDate", endDates);
		paramMap.put("finCode", request.getParameter("finCode")==null?"":request.getParameter("finCode"));//客户账号27位29位或31位
		paramMap.put("isShow", "1");
		paramMap.remove("startDate");
		logger.info("cusBalanceQuery paramMap is "+paramMap);
		Map<String,Object> resultMap = cusBalanceService.queryFinMxList(paramMap);
		//返回结果
		mav.addObject("cusBalanceDetailList", resultMap.get("mxList"));
		mav.addObject("totalCount", resultMap.get("totalCount")!=null?resultMap.get("totalCount"):"0");
		mav.addObject("startDate", startDate);
		mav.addObject("finCode", request.getParameter("finCode"));//27位客户号
		mav.addObject("trxTypeMap",CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_TRX_TYPE));//交易类型列表
		mav.addObject("bussTypeMap",CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_BUSS_TYPE));//业务类型列表
		mav.addObject("pageNoBack", request.getParameter("pageNoBack"));
		mav.addObject("cusNo", request.getParameter("cusNo"));
		mav.addObject("cusName", request.getParameter("cusName"));
		mav.addObject("bussType", request.getParameter("bussType"));
		mav.addObject("childAccType", request.getParameter("childAccType"));
		mav.addObject("currency", request.getParameter("currency"));
		mav.addObject("backType", request.getParameter("backType"));
		mav.addObject("beginDate", request.getParameter("beginDate"));
		return mav;
	}
	
	/**
	 * 客户每日余额总查询退回操作
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/cusBalanceFundDayQueryBack",method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView cusBalanceFundDayQueryBack(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("customerTrade/cusBalanceQueryFundDay");
//		HttpSession session = request.getSession();
		//组装查询参数
		Map<String,Object> paramMap = new HashMap<String,Object>();
		Integer pageNo = Integer.valueOf(request.getParameter("pageNoBack").toString());// 默认为第1页
		Integer pageSize = 10;
		paramMap.put("start", (pageNo-1)*pageSize);//分页起始
		paramMap.put("end", pageSize*pageNo);//分页结束
		paramMap.put("cusName", request.getParameter("cusName"));//客户名称
		paramMap.put("cusNo", request.getParameter("cusNo"));//客户号
		paramMap.put("bussType", request.getParameter("bussType"));//业务类型
		paramMap.put("childAccType", request.getParameter("childAccType"));//子账户类型
		paramMap.put("startDate", request.getParameter("startDate"));
		paramMap.put("currency", request.getParameter("currency"));
		//结束时间就是开始时间。查询时间为1天
		paramMap.put("endDate", request.getParameter("startDate"));
		logger.info("cusBalanceQueryBack paramMap is "+paramMap);
		List<Map<String,Object>> cusBalanceList = new ArrayList<Map<String,Object>>();
		int totalCount = 0;
		String queryUrl = null;
		String backType = request.getParameter("backType");
		//0  返回org查询  1 返回明细查询
		if (backType != null && backType.equals("1")) {
			List<String> codeIds = cusBalanceService.getCodeIdsByCusparamMap(paramMap);
			if (codeIds != null && codeIds.size() > 0) {
				paramMap.put("codeId", codeIds);
			}
			cusBalanceList = cusBalanceService.queryCusBalanceFundDay2(paramMap);
			totalCount = cusBalanceService.queryCusBalanceFundDayCount2(paramMap);
			queryUrl= "/cusBalanceFundDayQuery";
		}else{
			cusBalanceList = cusBalanceService.queryCusBalanceFundDay(paramMap);
			totalCount =  cusBalanceService.queryCusBalanceFundDayCount(paramMap);
			queryUrl= "/cusBalanceFundDayQueryInit";
		}
		//准备初始化参数
		mav.addObject("pageNo", pageNo);
		mav.addObject("pageSize", pageSize);
		mav.addObject("cusName", request.getParameter("cusName"));//客户名称
		mav.addObject("cusNo", request.getParameter("cusNo"));//客户号
		mav.addObject("bussType", request.getParameter("bussType"));//业务类型
		mav.addObject("childAccType", request.getParameter("childAccType"));//子账户类型
		mav.addObject("totalCount", totalCount);
		mav.addObject("cusBalanceList", cusBalanceService.handleCusBalanceList1(cusBalanceList));
		mav.addObject("ccyMap", CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_CCY));
		mav.addObject("bussTypeMap", CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_BUSS_TYPE));
		mav.addObject("childAccTypeMap", cusBalanceService.getChileAccTypeMap());
		mav.addObject("startDate", request.getParameter("startDate"));
		mav.addObject("queryUrl", queryUrl);
		List<UnifiedConfig> ccyList = UnifiedConfigSimple.getDicTypeConfig(Constants.CURRENCY_TYPE);
        mav.addObject("currencyList", ccyList);
        mav.addObject("currency", request.getParameter("currency"));
        mav.addObject("backType", request.getParameter("backType"));
		return mav;
	}
	
	/**
	 * 客户每日余额明细下载
	 * @param request
	 * @param response
	 * @param otrxInfo
	 * @throws ParseException 
	 */
	@RequestMapping(value="/cusBalanceFundDayDownload",method={RequestMethod.POST})
	public void cusBalanceFundDayDownload(HttpServletRequest request,HttpServletResponse response) throws ParseException{
		//组装查询参数
		Map<String,Object> paramMap =  new HashMap<String,Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -3);
		String startDate = request.getParameter("beginDate");;
		Date endDate =  sdf.parse(request.getParameter("beginDate"));
		cal.setTime(endDate);
		cal.add(Calendar.DATE, +1);//查询时间后一天
		paramMap.put("beginDate", startDate);
		paramMap.put("endDate", sdf.format(cal.getTime()));
		paramMap.put("finCode", request.getParameter("finCode"));//27位客户号
		paramMap.put("start", "");//分页起始
		paramMap.put("end", "");//分页结束
		paramMap.put("isShow", "1");
		logger.info("cusBalanceDownload paramMap is "+paramMap);
		String contentHead = "序号|科目代码|凭证号|凭证时间|借贷标志|借方发生额|贷方发生额|发生额|期初余额|期末余额|结汇损益|业务类型|交易类型|交易时间|交易流水号\r\n";
		download(request, response, paramMap, "客户每日余额明细下载.txt", contentHead, new IDownLoadContent(){
			@Override
			public String downloadContent(int i,Map<String,Object> paramMap){
				return cusBalanceService.cusBalanceDetailDownloadContent(i, paramMap);
			}
		});
	}
	
	@RequestMapping(value="/cusBalancePlatAccNames",method={RequestMethod.POST})
	@ResponseBody
	public void cusBalancePlatAccNames(HttpServletRequest request,HttpServletResponse response,String cusName){
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("start", 0);
		paramMap.put("end", 10);
		paramMap.put("cusName", cusName);
		List<String> sacCusParameterLsit  = cusBalanceService.selectlistSacCusParameterByCusName(paramMap);
		JSONObject  json = new JSONObject();
		 Map<String,Object> map = new HashMap<String,Object>();
		 map.put("cusNamesList", sacCusParameterLsit);
         json = JSONObject. fromObject(map);
         try {
              /*设置编码格式，返回结果 * ***/
             response.getWriter().write(json.toString());
        } catch (IOException e) {
             e.printStackTrace();
        }
	}
}
