package com.nec.asia.nic.comp.trans.domain;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "FamilyData")
@XmlAccessorType(XmlAccessType.FIELD)
public class FamilyData {

	@XmlElement(name = "fatherName") 
	private String fatherName;
	@XmlElement(name = "motherName") 
	private String motherName;
	@XmlElement(name = "spouseName") 
	private String spouseName;
	@XmlElement(name = "fatherDob") 
	private Date fatherDob;
	@XmlElement(name = "motherDob") 
	private Date motherDob;
	@XmlElement(name = "spouseDob") 
	private Date spouseDob;
	
	public FamilyData(){
		
	}
	
	public FamilyData(String fatherName, String motherName, String spouseName,
			Date fatherDob, Date motherDob, Date spouseDob) {
		super();
		this.fatherName = fatherName;
		this.motherName = motherName;
		this.spouseName = spouseName;
		this.fatherDob = fatherDob;
		this.motherDob = motherDob;
		this.spouseDob = spouseDob;
	}
	
	public String getFatherName() {
		return fatherName;
	}
	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}
	public String getMotherName() {
		return motherName;
	}
	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}
	public String getSpouseName() {
		return spouseName;
	}
	public void setSpouseName(String spouseName) {
		this.spouseName = spouseName;
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
	
	
}
