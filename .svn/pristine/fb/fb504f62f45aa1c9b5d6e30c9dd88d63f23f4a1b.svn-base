package com.nec.asia.nic.comp.job.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author chris_wong
 * <p>DTO for Perso Data Preparation and Perso Submission</p>
 */
/**
 * Sample Applicant Details XML: for CC Exception Handling.
<ApplicantDetails>
    <Surname>APPADOOD</Surname>
    <Firstname>KELLY-ANNE</Firstname>
    <Surnameatbirth>APPADOO</Surnameatbirth>
    <Dob>12/02/1972</Dob>
    <Gender>F</Gender>
    <MaritalStatus>M</MaritalStatus>
    <ExceptionDescription>Verified the Proof Document to update the Surname</ExceptionDescription>
</ApplicantDetails>
 * New Sample Applicant Details:
<ApplicantDetails>
    <Surname>Pandoo</Surname>
    <Firstname>Laxmi</Firstname>
    <Surnameatbirth></Surnameatbirth>
    <Dob>04-10-1982</Dob>
    <Gender>F</Gender>
    <MaritalStatus>M</MaritalStatus>
    <SpouseNin>K030282150036B</SpouseNin>
    <SpouseFirstName>K030282150036B</SpouseFirstName>
    <SpouseSurName>K030282150036B</SpouseSurName>
    <OptionSurname>OWN</OptionSurname>
    <ExceptionDescription>Marital Status is Wrong</ExceptionDescription>
</ApplicantDetails>

 *
 */

/* 
 * Modification History:
 * 
 * 24 Sep 2013 (chris): add SpouseNin, SpouseFirstName, SpouseSurName, OptionSurname
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="ApplicantDetails")
public class ApplicantDetailsDTO implements Serializable{
	private static final long serialVersionUID = 827571471822340952L;
	
	@XmlElement(name = "Surname")
    protected String surname;
	@XmlElement(name = "Firstname")
    protected String firstname;
	@XmlElement(name = "Surnameatbirth")
	protected String surnameatbirth;
	@XmlElement(name = "Dob")
	protected String dob;
	@XmlElement(name = "Gender")
	protected String gender;
	@XmlElement(name = "MaritalStatus")
	protected String maritalStatus;
	
	// 24 Sep 2013 (chris) start
	@XmlElement(name = "SpouseNin")
    protected String spouseNin;
	@XmlElement(name = "SpouseFirstName")
	protected String spouseFirstName;
	@XmlElement(name = "SpouseSurName")
	protected String spouseSurName;
	@XmlElement(name = "OptionSurname")
	protected String optionSurname;
	// 24 Sep 2013 (chris) end
	
	@XmlElement(name = "ExceptionDescription")
	protected String exceptionDescription;
	
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getSurnameatbirth() {
		return surnameatbirth;
	}
	public void setSurnameatbirth(String surnameatbirth) {
		this.surnameatbirth = surnameatbirth;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	
	// 24 Sep 2013 (chris) start
	public String getSpouseNin() {
		return spouseNin;
	}
	public void setSpouseNin(String spouseNin) {
		this.spouseNin = spouseNin;
	}
	public String getSpouseFirstName() {
		return spouseFirstName;
	}
	public void setSpouseFirstName(String spouseFirstName) {
		this.spouseFirstName = spouseFirstName;
	}
	public String getSpouseSurName() {
		return spouseSurName;
	}
	public void setSpouseSurName(String spouseSurName) {
		this.spouseSurName = spouseSurName;
	}
	public String getOptionSurname() {
		return optionSurname;
	}
	public void setOptionSurname(String optionSurname) {
		this.optionSurname = optionSurname;
	}
	// 24 Sep 2013 (chris) end
	
	public String getExceptionDescription() {
		return exceptionDescription;
	}
	public void setExceptionDescription(String exceptionDescription) {
		this.exceptionDescription = exceptionDescription;
	}
}
