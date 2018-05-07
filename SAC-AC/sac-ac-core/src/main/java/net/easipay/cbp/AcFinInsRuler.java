/**
 * 
 */
package net.easipay.cbp;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import net.easipay.cbp.constant.SacConstants;
import net.easipay.cbp.exception.AcException;
import net.easipay.cbp.model.SacChannelParam;
import net.easipay.cbp.model.SacFinInsRule;
import net.easipay.cbp.model.SacOtrxInfo;
import net.easipay.cbp.service.ISacTransService;
import net.easipay.cbp.util.StringReplaceUtil;
import net.easipay.dsfc.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @Description: TODO(用一句话描述该文件做什么)
 * @author dell (Cyrus)
 * @date 2015-7-14 下午04:34:41
 * @version V1.0
 * @jdk v1.6
 * @tomcat v7.0
 */

@Component
public class AcFinInsRuler
{
    private static ISacTransService sacTransService;
    
    //专项业务对应正常业务
    private static Map<String,String> bussSpecialAccountKeys = new HashMap<String, String>();
    
    public AcFinInsRuler()
    {
	//21 航付通专项 EIR放箱  --  20 航付通
	bussSpecialAccountKeys.put("21", "20");
	//22 航付通专项 放箱打单费  --  20 航付通
	bussSpecialAccountKeys.put("22", "20");
    }
    
    
    @Autowired
    @Qualifier("sacTransService")
    public void setSacTransService(ISacTransService sacTransService)
    {
	AcFinInsRuler.sacTransService = sacTransService;
    }
    
    /**
     * 根据参数模板生成参数列表 
     * @param sacOtrxInfo
     * @param rule
     * @return
     */
    public static String generateFinInsRuleParams(SacOtrxInfo sacOtrxInfo, SacFinInsRule rule)
    {
    	
    	String paramsTemp = rule.getParamsTemp();
		String creditRule = rule.getCreditRule();
		String debitRule = rule.getDebitRule();
		String creditTemp = rule.getCreditTemp();
		String debitTemp = rule.getDebitTemp();
		String currencyRule = rule.getCurrencyRule();
		
		
		if(StringUtils.isBlank(paramsTemp)||StringUtils.isBlank(creditRule)
				||StringUtils.isBlank(debitRule)||StringUtils.isBlank(creditTemp)
				||StringUtils.isBlank(debitTemp)||StringUtils.isBlank(currencyRule)){
			throw new RuntimeException("交易代码："+rule.getTrxCode()+"记账规则配置有误!");
		}
		String[] paramsArr = paramsTemp.split("\\|");
		
		String[] creditRuleArr = creditRule.split(";");
		String[] debitRuleArr = debitRule.split(";");
		
		String[] creditTempArr = creditTemp.split(";");
		String[] debitTempArr = debitTemp.split(";");
		
		String[] ccyArr = currencyRule.split("\\|");
		
		if(creditRuleArr.length!=creditTempArr.length||debitRuleArr.length!=debitTempArr.length){
			throw new RuntimeException("交易代码："+rule.getTrxCode()+"记账规则配置有误!");
		}
		
		if(paramsArr.length!=2||ccyArr.length!=2){
			throw new RuntimeException("交易代码："+rule.getTrxCode()+"记账规则配置有误!");
		}else{
			String dString = paramsArr[0];//借方
			String cString = paramsArr[1];//贷方
			
			String ccyDString = ccyArr[0];
			String ccyCString = ccyArr[1];
			
			
			String[] dparam = dString.split(";");
			String[] cparam = cString.split(";");
			
			String[] ccyD = ccyDString.split(";");
			String[] ccyC = ccyCString.split(";");
			
			if(dparam.length!=ccyD.length||cparam.length!=ccyC.length){
				throw new RuntimeException("交易代码："+rule.getTrxCode()+"记账规则配置有误!");
			}
			if(dparam.length!=debitRuleArr.length||cparam.length!=creditRuleArr.length){
				throw new RuntimeException("交易代码："+rule.getTrxCode()+"记账规则配置有误!");
			}
			
			StringBuffer sb = new StringBuffer("");
			for(int i=0;i<dparam.length;i++){
				if(i!=0){
					sb.append(";");
				}
				String currencyType = accessdCurrency(ccyD[i], sacOtrxInfo);
				String currencyNumberType = AcGenerator.transformCurrencyNumberType(currencyType);
				String amount = accessdAmount(ccyD[i], sacOtrxInfo);
				sb.append(StringReplaceUtil.replaceAll(dparam[i], "%s", 
                	    accessdBillingCode(debitTempArr[i], debitRuleArr[i], ccyD[i], currencyType, "", sacOtrxInfo,1), 
                	    accessdCusName(debitRuleArr[i], currencyType, "", sacOtrxInfo), 
                	    currencyNumberType, 
                	    amount));
			}
			sb.append("|");
			for(int i=0;i<cparam.length;i++){
				if(i!=0){
					sb.append(";");
				}
				String currencyType = accessdCurrency(ccyC[i], sacOtrxInfo);
				String currencyNumberType = AcGenerator.transformCurrencyNumberType(currencyType);
				String amount = accessdAmount(ccyC[i], sacOtrxInfo);
				sb.append(StringReplaceUtil.replaceAll(cparam[i], "%s", 
                	    accessdBillingCode(creditTempArr[i], creditRuleArr[i], ccyC[i], currencyType, "", sacOtrxInfo,-1), 
                	    accessdCusName(creditRuleArr[i], currencyType, "", sacOtrxInfo), 
                	    currencyNumberType, 
                	    amount));
			}
			return sb.toString();
		}
			
    }

