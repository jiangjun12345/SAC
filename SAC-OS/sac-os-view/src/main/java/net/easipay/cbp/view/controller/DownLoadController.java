package net.easipay.cbp.view.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.easipay.cbp.cache.CacheUtil;
import net.easipay.cbp.export.DataExportAsyncThread;
import net.easipay.cbp.export.DataExportColumn;
import net.easipay.cbp.export.DataExportContainer;
import net.easipay.cbp.export.DataExportDataSource;
import net.easipay.cbp.export.DataExportFileType;
import net.easipay.cbp.export.DataExportTask;
import net.easipay.cbp.export.DataExportTaskBean;
import net.easipay.cbp.model.FinMx;
import net.easipay.cbp.model.SacCheckInfo;
import net.easipay.cbp.model.SacOtrxInfo;
import net.easipay.cbp.service.IFinStatBankService;
import net.easipay.cbp.service.ISacCheckInfoService;
import net.easipay.cbp.util.ConstantParams;
import net.easipay.cbp.util.DownLoadUtil;

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
public class DownLoadController
{

	private Logger logger = LoggerFactory.getLogger(DownLoadController.class);
	
    
    @Autowired
    private IFinStatBankService finStatBankService;
    
    @Autowired
    private ISacCheckInfoService sacCheckInfoService;
    
    
		/**
		 * 每日明细下载
		 * @param request
		 * @param response
		 * @throws Exception 
		*/
		@RequestMapping(value="/finMxDailyYEDownload",method={RequestMethod.POST})
		public ModelAndView finMxDailyDownload(HttpServletRequest request,HttpServletResponse response) throws Exception{
			ModelAndView mav = new ModelAndView("downloadManage/downloadPage");
			
			final DataExportTaskBean dataExportTaskBean = DataExportContainer.newDataExportTaskBean("mxDaily", DataExportFileType.CSV);
			
			final Map<String,Object> paramMap =  new HashMap<String,Object>();
			paramMap.put("tradeTime", request.getParameter("tradeTime"));
			paramMap.put("codeId", request.getParameter("codeId"));
			paramMap.put("currency", request.getParameter("ccyEnglish"));
			logger.info("finMxDailyDownload paramMap is "+paramMap);
			
			final Map<String,Object> bussTypeMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_BUSS_TYPE);
			final Map<String,Object> trxTypeMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_TRX_TYPE);
			final SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			
			DataExportTask dataExportTask = new DataExportTask() {
			    @Override
			    public void init() throws Exception
			    {
			    }

			    @Override
			    public DataExportTaskBean getDataExportTaskBean()
			    {
				return dataExportTaskBean;
			    }

			    @Override
			    public List<DataExportColumn> getDataExportColumns()
			    {
				List<DataExportColumn> list = new ArrayList<DataExportColumn>();
				list.add(new DataExportColumn("serno", "交易流水号"));
				list.add(new DataExportColumn("codeId", "科目代码"));
				list.add(new DataExportColumn("direction", "借贷标志"));
				list.add(new DataExportColumn("fDebit", "借方发生额"));
				list.add(new DataExportColumn("fCredit", "贷方发生额"));
				list.add(new DataExportColumn("amount", "发生额"));
				list.add(new DataExportColumn("openBal", "期初余额"));
				list.add(new DataExportColumn("closeBal", "期末余额"));
				list.add(new DataExportColumn("bussType", "业务类型"));
				list.add(new DataExportColumn("trxType", "交易类型"));
				list.add(new DataExportColumn("tradeTime", "交易时间"));
				list.add(new DataExportColumn("gains", "结汇损益"));
				list.add(new DataExportColumn("pzNo", "凭证号"));
				list.add(new DataExportColumn("mxTime", "明细时间"));
				return list;
				
			    }
			    
			    List<DataExportDataSource> dataExportDataSources = new ArrayList<DataExportDataSource>();

			    @Override
			    public DataExportDataSource[] getDataExportData(int startIdx, int endIdx) throws Exception
			    {
				
			    dataExportDataSources.clear();
		    	
				paramMap.put("start", startIdx-1);
				
				paramMap.put("end", endIdx);
				
				List<FinMx> preparedFundDetailList = finStatBankService.selectPreparedFundDetail(paramMap);

				for(int i = 0 ; i < preparedFundDetailList.size(); i++){
					FinMx fm = preparedFundDetailList.get(i);
					DataExportDataSource dataExportDataSource = new DataExportDataSource();
					
					dataExportDataSource.set("serno", fm.getSerno());
					dataExportDataSource.set("codeId", fm.getCodeId());
					dataExportDataSource.set("direction", fm.getDirection()==1?"借":"贷");
					dataExportDataSource.set("fDebit", fm.getfDebit());
					dataExportDataSource.set("fCredit", fm.getfCredit());
					dataExportDataSource.set("amount", fm.getAmount());
					dataExportDataSource.set("openBal", fm.getOpenBal());
					dataExportDataSource.set("closeBal", fm.getCloseBal());
					dataExportDataSource.set("bussType", bussTypeMap.get(fm.getBusiType())==null?"-":bussTypeMap.get(fm.getBusiType()));
					dataExportDataSource.set("trxType", trxTypeMap.get(fm.getTrxCode())==null?"-":trxTypeMap.get(fm.getTrxCode()));
					dataExportDataSource.set("tradeTime", sf.format(fm.getTradeTime()));
					dataExportDataSource.set("gains", fm.getGains()==null?"-":fm.getGains());
					dataExportDataSource.set("pzNo", fm.getPzNo());
					dataExportDataSource.set("mxTime", sf.format(fm.getMxTime()));
					
				    dataExportDataSources.add(dataExportDataSource);
				}
				
				DataExportDataSource[] data = new DataExportDataSource[dataExportDataSources.size()];
				return dataExportDataSources.toArray(data);
			    }

			    @Override
			    public String getColumnsValue(DataExportDataSource dataSource, String columnKey) throws Exception
			    {
				Object value = dataSource.get(columnKey);
				return value == null ? "" : String.valueOf(value);
			    }
			};
			
			DataExportAsyncThread.inst.add(dataExportTask);
			mav.addObject("id",dataExportTaskBean.getId());
			return mav;
		 }
		/**
		 * 利息转出审核下载
		 * @param request
		 * @param response
		 * @throws Exception 
		 */
		@RequestMapping(value="/interrestOutCommonDownload",method={RequestMethod.POST})
		public ModelAndView interrestOutCommonDownload(HttpServletRequest request,HttpServletResponse response) throws Exception{
			ModelAndView mav = new ModelAndView("downloadManage/downloadPage");
			
			final DataExportTaskBean dataExportTaskBean = DataExportContainer.newDataExportTaskBean("interrestOut", DataExportFileType.CSV);
			
			final Map<String,Object> paramMap =  new HashMap<String,Object>();
			paramMap.put("startDate", request.getParameter("startDate"));
			paramMap.put("endDate", request.getParameter("endDate"));
			paramMap.put("checkStatus", request.getParameter("checkStatus"));
			paramMap.put("trxType","4402");
			logger.info("interrestOutCommonDownload paramMap is "+paramMap);
			
			final SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			
			DataExportTask dataExportTask = new DataExportTask() {
				@Override
				public void init() throws Exception
				{
				}
				
				@Override
				public DataExportTaskBean getDataExportTaskBean()
				{
					return dataExportTaskBean;
				}
				
				@Override
				public List<DataExportColumn> getDataExportColumns()
				{
					List<DataExportColumn> list = new ArrayList<DataExportColumn>();
					list.add(new DataExportColumn("checkStatus", "审核状态"));
					list.add(new DataExportColumn("draccBankNodeCode", "付款银行"));
					list.add(new DataExportColumn("draccNo", "付款银行账号"));
					list.add(new DataExportColumn("payCurrency", "币种"));
					list.add(new DataExportColumn("payAmount", "录入金额"));
					list.add(new DataExportColumn("createTime", "创建时间"));
					return list;
					
				}
				
				List<DataExportDataSource> dataExportDataSources = new ArrayList<DataExportDataSource>();
				
				@SuppressWarnings("unchecked")
				@Override
				public DataExportDataSource[] getDataExportData(int startIdx, int endIdx) throws Exception
				{
					
					dataExportDataSources.clear();
					
					paramMap.put("start", startIdx-1);
					
					paramMap.put("end", endIdx);
					
					List<SacCheckInfo> checkInfoList = sacCheckInfoService.querySacCheckInfo(paramMap);
					List<SacOtrxInfo> sacOtrxInfoList = (List<SacOtrxInfo>) sacCheckInfoService.objectFromList(checkInfoList);
					
					for(int i = 0 ; i < sacOtrxInfoList.size(); i++){
						SacOtrxInfo info = sacOtrxInfoList.get(i);
						DataExportDataSource dataExportDataSource = new DataExportDataSource();
						
						dataExportDataSource.set("checkStatus", transformStatus(info.getMemo()));
						dataExportDataSource.set("draccBankNodeCode", info.getDraccNodeCode());
						dataExportDataSource.set("draccNo", info.getDraccNo());
						dataExportDataSource.set("payCurrency", info.getPayCurrency());
						dataExportDataSource.set("payAmount", info.getPayAmount());
						dataExportDataSource.set("createTime", sf.format(info.getCreateTime()));
						
						dataExportDataSources.add(dataExportDataSource);
					}
					
					DataExportDataSource[] data = new DataExportDataSource[dataExportDataSources.size()];
					return dataExportDataSources.toArray(data);
				}
				
				@Override
				public String getColumnsValue(DataExportDataSource dataSource, String columnKey) throws Exception
				{
					Object value = dataSource.get(columnKey);
					return value == null ? "" : String.valueOf(value);
				}
			};
			
			DataExportAsyncThread.inst.add(dataExportTask);
			mav.addObject("id",dataExportTaskBean.getId());
			return mav;
		}
		/**
		 * 利息转入审核下载
		 * @param request
		 * @param response
		 * @throws Exception 
		 */
		@RequestMapping(value="/interrestInCommonDownload",method={RequestMethod.POST})
		public ModelAndView interrestInCommonDownload(HttpServletRequest request,HttpServletResponse response) throws Exception{
			ModelAndView mav = new ModelAndView("downloadManage/downloadPage");
			
			final DataExportTaskBean dataExportTaskBean = DataExportContainer.newDataExportTaskBean("interrestIn", DataExportFileType.CSV);
			
			final Map<String,Object> paramMap =  new HashMap<String,Object>();
			paramMap.put("startDate", request.getParameter("startDate"));
			paramMap.put("endDate", request.getParameter("endDate"));
			paramMap.put("checkStatus", request.getParameter("checkStatus"));
			paramMap.put("trxType","4401");
			logger.info("interrestInCommonDownload paramMap is "+paramMap);
			
			final SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			
			DataExportTask dataExportTask = new DataExportTask() {
				@Override
				public void init() throws Exception
				{
				}
				
				@Override
				public DataExportTaskBean getDataExportTaskBean()
				{
					return dataExportTaskBean;
				}
				
				@Override
				public List<DataExportColumn> getDataExportColumns()
				{
					List<DataExportColumn> list = new ArrayList<DataExportColumn>();
					list.add(new DataExportColumn("checkStatus", "审核状态"));
					list.add(new DataExportColumn("craccBankNodeCode", "收款银行"));
					list.add(new DataExportColumn("craccNo", "收款银行账号"));
					list.add(new DataExportColumn("payCurrency", "币种"));
					list.add(new DataExportColumn("payAmount", "录入金额"));
					list.add(new DataExportColumn("createTime", "创建时间"));
					return list;
					
				}
				
				List<DataExportDataSource> dataExportDataSources = new ArrayList<DataExportDataSource>();
				
				@SuppressWarnings("unchecked")
				@Override
				public DataExportDataSource[] getDataExportData(int startIdx, int endIdx) throws Exception
				{
					
					dataExportDataSources.clear();
					
					paramMap.put("start", startIdx-1);
					
					paramMap.put("end", endIdx);
					
					List<SacCheckInfo> checkInfoList = sacCheckInfoService.querySacCheckInfo(paramMap);
					List<SacOtrxInfo> sacOtrxInfoList = (List<SacOtrxInfo>) sacCheckInfoService.objectFromList(checkInfoList);
					
					for(int i = 0 ; i < sacOtrxInfoList.size(); i++){
						SacOtrxInfo info = sacOtrxInfoList.get(i);
						DataExportDataSource dataExportDataSource = new DataExportDataSource();
						
						dataExportDataSource.set("checkStatus", transformStatus(info.getMemo()));
						dataExportDataSource.set("craccBankNodeCode", info.getCraccNodeCode());
						dataExportDataSource.set("craccNo", info.getCraccNo());
						dataExportDataSource.set("payCurrency", info.getPayCurrency());
						dataExportDataSource.set("payAmount", info.getPayAmount());
						dataExportDataSource.set("createTime", sf.format(info.getCreateTime()));
						
						dataExportDataSources.add(dataExportDataSource);
					}
					
					DataExportDataSource[] data = new DataExportDataSource[dataExportDataSources.size()];
					return dataExportDataSources.toArray(data);
				}
				
				@Override
				public String getColumnsValue(DataExportDataSource dataSource, String columnKey) throws Exception
				{
					Object value = dataSource.get(columnKey);
					return value == null ? "" : String.valueOf(value);
				}
			};
			
			DataExportAsyncThread.inst.add(dataExportTask);
			mav.addObject("id",dataExportTaskBean.getId());
			return mav;
		}
		
		/**
		 * 费用支出录入下载
		 * @param request
		 * @param response
		 * @throws Exception 
		 */
		@RequestMapping(value="/feeInCommonDownload",method={RequestMethod.POST})
		public ModelAndView feeInCommonDownload(HttpServletRequest request,HttpServletResponse response) throws Exception{
			ModelAndView mav = new ModelAndView("downloadManage/downloadPage");
			
			final DataExportTaskBean dataExportTaskBean = DataExportContainer.newDataExportTaskBean("feeIn", DataExportFileType.CSV);
			
			final Map<String,Object> paramMap =  new HashMap<String,Object>();
			paramMap.put("startDate", request.getParameter("startDate"));
			paramMap.put("endDate", request.getParameter("endDate"));
			paramMap.put("checkStatus", request.getParameter("checkStatus"));
			paramMap.put("trxType","4413");
			logger.info("feeInCommonDownload paramMap is "+paramMap);
			
			final SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			
			DataExportTask dataExportTask = new DataExportTask() {
				@Override
				public void init() throws Exception
				{
				}
				
				@Override
				public DataExportTaskBean getDataExportTaskBean()
				{
					return dataExportTaskBean;
				}
				
				@Override
				public List<DataExportColumn> getDataExportColumns()
				{
					List<DataExportColumn> list = new ArrayList<DataExportColumn>();
					list.add(new DataExportColumn("checkStatus", "审核状态"));
					list.add(new DataExportColumn("payAmount", "录入金额"));
					list.add(new DataExportColumn("draccBankNodeCode", "付款银行"));
					list.add(new DataExportColumn("draccBankName", "付款银行名称"));
					list.add(new DataExportColumn("draccCardId", "付款方组织机构号"));
					list.add(new DataExportColumn("draccCusName", "付款客户名称"));
					list.add(new DataExportColumn("createTime", "创建时间"));
					return list;
					
				}
				
				List<DataExportDataSource> dataExportDataSources = new ArrayList<DataExportDataSource>();
				
				@SuppressWarnings("unchecked")
				@Override
				public DataExportDataSource[] getDataExportData(int startIdx, int endIdx) throws Exception
				{
					
					dataExportDataSources.clear();
					
					paramMap.put("start", startIdx-1);
					
					paramMap.put("end", endIdx);
					
					List<SacCheckInfo> checkInfoList = sacCheckInfoService.querySacCheckInfo(paramMap);
					List<SacOtrxInfo> sacOtrxInfoList = (List<SacOtrxInfo>) sacCheckInfoService.objectFromList(checkInfoList);
					
					for(int i = 0 ; i < sacOtrxInfoList.size(); i++){
						SacOtrxInfo info = sacOtrxInfoList.get(i);
						DataExportDataSource dataExportDataSource = new DataExportDataSource();
						
						dataExportDataSource.set("checkStatus", transformStatus(info.getMemo()));
						dataExportDataSource.set("payAmount", info.getPayAmount());
						dataExportDataSource.set("draccBankNodeCode", info.getDraccNodeCode());
						dataExportDataSource.set("draccBankName", info.getDraccBankName());
						dataExportDataSource.set("draccCardId", info.getDraccCardId());
						dataExportDataSource.set("draccCusName", info.getDraccCusName());
						dataExportDataSource.set("createTime", sf.format(info.getCreateTime()));
						
						dataExportDataSources.add(dataExportDataSource);
					}
					
					DataExportDataSource[] data = new DataExportDataSource[dataExportDataSources.size()];
					return dataExportDataSources.toArray(data);
				}
				
				@Override
				public String getColumnsValue(DataExportDataSource dataSource, String columnKey) throws Exception
				{
					Object value = dataSource.get(columnKey);
					return value == null ? "" : String.valueOf(value);
				}
			};
			
			DataExportAsyncThread.inst.add(dataExportTask);
			mav.addObject("id",dataExportTaskBean.getId());
			return mav;
		}
		
		/**
		 * 费用结转录入下载
		 * @param request
		 * @param response
		 * @throws Exception 
		 */
		@RequestMapping(value="/feeOutCommonDownload",method={RequestMethod.POST})
		public ModelAndView feeOutCommonDownload(HttpServletRequest request,HttpServletResponse response) throws Exception{
			ModelAndView mav = new ModelAndView("downloadManage/downloadPage");
			
			final DataExportTaskBean dataExportTaskBean = DataExportContainer.newDataExportTaskBean("feeOut", DataExportFileType.CSV);
			
			final Map<String,Object> paramMap =  new HashMap<String,Object>();
			paramMap.put("startDate", request.getParameter("startDate"));
			paramMap.put("endDate", request.getParameter("endDate"));
			paramMap.put("checkStatus", request.getParameter("checkStatus"));
			paramMap.put("trxType","4414");
			logger.info("feeInCommonDownload paramMap is "+paramMap);
			
			final SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			
			DataExportTask dataExportTask = new DataExportTask() {
				@Override
				public void init() throws Exception
				{
				}
				
				@Override
				public DataExportTaskBean getDataExportTaskBean()
				{
					return dataExportTaskBean;
				}
				
				@Override
				public List<DataExportColumn> getDataExportColumns()
				{
					List<DataExportColumn> list = new ArrayList<DataExportColumn>();
					list.add(new DataExportColumn("checkStatus", "审核状态"));
					list.add(new DataExportColumn("payAmount", "结转金额"));
					list.add(new DataExportColumn("craccBankNodeCode", "收款银行"));
					list.add(new DataExportColumn("craccBankName", "收款银行名称"));
					list.add(new DataExportColumn("craccCardId", "收款方组织机构号"));
					list.add(new DataExportColumn("craccCusName", "收款客户名称"));
					list.add(new DataExportColumn("createTime", "创建时间"));
					return list;
					
				}
				
				List<DataExportDataSource> dataExportDataSources = new ArrayList<DataExportDataSource>();
				
				@SuppressWarnings("unchecked")
				@Override
				public DataExportDataSource[] getDataExportData(int startIdx, int endIdx) throws Exception
				{
					
					dataExportDataSources.clear();
					
					paramMap.put("start", startIdx-1);
					
					paramMap.put("end", endIdx);
					
					List<SacCheckInfo> checkInfoList = sacCheckInfoService.querySacCheckInfo(paramMap);
					List<SacOtrxInfo> sacOtrxInfoList = (List<SacOtrxInfo>) sacCheckInfoService.objectFromList(checkInfoList);
					
					for(int i = 0 ; i < sacOtrxInfoList.size(); i++){
						SacOtrxInfo info = sacOtrxInfoList.get(i);
						DataExportDataSource dataExportDataSource = new DataExportDataSource();
						
						dataExportDataSource.set("checkStatus", transformStatus(info.getMemo()));
						dataExportDataSource.set("payAmount", info.getPayAmount());
						dataExportDataSource.set("craccBankNodeCode", info.getCraccNodeCode());
						dataExportDataSource.set("craccBankName", info.getCraccBankName());
						dataExportDataSource.set("craccCardId", info.getCraccCardId());
						dataExportDataSource.set("craccCusName", info.getCraccCusName());
						dataExportDataSource.set("createTime", sf.format(info.getCreateTime()));
						
						dataExportDataSources.add(dataExportDataSource);
					}
					
					DataExportDataSource[] data = new DataExportDataSource[dataExportDataSources.size()];
					return dataExportDataSources.toArray(data);
				}
				
				@Override
				public String getColumnsValue(DataExportDataSource dataSource, String columnKey) throws Exception
				{
					Object value = dataSource.get(columnKey);
					return value == null ? "" : String.valueOf(value);
				}
			};
			
			DataExportAsyncThread.inst.add(dataExportTask);
			mav.addObject("id",dataExportTaskBean.getId());
			return mav;
		}
		
		
		/**
		 * 下载进度查询
		 * @param request
		 * @param response
		 * @throws Exception 
		*/
		@ResponseBody
		@RequestMapping(value="/downloadingQuery",method=RequestMethod.POST)
		public void downloadingQuery(HttpServletRequest request,HttpServletResponse response) throws Exception{
			String id = request.getParameter("id");
			DataExportTaskBean dataExportTaskBean = DataExportContainer.getDataExportTaskBean(id);
			boolean complated = dataExportTaskBean.isComplated();
			if(complated){
				response.getWriter().write("{\"success\":true}"); 
			}else{
				response.getWriter().write("{\"success\":false}");
			}
		}
		
		/**
		 * 将数据下载至浏览器
		 * @param request
		 * @param response
		 * @throws Exception
		 */
		@RequestMapping(value="/downloadToBrowser",method=RequestMethod.GET)
		public void downloadToBrowser(HttpServletRequest request,HttpServletResponse response) throws Exception{
			String id = request.getParameter("id");
			DataExportTaskBean dataExportTaskBean = DataExportContainer.getDataExportTaskBean(id);
			DownLoadUtil.download(request, response, dataExportTaskBean.getDataExportAbsolute(), "");
		}
		public String transformStatus(String checkStatus){
			String checkName = "";
			if("1".equals(checkStatus)){
				checkName = "审核完成";
			}else if("2".equals(checkStatus)){
				checkName = "待审核";
			}else if("3".equals(checkStatus)){
				checkName = "审核不通过";
			}else{
				checkName = checkStatus;
			}
			return checkName;
		}
}
