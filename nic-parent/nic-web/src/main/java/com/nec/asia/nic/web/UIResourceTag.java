package com.nec.asia.nic.web;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.jstl.core.ConditionalTagSupport;

import com.nec.asia.nic.framework.admin.ApplicationScopeResourceLoaderService;
import com.nec.asia.nic.utils.ApplicationContextManager;

/**
 * UI resource tag
 * 
 * @author bright_zheng
 *
 */
public class UIResourceTag extends ConditionalTagSupport {
	private static final long serialVersionUID = -1635509771533862521L;
	/** user id */
	private String userId;
	/** UI resource code */
	private String resourceCode;
	
	private final ApplicationScopeResourceLoaderService applicationScopeResourceLoaderService
		= ApplicationContextManager
			.getBean("applicationScopeResourceLoaderService",ApplicationScopeResourceLoaderService.class);
	public UIResourceTag(){
		super();
	}
	
	@Override
	protected boolean condition() throws JspTagException {
		Object resource = applicationScopeResourceLoaderService.getUIResourceByUser(userId, resourceCode);
		if(resource!=null) {
			//true means the test is passed 
			//and the user gets the resource
			return true;
		}else{
			return false;
		}
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getResourceCode() {
		return resourceCode;
	}

	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}

}
