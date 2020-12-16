package service.transaction.model;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="ReceiptManager")
@XmlType(name="ReceiptManager", propOrder={"receiptNo", "officeName", "name", "dob", "address", "nin",
		"phone", "paymentAmount", "paymentFlag", "handovers", "bills",
		"regSiteCode", "dateOfIssue", "dateInWeek", "note", "placeOfReciept",
		"deliveryAtOffice", "deliveryOffice", "amountOfPassport", "amountOfRegistration", "amountOfPerson",
		"amountOfImage", "documentType", "prevPassportNo", "addedDocuments", "documentaryNo",
		"documentaryIssued", "status", "isDelivered", "isPostOffice", "noteOfDelivery", "signName",
		"docOfDelivery", "documentaryOffice", "documentaryAddress", "listCode", "inputCompleted", "deletedDate",
		"deletedBy", "deletedName", "deletedReason","feeRecieptPayment", "createByName", "createBy","receivedBy", "createDate"})

public class ReceiptManager {
	private String receiptNo;
	private String officeName;
	private String name;
	private String dob;
	private String address;
	private String nin;
	private String phone;
	private Double paymentAmount;
	private String paymentFlag;
	private List<DetailHandover> handovers;
	private List<ReceiptBill> bills;
	
	private String regSiteCode;
	private String dateOfIssue;
	private String dateInWeek;
	private String note;
	private String placeOfReciept;
	private String deliveryAtOffice;
	private String deliveryOffice;
	private Integer amountOfPassport;
	private Integer amountOfRegistration;
	private Integer amountOfPerson;
	private Integer amountOfImage;
	private String documentType;
	private String prevPassportNo;
	private String addedDocuments;
	private String documentaryNo;
	private String documentaryIssued;
	private String status;
	private String isDelivered;
	private String isPostOffice;
	private String noteOfDelivery;
	private String signName;
	private String docOfDelivery;
	private String documentaryOffice;
	private String documentaryAddress;
	private String listCode;
	private String inputCompleted;
	private String deletedDate;
	private String deletedBy;
	private String deletedName;
	private String deletedReason;
	private List<FeeRecieptPaymentModel> feeRecieptPayment;
	private String createByName;
	private String createBy;
	private String receivedBy;
	private String createDate;
	
	
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
	public String getCreateByName() {
		return createByName;
	}
	public void setCreateByName(String createByName) {
		this.createByName = createByName;
	}
	public String getReceivedBy() {
		return receivedBy;
	}
	public void setReceivedBy(String receivedBy) {
		this.receivedBy = receivedBy;
	}
	public String getReceiptNo() {
		return receiptNo;
	}
	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}
	public String getOfficeName() {
		return officeName;
	}
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getNin() {
		return nin;
	}
	public void setNin(String nin) {
		this.nin = nin;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Double getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public String getPaymentFlag() {
		return paymentFlag;
	}
	public void setPaymentFlag(String paymentFlag) {
		this.paymentFlag = paymentFlag;
	}
	public List<DetailHandover> getHandovers() {
		return handovers;
	}
	public void setHandovers(List<DetailHandover> handovers) {
		this.handovers = handovers;
	}
	public List<ReceiptBill> getBills() {
		return bills;
	}
	public void setBills(List<ReceiptBill> bills) {
		this.bills = bills;
	}
	public String getRegSiteCode() {
		return regSiteCode;
	}
	public void setRegSiteCode(String regSiteCode) {
		this.regSiteCode = regSiteCode;
	}
	public String getDateOfIssue() {
		return dateOfIssue;
	}
	public void setDateOfIssue(String dateOfIssue) {
		this.dateOfIssue = dateOfIssue;
	}
	public String getDateInWeek() {
		return dateInWeek;
	}
	public void setDateInWeek(String dateInWeek) {
		this.dateInWeek = dateInWeek;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getPlaceOfReciept() {
		return placeOfReciept;
	}
	public void setPlaceOfReciept(String placeOfReciept) {
		this.placeOfReciept = placeOfReciept;
	}
	public String getDeliveryAtOffice() {
		return deliveryAtOffice;
	}
	public void setDeliveryAtOffice(String deliveryAtOffice) {
		this.deliveryAtOffice = deliveryAtOffice;
	}
	public String getDeliveryOffice() {
		return deliveryOffice;
	}
	public void setDeliveryOffice(String deliveryOffice) {
		this.deliveryOffice = deliveryOffice;
	}
	public Integer getAmountOfPassport() {
		return amountOfPassport;
	}
	public void setAmountOfPassport(Integer amountOfPassport) {
		this.amountOfPassport = amountOfPassport;
	}
	public Integer getAmountOfRegistration() {
		return amountOfRegistration;
	}
	public void setAmountOfRegistration(Integer amountOfRegistration) {
		this.amountOfRegistration = amountOfRegistration;
	}
	public Integer getAmountOfPerson() {
		return amountOfPerson;
	}
	public void setAmountOfPerson(Integer amountOfPerson) {
		this.amountOfPerson = amountOfPerson;
	}
	public Integer getAmountOfImage() {
		return amountOfImage;
	}
	public void setAmountOfImage(Integer amountOfImage) {
		this.amountOfImage = amountOfImage;
	}
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	public String getPrevPassportNo() {
		return prevPassportNo;
	}
	public void setPrevPassportNo(String prevPassportNo) {
		this.prevPassportNo = prevPassportNo;
	}
	public String getAddedDocuments() {
		return addedDocuments;
	}
	public void setAddedDocuments(String addedDocuments) {
		this.addedDocuments = addedDocuments;
	}
	public String getDocumentaryNo() {
		return documentaryNo;
	}
	public void setDocumentaryNo(String documentaryNo) {
		this.documentaryNo = documentaryNo;
	}
	public String getDocumentaryIssued() {
		return documentaryIssued;
	}
	public void setDocumentaryIssued(String documentaryIssued) {
		this.documentaryIssued = documentaryIssued;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getIsDelivered() {
		return isDelivered;
	}
	public void setIsDelivered(String isDelivered) {
		this.isDelivered = isDelivered;
	}
	public String getIsPostOffice() {
		return isPostOffice;
	}
	public void setIsPostOffice(String isPostOffice) {
		this.isPostOffice = isPostOffice;
	}
	public String getNoteOfDelivery() {
		return noteOfDelivery;
	}
	public void setNoteOfDelivery(String noteOfDelivery) {
		this.noteOfDelivery = noteOfDelivery;
	}
	public String getSignName() {
		return signName;
	}
	public void setSignName(String signName) {
		this.signName = signName;
	}
	public String getDocOfDelivery() {
		return docOfDelivery;
	}
	public void setDocOfDelivery(String docOfDelivery) {
		this.docOfDelivery = docOfDelivery;
	}
	public String getDocumentaryOffice() {
		return documentaryOffice;
	}
	public void setDocumentaryOffice(String documentaryOffice) {
		this.documentaryOffice = documentaryOffice;
	}
	public String getDocumentaryAddress() {
		return documentaryAddress;
	}
	public void setDocumentaryAddress(String documentaryAddress) {
		this.documentaryAddress = documentaryAddress;
	}
	public String getListCode() {
		return listCode;
	}
	public void setListCode(String listCode) {
		this.listCode = listCode;
	}
	public String getInputCompleted() {
		return inputCompleted;
	}
	public void setInputCompleted(String inputCompleted) {
		this.inputCompleted = inputCompleted;
	}
	public String getDeletedDate() {
		return deletedDate;
	}
	public void setDeletedDate(String deletedDate) {
		this.deletedDate = deletedDate;
	}
	public String getDeletedBy() {
		return deletedBy;
	}
	public void setDeletedBy(String deletedBy) {
		this.deletedBy = deletedBy;
	}
	public String getDeletedName() {
		return deletedName;
	}
	public void setDeletedName(String deletedName) {
		this.deletedName = deletedName;
	}
	public String getDeletedReason() {
		return deletedReason;
	}
	public void setDeletedReason(String deletedReason) {
		this.deletedReason = deletedReason;
	}
	public List<FeeRecieptPaymentModel> getFeeRecieptPayment() {
		return feeRecieptPayment;
	}
	public void setFeeRecieptPayment(List<FeeRecieptPaymentModel> feeRecieptPayment) {
		this.feeRecieptPayment = feeRecieptPayment;
	}
	
}
