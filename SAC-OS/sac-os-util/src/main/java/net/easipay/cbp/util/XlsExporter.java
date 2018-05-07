/**
 * Copyright : www.easipay.net , 2009-2010 Project : PEPP $Id$ $Revision$ Last Changed by $Author$
 * at $Date$ $URL$ Change Log Author Change Date Comments
 * ------------------------------------------------------------- your name 2011-6-13 Initailized
 */

package net.easipay.cbp.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * TODO Add class descriptions
 */
public class XlsExporter {

  public static void export(Map<String, Object> beans, String collectionName, InputStream template, OutputStream output) throws ParsePropertyException, InvalidFormatException, IOException {
    XLSTransformer transformer = new XLSTransformer();
    if (null != collectionName) {
      transformer.groupCollection(collectionName);
    }
    Workbook wb = transformer.transformXLS(template, beans);
    wb.write(output);
  }

  public static void export(Map<String, Object> beans, String collectionName, String templateName, OutputStream output) throws ParsePropertyException, InvalidFormatException, IOException {
    export(beans, collectionName, Thread.currentThread().getContextClassLoader().getResourceAsStream(templateName), output);
  }
}
