package com.nec.asia.nic.comp.trans.dao;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nec.asia.nic.comp.trans.dao.NicUploadJobDao;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.dao.ParametersDao;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.domain.ParametersId;


public class NicUploadJobDaoTestCase extends TestCase{
	public static ApplicationContext context = null;
	public static  NicUploadJobDao  uploadJobDao = null;
	public static  ParametersDao  parametersDao = null;
	public NicUploadJobDaoTestCase() {
		init();
	}
	
	
	
	public void init() {
		try {
			context = new ClassPathXmlApplicationContext("spring-context.xml");
			uploadJobDao = context.getBean("uploadJobDao", NicUploadJobDao.class);
			parametersDao = context.getBean("parametersDao", ParametersDao.class);
		} catch (Error e) {
			e.printStackTrace();
		}
	}
	
	
	public void xtestDaoSave() {
		log("start Save dao");
		NicUploadJob nicObj = new NicUploadJob();
		nicObj.setTransactionId("RICHQ-2013-000002");
		nicObj.setCreateDatetime(new Date());
		nicObj.setCreateBy("JPRM");
		nicObj.setJobCurrentStage("00");
		nicObj.setJobType("TEST");
		
		nicObj.setAfisCheckStatus("Y");
		nicObj.setAfisRegisterStatus("Y");
		nicObj.setCpdCheckStatus("Y");
		nicObj.setJobCompletedFlag(true);
		nicObj.setJobStartTime(new Date());
		nicObj.setJobEndTime(new Date());
		nicObj.setPersoRegisterStatus("Y");
		
		
		try {
			long result = uploadJobDao.save(nicObj);
			log("save result:" + result);
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		log(" end  Save");
	}
	
	public void xtestDaoFindById() { 
		log("start find by Id");
		
		try {
			NicUploadJob t = uploadJobDao.findById(Long.valueOf(3));
			log("result t : "+t+"\n"+ReflectionToStringBuilder.toString(t, ToStringStyle.MULTI_LINE_STYLE));
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  find by id");
	}
	
	public void xtestDaoSaveOrUpdate () {
		log("start SaveOrUpdate dao");
		
		NicUploadJob nicObj = new NicUploadJob();
		nicObj.setWorkflowJobId(24L);
		
		
		try {
			
			nicObj = uploadJobDao.findById(nicObj.getWorkflowJobId());
			Assert.assertNotNull("Code Object is not found", nicObj);
			if (nicObj!=null){
			nicObj.setJobCurrentStage("01");//in progress = 01, completed = 02, completed with hit = 03, error = 09
			nicObj.setUpdateBy("SYSTEM");
			nicObj.setUpdateDatetime(new Date());
			nicObj.setUpdateWkstnId("SYSTEM01");
			uploadJobDao.saveOrUpdate(nicObj);
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		log(" end  SaveOrUpdate dao");
	}
	
	public void xtestDaoMerge () {
		log("start Merge dao");
		NicUploadJob nicObj = new NicUploadJob();
		nicObj.setWorkflowJobId(22L);
		nicObj.setTransactionId("RICHQ-2013-000002");
		
		nicObj.setJobCurrentStage("01");//in progress = 01, completed = 02, completed with hit = 03, error = 09
		
//		nicObj.setCpdCheckStatus("");
		
		nicObj.setCreateDatetime(new Date());
		nicObj.setCreateBy("JPRM");
		
		nicObj.setJobType("TEST3");
		
		nicObj.setAfisCheckStatus("Y");
		nicObj.setAfisRegisterStatus("Y");
		nicObj.setCpdCheckStatus("Y");
		nicObj.setJobCompletedFlag(true);
		nicObj.setJobStartTime(new Date());
		nicObj.setJobEndTime(new Date());
		nicObj.setPersoRegisterStatus("Y");
		
		try {
			NicUploadJob t = uploadJobDao.merge(nicObj);
			log("result t : "+t+"\n"+ReflectionToStringBuilder.toString(t, ToStringStyle.MULTI_LINE_STYLE));
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  merge dao");
	}
	
	public void xtestDaoDelete1() {
		log("start delete using obj dao");
		
		try {
			uploadJobDao.delete(Long.valueOf(7));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  delete Long dao");
	}
	
	public void xtestDaoDelete2 () {
		log("start delete using obj id dao");
		NicUploadJob nicObj = new NicUploadJob();
		nicObj.setTransactionId("RICHQ-2013-000002");
		nicObj.setWorkflowJobId(7L);
		
		try {
			uploadJobDao.delete(nicObj);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  delete obj dao");
	}
	
	
	public void xtestDaoFindAll() {
		log("start findAll dao");

		try {
			NicUploadJob nicObj = new NicUploadJob();
			nicObj.setCpdCheckStatus("00");
			List<NicUploadJob> list = uploadJobDao.findAll(nicObj);
			log("list count : "+ ((list==null)?0:list.size()));
			
			if(list != null && list.size() > 0){
				
				Iterator<NicUploadJob> iter1 = list.iterator();
				int i = 0;
				while(iter1.hasNext()){
					i++;
					log("transactionId("+i+")"+iter1.next().getTransactionId());
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  findAll dao");
	}
	
	public void xtestDaoFindAllByOfficerId() {
		log("start findAll dao");

		try {
			
			NicUploadJob e = new NicUploadJob();
			e.setInvestigationOfficerId("USER1");
			List<NicUploadJob> list = uploadJobDao.findAll(e);
			log("list count : "+ ((list==null)?0:list.size()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  findAll dao");
	}
	
	public void xtestDaoFindAllForPagination() {
		log("start findAllForPagination dao");

		try {
			int pageNo=1;
			int pageSize=3;
			List<NicUploadJob> resultList = null;
			do {
				NicUploadJob e = new NicUploadJob();
				e.setInvestigationOfficerId("USER1");
				PaginatedResult<NicUploadJob> list = uploadJobDao.findAllForPagination(e, pageNo, pageSize);
				log("list count : "+ ((list==null)?0:list.getRows().size()));
				for (NicUploadJob j :list.getRows()) {
					log("["+pageNo+"] jobId: "+j.getWorkflowJobId());
				}
				resultList = list.getRows();
				pageNo++;
			} while (resultList!=null && resultList.size()>0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  findAllForPagination dao");
	}
	
	//test workflow
	public void xtestDaoGetTransactionJobsInQueue() {
		log("start getTransactionJobsInQueue dao");

		try {
//			List<NicUploadJob> list = uploadJobDao.getTransactionJobsInQueue();
//			log("list count : "+ ((list==null)?0:list.size()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  getTransactionJobsInQueue dao");
	}
	
	//test investigation UI
	public void  xtestDaoAssignNewJob() {
		log("start assignNewJob dao");

		try {
			uploadJobDao.assignNewJob("afisIO");
			//log("list count : "+ ((list==null)?0:list.size()));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
		log(" end  assignNewJob dao");
	}
	
	public void  testGetValidJobInvStatus() {
		log("start testGetValidJobInvStatus");

		try {
			
			String jobInvStatus = StringUtils.EMPTY;		
			Parameters parameter = parametersDao.findById(new ParametersId("WORKFLOW", "VALID_JOB_INV_STATUS"));
			if (parameter != null) {
				jobInvStatus = (String) parameter.getParaValue();
			} else {
				jobInvStatus = "02";
			}

			String[] investigationStatus = jobInvStatus.split(",");
			if (investigationStatus == null) {
				investigationStatus = new String[] {"02"};
			}
			
			Assert.assertEquals(investigationStatus[0], "02");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
		log(" end  testGetValidJobInvStatus");
	}
	
	public static void log(String message) {
		System.out.println(message);
	}

}
