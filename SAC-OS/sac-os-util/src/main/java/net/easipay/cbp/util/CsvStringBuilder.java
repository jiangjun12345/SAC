package net.easipay.cbp.util;

import java.math.BigDecimal;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CsvStringBuilder {

  private StringBuilder sb = new StringBuilder();

  public CsvStringBuilder appendBlank(int num) {
    for (int i = 0; i < num; i++) {
      sb.append(",");
    }
    return this;
  }

  public CsvStringBuilder appendBlank() {
    sb.append(",");
    return this;
  }

  public CsvStringBuilder appendLine() {
    sb.append("\r\n");
    return this;
  }

  public CsvStringBuilder appendTab() {
    sb.append("\t");
    return this;
  }

  public boolean isNumeric(String str) {
    if (StringUtil.isBlank(str)) {
      return false;
    }
    Pattern pattern = Pattern.compile("[0-9]*");
    Matcher isNum = pattern.matcher(str);
    if (!isNum.matches()) {
      return false;
    }
    return true;
  }

  public CsvStringBuilder append(String str) {
    if (StringUtil.isNotBlank(str)) {
      if (str.length() > 8 && isNumeric(str)) {
        appendTab();
      } else {
        str = "\"" + str + "\"";
      }
      sb.append(str);
    }
    appendBlank();
    return this;
  }

  public CsvStringBuilder append(BigDecimal bigDecimal) {
    if (bigDecimal != null) {
      appendTab();
      sb.append(bigDecimal.toString());
    }
    appendBlank();
    return this;
  }

  public CsvStringBuilder append(Long longType) {
    if (longType != null) {
      appendTab();
      sb.append(String.valueOf(longType));
    }
    appendBlank();
    return this;
  }

  public CsvStringBuilder append(Date date) {
    if (date != null) {
      appendTab();
      sb.append(DateUtil.getFomateDate(date, "yyyy-MM-dd HH:mm:ss"));
    }
    appendBlank();
    return this;
  }

  @Override
  public String toString() {
    appendLine();
    return sb.toString();
  }

}
