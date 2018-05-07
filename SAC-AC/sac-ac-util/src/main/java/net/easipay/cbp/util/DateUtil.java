package net.easipay.cbp.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

    public static String getDateYesterday(String pattern)
    {
	Calendar cal = Calendar.getInstance();
	cal.add(Calendar.DATE, -1);
	DateFormat format = new SimpleDateFormat(pattern);
	return format.format(cal.getTime());
    }

}
