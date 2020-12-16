package com.nec.asia.nic.comp.trans.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.nec.asia.nic.dx.trans.FacialImage;
import com.nec.asia.nic.dx.trans.FacialInfo;
import com.nec.asia.nic.dx.trans.Fingerprint;
import com.nec.asia.nic.dx.trans.FingerprintInfo;
import com.nec.asia.nic.dx.trans.MainTransaction;
import com.nec.asia.nic.dx.trans.RegistrationData;
import com.nec.asia.nic.framework.common.ChecksumGenerator;
import com.nec.asia.nic.utils.DateUtil;



/**
 * @author chris_wong
 *
 */
/*
 * Modification History:
 * 
 * 23 Dec 2015 (chris): copy method computeChecksum() from ChecksumGenerator
 */
public class TransactionChecksumGenerator extends ChecksumGenerator {
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(TransactionChecksumGenerator.class);
	private static final String MD5_ALGORITHM 		= "MD5";
	private static final String SHA1_ALGORITHM 		= "SHA-1";
	private static final String SHA256_ALGORITHM 	= "SHA-256";
	
	/** The Base64 utility for encode and decode **/
	private Base64 base64Util = new Base64(0);
	private String algorithm;
	
	public TransactionChecksumGenerator() {
		algorithm = MD5_ALGORITHM;
	}
	public TransactionChecksumGenerator(String algorithm) {
		this.algorithm = algorithm;
		if (!(StringUtils.equalsIgnoreCase(MD5_ALGORITHM, algorithm) ||
				StringUtils.equalsIgnoreCase(MD5_ALGORITHM, algorithm) ||
				StringUtils.equalsIgnoreCase(MD5_ALGORITHM, algorithm))) {
			logger.warn("Non Default Algorithm["+algorithm+"] used.");
		}
	}
	
