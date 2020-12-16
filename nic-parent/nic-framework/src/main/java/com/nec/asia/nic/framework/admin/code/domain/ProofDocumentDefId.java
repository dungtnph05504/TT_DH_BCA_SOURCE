package com.nec.asia.nic.framework.admin.code.domain;

// Generated May 13, 2013 10:20:54 PM by Hibernate Tools 3.4.0.CR1

@Deprecated 
/**
 * @deprecated this class is not supported by this version 
 */
public class ProofDocumentDefId implements java.io.Serializable {

	private static final long serialVersionUID = 5670696405458840293L;

	private String documentId;
	private String transactionType;
	private String transactionSubtype;

	public ProofDocumentDefId() {
	}

	public ProofDocumentDefId(String documentId, String transactionType,
			String transactionSubtype) {
		this.documentId = documentId;
		this.transactionType = transactionType;
		this.transactionSubtype = transactionSubtype;
	}

	public String getDocumentId() {
		return this.documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
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

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ProofDocumentDefId))
			return false;
		ProofDocumentDefId castOther = (ProofDocumentDefId) other;

		return ((this.getDocumentId() == castOther.getDocumentId()) || (this
				.getDocumentId() != null && castOther.getDocumentId() != null && this
				.getDocumentId().equals(castOther.getDocumentId())))
				&& ((this.getTransactionType() == castOther
						.getTransactionType()) || (this.getTransactionType() != null
						&& castOther.getTransactionType() != null && this
						.getTransactionType().equals(
								castOther.getTransactionType())))
				&& ((this.getTransactionSubtype() == castOther
						.getTransactionSubtype()) || (this
						.getTransactionSubtype() != null
						&& castOther.getTransactionSubtype() != null && this
						.getTransactionSubtype().equals(
								castOther.getTransactionSubtype())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getDocumentId() == null ? 0 : this.getDocumentId()
						.hashCode());
		result = 37
				* result
				+ (getTransactionType() == null ? 0 : this.getTransactionType()
						.hashCode());
		result = 37
				* result
				+ (getTransactionSubtype() == null ? 0 : this
						.getTransactionSubtype().hashCode());
		return result;
	}

}
