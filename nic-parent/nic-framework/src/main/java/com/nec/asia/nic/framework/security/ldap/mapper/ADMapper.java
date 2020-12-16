package com.nec.asia.nic.framework.security.ldap.mapper;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;



import com.nec.asia.nic.framework.security.ldap.domain.ActiveDirectoryUser;

public class ADMapper {
	private static Logger logger =  Logger.getLogger(ADMapper.class);
	

	public static int UF_PASSWD_NOTREQD = 0x0020;
	public static int UF_PASSWD_CANT_CHANGE = 0x0040;
	public static int UF_NORMAL_ACCOUNT = 0x0200;
	public static int UF_DONT_EXPIRE_PASSWD = 0x10000;
	public static int ACCOUNTDISABLE = 	0x0002;
	
	public static String[] USER_ATTRIBUTES = {
    	"cn","sAMAccountName","userPrincipalName","uid","givenName","initials","sn",
    	"streetAddress","l","st","co","postalCode","telephoneNumber","mobile","homePhone",
    	"homePhone","ipPhone","description","title","physicalDeliveryOfficeName","mail",
    	"department","unicodePwd","distinguishedName","company","userAccountControl","unicodePwd","officer"
    };
	
	
	//int UF_PASSWORD_EXPIRED = 0�800000;
	public static Attributes mapUserToAttributes(ActiveDirectoryUser user){
		Attributes container = new BasicAttributes();
		Attribute objClasses = new BasicAttribute("objectClass");
        objClasses.add("top");
        objClasses.add("person");
        objClasses.add("organizationalPerson");
        objClasses.add("user");
        addAttribute(container, objClasses);
        addAttribute(container,"cn", getNullSafeString(user.getCN()));
        addAttribute(container, "sAMAccountName", getNullSafeString(user.getUserName()));
        addAttribute(container, "userPrincipalName",getNullSafeString(user.getuserPrincipalName()));
        addAttribute(container, "uid", getNullSafeString(user.getUID()));
        addAttribute(container, "givenName", getNullSafeString(user.getFirstName()));
        addAttribute(container, "initials", getNullSafeString(user.getMiddleInitial()));
        addAttribute(container, "sn", getNullSafeString(user.getLastName()));
        addAttribute(container, "streetAddress", getNullSafeString(user.getStreetAddress()));
        addAttribute(container, "l", getNullSafeString(user.getCity()));
        addAttribute(container, "st", getNullSafeString(user.getProvinceOrState()));
        addAttribute(container, "co", getNullSafeString(user.getCountry()));
        addAttribute(container, "postalCode", getNullSafeString(user.getPostalCode()));
        addAttribute(container, "telephoneNumber", getNullSafeString(user.getMainPhone()));
        addAttribute(container, "mobile", getNullSafeString(user.getMobile()));
        addAttribute(container, "homePhone", getNullSafeString(user.getHomePhone()));
        addAttribute(container, "ipPhone", getNullSafeString(user.getIpPhone()));
        addAttribute(container, "description", getNullSafeString(user.getDescription()));
        addAttribute(container, "title", getNullSafeString(user.getJobTitle()));
        addAttribute(container, "company",getNullSafeString(user.getCompany()));
        addAttribute(container, "physicalDeliveryOfficeName", getNullSafeString(user.getOfficeAddress()));
        addAttribute(container, "mail", getNullSafeString(user.getEmail()));
        addAttribute(container, "department", getNullSafeString(user.getDepartment()));
        addAttribute(container, "officer", getNullSafeString(user.getOfficer()));
        
    	String quotedPassword = "\"" + user.getPassword() + "\"";
    	byte[] newUnicodePassword=null;
        try {
        
        	newUnicodePassword = quotedPassword.getBytes("UTF-16LE");
			
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			logger.error(e1);
		}
        
        Attribute userPassword = new BasicAttribute("unicodePwd", newUnicodePassword);
        Attribute userCont = new BasicAttribute("userAccountControl",Integer.toString(UF_NORMAL_ACCOUNT + UF_PASSWD_NOTREQD + UF_DONT_EXPIRE_PASSWD ));
        container.put(userPassword);
        container.put(userCont);
        return container;
	}
	
