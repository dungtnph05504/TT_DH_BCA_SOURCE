package com.nec.asia.nic.comp.listHandover.dao;

import java.util.List;

import com.nec.asia.nic.comp.listHandover.domain.NicListHandover;
import com.nec.asia.nic.comp.listHandover.domain.NicPersoLocations;
import com.nec.asia.nic.comp.trans.dto.AssignmentFilterAll;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.domain.PaymentDef;
import com.nec.asia.nic.framework.admin.code.domain.PaymentDefId;
import com.nec.asia.nic.framework.dao.GenericDao;

public interface PersoLocationsDao extends GenericDao<NicPersoLocations, Long> {
	//default methods from super class
	//public PaymentDef findById(PaymentDefId id);
	//public Long save (PaymentDef e);
	//public void saveOrUpdate(PaymentDef e);
    //public PaymentDef merge(PaymentDef e);
	//public void delete(PaymentDef e);
	//public void delete(PaymentDefId id);
	//public List<PaymentDef> findAll ();

	public List<NicPersoLocations> findPersoLocationsById(long id) throws Exception;
	
	public PaginatedResult<NicPersoLocations> findAllPersoLocations(int PageNo, int PageSize, AssignmentFilterAll assignmentFilter) throws Exception;
	
	public boolean createPersoLocations(NicPersoLocations persoLocation) throws Exception;
	
	public NicPersoLocations findPersoByCode(String code, Long persoId);
	public NicPersoLocations findPersoByCodeAnd(String code, Long persoId);
	public List<NicPersoLocations> findPersoByStatus(Integer status, String code);
}
