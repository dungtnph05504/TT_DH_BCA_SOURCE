package com.nec.asia.nic.comp.trans.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.dto.ResponseDTO;
import com.nec.asia.nic.comp.trans.service.exception.PersoServiceException;
import com.nec.asia.nic.utils.DateUtil;
import com.nec.lds.chip.ChipInfo;
import com.sshtools.j2ssh.sftp.SftpFile;

/**
 * The interface to integrate with Perso System Web Service .
 * 
 * @author chris_wong
 *
 */

/* 
 * Modification History:
 *  
 * 03 Oct 2013 (chris): add constant for DEFAULT_PERSO_REF_ID as 0.
 * 29 Oct 2013 (chris): add constant for PARA_NAME [CURRENT_SITE_CODE], [EXPRESS_THRESHOLD]
 * 11 Dec 2013 (chris): add constant for PERSO REASON
 * 06 Jan 2014 (chris): add constants for PERSO CANCEL, add method cancelJobByTransactionId()
 * 03 Jun 2014 (chris): add constant for PACKAGE_NO_FOUND
 * 27 Apr 2016 (chris): add constant for PARA_NAME_MAX_NUM_DAYS_FOR_PRINTING
 * 17 Aug 2016 (chris): add constant for PARA: APPLY_NAME_TRUNCATION
 */
public interface PersoService {
	
	public static final String TRANSACTION_STAGE_PERSO_REGISTER = NicTransactionLog.TRANSACTION_STAGE_PERSO_REGISTER; //"PERSO_REGISTER";
	public static final String TRANSACTION_STAGE_PERSONALIZATION = "PERSO";
	
	//public final static String CARD_STATUS_DISPATCHED = "DISPATCHED";//SUSPENDED
	public final static String CARD_STATUS_ACTIVE = "ACTIVE";
	public final static String TRANSACTION_STATUS_CARD_PERSONALIZED = "PERSO_PERSONALIZED";
	public final static String TRANSACTION_STATUS_CARD_DISPATCHED = "PERSO_CARD_DISPATCHED";
	public final static String TRANSACTION_STATUS_PERSO_REGISTER_COMPLETED = NicTransactionLog.TRANSACTION_STATUS_NICWF_PERSO_REGISTER_COMPLETED;
	public final static String TRANSACTION_STATUS_PERSO_REGISTER_ERROR = NicTransactionLog.TRANSACTION_STATUS_NICWF_PERSO_REGISTER_ERROR;
	
	//12-Nov-2018
	public final static String TRANSACTION_STATUS_LDS_REGISTER_COMPLETED = NicTransactionLog.TRANSACTION_STATUS_NICWF_LDS_REGISTER_COMPLETED;
	public final static String TRANSACTION_STATUS_LDS_REGISTER_ERROR = NicTransactionLog.TRANSACTION_STATUS_NICWF_LDS_REGISTER_ERROR;
	
	//06 Jan 2014 (chris)
	public final static String TRANSACTION_STAGE_PERSO_CANCEL 				= NicTransactionLog.TRANSACTION_STAGE_PERSO_CANCEL;
	public final static String TRANSACTION_STATUS_PERSO_CANCEL_COMPLETED 	= NicTransactionLog.TRANSACTION_STATUS_PERSO_CANCEL_COMPLETED;
	public final static String TRANSACTION_STATUS_PERSO_CANCEL_ERROR 		= NicTransactionLog.TRANSACTION_STATUS_PERSO_CANCEL_ERROR;
	public final static String TRANSACTION_STATUS_PERSO_CANCEL_REJECTED		= NicTransactionLog.TRANSACTION_STATUS_PERSO_CANCEL_REJECTED;
	
	public final static String PERSO_ERROR_CODE_DESERIALIZE_DATA_ERROR		= "DESERIALIZE DATA ERROR";
	public final static String PERSO_ERROR_CODE_PERSO_QC_PERMANENT_REJECT	= "PERSO QC PERMANENT REJECT";
	
	public final static String NIC_PERSO_ERROR_CODE_PACKAGE_NO_FOUND		= "PACKAGE NO FOUND";
	
	//to indicate update_by or create_by
	public final static String SYSTEM_PERSO = "SYSTEM_PERSO";
	public final static String SYSTEM_NIC = "SYSTEM_NIC"; //29 Oct 2013 (chris)

	public final static String DEFAULT_PERSO_REF_ID = "0"; //03 Oct 2013 (chris)
	
