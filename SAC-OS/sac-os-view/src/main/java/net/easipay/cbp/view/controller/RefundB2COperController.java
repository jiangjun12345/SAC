package net.easipay.cbp.view.controller;

import java.io.ByteArrayOutputStream;
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
import net.easipay.cbp.cas.users.SecurityOperator;
import net.easipay.cbp.constant.Constants;
import net.easipay.cbp.model.SacB2cExrefundApply;
import net.easipay.cbp.service.IRefundB2cService;
import net.easipay.cbp.service.ISacOperHistoryService;
import net.easipay.cbp.util.BeanUtils;
import net.easipay.cbp.util.ConstantParams;
import net.easipay.cbp.util.PersonUtil;
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
public class RefundB2COperController extends BaseDataController {

  private Logger logger = LoggerFactory.getLogger(RefundB2COperController.class);

  @Autowired
  public IRefundB2cService refundB2cService;
  
  @Autowired
  private ISacOperHistoryService sacOperHistoryService;

  /**
   * B2B退款查询初始化操作
   * 
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/b2cRefundQueryInit", method = {RequestMethod.GET})
  public ModelAndView b2cRefundQueryInit(HttpServletRequest request, HttpServletResponse response) {
    SacB2cExrefundApply sacB2cExrefundApply = new SacB2cExrefundApply();
    return b2cRefundQuery(request, response, sacB2cExrefundApply);
  }

  private ModelAndView commandQueryForB2CRefund(ModelAndView mav, Map<String, Object> paramMap, SacB2cExrefundApply sacB2cExrefundApply) {
    // 分页查询退款明细
    List<SacB2cExrefundApply> commandList = refundB2cService.b2cRefundTrxInfoForPaging(paramMap);
    // 返回结果
    mav.addObject("refundStateMap", CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_B2C_REFUND_STATE));// 退款状态数据
    mav.addObject("bankMap", CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_BANK));// 银行数据
    mav.addObject("commandList", refundB2cService.handleSacRefundApplyList(commandList));
    mav.addObject("totalCount", refundB2cService.b2cRefundTrxCounts(paramMap));
    mav.addObject("sacB2cExrefundApply", sacB2cExrefundApply);//查询明细
    mav.addObject("ccyMap", CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_CCY));
    return mav;
  }

  /**
   * B2B退款查询操作
   * 
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/b2cRefundQuery", method = {RequestMethod.POST, RequestMethod.GET})
  public ModelAndView b2cRefundQuery(HttpServletRequest request, HttpServletResponse response, SacB2cExrefundApply sacB2cExrefundApply) {
    ModelAndView mav = new ModelAndView("refundManage/b2cRefundQuery");
    // 组装查询参数
    Map<String, Object> paramMap = BeanUtils.transBean2Map(sacB2cExrefundApply);
    handlePageAndDateRange(request, paramMap, mav, 7, 10);
    logger.info("b2cRefundQuery paramMap is " + paramMap);
    this.commandQueryForB2CRefund(mav, paramMap, sacB2cExrefundApply);
    return mav;
  }

  /**
   * 下载B2c退款查询记录
   */
  @RequestMapping(value = "/b2cRefundDownloadToExcel", method = RequestMethod.POST)
  public void b2cRefundDownloadToExcel(HttpServletRequest request, HttpServletResponse response, SacB2cExrefundApply sacB2cExrefundApply) throws Exception {

    //    String wipeStr = request.getParameter("wpIds");
    //    String[] wpIds = wipeStr.split("\\|");
    //    logger.debug("query params:" + wipeStr);
    ModelAndView mav = new ModelAndView("refundManage/b2cRefundQuery");
    // 组装查询参数
    Map<String, Object> paramMap = BeanUtils.transBean2Map(sacB2cExrefundApply);
    handlePageAndDateRange(request, paramMap, mav, 0, 10);
    logger.info("b2cRefundDownloadToExcel paramMap is " + paramMap);
    // 查询退款明细
    List<SacB2cExrefundApply> applyList = refundB2cService.b2cRefundTrxInfo(paramMap);
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    try {
      stream = refundB2cService.exportExcel(applyList);
      // 导出数据To OutputStream
      response.setContentType("application/vnd.ms-excel");
      // 下载文件名
      String downloadFileName = "RefundApply" + new SimpleDateFormat("yyyy-MM-dd_HHmmss").format(new Date()) + ".csv";
      response.addHeader("Content-Disposition", "attachment;filename=" + downloadFileName);
      response.addHeader("Content-Length", "" + stream.size());
      response.getOutputStream().write(stream.toByteArray());
    } catch (Exception e) {
      logger.error("Fail to download b2cRefundDownloadToExcel", e);
      throw new RuntimeException("Fail to download b2cRefundDownloadToExcel", e);
    } finally {
      stream.close();
    }
  }

