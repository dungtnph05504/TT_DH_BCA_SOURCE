package com.nec.asia.applet;

import sg.com.nec.spid.Imaging;
import sg.com.nec.spid.SpidTools;


/**
 * The Class SpidHelper.
 */
public class SpidHelper {
	
	/** The Constant IMAGE_RAW. */
	public final static int IMAGE_RAW = 0;
	
	/** The Constant IMAGE_BMP. */
	public final static int IMAGE_BMP = 1;
	
	/** The Constant IMAGE_JPG. */
	public final static int IMAGE_JPG = 3;
	
	/** The Constant IMAGE_TIF. */
	public final static int IMAGE_TIF = 4;
	
	/** The Constant IMAGE_J2K. */
	public final static int IMAGE_J2K = 6;
	
	/** The Constant IMAGE_WSQ. */
	public final static int IMAGE_WSQ = 9;
	
	public final static int SIGN_WIDTH = 453;
	public final static int SIGN_HEIGHT = 118;

	/** The instance. */
	private static SpidHelper instance;
	
	/** The debug. */
	private boolean debug = false;

	/**
	 * Instantiates a new spid helper.
	 *
	 * @throws Exception the exception
	 */
	private SpidHelper() throws Exception {
		String osName = System.getProperty("os.name");
		String osVersion = System.getProperty("os.version");
		System.out.println("Current OS version is [" + osName + "]["+ osVersion + "]");

		if (!((osName.equals("Windows 2000") && osVersion.equals("5.0")) || (osName
				.equals("Windows XP") && osVersion.equals("5.1")))) {
			System.out.println("Warning: Only certified to run on OS version [Windows 2000/5.0] or [Windows XP/5.1]");
		}
	}

	/**
	 * Returns the instance of SpidHelper class.
	 *
	 * @param debug String
	 * @return ServiceLocator
	 * @throws Exception the exception
	 */
	public static SpidHelper getInstance(String debug) throws Exception {
		if (instance == null) {
			instance = new SpidHelper();
			if (debug != null && debug.equals("Y")) {
				instance.debug = true;
			}
		}

		return instance;
	}

	/**
	 * Performs logging.
	 *
	 * @param msg the message to log
	 */
	private void log(String msg) {
		if (debug) {
			System.out.println("[SpidHelper]: " + msg);
		}
	}

	/**
	 * Perform WSQ decompression for fingerprint image Requires NIST PACK
	 * installation to run in server environment.
	 *
	 * @param imageBuf byte[]
	 * @param imageFormat int
	 * @return byte[]
	 */
	public static synchronized byte[] wsqDecompress(byte[] imageBuf, int imageFormat) {
		SpidTools spidTools = new SpidTools();
		if (spidTools.JavaWSQDecompress(imageBuf, imageBuf.length, imageFormat) == 0) {
			return spidTools.outBuf;
		} else {
			return null;
		}
	}

	/**
	 * Convert image format.
	 *
	 * @param imageBuf byte[]
	 * @param inFormat int
	 * @param outFormat int
	 * @return byte[]
	 */
	public static synchronized byte[] convertImageFormat(byte[] imageBuf, int inFormat,
			int outFormat) {

		Imaging imaging = new Imaging();

		long statusCode = 0;
		if (inFormat == IMAGE_RAW) {
			statusCode = imaging.ImageFormatConvert(imageBuf, imageBuf.length,
					512, 512, 8, inFormat, outFormat);
		} else {
			statusCode = imaging.ImageFormatConvert(imageBuf, imageBuf.length,
					0, 0, 0, inFormat, outFormat);
		}

		if (statusCode == 0) {
			return imaging.outBuf;
		} else {
			return null;
		}
	}
}