    /**
     * 根据摘要模板生成摘要 
     * @param sacOtrxInfo
     * @param rule
     * @return
     */
    public static String generateFinInsRuleDigest(SacOtrxInfo sacOtrxInfo, SacFinInsRule rule)
    {
    	int count = 0;
    	Boolean flag = false;//默认一借多贷
    	String paramsTemp = rule.getParamsTemp();
    	String currencyRule = rule.getCurrencyRule();
    	String[] paramArr = paramsTemp.split("\\|");
    	String[] paramD = paramArr[0].split(";");
    	String[] paramC = paramArr[1].split(";");
    	String[] currencyRuleArr = currencyRule.split("\\|");
    	String ccyDString = currencyRuleArr[0];
		String ccyCString = currencyRuleArr[1];
		String[] ccyD = ccyDString.split(";");
		String[] ccyC = ccyCString.split(";");
		String creditRule = rule.getCreditRule();
		String debitRule = rule.getDebitRule();
		String[] creditRuleArr = creditRule.split(";");
		String[] debitRuleArr = debitRule.split(";");
		
    	if(paramD.length>=paramC.length){
    		count = paramD.length;
    		flag = true;//多借一贷或者一借一贷
    	}else{
    		count = paramC.length;
    	}
    	
    	String digestTemp = rule.getDigestTemp();
    	if(StringUtils.isBlank(digestTemp)){
    		throw new RuntimeException("交易代码："+rule.getTrxCode()+"记账规则DigDest_Temp配置有误!");
    	}
    	
    	String[] digestTempArr = digestTemp.split("\\|");
    	if(digestTempArr.length!=count){
    		throw new RuntimeException("交易代码："+rule.getTrxCode()+"记账规则DigDest_Temp配置有误!");
    	}
    	
    	StringBuffer sb = new StringBuffer("");
    	if(flag){
    		//多借一贷或者一借一贷
    		for(int i=0;i<digestTempArr.length;i++){
    			if(i!=0){
    				sb.append("|");
    			}
    			String currency = accessdCurrency(ccyD[i], sacOtrxInfo);
				String amount = accessdAmount(ccyD[i], sacOtrxInfo);
    			sb.append(StringReplaceUtil.replaceAll(digestTempArr[i], "%s", 
        			accessdCusName(debitRuleArr[i], currency, "", sacOtrxInfo), 
        			accessdCusName(creditRuleArr[0], currency, "", sacOtrxInfo), 
        			currency, 
        			amount));
    		}
    	}else{
    		//一借多贷
    		for(int i=0;i<digestTempArr.length;i++){
    			if(i!=0){
    				sb.append("|");
    			}
    			String currency = accessdCurrency(ccyC[i], sacOtrxInfo);
				String amount = accessdAmount(ccyC[i], sacOtrxInfo);
        		sb.append(StringReplaceUtil.replaceAll(digestTempArr[i], "%s", 
        			accessdCusName(debitRuleArr[0], currency, "", sacOtrxInfo), 
        			accessdCusName(creditRuleArr[i], currency, "", sacOtrxInfo), 
        			currency, 
        			amount));
    		}
    	}
    	return sb.toString();
    }

