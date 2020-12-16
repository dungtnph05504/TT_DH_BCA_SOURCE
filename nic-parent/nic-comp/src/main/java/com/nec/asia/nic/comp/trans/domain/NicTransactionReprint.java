package com.nec.asia.nic.comp.trans.domain;

import java.util.Date;

/**
 * NicTransactionReprint
 */
public class NicTransactionReprint implements java.io.Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
    private NicTransactionReprintId id;
	private Integer reprintCount;
	private Boolean cancelPptFlag;	
	private String createBy;
	private String createWkstnId;
	private Date createDatetime;

	public NicTransactionReprintId getId() {
		return id;
	}
	public void setId(NicTransactionReprintId id) {
		this.id = id;
	}
	public Integer getReprintCount() {
		return reprintCount;
	}
	public void setReprintCount(Integer reprintCount) {
		this.reprintCount = reprintCount;
	}
	public Boolean getCancelPptFlag() {
		return cancelPptFlag;
	}
	public void setCancelPptFlag(Boolean cancelPptFlag) {
		this.cancelPptFlag = cancelPptFlag;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getCreateWkstnId() {
		return createWkstnId;
	}
	public void setCreateWkstnId(String createWkstnId) {
		this.createWkstnId = createWkstnId;
	}
	public Date getCreateDatetime() {
		return createDatetime;
	}
	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}
}
