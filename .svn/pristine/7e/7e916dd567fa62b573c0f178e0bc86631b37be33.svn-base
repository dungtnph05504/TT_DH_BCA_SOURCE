package com.nec.asia.nic.framework.security.ldap.dao.adImpl;

import java.util.LinkedList;
import java.util.List;



import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.security.ldap.active.directory.utils.LdapContextFactory;
import com.nec.asia.nic.framework.security.ldap.dao.UserADDao;
import com.nec.asia.nic.framework.security.ldap.domain.ActiveDirectoryUser;
import com.nec.asia.nic.framework.security.ldap.exceptions.ChangePasswordException;
import com.nec.asia.nic.framework.security.ldap.exceptions.IncorrectPasswordException;
import com.nec.asia.nic.framework.security.ldap.exceptions.UserNotFoundException;
import com.nec.asia.nic.framework.security.ldap.mapper.ADMapper;


import java.io.UnsupportedEncodingException;
import java.lang.Exception;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;

/**
 * @author Ambrocio,Paulo Johannes D.
 * @Company: NEC ASIA PACIFIC PTE
 * @since : Jun 5, 2013
 * <p>
 *	provides C.R.U.D. Operation for the User entity against the Active Directory
 * </p>
 * 
 *
 */



public class UserDaoADImpl implements UserADDao{
	private static Logger logger = Logger.getLogger(UserADDao.class); 
	private LdapContextFactory contextFactory;
	private LdapContextFactory passwordContextFactory;
	

	

	
	
	public UserDaoADImpl() {
		super();
	}

	public UserDaoADImpl(LdapContextFactory contextFactory,LdapContextFactory passwordContextFactory) {
		super();
		this.contextFactory = contextFactory;
		this.passwordContextFactory = passwordContextFactory;
	}

	public LdapContextFactory getContextFactory() {
		return contextFactory;
	}

	public void setContextFactory(LdapContextFactory contextFactory) {
		this.contextFactory = contextFactory;
	}

	
	
	public LdapContextFactory getPasswordContextFactory() {
		return passwordContextFactory;
	}

	public void setPasswordContextFactory(LdapContextFactory passwordContextFactory) {
		this.passwordContextFactory = passwordContextFactory;
	}

