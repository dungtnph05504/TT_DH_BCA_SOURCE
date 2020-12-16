package com.nec.asia.nic.util;

public class RegistrationConstants {
	public static final String HEADING_REG_MAIN = "REG_MAIN: REGISTRATION COUNTER";
	public static final String HEADING_NEW_REG = "REG_001N : NEW REGISTRATION";
	public static final String HEADING_UPDATE_PARTICULARS = "REG_001U : PARTICULAR'S UPDATE REGISTRATION";
	public static final String HEADING_UPDATE_PARTICULARS_SUMMARY = "REG_002U : UPDATE PARTICULARS REGISTRATION SUMMARY";
	public static final String HEADING_UPDATE_PARTICULARS_CONFIRM = "REG_003U : PARTICULARS UPDATE REGISTRATION CONFIRMATION";
	public static final String HEADING_UPDATE_PARTICULARS_VERIFICATION = "VER_001U : VERIFICATION for PARTICULARS UPDATE";
	public static final String HEADING_UPDATE_PARTICULARS_VERIFICATION_CONFIRM = "VER_002U : VERIFICATION CONFIRMATION for PARTICULARS UPDATE";
	
	public static final String HEADING_REP = "REG_001R : CARD REPLACEMENT REGISTRATION";
	public static final String HEADING_REP_SUMMARY = "REG_002R : CARD REPLACEMENT REGISTRATION SUMMARY";
	public static final String HEADING_REP_CONFIRM = "REG_003R : CARD REPLACEMENT REGISTRATION CONFIRMATION";
	public static final String HEADING_REP_VERIFICATION = "VER_001R : VERIFICATION for CARD REPLACEMENT";
	public static final String HEADING_REP_VERIFICATION_CONFIRM = "VER_002R : VERIFICATION CONFIRMATION for CARD REPLACEMENT";

	public static final String HEADING_SAVE_REG = "REG_002N: NEW REGISTRATION SUMMARY";

	public static final String REG_SUB_TYPE_CITIZEN = "REG_CITIZEN";
	public static final String REQ_INDICATOR_MANDATORY = "M";
	public static final String REQ_INDICATOR_OPTIONAL = "O";
	public static final String TRANSACTION_STAGE_PENDING_SUPERVISOR = "PEND_SUPERVISOR";
	public static final String TRANSACTION_STAGE_EXCEPTION_HANDLING = "EXC_HANDLING";
	public static final String CODE_ID_REASON = "REASON";
	public static final String CODE_ID_TRANSACTION = "TRANS_ID_SEQ";
	public static final String CODE_ID_GENDER = "GENDER";
	public static final String CODE_ID_MARITAL_STATUS = "MARITAL_STATUS";
	public static final String CODE_ID_TRANS_OTHER_SUBTYPES = "TRANS_OTHER_SUBTYPES";

	public static final String CODE_ID_TRANSACTION_TYPE = "TRANSACTION_TYPE";
	public static final String CODE_ID_TRANSACTION_SUBTYPE = "TRANSACTION_SUBTYPE";
	public static final String CODE_ID_TRANSACTION_STAGE = "TRANSACTION_STAGE";
	public static final String CODE_ID_TRANSACTION_STATUS = "TRANSACTION_STATUS";
	public static final String CODE_ID_REASON_FWD_TO_SUPERVISOR = "REASON_FWD_SUPERVISOR";
	public static final String CODE_ID_REASON_REJECT_ISSUANCE = "REASON_ISS_REJECT";

