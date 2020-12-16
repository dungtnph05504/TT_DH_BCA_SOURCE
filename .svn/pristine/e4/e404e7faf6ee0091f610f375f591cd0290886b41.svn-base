package com.nec.asia.nic.comp.perso.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="Unit")
public class UnitItem implements Serializable {
	
	@XmlAttribute(name = "Name")
	protected String name;
	@XmlAttribute(name = "Type")
	protected String type;
	@XmlAttribute(name = "Priority")
	protected String priority;
	
	@XmlElement(name="DataFields")
	protected DataFields dataFields = new DataFields();
	
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
	

	public DataFields getDataFields() {
		return dataFields;
	}
	public void setDataFields(DataFields dataFields) {
		this.dataFields = dataFields;
	}


	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlRootElement(name="DataFields")
	public static class DataFields {
		
		public DataFields() {
			super();
		}
		
		@XmlElement(name = "DataField")
		protected List<DataField> dataFieldList = new ArrayList<DataField> ();

		public List<DataField> getDataFieldList() {
			return dataFieldList;
		}

		public void setDataFieldList(List<DataField> dataFieldList) {
			this.dataFieldList = dataFieldList;
		}
		
	}
	
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlRootElement(name="DataField")
	public static class DataField {
		
		@XmlAttribute(name = "Name")
		protected String itemName;
		
		@XmlElement(name="Value")
		protected ValueDTO value;
		
	
		
		public String getItemName() {
			return itemName;
		}

		public void setItemName(String itemName) {
			this.itemName = itemName;
		}

		public ValueDTO getValue() {
			return value;
		}

		public void setValue(ValueDTO value) {
			this.value = value;
		}

		
		
	}
	
}
