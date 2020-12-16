package com.nec.asia.nic.framework.admin.code.domain;

// Generated May 13, 2013 10:20:54 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.Map;

import javax.persistence.Lob;

import com.nec.asia.nic.framework.admin.code.domain.type.ParameterType;

public class Parameters implements java.io.Serializable {

	private static final long serialVersionUID = 6579974820746228736L;
	//paraScope
	public static final String SCOPE_SYSTEM = "SYSTEM";
	
	//paraName
	public static final String FP_QUALITY_GOOD = "FP_QUALITY_GOOD";
	public static final String FP_QUALITY_ACCEPTABLE = "FP_QUALITY_ACCEPTABLE";
	public static final String FP_VERIFY_MATCH_SCORE = "FP_VERIFY_MATCH_SCORE";
	
	public static final String MAX_RECORDS_PER_PAGE = "MAX_RECORDS_PER_PAGE";
	public static final String WS_LDS_API = "WS_LDS_API";
	public static final String PARA_NAME_BELOW_14 = "BELOW_14";
	public static final String PARA_NAME_ABOVE_14 = "ABOVE_14";
	public static final String PARA_SCOPE_VALIDITY_PERIOD = "VALIDITY_PERIOD";
	public static final String PARA_NAME_RECEIVER_ERROR = "RECEIVER_ERROR";
	public static final String PARA_SCOPE_TRANSMISSION = "TRANSMISSION";

	private ParametersId id;
	private Object paraValue;
	private String paraShortValue;
	private String paraLobValue;
	private String paraDesc;
	private ParameterType paraType;
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
	private String status;
	private String message;
	private String displayTable;
	
	private String paraTypebln;
	private String paraTypeInt;
	private String paraTypeStr;
	private Map<String, String> sysCodeMap;
	
	private int stt;
	
	

	/*
	 * Modification History:
	 * 
	 * 9 Jan 2014 (chris lai) : update with toString method
	 */
	
	public int getStt() {
		return stt;
	}

	public void setStt(int stt) {
		this.stt = stt;
	}

	/**
	 * @param displayTable the displayTable to set
	 */
	public void setDisplayTable(String displayTable) {
		this.displayTable = displayTable;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @param paraValue the paraValue to set
	 */
	public void setParaValue(Object paraValue) {
		this.paraValue = paraValue;
	}

	public Parameters() {
	}

	public Parameters(ParametersId id) {
		this.id = id;
	}

	public Parameters(ParametersId id, String paraValue,
			String paraLobValue, String paraDesc, ParameterType paraType, Boolean userAccessibleFlag,
			Boolean adminAccessibleFlag, String systemId, String createBy,
			String createWkstnId, Date createDate, String updateBy,
			String updateWkstnId, Date updateDate, String deleteBy,
			String deleteWkstnId, Date deleteDate, Boolean deleteFlag) {
		this.id = id;
		this.paraValue = paraValue;
		this.paraLobValue = paraLobValue;
		this.paraDesc = paraDesc;
		this.paraType = paraType;
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
	}

	public ParametersId getId() {
		return this.id;
	}

	public void setId(ParametersId id) {
		this.id = id;
	}

	public Object getParaValue() {
		
		if (ParameterType.Integer.equals(this.getParaType())) {
			this.paraValue = Integer.parseInt(this.paraShortValue);
		} else if (ParameterType.Boolean.equals(this.getParaType())) {
			this.paraValue = Boolean.parseBoolean(this.paraShortValue);
		} else if (ParameterType.Percentage.equals(this.getParaType())) {
			this.paraValue = Double.parseDouble(this.paraShortValue) / 100;
		} else if (ParameterType.LongText.equals(this.getParaType())) {
			this.paraValue = this.paraLobValue;
		} else if (ParameterType.String.equals(this.getParaType())) {
			this.paraValue = this.paraShortValue;
		} else if (ParameterType.Code.equals(this.getParaType())) { //TODO
			this.paraValue = this.paraShortValue;
		} else if (ParameterType.Date.equals(this.getParaType())) { //TODO
			this.paraValue = this.paraShortValue;
		} else if (ParameterType.Datetime.equals(this.getParaType())) { //TODO
			this.paraValue = this.paraShortValue;
		} else if (ParameterType.NumberRange.equals(this.getParaType())) { //TODO
			this.paraValue = this.paraShortValue;
		} else if (ParameterType.Object.equals(this.getParaType())) { //TODO
			this.paraValue = this.paraLobValue;
		}
		return this.paraValue;
	}

	/*public void setParaValue(Object paraValue) {
		this.paraValue = paraValue;
	}*/
	
	public String getParaShortValue() {
		return this.paraShortValue;
	}

	public void setParaShortValue(String paraShortValue) {
		this.paraShortValue = paraShortValue;
	}

	@Lob
	public String getParaLobValue() {
		return this.paraLobValue;
	}

	public void setParaLobValue(String paraLobValue) {
		this.paraLobValue = paraLobValue;
	}

	public String getParaDesc() {
		return this.paraDesc;
	}

	public void setParaDesc(String paraDesc) {
		this.paraDesc = paraDesc;
	}

	public ParameterType getParaType() {
		return paraType;
	}

	public void setParaType(ParameterType paraType) {
		this.paraType = paraType;
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

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the paraTypebln
	 */
	public String getParaTypebln() {
		return paraTypebln;
	}

	/**
	 * @param paraTypebln the paraTypebln to set
	 */
	public void setParaTypebln(String paraTypebln) {
		this.paraTypebln = paraTypebln;
	}

	/**
	 * @return the paraTypeInt
	 */
	public String getParaTypeInt() {
		return paraTypeInt;
	}

	/**
	 * @param paraTypeInt the paraTypeInt to set
	 */
	public void setParaTypeInt(String paraTypeInt) {
		this.paraTypeInt = paraTypeInt;
	}

	/**
	 * @return the paraTypeStr
	 */
	public String getParaTypeStr() {
		return paraTypeStr;
	}

	/**
	 * @param paraTypeStr the paraTypeStr to set
	 */
	public void setParaTypeStr(String paraTypeStr) {
		this.paraTypeStr = paraTypeStr;
	}
	

	/**
	 * @return the sysCodeMap
	 */
	public Map<String, String> getSysCodeMap() {
		return sysCodeMap;
	}

	/**
	 * @param sysCodeMap the sysCodeMap to set
	 */
	public void setSysCodeMap(Map<String, String> sysCodeMap) {
		this.sysCodeMap = sysCodeMap;
	}

	/**
	 * @return the displayTable
	 */
	public String getDisplayTable() {
		return displayTable;
	}
	//chris lai  9 Jan 2014 (update with toString method)
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Parameters [ParametersId=" + id + ", paraValue="
				+ paraValue + ", paraLobValue=" + paraLobValue + 
				", paraDesc=" + paraDesc + ", paraType="
				+ paraType + ", systemId="+ systemId + ", createBy=" + createBy + 
				", createWkstnId=" + createWkstnId + ", createDate="
				+ createDate + ", updateBy=" + updateBy + 
				", updateWkstnId=" + updateWkstnId + ", updateDate="
				+ updateDate + ", deleteBy=" + deleteBy + 
				", deleteWkstnId=" + deleteWkstnId + ", deleteDate=" + deleteDate + "]";
	}
	
}
