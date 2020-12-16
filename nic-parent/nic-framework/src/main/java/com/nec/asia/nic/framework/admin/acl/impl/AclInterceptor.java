package com.nec.asia.nic.framework.admin.acl.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.nec.asia.nic.framework.Constants;
import com.nec.asia.nic.framework.admin.acl.AccessDeniedException;
import com.nec.asia.nic.framework.admin.acl.AclService;
import com.nec.asia.nic.framework.admin.acl.SessionTimeoutException;

/**
 * ACL intercepter for all MVC controllers Support Ant-style path patterns to
 * configure and match
 * 
 * It will only intercept the controllers which have the @Controller annotation
 * Once the access is denied, it will throw AccessDeniedException, and the MVC
 * framework can catch this exception and forward to login page.
 * 
 * revise log: 1. 01/02/2011 by bright: add ignore controllers support
 * 
 * @author bright_zheng
 * 
 */
public class AclInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = LoggerFactory
			.getLogger(AclInterceptor.class);

	/** auto wired the acl service */

	@SuppressWarnings("rawtypes")
	protected AclService service;

	/** ignore controllers' simple class name */
	private List<String> ignoreControllers;
	private List<String> ignoreURLs;
	/*
	 * error page
	 */
	private String errorPath;

	/** default constructor */
	public AclInterceptor() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		logger.trace("ACL interceptor executed");
		// only intercept for annotated business controllers
		Controller c = AnnotationUtils.findAnnotation(
				((HandlerMethod) handler).getBeanType(), Controller.class);
		String currentUri = request.getRequestURI();
		logger.debug("currentUri --- " + currentUri);

		/*
		 * if(request !=null && request.getSession()!=null){ HttpSession session
		 * = request.getSession();
		 * 
		 * logger.debug("request getRequestedSessionId: {}",
		 * request.getRequestedSessionId());
		 * logger.debug("request isRequestedSessionIdValid: {}",
		 * request.isRequestedSessionIdValid());
		 * logger.debug("session getId: {}", session.getId());
		 * logger.debug("session getCreationTime: {}",
		 * session.getCreationTime());
		 * logger.debug("session getMaxInactiveInterval: {}",
		 * session.getMaxInactiveInterval());
		 * logger.debug("session getLastAccessedTime: {}",
		 * session.getLastAccessedTime()); logger.debug("session isNew: {}",
		 * session.isNew());
		 * 
		 * if(!request.isRequestedSessionIdValid() &&
		 * request.getRequestedSessionId() != null && session.isNew()){ //
		 * Session timeout logger.debug("session timeout"); throw new
		 * SessionTimeoutException(); } }
		 */

		if (c != null
				&& (!isIgnoreController(((HandlerMethod) handler).getBeanType()
						.getSimpleName()) && !isIgnoreURL(currentUri)))
		// if(!isIgnoreURL(currentUri))
		{
			// logger.debug("current handler is @Controller annotated");

			Object resource = service.matchedResouce(
					service.getGrantedResources(request), currentUri);
			// if we can't match at least one resource, means current uri is not
			// granted for current user
			// then throw AccessDeniedException, and we should handle it by
			// configuration for such a checked exception
			if (resource == null) {
				// throw new AccessDeniedException();
				response.sendRedirect(request.getContextPath()+errorPath);
			}
			// put the current source to request so audit service can retrieve
			// it
			// request.setAttribute(Constants.CURRENT_RESOURCE, resource);
		}
		return true;
	}

	/**
	 * check whether current controller is in ignore list or not
	 * 
	 * @param currentControllerName
	 * @return
	 */
	protected boolean isIgnoreController(String currentControllerName) {
		if (ignoreControllers == null)
			return false;
		if (ignoreControllers.contains(currentControllerName)) {
			logger.debug("{} is in ignore list", currentControllerName);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @param URL
	 * @return
	 */
	protected boolean isIgnoreURL(String URL) {
		if (ignoreURLs == null)
			return false;
		for (String ignoreURL : ignoreURLs) {
			boolean isMatch = StringUtils.contains(URL, ignoreURL);
			// boolean isMatch = matcher.match(ignoreURL, URL);
			if (isMatch) {
				logger.debug("{} is in ignore list", URL);
				return true;
			}
		}
		return false;
	}

	/**
	 * Resolve the resource style Uri for example, a request Uri maybe
	 * http://host:port/servlet/hotels/search we just want /hotels/search only.
	 * 
	 * @param request
	 * @return
	 */
	protected String getResourceStyleUri(HttpServletRequest request) {
		return request.getPathInfo();
	}

	public List<String> getIgnoreControllers() {
		return ignoreControllers;
	}

	public void setIgnoreControllers(List<String> ignoreControllers) {
		this.ignoreControllers = ignoreControllers;
	}

	public List<String> getIgnoreURLs() {
		return ignoreURLs;
	}

	public void setIgnoreURLs(List<String> ignoreURLs) {
		this.ignoreURLs = ignoreURLs;
	}

	/**
	 * @return the errorPath
	 */
	public String getErrorPath() {
		return errorPath;
	}

	/**
	 * @param errorPath
	 *            the errorPath to set
	 */
	public void setErrorPath(String errorPath) {
		this.errorPath = errorPath;
	}

	/**
	 * @return the service
	 */
	@SuppressWarnings("rawtypes")
	public AclService getService() {
		return service;
	}

	/**
	 * @param service
	 *            the service to set
	 */
	public void setService(@SuppressWarnings("rawtypes") AclService service) {
		this.service = service;
	}

}
