package com.nec.asia.nic.comp.trans.utils;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.nec.asia.nic.utils.StringUtil;

public class HelperClass {
	public static Date convertStringToDate(String cal) {
		try {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = dateFormat.parse(cal);
			return date;
		} catch (Exception e) {
			System.out.println("Exception :" + e.getMessage());
			return null;
		}

	}
	
	public static String convertDateToString1(Date date) {
		if (date != null) {
			DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");
			String dateS = format.format(date);
			return dateS;
		}
		return null;
	}
	
	public static String convertMonthNumberToMonthWord1(String date_str) {
		String[] monthWord = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
				"Aug", "Sep", "Oct", "Nov", "Dec" };
		String[] monthNumber = { "01", "02", "03", "04", "05", "06", "07",
				"08", "09", "10", "11", "12" };
		String[] date = date_str.split("/");
		String dateNumber = date[0].toString() + "-";
		for (int i = 0; i < 12; i++) {
			if (monthNumber[i].equals(date[1])) {
				dateNumber += monthWord[i] + "-";
				break;
			}
		}
		dateNumber += date[2];
		return dateNumber;
	}
	
	public static Date convertStringToDate1(String cal) {
		try {
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			Date date = dateFormat.parse(cal);
			return date;
		} catch (Exception e) {
			System.out.println("Exception :" + e.getMessage());
			return null;
		}

	}
	
	public static String convertCalendarToStringVersion2(Calendar cal) {
		try {
			String dateS = "";
			Date date = cal.getTime();
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			dateS = dateFormat.format(date);
			return dateS;
		} catch (Exception e) {
			System.out.println("Exception :" + e.getMessage());
			return null;
		}

	}
	
	//Phúc thêm 1/8/2018
	public static String convertStringToString(String date_str){
		if(!StringUtil.isEmpty(date_str) && date_str.contains("-")){
			String[] date = date_str.split("-");
			String dateNumber = date[2].toString() + "/" + date[1].toString() + "/" + date[0].toString();
			return dateNumber;			
		}
		return "";
	}
	
	public static String convertStringToString1(String date_str){
		String[] date = date_str.split("/");
		String dateNumber = date[2].toString() + "-" + date[1].toString() + "-" + date[0].toString();
		return dateNumber;
	}

}
