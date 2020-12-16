package com.nec.asia.nic.framework.admin.code.dao;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import com.nec.asia.nic.framework.admin.GenericTestCase;
import com.nec.asia.nic.framework.admin.code.domain.Codes;
/**
 * Test sequence for methods will follow 
 * 1) the sequence order with @Test within the class 
 * 2) then follow @Test within the parent class.
 * 
 * @author chris_wong
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CodeDaoTestCase extends GenericTestCase<Codes, String, CodesDao> {
	
	@Override
	protected String getBeanName() {
		return "codesDao";
	}
	
	String codeId = "TEST_SITE_CODE";
	@Test
	public void test01Save() {
		logger.info("start Codes dao - save");
		try {
			Codes codeObj = new Codes(codeId, "The list of office and office code for testing", Boolean.TRUE, Boolean.TRUE, "NIC", "SYSTEM", "SYSTEM", new Date(), null, null, null, null, null, null, Boolean.FALSE, null);
			bean.save(codeObj);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in Save: "+e.getMessage());
			Assert.fail(e.getMessage());
		}
		logger.info(" end  Codes dao - save");
	}

	@Test
	public void test02Update() {
		logger.info("start Codes dao - update");
		try {
			Codes codeObj = bean.findById(codeId);
			Assert.assertNotNull("Code Object is not found", codeObj);
			if (codeObj!=null) {
				logger.debug("Codes t : "+codeId+"\n"+ReflectionToStringBuilder.toString(codeObj, ToStringStyle.MULTI_LINE_STYLE));
				codeObj.setUpdateBy("SYSTEM");
				codeObj.setUpdateDate(new Date());
				codeObj.setUpdateWkstnId("SYSTEM");
				codeObj.setAdminAccessibleFlag(Boolean.FALSE);
				codeObj.setDeleteBy("ADMIN");
				codeObj.setDeleteWkstnId("SYSTEM");
				codeObj.setDeleteDate(new Date());
				codeObj.setDeleteFlag(Boolean.FALSE);
				
				bean.saveOrUpdate(codeObj);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		logger.info(" end  Codes dao - update");		
	}

	@Test
	public void test03FindById() {
		logger.info("start Codes dao - find");

		try {
			Codes codeObj = bean.findById(codeId);
			if (codeObj!=null) {
				String message = String.format("Code[%2s] %-30s : %-80s", 1, codeObj.getCodeId(), codeObj.getCodeDesc());
				logger.debug(message);
			}
			//logger.info("Codes codeObj : "+codeObj+"\n"+ReflectionToStringBuilder.toString(codeObj, ToStringStyle.MULTI_LINE_STYLE));
			Assert.assertNotNull("Code Object is not found", codeObj);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		logger.info(" end  Codes dao - find");		
	}

	@Test
	public void test04FindAll() {
		logger.info("start Codes dao - findAll");
		try {
			List<Codes> codeList = bean.findAll();

			int count = 0;
			if (!CollectionUtils.isEmpty(codeList)) {				
				for (Codes codeObj : codeList) {
					count++;
					String message = String.format("Code[%2s] %-30s : %-80s", count, codeObj.getCodeId(), codeObj.getCodeDesc());
					logger.debug(message);
					//logger.info("Codes ["+(++count)+"] : "+codeObj+"\n"+ReflectionToStringBuilder.toString(codeObj, ToStringStyle.SHORT_PREFIX_STYLE));
				}
			}
			Assert.assertNotNull("codeList is not found", codeList);
			Assert.assertNotSame("codeList count should not be zero", count, 0);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		logger.info(" end  Codes dao - findAll");
	}

	@Test
	public void test05FindAllByExample() {
		logger.info("start Codes dao - findAllByExample");
		try {
			Codes d = new Codes();
			//d.setDeleteFlag(Boolean.FALSE);
			d.setUserAccessibleFlag(Boolean.TRUE);
			List<Codes> codeList = bean.findAll(d);

			int count = 0;
			if (!CollectionUtils.isEmpty(codeList)) {
				for (Codes codeObj : codeList) {
					count++;
					String message = String.format("Code[%2s] %-30s : %-80s", count, codeObj.getCodeId(), codeObj.getCodeDesc());
					logger.debug(message);
					//logger.info("Codes ["+(++count)+"] : "+codeObj+"\n"+ReflectionToStringBuilder.toString(codeObj, ToStringStyle.SIMPLE_STYLE));
				}
			}
			Assert.assertNotNull("Active codeList is not found", codeList);
			Assert.assertNotSame("Active codeList count should not be zero", count, 0);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
		logger.info(" end  Codes dao - findAllByExample");		
	}

	@Test
	public void test99Delete() {
		logger.info("start Codes dao - delete");
		try {
			Codes codeObj = bean.findById(codeId);
			if (codeObj!=null) {
				bean.delete(codeObj);
			} else {
				logger.warn("Codes[{}] not exists.", codeId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
		logger.info(" end  Codes dao - delete");
	}

	/* test customized method 150 to 199 */
	@Test
	public void test150GetCodeValuesByCodeID() {
		logger.info("[getCodeValuesByCodeID]");
		String codeId = "JOB_TYPE";
		try {
			Map<String, String> codeValues = bean.getCodeValuesByCodeID(codeId);
			logger.debug("Codes ["+codeId+"] : \t"+codeValues);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
		logger.info("[getCodeValuesByCodeID] completed");
	}
}