	public static final String DOC_TYPE_ENCODED_FACE = "PH_CHIP";
	public static final String DOC_TYPE_ENROLLED_FACE = "PH_CAP";
	public static final String DOC_TYPE_PRINTED_FACE = "PH_GREY";
	public static final String DOC_TYPE_THUMBNAIL_FACE = "TH_CAP";
	public static final String DOC_TYPE_THUMBNAIL_GREY_FACE = "TH_GREY";
	public static final String DOC_TYPE_THUMBNAIL_CHIP_FACE = "TH_CHIP";
	public static final String DOC_TYPE_SMALL_PORTRAIT_FACE = "PH_CLI";
	public static final String DOC_TYPE_SCAN_DOC_BIRTH_CERT = "SC_BIRTH_EXT";
	public static final String DOC_TYPE_SCAN_DOC_MARRIAGE_CERT = "SC_MAR_CERT";
	public static final String DOC_TYPE_FINGER_PRINT = "FP";
	public static final String DOC_TYPE_FINGER_PRINT_SIGN_FP = "SIGN_FP";
	public static final String DOC_TYPE_SIGN_IMAGE = "SIGNATURE";
	public static final String DOC_TYPE_USER_DECL = "SC_USER_DECL";
	public static final String TRANSACTION_STAGE_CANCEL = "REG_CANCELLED";
	public static final String TRANSACTION_STAGE_CANCEL_ISSUANCE = "CANCEL_ISSUANCE";
	public static final String HEADING_EXCEPTION_HANDLING_MAIN = "EXP_MAIN : EXCEPTION HANDLING COUNTER";
	public static final String TRANSACTION_STAGE_CARD_ISSUED = "CARD_ISSUED";
	public static final String TRANSACTION_STAGE_CARD_REJECTED = "CARD_REJECTED";
	public static final String TRANSACTION_STAGE_ISSUANCE = "ISSUANCE";
	public static final String HEADING_VER_MAIN = "VER_MAIN: VERIFICATION COUNTER";

	public static final String TRANSACTION_STATUS_REG_DRAFT = "RIC_NEW";
	public static final String TRANSACTION_STATUS_RIC_CANCELLED = "RIC_CANCELLED";
//	public static final String TRANSACTION_STATUS_VER_DRAFT = "VER_DRAFT";
	public static final String TRANSACTION_STATUS_REG_COMPLETE = "RIC_REGISTERED";
	public static final String TRANSACTION_STATUS_VER_COMPLETE = "RIC_VERIFIED";
	public static final String TRANSACTION_STATUS_RIC_UPLOADED = "RIC_UPLOADED";
	public static final String TRANSACTION_STATUS_RIC_CARD_RECEIVED = "RIC_CARD_RECEIVED";
	public static final String TRANSACTION_STATUS_RIC_CARD_ISSUED = "RIC_CARD_ISSUED";
	public static final String TRANSACTION_STATUS_RIC_CARD_REJECTED = "RIC_CARD_REJECTED";
	public static final String TRANSACTION_STAGE_REJECTED = "REJECTED";
	public static final String TRANSACTION_STAGE_REGISTRATION = "REGISTRATION";
	public static final String TRANSACTION_STAGE_VERIFICTION = "VERIFICATION";
	public static final String TRANSACTION_STAGE_COMPLETE = "COMPLETE";
	public static final String TRANSACTION_STAGE_NIC = "NIC";
	public static final String EXCPTION_HANDLING_REASON_CODE = "REASON_FWD_EXCEPTION";
	public static final String SUPERVISOR_REASON_CODE = "REASON_FWD_SUPERVISOR";
	public static final String CANCELLATION_REASON_CODE = "REASON_CANCEL";
	public static final String SUPERVISOR_DECISION_CODE = "SUPERVISOR_DECISION";
	
	public static final String CARDISSUANCE_CARD_ISSUED = "ISSUED";
	public static final String CODE_DISTRICT = "DISTRICT";
	public static final String CODE_TOWN = "TOWN_VILLAGE";

	/**
	 * SUPERVISOR Constants
	 */
	public static final String HEADING_SUPER_MAIN = "SUPER MAIN: MAIN SUPERVISOR COUNTER";
	public static final String HEADING_SUPER_REG = "SUPER_REG001N:  Supervisor - Registration";
	public static final String HEADING_SUPER_VER = "SUPER_VER001N:  Supervisor - Verification";
	public static final String COUNTER_TYPE_SUPERVISOR = "SUPERVISOR";
	public static final String HEADING_SOD_VERIFY = "VER_SOD: Card Chip Signature Verification";
	public static final String TRANSACTION_TYPE_OTHERS = "OTHERS";

