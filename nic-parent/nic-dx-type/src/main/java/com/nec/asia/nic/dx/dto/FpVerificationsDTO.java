package com.nec.asia.nic.dx.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * DTO class for Fp Verifications list data
 * 
 * @author chris_wong
 * Sample XML:
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
 */
/* 
 * Modification History:
 * 
 * 29 Nov 2013 (chris): init class
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class FpVerificationsDTO implements Serializable{
	
	@XmlElement(name = "FpVerification")
	protected List<FpVerificationDTO> fpVerificationList;

	public FpVerificationsDTO() {
	}

	public List<FpVerificationDTO> getFpVerificationList() {
		return fpVerificationList;
	}

	public void setFpVerificationList(List<FpVerificationDTO> fpVerificationList) {
		this.fpVerificationList = fpVerificationList;
	}
}
