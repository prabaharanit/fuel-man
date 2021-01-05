/**
 * 
 */
package com.mayavan.fuelman.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import com.mayavan.fuelman.util.DateAndTimeUtility;
import com.mayavan.fuelman.util.DateAndTimeUtility.DATEFORMAT;
import com.mayavan.fuelman.util.DateAndTimeUtility.TimeZoneConstant;

/**
 * DateAndTimeUtiityTest.java
 * 
 * @author "Prabaharan Periasamy"
 * @created 17-Mar-2014
 * @version 1.0
 */
public class DateAndTimeUtiityTest {

	
	@Test
	public void testgetTimeStampInterval(){
		//check current day - 1440 min one day differnce 
		DateTime now = new DateTime();
		DateTime expiryDateTime = now.minusMinutes(1440);
		Timestamp expiryTimeStamp = new Timestamp(expiryDateTime.getMillis());
		boolean dateExpired = DateAndTimeUtility.getTimeStampInterval(expiryTimeStamp);
		Assert.assertTrue(dateExpired);
		//check current day - 1400 min not one day differnce
		DateTime now1 = new DateTime();
		DateTime expiryDateTime1 = now1.minusMinutes(1400);
		Timestamp expiryTimeStamp1 = new Timestamp(expiryDateTime1.getMillis());
		boolean dateExpired1 = DateAndTimeUtility.getTimeStampInterval(expiryTimeStamp1);
		Assert.assertFalse(dateExpired1);
		//check current day - 0 min no day differnce
		DateTime now2 = new DateTime();
		DateTime expiryDateTime2 = now2.minusMinutes(0);
		Timestamp expiryTimeStamp2 = new Timestamp(expiryDateTime2.getMillis());
		boolean dateExpired2 = DateAndTimeUtility.getTimeStampInterval(expiryTimeStamp2);
		Assert.assertFalse(dateExpired2);
	}
	
	
	@Test
	public void testFindDateIntervalForTwoValidDatesWith1DayGap() throws ParseException {
		Long currentDateTime = System.currentTimeMillis();
		Date date1 = new Date(currentDateTime);
		DateAndTimeUtility dtUtility = new DateAndTimeUtility(date1);
		Date date2 = dtUtility.adjust(1);
		int dateInterval = DateAndTimeUtility.getDateInterval(date2);
		System.out.println("interval between two dates date1 is " + date1 + " and date2 " + date2 + " is, no of days interval " + dateInterval);
		Assert.assertEquals(1, dateInterval);
	}

	@Test
	public void testFindDateIntervalForTwoValidDatesWith7DaysGap() throws ParseException {
		Long currentDateTime = System.currentTimeMillis();
		Date date1 = new Date(currentDateTime);
		DateAndTimeUtility dtUtility = new DateAndTimeUtility(date1);
		Date date2 = dtUtility.adjust(7);
		int dateInterval = DateAndTimeUtility.getDateInterval(date2);
		System.out.println("interval between two dates date1 is " + date1 + " and date2 " + date2 + " is, no of days interval " + dateInterval);
		Assert.assertEquals(7, dateInterval);
	}

	@Test
	public void testFindDateIntervalForTwoValidDatesWith2DaysGap() throws ParseException {
		Long currentDateTime = System.currentTimeMillis();
		Date date1 = new Date(currentDateTime);
		DateAndTimeUtility dtUtility = new DateAndTimeUtility(date1);
		Date date2 = dtUtility.adjust(2);
		int dateInterval = DateAndTimeUtility.getDateInterval(date2);
		System.out.println("interval between two dates date1 is " + date1 + " and date2 " + date2 + " is, no of days interval " + dateInterval);
		Assert.assertEquals(2, dateInterval);
	}

	@Test
	public void testGetDateInTimeZone() {
		Date eventDate = new Date(System.currentTimeMillis());
		Date convertedDate;
		try {
			convertedDate = DateAndTimeUtility.getDateInTimeZone(eventDate, TimeZoneConstant.IST).getTime();
			Assert.assertNotNull(convertedDate);
		} catch (ParseException e) {
			Assert.fail();
		}

	}

	@Test
	public void testFormattedDate() {
		Long currentDateTime = System.currentTimeMillis();
		Date date1 = new Date(currentDateTime);
		DateAndTimeUtility dtUtility = new DateAndTimeUtility(date1);
		System.out.println(dtUtility.getFormattedDate(DATEFORMAT.DAY_DATE_MONTH_YEAR));
	}
	
	@Test
	public void testGetCurrentAdjustDateInString() {
		Date currentDate = new Date(System.currentTimeMillis());
		DateAndTimeUtility dtUtility = new DateAndTimeUtility(currentDate);
		Date date = dtUtility.adjust(2);
		DateAndTimeUtility dUtility = new DateAndTimeUtility(date);
		String sdate = dUtility.getFormattedDate(DATEFORMAT.DB_FORMAT_YYYY_DD_MM).toString(); 
		System.out.println(sdate);
	}


