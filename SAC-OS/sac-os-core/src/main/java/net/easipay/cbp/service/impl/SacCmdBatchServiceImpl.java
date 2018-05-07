package net.easipay.cbp.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import net.easipay.cbp.cas.users.SecurityOperator;
import net.easipay.cbp.constant.EnumConstants.FundGiveOLConstants.CmdBatchState;
import net.easipay.cbp.constant.EnumConstants.FundGiveOLConstants.CmdState;
import net.easipay.cbp.dao.ISacCmdBatchDao;
import net.easipay.cbp.dao.ISacCommandDao;
import net.easipay.cbp.dao.impl.SacCommandDaoImpl;
import net.easipay.cbp.exception.SacException;
import net.easipay.cbp.model.SacB2BCommand;
import net.easipay.cbp.model.SacB2bCmdBatch;
import net.easipay.cbp.service.ICusBalanceService;
import net.easipay.cbp.service.INotifyOperResultToB2BService;
import net.easipay.cbp.service.ISacChannelParamService;
import net.easipay.cbp.service.ISacCmdBatchService;
import net.easipay.cbp.service.ISacCommandService;
import net.easipay.cbp.util.CurrencyUtil;
import net.easipay.cbp.util.PersonUtil;
import net.easipay.cbp.util.XlsExporter;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sacCmdBatchService")
public class SacCmdBatchServiceImpl implements ISacCmdBatchService
{

	public static final Logger logger = Logger.getLogger(SacCmdBatchServiceImpl.class);

	@Autowired
	private ISacCmdBatchDao sacCmdBatchDao;
	@Autowired
	private ISacChannelParamService sacChannelParamService;
	@Autowired
	private ISacCommandService sacCommandService;
	@Autowired
	private ICusBalanceService cusBalanceService;
	@Autowired
	private ISacCommandDao sacCommandDao;
	@Autowired
	private INotifyOperResultToB2BService notifyOperResultToB2BService;
	
	
	
	@Override
	public Integer getCmdBatchCounts(Map<String, Object> queryMap) {
		return sacCmdBatchDao.getCmdBatchCounts(queryMap);
	}

	@Override
	public List<SacB2bCmdBatch> getCmdBatchByPaging(
			Map<String, Object> queryMap, int pageNo, int pageSize) {
		int start = (pageNo-1)*pageSize;
		int end = pageNo*pageSize;
		queryMap.put("start", start);
		queryMap.put("end", end);
		return sacCmdBatchDao.getCmdBatchByPaging(queryMap);
	}

	@Override
	public void dealBatchPass(String batchId) {
		if(StringUtils.isBlank(batchId)){
			return;
		}
		Map<String,Object> queryMap = new HashMap<String, Object>();
		queryMap.put("cmdBatchId", batchId);
		List<SacB2bCmdBatch> batchList = getCmdBatchByPaging(queryMap, 1, Integer.MAX_VALUE);
		if(batchList==null||batchList.size()<=0){
			return;
		}
		SacB2bCmdBatch batch = batchList.get(0);
		/*//TODO 检查企业余额是否足够
		//根据batchId获取指令明细信息
		Map<String,Object> queryMap1 = new HashMap<String, Object>();
		queryMap1.put("batchSerialNo", batchId);
		List<SacB2BCommand> commandList = sacCommandService.getCommandDetailByPaging(queryMap1, 1, Integer.MAX_VALUE);
		Map<String,BigDecimal> amountMap = new HashMap<String, BigDecimal>();
		for(SacB2BCommand command : commandList){
			String crtCode = command.getCrtCode();//收款方组织机构代码
			BigDecimal payAmount = command.getPayAmount();
			BigDecimal amount = amountMap.get(crtCode);
			if(amount==null){
				amount = new BigDecimal("0.00");
			}
			amountMap.put(crtCode, amount.add(payAmount));
		}
		StringBuffer bf = new StringBuffer("");
		Set<Entry<String, BigDecimal>> entrySet = amountMap.entrySet();
		for(Entry<String, BigDecimal> entry : entrySet){
			String crtCode = entry.getKey();
			BigDecimal amount = (BigDecimal)entry.getValue();
			Boolean flag = cusBalanceService.validateCusAvalibleBal(crtCode,amount,batch.getBatchCur());
			if(!flag){
				SacB2BCommand command = new SacB2BCommand();
				command.setBatchSerialNo(batchId);
				command.setCrtCode(crtCode);
				//command.setBatchState(CmdState.TransationLack.code());//客户余额不足
				command.setBatchSerialNo(batchId);
				sacCommandDao.updateCommand(command);
				bf.append(crtCode+"|");
			}
			
		}*/
		batch.setCmdBatchId(Long.parseLong(batchId));
		batch.setBatchState(CmdBatchState.WaitingReview.code());
		SecurityOperator user = PersonUtil.getUser();
		batch.setOperName(user.getEmail());
		batch.setOperTime(new Date());
		sacCmdBatchDao.update(batch);
		
		
	}
	@Override
	public void dealBatchCancel(String batchId) {
		if(StringUtils.isBlank(batchId)){
			return;
		}
		Map<String,Object> queryMap = new HashMap<String, Object>();
		queryMap.put("cmdBatchId", batchId);
		List<SacB2bCmdBatch> batchList = getCmdBatchByPaging(queryMap, 1, Integer.MAX_VALUE);
		if(batchList==null||batchList.size()<=0){
			return;
		}
		SacB2bCmdBatch batch = batchList.get(0);
		batch.setCmdBatchId(Long.parseLong(batchId));
		batch.setBatchState(CmdBatchState.Cancel.code());
		SecurityOperator user = PersonUtil.getUser();
		batch.setOperName(user.getEmail());
		batch.setOperTime(new Date());
		sacCmdBatchDao.update(batch);
		
		
	}

