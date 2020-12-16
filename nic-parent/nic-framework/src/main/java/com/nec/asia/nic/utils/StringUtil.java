package com.nec.asia.nic.utils;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.log4j.Logger;


/**
 * <p>
 * Title: StringUtil
 * </p>
 * 
 * <p>
 * Description: This class is a utility class helps to operate string.
 * <p>
 * <br>
 * Tutorial: <br>
 * String s = ""; <br>
 * if (StringUtil.isEmpty(s)){ <br>
 * s = "xxx"; <br>}
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: NEC
 * </p>
 * 
 * @zhong wen qing
 * @version 1.1
 * 
 * Modifications:
 * 2008/08/15: (Alvin) TokenizeString() added.
 *  
 */
public class StringUtil {
	private static final Logger _logger = Logger.getLogger(StringUtil.class);
	/**
	 * Description: This method check if string is null or blank.
	 *
	 * @param s the s
	 * @return true if the string is null or blank
	 */
	public static boolean isEmpty(String s) {
		return (s == null || ("".equals(s.trim())));
	}

	/**
	 * Description: This method converts null value of string to blank.
	 *
	 * @param s the s
	 * @return the string
	 */
	public static String nullToEmpty(String s) {
		if (s == null) {
			return "";
		} else {
			return s.trim();
		}

	}

	/**
	 * Description: This method converts blank value of string to null.
	 *
	 * @param s the s
	 * @return the string
	 */
	public static String emptyToNull(String s) {
		if ("".equals(s)) {
			return null;
		} else {
			return s.trim();
		}
	}

	/**
	 * <br>
	 * Tutorial code: <br>
	 * String s = "ss,dd,ss,aa,qq"; <br>
	 * String[] l = StringUtil.splitString(s,","); <br>
	 * //The size of returned String[] should be 5 <br>
	 * //The size of returned String[] should be 0 if s is null or splitor is
	 * null
	 *
	 * @param s the s
	 * @param splitor the splitor
	 * @return the string[]
	 */
	public static String[] splitString(String s, String splitor) {

		// log.debug("passed in string is :"+s);
		// log.debug("Splitor is :"+splitor);

		List l = new ArrayList();

		if ((s != null) && (splitor != null)) {

			// note: here if splitor = ",fddf",StringTokenizer still treat it as
			// ","
			StringTokenizer token = new StringTokenizer(s, splitor);

			while (token.hasMoreTokens()) {
				l.add(token.nextToken());
			}

		}

		String[] ss = new String[l.size()];
		for (int i = 0; i < l.size(); i++) {
			ss[i] = (String) l.get(i);
		}

		return ss;
	}

	/**
	 * <br>
	 * Tutorial code: <br>
	 * String[] s = new String[]{"ss","dd","ss","aa","qq"}; <br>
	 * String result = StringUtil.mergeString(s,","); <br>
	 * //The value of the result string should be "ss,dd,ss,aa,qq" <br>
	 * //The size of returned String should be null if s is null or splitor is
	 * null
	 *
	 * @param s the s
	 * @param splitor the splitor
	 * @return the string
	 */
	public static String mergeString(String[] s, String splitor) {

		// log.debug("passed in string array is :"+s);
		// log.debug("Splitor is :"+splitor);

		if ((s != null) && (splitor != null)) {

			String tmp = "";

			for (int i = 0; i < s.length; i++) {
				tmp = tmp + s[i] + splitor;
			}
			tmp = tmp.substring(0, tmp.length() - 1);

			return tmp;

		} else {

			return null;
		}
	}
	
	/**
	 * Merge string.
	 *
	 * @param objColl the obj coll
	 * @param splitor the splitor
	 * @return the string
	 */
	public static String mergeString(Collection objColl, String splitor) 
	{
		if (objColl != null && objColl.size()>0 && splitor != null) 
		{
			String tmp = "";
			for (Object obj:objColl) 
			{
				if(obj!=null) {
					tmp = tmp + obj.toString() + splitor;
				}
			}
			tmp = tmp.equals("")?null:tmp.substring(0, tmp.length() - splitor.length());
			return tmp;
		} else {

			return null;
		}
	}
	
	/**
	 * Wrap text.
	 *
	 * @param str the str
	 * @param lineLength the line length
	 * @return the string
	 */
	public static String wrapText(String str, int lineLength)
    {
        if (str !=null) {
	        String newLineChars = "\n";
	        StringTokenizer lineTokenizer = new StringTokenizer(str, newLineChars, true);
	        StringBuffer stringBuffer = new StringBuffer();
	
	        while (lineTokenizer.hasMoreTokens()) {
	            try {
	                String nextLine = lineTokenizer.nextToken();
	                if (nextLine.length() > lineLength) {
	                    // This line is long enough to be wrapped.
	                    nextLine = WordUtils.wrap(nextLine,lineLength,  newLineChars, true);
	                }
	                stringBuffer.append(nextLine);
	            } catch (NoSuchElementException nsee) {
	                // thrown by nextToken(), but I don't know why it would
	                nsee.printStackTrace();
	                break;
	            }
	        }
	        return stringBuffer.toString();
        }        
        return null;
    }
	
