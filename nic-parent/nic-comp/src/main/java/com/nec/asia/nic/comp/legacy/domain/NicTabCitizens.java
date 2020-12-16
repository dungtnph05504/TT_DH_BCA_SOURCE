package com.nec.asia.nic.comp.legacy.domain;

import java.util.Date;

/**
 * The domain class for NIC_TAB_CITIZEN table. 
 */
public class NicTabCitizens implements java.io.Serializable {

	private static final long serialVersionUID = 5329465863548480774L;

	private Long nicId;
	private String nicUsrId;
	private Date nicDatecreated;
	private Date nicDatemodify;
	private String nicNumber;
	private String nicSurname;
	private String nicNames;
	private String nicMaidenname;
	private String nicAddr1;
	private String nicAddr2;
	private String nicAddr3;
	private String nicAddr4;
	private String nicSex;
	private Date nicDateofbirth;
	private String nicDobApprox;
	private String nicBloodgroup;
	private Date nicIssuedate;
	private Date nicPrevIssuedate;
	private Long nicOfrId;
	private Long nicOfcId;
	private byte[] nicPhoto;
	private byte[] nicSign;
	private byte[] nicThumbprint;
	private String nicRemarks;
	private String nicSignFlag;
	private Long nicPrintCounter;
	private String nicPrintDob;
	private Date nicIssuetime;

	public NicTabCitizens() {
	}

	public NicTabCitizens(Long nicId, String nicUsrId,
			Date nicDatecreated, Date nicDatemodify, String nicNumber,
			String nicSurname, String nicNames, String nicMaidenname,
			String nicAddr1, String nicAddr2, String nicAddr3, String nicAddr4,
			String nicSex, Date nicDateofbirth, String nicDobApprox,
			String nicBloodgroup, Date nicIssuedate, Date nicPrevIssuedate,
			Long nicOfrId, Long nicOfcId, byte[] nicPhoto,
			byte[] nicSign, byte[] nicThumbprint, String nicRemarks,
			String nicSignFlag, Long nicPrintCounter, String nicPrintDob,
			Date nicIssuetime) {
		this.nicId = nicId;
		this.nicUsrId = nicUsrId;
		this.nicDatecreated = nicDatecreated;
		this.nicDatemodify = nicDatemodify;
		this.nicNumber = nicNumber;
		this.nicSurname = nicSurname;
		this.nicNames = nicNames;
		this.nicMaidenname = nicMaidenname;
		this.nicAddr1 = nicAddr1;
		this.nicAddr2 = nicAddr2;
		this.nicAddr3 = nicAddr3;
		this.nicAddr4 = nicAddr4;
		this.nicSex = nicSex;
		this.nicDateofbirth = nicDateofbirth;
		this.nicDobApprox = nicDobApprox;
		this.nicBloodgroup = nicBloodgroup;
		this.nicIssuedate = nicIssuedate;
		this.nicPrevIssuedate = nicPrevIssuedate;
		this.nicOfrId = nicOfrId;
		this.nicOfcId = nicOfcId;
		this.nicPhoto = nicPhoto;
		this.nicSign = nicSign;
		this.nicThumbprint = nicThumbprint;
		this.nicRemarks = nicRemarks;
		this.nicSignFlag = nicSignFlag;
		this.nicPrintCounter = nicPrintCounter;
		this.nicPrintDob = nicPrintDob;
		this.nicIssuetime = nicIssuetime;
	}

	public Long getNicId() {
		return this.nicId;
	}

	public void setNicId(Long nicId) {
		this.nicId = nicId;
	}

	public String getNicUsrId() {
		return this.nicUsrId;
	}

	public void setNicUsrId(String nicUsrId) {
		this.nicUsrId = nicUsrId;
	}

	public Date getNicDatecreated() {
		return this.nicDatecreated;
	}

	public void setNicDatecreated(Date nicDatecreated) {
		this.nicDatecreated = nicDatecreated;
	}

	public Date getNicDatemodify() {
		return this.nicDatemodify;
	}

	public void setNicDatemodify(Date nicDatemodify) {
		this.nicDatemodify = nicDatemodify;
	}

	public String getNicNumber() {
		return this.nicNumber;
	}

	public void setNicNumber(String nicNumber) {
		this.nicNumber = nicNumber;
	}

	public String getNicSurname() {
		return this.nicSurname;
	}

	public void setNicSurname(String nicSurname) {
		this.nicSurname = nicSurname;
	}

	public String getNicNames() {
		return this.nicNames;
	}

	public void setNicNames(String nicNames) {
		this.nicNames = nicNames;
	}

	public String getNicMaidenname() {
		return this.nicMaidenname;
	}

