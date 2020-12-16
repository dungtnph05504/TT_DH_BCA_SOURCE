package com.nec.asia.nic.dx.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/*
 * Modification History:
 * 
 * 14 Jan 2016 (chris): to support multiple format
 */
public class DateTimeAdapter extends XmlAdapter<String, Date> {

//	public Date unmarshal(String value) {
//		return (org.apache.cxf.xjc.runtime.DataTypeAdapter.parseDateTime(value));
//	}
//
//	public String marshal(Date value) {
//		return (org.apache.cxf.xjc.runtime.DataTypeAdapter.printDateTime(value));
//	}
	
	private SimpleDateFormat dateTimeFormat 				= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
	private SimpleDateFormat dateTimeFormatWithoutZone 		= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
	private SimpleDateFormat dateTimeFormatWithoutMS 		= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
	private SimpleDateFormat dateTimeFormatWithoutMSZone 	= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	private SimpleDateFormat dateFormat					 	= new SimpleDateFormat("yyyy-MM-dd");
	//not supporting format: timeFormat			"HH:mm:ss.SSSX", "HH:mm:ss.SSS", "HH:mm:ss.X", "HH:mm:ss"
	//not supporting format: monthFormat		"yyyy-MM"
	//not supporting format: yearFormat			"yyyy"
	
	
	private SimpleDateFormat[] dataFormats = {dateTimeFormat, dateTimeFormatWithoutZone, dateTimeFormatWithoutMS, dateTimeFormatWithoutMSZone, dateFormat};

    public Date unmarshal(String value) {
        for (SimpleDateFormat dateFormat : dataFormats) {
	        try {
	            return dateFormat.parse(value);
	        } catch (Exception e) {
	        }
        }
        return null;
    }

    public String marshal(Date value) {
        try {
            return dateTimeFormat.format(value);
        } catch (Exception e) {
            return null;
        }
    }

}