    /**
     * 生成会计核算所需的银行代码或商户代码 
     * @param sacOtrxInfo
     * @param rule
     * @return
     */
    public static String accessdBillingCode(String temp, String codeRule, String currencyRule, String currency, String _default, SacOtrxInfo sacOtrxInfo,int direction)
    {
	String cusCode = accessdCusCode(codeRule, currency, _default, sacOtrxInfo);
	String currencyNumberType = AcGenerator.transformCurrencyNumberType(currency);
	return StringReplaceUtil.replaceAll(temp, "%s", 
		cusCode, 
		currencyNumberType, 
		accessdBussType(sacOtrxInfo, direction,codeRule));
    }

    
    /**
     * 生成会计核算所需的业务类型
     * 
     * @param sacOtrxInfo
     * @param rule
     * @return
     */
    public static String accessdBussType(SacOtrxInfo sacOtrxInfo, int direction,String codeRule)
    {
	if(sacOtrxInfo.getTrxType().length()>4){
		if("1".equals(codeRule)||"2".equals(codeRule)||"3".equals(codeRule)||"4".equals(codeRule)){
			String areaCode = "";
			if(direction == 1){
				 areaCode = sacOtrxInfo.getCraccAreaCode().substring(0, 2);
			}else{
				 areaCode = sacOtrxInfo.getDraccAreaCode().substring(0, 2);
			}
			return areaCode;
		}
	}
	
	String bussType = sacOtrxInfo.getBussType();
	if (!bussSpecialAccountKeys.containsKey(bussType)) return bussType;
	if (!"1616".equals(sacOtrxInfo.getTrxType()) && direction == 1) return bussType;
	if ("1616".equals(sacOtrxInfo.getTrxType()) && direction == -1) return bussType;
	return bussSpecialAccountKeys.get(bussType);
    }
    
    /**
     * 客户号
     * @param codeRule
     * @param currency
     * @param _default
     * @param sacOtrxInfo
     * @return
     */
    public static String accessdCusCode(String codeRule, String currency, String _default, SacOtrxInfo sacOtrxInfo)
    {
	return "1".equals(codeRule) ? getSacChannelParamChnNo(sacOtrxInfo.getDraccNodeCode(), "3", currency).getChnNo() : 
	       "2".equals(codeRule) ? getSacChannelParamChnNo(sacOtrxInfo.getCraccNodeCode(), "3", currency).getChnNo() : 
	       "3".equals(codeRule) ? AcTrxCodeRuler.getSacChannelParam(sacOtrxInfo.getDraccNodeCode(), sacOtrxInfo.getPayconType(), currency).getChnNo() : 
	       "4".equals(codeRule) ? AcTrxCodeRuler.getSacChannelParam(sacOtrxInfo.getCraccNodeCode(), sacOtrxInfo.getPayconType(), currency).getChnNo() : 
	       "5".equals(codeRule) ? sacOtrxInfo.getDraccCusCode() : 
	       "6".equals(codeRule) ? sacOtrxInfo.getCraccCusCode() :
	       "7".equals(codeRule) ? SacConstants.CUSCODE.CUSTOMS  :
	       "9".equals(codeRule) ? SacConstants.CUSCODE.CUSTOMS_EASIPAY_GD:
	       "8".equals(codeRule) ? SacConstants.CUSCODE.CUSTOMS_EASIPAY  :_default;
	       

    }
    
    
    
