package com.nec.asia.nic.framework.admin.code.dao;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.criterion.Order;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.GenericTestCase;
import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
import com.nec.asia.nic.framework.admin.code.domain.CodeValuesId;
import com.nec.asia.nic.framework.admin.code.domain.Codes;
import com.nec.asia.nic.framework.admin.site.domain.SiteRepository;

public class CodeValueDaoTestCase extends GenericTestCase<SiteRepository, CodeValuesId, CodeValuesDao> {
	
	private static final Codes code = new Codes("JOB_TYPE");
	private static final String codeValueId = "TEST";
	
	private static int pageNo = 1;
	private static int pageSize = 20;

	@Override
	protected String getBeanName() {
		return "codeValuesDao";
	}
	
	public CodeValueDaoTestCase() {
		//init();
	}

	@Ignore
	public void test01Save() {
		logger.info("start CodeValue dao - save");
		try {
			CodeValuesId id = new CodeValuesId(code.getCodeId(), codeValueId);
			CodeValues codeValueObj = new CodeValues(id, code, "TEST", 99, Boolean.TRUE, "ALL", "SYSTEM", "SYSTEM", new Date(), null, null, null, null, null, null, Boolean.FALSE, null);
			bean.save(codeValueObj);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		logger.info(" end  CodeValue dao - save");
	}
	
	@Ignore
	public void test02Update() {
		logger.info("start CodeValue dao - update");
		try {
			CodeValuesId id = new CodeValuesId(code.getCodeId(), codeValueId);
			CodeValues codeValueObj = bean.findById(id);
			Assert.assertNotNull("CodeValue Object is not found", codeValueObj);
			if (codeValueObj!=null) {
				logger.debug("before update CodeValueId t : "+" ["+codeValueObj.getId().getCodeId()+"] ["+codeValueObj.getId().getCodeValue()+"] ["+codeValueObj.getCodeValueDesc()+"]");
				codeValueObj.setCodeValueDesc("TEST Updates");
				codeValueObj.setUpdateBy("SYSTEM");
				codeValueObj.setUpdateDate(new Date());
				codeValueObj.setUpdateWkstnId("SYSTEM");
				codeValueObj.setAdminAccessibleFlag(Boolean.FALSE);
				codeValueObj.setDeleteBy("ADMIN");
				codeValueObj.setDeleteWkstnId("SYSTEM");
				codeValueObj.setDeleteDate(new Date());
				codeValueObj.setDeleteFlag(Boolean.TRUE);
				
				bean.saveOrUpdate(codeValueObj);
				
				logger.info("after update CodeValueId t : "+" ["+codeValueObj.getId().getCodeId()+"] ["+codeValueObj.getId().getCodeValue()+"] ["+codeValueObj.getCodeValueDesc()+"]");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		logger.info(" end  CodeValue dao - update");
	}
	
	@Ignore
	public void test03FindById() { //Transaction
		logger.info("start CodeValue dao - find");

		try {
			CodeValuesId id = new CodeValuesId(code.getCodeId(), codeValueId);
			CodeValues codeValueObj = bean.findById(id);
			if (codeValueObj!=null) {
				logger.debug("CodeValueId ["+codeValueObj.getId().getCodeId()+"] ["+codeValueObj.getId().getCodeValue()+"] ["+codeValueObj.getCodeValueDesc()+"]");
			}
			Assert.assertNotNull("CodeValue Object is not found", codeValueObj);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		logger.info(" end  CodeValue dao - find");
	}
	
	@Ignore
	public void test04FindAll() {
		logger.info("start CodeValue dao - findAll");
		try {
			List<CodeValues> codeValueList = bean.findAll();

			int count = 0;
			if (!CollectionUtils.isEmpty(codeValueList)) {				
				for (CodeValues codeValueObj : codeValueList) {
					logger.debug("CodeValueId ["+(++count)+"] ["+codeValueObj.getId().getCodeId()+"] ["+codeValueObj.getId().getCodeValue()+"] ["+codeValueObj.getCodeValueDesc()+"]");
				}
			}
			Assert.assertNotNull("codeValueList is not found", codeValueList);
			Assert.assertNotSame("CodeValueList count should not be zero", count, 0);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		logger.info(" end  CodeValue dao - findAll");
	}
	
	@Ignore
	public void test05FindAllByExample() {
		logger.info("start CodeValue dao - findAllByExample");
		try {
			CodeValues d = new CodeValues();
			d.setCodes(new Codes("TRANSACTION_STATUS"));
			List<CodeValues> codeValueList = bean.findAll(d);

			int count = 0;
			if (!CollectionUtils.isEmpty(codeValueList)) {
				for (CodeValues codeValueObj : codeValueList) {
					logger.debug("CodeValueId ["+(++count)+"] ["+codeValueObj.getId().getCodeId()+"] ["+codeValueObj.getId().getCodeValue()+"] ["+codeValueObj.getCodeValueDesc()+"]");
				}
			}
			Assert.assertNotNull("Active codeValueList is not found", codeValueList);
			Assert.assertNotSame("Active CodeValueList count should not be zero", count, 0);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		logger.info(" end  CodeValue dao - findAllByExample");
	}
	
	@Ignore
	public void test99Delete() {
		logger.info("start CodeValue dao - delete");
		try {
			CodeValuesId id = new CodeValuesId(code.getCodeId(), codeValueId);
			CodeValues codeValueObj = bean.findById(id);
			if (codeValueObj!=null) {
				bean.delete(codeValueObj);
			} else {
				logger.warn("CodeValues[{}][{}] not exists.", code.getCodeId(), codeValueId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		logger.info(" end  CodeValue dao - delete");
	}
	
	/* test customized method 150 to 199 */
	@Test
	public void testDaoFindAllForPagination() {
		logger.info("start CodeValue dao - findAllForPagination");
		try {

			for (int pageNo=1; true; pageNo++) {
				logger.info("Testing data loading for page {}.", pageNo);
				
				Order orderBy1 = Order.asc("codes.codeId");
				Order orderBy2 = Order.asc("codePriority");
				PaginatedResult<CodeValues> pageResult = bean.findAllForPagination(pageNo, pageSize, orderBy1, orderBy2);
				
				logger.info("CodeValue PageResult : "+pageResult+",curPage : "+pageResult.getPage()+",total #row:"+pageResult.getTotal());
				int count = 0;
				if (!CollectionUtils.isEmpty(pageResult.getRows())) {
					logger.info("Result Size : "+pageResult.getRows().size());
					for (CodeValues codeValueObj : pageResult.getRows()) {
						//logger.info("CodeValueId ["+(++count)+"] : "+codeValueObj+"\n"+ReflectionToStringBuilder.toString(codeValueObj, ToStringStyle.MULTI_LINE_STYLE));
						logger.debug("CodeValueId ["+(++count)+"] : "+" ["+codeValueObj.getId().getCodeId()+"] ["+codeValueObj.getCodePriority()+"] ["+codeValueObj.getId().getCodeValue()+"] ["+codeValueObj.getCodeValueDesc()+"]");
					}
				}
				Assert.assertNotNull("PageResult is null", pageResult);
				Assert.assertNotSame("Active CodeValueList count should not be zero", count, 0);
				if (pageResult.getTotal() / pageSize == pageNo) {
					logger.info("Final Page!!!!");
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		logger.info(" end  CodeValue dao - findAllForPagination");
	}

	@Test
	public void testDaoFindAllByExampleForPagination() {
		logger.info("start CodeValue dao - findAllByExampleForPagination");
		try {
			CodeValues example = new CodeValues();
			example.setCodes(new Codes("NATIONALITY"));
			logger.debug("example : "+ReflectionToStringBuilder.toString(example, ToStringStyle.MULTI_LINE_STYLE));
			for (int pageNo=1; pageNo<100; pageNo++) {
				PaginatedResult<CodeValues> pageResult = bean.findAllForPagination(example, pageNo, pageSize);
				
				logger.info("CodeValue PageResult : "+pageResult+",curPage : "+pageResult.getPage()+",total #row:"+pageResult.getTotal());
				int count = 0;
				if (!CollectionUtils.isEmpty(pageResult.getRows())) {
					logger.info("Result Size : "+pageResult.getRows().size());
					for (CodeValues codeValueObj : pageResult.getRows()) {
						//logger.info("CodeValueId ["+(++count)+"] : "+codeValueObj+"\n"+ReflectionToStringBuilder.toString(codeValueObj, ToStringStyle.MULTI_LINE_STYLE));
						logger.debug("CodeValueId ["+(++count)+"] : "+" ["+codeValueObj.getId().getCodeId()+"] ["+codeValueObj.getId().getCodeValue()+"] ["+codeValueObj.getCodeValueDesc()+"]");
					}
				}
				Assert.assertNotNull("PageResult is null", pageResult);
				Assert.assertNotSame("Active CodeValueList count should not be zero", count, 0);
				if (pageResult.getTotal() / pageSize == pageNo) {
					logger.info("Final Page!!!!");
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		logger.info(" end  CodeValue dao - findAllByExampleForPagination");
	}
	
	@Test
	public void testFindAllByCodeId() {
		logger.info("start dao - findAllByCodeId");
		try {
			List<CodeValues> codeValueList = bean.findAllByCodeId(code.getCodeId());

			int count = 0;
			if (!CollectionUtils.isEmpty(codeValueList)) {
				logger.info("Result Size : "+codeValueList.size());
				for (CodeValues codeValueObj : codeValueList) {
					logger.debug("CodeValues ["+(++count)+"] : "+" ["+codeValueObj.getId().getCodeId()+"] ["+codeValueObj.getId().getCodeValue()+"] ["+codeValueObj.getCodeValueDesc()+"]");
				}
			}
			Assert.assertNotNull("Active codeValueList is not found", codeValueList);
			Assert.assertNotSame("Active CodeValueList count should not be zero", count, 0);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		logger.info(" end  dao - findAllByCodeId");
	}

}
