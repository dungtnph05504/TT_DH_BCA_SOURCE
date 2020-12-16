package com.nec.asia.nic.framework.security.ldap.dao;

import java.util.List;

import com.nec.asia.nic.framework.dao.GenericDao;
import com.nec.asia.nic.framework.security.ldap.active.directory.utils.Authenticator;
import com.nec.asia.nic.framework.security.ldap.domain.ActiveDirectoryUser;
import com.nec.asia.nic.framework.security.ldap.exceptions.ChangePasswordException;
import com.nec.asia.nic.framework.security.ldap.exceptions.IncorrectPasswordException;
import com.nec.asia.nic.framework.security.ldap.exceptions.UserNotFoundException;

public interface UserADDao extends GenericDao<ActiveDirectoryUser,Integer>,Authenticator
{
	List<ActiveDirectoryUser> findAllByOrganizationalUnitName(String orgunit);
	ActiveDirectoryUser findUserByPrincipalName(String userPrincipalName);
	ActiveDirectoryUser findUserByUserName(String userName,String organizationalUnit);
	ActiveDirectoryUser findUserByUserName(String userName);
	ActiveDirectoryUser findUserByCN(String CnName);
	boolean changePassword(ActiveDirectoryUser e,String newPassword, String oldPassword) throws IncorrectPasswordException,UserNotFoundException,ChangePasswordException;
	boolean changePassword(String userName,String newPassword, String oldPassword) throws ChangePasswordException,
			UserNotFoundException, IncorrectPasswordException;
    boolean resetPassword(String userName, String newPassword)
			throws ChangePasswordException, UserNotFoundException;
}
