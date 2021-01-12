/**
 * 
 */
package com.mayavan.fuelman.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.util.StringUtils;

import com.mayavan.fuelman.controller.FuelTypeController;

/**
 * DateUtility.java
 * 
 * @author "Prabaharan Periasamy"
 * @created 17-Mar-2014
 * @version 1.0
 */
public class DateAndTimeUtility {

	private static final Logger logger = LoggerFactory.getLogger(FuelTypeController.class);

	private Date date;

	/**
	 * 
	 */
	public DateAndTimeUtility(Date date) {
		this.date = date;
	}

	public Date adjust(int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("IST"));
		cal.setTimeInMillis(date.getTime());
		cal.add(Calendar.DAY_OF_MONTH, amount);
		Date adjustedDate = cal.getTime();
		return adjustedDate;

	}

	/**
	 * pass date1 as current date and date2 param as future date. <li>
	 * getDateInterval returns the interval between two dates as integer <li>For
	 * e.g:- difference between date1 17-Mar-2014 and date2 24-Mar-2014 returns
	 * <i>int<i> value as 7
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 * @throws ParseException 
	 */
	public static int getDateInterval(final Date eventDate) throws ParseException {
		DateTime dateTime2 = new DateTime(eventDate);
		Date currentTime = getDateInTimeZone(new Date(System.currentTimeMillis()), TimeZoneConstant.IST).getTime();
		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& Current time in IST &&&&&&&&&&&&&&&&&&&&&&&&& -  " + currentTime);
		DateTime dateTime1 = new DateTime(currentTime.getTime());
		Days dayInterval = Days.daysBetween(dateTime1.toLocalDate(), dateTime2.toLocalDate());
		int daysBtw = dayInterval.getDays();
		System.out.println("days interval between two dates is " + daysBtw);
		return daysBtw;
	}

	//check the given date is less than or greater than current time stamp  
	public static boolean getTimeStampInterval(Timestamp expiryDate){
		Date date1 = new Date(expiryDate.getTime());
		DateTime now = new DateTime();
		Timestamp currentTimeStamp = new Timestamp(now.getMillis());
		Date date2 = new Date(currentTimeStamp.getTime());
        int diffInDays = (int) ((date2.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24));
        System.out.print("differnce between current day and expiry date ="+diffInDays);
        return(diffInDays == 0 ? false : true);
	}
	
	/**
	 * return start and end time string , when both start and endtime available,
	 * final string will have both start and end time <li>For eg:- From 10:00 AM
	 * To 02:00 PM <li>
	 * if start time is empty and end time is available , then this is
	 * considered as invalid time and returns empty string. <li>if only start
	 * time present then final string is <i>From 10:00 AM Onwards</i>
	 * 
	 * @return String
	 */
	public static String formatStartAndEntTime(String startTime, String endTime) {
		if (!StringUtils.isEmpty(startTime) && !StringUtils.isEmpty(endTime)) {
			return ", From " + startTime + " To " + endTime;
		} else if (!StringUtils.isEmpty(startTime)) {
			return ", From " + startTime + " Onwards";
		}
		return "";
	}

	public static int getDateInterval(final Date fromDate, final Date toDate) {

		DateTime dateTime2 = new DateTime(fromDate.getTime());
		System.out.println("current date time -  " + toDate);
		DateTime dateTime1 = new DateTime(toDate.getTime());
		Days dayInterval = Days.daysBetween(dateTime1.toLocalDate(), dateTime2.toLocalDate());
		int daysBtw = dayInterval.getDays();
		System.out.println("days interval between two dates is " + daysBtw);
		return daysBtw;
	}

	public String getFormattedDate(DATEFORMAT dateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat.getDateFormat());
		sdf.setTimeZone(TimeZone.getTimeZone("IST"));
		String formattedDate = sdf.format(date);
		return formattedDate;
	}

	public static Date convertStringToDate(String dateString, DATEFORMAT dateFormat) throws ParseException {
		System.out.println("date format  " + dateString);
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat.getDateFormat());
		sdf.setTimeZone(TimeZone.getTimeZone("IST"));
		Date parsedDate = sdf.parse(dateString);
		return parsedDate;
	}
	
	public static Timestamp convertStringToTimestamp(String dateString, DATEFORMAT dateFormat) throws ParseException {
	    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat.getDateFormat());
	    sdf.setTimeZone(TimeZone.getTimeZone("IST"));
	    Date parsedDate = sdf.parse(dateString);
	    Timestamp timestamp = new Timestamp(parsedDate.getTime());
	    return timestamp;
	}

	public static String GetCurrentDateInStringDBFormat(int interval) throws ParseException{
		Date currentDate = new Date(System.currentTimeMillis());
		DateAndTimeUtility dtUtility = new DateAndTimeUtility(currentDate);
		Date date = dtUtility.adjust(interval);
		DateAndTimeUtility dUtility = new DateAndTimeUtility(date);
		return(dUtility.getFormattedDate(DATEFORMAT.DB_FORMAT_YYYY_DD_MM).toString()); 
	}
	
	public static Calendar getDateInTimeZone(Date currentDate, TimeZoneConstant timeZoneId) throws ParseException {
		Calendar mbCal = new GregorianCalendar(TimeZone.getTimeZone(timeZoneId.getTimeZoneConstant()));
		mbCal.setTimeInMillis(currentDate.getTime());
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, mbCal.get(Calendar.YEAR));
		cal.set(Calendar.MONTH, mbCal.get(Calendar.MONTH));
		cal.set(Calendar.DAY_OF_MONTH, mbCal.get(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, mbCal.get(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, mbCal.get(Calendar.MINUTE));
		cal.set(Calendar.SECOND, mbCal.get(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, mbCal.get(Calendar.MILLISECOND));
		return cal;
	}

	public static String formatDateTimeToISO8601(String dateTime) throws IllegalArgumentException {
		if (null == dateTime) {
			throw new IllegalArgumentException("DateTime String cant be null");
		}

		String[] dateSplitter = dateTime.split(" ", 2);

		switch (dateSplitter.length) {
		case 1:
			dateTime = dateTime.replace(" ", "T");
			break;

		case 2:
			dateTime = dateSplitter[0].concat("T").concat(dateSplitter[1]);
			break;

		default:
			return dateTime;
		}

		logger.debug("Timestamp converted to ISO8601 format - " + dateTime);
		return dateTime;
	}

	public static String convertTimeZones(final TimeZoneConstant fromTimeZoneString, final TimeZoneConstant toTimeZoneString, DATEFORMAT convertToFormat, final String fromDateTime) {
		final DateTimeZone fromTimeZone = DateTimeZone.forID(fromTimeZoneString.getTimeZoneConstant());
		final DateTimeZone toTimeZone = DateTimeZone.forID(toTimeZoneString.getTimeZoneConstant());
		final DateTime dateTime = new DateTime(fromDateTime, fromTimeZone);
		final DateTimeFormatter outputFormatter = DateTimeFormat.forPattern(convertToFormat.getDateFormat()).withZone(toTimeZone);
		return outputFormatter.print(dateTime);
	}

	public static String changeDateFormat(String dateString, DATEFORMAT oldFormat, DATEFORMAT newFormat) throws ParseException {
		DateFormat oldFormatter = new SimpleDateFormat(oldFormat.getDateFormat());
		oldFormatter.setLenient(false);
		Date parsedDate = oldFormatter.parse(dateString);
		String CheckFormat = newFormat.getDateFormat();
		String dateStringFrom = new SimpleDateFormat(CheckFormat).format(parsedDate);

		return dateStringFrom;
	}
	
	public static Date changeDateFormat(Date dateString, DATEFORMAT oldFormat, DATEFORMAT newFormat) throws ParseException {
		DateFormat oldFormatter = new SimpleDateFormat(oldFormat.getDateFormat());
		oldFormatter.setLenient(false);
		Date parsedDate = oldFormatter.parse(dateString.toString());
		String CheckFormat = newFormat.getDateFormat();
		String dateStringFrom = new SimpleDateFormat(CheckFormat).format(parsedDate);
		Date convertedDate = new SimpleDateFormat(CheckFormat).parse(dateStringFrom);

		return convertedDate;
	}
	
	public static Date convertStringToDate(String dateString, DATEFORMAT oldFormat, DATEFORMAT newFormat) throws ParseException {
		System.out.println("date format  " + dateString);
		DateFormat oldFormatter = new SimpleDateFormat(oldFormat.getDateFormat());
		oldFormatter.setLenient(false);
		Date parsedDate = oldFormatter.parse(dateString);
		String CheckFormat = newFormat.getDateFormat();
		DateFormat newFormatter = new SimpleDateFormat(CheckFormat);
		String dateStringFrom = newFormatter.format(parsedDate);
		Date convertedDate = newFormatter.parse(dateStringFrom);

		return convertedDate;
	}

	public enum TimeZoneConstant {
		IST("Asia/Kolkata"), CST("America/Chicago"), EST("America/New_York");

		private String timeZone;

		/**
		 * 
		 */
		private TimeZoneConstant(String mTimeZone) {
			timeZone = mTimeZone;
		}

		public String getTimeZoneConstant() {
			return timeZone;
		}
	}

	public enum DATEFORMAT {
		DAY_DATE_MONTH_YEAR("E dd MMM yyyy"), DD_MMM_YY("dd-MMM-yyyy"), DB_FORMAT_YYYY_DD_MM("yyyy-MM-dd"), SYSTEM_DATE_FORMAT("EEE MMM dd HH:mm:ss z yyyy"), CLIENT_FORMAT_DD_MM_YYYY("dd.MM.yyyy"), DB_DATE_TIME_FORMAT(
				"yyyy-MM-dd H:mm:ss"), CLIENT_DATE_TIME_FORMAT("dd.MM.yyyy h:mm:ss"), CLIENT_DATE_TIME_FORMAT_DTTM_OF_SALE("dd MM yyyy H:mm:ss");

		private String dateFormat;

		/**
		 * 
		 */
		private DATEFORMAT(String dateFormat) {
			this.dateFormat = dateFormat;
		}

		public String getDateFormat() {
			return dateFormat;
		}
	}
}
