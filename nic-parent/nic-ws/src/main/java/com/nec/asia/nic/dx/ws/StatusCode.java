package com.nec.asia.nic.dx.ws;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.enums.Enum;

/**
 * The Class ErrorCode.
 */

/* 
 * Modification History:
 *  
 * 23 May 2014 (chris): init class
 * 30 May 2014 (chris): add error code for VALWS_FIELD_LENGTH_EXCEEDED
 * 18 Jun 2014 (jp): add status codes for AdminWS.getAuthorizedFunctions
 * 19 Aug 2014 (chris): add error code for AdminWS.changeUserPassword
 * 13 Jan 2016 (chris): add error code for SyncWS.updateStatus
 */

@SuppressWarnings("serial")
public class StatusCode extends Enum {
	/** The states idx. */
	private static Map<String, StatusCode> statesIdx = new HashMap<String, StatusCode>();

	/** The key. */
	private String key;
	/** The desc. */
	private String desc;
	
	/**
	 * Instantiates a new StatusCode.
	 *
	 * @param value the value
	 * @param key the key
	 */
	private StatusCode(String key, String desc) {
		super(key);
		this.key = key;
		this.desc = desc;
		statesIdx.put(this.key, this);
	}
	/** The Constant Unknown. */
	public static final StatusCode Unknown = new StatusCode("unknown", "Unknown Error");
	/** The Constant Success. */
	public static final StatusCode Success = new StatusCode("success", "Operation Success");
	/** The Constant Failed. */
	public static final StatusCode Failed = new StatusCode("failed", "Operation Failed");
	/** The Constant Error. */
	public static final StatusCode Error = new StatusCode("error", "Operation encountered with Error");

	/* TransactionWS */
	public static final StatusCode TRANWS_MANDATORY_FIELD_ERROR         = new StatusCode("TR-E1001", "Mandatory field is missing.");
	public static final StatusCode TRANWS_DATA_NOT_FOUND                = new StatusCode("TR-E1003", "Data is not found.");
	public static final StatusCode TRANWS_UNEXPECTED_ERROR              = new StatusCode("TR-E9999", "Unexpected Error Encountered.");
	
	/* SyncWS */
	public static final StatusCode SYNCWS_UPDATE_SUCCESS                = new StatusCode("SU-I1000", "Status Updated Successfully.");
	public static final StatusCode SYNCWS_MANDATORY_FIELD_ERROR         = new StatusCode("SU-E1001", "Mandatory field is missing.");
	public static final StatusCode SYNCWS_FIELD_LENGTH_EXCEEDED         = new StatusCode("SU-E1002", "Max length of field is reached.");
	public static final StatusCode SYNCWS_DATA_NOT_FOUND                = new StatusCode("SU-E1003", "Data is not found.");
	public static final StatusCode SYNCWS_PASSPORT_NOT_FOUND            = new StatusCode("SU-E1004", "Passport(s) not found.");
	public static final StatusCode SYNCWS_UNEXPECTED_ERROR              = new StatusCode("SU-E9999", "Unexpected Error Encountered.");
	
	/* ValidateWS */
	/** The Constant NIC_CHECK_PASSED. */
	public static final StatusCode VALWS_NIC_CHECK_PASSED = new StatusCode("NC-I1000", "Nic Eliblity Check Passed.");
	/** The Constant VALWS_MANDATORY_FIELD_ERROR. */
	public static final StatusCode VALWS_MANDATORY_FIELD_ERROR = new StatusCode("NC-E1001", "Mandatory field is missing.");
	/** The Constant VALWS_FAILED_WITH_ACTIVE_TXN. */
	public static final StatusCode VALWS_FAILED_WITH_ACTIVE_TXN = new StatusCode("NC-E1002", "Nic Eliblity Check Failed, Active Transaction(s) found.");
	/** The Constant VALWS_FAILED_WITH_ACTIVE_TXN. */
	public static final StatusCode VALWS_FIELD_LENGTH_EXCEEDED = new StatusCode("NC-E1003", "Max length of field is reached.");
	/** The Constant VALWS_UNEXPECTED_ERROR. */
	public static final StatusCode VALWS_UNEXPECTED_ERROR = new StatusCode("NC-E9999", "Unexpected Error Encountered.");
	
