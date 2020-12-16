package com.nec.asia.nic.framework.admin.code.dao;
import java.io.File;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.CollectionUtils;

import com.nec.asia.nic.framework.admin.GenericTestCase;
import com.nec.asia.nic.framework.admin.code.dao.ParametersDao;
import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
import com.nec.asia.nic.framework.admin.code.domain.CodeValuesId;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.domain.ParametersId;
import com.nec.asia.nic.framework.admin.code.domain.type.ParameterType;
import com.nec.asia.nic.framework.admin.site.domain.SiteRepository;

public class ParametersDaoTestCase extends GenericTestCase<SiteRepository, ParametersId, ParametersDao> {
	
	private static String paraScope = "TEST";
	private static String paraName = "MAX_RECORDS_PER_SYSTEM";
	
	@Override
	protected String getBeanName() {
		return "parametersDao";
	}
	
	public ParametersDaoTestCase() {
		//init();
	}
	
	public void test01Save() {
		logger.info("start Parameters dao - save");
		try {
			ParametersId id = new ParametersId(paraScope, paraName);
			Parameters paraObj = new Parameters(id , "20", null, "Maximum Record.", ParameterType.Integer, true, true, "NIC", "SYSTEM", "SYSTEM", new Date(), null, null, null, null, null, null, false);
			paraObj.setParaShortValue("20");
			paraObj.setParaLobValue(null);
			
			bean.save(paraObj);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		logger.info(" end  Parameters dao - save");
	}
	
	public void test02Update() {
		logger.info("start Parameters dabean. update");
		try {
			ParametersId id = new ParametersId(paraScope, paraName);
			Parameters paraObj = bean.findById(id);
			Assert.assertNotNull("Parameters Object is not found", paraObj);
			if (paraObj!=null) {
				logger.debug("ParametersId t : "+paraObj+"\n"+ReflectionToStringBuilder.toString(paraObj, ToStringStyle.MULTI_LINE_STYLE));
				paraObj.setUpdateBy("SYSTEM");
				paraObj.setUpdateDate(new Date());
				paraObj.setUpdateWkstnId("SYSTEM");
				paraObj.setDeleteBy("ADMIN");
				paraObj.setDeleteWkstnId("SYSTEM");
				paraObj.setDeleteDate(new Date());
				paraObj.setDeleteFlag(Boolean.TRUE);
				
				bean.saveOrUpdate(paraObj);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		logger.info(" end  Parameters dao - update");
	}
	
	public void test03FindById() { 
		logger.info("start Parameters dao - find");

		try {
		    ParametersId id = new ParametersId(paraScope, paraName);
			Parameters paraObj = bean.findById(id);
			logger.debug("Parameters t : "+paraObj+"\n"+ReflectionToStringBuilder.toString(paraObj, ToStringStyle.MULTI_LINE_STYLE));
			logger.info("Parameters.paraValue : "+paraObj.getParaValue());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		logger.info(" end  Parameters dao - find");
	}
	
	
	public void test04FindAll() {
		logger.info("start Parameters dao - findAll");
		try {
			List<Parameters> paraList = bean.findAll();

			if (!CollectionUtils.isEmpty(paraList)) {
				int count = 0;
				for (Parameters paraObj : paraList) {
					logger.debug("ParametersId ["+(++count)+"] : "+paraObj);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		logger.info(" end  Parameters dao - findAll");
	}
	
	public void test05FindAllByExample() {
		logger.info("start Parameters dao - findAllByExample");
		try {
			Parameters d = new Parameters();
			d.setParaType(ParameterType.Boolean);
			//d.setAdminAccessibleFlag(Boolean.TRUE);
			List<Parameters> paraList = bean.findAll(d);

			if (!CollectionUtils.isEmpty(paraList)) {
				int count = 0;
				for (Parameters paraObj : paraList) {
					logger.debug("ParametersId ["+(++count)+"] : "+paraObj);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		logger.info(" end  Parameters dao - findAllByExample");
	}
	
	public void test99Delete() {
		logger.info("start Parameters dao - delete");
		try {
			ParametersId id = new ParametersId(paraScope, paraName);
			Parameters paraObj = bean.findById(id);
			if (paraObj!=null) {
				bean.delete(paraObj);
			} else {
				logger.warn("Parameters[{}][{}] not exists.", paraScope, paraName);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		logger.info(" end  Parameters dao - delete");
	}
	
	@Test
	public void test100DaoFindParametersByScope() {
		logger.info("start Parameters dao - findParametersByScope");
		try {

			List<Parameters> paraList = bean.findAllByScope("AFIS");

			int count = 0;
			if (!CollectionUtils.isEmpty(paraList)) {
				for (Parameters paraObj : paraList) {
					logger.debug("ParametersId ["+(++count)+"] : "+paraObj+"\n"+ReflectionToStringBuilder.toString(paraObj, ToStringStyle.MULTI_LINE_STYLE));
				}
			}
			Assert.assertNotNull("Payment paraList is not found", paraList);
			Assert.assertNotSame("Payment paraList count should not be zero", count, 0);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		logger.info(" end  Parameters dao - findParametersByScope");
	}
	
}
