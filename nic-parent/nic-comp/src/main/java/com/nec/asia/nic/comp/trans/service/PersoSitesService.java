package com.nec.asia.nic.comp.trans.service;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.BorderGate;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.PersoSites;
import com.nec.asia.nic.comp.trans.dto.DetailHandoverDto;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.service.BusinessService;

public interface PersoSitesService extends
	BusinessService<PersoSites, Long> {

	public List<PersoSites> findAllPersoSites(Long idPerso);
	
	public PersoSites findPersoSitesById(Long idPerso, String siteId);
	
	public PersoSites findPersoSitesByCode(String siteId);
	
	public PersoSites findPersoSitesById(Long id);
	
	public Boolean createNew(PersoSites perso);
	
	PaginatedResult<DetailHandoverDto> findPagiPersoSites(Long idPerso, int pageNo, int pageSize);
	
	public void delSiteLocation(PersoSites sites);
}
