package net.easipay.cbp.view.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.easipay.cbp.cache.CacheUtil;
import net.easipay.cbp.constant.Constants;
import net.easipay.cbp.constant.EnumConstants.FundGiveOLConstants.CmdBatchState;
import net.easipay.cbp.constant.EnumConstants.FundGiveOLConstants.CmdState;
import net.easipay.cbp.controller.BaseController;
import net.easipay.cbp.exception.SacException;
import net.easipay.cbp.model.SacB2BCommand;
import net.easipay.cbp.model.SacB2bCmdBatch;
import net.easipay.cbp.service.ISacCmdBatchService;
import net.easipay.cbp.service.ISacCommandService;
import net.easipay.cbp.service.ISacOperHistoryService;
import net.easipay.cbp.util.DateUtil;
import net.easipay.cbp.util.Utils;
import net.easipay.dsfc.UnifiedConfig;
import net.easipay.dsfc.UnifiedConfigSimple;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 线上出款
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
public class FundGiveOnlineController extends BaseController{

	private static final Logger logger = Logger.getLogger(FundGiveOnlineController.class);
	
	private static final String TPL_ALL_CMD_BATCH_REPORT_XLS = "template/allCmdBatchReport.xls";
	
	private static final String TPL_PENDING_CMD_BATCH_REPORT_XLS = "template/pendingCmdBatchReport.xls";
	
	private static final String TPL_WAITREVIEW_CMD_BATCH_REPORT_XLS = "template/waitingReviewCmdBatchReport.xls";
	
	@Autowired
	private ISacCmdBatchService sacCmdBatchService;
	
	@Autowired
	private ISacCommandService sacCommandService;
	
	@Autowired
	private ISacOperHistoryService sacOperHistoryService;
	
	
    @RequestMapping(value="/fundCmdBatchQueryInit", method = {RequestMethod.GET,RequestMethod.POST}) 
    public ModelAndView fundCmdBatchQueryInit(HttpServletRequest request, HttpServletResponse response)throws Exception  
    {
    	
    	Model model = new ExtendedModelMap();
    	
    	String startDate = request.getParameter("startDate");
    	
    	String endDate = request.getParameter("endDate");
    	
    	String msgReceiver = request.getParameter("msgReceiver");
    	
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
    	
    	queryMap.put("msgReceiver", msgReceiver);
    	
    	queryMap.put("batchStates", "'"+CmdBatchState.Init.code()+"','"+CmdBatchState.ReviewReject.code()+"'");
    	
    	Integer count = sacCmdBatchService.getCmdBatchCounts(queryMap);
    	
    	List<UnifiedConfig> bankList = UnifiedConfigSimple.getDicTypeConfig(Constants.BANK_TYPE);
    	
    	List<SacB2bCmdBatch> batchList = sacCmdBatchService.getCmdBatchByPaging(queryMap,pageNo,pageSize);
    	
    	BigDecimal totalAmount = new BigDecimal("0.00");
    	
        Iterator<SacB2bCmdBatch> it = batchList.iterator();
        
        Map<String,Object> bankMap = CacheUtil.getCacheByTypeToMap(Constants.BANK_TYPE);
        
        while(it.hasNext()){
        	SacB2bCmdBatch batch = it.next();
        	String msgReceive = batch.getMsgReceiver();
        	String bankName = (String)bankMap.get(msgReceive);
        	batch.setMsgReceiver(bankName);
        	BigDecimal batchTamount = batch.getBatchAmount();
        	totalAmount = totalAmount.add(batchTamount);
        }
    	
    	model.addAttribute("batchList",batchList);
    	model.addAttribute("count",count);
    	model.addAttribute("pageNo",pageNo);
    	model.addAttribute("totalAmount",totalAmount);
    	model.addAttribute("startDate",startDate);
    	model.addAttribute("endDate",endDate);
    	model.addAttribute("msgReceiver",msgReceiver);
    	model.addAttribute("bankList",bankList);
    	
    	return new ModelAndView("fundGiveOLManage/fundCmdBatchQueryInit",model.asMap());  
    } 
    
