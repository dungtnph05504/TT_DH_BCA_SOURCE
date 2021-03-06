/**
 * 
 */
package com.nec.asia.nic.framework.admin.code.service;

import java.util.List;

import org.hibernate.criterion.Order;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.domain.ParametersId;
import com.nec.asia.nic.framework.admin.code.exception.ParametersServiceException;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.framework.service.BusinessService;

/**
 * @author sailaja_chinapothula
 * @Company: NEC Asia Pacific Ltd
 * @Since: Jun 24, 2013
 */

/*
 * Modification History:
 * 25 Jan 2016 (Tue): Added findAllActForPagination method to get all params have delete_flg = true.
 */
public interface ParametersService extends BusinessService<Parameters, ParametersId>{
	
	/* To find all the results in Pagination */
	public PaginatedResult<Parameters> findAllForPaginationByScope(String paramScope, int pageNo, int pageSize) throws ParametersServiceException;

	public PaginatedResult<Parameters> findAllForPagination( int pageNo, int pageSize) throws ParametersServiceException;
    public Parameters getParamDetails(String paramScope , String paramName) throws ParametersServiceException;
    public List<Parameters> getParamDetailsList(String paramScope) throws ParametersServiceException;
    public String  saveParam(Parameters parameter) throws ParametersServiceException;
    public String  updateParam(Parameters parameter) throws ParametersServiceException;
    public String  deleteParam(Parameters parameter) throws ParametersServiceException;
    public PaginatedResult<Parameters> findAllActForPagination( int pageNo, int pageSize, Order orders) throws ParametersServiceException, DaoException;

	public PaginatedResult<Parameters> getPageParamByParaName(String parameter,
			int pageNo, int pageSize, Order hibernateOrder);
}
