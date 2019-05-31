package com.happy.para.common;

public class DateModule {

private static DateModule dateModule;
	
	private DateModule() {
		
	}
	
	public String changeDateFormat(String date) {
		String result = null;
		if(date.length() <= 1) {
			result = "-0"+date;
		}		
		else 
			result = "-"+date;
		
		return result;
	}
	

	public static DateModule getInstance() {
		
		if(dateModule == null) {
			dateModule = new DateModule();
		}		
		return dateModule;
	}
	
}
