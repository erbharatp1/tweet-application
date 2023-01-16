package com.csipl.hrms.org;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;

public class DateValidation {
	
	
	static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	HashSet<Date> holiday = new HashSet<>();
	HashSet<Date> leaveEnteries = new HashSet<>();
	
	
	public static  int weekOfDay(String fromDate) {
		 String yearMonthDate[]= fromDate.split("-");
		 Calendar c = Calendar.getInstance();
		 c = new GregorianCalendar(Integer.parseInt(yearMonthDate[0]),Integer.parseInt(yearMonthDate[1])-1,Integer.parseInt(yearMonthDate[2]));
			System.out.println("==============new date=============="+c.DAY_OF_WEEK);
		return c.get(Calendar.DAY_OF_WEEK);
	}
	
	public static void fromDateValidation(String fromDate) {
		
	}
	
	
	
	public static void main(String ... args) {
		DateValidation.weekOfDay("2019-04-22");
	}

}
