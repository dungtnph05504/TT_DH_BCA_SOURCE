package com.nec.asia.nic.admin.domain;

import java.io.Serializable;

/**
 * A user who can book hotels.
 * 
 * @author bright_zheng
 *
 */
public class User implements Serializable {
	private static final long serialVersionUID = 5052349090749014645L;

	private Integer id;
	private String account;
	private String password;
	private String name;
	private String redirect;
	private String workstationId;
	
	
	public User() {
	}

	public User(String account, String password, String name) {
		this.account = account;
		this.password = password;
		this.name = name;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the redirect
	 */
	public String getRedirect() {
		return redirect;
	}

	/**
	 * @param redirect the redirect to set
	 */
	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}

	/**
	 * @return the workstationId
	 */
	public String getWorkstationId() {
		return workstationId;
	}

	/**
	 * @param workstationId the workstationId to set
	 */
	public void setWorkstationId(String workstationId) {
		this.workstationId = workstationId;
	}

	@Override
	public String toString() {
		return "User(" + account + ")";
	}
}
