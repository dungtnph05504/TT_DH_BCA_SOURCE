package com.nec.asia.nic.comp.trans.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.asia.nic.comp.listHandover.domain.NicPersoLocations;
import com.nec.asia.nic.comp.listHandover.service.PersoLocationsService;
import com.nec.asia.nic.comp.trans.dao.BorderGateDao;
import com.nec.asia.nic.comp.trans.dao.NicTransactionDao;
import com.nec.asia.nic.comp.trans.dao.PersoSitesDao;
import com.nec.asia.nic.comp.trans.domain.BorderGate;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.PersoSites;
import com.nec.asia.nic.comp.trans.dto.DetailHandoverDto;
import com.nec.asia.nic.comp.trans.service.BorderGateService;
import com.nec.asia.nic.comp.trans.service.PersoSitesService;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.site.domain.SiteRepository;
import com.nec.asia.nic.framework.admin.site.service.SiteService;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;

@Service("persoSitesService")
public class PersoSitesServiceImpl extends
	DefaultBusinessServiceImpl<PersoSites, Long, PersoSitesDao>	
	implements PersoSitesService {

	@Autowired
	private PersoSitesDao dao;
	
	@Autowired
	private PersoLocationsService locationsService;
	
	@Autowired
	private SiteService siteService;
	
	@Override
	public List<PersoSites> findAllPersoSites(Long idPerso) {
		try {
			return dao.findAllPersoSites(idPerso);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	@Override
	public PersoSites findPersoSitesById(Long idPerso, String siteId) {
		try {
			return dao.findPersoSitesById(idPerso, siteId);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	@Override
	public PersoSites findPersoSitesByCode(String siteId) {
		try {
			return dao.findPersoSitesByCode(siteId);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	@Override
	public Boolean createNew(PersoSites perso) {
		try {
			dao.saveOrUpdate(perso);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return false;
	}

	@Override
	public PaginatedResult<DetailHandoverDto> findPagiPersoSites(Long idPerso,
			int pageNo, int pageSize) {
		NicPersoLocations psl = locationsService.findPersoByCode(null, idPerso);
		try {
			PaginatedResult<DetailHandoverDto> result = null;
			PaginatedResult<PersoSites> pr = dao.findPagiPersoSites(idPerso, pageNo, pageSize);
			List<PersoSites> list = null;
			if(pr != null){
				list = pr.getRows();
				List<DetailHandoverDto> dtoList = new ArrayList<DetailHandoverDto>();
				for(PersoSites ps : list){
					DetailHandoverDto dto = new DetailHandoverDto();
					dto.setSitePerso(psl != null ? psl.getName() : "");
					SiteRepository site = siteService.getSiteRepoById(ps.getSiteId());
					dto.setSiteId(site != null ? site.getSiteName() : "");
					dto.setIdPerso(idPerso);
					dto.setCode(psl != null ? psl.getCode() : "");
					dto.setId(ps.getId());
					dtoList.add(dto);
				}
				result = new PaginatedResult<DetailHandoverDto>(pr.getTotal(), pageNo, dtoList);
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new PaginatedResult<DetailHandoverDto>(0, pageNo, new ArrayList<DetailHandoverDto>());
	}

	@Override
	public PersoSites findPersoSitesById(Long id) {
		try {
			return dao.findPersoSitesById(id);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public void delSiteLocation(PersoSites sites) {
		dao.delete(sites);
		
	}
}
