package com.nec.asia.nic.framework.common;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

//import com.nec.asia.nic.dx.trans.Address;
//import com.nec.asia.nic.dx.trans.FacialImage;
//import com.nec.asia.nic.dx.trans.FacialInfo;
//import com.nec.asia.nic.dx.trans.Fingerprint;
//import com.nec.asia.nic.dx.trans.FingerprintInfo;
//import com.nec.asia.nic.dx.trans.MainTransaction;
//import com.nec.asia.nic.utils.DateUtil;



/**
 * @author chris_wong
 *
 */
/*
 * Modification History:
 * 
 * 23 Oct 2013 (chris): init
 * 01 Dec 2013 (chris): add logging if checksum is different, catch exception.
 * 18 Mar 2014 (chris): add synchronized on encode method
 * 23 Dec 2015 (chris): remove method computeChecksum() 
 */
public class ChecksumGenerator {
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(ChecksumGenerator.class);
	private static final String MD5_ALGORITHM 		= "MD5";
	private static final String SHA1_ALGORITHM 		= "SHA-1";
	private static final String SHA256_ALGORITHM 	= "SHA-256";
	
	/** The Base64 utility for encode and decode **/
	private Base64 base64Util = new Base64(0);
	private String algorithm;
	
	public ChecksumGenerator() {
		algorithm = MD5_ALGORITHM;
	}
	public ChecksumGenerator(String algorithm) {
		this.algorithm = algorithm;
		if (!(StringUtils.equalsIgnoreCase(MD5_ALGORITHM, algorithm) ||
				StringUtils.equalsIgnoreCase(MD5_ALGORITHM, algorithm) ||
				StringUtils.equalsIgnoreCase(MD5_ALGORITHM, algorithm))) {
			logger.warn("Non Default Algorithm["+algorithm+"] used.");
		}
	}
	
//	public synchronized String computeCheckSum(MainTransaction transactionDto) throws Exception {
//		String checkSum = null;
//		
//		String checkSumDG1 = "";
//		String checkSumDG2 = "";
//		String checkSumDG3 = "";
//		String checkSumDG11 = "";
//		try {
//			//DG1 (Demographic Data)
//			if (transactionDto!=null && transactionDto.getRegistrationData()!=null && transactionDto.getRegistrationData().getPersonDetail()!=null) {
//			    String docNumber = transactionDto.getNin();
//			    String dateOfApplication = DateUtil.parseDate(transactionDto.getDateOfApplication(), DateUtil.FORMAT_DDdashMMdashYYYY); //String dateIssue;
//			    String surname = transactionDto.getRegistrationData().getPersonDetail().getSurnameFull();
//			    String firstName = transactionDto.getRegistrationData().getPersonDetail().getFirstnameFull();
//			    String gender = transactionDto.getRegistrationData().getPersonDetail().getGender();
//			    String surnameAtBirth = StringUtils.defaultString(transactionDto.getRegistrationData().getPersonDetail().getSurnameAtBirthFull(), "");
//			    String dateBirth = "";
//			    if (transactionDto.getRegistrationData().getPersonDetail().getDateOfBirth()!=null) {
//			    	dateBirth = DateUtil.parseDate(transactionDto.getRegistrationData().getPersonDetail().getDateOfBirth(), DateUtil.FORMAT_DDdashMMdashYYYY); //String dateIssue;
//			    } else if (StringUtils.isNotBlank(transactionDto.getRegistrationData().getPersonDetail().getApproxDOB())) {
//			    	dateBirth = transactionDto.getRegistrationData().getPersonDetail().getApproxDOB();
//			    }
//			    String dataStringDG1 = "NO="+docNumber+"DOA="+dateOfApplication+"SN="+surname+"FN="+firstName+"SNB="+surnameAtBirth+"SEX="+gender+"DOB="+dateBirth;
//			    checkSumDG1 = computeCheckSum(dataStringDG1);
//			    if (logger.isDebugEnabled()) {
//			    	logger.debug("dataStringDG1="+dataStringDG1);
//			    	logger.debug("checkSumDG1="+checkSumDG1);
//			    }
//			}
//			//DG2 (Face)
//			if (transactionDto!=null && transactionDto.getRegistrationData()!=null && transactionDto.getRegistrationData().getFacialInfo()!=null) {
//			    String photoOriginal="";
//			    String photoGrey="";
//			    String photoChip="";
//			    String photoCLI="";
//			    FacialInfo facialInfoDto = transactionDto.getRegistrationData().getFacialInfo();
//			    for (FacialImage facialImageDto : facialInfoDto.getFacialImages()) {
//			    	if (facialImageDto.getFacialData()!=null && facialImageDto.getFacialData().length>0) {
//			    		if (StringUtils.equalsIgnoreCase("PH_CAP", facialImageDto.getDocType())) {
//			    			photoOriginal = base64Util.encodeToString(facialImageDto.getFacialData());
//			    		}
//			    		if (StringUtils.equalsIgnoreCase("PH_GREY", facialImageDto.getDocType())) {
//			    			photoGrey = base64Util.encodeToString(facialImageDto.getFacialData());
//			    		}
//			    		if (StringUtils.equalsIgnoreCase("PH_CHIP", facialImageDto.getDocType())) {
//			    			photoChip = base64Util.encodeToString(facialImageDto.getFacialData());
//			    		}
//			    		if (StringUtils.equalsIgnoreCase("PH_CLI", facialImageDto.getDocType())) {
//			    			photoCLI = base64Util.encodeToString(facialImageDto.getFacialData());
//			    		}
//			    	}
//			    }
//	
//			    String dataStringDG2 = "ORI="+photoOriginal+"CHI="+photoChip+"GRE="+photoGrey+"CLI="+photoCLI;
//			    checkSumDG2 = computeCheckSum(dataStringDG2);
//	//		    if (logger.isDebugEnabled()) {
//	//		    	logger.debug("dataStringDG2="+dataStringDG2);
//	//		    	logger.debug("checkSumDG2="+checkSumDG2);
//	//		    }
//			}
//			//DG3 (Fingerprint)
//			if (transactionDto!=null && transactionDto.getRegistrationData()!=null && transactionDto.getRegistrationData().getFingerprintInfo()!=null) {
//				String dataStringDG3 = "";
//			    FingerprintInfo fingerprintInfoDto = transactionDto.getRegistrationData().getFingerprintInfo();
//			    for (int i=1; i<=10; i++) { //1:Right Thumb, 10:Left Little 
//				    for (Fingerprint fingerprintDto : fingerprintInfoDto.getFingerprints()) {
//				    	int position = Integer.parseInt(fingerprintDto.getFingerprintPosition());
//				    	if (i==position) {
//				    		if (fingerprintDto.getFingerprintData()!=null) {
//				    			String fp = base64Util.encodeToString(fingerprintDto.getFingerprintData());
//				    			dataStringDG3 += "FP"+i+"="+fp;
//				    		}
//				    		if (fingerprintDto.getMinutiaData()!=null) {
//				    			String mnu = base64Util.encodeToString(fingerprintDto.getMinutiaData());
//				    			dataStringDG3 += "MNU"+i+"="+mnu;
//				    		}
//				    	}
//				    }
//			    }
//			    
//			    checkSumDG3 = computeCheckSum(dataStringDG3);
//	//		    if (logger.isDebugEnabled()) {
//	//		    	logger.debug("dataStringDG3="+dataStringDG3);
//	//		    	logger.debug("checkSumDG3="+checkSumDG3);
//	//		    }
//			}
//			//DG11 (Address)
//			if (transactionDto!=null && transactionDto.getRegistrationData()!=null && transactionDto.getRegistrationData().getAddress()!=null) {
//				String dataStringDG11 = "";
//				//get Address
//				Address addressDTO = transactionDto.getRegistrationData().getAddress();
//				String localAddressLine1 = StringUtils.defaultString(addressDTO.getAddressLine1(), "");
//				String localAddressLine2 = StringUtils.defaultString(addressDTO.getAddressLine2(), "");
//				String localAddressLine3 = StringUtils.defaultString(addressDTO.getAddressLine3(), "");
//				String localAddressLine4 = StringUtils.defaultString(addressDTO.getAddressLine4(), "");
//				String localAddressLine5 = StringUtils.defaultString(addressDTO.getAddressLine5(), "");
//				String localAddressLine6 = StringUtils.defaultString(addressDTO.getAddressLine6(), "");
//				String preferredMailingAddress = StringUtils.defaultString(addressDTO.getPreferredMailingAddress(), "");
//				String overseasAddress1 = StringUtils.defaultString(addressDTO.getOverseasAddress(), "");
//				String overseasAddress2 = StringUtils.defaultString(addressDTO.getOverseasCountry(), "");
//				
//				dataStringDG11 = "LA1="+localAddressLine1+"LA2="+localAddressLine2+"LA3="+localAddressLine3
//				+"LA4="+localAddressLine4+"LA5="+localAddressLine5+"LA6="+localAddressLine6
//				+"PMA="+preferredMailingAddress+"OA1="+overseasAddress1+"OA2="+overseasAddress2;
//			    checkSumDG11 = computeCheckSum(dataStringDG11);
//	//		    if (logger.isDebugEnabled()) {
//	//		    	logger.debug("dataStringDG11="+dataStringDG11);
//	//		    	logger.debug("checkSumDG11="+checkSumDG11);
//	//		    }
//			}
//			String dataStringOverall = "DG1="+checkSumDG1+"DG2="+checkSumDG2+"DG3="+checkSumDG3+"DG11"+checkSumDG11;
//			checkSum = computeCheckSum(dataStringOverall);
//			if (logger.isDebugEnabled()) {
//				logger.debug("transactionId="+transactionDto.getTransactionID()+",nin="+transactionDto.getNin()+",DOA="+transactionDto.getDateOfApplication());
//				logger.debug("dataStringOverall="+dataStringOverall);
//				logger.debug("checkSum="+checkSum);
//			}
//			if (StringUtils.isNotBlank(transactionDto.getChecksum()) && !StringUtils.equals(checkSum, transactionDto.getChecksum())) {
//				logger.warn("[computeCheckSum] different checksum found between generated and received. transactionId="+transactionDto.getTransactionID()+", checksum received="+transactionDto.getChecksum()+", checksum generated="+checkSum);
//			}
//		} catch (Exception e) {
//			logger.error("[computeCheckSum] unexpected error.", e);
//			throw e;
//		}
//		return checkSum;
//	}
	
	
	
