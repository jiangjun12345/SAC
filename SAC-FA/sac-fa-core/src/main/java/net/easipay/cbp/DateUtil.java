package net.easipay.cbp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author mchen
 * @date 2015-12-14
 */

public class DateUtil
{

    public static Date parse(String date, String pattern) throws ParseException
    {
	DateFormat df = new SimpleDateFormat(pattern);
	return df.parse(date);
    }

    public static String format(Date date, String pattern)
    {
	DateFormat df = new SimpleDateFormat(pattern);
	return df.format(date);
    }

    public static String formatCurrentDate(String pattern)
    {
	DateFormat df = new SimpleDateFormat(pattern);
	return df.format(currentDate());
    }

    public static Date currentDate()
    {
	return Calendar.getInstance().getTime();
    }

    public static Date getTomrrow(Date date)
    {
	Calendar cal = Calendar.getInstance();
	cal.setTime(date);
	cal.add(Calendar.DATE, 1);
	return cal.getTime();
    }
    
    public static String getTomrrow(Date date, String pattern)
    {
	return format(getTomrrow(date), pattern);
    }

    
    public static Date getYesterday(Date date)
    {
	Calendar cal = Calendar.getInstance();
	cal.setTime(date);
	cal.add(Calendar.DATE, -1);
	return cal.getTime();
    }
    
    public static String getYesterday(Date date, String pattern)
    {
	return format(getYesterday(date), pattern);
    }
}
