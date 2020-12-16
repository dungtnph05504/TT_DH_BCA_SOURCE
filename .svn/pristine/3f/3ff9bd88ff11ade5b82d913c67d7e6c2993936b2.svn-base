package service.transaction.model;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.nec.asia.nic.util.DateHandler;

@XmlRootElement(name="Handover")
@XmlType(name="Handover", propOrder={"packageId", "offerName", "offerDate", "approveName", "approveDate", "siteCode", "type", "count", "idQueue", "dtHandover", "receipts", "dtReceipts", "receiptBills", "payments"})
public class Handover {
	private String packageId;
	private String offerName;	
	private String offerDate;
	private String approveName;	
	private String approveDate;
	private String siteCode;
	private int type;
	private int count;
	private Long idQueue;
	private List<DetailHandover> dtHandover;
	private List<ReceiptManager> receipts;
	private List<DetailReceipt> dtReceipts;
	private List<ReceiptBill> receiptBills;
	private List<PaymentDetail> payments;
	
	
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getPackageId() {
		return packageId;
	}
	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}
	public String getOfferName() {
		return offerName;
	}
	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}
	
	public String getApproveName() {
		return approveName;
	}
	public void setApproveName(String approveName) {
		this.approveName = approveName;
	}
	
	public String getOfferDate() {
		return offerDate;
	}
	public void setOfferDate(String offerDate) {
		this.offerDate = offerDate;
	}
	public String getApproveDate() {
		return approveDate;
	}
	public void setApproveDate(String approveDate) {
		this.approveDate = approveDate;
	}
	public String getSiteCode() {
		return siteCode;
	}
	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public List<DetailHandover> getDtHandover() {
		return dtHandover;
	}
	public void setDtHandover(List<DetailHandover> dtHandover) {
		this.dtHandover = dtHandover;
	}
	public List<ReceiptManager> getReceipts() {
		return receipts;
	}
	public void setReceipts(List<ReceiptManager> receipts) {
		this.receipts = receipts;
	}
	public List<DetailReceipt> getDtReceipts() {
		return dtReceipts;
	}
	public void setDtReceipts(List<DetailReceipt> dtReceipts) {
		this.dtReceipts = dtReceipts;
	}
	public List<ReceiptBill> getReceiptBills() {
		return receiptBills;
	}
	public void setReceiptBills(List<ReceiptBill> receiptBills) {
		this.receiptBills = receiptBills;
	}
	public List<PaymentDetail> getPayments() {
		return payments;
	}
	public void setPayments(List<PaymentDetail> payments) {
		this.payments = payments;
	}
	public Long getIdQueue() {
		return idQueue;
	}
	public void setIdQueue(Long idQueue) {
		this.idQueue = idQueue;
	}
	
	
	
}