  /**
   * B2B退款经办初始化操作
   * 
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/b2cRefundHandlingQueryInit", method = {RequestMethod.GET})
  public ModelAndView b2cRefundHandlingQueryInit(HttpServletRequest request, HttpServletResponse response) {
    SacB2cExrefundApply sacB2cExrefundApply = new SacB2cExrefundApply();
    return b2cRefundHandlingQuery(request, response, sacB2cExrefundApply);
  }

  /**
   * B2B退款经办查询操作
   * 
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/b2cRefundHandlingQuery", method = {RequestMethod.POST, RequestMethod.GET})
  public ModelAndView b2cRefundHandlingQuery(HttpServletRequest request, HttpServletResponse response, SacB2cExrefundApply sacB2cExrefundApply) {
    ModelAndView mav = new ModelAndView("refundManage/b2cRefundHandlingQuery");
    // 组装查询参数
    saveFormDataToSession(request);
    Map<String, Object> paramMap = BeanUtils.transBean2Map(sacB2cExrefundApply);
    handlePageAndDateRange(request, paramMap, mav, 7, 10);
    UnifiedConfig unifiedConfig = UnifiedConfigSimple.getDicCodeConfig(Constants.REFUND_B2C_STATE_NEW);
    paramMap.put("applyState", unifiedConfig.getDicValue());
    logger.info("b2cRefundQuery paramMap is " + paramMap);
    this.commandQueryForB2CRefund(mav, paramMap, sacB2cExrefundApply);
    return mav;
  }

  /**
   * 下载B2C退款经办记录
   */
  @RequestMapping(value = "/b2cRefundHandlingDownloadToExcel", method = RequestMethod.POST)
  public void b2cRefundHandlingDownloadToExcel(HttpServletRequest request, HttpServletResponse response, SacB2cExrefundApply sacB2cExrefundApply) throws Exception {
    //    String wipeStr = request.getParameter("wpIds");
    //    String[] wpIds = wipeStr.split("\\|");
    //    logger.debug("query params:" + wipeStr);
    ModelAndView mav = new ModelAndView("refundManage/b2cRefundHandlingQuery");
    // 组装查询参数
    Map<String, Object> paramMap = BeanUtils.transBean2Map(sacB2cExrefundApply);
    handlePageAndDateRange(request, paramMap, mav, 0, 10);
    UnifiedConfig unifiedConfig = UnifiedConfigSimple.getDicCodeConfig(Constants.REFUND_B2C_STATE_NEW);
    paramMap.put("applyState", unifiedConfig.getDicValue());
    logger.info("b2cRefundHandlingDownloadToExcel paramMap is " + paramMap);
    // 查询退款明细
    List<SacB2cExrefundApply> applyList = refundB2cService.b2cRefundTrxInfo(paramMap);
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    try {
      stream = refundB2cService.exportExcel(applyList);
      // 导出数据To OutputStream
      response.setContentType("application/vnd.ms-excel");
      // 下载文件名
      String downloadFileName = "RefundApply" + new SimpleDateFormat("yyyy-MM-dd_HHmmss").format(new Date()) + ".csv";
      response.addHeader("Content-Disposition", "attachment;filename=" + downloadFileName);
      response.addHeader("Content-Length", "" + stream.size());
      response.getOutputStream().write(stream.toByteArray());
    } catch (Exception e) {
      logger.error("Fail to download b2cRefundHandlingDownloadToExcel", e);
      throw new RuntimeException("Fail to download b2cRefundHandlingDownloadToExcel", e);
    } finally {
      stream.close();
    }
  }

