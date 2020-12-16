/**
 * 
 */
package com.nec.asia.applet;

import java.applet.Applet;
import java.awt.HeadlessException;
import java.net.UnknownHostException;

/**

 * @author Ambrocio,Paulo Johannes D.
 * @Company: NEC ASIA PACIFIC PTE
 * @since : Jul 1, 2013
 * <p>
 *	Decription here
 * </p>
 * 
 *
 */
public class WorkStationInfoApplet extends Applet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7244652026197544664L;
	private String lagyu;

	/**
	 * @throws HeadlessException
	 */
	public WorkStationInfoApplet() throws HeadlessException {
		// TODO Auto-generated constructor stub
	}

	
	
	@Override
	public void init() {
		
		super.init();
		lagyu = System.getenv("COMPUTERNAME");
		System.out.println("ComputerName: lagyu2:" + lagyu);
	}



	public String getWorkStationId(){
		return lagyu;
	}
}
