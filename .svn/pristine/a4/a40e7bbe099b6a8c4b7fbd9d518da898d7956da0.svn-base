package service.perso.model;

import java.sql.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="UpdatePackageRequest")
@XmlType ( name = "UpdatePackageRequest",  propOrder = { "packageId","fullName","userName", "createdDate","amount","transactions" } )
public class UpdatePackageRequest {
	
	private String packageId;
	private String fullName;
	private String userName;
	private String createdDate;
	
	private int amount;
	
	private List<TransactionPerso> transactions;
	
	public UpdatePackageRequest() {
		super();
	}

	public String getPackageId() {
		return packageId;
	}
	
	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	/*public Date getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(Date dateCreate) {
		this.dateCreate = dateCreate;
	}
	*/
	public List<TransactionPerso> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<TransactionPerso> transactions) {
		this.transactions = transactions;
	}
	
}

