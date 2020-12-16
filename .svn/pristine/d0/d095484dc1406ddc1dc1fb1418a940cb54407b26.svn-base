package com.nec.asia.nic.framework.admin.code.domain;

// Generated May 13, 2013 10:20:54 PM by Hibernate Tools 3.4.0.CR1


public class PaymentDefId implements java.io.Serializable {

	private String transactionType;
	private String transactionSubtype;
	private int noOfTimeLost;

	public PaymentDefId() {
	}

	public PaymentDefId(String transactionType, String transactionSubtype,
			int noCardLost) {
		this.transactionType = transactionType;
		this.transactionSubtype = transactionSubtype;
		this.noOfTimeLost = noCardLost;
	}

	public String getTransactionType() {
		return this.transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getTransactionSubtype() {
		return this.transactionSubtype;
	}

	public void setTransactionSubtype(String transactionSubtype) {
		this.transactionSubtype = transactionSubtype;
	}

	public int getNoOfTimeLost() {
		return this.noOfTimeLost;
	}

	public void setNoOfTimeLost(int noCardLost) {
		this.noOfTimeLost = noCardLost;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PaymentDefId))
			return false;
		PaymentDefId castOther = (PaymentDefId) other;

		return ((this.getTransactionType() == castOther.getTransactionType()) || (this
				.getTransactionType() != null
				&& castOther.getTransactionType() != null && this
				.getTransactionType().equals(castOther.getTransactionType())))
				&& ((this.getTransactionSubtype() == castOther
						.getTransactionSubtype()) || (this
						.getTransactionSubtype() != null
						&& castOther.getTransactionSubtype() != null && this
						.getTransactionSubtype().equals(
								castOther.getTransactionSubtype())))
				&& (this.getNoOfTimeLost() == castOther.getNoOfTimeLost());
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getTransactionType() == null ? 0 : this.getTransactionType()
						.hashCode());
		result = 37
				* result
				+ (getTransactionSubtype() == null ? 0 : this
						.getTransactionSubtype().hashCode());
		result = 37 * result + (int) this.getNoOfTimeLost();
		return result;
	}

}
