package net.easipay.cbp.view.controller;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.easipay.cbp.cache.CacheUtil;
import net.easipay.cbp.cas.users.SecurityOperator;
import net.easipay.cbp.constant.Constants;
import net.easipay.cbp.exception.OFLOnloadException;
import net.easipay.cbp.model.SacRefundBatch;
import net.easipay.cbp.model.SacRefundCommand;
import net.easipay.cbp.service.IRefundService;
import net.easipay.cbp.service.ISacOperHistoryService;
import net.easipay.cbp.service.impl.RefundServiceImpl.OFLBatchState;
import net.easipay.cbp.util.BeanUtils;
import net.easipay.cbp.util.ConstantParams;
import net.easipay.cbp.util.PersonUtil;
import net.easipay.cbp.util.StringUtil;
import net.easipay.dsfc.UnifiedConfig;
import net.easipay.dsfc.UnifiedConfigSimple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description: 客户交易管理控制层
 * @author dsy (作者英文名称)
 * @date 2015-7-1 下午02:31:06
 * @version V1.0
 * @jdk v1.6
 */
@Controller
public class RefundOperController extends BaseDataController {

  private Logger logger = LoggerFactory.getLogger(RefundOperController.class);

  @Autowired
  public IRefundService refundService;
  
  @Autowired
  private ISacOperHistoryService sacOperHistoryService;

  /**
   * B2B退款查询初始化操作
   * 
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/b2bRefundQueryInit", method = {RequestMethod.GET})
  public ModelAndView b2bRefundQueryInit(HttpServletRequest request, HttpServletResponse response) {
    SacRefundCommand sacRefundDetail = new SacRefundCommand();
    return b2bRefundQuery(request, response, sacRefundDetail);
  }

  /**
   * B2B退款查询操作
   * 
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/b2bRefundQuery", method = {RequestMethod.POST, RequestMethod.GET})
  public ModelAndView b2bRefundQuery(HttpServletRequest request, HttpServletResponse response, SacRefundCommand sacRefundCommand) {
    ModelAndView mav = new ModelAndView("refundManage/b2bRefundQuery");
    // 组装查询参数
    Map<String, Object> paramMap = BeanUtils.transBean2Map(sacRefundCommand);
    handlePageAndDateRange(request, paramMap, mav, 1, 10);
    logger.info("b2bRefundQuery paramMap is " + paramMap);
    // 分页查询退款明细
    List<SacRefundCommand> commandList = refundService.b2bRefundTrxInfoForPaging(paramMap);
    // 返回结果

    mav.addObject("refundStateMap", CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_REFUND_STATE));// 退款状态数据
    mav.addObject("bankMap", CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_BANK));// 银行数据
    mav.addObject("commandList", commandList);
    mav.addObject("totalCount", refundService.b2bRefundTrxCounts(paramMap));
    mav.addObject("sacRefundCommand", sacRefundCommand);// 查询明细
    return mav;
  }

  /**
   * B2B退款经办查询初始化操作
   * 
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/b2bRefundOperateInit", method = {RequestMethod.GET})
  public ModelAndView b2bRefundOperateInit(HttpServletRequest request, HttpServletResponse response) {
    SacRefundCommand sacRefundDetail = new SacRefundCommand();
    return b2bRefundOperateQuery(request, response, sacRefundDetail);
  }

  /**
   * B2B退款经办查询操作
   * 
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/b2bRefundOperateQuery", method = {RequestMethod.POST, RequestMethod.GET})
  public ModelAndView b2bRefundOperateQuery(HttpServletRequest request, HttpServletResponse response, SacRefundCommand sacRefundCommand) {
    ModelAndView mav = new ModelAndView("refundManage/b2bRefundOperateQuery");
    // 组装查询参数
    Map<String, Object> paramMap = BeanUtils.transBean2Map(sacRefundCommand);
    handlePageAndDateRange(request, paramMap, mav, 1, 10);
    logger.info("b2bRefundOperateQuery paramMap is " + paramMap);
    // 分页查询退款明细
    List<SacRefundCommand> commandList = refundService.b2bRefundTrxInfoForPaging(paramMap);
    // 返回结果
    Map<String, Object> stateMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_REFUND_STATE);
    UnifiedConfig unifiedConfig = UnifiedConfigSimple.getDicCodeConfig(ConstantParams.SAC_B2B_REFUND_SUCC_STATE);
    stateMap.remove(unifiedConfig.getDicValue());
    mav.addObject("refundStateMap", stateMap);// 退款状态数据-删除掉成功状态
    mav.addObject("bankMap", CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_BANK));// 银行数据
    mav.addObject("commandList", commandList);
    mav.addObject("totalCount", refundService.b2bRefundTrxCounts(paramMap));
    mav.addObject("sacRefundCommand", sacRefundCommand);// 查询明细
    return mav;
  }

  /**
   * 下载网银退款记录
   */
  @RequestMapping(value = "/b2bRefundDownloadToExcel", method = RequestMethod.POST)
  public void b2bRefundDownloadToExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {

    String wipeStr = request.getParameter("wpIds");
    String[] wpIds = wipeStr.split("\\|");
    logger.debug("query params:" + wipeStr);

    ByteArrayOutputStream stream = refundService.exportExcel(wpIds, Constants.TPL_WP_RUFUND_REPORT_XLS);
    response.setContentType("application/vnd.ms-excel");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String downloadFileName = "WpRefund" + sdf.format(new Date()) + ".xls";
    response.addHeader("Content-Disposition", "attachment;filename=" + downloadFileName);
    response.addHeader("Content-Length", "" + stream.size());
    response.getOutputStream().write(stream.toByteArray());
    stream.close();
  }

