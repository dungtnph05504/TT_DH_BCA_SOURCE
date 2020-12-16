package com.nec.asia.nic.comp.trans.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.asia.nic.comp.listHandover.domain.NicPersoLocations;
import com.nec.asia.nic.comp.listHandover.service.PersoLocationsService;
import com.nec.asia.nic.comp.trans.dao.ConfigurationWorkflowDao;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.ConfigurationWorkflow;
import com.nec.asia.nic.comp.trans.dto.NicUploadJobDto;
import com.nec.asia.nic.comp.trans.service.ConfigurationWorkflowService;
import com.nec.asia.nic.comp.ws.log.domain.CreateMessageException;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.rbac.domain.Users;
import com.nec.asia.nic.framework.admin.rbac.service.UserService;
import com.nec.asia.nic.framework.admin.site.domain.SiteGroups;
import com.nec.asia.nic.framework.admin.site.domain.SiteRepository;
import com.nec.asia.nic.framework.admin.site.service.SiteService;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;
import com.nec.asia.nic.utils.HelperClass;


@Service("configurationWorkflowService")
public class ConfigurationWorkflowServiceImpl extends DefaultBusinessServiceImpl<ConfigurationWorkflow, Long, ConfigurationWorkflowDao> implements ConfigurationWorkflowService{

	@Autowired
	private ConfigurationWorkflowDao dao;
	
	@Autowired
	private SiteService siteService;
	
	@Autowired
	private PersoLocationsService persoService;
	
	@Autowired
	private UserService userService;
	
	@Override
	public List<ConfigurationWorkflow> findSiteRepositoryByActive(
			String siteGroup, String type, Date todayTime, Integer me, Boolean stage) {
		// TODO Auto-generated method stub
		return dao.findSiteRepositoryByActive(siteGroup, type, todayTime, me, stage);
	}

	@Override
	public ConfigurationWorkflow findSiteRepositoryByType(String siteGroup,
			String siteCode, String type) {
		// TODO Auto-generated method stub
		return dao.findSiteRepositoryByType(siteGroup, siteCode, type);
	}

	@Override
	public Boolean saveOrUpdateConfig(ConfigurationWorkflow cf) {
		try {
			dao.saveOrUpdate(cf);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public BaseModelSingle<ConfigurationWorkflow> findSiteRepositoryBySite(String siteCode,
			Date todayTime, String type, Boolean stage) {
		try {
			return dao.findSiteRepositoryBySite(siteCode, todayTime, type, stage);
		} catch (Exception e) {
			return new BaseModelSingle<ConfigurationWorkflow>(null, false,  CreateMessageException.createMessageException(e) + " - findSiteRepositoryBySite - " + siteCode + " - thất bại.");
		}

	}

	@Override
	public PaginatedResult<NicUploadJobDto> findPaginateBySearch(
			String configType, String stage, String siteFrom, Date todayTime,
			int pageNo, int pageSize) {
		try {
			PaginatedResult<ConfigurationWorkflow> pr = dao.findPaginateBySearch(configType, stage, siteFrom, todayTime, pageNo, pageSize);
			List<NicUploadJobDto> list = new ArrayList<NicUploadJobDto>();
			if(pr != null){
				List<ConfigurationWorkflow> listCf = pr.getRows();
				int i = (pageNo - 1) * pageSize;
				for(ConfigurationWorkflow cfw : listCf){
					NicUploadJobDto dto = new NicUploadJobDto();
					i++;
					dto.setStt(i);
					dto.setUploadJobId(cfw.getId());
					dto.setStylePP(cfw.getStage() ? 1 : 0);
					if(StringUtils.isNotEmpty(cfw.getSiteIdFrom())){
						SiteRepository site = siteService.getSiteRepoById(cfw.getSiteIdFrom());
						dto.setSiteFrom(site != null ? site.getSiteName() : "");
					}
					if(StringUtils.isNotEmpty(cfw.getCreateBy())){
						Users users = userService.findByUserId(cfw.getCreateBy());
						dto.setCreateBy(users != null ? users.getUserName() : "");
					}
					switch (cfw.getConfigType()) {
						case "XL":
							dto.setJobType("Luồng xử lý");
							SiteGroups sg = siteService.findSiteGroupsByGroupId(cfw.getSiteIdTo());
							dto.setSiteTo(sg != null ? sg.getGroupName() : "");
							break;
						case "IN":
							dto.setJobType("Luồng cá thể hóa");
							NicPersoLocations perso = persoService.findPersoByCode(cfw.getSiteIdTo(), null);
							dto.setSiteTo(perso != null ? perso.getName() : "");
							break;
						case "PH":
							dto.setJobType("Luồng phát hành");
							SiteRepository site = siteService.getSiteRepoById(cfw.getSiteIdTo());
							dto.setSiteTo(site != null ? site.getSiteName() : "");
							break;
						default:
							break;
					}
					if(cfw.getDateTimeFrom() != null){
						dto.setDateFrom(HelperClass.convertDateToString1(cfw.getDateTimeFrom()));
					}
					if(cfw.getDateTimeTo() != null){
						dto.setDateTo(HelperClass.convertDateToString1(cfw.getDateTimeTo()));
					}
					ConfigurationWorkflow cf = dao.findSiteRepositoryBySite(cfw.getSiteIdFrom(), todayTime , cfw.getConfigType(), true).getModel();
					dto.setTransactionStatus(cf != null ? "Còn hiệu lực" : "Hết hiệu lực");
					list.add(dto);
				}
				return new PaginatedResult<>(pr.getTotal(), pageNo, list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new PaginatedResult<>(0, pageNo, new ArrayList<NicUploadJobDto>());
	}

	@Override
	public ConfigurationWorkflow findConfigById(Long id, Boolean stage) {
		// TODO Auto-generated method stub
		return dao.findConfigById(id, stage);
	}

}
