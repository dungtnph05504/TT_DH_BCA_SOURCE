package com.nec.asia.nic.framework.admin.job.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nec.asia.nic.framework.dataAccess.Dbo;
import com.nec.asia.nic.framework.job.scheduler.dataAccess.type.ActionOnEvent;
import com.nec.asia.nic.framework.job.scheduler.dataAccess.type.Frequency;

/**
 * The Class JobSchedule.
 *
 * @author boon_wui
 * @since 14 April 208
 * @version 1.0
 */
public class JobSchedule extends Dbo {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /** The job id. */
    private JobScheduleId id;

    /** The start date. */
    private Date startDate = null;

    /** The end date. */
    private Date endDate = null;

    /** The start hour. */
    private int startHour = 0;

    /** The start min. */
    private int startMin = 0;

    /** The end hour. */
    private int endHour = 0;

    /** The end min. */
    private int endMin = 0;

    /** The repeat count. */
    private int repeatCount = 0;

    /** The repeat interval second. */
    private long repeatIntervalSecond = 0; // rem by Tue
    //private int repeatIntervalSecond = 0; // Added by Tue
    
    /** The frequency. */
    private Frequency frequency = null;

    /** The dayof week. */
    private String[] dayofWeek = null;

    /** The days of week. */
    //private String daysOfWeek = null; // Rem by Tue
    private int daysOfWeek = 0; // Added by Tue

    /** The day of month. */
    private String dayOfMonth = null;

    /** The action on failure. */
    private ActionOnEvent actionOnFailure = ActionOnEvent.None;

    /** The action on success. */
    private ActionOnEvent actionOnSuccess = ActionOnEvent.None;

    /** The active. */
    private boolean active;

    /** The schedule description. */
    private String scheduleDescription;

    /** The yearly month. */
    private String yearlyMonth;

    /** The yearly day of month. */
    private String yearlyDayOfMonth;

    /** The email address. */
    private String emailAddress;

    /** The job. */
    @JsonBackReference
    private Job job;

    /** The re-occurrence. */
    private int reOccurrence;
    
    /** The weekday. */
    private String weekDay;
    
    /**
     * The Enum DayOfWeek.
     */
    public enum DayOfWeek {
        /** The MON. */
        MON,
        /** The TUE. */
        TUE,
        /** The WED. */
        WED,
        /** The THU. */
        THU,
        /** The FRI. */
        FRI,
        /** The SAT. */
        SAT,
        /** The SUN. */
        SUN
    }

    /**
     * The Enum Month.
     */
    public enum Month {
        /** The JAN. */
        JAN,
        /** The FEB. */
        FEB,
        /** The MAR. */
        MAR,
        /** The APR. */
        APR,
        /** The MAY. */
        MAY,
        /** The JUN. */
        JUN,
        /** The JUL. */
        JUL,
        /** The AUG. */
        AUG,
        /** The SEP. */
        SEP,
        /** The OCT. */
        OCT,
        /** The NOV. */
        NOV,
        /** The DEC. */
        DEC
    }

    /**
     * Gets the start date.
     *
     * @return the start date
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date.
     *
     * @param startDate the new start date
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the end date.
     *
     * @return the end date
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date.
     *
     * @param endDate the new end date
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Gets the repeat count.
     *
     * @return the repeat count
     */
    public int getRepeatCount() {
        return repeatCount;
    }

    /**
     * Sets the repeat count.
     *
     * @param repeat the new repeat count
     */
    public void setRepeatCount(int repeat) {
        this.repeatCount = repeat;
    }

    /**
     * Gets the repeat interval second.
     *
     * @return the repeat interval second
     */
    public long getRepeatIntervalSecond() {
        return repeatIntervalSecond;
    }

    /**
     * Sets the repeat interval second.
     *
     * @param repeatIntervalSecond the new repeat interval second
     */
    public void setRepeatIntervalSecond(long repeatIntervalSecond) {
        this.repeatIntervalSecond = repeatIntervalSecond;
    }
    /*public void setRepeatIntervalSecond(int repeatIntervalSecond) {
        this.repeatIntervalSecond = repeatIntervalSecond;
    }*/

    /**
     * Gets the frequency.
     *
     * @return the frequency
     */
    public Frequency getFrequency() {
        return frequency;
    }

    /**
     * Sets the frequency.
     *
     * @param frequency the new frequency
     */
    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    /**
     * Gets the dayof week.
     *
     * @return the dayof week
     */
    public String[] getDayofWeek() {
        return dayofWeek;
    }

    /**
     * Sets the dayof week.
     *
     * @param dayofWeek the new dayof week
     */
    public void setDayofWeek(String[] dayofWeek) {
        this.dayofWeek = dayofWeek;
    }

