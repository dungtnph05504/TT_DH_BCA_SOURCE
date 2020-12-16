package com.nec.asia.nic.framework.admin.acl.impl;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.AntPathMatcher;

import com.nec.asia.nic.framework.admin.acl.AclService;

/**
 * 
 * The abstraction of ACL control service.
 * 
 * 
 * @author bright_zheng
 *
 */
public abstract class AbstractAclService<T> implements AclService<T> {
	
	/** use Spring AntPathMatcher as our default matching tool */
	private final AntPathMatcher matcher = new AntPathMatcher();

	public T matchedResouce(T[] resources, String currentUri){
		//return null if still not granted any resource, means no resource matched 
		if(resources==null) return null;
		T hit = null;
		for(T resource: resources){
			String toMatch = this.getResourceUri(resource);
			if(StringUtils.isBlank(toMatch))
				continue;
			boolean isMatch = currentUri.contains(toMatch);
			if(isMatch){
				hit = resource;
				break;
			}
		}
		return hit;
	}
	
	abstract public String getResourceUri(T resource);
	
	abstract public T[] getGrantedResources(HttpServletRequest request);
}
