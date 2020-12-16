package com.nec.asia.nic.comp.trans.service.status;

/**
 * Enum class with defined set of document status.
 *
 * @author setia_budiyono
 * @version 1.0
 * @since 03 February 2016
 */


public enum DocumentStatus {
	
	ACTIVE("OK", "ACTIVE"), //Active Document
	CANCELLED("CA", "CANCELLED"), //Cancel Document
	RECEIVED("RD", "RECEIVED"), // Received Document
	SENT("ST", "SENT"); // sent document
	
	/** The id. */
	private String id;
	
	/** The value. */
	private String description;
	
	/**
	 * Instantiates a new document status.
	 *
	 * @param id the id
	 * @param value the value
	 * @param size the size
	 */
	DocumentStatus(String id, String description){
		this.id = id;
		this.description = description;
	}
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId(){
		return id;
	}
	
	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue(){
		return description;
	}
	
}
