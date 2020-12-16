/**
 * 
 */
package com.nec.asia.nic.utils;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
//EUNIKE END
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.swing.SwingUtilities;

import com.nec.asia.nic.framework.admin.job.domain.JobExecutionDetails;
import com.nec.asia.nic.framework.admin.job.domain.JobExecutionHistory;

import oracle.net.aso.i;

import org.apache.commons.lang.StringUtils;
import org.jnbis.ImageUtils;
import org.jnbis.WsqDecoder;
import org.jnbis.Bitmap;

/**
 * @author aparna_sharma
 * 
 */
public class HelperClass {
	
	public static final String STR_FORMAT_DATE_yyyyMMddHHmmss = "yyyyMMddHHmmss";
	public static final String STR_FORMAT_DATE_yyyyMMdd = "yyyyMMdd";
	
	public static byte[] ConvertWSQToJPG(byte[] Wsq) {
		//String base64Jpg="";
		
		WsqDecoder wsqDecoder=new WsqDecoder();
		Bitmap bitmap = wsqDecoder.decode(Wsq);		
		ImageUtils imageUtils= new ImageUtils();
		
		byte[] b=  imageUtils.bitmap2jpeg(bitmap);
		//base64Jpg  =  new String(Base64.decodeBase64(b));
		
		return b;	
	}
	
	public static String removeAccent(String s) {
		  
		if(StringUtils.isNotBlank(s)){
			String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
			Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
			return pattern.matcher(temp).replaceAll("").replaceAll("Đ", "D").replaceAll("đ", "d");
		}
		return s;
	}
	
	public static String createFullName(String surname, String midname, String firstname){
		String fullname = "";
		if(!StringUtil.isEmpty(surname)) fullname += surname;
		if(!StringUtil.isEmpty(midname)) fullname += " " + midname;
		if(!StringUtil.isEmpty(firstname)) fullname += " " + firstname;
		return fullname;
	}
	
	public static String loadDateOfBirth(String Date, String styleDate){
		String date = "";
		int n = 0;
		if(StringUtils.isEmpty(Date) || StringUtils.isEmpty(styleDate)){
			return Date;
		}
		if(styleDate.equals("D")){
			n = 1;
		}else if(styleDate.equals("M")){
			n = 2;
		}else{
			n = 3;
		}
		switch (n) {
		case 1:
			//Đủ ngày
			date = Date;
			break;
		case 2:
			//Đủ tháng
			date = Date.substring(3, Date.length());
			break;
		case 3:
			//Đủ năm
			date = Date.substring(6, Date.length());
			break;
		default:
			break;
		}
		return date;
	}
	
	public static String loadDateOfBirths(String Date, String styleDate){
		String date = "";
		int n = 0;
		if(StringUtils.isEmpty(Date) || StringUtils.isEmpty(styleDate)){
			return Date;
		}
		if(styleDate.equals("D")){
			n = 1;
		}else if(styleDate.equals("M")){
			n = 2;
		}else{
			n = 3;
		}
		switch (n) {
		case 1:
			//Đủ ngày
			date = Date;
			break;
		case 2:
			//Đủ tháng
			date = Date.substring(0, 6);
			break;
		case 3:
			//Đủ năm
			date = Date.substring(0, 4);
			break;
		default:
			break;
		}
		return date;
	}
	
	public static String convertStringToStringTk(String cal, int no) {
		try {
			if(cal.contains("-")){
				cal = cal.replace("-", "/");
			}
			if(cal.contains("/")){
				cal = cal.replace("/", "");
			}
			int lengthDate = cal.length();
			String dateFormat = "";
			switch (lengthDate) {
			case 4:
				dateFormat = cal;
				break;
			case 6:
				if(no == 1){
					//ngày/tháng/năm
					String month = cal.substring(0, 2);
					String year = cal.substring(2, 6);					
					dateFormat = month + "/" + year;
				}else{
					//năm/tháng/ngày
					String year = cal.substring(0, 4);
					String month = cal.substring(4, 6);
					dateFormat = month + "/" + year;
				}
				break;
			case 8:
				if(no == 1){
					//ngày/tháng/năm
					String day = cal.substring(0, 2);
					String month = cal.substring(2, 4);
					String year = cal.substring(4, 8);
					dateFormat = day + "/" + month + "/" + year;
				}else{
					//năm/tháng/ngày
					String year = cal.substring(0, 4);
					String month = cal.substring(4, 6);
					String day = cal.substring(6, 8);
					dateFormat = day + "/" + month + "/" + year;
				}
				break;	

			default:
				return null;
			}
			return dateFormat;
		} catch (Exception e) {
			System.out.println("Exception :" + e.getMessage());
		}
		return null;

	}
	
