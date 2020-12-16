package service.transaction.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="HandoverA")
@XmlType(name="HandoverA", propOrder = {"packageId","offerUserName","offerDate","approveUserName",
		"approveDate","siteCode","type","count", "idQueue", "receipts",
		"proposalName","approveName", "approvePosition", "creatorName","updateDate", "updateBy", "updateByName", "dateOfDelivery", "placeOfDelivery"})
public class HandoverA {
	private String packageId;
	private String offerUserName;	
	private String offerDate;
	private String approveUserName;	
	private String approveDate;
	private String siteCode;
	private String type;
	private int count;
	private Long idQueue;
	private List<ReceiptManager> receipts;
	private String proposalName;
	private String approveName;	
	private String approvePosition;
	private String creatorName;
	private String updateDate;
	private String updateBy;
	private String updateByName;
	private String dateOfDelivery;
	private String placeOfDelivery;
	
	public String getPackageId() {
		return packageId;
	}
	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}
	public String getOfferUserName() {
		return offerUserName;
	}
	public void setOfferUserName(String offerUserName) {
		this.offerUserName = offerUserName;
	}
	public String getOfferDate() {
		return offerDate;
	}
	public void setOfferDate(String offerDate) {
		this.offerDate = offerDate;
	}
	public String getApproveUserName() {
		return approveUserName;
	}
	public void setApproveUserName(String approveUserName) {
		this.approveUserName = approveUserName;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Long getIdQueue() {
		return idQueue;
	}
	public void setIdQueue(Long idQueue) {
		this.idQueue = idQueue;
	}
	public List<ReceiptManager> getReceipts() {
		return receipts;
	}
	public void setReceipts(List<ReceiptManager> receipts) {
		this.receipts = receipts;
	}
	public String getProposalName() {
		return proposalName;
	}
	public void setProposalName(String proposalName) {
		this.proposalName = proposalName;
	}
	public String getApproveName() {
		return approveName;
	}
	public void setApproveName(String approveName) {
		this.approveName = approveName;
	}
	public String getApprovePosition() {
		return approvePosition;
	}
	public void setApprovePosition(String approvePosition) {
		this.approvePosition = approvePosition;
	}
	public String getCreatorName() {
		return creatorName;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public String getUpdateByName() {
		return updateByName;
	}
	public void setUpdateByName(String updateByName) {
		this.updateByName = updateByName;
	}
	public String getDateOfDelivery() {
		return dateOfDelivery;
	}
	public void setDateOfDelivery(String dateOfDelivery) {
		this.dateOfDelivery = dateOfDelivery;
	}
	public String getPlaceOfDelivery() {
		return placeOfDelivery;
	}
	public void setPlaceOfDelivery(String placeOfDelivery) {
		this.placeOfDelivery = placeOfDelivery;
	}
	
}