    /**
     * 客户号
     * @param codeRule
     * @param currencyType
     * @param _default
     * @param sacOtrxInfo
     * @return
     */
    public static String accessdCusName(String codeRule, String currencyType, String _default, SacOtrxInfo sacOtrxInfo)
    {
	return "1".equals(codeRule) ? getSacChannelParamChnNo(sacOtrxInfo.getDraccNodeCode(), "3", currencyType).getChnName() : 
	       "2".equals(codeRule) ? getSacChannelParamChnNo(sacOtrxInfo.getCraccNodeCode(), "3", currencyType).getChnName() : 
	       "3".equals(codeRule) ? sacOtrxInfo.getDraccBankName() : 
	       "4".equals(codeRule) ? sacOtrxInfo.getCraccBankName() : 
	       "5".equals(codeRule) ? sacOtrxInfo.getDraccCusName() : 
	       "6".equals(codeRule) ? sacOtrxInfo.getCraccCusName() :
	       "7".equals(codeRule) ? SacConstants.CUSCODE.CUSTOMS_NAME :
	       "9".equals(codeRule) ? SacConstants.CUSCODE.CUSTOMS_EASIPAY_NAME_GD :
	       "8".equals(codeRule) ? SacConstants.CUSCODE.CUSTOMS_EASIPAY_NAME:  _default;
    }

    /**
     * 根据规则获取币种
     * @param currencyRule
     * @param sacOtrxInfo
     * @return
     */
    public static String accessdCurrency(String currencyRule, SacOtrxInfo sacOtrxInfo)
    {
    //1支付金额；2购结汇币种；3手续费；4成本；5支付金额-手续费；6支付金额-成本；7支付金额-手续费-成本；
	return "1".equals(currencyRule) ? sacOtrxInfo.getPayCurrency().toString() : 
	       "2".equals(currencyRule) ? sacOtrxInfo.getSacCurrency().toString() :
	       "3".equals(currencyRule) ? sacOtrxInfo.getPayCurrency().toString() :
	       "4".equals(currencyRule) ? sacOtrxInfo.getPayCurrency().toString() :
	       "5".equals(currencyRule) ? sacOtrxInfo.getPayCurrency().toString() :
	       "6".equals(currencyRule) ? sacOtrxInfo.getPayCurrency().toString() :
	       "7".equals(currencyRule) ? sacOtrxInfo.getPayCurrency().toString() :
		                          sacOtrxInfo.getPayCurrency().toString();  
    }

    /**
     * 1.支付金额
     * 2.购付汇金额
     * 3.手续费
     * 4.支付金额-手续费
     * @param currencyRule
     * @param sacOtrxInfo
     * @return
     */
    public static String accessdAmount(String currencyRule, SacOtrxInfo sacOtrxInfo)
    {
        //1支付金额；2购结汇金额；3手续费；4成本；5支付金额-手续费；6支付金额-成本；7支付金额-手续费-成本；
	return "1".equals(currencyRule) ? sacOtrxInfo.getPayAmount().toString() : 
	       "2".equals(currencyRule) ? sacOtrxInfo.getSacAmount().toString() :
	       "3".equals(currencyRule) ? sacOtrxInfo.getCusCharge().toString() :
	       "4".equals(currencyRule) ? sacOtrxInfo.getChannelCost().toString() :
	       "5".equals(currencyRule) ? sacOtrxInfo.getPayAmount().subtract(sacOtrxInfo.getCusCharge()).toString() :
	       "6".equals(currencyRule) ? sacOtrxInfo.getPayAmount().subtract(sacOtrxInfo.getChannelCost()).toString() :
	       "7".equals(currencyRule) ? sacOtrxInfo.getPayAmount().subtract(sacOtrxInfo.getCusCharge()).subtract(sacOtrxInfo.getChannelCost()).toString() :
	                                  sacOtrxInfo.getPayAmount().toString();
    }
    

    public static SacChannelParam getSacChannelParamChnNo(String chnCode, String payconType, String currencyType)
    {
	SacChannelParam sacChannelParam = AcTrxCodeRuler.getSacChannelParam(chnCode, payconType, currencyType);
	
	if (!sacChannelParam.getBankNodeCode().equals(sacChannelParam.getChnCode())) {
	    sacChannelParam = AcTrxCodeRuler.getSacChannelParam(sacChannelParam.getBankNodeCode(), payconType, currencyType);
	}
	
	return sacChannelParam;
    }
    
