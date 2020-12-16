/**
 * 
 */
package com.nec.asia.nic.comp.job.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

import com.nec.asia.nic.framework.admin.code.domain.CodeValues;

/**
 * @author sailaja_chinapothula
 * @Company: NEC Asia Pacific Ltd
 * @Since: Jun 27, 2013
 */
/*
 * For Investigation UI Module 
 */
public class HitCandidateDTO implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	private String nicId;
	private String transactionId;
	private String hitFingers;
	private String matchingScore;
	private Map<Integer,Double> matchScore;
	
	private String remarksData;
	
	private long uploadJobId;
	private String userId;
	private String wkstnId;
	private String investigationType;
	private String 	nin;
	private Boolean ninMatchFlag;
	private String 	surname;
	private Boolean surnameMatchFlag;
	private String 	firstName;
	private Boolean firstNameMatchFlag;
	private String 	surnameAtBirth;
	private Boolean surnameAtBirthMatchFlag;
	private String 	sex;
	private Boolean sexMatchFlag;
	private Date 	birthDate;
	private String 	birthDateApprox;
	
	private String 	flatNoApartmentName;
	private Boolean flatNoApartmentNameMatchFlag;
	private String 	townVillage;
	private Boolean townVillageMatchFlag;
	private String 	streetName;
	private Boolean streetNameMatchFlag;
	private String 	locality; //TODO to continue 
	private String 	postalCode;
	private String 	district;
	
	private byte[] photoBinary;
	private byte[] thumbprintBinary;
	private byte[] signatureBinary;
	private String photo; //base64String
	private String thumbprint; //base64String
	private String signature; //base64String
	private List<FingerprintDTO> fpList;
	private Long searchResultId;
	private String jobId; // added by Sailaja, for remarks tab update
	private Boolean fullyAmputatedFlag; // added by Sailaja, for fully amputated 
	private Map<Integer,Double> fpEncodeMap; // added by Sailaja, to display the fp encode icon in the investigation page
	private Map<Integer,Double> fpQualityMap; // added by Sailaja, to display the fp quality icon in the investigation page
	private Map<Integer, Double> fpVerifyMap; //added by Sailaja, to display the fp verify icon in investigation page
	private List<CodeValues> rejectReason; //added by Sailaja on 25 Oct 2013 to display the desc from code table.
	private String rejectRemarks; // added by Sailaja on 31 Oct 2013 to serialise the form
	private String rejectReasons;  // added by Sailaja on 31 Oct 2013 to serialise the form
	private String ccn; // added by Sailaja on 01 Nov 2013 to display ccn in hit records.
	private String cardStatus; // added by Sailaja on 01 Nov 2013 to display card status in hit records.
	private String mainCandNin; //added by Sailaja on 04 Nov 2013 to get the main Candidate Nin value in form
	private List<CodeValues> cancelTransOptions; //added by Sailaja on 25 Nov 2013 to cancel the transaction options
	private String cancelTransnOption; //added by Sailaja on 25 Nov 2013 to cancel the transaction form
	private Date logCreateTime; //added by Sailaja to display in the perso tab
	
	
	public Date getLogCreateTime() {
		return logCreateTime;
	}
	public void setLogCreateTime(Date logCreateTime) {
		this.logCreateTime = logCreateTime;
	}
	public String getCancelTransnOption() {
		return cancelTransnOption;
	}
	public void setCancelTransnOption(String cancelTransnOption) {
		this.cancelTransnOption = cancelTransnOption;
	}
	public List<CodeValues> getCancelTransOptions() {
		return cancelTransOptions;
	}
	public void setCancelTransOptions(List<CodeValues> list) {
		this.cancelTransOptions = list;
	}
	/**
	 * @return the mainCandNin
	 */
	public String getMainCandNin() {
		return mainCandNin;
	}
	/**
	 * @param mainCandNin the mainCandNin to set
	 */
	public void setMainCandNin(String mainCandNin) {
		this.mainCandNin = mainCandNin;
	}
	/**
	 * @return the ccn
	 */
	public String getCcn() {
		return ccn;
	}
	/**
	 * @param ccn the ccn to set
	 */
	public void setCcn(String ccn) {
		this.ccn = ccn;
	}
	/**
	 * @return the cardStatus
	 */
	public String getCardStatus() {
		return cardStatus;
	}
	/**
	 * @param cardStatus the cardStatus to set
	 */
	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}
	/**
	 * @return the rejectRemarks
	 */
	public String getRejectRemarks() {
		return rejectRemarks;
	}
	/**
	 * @param rejectRemarks the rejectRemarks to set
	 */
	public void setRejectRemarks(String rejectRemarks) {
		this.rejectRemarks = rejectRemarks;
	}
	/**
	 * @return the rejectReasons
	 */
	public String getRejectReasons() {
		return rejectReasons;
	}
	/**
	 * @param rejectReasons the rejectReasons to set
	 */
	public void setRejectReasons(String rejectReasons) {
		this.rejectReasons = rejectReasons;
	}
	/**
	 * @return the fpVerifyMap
	 */
	public Map<Integer, Double> getFpVerifyMap() {
		return fpVerifyMap;
	}
	/**
	 * @param fpVerifyMap the fpVerifyMap to set
	 */
	public void setFpVerifyMap(Map<Integer, Double> fpVerifyMap) {
		this.fpVerifyMap = fpVerifyMap;
	}
	/**
	 * @return the rejectReason
	 */
	public List<CodeValues> getRejectReason() {
		return rejectReason;
	}
	/**
	 * @param list the rejectReason to set
	 */
	public void setRejectReason(List<CodeValues> list) {
		this.rejectReason = list;
	}
	private String verifyDecision; // added by swapna.
	/**
	 * @return the verifyDecision
	 */
	public String getVerifyDecision() {
		return verifyDecision;
	}
	/**
	 * @param verifyDecision the verifyDecision to set
	 */
	public void setVerifyDecision(String verifyDecision) {
		this.verifyDecision = verifyDecision;
	}
	/**
	 * @return the fullyAmputatedFlag
	 */
	public Boolean getFullyAmputatedFlag() {
		return fullyAmputatedFlag;
	}
	/**
	 * @param fullyAmputatedFlag the fullyAmputatedFlag to set
	 */
	public void setFullyAmputatedFlag(Boolean fullyAmputatedFlag) {
		this.fullyAmputatedFlag = fullyAmputatedFlag;
	}
	/**
	 * @return the jobId
	 */
	public String getJobId() {
		return jobId;
	}
	/**
	 * @param jobId the jobId to set
	 */
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	/**
	 * @return the remarksData
	 */
	public String getRemarksData() {
		return remarksData;
	}
	/**
	 * @param remarksData the remarksData to set
	 */
	public void setRemarksData(String remarksData) {
		this.remarksData = remarksData;
	}
	
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the wkstnId
	 */
	public String getWkstnId() {
		return wkstnId;
	}
	/**
	 * @param wkstnId the wkstnId to set
	 */
	public void setWkstnId(String wkstnId) {
		this.wkstnId = wkstnId;
	}
	/**
	 * @return the uploadJobId
	 */
	public long getUploadJobId() {
		return uploadJobId;
	}
	/**
	 * @param uploadJobId the uploadJobId to set
	 */
	public void setUploadJobId(long uploadJobId) {
		this.uploadJobId = uploadJobId;
	}
	/**
	 * @return the investigationType
	 */
	public String getInvestigationType() {
		return investigationType;
	}
	/**
	 * @param investigationType the investigationType to set
	 */
	public void setInvestigationType(String investigationType) {
		this.investigationType = investigationType;
	}
	/**
	 * @return the matchingScore
	 */
	public String getMatchingScore() {
		return matchingScore;
	}
	/**
	 * @param matchingScore the matchingScore to set
	 */
	public void setMatchingScore(String matchingScore) {
		this.matchingScore = matchingScore;
	}
	/**
	 * @return the matchScore
	 */
	public Map<Integer, Double> getMatchScore() {
		return matchScore;
	}
	/**
	 * @param matchScore the matchScore to set
	 */
	public void setMatchScore(Map<Integer, Double> matchScore) {
		this.matchScore = matchScore;
	}
	
	/**
	 * @return the searchResultId
	 */
	public Long getSearchResultId() {
		return searchResultId;
	}
	/**
	 * @param searchResultId the searchResultId to set
	 */
	public void setSearchResultId(Long searchResultId) {
		this.searchResultId = searchResultId;
	}
	/**
	 * @return the fpList
	 */
	public List<FingerprintDTO> getFpList() {
		return fpList;
	}
	/**
	 * @param fpList the fpList to set
	 */
	public void setFpList(List<FingerprintDTO> fpList) {
		this.fpList = fpList;
	}
	/**
	 * @param photo the photo to set
	 */
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	/**
	 * @param thumbprint the thumbprint to set
	 */
	public void setThumbprint(String thumbprint) {
		this.thumbprint = thumbprint;
	}
	/**
	 * @param signature the signature to set
	 */
	public void setSignature(String signature) {
		this.signature = signature;
	}
	private String maritalStatus;
	private Date marriageDate;
	private String marriageDateApprox;
	private Date divorceDate;
	private String divorceDateApprox;
	
	private String spouseNin;
	private String spouseSurname;
	private String spouseFirstName;
	private String optionSurname; // Added by Sailaja on 25/09/2013
	
	private String fatherNin;
	private String fatherSurname;
	private String fatherFirstName;
	private String motherNin;
	private String motherSurname;
	private String motherFirstName;
	
	/**
	 * @return the optionSurname
	 */
	public String getOptionSurname() {
		return optionSurname;
	}
	/**
	 * @param optionSurname the optionSurname to set
	 */
	public void setOptionSurname(String optionSurname) {
		this.optionSurname = optionSurname;
	}

	private Date deathDate;
	private String deathDateApprox;
	/**
	 * @return the nicId
	 */
	public String getNicId() {
		return nicId;
	}
	/**
	 * @param nicId the nicId to set
	 */
	public void setNicId(String nicId) {
		this.nicId = nicId;
	}
	/**
	 * @return the transactionId
	 */
	public String getTransactionId() {
		return transactionId;
	}
	/**
	 * @param transactionId the transactionId to set
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	/**
	 * @return the hitFingers
	 */
	public String getHitFingers() {
		return hitFingers;
	}
	/**
	 * @param hitFingers the hitFingers to set
	 */
	public void setHitFingers(String hitFingers) {
		this.hitFingers = hitFingers;
	}
	
	/**
	 * @return the nin
	 */
	public String getNin() {
		return nin;
	}
	/**
	 * @param nin the nin to set
	 */
	public void setNin(String nin) {
		this.nin = nin;
	}
	/**
	 * @return the ninMatchFlag
	 */
	public Boolean getNinMatchFlag() {
		return ninMatchFlag;
	}
	/**
	 * @param ninMatchFlag the ninMatchFlag to set
	 */
	public void setNinMatchFlag(Boolean ninMatchFlag) {
		this.ninMatchFlag = ninMatchFlag;
	}
	/**
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}
	/**
	 * @param surname the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}
	/**
	 * @return the surnameMatchFlag
	 */
	public Boolean getSurnameMatchFlag() {
		return surnameMatchFlag;
	}
	/**
	 * @param surnameMatchFlag the surnameMatchFlag to set
	 */
	public void setSurnameMatchFlag(Boolean surnameMatchFlag) {
		this.surnameMatchFlag = surnameMatchFlag;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the firstNameMatchFlag
	 */
	public Boolean getFirstNameMatchFlag() {
		return firstNameMatchFlag;
	}
	/**
	 * @param firstNameMatchFlag the firstNameMatchFlag to set
	 */
	public void setFirstNameMatchFlag(Boolean firstNameMatchFlag) {
		this.firstNameMatchFlag = firstNameMatchFlag;
	}
	/**
	 * @return the surnameAtBirth
	 */
	public String getSurnameAtBirth() {
		return surnameAtBirth;
	}
	/**
	 * @param surnameAtBirth the surnameAtBirth to set
	 */
	public void setSurnameAtBirth(String surnameAtBirth) {
		this.surnameAtBirth = surnameAtBirth;
	}
	/**
	 * @return the surnameAtBirthMatchFlag
	 */
	public Boolean getSurnameAtBirthMatchFlag() {
		return surnameAtBirthMatchFlag;
	}
	/**
	 * @param surnameAtBirthMatchFlag the surnameAtBirthMatchFlag to set
	 */
	public void setSurnameAtBirthMatchFlag(Boolean surnameAtBirthMatchFlag) {
		this.surnameAtBirthMatchFlag = surnameAtBirthMatchFlag;
	}
	/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}
	/**
	 * @param sex the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}
	/**
	 * @return the sexMatchFlag
	 */
	public Boolean getSexMatchFlag() {
		return sexMatchFlag;
	}
	/**
	 * @param sexMatchFlag the sexMatchFlag to set
	 */
	public void setSexMatchFlag(Boolean sexMatchFlag) {
		this.sexMatchFlag = sexMatchFlag;
	}
	/**
	 * @return the birthDate
	 */
	public Date getBirthDate() {
		return birthDate;
	}
	/**
	 * @param birthDate the birthDate to set
	 */
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	/**
	 * @return the birthDateApprox
	 */
	public String getBirthDateApprox() {
		return birthDateApprox;
	}
	/**
	 * @param birthDateApprox the birthDateApprox to set
	 */
	public void setBirthDateApprox(String birthDateApprox) {
		this.birthDateApprox = birthDateApprox;
	}
	/**
	 * @return the flatNoApartmentName
	 */
	public String getFlatNoApartmentName() {
		return flatNoApartmentName;
	}
	/**
	 * @param flatNoApartmentName the flatNoApartmentName to set
	 */
	public void setFlatNoApartmentName(String flatNoApartmentName) {
		this.flatNoApartmentName = flatNoApartmentName;
	}
	/**
	 * @return the flatNoApartmentNameMatchFlag
	 */
	public Boolean getFlatNoApartmentNameMatchFlag() {
		return flatNoApartmentNameMatchFlag;
	}
	/**
	 * @param flatNoApartmentNameMatchFlag the flatNoApartmentNameMatchFlag to set
	 */
	public void setFlatNoApartmentNameMatchFlag(Boolean flatNoApartmentNameMatchFlag) {
		this.flatNoApartmentNameMatchFlag = flatNoApartmentNameMatchFlag;
	}
	/**
	 * @return the townVillage
	 */
	public String getTownVillage() {
		return townVillage;
	}
	/**
	 * @param townVillage the townVillage to set
	 */
	public void setTownVillage(String townVillage) {
		this.townVillage = townVillage;
	}
	/**
	 * @return the townVillageMatchFlag
	 */
	public Boolean getTownVillageMatchFlag() {
		return townVillageMatchFlag;
	}
	/**
	 * @param townVillageMatchFlag the townVillageMatchFlag to set
	 */
	public void setTownVillageMatchFlag(Boolean townVillageMatchFlag) {
		this.townVillageMatchFlag = townVillageMatchFlag;
	}
	/**
	 * @return the streetName
	 */
	public String getStreetName() {
		return streetName;
	}
	/**
	 * @param streetName the streetName to set
	 */
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	/**
	 * @return the streetNameMatchFlag
	 */
	public Boolean getStreetNameMatchFlag() {
		return streetNameMatchFlag;
	}
	/**
	 * @param streetNameMatchFlag the streetNameMatchFlag to set
	 */
	public void setStreetNameMatchFlag(Boolean streetNameMatchFlag) {
		this.streetNameMatchFlag = streetNameMatchFlag;
	}
	/**
	 * @return the locality
	 */
	public String getLocality() {
		return locality;
	}
	/**
	 * @param locality the locality to set
	 */
	public void setLocality(String locality) {
		this.locality = locality;
	}
	/**
	 * @return the postalCode
	 */
	public String getPostalCode() {
		return postalCode;
	}
	/**
	 * @param postalCode the postalCode to set
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	/**
	 * @return the district
	 */
	public String getDistrict() {
		return district;
	}
	/**
	 * @param district the district to set
	 */
	public void setDistrict(String district) {
		this.district = district;
	}
	
	/**
	 * @return the photo
	 */
	public byte[] getPhotoBinary() {
		return photoBinary;
	}
	/**
	 * @param photo the photo to set
	 */
	public void setPhotoBinary(byte[] photoBinary) {
		this.photoBinary = photoBinary;
	}
	/**
	 * @return the thumbprint
	 */
	public byte[] getThumbprintBinary() {
		return thumbprintBinary;
	}
	/**
	 * @param thumbprint the thumbprint to set
	 */
	public void setThumbprintBinary(byte[] thumbprintBinary) {
		this.thumbprintBinary = thumbprintBinary;
	}
	/**
	 * @return the signature
	 */
	public byte[] getSignatureBinary() {
		return signatureBinary;
	}
	/**
	 * @param signature the signature to set
	 */
	public void setSignatureBinary(byte[] signatureBinary) {
		this.signatureBinary = signatureBinary;
	}
	/**
	 * @return the photo
	 */
	public String getPhoto() {
		if (photo==null && photoBinary!=null) {
			photo = Base64.encodeBase64String(photoBinary);
		}
		return photo;
	}

	/**
	 * @return the thumbprint
	 */
	public String getThumbprint() {
		if (thumbprint==null && thumbprintBinary!=null) {
			thumbprint = Base64.encodeBase64String(thumbprintBinary);
		}
		return thumbprint;
	}

	/**
	 * @return the signature
	 */
	public String getSignature() {
		if (signature==null && signatureBinary!=null) {
			signature = Base64.encodeBase64String(signatureBinary);
		}
		return signature;
	}	
	
	/**
	 * @return the maritalStatus
	 */
	public String getMaritalStatus() {
		return maritalStatus;
	}
	/**
	 * @param maritalStatus the maritalStatus to set
	 */
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	/**
	 * @return the marriageDate
	 */
	public Date getMarriageDate() {
		return marriageDate;
	}
	/**
	 * @param marriageDate the marriageDate to set
	 */
	public void setMarriageDate(Date marriageDate) {
		this.marriageDate = marriageDate;
	}
	/**
	 * @return the marriageDateApprox
	 */
	public String getMarriageDateApprox() {
		return marriageDateApprox;
	}
	/**
	 * @param marriageDateApprox the marriageDateApprox to set
	 */
	public void setMarriageDateApprox(String marriageDateApprox) {
		this.marriageDateApprox = marriageDateApprox;
	}
	/**
	 * @return the divorceDate
	 */
	public Date getDivorceDate() {
		return divorceDate;
	}
	/**
	 * @param divorceDate the divorceDate to set
	 */
	public void setDivorceDate(Date divorceDate) {
		this.divorceDate = divorceDate;
	}
	/**
	 * @return the divorceDateApprox
	 */
	public String getDivorceDateApprox() {
		return divorceDateApprox;
	}
	/**
	 * @param divorceDateApprox the divorceDateApprox to set
	 */
	public void setDivorceDateApprox(String divorceDateApprox) {
		this.divorceDateApprox = divorceDateApprox;
	}
	/**
	 * @return the spouseNin
	 */
	public String getSpouseNin() {
		return spouseNin;
	}
	/**
	 * @param spouseNin the spouseNin to set
	 */
	public void setSpouseNin(String spouseNin) {
		this.spouseNin = spouseNin;
	}
	/**
	 * @return the spouseSurname
	 */
	public String getSpouseSurname() {
		return spouseSurname;
	}
	/**
	 * @param spouseSurname the spouseSurname to set
	 */
	public void setSpouseSurname(String spouseSurname) {
		this.spouseSurname = spouseSurname;
	}
	/**
	 * @return the spouseFirstName
	 */
	public String getSpouseFirstName() {
		return spouseFirstName;
	}
	/**
	 * @param spouseFirstName the spouseFirstName to set
	 */
	public void setSpouseFirstName(String spouseFirstName) {
		this.spouseFirstName = spouseFirstName;
	}
	/**
	 * @return the fatherNin
	 */
	public String getFatherNin() {
		return fatherNin;
	}
	/**
	 * @param fatherNin the fatherNin to set
	 */
	public void setFatherNin(String fatherNin) {
		this.fatherNin = fatherNin;
	}
	/**
	 * @return the fatherSurname
	 */
	public String getFatherSurname() {
		return fatherSurname;
	}
	/**
	 * @param fatherSurname the fatherSurname to set
	 */
	public void setFatherSurname(String fatherSurname) {
		this.fatherSurname = fatherSurname;
	}
	/**
	 * @return the fatherFirstName
	 */
	public String getFatherFirstName() {
		return fatherFirstName;
	}
	/**
	 * @param fatherFirstName the fatherFirstName to set
	 */
	public void setFatherFirstName(String fatherFirstName) {
		this.fatherFirstName = fatherFirstName;
	}
	/**
	 * @return the motherNin
	 */
	public String getMotherNin() {
		return motherNin;
	}
	/**
	 * @param motherNin the motherNin to set
	 */
	public void setMotherNin(String motherNin) {
		this.motherNin = motherNin;
	}
	/**
	 * @return the motherSurname
	 */
	public String getMotherSurname() {
		return motherSurname;
	}
	/**
	 * @param motherSurname the motherSurname to set
	 */
	public void setMotherSurname(String motherSurname) {
		this.motherSurname = motherSurname;
	}
	/**
	 * @return the motherFirstName
	 */
	public String getMotherFirstName() {
		return motherFirstName;
	}
	/**
	 * @param motherFirstName the motherFirstName to set
	 */
	public void setMotherFirstName(String motherFirstName) {
		this.motherFirstName = motherFirstName;
	}
	/**
	 * @return the deathDate
	 */
	public Date getDeathDate() {
		return deathDate;
	}
	/**
	 * @param deathDate the deathDate to set
	 */
	public void setDeathDate(Date deathDate) {
		this.deathDate = deathDate;
	}
	/**
	 * @return the deathDateApprox
	 */
	public String getDeathDateApprox() {
		return deathDateApprox;
	}
	/**
	 * @param deathDateApprox the deathDateApprox to set
	 */
	public void setDeathDateApprox(String deathDateApprox) {
		this.deathDateApprox = deathDateApprox;
	}
	/**
	 * @return the fpEncodeMap
	 */
	public Map<Integer, Double> getFpEncodeMap() {
		return fpEncodeMap;
	}
	/**
	 * @param fpEncodeMap the fpEncodeMap to set
	 */
	public void setFpEncodeMap(Map<Integer, Double> fpEncodeMap) {
		this.fpEncodeMap = fpEncodeMap;
	}
	/**
	 * @return the fpQualityMap
	 */
	public Map<Integer, Double> getFpQualityMap() {
		return fpQualityMap;
	}
	/**
	 * @param fpQualityMap the fpQualityMap to set
	 */
	public void setFpQualityMap(Map<Integer, Double> fpQualityMap) {
		this.fpQualityMap = fpQualityMap;
	}
	
	

	
}
