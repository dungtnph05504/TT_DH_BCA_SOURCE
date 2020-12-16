package service.transaction.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="TransactionStatusInfo")
@XmlType(name="TransactionStatusInfo", propOrder={"id", "transactionId", "transactionStatus", "updateBy", "updateDate", "updateWkstnID", "passportNo"})
public class TransactionStatusInfo {
	private Double id;
	private String transactionId;
	private String transactionStatus;
	private String updateBy;
	private String updateDate;
	private String updateWkstnID;
	private String passportNo;
	
	public String getPassportNo() {
		return passportNo;
	}
	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}
	public Double getId() {
		return id;
	}
	public void setId(Double id) {
		this.id = id;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getTransactionStatus() {
		return transactionStatus;
	}
	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public String getUpdateWkstnID() {
		return updateWkstnID;
	}
	public void setUpdateWkstnID(String updateWkstnID) {
		this.updateWkstnID = updateWkstnID;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	
}
