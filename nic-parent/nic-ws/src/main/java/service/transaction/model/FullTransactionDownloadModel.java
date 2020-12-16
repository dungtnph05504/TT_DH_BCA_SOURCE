package service.transaction.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import service.perso.model.UpdatePackageRequest;

@XmlRootElement(name="FullTransactionDownloadModel")
@XmlType(name="FullTransactionDownloadModel", propOrder={"transactionF", "mhandoverA", "mhandoverB", "mhandoverDC", "mpassport", "orgPerson"})
public class FullTransactionDownloadModel {

	private Transaction transactionF;
	private HandoverA mhandoverA;
	private HandoverB mhandoverB;
	private HandoverC mhandoverDC;
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
	public HandoverC getMhandoverDC() {
		return mhandoverDC;
	}
	public void setMhandoverDC(HandoverC mhandoverDC) {
		this.mhandoverDC = mhandoverDC;
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
