package com.nec.asia.nic.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Date & Time tool
 * 
 * @author bright_zheng
 *
 */
public class DateTimeToolkit{    
    /**
     * Get Current java.sql.Date instance
     * @return java.sql.Date object of current datetime
     */
    public static java.sql.Date getCurrentSqlDate(){
    	java.util.Date date = new java.util.Date();
    	return new java.sql.Date(date.getTime());
    }
    
    /**
     * Get Current java.sql.Date instance by a specific String
     * @return java.sql.Date object of current datetime
     */
    public static java.sql.Date getCurrentSqlDate(String dateString){
    	return java.sql.Date.valueOf(dateString);
    }

    public static Timestamp currentTime(int i){
        return new Timestamp(System.currentTimeMillis() + (long)i * 0x5265c00L);
    }

    public static java.sql.Date currentDate(){
        return new java.sql.Date(System.currentTimeMillis());
    }

    public static java.sql.Date currentDate(int i){
        return new java.sql.Date(System.currentTimeMillis() + (long)i * 0x5265c00L);
    }
    
    public static java.util.Date toDate(String date) throws ParseException{
    	return toDate(date,"yyyy-MM-dd");
    }
    
    public static java.util.Date toDate(String date, String format) throws ParseException{
    	DateFormat dateFormat = new SimpleDateFormat(format);
    	dateFormat.setLenient(false);
    	return dateFormat.parse(date);
    }
    
    public static java.util.Date toDateTime(String date) throws ParseException{
    	return toDate(date,"yyyy-MM-dd HH:mm:ss");
    }
    
    public static java.util.Date toDateTime(String date, String format) throws ParseException{
    	DateFormat dateFormat = new SimpleDateFormat(format);
    	return dateFormat.parse(date);
    }
    
    public static String toString(java.util.Date date) throws ParseException{
    	return toString(date,"yyyy-MM-dd HH:mm:ss");
    }
    
    public static String toString(java.util.Date date, String format) throws ParseException{
    	DateFormat dateFormat = new SimpleDateFormat(format);
    	return dateFormat.format(date);
    }

}
