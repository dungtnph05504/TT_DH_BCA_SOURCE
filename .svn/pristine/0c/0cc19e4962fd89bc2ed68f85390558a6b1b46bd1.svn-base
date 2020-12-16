package com.nec.asia.nic.comp.trans.service;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.dto.LdsRequestWsDto;
import com.nec.asia.nic.comp.trans.dto.LdsResponseWsDto;
import com.nec.asia.nic.comp.trans.service.exception.DataPackServiceException;

/**
 * The interface to integrate with Data Packing Web Service .
 * 
 * @author chris_wong
 *
 */
/* 
 * Modification History:
 *  
 * 29 Oct 2013 (chris): add constant for SYSTEM_NIC
 */
public interface DataPackService {
	
	public static final String PARA_SCOPE_SYSTEM = "SYSTEM";
	public static final String PARA_NAME_SAM_KEY_VERSION = "SAM_KEY_VERSION";
	@Deprecated
	public static final String PARA_NAME_DEFAULT_SIGNATURE_LOGO = "SIGNATURE_LOGO";
	public static final String PARA_NAME_CURRENT_SITE_CODE = "CURRENT_SITE_CODE";
	
	public static final String DOC_TYPE_ENCODING = NicTransactionAttachment.DOC_TYPE_ENCODING;
	public static final String DOC_TYPE_PERSO = NicTransactionAttachment.DOC_TYPE_PERSO;
	
	public static final String TRANSACTION_STATE_DATA_PREPARATION = NicTransactionLog.TRANSACTION_STAGE_DATA_PREPARATION;
	public static final String TRANSACTION_STATUS_DATA_PREPARATION_SUCCESS = NicTransactionLog.TRANSACTION_STATUS_NICWF_DATA_PREPARATION_COMPLETED;
	public static final String TRANSACTION_STATUS_DATA_PREPARATION_ERROR = NicTransactionLog.TRANSACTION_STATUS_NICWF_DATA_PREPARATION_ERROR;
	//PERSO_DATA_COMPLETED,	PERSO_DATA_ERROR

	public static final String REGISTRATION_TOWN_VILLAGE = "TOWN_VILLAGE";
	public static final String REGISTRATION_DISTRICT  = "DISTRICT";
	public static final String REGISTRATION_COUNTRY = "COUNTRY";
	
	//to indicate update_by or create_by
	public final static String SYSTEM_NIC = "SYSTEM_NIC";
	
	public void preparePersoData(NicTransaction nicTransaction) throws DataPackServiceException;
	
	public LdsResponseWsDto preparePersoData_V5(NicTransaction nicTransaction) throws DataPackServiceException;
	
	public LdsResponseWsDto GetLDS(LdsRequestWsDto request, String paramUrl)
			throws JsonParseException, JsonMappingException, IOException;
	
	public byte[] getTransactionDocument(NicTransaction nicTransaction,
			String docType, String serialNo);
}