  /**
   * B2C退款经办
   */
  @RequestMapping(value = "/b2cRefundOperate", method = RequestMethod.POST)
  public ModelAndView b2cRefundOperate(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ModelAndView mv = new ModelAndView("refundManage/b2cRefundHandlingQuery");
    String applyIds = request.getParameter("applyIds");
    logger.debug("退款经办通知，退款通知申请IDs：" + applyIds);
    String[] refundIds = applyIds.split("\\|");
    SecurityOperator person = PersonUtil.getUser();

    HttpSession session = request.getSession();
    SacB2cExrefundApply sacB2cExrefundApply = new SacB2cExrefundApply();
    sacB2cExrefundApply.setRefundSerialNo((String) session.getAttribute("refundSerialNo"));
    sacB2cExrefundApply.setOtrxSerialNo((String) session.getAttribute("otrxSerialNo"));
    sacB2cExrefundApply.setMerchantName((String) session.getAttribute("merchantName"));
    sacB2cExrefundApply.setRecNcode((String) session.getAttribute("recNcode"));
    String message = "";
    try {
      refundB2cService.b2cRefundOperate(string2LongArray(refundIds), person);
    } catch (Exception e) {
      e.printStackTrace();
      message = e.getMessage();
      request.setAttribute("message", message);
      return this.b2cRefundHandlingQuery(request, response, sacB2cExrefundApply);
    }
    message = "经办完成";
    request.setAttribute("message", message);
    sacOperHistoryService.insertSacOperHistory(Constants.B2C_REFUND_WAIT_CHECK,request);//B2C退款经办成功
    return this.b2cRefundHandlingQuery(request, response, sacB2cExrefundApply);
  }

  /**
   * B2B退款复核初始化操作
   * 
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/b2cRefundCheckQueryInit", method = {RequestMethod.GET})
  public ModelAndView b2cRefundCheckQueryInit(HttpServletRequest request, HttpServletResponse response) {
    SacB2cExrefundApply sacB2cExrefundApply = new SacB2cExrefundApply();
    return b2cRefundCheckQuery(request, response, sacB2cExrefundApply);
  }

  /**
   * B2B退款复核操作
   * 
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/b2cRefundCheckQuery", method = {RequestMethod.POST, RequestMethod.GET})
  public ModelAndView b2cRefundCheckQuery(HttpServletRequest request, HttpServletResponse response, SacB2cExrefundApply sacB2cExrefundApply) {
    ModelAndView mav = new ModelAndView("refundManage/b2cRefundCheckQuery");
    // 组装查询参数
    saveFormDataToSession(request);
    Map<String, Object> paramMap = BeanUtils.transBean2Map(sacB2cExrefundApply);
    this.handlePageAndDateRangeSpec(request, paramMap, mav, 7, 10);
    UnifiedConfig unifiedConfig = UnifiedConfigSimple.getDicCodeConfig(Constants.REFUND_B2C_STATE_WAIT_CHECK);
    paramMap.put("applyState", unifiedConfig.getDicValue());
    logger.info("b2cRefundQuery paramMap is " + paramMap);
    this.commandQueryForB2CRefund(mav, paramMap, sacB2cExrefundApply);
    return mav;
  }

  /**
   * 下载B2C退款复核记录
   */
  @RequestMapping(value = "/b2cRefundCheckDownloadToExcel", method = RequestMethod.POST)
  public void b2cRefundCheckDownloadToExcel(HttpServletRequest request, HttpServletResponse response, SacB2cExrefundApply sacB2cExrefundApply) throws Exception {
    //    String wipeStr = request.getParameter("wpIds");
    //    String[] wpIds = wipeStr.split("\\|");
    //    logger.debug("query params:" + wipeStr);
    ModelAndView mav = new ModelAndView("refundManage/b2cRefundCheckQuery");
    // 组装查询参数
    Map<String, Object> paramMap = BeanUtils.transBean2Map(sacB2cExrefundApply);
    this.handlePageAndDateRangeSpec(request, paramMap, mav, 0, 10);
    UnifiedConfig unifiedConfig = UnifiedConfigSimple.getDicCodeConfig(Constants.REFUND_B2C_STATE_WAIT_CHECK);
    paramMap.put("applyState", unifiedConfig.getDicValue());
    logger.info("b2cRefundCheckDownloadToExcel paramMap is " + paramMap);
    // 查询退款明细
    List<SacB2cExrefundApply> applyList = refundB2cService.b2cRefundTrxInfo(paramMap);
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    try {
      stream = refundB2cService.exportExcel(applyList);
      // 导出数据To OutputStream
      response.setContentType("application/vnd.ms-excel");
      // 下载文件名
      String downloadFileName = "RefundApply" + new SimpleDateFormat("yyyy-MM-dd_HHmmss").format(new Date()) + ".csv";
      response.addHeader("Content-Disposition", "attachment;filename=" + downloadFileName);
      response.addHeader("Content-Length", "" + stream.size());
      response.getOutputStream().write(stream.toByteArray());
    } catch (Exception e) {
      logger.error("Fail to download b2cRefundCheckDownloadToExcel", e);
      throw new RuntimeException("Fail to download b2cRefundCheckDownloadToExcel", e);
    } finally {
      stream.close();
    }
  }

