package service.perso.util;

import java.text.DateFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class HelperClass {
	
	
	public static Date convertStringToDateTk(String cal, int no) {
		try {
			if(cal == null){
				return null;
			}
			String dateTeca = "";
			if(cal.contains("-")){
				cal = cal.replaceAll("-", "/");
			}
			if(cal.contains("/")){
				cal = cal.replaceAll("/", "");
			}
			int lengthDate = cal.length();
			switch (lengthDate) {
			case 4:
				dateTeca = "01/01/" + cal;
				break;
			case 6:
				if(no == 1){
					//ngày/tháng/năm
					String month = cal.substring(0, 2);
					String year = cal.substring(2, 6);
					dateTeca = "01/" + month + "/" + year;
				}else{
					//năm/tháng/ngày
					String year = cal.substring(0, 4);
					String month = cal.substring(4, 6);
					dateTeca = "01/" + month + "/" + year;
				}
				break;
			case 8:
				if(no == 1){
					//ngày/tháng/năm
					String day = cal.substring(0, 2);
					String month = cal.substring(2, 4);
					String year = cal.substring(4, 8);
					dateTeca = day + "/" + month + "/" + year;
				}else{
					//năm/tháng/ngày
					String year = cal.substring(0, 4);
					String month = cal.substring(4, 6);
					String day = cal.substring(6, 8);
					dateTeca = day + "/" + month + "/" + year;
				}
				break;	

			default:
				return null;
			}
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = dateFormat.parse(dateTeca);
			return date;
		} catch (Exception e) {
			System.out.println("Exception :" + e.getMessage());
			return null;
		}
	}
	
	public static String convertDateToString(Date date, String type){
		try {
			if(date != null){
				DateFormat dateFormat = new SimpleDateFormat(type);
				String strDate = dateFormat.format(date);
				return strDate;				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String removeAccent(String s) {
		  
		  String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
		  Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		  return pattern.matcher(temp).replaceAll("");
		 }
}