    /**
     * 线上出款经办
     * @param request
     * @param response
     * @param cmdBatch
     * @throws Exception
     */
    @RequestMapping(value="/dealBatchPass", method = RequestMethod.POST) 
    public void dealBatchPass(HttpServletRequest request, HttpServletResponse response,SacB2bCmdBatch cmdBatch)throws Exception  
    {
    	
    	String batchIds =request.getParameter("batchIds");
    	
    	String[] splitedIds = batchIds.split("\\|");
    	
    	for(String batchId : splitedIds){
    		
    		sacCmdBatchService.dealBatchPass(batchId);
    	}
    	response.getWriter().write("{\"success\":true}"); 
    	
    	sacOperHistoryService.insertSacOperHistory(Constants.OPER_FUND_OL_GIVE_HANDLE, request);
    } 
    
    /**
     * 线上出款批次作废
     * @param request
     * @param response
     * @param cmdBatch
     * @throws Exception
     */
    @RequestMapping(value="/dealBatchCancel", method = RequestMethod.POST) 
    public void dealBatchCancel(HttpServletRequest request, HttpServletResponse response,SacB2bCmdBatch cmdBatch)throws Exception  
    {
    	
    	String batchIds =request.getParameter("batchIds");
    	
    	String[] splitedIds = batchIds.split("\\|");
    	
    	for(String batchId : splitedIds){
    		
    		sacCmdBatchService.dealBatchCancel(batchId);
    	}
    	response.getWriter().write("{\"success\":true}"); 
    	
    	sacOperHistoryService.insertSacOperHistory(Constants.OPER_FUND_OL_GIVE_CANCEL, request);
    } 
    
    
    @RequestMapping(value="/fundCmdBatchCheckInit", method = {RequestMethod.GET,RequestMethod.POST}) 
    public ModelAndView fundCmdBatchCheckInit(HttpServletRequest request, HttpServletResponse response)throws Exception  
    {
    	
    	Model model = new ExtendedModelMap();
    	
    	String startDate = request.getParameter("startDate");
    	
    	String endDate = request.getParameter("endDate");
    	
    	String msgReceiver = request.getParameter("msgReceiver");
    	
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
    	
    	queryMap.put("batchState",CmdBatchState.WaitingReview.code());
    	
    	queryMap.put("msgReceiver",msgReceiver);
    	
    	Integer count = sacCmdBatchService.getCmdBatchCounts(queryMap);
    	
    	List<SacB2bCmdBatch> batchList = sacCmdBatchService.getCmdBatchByPaging(queryMap,pageNo,pageSize);
    	
    	List<UnifiedConfig> bankList = UnifiedConfigSimple.getDicTypeConfig(Constants.BANK_TYPE);
    	
    	Map<String,Object> bankMap = CacheUtil.getCacheByTypeToMap(Constants.BANK_TYPE);
    	
    	BigDecimal totalAmount = new BigDecimal("0.00");
        Iterator<SacB2bCmdBatch> it = batchList.iterator();
        while(it.hasNext()){
        	SacB2bCmdBatch batch = it.next();
        	String msgReceive = batch.getMsgReceiver();
        	String bankName = (String)bankMap.get(msgReceive);
        	batch.setMsgReceiver(bankName);
        	BigDecimal batchTamount = batch.getBatchAmount();
        	totalAmount = totalAmount.add(batchTamount);
        }
    	
    	
    	model.addAttribute("batchList",batchList);
    	model.addAttribute("count",count);
    	model.addAttribute("pageNo",pageNo);
    	model.addAttribute("totalAmount",totalAmount);
    	model.addAttribute("startDate",startDate);
    	model.addAttribute("endDate",endDate);
    	model.addAttribute("msgReceiver",msgReceiver);
    	model.addAttribute("bankList",bankList);
    	
    	return new ModelAndView("fundGiveOLManage/fundCmdBatchCheckInit",model.asMap());  
    } 
    
    
    @RequestMapping(value="/checkBatchPass", method = RequestMethod.POST) 
    public void checkBatchPass(HttpServletRequest request, HttpServletResponse response,SacB2bCmdBatch cmdBatch)throws Exception  
    {
    	
    	String batchIds =request.getParameter("batchIds");
    	
    	String[] splitedIds = batchIds.split("\\|");
    	
    	for(String batchId : splitedIds){
    		
    		try {
    			sacCmdBatchService.checkBatchPass(batchId);
			} catch (SacException e) {
				String message = e.getMessage();
				response.getWriter().write("{\"success\":false,\"message\":\""+message+"\"}");
				return;
			}
    	}
    	
    	sacOperHistoryService.insertSacOperHistory(Constants.OPER_FUND_OL_GIVE_CHECK, request);
    	
    	response.getWriter().write("{\"success\":true}"); 
    } 
    
