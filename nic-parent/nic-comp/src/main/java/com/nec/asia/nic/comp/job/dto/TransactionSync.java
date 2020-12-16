package com.nec.asia.nic.comp.job.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.domain.NicTransactionPayment;
import com.nec.asia.nic.comp.trans.domain.NicTransactionPaymentDetail;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;

@XmlRootElement(name="TransactionSync")
@XmlType ( name = "TransactionSync",  propOrder = { "nicUp", "listattachment", "listLog", "listPdetail", "payment", "tran", "regis", "handoverPackage"} )

public class TransactionSync {
	private String nicUp;
	private String listattachment;
	private String listLog;
	private String listPdetail;
	private String payment;
	private String tran;
	private String regis;
	private String handoverPackage;
	
	public String getNicUp() {
		return nicUp;
	}
	public void setNicUp(String nicUp) {
		this.nicUp = nicUp;
	}
	public String getListattachment() {
		return listattachment;
	}
	public void setListattachment(String listattachment) {
		this.listattachment = listattachment;
	}
	public String getListLog() {
		return listLog;
	}
	public void setListLog(String listLog) {
		this.listLog = listLog;
	}
	public String getListPdetail() {
		return listPdetail;
	}
	public void setListPdetail(String listPdetail) {
		this.listPdetail = listPdetail;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public String getTran() {
		return tran;
	}
	public void setTran(String tran) {
		this.tran = tran;
	}
	public String getRegis() {
		return regis;
	}
	public void setRegis(String regis) {
		this.regis = regis;
	}
	public String getHandoverPackage() {
		return handoverPackage;
	}
	public void setHandoverPackage(String handoverPackage) {
		this.handoverPackage = handoverPackage;
	}
	
}
