package com.nec.asia.nic.framework.admin.code.dao;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.CollectionUtils;

import com.nec.asia.nic.framework.admin.GenericTestCase;
import com.nec.asia.nic.framework.admin.code.dao.PaymentDefDao;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.domain.ParametersId;
import com.nec.asia.nic.framework.admin.code.domain.PaymentDef;
import com.nec.asia.nic.framework.admin.code.domain.PaymentDefId;
import com.nec.asia.nic.framework.admin.site.domain.SiteRepository;


public class PaymentDefDaoTestCase extends GenericTestCase<SiteRepository, PaymentDefId, PaymentDefDao> {
	
	public static ApplicationContext context = null;
	//public static PaymentDefDao dao = null;
	public PaymentDefDaoTestCase() {
		//init();
	}
	
	private String transactionType = "REP";
	private String transactionSubtype = "P";
	private int    lostCount = 100;
	
	@Override
	protected String getBeanName() {
		return "paymentDefDao";
	}
	
	public void test01Save() {
		logger.info("start PaymentDef dao - save");
		try {
			PaymentDefId id = new PaymentDefId(transactionType, transactionSubtype, lostCount);
			PaymentDef payObj = new PaymentDef(id , 300000D, true, true, "RIC", "SYSTEM", "SYSTEM", new Date(), null, null, null, null, null, null, false);
			bean.save(payObj);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		logger.info(" end  PaymentDef dao - save");
	}
	

	public void test02Update() {
		logger.info("start PaymentDef dao - update");
		try {
			PaymentDefId id = new PaymentDefId(transactionType, transactionSubtype, lostCount);
			PaymentDef payObj = bean.findById(id);
			Assert.assertNotNull("PaymentDef Object not found", payObj);
			if (payObj!=null) {
				logger.debug("PaymentDefId t : "+ReflectionToStringBuilder.toString(payObj, ToStringStyle.SHORT_PREFIX_STYLE));
				payObj.setFeeAmount(1000000D);
				
				payObj.setUpdateBy("SYSTEM");
				payObj.setUpdateDateTime(new Date());
				payObj.setUpdateWkstnId("SYSTEM");
				
				bean.saveOrUpdate(payObj);
				logger.info("PaymentDefId t : "+payObj.getId()+" updated.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		logger.info(" end  PaymentDef dao - update");
	}
	
	public void test03FindById() { //Transaction
		logger.info("start PaymentDef dao - find");

		try {
			PaymentDefId id = new PaymentDefId(transactionType, transactionSubtype, lostCount);
			PaymentDef payObj = bean.findById(id);
			logger.debug("PaymentDefId t : "+payObj+"\n"+ReflectionToStringBuilder.toString(payObj, ToStringStyle.MULTI_LINE_STYLE));
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info(" end  PaymentDef dao - find");
	}
	
	public void test04FindAll() {
		logger.info("start PaymentDef dao - findAll");
		try {
			List<PaymentDef> payList = bean.findAll();

			if (!CollectionUtils.isEmpty(payList)) {
				int count = 0;
				for (PaymentDef payObj : payList) {
					logger.debug("PaymentDefId ["+(++count)+"] : "+ReflectionToStringBuilder.toString(payObj, ToStringStyle.SHORT_PREFIX_STYLE));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		logger.info(" end  PaymentDef dao - findAll");
	}
	
	public void test05FindAllByExample() {
		logger.info("start PaymentDef dao - findAllByExample");
		try {
			PaymentDef d = new PaymentDef();
			d.setWaivableFlag(Boolean.TRUE);
			List<PaymentDef> payList = bean.findAll(d);

			if (!CollectionUtils.isEmpty(payList)) {
				int count = 0;
				for (PaymentDef payObj : payList) {
					logger.debug("PaymentDefId ["+(++count)+"] : "+ReflectionToStringBuilder.toString(payObj, ToStringStyle.SHORT_PREFIX_STYLE));
				}
			} else {
				logger.info("No Payment Data found.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		logger.info(" end  PaymentDef dao - findAllByExample");
	}
	

	public void test99Delete() {
		logger.info("start PaymentDef dao - delete");
		try {
			PaymentDefId id = new PaymentDefId(transactionType, transactionSubtype, lostCount);
			PaymentDef payObj = bean.findById(id);
			if (payObj!=null) {
				bean.delete(payObj);
			} else {
				logger.warn("PaymentDef[{}][{}][{}] not exists.", new Object[] {transactionType, transactionSubtype, lostCount});
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		logger.info(" end  PaymentDef dao - delete");
	}
	
	@Test
	public void testDaoFindPaymentDefByTransTypes() {
		logger.info("start PaymentDef dao - findPaymentDefByTransType");
		try {

			List<PaymentDef> payList = bean.findPaymentDefByTransType(transactionType, transactionSubtype);

			if (!CollectionUtils.isEmpty(payList)) {
				int count = 0;
				for (PaymentDef payObj : payList) {
					logger.debug("PaymentDefId ["+(++count)+"] : "+ReflectionToStringBuilder.toString(payObj, ToStringStyle.SHORT_PREFIX_STYLE));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		logger.info(" end  PaymentDef dao - findPaymentDefByTransType");
	}
	
}
