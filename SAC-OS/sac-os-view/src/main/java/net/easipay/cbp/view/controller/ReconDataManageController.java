package net.easipay.cbp.view.controller;

import java.io.IOException;
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

import net.easipay.cbp.cache.CacheUtil;
import net.easipay.cbp.constant.Constants;
import net.easipay.cbp.controller.BaseController;
import net.easipay.cbp.dao.IFinTrialBalancingDao;
import net.easipay.cbp.exception.SacException;
import net.easipay.cbp.model.FinTrialBalancing;
import net.easipay.cbp.model.SacOtrxInfo;
import net.easipay.cbp.model.SacRecDifferences;
import net.easipay.cbp.model.form.SacTransationSendForm;
import net.easipay.cbp.service.IDownLoadContent;
import net.easipay.cbp.service.IFinTrialBalancingService;
import net.easipay.cbp.service.ISacOperHistoryService;
import net.easipay.cbp.service.ISacOtrxInfoService;
import net.easipay.cbp.service.ISacRecDifferencesService;
import net.easipay.cbp.service.ITransactionService;
import net.easipay.cbp.util.BeanUtils;
import net.easipay.cbp.util.ConstantParams;
import net.easipay.cbp.util.DateUtil;
import net.easipay.cbp.util.Utils;
import net.easipay.cbp.util.XlsUploadUtil;
import net.easipay.cbp.validation.SacValidatorSimple;
import net.easipay.dsfc.UnifiedConfig;
import net.easipay.dsfc.UnifiedConfigSimple;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * 差错管理
 * 
 * @author Administrator
 *
 */
@Controller
public class ReconDataManageController extends BaseDataController {
	private static final Logger logger = Logger
			.getLogger(ReconDataManageController.class);

	@Autowired
	private ISacRecDifferencesService sacRecDifferencesService;

	@Autowired
	private ISacOperHistoryService sacOperHistoryService;

	@Autowired
	private ISacOtrxInfoService sacOtrxInfoService;

	@Autowired
	private ITransactionService transactionService;
	
	@Autowired
	private IFinTrialBalancingService finTrialBalancingService;
	

	/**
	 * 差错查询初始化
	 * */
	@RequestMapping(value = "/shortPatternQueryInit", method = RequestMethod.GET)
	public ModelAndView shortPatternQueryInit(HttpServletRequest request,
			HttpServletResponse respons, SacRecDifferences sacRecDifferences)
			throws Exception {
		
		Model model = new ExtendedModelMap();

		String pageNoStr = Utils.trim(request.getParameter("pageNo"));

		int pageNo = Utils.parseInt(pageNoStr, 1);

		// String startDate = DateUtil.formatCurrentDate(Calendar.MONTH, -1,
		// "yyyyMMdd");

		// String endDate = DateUtil.formatCurrentDate(Calendar.DATE, 0,
		// "yyyyMMdd");

		Map<String, Object> queryMap = BeanUtils
				.transBean2Map(sacRecDifferences);
		
		Calendar cr = Calendar.getInstance();

		Date endDate = cr.getTime();

		cr.add(Calendar.MONTH, -1);

		Date startDate = cr.getTime();

		if(StringUtils.isBlank(request.getParameter("recStartDate"))) {

			queryMap.put("recStartDate", startDate);

			sacRecDifferences.setRecStartDate(startDate);
			
		}

		if(StringUtils.isBlank(request.getParameter("recEndDate"))){

			queryMap.put("recEndDate", endDate);
			
			sacRecDifferences.setRecEndDate(endDate);
			
		}
		
		Integer count = sacRecDifferencesService
				.selectSacRecDifferencesCounts(queryMap);

		String pageSizeStr = Utils.trim(request.getParameter("pageSize"));

		int pageSize = Utils.parseInt(pageSizeStr, 10);

		List<SacRecDifferences> sacRecDifferencesList = sacRecDifferencesService
				.selectSacRecDifferences(queryMap, pageNo, pageSize);

		dealList(sacRecDifferencesList);

		List<UnifiedConfig> sysDicStatusList = UnifiedConfigSimple
				.getDicTypeConfig(Constants.DEAL_TYPE);

		List<UnifiedConfig> recDiffTypeList = UnifiedConfigSimple
				.getDicTypeConfig(Constants.REC_DIFF_TYPE);

		List<UnifiedConfig> currencyTypeList = UnifiedConfigSimple.getDicTypeConfig(Constants.CURRENCY_TYPE);

		model.addAttribute("pageSize", "10");

		model.addAttribute("count", count.intValue());

		model.addAttribute("pageNo", pageNo);

		model.addAttribute("sacRecDifferencesList", sacRecDifferencesList);

		model.addAttribute("currencyTypeList", currencyTypeList);

		model.addAttribute("sysDicStatusList", sysDicStatusList);

		model.addAttribute("recDiffTypeList", recDiffTypeList);

		return new ModelAndView("/reconDataManage/shortPatternQueryInit",
				model.asMap());
	}

