package net.easipay.cbp.util;



import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.RandomStringUtils;

public abstract class Utils {

    private static DecimalFormat df;

    static {
        df = (DecimalFormat) NumberFormat.getInstance();
        df.applyPattern("##.##%");
    }

    /**
     * 格式化数字成百分比
     * 
     * @param date
     * @return
     */
    public static String formatNum(Double data) {
        synchronized (df) {
            if (data == null) {
                return null;
            }
            return df.format(data);
        }
    }

    /**
     * 生成制定位随机数字
     */
    public static String randomNumeric(int i) {
        return RandomStringUtils.randomNumeric(i);
    }

    /**
     * 生成制定位随机字母和数字
     */
    public static String randomAlphanumeric(int i) {
        return RandomStringUtils.randomAlphanumeric(i);
    }

    /**
     * 将字符串数字转化为int型数字
     * 
     * @param str被转化字符串
     * @param defValue转化失败后的默认值
     * @return int
     */
    public static int parseInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return defValue;
        }
    }

    /**
     * 将字符串数字转化为double型数字
     * 
     * @param str被转化字符串
     * @param defValue转化失败后的默认值
     * @return double
     */
    public static double parseDouble(String str, double defValue) {
        try {
            return Double.parseDouble(str);
        } catch (Exception e) {
            return defValue;
        }
    }

    /**
     * 去空格，如为null则转化为空字符串
     */
    public static String trim(String str) {
        if (str == null || "null".equalsIgnoreCase(str)) {
            return "";
        }
        return str.trim();
    }

    /**
     * 将字符串数组转化成中间用逗号分割的字符串 "'a','b','c'"
     */
    public static String getRecordIds(String[] recordIds) {
        if (recordIds == null || recordIds.length == 0) {
            return "";
        }
        if (recordIds.length == 1) {
            return recordIds[0];
        }
        StringBuffer ids = new StringBuffer();
        for (int i = 0; i < recordIds.length; i++) {
            if (i == recordIds.length - 1) {
                ids.append("'" + recordIds[i] + "'");
            } else {
                ids.append("'" + recordIds[i] + "'" + ",");
            }
        }
        return ids.toString();
    }

    public static String getManageId(String[] recordIds) {
        if (recordIds == null || recordIds.length == 0) {
            return "";
        }
        StringBuffer ids = new StringBuffer();
        for (int i = 0; i < recordIds.length; i++) {
            if (i == recordIds.length - 1) {
                ids.append("'" + recordIds[i] + "'");
            } else {
                ids.append("'" + recordIds[i] + "'" + ",");
            }
        }
        return ids.toString();
    }

    /**
     * 将字符串数组转化成中间用逗号分割的字符串 "a,b,c"
     */
    public static String getStrs(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        if (strs.length == 1) {
            return strs[0];
        }
        StringBuffer ids = new StringBuffer();
        for (int i = 0; i < strs.length; i++) {
            if (i == strs.length - 1) {
                ids.append(strs[i]);
            } else {
                ids.append(strs[i] + ",");
            }
        }
        return ids.toString();
    }

    /**
     * 处理Url与其参数的组合
     * 
     * @param url页面Url
     * @param param被加入到该Url后的参数
     * @return 一个完整的Url,包括参数
     */
    public static String dealUrl(String url, String param) {
        if (url == null || url.length() < 1) {
            return "";
        }
        url = url.trim();
        if (param == null || param.length() < 1) {
            return url;
        }
        param = param.trim();
        if (url.endsWith("?") || url.endsWith("&")) {
            url = url.substring(0, url.length() - 1);
        }
        if (url.indexOf("?") != -1) {
            if (param.charAt(0) == '&' || param.charAt(0) == '?') {
                return url + param.substring(1);
            } else {
                return url + "&" + param;
            }
        } else {
            if (param.startsWith("&") || param.startsWith("?")) {
                return url + "?" + param.substring(1);
            } else {
                return url + "?" + param;
            }
        }
    }

    /**
     * 得到指定符号前或后的字符
     */
    private static String getPreOrSufStr(String str, int action) {
        if (str == null || str.equals("")) {
            return "";
        }
        int index = -1;
        if ((index = str.indexOf(ConstantParams.SPLIT_SIGN)) != -1) {
            if (action == 0) {
                return str.substring(index + 1).trim();
            }
            return str.substring(0, index).trim();
        }
        return str;
    }

    /**
     * 得到指定符号前的字符
     */
    public static String getPreStr(String str) {
        return getPreOrSufStr(str, 1);
    }

    /**
     * 得到指定符号后的字符
     */
    public static String getSufStr(String str) {
        return getPreOrSufStr(str, 0);
    }

    /**
     * 验证EMAIL方法
     * 
     * @param str
     *            被验证的email字符串
     * @return 成功返回true 失败返回false
     */
    public static boolean isEmail(String str) {
        if (str == null) {
            return false;
        }
        str = str.trim();
        if (str.length() < 6) {
            return false;
        }
        return true;
    }

    public static boolean isEmail(String value, String expression) {

        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(value);

        return matcher.find();
    }

    /**
     * 
     * 在不足len位的数字前面自动补零
     */
    public static String getLimitLenStr(String str, int len) {
        if (str == null) {
            return "";
        }
        while (str.length() < len) {
            str = "0" + str;
        }
        return str;
    }

    /**
     * 在大于len位的STR后面自动补...
     * 
     */
    public static String getLimitLenStr(String str, int len, String replaceValue) {
        if (str == null) {
            return "";
        }
        if (replaceValue == null || "".equals(replaceValue)) {
            replaceValue = "...";
        }
        if (str.length() > len) {
            str = str.substring(0, len) + replaceValue;
        }
        return str;
    }

    /**
     * 在大于len位的STR后面自动补...
     * 
     */
    public static String getLimitLenStr1(String str, int len, String replaceValue) {
        if (str == null) {
            return "";
        }
        if (replaceValue == null || "".equals(replaceValue)) {
            replaceValue = "";
        }
        if (str.length() > len) {
            str = str.substring(0, len) + replaceValue;
        }
        return str;
    }

    /**
     * 截取报告出处 研究机构名称的前四个字
     * 
     */
    public static String getSomeString(java.lang.String str, int len) {
        if (str == null) {
            return "";
        }
        if (str.length() > len) {
            str = str.substring(0, len);
        }
        return str;
    }

    /**
     * 字符串GBK到UTF-8码的转化
     * 
     * @param inStr
     *            GBK编码的字符串
     * @return UTF-8编码的字符串
     */
    public static String wapGbkToUtf(String inStr) {
        char temChr;
        int ascInt;
        int i;
        String result = new String();
        if (inStr == null) {
            inStr = "";
        }
        for (i = 0; i < inStr.length(); i++) {
            temChr = inStr.charAt(i);
            ascInt = temChr + 0;
            if (ascInt > 255) {
                result = result + "&#x" + Integer.toHexString(ascInt) + ";";
            } else {
                result = result + temChr;
            }
        }
        return result;
    }

    /**
     * 特殊字符替换
     */
    public static String replaceStrHtml(String inStr) {
        String result = inStr;
        if (result != null && "".equals(result)) {
            result = result.replaceAll("\r\n", "<br>");
            result = result.replaceAll(" ", "&nbsp;");
        }
        return result;
    }

    /**
     * 特殊字符&替换&amp;
     */

    public static String replaceStrForWap(String inStr) {
        String result = inStr;
        if (!StringUtil.isEmptyString(inStr)) {
            result = result.replaceAll("&amp;", "&");
            result = result.replaceAll("&", "&amp;");
        }
        return result;
    }

    public static List getInform(String[] str) {

        Map map = null;
        String[] recordId = new String[str.length];
        String[] email = new String[str.length];
        String[] ctype = new String[str.length];
        String[] tel = new String[str.length];
        String[] webmaster = new String[str.length];
        String[] webuser = new String[str.length];
        String[] webLogin = new String[str.length];
        int k = 0;
        for (String element : str) {
            java.util.StringTokenizer tokenTO = new StringTokenizer(element, ",");
            int j = 0;
            while (tokenTO.hasMoreTokens()) {
                try {
                    if (j == 0) {
                        recordId[k] = tokenTO.nextToken().toString();// 客户端ID号
                    } else if (j == 1) {
                        email[k] = tokenTO.nextToken().toString();// Email地址
                    } else if (j == 2) {
                        ctype[k] = tokenTO.nextToken().toString();// 网站类型
                    } else if (j == 3) {
                        tel[k] = tokenTO.nextToken().toString();// 手机号
                    } else if (j == 4) {
                        webmaster[k] = tokenTO.nextToken().toString();// 网站主名称
                    } else if (j == 5) {
                        webuser[k] = tokenTO.nextToken().toString();// 网站主用户ID
                    } else {
                        webLogin[k] = tokenTO.nextToken().toString();// 网站主登陆ID
                    }
                    j++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            k++;
        }
        List list = new ArrayList();
        list.add(recordId);
        list.add(email);
        list.add(ctype);
        list.add(tel);
        list.add(webmaster);
        list.add(webuser);
        list.add(webLogin);
        // map.put("recordId",(String[])recordId);
        // map.put("email",(String[])email);
        // map.put("ctype",(String[])ctype);
        // map.put("tel",(String[])tel);

        return list;
    }

    /**
     * 出错的详细信息转化为字符串
     * 
     * @param e
     * @return 错误调用栈详情
     * 
     */
    public static String stringifyException(Throwable e) {
        StringWriter stm = new StringWriter();
        PrintWriter wrt = new PrintWriter(stm);
        e.printStackTrace(wrt);
        wrt.close();
        return stm.toString();
    }

    public static ClassLoader getStandardClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    public static ClassLoader getFallbackClassLoader() {
        return Utils.class.getClassLoader();
    }

    /**
     * 得到配置文件的URL
     * 
     * @param name
     * @return 配置文件URL
     * 
     */
    public static URL getConfURL(String name) {
        try {
            return getStandardClassLoader().getResource(name);
        } catch (Exception e) {
            try {
                return getFallbackClassLoader().getResource(name);
            } catch (Exception ex) {
                return null;
            }
        }
    }

    /**
     * 根据传近来的大字段,求的大字段的字符串型
     * 
     * @param byte[]
     * 
     * @return String
     */
    public static String blobChangetoStr(byte[] msgContent) {

        String newStr = "";

        if ((msgContent == null)) {
            return "";
        } else {
            try {
                newStr = new String(msgContent, 0, msgContent.length, "gb2312");
                // System.err.println("================================");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        return newStr;
    }

    /**
     * 中文转换为UTF-8编码,用于wap页面现实
     * 
     * @param Str
     *            中文字符串
     * @return UTF-8编码字符串
     */
    public static String gbkToUtf8(String Str) {
        try {
            return new String(Str.getBytes("ISO8859-1"), "GB2312");
        } catch (Exception ex) {
            return "CharSet Error!";
        }
    }

    public static String isoToGb2312(String Str) {
        try {
            return new String(Str.getBytes("ISO8859-1"), "GB2312");
        } catch (Exception ex) {
            return "";
        }
    }

    public static String Utf8Togbk(String Str) {
        try {
            return new String(Str.getBytes("GB2312"), "ISO8859-1");
        } catch (Exception ex) {
            return "CharSet Error!";
        }
    }

    // /**
    // * 参数加密
    // * @param key
    // * @param param
    // * @return
    // */
    // public static String getEncParam(String key,String param)
    // {
    // DesEncrypt des = new DesEncrypt();// 实例化一个对像
    // des.getKey(key);// 生成密匙
    // return des.getEncString(param);
    // }
    // /**
    // * 参数解密
    // * @param key
    // * @param param
    // * @return
    // */
    // public static String getDecParam(String key,String param)
    // {
    // DesEncrypt des = new DesEncrypt();// 实例化一个对像
    // des.getKey(key);// 生成密匙
    // return des.getDesString(param);
    // }
    // /*public static void main(String[] args) {
    //
    // String[] str = { "qqq,www,ddd,kk,pp,oo", "111,222,333,44,55,66" };
    // List list = Utils.getInform(str);
    //
    // String AAA = getEncParam("INXITE","001007004002");
    // System.out.println(AAA);
    //
    // String AAA1 = getDecParam("INXITE","C102447DD5906F48C9FD5E52C42657F0");
    // System.out.println(AAA1);
    //
    // String[] a = (String[]) list.get(5);
    // System.out.println(a[0] + a[1]);
    // }*/

    public static String conFmt(String date) throws Exception {
        if (date == null) {
            return "";
        }
        if (date.length() == 12) {
            return date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8) + " " + date.substring(8, 10) + ":" + date.substring(10);
        } else if (date.length() == 8) {
            return date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8);
        } else {
            return date;
        }
    }

    public static String showFloat(double dl) {
        java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();
        nf.setGroupingUsed(false);
        return nf.format(dl);
    }

    public static String getDouble(double d, int scale) {
        double result = d / 10000.0f;
        BigDecimal bg = new BigDecimal(result);
        result = bg.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        return "" + result;
    }

    public static String changeValueToHtmlTag(String value) {
        if (value == null || "".equals(value)) {
            return "&nbsp;";
        }
        value = value.replaceAll("  ", "&nbsp;&nbsp;");
        // value=value.replaceAll("<","&lt;");
        // value=value.replaceAll(">","&gt;");
        value = value.replaceAll("\n", "<br/>");
        value = value.replaceAll("\t", "&nbsp;&nbsp;&nbsp;");
        // value=value.replaceAll("\"","&quot;");
        return value;
    }

    public static String getCode(List<String> list) {
        StringBuffer code = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            String tem = list.get(i);
            if (i == list.size() - 1) {
                code.append("'" + tem + "'");
            } else {
                code.append("'" + tem + "'" + ",");
            }
        }

        String gpdm = ("".equals(code.toString())) ? "''" : code.toString();
        return gpdm;
    }

    /**
     * 过滤List中的相同元素
     * 
     * @author LeiYong
     * @param list
     * @return
     */
    public static List removeSameObject(List list) {

        Set set = new HashSet(list);
        return Arrays.asList(set.toArray());
    }

    /**
     * 判断是否为正正数<br>
     * 
     * @param value
     * @return
     */
    public static boolean isNam(String str) {
        // 数字
        // str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
        return str.matches("^[+]?(([0-9]+)(([0-9]+))?|(([0-9]+))?)$");

    }

    public static String getCharAndNumr(int length) {
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字

            if ("char".equalsIgnoreCase(charOrNum)) // 字符串
            {
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; // 取得大写字母还是小写字母
                val += (char) (choice + random.nextInt(26));
            } else if ("num".equalsIgnoreCase(charOrNum)) // 数字
            {
                val += String.valueOf(random.nextInt(10));
            }
        }

        return val;
    }
    
    /**
	 * 获取日期参数的前后N天
	 * 
	 * @param dateParam
	 * @param n
	 * @return
	 * @throws ParseException
	 */
	public static String convertDate(Date dateParam, int n)
	{
		SimpleDateFormat sdf_yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateParam);
		cal.add(Calendar.DAY_OF_MONTH, n);
		return sdf_yyyyMMdd.format(cal.getTime());
	}

}
