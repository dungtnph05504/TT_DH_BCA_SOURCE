package com.nec.asia.nic.comp.trans.dto;

import java.lang.reflect.Field;

public class LdsRequestWsDto {
	private Boolean IsEPassport;
	private String DocumentType;
	private String IssueState;
	private String DateOfBirth;
	private String DateOfExpiry;
	private String DocumentNumber;
	private String NameOfHolder;
	private String Nationality;
	private String OptionalData;
	private String Sex;
	private String Photo;
	private String Finger01Position;
	private String Finger01;
	private String Finger02Position;
	private String Finger02;
	
	public LdsRequestWsDto(){
		
	}
	
	public LdsRequestWsDto(Boolean isEPassport, String documentType,
			String issueState, String dateOfBirth, String dateOfExpiry,
			String documentNumber, String nameOfHolder, String nationality,
			String optionalData, String sex, String photo,
			String finger01Position, String finger01, String finger02Position,
			String finger02) {
		super();
		IsEPassport = isEPassport;
		DocumentType = documentType;
		IssueState = issueState;
		DateOfBirth = dateOfBirth;
		DateOfExpiry = dateOfExpiry;
		DocumentNumber = documentNumber;
		NameOfHolder = nameOfHolder;
		Nationality = nationality;
		OptionalData = optionalData;
		Sex = sex;
		Photo = photo;
		Finger01Position = finger01Position;
		Finger01 = finger01;
		Finger02Position = finger02Position;
		Finger02 = finger02;
	}

	public Boolean getIsEPassport() {
		return IsEPassport;
	}

	public void setIsEPassport(Boolean isEPassport) {
		IsEPassport = isEPassport;
	}

	public String getDocumentType() {
		return DocumentType;
	}

	public void setDocumentType(String documentType) {
		DocumentType = documentType;
	}

	public String getIssueState() {
		return IssueState;
	}

	public void setIssueState(String issueState) {
		IssueState = issueState;
	}

	public String getDateOfBirth() {
		return DateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		DateOfBirth = dateOfBirth;
	}

	public String getDateOfExpiry() {
		return DateOfExpiry;
	}

	public void setDateOfExpiry(String dateOfExpiry) {
		DateOfExpiry = dateOfExpiry;
	}

	public String getDocumentNumber() {
		return DocumentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		DocumentNumber = documentNumber;
	}

	public String getNameOfHolder() {
		return NameOfHolder;
	}

	public void setNameOfHolder(String nameOfHolder) {
		NameOfHolder = nameOfHolder;
	}

	public String getNationality() {
		return Nationality;
	}

	public void setNationality(String nationality) {
		Nationality = nationality;
	}

	public String getOptionalData() {
		return OptionalData;
	}

	public void setOptionalData(String optionalData) {
		OptionalData = optionalData;
	}

	public String getSex() {
		return Sex;
	}

	public void setSex(String sex) {
		Sex = sex;
	}

	public String getPhoto() {
		return Photo;
	}

	public void setPhoto(String photo) {
		Photo = photo;
	}

	public String getFinger01Position() {
		return Finger01Position;
	}

	public void setFinger01Position(String finger01Position) {
		Finger01Position = finger01Position;
	}

	public String getFinger01() {
		return Finger01;
	}

	public void setFinger01(String finger01) {
		Finger01 = finger01;
	}

	public String getFinger02Position() {
		return Finger02Position;
	}

	public void setFinger02Position(String finger02Position) {
		Finger02Position = finger02Position;
	}

	public String getFinger02() {
		return Finger02;
	}

	public void setFinger02(String finger02) {
		Finger02 = finger02;
	}
}
