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

import NECA.SPID.Quality.Fingerprint;
//import sg.com.nec.spid.Face;
import sg.com.nec.spid.FingerPrint;

public class SPIDFPTest {
	private static FingerPrint _fp = null;
	public final static int IMAGE_JPG = 3;
	public final static int IMAGE_WSQ = 9;
	
	public static final long MINU_FMP5 				= 0x1000;
	public static final long MINU_CBEFF_SC37_N464	= 0x1007;

	@Before
	public void setUp() {
		_fp = new FingerPrint();
	}
	
	@Ignore //bad quality
	@Test
	public void testSPIDFPCapture() {
		System.out.println("fp.JavaFPCapture() - start");
		_fp.JavaFPCapture(1, IMAGE_WSQ, 2000);
		System.out.println("fp.JavaFPCapture() - end");
	}
	
	@Ignore
	@Test
	public void testSPIDCaptureVerify() {
		System.out.println("fp.JavaFPVerify() - start");
		try {
			int nThreshold = 2000;
			File fp1 = new File("C:/temp", "F1.wsq");
			byte[] fpByte1 = read(fp1);
			
			long result = _fp.JavaFPVerify(1,fpByte1,fpByte1.length,8000,nThreshold);
			System.out.println("fp.JavaFPVerify() :"+result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("fp.JavaFPVerify() - end");
	}
	
	@Ignore
	@Test
	public void buttonCompare(){
		System.out.println("fp.JavaFPVerifyViewer() - start");
		try {
			File fp1 = new File("C:/temp", "F1.wsq");
			File fp2 = new File("C:/temp", "F1.wsq");
			final byte[] fpByte1 = read(fp1);
			final byte[] fpByte2 = read(fp2);
			Thread childThread = new Thread(
					new Runnable() {
						@Override
						public void run() {
							FingerPrint fpSPID = new FingerPrint();
							long result = fpSPID.JavaFPVerifyViewer(fpByte1, IMAGE_WSQ, 512, 512, fpByte2, IMAGE_WSQ, 512, 512);
							System.out.println("fp.JavaFPVerifyViewer() :"+result);
						}
					}
			);
			childThread.start();
			Thread.sleep(10*1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("fp.JavaFPVerifyViewer() - end");
	}
	
	//@Test
	public void getFPQuality(){
		System.out.println("fp.JavaFPCreateMinutia() - start");
		try {
			File fp1 = new File("C:/TMP/DEMO1", "F1.wsq");
			File fp2 = new File("C:/TMP/DEMO1", "F1.wsq");
			final byte[] fpByte1 = read(fp1);
			final byte[] fpByte2 = read(fp2);
			System.out.println("F1 :"+fpByte1+"\tsize:"+fpByte1.length);
			System.out.println("F2 :"+fpByte2+"\tsize:"+fpByte2.length);
			Thread childThread = new Thread(
					new Runnable() {
						@Override
						public void run() {
							FingerPrint fpLib = new FingerPrint();
							//long score = fpLib.JavaFPVerifyViewer(fpByte1, IMAGE_WSQ, 512, 512, fpByte2, IMAGE_WSQ, 512, 512);
							//System.out.println("fp.JavaFPVerifyViewer():"+score);
							long result = fpLib.JavaFPCreateMinutia(fpByte1, IMAGE_WSQ);
							fpLib.JavaFPCreateMinutia(fpByte1, IMAGE_WSQ, fpByte1.length, MINU_FMP5);
							System.out.println("fp.MinutiaCount:"+fpLib.MinutiaCount);
							Fingerprint fpSPID = new Fingerprint();
							long quality = fpSPID.GetQuality(fpByte1, IMAGE_WSQ, 0, 0);
							System.out.println("fp.GetQuality() Quality:"+quality);
						}
					}
			);
			childThread.start();
			Thread.sleep(60*1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("fp.JavaFPCreateMinutia() - end");
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
