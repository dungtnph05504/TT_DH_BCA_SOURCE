package com.nec.asia.nic.investigation.dto;

import java.util.Date;

public class InvestigationImmiDto {

	public String typeImmi;
	public Date dateImmi;
	public String documentNo; //Số giấy tờ (Hộ chiếu, giấy nhập cảnh...)
	public String documentType;
	public String documentExpireDate;
	public int childFollow; //Số trẻ e đi kèm
	public String visaNo;
	public String visaSymbol;
	public String visaType;
	public String gateImmi;
	public String job;
	public String purpose; //Mục đích chuyến đi
	public String remark;
	public String surveyor; //Kiểm soát viên
	public String supervior; //Giám sát viên
	
	
	public InvestigationImmiDto(String typeImmi, Date dateImmi,
			String documentNo, String documentType, String documentExpireDate,
			int childFollow, String visaNo, String visaSymbol, String visaType,
			String gateImmi, String job, String purpose, String remark,
			String surveyor, String supervior) {
		super();
		this.typeImmi = typeImmi;
		this.dateImmi = dateImmi;
		this.documentNo = documentNo;
		this.documentType = documentType;
		this.documentExpireDate = documentExpireDate;
		this.childFollow = childFollow;
		this.visaNo = visaNo;
		this.visaSymbol = visaSymbol;
		this.visaType = visaType;
		this.gateImmi = gateImmi;
		this.job = job;
		this.purpose = purpose;
		this.remark = remark;
		this.surveyor = surveyor;
		this.supervior = supervior;
	}


	public String getTypeImmi() {
		return typeImmi;
	}


	public void setTypeImmi(String typeImmi) {
		this.typeImmi = typeImmi;
	}


	public Date getDateImmi() {
		return dateImmi;
	}


	public void setDateImmi(Date dateImmi) {
		this.dateImmi = dateImmi;
	}


	public String getDocumentNo() {
		return documentNo;
	}


	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}


	public String getDocumentType() {
		return documentType;
	}


	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}


	public String getDocumentExpireDate() {
		return documentExpireDate;
	}


	public void setDocumentExpireDate(String documentExpireDate) {
		this.documentExpireDate = documentExpireDate;
	}


	public int getChildFollow() {
		return childFollow;
	}


	public void setChildFollow(int childFollow) {
		this.childFollow = childFollow;
	}


	public String getVisaNo() {
		return visaNo;
	}


	public void setVisaNo(String visaNo) {
		this.visaNo = visaNo;
	}


	public String getVisaSymbol() {
		return visaSymbol;
	}


	public void setVisaSymbol(String visaSymbol) {
		this.visaSymbol = visaSymbol;
	}


	public String getVisaType() {
		return visaType;
	}


	public void setVisaType(String visaType) {
		this.visaType = visaType;
	}


	public String getGateImmi() {
		return gateImmi;
	}


	public void setGateImmi(String gateImmi) {
		this.gateImmi = gateImmi;
	}


	public String getJob() {
		return job;
	}


	public void setJob(String job) {
		this.job = job;
	}


	public String getPurpose() {
		return purpose;
	}


	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getSurveyor() {
		return surveyor;
	}


	public void setSurveyor(String surveyor) {
		this.surveyor = surveyor;
	}


	public String getSupervior() {
		return supervior;
	}


	public void setSupervior(String supervior) {
		this.supervior = supervior;
	}
	
	
}
