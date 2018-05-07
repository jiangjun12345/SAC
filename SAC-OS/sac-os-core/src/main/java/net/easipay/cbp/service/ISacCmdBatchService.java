package net.easipay.cbp.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.easipay.cbp.model.SacB2BCommand;
import net.easipay.cbp.model.SacB2bCmdBatch;
import net.easipay.cbp.model.SacChargeApply;


/**
 * 
* ClassName: ISacChargeApplyService <br/> 
* Function: TODO ADD FUNCTION. <br/> 
* Reason: TODO ADD REASON(可选). <br/> 
* date: 2016年2月26日 下午5:01:03 <br/> 
* 
* @author Administrator 
* @version  
* @since JDK 1.6
 */
public interface ISacCmdBatchService
{

	public Integer getCmdBatchCounts(Map<String, Object> queryMap);

	public List<SacB2bCmdBatch> getCmdBatchByPaging(
			Map<String, Object> queryMap, int pageNo, int pageSize);

	public void dealBatchPass(String batchId);
	
	public void dealBatchCancel(String batchId);

	public void checkBatchFailue(String batchId);

	public void checkBatchPass(String batchId);

	public void exportCmdBatchToExcel(HttpServletResponse response,
			Map<String, Object> queryMap, List<SacB2bCmdBatch> batchList,
			String tplAllCmdBatchReportXls) throws IOException;

	public void exportCmdBatchToExcel(HttpServletResponse response,
			Map<String, Object> queryMap, List<SacB2bCmdBatch> batchList,
			List<SacB2BCommand> detailList, String tplPendingCmdBatchReportXls) throws IOException;

	

}
