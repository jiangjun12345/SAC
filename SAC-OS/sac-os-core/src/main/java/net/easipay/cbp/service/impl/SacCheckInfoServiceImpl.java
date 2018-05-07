package net.easipay.cbp.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.easipay.cbp.cache.CacheUtil;
import net.easipay.cbp.dao.ISacCheckInfoDao;
import net.easipay.cbp.model.SacCheckInfo;
import net.easipay.cbp.model.SacOtrxInfo;
import net.easipay.cbp.sequence.SequenceCreatorUtil;
import net.easipay.cbp.service.ISacCheckInfoService;
import net.easipay.cbp.util.BeanUtils;
import net.easipay.cbp.util.ConstantParams;
import net.easipay.cbp.util.StringUtil;
import net.sf.json.JSONObject;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SacCheckInfoService")
@SuppressWarnings("rawtypes")
public class SacCheckInfoServiceImpl implements ISacCheckInfoService {

	@Autowired
	private ISacCheckInfoDao sacCheckInfoDao;
	@Override
	public List<SacCheckInfo> querySacCheckInfo(SacCheckInfo sacCheckInfo, int pageNo, int pageSize) {
		int start = (pageNo-1)*pageSize;
		int end = pageNo*pageSize;
		Map<String,Object> paramMap = BeanUtils.transBean2Map(sacCheckInfo);		
		paramMap.put("start", start);
		paramMap.put("end", end);
		return sacCheckInfoDao.querySacCheckInfo(paramMap);
	}

	@Override
	public int getCheckInfoCount(SacCheckInfo sacCheckInfo) {
		Map<String,Object> paramMap = BeanUtils.transBean2Map(sacCheckInfo);
		return (Integer)sacCheckInfoDao.getCheckInfoCount(paramMap);
	}

	@Override
	public SacCheckInfo selectSacCheckInfoById(Long id) {
		return sacCheckInfoDao.selectSacCheckInfoById(id);
	}

	@Override
	public void insertSacCheckInfo(SacCheckInfo sacCheckInfo) {
		sacCheckInfo.setId(SequenceCreatorUtil.getTableId());
		sacCheckInfoDao.insertSacCheckInfo(sacCheckInfo);
	}

	@Override
	public void updateSacCheckInfo(SacCheckInfo sacCheckInfo) {
		sacCheckInfoDao.updateSacCheckInfo(sacCheckInfo);
		
	}
	@Override
	public String objectFromMap(Map map)throws Exception{
		ObjectMapper objectMapper = new ObjectMapper();	
			String writeValueAsString = objectMapper.writeValueAsString(map);
			return writeValueAsString;
		
		/*if(map!=null&&("4411".equals(map.get("trxType"))||"4412".equals(map.get("trxType")))){
			stringBuffer.append(map.get("craccBankNodeCode")).append(Constants.NULL_REPLANCE)
			.append(map.get("craccNo")).append(Constants.NULL_REPLANCE)
			.append(map.get("payAmount")).append(Constants.NULL_REPLANCE)
			.append(map.get("craccCardId")).append(Constants.NULL_REPLANCE)
			.append(map.get("bussType")).append(Constants.NULL_REPLANCE); 
			System.out.println("stringBuffer="+stringBuffer);			
		}*/
		//return stringBuffer.toString();		
	}

