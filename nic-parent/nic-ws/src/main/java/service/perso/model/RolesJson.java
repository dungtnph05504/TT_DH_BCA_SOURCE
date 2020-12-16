package service.perso.model;

// Generated May 16, 2013 6:44:55 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@XmlRootElement(name="RolesJson")
@XmlType ( name = "RolesJson",  propOrder = { "roleId", "roleDesc", "systemId", "createBy", "createWkstnId",
		"createDate", "updateBy", "updateWkstnId", "updateDate", "deleteBy",
		"deleteWkstnId", "deleteDate", "deleteFlag"} )
public class RolesJson implements java.io.Serializable {

	private String roleId;
	private String roleDesc;
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
	@JsonIgnore
	private Boolean deleteFlag;

	public RolesJson() {
	}

	public RolesJson(String roleId) {
		this.roleId = roleId;
	}

	public RolesJson(String roleId, String roleDesc, String systemId,
			String createBy, String createWkstnId, Date createDate,
			String updateBy, String updateWkstnId, Date updateDate,
			String deleteBy, String deleteWkstnId, Date deleteDate,
			Boolean deleteFlag) {
		this.roleId = roleId;
		this.roleDesc = roleDesc;
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

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleDesc() {
		return this.roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
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
}
