/**
 * 
 */
package net.easipay.cbp;

import java.util.Map;

import net.easipay.cbp.cache.CacheHandleInit;
import net.easipay.cbp.constant.Exceptions;
import net.easipay.cbp.constant.SacConstants;
import net.easipay.cbp.exception.AcException;
import net.easipay.cbp.model.SacChannelParam;
import net.easipay.cbp.model.SacOtrxInfo;
import net.easipay.cbp.model.SacTrxCodeRule;
import net.easipay.cbp.model.SacTrxDetail;
import net.easipay.dsfc.cache.CacheManager;

import org.apache.commons.lang.StringUtils;

/**
 * @Description: TODO(用一句话描述该文件做什么)
 * @author dell (Cyrus)
 * @date 2015-7-14 下午04:34:41
 * @version V1.0
 * @jdk v1.6
 * @tomcat v7.0
 */

public class AcTrxCodeRuler
{

    @SuppressWarnings("unchecked")
    public static SacTrxCodeRule getSacTrxCodeRule(String trxCode)
    {
	Map<String, SacTrxCodeRule> rules = (Map<String, SacTrxCodeRule>) CacheManager.getCache(CacheHandleInit.SAC_TRX_CODE_RULE);
	SacTrxCodeRule sacTrxCodeRule = rules.get(trxCode);
	if (sacTrxCodeRule == null) {
	    throw new AcException(Exceptions.EXP_010203, String.format("Not found code rules for the transaction[%s]", trxCode));
	}
	return sacTrxCodeRule;

    }

    @SuppressWarnings("unchecked")
    public static SacChannelParam getSacChannelParam(String chnCode, String payconType, String currencyType)
    {
	Map<String, SacChannelParam> rules = (Map<String, SacChannelParam>) CacheManager.getCache(CacheHandleInit.SAC_CHANNEL_PARAM);
	SacChannelParam sacChannelParam = rules.get(chnCode + payconType + currencyType);
	if (sacChannelParam == null) {
	    throw new AcException(Exceptions.EXP_010203, String.format("Not found channel parameter data[%s,%s,%s]", chnCode, payconType, currencyType));
	}
	return sacChannelParam;

    }

    /**
     * 付款方客户号
     * 
     * @param sacOtrxInfo
     * @return
     */
    public static String generatePaymentPlatAcc(SacOtrxInfo sacOtrxInfo)
    {
	SacTrxCodeRule rule = getSacTrxCodeRule(sacOtrxInfo.getTrxType());

	if (rule.getDraccRule().equals(SacConstants.RULE.YES) && rule.getCraccRule().equals(SacConstants.RULE.YES)) {
	    return AcGenerator.generateCusPlatAcc(sacOtrxInfo.getDraccCusCode(), sacOtrxInfo.getPayCurrency());
	}
	else if (rule.getDraccRule().equals(SacConstants.RULE.NO) && rule.getCraccRule().equals(SacConstants.RULE.NO)) {
	    return AcGenerator.generateCusPlatAcc(sacOtrxInfo.getDraccCusCode(), sacOtrxInfo.getPayCurrency());
	}
	else if (SacConstants.RULE.YES.equals(rule.getDraccRule())) {
	    return AcGenerator.generateCusPlatAcc(sacOtrxInfo.getDraccCusCode(), sacOtrxInfo.getPayCurrency());
	}
	else {
	    return sacOtrxInfo.getDraccCusCode();
	}
    }

