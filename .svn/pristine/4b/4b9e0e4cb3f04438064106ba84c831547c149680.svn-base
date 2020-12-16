package com.nec.asia.nic.framework.admin.acl;

import javax.servlet.http.HttpServletRequest;

/**
 * ACL access control service interface
 * 
 * @author bright_zheng
 *
 */
public interface AclService<T> {
	
	/**
	 * verify current accessing uri if matches the resources granted.
	 * @param resources
	 * @param currentUri
	 * @return
	 */
	public T matchedResouce(T[] resources, String currentUri);

	/**
	 * retrieve the Uri from resource object
	 * @param resource
	 * @return
	 */
	public String getResourceUri(T resource);
	
	/**
	 * get resources granted to current user
	 * @param request
	 * @return
	 */
	public T[] getGrantedResources(HttpServletRequest request);
}
