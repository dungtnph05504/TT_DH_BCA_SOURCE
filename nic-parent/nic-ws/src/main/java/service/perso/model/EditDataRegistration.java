package service.perso.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.nec.asia.nic.comp.trans.domain.EppBlacklist;
import com.nec.asia.nic.comp.trans.domain.EppBlacklistAttachment;

@XmlRootElement(name="EditDataRegistration")
@XmlType ( name = "EditDataRegistration",  propOrder = { "transactionId", "nin","fullname", "surname", 
		"middlename", "lastname", "styleDob", "printDob", "dob", "addressline4", 
		"addressline5", "addressline1" } )

public class EditDataRegistration {
	private String transactionId;
	private String nin;
	private String fullname;
	private String surname;
	private String middlename;
	private String lastname;
	private String styleDob;
	private String printDob;
	private String dob;
	private String addressline4;
	private String addressline5;
	private String addressline1;
	
	
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getNin() {
		return nin;
	}
	public void setNin(String nin) {
		this.nin = nin;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getMiddlename() {
		return middlename;
	}
	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getStyleDob() {
		return styleDob;
	}
	public void setStyleDob(String styleDob) {
		this.styleDob = styleDob;
	}
	public String getPrintDob() {
		return printDob;
	}
	public void setPrintDob(String printDob) {
		this.printDob = printDob;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getAddressline4() {
		return addressline4;
	}
	public void setAddressline4(String addressline4) {
		this.addressline4 = addressline4;
	}
	public String getAddressline5() {
		return addressline5;
	}
	public void setAddressline5(String addressline5) {
		this.addressline5 = addressline5;
	}
	public String getAddressline1() {
		return addressline1;
	}
	public void setAddressline1(String addressline1) {
		this.addressline1 = addressline1;
	}
}