	@Override
	public ActiveDirectoryUser findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}


	/**
	 * 
	 * @author Ambrocio,Paulo Johannes D.
	 * @Company: NEC ASIA PACIFIC PTE
	 * @since : Jun 5, 2013
	 * <p>
	 *	creates a new user against LDAP
	 * </p>
	 * @param ActiveDirectoryUser - user entity to be saved
	 * @return -1 on failure to save 0 if saving is successful
	 */
	public Integer save(ActiveDirectoryUser e) {
		e.setDomain(contextFactory.getDomain());
		Attributes butes = ADMapper.mapUserToAttributes(e);
		try{
			contextFactory.getContext().createSubcontext("cn="+e.getCN()+","+e.getOrganizationalUnitString()+","+contextFactory.getDomainDC(), butes );
		}catch(Exception ex){
			logger.error("Error occurred while getting creating the user. Exception:"+ex.getMessage());
			return new Integer(-1);
		}
		return 0;
	}

	@Override
	public void saveOrUpdate(ActiveDirectoryUser user) {
		// TODO Auto-generated
		ModificationItem[] mods = ADMapper.getModificationItems(user);
		
		try {
			LdapContext context = contextFactory.getContext();
			context.modifyAttributes(user.getDistinguishedName(), mods);         

        } catch (NamingException e1) {
        	//System.out.println(e1);
			logger.error(e1);
		}
	}

	@Override
	public ActiveDirectoryUser merge(ActiveDirectoryUser e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(ActiveDirectoryUser e) {
		try {
			contextFactory.getContext().destroySubcontext(e.getDistinguishedName());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ActiveDirectoryUser> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ActiveDirectoryUser> findAll(ActiveDirectoryUser e) {
		// TODO Auto-generated method stub
		return null;
	}





	@Override
	public boolean changePassword(ActiveDirectoryUser e, String newPassword, String oldPassword) throws IncorrectPasswordException,UserNotFoundException,ChangePasswordException {
		return changePassword(e.getUserName(), newPassword, oldPassword);
	}

	@Override
	public List<ActiveDirectoryUser> findAllByOrganizationalUnitName(String orgunit) {
		List<ActiveDirectoryUser> searchRes=new LinkedList<ActiveDirectoryUser>();
		SearchControls searchCtls = new SearchControls();
		searchCtls.setReturningAttributes(ADMapper.USER_ATTRIBUTES);
		searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		String searchFilter = "(objectclass=person)";
		
		try {
			
			NamingEnumeration<SearchResult> answer = contextFactory.getContext().search(orgunit+","+contextFactory.getDomainDC(), searchFilter, searchCtls);		
			searchRes=ADMapper.mapSearchResultToUser(answer);

		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return searchRes;
	}

	@Override
	public ActiveDirectoryUser findUserByPrincipalName(String userPrincipalName) {
		ActiveDirectoryUser res = null;
		SearchControls searchCtls = new SearchControls();
		searchCtls.setReturningAttributes(ADMapper.USER_ATTRIBUTES);
		searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		String searchFilter = "(&((userPrincipalName="+userPrincipalName+")(objectclass=person)))";
		
		try {
			
			NamingEnumeration<SearchResult> answer = contextFactory.getContext().search(contextFactory.getDomainDC(), searchFilter, searchCtls);		
			List<ActiveDirectoryUser> searchRes=ADMapper.mapSearchResultToUser(answer);
			if(searchRes != null && searchRes.size() > 0){
				res=searchRes.get(0);
			}
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public ActiveDirectoryUser findUserByUserName(String principal, String organizationalUnit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean authenticate(String userName, String password) {
		boolean res=false;
		try{
			passwordContextFactory.setUserName(userName);
			passwordContextFactory.setPassword(password);
			LdapContext context = passwordContextFactory.getContext();
			if(context!=null){
				res=true;
			}
		}catch(Exception e){
			logger.error(e);
		}
		return res;
	}



	private boolean changePassword(String userName, String newPassword,String oldPassword,
			boolean checkPassword) throws ChangePasswordException,
			UserNotFoundException, IncorrectPasswordException {
		try {
			//test Existance of user
			ActiveDirectoryUser user = findUserByPrincipalName(userName + "@" + contextFactory.getDomain());
			if (user == null) {
				throw new UserNotFoundException("UserName ("+userName+") cannot found in the system.");
			}
			if(checkPassword){
				//test Current password
				passwordContextFactory.setPassword(oldPassword);
				passwordContextFactory.setUserName(userName);
				LdapContext passwordContext = passwordContextFactory.getContext();
				if (passwordContext == null) {
					throw new IncorrectPasswordException(
							"Invalid password exception");
				}
			}
			//reset to null for subsequent call
			passwordContextFactory.setLdapContext(null);
			ModificationItem[] mods = new ModificationItem[1];
			String quotedPassword = "\"" + newPassword + "\"";
			byte[] newUnicodePassword = null;
			try {

				newUnicodePassword = quotedPassword.getBytes("UTF-16LE");

			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Attribute userPassword = new BasicAttribute("UnicodePwd",
					newUnicodePassword);
			mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
					userPassword);
			contextFactory.getContext().modifyAttributes(
					user.getDistinguishedName(), mods);
		} catch (Exception e2) {
			logger.error(e2);
			throw new ChangePasswordException(e2);
		}
		return true;
	}

	@Override
	public boolean resetPassword(String userName, String newPassword)
			throws ChangePasswordException, UserNotFoundException {
		boolean res=false;
		try {
			res=changePassword(userName, newPassword, null,false);
		} catch (IncorrectPasswordException e) {
			logger.error(e);
		}
		return res;
	}

	@Override
	public boolean changePassword(String userName,String newPassword, String oldPassword
			) throws ChangePasswordException,
			UserNotFoundException, IncorrectPasswordException {
			
		return changePassword(userName, newPassword, oldPassword,true);
	}

	@Override
	public ActiveDirectoryUser findUserByUserName(String userName) {
		return findUserByPrincipalName(userName + "@" + contextFactory.getDomain());
	}

	@Override
	public PaginatedResult<ActiveDirectoryUser> findAllForPagination(
			int pageNo, int pageSize, Order... order) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginatedResult<ActiveDirectoryUser> findAllForPagination(
			ActiveDirectoryUser e, int pageNo, int pageSize, Order... order) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginatedResult<ActiveDirectoryUser> findAllForPagination(
			ActiveDirectoryUser e, int pageNo, int pageSize,
			boolean ignoreCase, boolean enableLike, Order... orders) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count(ActiveDirectoryUser e) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.nec.asia.nic.framework.security.ldap.dao.UserADDao#findUserByCN(java.lang.String)
	 */
	@Override
	public ActiveDirectoryUser findUserByCN(String CnName) {
		ActiveDirectoryUser res = null;
		SearchControls searchCtls = new SearchControls();
		searchCtls.setReturningAttributes(ADMapper.USER_ATTRIBUTES);
		searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		String searchFilter = "(&((CN="+CnName+")(objectclass=person)))";
		
		try {
			NamingEnumeration<SearchResult> answer = contextFactory.getContext().search(contextFactory.getDomainDC(), searchFilter, searchCtls);		
			List<ActiveDirectoryUser> searchRes=ADMapper.mapSearchResultToUser(answer);
			if(searchRes != null && searchRes.size() > 0){
				res=searchRes.get(0);
			}
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}


	



}
