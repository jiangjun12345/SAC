package net.easipay.cbp.view.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.easipay.cbp.constant.Constants;
import net.easipay.cbp.controller.BaseController;
import net.easipay.cbp.model.SacOtrxInfo;
import net.easipay.cbp.model.form.SacRemittanceBatchIdForm;
import net.easipay.cbp.model.form.SacTransationReceiveForm;
import net.easipay.cbp.service.ITransactionService;
import net.easipay.dsfc.ws.jws.JwsResult;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;



/**
 * 
 * @author jjiang
 *
 */

@Controller
public class ManualSendTrxManageController extends BaseController{ 
	private static final Logger logger = Logger.getLogger(ManualSendTrxManageController.class);
    


	@Autowired
	private ITransactionService transactionService;
	
 	@RequestMapping(value="/manualSendTrxMsgInit", method = RequestMethod.GET) 
    public ModelAndView manualSendTrxMsgInit(HttpServletRequest request, HttpServletResponse response )throws Exception  
    {
    	
 		Model model = new ExtendedModelMap();
 		
 		return new ModelAndView("/reconDataManage/manualSendTrxMsgInit", model.asMap()); 
    	
    } 
	
    
    @SuppressWarnings("unchecked")
	@RequestMapping(value="/manualSendTrxMsg", method = RequestMethod.POST) 
    @ResponseBody
    public void manualSendTrxMsg(HttpServletRequest request, HttpServletResponse response )throws Exception  
    {
    	
    	String sendMsg = request.getParameter("sendMsg");
    	
    	String interfaceCode = request.getParameter("interfaceCode");
    	
    	JSONObject fromObject = null;
    	
    	JwsResult result = null;
    	
    	if(Constants.RCV_TRANSACTION.equals(interfaceCode)){
    		
    		try {
        		fromObject = JSONObject.fromObject(sendMsg);
    		} catch (Exception e) {
    			response.getWriter().write("{\"success\":false,\"message\":\"报文解析有误!\"}");
    			return;
    		}
    		
    		SacTransationReceiveForm s = (SacTransationReceiveForm) JSONObject.toBean(fromObject,SacTransationReceiveForm.class);  
    		
    		List<SacTransationReceiveForm> trxList = new ArrayList<SacTransationReceiveForm>();
    		
    		trxList.add(s);
    		
    		result = transactionService.manualSendTrxMsg(trxList);
    		
    	}else if(Constants.UPDATE_TRANSACTION.equals(interfaceCode)){
    		
    		try {
        		fromObject = JSONObject.fromObject(sendMsg);
    		} catch (Exception e) {
    			response.getWriter().write("{\"success\":false,\"message\":\"报文解析有误!\"}");
    			return;
    		}
    		
    		SacOtrxInfo trx = (SacOtrxInfo) JSONObject.toBean(fromObject,SacOtrxInfo.class);  
    		
    		result = transactionService.updateTransactionStateInterface(trx);
    	}else if(Constants.UPDATE_BATCH_NO.equals(interfaceCode)){
    		
    		 JSONArray jsonArray = JSONArray.fromObject(sendMsg); 
    		
    		 List<SacRemittanceBatchIdForm> listJson = (List<SacRemittanceBatchIdForm>)jsonArray; 
    		 
    		 result = transactionService.updateRemitBatchNo(listJson);
    	}else{
    		response.getWriter().write("{\"success\":false,\"message\":\"不支持的接口调用!\"}");
    		return;
    	}
    	
    	
    	boolean success = result.isSuccess();
    	
    	
    	if(success){
    		response.getWriter().write("{\"success\":true}");
    	}else{
    		String message = result.getMessage();
    		response.getWriter().write("{\"success\":false,\"message\":\""+message+"\"}");
    	}
    	
    } 
    
   
}