	/**
	 * 差错查询
	 * */
	@RequestMapping(value = "/shortPatternQueryInit", method = RequestMethod.POST)
	public ModelAndView shortPatternQuery(HttpServletRequest request,
			HttpServletResponse respons, SacRecDifferences sacRecDifferences)
			throws Exception {

		Model model = new ExtendedModelMap();

		String pageNoStr = Utils.trim(request.getParameter("pageNo"));

		int pageNo = Utils.parseInt(pageNoStr, 1);
		
		String pageSizeStr = Utils.trim(request.getParameter("pageSize"));

		int pageSize = Utils.parseInt(pageSizeStr, 10);

		Map<String, Object> queryMap = BeanUtils
				.transBean2Map(sacRecDifferences);

		Integer count = sacRecDifferencesService
				.selectSacRecDifferencesCounts(queryMap);

		List<SacRecDifferences> sacRecDifferencesList = sacRecDifferencesService
				.selectSacRecDifferences(queryMap, pageNo, pageSize);

		dealList(sacRecDifferencesList);

		List<UnifiedConfig> sysDicStatusList = UnifiedConfigSimple
				.getDicTypeConfig(Constants.DEAL_TYPE);

		List<UnifiedConfig> recDiffTypeList = UnifiedConfigSimple
				.getDicTypeConfig(Constants.REC_DIFF_TYPE);

		List<UnifiedConfig> currencyTypeList = UnifiedConfigSimple.getDicTypeConfig(Constants.CURRENCY_TYPE);

		model.addAttribute("pageSize", "10");

		model.addAttribute("count", count.intValue());

		model.addAttribute("pageNo", pageNo);

		model.addAttribute("sacRecDifferencesList", sacRecDifferencesList);

		model.addAttribute("currencyTypeList", currencyTypeList);

		model.addAttribute("sysDicStatusList", sysDicStatusList);

		model.addAttribute("recDiffTypeList", recDiffTypeList);

		return new ModelAndView("/reconDataManage/shortPatternQueryInit",
				model.asMap());
	}

