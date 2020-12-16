package service.perso.model;

import java.sql.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="TransactionPerso")
@XmlType ( name = "TransactionPerso",  propOrder = { "transactionId","passportNo","serialPhoi","icaoLine1",
		"icaoLine2","signer","positionSigner",
		"printSite","dateOfIssue","dateOfExpire"} )

public class TransactionPerso {
	private String transactionId;
	private String passportNo;
	private String serialPhoi;
	private String icaoLine1;
	private String icaoLine2;
	private String signer;
	private String positionSigner;
	private String printSite;
	private String dateOfIssue;
	private String dateOfExpire;
	
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	
	
	public String getTransactionId() {
		return transactionId;
	}
	
	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}
	
	
	public String getPassportNo() {
		return passportNo;
	}


	public String getSerialPhoi() {
		return serialPhoi;
	}


	public void setSerialPhoi(String serialPhoi) {
		this.serialPhoi = serialPhoi;
	}


	public String getIcaoLine1() {
		return icaoLine1;
	}


	public void setIcaoLine1(String icaoLine1) {
		this.icaoLine1 = icaoLine1;
	}

	public String getIcaoLine2() {
		return icaoLine2;
	}


	public void setIcaoLine2(String icaoLine2) {
		this.icaoLine2 = icaoLine2;
	}

	public String getSigner() {
		return signer;
	}


	public void setSigner(String signer) {
		this.signer = signer;
	}


	public String getPositionSigner() {
		return positionSigner;
	}


	public void setPositionSigner(String positionSigner) {
		this.positionSigner = positionSigner;
	}


	public String getPrintSite() {
		return printSite;
	}


	public void setPrintSite(String printSite) {
		this.printSite = printSite;
	}


	public String getDateOfIssue() {
		return dateOfIssue;
	}


	public void setDateOfIssue(String dateOfIssue) {
		this.dateOfIssue = dateOfIssue;
	}


	public String getDateOfExpire() {
		return dateOfExpire;
	}


	public void setDateOfExpire(String dateOfExpire) {
		this.dateOfExpire = dateOfExpire;
	}
	
	
}
