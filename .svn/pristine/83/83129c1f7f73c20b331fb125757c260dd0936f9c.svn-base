package com.nec.asia.nic.comp.trans.dao;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.EppBlacklist;
import com.nec.asia.nic.framework.dao.GenericDao;

/* 
 * Modification History:
 *  
 * 11 Dec 2013 (chris): init class. 
 */
public interface NicBlacklistDao extends GenericDao<EppBlacklist, Long> {
	
	public List<EppBlacklist> findByNin (String nin);
	
	public List<EppBlacklist> findByInfoPerson (String fullname, String dob);
	
	public List<EppBlacklist> findByMutilCriterion(String fullname, String dob, String nin);
}
