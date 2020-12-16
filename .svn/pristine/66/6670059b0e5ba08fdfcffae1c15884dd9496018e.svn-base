package com.nec.asia.nic.dx.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
/**
 * DTO class for Issuance log data
 * 
 * Sample XML
<DataExchange source = "RIC" target = "NIC" version = "1.0">
    <IssuanceDetails>
        <TransactionId>RICHQ-2013-000001</TransactionId>
        <Nin>S1702903011891</Nin>
        <Ccn>926782376</Ccn>
        <CardStatus>ACTIVE</CardStatus><!--ACTIVE, SUSPENDED, TERMINATED-->
        <IssuanceBy>RICHQ-01</IssuanceBy>
        <IssuanceWkstnId>RICHQ-ISS-01</IssuanceWkstnId>
        <IssuanceDate></IssuanceDate><!--Issuance Date In DD-MM-YYYY format -->
        <IssuanceDecision></IssuanceDecision><!--I: Issued, R: Rejected -->
		<FpVerifications>
		    <FpVerification>
		        <TransactionStage>Registration</TransactionStage>
		        <FpSource></FpSource><!--IDCARD, RICDB, NICDB -->
		        <FingerprintPosition></FingerprintPosition><!--1:Right Thumb,2: Right Index, 3: Right Middle, 4: Right Ring, 5: Right Little, 6:Left Thumb, 7:Left Index, 8: Left Middle, 9: Left Ring, 10: Left Little-->
		        <FingerprintData></FingerprintData><!--base64binary-->
		        <MinutiaData></MinutiaData><!--base64binary-->
		        <MinutiaFormat>SC37_CBEFF</MinutiaFormat><!--PC2, SC37_CBEFF (default)-->
		        <FpVerifyFlag></FpVerifyFlag><!--Y: Verified, N: No Verified -->
		        <FpQuality></FpQuality><!--0 to 100 -->
		        <MatchScore></MatchScore><!--0 to 9999 -->
		        <CreateBy></CreateBy>
		        <CreateWkstnId></CreateWkstnId>
		        <CreateDate></CreateDate>
		    </FpVerification>
		</FpVerifications>
    </IssuanceDetails>
</DataExchange>
 *
 */

/* 
 * Modification History:
 * 
 * 29 Nov 2013 (chris): init class
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="IssuanceDetails")
public class IssuanceDetailsDTO implements Serializable{
	@XmlElement(name = "TransactionId")
    protected String transactionId;
	@XmlElement(name = "Nin")
    protected String nin;
	@XmlElement(name = "Ccn")
    protected String ccn;
	@XmlElement(name = "CardStatus")
    protected String cardStatus;
	@XmlElement(name = "IssuanceBy")
    protected String issuanceBy;
	@XmlElement(name = "IssuanceWkstnId")
    protected String issuanceWkstnId;
	@XmlElement(name = "IssuanceDate")
    protected String issuanceDate;
	@XmlElement(name = "IssuanceDecision")
    protected String issuanceDecision;
    @XmlElement(name = "FpVerifications")
	protected FpVerificationsDTO FpVerifications;
    
    public IssuanceDetailsDTO() {
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getNin() {
		return nin;
	}

	public void setNin(String nin) {
		this.nin = nin;
	}

	public String getCcn() {
		return ccn;
	}

	public void setCcn(String ccn) {
		this.ccn = ccn;
	}

	public String getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}

	public String getIssuanceBy() {
		return issuanceBy;
	}

	public void setIssuanceBy(String issuanceBy) {
		this.issuanceBy = issuanceBy;
	}

	public String getIssuanceWkstnId() {
		return issuanceWkstnId;
	}

	public void setIssuanceWkstnId(String issuanceWkstnId) {
		this.issuanceWkstnId = issuanceWkstnId;
	}

	public String getIssuanceDate() {
		return issuanceDate;
	}

	public void setIssuanceDate(String issuanceDate) {
		this.issuanceDate = issuanceDate;
	}

	public String getIssuanceDecision() {
		return issuanceDecision;
	}

	/**
	 * The issuance decision, I: Issued, R: Rejected
	 * @param issuanceDecision
	 */
	public void setIssuanceDecision(String issuanceDecision) {
		this.issuanceDecision = issuanceDecision;
	}

	public FpVerificationsDTO getFpVerifications() {
		return FpVerifications;
	}

	public void setFpVerifications(FpVerificationsDTO fpVerifications) {
		FpVerifications = fpVerifications;
	}
}