	/* TerminationWS */
	/** The Constant I500000 - OK. */
	public static final StatusCode TEMWS_OPERATION_COMPLETED = new StatusCode("I500000", "Ok");
	/** The Constant E500001 - Field <field name> is mandatory. */
	public static final StatusCode TEMWS_MANDATORY_FIELD_ERROR = new StatusCode("E500001", "Mandatory field is missing.");
	/** The Constant E500002 - Max length of field <field name> is reached. */
	public static final StatusCode TEMWS_FIELD_LENGTH_EXCEEDED = new StatusCode("E500002", "Max length of field is reached.");
	/** The Constant E500003 - No result. */
	public static final StatusCode TEMWS_NO_RESULT_ERROR = new StatusCode("E500003", "No result.");
	/** The Constant E999999 - An unexpected error occurred. */
	public static final StatusCode TEMWS_UNEXPECTED_ERROR = new StatusCode("E999999", "Unexpected Error Encountered.");
	

	
	/*Admin WS getAuthorizedFunctions*/

//	GF-I1000	Get Authorized Function Completed.
//	GF-E1001	Mandatory field is missing.
//	GF-E1002	Invalid field is inputted. 
//	GF-E1003	Max length of field is reached.
//	GF-E2001	User Account is invalid
//	GF-E2002	Workstation is invalid
//	GF-E2003	User Account is inactive.
//	GF-E2004	User is not authorized with the selected Site.
//	GF-E2101	Password Expired for the login user.
//	GF-E9999	Unexpected Error Encountered.
	
	public static final StatusCode COMPLETED = new StatusCode("GF-I1000", "Get Authorized Function Completed.");
	public static final StatusCode MANDATORY = new StatusCode("GF-E1001", "Mandatory field is missing.");
	public static final StatusCode INVALID = new StatusCode("GF-E1002", "Invalid field is inputted.");
	public static final StatusCode MAX_LENGTH = new StatusCode("GF-E1003", "Max length of field is reached.");
		
	public static final StatusCode USERACCOUNT_INVALID = new StatusCode("GF-E2001", "User Account is invalid.");
	public static final StatusCode WKSTN_INVALID = new StatusCode("GF-E2002", "Workstation is invalid.");
	public static final StatusCode USERACCOUNT_INACTIVE = new StatusCode("GF-E2003", "User Account is inactive.");
	public static final StatusCode SITECODE_MISMATCH = new StatusCode("GF-E2004", "User is not authorized with the selected Site.");
	
	public static final StatusCode PASSWORD_EXPIRED = new StatusCode("GF-E2101", "Password Expired for the login user.");
	public static final StatusCode ERROR = new StatusCode("GF-E9999", "Unexpected Error Encountered.");
	
	/* Admin WS changeUserPassword */

//	CP-I1000	Change Password Completed.
//	CP-E1001	Mandatory field is missing.
//	CP-E1002	Max length of field is reached.
//	CP-E2001	User Id is invalid
//	CP-E2002	Current Password is invalid
//	CP-E2003	New Password doesn't meet the Password Complexity Policy.
//	CP-E9999	Unexpected Error Encountered.
	/** The Constant CP-I1000 - Change Password Completed. */
	public static final StatusCode ADMWS_CP_OPERATION_COMPLETED = new StatusCode("CP-I1000", "Change Password Completed.");
	/** The Constant CP-E1001 - Field <field name> is mandatory. */
	public static final StatusCode ADMWS_CP_MANDATORY_FIELD_ERROR = new StatusCode("CP-E1001", "Mandatory field is missing.");
	/** The Constant CP-E1002 - Max length of field <field name> is reached. */
	public static final StatusCode ADMWS_CP_FIELD_LENGTH_EXCEEDED = new StatusCode("CP-E1002", "Max length of field is reached.");
	/** The Constant CP-E2001 - User Id is invalid. */
	public static final StatusCode ADMWS_CP_INVALID_USERID = new StatusCode("CP-E2001", "User Id is invalid.");
	/** The Constant CP-E2002 - Current Password is invalid. */
	public static final StatusCode ADMWS_CP_INVALID_CURRENT_PASSWORD = new StatusCode("CP-E2002", "Current Password is invalid.");
	/** The Constant CP-E2003 - New Password doesn't meet the Password Complexity Policy. */
	public static final StatusCode ADMWS_CP_INVALID_NEW_PASSWORD = new StatusCode("CP-E2003", "New Password doesn't meet the Password Complexity Policy.");
	/** The Constant CP-E9999 - An unexpected error occurred. */
	public static final StatusCode ADMWS_CP_UNEXPECTED_ERROR = new StatusCode("CP-E9999", "Unexpected Error Encountered.");
	/**
	 * Gets the single instance of ErrorCode.
	 *
	 * @param key the key
	 * @return single instance of ErrorCode
	 */
	public static StatusCode getInstance(Short key) {
		return (StatusCode) statesIdx.get(key);
	}

	/**
	 * Gets the all instances.
	 *
	 * @return the all instances
	 */
	public static Collection<StatusCode> getAllInstances() {
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

	/**
	 * Gets the description.
	 *
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * Equals.
	 *
	 * @param key the key
	 * @return true, if successful
	 */
	public boolean equals(String key) {
		if (this.key.equals(key)) {
			return true;
		} else {
			return false;
		}
	}
	
	/* custom utility */
	public com.nec.asia.nic.dx.common.FaultDetail convertToFaultDetail() {
		com.nec.asia.nic.dx.common.FaultDetail detail = new com.nec.asia.nic.dx.common.FaultDetail();
		detail.setCode(key);
		detail.setDetailMessage(desc);
		return detail;
	}
}