	public static final String PARA_SCOPE_SYSTEM = "SYSTEM"; //29 Oct 2013 (chris)
	public static final String PARA_SCOPE_PERSO = "PERSO"; // 01 Apr 2016 (khang)
	public static final String PARA_NAME_SYSTEM_SITE_CODE = "SYSTEM_SITE_CODE"; 
	public static final String PARA_NAME_NIC_EXPRESS_THRESHOLD = "NIC_EXPRESS_THRESHOLD"; //29 Oct 2013 (chris)
	public static final String PARA_NAME_APPLY_NAME_TRUNCATION = "APPLY_NAME_TRUNCATION"; //17 Aug 2016 (chris)
	
	//29 Dec 2015 (khang)
	public static final String PARA_NAME_PERSO_SFTP_CONFIG = "PERSO_SFTP_CONFIG";     
	public static final String SFTP_PERSO_HOST = "SFTP.HOST";
	public static final String SFTP_PERSO_PORT = "SFTP.PORT";
	public static final String SFTP_PERSO_USERID = "SFTP.USERID";
	public static final String SFTP_PERSO_PASSKEY = "SFTP.PASSKEY";
	public static final String SFTP_PERSO_DISPATCH_DIRECTORY = "SFTP.DISPATCH.INFO.DIRECTORY";	
	public static final String SFTP_PERSO_DATA_DIRECTORY = "SFTP.PERSO.DATA.DIRECTORY";
	public static final String SFTP_PERSO_STATUS_DIRECTORY = "SFTP.PERSO.STATUS.DIRECTORY";
	public static final String SFTP_PERSO_ARCHIVE_DIRECTORY = "SFTP.PERSO.ARCHIVE.DIRECTORY";
	public static final String SFTP_PERSO_ROOT_DIRECTORY = "SFTP.PERSO.ROOT.DIRECTORY";
	
	public static final String PARA_NAME_IS_REMARK_SUPPORTED = "IS_REMARK_SUPPORTED"; // 01 Apr 2016 (khang)
	public static final String PARA_NAME_DEFAULT_SIGNATURE_IMAGE = "DEFAULT_SIGNATURE_IMAGE"; // 01 Apr 2016 (khang)
	public static final String PARA_NAME_MAX_NUM_DAYS_FOR_PRINTING = "MAX_NUM_DAYS_FOR_PRINTING"; // 27 Apr 2016 (chris)
	
	public static final String PARA_NAME_PERSO_FILE_CONFIG = "PERSO_FILE_CONFIG";     
	public static final String FILE_PERSO_ROOT_DIRECTORY = "FILE.PERSO.ROOT.DIRECTORY";    
	public static final String FILE_PERSO_DATA_DIRECTORY = "FILE.PERSO.DATA.DIRECTORY";    
	public static final String FILE_PERSO_STATUS_DIRECTORY = "FILE.PERSO.STATUS.DIRECTORY";
	public static final String FILE_PERSO_HOST = "FILE.HOST";
	public static final String FILE_PERSO_USERID = "FILE.USERID";
	public static final String FILE_PERSO_PASSKEY = "FILE.PASSKEY";
	
	public final static int PERSO_CANCEL_SUCCEED 	= 0;
	public final static int PERSO_CANCEL_FAILED 	= 1;
	public final static int PERSO_CANCEL_DENIED 	= 2;
	
	public final static String DATE_FORMAT_FOR_PERSO = DateUtil.FORMAT_DD_MM_YYYY; 
	
	public void submitPersoData(NicTransaction nicTransaction, String submittingArn) throws PersoServiceException;
	
	public void submitPersoDataNew(NicTransaction nicTransaction, String submittingArn, ChipInfo chipinfo, Date dateOfIssue, Date dateOfExpiry) throws PersoServiceException;
	
	public void receiveNicPersoInfo(String nicPersoInfoUploadXml, String xmlCheckSum) throws PersoServiceException;

	public void updateCardStatus(String cardInfoXml, String xmlCheckSum) throws PersoServiceException;

	public void getPackageJobByPackageID(String packageId) throws PersoServiceException;
	
	public void getPackageJobByTransactionID(String transactionId) throws PersoServiceException;
	
	public ResponseDTO cancelJobByTransactionId(String transactionId, String officerId, String workstationId) throws PersoServiceException;

	public List<SftpFile> listFiles(String directory) throws PersoServiceException;

	public void updatePersoStatus() throws PersoServiceException;

    public void updateDispatchInfo() throws PersoServiceException;
    
    public Map<String, Integer> getFolderStatistics () throws PersoServiceException;
}
