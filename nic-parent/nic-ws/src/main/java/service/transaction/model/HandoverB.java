package service.transaction.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "HandoverB")
@XmlType(name = "HandoverB", propOrder = { "packageId", "offerUserName",
		"offerDate", "approveName", "approveDate", "siteCode", "type", "count",
		"idQueue", "handovers", "packageOldId", "proposalName",
		"approvePosition", "creatorName", "approveUser", "passportCancelInfo" })
public class HandoverB {
	private String packageId;
	private String packageOldId;
	private String offerUserName;
	private String offerDate;
	private String approveUser;
	private String approveName;
	private String approveDate;
	private String proposalName;
	private String approvePosition;
	private String creatorName;
	private String siteCode;
	private int type;
	private int count;
	private Long idQueue;
	private List<DetailHandoverB> handovers;
	private List<PassportCancelInput> passportCancelInfo;

	public List<PassportCancelInput> getPassportCancelInfo() {
		return passportCancelInfo;
	}

	public void setPassportCancelInfo(List<PassportCancelInput> passportCancelInfo) {
		this.passportCancelInfo = passportCancelInfo;
	}

	public String getApproveUser() {
		return approveUser;
	}

	public void setApproveUser(String approveUser) {
		this.approveUser = approveUser;
	}

	public String getPackageOldId() {
		return packageOldId;
	}

	public void setPackageOldId(String packageOldId) {
		this.packageOldId = packageOldId;
	}

	public String getProposalName() {
		return proposalName;
	}

	public void setProposalName(String proposalName) {
		this.proposalName = proposalName;
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

	public String getApproveName() {
		return approveName;
	}

	public void setApproveName(String approveName) {
		this.approveName = approveName;
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

	public List<DetailHandoverB> getHandovers() {
		return handovers;
	}

	public void setHandovers(List<DetailHandoverB> handovers) {
		this.handovers = handovers;
	}

}
