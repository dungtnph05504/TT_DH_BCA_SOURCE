package service.transaction.model;

import java.util.List;

public class UpdateTranStatusProcess {

	private String userName;
	private String fullName;
	private String processDate;
	private List<TransactionIdInput> listTranId;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getProcessDate() {
		return processDate;
	}
	public void setProcessDate(String processDate) {
		this.processDate = processDate;
	}
	public List<TransactionIdInput> getListTranId() {
		return listTranId;
	}
	public void setListTranId(List<TransactionIdInput> listTranId) {
		this.listTranId = listTranId;
	}
	
	
}
