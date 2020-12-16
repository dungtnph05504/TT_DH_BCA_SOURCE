package com.nec.asia.nic.investigation.dto;

import java.util.Date;
import java.util.List;

import com.nec.asia.nic.comp.trans.dto.EppPersonDto;

public class InvestigationListInfoTargetDto {

	public String fullName_O;
	public String gender_O;
	public String dob_O;
	public String nin_O;
	public Date issueDateNin_O;
	public Double scoreBMS_O;
	public String scoreBMSall_O;
	public String pob_O;
	public String religion_O; //tôn giáo của đối tượng
	public String nation_O; //dân tộc  của đối tượng
	public String phone_O;
	public String photo_O; //ảnh mặt đối tượng
	public String dateNin_O;
	public String addressNin_O;
	public String job_O;
	public String image_O;
	public String address_O;//thường trú đối tượng
	public String danhSachA_O;
	public String dateA_O;
	public String byA_O;
	
	//Thông tin người thân
	public String fullNameFather_O;
	public String dobFather_O;
	public String fullNameMother_O;
	public String dobMother_O;
	public String fullNameSpouser_O;
	public String dobSpouser_O;
	public List<InfoFamillyDto> infoFa;
	
	//Thông tin hồ sơ 
	public String transactionId_O;
	public String packageId_O;
	public String typeTransaction_O;
	public String reg_O; //Nơi đăng ký
	public String status_O; //Trạng thái hồ sơ
	public String officerMasterApprove_O; //Lãnh đạo phê duyệt
	public String position_O; //Chức vụ lãnh đạo phê duyệt
	public String passportNo_O;
	public String issueDatePassport_O; 
	public String payDatePassport_O; //Ngày trả
	public String payPlacePassport_O; //Nơi trả
	public String personRecieve_O; //Người nhận
	public String remark_O; //Ghi chú
	
	public String scoreBms_O;
	public String nationality_O;
	private String note_O;
	private String genderS_O;
	private List<EppPersonDto> dsSon_O;
	
	public InvestigationListInfoTargetDto(){
		
	}
	
	public InvestigationListInfoTargetDto(String fullName_O, String gender_O,
			String dob_O, String nin_O, Date issueDateNin_O, Double scoreBMS_O,
			String scoreBMSall_O, String pob_O, String religion_O,
			String nation_O, String phone_O, String photo_O, String dateNin_O,
			String addressNin_O, String job_O, String image_O,
			String address_O, String danhSachA_O, String dateA_O, String byA_O,
			String fullNameFather_O, String dobFather_O,
			String fullNameMother_O, String dobMother_O,
			String fullNameSpouser_O, String dobSpouser_O,
			List<InfoFamillyDto> infoFa, String transactionId_O,
			String packageId_O, String typeTransaction_O, String reg_O,
			String status_O, String officerMasterApprove_O, String position_O,
			String passportNo_O, String issueDatePassport_O,
			String payDatePassport_O, String payPlacePassport_O,
			String personRecieve_O, String remark_O) {
		super();
		this.fullName_O = fullName_O;
		this.gender_O = gender_O;
		this.dob_O = dob_O;
		this.nin_O = nin_O;
		this.issueDateNin_O = issueDateNin_O;
		this.scoreBMS_O = scoreBMS_O;
		this.scoreBMSall_O = scoreBMSall_O;
		this.pob_O = pob_O;
		this.religion_O = religion_O;
		this.nation_O = nation_O;
		this.phone_O = phone_O;
		this.photo_O = photo_O;
		this.dateNin_O = dateNin_O;
		this.addressNin_O = addressNin_O;
		this.job_O = job_O;
		this.image_O = image_O;
		this.address_O = address_O;
		this.danhSachA_O = danhSachA_O;
		this.dateA_O = dateA_O;
		this.byA_O = byA_O;
		this.fullNameFather_O = fullNameFather_O;
		this.dobFather_O = dobFather_O;
		this.fullNameMother_O = fullNameMother_O;
		this.dobMother_O = dobMother_O;
		this.fullNameSpouser_O = fullNameSpouser_O;
		this.dobSpouser_O = dobSpouser_O;
		this.infoFa = infoFa;
		this.transactionId_O = transactionId_O;
		this.packageId_O = packageId_O;
		this.typeTransaction_O = typeTransaction_O;
		this.reg_O = reg_O;
		this.status_O = status_O;
		this.officerMasterApprove_O = officerMasterApprove_O;
		this.position_O = position_O;
		this.passportNo_O = passportNo_O;
		this.issueDatePassport_O = issueDatePassport_O;
		this.payDatePassport_O = payDatePassport_O;
		this.payPlacePassport_O = payPlacePassport_O;
		this.personRecieve_O = personRecieve_O;
		this.remark_O = remark_O;
	}
	
	

