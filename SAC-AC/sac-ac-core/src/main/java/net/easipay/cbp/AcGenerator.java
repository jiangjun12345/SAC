/**
 * 
 */
package net.easipay.cbp;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import net.easipay.cbp.constant.SacConstants;
import net.easipay.cbp.exception.AcException;
import net.easipay.cbp.util.DigitalConversion;
import net.easipay.dsfc.StringUtils;
import net.easipay.dsfc.UnifiedConfig;
import net.easipay.dsfc.UnifiedConfigSimple;

/**
 * 
 * @author mchen
 * @date 2015-12-9
 */
public class AcGenerator
{
    public static String generateCusName(String cusName, String currencyType)
    {
	if (StringUtils.isBlank(currencyType)) throw new AcException(SacConstants.RTN_FAIL, "币种不能为空");
	
	UnifiedConfig dicCodeConfig = UnifiedConfigSimple.getDicCodeConfig(currencyType);
	
	if (dicCodeConfig == null) throw new AcException(SacConstants.RTN_FAIL, "不支持的币种" + currencyType);
	
	return cusName + dicCodeConfig.getDicDesc();
    }

    public static String transformCurrencyNumberType(String currencyType)
    {
	if (StringUtils.isBlank(currencyType)) throw new AcException(SacConstants.RTN_FAIL, "币种不能为空");
	
	UnifiedConfig dicCodeConfig = UnifiedConfigSimple.getDicCodeConfig(currencyType);
	
	if (dicCodeConfig == null) throw new AcException(SacConstants.RTN_FAIL, "不支持的币种" + currencyType);

	return dicCodeConfig.getDicValue();
    }

    public static String generateCusPlatAcc(String cusCode, String currencyType)
    {
	return "220203" + cusCode + AcGenerator.transformCurrencyNumberType(currencyType);
    }

    public static String generateCusCode(String cusType, String cusNumber)
    {
	// 企业组织编码
	if ("1".equals(cusType)) {
	    String _cusNumber = cusNumber;
	    
	    if(cusNumber.length() == 18)_cusNumber = cusNumber.substring(8, 17);
	    
	    StringBuffer sb = new StringBuffer();
	    for (char c : _cusNumber.toCharArray()) {
		if (Character.isLetter(c)) {
		    sb.append(String.valueOf(c).getBytes()[0]);
		}
		else {
		    sb.append(c);
		}
	    }
	    return String.format("%1$d%2$018d", 1, new BigDecimal(sb.toString()).toBigInteger());
	}

	// 个人身份证
	if ("2".equals(cusType)) return String.format("%1$d%2$018d", 2, new BigDecimal(cusNumber.replaceAll("X", "0")).toBigInteger());
	return "";
    }

    public static String generateChnNo(String chnType, String chnCode)
    {
	String front = SacConstants.CHN_TYPE.B2B.equals(chnType) ? "8" : 
	    	       SacConstants.CHN_TYPE.B2C.equals(chnType) ? "7" : 
	    	       SacConstants.CHN_TYPE.DSF.equals(chnType) ? "4" :
	    	       SacConstants.CHN_TYPE.WHT.equals(chnType) ? "5" : "9";
	String mid = DigitalConversion.letterToNum(chnCode.replaceAll("\\d", ""));
	String last = chnCode.replaceAll("\\D", "");
	return front + String.format("%018d", Long.valueOf(mid + last));
    }

    public static String generateUnionId(String id)
    {
	try {
	    MessageDigest mdInst = MessageDigest.getInstance("MD5");
	    mdInst.update(id.getBytes());
	    byte[] md5Bytes = mdInst.digest();
	    StringBuffer hexValue = new StringBuffer();
	    for (int i = 0; i < md5Bytes.length; i++) {
		int val = ((int) md5Bytes[i]) & 0xff;
		if (val < 16) hexValue.append("0");
		hexValue.append(Integer.toHexString(val));
	    }
	    return hexValue.toString();
	} catch ( NoSuchAlgorithmException e ) {
	    throw new AcException(SacConstants.RTN_FAIL, e.getMessage());
	}
    }
    
}