    /** The Constant INFO_SEPARATOR. */
    private static final String INFO_SEPARATOR="|,";
	
	/**
	 * Tokenize string.
	 *
	 * @param inval the inval
	 * @return the vector
	 */
	public static Vector TokenizeString(String inval){
		return TokenizeString(inval, INFO_SEPARATOR);
	}
	
    /**
     * Tokenize string.
     *
     * @param inval the inval
     * @param separator the separator
     * @return the vector
     */
    public static Vector TokenizeString(String inval, String separator){
      Vector result=new Vector();

      if (inval!=null){
        java.util.StringTokenizer st= new java.util.StringTokenizer(inval, separator);

      // If there is a token, append the token and spacing to the result.
        String value="";
        while (st.hasMoreTokens()) {
          value = (String)st.nextElement();
          result.addElement(value);
        }
      }
      return result;
    }
    
    /**
     * This method splits string with specified separator, return tokens contain even empty string
     * Unlike string.split
     *
     * @param str the str
     * @param seprator the seprator
     * @return the list
     */
	public static List<String> splitToList(String str, String seprator) {
		int startIndex=0;
		List<String> strList = new ArrayList<String>();
		boolean loopFlag = str!=null && str.trim().length()>0;
		while (loopFlag) {
			int endIndix = str.indexOf(seprator,startIndex);
			if(endIndix>-1) {
				strList.add(str.substring(startIndex, endIndix));
				startIndex = endIndix+1;
			}else {
				String tmpStr = str.substring(startIndex);
				if(tmpStr.endsWith("\r")) {
					tmpStr=tmpStr.substring(0, tmpStr.length()-1);
				}
				strList.add(tmpStr);
				break;
			}
		}
		return strList;
	}
	
	/**
	 * Removes the null chars.
	 *
	 * @param str the str
	 * @return the string
	 */
	public static final String removeNullChars(String str) {
		return (str==null)?str:str.replace('\0', ' ');
	}
	
	/**
	 * String to map.
	 *
	 * @param mapStr the map str
	 * @param keyValuePairSep the key value pair sep
	 * @param entrySep the entry sep
	 * @return the map
	 */
	public static Map<String, String> stringToMap(String mapStr, String keyValuePairSep, String entrySep) {
		HashMap<String, String> map = new HashMap<String, String>();
		if(mapStr!=null) {
			String keyValueArr[] = mapStr.split(entrySep);
			if(keyValueArr!=null) {
				for(String keyValue:keyValueArr) {
					String keyValuePair[] = keyValue.split(keyValuePairSep);
					if(keyValuePair!=null && keyValuePair.length==2) {
						map.put(keyValuePair[0], keyValuePair[1]);
					}
				}
			}
		}
		return map;
	}
	
	/**
	 * Map to string.
	 *
	 * @param map the map
	 * @param keyValuePairSep the key value pair sep
	 * @param entrySep the entry sep
	 * @return the string
	 */
	public static String mapToString(Map<String, String> map, String keyValuePairSep, String entrySep) {
		String result = null;
		if(map!=null && map.size()>0) {
			StringBuffer sb = new StringBuffer();
			boolean appendSep = false;
			for(Entry<String, String> entry:map.entrySet()) {
				if(appendSep) {
					sb.append(entrySep);
				}
				sb.append(entry.getKey()).append(keyValuePairSep).append(entry.getValue());
				appendSep = true;
			}
			result = sb.toString();
		}
		return result;
	}
	
	/**
	 * Checks if is valid numeric.
	 *
	 * @param valueStr the value str
	 * @return true, if is valid numeric
	 */
	public static boolean isValidNumeric(String valueStr) {
		return (StringUtils.isNumeric(valueStr) && valueStr.length()>0);
	}
	
	/**
	 * String to int.
	 *
	 * @param valueStr the value str
	 * @return the integer
	 */
	public static Integer stringToInt(String valueStr) {
		if(isValidNumeric(valueStr)) {
			try {
				return Integer.parseInt(valueStr);
			}
			catch(Throwable th) {
				_logger.error("Error converting string('"+valueStr+"') to int: "+th.getMessage(), th);
			}
		}
		return null;
	}

	/**
	 * String to int.
	 *
	 * @param valueStr the value str
	 * @param defaultValue the default value
	 * @return the int
	 */
	public static int stringToInt(String valueStr, int defaultValue) {
		Integer value = stringToInt(valueStr);
		return (value==null)? defaultValue:value.intValue();
	}

    /**
     * String to Properties
     * 
     * @param valueStr
     * @return the properties
     */
    public static Properties stringToProperties(String valueStr) {
        try {
            Properties properties = new Properties();
            properties.load(new StringReader(valueStr));
            return properties;
        } catch (IOException e) {
            _logger.error("Error converting string to properties: " + e.getMessage(), e);
        }
        return null;
    }
    
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {

		StringUtil util = new StringUtil();

	}

}
