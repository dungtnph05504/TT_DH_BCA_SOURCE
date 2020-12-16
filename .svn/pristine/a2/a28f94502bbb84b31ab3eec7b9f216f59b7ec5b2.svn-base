/**
 * 
 */
package com.nec.asia.nic.framework.admin.job.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;

import com.nec.asia.nic.framework.common.LabelValueBean;
import com.nec.asia.nic.framework.job.scheduler.dataAccess.type.ActionOnEvent;
import com.nec.asia.nic.framework.job.scheduler.dataAccess.type.Frequency;

/**
 * @author Prasad_Karnati
 *
 */
public class JobScheduleDto {

	@NotEmpty
	// private Long jobId; // Rem by Tue 23 Feb 2016
	private String jobId; // Added by Tue 23 Feb 2016
	@NotEmpty
	private String jobName;
	@NotEmpty
	private String jobDesc;
	@NotEmpty
	private String jobClass;
	@NotEmpty
	private String jobProperties;
	
	private String jobStatus;
	private String activeIndicator;
	private String systemId;
	private String createBy;
	private String createWkstnId;
	private Date createDate;
	private String updateBy;
	private String updateWkstnId;
	private Date updateDate;
	private String deleteBy;
	private String deleteWkstnId;
	private Date deleteDate;
	private String deleteFlag;
	private Set jobSchedules = new HashSet(0);
	private Set jobExecLogs = new HashSet(0);

	private String mode;
	private String prevMode;
	
	private String scheduleName;
	//private String scheduleEnabled; // rem by Tue
	private boolean scheduleEnabled; // Added by Tue
	private String freqOccurType;
	private String actionOnSuccessType;
	private String actionOnFailureType;
	private String emailAddress;
	private String freqDailyTriggerTimeHr;
	private String freqDailyTriggerTimeMin;
	//private String freqDailyTriggerTimePm;// rem by Tue
	//private String freqDailyTriggerTimeAm;// rem by Tue
	private String freqDailyTriggerTimeFormat; // Added by Tue
	//private String freqWeeklyDayOfWeek; // rem by Tue
	private String[] freqWeeklyDayOfWeek; //Added by Tue
	private String freqMonthlyDayOfMonth;
	private String freqYearlyMonth;
	private String freqYearlyDayOfMonth;
	private String freqRepeatIntervalCount;
	private String freqRepeatIntervalSecond;

	//[23 Feb 2016][Tue] - Add
	private List <LabelValueBean> freqOccurTypeList;
	private List <LabelValueBean>  successTypeList;
	private List <LabelValueBean>  failureTypeList;
	private List <LabelValueBean>  hourList;
	private List <LabelValueBean>  minList;
	private List <LabelValueBean>  weeklyDayList;
	private List <LabelValueBean>  dayList;
	private List <LabelValueBean>  monthList;
	private List <LabelValueBean>  yearDayList;
	private String durationEndDateEnabled;
	private String durationStartDate;
	private String durationEndDate;
	
	
	/**
	 * @return the prevMode
	 */
	public String getPrevMode() {
		return prevMode;
	}

	/**
	 * @param prevMode the prevMode to set
	 */
	public void setPrevMode(String prevMode) {
		this.prevMode = prevMode;
	}

	/**
	 * @return the freqWeeklyDayOfWeek
	 */
	public String[] getFreqWeeklyDayOfWeek() {
		return freqWeeklyDayOfWeek;
	}

	/**
	 * @param freqWeeklyDayOfWeek the freqWeeklyDayOfWeek to set
	 */
	public void setFreqWeeklyDayOfWeek(String[] freqWeeklyDayOfWeek) {
		this.freqWeeklyDayOfWeek = freqWeeklyDayOfWeek;
	}

	/**
	 * @return the durationEndDateEnabled
	 */
	public String getDurationEndDateEnabled() {
		return durationEndDateEnabled;
	}

	/**
	 * @param durationEndDateEnabled the durationEndDateEnabled to set
	 */
	public void setDurationEndDateEnabled(String durationEndDateEnabled) {
		this.durationEndDateEnabled = durationEndDateEnabled;
	}

	/**
	 * @return the durationStartDate
	 */
	public String getDurationStartDate() {
		return durationStartDate;
	}

	/**
	 * @param durationStartDate the durationStartDate to set
	 */
	public void setDurationStartDate(String durationStartDate) {
		this.durationStartDate = durationStartDate;
	}

