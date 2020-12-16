package service.perso.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "PropertyModel")
@XmlType(name = "PropertyModel", propOrder = { "code", "message" })
public class PropertyModel {

	private int code;
	private String message;

	public PropertyModel() {

	}

	public PropertyModel(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