	public String getNote_O() {
		return note_O;
	}

	public void setNote_O(String note_O) {
		this.note_O = note_O;
	}

	public String getGenderS_O() {
		return genderS_O;
	}

	public void setGenderS_O(String genderS_O) {
		this.genderS_O = genderS_O;
	}

	

	public List<EppPersonDto> getDsSon_O() {
		return dsSon_O;
	}

	public void setDsSon_O(List<EppPersonDto> dsSon_O) {
		this.dsSon_O = dsSon_O;
	}

	public String getNationality_O() {
		return nationality_O;
	}

	public void setNationality_O(String nationality_O) {
		this.nationality_O = nationality_O;
	}

	public String getScoreBms_O() {
		return scoreBms_O;
	}

	public void setScoreBms_O(String scoreBms_O) {
		this.scoreBms_O = scoreBms_O;
	}

	public String getFullName_O() {
		return fullName_O;
	}

	public void setFullName_O(String fullName_O) {
		this.fullName_O = fullName_O;
	}

	public String getGender_O() {
		return gender_O;
	}

	public void setGender_O(String gender_O) {
		this.gender_O = gender_O;
	}

	public String getDob_O() {
		return dob_O;
	}

	public void setDob_O(String dob_O) {
		this.dob_O = dob_O;
	}

	public String getNin_O() {
		return nin_O;
	}

	public void setNin_O(String nin_O) {
		this.nin_O = nin_O;
	}

	public Date getIssueDateNin_O() {
		return issueDateNin_O;
	}

	public void setIssueDateNin_O(Date issueDateNin_O) {
		this.issueDateNin_O = issueDateNin_O;
	}

	public Double getScoreBMS_O() {
		return scoreBMS_O;
	}

	public void setScoreBMS_O(Double scoreBMS_O) {
		this.scoreBMS_O = scoreBMS_O;
	}

	public String getScoreBMSall_O() {
		return scoreBMSall_O;
	}

	public void setScoreBMSall_O(String scoreBMSall_O) {
		this.scoreBMSall_O = scoreBMSall_O;
	}

	public String getPob_O() {
		return pob_O;
	}

	public void setPob_O(String pob_O) {
		this.pob_O = pob_O;
	}

	public String getReligion_O() {
		return religion_O;
	}

	public void setReligion_O(String religion_O) {
		this.religion_O = religion_O;
	}

	public String getNation_O() {
		return nation_O;
	}

	public void setNation_O(String nation_O) {
		this.nation_O = nation_O;
	}

	public String getPhone_O() {
		return phone_O;
	}

	public void setPhone_O(String phone_O) {
		this.phone_O = phone_O;
	}

	public String getPhoto_O() {
		return photo_O;
	}

	public void setPhoto_O(String photo_O) {
		this.photo_O = photo_O;
	}

	public String getDateNin_O() {
		return dateNin_O;
	}

	public void setDateNin_O(String dateNin_O) {
		this.dateNin_O = dateNin_O;
	}

	public String getAddressNin_O() {
		return addressNin_O;
	}

	public void setAddressNin_O(String addressNin_O) {
		this.addressNin_O = addressNin_O;
	}

	public String getJob_O() {
		return job_O;
	}

	public void setJob_O(String job_O) {
		this.job_O = job_O;
	}

	public String getImage_O() {
		return image_O;
	}

	public void setImage_O(String image_O) {
		this.image_O = image_O;
	}

	public String getAddress_O() {
		return address_O;
	}

	public void setAddress_O(String address_O) {
		this.address_O = address_O;
	}

	public String getDanhSachA_O() {
		return danhSachA_O;
	}

