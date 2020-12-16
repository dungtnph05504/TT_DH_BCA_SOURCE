package com.nec.asia.nic.comp.integration.impl;


/**
 * The Class SecureFtpServer.
 *
 * @author Alvin Chua
 */
public class SecureFtpServer extends FtpServer{
	
	/** The ssl private key file. */
	private String sslPrivateKeyFile=null;
	
	/** The passphrase. */
	private String passphrase=null;
	
	/**
	 * Gets the ssl private key file.
	 *
	 * @return the ssl private key file
	 */
	public String getSslPrivateKeyFile() {
		return sslPrivateKeyFile;
	}
	
	/**
	 * Sets the ssl private key file.
	 *
	 * @param sslPrivateKeyFile the new ssl private key file
	 */
	public void setSslPrivateKeyFile(String sslPrivateKeyFile) {
		this.sslPrivateKeyFile = sslPrivateKeyFile;
	}
	
	/**
	 * Gets the passphrase.
	 *
	 * @return the passphrase
	 */
	public String getPassphrase() {
		return passphrase;
	}
	
	/**
	 * Sets the passphrase.
	 *
	 * @param passphrase the new passphrase
	 */
	public void setPassphrase(String passphrase) {
		this.passphrase = passphrase;
	}
}
