package com.nec.asia.applet;

import java.util.Properties;

import javax.swing.JApplet;

import org.apache.commons.codec.binary.Base64;

import com.nec.asia.epid.client.ePID_Client;

import sg.com.nec.spid.FingerPrint;

/**
 * The Class EppApplet.
 */
/*
 * Modification History:
 * 
 * 24 Feb 2016 (chris) : init class combine workstation, and epid applets.
 * 26 Feb 2016 (chris) : combine with investigation applet
 * 15 Mar 2016 (chris) : changed for logging.
 * 31 May 2017 (chris) : to test epidEnable as false
 */
public class EppApplet extends JApplet {

	private static final long serialVersionUID = -8067610617154479704L;
	
	private static ePID_Client epid = null;
	private Properties prop = null;
	private String workStationId = null;
	private boolean debug = false;
	
	private static FingerPrint fp = null;
	//
	public void init() {
		super.init();
		workStationId = System.getenv("COMPUTERNAME");
		System.out.println("COMPUTERNAME: " + workStationId);
		
		prop = new Properties();
		try {
			prop.load(EppApplet.class.getClassLoader().getResourceAsStream("epid.properties"));
			
			String epidEnable = prop.getProperty("epid.enable");
			if ("true".equalsIgnoreCase(epidEnable)) {
				epid = new ePID_Client();
			} else {
				log("Skipped epid loading");
			}
		} catch (Throwable e) {
			log("Can't create because of " + e);
		}
	}

	public FingerPrint getFingerprintObject() {
		if (fp == null) {
			fp = new FingerPrint();
		}
		return fp;
	}
	
	public void start() {
		System.out.println("starting applet");
		if (epid == null && prop == null) {
			init();
		}
		if (fp == null) {
			fp = getFingerprintObject();
		}
		System.out.println("start done");
	}
	
	public void stop() {
		System.out.println("stopping applet");
		
		System.out.println("stop done");
	}

	public void destroy() {
		System.out.println("destroying applet");
		if (epid != null) {
		}
		epid = null;
		
		if (fp != null) {
			//fp.JavaTenprintExit();
		}
		fp = null;
		System.gc();
		System.out.println("destroying done");
	}
	
	//EPID
	public String fingerprintLogin(String userId) {
		log(" method   : CAPTURE_BIOMETRIC.Verification");
		String ACTIONS_START    = prop.getProperty("ACTIONS_START");
		String REQUEST_TOKEN    = prop.getProperty("REQUEST_TOKEN");
		String VERIFICATION     = prop.getProperty("CAPTURE_BIOMETRIC_VERIFICATION");
		String RETRIEVE_RESULT  = prop.getProperty("RETRIEVE_RESULT");
		String ACTIONS_END      = prop.getProperty("ACTIONS_END");
		
		String inputXml = ACTIONS_START + REQUEST_TOKEN + VERIFICATION + RETRIEVE_RESULT + ACTIONS_END;
		//set input
		if (userId!=null)	userId = userId.toUpperCase();
		inputXml = inputXml.replaceAll("VALUE__SUBJECT_ID", userId);
		log(" inputXml : "+inputXml);
		
		String outputXml = epid.ePID_Client_Action(inputXml);
		outputXml = outputXml.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>","");
		outputXml = outputXml.replace("\r\n", "");
		outputXml = outputXml.replace("      ","");
		log("outputXml : "+outputXml);
		
		String errorMessage = validateReturnCode(outputXml);
		boolean passFPAuth = validateRetrieveResult(outputXml);
		
		if (errorMessage==null && passFPAuth) {
			return "true";
		}
		return errorMessage;
	}
	
	//EPID
	public String fingerprintEnrollment(String userId, String userName, String email) {
		log(" method   : CAPTURE_BIOMETRIC.ENROLLMENT-WITHOUT-DUP-CHECK");
		String ACTIONS_START    = prop.getProperty("ACTIONS_START");
		String REQUEST_TOKEN    = prop.getProperty("REQUEST_TOKEN");
		String ENROLLMENT      = prop.getProperty("CAPTURE_BIOMETRIC_ENROLLMENT");
		String ACTIONS_END      = prop.getProperty("ACTIONS_END");
		
		String inputXml = ACTIONS_START + REQUEST_TOKEN + ENROLLMENT + ACTIONS_END;
		
		if (userId!=null)		userId = userId.toUpperCase();
		if (userName == null)	userName = userId;
		if (email == null)		email = "";
		
		//set input VALUE__SUBJECT_ID, VALUE__SUBJECT_NAME, VALUE__EMAIL
		inputXml = inputXml.replaceAll("VALUE__SUBJECT_ID", userId);
		inputXml = inputXml.replaceAll("VALUE__SUBJECT_NAME", userName);
		inputXml = inputXml.replaceAll("VALUE__EMAIL", email);
		log(" inputXml : "+inputXml);
		
		String outputXml = epid.ePID_Client_Action(inputXml);
		//log("outputXml: "+outputXml);
		outputXml = outputXml.replaceAll("<?xml version=\"1.0\" encoding=\"utf-8\"?>","");
		outputXml = outputXml.replaceAll("\r\n", "");
		outputXml = outputXml.replaceAll("      ","");
		log("outputXml : "+outputXml);
		String errorMessage = validateReturnCode(outputXml);
		if (errorMessage==null) {
			return "true";
		}
		return errorMessage;
	}
	
