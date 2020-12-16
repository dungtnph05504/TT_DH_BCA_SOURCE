package service.transaction.model;


import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



@XmlRootElement(name="Response")
@XmlType(name="Response", propOrder={"data", "code", "message"})
public class Response<T> {	
	private String code;
	private String message;
	private T data;	
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
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "{\"code\" : " + code + ", \"message\" : " + message + "}";
	}
	
	
	
}