	public void setDanhSachA_O(String danhSachA_O) {
		this.danhSachA_O = danhSachA_O;
	}

	public String getDateA_O() {
		return dateA_O;
	}

	public void setDateA_O(String dateA_O) {
		this.dateA_O = dateA_O;
	}

	public String getByA_O() {
		return byA_O;
	}

	public void setByA_O(String byA_O) {
		this.byA_O = byA_O;
	}

	public String getFullNameFather_O() {
		return fullNameFather_O;
	}

	public void setFullNameFather_O(String fullNameFather_O) {
		this.fullNameFather_O = fullNameFather_O;
	}

	public String getDobFather_O() {
		return dobFather_O;
	}

	public void setDobFather_O(String dobFather_O) {
		this.dobFather_O = dobFather_O;
	}

	public String getFullNameMother_O() {
		return fullNameMother_O;
	}

	public void setFullNameMother_O(String fullNameMother_O) {
		this.fullNameMother_O = fullNameMother_O;
	}

	public String getDobMother_O() {
		return dobMother_O;
	}

	public void setDobMother_O(String dobMother_O) {
		this.dobMother_O = dobMother_O;
	}

	public String getFullNameSpouser_O() {
		return fullNameSpouser_O;
	}

	public void setFullNameSpouser_O(String fullNameSpouser_O) {
		this.fullNameSpouser_O = fullNameSpouser_O;
	}

	public String getDobSpouser_O() {
		return dobSpouser_O;
	}

	public void setDobSpouser_O(String dobSpouser_O) {
		this.dobSpouser_O = dobSpouser_O;
	}

	public List<InfoFamillyDto> getInfoFa() {
		return infoFa;
	}

	public void setInfoFa(List<InfoFamillyDto> infoFa) {
		this.infoFa = infoFa;
	}

	public String getTransactionId_O() {
		return transactionId_O;
	}

	public void setTransactionId_O(String transactionId_O) {
		this.transactionId_O = transactionId_O;
	}

	public String getPackageId_O() {
		return packageId_O;
	}

	public void setPackageId_O(String packageId_O) {
		this.packageId_O = packageId_O;
	}

	public String getTypeTransaction_O() {
		return typeTransaction_O;
	}

	public void setTypeTransaction_O(String typeTransaction_O) {
		this.typeTransaction_O = typeTransaction_O;
	}

	public String getReg_O() {
		return reg_O;
	}

	public void setReg_O(String reg_O) {
		this.reg_O = reg_O;
	}

	public String getStatus_O() {
		return status_O;
	}

	public void setStatus_O(String status_O) {
		this.status_O = status_O;
	}

	public String getOfficerMasterApprove_O() {
		return officerMasterApprove_O;
	}

	public void setOfficerMasterApprove_O(String officerMasterApprove_O) {
		this.officerMasterApprove_O = officerMasterApprove_O;
	}

	public String getPosition_O() {
		return position_O;
	}

	public void setPosition_O(String position_O) {
		this.position_O = position_O;
	}

	public String getPassportNo_O() {
		return passportNo_O;
	}

	public void setPassportNo_O(String passportNo_O) {
		this.passportNo_O = passportNo_O;
	}

	public String getIssueDatePassport_O() {
		return issueDatePassport_O;
	}

	public void setIssueDatePassport_O(String issueDatePassport_O) {
		this.issueDatePassport_O = issueDatePassport_O;
	}

	public String getPayDatePassport_O() {
		return payDatePassport_O;
	}

	public void setPayDatePassport_O(String payDatePassport_O) {
		this.payDatePassport_O = payDatePassport_O;
	}

	public String getPayPlacePassport_O() {
		return payPlacePassport_O;
	}

	public void setPayPlacePassport_O(String payPlacePassport_O) {
		this.payPlacePassport_O = payPlacePassport_O;
	}

	public String getPersonRecieve_O() {
		return personRecieve_O;
	}

	public void setPersonRecieve_O(String personRecieve_O) {
		this.personRecieve_O = personRecieve_O;
	}

	public String getRemark_O() {
		return remark_O;
	}

	public void setRemark_O(String remark_O) {
		this.remark_O = remark_O;
	}
	
	
}
