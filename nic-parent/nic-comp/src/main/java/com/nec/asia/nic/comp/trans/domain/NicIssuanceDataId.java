package com.nec.asia.nic.comp.trans.domain;

// Generated May 16, 2013 7:03:37 PM by Hibernate Tools 3.4.0.CR1

/**
 * NicIssuanceDataId generated by hbm2java
 */
@Deprecated
public class NicIssuanceDataId implements java.io.Serializable {

	private String transactionId;
	private String persoRefId;

	public NicIssuanceDataId() {
	}

	public NicIssuanceDataId(String transactionId, String persoRefId) {
		this.transactionId = transactionId;
		this.persoRefId = persoRefId;
	}

	public String getTransactionId() {
		return this.transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getPersoRefId() {
		return this.persoRefId;
	}

	public void setPersoRefId(String persoRefId) {
		this.persoRefId = persoRefId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof NicIssuanceDataId))
			return false;
		NicIssuanceDataId castOther = (NicIssuanceDataId) other;

		return ((this.getTransactionId() == castOther.getTransactionId()) || (this
				.getTransactionId() != null
				&& castOther.getTransactionId() != null && this
				.getTransactionId().equals(castOther.getTransactionId())))
				&& ((this.getPersoRefId() == castOther.getPersoRefId()) || (this
						.getPersoRefId() != null
						&& castOther.getPersoRefId() != null && this
						.getPersoRefId().equals(castOther.getPersoRefId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getTransactionId() == null ? 0 : this.getTransactionId()
						.hashCode());
		result = 37
				* result
				+ (getPersoRefId() == null ? 0 : this.getPersoRefId()
						.hashCode());
		return result;
	}

}
