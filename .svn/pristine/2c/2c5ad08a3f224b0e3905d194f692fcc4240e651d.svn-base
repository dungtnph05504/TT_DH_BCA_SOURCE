/**
 * 
 */
package com.nec.asia.nic.framework.admin.login;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.nec.asia.nic.web.session.UserSession;

/**

 * @author Ambrocio,Paulo Johannes D.
 * @Company: NEC ASIA PACIFIC PTE
 * @since : Jul 4, 2013
 * <p>
 *	Intercept request to check if a UserSession object exist in session
 * </p>
 * 
 *
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
	private static Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
	private String userSessionKey;
	private String loginURL;
	private List<String> ignoreControllers;
	private List<String> ignoreURLs;
	
	
	/**
	 * 
	 */
	public LoginInterceptor() {
		// TODO Auto-generated constructor stub
	}



	public String getUserSessionKey() {
		return userSessionKey;
	}



	public void setUserSessionKey(String userSessionKey) {
		this.userSessionKey = userSessionKey;
	}



	public String getLoginURL() {
		return loginURL;
	}



	public void setLoginURL(String loginURL) {
		this.loginURL = loginURL;
	}



	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		//Controller c = AnnotationUtils.findAnnotation(((HandlerMethod)handler).getBeanType(), Controller.class);
		String currentUri = this.getResourceStyleUri(request);
		
		if(isIgnoreController(((HandlerMethod)handler).getBeanType().getSimpleName()) || isIgnoreURL(currentUri))
			return true;
		
		HttpSession session = request.getSession();
		UserSession userSession = (UserSession) session.getAttribute(userSessionKey);
		String path = request.getRequestURI().substring(request.getContextPath().length());
		if(!loginURL.equals(path)){
			if(userSession==null){
				response.sendRedirect(request.getContextPath()+loginURL);
				return false;
			}
		}
		return true;
	}
	
	protected String getResourceStyleUri(HttpServletRequest request){
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
	 * check whether current controller is in ignore list or not
	 * 
	 * @param currentControllerName
	 * @return
	 */
	protected boolean isIgnoreController(String currentControllerName){
		if (ignoreControllers==null) return false;
		if (ignoreControllers.contains(currentControllerName)){
			logger.debug("{} is in ignore list", currentControllerName);
			return true;
		}else{
			return false;
		}		
	}
	
	/**
	 * 
	 * @param URL
	 * @return
	 */
	protected boolean isIgnoreURL(String URL){
		if (ignoreURLs==null) return false;
		for(String ignoreURL: ignoreURLs){
			boolean isMatch = StringUtils.contains(URL, ignoreURL);
			//boolean isMatch = matcher.match(ignoreURL, URL);
			if(isMatch){
				logger.debug("{} is in ignore list", URL);
				return true;
			}
		}
		return false;
	}
	

}