	/**
	 * @return the durationEndDate
	 */
	public String getDurationEndDate() {
		return durationEndDate;
	}

	/**
	 * @param durationEndDate the durationEndDate to set
	 */
	public void setDurationEndDate(String durationEndDate) {
		this.durationEndDate = durationEndDate;
	}

	/**
	 * @return the freqDailyTriggerTimeFormat
	 */
	public String getFreqDailyTriggerTimeFormat() {
		return freqDailyTriggerTimeFormat;
	}

	/**
	 * @param freqDailyTriggerTimeFormat the freqDailyTriggerTimeFormat to set
	 */
	public void setFreqDailyTriggerTimeFormat(String freqDailyTriggerTimeFormat) {
		this.freqDailyTriggerTimeFormat = freqDailyTriggerTimeFormat;
	}

	/**
	 * @return the freqOccurTypeList
	 */
	public List<LabelValueBean> getFreqOccurTypeList() {
		return freqOccurTypeList;
	}

	/**
	 * @param freqOccurTypeList the freqOccurTypeList to set
	 */
	public void setFreqOccurTypeList(List<LabelValueBean> freqOccurTypeList) {
		this.freqOccurTypeList = freqOccurTypeList;
	}

	/**
	 * @return the successTypeList
	 */
	public List<LabelValueBean> getSuccessTypeList() {
		return successTypeList;
	}

	/**
	 * @param successTypeList the successTypeList to set
	 */
	public void setSuccessTypeList(List<LabelValueBean> successTypeList) {
		this.successTypeList = successTypeList;
	}

	/**
	 * @return the failureTypeList
	 */
	public List<LabelValueBean> getFailureTypeList() {
		return failureTypeList;
	}

	/**
	 * @param failureTypeList the failureTypeList to set
	 */
	public void setFailureTypeList(List<LabelValueBean> failureTypeList) {
		this.failureTypeList = failureTypeList;
	}

	/**
	 * @return the hourList
	 */
	public List<LabelValueBean> getHourList() {
		return hourList;
	}

	/**
	 * @param hourList the hourList to set
	 */
	public void setHourList(List<LabelValueBean> hourList) {
		this.hourList = hourList;
	}

	/**
	 * @return the minList
	 */
	public List<LabelValueBean> getMinList() {
		return minList;
	}

	/**
	 * @param minList the minList to set
	 */
	public void setMinList(List<LabelValueBean> minList) {
		this.minList = minList;
	}

	/**
	 * @return the weeklyDayList
	 */
	public List<LabelValueBean> getWeeklyDayList() {
		return weeklyDayList;
	}

	/**
	 * @param weeklyDayList the weeklyDayList to set
	 */
	public void setWeeklyDayList(List<LabelValueBean> weeklyDayList) {
		this.weeklyDayList = weeklyDayList;
	}

	/**
	 * @return the dayList
	 */
	public List<LabelValueBean> getDayList() {
		return dayList;
	}

	/**
	 * @param dayList the dayList to set
	 */
	public void setDayList(List<LabelValueBean> dayList) {
		this.dayList = dayList;
	}

	/**
	 * @return the monthList
	 */
	public List<LabelValueBean> getMonthList() {
		return monthList;
	}

	/**
	 * @param monthList the monthList to set
	 */
	public void setMonthList(List<LabelValueBean> monthList) {
		this.monthList = monthList;
	}

	/**
	 * @return the yearDayList
	 */
	public List<LabelValueBean> getYearDayList() {
		return yearDayList;
	}

	/**
	 * @param yearDayList the yearDayList to set
	 */
	public void setYearDayList(List<LabelValueBean> yearDayList) {
		this.yearDayList = yearDayList;
	}
	// End
	
	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
	
	public String getJobId() {
		return this.jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}


/*	public Long getJobId() {
		return this.jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}
*/
	public String getJobName() {
		return this.jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobDesc() {
		return this.jobDesc;
	}

	public void setJobDesc(String jobDesc) {
		this.jobDesc = jobDesc;
	}

	public String getJobClass() {
		return this.jobClass;
	}

	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}

	public String getJobProperties() {
		return this.jobProperties;
	}

	public void setJobProperties(String jobProperties) {
		this.jobProperties = jobProperties;
	}

