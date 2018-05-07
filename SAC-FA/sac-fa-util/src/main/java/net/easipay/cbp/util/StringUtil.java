package net.easipay.cbp.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;



public class StringUtil {

	public static boolean isEmptyString(String arg)
	  {
	    if ((arg == null) || (arg.trim().length() <= 0) || (arg.equals("")) || (arg.toLowerCase().equals("null"))) {
	      return true;
	    }
	    return false;
	  }
	
	public static String dateToStr(){

		//年月日****-**-**
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss"); 
		String str = format.format(new Date());   
		System.out.println("str:"+str);
		return str;
	}
	
	public static Date strToDate(String str){
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
	
	
	public static void main(String[] args) {
		System.out.println(dateToStr());
		System.out.println(strToDate("20150429111111"));
		
	}
}
