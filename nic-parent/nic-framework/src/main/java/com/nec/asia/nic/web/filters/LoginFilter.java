package com.nec.asia.nic.web.filters;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nec.asia.nic.framework.admin.login.LoginInterceptor;
import com.nec.asia.nic.web.session.UserSession;

public class LoginFilter implements Filter{
	private String userSessionKey;
	private String loginUrl;
	private List<String> ignoreURLs = new LinkedList<String>();;
	private static Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
	
	public LoginFilter()  {
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.loginUrl = filterConfig.getInitParameter("loginURL");
		this.userSessionKey = filterConfig.getInitParameter("userSessionKey");
		
		String ignore = filterConfig.getInitParameter("ignoreURLs");
		if(StringUtils.isNotBlank(ignore))
		{
			String[] urls = ignore.split(",");
			for(String str: urls){
				ignoreURLs.add(str);
			}
		}
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String currentUri = req.getRequestURI();
		
		//[chris] to fix for language encoding issue 
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		if( isIgnoreURL(currentUri)){
			chain.doFilter(request, response);
			return;
		}
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = ((HttpServletRequest) request).getSession();
		UserSession userSession = (UserSession) session.getAttribute(userSessionKey);
		String path = req.getRequestURI().substring(req.getContextPath().length());
		if(!loginUrl.equals(path)){
			if(userSession==null){
				resp.sendRedirect(req.getContextPath()+loginUrl);
				return;
			}
		}
		chain.doFilter(request, response);
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
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
