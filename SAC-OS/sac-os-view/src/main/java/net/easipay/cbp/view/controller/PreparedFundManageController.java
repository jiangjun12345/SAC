package net.easipay.cbp.view.controller;

import java.io.IOException;
import java.io.Writer;
import java.net.URLEncoder;
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
import net.easipay.cbp.controller.BaseController;
import net.easipay.cbp.model.FinMx;
import net.easipay.cbp.model.FinStatBankForm;
import net.easipay.cbp.model.SacChannelParam;
import net.easipay.cbp.model.SacCusBalance;
import net.easipay.cbp.service.IDownLoadContent;
import net.easipay.cbp.service.IFinStatBankService;
import net.easipay.cbp.service.ISacChannelParamService;
import net.easipay.cbp.util.ConstantParams;
import net.easipay.cbp.util.DateUtil;
import net.easipay.cbp.util.Utils;
import net.easipay.dsfc.UnifiedConfig;
import net.easipay.dsfc.UnifiedConfigSimple;
import net.easipay.dsfc.ws.jws.JwsClient;
import net.easipay.dsfc.ws.jws.JwsException;
import net.easipay.dsfc.ws.jws.JwsResult;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;



/**
 * 备付金管理
 * @author Administrator
 *
 */

@Controller
public class PreparedFundManageController extends BaseController{ 
	private static final Logger logger = Logger.getLogger(PreparedFundManageController.class);
    
    @Autowired
    private ISacChannelParamService sacChannelParamService;
    
