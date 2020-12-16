package com.nec.asia.nic.comp.job.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="HandoverSync")
@XmlType ( name = "HandoverSync",  propOrder = { "packID", "listTran", "size" } )

public class HandoverSync {
	private String packID;
	private List<String> listTran;
	private Integer size;
	
	public String getPackID() {
		return packID;
	}
	public void setPackID(String packID) {
		this.packID = packID;
	}
	public List<String> getListTran() {
		return listTran;
	}
	public void setListTran(List<String> listTran) {
		this.listTran = listTran;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
}
