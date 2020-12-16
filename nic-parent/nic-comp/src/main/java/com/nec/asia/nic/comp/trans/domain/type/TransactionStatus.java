package com.nec.asia.nic.comp.trans.domain.type;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.enums.Enum;

/**
 * Transaction Status type enumerator.
 * 
 * @author chris
 * @version 1.0
 */
public class TransactionStatus extends Enum {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The states idx. */
	private static Map<String, TransactionStatus> statesIdx = new HashMap<String, TransactionStatus>();

	/** The key. */
	private String key;

	/** The order. */
	private int order;

	/*
	 * RV Ready for Verification (Staging)
	 * VG Verifying (CPD)
	 * WA Waiting for AFIS check (AFIS)
	 * FG Pending Investigation
	 * SP Suspended (Case Suspended)
	 * VE Verified (Case Approved)
	 * KO Rejected (Case Rejected)
	 * RP Ready for Personalization (WF Completed)
	 * AS Associated (Imported)
	 * WC Waiting for chip personalization (PendingPerso)
	 * PO Personalized (Printed)
	 * QC Controlled (QcCompleted)
	 * FA Faulted (Printing Error) / Rejected By Applicant
	 * ST Sent (Dispatched)
	 * RD Received
	 * RR Reroute
	 * CC Cancelled
	 * OK Active (Issued)
	 */

	public static final TransactionStatus Appointment = new TransactionStatus("Appointment", "PC", 1);
	public static final TransactionStatus Captured = new TransactionStatus("Captured", "CA", 2);
	public static final TransactionStatus Transmission = new TransactionStatus("Transmission", "SI", 3);
	public static final TransactionStatus Staging = new TransactionStatus("Ready for Verification (Staging)", "RV", 4);
	public static final TransactionStatus Verifying = new TransactionStatus("Verifying (CPD)", "VG", 5);
	public static final TransactionStatus Afis = new TransactionStatus("Waiting for AFIS check (AFIS)", "WA", 6);
	public static final TransactionStatus PendingInvestigation = new TransactionStatus("Pending Investigation", "FG", 7);
	public static final TransactionStatus Suspended = new TransactionStatus("Suspended (Case Suspended)", "SP", 8);
	public static final TransactionStatus Verified = new TransactionStatus("Verified (Case Approved)", "VE", 9);
	public static final TransactionStatus Rejected = new TransactionStatus("Rejected (Case Rejected)", "KO", 10);
	public static final TransactionStatus Personalization = new TransactionStatus("Ready for Personalization (WF Completed)", "RP", 11);
	public static final TransactionStatus Imported = new TransactionStatus("Associated (Imported)", "AS", 112);
	public static final TransactionStatus PendingPerso = new TransactionStatus("Waiting for chip personalization (PendingPerso)", "WC", 13);
	public static final TransactionStatus Printed = new TransactionStatus("Personalized (Printed)", "PO", 14);
	public static final TransactionStatus QcCompleted = new TransactionStatus("Controlled (QcCompleted)", "QC", 15);
	public static final TransactionStatus Faulted = new TransactionStatus("Faulted (Printing Error) / Rejected By Applicant", "FA", 16);
	public static final TransactionStatus Dispatched = new TransactionStatus("Sent (Dispatched)", "ST", 17);
	public static final TransactionStatus Received = new TransactionStatus("Received", "RD", 18);
	public static final TransactionStatus Reroute = new TransactionStatus("Reroute", "RR", 19);
	public static final TransactionStatus Cancelled = new TransactionStatus("Cancelled", "CC", 20);
	public static final TransactionStatus Active = new TransactionStatus("Active (Issued)", "OK", 99);
	public static final TransactionStatus Redivisions = new TransactionStatus("Redivisions", "RD", 21);

	/**
	 * Instantiates a new transaction status.
	 *
	 * @param value the value
	 * @param key the key
	 */
	private TransactionStatus(String value, String key, int order) {
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
	public static TransactionStatus getInstance(String key) {
		return (TransactionStatus) statesIdx.get(key);
	}

	/**
	 * Gets the all instances.
	 *
	 * @return the all instances
	 */
	public static Collection<TransactionStatus> getAllInstances() {
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