	public static ModificationItem[] getModificationItems(ActiveDirectoryUser user){
		List<ModificationItem> modif = new LinkedList<ModificationItem>();
		/*
		 * commented line signify that active directory does not allow to update
		 */
//    	addModificationItem(modif,LdapContext.REPLACE_ATTRIBUTE,"cn", getNullSafeString(user.getCN()));
//		addModificationItem(modif,LdapContext.REPLACE_ATTRIBUTE,"sAMAccountName", getNullSafeString(user.getUserName()));
//		addModificationItem(modif,LdapContext.REPLACE_ATTRIBUTE,"userPrincipalName",getNullSafeString(user.getuserPrincipalName()));
//		addModificationItem(modif,LdapContext.REPLACE_ATTRIBUTE,"uid", getNullSafeString(user.getUID()));
		addModificationItem(modif,LdapContext.REPLACE_ATTRIBUTE,"givenName", getNullSafeString(user.getFirstName()));
		addModificationItem(modif,LdapContext.REPLACE_ATTRIBUTE,"initials", getNullSafeString(user.getMiddleInitial()));
		addModificationItem(modif,LdapContext.REPLACE_ATTRIBUTE,"sn", getNullSafeString(user.getLastName()));
		addModificationItem(modif,LdapContext.REPLACE_ATTRIBUTE,"streetAddress", getNullSafeString(user.getStreetAddress()));
		addModificationItem(modif,LdapContext.REPLACE_ATTRIBUTE,"l",getNullSafeString(user.getCity()));
		addModificationItem(modif,LdapContext.REPLACE_ATTRIBUTE,"st", getNullSafeString(user.getProvinceOrState()));
		addModificationItem(modif,LdapContext.REPLACE_ATTRIBUTE,"co",getNullSafeString(user.getCountry()));
		addModificationItem(modif,LdapContext.REPLACE_ATTRIBUTE,"postalCode",getNullSafeString(user.getPostalCode()));
		addModificationItem(modif,LdapContext.REPLACE_ATTRIBUTE,"telephoneNumber", getNullSafeString(user.getMainPhone()));
		addModificationItem(modif,LdapContext.REPLACE_ATTRIBUTE,"mobile", getNullSafeString(user.getMobile()));
		addModificationItem(modif,LdapContext.REPLACE_ATTRIBUTE,"homePhone", getNullSafeString(user.getHomePhone()));
		addModificationItem(modif,LdapContext.REPLACE_ATTRIBUTE,"ipPhone", getNullSafeString(user.getIpPhone()));
		addModificationItem(modif,LdapContext.REPLACE_ATTRIBUTE,"description", getNullSafeString(user.getDescription()));
		addModificationItem(modif,LdapContext.REPLACE_ATTRIBUTE,"title", getNullSafeString(user.getJobTitle()));
		addModificationItem(modif,LdapContext.REPLACE_ATTRIBUTE,"physicalDeliveryOfficeName", getNullSafeString(user.getOfficeAddress()));
		addModificationItem(modif,LdapContext.REPLACE_ATTRIBUTE,"mail", getNullSafeString(user.getEmail()));
		addModificationItem(modif,LdapContext.REPLACE_ATTRIBUTE,"department", getNullSafeString(user.getDepartment()));
		addModificationItem(modif,LdapContext.REPLACE_ATTRIBUTE,"officer", getNullSafeString(user.getOfficer()));
//		addModificationItem(modif,LdapContext.REPLACE_ATTRIBUTE,"distinguishedName", getNullSafeString(user.getDistinguishedName()));
     	int uac = UF_NORMAL_ACCOUNT + UF_PASSWD_NOTREQD + UF_DONT_EXPIRE_PASSWD;
        if(user.getUserAccountControl() >= 0){
        	uac = user.getUserAccountControl();
        }
        Attribute userCont = new BasicAttribute("userAccountControl",Integer.toString(uac));
        
        modif.add(new ModificationItem(LdapContext.REPLACE_ATTRIBUTE, userCont));
        ModificationItem[] rets = new ModificationItem[modif.size()];
		modif.toArray(rets);
		return rets;
	}
	
	private static void addModificationItem(List<ModificationItem> modif,int ldapOperation,String attributeName,String attributeValue){
		if(StringUtils.isNotBlank(attributeValue)){
			modif.add(new ModificationItem(ldapOperation, new BasicAttribute(attributeName, getNullSafeString(attributeValue))));
		}
	}
	
	private static String getNullSafeString(String string){
		return string==null?" ":string;
	}
	
