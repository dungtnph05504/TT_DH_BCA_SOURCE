package com.nec.asia.nic.comp.trans.domain;

import java.util.Date;

public class EppIssuance {
	private Long id;
	private	String	receiptNo;
	private	String	documentCode;
	private	String	ioDocCode;
	private	String	ioDocType;
	private	Date	dateOfDelivery;
	private	String	deliveryUser;
	private	String	deliveryName;
	private	String	deliveryNote;
	private	String	deliveryOffice;
	private	String	recipient;
	private	String	recipientIdNumber;
	private	String	recipientOffice;
	private	String	issResult;
	private	String	traKQId;
	
	public EppIssuance() {
	}
	public EppIssuance(Long id, String receiptNo, String documentCode,
			String ioDocCode, String ioDocType, Date dateOfDelivery,
			String deliveryUser, String deliveryName, String deliveryNote,
			String deliveryOffice, String recipient, String recipientIdNumber,
			String recipientOffice, String issResult, String traKQId) {
		super();
		this.id = id;
		this.receiptNo = receiptNo;
		this.documentCode = documentCode;
		this.ioDocCode = ioDocCode;
		this.ioDocType = ioDocType;
		this.dateOfDelivery = dateOfDelivery;
		this.deliveryUser = deliveryUser;
		this.deliveryName = deliveryName;
		this.deliveryNote = deliveryNote;
		this.deliveryOffice = deliveryOffice;
		this.recipient = recipient;
		this.recipientIdNumber = recipientIdNumber;
		this.recipientOffice = recipientOffice;
		this.issResult = issResult;
		this.traKQId = traKQId;
	}

	public String getDeliveryNote() {
		return deliveryNote;
	}

	public void setDeliveryNote(String deliveryNote) {
		this.deliveryNote = deliveryNote;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getReceiptNo() {
		return receiptNo;
	}
	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}
	public String getDocumentCode() {
		return documentCode;
	}
	public void setDocumentCode(String documentCode) {
		this.documentCode = documentCode;
	}
	public String getIoDocCode() {
		return ioDocCode;
	}
	public void setIoDocCode(String ioDocCode) {
		this.ioDocCode = ioDocCode;
	}
	public String getIoDocType() {
		return ioDocType;
	}
	public void setIoDocType(String ioDocType) {
		this.ioDocType = ioDocType;
	}
	public Date getDateOfDelivery() {
		return dateOfDelivery;
	}
	public void setDateOfDelivery(Date dateOfDelivery) {
		this.dateOfDelivery = dateOfDelivery;
	}
	public String getDeliveryUser() {
		return deliveryUser;
	}
	public void setDeliveryUser(String deliveryUser) {
		this.deliveryUser = deliveryUser;
	}
	public String getDeliveryName() {
		return deliveryName;
	}
	public void setDeliveryName(String deliveryName) {
		this.deliveryName = deliveryName;
	}
	public String getDeliveryOffice() {
		return deliveryOffice;
	}
	public void setDeliveryOffice(String deliveryOffice) {
		this.deliveryOffice = deliveryOffice;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public String getRecipientIdNumber() {
		return recipientIdNumber;
	}
	public void setRecipientIdNumber(String recipientIdNumber) {
		this.recipientIdNumber = recipientIdNumber;
	}
	public String getRecipientOffice() {
		return recipientOffice;
	}
	public void setRecipientOffice(String recipientOffice) {
		this.recipientOffice = recipientOffice;
	}
	public String getIssResult() {
		return issResult;
	}
	public void setIssResult(String issResult) {
		this.issResult = issResult;
	}
	public String getTraKQId() {
		return traKQId;
	}
	public void setTraKQId(String traKQId) {
		this.traKQId = traKQId;
	}
	
}
