package com.nec.asia.nic.framework.admin.code.domain;

// Generated May 13, 2013 10:20:54 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/* 
 * Modification History:
 * 
 * 20 Aug 2014 (chris): add system CODE_ID constants List. 
 * 27 Apr 2016 (chris): add FCM_REQUIRED_SUB_ITEM
 */

public class Codes implements java.io.Serializable {

	/* List of predefine system CODE_ID */
	public static final String SITE_CODE = "SITE_CODE";
	public static final String REPORT_SITE_CODE = "REPORT_SITE_CODE";
	public static final String TRANSACTION_TYPE = "TRANSACTION_TYPE";
	public static final String TRANSACTION_SUBTYPE = "TRANSACTION_SUBTYPE";
	public static final String TRANSACTION_STAGE = "TRANSACTION_STAGE";
	public static final String TRANSACTION_STATUS = "TRANSACTION_STATUS";
	public static final String SEARCH_TYPE = "SEARCH_TYPE";
	public static final String CARD_STATUS = "CARD_STATUS";
	public static final String CARD_STATUS_CHANGE_REASON = "CARD_STATUS_CHANGE_REASON";
	public static final String JOB_STATE = "JOB_STATE";
	public static final String JOB_TYPE = "JOB_TYPE";
	public static final String RECORD_STATUS = "RECORD_STATUS";
	public static final String TERMINATION_TYPE = "TERMINATION_TYPE";
	public static final String DATA_SYNC_STATUS = "DATA_SYNC_STATUS";
	public static final String PROOF_DOCUMENTS = "PROOF_DOCUMENTS";
	public static final String DOC_TYPE = "DOC_TYPE";
	public static final String PASSPORT_TYPE = "PASSPORT_TYPE";
	public static final String PRIORITY = "PRIORITY";
	public static final String SEARCH_HIT_TEXT_ITEM_TYPE = "SEARCH_HIT_TEXT_ITEM_TYPE";
	public static final String SRCH_HIT_RESULT__ITEM = "SRCH_HIT_RESULT__ITEM";
	public static final String SRCH_HIT_RESULT__ITEM_SUB_ITEM = "SRCH_HIT_RESULT__ITEM_SUB_ITEM";
	public static final String FCM_REQUIRED_SUB_ITEM = "FCM_REQUIRED_SUB_ITEM";
	
	public static final String NATIONALITY = "NATIONALITY";
	public static final String TOWN_VILLAGE = "TOWN_VILLAGE";
	public static final String DISTRICT = "DISTRICT";
	public static final String PREFERRED_MAILING_ADDRESS = "PREFERRED_MAILING_ADDRESS";
	public static final String COUNTRY = "COUNTRY";
	public static final String PLACEOFBIRTH = "CODE_BirthPlace";
	
	public static final String GENDER = "GENDER";
	public static final String MARITAL_STATUS = "MARITAL_STATUS";
	public static final String OPTION_SURNAME = "OPTION_SURNAME";
	
	public static final String INVESTIGATION_STATUS = "INVESTIGATION_STATUS";
	public static final String REPORT_CATEGORY = "REPORT_CATEGORY";
	
	private String codeId;
	private String codeDesc;
	private Boolean userAccessibleFlag;
	private Boolean adminAccessibleFlag;
	private String systemId;
	private String createBy;
	private String createWkstnId;
	private Date createDate;
	private String updateBy;
	private String updateWkstnId;
	private Date updateDate;
	private String deleteBy;
	private String deleteWkstnId;
	private Date deleteDate;
	private Boolean deleteFlag;
	private Set<CodeValues> codeValues = new HashSet<CodeValues>(0);
	
	private int stt;
	
	public Codes() {
	}

	public Codes(String codeId) {
		this.codeId = codeId;
	}

	public Codes(String codeId, String codeDesc, Boolean userAccessibleFlag,
			Boolean adminAccessibleFlag, String systemId, String createBy,
			String createWkstnId, Date createDate, String updateBy,
			String updateWkstnId, Date updateDate, String deleteBy,
			String deleteWkstnId, Date deleteDate, Boolean deleteFlag,
			Set<CodeValues> codeValues) {
		this.codeId = codeId;
		this.codeDesc = codeDesc;
		this.userAccessibleFlag = userAccessibleFlag;
		this.adminAccessibleFlag = adminAccessibleFlag;
		this.systemId = systemId;
		this.createBy = createBy;
		this.createWkstnId = createWkstnId;
		this.createDate = createDate;
		this.updateBy = updateBy;
		this.updateWkstnId = updateWkstnId;
		this.updateDate = updateDate;
		this.deleteBy = deleteBy;
		this.deleteWkstnId = deleteWkstnId;
		this.deleteDate = deleteDate;
		this.deleteFlag = deleteFlag;
		this.codeValues = codeValues;
	}
	
	

	public int getStt() {
		return stt;
	}

	public void setStt(int stt) {
		this.stt = stt;
	}

	public String getCodeId() {
		return this.codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	public String getCodeDesc() {
		return this.codeDesc;
	}

	public void setCodeDesc(String codeDesc) {
		this.codeDesc = codeDesc;
	}

	public Boolean getUserAccessibleFlag() {
		return this.userAccessibleFlag;
	}

	public void setUserAccessibleFlag(Boolean userAccessibleFlag) {
		this.userAccessibleFlag = userAccessibleFlag;
	}

	public Boolean getAdminAccessibleFlag() {
		return this.adminAccessibleFlag;
	}

	public void setAdminAccessibleFlag(Boolean adminAccessibleFlag) {
		this.adminAccessibleFlag = adminAccessibleFlag;
	}

	public String getSystemId() {
		return this.systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getCreateWkstnId() {
		return this.createWkstnId;
	}

	public void setCreateWkstnId(String createWkstnId) {
		this.createWkstnId = createWkstnId;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateBy() {
		return this.updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getUpdateWkstnId() {
		return this.updateWkstnId;
	}

	public void setUpdateWkstnId(String updateWkstnId) {
		this.updateWkstnId = updateWkstnId;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getDeleteBy() {
		return this.deleteBy;
	}

	public void setDeleteBy(String deleteBy) {
		this.deleteBy = deleteBy;
	}

	public String getDeleteWkstnId() {
		return this.deleteWkstnId;
	}

	public void setDeleteWkstnId(String deleteWkstnId) {
		this.deleteWkstnId = deleteWkstnId;
	}

	public Date getDeleteDate() {
		return this.deleteDate;
	}

	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}

	public Boolean getDeleteFlag() {
		return this.deleteFlag;
	}

	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Set<CodeValues> getCodeValues() {
		return this.codeValues;
	}

	public void setCodeValues(Set<CodeValues> codeValues) {
		this.codeValues = codeValues;
	}

}