	public static String convertMonthWordToMonthNumber(String date_str){
		if(!date_str.equals("") && date_str.contains("-")){
			String[] monthWord = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
			String[] monthNumber = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
			String[] date = date_str.split("-");
			String dateNumber = date[0].toString() + "/";
			for(int i = 0; i < 12; i++){
				if(monthWord[i].equals(date[1])){
					dateNumber += monthNumber[i] + "/";
					break;
				}
			}
			dateNumber += date[2];
			return dateNumber;			
		}
		return "";
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
	
	public static String convertMonthWordToMonthNumber1(String date_str){
		if(!date_str.equals("") && date_str.contains("-")){
			String[] monthWord = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
			String[] monthNumber = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
			String[] date = date_str.split("-");
			String dateNumber = date[2].toString();
			for(int i = 0; i < 12; i++){
				if(monthWord[i].equals(date[1])){
					dateNumber += monthNumber[i];
					break;
				}
			}
			dateNumber += date[0];
			return dateNumber;			
		}
		return "";
	}
	
	public static Date convertStringToDateTk(String cal, int no) {
		try {
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
	
	public static double getAge(Calendar dateOfBirth) {
		if (dateOfBirth == null)
			return 0;
		int[] monthDay = new int[] { 31, -1, 31, 30, 31, 30, 31, 31, 30, 31,
				30, 31 };
		Calendar fromDate = dateOfBirth;
		Calendar toDate = Calendar.getInstance();
		int year;
		int month;
		int day;
		int increment = 0;
		int dobYear = fromDate.get(Calendar.YEAR);
		System.out.println(fromDate.get(Calendar.DATE));
		if (fromDate.get(Calendar.DATE) > toDate.get(Calendar.DATE)) {
			increment = monthDay[fromDate.get(Calendar.MONTH)];
		}
		if (increment == -1) {
			if (((dobYear % 4 == 0) && (dobYear % 100 != 0))
					|| (dobYear % 400 == 0)) {
				increment = 29;
			} else {
				increment = 28;
			}
		}
		if (increment != 0) {
			day = (toDate.get(Calendar.DATE) + increment)
					- fromDate.get(Calendar.DATE);
			increment = 1;
		} else {
			day = toDate.get(Calendar.DATE) - fromDate.get(Calendar.DATE);
		}
		if ((fromDate.get(Calendar.MONTH) + increment) > toDate
				.get(Calendar.MONTH)) {
			month = (toDate.get(Calendar.MONTH) + 12)
					- (fromDate.get(Calendar.MONTH) + increment);
			increment = 1;
		} else {
			month = (toDate.get(Calendar.MONTH))
					- (fromDate.get(Calendar.MONTH) + increment);
			increment = 0;
		}
		year = toDate.get(Calendar.YEAR)
				- (fromDate.get(Calendar.YEAR) + increment);
		System.out.println(year + "Year(s), " + month + " month(s), " + day
				+ " day(s)");
		Float age = Float.parseFloat(year + "." + month);
		return age.doubleValue();
	}

	public Calendar convertStringToCalendar(String date_str) {
		try {
			DateFormat formatter;
			Date date;
			formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
			date = (Date) formatter.parse(date_str);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			return cal;
		} catch (ParseException e) {
			System.out.println("Exception :" + e);
			return null;
		}

	}

	public static Calendar convertStringToCalendarExcludeTime(String date_str) {
		try {
			DateFormat formatter;
			Date date;
			formatter = new SimpleDateFormat("dd-MMM-yyyy");
			date = (Date) formatter.parse(date_str);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			return cal;
		} catch (ParseException e) {
			System.out.println("Exception :" + e);
			return null;
		}

	}

	public Calendar convertStringToCalendarForAddressUpdate(String date_str) {
		try {
			DateFormat formatter;
			Date date;
			formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
			date = (Date) formatter.parse(date_str);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			return cal;
		} catch (ParseException e) {
			System.out.println("Exception :" + e);
			return null;
		}

	}

	public String generateTransactionId(Calendar cal, String siteCode,
			String sequence) {
		return siteCode + "-" + getCalendarWithTimeValueForTransactionId(cal)
				+ "-" + sequence;
	}

	public static String getCalendarWithTimeValueAsString(Calendar date) {
		if (date == null) {
			return "";
		}
		String output = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
			Calendar cal = (Calendar) date;
			sdf.setCalendar(cal);
			output = sdf.format(cal.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return output;
	}

	public static String getCalendarWithTimeValueAsString(Date date) {
		return getCalendarWithTimeValueAsString(convertDateToCalendar(date));
	}
	
	private static Calendar convertDateToCalendar(Date date) {
		if (date != null) {
			Calendar cal = Calendar.getInstance();
			DateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
			format.format(date);
			cal = format.getCalendar();
			return cal;
		}
		return null;
	}

	public String getCalendarWithTimeValueForTransactionId(Calendar date) {
		if (date == null) {
			return "";
		}
		String output = null;
		try {
			// SimpleDateFormat sdf = new SimpleDateFormat("ddmmyyyyHHmmss");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			Calendar cal = (Calendar) date;
			sdf.setCalendar(cal);
			// output = sdf.format(cal.getTime());
			output = String.valueOf(cal.get(Calendar.YEAR));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return output;
	}

	public static String getCalendarWithoutTimeValueAsString(Calendar date) {
		if (date == null) {
			return "";
		}
		String output = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
			Calendar cal = (Calendar) date;
			sdf.setCalendar(cal);
			output = sdf.format(cal.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return output;
	}
	
	public static String getCalendarWithoutTimeValueAsString1(Calendar date) {
		if (date == null) {
			return "";
		}
		String output = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
			Calendar cal = (Calendar) date;
			sdf.setCalendar(cal);
			output = sdf.format(cal.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return output;
	}

	public static String getCalendarWithoutTimeValueAsString(Date date) {
		return getCalendarWithoutTimeValueAsString(convertDateToCalendar(date));
	}
	
	public static boolean compareDateWithToday(Calendar date) {
		if (date == null) {
			return false;
		}
		boolean flag = false;
		try {
			String today = HelperClass
					.getCalendarWithoutTimeValueAsString(Calendar.getInstance());
			String receivedCal = HelperClass
					.getCalendarWithoutTimeValueAsString(date);
			if (today.equals(receivedCal))
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	public String getCalendarWithTimeValueForCollectionSlip(Calendar date) {
		if (date == null) {
			return "";
		}
		String output = null;
		try {
			// SimpleDateFormat sdf = new SimpleDateFormat("ddmmyyyyHHmmss");
			SimpleDateFormat sdf = new SimpleDateFormat("MMyyyy");
			Calendar cal = (Calendar) date;
			sdf.setCalendar(cal);
			output = sdf.format(cal.getTime());
			// output = String.valueOf(cal.get(Calendar.YEAR));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return output;
	}

	/*public static List<String> breakString(String value) {
		if (StringUtil.isEmpty(value))
			return null;
		String[] splitString = value.split(" ");
		List<String> resultList = new ArrayList<String>();
		String result = "";
		for (int i = 0; i < splitString.length; i++) {
			String str = splitString[i];
			if (result.length() <= RegistrationConstants.NAME_STRING_MAX_LENGTH)
				result = result + " " + str;
			else {
				resultList.add(result);
				result = "";
				result = result + " " + str;
			}
			if (i == splitString.length - 1) {
				resultList.add(result);
			}

		}
		return resultList;
	}*/

	public static String formLine3(String namePart, String fullName) {
		int length = namePart.length();
		String leftPart = fullName.substring(length - 1);
		return leftPart;
	}

	public static String getTown(String addressLine4) {
		Map<String, String> dtoMap = new HashMap<String, String>();
		dtoMap.put("TOWN_CODE_1", "Town Code 1");
		dtoMap.put("TOWN_CODE_2", "Town Code 2");
		dtoMap.put("TOWN_CODE_3", "Town Code 3");
		dtoMap.put("TOWN_CODE_4", "Town Code 4");
		Iterator<String> itr = dtoMap.keySet().iterator();
		while (itr.hasNext()) {
			String key = itr.next();
			if (dtoMap.get(key).equals(addressLine4)) {
				return key;
			}
		}
		return null;
	}
	
	//EUNIKE START 20130712

	// EUNIKE START 20130712
	public static int renderTextWidth(String text) {
		String fontFamily = "Helvetica"; // "Verdana"
		Font font = new Font(fontFamily, Font.PLAIN, 10);
		BufferedImage image = new BufferedImage(640, 480,
				BufferedImage.TYPE_INT_RGB);
		Graphics g2 = image.getGraphics();
		g2.setFont(font);
		int width = SwingUtilities
				.computeStringWidth(g2.getFontMetrics(), text);
		return width;
	}

	public static List<String> breakStringWidth(String value, int maxWidth) {
		if (StringUtil.isEmpty(value))
			return null;
		String[] splitString = value.split(" ");
		int spaceWidth = renderTextWidth(" ");
		List<String> resultList = new ArrayList<String>();
		String result = splitString[0];
		for (int i = 1; i < splitString.length; i++) {
			String str = splitString[i];
			int widthAfterAdd = renderTextWidth(str) + renderTextWidth(result);

			if (widthAfterAdd <= (maxWidth - spaceWidth))
				result = result + " " + str;
			else {
				resultList.add(result);
				result = str;
			}
			if (i == splitString.length - 1) {
				resultList.add(result);
			}

		}
		return resultList;
	}
	// EUNIKE END 20130712
	
	
	public static String getSqlTimeStampAsString(java.sql.Timestamp date) {
		  if (date == null) {
		   return "";
		  }
		  String output = null;
		  try {
		   SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss"); 
		   output = sdf.format(date);
		  } catch (Exception e) {
		   e.printStackTrace();
		  }
		  return output;
		 }
	//EUNIKE END 20130712
	

	// EUNIKE END 20130712

	public static boolean validateNin(String nin) {
		String ninPart = nin.substring(0, nin.length() - 1);
		int sum = 0;
		for (int i = 2; i <= 13; i++) {
			int num = (Integer.parseInt(ninPart.substring(i - 1, i)))
					* (15 - i);
			sum = sum + num;
		}
		int totalSum = sum
				+ (((int) (ninPart.substring(0, 1).toCharArray()[0])) - 55)
				* 14;
		float rest = 17 - (totalSum % 17);
		int finalRest = (int) (rest);
		String lastChar;
		if (finalRest == 17) {
			lastChar = "0";
		} else if (finalRest > 0 && finalRest < 10) {
			lastChar = String.valueOf(finalRest);
		} else
			lastChar = String.valueOf((char) (finalRest + 55));
		ninPart = ninPart + lastChar;
		if (ninPart.equals(nin))
			return true;
		return false;
	}

	public static String getDateStringForRptQuery(String date_str) {
		try {
			DateFormat formatter;
			Date date;
			formatter = new SimpleDateFormat("dd-MMM-yyyy");
			date = (Date) formatter.parse(date_str);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			DateFormat sqlFormat = new SimpleDateFormat("yyyyMMdd");
			String output = sqlFormat.format(cal.getTime());
			return output;
		} catch (ParseException e) {
			System.out.println("Exception :" + e);
			return null;
		}
	} 
	
	public static byte[] getBytesFromFile(File file) throws IOException {

		InputStream is = new FileInputStream(file);
		long length = file.length();
		if (length > Integer.MAX_VALUE) {
			System.out.println("File is too large to process");
			is.close();
			return null;
		}

		byte[] bytes = new byte[(int) length];
		int offset = 0;
		int numRead = 0;
		while ((offset < bytes.length)
				&& ((numRead = is.read(bytes, offset, bytes.length - offset)) >= 0)) {

			offset += numRead;

		}

		if (offset < bytes.length) {
			is.close();
			throw new IOException("Could not completely read file "
					+ file.getName());
		}

		is.close();

		return bytes;
	}
	
	public static String getCalendarValueAsStringForTransactionStatus(Calendar date) {
		if (date == null) {
			return "";
		}
		String output = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
			Calendar cal = (Calendar) date;
			sdf.setCalendar(cal);
			output = sdf.format(cal.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return output;
	}
	public static JobExecutionDetails formExecutionDetail(String msg) {
		JobExecutionDetails dtlStart = new JobExecutionDetails();
		dtlStart.setMessage(msg);
		dtlStart.setDetailType("I");
		return dtlStart;

	}
	public static JobExecutionHistory formExecutionHistory(String jobName,Calendar startDate, String errmsg) {
		JobExecutionHistory executionHist = new JobExecutionHistory();
		executionHist.setJobId(jobName);
		executionHist.setExecutionDate(startDate.getTime());
		executionHist.setStatus(0);
		if (StringUtil.isEmpty(errmsg)) {
			executionHist.setMessage(jobName + " completed successfully.");
		} else {
			executionHist.setMessage(errmsg);
		}
		executionHist.setCompleteDate(Calendar.getInstance().getTime());
		return executionHist;

	}
	
	public static String getHostName() {
		String localhostname = "";
		try {
			localhostname = System.getenv("COMPUTERNAME");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return localhostname;
	}
	//phúc thêm 28/3/2019
	public static String convertDateToString1(Date date) {
		if (date != null) {
			try {
				DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				String dateS = format.format(date);
				return dateS;
				
			} catch (Exception e) {
				System.out.println("Exception :" + e);
				return null;
			}
		}
		return null;
	}
	
	//phúc thêm 28/3/2019
		public static String convertDateToString2(Date date) {
			if (date != null) {
				try {
					DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
					String dateS = format.format(date);
					return dateS;
					
				} catch (Exception e) {
					System.out.println("Exception :" + e);
					return null;
				}
			}
			return null;
		}
		
	//hoald 17/06/2020
		public static String convertDateType3(Date date) {
			if (date != null) {
				try {
					DateFormat format = new SimpleDateFormat("yyyyMMdd");
					String dateS = format.format(date);
					return dateS;
				} catch (Exception e) {
					return null;
				}
			}
			return null;
		}
	
	public static Date convertStringToDate1(String cal) {
		try {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date date = dateFormat.parse(cal);
			return date;
		} catch (Exception e) {
			System.out.println("Exception :" + e.getMessage());
			return null;
		}

	}
	
	public static Date convertStringToDate3(String cal) {
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = dateFormat.parse(cal);
			return date;
		} catch (Exception e) {
			System.out.println("Exception :" + e.getMessage());
			return null;
		}

	}
	
	public static Date convertStringToDate2(String cal) {
		try {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = dateFormat.parse(cal);
			return date;
		} catch (Exception e) {
			System.out.println("Exception :" + e.getMessage());
			return null;
		}

	}
	
	public static Date convertStringToDate(String date, Integer length){
		try {
			String type = "";
			if(length == 8){
				type = STR_FORMAT_DATE_yyyyMMdd;
			}
			if(length > 8){
				type = STR_FORMAT_DATE_yyyyMMddHHmmss;
			}
			DateFormat dateFormat = new SimpleDateFormat(type);
			Date strDate = dateFormat.parse(date);
			return strDate;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
	
	public static String convertStringToString2(String date_str){
		String[] date = date_str.split("/");
		String dateNumber = date[2].toString() + date[1].toString() + date[0].toString();
		return dateNumber;
	}
	
	public static String convertStringToString3(String date_str){
		String[] date = date_str.split("/");
		String dateNumber = date[0].toString() + "/" + date[1].toString() + "/" + date[2].toString().subSequence(date[2].toString().length() - 2, date[2].toString().length());
		return dateNumber;
	}
	
	public static String convertStringToString_ForPerson(String date_str){
		try{
		String[] date = date_str.split("/");
		String dateNumber = date[2].toString() + date[1].toString() + date[0].toString();
		return dateNumber;
		}catch(Exception e){
			
		}
		return "";
	}
	
	//HoaLD
		public static String convertDocumentStatus(String documentStatus) {
			if (StringUtils.isNotBlank(documentStatus)) {
				switch (documentStatus) {
				case "PACKED":
					documentStatus = "Đóng gói";
					break;
				case "PERSONALIZED":
					documentStatus = "Hoàn thành cá thể hóa";
					break;
				case "ISSUANCE":
					documentStatus = "Phát hành";
					break;
				case "CANCELLED":
					documentStatus = "Hủy";
					break;
				case "NONE":
					documentStatus = "Khóa";
					break;
				default:
					break;
				}
			}
			return documentStatus;
		}
		//HoaLD
		public static String convertTransactionStatus(String transactionStatus) {
			if (StringUtils.isNotBlank(transactionStatus)) {
				switch (transactionStatus) {
				case "RIC_UPLOADED":
					transactionStatus = "Hồ sơ mới khởi tạo";
					break;
				case "WA":
					transactionStatus = "Đang chờ kiểm tra afis";
					break;
				case "FG":
					transactionStatus = "Chờ phân công";
					break;
				case "INVESTIGATION_ASSIGNED":
					transactionStatus = "Đã phân công";
					break;
				case "INVESTIGATION_PROCESSING":
					transactionStatus = "Hồ sơ đang xử lý";
					break;
				case "INVESTIGATION_SAVED":
					transactionStatus = "Đã xử lý";
					break;
				case "CREATED_B":
					transactionStatus = "Đã tạo danh sách B ";
					break;
				case "APPROVE_D":
					transactionStatus = "Đã duyệt Cấp";
					break;
				case "APPROVE_C":
					transactionStatus = "Bổ sung";
					break;
				case "APPROVE_K":
					transactionStatus = "Từ chối";
					break;
				case "PERSO_REGISTER_COMPLETED":
					transactionStatus = "Đã  đăng ký perso";
					break;
				case "PERSO_PRINTED":
					transactionStatus = "Đã in hộ chiếu";
					break;
				case "CREATED_C":
					transactionStatus = "Đã lập C";
					break;
				case "RIC_RECEIVED":
					transactionStatus = "Đã nhận hộ chiếu";
					break;
				case "RIC_ISSUED":
					transactionStatus = "Đã trả kết quả";
					break;

				default:
					break;
				}
			}
			return transactionStatus;
		}
		//HoaLD
		public static String convertApproveStage(String approveStage) {
			if (StringUtils.isNotBlank(approveStage)) {
				switch (approveStage) {
				case "D":
					approveStage = "Đã duyệt cấp HC";
					break;
				case "C":
					approveStage = "Bổ sung hồ sơ";
					break;
				case "K":
					approveStage = "Từ chối cấp HC";
					break;
				default:
					break;
				}
			}
			return approveStage;
		}
		
		public static String convertDateToString(Date date) {
			if (date != null) {
				DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				String dateS = format.format(date);
				return dateS;
			}
			return null;
		}
		
		public static String convertDateTimeToString(Date date) {
			if (date != null) {
				DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				String dateS = format.format(date);
				return dateS;
			}
			return null;
		}
		
		public static Integer getYearInDate(Date date) {
			if (date != null) {
				DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				String fmDate = format.format(date);
				Integer year = Integer.parseInt(fmDate.split("/")[2]);
				return year;
			}
			return 0;
		}
		
		public static String convertGender(String gender){
			switch (gender) {
			case "M":
				gender = "Nam";
				break;
			case "F":
				gender = "Nữ";
				break;
			default:
				gender = "Không xác định";
				break;
			}
			return gender;
		}

		public static String convertCancelType(String cancelType) {
			switch (cancelType) {
			case "LOST":
				cancelType = "mất";
				break;
			case "RENEW":
				cancelType = "hủy";
				break;
			case "PRINT_FAIL":
				cancelType = "hỏng";
				break;
			default:
				break;
			}
			return cancelType;
		}

		public static String convertBorderGateKind(String borderGateKind) {
			switch (borderGateKind) {
			case "S":
				borderGateKind = "Cảng hàng không";
				break;
			case "K":
				borderGateKind = "Cửa khẩu đất liền";
				break;
			case "C":
				borderGateKind = "Cảng biển";
				break;
			default:
				break;
			}
			return borderGateKind;
		}

		public static String convertImmiType(String immiType) {
			switch (immiType) {
			case "X":
				immiType = "Xuất";
				break;
			case "N":
				immiType = "Nhập";
				break;
			default:
				break;
			}
			return immiType;
		}
		
	public static String loadDOB(String dateOfBirth, String defDateOfBirth){
		switch (defDateOfBirth) {
		case "M":
			dateOfBirth = dateOfBirth.substring(3, dateOfBirth.length());
			break;
		case "Y":
			dateOfBirth = dateOfBirth.substring(dateOfBirth.length() - 4, dateOfBirth.length());
			break;	
		default:
			break;
		}
		
		return dateOfBirth;
	}
}
