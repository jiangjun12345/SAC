package net.easipay.cbp.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import net.easipay.cbp.constant.Constants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * Date Utility Class used to convert Strings to Dates and Timestamps
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a> Modified by
 *         <a href="mailto:dan@getrolling.com">Dan Kibler </a> to correct time
 *         pattern. Minutes should be mm not MM (MM is month).
 */
public final class DateUtil {
	private static Log log = LogFactory.getLog(DateUtil.class);
	private static final String TIME_PATTERN = "HH:mm";

	/**
	 * Checkstyle rule: utility classes should not have public constructor
	 */
	private DateUtil() {
	}

	/**
	 * Return default datePattern (yyyy-mm-dd)
	 * 
	 * @return a string representing the date pattern on the UI
	 */
	public static String getDatePattern() {
		Locale locale = LocaleContextHolder.getLocale();
		String defaultDatePattern;
		try {
			defaultDatePattern = ResourceBundle.getBundle(Constants.BUNDLE_KEY,
					locale).getString("date.format");
		} catch (MissingResourceException mse) {
			defaultDatePattern = "yyyy-mm-dd";
		}

		return defaultDatePattern;
	}

	public static String getDateTimePattern() {
		return DateUtil.getDatePattern() + " HH:mm:ss.S";
	}

	/**
	 * This method attempts to convert an Oracle-formatted date in the form
	 * dd-MMM-yyyy to yyyy-mm-dd.
	 * 
	 * @param aDate
	 *            date from database as a string
	 * @return formatted string for the ui
	 */
	public static String getDate(Date aDate) {
		SimpleDateFormat df;
		String returnValue = "";

		if (aDate != null) {
			df = new SimpleDateFormat(getDatePattern());
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	/**
	 * This method generates a string representation of a date/time in the
	 * format you specify on input
	 * 
	 * @param aMask
	 *            the date pattern the string is in
	 * @param strDate
	 *            a string representation of a date
	 * @return a converted Date object
	 * @throws ParseException
	 *             when String doesn't match the expected format
	 * @see java.text.SimpleDateFormat
	 */
	public static Date convertStringToDate(String aMask, String strDate)
			throws ParseException {
		SimpleDateFormat df;
		Date date;
		df = new SimpleDateFormat(aMask);

		if (log.isDebugEnabled()) {
			log.debug("converting '" + strDate + "' to date with mask '"
					+ aMask + "'");
		}

		try {
			date = df.parse(strDate);
		} catch (ParseException pe) {
			// log.error("ParseException: " + pe);
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());
		}

		return (date);
	}

	/**
	 * This method returns the current date time in the format: yyyy-mm-dd HH:MM
	 * a
	 * 
	 * @param theTime
	 *            the current time
	 * @return the current date/time
	 */
	public static String getTimeNow(Date theTime) {
		return getDateTime(TIME_PATTERN, theTime);
	}

	/**
	 * This method returns the current date in the format: yyyy-mm-dd
	 * 
	 * @return the current date
	 * @throws ParseException
	 *             when String doesn't match the expected format
	 */
	public static Calendar getToday() throws ParseException {
		Date today = new Date();
		SimpleDateFormat df = new SimpleDateFormat(getDatePattern());

		// This seems like quite a hack (date -> string -> date),
		// but it works ;-)
		String todayAsString = df.format(today);
		Calendar cal = new GregorianCalendar();
		cal.setTime(convertStringToDate(todayAsString));

		return cal;
	}

	/**
	 * This method generates a string representation of a date's date/time in
	 * the format you specify on input
	 * 
	 * @param aMask
	 *            the date pattern the string is in
	 * @param aDate
	 *            a date object
	 * @return a formatted string representation of the date
	 * @see java.text.SimpleDateFormat
	 */
	public static String getDateTime(String aMask, Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate == null) {
			log.warn("aDate is null!");
		} else {
			df = new SimpleDateFormat(aMask);
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	/**
	 * This method generates a string representation of a date based on the
	 * System Property 'dateFormat' in the format you specify on input
	 * 
	 * @param aDate
	 *            A date to convert
	 * @return a string representation of the date
	 */
	public static String convertDateToString(Date aDate) {
		return getDateTime(getDatePattern(), aDate);
	}

	/**
	 * This method converts a String to a date using the datePattern
	 * 
	 * @param strDate
	 *            the date to convert (in format yyyy-mm-dd)
	 * @return a date object
	 * @throws ParseException
	 *             when String doesn't match the expected format
	 */
	public static Date convertStringToDate(final String strDate)
			throws ParseException {
		return convertStringToDate(getDatePattern(), strDate);
	}

	/**
	 * Convert Sql to Util
	 * 
	 * @param inDate
	 * @return Converted date
	 */
	public static java.util.Date convertSqltoUtilDate(java.sql.Date inDate) {
		return new java.util.Date(inDate.getTime());
	}

	/**
	 * Convert Timestamp to java.sql.Date
	 * 
	 * @param dateTime
	 * @return Converted sql.date
	 */
	public static java.sql.Date convertTimestamptoSqlDate(
			java.sql.Timestamp dateTime) {
		if (dateTime == null) {
			return null;
		}
		return new java.sql.Date(dateTime.getTime());

	}

	/**
	 * Convert Util to sql date
	 * 
	 * @param inDate
	 * @return Converted date
	 */
	public static java.sql.Date convertUtiltoSqlDate(java.util.Date inDate) {
		if (inDate == null) {
			return null;
		}
		if (inDate instanceof java.sql.Date) {
			return (java.sql.Date) inDate;
		} else {
			return new java.sql.Date(inDate.getTime());
		}
	}

	/**
	 * Convert Util to timestamp
	 * 
	 * @param inDate
	 * @return Converted date
	 */
	public static java.sql.Timestamp convertUtiltoTimestamp(
			java.util.Date inDate) {
		if (inDate == null) {
			return null;
		}
		if (inDate instanceof Timestamp) {
			return (Timestamp) inDate;
		} else {
			return new java.sql.Timestamp(inDate.getTime());
		}
	}

	/**
	 * @param inDate
	 * @return
	 */
	public static java.sql.Timestamp convertSqlDatetoTimestamp(
			java.sql.Date inDate) {
		if (inDate == null) {
			return null;
		}
		return new java.sql.Timestamp(inDate.getTime());
	}
	
	
	/**
	  * 获取现在时间
	  *
	  * @return 返回时间类型 yyyy-MM-dd HH:mm:ss		yyyy-MM-dd
	  */
	 public static String getFomateDate(Date date,String pattern) {
		 SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		 String dateString = formatter.format(date);
		 return dateString;
	 }
	 
	 /**
	  * 获取当前时间为准，time 和 value 的差异时间
	  * @param time Calendar.MONTH Calendar.DATE
	  * @param value 
	  * @param pattern
	  * @return
	  */
    public static String formatCurrentDate(int time,int value,String pattern){
    	Calendar cal = Calendar.getInstance();
    	cal.add(time, value);
    	Date date = cal.getTime();
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }

}
