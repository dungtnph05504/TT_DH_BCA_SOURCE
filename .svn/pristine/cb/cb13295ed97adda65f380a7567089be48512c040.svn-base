package com.nec.asia.nic.framework.security.ldap.active.directory.utils;

import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import org.apache.log4j.Logger;

import com.nec.asia.nic.utils.encryption.StringEncyptionProvider;

/**

 * @author Ambrocio,Paulo Johannes D.
 * @Company: NEC ASIA PACIFIC PTE
 * @since : Jun 5, 2013
 * <p>
 *	A Factory LDAP contexts
 * </p>
 * 
 *
 */
public class LdapContextFactory {
	private static Logger logger = Logger.getLogger(LdapContextFactory.class);
	private Hashtable<String, String> ldapEnv = new Hashtable<String,String>();
	private String domain;
	private String ldapServerAddress;
	private String ldapPort;
	private String keyStore;
	private String trustStorePassword;
	private String userName;
	private String password;
	private StringEncyptionProvider encyptionProvider;
	
	private LdapContext  ldapContext;
	private boolean isSecure;

	

	
	
	
	
	public LdapContextFactory( String domain,
			String ldapServerAddress, String ldapPort, String userName,
			String password, StringEncyptionProvider encyptionProvider) {
		super();

		this.domain = domain.trim();
		this.ldapServerAddress = ldapServerAddress.trim();
		this.ldapPort = ldapPort.trim();
		this.userName = userName.trim();
		this.password = password.trim();
		this.encyptionProvider = encyptionProvider;
	}
	
	
	
	public LdapContextFactory(String domain, String ldapServerAddress,
			String ldapPort, String keyStore, String trustStorePassword,
			String userName, String password,
			StringEncyptionProvider encyptionProvider) {
		super();
		this.isSecure = true;
		this.domain = domain.trim();
		this.ldapServerAddress = ldapServerAddress.trim();
		this.ldapPort = ldapPort.trim();
		this.keyStore = keyStore.trim();
		this.trustStorePassword = trustStorePassword.trim();
		this.userName = userName.trim();
		this.password = password.trim();
		this.encyptionProvider = encyptionProvider;
		
		
	}



	/**
	 * @author Ambrocio,Paulo Johannes D.
	 * @Company: NEC ASIA PACIFIC PTE
	 * @since : Jun 5, 2013
	 * <p>
	 *	 Configures and Instatiates required LDAP Context
	 * </p>
	 * @return null if the an error in instantiating the LDAP Context
	 */
	public LdapContext getContext(){
		
			try{
				logger.info("Instantiating Ldap Context");
				ldapEnv = new Hashtable<String,String>();
				ldapEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
				ldapEnv.put(Context.PROVIDER_URL, "ldap://" + ldapServerAddress+":"+ldapPort);
				ldapEnv.put(Context.SECURITY_AUTHENTICATION, "simple");
				ldapEnv.put(Context.SECURITY_PRINCIPAL, userName+"@"+domain);
				String decryptedPassword="";
				try {
					decryptedPassword = encyptionProvider.decrypt(password);
				} catch (Exception e) {
					logger.error("Error in decrypting password", e);
				}
				ldapEnv.put(Context.SECURITY_CREDENTIALS, decryptedPassword);
				if(isSecure){
					
					ldapEnv.put(Context.SECURITY_PROTOCOL,  "ssl");
					System.setProperty( "javax.net.ssl.trustStore", keyStore );
					System.setProperty( "javax.net.ssl.trustStorePassword", trustStorePassword );
					
				}
				ldapContext = new InitialLdapContext(ldapEnv, null);
				logger.info("Done: Instantiating Ldap Context");
			}catch(AuthenticationException ex){
				logger.error("Error in establishing Ldap connection:",ex);
				return null;
			} catch (NamingException ex) {
				logger.error("Error in establishing Ldap connection:",ex);
				return null;
			}
	
		return ldapContext;
	}
	
	public String getLdapServerAddress() {
		return ldapServerAddress;
	}
	public String getLdapPort() {
		return ldapPort;
	}
	public String getKeyStore() {
		return keyStore;
	}
	public String getTrustStorePassword() {
		return trustStorePassword;
	}



	public String getDomain() {
		return domain;
	}



	public void setDomain(String domain) {
		this.domain = domain.trim();
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password.trim();
	}



	public void setUserName(String principalUser) {
		this.userName = principalUser.trim();
	}



	public String getUserName() {
		return userName;
	}
	
	
    public LdapContext getLdapContext() {
		return ldapContext;
	}



	public void setLdapContext(LdapContext ldapContext) {
		this.ldapContext = ldapContext;
	}



	public StringEncyptionProvider getEncyptionProvider() {
		return encyptionProvider;
	}



	public void setEncyptionProvider(StringEncyptionProvider encyptionProvider) {
		this.encyptionProvider = encyptionProvider;
	}



	public String getDomainDC() {
        StringBuilder buf = new StringBuilder();
        for (String token : domain.split("\\.")) {
            if(token.length()==0)   continue;   // defensive check
            if(buf.length()>0)  buf.append(",");
            buf.append("DC=").append(token);
        }
        return buf.toString();
    }
	
	
}