	public String getJobStatus() {
		return this.jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	public String getActiveIndicator() {
		return this.activeIndicator;
	}

	public void setActiveIndicator(String activeIndicator) {
		this.activeIndicator = activeIndicator;
	}

	public String getSystemId() {
		return this.systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getCreateWkstnId() {
		return this.createWkstnId;
	}

	public void setCreateWkstnId(String createWkstnId) {
		this.createWkstnId = createWkstnId;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateBy() {
		return this.updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getUpdateWkstnId() {
		return this.updateWkstnId;
	}

	public void setUpdateWkstnId(String updateWkstnId) {
		this.updateWkstnId = updateWkstnId;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getDeleteBy() {
		return this.deleteBy;
	}

	public void setDeleteBy(String deleteBy) {
		this.deleteBy = deleteBy;
	}

	public String getDeleteWkstnId() {
		return this.deleteWkstnId;
	}

	public void setDeleteWkstnId(String deleteWkstnId) {
		this.deleteWkstnId = deleteWkstnId;
	}

	public Date getDeleteDate() {
		return this.deleteDate;
	}

	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}

	public String getDeleteFlag() {
		return this.deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Set getJobSchedules() {
		return this.jobSchedules;
	}

	public void setJobSchedules(Set jobSchedules) {
		this.jobSchedules = jobSchedules;
	}

	public Set getJobExecLogs() {
		return this.jobExecLogs;
	}

	public void setJobExecLogs(Set jobExecLogs) {
		this.jobExecLogs = jobExecLogs;
	}

	/**
	 * @return the scheduleName
	 */
	public String getScheduleName() {
		return scheduleName;
	}

	/**
	 * @param scheduleName the scheduleName to set
	 */
	public void setScheduleName(String scheduleName) {
		this.scheduleName = scheduleName;
	}
/*
	*//**
	 * @return the scheduleEnabled
	 *//*
	public String getScheduleEnabled() {
		return scheduleEnabled;
	}

	*//**
	 * @param scheduleEnabled the scheduleEnabled to set
	 *//*
	public void setScheduleEnabled(String scheduleEnabled) {
		this.scheduleEnabled = scheduleEnabled;
	}*/
	

	/**
	 * @return the scheduleEnabled
	 */
	public boolean getScheduleEnabled() {
		return scheduleEnabled;
	}

	/**
	 * @param scheduleEnabled the scheduleEnabled to set
	 */
	public void setScheduleEnabled(boolean scheduleEnabled) {
		this.scheduleEnabled = scheduleEnabled;
	}

	/**
	 * @return the freqOccurType
	 */
	public String getFreqOccurType() {
		return freqOccurType;
	}

	/**
	 * @param freqOccurType the freqOccurType to set
	 */
	public void setFreqOccurType(String freqOccurType) {
		this.freqOccurType = freqOccurType;
	}

	/**
	 * @return the actionOnSuccessType
	 */
	public String getActionOnSuccessType() {
		return actionOnSuccessType;
	}

	/**
	 * @param actionOnSuccessType the actionOnSuccessType to set
	 */
	public void setActionOnSuccessType(String actionOnSuccessType) {
		this.actionOnSuccessType = actionOnSuccessType;
	}

	/**
	 * @return the actionOnFailureType
	 */
	public String getActionOnFailureType() {
		return actionOnFailureType;
	}

	/**
	 * @param actionOnFailureType the actionOnFailureType to set
	 */
	public void setActionOnFailureType(String actionOnFailureType) {
		this.actionOnFailureType = actionOnFailureType;
	}

	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @param emailAddress the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * @return the freqDailyTriggerTimeHr
	 */
	public String getFreqDailyTriggerTimeHr() {
		return freqDailyTriggerTimeHr;
	}

	/**
	 * @param freqDailyTriggerTimeHr the freqDailyTriggerTimeHr to set
	 */
	public void setFreqDailyTriggerTimeHr(String freqDailyTriggerTimeHr) {
		this.freqDailyTriggerTimeHr = freqDailyTriggerTimeHr;
	}

	/**
	 * @return the freqDailyTriggerTimeMin
	 */
	public String getFreqDailyTriggerTimeMin() {
		return freqDailyTriggerTimeMin;
	}

	/**
	 * @param freqDailyTriggerTimeMin the freqDailyTriggerTimeMin to set
	 */
	public void setFreqDailyTriggerTimeMin(String freqDailyTriggerTimeMin) {
		this.freqDailyTriggerTimeMin = freqDailyTriggerTimeMin;
	}

	/**
	 * @return the freqDailyTriggerTimePm
	 *//*
	public String getFreqDailyTriggerTimePm() {
		return freqDailyTriggerTimePm;
	}

	*//**
	 * @param freqDailyTriggerTimePm the freqDailyTriggerTimePm to set
	 *//*
	public void setFreqDailyTriggerTimePm(String freqDailyTriggerTimePm) {
		this.freqDailyTriggerTimePm = freqDailyTriggerTimePm;
	}

	*//**
	 * @return the freqDailyTriggerTimeAm
	 *//*
	public String getFreqDailyTriggerTimeAm() {
		return freqDailyTriggerTimeAm;
	}

	*//**
	 * @param freqDailyTriggerTimeAm the freqDailyTriggerTimeAm to set
	 *//*
	public void setFreqDailyTriggerTimeAm(String freqDailyTriggerTimeAm) {
		this.freqDailyTriggerTimeAm = freqDailyTriggerTimeAm;
	}*/

/*	*//**
	 * @return the freqWeeklyDayOfWeek
	 *//*
	public String getFreqWeeklyDayOfWeek() {
		return freqWeeklyDayOfWeek;
	}

	*//**
	 * @param freqWeeklyDayOfWeek the freqWeeklyDayOfWeek to set
	 *//*
	public void setFreqWeeklyDayOfWeek(String freqWeeklyDayOfWeek) {
		this.freqWeeklyDayOfWeek = freqWeeklyDayOfWeek;
	}*/

	/**
	 * @return the freqMonthlyDayOfMonth
	 */
	public String getFreqMonthlyDayOfMonth() {
		return freqMonthlyDayOfMonth;
	}

	/**
	 * @param freqMonthlyDayOfMonth the freqMonthlyDayOfMonth to set
	 */
	public void setFreqMonthlyDayOfMonth(String freqMonthlyDayOfMonth) {
		this.freqMonthlyDayOfMonth = freqMonthlyDayOfMonth;
	}

	/**
	 * @return the freqYearlyMonth
	 */
	public String getFreqYearlyMonth() {
		return freqYearlyMonth;
	}

	/**
	 * @param freqYearlyMonth the freqYearlyMonth to set
	 */
	public void setFreqYearlyMonth(String freqYearlyMonth) {
		this.freqYearlyMonth = freqYearlyMonth;
	}

	/**
	 * @return the freqYearlyDayOfMonth
	 */
	public String getFreqYearlyDayOfMonth() {
		return freqYearlyDayOfMonth;
	}

	/**
	 * @param freqYearlyDayOfMonth the freqYearlyDayOfMonth to set
	 */
	public void setFreqYearlyDayOfMonth(String freqYearlyDayOfMonth) {
		this.freqYearlyDayOfMonth = freqYearlyDayOfMonth;
	}

	/**
	 * @return the freqRepeatIntervalCount
	 */
	public String getFreqRepeatIntervalCount() {
		return freqRepeatIntervalCount;
	}

	/**
	 * @param freqRepeatIntervalCount the freqRepeatIntervalCount to set
	 */
	public void setFreqRepeatIntervalCount(String freqRepeatIntervalCount) {
		this.freqRepeatIntervalCount = freqRepeatIntervalCount;
	}

	/**
	 * @return the freqRepeatIntervalSecond
	 */
	public String getFreqRepeatIntervalSecond() {
		return freqRepeatIntervalSecond;
	}

	/**
	 * @param freqRepeatIntervalSecond the freqRepeatIntervalSecond to set
	 */
	public void setFreqRepeatIntervalSecond(String freqRepeatIntervalSecond) {
		this.freqRepeatIntervalSecond = freqRepeatIntervalSecond;
	}
	
	public void init() throws Exception {
		this.freqOccurTypeList = new ArrayList<LabelValueBean>() {{
			this.add(new LabelValueBean(Frequency.Daily.getDescription(), Frequency.Daily.getKey()));
			this.add(new LabelValueBean(Frequency.Weekly.getDescription(), Frequency.Weekly.getKey()));
			this.add(new LabelValueBean(Frequency.Monthly.getDescription(), Frequency.Monthly.getKey()));
			this.add(new LabelValueBean(Frequency.Yearly.getDescription(), Frequency.Yearly.getKey()));
			this.add(new LabelValueBean(Frequency.RepeatAtInterval.getDescription(), Frequency.RepeatAtInterval.getKey()));
			this.add(new LabelValueBean(Frequency.RepeatInDefinitely.getDescription(), Frequency.RepeatInDefinitely.getKey()));
			this.add(new LabelValueBean(Frequency.Specific.getDescription(), Frequency.Specific.getKey()));
		}};
		
		this.successTypeList = new ArrayList<LabelValueBean>() {{			
			this.add(new LabelValueBean(ActionOnEvent.None.getDescription(), ActionOnEvent.None.getKey()));
//			this.add(new LabelValueBean(ActionOnEvent.NotifyEmail.getDescription(), ActionOnEvent.NotifyEmail.getKey()));
			this.add(new LabelValueBean(ActionOnEvent.Rerun.getDescription(), ActionOnEvent.Rerun.getKey()));
//			this.add(new LabelValueBean(ActionOnEvent.RerunNotifyEmail.getDescription(),ActionOnEvent.RerunNotifyEmail.getKey()));
		}};
		
		this.failureTypeList = new ArrayList<LabelValueBean>() {{
			this.add(new LabelValueBean(ActionOnEvent.None.getDescription(), ActionOnEvent.None.getKey()));
//			this.add(new LabelValueBean(ActionOnEvent.NotifyEmail.getDescription(), ActionOnEvent.NotifyEmail.getKey()));
			this.add(new LabelValueBean(ActionOnEvent.Rerun.getDescription(), ActionOnEvent.Rerun.getKey()));
//			this.add(new LabelValueBean(ActionOnEvent.RerunNotifyEmail.getDescription(),ActionOnEvent.RerunNotifyEmail.getKey()));
		}};
		
		this.hourList = new ArrayList<LabelValueBean>() {{
			for (int i=1 ; i <=12 ; i++) {
				this.add(new LabelValueBean(StringUtils.leftPad(""+i,2, '0'),StringUtils.leftPad(""+i,1, '0')));
			}
		}};
		
		this.minList = new ArrayList<LabelValueBean>() {{
			for (int i=0 ; i <=59 ; i++) {
				this.add(new LabelValueBean(StringUtils.leftPad(""+i,2, '0'),StringUtils.leftPad(""+i,1, '0')));
			}
		}};
		
		this.weeklyDayList = new ArrayList<LabelValueBean>() {{
			this.add(new LabelValueBean("Thứ 2","MON"));
			this.add(new LabelValueBean("Thứ 3","TUE"));
			this.add(new LabelValueBean("Thứ 4","WED"));
			this.add(new LabelValueBean("Thứ 5","THU"));
			this.add(new LabelValueBean("Thứ 6","FRI"));	
			this.add(new LabelValueBean("Thứ 7","SAT"));
			this.add(new LabelValueBean("Chủ nhật","SUN"));	
		}};
		
		this.dayList = new ArrayList<LabelValueBean>() {{
			for (int i=1 ; i <=31 ; i++) {
				this.add(new LabelValueBean(StringUtils.leftPad(""+i,2, '0'),StringUtils.leftPad(""+i,1, '0')));
			}
			this.add(new LabelValueBean("Ngày cuối tháng","LAST"));	
		}};
		
		this.monthList = new ArrayList<LabelValueBean>() {{
				this.add(new LabelValueBean("Tháng 1","01"));
				this.add(new LabelValueBean("Tháng 2","02"));
				this.add(new LabelValueBean("Tháng 3","03"));
				this.add(new LabelValueBean("Tháng 4","04"));
				this.add(new LabelValueBean("Tháng 5","05"));
				this.add(new LabelValueBean("Tháng 6","06"));
				this.add(new LabelValueBean("Tháng 7","07"));
				this.add(new LabelValueBean("Tháng 8","08"));
				this.add(new LabelValueBean("Tháng 9","09"));
				this.add(new LabelValueBean("Tháng 10","10"));
				this.add(new LabelValueBean("Tháng 11","11"));
				this.add(new LabelValueBean("Tháng 12","12"));
		}};
		
		this.yearDayList = new ArrayList<LabelValueBean>() {{
			for (int i=1 ; i <=31 ; i++) {
				this.add(new LabelValueBean(StringUtils.leftPad(""+i,2, '0'),StringUtils.leftPad(""+i,1, '0')));
			}
		}};
	}

}