    /**
     * 收款方客户号
     * 
     * @param sacOtrxInfo
     * @return
     */
    public static String generateReceiverPlatAcc(SacOtrxInfo sacOtrxInfo)
    {
	SacTrxCodeRule rule = getSacTrxCodeRule(sacOtrxInfo.getTrxType());
	String currencyType = SacConstants.RULE.YES.equals(rule.getCurrencyRule()) ? sacOtrxInfo.getSacCurrency() : sacOtrxInfo.getPayCurrency();

	if (rule.getDraccRule().equals(SacConstants.RULE.YES) && rule.getCraccRule().equals(SacConstants.RULE.YES)) {
	    return AcGenerator.generateCusPlatAcc(sacOtrxInfo.getCraccCusCode(), currencyType);
	}
	else if (rule.getDraccRule().equals(SacConstants.RULE.NO) && rule.getCraccRule().equals(SacConstants.RULE.NO)) {
	    return AcGenerator.generateCusPlatAcc(sacOtrxInfo.getCraccCusCode(), currencyType);
	}
	else if (SacConstants.RULE.YES.equals(rule.getCraccRule())) {
	    return AcGenerator.generateCusPlatAcc(sacOtrxInfo.getCraccCusCode(), currencyType);
	}
	else {
	    return sacOtrxInfo.getCraccCusCode();
	}
    }

    /**
     * 付款方银行节点
     * 
     * @param sacOtrxInfo
     * @return
     */
    public static String generateDraccNodeCode(SacOtrxInfo sacOtrxInfo)
    {
	SacTrxCodeRule rule = getSacTrxCodeRule(sacOtrxInfo.getTrxType());

	if (SacConstants.RULE.DEFAULT.equals(rule.getDraccRule())) return sacOtrxInfo.getDraccNodeCode();

	if (!SacConstants.RULE.YES.equals(rule.getDraccRule())) return "";

	//SacChannelParam sacChannelParam = getSacChannelParam(sacOtrxInfo.getDraccNodeCode(), sacOtrxInfo.getPayconType(), sacOtrxInfo.getPayCurrency());

	return sacOtrxInfo.getDraccNodeCode();
    }

    /**
     * 收款方银行节点
     * 
     * @param sacOtrxInfo
     * @return
     */
    public static String generateCraccNodeCode(SacOtrxInfo sacOtrxInfo)
    {
	SacTrxCodeRule rule = getSacTrxCodeRule(sacOtrxInfo.getTrxType());

	if (SacConstants.RULE.DEFAULT.equals(rule.getCraccRule())) return sacOtrxInfo.getCraccNodeCode();

	if (!SacConstants.RULE.YES.equals(rule.getCraccRule())) return "";

	//String currencyType = SacConstants.RULE.YES.equals(rule.getCurrencyRule()) ? sacOtrxInfo.getSacCurrency() : sacOtrxInfo.getPayCurrency();

	//SacChannelParam sacChannelParam = getSacChannelParam(sacOtrxInfo.getDraccNodeCode(), sacOtrxInfo.getPayconType(), currencyType);

	return sacOtrxInfo.getDraccNodeCode();
    }

    /**
     * 计算交易明细表渠道号
     * 
     * @param form
     * @return
     */
    public static String generateChnNo(SacTrxDetail sacTrxDetail)
    {
	try {
	    if (StringUtils.isNotBlank(sacTrxDetail.getDraccNodeCode()) || StringUtils.isNotBlank(sacTrxDetail.getCraccNodeCode())) {
		String chnCode = StringUtils.isNotBlank(sacTrxDetail.getDraccNodeCode()) ? sacTrxDetail.getDraccNodeCode() : sacTrxDetail.getCraccNodeCode();
		if("170100".equals(sacTrxDetail.getTrxType())) chnCode  = sacTrxDetail.getCraccNodeCode();

		SacChannelParam sacChannelParam = AcTrxCodeRuler.getSacChannelParam(chnCode, sacTrxDetail.getPayconType(), sacTrxDetail.getTrxCurrencyType());
		return sacChannelParam.getChnNo();
	    }
	} catch ( Exception e ) {
	    AcLogger.error("生成交易明细表渠道号报错", e);
	    throw new AcException(Exceptions.EXP_010203, "生成交易明细表渠道号报错：" + e.getMessage());
	}
	return "";
    }

}
