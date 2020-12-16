package com.nec.asia.nic.statistic.controller;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nec.asia.nic.comp.trans.service.PersoService;
import com.nec.asia.nic.framework.report.service.AuditAccessLogService;

/**
 * @author setia
 *
 */

@Controller
@RequestMapping(value = "/sftpStatistic")
public class SftpStatisticController {
	
	private static final Logger logger = LoggerFactory.getLogger(SftpStatisticController.class);
	
	@Autowired
	private AuditAccessLogService auditAccessLogService;
	
	@Autowired
	private PersoService persoService;
	
	
	@RequestMapping(value="/view")
	@ExceptionHandler
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		
		long startTimeMs = System.currentTimeMillis();
		Throwable throwable = null;
		Map<String, Integer> folderStatMap = new HashMap<String, Integer> ();
		try {
			String functionName = "view";
			
			folderStatMap = persoService.getFolderStatistics();
			//Set<String> keyset = folderStatMap.keySet();
						
			long timeMs = System.currentTimeMillis() - startTimeMs;
			auditAccessLogService.saveAuditAccessLogForWeb(request, functionName, null, throwable, timeMs);
		} catch (Exception exp) {
			logMessage(exp);
		}
		

		modelMap.addAttribute("fnSelected", "Online SFTP Statistics");
		ModelAndView modelView = new ModelAndView("sftpStatistic.init", folderStatMap);
		return modelView;
	}
	
	private void logMessage(Exception ex) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		ex.printStackTrace(ps);
		logger.error(ex.getMessage() + " : \n" + new String(baos.toByteArray()));
	}
	
}
