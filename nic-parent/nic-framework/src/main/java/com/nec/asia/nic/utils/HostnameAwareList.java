package com.nec.asia.nic.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * 
 * @author Mahesh
 *
 */
public class HostnameAwareList{
	Logger logger = Logger.getLogger(HostnameAwareList.class);
	public static final String DEFAULT_KEY="default";
	
	private boolean fallbackToDefault=false;
	private List<String> defaultValueList;

	private String hostname;
	private List<String> valueList;
	
	
	public HostnameAwareList() throws UnknownHostException {
		 hostname = InetAddress.getLocalHost().getHostName().toUpperCase();
		 logger.debug("Resolved hostname: "+hostname);
		 
		 if(hostname==null || hostname.trim().length()==0) {
			 throw new UnknownHostException("Error resolving hostname, hostname is null or empty: "+hostname);
		 }
		 
	}
	
	public void setHostnameValueListMap(Map<String, List<String>> hostnameValueListMap){
		logger.debug("Current configured valueList keys: "+hostnameValueListMap.keySet());
		
		defaultValueList =  hostnameValueListMap.get(DEFAULT_KEY);
		
		for(String hostnames : hostnameValueListMap.keySet()) 
		{
			valueList = hostnameValueListMap.get(hostnames);
			hostnames=hostnames.toUpperCase().trim();
			if(hostnames.equals(hostname) || hostnames.startsWith(hostname+",") || hostnames.indexOf(","+hostname+",")>-1 || hostnames.endsWith(","+hostname)) {
				logger.debug("Found valueList for hostname: "+hostname+" matching with hostnames key: "+hostnames);
				logger.debug("ValueList for hostname: "+hostname+", values: "+valueList);
				break;
			}
			else {
			    valueList = null;
			}
		}
		
		if(valueList==null) {
			logger.warn("Cannot find valueList for hostname: "+hostname+". Current configured valueList keys: "+hostnameValueListMap.keySet());
		}
	}
	
	/**
	 * @param fallbackToDefault the fallbackToDefault to set
	 */
	public void setFallbackToDefault(boolean fallbackToDefault) {
		this.fallbackToDefault = fallbackToDefault;
		logger.debug("In setFallbackToDefault, fallbackToDefault: "+fallbackToDefault);
	}

	public List getValueList() {
		if(valueList==null) {
			logger.warn("Cannot find valueList for hostname: "+hostname);
			logger.debug("fallbackToDefault: "+fallbackToDefault);
			if(fallbackToDefault) {
				logger.warn("Trying to fallback on default list");
				if(defaultValueList==null) {
					throw new IllegalArgumentException("Value list not configured for <hostname> or default: "+hostname);
				}
				else {
					logger.debug("DefaultValueList values: "+defaultValueList);
					return defaultValueList;
				}
			}
			else {
				throw new IllegalArgumentException("Value list not configured for <hostname>: "+hostname);
			}
		}
		return valueList;
	}

}
