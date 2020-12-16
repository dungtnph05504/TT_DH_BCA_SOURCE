package com.nec.asia.nic.comp.trans.utils;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

public class ReportDataRecord {
   	
	   /** The name. */
	   @XStreamAsAttribute
	private String name;

   	/** The value. */
	   @XStreamAsAttribute
	private String value;
   	
	/** The link. */
	@XStreamAsAttribute
	protected String link;
   	
   	/** The color. */
	   @XStreamAsAttribute
	private String color;
	

	/**
	 * Instantiates a new report data record.
	 */
	public ReportDataRecord() {
   		
   	}
	
   	/**
	    * Instantiates a new report data record.
	    *
	    * @param name the name
	    * @param value the value
	    * @param color the color
	    * @param link the link
	    */
	   public ReportDataRecord(String name, String value, String color,String link) {
   		this.name=name;
   		this.value=value;
   		this.color=color;
   		this.link=link;
   	}
   	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	/**
	 * Gets the color.
	 *
	 * @return the color
	 */
	public String getColor() {
		return color;
	}
	
	/**
	 * Sets the color.
	 *
	 * @param color the new color
	 */
	public void setColor(String color) {
		this.color = color;
	}
   	

	/**
	 * Gets the link.
	 *
	 * @return the link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * Sets the link.
	 *
	 * @param link the new link
	 */
	public void setLink(String link) {
		this.link = link;
	}

}