	/*
	 * VERIFICATION USER DECLARATION STATEMENT
	 */
	public static final String USER_DECLARATION_STATEMENT = "I declare that the particulars stated in this application and the documents submitted with it are all true and correct to the best of my knowledge and belief, and"
			+ " that I have not willfully suppressed any material fact. I "
			+ "hereby give my consent to the use of the same set of fingerprints"
			+ " and photograph, submitted in respect of my application for the"
			+ " purpose of Identify Card registration, if applicable. I"
			+ " also hereby give my consent for your department to obtain and"
			+ " verify the information provided by me in respect of this"
			+ " application, from or with any source, as you deem appropriate for "
			+ "the purpose of assessment of my application for Identify Card.";
	public static final String USER_DECLARATION_STATEMENT1 = " * Please be informed that this captured signature will be"
			+ " printed on the ID Card and will be treated as a form of formal acceptance of this application  ";
	//Added 19/8/2013 priya start
	public static final String USER_DECLARATION_STATEMENT_1 = "I declare that I am a citizen of Mauritius and that the particulars stated in this application and the documents submitted with it are all true and correct, and that I have not willfully concealed any material fact.";
	public static final String USER_DECLARATION_STATEMENT_2 = "I am aware that in case I have supplied any false information/document, I am liable to prosecution and any National Identity Card issued to me will be cancelled.";
	
	public static final String USER_DECLARATION_STATEMENT_3 = "I hereby give my consent to the use of the same set of fingerprints and photograph, submitted in respect of my application for the purpose of National Identify Card registration and for the verification of my identity.";
	public static final String USER_DECLARATION_STATEMENT_4 = "NOTE: This captured signature or thumbprint will be printed on this National Identity Card Registration Form and will be your formal acceptance of this registration.";
	public static final String USER_DECLARATION_STATEMENT_4_PAR_UPD = "NOTE: This captured signature or thumbprint will be your formal acceptance of this registration.";
	//Added 19/8/2013 priya end

