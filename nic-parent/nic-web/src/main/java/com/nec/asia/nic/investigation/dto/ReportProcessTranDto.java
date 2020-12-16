package com.nec.asia.nic.investigation.dto;

public class ReportProcessTranDto {
	
	private int amountTotal;
	private int amountApprove;
	private int amountReject;
	private int amountAboutToExpire;
	private int amountOutOfDate;	
	
	public ReportProcessTranDto() {
		super();
	}

	public ReportProcessTranDto(int amountTotal, int amountApprove,
			int amountReject, int amountAboutToExpire, int amountOutOfDate) {
		super();
		this.amountTotal = amountTotal;
		this.amountApprove = amountApprove;
		this.amountReject = amountReject;
		this.amountAboutToExpire = amountAboutToExpire;
		this.amountOutOfDate = amountOutOfDate;
	}

	public int getAmountTotal() {
		return amountTotal;
	}

	public void setAmountTotal(int amountTotal) {
		this.amountTotal = amountTotal;
	}

	public int getAmountApprove() {
		return amountApprove;
	}

	public void setAmountApprove(int amountApprove) {
		this.amountApprove = amountApprove;
	}

	public int getAmountReject() {
		return amountReject;
	}

	public void setAmountReject(int amountReject) {
		this.amountReject = amountReject;
	}

	public int getAmountAboutToExpire() {
		return amountAboutToExpire;
	}

	public void setAmountAboutToExpire(int amountAboutToExpire) {
		this.amountAboutToExpire = amountAboutToExpire;
	}

	public int getAmountOutOfDate() {
		return amountOutOfDate;
	}

	public void setAmountOutOfDate(int amountOutOfDate) {
		this.amountOutOfDate = amountOutOfDate;
	}

}
