package com.nec.asia.nic.framework.admin.code.dao;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import com.nec.asia.nic.framework.admin.GenericTestCase;
import com.nec.asia.nic.framework.admin.code.domain.Codes;
import com.nec.asia.nic.framework.admin.site.dao.SiteRepositoryDao;
import com.nec.asia.nic.framework.admin.site.domain.SiteGroups;
import com.nec.asia.nic.framework.admin.site.domain.SiteRepository;

public class SiteRepositoryDaoTestCase extends GenericTestCase<SiteRepository, String, SiteRepositoryDao> {

	@Override
	protected String getBeanName() {
		return "siteRepositoryDao";
	}
	
	String siteId = "TEST";
	@Override
	public void test01Save() {
		logger.info("start SiteRepository dao - save");
		try {
			SiteRepository siteObj = new SiteRepository();
			siteObj.setSiteId(siteId);
			siteObj.setSiteName("NIC TEST SITE");
			siteObj.setActiveIndicator("Y");
			siteObj.setAuthority("NIC");
			bean.save(siteObj);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		logger.info(" end  SiteRepository dao - save");
	}

	@Override
	public void test02Update() {
		logger.info("start SiteRepository dao - update");
		try {
			int version = 0;
			SiteRepository siteObj = bean.findById(siteId);
			Assert.assertNotNull("Code Object is not found", siteObj);
			if (siteObj!=null) {
				logger.debug("SiteRepository t : "+siteObj+"\t"+ReflectionToStringBuilder.toString(siteObj, ToStringStyle.SHORT_PREFIX_STYLE));
				if (siteObj.getUpdateVersion()!=null) {
					version = siteObj.getUpdateVersion().intValue()+1;
				}
				siteObj.setUpdateVersion(version);
				
				bean.saveOrUpdate(siteObj);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		logger.info(" end  SiteRepository dao - update");		
	}

	@Override
	public void test03FindById() {
		logger.info("start SiteRepository dao - find");

		try {
			SiteRepository siteObj = bean.findById(siteId);
			logger.debug("SiteRepository siteObj : "+siteObj+"\n"+ReflectionToStringBuilder.toString(siteObj, ToStringStyle.MULTI_LINE_STYLE));
			Assert.assertNotNull("Code Object is not found", siteObj);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		logger.info(" end  SiteRepository dao - find");		
	}

	@Override
	public void test04FindAll() {
		logger.info("start SiteRepository dao - findAll");
		try {
			List<SiteRepository> siteList = bean.findAll();

			int count = 0;
			if (!CollectionUtils.isEmpty(siteList)) {				
				for (SiteRepository siteObj : siteList) {
					++count;
					logger.debug("SiteRepository ["+(count)+"] : "+ReflectionToStringBuilder.toString(siteObj, ToStringStyle.SHORT_PREFIX_STYLE));
				}
				logger.info("SiteRepository size["+(count)+"] ");
			}
			Assert.assertNotNull("codeList is not found", siteList);
			Assert.assertNotSame("codeList count should not be zero", count, 0);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		logger.info(" end  SiteRepository dao - findAll");
	}

	@Override
	public void test05FindAllByExample() {
		logger.info("start SiteRepository dao - findAllByExample");
		try {
			SiteRepository d = new SiteRepository();
			d.setActiveIndicator("Y");
			//d.setSiteGroups(new SiteGroups("04"));
			List<SiteRepository> siteList = bean.findAll(d);

			int count = 0;
			if (!CollectionUtils.isEmpty(siteList)) {
				for (SiteRepository siteObj : siteList) {
					logger.debug("SiteRepository ["+(++count)+"] : "+ReflectionToStringBuilder.toString(siteObj, ToStringStyle.SIMPLE_STYLE));
				}
				logger.info("SiteRepository size["+(count)+"] ");
			}
			Assert.assertNotNull("Active siteList is not found", siteList);
			Assert.assertNotSame("Active siteList count should not be zero", count, 0);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		logger.info(" end  SiteRepository dao - findAllByExample");		
	}
	

	@Override
	public void test99Delete() {
		logger.info("start SiteRepository dao - delete");
		try {
			SiteRepository siteObj = bean.findById(siteId);
			if (siteObj!=null) {
				bean.delete(siteObj);
			} else {
				logger.warn("SiteRepository[{}] not exists.", new Object[] {siteId});
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
		logger.info(" end  SiteRepository dao - delete");
	}

	/* test customized method */
	@Test
	public void testFindBySiteId() {
		logger.info("[findBySiteId]");
		String siteId = "RICHQ";
		try {
			SiteRepository siteObj = bean.findBySiteId(siteId);
			logger.debug("SiteRepository ["+siteId+"] : "+siteObj+ReflectionToStringBuilder.toString(siteObj, ToStringStyle.MULTI_LINE_STYLE));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		logger.info("[findBySiteId] Completed");
	}

}
