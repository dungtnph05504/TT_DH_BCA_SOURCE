package com.nec.asia.nic.comp.trans.domain;
public class NicTransactionReprintId implements java.io.Serializable {

    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private String transactionId;

    private String refArn;

    public NicTransactionReprintId() {
    }

    public NicTransactionReprintId(String transactionId, String refArn) {
        this.transactionId = transactionId;
        this.refArn = refArn;
    }

    public String getTransactionId() {
        return this.transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getRefArn() {
        return this.refArn;
    }

    public void setRefArn(String refArn) {
        this.refArn = refArn;
    }

    public boolean equals(Object other) {
        if ((this == other))
            return true;
        if ((other == null))
            return false;
        if (!(other instanceof NicTransactionReprintId))
            return false;
        NicTransactionReprintId castOther = (NicTransactionReprintId) other;

		return ((this.getTransactionId() == castOther.getTransactionId())
				|| (this.getTransactionId() != null && castOther.getTransactionId() != null
						&& this.getTransactionId().equals(castOther.getTransactionId())))
				&& ((this.getRefArn() == castOther.getRefArn())
						|| (this.getRefArn() != null && castOther.getRefArn() != null
								&& this.getRefArn().equals(castOther.getRefArn())));
	}

    public int hashCode() {
        int result = 17;

        result = 37 * result + (getTransactionId() == null ? 0 : this.getTransactionId().hashCode());
        result = 37 * result + (getRefArn() == null ? 0 : this.getRefArn().hashCode());
        return result;
    }

}