    @RequestMapping(value="/checkBatchFailue", method = RequestMethod.POST) 
    public void checkBatchFailue(HttpServletRequest request, HttpServletResponse response,SacB2bCmdBatch cmdBatch)throws Exception  
    {
    	
    	String batchIds =request.getParameter("batchIds");
    	
    	String[] splitedIds = batchIds.split("\\|");
    	
    	for(String batchId : splitedIds){
    		
    		sacCmdBatchService.checkBatchFailue(batchId);
    		
    	}
    	
    	sacOperHistoryService.insertSacOperHistory(Constants.OPER_FUND_OL_GIVE_CHECK, request);
    	
    	response.getWriter().write("{\"success\":true}"); 
    } 
    
    
    @RequestMapping(value="/fundCmdBatchAllQueryInit", method = {RequestMethod.GET,RequestMethod.POST}) 
    public ModelAndView fundCmdBatchAllQueryInit(HttpServletRequest request, HttpServletResponse response)throws Exception  
    {
    	
    	Model model = new ExtendedModelMap();
    	
    	String startDate = request.getParameter("startDate");
    	
    	String endDate = request.getParameter("endDate");
    	
    	String batchState = request.getParameter("batchState");
    	
    	String msgReceiver = request.getParameter("msgReceiver");
    	
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
    	
    	queryMap.put("batchState", batchState);
    	
    	queryMap.put("msgReceiver", msgReceiver);
    	
    	Integer count = sacCmdBatchService.getCmdBatchCounts(queryMap);
    	
    	List<SacB2bCmdBatch> batchList = sacCmdBatchService.getCmdBatchByPaging(queryMap,pageNo,pageSize);
    	
    	List<UnifiedConfig> bankList = UnifiedConfigSimple.getDicTypeConfig(Constants.BANK_TYPE);
    	
    	List<UnifiedConfig> batchStateList = UnifiedConfigSimple.getDicTypeConfig(Constants.FUND_BATCH_STATE);
    	
    	Map<String,Object> bankMap = CacheUtil.getCacheByTypeToMap(Constants.BANK_TYPE);
    	
    	BigDecimal totalAmount = new BigDecimal("0.00");
        Iterator<SacB2bCmdBatch> it = batchList.iterator();
        while(it.hasNext()){
        	SacB2bCmdBatch batch = it.next();
        	String msgReceive = batch.getMsgReceiver();
        	String bankName = (String)bankMap.get(msgReceive);
        	batch.setMsgReceiver(bankName);
        	BigDecimal batchTamount = batch.getBatchAmount();
        	totalAmount = totalAmount.add(batchTamount);
        }
    	
    	
    	model.addAttribute("batchList",batchList);
    	model.addAttribute("count",count);
    	model.addAttribute("pageNo",pageNo);
    	model.addAttribute("totalAmount",totalAmount);
    	model.addAttribute("startDate",startDate);
    	model.addAttribute("endDate",endDate);
    	model.addAttribute("batchState",batchState);
    	model.addAttribute("msgReceiver",msgReceiver);
    	model.addAttribute("bankList",bankList);
    	model.addAttribute("batchStateList",batchStateList);
    	
    	return new ModelAndView("fundGiveOLManage/fundCmdBatchAllQueryInit",model.asMap());  
    } 
    @RequestMapping(value="/getfundDetailByBatch", method = RequestMethod.GET) 
    public ModelAndView getfundDetailByBatch(HttpServletRequest request, HttpServletResponse response)throws Exception  
    {
    	
    	Model model = new ExtendedModelMap();
    	
    	String cmdBatchId = request.getParameter("cmdBatchId");
    	
    	String pageNoStr = Utils.trim(request.getParameter("pageNo"));
    	
    	int pageNo = Utils.parseInt(pageNoStr, 1);
    	
    	String pageSizeStr = Utils.trim(request.getParameter("pageSize"));
    	
    	int pageSize = Utils.parseInt(pageSizeStr, 10);
    	
    	Map<String, Object> queryMap = new HashMap<String, Object>();
    	
    	queryMap.put("batchSerialNo", cmdBatchId);
    	
    	Integer count = sacCommandService.getCommandDetailCounts(queryMap);
    	
    	List<SacB2BCommand> detailList = sacCommandService.getCommandDetailByPaging(queryMap,pageNo,pageSize);
    	
    	model.addAttribute("detailList",detailList);
    	model.addAttribute("count",count);
    	model.addAttribute("pageNo",pageNo);
    	model.addAttribute("cmdBatchId",cmdBatchId);
    	
    	return new ModelAndView("fundGiveOLManage/fundCommandDetail",model.asMap());  
    } 
    