    public static String generateFinInsRuleParamsForOther(SacOtrxInfo sacOtrxInfo, SacFinInsRule rule)
    {
	String currencyNumberType = AcGenerator.transformCurrencyNumberType(sacOtrxInfo.getPayCurrency());
	// B2C支付交易 日终渠道对账
	if ("3302".equals(rule.getTrxCode()) && rule.getStep() == 2) {
	    return StringReplaceUtil.replaceAll(rule.getParamsTemp(), "%s", 
		    accessdBillingCode("220203%s%s%s01", "6", "1", sacOtrxInfo.getPayCurrency(), "", sacOtrxInfo,1), 
		    accessdCusName("6", "1", sacOtrxInfo.getPayCurrency(), sacOtrxInfo), 
		    currencyNumberType, 
		    sacOtrxInfo.getPayAmount().toString(),
		    accessdBillingCode("220203%s%s%s02", "6", "1", sacOtrxInfo.getPayCurrency(), "", sacOtrxInfo,-1), 
		    accessdCusName("6", "1", sacOtrxInfo.getPayCurrency(), sacOtrxInfo), 
		    currencyNumberType, 
		    sacOtrxInfo.getPayAmount().subtract(sacOtrxInfo.getTaxAmount()).subtract(sacOtrxInfo.getCusCharge()).toString(),
		    accessdBillingCode("220203%s%s%s03", "6", "1", sacOtrxInfo.getPayCurrency(), "", sacOtrxInfo,-1), 
		    accessdCusName("6", "1", sacOtrxInfo.getPayCurrency(), sacOtrxInfo), 
		    currencyNumberType, 
		    sacOtrxInfo.getTaxAmount().toString(),
		    accessdBillingCode("220201%s%s%s02", "8", "1", sacOtrxInfo.getPayCurrency(), "", sacOtrxInfo,-1), 
		    accessdCusName("8", "1", sacOtrxInfo.getPayCurrency(), sacOtrxInfo), 
		    currencyNumberType,
		    sacOtrxInfo.getCusCharge().toString());
	}
	
	//行邮税实缴
	if("3204".equals(rule.getTrxCode()) && rule.getStep() == 1){
	    SacOtrxInfo oSacOtrxInfo = sacTransService.getSacOtrxInfo(sacOtrxInfo.getOtrxSerialNo());
	    if(oSacOtrxInfo == null || oSacOtrxInfo.getTaxAmount() == null){
		throw new AcException(SacConstants.RTN_FAIL, "原始交易异常");
	    }
	    BigDecimal subtract = sacOtrxInfo.getPayAmount().subtract(oSacOtrxInfo.getTaxAmount());
	    if( subtract.compareTo(new BigDecimal(0)) > 0 ){
		    return StringReplaceUtil.replaceAll("%s,%s,%s,%s;%s,%s,%s,%s|%s,%s,%s,%s", "%s", 
			    accessdBillingCode("220203%s%s%s02", "5", "1", sacOtrxInfo.getPayCurrency(), "", sacOtrxInfo,1), 
			    accessdCusName("5", "1", sacOtrxInfo.getPayCurrency(), sacOtrxInfo), 
			    currencyNumberType, 
			    subtract.toString(),
			    accessdBillingCode("220203%s%s%s03", "5", "1", sacOtrxInfo.getPayCurrency(), "", sacOtrxInfo,1), 
			    accessdCusName("5", "1", sacOtrxInfo.getPayCurrency(), sacOtrxInfo), 
			    currencyNumberType, 
			    oSacOtrxInfo.getTaxAmount().toString(),
			    accessdBillingCode("220203%s%s%s02", "0", "1", sacOtrxInfo.getPayCurrency(), SacConstants.CUSCODE.CUSTOMS, sacOtrxInfo,-1), 
			    SacConstants.CUSCODE.CUSTOMS_NAME, 
			    currencyNumberType, 
			    sacOtrxInfo.getPayAmount().toString());
	    } else if ( subtract.compareTo(new BigDecimal(0)) < 0 ){
		    return StringReplaceUtil.replaceAll("%s,%s,%s,%s|%s,%s,%s,%s;%s,%s,%s,%s", "%s", 
			    accessdBillingCode("220203%s%s%s03", "5", "1", sacOtrxInfo.getPayCurrency(), "", sacOtrxInfo,1), 
			    accessdCusName("5", "1", sacOtrxInfo.getPayCurrency(), sacOtrxInfo), 
			    currencyNumberType, 
			    oSacOtrxInfo.getTaxAmount().toString(),
			    accessdBillingCode("220203%s%s%s02", "5", "1", sacOtrxInfo.getPayCurrency(), "", sacOtrxInfo,-1), 
			    accessdCusName("5", "1", sacOtrxInfo.getPayCurrency(), sacOtrxInfo),
			    currencyNumberType, 
			    subtract.abs().toString(),
			    accessdBillingCode("220203%s%s%s02", "0", "1", sacOtrxInfo.getPayCurrency(), SacConstants.CUSCODE.CUSTOMS, sacOtrxInfo,-1), 
			    SacConstants.CUSCODE.CUSTOMS_NAME, 
			    currencyNumberType, 
			    sacOtrxInfo.getPayAmount().toString());
	    } else {
		    return StringReplaceUtil.replaceAll("%s,%s,%s,%s|%s,%s,%s,%s", "%s", 
			    accessdBillingCode("220203%s%s%s03", "5", "1", sacOtrxInfo.getPayCurrency(), "", sacOtrxInfo,1), 
			    accessdCusName("5", "1", sacOtrxInfo.getPayCurrency(), sacOtrxInfo), 
			    currencyNumberType, 
			    sacOtrxInfo.getPayAmount().toString(),
			    accessdBillingCode("220203%s%s%s02", "0", "1", sacOtrxInfo.getPayCurrency(), SacConstants.CUSCODE.CUSTOMS, sacOtrxInfo,-1), 
			    SacConstants.CUSCODE.CUSTOMS_NAME, 
			    currencyNumberType, 
			    sacOtrxInfo.getPayAmount().toString());
	    }

	    
	}
	
	return "";
    }

