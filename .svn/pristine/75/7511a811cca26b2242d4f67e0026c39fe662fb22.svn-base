package com.nec.asia.nic.framework.admin.code.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Assert;
import org.junit.Test;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.GenericTestCase;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.domain.ParametersId;
import com.nec.asia.nic.framework.admin.code.domain.type.ParameterType;
import com.nec.asia.nic.framework.admin.code.exception.ParametersServiceException;


public class ParametersServiceTestCase extends GenericTestCase<Parameters, ParametersId, ParametersService> {

	private static String paraScope = "AFIS";
	private static String paraName = "1_TO_N_FP_AUTO_HIT_THRESHOLD";
	
	@Override
	protected String getBeanName() {
		return "parametersService";
	}

	public void test01Save() {
		logger.info("start ParametersService - Save");
		Assert.assertNotNull(bean);
		ParametersId paramId = new ParametersId(paraScope, paraName);
		Parameters paramObj = new Parameters();
		paramObj.setId(paramId);
		paramObj.setParaShortValue("700");
		paramObj.setParaType(ParameterType.Integer);
		paramObj.setAdminAccessibleFlag(Boolean.TRUE);
		paramObj.setCreateBy("AFIS_SYSTEM");
		paramObj.setCreateWkstnId("SYSTEM");
		paramObj.setCreateDate(new Date());
		paramObj.setDeleteFlag(Boolean.FALSE);
		
		ParametersId result = bean.save(paramObj);
		Assert.assertNotNull(result);
		logger.info(" end  ParametersService - Save");
	}

	public void test02Update() {
		logger.info("start ParametersService - Update");
		Assert.assertNotNull(bean);
		try {
			ParametersId id = new ParametersId(paraScope, paraName);
			Parameters paraObj = bean.findById(id);
			if (paraObj!=null) {
				paraObj.setParaShortValue("1200");
				paraObj.setUpdateBy("AFIS");
				paraObj.setUpdateWkstnId("SYSTEM");
				paraObj.setUpdateDate(new Date());
				bean.updateParam(paraObj);
			} else {
				logger.warn("Parameters[{}][{}] not exists.", paraScope, paraName);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		logger.info(" end  ParametersService - Update");		
	}

	public void test03FindById() {
		logger.info("start ParametersService - FindById");
		Assert.assertNotNull(bean);
		ParametersId paramId = new ParametersId(paraScope, paraName);
		Parameters result = bean.findById(paramId);
		Assert.assertNotNull(result);
		Assert.assertEquals(result.getParaType(), ParameterType.Integer);
		logger.info("Parameters : "+ReflectionToStringBuilder.toString(result.getId(), ToStringStyle.SHORT_PREFIX_STYLE)+"\t"+ReflectionToStringBuilder.toString(result, ToStringStyle.SHORT_PREFIX_STYLE));
		logger.info(" end  ParametersService - FindById");		
	}

	public void test04FindAll() {
		logger.info("start ParametersService - findAll");
		Assert.assertNotNull(bean);
		List<Parameters> resultList = this.bean.findAll();
		int count = 0;
		for (Parameters DBO : resultList) {
			logger.debug("Parameters ["+(++count)+"] : "+ReflectionToStringBuilder.toString(DBO.getId(), ToStringStyle.SHORT_PREFIX_STYLE)+"\t"+ReflectionToStringBuilder.toString(DBO, ToStringStyle.SHORT_PREFIX_STYLE));
		}
		logger.info(" end  ParametersService - findAll");		
	}

	public void test05FindAllByExample() {
		logger.info("start ParametersService - FindAllByExample");
		Assert.assertNotNull(bean);
		logger.info(" end  ParametersService - FindAllByExample");		
	}
	
	public void test99Delete() {
		logger.info("start ParametersService- delete");
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
		logger.info(" end  ParametersService - delete");
	}
	
	@Test
	public void testFindAllForPaginationByScope() throws ParametersServiceException {
		logger.info("start ParametersService - findAllForPaginationByScope");
		Assert.assertNotNull(bean);
		String paramScope = "AFIS";
		PaginatedResult<Parameters> result = this.bean.findAllForPaginationByScope(paramScope, 1, 20);
		int count = 0;
		for (Parameters DBO : result.getRows()) {
			logger.debug("Parameters ["+(++count)+"] : "+ReflectionToStringBuilder.toString(DBO.getId(), ToStringStyle.SHORT_PREFIX_STYLE)+"\t"+ReflectionToStringBuilder.toString(DBO, ToStringStyle.SHORT_PREFIX_STYLE));
		}
		logger.info(" end  ParametersService - findAllForPaginationByScope");
	}
	
}
