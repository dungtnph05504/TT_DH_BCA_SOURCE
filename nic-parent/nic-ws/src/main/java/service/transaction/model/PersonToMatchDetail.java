package service.transaction.model;

import java.util.List;

public class PersonToMatchDetail {
	private TransactionDetailToHanding transaction;
	private List<OrgPersonDetailToHanding> listOrgPerson;
	public TransactionDetailToHanding getTransaction() {
		return transaction;
	}
	public void setTransaction(TransactionDetailToHanding transaction) {
		this.transaction = transaction;
	}
	public List<OrgPersonDetailToHanding> getListOrgPerson() {
		return listOrgPerson;
	}
	public void setListOrgPerson(List<OrgPersonDetailToHanding> listOrgPerson) {
		this.listOrgPerson = listOrgPerson;
	}
	
}