    public static String generateFinInsRuleDigestForOther(SacOtrxInfo sacOtrxInfo, SacFinInsRule rule)
    {
	// B2C支付交易 日终渠道对账
	if ("3302".equals(rule.getTrxCode()) && rule.getStep() == 2) {
	    return StringReplaceUtil.replaceAll(rule.getDigestTemp(), "%s", 
		    sacOtrxInfo.getDraccCusName(), 
		    sacOtrxInfo.getCraccCusName(), 
		    sacOtrxInfo.getPayCurrency(), 
		    sacOtrxInfo.getPayAmount().subtract(sacOtrxInfo.getTaxAmount()).toString(), 
		    sacOtrxInfo.getTaxAmount().toString(),
		    sacOtrxInfo.getTransportExpenses().toString(),
		    sacOtrxInfo.getCusCharge().toString());
	}
	
	
	//行邮税实缴
	if("3204".equals(rule.getTrxCode()) && rule.getStep() == 1){
	    SacOtrxInfo oSacOtrxInfo = sacTransService.getSacOtrxInfo(sacOtrxInfo.getOtrxSerialNo());
	    if(oSacOtrxInfo == null || oSacOtrxInfo.getTaxAmount() == null){
		throw new AcException(SacConstants.RTN_FAIL, "原始交易异常");
	    }
	    return StringReplaceUtil.replaceAll(rule.getDigestTemp(), "%s", 
		    sacOtrxInfo.getDraccCusName(), 
		    sacOtrxInfo.getCraccCusName(), 
		    sacOtrxInfo.getPayCurrency(), 
		    sacOtrxInfo.getPayAmount().toString(),
		    sacOtrxInfo.getPayCurrency(), 
		    oSacOtrxInfo.getTaxAmount().toString());
	}
	
	return "";
    }
    
    /**
     * 冲正记账 交换借贷方
     * @param params
     * @return
     */
    public static String changeCorrectParams(String params){
	String[] split = params.split("\\|");
	return split[1] + "|" + split[0];
    }
    
    
    
    
}
