package net.easipay.cbp.util;

import java.io.CharArrayWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtil {

  public static boolean isEmptyString(String arg) {
    if ((arg == null) || (arg.trim().length() <= 0) || (arg.equals("")) || (arg.toLowerCase().equals("null"))) {
      return true;
    }
    return false;
  }

  public static String dateToStr() {

    //年月日****-**-**
    DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
    String str = format.format(new Date());
    System.out.println("str:" + str);
    return str;
  }

  public static Date strToDate(String str) {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
    Date date = null;
    Timestamp ts = null;
    try {
      date = formatter.parse(str);
      ts = new Timestamp(date.getTime());
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return ts;
  }

  public static String trimStr(String str) {
    if (str == null) {
      return "";
    }
    return str;
  }

  /**
   * 检查字符串是否为空或全为空白字符
   * 
   * @param str 待检查的字符串
   * @return
   */
  public static boolean isBlank(String str) {
    if (str == null || str.trim().length() == 0) {
      return true;
    }
    return false;
  }

  // to validate the String is not null
  public static boolean isNotBlank(String str) {
    return !(isBlank(str));
  }

  /**
   * 获取以空格填充的定长字符串
   * 
   * @author wangweiguo
   * @param ori 原始字符串
   * @param totLength 目标字符串长度，负值表示前面以空格填充
   * @return
   */
  public static String appendBlank(String ori, int totLength) {
    return fixedString(ori, totLength, ' ');
  }

  /**
   * 获取以'0'填充的定长字符串
   * 
   * @author wangweiguo
   * @param ori 原始字符串
   * @param totLength 目标字符串长度，负值表示前面以'0'填充
   * @return
   */
  public static String appendZero(String ori, int totLength) {
    return fixedString(ori, totLength, '0');
  }

  /**
   * @author wangweiguo 获取定长字符串
   * @param ori 原始字符串
   * @param length 目标字符串长度,正值后部填充字符，负值前部填充填充字符
   * @param fillChar 填充字符, 限英文字符
   * @return 定长字符串
   */
  public static String fixedString(String ori, int length, char fillChar) {
    if (StringUtil.isBlank(ori)) {
      ori = " ";
    }
    byte[] b = fixedByte(ori.getBytes(), length, fillChar);
    return new String(b);
  }

  public static byte[] fixedByte(byte[] ori, int length, char fillChar) {
    int oriLength = ori.length;
    int absLength = Math.abs(length);

    if (length == 0) {
      return null;
    }

    if (oriLength == absLength) {
      return ori;
    }

    byte[] result = new byte[absLength];
    if (oriLength > absLength) {
      if (length > 0) {
        System.arraycopy(ori, 0, result, 0, absLength);
      } else {
        System.arraycopy(ori, oriLength - absLength, result, 0, absLength);
      }
      return result;
    }

    result = extChar(fillChar, absLength).getBytes();
    int index = length < 0 ? absLength - oriLength : 0;
    System.arraycopy(ori, 0, result, index, oriLength);
    return result;
  }

  private static String extChar(char s, int multi) {
    CharArrayWriter caw = new CharArrayWriter();
    for (int i = 0; i < multi; i++) {
      caw.write(s);
    }
    return caw.toString();
  }

  /**
   * 获取指定字串之前部分
   * 
   * @param input 原始字符串
   * @param token 子字符串
   * @return
   */
  public static String before(String input, String token) {
    int idx = input.toLowerCase().indexOf(token.toLowerCase());
    if (idx > 0) {
      return input.substring(0, idx);
    } else {
      return input;
    }
  }

  /**
   * 获取指定字串之后部分
   * 
   * @param input 原始字符串
   * @param token 子字符串
   * @return
   */
  public static String after(String input, String token) {
    int idx = input.toLowerCase().indexOf(token.toLowerCase());
    if (idx > 0) {
      return input.substring(idx + token.length(), input.length());
    } else {
      return input;
    }
  }

  public static void main(String[] args) {
    System.out.println(dateToStr());
    System.out.println(strToDate("20150429111111"));

  }
}
