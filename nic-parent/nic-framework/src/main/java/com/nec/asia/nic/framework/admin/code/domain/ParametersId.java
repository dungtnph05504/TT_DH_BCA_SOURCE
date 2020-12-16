package com.nec.asia.nic.framework.admin.code.domain;

// Generated May 13, 2013 10:20:54 PM by Hibernate Tools 3.4.0.CR1

public class ParametersId implements java.io.Serializable {

	private static final long serialVersionUID = 5887906072136519505L;

	private String paraScope;
	private String paraName;

	public ParametersId() {
	}

	public ParametersId(String paraScope, String paraName) {
		this.paraScope = paraScope;
		this.paraName = paraName;
	}

	public String getParaScope() {
		return this.paraScope;
	}

	public void setParaScope(String paraScope) {
		this.paraScope = paraScope;
	}

	public String getParaName() {
		return this.paraName;
	}

	public void setParaName(String paraName) {
		this.paraName = paraName;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ParametersId))
			return false;
		ParametersId castOther = (ParametersId) other;

		return ((this.getParaScope() == castOther.getParaScope()) || (this
				.getParaScope() != null && castOther.getParaScope() != null && this
				.getParaScope().equals(castOther.getParaScope())))
				&& ((this.getParaName() == castOther.getParaName()) || (this
						.getParaName() != null
						&& castOther.getParaName() != null && this
						.getParaName().equals(castOther.getParaName())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getParaScope() == null ? 0 : this.getParaScope().hashCode());
		result = 37 * result
				+ (getParaName() == null ? 0 : this.getParaName().hashCode());
		return result;
	}

	public String toString() {
		return "(paraScope=" + paraScope + ", paraName="+paraName+")";
	}
}
