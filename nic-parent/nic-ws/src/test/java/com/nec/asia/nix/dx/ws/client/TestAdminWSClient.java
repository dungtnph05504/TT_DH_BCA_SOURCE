package com.nec.asia.nix.dx.ws.client;

import java.net.MalformedURLException;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nec.asia.nic.dx.admin.FunctionDto;
import com.nec.asia.nic.dx.admin.GetAuthorizedFunctions;
import com.nec.asia.nic.dx.admin.GetAuthorizedFunctionsResponse;
import com.nec.asia.nic.dx.ws.AdminWS;
import com.nec.asia.nic.dx.ws.FaultException;
import com.nec.asia.nic.dx.ws.impl.AdminWSImplService;

import junit.framework.Assert;
import junit.framework.TestCase;

public class TestAdminWSClient extends TestCase {
	public static ApplicationContext context = null;

	public static AdminWS bean = null;

	public TestAdminWSClient() {
		init();
	}

	public void init() {
		try {
			context = new ClassPathXmlApplicationContext("spring-context-test.xml");
			bean = context.getBean("adminWS", AdminWS.class);
		} catch (Error e) {
			e.printStackTrace();
		}
	}

	/*public void testGetFunction1() {
		log("start getAuthorizedFunctions");

		try {
			GetAuthorizedFunctions input = this.prepareInputData();
			GetAuthorizedFunctionsResponse result = bean.getAuthorizedFunctions(input);
			if (result != null) {
				for (FunctionDto function : result.getFunctions()) {
					log(function.getFunctionID());
				}
				log("Upload is Success.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}*/

	public void testGetFunction2() {

		try {

			JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();

			factoryBean.getInInterceptors().add(new LoggingInInterceptor());
			factoryBean.getOutInterceptors().add(new LoggingOutInterceptor());
			factoryBean.setServiceClass(AdminWS.class);
			factoryBean.setAddress("http://192.168.1.222:8480/eppws/services/admin?wsdl");

			AdminWS aa = (AdminWS) factoryBean.create();

			GetAuthorizedFunctions input = this.prepareInputData();
			input.setUserID("NICDEV");
			input.setWorkstationID("THANHTU");
			input.setPassword("P@ssw0rd");
			
			GetAuthorizedFunctionsResponse result = aa.getAuthorizedFunctions(input);
			if (result != null) {
				for (FunctionDto function : result.getFunctions()) {
					log(function.getFunctionID());
				}
			}
		} catch (FaultException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void xtestGetFunction3() {
		try {

			GetAuthorizedFunctions l_Input = new GetAuthorizedFunctions();
			l_Input.setUserID("NICDEV");
			l_Input.setWorkstationID("THANHTU");
			l_Input.setPassword("P@ssw0rd");
			GetAuthorizedFunctionsResponse l_Response = getAuthorizedFunctions(l_Input);
			if (l_Response != null) {
				for (FunctionDto function : l_Response.getFunctions()) {
					log(function.getFunctionID());
				}
			}
		} catch (FaultException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		log(" end  getAuthorizedFunctions");
	}

	private GetAuthorizedFunctions prepareInputData() {
		GetAuthorizedFunctions author = new GetAuthorizedFunctions();
		try {

			author.setUserID("INVUSER");
			author.setPassword("Password1");
			author.setWorkstationID("USVR-1601PPS-APP");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return author;
	}

	private static GetAuthorizedFunctionsResponse getAuthorizedFunctions(GetAuthorizedFunctions input) throws FaultException, MalformedURLException {
		AdminWSImplService service = new AdminWSImplService();
		AdminWS port = service.getAdminWSImplPort();
		return port.getAuthorizedFunctions(input);
	}

	public static void log(String message) {
		System.out.println(message);
	}
}
