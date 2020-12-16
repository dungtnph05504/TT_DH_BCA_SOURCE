package com.nec.asia.nic.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.JAXBContext;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.nec.asia.nic.dx.dto.DataExchangeDTO;
import com.nec.asia.nic.framework.common.JaxbXmlConvertor;
import com.nec.asia.nic.framework.exception.JaxbXmlConvertorException;



/**
 * @author chris_wong
 *
 */
/*
 * Modification History:
 * 
 * 29 Nov 2013 (chris): init class
 * 24 Feb 2014 (chris): change to method to encodeString() synchronized
 */
public class LogDataXmlConvertor extends JaxbXmlConvertor {
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(LogDataXmlConvertor.class);
	
	//JAXBContext is thread safe, so instantiate only once
	/** The jaxb context. */
	private static volatile JAXBContext jaxbContext;
	
	/** The Base64 utility for encode and decode **/
	private Base64 base64Util = new Base64(0);
	
	/**
	 * Instantiates a new event data xml convertor.
	 *
	 * @throws RuntimeException the runtime exception
	 */
	public LogDataXmlConvertor() throws RuntimeException {
		try {
			if(jaxbContext==null) 
		    {
				synchronized(LogDataXmlConvertor.class) {
					if(jaxbContext==null) {
						jaxbContext = JAXBContext.newInstance(new Class[] {
								DataExchangeDTO.class
						});
					}
				}
		    }
		}
		catch(Throwable th) {
			logger.fatal("Error in EventDataXmlConvertor.init: "+th.getMessage(), th);
			throw new RuntimeException("Error in EventDataXmlConvertor.init: "+th.getMessage(), th);
		}			
	}
	
	
	/* (non-Javadoc)
	 * @see com.nec.asia.baf.comp.common.xml.JaxbXmlConvertor#getJAXBContext()
	 */
	@Override
	protected JAXBContext getJAXBContext() {
		// TODO Auto-generated method stub
		return jaxbContext;
	}
	
	/**
	 * Marshal.
	 *
	 * @param obj the obj
	 * @param defaultvalue the defaultvalue
	 * @return the string
	 */
	public String marshal(Object obj, String defaultvalue) {
		try {
			return super.marshal(obj);
		}
		catch(JaxbXmlConvertorException ex) {
			return defaultvalue;
		}
	}
	
	public String computeCheckSum(String input) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    	String checkSum = null;
    	if (StringUtils.isNotBlank(input)) {
    		input = input.trim();
    		MessageDigest messageDigest = MessageDigest.getInstance("MD5");//"SHA-256");
    		messageDigest.reset();
			byte origData[] = input.getBytes("UTF-8");			
			messageDigest.update(origData);
			byte byteData[] = messageDigest.digest();
			// convert the byte to hex format method 1
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				//sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
				sb.append(String.format("%02X", byteData[i] & 0xFF));
			}
			checkSum = sb.toString();
		}
    	return checkSum;
    }
	
	public synchronized String encodeString(String input) {
		String encodeString = null;
		if (StringUtils.isNotBlank(input)) {
			input = input.trim();
			encodeString = base64Util.encodeToString(input.getBytes());
		}
		return encodeString;
	}
	
	public synchronized String decodeString(String input) {
		String decodeString = null;
		if (StringUtils.isNotBlank(input)) {
			input = input.trim();
			byte[] decodeBinary = base64Util.decode(input);//.decodeBase64(input);
			decodeString = new String(decodeBinary);
		}
		return decodeString;
	}
}
