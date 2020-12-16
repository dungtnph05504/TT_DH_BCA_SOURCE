package com.nec.asia.nic.comp.perso.model;

/*
 * Modification History:
 * 04 Jul 2017 chris :  add DG3
 */

public class PersonalizationData {
	
	/** Header of Unit or Job name*/
	private String jobName = "ID60_SimpleJob";
	private String type = "Job";
	private String priority = "2";
	private String comment = "This is a comment";
	private String product = "Demo";
	
	private String transactionId;
	private String documentNumber;
	private String firstName;
	private String middleName;
	private String lastName;
	private String fullName;
	private String passportType;
	private String countryCode;
	private String nationality;
	private String sex;
	private String dateOfBirth;
	private String placeOfBirth;
	private String dateOfIssue;
	private String placeOfIssue;
	private String dateOfExpiry;
	private String nationalIdNumber;
	private String photoInline64Encoded;
	private String fingerprint1Inline64Encoded;
	private String fingerprint1Position;
	private String fingerprint2Inline64Encoded;
	private String fingerprint2Position;
	private String mrz1;
	private String mrz2;
	private String com;
	private String dg1;
	private String dg2;
	private String dg3;
	private String sod;
	
	
	public PersonalizationData () {  }
	
	public PersonalizationData (String jobName, String type, String priority) {
		if (jobName!=null)
			this.jobName = jobName;
		
		if (type!=null)
			this.type = type;
		
		if (priority!=null)
			this.priority = priority;
	}
	
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	
	public String getDocumentNumber() {
		return documentNumber;
	}
	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getPassportType() {
		return passportType;
	}
	public void setPassportType(String passportType) {
		this.passportType = passportType;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getPlaceOfBirth() {
		return placeOfBirth;
	}
	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}
	public String getDateOfIssue() {
		return dateOfIssue;
	}
	public void setDateOfIssue(String dateOfIssue) {
		this.dateOfIssue = dateOfIssue;
	}
	public String getPlaceOfIssue() {
		return placeOfIssue;
	}
	public void setPlaceOfIssue(String placeOfIssue) {
		this.placeOfIssue = placeOfIssue;
	}
	public String getDateOfExpiry() {
		return dateOfExpiry;
	}
	public void setDateOfExpiry(String dateOfExpiry) {
		this.dateOfExpiry = dateOfExpiry;
	}
	public String getNationalIdNumber() {
		return nationalIdNumber;
	}
	public void setNationalIdNumber(String nationalIdNumber) {
		this.nationalIdNumber = nationalIdNumber;
	}
	
	public String getFingerprint1Position() {
		return fingerprint1Position;
	}
	public void setFingerprint1Position(String fingerprint1Position) {
		this.fingerprint1Position = fingerprint1Position;
	}
	
	public String getFingerprint2Position() {
		return fingerprint2Position;
	}
	public void setFingerprint2Position(String fingerprint2Position) {
		this.fingerprint2Position = fingerprint2Position;
	}
	public String getMrz1() {
		return "<![CDATA[" + mrz1 + "]]>";
	}
	public void setMrz1(String mrz1) {
		this.mrz1 = mrz1;
	}
	public String getMrz2() {
		return "<![CDATA[" + mrz2 + "]]>";
	}
	public void setMrz2(String mrz2) {
		this.mrz2 = mrz2;
	}
	public String getCom() {
		return com;
	}
	public void setCom(String com) {
		this.com = com;
	}
	public String getDg1() {
		return dg1;
	}
	public void setDg1(String dg1) {
		this.dg1 = dg1;
	}
	public String getDg2() {
		return dg2;
	}
	public void setDg2(String dg2) {
		this.dg2 = dg2;
	}
	public String getDg3() {
		return dg3;
	}
	public void setDg3(String dg3) {
		this.dg3 = dg3;
	}
	public String getSod() {
		return sod;
	}
	public void setSod(String sod) {
		this.sod = sod;
	}
	public String getPhotoInline64Encoded() {
		return photoInline64Encoded;
	}
	public void setPhotoInline64Encoded(String photoInline64Encoded) {
		this.photoInline64Encoded = photoInline64Encoded;
	}
	public String getFingerprint1Inline64Encoded() {
		return fingerprint1Inline64Encoded;
	}
	public void setFingerprint1Inline64Encoded(String fingerprint1Inline64Encoded) {
		this.fingerprint1Inline64Encoded = fingerprint1Inline64Encoded;
	}
	public String getFingerprint2Inline64Encoded() {
		return fingerprint2Inline64Encoded;
	}
	public void setFingerprint2Inline64Encoded(String fingerprint2Inline64Encoded) {
		this.fingerprint2Inline64Encoded = fingerprint2Inline64Encoded;
	}
	

	public String getJobName() {
		return jobName;
	}

	public String getType() {
		return type;
	}

	public String getPriority() {
		return priority;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}
	
	
	
}
