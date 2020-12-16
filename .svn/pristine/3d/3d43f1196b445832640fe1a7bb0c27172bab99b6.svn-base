package com.nec.asia.nic.job;

/**
 * 
 * @author bright_zheng
 *
 */
public class QuartzJob implements java.io.Serializable{
	private static final long serialVersionUID = -1190636389388585349L;
	
	private String jobName;
	private String jobGroup;
	private String jobClass;
	private String jobTriggerCronExpression;
	private String jobParameters;
	
	public QuartzJob(String jobName, 
			String jobGroup, 
			String jobClass, 
			String jobTriggerCronExpression, 
			String jobParameters){
		this.jobName = jobName;
		this.jobGroup = jobGroup;
		this.jobClass = jobClass;
		this.jobTriggerCronExpression = jobTriggerCronExpression;
		this.jobParameters = jobParameters;
	}
	
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getJobGroup() {
		return jobGroup;
	}
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}
	public String getJobClass() {
		return jobClass;
	}
	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}
	public String getJobTriggerCronExpression() {
		return jobTriggerCronExpression;
	}
	public void setJobTriggerCronExpression(String jobTriggerCronExpression) {
		this.jobTriggerCronExpression = jobTriggerCronExpression;
	}

	public String getJobParameters() {
		return jobParameters;
	}

	public void setJobParameters(String jobParameters) {
		this.jobParameters = jobParameters;
	}
}
