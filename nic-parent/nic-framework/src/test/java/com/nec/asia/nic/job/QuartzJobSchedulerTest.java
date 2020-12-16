package com.nec.asia.nic.job;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/test/resources/oracle/spring-context.xml" })
public class QuartzJobSchedulerTest {
	public final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Test
	public void addJob(){
		try {
			initJobScheduler();
			logger.info("Before Thread.sleep() 1 minutes.");
			Thread.sleep(1 * 60 * 1000);
			logger.info("After 1 minutes of Thread.sleep().");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	private void initJobScheduler() throws SchedulerException {
		logger.info("Start initJobScheduler()");
		JobDetail jobDetail = JobBuilder.newJob(MyJob1Impl.class)
				.withIdentity("myJob1", "jobGroup1")
				.withDescription("MyJob1 without DataMap")
				.build();
		
		Trigger trigger = TriggerBuilder
				.newTrigger()
				.withIdentity("myJob1Trigger", "jobGroup1")
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever())
				.build();

        /** STEP 4 : INSTANTIATE SCHEDULER FACTORY BEAN AND SET ITS PROPERTIES **/
        SchedulerFactory sfb = new StdSchedulerFactory();
        Scheduler scheduler = sfb.getScheduler();       
        logger.info("scheduler : "+scheduler.getSchedulerInstanceId());
        scheduler.start();  
        
        scheduler.scheduleJob(jobDetail, trigger); 
        TriggerKey triggerKey = new TriggerKey("myJob1Trigger", "jobGroup1");
		logger.info("myJob1Trigger.TriggerState : "+scheduler.getTriggerState(triggerKey));
		Trigger curTrigger = scheduler.getTrigger(triggerKey);
		logger.info("myJob1Trigger.NextFireTime() : "+curTrigger.getNextFireTime());
		logger.info("                         now : "+new Date());
		
		JobDetail jobDetail2 = JobBuilder.newJob(MyJob2Impl.class)
				.withIdentity("myJob2", "jobGroup2")
				.withDescription("MyJob2 with DataMap")
				.usingJobData("name", "KC")
				.usingJobData("age", "30")
				.usingJobData("gender", "M")
				.build();
		
		Trigger trigger2 = TriggerBuilder
				.newTrigger()
				.withIdentity("myJob2Trigger", "jobGroup2")
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever())
				.build();
		scheduler.scheduleJob(jobDetail2, trigger2); 
		
        logger.info(" End initJobScheduler()");
	}
}
