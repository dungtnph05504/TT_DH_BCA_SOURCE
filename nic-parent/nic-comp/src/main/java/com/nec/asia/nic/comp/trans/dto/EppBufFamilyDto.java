package com.nec.asia.nic.comp.trans.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "EppBufFamilyDto")
@XmlAccessorType(XmlAccessType.FIELD)
public class EppBufFamilyDto {

	@XmlElement(name = "fatherSurname") 
	private String fatherSurname;
	@XmlElement(name = "motherSurname") 
	private String motherSurname;
	@XmlElement(name = "spouseSurname") 
	private String spouseSurname;
	@XmlElement(name = "fatherDob") 
	private Date fatherDob;
	@XmlElement(name = "motherDob") 
	private Date motherDob;
	@XmlElement(name = "spouseDob") 
	private Date spouseDob;
	@XmlElement(name = "epp_person") 
	private List<EppPersonDto> epp_person = new ArrayList<EppPersonDto>();
	
	public String getFatherSurname() {
		return fatherSurname;
	}
	public void setFatherSurname(String fatherSurname) {
		this.fatherSurname = fatherSurname;
	}
	public String getMotherSurname() {
		return motherSurname;
	}
	public void setMotherSurname(String motherSurname) {
		this.motherSurname = motherSurname;
	}
	public String getSpouseSurname() {
		return spouseSurname;
	}
	public void setSpouseSurname(String spouseSurname) {
		this.spouseSurname = spouseSurname;
	}
	public Date getFatherDob() {
		return fatherDob;
	}
	public void setFatherDob(Date fatherDob) {
		this.fatherDob = fatherDob;
	}
	public Date getMotherDob() {
		return motherDob;
	}
	public void setMotherDob(Date motherDob) {
		this.motherDob = motherDob;
	}
	public Date getSpouseDob() {
		return spouseDob;
	}
	public void setSpouseDob(Date spouseDob) {
		this.spouseDob = spouseDob;
	}
	public List<EppPersonDto> getEpp_person() {
		return epp_person;
	}
	public void setEpp_person(List<EppPersonDto> epp_person) {
		this.epp_person = epp_person;
	}
	
	
}