	/*
	 * UPDATE PARTICULARS USER DECLARATION STATEMENT
	 */
	public static final String USER_DECLARATION_STATEMENT_UPDATE = " * Please be informed that this captured signature will be"
			+ " treated as a form of formal acceptance of this application  ";
	/*
	 * REGISTRATION SUCCESS MSGS
	 */
	public static final String UPDATE_PARTICULAR_VERIFICATION_SUCCESS_MSG_HEADING="Particular Update Successful";
	public static final String SAVE_REGISTRATION_SUCCESS_MSG_HEADING = "Registration Saved";
	public static final String SAVE_REGISTRATION_SUCCESS_MSG = "Successfully Saved the Registration Information as Draft.";
	public static final String CONFIRM_REGISTRATION_SUCCESS_MSG_HEADING = "Registration Completed";
	public static final String CONFIRM_REGISTRATION_SUCCESS_MSG = "Successfully Completed the Registration Process.";
	public static final String HEADING_NEW_VER = "VER_001N : VERIFICATION for NEW REGISTRATION";
	public static final String HEADING_NEW_VER_CONFIRM = "VER_002N : VERIFICATION CONFIRMATION for NEW REGISTRATION";
	
	
	public static final String CONFIRM_VERIFICATION_SUCCESS_MSG_HEADING = "Verification Successful";
	public static final String CONFIRM_APPROVAL_REGISTRATION_SUCCESS_MSG_HEADING = "Approved";
	public static final String CONFIRM_APPROVAL_REGISTRATION_SUCCESS_MSG = "Successfully forwarded to registration counter.";
	public static final String CONFIRM_APPROVAL_CARDISSUANCE_SUCCESS_MSG = "Successfully forwarded to CardIssue counter.";
	public static final String CONFIRM_APPROVAL_REGISTRATION_HEADING_REG_MAIN = "REG_003N : New Registration Confirmation";
	public static final String CONFIRM_APPROVAL_FWDSUPERVISOR_HEADING_REG_MAIN = "SUPER_APP : Supervisor Confirmation";
	public static final String CONFIRM_APPROVAL_FWDSUPERVISOR_SUCCESS_MSG_HEADING="Forward to Supervisor Successful";
	public static final String CONFIRM_APPROVAL_FWDSUPERVISOR_SUCCESS_MSG="Successfully forwarded to Supervisor counter."; 
	public static final String CONFIRM_APPROVAL_EXCOFCR_HEADING_REG_MAIN = "EXC_OFCR : Exception Handling Confirmation";
	public static final String CONFIRM_APPROVAL_EXCOFCR_SUCCESS_MSG_HEADING="Forward to Exception Officer Successful";
	public static final String CONFIRM_APPROVAL_EXCOFCR_SUCCESS_MSG="Successfully forwarded to Exception counter."; 
	public static final String MODEL_NAME_QUEUE = "QUEUE";
	// public static final String PARA_NAME_QUEUE_MODULE_FLG_PAYMENT =
	// "QUEUE_MODULE_FLG_PAYMENT";
	// public static final String PARA_NAME_QUEUE_MODULE_FLG_REGISTRATION =
	// "QUEUE_MODULE_FLG_REGISTRATION";
	// public static final String PARA_NAME_QUEUE_MODULE_FLG_VERIFICATION =
	// "QUEUE_MODULE_FLG_VERIFICATION";
	// public static final String PARA_NAME_QUEUE_MODULE_FLG_EXCEPTION =
	// "QUEUE_MODULE_FLG_EXCEPTION";
	// public static final String PARA_NAME_QUEUE_MODULE_FLG_SUPERVISOR =
	// "QUEUE_MODULE_FLG_SUPERVISOR";
	// public static final String PARA_NAME_QUEUE_MODULE_FLG_CAR_ISS =
	// "QUEUE_MODULE_FLG_CAR_ISS";
	// public static final String PARA_NAME_QUEUE_RICSITE_FLG =
	// "QUEUE_RICSITE_FLG";//only 1 is required as each RIC site has its own db
	// public static final String PARA_SCOPE_SYSTEM ="SYSTEM";
	// public static final String PARA_NAME_NO_OF_WORKING_DAYS =
	// "NO_OF_WORKING_DAYS"; //no of working days for collection slip
	// public static final String PARA_NAME_SYSTEM_SITE_CODE =
	// "SYSTEM_SITE_CODE";

	/*
	 * User declaration for title
	 */
	public static final String USER_DECLARATION_TITLE_NEW_REG = "Declaration Form For New Registration";
	public static final String USER_DECLARATION_TITLE_REPLACEMENT = "Declaration Form For Replacement";
	public static final String USER_DECLARATION_TITLE_PAR_UPD = "Declaration Form For Particulars Update";

	public static final String MODEL_NAME_TRANSACTION_SUBTYPE_MAP = "TRANSACTION_SUBTYPE_MAP";
	public static final String MODEL_NAME_TRANSACTION_TYPE_LIST = "TRANSACTION_TYPE_LIST";

	public static final String TRANSACTION_TYPE_REG = "REG";
	public static final String TRANSACTION_TYPE_REP = "REP";
	public static final String TRANSACTION_TYPE_PAR_UPD = "PAR_UPD";
	public static final String TRANSACTION_TYPE_REP_CHG = "CHG";
	public static final String REG_TYPE_NEW_REG = TRANSACTION_TYPE_REG;
//	public static final String TRANSACTION_STATUS_REG_NEW = TRANSACTION_TYPE_REG;

	public static final String TRANSACTION_STAGE_PAYMENT = "PAYMENT";
	public static final String QUEUE_STATUS_ACTIVE = "ACTIVE";
	public static final String QUEUE_STATUS_FINISHED = "FINISHED";
	public static final String CODE_QUEUE_VALUE_REG = "REG";
	public static final String CODE_QUEUE_VALUE_VER = "VER";
	public static final String CODE_QUEUE_VALUE_ISS = "ISS";
	public static final String CODE_QUEUE_VALUE_SUP = "SUP";
	public static final String CODE_QUEUE_VALUE_EXC = "EXC";
	public static final String CODE_QUEUE_VALUE_PAY = "PAY";
	public static final String CODE_QUEUE_ID 		 = "QUEUE_ID";
	
