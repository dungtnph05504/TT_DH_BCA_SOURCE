package com.nec.asia.nic.comp.perso.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType (XmlAccessType.FIELD)
public class Unit implements Serializable {
		
	@XmlAttribute(name = "Name")
	protected String name;
	@XmlAttribute(name = "Type")
	protected String type;
	@XmlAttribute(name = "Priority")
	protected String priority;
	
	@XmlElement(name = "Comment")
    protected String comment;
	@XmlElement(name = "Product")
    protected String product;
	@XmlElement(name = "CustomerUnitData")
    protected ValueDTO customerUnitData;
	@XmlElement(name = "UnitMatching")
    protected ValueDTO unitMatching;

	@XmlElement(name = "Unit")
	protected UnitItem unitItem;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public ValueDTO getCustomerUnitData() {
		return customerUnitData;
	}
	public void setCustomerUnitData(ValueDTO customerUnitData) {
		this.customerUnitData = customerUnitData;
	}
	public ValueDTO getUnitMatching() {
		return unitMatching;
	}
	public void setUnitMatching(ValueDTO unitMatching) {
		this.unitMatching = unitMatching;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public UnitItem getUnitItem() {
		return unitItem;
	}
	public void setUnitItem(UnitItem unitItem) {
		this.unitItem = unitItem;
	}
	
	
	
}