	public String computeCheckSum(String input) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    	String checkSum = null;
    	if (StringUtils.isNotBlank(input)) {
    		input = input.trim();
    		MessageDigest messageDigest = MessageDigest.getInstance(algorithm);//"MD5");//"SHA-256");
    		messageDigest.reset();
			byte origData[] = input.getBytes("UTF-8");			
			messageDigest.update(origData);
			byte byteData[] = messageDigest.digest();
			// convert the byte to hex format method 1
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				//sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
				sb.append(String.format("%02X", byteData[i] & 0xFF));
			}
			checkSum = sb.toString();
		}
    	return checkSum;
    }
	
	public synchronized String encodeString(String input) {
		String encodeString = null;
		if (StringUtils.isNotBlank(input)) {
			input = input.trim();
			encodeString = base64Util.encodeToString(input.getBytes());
		}
		return encodeString;
	}
	
	public synchronized String decodeString(String input) {
		String decodeString = null;
		if (StringUtils.isNotBlank(input)) {
			input = input.trim();
			byte[] decodeBinary = base64Util.decode(input);//.decodeBase64(input);
			decodeString = new String(decodeBinary);
		}
		return decodeString;
	}
	
	public boolean validateCheckSum(String input, String inputCheckSum) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		boolean valid = false;
    	if (StringUtils.isNotBlank(input)) {
    		String computedCheckSum = this.computeCheckSum(input);
    		valid = StringUtils.equalsIgnoreCase(inputCheckSum, computedCheckSum);
    		//logger.debug("compare checksum [1]:"+inputCheckSum+" , [2]:"+computedCheckSum+", result:"+valid);
		}
    	return valid;
	}
}
