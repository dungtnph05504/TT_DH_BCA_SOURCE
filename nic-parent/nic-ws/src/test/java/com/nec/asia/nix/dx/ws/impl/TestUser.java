package com.nec.asia.nix.dx.ws.impl;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.nec.asia.nic.dx.admin.GetAuthorizedFunctions;
import com.nec.asia.nic.dx.ws.AdminWS;
import com.nec.asia.nic.dx.ws.FaultException;

import junit.framework.TestCase;

public class TestUser extends TestCase {

	public void testAdminWS() {

		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(AdminWS.class);
		factory.setAddress("http://localhost:8680/eppws/services/admin");
		AdminWS client = (AdminWS) factory.create();
		GetAuthorizedFunctions input = new GetAuthorizedFunctions();
		input.setPassword("Password1");
		input.setUserID("RICADMIN");
		input.setWorkstationID("DESKTOP-J7HFMMT");
		try {
			client.getAuthorizedFunctions(input);
		} catch (FaultException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