	@Override
	public void checkBatchFailue(String batchId) {
		SacB2bCmdBatch batch = new SacB2bCmdBatch();
		batch.setCmdBatchId(Long.parseLong(batchId));
		batch.setBatchState(CmdBatchState.ReviewReject.code());
		SecurityOperator user = PersonUtil.getUser();
		batch.setCheckName(user.getEmail());
		batch.setCheckTime(new Date());
		sacCmdBatchDao.update(batch);
	}

	@Override
	public void checkBatchPass(String batchId) {
		Map<String,Object> queryMap = new HashMap<String, Object>();
		queryMap.put("cmdBatchId", batchId);
		List<SacB2bCmdBatch> batchList = sacCmdBatchDao.getSacB2bCmdBatch(queryMap);
		
		if(batchList==null||batchList.size()<=0){
			return;
		}
		SacB2bCmdBatch batch = batchList.get(0);
		
		batch.setCmdBatchId(Long.parseLong(batchId));
		batch.setBatchState(CmdBatchState.ReviewPass.code());
		SecurityOperator user = PersonUtil.getUser();
		batch.setCheckName(user.getEmail());
		batch.setCheckTime(new Date());
		sacCmdBatchDao.update(batch);
		
		Map<String,Object> detailQueryMap = new HashMap<String, Object>();
		detailQueryMap.put("batchSerialNo", batchId);
		detailQueryMap.put("cmdStates", "'N','F'");//待发送 交易失败
		detailQueryMap.put("start", 0);
		detailQueryMap.put("end", 999);
		
		List<SacB2BCommand> detailList = sacCommandDao.getCommandDetailByPaging(detailQueryMap);
		
		
		// 检查备付金余额是否足够
		String bankNodeCode = batch.getMsgReceiver();
		String currencyType = batch.getBatchCur();
		//currencyType = CurrencyUtil.EnglishToNumber(currencyType);
		BigDecimal payAmount = batch.getBatchAmount();
		Boolean flag = sacChannelParamService.validateBankAvalibleBal(bankNodeCode,currencyType,payAmount);
		if(flag){
			//备付金余额足够
			for(SacB2BCommand cmd : detailList){
				//调用接口
				notifyOperResultToB2BService.notifyFundGiveOnline(cmd,batch);
			}
			
		}else{
			throw new SacException("999990", "批次号为["+batchId+"]对应的备付金余额不足");
		}
		
		
	}

	@Override
	public void exportCmdBatchToExcel(HttpServletResponse response,
			Map<String, Object> queryMap, List<SacB2bCmdBatch> batchList,
			String tempFile) throws IOException {
        
        Map<String, Object> statMap = new HashMap<String, Object>();
        statMap.put("query", queryMap);
        statMap.put("cmdBatch", batchList);
        
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            // 导出数据To OutputStream
            XlsExporter.export(statMap, "cmdBatch", tempFile, stream);
            
            response.setContentType("application/vnd.ms-excel");
            // 下载文件名
            String downloadFileName = "CmdBatch"
                    + new SimpleDateFormat("yyyy-MM-dd_HHmmss").format(new Date())
                    + ".xls";
            response.addHeader("Content-Disposition", "attachment;filename="
                    + downloadFileName);
            response.addHeader("Content-Length", "" + stream.size());
            response.getOutputStream().write(stream.toByteArray());
        } catch (Exception e) {
            logger.error("Fail to download CmdBatchReport", e);
            throw new RuntimeException("Fail to download CmdBatchReport", e);
        } finally {
            stream.close();
        }
    }

	@Override
	public void exportCmdBatchToExcel(HttpServletResponse response,
			Map<String, Object> queryMap, List<SacB2bCmdBatch> batchList,
			List<SacB2BCommand> detailList, String tempFile) throws IOException {
        
        Map<String, Object> statMap = new HashMap<String, Object>();
        statMap.put("query", queryMap);
        statMap.put("cmdBatch", batchList);
        statMap.put("cmdList", detailList);
        
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            // 导出数据To OutputStream
            XlsExporter.export(statMap, "cmdBatch", tempFile, stream);
            
            response.setContentType("application/vnd.ms-excel");
            // 下载文件名
            String downloadFileName = "CmdBatch"
                    + new SimpleDateFormat("yyyy-MM-dd_HHmmss").format(new Date())
                    + ".xls";
            response.addHeader("Content-Disposition", "attachment;filename="
                    + downloadFileName);
            response.addHeader("Content-Length", "" + stream.size());
            response.getOutputStream().write(stream.toByteArray());
        } catch (Exception e) {
            logger.error("Fail to download CmdBatchReport", e);
            throw new RuntimeException("Fail to download CmdBatchReport", e);
        } finally {
            stream.close();
        }
    }
	
}
