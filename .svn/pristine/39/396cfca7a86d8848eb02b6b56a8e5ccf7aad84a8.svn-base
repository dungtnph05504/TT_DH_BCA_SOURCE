/**
 * 
 */
package com.nec.asia.nic.common.dto;

import java.io.Serializable;


/**
 * @author aparna_sharma
 * 
 */
public class RicRegistrationDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	@NotEmpty(message = "Transaction Type Is Required.")
	private String transactionType;
//	@NotEmpty(message = "Transaction Sub-Type Is Required.")
	private String transactionSubType;
//	@Pattern(regexp = "^$|^.{0,13}$|^[a-zA-Z][0-9]{12}[a-zA-Z0-9]$", message = "NIN Must Start With Alphabet And End With Alphanumeric And In-Between Must Be Numbers.")
	private String nin;
	private String queueNumber;
	private RicTransactionLogDTO transactionLog;
	private boolean counterRegNewFlag;
	private boolean validateNinChecksumFlag;
	private String counterId;
	private boolean queueFlg;

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getTransactionSubType() {
		return transactionSubType;
	}

	public void setTransactionSubType(String transactionSubType) {
		this.transactionSubType = transactionSubType;
	}

	public String getNin() {
		return nin;
	}

	public void setNin(String nin) {
		this.nin = nin;
	}

	public String getQueueNumber() {
		return queueNumber;
	}

	public void setQueueNumber(String queueNumber) {
		this.queueNumber = queueNumber;
	}

	public RicTransactionLogDTO getTransactionLog() {
		return transactionLog;
	}

	public void setTransactionLog(RicTransactionLogDTO transactionLog) {
		this.transactionLog = transactionLog;
	}

	public boolean isCounterRegNewFlag() {
		return counterRegNewFlag;
	}

	public void setCounterRegNewFlag(boolean counterRegNewFlag) {
		this.counterRegNewFlag = counterRegNewFlag;
	}

	public String getCounterId() {
		return counterId;
	}

	public void setCounterId(String counterId) {
		this.counterId = counterId;
	}

	public boolean isQueueFlg() {
		return queueFlg;
	}

	public void setQueueFlg(boolean queueFlg) {
		this.queueFlg = queueFlg;
	}

	public boolean isValidateNinChecksumFlag() {
		return validateNinChecksumFlag;
	}

	public void setValidateNinChecksumFlag(boolean validateNinChecksumFlag) {
		this.validateNinChecksumFlag = validateNinChecksumFlag;
	}

}
