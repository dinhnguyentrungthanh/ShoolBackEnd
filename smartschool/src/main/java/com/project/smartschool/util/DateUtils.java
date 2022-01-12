package com.project.smartschool.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	public static String getStringDate(Date date, String format) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		
		return dateFormat.format(date);
	}
	
	public static Date toDate(String input, String format) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		
		return dateFormat.parse(input);
	}
	
}
