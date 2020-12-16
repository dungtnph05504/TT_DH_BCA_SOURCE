/**
 * 
 */
package com.nec.asia.nic.admin.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.nec.asia.nic.framework.controller.AbstractController;

/**
 * @author sailaja_chinapothula
 * 
 */
@Controller
@RequestMapping(value = "/nic")
public class NicIndexController extends AbstractController {

	private static final Logger logger = LoggerFactory
			.getLogger(NicIndexController.class);

	public NicIndexController() {
	}

	@RequestMapping(value="/welcome")
	@ExceptionHandler
	public ModelAndView welcome(WebRequest request,Model model) throws Exception {
		logger.debug("welcome!");	
		return new  ModelAndView("global.view.welcome");
	}
	
	@RequestMapping(value="/accessDenied")
	@ExceptionHandler
	public ModelAndView accessDenied(WebRequest request,Model model) throws Exception {
		logger.debug("welcome!");	
		return new  ModelAndView("global.view.accessDenied");
	}
}