    @RequestMapping(value="/downloadAllToExcel", method = RequestMethod.POST) 
    public void downloadAllToExcel(HttpServletRequest request, HttpServletResponse response,SacB2bCmdBatch cmdBatch)throws Exception  
    {
    	
    	String startDate = request.getParameter("startDate");
    	
    	String endDate = request.getParameter("endDate");
    	
    	String batchState = request.getParameter("batchState");
    	
    	String msgReceiver = request.getParameter("msgReceiver");
        
        Map<String, Object> queryMap = new HashMap<String, Object>();
    	
    	queryMap.put("startDate", startDate);
    	
    	queryMap.put("endDate", endDate);
    	
    	queryMap.put("batchState", batchState);
    	
    	queryMap.put("msgReceiver", msgReceiver);
    	// 查询待下载数据
    	
        List<SacB2bCmdBatch> batchList = sacCmdBatchService.getCmdBatchByPaging(queryMap, 1, Integer.MAX_VALUE);
        
        sacCmdBatchService.exportCmdBatchToExcel(response, queryMap, batchList,TPL_ALL_CMD_BATCH_REPORT_XLS);
        
    } 
    
    @RequestMapping(value="/downloadCmdAndBatchToExcel", method = RequestMethod.POST) 
    public void downloadCmdAndBatchToExcel(HttpServletRequest request, HttpServletResponse response,SacB2bCmdBatch cmdBatch)throws Exception  
    {
    	
    	String startDate = request.getParameter("startDate");
    	
    	String endDate = request.getParameter("endDate");
    	
    	String msgReceiver = request.getParameter("msgReceiver");
        
        Map<String, Object> queryMap = new HashMap<String, Object>();
        
        queryMap.put("batchStates", "'"+CmdBatchState.Init.code()+"','"+CmdBatchState.ReviewReject.code()+"'");
    	
    	queryMap.put("startDate", startDate);
    	
    	queryMap.put("endDate", endDate);
    	
    	queryMap.put("msgReceiver", msgReceiver);
    	// 查询待下载数据
    	
        List<SacB2bCmdBatch> batchList = sacCmdBatchService.getCmdBatchByPaging(queryMap, 1, Integer.MAX_VALUE);
        
        Map<String,Object> bankMap = CacheUtil.getCacheByTypeToMap(Constants.BANK_TYPE);
        
        List<SacB2BCommand> detailList = new ArrayList<SacB2BCommand>();
        
        Map<String,Object> detailMap = new HashMap<String,Object>();
        for(SacB2bCmdBatch batch : batchList){
        	String batchSerialNo = batch.getBatchSerialNo();
        	String batchState = batch.getBatchState();
        	String stateValue = CmdBatchState.getStateValue(batchState);
        	batch.setBatchState(stateValue);
        	String draccBankCode = batch.getMsgReceiver();
        	String draccName = (String)bankMap.get(draccBankCode);
        	batch.setMsgReceiver(draccName);
        	detailMap.put("batchSerialNo", batchSerialNo);
        	List<SacB2BCommand> resultList = sacCommandService.getCommandDetailByPaging(detailMap, 1, Integer.MAX_VALUE);
        	detailList.addAll(resultList);
        	detailMap.clear();
        }
        
        sacCmdBatchService.exportCmdBatchToExcel(response, queryMap, batchList,detailList,TPL_PENDING_CMD_BATCH_REPORT_XLS);
        
    } 
    
    
    @RequestMapping(value="/downloadCheckToExcel", method = RequestMethod.POST) 
    public void downloadCheckToExcel(HttpServletRequest request, HttpServletResponse response,SacB2bCmdBatch cmdBatch)throws Exception  
    {
    	
    	String startDate = request.getParameter("startDate");
    	
    	String endDate = request.getParameter("endDate");
    	
    	String msgReceiver = request.getParameter("msgReceiver");
        
        Map<String, Object> queryMap = new HashMap<String, Object>();
    	
        queryMap.put("batchState",CmdBatchState.WaitingReview.code());
        
    	queryMap.put("startDate", startDate);
    	
    	queryMap.put("endDate", endDate);
    	
    	queryMap.put("msgReceiver", msgReceiver);
    	// 查询待下载数据
    	
        List<SacB2bCmdBatch> batchList = sacCmdBatchService.getCmdBatchByPaging(queryMap, 1, Integer.MAX_VALUE);
        
        sacCmdBatchService.exportCmdBatchToExcel(response, queryMap, batchList,TPL_WAITREVIEW_CMD_BATCH_REPORT_XLS);
        
    } 
    @RequestMapping(value="/fundFailueCommandQueryInit", method = {RequestMethod.POST,RequestMethod.GET}) 
    public ModelAndView fundFailueCommandQueryInit(HttpServletRequest request, HttpServletResponse response)throws Exception  
    {
    	
    	Model model = new ExtendedModelMap();
    	
    	String startDate = request.getParameter("startDate");
    	
    	String endDate = request.getParameter("endDate");
    	
    	String craccName = request.getParameter("craccName");
    	
    	String payAmount = request.getParameter("payAmount");
    	
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
    	queryMap.put("cmdState", CmdState.TransationFail.code());//查询所有失败的线上出款指令
    	
    	Integer count = sacCommandService.getCommandDetailCounts(queryMap);
    	
    	List<SacB2BCommand> detailList = sacCommandService.getCommandDetailByPaging(queryMap,pageNo,pageSize);
    	
    	model.addAttribute("detailList",detailList);
    	model.addAttribute("count",count);
    	model.addAttribute("pageNo",pageNo);
    	model.addAttribute("startDate", startDate);
    	model.addAttribute("endDate", endDate);
    	model.addAttribute("craccName", craccName);
    	model.addAttribute("payAmount", payAmount);
    	
    	return new ModelAndView("fundGiveOLManage/fundFailueCommandQueryInit",model.asMap());  
    } 
    @RequestMapping(value="/dealCommandOfFailue", method = RequestMethod.POST) 
    public void dealCommandOfFailue(HttpServletRequest request, HttpServletResponse response)throws Exception  
    {
    	
    	String cmdId = request.getParameter("cmdId");
    	
    	sacCommandService.dealCommandOfFailue(cmdId);
    	
    	response.getWriter().write("{\"success\":true}");  
    } 
    
    
    
}
