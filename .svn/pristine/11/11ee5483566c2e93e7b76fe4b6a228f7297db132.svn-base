/**
 * 
 */
package com.nec.asia.nic.admin.pkd.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.bcel.classfile.Code;
import org.apache.commons.lang.StringUtils;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.hibernate.criterion.Order;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
import com.nec.asia.nic.framework.admin.code.domain.Codes;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.domain.ParametersId;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.framework.admin.code.service.ParametersService;
import com.nec.asia.nic.framework.report.form.CodeValueForm;
import com.nec.asia.nic.framework.report.service.AuditAccessLogService;
import com.nec.asia.nic.util.Constants;
import com.nec.asia.nic.util.pagingUtil;
import com.nec.asia.nic.utils.DateUtil;
import com.nec.asia.nic.web.session.UserSession;


/**
 * @author prasad_karnati
 *
 */

/* 
* Modification History:
*  
* 08 Oct 2013 (Chris Lai):Update to set the ParentCodeValue
* 
* 13 Dec 2013 (Chris Lai) : Add in Audit Log
* 
*/

@Controller
@RequestMapping(value = "/pkdCtrl")
public class PkdController {
	
	private static final Logger logger = LoggerFactory.getLogger(PkdController.class);
	
	private static String ASSENDING_ORDER="1";
	private static String DECENDING_ORDER="2";
	@Autowired
	private CodesService codesService;
	
	@Autowired
	private ParametersService parametersService;
	
	@Autowired
	private AuditAccessLogService auditAccessLogService;
	
	//[tuenv][13 Jan 2016] - start
	//@RequestMapping(value = "/view", method = RequestMethod.GET)	
	@RequestMapping(value = "/view")
	@ExceptionHandler
	public ModelAndView getPkd(WebRequest request, Model model) throws Exception {	
		String html = "";
		Parameters param = parametersService.getParamDetails(Parameters.SCOPE_SYSTEM, "URL_PKD");
		if(param != null){
			File dirParent = new File(param.getParaLobValue());
			html = AddNodeAndDescendents(dirParent);
		}
		model.addAttribute("fnSelected", Constants.HEADING_NIC_PKD);
		model.addAttribute("pkdHtml", html);
		return  new ModelAndView("pkd.pkdView");
	}
	
	private String AddNodeAndDescendents(File folder)
    {
		 //File subFolders = folder.getParentFile();
         String node = "<span url=\""+ folder.getPath() + "\">" + folder.getName() + "</span><ul role=\"group\">";
         
	         for (File subFolder : folder.listFiles())
	         {
	        	 if(subFolder.isDirectory()){
		             node += "<li role=\"treeitem\" aria-expanded=\"false\" tabindex=\"-1\">";
		             String child = AddNodeAndDescendents(subFolder);
		             node += child;
		             node += "</li>";
	        	 }
	             else{
	    	         node += "<li role=\"treeitem\" class=\"doc\" tabindex=\"- 1\">";
	    	         node += subFolder.getName();
	    	         node += "<input type=\"checkbox\" id=\"flagcheck\" name=\"objid\" style=\"float:right\" value=\"" + folder.getPath() + "\\" + subFolder.getName() + "\"></li>";
	             }
	         }
         node += "</ul>";
         return node;
    }
	// Chris Lai (13 Dec 2013) : Add in Audit Log
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ExceptionHandler
	public @ResponseBody String save(@RequestParam("imgupload") MultipartFile multipartFile, @RequestParam("ffocus") String ffocus) throws Exception {
		String status = "error";
		try {
			List<String> urls = Arrays.asList(ffocus);
			List<MultipartFile> files = Arrays.asList(multipartFile);
			for (MultipartFile file : files) {
	            if (file.isEmpty()) {
	                continue; //next pls
	            }
	            if (urls.size() <= 0){
		            Parameters param = parametersService.getParamDetails(Parameters.SCOPE_SYSTEM, "URL_PKD");
		    		if(param != null){
			            byte[] bytes = file.getBytes();
			            Path path = Paths.get(param.getParaLobValue() + "\\" + file.getOriginalFilename());
			            Files.write(path, bytes);
			            status = "success";
		    		}
	            } else {
	            	byte[] bytes = file.getBytes();
		            Path path = Paths.get(urls.get(0) + "\\" + file.getOriginalFilename());
		            Files.write(path, bytes);
		            status = "success";
	            }
	        }
		} catch(Exception exp){
		}
		
		return status;		
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ExceptionHandler
	public @ResponseBody String delete(@RequestParam("ids") String[] urls) throws Exception {
		String status = "error";
		try {
			
			List<String> files = Arrays.asList(urls);
			for (String file : files) {
				File file_ = new File(file);
				Files.deleteIfExists(file_.toPath());
	            status = "success";
	        }
		} catch(Exception exp){
		}
		
		return status;		
	}
	
	@RequestMapping(value = "/deleteF", method = RequestMethod.POST)
	@ExceptionHandler
	public @ResponseBody String deleteF(@RequestBody String yourArray) throws Exception {
		JSONObject onb = new JSONObject(yourArray);
		String status = "error";
		try {
			String ffocus = (onb.has("ffocus") && !onb.isNull("ffocus")) ? onb.getString("ffocus") : "";
			File file_ = new File(ffocus);
			file_.delete();
	        status = "success";
		} catch(Exception exp){
		}
		
		return status;		
	}
	
	@RequestMapping(value = "/addF", method = RequestMethod.POST)
	@ExceptionHandler
	public @ResponseBody String addF(@RequestBody String yourArray) throws Exception {
		JSONObject onb = new JSONObject(yourArray);
		String status = "error";
		try {
			String ffocus = (onb.has("ffocus") && !onb.isNull("ffocus")) ? onb.getString("ffocus") : "";
			String name = (onb.has("name") && !onb.isNull("name")) ? onb.getString("name") : "";
			File file_ = new File(ffocus + "\\" + name);
			file_.mkdirs();
	        status = "success";
		} catch(Exception exp){
		}
		
		return status;		
	}
	
	/*private void logMessage(Exception ex) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		  PrintStream ps = new PrintStream(baos);
		  ex.printStackTrace(ps);
		  logger.error(ex.getMessage()+" : \n" + new String(baos.toByteArray()));
	}*/
}
