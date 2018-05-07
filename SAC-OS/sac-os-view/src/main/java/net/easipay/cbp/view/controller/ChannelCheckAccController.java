package net.easipay.cbp.view.controller;

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

import net.easipay.cbp.model.SacRecDetail;
import net.easipay.cbp.model.SacRecDifferences;
import net.easipay.cbp.model.SacReceiveBankRecon;
import net.easipay.cbp.service.IChannelCheckAccService;
import net.easipay.dsfc.UnifiedConfig;
import net.easipay.dsfc.UnifiedConfigSimple;
import net.easipay.dsfc.ws.fws.FwsClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ChannelCheckAccController extends BaseDataController
{

	private Logger logger = LoggerFactory.getLogger(ChannelCheckAccController.class);

	@Autowired
	private IChannelCheckAccService checkAccService;

	/**
	 * 上传对账文件初始化
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/uploadCheckAccFileInit" ,method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView uploadCheckAccFileInit(HttpServletRequest request, HttpServletResponse response)
	{
		//将页面参数保存在session中
		if("Y".equals(request.getParameter("saveFlag"))){//需要保存
			saveFormDataToSession(request);
			return null;
		}
		logger.debug("uploadCheckAccFile start ………………");
		ModelAndView mav = new ModelAndView("channelCheckAccount/checkAccHandle");
		Map<String,Object> recFileParamMap = new HashMap<String, Object>();
		recFileParamMap.put("recType", "2");
		recFileParamMap.put("payconType", "2");
		mav.addObject("recFileParamList", checkAccService.getRecFileParamList(recFileParamMap));// 查询对账文件参数
		mav.addObject("chnCode", request.getParameter("chnCode"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -1);
		mav.addObject("trxDate", request.getParameter("trxDate")==null?sdf.format(c.getTime()):request.getParameter("trxDate"));
		return mav;
	}
	
	/**
	 * 上传对账文件退回
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/uploadCheckAccFileBack" ,method={RequestMethod.GET})
	public ModelAndView uploadCheckAccFileBack(HttpServletRequest request, HttpServletResponse response)
	{
		logger.debug("uploadCheckAccFileBack start ………………");
		HttpSession session = request.getSession();
		if(!"Y".equals(session.getAttribute("uploadFlag"))){//不是从查询页面过来
			return uploadCheckAccFileInit(request,response);
		}
		// 组装请求参数
		ModelAndView mav = new ModelAndView("channelCheckAccount/checkAccResult");
		// 组装请求参数
		Map<String,Object> paramMap = new HashMap<String,Object>();
		Integer pageNo = Integer.valueOf(session.getAttribute("pageNo").toString());// 默认为第1页
		Integer pageSize = 10;
		paramMap.put("start", (pageNo-1)*pageSize);//分页起始
		paramMap.put("end", pageSize*pageNo);//分页结束
		paramMap.put("startDate", session.getAttribute("startDate"));//开始日期
		paramMap.put("endDate", session.getAttribute("endDate"));//结束日期
		paramMap.put("chnCode", session.getAttribute("queryChnCode"));// 渠道号
		paramMap.put("payconType", session.getAttribute("payconType"));//支付渠道类型
		paramMap.put("recType", session.getAttribute("recType"));// 对账类型
		// 查询对账结果
		List<Map<String, Object>> checkAccResultList = checkAccService.queryCheckAccResult(paramMap);
		mav.addObject("checkAccResultList", checkAccResultList);
		mav.addObject("chnCode", session.getAttribute("queryChnCode"));// 渠道号
		mav.addObject("payconType", session.getAttribute("payconType"));//支付渠道类型
		mav.addObject("recType", session.getAttribute("recType"));// 对账类型
		mav.addObject("totalCount",checkAccService.queryCheckAccResultCount(paramMap));//总数
		mav.addObject("recFileParamList", checkAccService.getRecFileParamList(new HashMap<String, Object>()));// 查询对账文件参数
		mav.addObject("endDate", session.getAttribute("endDate"));//结束日期
		mav.addObject("startDate", session.getAttribute("startDate"));//开始日期
		mav.addObject("pageNo",pageNo);
		mav.addObject("pageSize", pageSize);
		session.removeAttribute("uploadFlag");//移除标志
		session.removeAttribute("startDate");
		session.removeAttribute("endDate");
		session.removeAttribute("queryChnCode");
		session.removeAttribute("payconType");
		session.removeAttribute("recType");
		session.removeAttribute("saveFlag");
		return mav;
	}

	/**
	 * 无对账数据时上传对账文件并发送账务系统
	 * 
	 * @param request
	 * @param response
	 * @throws JwsFsException
	 */
	@RequestMapping(value="/uploadCheckAccFileNoData",method={RequestMethod.POST})
	public ModelAndView uploadCheckAccFileNoData(HttpServletRequest request, HttpServletResponse response)
	{
		logger.debug("uploadCheckAccFileNoData start ………………");
		ModelAndView mav = new ModelAndView("channelCheckAccount/checkAccHandle");
		String checkTrxDate = request.getParameter("trxDate");// 上传文件的日期
		String chnCode = request.getParameter("chnCode");// 支付渠道号
		String bankNodeCode = checkAccService.getBankNodeCode(chnCode);// 银行代码
		// 组装出发送给账务系统的数据
		String message = null;
		try
		{
			if(checkAccService.recBatchIsExist("2",checkTrxDate,chnCode,"2")){//返回true,表示已经上传
				message="已经上传！请勿重复上传！";
			}else{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				Date startTrxDate = sdf.parse(checkTrxDate+"000000");
				Date endTrxDate = sdf.parse(checkTrxDate+"235959");
				//组装一条伪数据发送账务系统
				List<SacReceiveBankRecon> bankReconList = new ArrayList<SacReceiveBankRecon>();
				SacReceiveBankRecon sacReceiveBankRecon = new SacReceiveBankRecon();
				sacReceiveBankRecon.setBankSerialNo("0");
				sacReceiveBankRecon.setPayconType("2");
				sacReceiveBankRecon.setCurrencyType("CNY");
				sacReceiveBankRecon.setPayAmount("0.00");
				sacReceiveBankRecon.setTrxTime(startTrxDate);
				sacReceiveBankRecon.setRecOper("0000001");
				sacReceiveBankRecon.setChnNo(bankNodeCode);
				sacReceiveBankRecon.setBusiType("1");
				sacReceiveBankRecon.setRecStartDate(startTrxDate);
				sacReceiveBankRecon.setRecEndDate(endTrxDate);
				sacReceiveBankRecon.setRecCount(0L);
				bankReconList.add(sacReceiveBankRecon);
				// 发送账务系统
				FwsClient fwsClient = new FwsClient("SAC-AC-0006");
				fwsClient.putParam(bankReconList, SacReceiveBankRecon.class).call();
				message="上传成功！";
			}
		}catch (Exception e)
		{
			message=e.getMessage()+"，上传失败！";
			logger.error("uploadCheckAccFile occur exception……" + e.getMessage());
		}
		Map<String,Object> recFileParamMap = new HashMap<String, Object>();
		recFileParamMap.put("recType", "2");
		recFileParamMap.put("payconType", "2");
		mav.addObject("recFileParamList", checkAccService.getRecFileParamList(recFileParamMap));// 查询对账文件参数
		mav.addObject("chnCode", chnCode);
		mav.addObject("trxDate", checkTrxDate);
		mav.addObject("message", message);
		return mav;
	}
	
	/**
	 * 上传对账文件并发送账务系统
	 * 
	 * @param request
	 * @param response
	 * @throws JwsFsException
	 */
	@RequestMapping(value="/uploadCheckAccFile",method={RequestMethod.POST})
	public ModelAndView uploadCheckAccFile(HttpServletRequest request, HttpServletResponse response)
	{
		logger.debug("uploadCheckAccFile start ………………");
		ModelAndView mav = new ModelAndView("channelCheckAccount/checkAccHandle");
		String checkTrxDate = request.getParameter("trxDate");// 上传文件的日期
		String chnCode = request.getParameter("chnCode");// 支付渠道号
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		List<MultipartFile> files = multipartRequest.getFiles("checkAccFile");
		String fileName = files.get(0).isEmpty()?files.get(1).getOriginalFilename():files.get(0).getOriginalFilename();
		// 解析file,组装出发送给账务系统的数据
		String message = null;
		try
		{
			if(checkAccService.recBatchIsExist("2",checkTrxDate,chnCode,"2")){//返回true,表示已经上传
				message="该文件已经上传！请勿重复上传！";
			}else{
				String bankNodeCode = checkAccService.getBankNodeCode(chnCode);// 银行代码
				List<SacReceiveBankRecon> bankReconList = checkAccService.resolveRecFile(files, bankNodeCode, fileName);
				if (checkAccService.recDateIsRight(bankReconList,checkTrxDate)) {// 判断对账文件与交易日期是否匹配
					//bankReconList = convertCcy(bankReconList);
					// 发送账务系统
					FwsClient fwsClient = new FwsClient("SAC-AC-0006");
					fwsClient.putParam(bankReconList, SacReceiveBankRecon.class).call();
					message="上传成功！";
				} else {
					message="该对账文件内容日期与交易日期不一致,不能上传！";
					logger.info("该对账文件" + fileName + "该对账文件内容日期与交易日期不一致,不能上传！");
				}
			}
		}catch (Exception e)
		{
			message=e.getMessage()+"，请检查文件！";
			logger.error("uploadCheckAccFile occur exception……" + e.getMessage());
		}
		Map<String,Object> recFileParamMap = new HashMap<String, Object>();
		recFileParamMap.put("recType", "2");
		recFileParamMap.put("payconType", "2");
		mav.addObject("recFileParamList", checkAccService.getRecFileParamList(recFileParamMap));// 查询对账文件参数
		mav.addObject("chnCode", chnCode);
		mav.addObject("trxDate", checkTrxDate);
		mav.addObject("message", message);
		return mav;
	}
	

	/**
	 * 对账结果查询初始化
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/checkAccResultInit" ,method=RequestMethod.GET)
	public ModelAndView checkAccResultInit(HttpServletRequest request, HttpServletResponse response)
	{
		logger.debug("checkAccResultInit start ………………");
		return checkAccResult(request, response);
	}

	/**
	 * 对账结果查询
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/checkAccResult",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView checkAccResult(HttpServletRequest request, HttpServletResponse response)
	{
		logger.debug("checkAccResult start ………………");
		ModelAndView mav = new ModelAndView("channelCheckAccount/checkAccResult");
		// 组装请求参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		handlePageAndDateRange(request, paramMap, mav, 1,10);
		paramMap.put("chnCode", request.getParameter("chnCode"));// 渠道号
		paramMap.put("recOper", request.getParameter("recOper"));// 操作员
		paramMap.put("endDate", request.getParameter("endDate")==null?paramMap.get("startDate"):request.getParameter("endDate"));//结束日期
		paramMap.put("payconType", request.getParameter("payconType")==null?"":request.getParameter("payconType"));//支付渠道类型
		paramMap.put("recType", request.getParameter("recType")==null?"":request.getParameter("recType"));// 对账类型
		paramMap.put("recStatus", request.getParameter("recStatus")==null?"":request.getParameter("recStatus"));// 对账状态
		// 查询对账结果
		List<Map<String, Object>> checkAccResultList = checkAccService.queryCheckAccResult(paramMap);
		mav.addObject("checkAccResultList", checkAccResultList);
		mav.addObject("chnCode", request.getParameter("chnCode"));// 渠道号
		mav.addObject("payconType", request.getParameter("payconType"));//支付渠道类型
		mav.addObject("recType", request.getParameter("recType"));// 对账类型
		mav.addObject("recOper", request.getParameter("recOper"));// 对账类型
		mav.addObject("recStatus", request.getParameter("recStatus"));// 对账状态
		mav.addObject("totalCount",checkAccService.queryCheckAccResultCount(paramMap));//总数
		mav.addObject("recFileParamList", checkAccService.getRecFileParamList(new HashMap<String, Object>()));// 查询对账文件参数
		mav.addObject("endDate", request.getParameter("endDate")==null?paramMap.get("startDate"):request.getParameter("endDate"));//结束日期
		return mav;
	}

	/**
	 * 对账差错明细查询
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/queryDiffDetail",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView queryDiffDetail(HttpServletRequest request, HttpServletResponse response)
	{
		//将明细页面参数保存在session中
		if("Y".equals(request.getParameter("saveFlag"))){//需要保存
			saveFormDataToSession(request);
			return null;
		}
		logger.debug("queryDiffDetail start ………………");
		ModelAndView mav = new ModelAndView("channelCheckAccount/checkAccDiffDetail");
		// 组装请求参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		handlePageAndDateRange(request, paramMap, mav, 0, 10);
		paramMap.remove("startDate");
		paramMap.remove("endDate");
		paramMap.put("recBatchId", request.getParameter("recBatchId"));// 对账批次
		// 查询对账差错明细
		List<SacRecDifferences> recDiffDetailList = checkAccService.queryRecDiffDetail(paramMap);
		mav.addObject("recDiffDetailList", checkAccService.convertDiffList(recDiffDetailList));
		mav.addObject("recBatchId", request.getParameter("recBatchId"));// 对账批次
		mav.addObject("totalCount",checkAccService.queryRecDiffDetailCount(paramMap));//总数
		return mav;
	}
	
	/**
	 * 对账差错明细查询退回
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/query*DetailBack",method={RequestMethod.GET})
	public ModelAndView queryDiffDetailBack(HttpServletRequest request, HttpServletResponse response)
	{
		logger.debug("queryDiffDetailBack start ………………");
		ModelAndView mav = new ModelAndView("channelCheckAccount/checkAccResult");
		HttpSession session = request.getSession();
		// 组装请求参数
		Map<String,Object> paramMap = new HashMap<String,Object>();
		Integer pageNo = Integer.valueOf(session.getAttribute("pageNo").toString());// 默认为第1页
		Integer pageSize = 10;
		paramMap.put("start", (pageNo-1)*pageSize);//分页起始
		paramMap.put("end", pageSize*pageNo);//分页结束
		paramMap.put("startDate", session.getAttribute("startDate"));//开始日期
		paramMap.put("endDate", session.getAttribute("endDate"));//结束日期
		paramMap.put("chnCode", session.getAttribute("chnCode"));// 渠道号
		paramMap.put("payconType", session.getAttribute("payconType"));//支付渠道类型
		paramMap.put("recType", session.getAttribute("recType"));// 对账类型
		paramMap.put("recStatus", session.getAttribute("recStatus"));// 对账状态
		// 查询对账结果
		List<Map<String, Object>> checkAccResultList = checkAccService.queryCheckAccResult(paramMap);
		mav.addObject("checkAccResultList", checkAccResultList);
		mav.addObject("totalCount",checkAccService.queryCheckAccResultCount(paramMap));//总数
		mav.addObject("recFileParamList", checkAccService.getRecFileParamList(new HashMap<String, Object>()));// 查询对账文件参数
		mav.addObject("pageNo",pageNo);
		mav.addObject("pageSize", pageSize);
		mav.addObject("chnCode", session.getAttribute("chnCode"));// 渠道号
		mav.addObject("payconType", session.getAttribute("payconType"));//支付渠道类型
		mav.addObject("recType", session.getAttribute("recType"));// 对账类型
		mav.addObject("endDate", session.getAttribute("endDate"));//结束日期
		mav.addObject("startDate", session.getAttribute("startDate"));//开始日期
		mav.addObject("recStatus", session.getAttribute("recStatus"));// 对账状态
		session.removeAttribute("startDate");
		session.removeAttribute("endDate");
		session.removeAttribute("chnCode");
		session.removeAttribute("payconType");
		session.removeAttribute("recType");
		session.removeAttribute("recStatus");
		return mav;
	}
	
	/**
	 * 对账批次明细查询
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/queryBatchDetail",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView queryBatchDetail(HttpServletRequest request, HttpServletResponse response)
	{
		//将明细页面参数保存在session中
		if("Y".equals(request.getParameter("saveFlag"))){//需要保存
			saveFormDataToSession(request);
			return null;
		}
		logger.debug("queryBatchDetail start ………………");
		ModelAndView mav = new ModelAndView("channelCheckAccount/checkAccBatchDetail");
		// 组装请求参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		handlePageAndDateRange(request, paramMap, mav, 0, 10);
		paramMap.remove("startDate");
		paramMap.remove("endDate");
		paramMap.put("recBatchId", request.getParameter("recBatchId"));// 对账批次
		// 查询对账差错明细
		List<SacRecDetail> recBatchDetailList = checkAccService.queryRecBatchDetail(paramMap);
		mav.addObject("recBatchDetailList", checkAccService.convertDetailList(recBatchDetailList));
		mav.addObject("recBatchId", request.getParameter("recBatchId"));// 对账批次
		mav.addObject("totalCount",checkAccService.queryRecBatchDetailCount(paramMap));//总数
		return mav;
	}
	
	/**
	 * 统一对账文件中的币种格式
	 */
	private List<SacReceiveBankRecon> convertCcy(List<SacReceiveBankRecon> bankReconList){
		if(bankReconList==null||bankReconList.size()==0){
			return bankReconList;
		}
		SacReceiveBankRecon bankRecon = bankReconList.get(0);//取出第一条
		String currencyType = bankRecon.getCurrencyType();
		if(currencyType.length()==2){//数字类型
			return bankReconList;
		}
		List<UnifiedConfig> ccyList = UnifiedConfigSimple.getDicTypeConfig ("11");
		for(SacReceiveBankRecon bankRecons:bankReconList){
			for(UnifiedConfig config:ccyList){
				if(bankRecons.getCurrencyType().equals(config.getDicCode())){
					bankRecons.setCurrencyType(config.getDicValue());//币种转成数字类型
					break;
				}
			}
		}
		return bankReconList;
	}
	
}
