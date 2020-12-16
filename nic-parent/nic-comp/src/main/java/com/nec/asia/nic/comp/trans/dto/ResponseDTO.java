/**
 * 
 */
package com.nec.asia.nic.comp.trans.dto;

import java.io.Serializable;

/**
 * @author chris_wong
 *
 */
public class ResponseDTO implements Serializable {

	private static final long serialVersionUID = 255026558806774559L;
	
	private String serviceCode;
    private String serviceMessage;
    
	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	public String getServiceMessage() {
		return serviceMessage;
	}
	public void setServiceMessage(String serviceMessage) {
		this.serviceMessage = serviceMessage;
	}
}
