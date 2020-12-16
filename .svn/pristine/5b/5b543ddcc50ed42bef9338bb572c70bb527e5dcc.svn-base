package com.nec.asia.nic.comp.trans.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InvestigationHitData {
	public static final String transactionIdAndPassportNosToCancel__MULTIPLE_PASSPORT__DELIMITER = ";;;";

	String uploadJobId;
	String hitTransactionId;
	String searchResultId;
	String remarks;
	String duplicateDecision;
	String jobApproveRemarks;
	String jobRejectRemarks;
	String jobSuspendRemarks;
	String transactionIdAndPassportNumbersToCancel;

	public InvestigationHitData() {
		super();
	}

	public InvestigationHitData(String uploadJobId, String hitTransactionId, String searchResultId, String remarks,
			String duplicateDecision, String jobApproveRemarks, String jobRejectRemarks, String jobSuspendRemarks,
			String transactionIdAndPassportNumbersToCancel) {
		super();
		this.uploadJobId = uploadJobId;
		this.hitTransactionId = hitTransactionId;
		this.searchResultId = searchResultId;
		this.remarks = remarks;
		this.duplicateDecision = duplicateDecision;
		this.jobApproveRemarks = jobApproveRemarks;
		this.jobRejectRemarks = jobRejectRemarks;
		this.jobSuspendRemarks = jobSuspendRemarks;
		this.transactionIdAndPassportNumbersToCancel = transactionIdAndPassportNumbersToCancel;
	}

	public List<Passport> getPassportsToCancel_asList() {

		try {
			List<String> items = Arrays.asList(this.getTransactionIdAndPassportNumbersToCancel()
					.split(InvestigationHitData.transactionIdAndPassportNosToCancel__MULTIPLE_PASSPORT__DELIMITER));

			List<Passport> passports = new ArrayList<Passport>();
			for (String item : items) {
				Passport aPassport = new Passport().getPassportFrom_concatenated_transactionIdAndPassportNo(item);
				if (aPassport != null) {
					passports.add(aPassport);
				}
			}

			return passports;
		} catch (Exception e) {
			System.out.println("Exception getTransactionIdAndPassportNumbersToCancel_asList");
			return new ArrayList<Passport>();
		}
	}

	public String getUploadJobId() {
		return uploadJobId;
	}

	public void setUploadJobId(String uploadJobId) {
		this.uploadJobId = uploadJobId;
	}

	public String getHitTransactionId() {
		return hitTransactionId;
	}

	public void setHitTransactionId(String hitTransactionId) {
		this.hitTransactionId = hitTransactionId;
	}

	public String getSearchResultId() {
		return searchResultId;
	}

	public void setSearchResultId(String searchResultId) {
		this.searchResultId = searchResultId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getDuplicateDecision() {
		return duplicateDecision;
	}

	public void setDuplicateDecision(String duplicateDecision) {
		this.duplicateDecision = duplicateDecision;
	}

	public String getJobApproveRemarks() {
		return jobApproveRemarks;
	}

	public void setJobApproveRemarks(String jobApproveRemarks) {
		this.jobApproveRemarks = jobApproveRemarks;
	}

	public String getJobRejectRemarks() {
		return jobRejectRemarks;
	}

	public void setJobRejectRemarks(String jobRejectRemarks) {
		this.jobRejectRemarks = jobRejectRemarks;
	}

	public String getJobSuspendRemarks() {
		return jobSuspendRemarks;
	}

	public void setJobSuspendRemarks(String jobSuspendRemarks) {
		this.jobSuspendRemarks = jobSuspendRemarks;
	}

	public String getTransactionIdAndPassportNumbersToCancel() {
		return transactionIdAndPassportNumbersToCancel;
	}

	public void setTransactionIdAndPassportNumbersToCancel(String transactionIdAndPassportNumbersToCancel) {
		this.transactionIdAndPassportNumbersToCancel = transactionIdAndPassportNumbersToCancel;
	}

}
