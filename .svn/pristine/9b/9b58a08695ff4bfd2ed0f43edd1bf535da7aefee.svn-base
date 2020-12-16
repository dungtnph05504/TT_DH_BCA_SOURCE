package service.transaction.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "InfoJoinPerson")
@XmlType(name = "InfoJoinPerson", propOrder = { "transactionId", "userProcess",
		"dateProcess", "note", "personCode", "personStage", "personOrgCode", "person", "approverName", "approveDate" })
public class InfoJoinPerson {

	private String transactionId;
	private String userProcess;
	private String dateProcess;
	private String note;
	private String personCode;
	private String personStage;
	private String personOrgCode;
	//Hoald thêm
	private PersonModel person;
	private String approverName;
	private String approveDate;
	
	public String getApproverName() {
		return approverName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	public String getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(String approveDate) {
		this.approveDate = approveDate;
	}

	public PersonModel getPerson() {
		return person;
	}

	public void setPerson(PersonModel person) {
		this.person = person;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getUserProcess() {
		return userProcess;
	}

	public void setUserProcess(String userProcess) {
		this.userProcess = userProcess;
	}

	public String getDateProcess() {
		return dateProcess;
	}

	public void setDateProcess(String dateProcess) {
		this.dateProcess = dateProcess;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPersonCode() {
		return personCode;
	}

	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}

	public String getPersonStage() {
		return personStage;
	}

	public void setPersonStage(String personStage) {
		this.personStage = personStage;
	}

	public String getPersonOrgCode() {
		return personOrgCode;
	}

	public void setPersonOrgCode(String personOrgCode) {
		this.personOrgCode = personOrgCode;
	}
}