  /**
   * B2C退款复核
   */
  @RequestMapping(value = "/b2cRefundAudit", method = RequestMethod.POST)
  public ModelAndView b2cRefundAudit(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ModelAndView mv = new ModelAndView("refundManage/b2cRefundCheckQuery");
    String applyIds = request.getParameter("applyIds");
    logger.debug("退款复核，退款复核申请IDs：" + applyIds);
    String[] refundIds = applyIds.split("\\|");
    SecurityOperator person = PersonUtil.getUser();

    HttpSession session = request.getSession();
    SacB2cExrefundApply sacB2cExrefundApply = new SacB2cExrefundApply();
    sacB2cExrefundApply.setRefundSerialNo((String) session.getAttribute("refundSerialNo"));
    sacB2cExrefundApply.setOtrxSerialNo((String) session.getAttribute("otrxSerialNo"));
    sacB2cExrefundApply.setMerchantName((String) session.getAttribute("merchantName"));
    sacB2cExrefundApply.setRecNcode((String) session.getAttribute("recNcode"));
    sacB2cExrefundApply.setCrtCurrency((String) session.getAttribute("crtCurrency"));
    sacB2cExrefundApply.setExBankCode((String) session.getAttribute("exBankCode"));

    String message = "";
    try {
      refundB2cService.b2cRefundCheckPass(string2LongArray(refundIds), person);
    } catch (Exception e) {
      e.printStackTrace();
      message = e.getMessage();
      request.setAttribute("message", message);
      return this.b2cRefundCheckQuery(request, response, sacB2cExrefundApply);
    }
    message = "复核完成";
    request.setAttribute("message", message);
    sacOperHistoryService.insertSacOperHistory(Constants.B2C_REFUND_CHECK,request);//B2C退款复核
    return this.b2cRefundCheckQuery(request, response, sacB2cExrefundApply);
  }

  /**
   * B2C退款详情
   */
  @ResponseBody
  @RequestMapping(value = "/b2cRefundDetail", method = RequestMethod.POST)
  public String b2cRefundDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String selectId = request.getParameter("id");
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("exrefundApplyId", selectId);
    SacB2cExrefundApply sacB2cExrefundApply = refundB2cService.b2cRefundTrxInfoForOnly(paramMap);
    List<SacB2cExrefundApply> applyList = new ArrayList<SacB2cExrefundApply>();
    applyList.add(sacB2cExrefundApply);
    refundB2cService.handleSacRefundApplyList(applyList);
    String content = JSONObject.fromObject(sacB2cExrefundApply).toString();
    return content;
  }

  /**
   * 处理页码和默认起止时间
   * 
   * @param request
   * @param paramMap
   * @param mav
   * @param startNum :当前时间向前推几天
   * @param pageSize
   */

  private void handlePageAndDateRangeSpec(HttpServletRequest request, Map<String, Object> paramMap, ModelAndView mav, int startNum, int pageSizeNum) {
    // 页码处理
    Integer pageNo = 1;// 默认为第1页
    if (request.getParameter("pageNo") != null && !"".equals(request.getParameter("pageNo"))) {
      pageNo = Integer.parseInt(request.getParameter("pageNo"));
    }

    Integer pageSize = pageSizeNum;// 默认每页显示pageSizeNum条
    if (request.getParameter("pageSize") != null && !"".equals(request.getParameter("pageSize"))) {
      pageSize = Integer.parseInt(request.getParameter("pageSize"));
    }
    //起止时间处理
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.DAY_OF_MONTH, -startNum);
    String startDate = sdf.format(cal.getTime());
    paramMap.put("startDate1", request.getParameter("startDate1") == null ? startDate : request.getParameter("startDate1"));//开始日期
    paramMap.put("endDate1", request.getParameter("endDate1"));//结束日期
    paramMap.put("start", (pageNo - 1) * pageSize);//分页起始
    paramMap.put("end", pageSize * pageNo);//分页结束
    mav.addObject("pageNo", pageNo);
    mav.addObject("pageSize", pageSize);
    mav.addObject("startDate1", request.getParameter("startDate1") == null ? startDate : request.getParameter("startDate1"));//开始日期
    mav.addObject("endDate1", request.getParameter("endDate1"));//结束日期
  }

  /**
   * 字符串数组转化为Long数组
   * 
   * @param str
   * @return
   */
  public Long[] string2LongArray(String[] str) {
    Long[] l = new Long[str.length];

    for (int i = 0; i < str.length; i++) {
      l[i] = Long.parseLong(str[i]);
    }
    return l;
  }

}