	private String validateReturnCode(String outputXml) {
		String errorMsg = null;
		if (outputXml!=null) {
			String tempXml = outputXml;
			int actionNameStart = tempXml.indexOf("Action_Name=");
			do {
				if (actionNameStart!=-1) {
					int actionNameEnd = tempXml.indexOf(">", actionNameStart+12);
					String actionName = tempXml.substring(actionNameStart+12, actionNameEnd);
					actionName = actionName.replaceAll("\"", "");
					
					int returnCodeStart = tempXml.indexOf("<Return_Code>", actionNameEnd);
					int returnCodeEnd = tempXml.indexOf("</Return_Code>", returnCodeStart);
					String returnCode = tempXml.substring(returnCodeStart+13, returnCodeEnd);
					
					log("actionName : "+actionName+", \t returnCode : "+returnCode);
					if (!"0".equalsIgnoreCase(returnCode)) {
						int returnMsgStart = tempXml.indexOf("<Return_Msg>", actionNameEnd);
						int returnMsgEnd = tempXml.indexOf("</Return_Msg>", returnCodeStart);
						String returnMsg = tempXml.substring(returnMsgStart+12, returnMsgEnd);
						log("Error in actionName : "+actionName+", \t returnMsg : "+returnMsg);
						
						errorMsg = "Error in actionName : "+actionName+", returnCode : "+returnCode+",  returnMsg : "+returnMsg;
					}
					tempXml = tempXml.substring(returnCodeEnd);
					actionNameStart = tempXml.indexOf("Action_Name=");
				}
			} while (actionNameStart!=-1);
		}
		return errorMsg;
	}
	
	private boolean validateRetrieveResult(String outputXml) {
		if (outputXml!=null) {
			String tempXml = outputXml;
			int actionNameStart = tempXml.indexOf("Action_Name=");
			do {
				if (actionNameStart!=-1) {
					int actionNameEnd = tempXml.indexOf(">", actionNameStart+12);
					String actionName = tempXml.substring(actionNameStart+12, actionNameEnd);
					actionName = actionName.replaceAll("\"", "");
					
					if ("RETRIEVE_RESULT".equalsIgnoreCase(actionName)) {
						int clearResultStart = tempXml.indexOf("<Clear_Result>", actionNameEnd);
						int clearResultEnd = tempXml.indexOf("</Clear_Result>", clearResultStart);
						String clearResult = tempXml.substring(clearResultStart+14, clearResultEnd);
						
						log("actionName : "+actionName+", \t clearResult : "+clearResult);
						if ("1".equalsIgnoreCase(clearResult)) {
							return true;
						}
					}
					tempXml = tempXml.substring(actionNameEnd);
					actionNameStart = tempXml.indexOf("Action_Name=");
				}
			} while (actionNameStart!=-1);
		}
		return false;
	}
	
	//InvestigationApplet
	/* NIC Investigation compare finger prints candidate method */
	public long compareFingerprints(String wsqImage1, String wsqImage2) {
		long result = -1;
		byte[] fpByte1 = Base64.decodeBase64(wsqImage1);
		byte[] fpByte2 = Base64.decodeBase64(wsqImage2);
		log("fpByte1 :" + fpByte1);
		log("fpByte2 :" + fpByte2);
		result = fp.JavaFPVerifyViewer(fpByte1, SpidHelper.IMAGE_WSQ, 512, 512, fpByte2, SpidHelper.IMAGE_WSQ, 512, 512);
		log("fp.JavaFPVerifyViewer() :" + result);
		return result;
	}
	
	//InvestigationApplet
	public String convertImageFormatWsqToJpg(String imageBase64String) {
		String convertedBase64String = "";
		try{
			byte[] imageBinary = Base64.decodeBase64(imageBase64String);
			log("The image binary before conversion===="+imageBinary);
			byte[] convertedBinary = SpidHelper.convertImageFormat(imageBinary, SpidHelper.IMAGE_WSQ, SpidHelper.IMAGE_JPG);
			log("The image binary after conversion===="+convertedBinary);
			convertedBase64String = Base64.encodeBase64String(convertedBinary);
			//log("The image binary after base64 conversion===="+convertedBase64String);
		} catch(Throwable ex){
			logError("Error occured while converting the wsq image to jpeg"+ex.getMessage());
		}
		return convertedBase64String;
	}
	
	private void log(String msg) {
		if (debug) {
			System.out.println("[EppApplet]: " + msg);
		}
	}
	
	private void logError(String msg) {
		System.err.println("[EppApplet]: " + msg);
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}
	
	public String getWorkStationId(){
		log("COMPUTERNAME: " + workStationId);
		return workStationId;
	}
}
