package service.transaction.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import service.perso.model.UpdatePackageRequest;

@XmlRootElement(name="FullTransactionModel")
@XmlType(name="FullTransactionModel", propOrder={"transactionF", "mhandoverA", "mhandoverB", "mhandoverC", "mpassport", "orgPerson"})
public class FullTransactionModel {

	private Transaction transactionF;
	private HandoverA mhandoverA;
	private HandoverB mhandoverB;
	private UpdatePackageRequest mhandoverC;
	private UpdatePassportModel mpassport;
	private PersonModel orgPerson;
	
	public Transaction getTransactionF() {
		return transactionF;
	}
	public void setTransactionF(Transaction transactionF) {
		this.transactionF = transactionF;
	}
	public HandoverA getMhandoverA() {
		return mhandoverA;
	}
	public void setMhandoverA(HandoverA mhandoverA) {
		this.mhandoverA = mhandoverA;
	}
	public HandoverB getMhandoverB() {
		return mhandoverB;
	}
	public void setMhandoverB(HandoverB mhandoverB) {
		this.mhandoverB = mhandoverB;
	}
	public UpdatePackageRequest getMhandoverC() {
		return mhandoverC;
	}
	public void setMhandoverC(UpdatePackageRequest mhandoverC) {
		this.mhandoverC = mhandoverC;
	}

	public UpdatePassportModel getMpassport() {
		return mpassport;
	}
	public void setMpassport(UpdatePassportModel mpassport) {
		this.mpassport = mpassport;
	}
	public PersonModel getOrgPerson() {
		return orgPerson;
	}
	public void setOrgPerson(PersonModel orgPerson) {
		this.orgPerson = orgPerson;
	}
	
}