	//Card Status
	public static final String CARD_STATUS_ACTIVE_ID = "ACTIVE";
	public static final String CARD_STATUS_SUSPENDED_ID = "SUSPENDED";
	public static final String CARD_STATUS_TERMINATED_ID = "TERMINATED";
	
	public static final String RIC_SITE_CODE = "SITE_CODE";
	public static final String CODE_ID_COLLECTION_SLIP_ID = "COLL_SLIP_ID_SEQ";
	
	public static final String TRANSACTION_SUBTYPE_LOST = "REP_LOST";
	public static final String TRANSACTION_SUBTYPE_DAMAGE = "REP_DAMAGE";
	public static final String CODE_ID_SITE_CODE = "SITE_CODE";
	public static final String CODE_ID_COUNTRY = "COUNTRY";
	
	public static final String GRACE_PERIOD_MSG="Grace Period Checking Failed, 6 months elapsed.";
	public static final String ALL_FINGERS_MUST_BE_CAPTURED="All the Fingerprints must be captured.";
	public static final String ALL_FINGERS_MUST_BE_ACCEPTABLE_QUALITY="All Captured Fingers Must Be Of Good Or Acceptable Quality.";
	public static final String TRANSACTION_SELECTION_ERROR_MSG="There is a Transaction exists already for this NIN with Transaction Type :";
	public static final String FINGERPRINT_VERIFICATION_FAILDED="1:1 Fingerprint verification not done.";
	public static final String CARD_VERIFICATION_FAILDED="Card not Verified.";
	public static final String SUPERVISOR_DECISION_GRACE_PERIOD_CODE="0201";
	public static final String SUPERVISOR_DECISION_FULL_AMPUTATE="0001";
	public static final String SUPERVISOR_DECISION_BAD_QUALITY_FP="0002";
	public static final String SUPERVISOR_DECISION_BAD_FACIAL_QUALITY="0101";
	public static final String SUPERVISOR_DECISION_BYPASS_FPMINUTIA_DB_VERIFY="1001";
	public static final String SUPERVISOR_DECISION_BYPASS_FPMINUTIA_CARD_VERIFY="1002";
	public static final String PENDING_WITH_SUPERVISOR="This Registration is Currently Pending with Supervisor.";
	public static final String PENDING_WITH_EXCEPTION_OFFICER="This Registration is Currently Pending with Exception Handling Officer .";
	public static final String DOCUMENT_TYPE_SC_OTHER="SC_OTH_";
	
	
	public static final String VIEW_JSP_NAME_REGISTRATION_MAIN="show.registration.main";
	public static final String VIEW_JSP_NAME_REGISTRATION_NEW_DTL="show.new.registration";
	public static final String NIN_NOT_FOUND = "NIN Does Not Exist.";
	public static final String TRANSACTION_SUBTYPE_REG_CITIZEN = "REG_CITIZEN";
	
	public static final String CODE_ID_CARD_STATUS = "CARD_STATUS";
	
	public static final String PAYMENT_STATUS_PAID = "PAID";
	public static final String PAYMENT_STATUS_PENDING = "PENDING";
	public static final String PAYMENT_STATUS_UNPAID = "UNPAID";
	public static final String PAYMENT_STATUS_WAIVED = "WAIVED";
	
	public static final String CODE_ID_WAIVER_REASON = "WAIVER_REASON";
	
	public static final String NUMBER_1="01";
	public static final String NUMBER_2="02";
	public static final String NUMBER_3="03";
	public static final String NUMBER_4="04";
	public static final String NUMBER_5="05";
	public static final String NUMBER_6="06";
	public static final String NUMBER_7="07";
	public static final String NUMBER_8="08";
	public static final String NUMBER_9="09";
	public static final String NUMBER_10="10";
	