	public static List<ActiveDirectoryUser> mapSearchResultToUser(NamingEnumeration<SearchResult> ldapSearchResult)
	{
		List<ActiveDirectoryUser> result = new LinkedList<ActiveDirectoryUser>();
	    
		while (ldapSearchResult.hasMoreElements())
	    {
	    	try {
				SearchResult sr = (SearchResult) ldapSearchResult.next();
				Attributes attr = sr.getAttributes();
				ActiveDirectoryUser user = new ActiveDirectoryUser();
				user.setFirstName(retrieveAttribute(attr,"givenName"));
				user.setMiddleInitial(retrieveAttribute(attr,"initials"));
				user.setLastName(retrieveAttribute(attr,"sn"));
				user.setStreetAddress(retrieveAttribute(attr,"streetAddress"));
				user.setCity(retrieveAttribute(attr,"l")) ;
				user.setProvinceOrState(retrieveAttribute(attr,"st")) ;
				user.setCountry(retrieveAttribute(attr,"co")) ;
				user.setPostalCode(retrieveAttribute(attr,"postalCode")) ;
				user.setMainPhone(retrieveAttribute(attr,"telephoneNumber")) ;
				user.setMobile(retrieveAttribute(attr,"mobile")) ;
				user.setHomePhone(retrieveAttribute(attr,"homePhone")) ;
				user.setIpPhone(retrieveAttribute(attr,"ipPhone")) ;
				user.setDescription(retrieveAttribute(attr,"description")) ;
				user.setOfficeAddress(retrieveAttribute(attr,"physicalDeliveryOfficeName"));
				user.setEmail(retrieveAttribute(attr,"mail"));
				user.setJobTitle(retrieveAttribute(attr, "title"));
				user.setDepartment(retrieveAttribute(attr,"department")) ;
				user.setCompany(retrieveAttribute(attr,"company")) ;
				user.setUserName(retrieveAttribute(attr,"sAMAccountName"));
				user.setDistinguishedName(retrieveAttribute(attr,"distinguishedName")) ;
				String userAccesControl = retrieveAttribute(attr, "userAccountControl");
				
				int uac = 0;
				
				try {
					uac = Integer.parseInt(userAccesControl);
				} catch (NumberFormatException e) {
					logger.error(e);
				}
				user.setUserAccountControl(uac);
				String dn = user.getDistinguishedName();
				StringTokenizer st = new StringTokenizer(dn, ",");
				
				boolean dcfound=false;
				boolean userCnfound=false;
				String userCn="";
				String userOu="";
				String userdc="";
				while (st.hasMoreTokens()) {
					String token = st.nextToken();
					if (!userCnfound) {
						userCn = token;
						userCnfound = true;
						continue;
					}
					if (!dcfound) {
						
						if (token.contains("DC=") || token.contains("dc=")) {
							dcfound = true;
						
						}
						if (userOu.length() == 0) {
							userOu = token;
							continue;
						} else if (!(token.contains("DC=") || token.contains("dc="))) {
							userOu = userOu + "," + token;
							continue;
						}
					}

					if (userdc.length() == 0) {
						userdc = token;
						continue;
					} else {
						userdc = userdc + "," + token;
						continue;
					}
				}
				String sub = userdc.replace("DC=", "dc=");
				sub = sub.replace("dc=", "");
				sub = sub.replace(",",".");
				user.setDomain(sub);
				user.setOrganizationalUnitString(userOu);
				result.add(user);
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e);
			}
	    	
	    }
	    return result;
	}
	
	private static String retrieveAttribute(Attributes attribs,String key){
		Attribute attrib = attribs.get(key);
		if(attrib!=null){
			String tanay = "";
					
			try {
				tanay=attrib.get().toString();
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				logger.error(e);
			}
			return tanay;
		}
		return null;
	}

	private static void addAttribute(Attributes container,Attribute attribute){
		container.put(attribute);
	}
	
	private static void addAttribute(Attributes container,String attributeName,String attributeValue){
		if(StringUtils.isNotBlank(attributeValue))
			container.put(new BasicAttribute(attributeName,attributeValue));
	}
	
    private static String toDC(String domainName) {
        StringBuilder buf = new StringBuilder();
        for (String token : domainName.split("\\.")) {
            if(token.length()==0)   continue;   // defensive check
            if(buf.length()>0)  buf.append(",");
            buf.append("DC=").append(token);
        }
        return buf.toString();
    }
}
