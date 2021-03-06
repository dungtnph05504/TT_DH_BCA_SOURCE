package com.nec.asia.nic.utils;

import java.io.Serializable;

/**
 * Global constants for nric framework
 * 
 * @author bright_zheng
 *
 */
public class Constants implements Serializable {
	private static final long serialVersionUID = -6481886932797110737L;

	/** default page size */
	public static final int PAGE_SIZE_DEFAULT = 10;
	
	/** variable name for current user which should be stored in the session */
	public static final String CURRENT_USER = "current_user";
	
	/** variable name for granted resources of current user */
	public static final String CURRENT_USER_GRANTED_RESOURCES = "current_user_granted_resources";
	
	/** variable name for current hitted resource */
	public static final String CURRENT_RESOURCE = "current_resource";
	public static final String USERS_ID_ROLES_CODE = "NIC_INVESTIGATION";
	
	public static final String DOC_TYPE_USER_DECL = "SC_USER_DECL"; //Added by Sailaja on 24/09/2013
	
	public static final String ROLE_ID_NIC_LANHDAOPHEDUYET = "NIC_LANHDAOPHEDUYET";
	
	public static final String PARA_SCOPE_SYSTEM = "SYSTEM";
	
	public static final String PARA_LIST_HANDOVER = "COUNT_KEY_HANDOVER";
	
	public static final String PARA_PACKAGE_NEXT = "COUNT_PACKAGE_NEXT";
}
