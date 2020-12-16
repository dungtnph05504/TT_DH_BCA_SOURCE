package com.nec.asia.nic.investigation.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="EPP_PERSONS")
@XmlAccessorType(XmlAccessType.FIELD)
public class InfoPersons implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlElement(name="EPP_PERSON")
	private List<InfoPerson> infoPersons = null;
	public InfoPersons() {
		super();
	}
	public List<InfoPerson> getInfoPersons() {
		return infoPersons;
	}
	public void setInfoPersons(List<InfoPerson> infoPersons) {
		this.infoPersons = infoPersons;
	}

}
