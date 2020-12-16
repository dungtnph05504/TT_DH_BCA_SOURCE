package com.nec.asia.applet;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import sg.com.nec.spid.Face;
import sg.com.nec.spid.Portrait;
import sg.com.nec.spid.SpidTools;

public class SPIDFaceTest {
	private static Portrait facial = null;
	
	public final static int IMAGE_JPG = 3;
	public final static int IMAGE_WSQ = 9;

	/** The Constant PORTRAIT_WIDTH_DEFAULT. */
	private final static long PORTRAIT_FACE_WIDTH_DEFAULT =992;//= 992;

	/** The Constant PORTRAIT_HEIGHT_DEFAULT. */
	private final static long PORTRAIT_FACE_HEIGHT_DEFAULT =1276;//= 1322;
	
	@Before
	public void setUp() {
		facial = new Portrait();
	}
	
	@Test
	public void testSPIDFaceCapture() {
		System.out.println("fp.JavaFPCapture() - start");
		long statusCode = facial.JavaPortraitInit((long) Portrait.CAMERA_MODEL.CAMERA_WEBCAM);
		System.out.println("JavaPortraitInit() - statusCode "+statusCode);
		
		String fileName = "c:/temp/JavaPortraitCapture-"+PORTRAIT_FACE_HEIGHT_DEFAULT+"x"+PORTRAIT_FACE_WIDTH_DEFAULT+".jpg";
		
		statusCode = facial.JavaPortraitCapture(Portrait.CAPTURE_MODE.ICAO_FACE, 
				fileName, 
				SpidHelper.IMAGE_JPG, //IMAGE_BMP
				PORTRAIT_FACE_WIDTH_DEFAULT,
				PORTRAIT_FACE_HEIGHT_DEFAULT, true);
		String xml = facial.JavaPortraitGetQualityMesasure();
		System.out.println("JavaPortraitGetQualityMesasure() - xml "+xml);
		
		
		System.out.println("JavaPortraitCapture() - statusCode "+statusCode);
		
		statusCode = facial.JavaPortraitUninit();
		System.out.println("JavaPortraitUninit() - statusCode "+statusCode);
		System.out.println("fp.JavaFPCapture() - end");
	}
	
	@Ignore
	@Test
	public void FaceCompare(){
		System.out.println("face.FaceImageVerify() - start");
		try {
			File fr1 = new File("C:/temp", "001.jpg");
			File fr2 = new File("C:/temp", "001.jpg");
			final byte[] frByte1 = read(fr1);
			final byte[] frByte2 = read(fr2);
			Thread childThread = new Thread(
					new Runnable() {
						@Override
						public void run() {
							Face face = new Face();
							face.FaceInitialize();
							float result = face.FaceImageVerify(frByte1, IMAGE_JPG, frByte2, IMAGE_JPG);
							System.out.println("face.FaceImageVerify() :"+result);
							SpidTools tool = new SpidTools();
							int nLen1 = frByte1.length, nLen2 = frByte2.length;
                            long nRet = -1;
                            //required to SPID 1.31
                            //nRet = tool.JavaFaceViewer(frByte1, nLen1, frByte2, nLen2);
                            System.console().printf("JavaFaceViewer return:"+nRet);
						}
					}
			);
			childThread.start();
			Thread.sleep(10*1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("face.FaceImageVerify() - end");
	}
	
	private byte[] read(File file) throws IOException {
	    ByteArrayOutputStream ous = null;
	    InputStream ios = null;
	    try {
	        byte[] buffer = new byte[4096];
	        ous = new ByteArrayOutputStream();
	        ios = new FileInputStream(file);
	        int read = 0;
	        while ( (read = ios.read(buffer)) != -1 ) {
	            ous.write(buffer, 0, read);
	        }
	    } finally { 
	        try {
	             if ( ous != null ) 
	                 ous.close();
	        } catch ( IOException e) {
	        }

	        try {
	             if ( ios != null ) 
	                  ios.close();
	        } catch ( IOException e) {
	        }
	    }
	    return ous.toByteArray();
	}


	@After
	public void tearDown() {
//		viewer.unloadApplet();
//		applet.cleanUp();
	}
}