	@Override
	public Object objectFromList(List<SacCheckInfo> sacCheckInfoList) {
	   Object object = null;
	   if(sacCheckInfoList != null && sacCheckInfoList.size()>0){
		  List<SacOtrxInfo> sacOtrxInfoList = new ArrayList<SacOtrxInfo>();		 	
		   for(SacCheckInfo sacCheckInfo : sacCheckInfoList){
			   if("4411".equals(sacCheckInfo.getTrxType())){
				   //补账转入
                   SacOtrxInfo sacOtrxInfo = new SacOtrxInfo();
                	   sacOtrxInfo.setId(sacCheckInfo.getId());
                	   JSONObject jo=JSONObject.fromObject(sacCheckInfo.getDigst());
                	   sacOtrxInfo.setCraccNodeCode(jo.getString("craccBankNodeCode"));
                	   sacOtrxInfo.setCraccNo(jo.getString("craccNo"));
                	   sacOtrxInfo.setPayAmount(new BigDecimal(jo.getString("payAmount")));
                	   sacOtrxInfo.setCraccCardId(jo.getString("craccCardId"));
                	   sacOtrxInfo.setPayCurrency(jo.containsKey("ccy") ? jo.getString("ccy"):"");
                	   sacOtrxInfo.setCreateTime(sacCheckInfo.getCreateTime());
                	   Map<String, Object> bussTypeToMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_BUSS_TYPE);
                	   String bussType = jo.getString("bussType");
                	   String bussTypeDesc = "";
                	   bussTypeDesc = (String)bussTypeToMap.get(bussType);
                	   sacOtrxInfo.setBussType(bussType);
                	   sacOtrxInfo.setTrxErrDealType(bussTypeDesc);//业务类型中文
                	   sacOtrxInfo.setTrxType(jo.getString("trxType"));
                	   sacOtrxInfo.setMemo(sacCheckInfo.getCheckStatus());
                	   
                	   if (sacCheckInfo.getDigst().contains("draccNo")) {
                		   sacOtrxInfo.setDraccNo(jo.getString("draccNo"));
					}
                	   if (sacCheckInfo.getDigst().contains("etrxSerialNo")) {
                		   sacOtrxInfo.setEtrxSerialNo(jo.getString("etrxSerialNo"));
					}
                	   sacOtrxInfoList.add(sacOtrxInfo);
                   }
			   if("4412".equals(sacCheckInfo.getTrxType())){
				   //补账转出
                   SacOtrxInfo sacOtrxInfo = new SacOtrxInfo();
                	   sacOtrxInfo.setId(sacCheckInfo.getId());
                	   JSONObject jo=JSONObject.fromObject(sacCheckInfo.getDigst());
                	   sacOtrxInfo.setDraccNodeCode(jo.getString("draccBankNodeCode"));
                	   sacOtrxInfo.setDraccNo(jo.getString("draccNo"));
                	   sacOtrxInfo.setPayAmount(new BigDecimal(jo.getString("payAmount")));
                	   sacOtrxInfo.setDraccCardId(jo.getString("draccCardId"));
                	   sacOtrxInfo.setPayCurrency(jo.containsKey("ccy") ? jo.getString("ccy"):"");
                	   sacOtrxInfo.setCreateTime(sacCheckInfo.getCreateTime());
                	   Map<String, Object> bussTypeToMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_BUSS_TYPE);
                	   String bussType = jo.getString("bussType");
                	   String bussTypeDesc = "";
                	   bussTypeDesc = (String)bussTypeToMap.get(bussType);
                	   sacOtrxInfo.setBussType(bussType);
                	   sacOtrxInfo.setTrxErrDealType(bussTypeDesc);//业务类型中文
                	   sacOtrxInfo.setTrxType(jo.getString("trxType"));
                	   sacOtrxInfo.setMemo(sacCheckInfo.getCheckStatus());
                	   
                	   if (sacCheckInfo.getDigst().contains("craccNo")) {
                		   sacOtrxInfo.setCraccNo(jo.getString("craccNo"));
					}
                	   if (sacCheckInfo.getDigst().contains("etrxSerialNo")) {
                		   sacOtrxInfo.setEtrxSerialNo(jo.getString("etrxSerialNo"));
					}
                	   sacOtrxInfoList.add(sacOtrxInfo);
                   }
			   if("1612".equals(sacCheckInfo.getTrxType())){	
				   //线下出款
                   SacOtrxInfo sacOtrxInfo = new SacOtrxInfo();
                	   sacOtrxInfo.setId(sacCheckInfo.getId());
                	   JSONObject jo=JSONObject.fromObject(sacCheckInfo.getDigst());
                	   sacOtrxInfo.setDraccCusCode(jo.getString("cusNo"));
                	   sacOtrxInfo.setDraccCusName(jo.getString("cusName"));
                	   sacOtrxInfo.setDraccNo(jo.getString("cusPlatAcc"));
                	   sacOtrxInfo.setPayCurrency(jo.getString("payCurrency"));
                	   sacOtrxInfo.setDraccNodeCode(jo.getString("draccNodeCode"));
                	   sacOtrxInfo.setDraccAreaCode(jo.containsKey("draccAreaCode") ? jo.getString("draccAreaCode"):"000000");
                	   sacOtrxInfo.setDraccBankName(jo.containsKey("draccBankName") ? jo.getString("draccBankName"):jo.getString("draccNodeCodeText"));
                	   sacOtrxInfo.setCraccNodeCode(jo.containsKey("craccNodeCode") ? jo.getString("craccNodeCode"):"");
                	   sacOtrxInfo.setCraccBankName(jo.containsKey("craccBankName") ? jo.getString("craccBankName"):"");
                	   sacOtrxInfo.setPayAmount(new BigDecimal(jo.getString("payAmount")));
                	   sacOtrxInfo.setDraccCardId(jo.getString("orgCardId"));
                	   sacOtrxInfo.setBussType(jo.getString("bussType"));
                	   
                	   if (sacCheckInfo.getDigst().contains("craccNo")) {
                		   sacOtrxInfo.setCraccNo(jo.getString("craccNo"));
					}
                	   if (sacCheckInfo.getDigst().contains("etrxSerialNo")) {
                		   sacOtrxInfo.setEtrxSerialNo(jo.getString("etrxSerialNo"));
					}
                	   
                	   
                	   sacOtrxInfo.setMemo(sacCheckInfo.getCheckStatus());
                	   sacOtrxInfoList.add(sacOtrxInfo);
                   }
			   if("4401".equals(sacCheckInfo.getTrxType())){
				   //利息转入
                   SacOtrxInfo sacOtrxInfo = new SacOtrxInfo();
                	   sacOtrxInfo.setId(sacCheckInfo.getId());
                	   JSONObject jo=JSONObject.fromObject(sacCheckInfo.getDigst());
                	   sacOtrxInfo.setCraccNodeCode(jo.getString("craccBankNodeCode"));
                	   sacOtrxInfo.setCraccNo(jo.getString("craccNo"));
                	   sacOtrxInfo.setPayAmount(new BigDecimal(jo.getString("payAmount")));
                	   sacOtrxInfo.setMemo(sacCheckInfo.getCheckStatus());
                	   sacOtrxInfo.setCreateTime(sacCheckInfo.getCreateTime());
                	   Map<String, Object> bussTypeToMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_CCY);
                	   String ccyName = "";
                	   String ccy = jo.getString("payCurrency");
                	   if(StringUtil.isNotBlank(ccy)){
                		   ccyName = (String)bussTypeToMap.get(ccy);
                	   }
                	   sacOtrxInfo.setPayCurrency(ccyName);
                	   sacOtrxInfoList.add(sacOtrxInfo);
                   }
			   if("4402".equals(sacCheckInfo.getTrxType())){
				   //利息转出
                   SacOtrxInfo sacOtrxInfo = new SacOtrxInfo();
                	   sacOtrxInfo.setId(sacCheckInfo.getId());
                	   JSONObject jo=JSONObject.fromObject(sacCheckInfo.getDigst());
                	   sacOtrxInfo.setDraccNodeCode(jo.getString("draccBankNodeCode"));
                	   sacOtrxInfo.setDraccNo(jo.getString("draccNo"));
                	   sacOtrxInfo.setPayAmount(new BigDecimal(jo.getString("payAmount")));
                	   sacOtrxInfo.setMemo(sacCheckInfo.getCheckStatus());
                	   sacOtrxInfo.setCreateTime(sacCheckInfo.getCreateTime());
                	   Map<String, Object> bussTypeToMap = CacheUtil.getCacheByTypeToMap(ConstantParams.SAC_CCY);
                	   String ccyName = "";
                	   String ccy = jo.getString("payCurrency");
                	   if(StringUtil.isNotBlank(ccy)){
                		   ccyName = (String)bussTypeToMap.get(ccy);
                	   }
                	   sacOtrxInfo.setPayCurrency(ccyName);
                	   sacOtrxInfoList.add(sacOtrxInfo);
                   }
			   
