package com.nec.asia.nic.comp.listHandover.domain;

import java.io.Serializable;

public class NicListHandoverId implements Serializable{

	private String packageId;
	private String typeList;
	public String getPackageId() {
		return packageId;
	}
	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}
	public String getTypeList() {
		return typeList;
	}
	public void setTypeList(String typeList) {
		this.typeList = typeList;
	}
	public NicListHandoverId(String packageId, String typeList) {
		this.packageId = packageId;
		this.typeList = typeList;
	}
	public NicListHandoverId() {
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((packageId == null) ? 0 : packageId.hashCode());
		result = prime * result
				+ ((typeList == null) ? 0 : typeList.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NicListHandoverId other = (NicListHandoverId) obj;
		if (packageId == null) {
			if (other.packageId != null)
				return false;
		} else if (!packageId.equals(other.packageId))
			return false;
		if (typeList == null) {
			if (other.typeList != null)
				return false;
		} else if (!typeList.equals(other.typeList))
			return false;
		return true;
	}
}