	/**
	 * 差错下载
	 * @param request
	 * @param response
	 * @param otrxInfo
	 * @throws ParseException 
	 */
	@RequestMapping(value="/shortPatternQueryDownload",method={RequestMethod.POST})
	public void shortPatternQueryDownload(HttpServletRequest request,HttpServletResponse response, SacRecDifferences sacRecDifferences) throws ParseException{
		//组装查询参数
		Map<String,Object> paramMap =  new HashMap<String,Object>();
		paramMap = BeanUtils.transBean2Map(sacRecDifferences);
		logger.info("shortPatternQueryDownload paramMap is "+paramMap);

		String contentHead = "序号|交易流水号|外部流水号|对账文件支付金额|原交易支付金额|币种|差错类型|交易类型|支付渠道类型|处理状态|对账开始时间|对账结束时间|交易时间\r\n";
		download(request, response, paramMap, "差错下载.txt", contentHead, new IDownLoadContent(){
			@Override
			public String downloadContent(int i,Map<String,Object> paramMap){
				return sacRecDifferencesService.sacRecDifferencesDownloadContent(i, paramMap);
			}
		});
	}
	/**
	 * 初始查询退款信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/refundDealInit", method = RequestMethod.GET)
	public ModelAndView refundDealInit(HttpServletRequest request,
			HttpServletResponse response, SacOtrxInfo sacOtrxInfo) {

		Model model = new ExtendedModelMap();

		String pageNoStr = Utils.trim(request.getParameter("pageNo"));

		int pageNo = Utils.parseInt(pageNoStr, 1);

		Integer count = sacOtrxInfoService
				.selectSacOtrxInfoCountsForRefund(sacOtrxInfo);

		String pageSizeStr = Utils.trim(request.getParameter("pageSize"));

		int pageSize = Utils.parseInt(pageSizeStr, 10);

		List<SacOtrxInfo> sacOtrxInfoList = sacOtrxInfoService
				.selectSacOtrxInfoForRefund(sacOtrxInfo, pageNo, pageSize);

		List<UnifiedConfig> trxStateList = UnifiedConfigSimple
				.getDicTypeConfig(Constants.TRANSACTION_STATE);

		List<UnifiedConfig> confirmStateList = UnifiedConfigSimple
				.getDicTypeConfig(Constants.CONFIRM_STATE);

		List<UnifiedConfig> currencyTypeList = UnifiedConfigSimple.getDicTypeConfig(Constants.CURRENCY_TYPE);

		model.addAttribute("count", count.intValue());

		model.addAttribute("pageNo", pageNo);

		model.addAttribute("sacOtrxInfoList", sacOtrxInfoList);

		model.addAttribute("currencyTypeList", currencyTypeList);

		model.addAttribute("trxStateList", trxStateList);

		model.addAttribute("confirmStateList", confirmStateList);

		return new ModelAndView("/reconDataManage/refundInit", model.asMap());

	}

	/**
	 * 查询退款信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/refundDealInit", method = RequestMethod.POST)
	public ModelAndView refundDeal(HttpServletRequest request,
			HttpServletResponse response, SacOtrxInfo sacOtrxInfo) {

		Model model = new ExtendedModelMap();

		String pageNoStr = Utils.trim(request.getParameter("pageNo"));

		int pageNo = Utils.parseInt(pageNoStr, 1);

		Integer count = sacOtrxInfoService
				.selectSacOtrxInfoCountsForRefund(sacOtrxInfo);

		String pageSizeStr = Utils.trim(request.getParameter("pageSize"));

		int pageSize = Utils.parseInt(pageSizeStr, 10);

		List<SacOtrxInfo> sacOtrxInfoList = sacOtrxInfoService
				.selectSacOtrxInfoForRefund(sacOtrxInfo, pageNo, pageSize);

		List<UnifiedConfig> trxStateList = UnifiedConfigSimple
				.getDicTypeConfig(Constants.TRANSACTION_STATE);

		List<UnifiedConfig> confirmStateList = UnifiedConfigSimple
				.getDicTypeConfig(Constants.CONFIRM_STATE);

		List<UnifiedConfig> currencyTypeList = UnifiedConfigSimple.getDicTypeConfig(Constants.CURRENCY_TYPE);

		model.addAttribute("count", count.intValue());

		model.addAttribute("pageNo", pageNo);

		model.addAttribute("sacOtrxInfoList", sacOtrxInfoList);

		model.addAttribute("currencyTypeList", currencyTypeList);

		model.addAttribute("trxStateList", trxStateList);

		model.addAttribute("confirmStateList", confirmStateList);

		return new ModelAndView("/reconDataManage/refundInit", model.asMap());

	}

	/**
	 * 查询退款信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/showRefundDetail", method = RequestMethod.POST)
	public String showRefundDetail(HttpServletRequest request,
			HttpServletResponse response, SacOtrxInfo sacOtrxInfo) {

		sacOtrxInfo = sacOtrxInfoService.selectSacOtrxInfoById(sacOtrxInfo);

		String content = JSONObject.fromObject(sacOtrxInfo).toString();

		return content;

	}

	/**
	 * 退款处理
	 * 
	 * @param request
	 * @param response
	 * @param sacRefund
	 * @return
	 * @throws Exception
	 */
	/*@RequestMapping(value = "/refundConfirm", method = RequestMethod.POST)
	public void refundConfirm(HttpServletRequest request,
			HttpServletResponse response, SacOtrxInfo sacOtrxInfo)
			throws Exception {

		String flag = request.getParameter("flag");

		String memo = sacOtrxInfo.getMemo();

		sacOtrxInfo = sacOtrxInfoService.selectSacOtrxInfoById(sacOtrxInfo);

		sacOtrxInfo.setMemo(memo);
		sacOtrxInfo.setConfirmTime(Calendar.getInstance().getTime());
		SecurityOperator person = PersonUtil.getUser();
		sacOtrxInfo.setConfirmUser(person.getUsername());
		if ("Y".equals(flag)) {
			sacOtrxInfo.setConfirmStatus("P");
			// 审核通过
			// String dealType = sacOtrxInfo.getTrxErrDealType();//1：商户退款
			// 2：系统差错退款
			// 1. 若退款方式为差错退款，则需更新差错表该记录的处理状态为已处理，同时更新退款表审核状态为已审核
			// 2. 若退款方式为商户主动退款，则只需更新退款表审核状态为已审核。
			
			 * if("1".equals(dealType)){//商户退款
			 * 
			 * //TODO 调用陈萌更新审核状态接口
			 * sacOtrxInfoService.updateSacOtrxInfo(sacOtrxInfo);
			 * 
			 * }else if("2".equals(dealType)){//系统差错退款
			 * 
			 * sacOtrxInfoService.updateSacOtrxInfo(sacOtrxInfo);
			 * 
			 * SacRecDifferences sacRecDifferences = new SacRecDifferences();
			 * sacRecDifferences.setStatus("S");//已处理
			 * sacRecDifferences.setTrxSerialNo(sacOtrxInfo.getTrxSerialNo());
			 * sacRecDifferencesService
			 * .updateSacRecDifferencesByTrxSerialNo(sacRecDifferences);
			 * 
			 * }
			 
			// 暂时个别银行退款接口没接通 所以需改变流程为：新清算审核，审核完成后 改交易状态为S 对账状态 以及 清分状态 然后分别去记账。
			sacRefundService.dealRefund(sacOtrxInfo);

			sacOtrxInfoService.updateSacOtrxInfo(sacOtrxInfo);

			
			 * //TODO 调用外部交易系统退款接口 去银行退款 同步返回信息 //成功后调用交易状态更新接口（特殊处理 不会记账
			 * 记账放在对账时候），失败直接提示退款失败 Boolean flag1 = true; //接口返回值 if(flag1){
			 * sacOtrxInfo.setTrxState("S");
			 * sacOtrxInfo.setEtrxSerialNo("123");//接口返回外部流水号
			 * transactionService.updateTransactionStateInterface(sacOtrxInfo);
			 * }
			 
			sacOperHistoryService.insertSacOperHistory(
					Constants.OPER_REFUND_PASS, request);
		} else if ("N".equals(flag)) {
			// 审核不通过
			sacOtrxInfo.setConfirmStatus("N");
			String dealType = sacOtrxInfo.getTrxErrDealType();// 1：商户退款 2：系统差错退款
			// 1. 若退款方式为差错退款，则需更新差错表该记录的处理状态为已处理，同时更新退款表审核状态为已审核
			// 2. 若退款方式为商户主动退款，则只需更新退款表审核状态为已审核。
			if ("1".equals(dealType)) {// 差错退款

				sacOtrxInfoService.updateSacOtrxInfo(sacOtrxInfo);

			} else if ("2".equals(dealType)) {// 商户主动退款

				sacOtrxInfoService.updateSacOtrxInfo(sacOtrxInfo);

				SacRecDifferences sacRecDifferences = new SacRecDifferences();
				sacRecDifferences.setStatus("S");// 已处理
				sacRecDifferences.setTrxSerialNo(sacOtrxInfo.getTrxSerialNo());
				sacRecDifferencesService
						.updateSacRecDifferencesByTrxSerialNo(sacRecDifferences);

			}
			// TODO 调用冲正接口 将之前那笔冲掉
			sacOperHistoryService.insertSacOperHistory(
					Constants.OPER_REFUND_FORBID, request);
		}
		response.getWriter().write("{\"success\":true}");
	}*/

