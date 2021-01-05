package com.mayavan.fuelman.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {

	public static Timestamp convertStringToTimestamp(String str_date) {
		try {
			DateFormat formatter;
			formatter = new SimpleDateFormat("dd/MM/yyyy H:mm:ss");
			Date date = (Date) formatter.parse(str_date);
			java.sql.Timestamp timeStampDate = new Timestamp(date.getTime());
			return timeStampDate;
		} catch (ParseException parExp) {
			System.out.println("Exception while converting string to timestamp :" + parExp);
			return null;
		}
	}

	
	public static Date convertStringToDate(String str_date) {
		try {
			DateFormat formatter;
			formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date date = (Date) formatter.parse(str_date);
			return date;
		} catch (ParseException parExp) {
			System.out.println("Exception while converting string to Date :" + parExp);
			return null;
		}
	}
}