	@Test
	public void testConvertStringToDate() {
		String parsedDate = null;
		try {
			parsedDate = DateAndTimeUtility.changeDateFormat("2020-09-11T08:27:08.996Z", DATEFORMAT.DB_FORMAT_YYYY_DD_MM, DATEFORMAT.DD_MMM_YY);
			System.out.println(parsedDate.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testConvertDateFormat() {
		try {
			String parsedDate = DateAndTimeUtility.changeDateFormat("24.04.2014", DATEFORMAT.CLIENT_FORMAT_DD_MM_YYYY, DATEFORMAT.DB_FORMAT_YYYY_DD_MM);
			System.out.println(" parsing date to db format" + parsedDate.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testConvertTimeZoneFromESTToISTSucess() throws ParseException {
		try {
			String convertedToISOFormat = DateAndTimeUtility.formatDateTimeToISO8601("2014-04-17 23:30:02");
			String convertTimeZone = DateAndTimeUtility.convertTimeZones(TimeZoneConstant.EST, TimeZoneConstant.IST, DATEFORMAT.DB_DATE_TIME_FORMAT, convertedToISOFormat);
			System.out.println("TimeZone  for UTC To LocalTime " + convertTimeZone);
			Assert.assertEquals("2014-04-18 9:00:02", convertTimeZone);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}

	}

	@Test
	public void testConvertTimeZoneFromESTToISTFail() throws ParseException {
		try {
			String convertedToISOFormat = DateAndTimeUtility.formatDateTimeToISO8601("2014-04-17 23:30:02");
			String convertTimeZone = DateAndTimeUtility.convertTimeZones(TimeZoneConstant.EST, TimeZoneConstant.CST, DATEFORMAT.DB_DATE_TIME_FORMAT, convertedToISOFormat);
			System.out.println(" fail timezone " + convertTimeZone);
			Assert.assertNotSame(" CST date timezone is not matched, test case passed", "2014-04-18 9:00:02", convertTimeZone);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}

	}

	@Test
	public void testConvertStringToDateFormat() {
		try {
			String fromDate = "2020-09-11T08:57:44.681Z";
			Date fromEventDateStr = DateAndTimeUtility.convertStringToDate(fromDate, DATEFORMAT.DB_FORMAT_YYYY_DD_MM, DATEFORMAT.DD_MMM_YY);
			System.out.println(" converted to date obj " + fromEventDateStr);
			//Date previousEventDate = DateAndTimeUtility.convertStringToDate(fromEventDateStr, DATEFORMAT.SYSTEM_DATE_FORMAT);
			//int dayInterval = DateAndTimeUtility.getDateInterval(previousEventDate, updatedEventDate);
			//Assert.assertEquals(4, dayInterval);
		} catch (ParseException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void testUpdatedEventDateLessThanPreviousEventDate() {
		try {
			String fromDate = "21.05.2014";
			String fromEventDateStr = DateAndTimeUtility.changeDateFormat(fromDate, DATEFORMAT.CLIENT_FORMAT_DD_MM_YYYY, DATEFORMAT.SYSTEM_DATE_FORMAT);
			Date previousEventDate = DateAndTimeUtility.convertStringToDate(fromEventDateStr, DATEFORMAT.SYSTEM_DATE_FORMAT);
			String toDate = "17.05.2014";

			String toEventDateStr = DateAndTimeUtility.changeDateFormat(toDate, DATEFORMAT.CLIENT_FORMAT_DD_MM_YYYY, DATEFORMAT.SYSTEM_DATE_FORMAT);
			Date updatedEventDate = DateAndTimeUtility.convertStringToDate(toEventDateStr, DATEFORMAT.SYSTEM_DATE_FORMAT);
			int dayInterval = DateAndTimeUtility.getDateInterval(previousEventDate, updatedEventDate);
			Assert.assertEquals(4, dayInterval);
		} catch (ParseException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@Test
	public void testUpdatedEventDateGreaterThanPreviousEventDate() {
		try {
			String fromDate = "21.05.2014";
			String fromEventDateStr = DateAndTimeUtility.changeDateFormat(fromDate, DATEFORMAT.CLIENT_FORMAT_DD_MM_YYYY, DATEFORMAT.SYSTEM_DATE_FORMAT);
			Date previousEventDate = DateAndTimeUtility.convertStringToDate(fromEventDateStr, DATEFORMAT.SYSTEM_DATE_FORMAT);
			String toDate = "27.05.2014";

			String toEventDateStr = DateAndTimeUtility.changeDateFormat(toDate, DATEFORMAT.CLIENT_FORMAT_DD_MM_YYYY, DATEFORMAT.SYSTEM_DATE_FORMAT);
			Date updatedEventDate = DateAndTimeUtility.convertStringToDate(toEventDateStr, DATEFORMAT.SYSTEM_DATE_FORMAT);
			int dayInterval = DateAndTimeUtility.getDateInterval(previousEventDate, updatedEventDate);
			Assert.assertEquals(-6, dayInterval);
		} catch (ParseException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@Test
	public void testUpdatedEventDateSameAsPreviousEventDate() {
		try {
			String fromDate = "27.05.2014";
			String fromEventDateStr = DateAndTimeUtility.changeDateFormat(fromDate, DATEFORMAT.CLIENT_FORMAT_DD_MM_YYYY, DATEFORMAT.SYSTEM_DATE_FORMAT);
			Date previousEventDate = DateAndTimeUtility.convertStringToDate(fromEventDateStr, DATEFORMAT.SYSTEM_DATE_FORMAT);
			String toDate = "27.05.2014";

			String toEventDateStr = DateAndTimeUtility.changeDateFormat(toDate, DATEFORMAT.CLIENT_FORMAT_DD_MM_YYYY, DATEFORMAT.SYSTEM_DATE_FORMAT);
			Date updatedEventDate = DateAndTimeUtility.convertStringToDate(toEventDateStr, DATEFORMAT.SYSTEM_DATE_FORMAT);
			int dayInterval = DateAndTimeUtility.getDateInterval(previousEventDate, updatedEventDate);
			Assert.assertEquals(0, dayInterval);
		} catch (ParseException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}
