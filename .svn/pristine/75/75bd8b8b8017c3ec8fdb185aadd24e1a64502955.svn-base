package com.nec.asia.nic.comp.ws.log.domain;

public class CreateMessageException {

	public static String createMessageException(Exception e){
		String mess = null;
		if (e != null) {
			mess  = e.getMessage();
			if (e.getCause() != null) {
				mess = mess + "\n [subtrack] \n" + e.getCause().getMessage();
				if (e.getCause().getCause() != null) {
					mess = mess + "\n [subtrack] \n" + e.getCause().getCause().getMessage();
				}
			}
			
		}
		return mess;
	}
}
