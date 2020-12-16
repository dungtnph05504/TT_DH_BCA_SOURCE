package com.nec.asia.nic.comp.perso.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
//@XmlRootElement(name="Value")
public class ValueDTO implements Serializable {
	
	
	public ValueDTO() { }
	
	public ValueDTO(String inputFormat) {
		this.inputFormat = inputFormat;
	}
	
	
	@XmlAttribute(name = "InputFormat")
	protected String inputFormat;
	
	@XmlValue
    protected String data;

	public String getInputFormat() {
		return inputFormat;
	}

	public void setInputFormat(String inputFormat) {
		this.inputFormat = inputFormat;
	}
	
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
    
	
}
