package com.nec.asia.nic.dx.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.nec.asia.nic.dx.utils.DateAdapter;

/**
 * DTO class for Fp Verification data
 * 
 * @author chris_wong
 *
 * Sample XML
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
 */

/* 
 * Modification History:
 * 
 * 29 Nov 2013 (chris): init class
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class FpVerificationDTO implements Serializable {
	public static final String RIGHT_THUMB 	= "1";
	public static final String RIGHT_INDEX 	= "2";
	public static final String RIGHT_MIDDLE = "3";
	public static final String RIGHT_RING 	= "4";
	public static final String RIGHT_LITTLE = "5";
	public static final String LEFT_THUMB 	= "6";
	public static final String LEFT_INDEX 	= "7";
	public static final String LEFT_MIDDLE 	= "8";
	public static final String LEFT_RING 	= "9";
	public static final String LEFT_LITTLE 	= "10";
	
	public static final String MNU_FORMAT_SC37_CBEFF 	= "SC37_CBEFF";
	public static final String MNU_FORMAT_PC2 			= "PC2";
	public static final String MNU_FORMAT_OTHER 		= "Other";
//	public static final String MNU_FORMAT_PC1 			= "PC1";
//	public static final String MNU_FORMAT_FMP2 			= "FMP2";
//	public static final String MNU_FORMAT_FMP5 			= "FMP5";
//	public static final String MNU_FORMAT_AP1 			= "AP1";
//	public static final String MNU_FORMAT_BT5 			= "BT5";
//	public static final String MNU_FORMAT_ISO 			= "ISO";
	
	@XmlElement(name = "TransactionStage")
	protected String transactionStage;
	@XmlElement(name = "FpSource")
	protected String fpSource;
	@XmlElement(name = "FingerprintPosition")
	protected String fingerprintPosition;
	@XmlElement(name = "FingerprintData")
	protected byte[] fingerprintData;
	@XmlElement(name = "MinutiaData")
	protected byte[] minutiaData;
	@XmlElement(name = "MinutiaFormat")
	protected String minutiaFormat;
	@XmlElement(name = "FpVerifyFlag")
	protected String fpVerifyFlag;
	@XmlElement(name = "FpQuality")
	protected String fpQuality;	
	@XmlElement(name = "MatchScore")
	protected String matchScore;
	@XmlElement(name = "CreateBy")
	protected String createBy;
	@XmlElement(name = "CreateWkstnId")
	protected String createWkstnId;
	@XmlElement(name = "CreateDate", type = String.class)
	@XmlJavaTypeAdapter(DateAdapter.class)
	@XmlSchemaType(name = "dateTime")
	protected Date createDate;
	
	public FpVerificationDTO() {
	}
	
	public String getTransactionStage() {
		return transactionStage;
	}
	public void setTransactionStage(String transactionStage) {
		this.transactionStage = transactionStage;
	}
	public String getFpSource() {
		return fpSource;
	}
	public void setFpSource(String fpSource) {
		this.fpSource = fpSource;
	}
	public String getFingerprintPosition() {
		return fingerprintPosition;
	}
	public void setFingerprintPosition(String fingerprintPosition) {
		this.fingerprintPosition = fingerprintPosition;
	}
	public byte[] getFingerprintData() {
		return fingerprintData;
	}
	public void setFingerprintData(byte[] fingerprintData) {
		this.fingerprintData = fingerprintData;
	}
	public byte[] getMinutiaData() {
		return minutiaData;
	}
	public void setMinutiaData(byte[] minutiaData) {
		this.minutiaData = minutiaData;
	}
	public String getMinutiaFormat() {
		return minutiaFormat;
	}
	public void setMinutiaFormat(String minutiaFormat) {
		this.minutiaFormat = minutiaFormat;
	}
	public String getFpVerifyFlag() {
		return fpVerifyFlag;
	}
	public void setFpVerifyFlag(String fpVerifyFlag) {
		this.fpVerifyFlag = fpVerifyFlag;
	}
	public String getFpQuality() {
		return fpQuality;
	}
	public void setFpQuality(String fpQuality) {
		this.fpQuality = fpQuality;
	}
	public String getMatchScore() {
		return matchScore;
	}
	public void setMatchScore(String matchScore) {
		this.matchScore = matchScore;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getCreateWkstnId() {
		return createWkstnId;
	}
	public void setCreateWkstnId(String createWkstnId) {
		this.createWkstnId = createWkstnId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}

