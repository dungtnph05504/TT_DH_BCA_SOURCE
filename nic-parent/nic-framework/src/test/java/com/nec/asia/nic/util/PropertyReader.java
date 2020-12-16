package com.nec.asia.nic.util;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:**/test-context.xml"})
@Configuration
//@PropertySource("classpath:properties/ad.properties")
public class PropertyReader {
	
	@Value("${ldap.primary_hostname}")
	private String primaryHostName;
	@Value("${ldap.secondary_hostname}")
	private String secondaryHostName;
	
	@Value("${ldap.primary_hostname}")
	private String[] multiIpAddress;
	
	@Value("${ldap.port}")
	private String portNumber;
	@Value("${ldap.ssl.port}")
	private String sslPortNumber;
	@Value("${ldap.ssl}")
	private boolean sslEnabled;
	@Value("${ldap.user}")
	private String authUser;
	@Value("${ldap.password}")
	private String authPassword;
	@Value("${ldap.base}")
	private String base;
	@Value("${ldap.truststore.file}")
	private String keyStore;
	@Value("${ldap.truststore.password}")
	private String trustStorePassword;
	
	public String toString() {
		return "[Properties] \n"
				+ "multiIpAddress: " + ((multiIpAddress==null)? "null" :Arrays.asList(multiIpAddress)) +"\n"
				+ "portNumber: " + portNumber +"\n"
				+ "sslPortNumber: " + sslPortNumber +"\n"
				+ "sslEnabled: " + sslEnabled +"\n"
				+ "authUser: " + authUser +"\n"
				+ "authPassword: " + authPassword +"\n"
				+ "base: " + base +"\n"
				+ "keyStore: " + keyStore +"\n"
				+ "trustStorePassword: " + trustStorePassword +"\n"
				;
	}
	
	public static void main(String [] args) {
		PropertyReader r = new PropertyReader();
		System.out.println(r);
	}
	
	@Test
	public void testIt() {
		//PropertyReader r = new PropertyReader();
		System.out.println(this);
	}
}