	/**
	 * 初始差错调账信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/diffDataAdjustInit", method = RequestMethod.GET)
	public ModelAndView diffDataAdjustInit(HttpServletRequest request,
			HttpServletResponse response) {

		Model model = new ExtendedModelMap();

		List<UnifiedConfig> payTypeList = UnifiedConfigSimple
				.getDicTypeConfig(Constants.PAY_TYPE);

		List<UnifiedConfig> customerTypeList = UnifiedConfigSimple
				.getDicTypeConfig(Constants.CUSTOMER_TYPE);

		List<UnifiedConfig> currencyTypeList = UnifiedConfigSimple.getDicTypeConfig(Constants.CURRENCY_TYPE);

		model.addAttribute("payTypeList", payTypeList);

		model.addAttribute("customerTypeList", customerTypeList);

		model.addAttribute("currencyTypeList", currencyTypeList);

		return new ModelAndView("/reconDataManage/diffAdjustInit",
				model.asMap());

	}

	/**
	 * 补单录入
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping(value = "/addConfirmForDiff", method = RequestMethod.POST)
	public void addConfirmForDiff(HttpServletRequest request,
			HttpServletResponse response, SacOtrxInfo sacOtrxInfo)
			throws Exception {

		String etrxSerialNo = sacOtrxInfo.getEtrxSerialNo();// 外部流水号

		String trxSerialNo = sacOtrxInfo.getTrxSerialNo();// 交易流水号

		String trxTimeString = request.getParameter("trxTimeString");

		Date trxTime = DateUtil.convertStringToDate("yyyyMMddHHmmss",
				trxTimeString);

		sacOtrxInfo.setTrxTime(trxTime);

		Map<String, Object> queryMap = new HashMap<String, Object>();

		queryMap.put("trxSerialNo", trxSerialNo);

		List<SacRecDifferences> sacRecDifferencesListForT = sacRecDifferencesService
				.selectSacRecDifferencesByParam(queryMap);

		if (sacRecDifferencesListForT == null
				|| sacRecDifferencesListForT.size() == 0) {
			response.getWriter().write(
					"{\"success\":false,\"message\":\"流水号对应的差错数据不存在\"}");
			return;
		}

		queryMap.clear();

		queryMap.put("bankSerialNo", etrxSerialNo);

		List<SacRecDifferences> sacRecDifferencesListForE = sacRecDifferencesService
				.selectSacRecDifferencesByParam(queryMap);

		if (sacRecDifferencesListForE == null
				|| sacRecDifferencesListForE.size() == 0) {
			response.getWriter().write(
					"{\"success\":false,\"message\":\"外部流水号对应的差错数据不存在\"}");
			return;
		}

		// 插入历史操作表
		sacOperHistoryService.insertSacOperHistory(
				Constants.OPER_MANUAL_ADD_RECORD, request);// 补单

		// 调用客户账务系统交易接收接口
		List<SacOtrxInfo> list = new ArrayList<SacOtrxInfo>();
		// sacOtrxInfo.setTrxType(Constants.ADJUST_DATA);//补单的交易类型
		list.add(sacOtrxInfo);
		transactionService.transactionDealNewInterface(list);

		// 调用5.9 更新差错数据状态接口
		/*
		 * SacRecDifferences sacRecDifferencesForE =
		 * sacRecDifferencesListForE.get(0);
		 * 
		 * transactionService.updateSacRecDifferencesStateInterface(
		 * sacRecDifferencesForE);
		 * 
		 * SacRecDifferences sacRecDifferencesForT =
		 * sacRecDifferencesListForT.get(0);
		 * 
		 * transactionService.updateSacRecDifferencesStateInterface(
		 * sacRecDifferencesForT);
		 */