    @Autowired
    private IFinStatBankService finStatBankService;
    
    
    /**
     * 备付金每日余额查询
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/preparedFundQuery", method = RequestMethod.GET) 
    public ModelAndView preparedFundQueryInit(HttpServletRequest request, HttpServletResponse response,FinStatBankForm finStatBankForm)throws Exception  
    {
    	
    	Model model = new ExtendedModelMap();
    	
 		String pageNoStr = Utils.trim(request.getParameter("pageNo"));
 		
        int pageNo = Utils.parseInt(pageNoStr, 1);
        
        String pageSizeStr = Utils.trim(request.getParameter("pageSize"));
        
        int pageSize = Utils.parseInt(pageSizeStr, 10);
        
        String startDate = null;
        
        startDate = request.getParameter("startDate");
        
        String endDate = null;
        
        endDate = request.getParameter("endDate");
        
        if(startDate==null||"".equals(startDate)){
        	
        	startDate = DateUtil.formatCurrentDate(Calendar.DATE, -1, "yyyy-MM-dd");
        	
        }
        
        if(endDate==null||"".equals(endDate)){
        	
        	endDate = DateUtil.formatCurrentDate(Calendar.DATE, 0, "yyyy-MM-dd");
        	
        }
        
        String cusId = request.getParameter("cusId");
        
        Map<String, Object> ccyTransferMap = CacheUtil.getCcyTransferMap();
        String currency = ccyTransferMap.get(request.getParameter("currency"))!=null?ccyTransferMap.get(request.getParameter("currency")).toString():"";
        
        
        Map<String,Object> queryMap = new HashMap<String,Object>();
        
        if(cusId!=null&&!"".equals(cusId)&&currency!=null&&!"".equals(currency)){
        	queryMap.put("codeId", "100200"+cusId+currency);//27位
        }else if(cusId!=null&&!"".equals(cusId)){
        	queryMap.put("chnNo", cusId);//chnNo
        }else if(currency!=null&&!"".equals(currency)){
        	queryMap.put("currency", currency);//currency
        }
        
        queryMap.put("startDate", startDate);//默认查询三月内的信息 当前日期不可查
        
        queryMap.put("endDate", endDate);//默认查询三月内的信息 当前日期不可查
        
        Integer count = finStatBankService.selectFinStatBankCounts(queryMap);
        
        List<FinStatBankForm> finStatBankList = finStatBankService.selectFinStatBankForSplit(queryMap, pageNo, pageSize);
        
        List<SacChannelParam> sacChannelParamList = sacChannelParamService.selectUniqueSacChannelParamOfChnNo();
        
        List<UnifiedConfig> ccyList = UnifiedConfigSimple.getDicTypeConfig(Constants.CURRENCY_TYPE);
        
 		model.addAttribute("currencyList", ccyList);
 		
        model.addAttribute("count", count.intValue());
        
        model.addAttribute("pageNo", pageNo);
 		
 		model.addAttribute("finStatBankList", finStatBankList);
 		
 		model.addAttribute("sacChannelParamList", sacChannelParamList);
 		
 		model.addAttribute("finStatBankForm", finStatBankForm);
 		
 		model.addAttribute("startDate", startDate);
 		
 		model.addAttribute("endDate", endDate);
    	 
        return new ModelAndView("/preparedFundManage/preparedFundManageInit", model.asMap()); 
    } 
    
    
    @RequestMapping(value="/preparedFundQuery", method = RequestMethod.POST) 
    public ModelAndView preparedFundQuery(HttpServletRequest request, HttpServletResponse response,FinStatBankForm finStatBankForm)throws Exception  
    {
    	
    	Model model = new ExtendedModelMap();
    	
 		String pageNoStr = Utils.trim(request.getParameter("pageNo"));
 		
        int pageNo = Utils.parseInt(pageNoStr, 1);
        
        String pageSizeStr = Utils.trim(request.getParameter("pageSize"));
        
        int pageSize = Utils.parseInt(pageSizeStr, 10);
        
        String startDate = request.getParameter("startDate");
        
        String endDate = request.getParameter("endDate");
        
        String cusId = request.getParameter("cusId");
        
        Map<String, Object> ccyTransferMap = CacheUtil.getCcyTransferMap();
        String currency = ccyTransferMap.get(request.getParameter("currency"))!=null?ccyTransferMap.get(request.getParameter("currency")).toString():"";
        
        Map<String,Object> queryMap = new HashMap<String,Object>();
        
        if(cusId!=null&&!"".equals(cusId)&&currency!=null&&!"".equals(currency)){
        	queryMap.put("codeId", "100200"+cusId+currency);//27位
        }else if(cusId!=null&&!"".equals(cusId)){
        	queryMap.put("chnNo", cusId);//chnNo
        }else if(currency!=null&&!"".equals(currency)){
        	queryMap.put("currency", currency);//currency
        }
        queryMap.put("startDate", startDate);//默认查询三月内的信息 当前日期不可查
        
        queryMap.put("endDate", endDate);//默认查询三月内的信息 当前日期不可查
        
        Integer count = finStatBankService.selectFinStatBankCounts(queryMap);
        
        List<FinStatBankForm> finStatBankList = finStatBankService.selectFinStatBankForSplit(queryMap, pageNo, pageSize);
        
        List<SacChannelParam> sacChannelParamList = sacChannelParamService.selectUniqueSacChannelParamOfChnNo();
        
        List<UnifiedConfig> ccyList = UnifiedConfigSimple.getDicTypeConfig(Constants.CURRENCY_TYPE);
        
 		model.addAttribute("currencyList", ccyList);
 		
        model.addAttribute("count", count.intValue());
        
        model.addAttribute("pageNo", pageNo);
 		
 		model.addAttribute("finStatBankList", finStatBankList);
 		
 		model.addAttribute("sacChannelParamList", sacChannelParamList);
 		
 		model.addAttribute("finStatBankForm", finStatBankForm);
 		
 		model.addAttribute("startDate", startDate);
 		
 		model.addAttribute("endDate", endDate);
    	 
        return new ModelAndView("/preparedFundManage/preparedFundManageInit", model.asMap()); 
    } 
    
    @RequestMapping(value="/preparedFundDetailQuery",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView preparedFundDetailQuery(HttpServletRequest request,HttpServletResponse response,SacCusBalance cusBalance) throws ParseException{
		logger.debug("preparedFundDetailQuery start …………");
		//将明细页面参数保存在session中
		if("Y".equals(request.getParameter("saveFlag"))){//需要保存
			BaseDataController.saveFormDataToSession(request);
			return null;
		}
		ModelAndView mav = new ModelAndView("preparedFundManage/preparedFundDetailQuery");
		// 页码处理
		Integer pageNo = 1;// 默认为第1页
		if (request.getParameter("pageNo") != null && !"".equals(request.getParameter("pageNo")))
		{
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		}

		Integer pageSize = 10;// 默认每页显示10条
		if (request.getParameter("pageSize") != null && !"".equals(request.getParameter("pageSize")))
		{
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
		}
		//组装查询参数
		Map<String,Object> paramMap =  new HashMap<String,Object>();
		paramMap.put("tradeTime", request.getParameter("statTime"));
		paramMap.put("codeId", request.getParameter("codeId"));
		paramMap.put("currency", request.getParameter("currency"));
		paramMap.put("start", (pageNo-1)*pageSize);//分页起始
		paramMap.put("end", pageSize*pageNo);//分页结束
		logger.info("preparedFundDetailQuery paramMap is "+paramMap);
		//查询结果
		List<FinMx> preparedFundDetailList = finStatBankService.selectPreparedFundDetail(paramMap);
		//返回结果
		mav.addObject("preparedFundDetailList", preparedFundDetailList);
		mav.addObject("totalCount", finStatBankService.selectPreparedFundDetailCount(paramMap));
		mav.addObject("pageNo", pageNo);
		mav.addObject("pageSize", pageSize);
		mav.addObject("trxTypeMap",CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_TRX_TYPE));//交易类型列表
		mav.addObject("bussTypeMap",CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_BUSS_TYPE));//业务类型列表
		mav.addObject("ccyMap",CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_CCY));//币种列表
		mav.addObject("bankName", request.getParameter("bankName"));
		mav.addObject("tradeTime", request.getParameter("statTime"));
		mav.addObject("currency", request.getParameter("currency"));
		mav.addObject("codeId", request.getParameter("codeId"));
		mav.addObject("endDate", request.getParameter("endDate"));
		logger.debug("preparedFundDetailQuery end …………");
		return mav;
	}
    
    @RequestMapping(value="/preparedFundDetailQueryBack",method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView preparedFundDetailQueryBack(HttpServletRequest request,HttpServletResponse response){
		logger.debug("preparedFundDetailQueryBack start …………");
		ModelAndView mav = new ModelAndView("preparedFundManage/preparedFundManageInit");
		HttpSession session = request.getSession();
		// 页码处理
		Integer pageNo = Integer.valueOf(session.getAttribute("pageNo").toString());// 默认为第1页
		Integer pageSize = 10;// 默认每页显示10条
		//组装查询参数
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("start", (pageNo-1)*pageSize);//分页起始
		paramMap.put("end", pageSize*pageNo);//分页结束
		Object cusId = session.getAttribute("cusId");
		Map<String, Object> ccyTransferMap = CacheUtil.getCcyTransferMap();
	    String currency = ccyTransferMap.get(session.getAttribute("currency"))!=null?ccyTransferMap.get(session.getAttribute("currency")).toString():"";
		if(cusId!=null&&!"".equals(cusId)&&currency!=null&&!"".equals(currency)){
			paramMap.put("codeId", "100200"+cusId+currency);//27位
        }else if(cusId!=null&&!"".equals(cusId)){
        	paramMap.put("chnNo", cusId);//chnNo
        }else if(currency!=null&&!"".equals(currency)){
        	paramMap.put("currency", currency);//currency
        }
		paramMap.put("startDate", session.getAttribute("startDate"));
		paramMap.put("endDate", request.getParameter("endDate"));
		logger.info("preparedFundDetailQueryBack paramMap is "+paramMap);
		List<FinStatBankForm> finStatBankList = finStatBankService.selectFinStatBankForSplit(paramMap, pageNo, pageSize);
		List<SacChannelParam> sacChannelParamList = sacChannelParamService.selectUniqueSacChannelParamOfChnNo();
	    List<UnifiedConfig> ccyList = UnifiedConfigSimple.getDicTypeConfig(Constants.CURRENCY_TYPE);
		//准备初始化参数
	    FinStatBankForm finStatBankForm = new FinStatBankForm();
	    finStatBankForm.setCurrency(session.getAttribute("currency").toString());
	    finStatBankForm.setCusId(cusId.toString());
		mav.addObject("pageNo", pageNo);
		mav.addObject("pageSize", pageSize);
		mav.addObject("count", finStatBankService.selectFinStatBankCounts(paramMap));
		mav.addObject("finStatBankList", finStatBankList);
		mav.addObject("currencyList", ccyList);
		mav.addObject("sacChannelParamList", sacChannelParamList);
		mav.addObject("startDate", session.getAttribute("startDate"));
		mav.addObject("endDate", request.getParameter("endDate"));
		mav.addObject("finStatBankForm", finStatBankForm);
		session.removeAttribute("pageNo");
		session.removeAttribute("startDate");
		session.removeAttribute("cusId");
		session.removeAttribute("currency");
		session.removeAttribute("saveFlag");
		return mav;
	}
	
    /**
	 * 备付金实时查询操作
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/preparedFundRealTimeQuery",method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView preparedFundRealTimeQuery(HttpServletRequest request,HttpServletResponse response){
		logger.debug("preparedFundRealTimeQuery start …………");
		ModelAndView mav = new ModelAndView("preparedFundManage/preparedFundRealTimeQuery");
		// 页码处理
		Integer pageNo = 1;// 默认为第1页
		if (request.getParameter("pageNo") != null && !"".equals(request.getParameter("pageNo")))
		{
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		}

		Integer pageSize = 10;// 默认每页显示10条
		if (request.getParameter("pageSize") != null && !"".equals(request.getParameter("pageSize")))
		{
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
		}
		//组装查询参数
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("start", (pageNo-1)*pageSize);//分页起始
		paramMap.put("end", pageSize*pageNo);//分页结束
		paramMap.put("chnName", request.getParameter("chnName"));//渠道名称
		paramMap.put("chnNo", request.getParameter("chnNo"));//渠道号
		logger.info("preparedFundRealTimeQuery paramMap is "+paramMap);
		List<Map<String,Object>> preparedFundRealTimeList = sacChannelParamService.preparedFundRealTimeQuery(paramMap);
		//准备初始化参数
		mav.addObject("pageNo", pageNo);
		mav.addObject("pageSize", pageSize);
		mav.addObject("chnName", request.getParameter("chnName"));//渠道名称
		mav.addObject("chnNo", request.getParameter("chnNo"));//渠道号
		mav.addObject("totalCount", sacChannelParamService.getPreparedFundRealTimeCount(paramMap));
		mav.addObject("preparedFundRealTimeList", preparedFundRealTimeList);
		mav.addObject("ccyMap", CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_CCY));
		return mav;
	}
	
	/**
	 * 备付金实时查询退回操作
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/preparedFundRealTimeQueryBack",method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView preparedFundRealTimeQueryBack(HttpServletRequest request,HttpServletResponse response){
		logger.debug("preparedFundRealTimeQueryBack start …………");
		ModelAndView mav = new ModelAndView("preparedFundManage/preparedFundRealTimeQuery");
		HttpSession session = request.getSession();
		// 页码处理
		Integer pageNo = Integer.valueOf(session.getAttribute("pageNo").toString());// 默认为第1页
		Integer pageSize = 10;// 默认每页显示10条
		//组装查询参数
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("start", (pageNo-1)*pageSize);//分页起始
		paramMap.put("end", pageSize*pageNo);//分页结束
		paramMap.put("chnName", session.getAttribute("chnName"));//渠道名称
		paramMap.put("chnNo", session.getAttribute("chnNo"));//渠道号
		logger.info("preparedFundRealTimeQueryBack paramMap is "+paramMap);
		List<Map<String,Object>> preparedFundRealTimeList = sacChannelParamService.preparedFundRealTimeQuery(paramMap);
		//准备初始化参数
		mav.addObject("pageNo", pageNo);
		mav.addObject("pageSize", pageSize);
		mav.addObject("chnName", session.getAttribute("chnName"));//渠道名称
		mav.addObject("chnNo", session.getAttribute("chnNo"));//渠道号
		mav.addObject("totalCount", sacChannelParamService.getPreparedFundRealTimeCount(paramMap));
		mav.addObject("preparedFundRealTimeList", preparedFundRealTimeList);
		mav.addObject("ccyMap", CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_CCY));
		return mav;
	}
	
	/**
	 * 备付金实时余额明细查询
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="/preparedFundRealTimeDetailQuery",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView preparedFundRealTimeDetailQuery(HttpServletRequest request,HttpServletResponse response,SacCusBalance cusBalance) throws ParseException{
		logger.debug("preparedFundRealTimeDetailQuery start …………");
		//将明细页面参数保存在session中
		if("Y".equals(request.getParameter("saveFlag"))){//需要保存
			BaseDataController.saveFormDataToSession(request);
			return null;
		}
		ModelAndView mav = new ModelAndView("preparedFundManage/preparedFundRealTimeDetailQuery");
		// 页码处理
		Integer pageNo = 1;// 默认为第1页
		if (request.getParameter("pageNo") != null && !"".equals(request.getParameter("pageNo")))
		{
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		}

		Integer pageSize = 10;// 默认每页显示10条
		if (request.getParameter("pageSize") != null && !"".equals(request.getParameter("pageSize")))
		{
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
		}
		//组装查询参数
		Map<String,Object> paramMap =  new HashMap<String,Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -30);
		String startDate = request.getParameter("startDate")==null?sdf.format(cal.getTime()):request.getParameter("startDate");
		Date endDate = request.getParameter("endDate")==null||"".equals(request.getParameter("endDate").trim())? new Date(): sdf.parse(request.getParameter("endDate"));
		cal.setTime(endDate);
		cal.add(Calendar.DATE, +1);//查询时间后一天
		paramMap.put("beginDate", startDate);
		paramMap.put("endDate", sdf.format(cal.getTime()));
		paramMap.put("start", (pageNo-1)*pageSize);//分页起始
		paramMap.put("end", pageSize*pageNo);//分页结束
		paramMap.put("finCode", request.getParameter("finCode")==null?"":request.getParameter("finCode"));//27位渠道号
		paramMap.put("isShow", "1");
		logger.info("preparedFundRealTimeDetailQuery paramMap is "+paramMap);
		List<FinMx> preparedFundRealTimeDetailList = new ArrayList<FinMx>();//分页查询备付金余额明细
		Integer totalCount = 0;//记录总数
		JwsResult result = null;
		try{
			JwsClient jc = new JwsClient("SAC-FA-0001");
			result = jc.putParam(paramMap).call();
			if(result.isSuccess()&&result.getCode().equals(ConstantParams.RTN_CODE_SUCCESSS)){
				preparedFundRealTimeDetailList = result.getList("finMxList",FinMx.class);
				totalCount = result.getIntValue("totalCount");
			}
		}catch(JwsException e){
			logger.error("request FA exception :"+e.getMessage());
			mav.addObject("message","查询出错,请联系管理员！");
			return mav;
		}
		//返回结果
		mav.addObject("preparedFundRealTimeDetailList", preparedFundRealTimeDetailList);
		mav.addObject("totalCount", totalCount);
		mav.addObject("pageNo", pageNo);
		mav.addObject("pageSize", pageSize);
		mav.addObject("startDate", startDate);
		mav.addObject("endDate", request.getParameter("endDate"));
		mav.addObject("finCode", request.getParameter("finCode"));//27位渠道号
		mav.addObject("trxTypeMap",CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_TRX_TYPE));//交易类型列表
		mav.addObject("bussTypeMap",CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_BUSS_TYPE));//业务类型列表
		logger.debug("preparedFundRealTimeDetailQuery end …………");
		return mav;
	}
	
	/**
	 * 备付金实时余额明细下载
	 * @param request
	 * @param response
	 * @param otrxInfo
	 * @throws ParseException 
	 */
	@RequestMapping(value="/preparedFundRealTimeDetailDownload",method={RequestMethod.POST})
	public void preparedFundRealTimeDetailDownload(HttpServletRequest request,HttpServletResponse response) throws ParseException{
		logger.info("preparedFundRealTimeDetailDownload start …………");
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
		paramMap.remove("startDate");
		logger.info("preparedFundRealTimeDetailDownload paramMap is "+paramMap);
		String contentHead = "序号|科目代码|凭证号|凭证时间|借贷标志|借方发生额|贷方发生额|发生额|期初余额|期末余额|结汇损益|业务类型|交易类型|交易时间|交易流水号\r\n";
		download(request, response, paramMap, "备付金实时余额明细下载.txt", contentHead, new IDownLoadContent(){
			@Override
			public String downloadContent(int i,Map<String,Object> paramMap){
				List<FinMx> preparedFundRealTimeDetailList = new ArrayList<FinMx>();//分页查询客户余额明细
				JwsClient jc = new JwsClient("SAC-FA-0001");
				JwsResult result = jc.putParam(paramMap).call();
				if(result.isSuccess()&&result.getCode().equals(ConstantParams.RTN_CODE_SUCCESSS)){
					preparedFundRealTimeDetailList = result.getList("finMxList",FinMx.class);
					if(preparedFundRealTimeDetailList==null||preparedFundRealTimeDetailList.size()==0){
						return null;
					}
				}
				//组装下载文件内容
				Map<String,Object> bussTypeMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_BUSS_TYPE);
				Map<String,Object> trxTypeMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_TRX_TYPE);
				StringBuffer sb = new StringBuffer();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				int j=i*1000+1;
				for(FinMx fm:preparedFundRealTimeDetailList){
					sb.append(j+"|");
					sb.append(fm.getCodeId()+"|");
					sb.append(fm.getPzNo()+"|");
					sb.append(sdf.format(fm.getMxTime())+"|");
					sb.append((fm.getDirection()==1?"借":"贷")+"|");
					sb.append(fm.getfDebit()+"|");
					sb.append(fm.getfCredit()+"|");
					sb.append(fm.getAmount()+"|");
					sb.append(fm.getOpenBal()+"|");
					sb.append(fm.getCloseBal()+"|");
					sb.append((fm.getGains()==null?"-":fm.getGains())+"|");
					sb.append((bussTypeMap.get(fm.getBusiType())==null?"-":bussTypeMap.get(fm.getBusiType()))+"|");
					sb.append((trxTypeMap.get(fm.getTrxCode())==null?"-":trxTypeMap.get(fm.getTrxCode()))+"|");
					sb.append(sdf.format(fm.getTradeTime())+"|");
					sb.append(fm.getSerno()+"\r\n");
					j++;
				}
				return sb.toString();
			}
		});
	}
	
	/**
	 * 分批下载
	 * @param request
	 * @param response
	 * @param paramMap
	 * @param count
	 * @param fileName
	 * @param contentHead
	 */
	protected void download(HttpServletRequest request,HttpServletResponse response,
			Map<String,Object> paramMap,String fileName,String contentHead,IDownLoadContent a){
		Writer out = null;
		try
		{
			//解决火狐和IE中文名乱码
			String userAgent = request.getHeader("User-Agent");
			if (null != userAgent && -1 != userAgent.indexOf("MSIE")) {
				fileName = URLEncoder.encode(fileName, "UTF8");  
	        } else if (null != userAgent && -1 != userAgent.indexOf("Mozilla")) {
	            fileName =  new String( fileName.getBytes("GB2312"), "ISO-8859-1" );
	        }
			// 设置文件输出类型
			response.setContentType("application/octet-stream");
			response.setHeader("Content-disposition", "attachment; filename="+ fileName);
			out = response.getWriter();
			out.write(contentHead);
			//循环写内容
			for(int i=0;;i++){
				paramMap.put("start", i*10000);
				paramMap.put("end", (i+1)*10000);
				String content = a.downloadContent(i,paramMap);
				if(content==null||"".equals(content)){
					break;
				}
				out.write(content);
				content=null;
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}finally{
			try
			{
				out.flush();
				if(out!=null){out.close();}
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