	public synchronized String computeCheckSum(MainTransaction transactionDto) throws Exception {
		String checkSum = null;
		
		String checkSumDG1 = "";
		String checkSumDG2 = "";
		String checkSumDG3 = "";
		String checkSumDG11 = "";
		try {
			//DG1 (Demographic Data)
			if (transactionDto!=null && transactionDto.getRegistrationData()!=null && transactionDto.getRegistrationData().getPersonDetail()!=null) {
			    String docNumber = ""; //transactionDto.getNin();
			    String dateOfApplication = DateUtil.parseDate(transactionDto.getDateOfApplication(), DateUtil.FORMAT_DDdashMMdashYYYY); //String dateIssue;
			    String surname = transactionDto.getRegistrationData().getPersonDetail().getSurnameFull();
			    String firstName = transactionDto.getRegistrationData().getPersonDetail().getFirstnameFull();
			    String gender = transactionDto.getRegistrationData().getPersonDetail().getGender();
			    String surnameAtBirth = ""; //StringUtils.defaultString(transactionDto.getRegistrationData().getPersonDetail().getSurnameAtBirthFull(), "");
			    String dateBirth = "";
			    if (transactionDto.getRegistrationData().getPersonDetail().getDateOfBirth()!=null) {
			    	dateBirth = DateUtil.parseDate(transactionDto.getRegistrationData().getPersonDetail().getDateOfBirth(), DateUtil.FORMAT_DDdashMMdashYYYY); //String dateIssue;
			    } else if (StringUtils.isNotBlank(transactionDto.getRegistrationData().getPersonDetail().getApproxDOB())) {
			    	dateBirth = transactionDto.getRegistrationData().getPersonDetail().getApproxDOB();
			    }
			    String dataStringDG1 = "NO="+docNumber+"DOA="+dateOfApplication+"SN="+surname+"FN="+firstName+"SNB="+surnameAtBirth+"SEX="+gender+"DOB="+dateBirth;
			    checkSumDG1 = computeCheckSum(dataStringDG1);
			    if (logger.isDebugEnabled()) {
			    	logger.debug("dataStringDG1="+dataStringDG1);
			    	logger.debug("checkSumDG1="+checkSumDG1);
			    }
			}
			//DG2 (Face)
			if (transactionDto!=null && transactionDto.getRegistrationData()!=null && transactionDto.getRegistrationData().getFacialInfo()!=null) {
			    String photoOriginal="";
			    String photoGrey="";
			    String photoChip="";
			    String photoCLI="";
			    FacialInfo facialInfoDto = transactionDto.getRegistrationData().getFacialInfo();
			    for (FacialImage facialImageDto : facialInfoDto.getFacialImages()) {
			    	if (facialImageDto.getFacialData()!=null && facialImageDto.getFacialData().length>0) {
			    		if (StringUtils.equalsIgnoreCase("PH_CAP", facialImageDto.getDocType())) {
			    			photoOriginal = base64Util.encodeToString(facialImageDto.getFacialData());
			    		}
			    		if (StringUtils.equalsIgnoreCase("PH_GREY", facialImageDto.getDocType())) {
			    			photoGrey = base64Util.encodeToString(facialImageDto.getFacialData());
			    		}
			    		if (StringUtils.equalsIgnoreCase("PH_CHIP", facialImageDto.getDocType())) {
			    			photoChip = base64Util.encodeToString(facialImageDto.getFacialData());
			    		}
			    		if (StringUtils.equalsIgnoreCase("PH_CLI", facialImageDto.getDocType())) {
			    			photoCLI = base64Util.encodeToString(facialImageDto.getFacialData());
			    		}
			    	}
			    }
	
			    String dataStringDG2 = "ORI="+photoOriginal+"CHI="+photoChip+"GRE="+photoGrey+"CLI="+photoCLI;
			    checkSumDG2 = computeCheckSum(dataStringDG2);
	//		    if (logger.isDebugEnabled()) {
	//		    	logger.debug("dataStringDG2="+dataStringDG2);
	//		    	logger.debug("checkSumDG2="+checkSumDG2);
	//		    }
			}
			//DG3 (Fingerprint)
			if (transactionDto!=null && transactionDto.getRegistrationData()!=null && transactionDto.getRegistrationData().getFingerprintInfo()!=null) {
				String dataStringDG3 = "";
			    FingerprintInfo fingerprintInfoDto = transactionDto.getRegistrationData().getFingerprintInfo();
			    for (int i=1; i<=10; i++) { //1:Right Thumb, 10:Left Little 
				    for (Fingerprint fingerprintDto : fingerprintInfoDto.getFingerprints()) {
				    	int position = Integer.parseInt(fingerprintDto.getFingerprintPosition());
				    	if (i==position) {
				    		if (fingerprintDto.getFingerprintData()!=null) {
				    			String fp = base64Util.encodeToString(fingerprintDto.getFingerprintData());
				    			dataStringDG3 += "FP"+i+"="+fp;
				    		}
//				    		if (fingerprintDto.getMinutiaData()!=null) {
//				    			String mnu = base64Util.encodeToString(fingerprintDto.getMinutiaData());
//				    			dataStringDG3 += "MNU"+i+"="+mnu;
//				    		}
				    	}
				    }
			    }
			    
			    checkSumDG3 = computeCheckSum(dataStringDG3);
	//		    if (logger.isDebugEnabled()) {
	//		    	logger.debug("dataStringDG3="+dataStringDG3);
	//		    	logger.debug("checkSumDG3="+checkSumDG3);
	//		    }
			}
			//DG11 (Address)
			if (transactionDto!=null && transactionDto.getRegistrationData()!=null) {
				String dataStringDG11 = "";
				//get Address
				RegistrationData addressDTO = transactionDto.getRegistrationData();
				String localAddressLine1 = StringUtils.defaultString(addressDTO.getAddressLine1(), "");
				String localAddressLine2 = StringUtils.defaultString(addressDTO.getAddressLine2(), "");
				String localAddressLine3 = StringUtils.defaultString(addressDTO.getAddressLine3(), "");
				String localAddressLine4 = StringUtils.defaultString(addressDTO.getAddressLine4(), "");
				String overseasAddress1 = StringUtils.defaultString(addressDTO.getOverseasAddress(), "");
				String overseasAddress2 = StringUtils.defaultString(addressDTO.getOverseasCountry(), "");
				
				dataStringDG11 = "LA1="+localAddressLine1+"LA2="+localAddressLine2+"LA3="+localAddressLine3
				+"LA4="+localAddressLine4+"OA1="+overseasAddress1+"OA2="+overseasAddress2;
			    checkSumDG11 = computeCheckSum(dataStringDG11);
	//		    if (logger.isDebugEnabled()) {
	//		    	logger.debug("dataStringDG11="+dataStringDG11);
	//		    	logger.debug("checkSumDG11="+checkSumDG11);
	//		    }
			}
			String dataStringOverall = "DG1="+checkSumDG1+"DG2="+checkSumDG2+"DG3="+checkSumDG3+"DG11"+checkSumDG11;
			checkSum = computeCheckSum(dataStringOverall);
			if (logger.isDebugEnabled()) {
				logger.debug("transactionId="+transactionDto.getTransactionID()+",DOA="+transactionDto.getDateOfApplication());
				logger.debug("dataStringOverall="+dataStringOverall);
				logger.debug("checkSum="+checkSum);
			}
			if (StringUtils.isNotBlank(transactionDto.getChecksum()) && !StringUtils.equals(checkSum, transactionDto.getChecksum())) {
				logger.warn("[computeCheckSum] different checksum found between generated and received. transactionId="+transactionDto.getTransactionID()+", checksum received="+transactionDto.getChecksum()+", checksum generated="+checkSum);
			}
		} catch (Exception e) {
			logger.error("[computeCheckSum] unexpected error.", e);
			throw e;
		}
		return checkSum;
	}
	
}