	public void setNicMaidenname(String nicMaidenname) {
		this.nicMaidenname = nicMaidenname;
	}

	public String getNicAddr1() {
		return this.nicAddr1;
	}

	public void setNicAddr1(String nicAddr1) {
		this.nicAddr1 = nicAddr1;
	}

	public String getNicAddr2() {
		return this.nicAddr2;
	}

	public void setNicAddr2(String nicAddr2) {
		this.nicAddr2 = nicAddr2;
	}

	public String getNicAddr3() {
		return this.nicAddr3;
	}

	public void setNicAddr3(String nicAddr3) {
		this.nicAddr3 = nicAddr3;
	}

	public String getNicAddr4() {
		return this.nicAddr4;
	}

	public void setNicAddr4(String nicAddr4) {
		this.nicAddr4 = nicAddr4;
	}

	public String getNicSex() {
		return this.nicSex;
	}

	public void setNicSex(String nicSex) {
		this.nicSex = nicSex;
	}

	public Date getNicDateofbirth() {
		return this.nicDateofbirth;
	}

	public void setNicDateofbirth(Date nicDateofbirth) {
		this.nicDateofbirth = nicDateofbirth;
	}

	public String getNicDobApprox() {
		return this.nicDobApprox;
	}

	public void setNicDobApprox(String nicDobApprox) {
		this.nicDobApprox = nicDobApprox;
	}

	public String getNicBloodgroup() {
		return this.nicBloodgroup;
	}

	public void setNicBloodgroup(String nicBloodgroup) {
		this.nicBloodgroup = nicBloodgroup;
	}

	public Date getNicIssuedate() {
		return this.nicIssuedate;
	}

	public void setNicIssuedate(Date nicIssuedate) {
		this.nicIssuedate = nicIssuedate;
	}

	public Date getNicPrevIssuedate() {
		return this.nicPrevIssuedate;
	}

	public void setNicPrevIssuedate(Date nicPrevIssuedate) {
		this.nicPrevIssuedate = nicPrevIssuedate;
	}

	public Long getNicOfrId() {
		return this.nicOfrId;
	}

	public void setNicOfrId(Long nicOfrId) {
		this.nicOfrId = nicOfrId;
	}

	public Long getNicOfcId() {
		return this.nicOfcId;
	}

	public void setNicOfcId(Long nicOfcId) {
		this.nicOfcId = nicOfcId;
	}

	public byte[] getNicPhoto() {
		return this.nicPhoto;
	}

	public void setNicPhoto(byte[] nicPhoto) {
		this.nicPhoto = nicPhoto;
	}

	public byte[] getNicSign() {
		return this.nicSign;
	}

	public void setNicSign(byte[] nicSign) {
		this.nicSign = nicSign;
	}

	public byte[] getNicThumbprint() {
		return this.nicThumbprint;
	}

	public void setNicThumbprint(byte[] nicThumbprint) {
		this.nicThumbprint = nicThumbprint;
	}

	public String getNicRemarks() {
		return this.nicRemarks;
	}

	public void setNicRemarks(String nicRemarks) {
		this.nicRemarks = nicRemarks;
	}

	public String getNicSignFlag() {
		return this.nicSignFlag;
	}

	public void setNicSignFlag(String nicSignFlag) {
		this.nicSignFlag = nicSignFlag;
	}

	public Long getNicPrintCounter() {
		return this.nicPrintCounter;
	}

	public void setNicPrintCounter(Long nicPrintCounter) {
		this.nicPrintCounter = nicPrintCounter;
	}

	public String getNicPrintDob() {
		return this.nicPrintDob;
	}

	public void setNicPrintDob(String nicPrintDob) {
		this.nicPrintDob = nicPrintDob;
	}

	public Date getNicIssuetime() {
		return this.nicIssuetime;
	}

