package com.nec.asia.nic.comp.trans.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Order;

import com.nec.asia.nic.comp.trans.domain.BorderGate;
import com.nec.asia.nic.comp.trans.domain.NicSearchHitResult;
import com.nec.asia.nic.comp.trans.domain.NicSearchResult;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.domain.PersoSites;
import com.nec.asia.nic.comp.trans.dto.AssignmentFilter;
import com.nec.asia.nic.comp.trans.dto.AssignmentFilterAll;
import com.nec.asia.nic.comp.trans.service.exception.NicUploadJobServiceException;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.GenericDao;
/**
 * Nic Upload Job Dao interface: To retrieve, save/update the job.
 * 
 * @author sailaja_chinapothula
 */


public interface PersoSitesDao extends GenericDao<PersoSites, Long> {
	
	List<PersoSites> findAllPersoSites(Long idPerso);
	
	PaginatedResult<PersoSites> findPagiPersoSites(Long idPerso, int pageNo, int pageSize);
	
	PersoSites findPersoSitesById(Long idPerso, String siteId);
	
	PersoSites findPersoSitesById(Long id);
	
	PersoSites findPersoSitesByCode(String siteId);
}
