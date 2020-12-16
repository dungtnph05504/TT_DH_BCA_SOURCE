package com.nec.asia.nic.comp.trans.domain.type;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.enums.Enum;


/**
 * Document Status type enumerator.
 * 
 * @author chris
 * @version 1.0
 */
public class DocumentStatus extends Enum {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The states idx. */
	private static Map<String, DocumentStatus> statesIdx = new HashMap<String, DocumentStatus>();

	/** The key. */
	private String key;
	
	/** The order. */
	private int order;
	
	/*
AS	Associated (Imported)	2
WC	Waiting for chip personalization (PendingPerso)	3
PO	Personalized (Printed)	4
CQ	Controlled (QcCompleted)	5
ST	Sent	6
RD	Received	7
OK	Active	8
CC	Cancelled (Rejected)	9
FA	Faulted (Error)	10
	 */
	public static final DocumentStatus Imported = new DocumentStatus("Associated (Imported)", "AS", 2);
	public static final DocumentStatus PendingPerso = new DocumentStatus("Waiting for chip personalization (PendingPerso)", "WC", 3);
	public static final DocumentStatus Printed = new DocumentStatus("Personalized (Printed)", "PO", 4);
	public static final DocumentStatus QcCompleted = new DocumentStatus("Controlled (QcCompleted)", "QC", 5);
	public static final DocumentStatus Sent = new DocumentStatus("Sent", "ST", 6);
	public static final DocumentStatus Received = new DocumentStatus("Received", "RD", 7);
	public static final DocumentStatus Active = new DocumentStatus("Active", "OK", 8);
	public static final DocumentStatus Cancelled = new DocumentStatus("Cancelled (Rejected / Renewed)", "CC", 9);
	public static final DocumentStatus Faulted = new DocumentStatus("Faulted (Error)", "FA", 10);
	public static final DocumentStatus Rerouted = new DocumentStatus("Rerouted", "RR", 6);

	/**
	 * Instantiates a new transaction status.
	 *
	 * @param value the value
	 * @param key the key
	 */
	private DocumentStatus(String value, String key, int order) {
		super(value);
		this.key = key;
		this.order = order;
		statesIdx.put(this.key, this);
	}

	/**
	 * Gets the single instance of Pose.
	 *
	 * @param key the key
	 * @return single instance of Pose
	 */
	public static DocumentStatus getInstance(String key) {
		return (DocumentStatus) statesIdx.get(key);
	}

	/**
	 * Gets the all instances.
	 *
	 * @return the all instances
	 */
	public static Collection<DocumentStatus> getAllInstances() {
		return statesIdx.values();
	}

	/**
	 * Gets the key.
	 *
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
	
	public int getOrder() {
		return order;
	}

	/**
	 * Equals.
	 *
	 * @param key the key
	 * @return true, if successful
	 */
	public boolean equals(String key) {
		if (String.valueOf(this.key).equals(key)) {
			return true;
		} else {
			return false;
		}
	}
	
}