	public void setNicIssuetime(Date nicIssuetime) {
		this.nicIssuetime = nicIssuetime;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof NicTabCitizens))
			return false;
		NicTabCitizens castOther = (NicTabCitizens) other;

		return ((this.getNicId() == castOther.getNicId()) || (this.getNicId() != null
				&& castOther.getNicId() != null && this.getNicId().equals(
				castOther.getNicId())))
				&& ((this.getNicUsrId() == castOther.getNicUsrId()) || (this
						.getNicUsrId() != null
						&& castOther.getNicUsrId() != null && this
						.getNicUsrId().equals(castOther.getNicUsrId())))
				&& ((this.getNicDatecreated() == castOther.getNicDatecreated()) || (this
						.getNicDatecreated() != null
						&& castOther.getNicDatecreated() != null && this
						.getNicDatecreated().equals(
								castOther.getNicDatecreated())))
				&& ((this.getNicDatemodify() == castOther.getNicDatemodify()) || (this
						.getNicDatemodify() != null
						&& castOther.getNicDatemodify() != null && this
						.getNicDatemodify()
						.equals(castOther.getNicDatemodify())))
				&& ((this.getNicNumber() == castOther.getNicNumber()) || (this
						.getNicNumber() != null
						&& castOther.getNicNumber() != null && this
						.getNicNumber().equals(castOther.getNicNumber())))
				&& ((this.getNicSurname() == castOther.getNicSurname()) || (this
						.getNicSurname() != null
						&& castOther.getNicSurname() != null && this
						.getNicSurname().equals(castOther.getNicSurname())))
				&& ((this.getNicNames() == castOther.getNicNames()) || (this
						.getNicNames() != null
						&& castOther.getNicNames() != null && this
						.getNicNames().equals(castOther.getNicNames())))
				&& ((this.getNicMaidenname() == castOther.getNicMaidenname()) || (this
						.getNicMaidenname() != null
						&& castOther.getNicMaidenname() != null && this
						.getNicMaidenname()
						.equals(castOther.getNicMaidenname())))
				&& ((this.getNicAddr1() == castOther.getNicAddr1()) || (this
						.getNicAddr1() != null
						&& castOther.getNicAddr1() != null && this
						.getNicAddr1().equals(castOther.getNicAddr1())))
				&& ((this.getNicAddr2() == castOther.getNicAddr2()) || (this
						.getNicAddr2() != null
						&& castOther.getNicAddr2() != null && this
						.getNicAddr2().equals(castOther.getNicAddr2())))
				&& ((this.getNicAddr3() == castOther.getNicAddr3()) || (this
						.getNicAddr3() != null
						&& castOther.getNicAddr3() != null && this
						.getNicAddr3().equals(castOther.getNicAddr3())))
				&& ((this.getNicAddr4() == castOther.getNicAddr4()) || (this
						.getNicAddr4() != null
						&& castOther.getNicAddr4() != null && this
						.getNicAddr4().equals(castOther.getNicAddr4())))
				&& ((this.getNicSex() == castOther.getNicSex()) || (this
						.getNicSex() != null && castOther.getNicSex() != null && this
						.getNicSex().equals(castOther.getNicSex())))
				&& ((this.getNicDateofbirth() == castOther.getNicDateofbirth()) || (this
						.getNicDateofbirth() != null
						&& castOther.getNicDateofbirth() != null && this
						.getNicDateofbirth().equals(
								castOther.getNicDateofbirth())))
				&& ((this.getNicDobApprox() == castOther.getNicDobApprox()) || (this
						.getNicDobApprox() != null
						&& castOther.getNicDobApprox() != null && this
						.getNicDobApprox().equals(castOther.getNicDobApprox())))
				&& ((this.getNicBloodgroup() == castOther.getNicBloodgroup()) || (this
						.getNicBloodgroup() != null
						&& castOther.getNicBloodgroup() != null && this
						.getNicBloodgroup()
						.equals(castOther.getNicBloodgroup())))
				&& ((this.getNicIssuedate() == castOther.getNicIssuedate()) || (this
						.getNicIssuedate() != null
						&& castOther.getNicIssuedate() != null && this
						.getNicIssuedate().equals(castOther.getNicIssuedate())))
				&& ((this.getNicPrevIssuedate() == castOther
						.getNicPrevIssuedate()) || (this.getNicPrevIssuedate() != null
						&& castOther.getNicPrevIssuedate() != null && this
						.getNicPrevIssuedate().equals(
								castOther.getNicPrevIssuedate())))
				&& ((this.getNicOfrId() == castOther.getNicOfrId()) || (this
						.getNicOfrId() != null
						&& castOther.getNicOfrId() != null && this
						.getNicOfrId().equals(castOther.getNicOfrId())))
				&& ((this.getNicOfcId() == castOther.getNicOfcId()) || (this
						.getNicOfcId() != null
						&& castOther.getNicOfcId() != null && this
						.getNicOfcId().equals(castOther.getNicOfcId())))
				&& ((this.getNicPhoto() == castOther.getNicPhoto()) || (this
						.getNicPhoto() != null
						&& castOther.getNicPhoto() != null && this
						.getNicPhoto().equals(castOther.getNicPhoto())))
				&& ((this.getNicSign() == castOther.getNicSign()) || (this
						.getNicSign() != null && castOther.getNicSign() != null && this
						.getNicSign().equals(castOther.getNicSign())))
				&& ((this.getNicThumbprint() == castOther.getNicThumbprint()) || (this
						.getNicThumbprint() != null
						&& castOther.getNicThumbprint() != null && this
						.getNicThumbprint()
						.equals(castOther.getNicThumbprint())))
				&& ((this.getNicRemarks() == castOther.getNicRemarks()) || (this
						.getNicRemarks() != null
						&& castOther.getNicRemarks() != null && this
						.getNicRemarks().equals(castOther.getNicRemarks())))
				&& ((this.getNicSignFlag() == castOther.getNicSignFlag()) || (this
						.getNicSignFlag() != null
						&& castOther.getNicSignFlag() != null && this
						.getNicSignFlag().equals(castOther.getNicSignFlag())))
				&& ((this.getNicPrintCounter() == castOther
						.getNicPrintCounter()) || (this.getNicPrintCounter() != null
						&& castOther.getNicPrintCounter() != null && this
						.getNicPrintCounter().equals(
								castOther.getNicPrintCounter())))
				&& ((this.getNicPrintDob() == castOther.getNicPrintDob()) || (this
						.getNicPrintDob() != null
						&& castOther.getNicPrintDob() != null && this
						.getNicPrintDob().equals(castOther.getNicPrintDob())))
				&& ((this.getNicIssuetime() == castOther.getNicIssuetime()) || (this
						.getNicIssuetime() != null
						&& castOther.getNicIssuetime() != null && this
						.getNicIssuetime().equals(castOther.getNicIssuetime())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getNicId() == null ? 0 : this.getNicId().hashCode());
		result = 37 * result
				+ (getNicUsrId() == null ? 0 : this.getNicUsrId().hashCode());
		result = 37
				* result
				+ (getNicDatecreated() == null ? 0 : this.getNicDatecreated()
						.hashCode());
		result = 37
				* result
				+ (getNicDatemodify() == null ? 0 : this.getNicDatemodify()
						.hashCode());
		result = 37 * result
				+ (getNicNumber() == null ? 0 : this.getNicNumber().hashCode());
		result = 37
				* result
				+ (getNicSurname() == null ? 0 : this.getNicSurname()
						.hashCode());
		result = 37 * result
				+ (getNicNames() == null ? 0 : this.getNicNames().hashCode());
		result = 37
				* result
				+ (getNicMaidenname() == null ? 0 : this.getNicMaidenname()
						.hashCode());
		result = 37 * result
				+ (getNicAddr1() == null ? 0 : this.getNicAddr1().hashCode());
		result = 37 * result
				+ (getNicAddr2() == null ? 0 : this.getNicAddr2().hashCode());
		result = 37 * result
				+ (getNicAddr3() == null ? 0 : this.getNicAddr3().hashCode());
		result = 37 * result
				+ (getNicAddr4() == null ? 0 : this.getNicAddr4().hashCode());
		result = 37 * result
				+ (getNicSex() == null ? 0 : this.getNicSex().hashCode());
		result = 37
				* result
				+ (getNicDateofbirth() == null ? 0 : this.getNicDateofbirth()
						.hashCode());
		result = 37
				* result
				+ (getNicDobApprox() == null ? 0 : this.getNicDobApprox()
						.hashCode());
		result = 37
				* result
				+ (getNicBloodgroup() == null ? 0 : this.getNicBloodgroup()
						.hashCode());
		result = 37
				* result
				+ (getNicIssuedate() == null ? 0 : this.getNicIssuedate()
						.hashCode());
		result = 37
				* result
				+ (getNicPrevIssuedate() == null ? 0 : this
						.getNicPrevIssuedate().hashCode());
		result = 37 * result
				+ (getNicOfrId() == null ? 0 : this.getNicOfrId().hashCode());
		result = 37 * result
				+ (getNicOfcId() == null ? 0 : this.getNicOfcId().hashCode());
		result = 37 * result
				+ (getNicPhoto() == null ? 0 : this.getNicPhoto().hashCode());
		result = 37 * result
				+ (getNicSign() == null ? 0 : this.getNicSign().hashCode());
		result = 37
				* result
				+ (getNicThumbprint() == null ? 0 : this.getNicThumbprint()
						.hashCode());
		result = 37
				* result
				+ (getNicRemarks() == null ? 0 : this.getNicRemarks()
						.hashCode());
		result = 37
				* result
				+ (getNicSignFlag() == null ? 0 : this.getNicSignFlag()
						.hashCode());
		result = 37
				* result
				+ (getNicPrintCounter() == null ? 0 : this.getNicPrintCounter()
						.hashCode());
		result = 37
				* result
				+ (getNicPrintDob() == null ? 0 : this.getNicPrintDob()
						.hashCode());
		result = 37
				* result
				+ (getNicIssuetime() == null ? 0 : this.getNicIssuetime()
						.hashCode());
		return result;
	}

}