	public static final String SIGNATURE_INDICATOR_SIGN="S";
	public static final String SIGNATURE_INDICATOR_FP="T";
	public static final String SIGNATURE_INDICATOR_BLANK="B";
	

	//AFIS CHECK CONSTANTS
		public static final String HEADING_AFIS_MAIN = "AFIS_MAIN : RIC AFIS VERIFICATION ";
	
	public static final String TRANSACTION_SUBTYPE_REP_NAME_CHG_MAR = "REP_NAME_CHG_MAR";
	public static final String GENDER_MALE = "M";
	public static final String GENDER_FEMALE = "F";
	public static final String MARITAL_STATUS_MARRIED = "M";
	public static final String NOT_MARRIED_MSG="Applicant is not Married.";
	public static final String OPTION_SURNAME_OWN_SPOUSE = "OWN_SPOUSE";
	public static final String OPTION_SURNAME_SPOUSE_OWN = "SPOUSE_OWN";
	public static final String OPTION_SURNAME_OWN = "OWN_SURNAME";
	public static final String OPTION_SURNAME_SPOUSE = "SPOUSE_SURNAME";
	
	public static final String HEADING_AFIS_1TON_PAGE = "AFIS_MAIN : RIC AFIS VERIFICATION (1:N Matching)";
	
	public static final int NAME_STRING_MAX_LENGTH=40;
	//EUNIKE START
	public static final int NAME_STRING_MAX_WIDTH=215;
	public static final int NAME_BIRTH_STRING_MAX_WIDTH=255;
	//EUNIKE END
	
	public static final String NO_CARD_LOST = "NO_CARD_LOST";
	
	
	/** The Constant PORTRAIT_WIDTH_DEFAULT for Encoded. */
	public final static int ENCODE_FACE_WIDTH_DEFAULT = 240;

	/** The Constant PORTRAIT_HEIGHT_DEFAULT for Encoded. */
	public final static int ENCODE_FACE_HEIGHT_DEFAULT = 320;

	/** The Constant PORTRAIT_WIDTH_DEFAULT for Encoded. */
	public final static int FACE_WIDTH_DEFAULT = 480;

	/** The Constant PORTRAIT_HEIGHT_DEFAULT for Encoded. */
	public final static int FACE_HEIGHT_DEFAULT = 640;

	/** The Constant PORTRAIT_WIDTH_DEFAULT for Encoded. */
	public final static int SMALL_CLIP_FACE_WIDTH_DEFAULT = 96;

	/** The Constant PORTRAIT_HEIGHT_DEFAULT for Encoded. */
	public final static int SMALL_CLIP_FACE_HEIGHT_DEFAULT = 128;

	public final static long FILE_SIZE_LIMIT = 12 * 1024;
	
	//PRIYA START
		/** The Constant PORTRAIT_WIDTH_DEFAULT for Encoded. */
	public final static int THUMBNAIL_FACE_WIDTH_DEFAULT = 120;

		/** The Constant PORTRAIT_HEIGHT_DEFAULT for Encoded. */
	public final static int THUMBNAIL_FACE_HEIGHT_DEFAULT = 160;
		
		/** The Constant PORTRAIT_WIDTH_DEFAULT for Encoded. */
	public final static int THUMBNAIL_ENCODED_FACE_WIDTH_DEFAULT = 120;

		/** The Constant PORTRAIT_HEIGHT_DEFAULT for Encoded. */
	public final static int THUMBNAIL_ENCODED_FACE_HEIGHT_DEFAULT = 160;
	
	public static final String TRANSACTION_STATUS_RIC_PEND_PAYMENT = "RIC_PEND_PAYMENT";
	
	public static final String SUPERVISOR_DECISION_1TO1_VERIFICATION_CODE="0401";
	public static final String SUPERVISOR_DECISION_CARD_VERIFICATION_CODE="0402";
	public static final String SUPERVISOR_DECISION_FACIAL_QUALITY_CODE="0101";
	
