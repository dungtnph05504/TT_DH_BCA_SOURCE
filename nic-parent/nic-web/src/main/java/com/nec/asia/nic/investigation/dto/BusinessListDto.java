package com.nec.asia.nic.investigation.dto;

public class BusinessListDto {
	private String id;
	private String decisionNumber;
	private String serial;
    private String fullName;
    private String gender;
    private String genderDesc;
    private String dob;
    private String pob;
    private String pobDesc;
    private String address;
    private String agency;
    private String agencyDesc;
    private String position;
    private String positionEng;
    private String addressAgency;
    private String phone;
    private String type;
    private String rank;
    private String curb;
    private String jaw;
    private String createDate;
    private String createBy;
    private String modifyDate;
    private String modifyBy;	
    private String description;
    
    private String recieptNo;
    private String transactionId;
    private String imgFacial;
    private String imgDoc;
    private String createDateDS;
    private String nin;
    private String ngayCapCMND;
    private String danToc;
    private String tonGiao;
    private String soHC;
    private String loaiHC;
    private String ngayCapHC;
    private String ngayHetHan;
    private String noiCap;
    private String nguoiKy;
    private String chucVu;
    private String trangThai;
    private String ghiChu;
    private int stylePP;
    private int styleFP;
    private String icao1;
    private String icao2;
    
	public BusinessListDto() {
		super();
	}

	public BusinessListDto(String id, String decisionNumber, String serial,
			String fullName, String gender, String genderDesc, String dob,
			String pob, String pobDesc, String address, String agency,
			String agencyDesc, String position, String positionEng,
			String addressAgency, String phone, String type, String rank,
			String curb, String jaw, String createDate, String createBy,
			String modifyDate, String modifyBy, String description,
			String recieptNo, String transactionId, String imgFacial,
			String imgDoc, String createDateDS, String nin, String icao1, String icao2) {
		super();
		this.id = id;
		this.decisionNumber = decisionNumber;
		this.serial = serial;
		this.fullName = fullName;
		this.gender = gender;
		this.genderDesc = genderDesc;
		this.dob = dob;
		this.pob = pob;
		this.pobDesc = pobDesc;
		this.address = address;
		this.agency = agency;
		this.agencyDesc = agencyDesc;
		this.position = position;
		this.positionEng = positionEng;
		this.addressAgency = addressAgency;
		this.phone = phone;
		this.type = type;
		this.rank = rank;
		this.curb = curb;
		this.jaw = jaw;
		this.createDate = createDate;
		this.createBy = createBy;
		this.modifyDate = modifyDate;
		this.modifyBy = modifyBy;
		this.description = description;
		this.recieptNo = recieptNo;
		this.transactionId = transactionId;
		this.imgFacial = imgFacial;
		this.imgDoc = imgDoc;
		this.createDateDS = createDateDS;
		this.nin = nin;
		this.icao1 = icao1;
		this.icao2 = icao2;
	}
	
	
	

	public String getIcao1() {
		return icao1;
	}

	public void setIcao1(String icao1) {
		this.icao1 = icao1;
	}

	public String getIcao2() {
		return icao2;
	}

	public void setIcao2(String icao2) {
		this.icao2 = icao2;
	}

	public int getStyleFP() {
		return styleFP;
	}

	public void setStyleFP(int styleFP) {
		this.styleFP = styleFP;
	}

	public int getStylePP() {
		return stylePP;
	}

	public void setStylePP(int stylePP) {
		this.stylePP = stylePP;
	}

	public String getGhiChu() {
		return ghiChu;
	}

	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}

	public String getNgayCapCMND() {
		return ngayCapCMND;
	}

	public void setNgayCapCMND(String ngayCapCMND) {
		this.ngayCapCMND = ngayCapCMND;
	}

	public String getDanToc() {
		return danToc;
	}

	public void setDanToc(String danToc) {
		this.danToc = danToc;
	}

	public String getTonGiao() {
		return tonGiao;
	}

	public void setTonGiao(String tonGiao) {
		this.tonGiao = tonGiao;
	}

	public String getSoHC() {
		return soHC;
	}

	public void setSoHC(String soHC) {
		this.soHC = soHC;
	}

	public String getLoaiHC() {
		return loaiHC;
	}

	public void setLoaiHC(String loaiHC) {
		this.loaiHC = loaiHC;
	}

	public String getNgayCapHC() {
		return ngayCapHC;
	}

	public void setNgayCapHC(String ngayCapHC) {
		this.ngayCapHC = ngayCapHC;
	}

	public String getNgayHetHan() {
		return ngayHetHan;
	}

	public void setNgayHetHan(String ngayHetHan) {
		this.ngayHetHan = ngayHetHan;
	}

	public String getNoiCap() {
		return noiCap;
	}

	public void setNoiCap(String noiCap) {
		this.noiCap = noiCap;
	}

	public String getNguoiKy() {
		return nguoiKy;
	}

	public void setNguoiKy(String nguoiKy) {
		this.nguoiKy = nguoiKy;
	}

	public String getChucVu() {
		return chucVu;
	}

	public void setChucVu(String chucVu) {
		this.chucVu = chucVu;
	}

	public String getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDecisionNumber() {
		return decisionNumber;
	}

	public void setDecisionNumber(String decisionNumber) {
		this.decisionNumber = decisionNumber;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
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

	public String getGenderDesc() {
		return genderDesc;
	}

	public void setGenderDesc(String genderDesc) {
		this.genderDesc = genderDesc;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getPob() {
		return pob;
	}

	public void setPob(String pob) {
		this.pob = pob;
	}

	public String getPobDesc() {
		return pobDesc;
	}

	public void setPobDesc(String pobDesc) {
		this.pobDesc = pobDesc;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public String getAgencyDesc() {
		return agencyDesc;
	}

	public void setAgencyDesc(String agencyDesc) {
		this.agencyDesc = agencyDesc;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getPositionEng() {
		return positionEng;
	}

	public void setPositionEng(String positionEng) {
		this.positionEng = positionEng;
	}

	public String getAddressAgency() {
		return addressAgency;
	}

	public void setAddressAgency(String addressAgency) {
		this.addressAgency = addressAgency;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getCurb() {
		return curb;
	}

	public void setCurb(String curb) {
		this.curb = curb;
	}

	public String getJaw() {
		return jaw;
	}

	public void setJaw(String jaw) {
		this.jaw = jaw;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRecieptNo() {
		return recieptNo;
	}

	public void setRecieptNo(String recieptNo) {
		this.recieptNo = recieptNo;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getImgFacial() {
		return imgFacial;
	}

	public void setImgFacial(String imgFacial) {
		this.imgFacial = imgFacial;
	}

	public String getImgDoc() {
		return imgDoc;
	}

	public void setImgDoc(String imgDoc) {
		this.imgDoc = imgDoc;
	}

	public String getCreateDateDS() {
		return createDateDS;
	}

	public void setCreateDateDS(String createDateDS) {
		this.createDateDS = createDateDS;
	}

	public String getNin() {
		return nin;
	}

	public void setNin(String nin) {
		this.nin = nin;
	}
	
}
