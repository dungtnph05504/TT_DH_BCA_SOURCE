package com.nec.asia.nic.framework.admin.code.dao;

import java.util.List;
import java.util.Properties;

import org.hibernate.criterion.Order;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.domain.ParametersId;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.framework.dao.GenericDao;

/*
 * Modification History:
 * 25 Jan 2016 (Tue): Added findAllActForPagination method to get all params have delete_flg = true.
 */
public interface ParametersDao extends GenericDao<Parameters, ParametersId> {

	public List<Parameters> findAllByScope (String paraScope);
	public void savePendingParameter(Parameters pending);
	public boolean updateParameter(Parameters param);
	public boolean deleteParameter(Parameters param);

	//public PaginatedResult<Parameters> findAllForPagination(String paramScope, int pageNo, int pageSize) throws Exception;

	public PaginatedResult<Parameters> findAllForPaginationByScope(String paraScope, int pageNo, int pageSize);
	
	public List<Parameters> findMatchingParameters(String paraNameSystemSiteCode);
	
	//APARNA CHANGES START 06/01/2014
	public boolean resetTransactionIdSequence();
	public boolean resetCollectionSlipSequence();
	public void updateAsSequenceResetDone(String paraScope,String paraName);
	//APARNA CHANGES END 06/01/2014
    Properties readProperties(Parameters parameters) throws Exception;
    
    PaginatedResult<Parameters> findAllActForPagination(int pageNo, int pageSize, Order orders) throws DaoException;
	public PaginatedResult<Parameters> getPageParamByParaName(String parameter,
			int pageNo, int pageSize, Order hibernateOrder);
    

}
