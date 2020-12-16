/**
 * 
 */
package com.nec.asia.applet;

import javax.swing.JApplet;
import javax.swing.SwingUtilities;

import org.apache.commons.codec.binary.Base64;

import com.nec.asia.applet.tools.jp2.Jp2ConverterSpidImpl;

import sg.com.nec.spid.FingerPrint;

/**
 * @author sailaja_chinapothula
 * @Company: NEC Asia Pacific Ltd
 * @Since: Jul 5, 2013
 */

/*
 * Modification History:
 * 
 * 24 Mar 2014 (chris) : to remove debug message.
 */
public class InvestigationApplet extends JApplet {
	
	private static final long serialVersionUID = 1016611631940590823L;
	
	/** The applet. */
	public InvestigationApplet applet;

	public final static int IMAGE_WSQ = SpidHelper.IMAGE_WSQ;
	/** The Constant IMAGE_RAW. */
	public final static int IMAGE_RAW = 0;
	
	public static FingerPrint fp;
	
	/**
	 * WSQ image values
	 */
	byte[] _wsqimage1,
		   _wsqimage2,
		   _wsqimage3,
		   _wsqimage4,
		   _wsqimage5,
		   _wsqimage6,
		   _wsqimage7,
		   _wsqimage8,
		   _wsqimage9,
		   _wsqimage10;
	
	/**
	 * JPG image values
	 */
	byte[] _jpg1,
		   _jpg2,
		   _jpg3,
		   _jpg4,
		   _jpg5,
		   _jpg6,
		   _jpg7,
		   _jpg8,
		   _jpg9,
		   _jpg10;
	
	/**
	 * Instantiates a new Registration applet.
	 */
	public InvestigationApplet() {
		super();
	}

	public void init() {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					System.out.println(" init Started");
					String param = getParameter("verify");
					System.out.println("Nic Investigation = " + param);
					System.out.println(" init Done");
				}
			});
		} catch (Exception exc) {
			System.out.println("Can't create because of " + exc);
		}
	}

	public void start() {
		System.out.println("starting applet");
		if (fp == null) {
			fp = getFingerprintObject();
		}
		System.out.println("start done (applet)");
	}
	
	public void stop() {
		System.out.println("stopping applet");
		
		System.out.println("stop done");
	}

	public void destroy() {
		System.out.println("destroying applet");
		if (fp != null) {
			fp.JavaTenprintExit();
		}
		fp = null;
		System.gc();
		System.out.println("destroying done");
	}
	
	
	/**
	 * Gets the fingerprint object.
	 * 
	 * @return the fingerprint object
	 */
	public FingerPrint getFingerprintObject() {
		if (fp == null) {
			fp = new FingerPrint();
			// fp.JavaTenprintInit((int)FingerPrint.TENPRINT_DEVICE.CROSSMATCH_LSCAN);
			// tenprintDeviceInitStatus = 1;
		}
		return fp;
	}
	
	/* NIC Investigation compare finger prints candidate method */
	public long compareFingerprints(String wsqImage1, String wsqImage2) {
		long result = -1;
		byte[] fpByte1 = Base64.decodeBase64(wsqImage1);
		byte[] fpByte2 = Base64.decodeBase64(wsqImage2);
		System.out.println("fpByte1 :" + fpByte1);
		System.out.println("fpByte2 :" + fpByte2);
		result = fp.JavaFPVerifyViewer(fpByte1, IMAGE_WSQ, 512, 512, fpByte2, IMAGE_WSQ, 512, 512);
		System.out.println("fp.JavaFPVerifyViewer() :" + result);
		return result;
	}
	
	/**
	 * Convert WSQ FP image to JPG format.
	 *
	 * @param imageBase64String String
	 * @return convertedBase64String
	 */
	public String convertImageFormatWsqToJpg(String imageBase64String) {
		String convertedBase64String = "";
		try{
			byte[] imageBinary = Base64.decodeBase64(imageBase64String);
			System.out.println("The image binary before conversion===="+imageBinary);
			byte[] convertedBinary = SpidHelper.convertImageFormat(imageBinary, SpidHelper.IMAGE_WSQ, SpidHelper.IMAGE_JPG);
			System.out.println("The image binary after conversion===="+convertedBinary);
			convertedBase64String = Base64.encodeBase64String(convertedBinary);
			//System.out.println("The image binary after base64 conversion===="+convertedBase64String);
		} catch(Exception ex){
			System.out.println("Error occured while converting the wsq image to jpeg"+ex.getMessage());
		}
		return convertedBase64String;
	}
	
	public String getAJpgSafeVersion(String inputImageBase64String) {
		System.out.println("getAJpgSafeVersion:  start");

		try {
			byte[] inputImageByteArray = Base64.decodeBase64(inputImageBase64String);

			byte[] convertedBinary = new Jp2ConverterSpidImpl().toJpg(inputImageByteArray); 

			return Base64.encodeBase64String(convertedBinary);
		} catch (Exception ex) {
			System.out.println("getAJpgSafeVersion:  Exception");
			return null;
		} finally {
			System.out.println("getAJpgSafeVersion:  end");
		}
	}
}