	public static final String SUPERVISOR_DECISION_MANDATORY_DOC_ADDR_PROOF_DOC_CODE="0501";
	public static final String SUPERVISOR_DECISION_MANDATORY_DOC_BIRTH_CERT_CODE="0502";
	public static final String SUPERVISOR_DECISION_MANDATORY_DOC_MAR_CERT_CODE="0503";
	public static final String SUPERVISOR_DECISION_MANDATORY_DOC_PAR_BIRTH_CERT_CODE="0504";
	public static final String SUPERVISOR_DECISION_MANDATORY_DOC_PAR_ID_CARD_CODE="0505";
	public static final String SUPERVISOR_DECISION_MANDATORY_DOC_PASSPORT_CODE="0506";
	public static final String SUPERVISOR_DECISION_MANDATORY_DOC_PMO_LETTER_CODE="0507";
	
	public static final String DOC_ID_ADDR_PROOF_DOC = "ADDR_PROOF_DOC"; 
	public static final String DOC_ID_BIRTH_CERT = "BIRTH_CERT";
	public static final String DOC_ID_MAR_CERT = "MAR_CERT"; 
	public static final String DOC_ID_PAR_BIRTH_CERT = "PAR_BIRTH_CERT";
	public static final String DOC_ID_PAR_ID_CARD = "PAR_ID_CARD";
	public static final String DOC_ID_PASSPORT = "PASSPORT";
	public static final String CODE_ID_PASSPORT = "PASSPORT_TYPE";
	public static final String DOC_ID_PMO_LETTER = "PMO_LETTER";

	
	public static final String DOC_TYPE_COL_SLIP = "REF_COL_SLIP";
	
	public static final String MANDATORY_DOC_PREFIX = "REG_ALL_DOC_";
	
	public static final String CODE_ID_DISTRICT = "DISTRICT";
	public static final String CODE_ID_TOWN = "TOWN_VILLAGE";
	
	public static final String CODE_ID_BirthPlace = "CODE_BirthPlace";
	
	public static final String TRANSACTION_STATUS_RIC_CARD_EXPIRED = "RIC_CARD_EXPIRED";
	public static final String TRANSACTION_STATUS_RIC_CARD_RETURNED = "RIC_CARD_RETURNED";
	public static final String CODE_ID_REASON_NIC_INV_REJECT="REASON_NIC_INV_REJ";
	public static final String USERS_ID_BY_ROLES_CODE = "NIC_INVESTIGATION";
	public static final String CODE_RELIGION_CODE_ID = "CODE_RELIGION";
	public static final String CODE_NATION_CODE_ID = "CODE_NATION";
	public static final String CODE_IDPlace_CODE_ID = "CODE_IDPlace";
	public static final String MARITAL_STATUS_CODE_ID = "MARITAL_STATUS";
	public static final String CODE_JOB_CODE_ID = "CODE_JOB";
	public static final String NATIONALITY_CODE_ID = "NATIONALITY";
	public static final String CODE_IDGovernment_CODE_ID = "CODE_IDGovernment";
	public static final String CODE_SIGNER_GOV_CODE_ID = "CODE_SIGNER_GOV";
	public static final String CODE_TripPurpose_CODE_ID = "CODE_TripPurpose";
	public static final String CODE_TripCost_CODE_ID = "CODE_TripCost";
	public static final String CODE_PRIORITY_CODE_ID = "PRIORITY";
	public static final String DETAULT_START_KHOP_CA_NHAN = "[KHOP_CA_NHAN]: TRUNG";
	public static final String DETAULT_START_TRACUU_DT = "[TRA_CUU_DOI_TUONG]: TRUNG";
	public static final String DETAULT_START_TRACUU_CMND = "[TRA_CUU_CHUNG_MINH_NHAN_DAN]:";
	public static final String DETAULT_START_KHOP_CA_NHAN_N = "[KHOP_CA_NHAN]: KHONG TRUNG";
	public static final String DETAULT_START_TRACUU_DT_N = "[TRA_CUU_DOI_TUONG]: KHONG TRUNG";
	public static final String DETAULT_START_TRACUU_CMND_N = "[TRA_CUU_CHUNG_MINH_NHAN_DAN]: KHONG TRUNG";
}
