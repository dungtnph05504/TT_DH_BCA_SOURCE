package com.nec.asia.nic.framework.admin;

/**
 * application scope resource loader service 
 * for exporting code tables / UI resource etc. to application scope
 * 
 * but we don't put the dummy data to application scope directly
 * for avoiding non-refreshable 
 * 
 * @author bright_zheng
 *
 */
public interface ApplicationScopeResourceLoaderService {

	/**
	 * load all code types from db/cache
	 * 
	 * @return an array including all code types
	 */
	public String[] loadAllCodeTypes();
	
	/**
	 * get all code objects/beans filter by code type
	 * 
	 * @param codeType
	 * @return responding code objects/beans
	 */
	public Object[] getCodesByType(String codeType);
	
	
	/**
	 * get the UI resource by code from particular user's granted resources
	 * 
	 * @param userId
	 * @param workstationId
	 * @param resourceCode
	 * @return 
	 */
	public boolean getUIResourceByUserByWS(String userId, String workstationId, String resourceCode);
	
	/**
	 * get the UI resource by code from particular user's granted resources
	 * 
	 * @param userId
	 * @param workstationId
	 * @param resourceCode
	 * @return 
	 */
	public boolean getUIResourceByUser(String userId, String resourceCode);
	
	
}

