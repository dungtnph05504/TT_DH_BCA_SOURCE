package com.nec.asia.epid.client;

import java.security.AccessController;
import java.security.PrivilegedAction;

/*
 * Modification History :
 * 25 Feb 2016  chris : init class
 * 14 Mar 2016  chris : update for epid version 3.10 (load JNI dll for 64 bit and 32 bit).
 *
 */

public class ePID_Client {
	private static final String DLL_NAME = Integer.valueOf(System.getProperty("sun.arch.data.model")).intValue() == 32 ? "ePID_Client_JNI_32" : "ePID_Client_JNI_64";
	private static final String DLL_NAME_LEGACY = "ePID_Client_JNI";

	static {
		loadLib();
	}

	public native String ePID_Client_Action(String paramString);

	public native void ePID_VerifyView(String paramString);

	private static void loadLib() {
		try {
			System.out.println(DLL_NAME + " loading...");
			AccessController.doPrivileged(new LoadLibraryAction(null));
			System.out.println(DLL_NAME + " loaded.");
		} catch (Throwable e) {
			System.out.println("Exception loading library: " + e.getMessage());
			e.printStackTrace(System.out);
		}
	}

	protected void finalize() throws Throwable {
		super.finalize();
		System.out.println("ePID_Client gc");
	}

	private static final class LoadLibraryAction implements PrivilegedAction<Object> {

		public LoadLibraryAction(String name) {
		}

		public Object run() {
			try {
				System.loadLibrary(ePID_Client.DLL_NAME);
			} catch (Throwable e) {
				System.out.println("Unable to find " + ePID_Client.DLL_NAME + ".dll library.");
				System.loadLibrary(ePID_Client.DLL_NAME_LEGACY);
				System.out.println(ePID_Client.DLL_NAME_LEGACY+" is loaded instead.");
			}
			return null;
		}
	}
}