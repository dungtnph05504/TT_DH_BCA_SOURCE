package service.investigation.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "InfoGetDetailBuffPerson")
@XmlType(name = "InfoGetDetailBuffPerson", propOrder = {
		"transactionId", "personCode", "transactionMasterId", "maCaNhan"
})
public class InfoGetDetailBuffPerson {
	private String transactionMasterId;
	private String transactionId;
	private String personCode;
	private String maCaNhan;
	
	public String getMaCaNhan() {
		return maCaNhan;
	}
	public void setMaCaNhan(String maCaNhan) {
		this.maCaNhan = maCaNhan;
	}
	public String getTransactionMasterId() {
		return transactionMasterId;
	}
	public void setTransactionMasterId(String transactionMasterId) {
		this.transactionMasterId = transactionMasterId;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getPersonCode() {
		return personCode;
	}
	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}
	
}
