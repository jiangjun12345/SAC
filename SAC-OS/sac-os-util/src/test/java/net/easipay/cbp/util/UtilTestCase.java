package net.easipay.cbp.util;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.easipay.cbp.base.BaseTestCase;

import org.junit.Test;

public class UtilTestCase  extends BaseTestCase{
	org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor c;
	@Test
	public void testcase() throws ParseException{
		Date date = new Date();
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	    String dateString = formatter.format(date);
	    Date currentTime_2 = formatter.parse(dateString);
	   
	    String dateStr = DateUtil.getFomateDate(date, "yyyy-MM-dd");
	    System.out.println(currentTime_2);
	    System.out.println( dateStr);
	}
}