			   if("4413".equals(sacCheckInfo.getTrxType())){	
				   //费用支出录入
                   SacOtrxInfo sacOtrxInfo = new SacOtrxInfo();
                	   sacOtrxInfo.setId(sacCheckInfo.getId());
                	   JSONObject jo=JSONObject.fromObject(sacCheckInfo.getDigst());
                	   sacOtrxInfo.setDraccNodeCode(jo.getString("draccBankNodeCode"));
                	   sacOtrxInfo.setDraccBankName(jo.getString("draccBankName"));
                	   sacOtrxInfo.setPayAmount(new BigDecimal(jo.getString("payAmount")));
                	   sacOtrxInfo.setDraccCusName("东方电子支付有限公司");
                	   sacOtrxInfo.setDraccCardId("674612978");
                	   sacOtrxInfo.setMemo(sacCheckInfo.getCheckStatus());
                	   sacOtrxInfo.setCreateTime(sacCheckInfo.getCreateTime());
                	   sacOtrxInfoList.add(sacOtrxInfo);
               }
			   if("4414".equals(sacCheckInfo.getTrxType())){	
				   //费用支出结转
				   SacOtrxInfo sacOtrxInfo = new SacOtrxInfo();
            	   sacOtrxInfo.setId(sacCheckInfo.getId());
            	   JSONObject jo=JSONObject.fromObject(sacCheckInfo.getDigst());
            	   sacOtrxInfo.setCraccNodeCode(jo.getString("craccBankNodeCode"));
            	   sacOtrxInfo.setCraccBankName(jo.getString("craccBankName"));
            	   sacOtrxInfo.setPayAmount(new BigDecimal(jo.getString("payAmount")));
            	   sacOtrxInfo.setCraccCusName("东方电子支付有限公司");
            	   sacOtrxInfo.setCraccCardId("674612978");
            	   sacOtrxInfo.setMemo(sacCheckInfo.getCheckStatus());
            	   sacOtrxInfo.setCreateTime(sacCheckInfo.getCreateTime());
            	   sacOtrxInfoList.add(sacOtrxInfo);
			   }
			   if("5101".equals(sacCheckInfo.getTrxType())){	
				   //代付审核
				   SacOtrxInfo sacOtrxInfo = new SacOtrxInfo();
            	   sacOtrxInfo.setId(sacCheckInfo.getId());
            	   JSONObject jo=JSONObject.fromObject(sacCheckInfo.getDigst());
            	   sacOtrxInfo.setTrxSerialNo(jo.getString("trxSerialNo"));
            	   sacOtrxInfo.setPayAmount(new BigDecimal(jo.getString("payAmount")));
            	   sacOtrxInfo.setCusCharge(new BigDecimal(jo.getString("cusCharge")));
            	   sacOtrxInfo.setPayCurrency(jo.getString("payCurrency"));
            	   sacOtrxInfo.setDraccBankName(jo.getString("draccBankName"));
            	   sacOtrxInfo.setDraccNodeCode(jo.getString("draccNodeCode"));
            	   sacOtrxInfo.setCraccNodeCode(jo.getString("craccNodeCode"));
            	   sacOtrxInfo.setCraccCusName(jo.getString("craccCusName"));
            	   sacOtrxInfo.setDraccCardId(jo.getString("draccCardId"));
            	   sacOtrxInfo.setCreateTime(sacCheckInfo.getCreateTime());
            	   sacOtrxInfo.setMemo(sacCheckInfo.getCheckStatus());
            	   sacOtrxInfo.setTrxErrDealType(jo.containsKey("acType") ? jo.getString("acType"):"1");
            	   sacOtrxInfoList.add(sacOtrxInfo);
			   }
			   if("610341".equals(sacCheckInfo.getTrxType())){	
				   //东方付线下出款指令
                   SacOtrxInfo sacOtrxInfo = new SacOtrxInfo();
                	   sacOtrxInfo.setId(sacCheckInfo.getId());
                	   JSONObject jo=JSONObject.fromObject(sacCheckInfo.getDigst());
                	   sacOtrxInfo.setMemo(jo.getString("id"));
                	   sacOtrxInfo.setCraccCardId(jo.getString("craccCardId"));
                	   sacOtrxInfo.setCraccName(jo.getString("craccName"));
                	   sacOtrxInfo.setCraccBankName(jo.getString("craccBankName"));
                	   sacOtrxInfo.setCraccNodeCode(jo.getString("craccNodeCode"));
                	   sacOtrxInfo.setPayCurrency(jo.getString("payCurrency"));
                	   sacOtrxInfo.setPayAmount(new BigDecimal(jo.getString("payAmount")));
                	   sacOtrxInfo.setDraccNodeCode(jo.getString("draccNodeCode"));
                	   sacOtrxInfo.setDraccBankName(jo.getString("draccNodeCodeText"));
                	   sacOtrxInfo.setDraccAreaCode(jo.getString("draccAreaCode"));
                	   sacOtrxInfo.setCraccAreaCode(jo.getString("draccAreaCodeText"));//暂时借该字段存名称
                	   sacOtrxInfo.setTrxSerialNo(jo.getString("skSerialNo"));
                	   sacOtrxInfo.setOtrxSerialNo(jo.getString("ykSerialNo"));
                	   
                	   if (sacCheckInfo.getDigst().contains("craccNo")) {
                		   sacOtrxInfo.setCraccNo(jo.getString("craccNo"));
					}
                	   if (sacCheckInfo.getDigst().contains("etrxSerialNo")) {
                		   sacOtrxInfo.setEtrxSerialNo(jo.getString("etrxSerialNo"));
					}
                	   
                	   
                	   sacOtrxInfo.setTrxState(sacCheckInfo.getCheckStatus());
                	   sacOtrxInfoList.add(sacOtrxInfo);
                   }
		   }
		   return sacOtrxInfoList;
	   }
		return object;
	}
	
	   public static void main(String[] args) {
		   String aa  = "{\"payAmount\":\"293601.15\"}";
		   JSONObject jo=JSONObject.fromObject(aa.toString());
		   BigDecimal bigDecimal = new BigDecimal(jo.getString("payAmount"));
		   System.out.println(bigDecimal);
	}

	@Override
	public List<SacCheckInfo> querySacCheckInfo(Map<String, Object> queryMap) {
		return sacCheckInfoDao.querySacCheckInfo(queryMap);
	}
	 


	
}
