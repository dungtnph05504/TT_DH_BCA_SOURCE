package service.transaction.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="ResponseBase")
@XmlType(name="ResponseBase", propOrder={"code", "message"})
public class ResponseBase {
	
	private String code;
	private String message;
		
	public ResponseBase(String code, String message) {
		this.code = code;
		this.message = message;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "{\"code\": \"" + code + "\", \"message\": \"" + message + "\"}";
	}
	
	public ResponseBase() {
	}	
}