    /**
     * Gets the days of week.
     *
     * @return the days of week
     */
    //public String getDaysOfWeek() { // Rem by Tue
    public int getDaysOfWeek() {
        return daysOfWeek;
    }

    /**
     * Sets the days of week.
     *
     * @param daysOfWeek the new days of week
     */
    //public void setDaysOfWeek(String daysOfWeek) { // Rem by Tue
    public void setDaysOfWeek(int daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    /**
     * Gets the day of month.
     *
     * @return the day of month
     */
    public String getDayOfMonth() {
        return dayOfMonth;
    }

    /**
     * Sets the day of month.
     *
     * @param dayOfMonth the new day of month
     */
    public void setDayOfMonth(String dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    /**
     * Gets the start hour.
     *
     * @return the start hour
     */
    public int getStartHour() {
        return startHour;
    }

    /**
     * Sets the start hour.
     *
     * @param startHour the new start hour
     */
    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    /**
     * Gets the start min.
     *
     * @return the start min
     */
    public int getStartMin() {
        return startMin;
    }

    /**
     * Sets the start min.
     *
     * @param startMin the new start min
     */
    public void setStartMin(int startMin) {
        this.startMin = startMin;
    }

    /**
     * Gets the schedule description.
     *
     * @return the schedule description
     */
    public String getScheduleDescription() {
        return scheduleDescription;
    }

    /**
     * Sets the schedule description.
     *
     * @param scheduleDescription the new schedule description
     */
    public void setScheduleDescription(String scheduleDescription) {
        this.scheduleDescription = scheduleDescription;
    }

    /**
     * Gets the action on failure.
     *
     * @return the action on failure
     */
    public ActionOnEvent getActionOnFailure() {
        return actionOnFailure;
    }

    /**
     * Sets the action on failure.
     *
     * @param actionOnFailure the new action on failure
     */
    public void setActionOnFailure(ActionOnEvent actionOnFailure) {
        this.actionOnFailure = actionOnFailure;
    }

    /**
     * Gets the action on success.
     *
     * @return the action on success
     */
    public ActionOnEvent getActionOnSuccess() {
        return actionOnSuccess;
    }

    /**
     * Sets the action on success.
     *
     * @param actionOnSuccess the new action on success
     */
    public void setActionOnSuccess(ActionOnEvent actionOnSuccess) {
        this.actionOnSuccess = actionOnSuccess;
    }

    /**
     * Checks if is active.
     *
     * @return true, if is active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets the active.
     *
     * @param active the new active
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Gets the yearly month.
     *
     * @return the yearly month
     */
    public String getYearlyMonth() {
        return yearlyMonth;
    }

    /**
     * Sets the yearly month.
     *
     * @param yearlyMonth the new yearly month
     */
    public void setYearlyMonth(String yearlyMonth) {
        this.yearlyMonth = yearlyMonth;
    }

    /**
     * Gets the yearly day of month.
     *
     * @return the yearly day of month
     */
    public String getYearlyDayOfMonth() {
        return yearlyDayOfMonth;
    }

    /**
     * Sets the yearly day of month.
     *
     * @param yearlyDayOfMonth the new yearly day of month
     */
    public void setYearlyDayOfMonth(String yearlyDayOfMonth) {
        this.yearlyDayOfMonth = yearlyDayOfMonth;
    }

    /**
     * Gets the email address.
     *
     * @return the email address
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Sets the email address.
     *
     * @param emailAddress the new email address
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * @return the id
     */
    public JobScheduleId getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(JobScheduleId id) {
        this.id = id;
    }

    /**
     * @return the endHour
     */
    public int getEndHour() {
        return endHour;
    }

    /**
     * @param endHour the endHour to set
     */
    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    /**
     * @return the endMin
     */
    public int getEndMin() {
        return endMin;
    }

    /**
     * @param endMin the endMin to set
     */
    public void setEndMin(int endMin) {
        this.endMin = endMin;
    }

    /**
     * @return the job
     */
    public Job getJob() {
        return job;
    }

    /**
     * @param job the job to set
     */
    public void setJob(Job job) {
        this.job = job;
    }

    /**
     * @return the reOccurrence
     */
    public int getReOccurrence() {
        return reOccurrence;
    }

    /**
     * @param reOccurrence the reOccurrence to set
     */
    public void setReOccurrence(int reOccurrence) {
        this.reOccurrence = reOccurrence;
    }

    /**
     * @return the weekDay
     */
    public String getWeekDay() {
        return weekDay;
    }

    /**
     * @param weekDay the weekDay to set
     */
    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }
}
