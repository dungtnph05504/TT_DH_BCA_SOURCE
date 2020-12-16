package com.nec.asia.nic.comp.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;



public class JobInitializingBean {
	private static final Logger logger = LoggerFactory.getLogger(JobInitializingBean.class);
	
	public static void main(String[] args) {
		try {
			ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-quartz.xml");
//			
//			JobScheduleService jobScheduleService = (JobScheduleService)ctx.getBean("jobScheduleService");
//			Job job = new Job();
//			JobSchedule schedule = new JobSchedule();
//			schedule.setActive(true);
//			schedule.setJobId("00000001");
//			schedule.setScheduleName("Daily");
//			schedule.setFrequency(Frequency.RepeatAtInterval);
//			schedule.setRepeatIntervalSecond(5);
//			Set<JobSchedule> jobSchedules = new HashSet<>();
//			jobSchedules.add(schedule);
//			
//			job.setJobClass("com.nec.asia.nic.comp.job.StoredProcDailyJob");
//			job.setActive(true);
//			job.setCreateby("ADMIN");
//			job.setCreatedate(new Date());
//			job.setJobDescription("Test");
//			job.setJobId("00000001");
//			job.setJobImplClass(StoredProcDailyJob.class);
//			job.setJobName("Daily Job");
//			job.setJobStatus(JobStatus.Waiting);
//			job.setSchedule(jobSchedules);
//			
//			
//			jobScheduleService.createJob(job);			
//			jobScheduleService.enableTrigger("Daily", "00000001");
//			jobScheduleService.startScheduler();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in main()", e);
		}
	}
	

}
