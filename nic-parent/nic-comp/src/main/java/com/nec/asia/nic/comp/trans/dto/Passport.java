package com.nec.asia.nic.comp.trans.dto;

import java.util.Date;

public class Passport {
	public static final String transactionIdAndPassportNos__PASSPORT_KEYS__DELIMITER = ":::";

	private String transactionId;
	private String passportNo;

	private String passportType;
	private String passportType_forDisplay;

	private Date dateOfIssue;
	private Date dateOfExpiry;

	private String status;
	private String status_forDisplay;

	public String getTransactionIdAndPassportNo_concatenated() {
		try {
			return this.getTransactionId() + Passport.transactionIdAndPassportNos__PASSPORT_KEYS__DELIMITER
					+ this.getPassportNo();
		} catch (Exception e) {
			return "";
		}
	}

	public Passport getPassportFrom_concatenated_transactionIdAndPassportNo(String concatenated) {
		try {
			String[] items = concatenated.split(Passport.transactionIdAndPassportNos__PASSPORT_KEYS__DELIMITER);
			return new Passport(items[0], items[1]);
		} catch (Exception e) {
			return null;
		}
	}

	public Passport() {
		super();
	}

	public Passport(String transactionId, String passportNo) {
		super();
		this.transactionId = transactionId;
		this.passportNo = passportNo;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getPassportNo() {
		return passportNo;
	}

	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}

	public String getPassportType() {
		return passportType;
	}

	public void setPassportType(String passportType) {
		this.passportType = passportType;
	}

	public String getPassportType_forDisplay() {
		return passportType_forDisplay;
	}

	public void setPassportType_forDisplay(String passportType_forDisplay) {
		this.passportType_forDisplay = passportType_forDisplay;
	}

	public Date getDateOfIssue() {
		return dateOfIssue;
	}

	public void setDateOfIssue(Date dateOfIssue) {
		this.dateOfIssue = dateOfIssue;
	}

	public Date getDateOfExpiry() {
		return dateOfExpiry;
	}

	public void setDateOfExpiry(Date dateOfExpiry) {
		this.dateOfExpiry = dateOfExpiry;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus_forDisplay() {
		return status_forDisplay;
	}

	public void setStatus_forDisplay(String status_forDisplay) {
		this.status_forDisplay = status_forDisplay;
	}
}
