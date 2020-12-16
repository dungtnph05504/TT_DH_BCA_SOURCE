package com.nec.asia.nic.framework.controller;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.*;
import org.springframework.web.servlet.*;

public class LoggingHandlerExceptionResolver 
implements HandlerExceptionResolver, Ordered {
    public int getOrder() {
        return Integer.MIN_VALUE; // we're first in line, yay!
    }

    public ModelAndView resolveException(
        HttpServletRequest aReq, HttpServletResponse aRes,
        Object aHandler, Exception anExc
    ) {
    	//Do something additional if required
    			ModelAndView modelAndView = new ModelAndView();
    			modelAndView.setViewName("error");
    			modelAndView.addObject("exception", anExc);
    			modelAndView.addObject("message", anExc.getLocalizedMessage());
    			modelAndView.addObject("messageStack", anExc.getMessage());
    			return modelAndView;
    }
}