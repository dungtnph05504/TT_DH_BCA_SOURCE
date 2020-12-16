package com.nec.asia.nic.investigation.controller;

import com.nec.asia.nic.comp.immihistory.domain.ImmiHistory;

public class ImmiHistoryDataOuput {
   private ImmiHistory im;
   private String createTime;
   private String dateOfBirth;
   private String passportExpiredDate;
   private String visaIssueDate;
   private String lastModifiedTime;
   private String residenceUntilDate;
   private String adminResult;
   public String getAdminResult() {
	return adminResult;
}
public void setAdminResult(String adminResult) {
	this.adminResult = adminResult;
}
public String getCreateTime() {
	return createTime;
}
public void setCreateTime(String createTime) {
	this.createTime = createTime;
}
public String getDateOfBirth() {
	return dateOfBirth;
}
public void setDateOfBirth(String dateOfBirth) {
	this.dateOfBirth = dateOfBirth;
}
public String getPassportExpiredDate() {
	return passportExpiredDate;
}
public void setPassportExpiredDate(String passportExpiredDate) {
	this.passportExpiredDate = passportExpiredDate;
}
public String getVisaIssueDate() {
	return visaIssueDate;
}
public void setVisaIssueDate(String visaIssueDate) {
	this.visaIssueDate = visaIssueDate;
}
public String getLastModifiedTime() {
	return lastModifiedTime;
}
public void setLastModifiedTime(String lastModifiedTime) {
	this.lastModifiedTime = lastModifiedTime;
}
public String getResidenceUntilDate() {
	return residenceUntilDate;
}
public void setResidenceUntilDate(String residenceUntilDate) {
	this.residenceUntilDate = residenceUntilDate;
}
private String data;
public ImmiHistory getIm() {
	return im;
}
public void setIm(ImmiHistory im) {
	this.im = im;
}
public String getData() {
	return data;
}
public void setData(String data) {
	this.data = data;
}
   
}
