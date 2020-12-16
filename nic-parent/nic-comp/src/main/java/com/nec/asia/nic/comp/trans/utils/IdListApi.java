package com.nec.asia.nic.comp.trans.utils;

import java.util.ArrayList;
import java.util.List;

public class IdListApi {

	private Long ID;
	private String NAME;
	private String SEARCH_NAME;
	private String GENDER;
	private String DATE_OF_BIRTH;
	private String PLACE_OF_BIRTH;
	private String ID_NUMBER;
	private String DATE_OF_ISSUE;
	private String FATHER_NAME;
	private String MOTHER_NAME;
	private String STATUS;
	private List<FileAttachmentApi> fileAttachments = new ArrayList<FileAttachmentApi>();
	
	public List<FileAttachmentApi> getFileAttachments() {
		return fileAttachments;
	}

	public void setFileAttachments(List<FileAttachmentApi> fileAttachments) {
		this.fileAttachments = fileAttachments;
	}
	
	
	public IdListApi(){
		
	}

	public Long getID() {
		return ID;
	}

	public void setID(Long iD) {
		ID = iD;
	}

	public String getNAME() {
		return NAME;
	}

	public void setNAME(String nAME) {
		NAME = nAME;
	}

	public String getSEARCH_NAME() {
		return SEARCH_NAME;
	}

	public void setSEARCH_NAME(String sEARCH_NAME) {
		SEARCH_NAME = sEARCH_NAME;
	}

	public String getGENDER() {
		return GENDER;
	}

	public void setGENDER(String gENDER) {
		GENDER = gENDER;
	}

	public String getDATE_OF_BIRTH() {
		return DATE_OF_BIRTH;
	}

	public void setDATE_OF_BIRTH(String dATE_OF_BIRTH) {
		DATE_OF_BIRTH = dATE_OF_BIRTH;
	}

	public String getPLACE_OF_BIRTH() {
		return PLACE_OF_BIRTH;
	}

	public void setPLACE_OF_BIRTH(String pLACE_OF_BIRTH) {
		PLACE_OF_BIRTH = pLACE_OF_BIRTH;
	}

	public String getID_NUMBER() {
		return ID_NUMBER;
	}

	public void setID_NUMBER(String iD_NUMBER) {
		ID_NUMBER = iD_NUMBER;
	}

	public String getDATE_OF_ISSUE() {
		return DATE_OF_ISSUE;
	}

	public void setDATE_OF_ISSUE(String dATE_OF_ISSUE) {
		DATE_OF_ISSUE = dATE_OF_ISSUE;
	}

	public String getFATHER_NAME() {
		return FATHER_NAME;
	}

	public void setFATHER_NAME(String fATHER_NAME) {
		FATHER_NAME = fATHER_NAME;
	}

	public String getMOTHER_NAME() {
		return MOTHER_NAME;
	}

	public void setMOTHER_NAME(String mOTHER_NAME) {
		MOTHER_NAME = mOTHER_NAME;
	}

	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
}
