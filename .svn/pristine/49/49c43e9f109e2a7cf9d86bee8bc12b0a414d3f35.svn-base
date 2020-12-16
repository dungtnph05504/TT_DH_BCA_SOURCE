package com.nec.asia.nic.framework.security.ldap.active.directory.utils;

import javax.naming.ldap.LdapContext;

/**

 * @author Ambrocio,Paulo Johannes D.
 * @Company: NEC ASIA PACIFIC PTE
 * @since : Jun 5, 2013
 * <p>
 *	Authenticates A user against the Active Directory
 * </p>
 * 
 *
 */
public class LdapAuthenticator implements Authenticator {
	
	private LdapContextFactory contextFactory;
	

	public LdapAuthenticator(LdapContextFactory contextFactory) {
		super();
		this.contextFactory = contextFactory;
	}


	public LdapAuthenticator() {
		super();
	}


	public LdapContextFactory getContextFactory() {
		return contextFactory;
	}


	public void setContextFactory(LdapContextFactory contextFactory) {
		this.contextFactory = contextFactory;
	}


	@Override
	public boolean authenticate(String userName, String password) {
		contextFactory.setUserName(userName);
		contextFactory.setPassword(password);
		LdapContext context = contextFactory.getContext();
		return context!=null;
	}

}
