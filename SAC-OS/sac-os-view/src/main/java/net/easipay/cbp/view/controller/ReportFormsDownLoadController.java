package net.easipay.cbp.view.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.easipay.cbp.cache.CacheUtil;
import net.easipay.cbp.constant.Constants;
import net.easipay.cbp.model.SacCancelVerify;
import net.easipay.cbp.model.SacChnSetDetail;
import net.easipay.cbp.model.SacChnSettlement;
import net.easipay.cbp.model.SacCusParameter;
import net.easipay.cbp.model.SacCusPayment;
import net.easipay.cbp.model.SacCusSettlement;
import net.easipay.cbp.model.SacOtrxInfo;
import net.easipay.cbp.service.IChnSettlementReportFormsService;
import net.easipay.cbp.service.ICusPaymentReportFormsService;
import net.easipay.cbp.service.ICustomerTradeService;
import net.easipay.cbp.service.IDownLoadContent;
import net.easipay.cbp.service.ISacChnSetDetailService;
import net.easipay.cbp.service.ISacCusParameterService;
import net.easipay.cbp.util.BeanUtils;
import net.easipay.cbp.util.ConstantParams;
import net.easipay.cbp.util.DateUtil;
import net.easipay.cbp.util.StringUtil;
import net.easipay.cbp.util.Utils;
import net.easipay.dsfc.UnifiedConfig;
import net.easipay.dsfc.UnifiedConfigSimple;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ReportFormsDownLoadController extends BaseDataController
{
	private Logger logger = LoggerFactory.getLogger(ReportFormsDownLoadController.class);

	@Autowired
	private ICusPaymentReportFormsService cusPaymentReport; 
	
	@Autowired
	private IChnSettlementReportFormsService chnSettlementReport;
	
	@Autowired
	private ISacChnSetDetailService chnSetDetailService;
	
	@Autowired
	public ICustomerTradeService cusTradeService;
	
    @Autowired
    private ISacCusParameterService sacCusParameterService;
    
    @Autowired
    private ICusPaymentReportFormsService cusPaymentReportFormsService;
	
	/**
	 * 渠道应收款报表查询初始化
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/channelSettlementQueryInit",method=RequestMethod.GET)
	public ModelAndView reportFormsInit(HttpServletRequest request,HttpServletResponse response){
		SacChnSettlement chnSettlement = new SacChnSettlement();
		return channelSettlementQuery(request,response,chnSettlement);
	}
	
	/**
	 * 渠道应收款报表查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="channelSettlementQuery",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView channelSettlementQuery(HttpServletRequest request,HttpServletResponse response,SacChnSettlement chnSettlement){
		ModelAndView mav = new ModelAndView("reportFormsDownLoad/channelSettlement");
		// 组装请求参数
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -7);
		Map<String, Object> paramMap = BeanUtils.transBean2Map(chnSettlement);
		handlePageAndDateRange(request, paramMap, mav, 7,10);
		paramMap.put("startSacDate", request.getParameter("startSacDate"));//清算开始日期
		paramMap.put("endSacDate", request.getParameter("endSacDate"));//清算结束日期
		paramMap.put("startTransDate", request.getParameter("startTransDate")==null?sdf.format(cal.getTime()):request.getParameter("startTransDate"));//到账开始日期
		paramMap.put("endTransDate", request.getParameter("endTransDate"));//到账结束日期
		List<SacChnSettlement> chnSettlementList = chnSettlementReport.queryChnSettlement(paramMap);
		List<Map<String,Object>> countChnSettlementAmount =  chnSettlementReport.countChnSettlementAmount(paramMap);
		mav.addObject("countList", countChnSettlementAmount);
		mav.addObject("startSacDate", request.getParameter("startSacDate"));//清算开始日期
		mav.addObject("endSacDate", request.getParameter("endSacDate"));//清算结束日期
		mav.addObject("startTransDate", request.getParameter("startTransDate")==null?sdf.format(cal.getTime()):request.getParameter("startTransDate"));//到账开始日期
		mav.addObject("endTransDate", request.getParameter("endTransDate"));//到账结束日期
		mav.addObject("chnSettlement", chnSettlement);
		mav.addObject("channelSettlementList", chnSettlementList);//查询结果
		mav.addObject("totalCount",chnSettlementReport.querySacChnSettlementCount(paramMap));
		mav.addObject("bankMap", CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_BANK));//查询全部银行参数
		mav.addObject("ccyMap", CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_CCY));
		return mav;
	}
	
	/**
	 * 渠道应收款报表下载
	 * @param request
	 * @param response
	 * @param chnSettlement
	 */
	@RequestMapping(value="channelSettlementDownload",method={RequestMethod.POST})
	public void channelSettlementDownLoad(HttpServletRequest request,HttpServletResponse response,SacChnSettlement chnSettlement){
		// 组装请求参数
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -7);
		Map<String, Object> paramMap = BeanUtils.transBean2Map(chnSettlement);
		paramMap.put("startSacDate", request.getParameter("startSacDate"));//清算开始日期
		paramMap.put("endSacDate", request.getParameter("endSacDate"));//清算结束日期
		paramMap.put("startTransDate", request.getParameter("startTransDate")==null?sdf.format(cal.getTime()):request.getParameter("startTransDate"));//到账开始日期
		paramMap.put("endTransDate", request.getParameter("endTransDate"));//到账结束日期
		String contentHead = "序号|清算行名称|来款备付金账号|清算日期|到账日期|类型|笔数|总金额|成本|东方支付应收支付渠道金额|币种\r\n";
		download(request, response, paramMap, "渠道应收款报表.txt", contentHead, new IDownLoadContent(){
			@Override
			public String downloadContent(int i,Map<String,Object> paramMap){
				return chnSettlementReport.chnSettlementDownloadContent(i, paramMap);
			}
		});
	}
	
	/**
	 * 渠道应收款明细报表查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="chnSetDetailQuery",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView chnSetDetailQuery(HttpServletRequest request,HttpServletResponse response){
		logger.debug("chnSetDetailQuery start…………");
		//将明细页面参数保存在session中
		if("Y".equals(request.getParameter("saveFlag"))){//需要保存
			saveFormDataToSession(request);
			return null;
		}
		ModelAndView mav = new ModelAndView("reportFormsDownLoad/chnSetDetailQuery");
		// 组装请求参数
		Map<String, Object> paramMap = new HashMap<String,Object>();
		handlePageAndDateRange(request, paramMap, mav, 7,10);
		paramMap.put("chnBatchNo", request.getParameter("chnBatchNo"));//清分批次号
		paramMap.remove("startDate");
		paramMap.remove("endDate");
		List<SacChnSetDetail> chnSetDetailList = chnSetDetailService.queryChnSetDetail(paramMap);
		mav.addObject("chnSetDetailList", chnSetDetailService.handleSacChnSetDetail(chnSetDetailList));//查询结果
		mav.addObject("chnBatchNo", request.getParameter("chnBatchNo"));//清分批次号
		mav.addObject("totalCount",chnSetDetailService.queryChnSetDetailCount(paramMap));
		return mav;
	}
	
	/**
	 * 渠道应收款明细报表查询退回
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="chnSetDetailQueryBack",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView chnSetDetailQueryBack(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("reportFormsDownLoad/channelSettlement");
		HttpSession session = request.getSession();
		// 组装请求参数
		Map<String,Object> paramMap = new HashMap<String,Object>();
		Integer pageNo = Integer.valueOf(session.getAttribute("pageNo").toString());// 默认为第1页
		Integer pageSize = 10;
		paramMap.put("start", (pageNo-1)*pageSize);//分页起始
		paramMap.put("end", pageSize*pageNo);//分页结束
		paramMap.put("startSacDate", session.getAttribute("startSacDate"));//清算开始日期
		paramMap.put("endSacDate", session.getAttribute("endSacDate"));//清算结束日期
		paramMap.put("startTransDate", session.getAttribute("startTransDate"));//到账开始日期
		paramMap.put("endTransDate", session.getAttribute("endTransDate"));//到账结束日期
		paramMap.put("bankNodeCode", session.getAttribute("bankNodeCode"));//清算行
		paramMap.put("currencyType", session.getAttribute("currencyType"));//币种
		List<SacChnSettlement> chnSettlementList = chnSettlementReport.queryChnSettlement(paramMap);
		List<Map<String,Object>> countChnSettlementAmount =  chnSettlementReport.countChnSettlementAmount(paramMap);
		mav.addObject("countList", countChnSettlementAmount);
		mav.addObject("start", (pageNo-1)*pageSize);//分页起始
		mav.addObject("end", pageSize*pageNo);//分页结束
		mav.addObject("startSacDate", session.getAttribute("startSacDate"));//清算开始日期
		mav.addObject("endSacDate", session.getAttribute("endSacDate"));//清算结束日期
		mav.addObject("startTransDate", session.getAttribute("startTransDate"));//到账开始日期
		mav.addObject("endTransDate", session.getAttribute("endTransDate"));//到账结束日期
		SacChnSettlement chnSettlement = new SacChnSettlement();
		chnSettlement.setBankNodeCode(session.getAttribute("bankNodeCode").toString());
		chnSettlement.setCurrencyType(session.getAttribute("currencyType").toString());
		mav.addObject("chnSettlement", chnSettlement);
		//mav.addObject("bankNodeCode", session.getAttribute("bankNodeCode"));//清算行
		//mav.addObject("currencyType", session.getAttribute("currencyType"));//币种
		mav.addObject("channelSettlementList", chnSettlementList);//查询结果
		mav.addObject("totalCount",chnSettlementReport.querySacChnSettlementCount(paramMap));
		mav.addObject("bankMap", CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_BANK));//查询全部银行参数
		mav.addObject("ccyMap", CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_CCY));
		return mav;
	}
	
	/**
	 * 渠道应收款明细报表下载
	 * @param request
	 * @param response
	 * @param chnSettlement
	 */
	@RequestMapping(value="chnSetDetailDownload",method={RequestMethod.POST})
	public void chnSetDetailDownload(HttpServletRequest request,HttpServletResponse response){
		// 组装请求参数
		Map<String, Object> paramMap = new HashMap<String,Object>();
		paramMap.put("chnBatchNo", request.getParameter("chnBatchNo"));//清分批次号
		String contentHead = "序号|支付渠道名称|支付渠道类型|清算行名称|来款账号|到账日期|清算日期|类型|正逆向|笔数|总金额|成本|币种|应收清分批次号|创建时间\r\n";
		download(request, response, paramMap, "渠道应收款明细报表.txt", contentHead, new IDownLoadContent(){
			@Override
			public String downloadContent(int i,Map<String,Object> paramMap){
				return chnSetDetailService.chnSettlementDetailDownloadContent(i, paramMap);
			}
		});
	}
	
	/**
	 * 商户应付款报表查询初始化
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="cusPaymentQueryInit",method={RequestMethod.GET})
	public ModelAndView cusPaymentQueryInit(HttpServletRequest request,HttpServletResponse response){
		return cusPaymentQuery(request, response);
	}
	
	/**
	 * 商户应付款报表查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="cusPaymentQuery",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView cusPaymentQuery(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("reportFormsDownLoad/cusPayment");
		// 组装请求参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		handlePageAndDateRange(request, paramMap, mav, 7,10);
		paramMap.put("cusNo", request.getParameter("cusNo"));//商户号
		paramMap.put("currencyType", request.getParameter("currencyType"));//币种
		List<SacCusSettlement> cusPaymentList = cusPaymentReport.queryCusPayment(paramMap);
		List<Map<String,Object>> countCusSettlementList = cusPaymentReport.countCusSettlementAmount(paramMap);
		mav.addObject("countList", countCusSettlementList);
		mav.addObject("cusNo", request.getParameter("cusNo"));//商户号
		mav.addObject("currencyType", request.getParameter("currencyType"));//币种
		mav.addObject("cusPaymentList", cusPaymentReport.handleSacCusSettlementList(cusPaymentList));//查询结果
		mav.addObject("totalCount",cusPaymentReport.queryCusPaymentCount(paramMap));
		mav.addObject("ccyMap", CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_CCY));
		return mav;
	}
	
	/**
	 * 商户应付款报表下载
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="cusPaymentDownload",method={RequestMethod.POST})
	public void cusPaymentDownload(HttpServletRequest request,HttpServletResponse response){
		// 组装请求参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("startDate", request.getParameter("startDate"));//开始日期
		paramMap.put("endDate", request.getParameter("endDate"));//结束日期
		paramMap.put("cusNo", request.getParameter("cusNo"));//商户号
		paramMap.put("currencyType", request.getParameter("currencyType"));//币种
		String contentHead = "序号|商户号|商户名称|划账日期|总笔数|总金额|币种|手续费|结算金额|结算批次号|付款方账户名|是否已结算|划款状态|是否已勾兑\r\n";
		download(request, response, paramMap, "商户应付款报表.txt", contentHead, new IDownLoadContent(){
			@Override
			public String downloadContent(int i,Map<String,Object> paramMap){
				return cusPaymentReport.cusPaymentDownloadContent(i, paramMap);
			}
		});
	}
	
	/**
	 * 商户应付款明细报表查询初始化
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="cusPaymentQueryDetailInit",method={RequestMethod.GET})
	public ModelAndView cusPaymentQueryDetailInit(HttpServletRequest request,HttpServletResponse response){
		return cusPaymentDetailQuery(request, response);
	}
	
	/**
	 * 商户应付款明细报表查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("cusPaymentDetailQuery")
	public ModelAndView cusPaymentDetailQuery(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("reportFormsDownLoad/cusPaymentDetail");
		// 组装请求参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		handlePageAndDateRange(request, paramMap, mav, 7,10);
		paramMap.put("cusNo", request.getParameter("cusNo"));//商户号
		paramMap.put("currencyType", request.getParameter("currencyType"));//币种
		List<SacCusPayment> cusPaymentDetailList = cusPaymentReport.queryCusPaymentDetail(paramMap);
		List<Map<String,Object>> countCusPaymentList = cusPaymentReport.countCusPaymentAmount(paramMap);
		mav.addObject("countList", countCusPaymentList);
		mav.addObject("cusNo", request.getParameter("cusNo"));//商户号
		mav.addObject("currencyType", request.getParameter("currencyType"));//币种
		mav.addObject("cusPaymentDetailList", cusPaymentReport.handleSacCusPaymentList(cusPaymentDetailList));//查询结果
		mav.addObject("totalCount",cusPaymentReport.queryCusPaymentDetailCount(paramMap));
		mav.addObject("ccyMap", CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_CCY));
		return mav;
	}
	
	/**
	 * 商户应付款明细报表下载
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="cusPaymentDetailDownload",method={RequestMethod.POST})
	public void cusPaymentDetailDownload(HttpServletRequest request,HttpServletResponse response){
		// 组装请求参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("startDate", request.getParameter("startDate"));//开始日期
		paramMap.put("endDate", request.getParameter("endDate"));//结束日期
		paramMap.put("cusNo", request.getParameter("cusNo"));//商户号
		paramMap.put("currencyType", request.getParameter("currencyType"));//币种
		String contentHead = "序号|商户号|商户名称|交易日期|清算日期|业务类型|交易类型|笔数|总金额|币种|商户手续费|商户结算总金额|是否已结算|结算批次号\r\n";
		download(request, response, paramMap, "商户应付款明细报表.txt", contentHead, new IDownLoadContent(){
			@Override
			public String downloadContent(int i,Map<String,Object> paramMap){
				return cusPaymentReport.cusPaymentDetailDownloadContent(i, paramMap);
			}
		});
	}
	
	/**
	 * 中金交易明细查询初始化操作
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/unionTrxDetailQueryInit",method={RequestMethod.GET})
	public ModelAndView trxDetailQueryInit(HttpServletRequest request,HttpServletResponse response){
		SacOtrxInfo trxDetail = new SacOtrxInfo();
		return trxDetailQuery(request,response,trxDetail);
	}
	
	@RequestMapping(value="/unionTrxDetailQuery",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView trxDetailQuery(HttpServletRequest request,HttpServletResponse response,SacOtrxInfo trxDetail){
		ModelAndView mav = new ModelAndView("reportFormsDownLoad/unionTrxDetailQuery");
		//组装查询参数
		Map<String,Object> paramMap = BeanUtils.transBean2Map(trxDetail);
		String endDate  = request.getParameter("endDate");
		if(StringUtils.isBlank(endDate)){
			endDate = DateUtil.formatCurrentDate(Calendar.DATE, -2, "yyyyMMdd");
		}
		handlePageAndDateRange(request,paramMap,mav,2,10);//页码和起止时间处理
		paramMap.put("cusNo", request.getParameter("cusNo"));//客户号(名称)
		paramMap.put("payAmountStart", request.getParameter("payAmountStart"));//支付起始金额
		paramMap.put("payAmountEnd", request.getParameter("payAmountEnd"));//支付终止金额
		paramMap.put("trxSuccStartDate", request.getParameter("trxSuccStartDate"));//交易完成时间起
		paramMap.put("trxSuccEndDate", request.getParameter("trxSuccEndDate"));//交易完成时间止
		paramMap.put("endDate", endDate);
		//paramMap.put("trxTypeList", "'1312','1315','1631'");
		paramMap.put("draccNodeCode", "CFCA000");
		//对当页汇总处理
		List<SacOtrxInfo> trxDetailList = cusTradeService.queryTrxInfo(paramMap);//分页查询客户交易明细
		Map<String,BigDecimal> currentMap = cusTradeService.trxCurrencyCount(trxDetailList);
		//对总结果汇总处理
		paramMap.remove("start");
		paramMap.remove("end");
		Map<String,Object> allAmountCount = cusTradeService.getTrxInfoAmountCount(paramMap);
		Map<String, Object> trxTypeMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_TRX_TYPE);
		Iterator<String> iterator = trxTypeMap.keySet().iterator();
		while(iterator.hasNext()){
			String next = iterator.next();
			if(!next.equals("1312")&&!next.equals("1315")&&!next.equals("1631")&&!next.equals("212113")&&!next.equals("202313")&&!next.equals("202312")&&!next.equals("202413")&&!next.equals("202412")){
				iterator.remove();
			}
		}
		//返回结果
		mav.addObject("payCurrencyMap", CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_CCY));//交易币种数据
		mav.addObject("trxTypeMap", trxTypeMap);//交易类型数据
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
		mav.addObject("endDate",endDate);
		mav.addObject("allTrxDetailMap",allAmountCount);//查询结果汇总
		return mav;
	}
	
	/**
	 * 中金客户交易明细下载
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/unionTrxDetailDownload",method={RequestMethod.POST})
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
		//paramMap.put("trxTypeList", "'1312','1315','1631'");
		paramMap.put("draccNodeCode", "CFCA000");
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
	 * 核销信息查询
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/suspendedAccInfoQuery",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView suspendedAccInfoQuery(HttpServletRequest request,HttpServletResponse response){
		//组装查询参数
		Model model = new ExtendedModelMap();
		Map<String,Object> paramMap = new HashMap<String, Object>();
		String orgCardId = request.getParameter("orgCardId");
		String cusName = request.getParameter("cusName");
		String currencyType = request.getParameter("currencyType");
		String bussType = request.getParameter("bussType");
		String cavType = request.getParameter("cavType");
		String pageNoStr = Utils.trim(request.getParameter("pageNo"));
		List<UnifiedConfig> currencyTypeList = UnifiedConfigSimple.getDicTypeConfig(Constants.CURRENCY_TYPE);
		List<UnifiedConfig> bussTypeList = UnifiedConfigSimple.getDicTypeConfig(Constants.BUSS_TYPE);
		List<UnifiedConfig> cavTypeList = UnifiedConfigSimple.getDicTypeConfig(Constants.CAV_STATE);
 		
        int pageNo = Utils.parseInt(pageNoStr, 1);
        
        String pageSizeStr = Utils.trim(request.getParameter("pageSize"));
        
        int pageSize = Utils.parseInt(pageSizeStr, 10);
        
		if(StringUtil.isBlank(bussType)){
			bussType="20";//航付通
		}
		if(StringUtils.isBlank(cusName)&&StringUtil.isBlank(orgCardId)){
			orgCardId = "674612978";//默认查询东方电子支付的暂挂账户
		}
		if(StringUtil.isBlank(currencyType)){
			currencyType= "01";
		}
		if(StringUtil.isBlank(cavType)){
			cavType="1";
		}
		
		SacCusParameter param = new SacCusParameter();
		param.setOrgCardId(orgCardId);
		param.setCusName(cusName);
		List<SacCusParameter> paramList = sacCusParameterService.selectAllSacCusParameter(param, 1, 10);
		int count = 0 ;
		List<SacCancelVerify> suspendedAccinfoList = new ArrayList<SacCancelVerify>();
		if(paramList!=null&&paramList.size()!=0){
			String codeId = "";
			param = paramList.get(0);
			String cusNo = param.getCusNo();
			codeId="220203"+cusNo+currencyType+bussType+"02";
			
			paramMap.put("pageNo", pageNo);
	        
	        paramMap.put("pageSize", pageSize);
	        
	        paramMap.put("codeId", codeId);
	        
	        paramMap.put("cavType", cavType);
	        
	        suspendedAccinfoList = cusPaymentReportFormsService.getSuspendedAccinfoList(paramMap);
	        
	        count = cusPaymentReportFormsService.getSuspendedAccinfoListCount(paramMap);
	        
		}
		model.addAttribute("cavTypeList", cavTypeList);
		model.addAttribute("bussTypeList", bussTypeList);
		model.addAttribute("currencyTypeList", currencyTypeList);
		model.addAttribute("count", count);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("bussType", bussType);
		model.addAttribute("cavType", cavType);
		model.addAttribute("orgCardId", orgCardId);
		model.addAttribute("cusName", cusName);
		model.addAttribute("currencyType", currencyType);
		model.addAttribute("suspendedAccinfoList", suspendedAccinfoList);
		return new ModelAndView("/reportFormsDownLoad/suspendedAccInfoQueryInit", model.asMap());  
	}
	
	/**
	 * 客户交易明细下载
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/suspendedAccInfoDownload",method={RequestMethod.POST})
	public void suspendedAccInfoDownload(HttpServletRequest request,HttpServletResponse response,SacCancelVerify cancelVerify){
		//组装查询参数
		Map<String,Object> paramMap = new HashMap<String, Object>();
		String orgCardId = request.getParameter("orgCardId");
		String cusName = request.getParameter("cusName");
		String currencyType = request.getParameter("currencyType");
		String bussType = request.getParameter("bussType");
		String cavType = request.getParameter("cavType");
		
		if(StringUtil.isBlank(bussType)){
			bussType="20";//航付通
		}
		if(StringUtil.isBlank(orgCardId)){
			orgCardId = "674612978";//默认查询东方电子支付的暂挂账户
		}
		if(StringUtil.isBlank(currencyType)){
			currencyType= "01";
		}
		if(StringUtil.isBlank(cavType)){
			cavType="1";
		}
		paramMap.put("orgCardId", orgCardId);
		paramMap.put("cusName", cusName);
		paramMap.put("currencyType", currencyType);
		paramMap.put("bussType", bussType);
		paramMap.put("cavType", cavType);
		
		logger.info("suspendedAccInfoDownload paramMap is "+paramMap);
		String contentHead = "交易流水号|交易金额|交易币种|已核销金额|未核销金额|交易创建时间|交易成功时间|核销类型\r\n";
		download(request, response, paramMap, "核销信息下载.txt", contentHead, new IDownLoadContent(){
			@Override
			public String downloadContent(int i,Map<String,Object> paramMap){
				return cusPaymentReportFormsService.suspendedAccInfoDownload(i,paramMap);
			}
		});
	}
	
	
}