  /**
   * 上传B2B退款文件初始化
   * 
   * @param request
   * @param response
   */
  @RequestMapping(value = "/uploadB2BRefundFileInit", method = {RequestMethod.GET, RequestMethod.POST})
  public ModelAndView uploadB2BRefundFileInit(HttpServletRequest request, HttpServletResponse response) {
    ModelAndView mav = new ModelAndView("refundManage/uploadB2BRefund");
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
  @RequestMapping(value = "/uploadB2BRefundFile", method = {RequestMethod.POST})
  public ModelAndView uploadB2BRefundFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ModelAndView mav = new ModelAndView("refundManage/uploadB2BRefund");
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

    SacRefundBatch oflBatch = null;
    if (oflExcelFile.getSize() != 0) {
      SecurityOperator person = PersonUtil.getUser();
      try {
        oflBatch = refundService.readOflXls(oflExcelFileName, oflExcelFile.getInputStream(), person);
      } catch (OFLOnloadException e) {
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
    message = "上传处理成功！文件名：" + oflExcelFileName + oflBatch.toViewInfo();
    mav.addObject("message", message);
    sacOperHistoryService.insertSacOperHistory(Constants.B2B_REFUND_UPLOAD,request);//银联B2B退款批次上传
    return mav;
  }

  /**
   * B2B退款批次查询操作
   * 
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/b2bRefundBatchQuery", method = {RequestMethod.POST, RequestMethod.GET})
  public ModelAndView b2bRefundBatchQuery(HttpServletRequest request, HttpServletResponse response) {
    ModelAndView mav = new ModelAndView("refundManage/refundB2BBatchQuery");
    //组装查询参数
    Map<String, Object> paramMap = new HashMap<String, Object>();
    String batchState = request.getParameter("batchState");
    batchState = (batchState == null) ? OFLBatchState.Init.code() : batchState;
    handlePageAndDateRange(request, paramMap, mav, 0, 10);
    paramMap.put("batchState", batchState);//批次状态
    logger.info("b2bRefundBatchQuery paramMap is " + paramMap);
    List<SacRefundBatch> batchList = refundService.b2bRefundBatchInfoForPaging(paramMap);
    //准备初始化参数
    mav.addObject("batchState", batchState);//客户名称
    mav.addObject("totalCount", refundService.b2bRefundBatchCounts(paramMap));
    mav.addObject("batchList", refundService.handleSacRefundBatchList(batchList));
    mav.addObject("batchSateMap", CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_REFUND_BATCH_STATE));

    return mav;
  }

  /**
   * 客户余额明细查询操作
   * 
   * @param request
   * @param response
   * @return
   * @throws ParseException
   */
  @RequestMapping(value = "/b2bRefundBatchDetailQuery", method = {RequestMethod.POST, RequestMethod.GET})
  public ModelAndView b2bRefundBatchDetailQuery(HttpServletRequest request, HttpServletResponse response) throws ParseException {
    //将明细页面参数保存在session中
    if ("Y".equals(request.getParameter("saveFlag"))) {//需要保存
      saveFormDataToSession(request);
      return null;
    }
    ModelAndView mav = new ModelAndView("refundManage/refundB2BBatchDetailQuery");
    Map<String, Object> paramMapBatch = new HashMap<String, Object>();
    paramMapBatch.put("oflWithdrawBatchId", request.getParameter("oflWithdrawBatchId") == null ? "" : request.getParameter("oflWithdrawBatchId"));//批次表主键
    SacRefundBatch batchInfo = refundService.b2bRefundBatchInfoForOnly(paramMapBatch);
    logger.info("b2bRefundBatchQuery paramMap is " + paramMapBatch);
    List<SacRefundBatch> batchList = new ArrayList<SacRefundBatch>();
    batchList.add(batchInfo);

    //组装查询参数
    Map<String, Object> paramMap = new HashMap<String, Object>();
    handlePageAndDateRange(request, paramMap, mav, 0, 10);
    paramMap.remove("startDate");
    paramMap.remove("endDate");
    String batchSeriNo = batchInfo.getBatchSerialNo();
    paramMap.put("expBatch", batchSeriNo);
    logger.info("b2bRefundBatchDetailQuery paramMap is " + paramMap);

    List<SacRefundCommand> sacCommandList = refundService.b2bRefundTrxInfoForPaging(paramMap);
    //返回结果

    mav.addObject("batchList", refundService.handleSacRefundBatchList(batchList));
    mav.addObject("commandList", sacCommandList);
    mav.addObject("totalCount", refundService.b2bRefundTrxCounts(paramMap));
    mav.addObject("oflWithdrawBatchId", request.getParameter("oflWithdrawBatchId"));
    return mav;
  }

  /**
   * 客户余额总查询退回操作
   * 
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/b2bRefundBatchDetailBack", method = {RequestMethod.GET, RequestMethod.POST})
  public ModelAndView cusBalanceQueryBack(HttpServletRequest request, HttpServletResponse response) {
    ModelAndView mav = new ModelAndView("refundManage/refundB2BBatchQuery");
    HttpSession session = request.getSession();
    //组装查询参数
    Map<String, Object> paramMap = new HashMap<String, Object>();
    Integer pageNo = Integer.valueOf(session.getAttribute("pageNo").toString());// 默认为第1页
    Integer pageSize = 10;
    paramMap.put("start", (pageNo - 1) * pageSize);//分页起始
    paramMap.put("end", pageSize * pageNo);//分页结束
    paramMap.put("batchState", session.getAttribute("batchState"));//批次状态
    logger.info("b2bRefundBatchDetailBack paramMap is " + paramMap);
    List<SacRefundBatch> batchList = refundService.b2bRefundBatchInfoForPaging(paramMap);
    //准备初始化参数
    mav.addObject("pageNo", pageNo);
    mav.addObject("pageSize", pageSize);
    mav.addObject("batchState", session.getAttribute("batchState"));//批次状态
    mav.addObject("totalCount", refundService.b2bRefundBatchCounts(paramMap));
    mav.addObject("batchList", refundService.handleSacRefundBatchList(batchList));
    mav.addObject("batchSateMap", CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_REFUND_BATCH_STATE));

    return mav;
  }

  /**
   * 复核批次通过
   */
  @RequestMapping(value = "/reviewB2BRefundPass", method = {RequestMethod.GET, RequestMethod.POST})
  public ModelAndView reviewB2BRefundPass(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ModelAndView mv = new ModelAndView("refundManage/refundB2BBatchQuery");
    String wipeStr = request.getParameter("wpIds");
    String[] batchIdNos = wipeStr.split("\\|");
    logger.debug("Selected BatchID:" + wipeStr);
    SecurityOperator person = PersonUtil.getUser();
    refundService.reviewB2BRefundBatchPass(batchIdNos, person);
    request.setAttribute("message", "批次复核成功");
    sacOperHistoryService.insertSacOperHistory(Constants.B2B_REFUND_CHECK,request);//银联B2B退款批次复核
    return this.b2bRefundBatchQuery(request, response);
  }

  /**
   * 复核批次不通过
   */
  @RequestMapping(value = "/reviewB2BRefundReject", method = {RequestMethod.GET, RequestMethod.POST})
  public ModelAndView reviewB2BRefundReject(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ModelAndView mv = new ModelAndView("refundManage/refundB2BBatchQuery");
    String wipeStr = request.getParameter("wpIds");
    String[] batchIdNos = wipeStr.split("\\|");
    logger.debug("Selected BatchID:" + wipeStr);
    SecurityOperator person = PersonUtil.getUser();
    refundService.reviewB2BRefundBatchReject(batchIdNos, person);
    request.setAttribute("message", "批次复核不成功");
    sacOperHistoryService.insertSacOperHistory(Constants.B2B_REFUND_CHECK,request);//银联B2B退款批次复核
    return this.b2bRefundBatchQuery(request, response);
  }
}
