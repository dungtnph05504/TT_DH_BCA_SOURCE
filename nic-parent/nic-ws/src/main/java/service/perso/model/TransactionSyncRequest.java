package service.perso.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.nec.asia.nic.comp.listHandover.domain.NicListHandover;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.domain.NicTransactionPackage;
import com.nec.asia.nic.comp.trans.domain.NicTransactionPayment;
import com.nec.asia.nic.comp.trans.domain.NicTransactionPaymentDetail;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.dto.NicListHandoverDto;
import com.nec.asia.nic.comp.trans.dto.NicPaymentJsonDto;
import com.nec.asia.nic.comp.trans.dto.NicTransactionDto;
import com.nec.asia.nic.comp.trans.dto.NicTransactionJsonDto;
import com.nec.asia.nic.comp.trans.dto.NicTransactionPaymentDto;
import com.nec.asia.nic.comp.trans.dto.NicUploadJobDto;
import com.nec.asia.nic.comp.trans.dto.NicUploadJobJsonDto;

@XmlRootElement(name="TransactionSyncRequest")
@XmlType ( name = "TransactionSyncRequest",  propOrder = { "nicUp", "handover", "listLog", "listPdetail", "payment", "tran", "listHandover"} )

public class TransactionSyncRequest {
	private NicUploadJobJsonDto nicUp;
	private List<NicListHandoverDto> handover = new ArrayList<NicListHandoverDto>();
	private List<NicTransactionLog> listLog = new ArrayList<NicTransactionLog>();
	private List<NicTransactionPaymentDetail> listPdetail = new ArrayList<NicTransactionPaymentDetail>();
	private NicPaymentJsonDto payment;
	private NicTransactionJsonDto tran;
	private List<NicTransactionPackage> listHandover = new ArrayList<NicTransactionPackage>();
	private Boolean personFlag;
	
	
	public Boolean getPersonFlag() {
		return personFlag;
	}
	public void setPersonFlag(Boolean personFlag) {
		this.personFlag = personFlag;
	}
	public NicUploadJobJsonDto getNicUp() {
		return nicUp;
	}
	public void setNicUp(NicUploadJobJsonDto nicUp) {
		this.nicUp = nicUp;
	}
	public List<NicListHandoverDto> getHandover() {
		return handover;
	}
	public void setHandover(List<NicListHandoverDto> handover) {
		this.handover = handover;
	}
	public List<NicTransactionLog> getListLog() {
		return listLog;
	}
	public void setListLog(List<NicTransactionLog> listLog) {
		this.listLog = listLog;
	}
	public List<NicTransactionPaymentDetail> getListPdetail() {
		return listPdetail;
	}
	public void setListPdetail(List<NicTransactionPaymentDetail> listPdetail) {
		this.listPdetail = listPdetail;
	}
	public NicPaymentJsonDto getPayment() {
		return payment;
	}
	public void setPayment(NicPaymentJsonDto payment) {
		this.payment = payment;
	}
	public NicTransactionJsonDto getTran() {
		return tran;
	}
	public void setTran(NicTransactionJsonDto tran) {
		this.tran = tran;
	}
	public List<NicTransactionPackage> getListHandover() {
		return listHandover;
	}
	public void setListHandover(List<NicTransactionPackage> listHandover) {
		this.listHandover = listHandover;
	}
	
	
}
