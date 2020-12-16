package com.nec.asia.nic.util;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlRootElement(name="XuLyHoSoInfo")
@XmlType ( name = "XuLyHoSoInfo",  propOrder = { "type","name", "data" } )
public class XuLyHoSoInfo {
	private String type;
	private String name;
	private int[] data;
	
	public XuLyHoSoInfo() {
		super();
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int[] getData() {
		return data;
	}
	public void setData(int[] data) {
		this.data = data;
	}
}