		response.getWriter().write("{\"success\":true}");

	}


	private List<SacRecDifferences> dealList(
			List<SacRecDifferences> sacRecDifferencesList) {
		Map<String, Object> trxTypeMap = CacheUtil
				.getCacheByTypeToMap(ConstantParams.SAC_TRX_TYPE);
		for (SacRecDifferences sacRecDifferences : sacRecDifferencesList) {
			// 交易类型
			sacRecDifferences.setTrxCode(trxTypeMap.get(sacRecDifferences
					.getTrxCode()) == null ? "-" : trxTypeMap.get(
					sacRecDifferences.getTrxCode()).toString());
		}
		return sacRecDifferencesList;

	}
	
	private List<Map<String,Object>> dealMapList(
			List<Map<String,Object>> mapList) {
		Map<String, Object> trxTypeMap = CacheUtil
				.getCacheByTypeToMap(ConstantParams.SAC_TRX_TYPE);
		for (Map<String,Object> map : mapList) {
			// 交易类型
			map.put("trxCode",(trxTypeMap.get(
					map.get("trxCode")) == null ? "-" : trxTypeMap.get(
							map.get("trxCode")).toString()+"("+map.get("trxCode")+")" ));
		}
		return mapList;

	}
	
	/**
	 * 批量补单
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping(value = "/uploadTransactionBatch", method = RequestMethod.GET)
	public ModelAndView uploadTransaction(HttpServletRequest request,
			HttpServletResponse response, SacOtrxInfo sacOtrxInfo)
			throws Exception {
		Model model = new ExtendedModelMap();

		return new ModelAndView("/reconDataManage/uploadTransactionBatch",
				model.asMap());

	}

	@RequestMapping(value = "/uploadTransactionBatchCommit", method = RequestMethod.POST)
	public ModelAndView uploadTransactionBatchCommit(
			@RequestParam MultipartFile[] myfiles, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView model = new ModelAndView("reconDataManage/uploadTransactionBatch");
		
		List<SacTransationSendForm> trxList =XlsUploadUtil.uploadXlsFile(myfiles, request);
		
		
		try {
			SacValidatorSimple.validateList(trxList);
		} catch (SacException e) {
			logger.error("",e);
			String errorMsg = e.getMessage();
			model.addObject("message", errorMsg);
			return model;
		}
		
		// 插入历史操作表
		sacOperHistoryService.insertSacOperHistory(
				Constants.OPER_MANUAL_ADD_RECORD, request);// 补单

		// 调用客户账务系统交易接收接口
		transactionService.transactionSendAfterValidate(trxList);
		
		model.addObject("message", "批量录入成功");
		return model;
	}
	
	
	@RequestMapping(value = "/supplementTrxQueryInit", method = RequestMethod.GET)
	public ModelAndView supplementTrxQueryInit(HttpServletRequest request,
			HttpServletResponse response,SacRecDifferences sacRecDifferences) throws Exception {

		Model model = new ExtendedModelMap();

		String pageNoStr = Utils.trim(request.getParameter("pageNo"));

		int pageNo = Utils.parseInt(pageNoStr, 1);

		Map<String, Object> queryMap = BeanUtils
				.transBean2Map(sacRecDifferences);

		String startDate = DateUtil.formatCurrentDate(Calendar.DAY_OF_MONTH, -2, "yyyyMMdd");
		
		if (StringUtils.isBlank(request.getParameter("startDate"))) {
			

			queryMap.put("startDate", startDate);

		}
		
		String endDate = DateUtil.formatCurrentDate(Calendar.DAY_OF_MONTH, -1, "yyyyMMdd");

		if (StringUtils.isBlank(request.getParameter("endDate"))) {

			queryMap.put("endDate", endDate);

		}

		Integer count = sacRecDifferencesService
				.selectDifferencesForSupplementCounts(queryMap);

		String pageSizeStr = Utils.trim(request.getParameter("pageSize"));

		int pageSize = Utils.parseInt(pageSizeStr, 10);

		List<Map<String,Object>> sacRecDifferencesList = sacRecDifferencesService
				.selectDifferencesForSupplement(queryMap, pageNo, pageSize);

		dealMapList(sacRecDifferencesList);

		List<UnifiedConfig> sysDicStatusList = UnifiedConfigSimple
				.getDicTypeConfig(Constants.DEAL_TYPE);

		List<UnifiedConfig> recDiffTypeList = UnifiedConfigSimple
				.getDicTypeConfig(Constants.REC_DIFF_TYPE);

		List<UnifiedConfig> currencyTypeList = UnifiedConfigSimple.getDicTypeConfig(Constants.CURRENCY_TYPE);
		
		List<UnifiedConfig> supplementFlagList = UnifiedConfigSimple
				.getDicTypeConfig(Constants.SUPPLEMENT_FLAG);

		List<UnifiedConfig> trxTypeList = UnifiedConfigSimple.getDicTypeConfig(Constants.TRX_TYPE);
		
		model.addAttribute("pageSize", "10");

		model.addAttribute("count", count.intValue());

		model.addAttribute("pageNo", pageNo);

		model.addAttribute("sacRecDifferencesList", sacRecDifferencesList);

		model.addAttribute("currencyTypeList", currencyTypeList);

		model.addAttribute("sysDicStatusList", sysDicStatusList);

		model.addAttribute("recDiffTypeList", recDiffTypeList);
		
		model.addAttribute("supplementFlagList", supplementFlagList);
		
		model.addAttribute("trxTypeList", trxTypeList);
		
		model.addAttribute("startDate", startDate);
		
		model.addAttribute("endDate", endDate);

		return new ModelAndView("/reconDataManage/SupplementTrxManual",
				model.asMap());
	}
	
	@RequestMapping(value = "/supplementTrxQueryInit", method = RequestMethod.POST)
	public ModelAndView supplementTrxQuery(HttpServletRequest request,
			HttpServletResponse response,SacRecDifferences sacRecDifferences) throws Exception {

		Model model = new ExtendedModelMap();

		String pageNoStr = Utils.trim(request.getParameter("pageNo"));

		int pageNo = Utils.parseInt(pageNoStr, 1);

		Map<String, Object> queryMap = BeanUtils
				.transBean2Map(sacRecDifferences);
		
		String startDate = request.getParameter("startDate");
		
		String endDate = request.getParameter("endDate");
		
		queryMap.put("startDate", startDate);
		
		queryMap.put("endDate", endDate);

		Integer count = sacRecDifferencesService
				.selectDifferencesForSupplementCounts(queryMap);

		String pageSizeStr = Utils.trim(request.getParameter("pageSize"));

		int pageSize = Utils.parseInt(pageSizeStr, 10);

		List<Map<String,Object>> sacRecDifferencesList = sacRecDifferencesService
				.selectDifferencesForSupplement(queryMap, pageNo, pageSize);

		dealMapList(sacRecDifferencesList);

		List<UnifiedConfig> sysDicStatusList = UnifiedConfigSimple
				.getDicTypeConfig(Constants.DEAL_TYPE);
		
		List<UnifiedConfig> supplementFlagList = UnifiedConfigSimple
				.getDicTypeConfig(Constants.SUPPLEMENT_FLAG);

		List<UnifiedConfig> recDiffTypeList = UnifiedConfigSimple
				.getDicTypeConfig(Constants.REC_DIFF_TYPE);

		List<UnifiedConfig> currencyTypeList = UnifiedConfigSimple.getDicTypeConfig(Constants.CURRENCY_TYPE);
		
		List<UnifiedConfig> trxTypeList = UnifiedConfigSimple.getDicTypeConfig(Constants.TRX_TYPE);

		model.addAttribute("pageSize", "10");

		model.addAttribute("count", count.intValue());

		model.addAttribute("pageNo", pageNo);

		model.addAttribute("sacRecDifferencesList", sacRecDifferencesList);

		model.addAttribute("currencyTypeList", currencyTypeList);

		model.addAttribute("sysDicStatusList", sysDicStatusList);

		model.addAttribute("recDiffTypeList", recDiffTypeList);
		
		model.addAttribute("trxTypeList", trxTypeList);
		
		model.addAttribute("supplementFlagList", supplementFlagList);
		
		model.addAttribute("startDate", startDate);
		
		model.addAttribute("endDate", endDate);

		return new ModelAndView("/reconDataManage/SupplementTrxManual",
				model.asMap());
	}
	
	@RequestMapping(value = "/supplementTrx", method = RequestMethod.POST)
	public void supplementTrx(HttpServletRequest request,
			HttpServletResponse response,SacRecDifferences sacRecDifferences) throws Exception {
		//根据交易流水号以及交易号调用交易系统接口获取交易进行补单操作
		try {
			//过滤b2c真长款交易 只针对B2B的真假长款以及b2c的假长款进行补单操作，b2c真长款在交易系统已经做好了
			transactionService.supplementTransactionFromTrxSys(sacRecDifferences);
			//更新差错状态为处理中
			sacRecDifferences.setStatus("H");
			sacRecDifferences.setUpdateTime(new Date());
			sacRecDifferencesService.updateSacRecDifferencesByTrxSerialNo(sacRecDifferences);
		} catch (SacException e) {
			logger.error(e.getMessage(), e);
			response.getWriter().write("{\"success\":false}");
			return;
		}
		
		sacOperHistoryService.insertSacOperHistory(Constants.OPER_MANUAL_ADD_RECORD, request);
		
		response.getWriter().write("{\"success\":true}");
	}

	/**
	 * 更新差错状态
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(value = "/handleRecStatus", method = RequestMethod.POST)
	public void handleRecStatus(HttpServletRequest request, HttpServletResponse response) throws IOException{
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("recId", request.getParameter("recId"));
		paramMap.put("trxSerialNo", request.getParameter("trxSerialNo"));
		paramMap.put("diffType", request.getParameter("diffType"));
		paramMap.put("handleFlag", request.getParameter("handleFlag"));
		//paramMap.put("payAmount", request.getParameter("payAmount"));
		//paramMap.put("trxState", request.getParameter("trxSate"));
		try{
			String result = sacRecDifferencesService.handleRecStatus(paramMap);
			// 插入历史操作表
			sacOperHistoryService.insertSacOperHistory(Constants.OPER_DIFF_HANDLE, request);// 差错处理
			if(result==null){
				response.getWriter().write("{\"success\":true}");
			}else{
				response.getWriter().write("{\"success\":false,\"message\":\""+result+"\"}");
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			response.getWriter().write("{\"success\":false}");
		}
	}
	
	
	@RequestMapping(value = "/trialBalanceQueryInit", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView trialBalanceQueryInit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		List<UnifiedConfig> currencyTypeList = UnifiedConfigSimple.getDicTypeConfig(Constants.CURRENCY_TYPE);
		
		Map<String, Object> currencyTypeMap = CacheUtil.getCcyTransferMap2();
		
		Map<String, Object> currencyTypeMap2 = CacheUtil.getCacheByTypeToMap(Constants.CURRENCY_TYPE);

		Model model = new ExtendedModelMap();

		String pageNoStr = Utils.trim(request.getParameter("pageNo"));

		int pageNo = Utils.parseInt(pageNoStr, 1);

		String queryDate = request.getParameter("queryDate");
		
		String currency = request.getParameter("sacCurrency");
		
		Map<String, Object> queryMap = new HashMap<String, Object>();
		String today = DateUtil.formatCurrentDate(Calendar.DATE, 0, "yyyyMMdd");
		if(StringUtils.isBlank(queryDate)){
			queryDate = today;
		}
		if(queryDate.equals(today)){
			queryMap.put("flag", true);
		}else{
			queryMap.put("flag", false);
		}
		
		queryMap.put("queryDate", queryDate);
		
		queryMap.put("sacCurrency", currency);
		
		Integer count = finTrialBalancingService
				.getFinTrialBalancingCounts(queryMap);

		String pageSizeStr = Utils.trim(request.getParameter("pageSize"));

		int pageSize = Utils.parseInt(pageSizeStr, 10);

		List<FinTrialBalancing> finTrialBalancingList = finTrialBalancingService
				.getFinTrialBalancingBySplit(queryMap, pageNo, pageSize);
		
		for(FinTrialBalancing fin : finTrialBalancingList){
			String sacCurrency = fin.getSacCurrency();
			fin.setSacCurrency((String)currencyTypeMap2.get(currencyTypeMap.get(sacCurrency)));
		}

		model.addAttribute("pageSize", "10");

		model.addAttribute("count", count.intValue());

		model.addAttribute("pageNo", pageNo);
		
		model.addAttribute("queryDate", queryDate);
		
		model.addAttribute("sacCurrency", currency);
		
		model.addAttribute("currencyTypeList", currencyTypeList);

		model.addAttribute("finTrialBalancingList", finTrialBalancingList);

		return new ModelAndView("/reconDataManage/trialBalancingQueryInit",
				model.asMap());
	}
}
