package net.easipay.cbp.view.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.easipay.cbp.cas.users.SecurityOperator;
import net.easipay.cbp.constant.Constants;
import net.easipay.cbp.constant.EnumConstants.PrestoreOFLConstants.OFLBatchState;
import net.easipay.cbp.constant.EnumConstants.PrestoreOFLConstants.OFLCheckDealState;
import net.easipay.cbp.constant.EnumConstants.PrestoreOFLConstants.OFLDepositDealState;
import net.easipay.cbp.controller.BaseController;
import net.easipay.cbp.exception.SacException;
import net.easipay.cbp.model.SacChargeApply;
import net.easipay.cbp.model.SacDepositBatch;
import net.easipay.cbp.model.SacDepositDetail;
import net.easipay.cbp.model.form.PrestoreDetailForm;
import net.easipay.cbp.model.form.PrestoreDetailReceiveForm;
import net.easipay.cbp.service.ISacChargeApplyService;
import net.easipay.cbp.service.ISacDepositService;
import net.easipay.cbp.service.ISacOperHistoryService;
import net.easipay.cbp.service.ITransactionService;
import net.easipay.cbp.util.BeanUtils;
import net.easipay.cbp.util.DateUtil;
import net.easipay.cbp.util.PersonUtil;
import net.easipay.cbp.util.StringUtil;
import net.easipay.cbp.util.Utils;
import net.easipay.dsfc.UnifiedConfig;
import net.easipay.dsfc.UnifiedConfigSimple;
import net.easipay.dsfc.ws.jws.JwsResult;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PrestoreOfflineController extends BaseController{

	private static final Logger logger = Logger.getLogger(PrestoreOfflineController.class);
	
	private static final String TPL_PRESTORE_OFFLINE_REPORT_XLS = "template/prestoreOfflineReport.xls";
	
	@Autowired
	private ITransactionService transactionService;
	
	@Autowired
	private ISacDepositService sacDepositService;
	
	@Autowired
	private ISacChargeApplyService sacChargeApplyService;
	
	@Autowired
	private ISacOperHistoryService sacOperHistoryService;
	
	private static Map<String,List<PrestoreDetailForm>> cacheListMap = new HashMap<String, List<PrestoreDetailForm>>();
	
	private static Map<String,Object> detailMap = new HashMap<String,Object>();
	
	private static Map<String,Object> applyMap = new HashMap<String,Object>();

    @RequestMapping(value="/prestoreQueryInit", method = RequestMethod.GET) 
    public ModelAndView prestoreQueryInit(HttpServletRequest request, HttpServletResponse response)throws Exception  
    {
    	cacheListMap.remove("cacheList");
    	
    	Model model = new ExtendedModelMap();
    	
    	/*Map<String,Object> queryPrestoreMap = new HashMap<String,Object>();
    	
    	//默认查询今天和昨天的数据
    	
    	String startDate = DateUtil.formatCurrentDate(Calendar.DATE, -1, "yyyyMMdd");
    	
    	String endDate = DateUtil.formatCurrentDate(Calendar.DATE, 0, "yyyyMMdd");
    	
    	queryPrestoreMap.put("startDate",startDate);
    	
    	queryPrestoreMap.put("endDate", endDate);
    	
    	queryPrestoreMap.put("bankCode", "0104");//中行

    	queryPrestoreMap.put("direction", "1");//来款
    	
    	queryPrestoreMap.put("prestoreFlag", "0");//线下预存数据
    	
    	JwsResult result = transactionService.getPrestoreTrxFromDSF(queryPrestoreMap);
    	
    	List<PrestoreDetailReceiveForm> detailList = result.getList("requestData", PrestoreDetailReceiveForm.class);
    	
    	if(detailList==null||detailList.size()<=0){
    		detailList = new ArrayList<PrestoreDetailReceiveForm>();
    	}
    	List<PrestoreDetailForm> transferList = transferList(detailList);
    	
    	Collections.sort(transferList, new Comparator<PrestoreDetailForm>() {
			@Override
			public int compare(PrestoreDetailForm o1, PrestoreDetailForm o2) {
				return -o1.getBankTrxDate().compareTo(o2.getBankTrxDate());
			}
		});*/
    	
    	List<PrestoreDetailReceiveForm> detailList = new ArrayList<PrestoreDetailReceiveForm>();
    	
    	for(int i=0;i<3;i++){
    		PrestoreDetailReceiveForm detaili = new PrestoreDetailReceiveForm();
    		detaili.setSerialNumber("22345678"+i);
    		detaili.setTradeDate("20170821174622");
    		detaili.setFrLineNumber("SPDB000");
    		detaili.setFrBankName("浦发银行");
    		detaili.setFrBankAcctName("西域");
    		detaili.setFrBankAcctNo("45451545");
    		detaili.setAmount("12");
    		detaili.setRemark(":ZTD1CY9B"+i+" 代收付123456777");
    		detailList.add(detaili);
    	}
    	String startDate = DateUtil.formatCurrentDate(Calendar.DATE, -1, "yyyyMMdd");
    	
    	String endDate = DateUtil.formatCurrentDate(Calendar.DATE, 0, "yyyyMMdd");
    	
    	List<PrestoreDetailForm> transferList = transferList(detailList);
    	
    	cacheListMap.put("cacheList", transferList);
    	
    	List<UnifiedConfig> bankList = UnifiedConfigSimple.getDicTypeConfig(Constants.BANK_TYPE);
    	
    	model.addAttribute("detailList", transferList);
    	
    	model.addAttribute("bankList", bankList);
    	
    	model.addAttribute("startDate", startDate);
    	
    	model.addAttribute("endDate", endDate);
    	
    	
        return new ModelAndView("/prestoreManage/prestoreQueryInit", model.asMap()); 
    } 
    
    private List<PrestoreDetailForm> transferList(List<PrestoreDetailReceiveForm> detailList) throws ParseException {
    	List<PrestoreDetailForm> list = new ArrayList<PrestoreDetailForm>();
    	Iterator<PrestoreDetailReceiveForm> it = detailList.iterator();
    	while(it.hasNext()){
    		PrestoreDetailForm form = new PrestoreDetailForm();
    		PrestoreDetailReceiveForm next = it.next();
    		
    		String bankSerialNo = next.getSerialNumber();
    		int counts = sacDepositService.validRepeat(bankSerialNo);
    		if(counts>0){
    			continue;
    		}
    		
    		
    		String bankTrxDate = next.getTradeDate();
    		Date date = DateUtil.convertStringToDate("yyyyMMddHHmmss", bankTrxDate);
    		String dateTime = DateUtil.getDateTime("yyyy-MM-dd HH:mm:ss", date);
    		
    		String remark = next.getRemark();
    		String applyCode = "";
    		applyCode = getApplyCodeFromStr(remark);
    		
    		form.setBankTrxDate(dateTime);
    		form.setApplyCode(applyCode);
    		form.setBankSerialNo(next.getSerialNumber());
    		form.setDbtCode("");//付款方组织机构代码
    		form.setDraccBankName(next.getFrBankName());
    		form.setDraccName(next.getFrBankAcctName());
    		form.setDraccNo(next.getFrBankAcctNo());
    		form.setMemo(remark);
    		form.setPayAmount(new BigDecimal(next.getAmount()));
    		form.setPayCurrency("CNY");
    		list.add(form);
    	}
    	return list;
		
	}

	/*private void dealList(List<SacDepositDetail> listForRepeat,
			List<PrestoreDetailForm> dateList) {
		for(SacDepositDetail detail : listForRepeat){
			PrestoreDetailForm form = new PrestoreDetailForm();
			form.setApplyCode(detail.getApplyCode());
			form.setBankSerialNo(detail.getBankSerialNo());
			form.setBankTrxDate(DateUtil.getDateTime("yyyy-MM-dd HH:mm:ss", detail.getBankTrxDate()));
			form.setDraccBankName(detail.getDraccBankName());
			form.setDraccName(detail.getDraccName());
			form.setDraccNo(detail.getDraccNo());
			form.setMemo(detail.getMemo());
			form.setPayAmount(detail.getPayAmount());
			form.setPayCurrency(detail.getPayCurrency());
			dateList.add(form);
		}
		
	}*/

	@RequestMapping(value="/prestoreQueryInit", method = RequestMethod.POST) 
    public ModelAndView getPrestoreDataByParam(HttpServletRequest request, HttpServletResponse response)throws Exception  
    {
    	cacheListMap.remove("cacheList");
    	
    	Model model = new ExtendedModelMap();
    	
    	Map<String,Object> queryPrestoreMap = new HashMap<String,Object>();
    	
    	//默认查询今天和昨天的数据
    	
    	String startDate = request.getParameter("startDate");
    	
    	if(StringUtils.isBlank(startDate)){
    		
    		startDate = DateUtil.formatCurrentDate(Calendar.DATE, -1, "yyyyMMdd");
    		
    	}
    	
    	String endDate = request.getParameter("endDate");
    	
    	if(StringUtils.isBlank(endDate)){

        	endDate = DateUtil.formatCurrentDate(Calendar.DATE, 0, "yyyyMMdd");
        	
    	}
    	queryPrestoreMap.put("startDate",startDate);
    	
    	queryPrestoreMap.put("endDate", endDate);
    	
    	queryPrestoreMap.put("bankCode", "0104");//中行

    	queryPrestoreMap.put("direction", "1");//来款
    	
    	queryPrestoreMap.put("prestoreFlag", "0");//线下预存数据
    	
    	JwsResult result = transactionService.getPrestoreTrxFromDSF(queryPrestoreMap);
    	
    	List<PrestoreDetailReceiveForm> detailList = result.getList("requestData", PrestoreDetailReceiveForm.class);
    	
    	
    	/*List<PrestoreDetailReceiveForm> detailList = new ArrayList<PrestoreDetailReceiveForm>();
    	
    	for(int i=0;i<10;i++){
    		PrestoreDetailReceiveForm detaili = new PrestoreDetailReceiveForm();
    		detaili.setSerialNumber("12345678"+i);
    		detaili.setTradeDate("20160301174622");
    		detaili.setFrLineNumber("BOC0000");
    		detaili.setFrBankName("北京银行");
    		detaili.setFrBankAcctName("上海亿马物流系统有限公司0709");
    		detaili.setFrBankAcctNo("62179210");
    		detaili.setAmount("1000");
    		detaili.setRemark("QWERT"+i+"代收付123456777");
    		detailList.add(detaili);
    	}
    	String startDate = DateUtil.formatCurrentDate(Calendar.DATE, -1, "yyyyMMdd");
    	
    	String endDate = DateUtil.formatCurrentDate(Calendar.DATE, 0, "yyyyMMdd");
    	
    	List<PrestoreDetailForm> transferList = transferList(detailList);*/
    	
    	if(detailList==null||detailList.size()<=0){
    		detailList = new ArrayList<PrestoreDetailReceiveForm>();
    	}
    	List<PrestoreDetailForm> transferList = transferList(detailList);
    	
    	Collections.sort(transferList, new Comparator<PrestoreDetailForm>() {
			@Override
			public int compare(PrestoreDetailForm o1, PrestoreDetailForm o2) {
				return -o1.getBankTrxDate().compareTo(o2.getBankTrxDate());
			}
		});
    	
    	/*//将需要重新制作的预存信息加入进来
    	Map<String,Object> queryMap = new HashMap<String, Object>();
    	queryMap.put("dealState", OFLDepositDealState.WaitMakeBatch.code());
    	List<SacDepositDetail> listForRepeat = sacDepositService.findDepositDetailByParam(queryMap);
    	dealList(listForRepeat,transferList);*/
    	cacheListMap.put("cacheList", transferList);
    	
    	List<UnifiedConfig> bankList = UnifiedConfigSimple.getDicTypeConfig(Constants.BANK_TYPE);
    	
    	model.addAttribute("detailList", transferList);
    	
    	model.addAttribute("bankList", bankList);
    	
    	model.addAttribute("startDate", startDate);
    	
    	model.addAttribute("endDate", endDate);
    	
        return new ModelAndView("/prestoreManage/prestoreQueryInit", model.asMap());  
    } 
    
	@RequestMapping(value="/createPrestoreBatch", method = RequestMethod.POST) 
    public void createPrestoreBatch(HttpServletRequest request, HttpServletResponse response)throws Exception  
    {
    	String serialNoListStr =request.getParameter("serialNoListStr");
    	
    	List<PrestoreDetailForm> list = cacheListMap.get("cacheList");
    	
    	//String craccBankName = request.getParameter("craccBankName");
    	
    	//String craccNodeCode = request.getParameter("craccNodeCode");
    	
    	try {
    		String msg = sacDepositService.deal(serialNoListStr,list);
    		
    		sacOperHistoryService.insertSacOperHistory(Constants.OPER_PRESTORE_CREATE_BATCH,request);
        	
    		if(StringUtil.isNotBlank(msg)){
    			response.getWriter().write(
    					"{\"success\":true,\"message\":\""+msg+"\"}");
    			return;
    		}
    		
        	response.getWriter().write("{\"success\":true}");
        	
		} catch (SacException e) {
			String message = e.getMessage();
			response.getWriter().write(
					"{\"success\":false,\"message\":\""+message+"\"}");
			return;
		}
    	
    }
	
    
    @RequestMapping(value="/prestoreBatchCheckInit", method = RequestMethod.GET) 
    public ModelAndView prestoreBatchCheckInit(HttpServletRequest request, HttpServletResponse response,SacDepositBatch depositBatch)throws Exception  
    {
    	
    	Model model = new ExtendedModelMap();
    	
 		String pageNoStr = Utils.trim(request.getParameter("pageNo"));
 		
        int pageNo = Utils.parseInt(pageNoStr, 1);
        
        String pageSizeStr = Utils.trim(request.getParameter("pageSize"));
        
        int pageSize = Utils.parseInt(pageSizeStr, 10);
        
        Map<String,Object> queryMap = new HashMap<String,Object>();
        
        queryMap.put("batchState", OFLBatchState.Init.code());
        
        Integer count = sacDepositService.getBatchCountsByParam(queryMap);
        
        List<SacDepositBatch> batchList = sacDepositService.getDepositBatchByParam(queryMap, pageNo, pageSize);
 		
        BigDecimal totalAmount = new BigDecimal("0.00");
        Iterator<SacDepositBatch> it = batchList.iterator();
        while(it.hasNext()){
        	SacDepositBatch batch = it.next();
        	BigDecimal batchTamount = batch.getBatchTamount();
        	totalAmount = totalAmount.add(batchTamount);
        }
        model.addAttribute("count", count.intValue());
        
        model.addAttribute("pageNo", pageNo);
 		
 		model.addAttribute("batchList", batchList);
 		
 		model.addAttribute("totalAmount", totalAmount);
 		
        return new ModelAndView("/prestoreManage/prestoreBatchCheckInit", model.asMap()); 
    } 
    
    @RequestMapping(value="/getPrestoreDetailByBatch", method = RequestMethod.GET) 
    public ModelAndView getPrestoreDetailByBatch(HttpServletRequest request, HttpServletResponse response,SacDepositBatch depositBatch)throws Exception  
    {
    	
    	Model model = new ExtendedModelMap();
    	
    	Long oflDepositBatchId = depositBatch.getOflDepositBatchId();
    	
    	Map<String,Object> queryMap = new HashMap<String, Object>();
        
        queryMap.put("oflDepositBatchId", oflDepositBatchId);
    	
        List<SacDepositBatch> batchList = sacDepositService.getDepositBatchByParam(queryMap, 1, 10);
        
        SacDepositBatch batch = batchList.get(0);

 		String pageNoStr = Utils.trim(request.getParameter("pageNo"));
 		
        int pageNo = Utils.parseInt(pageNoStr, 1);
        
        String pageSizeStr = Utils.trim(request.getParameter("pageSize"));
        
        int pageSize = Utils.parseInt(pageSizeStr, 10);
        
        Integer count = sacDepositService.getDepositDetailCountsByParam(queryMap);
        
        List<SacDepositDetail> detailList  = sacDepositService.getDepositDetailByPaging(queryMap, pageNo, pageSize);
        
 		model.addAttribute("batch", batch);
 		
 		model.addAttribute("detailList", detailList);
 		
	    model.addAttribute("count", count.intValue());
         
        model.addAttribute("pageNo", pageNo);
  		
 		
        return new ModelAndView("/prestoreManage/prestoreBatchDetail", model.asMap()); 
    } 
    
    @RequestMapping(value="/prestoreDetailDownload", method = RequestMethod.POST) 
    public void prestoreDetailDownload(HttpServletRequest request, HttpServletResponse response,SacDepositBatch depositBatch)throws Exception  
    {
    	
    	Long oflDepositBatchId = depositBatch.getOflDepositBatchId();
    	
    	Map<String,Object> queryMap = new HashMap<String, Object>();
        
        queryMap.put("oflDepositBatchId", oflDepositBatchId);
    	
        List<SacDepositBatch> batchList = sacDepositService.getDepositBatchByParam(queryMap, 1, 10);
        
        SacDepositBatch batch = batchList.get(0);

        List<SacDepositDetail> detailList  = sacDepositService.getDepositDetailByPaging(queryMap, 1, Integer.MAX_VALUE);
        
        String craccNodeCode = batch.getCraccNodeCode();
        String craccBankName = batch.getCraccBankName();
        BigDecimal totalAmount = batch.getBatchTamount();
        Long totalCount = batch.getBatchTcount();
        
        queryMap.put("craccNodeCode", craccNodeCode);
        queryMap.put("craccBankName", craccBankName);
        queryMap.put("totalAmount", totalAmount);
        queryMap.put("totalCount", totalCount);
        sacDepositService.exportPrestoreDetailToExcel(response, queryMap, detailList,TPL_PRESTORE_OFFLINE_REPORT_XLS);
    } 
    
    @RequestMapping(value="/prestoreBatchPass", method = RequestMethod.POST) 
    public void prestoreBatchPass(HttpServletRequest request, HttpServletResponse response,SacDepositBatch depositBatch)throws Exception  
    {
    	
    	String batchIds =request.getParameter("batchIds");
    	
    	String[] splitedIds = batchIds.split("\\|");
    	
    	for(String batchId : splitedIds){
    		long id = Long.parseLong(batchId);
    		Map<String,Object> map = new HashMap<String,Object>();
    		map.put("oflDepositBatchId", id);
    		List<SacDepositDetail> detailList = sacDepositService.findDepositDetailByParam(map);
    		
    		List<SacDepositBatch> batchList = sacDepositService.getDepositBatchByParam(map, 1, 10);
    		SacDepositBatch batch = batchList.get(0);
    		
    		if(OFLBatchState.ReviewPass.code().equals(batch.getBatchState())||OFLBatchState.ReviewReject.code().equals(batch.getBatchState())){
    			continue;
    		}
    		
    		int countDff = 0;
    		BigDecimal amountDff = new BigDecimal("0.00");
    		for(SacDepositDetail detail : detailList){
    			String applyCode = detail.getApplyCode();
    			if(applyCode.contains("Z")||applyCode.trim().length()>8){
    				countDff = countDff+1;
    				amountDff = amountDff.add(detail.getPayAmount());
    			}
    		}
    		
    		for(SacDepositDetail detail : detailList){
    			sacDepositService.passConfirm(detail,batch,countDff,amountDff);
    		}
    		
    		batch.setAuditMemo("复核通过");
    		batch.setBatchState(OFLBatchState.ReviewPass.code());
    		sacDepositService.updateDepositBatch(batch);
    	}
    	
    	sacOperHistoryService.insertSacOperHistory(Constants.OPER_PRESTORE_CHECK_BATCH, request);
    	
    	response.getWriter().write("{\"success\":true}"); 
    } 
    
    
    @RequestMapping(value="/prestoreBatchPassFailue", method = RequestMethod.POST) 
    public void prestoreBatchPassFailue(HttpServletRequest request, HttpServletResponse response,SacDepositBatch depositBatch)throws Exception  
    {
    	
    	String batchIds =request.getParameter("batchIds");
    	
    	String[] splitedIds = batchIds.split("\\|");
    	
    	for(String batchId : splitedIds){
    		long id = Long.parseLong(batchId);
    		Map<String,Object> queryMap = new HashMap<String,Object>();
    		queryMap.put("oflDepositBatchId", id);
    		
    		List<SacDepositBatch> batchList = sacDepositService.getDepositBatchByParam(queryMap, 1, Integer.MAX_VALUE);
    		SacDepositBatch bt = batchList.get(0);
    		String craccNodeCode = bt.getCraccNodeCode();
    		if("BOC0000".equals(craccNodeCode)){

        		List<SacDepositDetail> detailList = sacDepositService.findDepositDetailByParam(queryMap);
        		for(SacDepositDetail detail : detailList){
        			String dealState = detail.getDealState();
        			if(OFLDepositDealState.InitFail.code().equals(dealState)||OFLDepositDealState.InitSuc.code().equals(dealState)){
        				sacDepositService.passFailue(detail);
        			}
        		}
        		SacDepositBatch batch = new SacDepositBatch();
        		batch.setOflDepositBatchId(id);
        		batch.setAuditMemo("复核不通过");
        		batch.setBatchState(OFLBatchState.ReviewReject.code());
        		sacDepositService.updateDepositBatch(batch);
    		}else if("SPDB000".equals(craccNodeCode)){
    			sacDepositService.spdbCheckFailue(bt);
    		}
    		
    		
    		
    	}
    	
    	sacOperHistoryService.insertSacOperHistory(Constants.OPER_PRESTORE_CHECK_BATCH, request);
    	
    	response.getWriter().write("{\"success\":true}"); 
    } 
    
    
    @RequestMapping(value="/prestoreManualMatchInit", method = RequestMethod.GET) 
    public ModelAndView prestoreManualMatchInit(HttpServletRequest request, HttpServletResponse response,SacDepositBatch depositBatch)throws Exception  
    {
    	
    	Model model = new ExtendedModelMap();
    	
    	applyMap.clear();
    	detailMap.clear();
 		
        return new ModelAndView("/prestoreManage/prestoreManualMatchInit", model.asMap()); 
    } 
    @RequestMapping(value="/prestoreApplyQuery", method = {RequestMethod.POST,RequestMethod.GET}) 
    public  ModelAndView prestoreApplyQuery(HttpServletRequest request, HttpServletResponse response,SacChargeApply chargeApply)throws Exception  
    {
    	
    	applyMap.clear();
    	
    	Model model = new ExtendedModelMap();
    	
    	String startDate = request.getParameter("startDate");
    	
    	if(StringUtils.isNotBlank(startDate)){
    		startDate = startDate.replaceAll("-", "");
    	}
    	
    	String endDate = request.getParameter("endDate");
    	
    	if(StringUtils.isNotBlank(endDate)){
    		endDate = endDate.replaceAll("-", "");
    	}

    	Map<String, Object> queryMap = BeanUtils.transBean2Map(chargeApply);
    	
    	queryMap.put("startDate", startDate);
    	
    	queryMap.put("endDate", endDate);
    	
    	queryMap.put("applyState", Constants.CHARGE_STATE_0);
    	
    	String pageNoStr = Utils.trim(request.getParameter("pageNo"));
 		
        int pageNo = Utils.parseInt(pageNoStr, 1);
        
        String pageSizeStr = Utils.trim(request.getParameter("pageSize"));
        
        int pageSize = Utils.parseInt(pageSizeStr, 10);
        
        Integer count = sacChargeApplyService.selectApplyCountsByParam(queryMap);
        
    	List<SacChargeApply> applyList = sacChargeApplyService.selectApplyByPaging(queryMap,pageNo,pageSize);
    	
    	model.addAttribute("applyList", applyList);
    	model.addAttribute("count", count);
    	model.addAttribute("pageNo", pageNo);
    	model.addAttribute("startDate", startDate);
    	model.addAttribute("endDate", endDate);
    	model.addAttribute("payAmount", chargeApply.getPayAmount());
    	model.addAttribute("applyCode", chargeApply.getApplyCode());
    	model.addAttribute("applyOrgName", chargeApply.getApplyOrgName());
    	applyMap = model.asMap();
    	Map<String,Object> newMap = new HashMap<String,Object>();
    	newMap.putAll(applyMap);
    	newMap.putAll(detailMap);
    	return new ModelAndView("prestoreManage/prestoreManualMatchInit",newMap); 
    } 
    @RequestMapping(value="/prestoreTrxQuery", method = {RequestMethod.POST,RequestMethod.GET}) 
    public ModelAndView prestoreTrxQuery(HttpServletRequest request, HttpServletResponse response,SacDepositDetail depositBatch)throws Exception  
    {
    	
    	detailMap.clear();
    	
    	Model model = new ExtendedModelMap();
    	
    	String startDate = request.getParameter("startDate1");
    	
    	if(StringUtils.isNotBlank(startDate)){
    		startDate = startDate.replaceAll("-", "");
    	}
    	
    	String endDate = request.getParameter("endDate1");
    	
    	if(StringUtils.isNotBlank(endDate)){
    		endDate = endDate.replaceAll("-", "");
    	}
    	
    	String applyCode = request.getParameter("applyCode1");
    	
    	depositBatch.setApplyCode(applyCode);
    	
    	String payAmount = request.getParameter("payAmount1");
    	if(payAmount!=null&&StringUtils.isNotBlank(payAmount)){
    		depositBatch.setPayAmount(new BigDecimal(payAmount));
    	}
    	
    	Map<String, Object> queryMap = BeanUtils.transBean2Map(depositBatch);
    	
    	queryMap.put("startDate", startDate);
    	
    	queryMap.put("endDate", endDate);
    	
    	queryMap.put("dealState", OFLDepositDealState.FailPendingProcess.code());//未成功待处理
    	
    	String pageNoStr = Utils.trim(request.getParameter("pageNo"));
 		
        int pageNo = Utils.parseInt(pageNoStr, 1);
        
        String pageSizeStr = Utils.trim(request.getParameter("pageSize"));
        
        int pageSize = Utils.parseInt(pageSizeStr, 10);
        
        Integer count = sacDepositService.getDepositDetailCountsByParam(queryMap);
        
    	List<SacDepositDetail> detailList = sacDepositService.getDepositDetailByPaging(queryMap,pageNo,pageSize);
    	
    	model.addAttribute("detailList", detailList);
    	model.addAttribute("count1", count);
    	model.addAttribute("pageNo1", pageNo);
    	model.addAttribute("startDate1", startDate);
    	model.addAttribute("endDate1", endDate);
    	model.addAttribute("payAmount1", payAmount);
    	model.addAttribute("applyCode1", applyCode);
    	model.addAttribute("draccName", depositBatch.getDraccName());
    	detailMap = model.asMap();
    	Map<String,Object> newMap = new HashMap<String,Object>();
    	newMap.putAll(detailMap);
    	newMap.putAll(applyMap);
        return new ModelAndView("prestoreManage/prestoreManualMatchInit",newMap);  
    } 
    
    
    @RequestMapping(value="/prestoreMunualMatch", method = RequestMethod.POST) 
    public void prestoreMunualMatch(HttpServletRequest request, HttpServletResponse response)throws Exception  
    {
    	String chargeApplyId = request.getParameter("chargeApplyId");
    	Long applyId = Long.parseLong(chargeApplyId);
    	String oflDepositId = request.getParameter("oflDepositId");
    	Long detailId = Long.parseLong(oflDepositId);
    	
//   	SacChargeApply apply = new SacChargeApply();
//    	apply.setChargeApplyId(applyId);
//    	
//    	sacChargeApplyService.updateChargeApply(apply);
    	
    	SecurityOperator user = PersonUtil.getUser();
    	SacDepositDetail detail = new SacDepositDetail();
    	Date date = new Date();
    	detail.setChargeApplyId(applyId);
    	detail.setOflDepositId(detailId);
    	detail.setDealState(OFLDepositDealState.ManualCancelInit.code());
    	detail.setLastUpdateTime(date);
    	detail.setOperatorId(Long.parseLong(user.getMobile()));
    	detail.setOperatorName(user.getEmail());
    	detail.setOperTime(date);
    	sacDepositService.updateDepositDetail(detail);
    	
    	sacOperHistoryService.insertSacOperHistory(Constants.OPER_PRESTORE_MANUAL_MATCH, request);
    	
    	response.getWriter().write("{\"success\":true}"); 
    	
    } 
    
    @RequestMapping(value="/prestoreMunualMatchCheck", method = {RequestMethod.POST,RequestMethod.GET}) 
    public ModelAndView prestoreMunualMatchCheck(HttpServletRequest request, HttpServletResponse response)throws Exception  
    {
    	
    	Model model = new ExtendedModelMap();
    	
    	String startDate = request.getParameter("startDate");
    	
    	String endDate = request.getParameter("endDate");
    	
    	Map<String, Object> queryMap = new HashMap<String, Object>();
    	
    	if(StringUtils.isBlank(startDate)){
         	startDate = DateUtil.formatCurrentDate(Calendar.DATE, -7, "yyyyMMdd");
        }
        queryMap.put("startDate", startDate);
        if(StringUtils.isBlank(endDate)){
            endDate = DateUtil.formatCurrentDate(Calendar.DATE,0, "yyyyMMdd");
        }
        queryMap.put("endDate", endDate);
    	
    	String pageNoStr = Utils.trim(request.getParameter("pageNo"));
 		
        int pageNo = Utils.parseInt(pageNoStr, 1);
        
        String pageSizeStr = Utils.trim(request.getParameter("pageSize"));
        
        int pageSize = Utils.parseInt(pageSizeStr, 10);
        
        Integer count = sacDepositService.getMunualMatchCheckCounts(queryMap);
        
    	List<Map<String,Object>> checkList = sacDepositService.getMunualMatchCheckInfo(queryMap,pageNo,pageSize);
    	
    	model.addAttribute("checkList", checkList);
    	model.addAttribute("count", count);
    	model.addAttribute("pageNo", pageNo);
    	model.addAttribute("startDate", startDate);
    	model.addAttribute("endDate", endDate);
        return new ModelAndView("prestoreManage/prestoreManualMatchCheckInit",model.asMap());  
    } 
    
    @RequestMapping(value="/prestoreMunualMatchCheckPass", method = {RequestMethod.POST,RequestMethod.GET}) 
    public void prestoreMunualMatchCheckPass(HttpServletRequest request, HttpServletResponse response)throws Exception  
    {
    	
    	String oflDepositIdStr = request.getParameter("oflDepositId");
    	
    	if(!StringUtils.isNotBlank(oflDepositIdStr)){
    		response.getWriter().write(
					"{\"success\":false,\"message\":\"请选择一条记录!\"}");
    		return;
    	}
    	
    	long oflDepositId = Long.parseLong(oflDepositIdStr);
    	
    	
    	Map<String,Object> queryMap = new HashMap<String, Object>();
    	queryMap.put("oflDepositId", oflDepositId);
    	List<SacDepositDetail> detailList = sacDepositService.findDepositDetailByParam(queryMap);
    	
    	if(detailList!=null&&detailList.size()>0){
    		
    		sacOperHistoryService.insertSacOperHistory(Constants.OPER_PRESTORE_MANUAL_MATCH_CHECK, request);
    		
    		SacDepositDetail detail = detailList.get(0);
    		String message = sacDepositService.checkPassForManualMatch(detail);
    		if(StringUtils.isBlank(message)){
    			response.getWriter().write("{\"success\":true}"); 
    		}else{
    			response.getWriter().write(
    					"{\"success\":false,\"message\":\""+message+"\"}");
    		}
    		
    	}
    	
    	
    	
    	
    } 
    @RequestMapping(value="/prestoreMunualMatchCheckFailue", method = {RequestMethod.POST,RequestMethod.GET}) 
    public void prestoreMunualMatchCheckFailue(HttpServletRequest request, HttpServletResponse response)throws Exception  
    {
    	

    	String oflDepositIdStr = request.getParameter("oflDepositId");
    	
    	if(!StringUtils.isNotBlank(oflDepositIdStr)){
    		response.getWriter().write(
					"{\"success\":false,\"message\":\"请选择一条记录!\"}");
    		return;
    	}
    	
    	long oflDepositId = Long.parseLong(oflDepositIdStr);
    	
    	
    	Map<String,Object> queryMap = new HashMap<String, Object>();
    	queryMap.put("oflDepositId", oflDepositId);
    	List<SacDepositDetail> detailList = sacDepositService.findDepositDetailByParam(queryMap);
    	if(detailList!=null&&detailList.size()>0){
    		SacDepositDetail detail = detailList.get(0);
    		sacDepositService.checkFailueForManualMatch(detail);
    	}
    	
    	sacOperHistoryService.insertSacOperHistory(Constants.OPER_PRESTORE_MANUAL_MATCH_CHECK, request);
    	
    	response.getWriter().write("{\"success\":true}"); 
    } 
    
    
    @RequestMapping(value="/prestoreBatchQueryInit", method = {RequestMethod.POST,RequestMethod.GET}) 
    public ModelAndView prestoreBatchQueryInit(HttpServletRequest request, HttpServletResponse response)throws Exception  
    {
    	
    	Model model = new ExtendedModelMap();
    	
    	String startDate = request.getParameter("startDate");
    	
    	String endDate = request.getParameter("endDate");
    	
    	String batchState = request.getParameter("batchState");
    	
    	Map<String, Object> queryMap = new HashMap<String, Object>();
    	
    	queryMap.put("batchState", batchState);
    	
    	if(StringUtils.isBlank(startDate)){
         	startDate = DateUtil.formatCurrentDate(Calendar.DATE, -7, "yyyyMMdd");
        }
        queryMap.put("startDate", startDate);
        if(StringUtils.isBlank(endDate)){
            endDate = DateUtil.formatCurrentDate(Calendar.DATE,0, "yyyyMMdd");
        }
        queryMap.put("endDate", endDate);
        
    	String pageNoStr = Utils.trim(request.getParameter("pageNo"));
 		
        int pageNo = Utils.parseInt(pageNoStr, 1);
        
        String pageSizeStr = Utils.trim(request.getParameter("pageSize"));
        
        int pageSize = Utils.parseInt(pageSizeStr, 10);
        
        Integer count = sacDepositService.getBatchCountsByParam(queryMap);
        
        List<UnifiedConfig> batchStateList = UnifiedConfigSimple.getDicTypeConfig(Constants.PRESTORE_BATCH_STATE);
        
    	List<SacDepositBatch> batchList = sacDepositService.getDepositBatchByParam(queryMap, pageNo, pageSize);
    	
    	model.addAttribute("batchList", batchList);
    	model.addAttribute("batchStateList", batchStateList);
    	model.addAttribute("count", count);
    	model.addAttribute("pageNo", pageNo);
    	model.addAttribute("startDate", startDate);
    	model.addAttribute("endDate", endDate);
    	model.addAttribute("batchState", batchState);
        return new ModelAndView("prestoreManage/prestoreBatchQueryInit",model.asMap());  
    } 
    @RequestMapping(value="/prestoreAllTrxQueryInit", method = {RequestMethod.POST,RequestMethod.GET}) 
    public ModelAndView prestoreAllTrxQueryInit(HttpServletRequest request, HttpServletResponse response)throws Exception  
    {
    	
    	Model model = new ExtendedModelMap();
    	
    	String startDate = request.getParameter("startDate");
    	
    	String endDate = request.getParameter("endDate");
    	
    	String dealState = request.getParameter("dealState");
    	
    	String draccName = request.getParameter("draccName");
    	
    	String payAmount = request.getParameter("payAmount");
    	
    	Map<String, Object> queryMap = new HashMap<String, Object>();
    	
    	queryMap.put("dealState", dealState);
    	
    	if(StringUtils.isBlank(startDate)){
         	startDate = DateUtil.formatCurrentDate(Calendar.DATE, -7, "yyyyMMdd");
        }
        queryMap.put("startDate", startDate);
        if(StringUtils.isBlank(endDate)){
            endDate = DateUtil.formatCurrentDate(Calendar.DATE,0, "yyyyMMdd");
        }
        queryMap.put("endDate", endDate);
        
    	queryMap.put("payAmount", payAmount);
    	
    	queryMap.put("draccName", draccName);
    	
    	queryMap.put("dealState", dealState);
    	
    	String pageNoStr = Utils.trim(request.getParameter("pageNo"));
    	
    	int pageNo = Utils.parseInt(pageNoStr, 1);
    	
    	String pageSizeStr = Utils.trim(request.getParameter("pageSize"));
    	
    	int pageSize = Utils.parseInt(pageSizeStr, 10);
    	
    	Integer count = sacDepositService.getDepositDetailCountsByParam(queryMap);
    	
    	List<UnifiedConfig> dealStateList = UnifiedConfigSimple.getDicTypeConfig(Constants.PRESTORE_DETAIL_STATE);
    	
    	List<SacDepositDetail> detailList = sacDepositService.getDepositDetailByPaging(queryMap, pageNo, pageSize);
    	
    	model.addAttribute("detailList", detailList);
    	model.addAttribute("count", count);
    	model.addAttribute("pageNo", pageNo);
    	model.addAttribute("startDate", startDate);
    	model.addAttribute("endDate", endDate);
    	model.addAttribute("dealState", dealState);
    	model.addAttribute("payAmount", payAmount);
    	model.addAttribute("draccName", draccName);
    	model.addAttribute("dealStateList", dealStateList);
    	return new ModelAndView("prestoreManage/prestoreAllTrxQueryInit",model.asMap());  
    } 
    @RequestMapping(value="/getPrestoreDetailById", method = RequestMethod.GET) 
    public ModelAndView getPrestoreDetailById(HttpServletRequest request, HttpServletResponse response)throws Exception  
    {
    	
    	Model model = new ExtendedModelMap();
    	
    	String oflDepositId = request.getParameter("oflDepositId");
    	
    	Map<String, Object> queryMap = new HashMap<String, Object>();
    	
    	queryMap.put("oflDepositId", oflDepositId);
    	
    	List<SacDepositDetail> detailList = sacDepositService.findDepositDetailByParam(queryMap);
    	SacDepositDetail detail = detailList.get(0);
    	String dealStateStr = OFLDepositDealState.getStateValue(detail.getDealState());
    	String checkStateStr = OFLCheckDealState.getStateValue(detail.getCheckState());
    	detail.setDealState(dealStateStr);
    	detail.setCheckState(checkStateStr);
    	model.addAttribute("detail", detail);
    	
    	return new ModelAndView("prestoreManage/prestoreQueryDetail",model.asMap());  
    } 
    @RequestMapping(value="/spdbMakeInit", method = RequestMethod.POST) 
    public ModelAndView spdbMakeInit(HttpServletRequest request, HttpServletResponse response)throws Exception  
    {
    	
    	Model model = new ExtendedModelMap();
    	
    	return new ModelAndView("prestoreManage/prestoreSPDBMakeInit",model.asMap());  
    	
    } 
    
    @RequestMapping(value="/spdbBatchMake", method = RequestMethod.POST) 
    public void spdbBatchMake(HttpServletRequest request, HttpServletResponse response)throws Exception  
    {
    	
    	String str = request.getParameter("str");
    	
    	String msg = sacDepositService.spdbBatchMake(str);
    	
    	sacOperHistoryService.insertSacOperHistory(Constants.OPER_PRESTORE_CREATE_BATCH, request);
    	
    	if(StringUtil.isNotBlank(msg)){
    		response.getWriter().write(
					"{\"success\":true,\"message\":\""+msg.substring(0, msg.length()-1)+"\"}");
			return;
    	}
    	
    	response.getWriter().write("{\"success\":true}"); 
    	
    } 
    
    public String getApplyCodeFromStr(String str){
    	
    	if(StringUtils.isBlank(str)){
    		return "";
    	}
    	str = str.replaceAll("[\u4e00-\u9fa5]+", "");//将汉字过滤
    	
    	str = ToDBC(str);//将全角改成半角
    	
    	Pattern p = Pattern.compile(":[A-Za-z0-9]{8,9}([^(A-Za-z0-9)]|$)");
    	
    	Matcher m = p.matcher(str);
    	
		List<String> result=new ArrayList<String>();
		List<String> rt=new ArrayList<String>();
		while(m.find()){
			result.add(m.group());
		}
		for(String s:result){
			Pattern pn = Pattern.compile("[A-Za-z0-9]{8,9}");
			Matcher mr= pn.matcher(s);
			while(mr.find()){
				rt.add(mr.group());
			}
		}
		if(rt.size()>0){
			return rt.get(0);
		}
    	
    	return "";
    }
    
    
    
    public static String ToDBC(String input) {
		if( input.length() < input.getBytes().length ){  
		   

         char c[] = input.toCharArray();
         for (int i = 0; i < c.length; i++) {
           if (c[i] == '\u3000') {
             c[i] = ' ';
           } else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
             c[i] = (char) (c[i] - 65248);

           }
         }
         String returnString = new String(c);
         return returnString;
		} else{
			return input;
		}
    }
    
    /**
     * 上传B2B退款文件初始化
     * 
     * @param request
     * @param response
     */
    @RequestMapping(value = "/uploadPrestoreOflFileInit", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView uploadPrestoreOflFileInit(HttpServletRequest request, HttpServletResponse response) {
      ModelAndView mav = new ModelAndView("prestoreManage/uploadPrestore");
      return mav;
    }
    
    
    /**
     * 保存上传的xls
     * 
     * @param request
     * @param response
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/uploadPrestoreFile", method = {RequestMethod.POST})
    public ModelAndView uploadPrestoreFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
      ModelAndView mav = new ModelAndView("prestoreManage/uploadPrestore");
      // 默认为application/json,如果不设置ContentType属性为text/html则会返回json对象并出现下载界面
      response.setContentType("text/html;charset=utf-8");
      // 转型为MultipartHttpRequest(重点所在)
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

      // 根据前台的name名称得到上传的文件
      MultipartFile oflExcelFile = multipartRequest.getFile("fileRes");
      logger.debug("oflExcelFile is" + oflExcelFile.getOriginalFilename());
      String message = null;
      String oflExcelFileName = oflExcelFile.getOriginalFilename();
      String oflExcelFileSuffix = "";
      if (StringUtil.isNotBlank(oflExcelFileName)) {
        // 截取文件名的后缀,例如lawMan.jpg,则截取结果为.jpg
        oflExcelFileSuffix = oflExcelFileName.substring(oflExcelFileName.lastIndexOf("."), oflExcelFileName.length());
      }
      if (!".xls".equalsIgnoreCase(oflExcelFileSuffix)) {
        logger.error("后缀名不符，请重新上传！");
        message = "后缀名不符，请重新上传!";
        mav.addObject("message", message);
        return mav;
      }
      // 获取文件大小，以kB为单位   
      long size = oflExcelFile.getSize();
      Double fSize = Math.round(size / 1024.0 * 100) / 100.0;
      String fileSize = Double.toString(fSize) + "KB";
      logger.debug("oflExcelFile fileSize:" + fileSize);

      SacDepositBatch oflBatch = null;
      String msg = "";
      if (oflExcelFile.getSize() != 0) {
        SecurityOperator person = PersonUtil.getUser();
        try {
          Object[] obj = sacDepositService.readOflXls(oflExcelFileName, oflExcelFile.getInputStream(), person);
          msg = (String)obj[0];
          oflBatch = (SacDepositBatch)obj[1];
        } catch (SacException e) {
          logger.error("解析出错！文件名：" + oflExcelFileName + e.getMessage(), e);
          message = "解析出错！请确认xls内容格式;" + e.getMessage();
          mav.addObject("message", message);
          return mav;
        } catch (Exception e) {
          logger.error("解析出错！文件名：" + oflExcelFileName + e.getMessage(), e);
          message = "解析出错！未知异常！请确认xls内容格式;";
          mav.addObject("message", message);
          return mav;
        } finally {
          oflExcelFile.getInputStream().close();
        }
      }
      if(StringUtils.isBlank(msg)){
    	  message = "上传处理成功！文件名：" + oflExcelFileName + oflBatch.toViewInfo();
      }else{
    	  msg = msg.substring(0, msg.length()-1);
    	  Long count = oflBatch.getBatchTcount();
    	  if(count!=null&&count>0){
        	  message = "上传处理成功！文件名：" + oflExcelFileName + oflBatch.toViewInfo()+",但是"+msg+"的预存申请码错误或者已被使用!";
    	  }else{
    		  message = "上传失败!无符合条件的记录,请检查预存申请码是否正确!";
    	  }
    	 
      }
      mav.addObject("message", message);
      sacOperHistoryService.insertSacOperHistory(Constants.OPER_PRESTORE_CREATE_BATCH,request);//银联B2B退款批次上传
      return mav;
    }
    
    
    
    
    public static void main(String[] args) {
    	
    	String str = "F:Ｅ１ＸＤ７ＴＥＦ//A:BEPS103305153706 2016053124413590//U://R:beps.121.001.01 A100 R104290003582 中国银行上海市浦东分行";
    	
    	str = str.replaceAll("[\u4e00-\u9fa5]+", "");
    	
    	str = ToDBC(str);
    	
    	Pattern p = Pattern.compile(":[A-Za-z0-9]{8}([^(A-Za-z0-9)]|$)");
		Matcher m = p.matcher(str);
		List<String> result=new ArrayList<String>();
		List<String> result1=new ArrayList<String>();
		while(m.find()){
			result.add(m.group());
		}
		for(String s1:result){
			//System.out.println(s1);
			Pattern p1 = Pattern.compile("[A-Za-z0-9]{8}");
			Matcher m1= p1.matcher(s1);
			while(m1.find()){
				result1.add(m1.group());
			}
			for(String s2:result1){
				System.out.println(s2);
			}
			
		}
	}
    
    
}
