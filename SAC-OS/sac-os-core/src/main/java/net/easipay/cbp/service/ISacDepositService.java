package net.easipay.cbp.service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.easipay.cbp.cas.users.SecurityOperator;
import net.easipay.cbp.model.SacDepositBatch;
import net.easipay.cbp.model.SacDepositDetail;
import net.easipay.cbp.model.form.PrestoreDetailForm;


/**
 * 
* ClassName: ISacDepositBatchService <br/> 
* Function: TODO ADD FUNCTION. <br/> 
* Reason: TODO ADD REASON(可选). <br/> 
* date: 2016年2月26日 下午5:01:03 <br/> 
* 
* @author Administrator 
* @version  
* @since JDK 1.6
 */
public interface ISacDepositService
{

	public String deal(String serialNoListStr, List<PrestoreDetailForm> list)throws Exception ;
	
	public List<SacDepositDetail> findDepositDetailByParam(Map<String,Object> queryMap) ;
	
	public List<SacDepositDetail> findDepositDetailByParamForValid(Map<String,Object> queryMap) ;
	
	public void saveDetail(SacDepositDetail detail) ;

	public int validRepeat(String bankSerialNo);
	
	public int getBatchCountsByParam(Map<String,Object> queryMap);

	public List<SacDepositBatch> getDepositBatchByParam(
			Map<String,Object> queryMap, int pageNo, int pageSize);

	public void passConfirm(SacDepositDetail detail,SacDepositBatch batch,int countDff,BigDecimal amountDff);

	public void passFailue(SacDepositDetail detail);

	public void updateDepositBatch(SacDepositBatch batch);

	public Integer getDepositDetailCountsByParam(Map<String, Object> queryMap);

	public List<SacDepositDetail> getDepositDetailByPaging(
			Map<String, Object> queryMap, int pageNo, int pageSize);

	public void updateDepositDetail(SacDepositDetail detail);

	public Integer getMunualMatchCheckCounts(Map<String, Object> queryMap);

	public List<Map<String, Object>> getMunualMatchCheckInfo(
			Map<String, Object> queryMap, int pageNo, int pageSize);

	public String checkPassForManualMatch(SacDepositDetail detail);

	public void checkFailueForManualMatch(SacDepositDetail detail);

	public String spdbBatchMake(String str)throws Exception;

	public void spdbCheckFailue(SacDepositBatch bt);
	
	public void exportPrestoreDetailToExcel(HttpServletResponse response,
			Map<String, Object> queryMap, List<SacDepositDetail> detailList, String tempFile) throws IOException;

	public Object[] readOflXls(String oflExcelFileName,
			InputStream inputStream, SecurityOperator person) throws Exception;
	
}
