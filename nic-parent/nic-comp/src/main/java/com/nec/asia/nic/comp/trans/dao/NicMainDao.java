package com.nec.asia.nic.comp.trans.dao;

import java.util.List;

import org.hibernate.criterion.Order;

import com.nec.asia.nic.comp.trans.domain.NicMain;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.GenericDao;

public interface NicMainDao extends GenericDao<NicMain, Long> {
	//default methods from super class
	//public NicMain findById(Long nicId);
	//public Long save (NicMain e);
	//public void saveOrUpdate(NicMain e);
    //public NicMain merge(NicMain e);
	//public void delete(NicMain e);
	//public void delete(Long id);
	//public List<NicMain> findAll ();
	
	/**
	 * To check NicMain existence by using nin.
	 * 
	 * @param nin The nin of the NIC Holder/Applicant.
	 */
	public boolean checkNicMainExistsByNin(String nin);
	
	/**
	 * To inquiry NicMain by search criteria.
	 * 
	 */
	public List<NicMain> findAllByFields(String transactionId, String nin, String ccn, 
			String surname,	String firstName, String surnameAtBirth, String sex, String dob);

	public PaginatedResult<NicMain> findAllForPaginationByFilter(
			NicMain nicMain, int pageNo, int pageSize, boolean ignoreCase,
			boolean enableLike, Order order);
}
