package com.nec.asia.nic.web.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class XSSFilter implements Filter{
	
	private static final Logger logger = Logger.getLogger(XSSFilter.class);
	private boolean enableFilter = false;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String enableStr = filterConfig.getInitParameter("enableFilter");
		if ( enableStr!=null ) {
			this.enableFilter = new Boolean (enableStr);
		}
		logger.debug("Filter for XSSFilter enable:" + this.enableFilter );
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if ( this.enableFilter )
			chain.doFilter(new XSSRequestWrapper((HttpServletRequest) request), response);
		else chain.doFilter(request, response);
	}
}
