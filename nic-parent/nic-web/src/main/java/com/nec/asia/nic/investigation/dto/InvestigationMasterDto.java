package com.nec.asia.nic.investigation.dto;

public class InvestigationMasterDto {

	public String fullName;
	public String gender;
	public String dob;
	public String nin;
	public String address;
	public String religion; //tôn giáo
	public String nation; //dân tộc
	public String photo; //ảnh mặt
	
	public InvestigationMasterDto(){
		
	}
	
	public InvestigationMasterDto(String fullName, String gender, String dob,
			String nin, String address, String religion, String nation,
			String photo) {
		super();
		this.fullName = fullName;
		this.gender = gender;
		this.dob = dob;
		this.nin = nin;
		this.address = address;
		this.religion = religion;
		this.nation = nation;
		this.photo = photo;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getNin() {
		return nin;
	}

	public void setNin(String nin) {
		this.nin = nin;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	
}
