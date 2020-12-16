package com.nec.asia.nic.framework.job.quartz;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.service.ParametersService;
import com.nec.asia.nic.framework.job.BatchJob;
import com.nec.asia.nic.framework.job.ExecutionMessage;
import com.nec.asia.nic.framework.springSupport.SpringServiceManager;

/**
 * The Class BatchJobQuartzStatefulImpl.
 * 
 * @author Mahesh
 */
public class BatchJobQuartzStatefulImpl extends BatchJob implements StatefulJob {

	/** The _logger. */
	private static Logger _logger = Logger.getLogger(BatchJobQuartzStatefulImpl.class);

	/** The Constant CONTEXT_EXECUTION_PROCESSED_TASKS_KEY. */
	protected static final String CONTEXT_EXECUTION_PROCESSED_TASKS_KEY = "CONTEXT_EXECUTION_PROCESSED_TASKS";

	/** The Constant JOB_SCHEDULER_HOSTNAME_LIST. */
	protected static final String JOB_SCHEDULER_HOSTNAME_LIST = "JOB_SCHEDULER_HOSTNAME_LIST";

	protected static final String PARA_SCOPE_SYSTEM = "SYSTEM";
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	public final void execute(JobExecutionContext context) throws JobExecutionException {
		List<ExecutionMessage> contextDetails = (List<ExecutionMessage>) context.get(CONTEXT_EXECUTION_DETAILS_KEY);
		if (contextDetails != null) {
			setDetails(contextDetails);
		}
		else {
			setDetails(new ArrayList<ExecutionMessage>());
			context.put(CONTEXT_EXECUTION_DETAILS_KEY, getDetails());
		}

		if (checkValidBatchJobServer()) {
			this.executeJob(context);
		}
		else {
			_logger.warn("Not a valid batch job server");
		}

	}

	/**
	 * Execute job.
	 * 
	 * @param context the context
	 * @throws JobExecutionException the job execution exception
	 */
	public void executeJob(JobExecutionContext context) throws JobExecutionException {

	}

	/**
	 * Sets the job processed tasks.
	 * 
	 * @param context the new job processed tasks
	 */
	public void setJobProcessedTasks(JobExecutionContext context) {
		if (context != null) {
			context.put(CONTEXT_EXECUTION_PROCESSED_TASKS_KEY, Boolean.TRUE);
		}
	}

	/**
	 * Check valid batch job server.
	 * 
	 * @return true, if successful
	 */
	public boolean checkValidBatchJobServer() {
		_logger.debug("In checkValidBatchJobServer- Start");
		try {
			String hostname = getHostname();
			ParametersService parameterService = (ParametersService) SpringServiceManager.getBean("parametersService");
			Parameters parameter = parameterService.getParamDetails(PARA_SCOPE_SYSTEM, JOB_SCHEDULER_HOSTNAME_LIST);
			if (parameter != null && parameter.getParaLobValue() != null) {
				String hostnameList = parameter.getParaLobValue().toUpperCase();
				if (hostname != null && hostnameList.indexOf(hostname.toUpperCase()) > -1) {
					addExecutionInfo("Job was triggered on host: " + getHostname());
					return true;
				}
				else {
					_logger.warn("Current hostname: " + hostname + " is not in preconfigured batch job host list: " + hostnameList);
				}
			}
			else {
				_logger.warn("Preconfigured batch job host list parameter is empty or null, please check: " + JOB_SCHEDULER_HOSTNAME_LIST);
			}
			return false;
		}
		catch (Exception ex) {
			_logger.error("Error in checkValidBatchJobServer: " + ex.getMessage(), ex);
			return false;
		}
	}

	/**
	 * Gets the int property.
	 * 
	 * @param dataMap the data map
	 * @param key the key
	 * @param defaultValue the default value
	 * @return the int property
	 */
	protected int getIntProperty(JobDataMap dataMap, String key, int defaultValue) {
		int value = defaultValue;
		String valueStr = dataMap.getString(key);
		if (StringUtils.isBlank(valueStr)) {
			_logger.warn("Missing '" + key + "' property. Using default (" + defaultValue + ") in "+getClass().getSimpleName()+" configuration");
		}
		else if (StringUtils.isNumeric(valueStr)) {
			try {
				value = Integer.parseInt(valueStr);
			}
			catch (Exception e) {
				_logger.error("Not integer value (" + valueStr + ") for '" + key + "' property. Using default (" + defaultValue + ").: " + e.getMessage(), e);
				this.addExecutionWarn("Not integer value (" + valueStr + ") for '" + key + "' property. Using default (" + defaultValue + ").");
			}
		}
		else {
			this.addExecutionWarn("Not integer value (" + valueStr + ") for '" + key + "' property. Using default (" + defaultValue + ").");
		}
		return value;
	}
	
    /**
     * Gets the boolean property.
     *
     * @param dataMap the data map
     * @param key the key
     * @param defaultValue the default value
     * @return the boolean property
     */
	protected boolean getBooleanProperty(JobDataMap dataMap, String key, boolean defaultValue) {
		boolean value = defaultValue;
		String valueStr = dataMap.getString(key);
		if (StringUtils.isBlank(valueStr)) {
			_logger.warn("Missing '" + key + "' property. Using default (" + defaultValue + ") in "+getClass().getSimpleName()+" configuration");
		}
		else if ("true".equalsIgnoreCase(valueStr.trim()) || "false".equalsIgnoreCase(valueStr.trim())) {
			value = Boolean.parseBoolean(valueStr.trim());
		}
		else {
			this.addExecutionWarn("Not boolean value (" + valueStr + ") for '" + key + "' property. Using default (" + defaultValue + ").");
		}
		return value;
	}

	protected String getProperty(JobDataMap dataMap, String key, String defaultValue) {
		String value = defaultValue;
		String valueStr = dataMap.getString(key);
		if (StringUtils.isNotBlank(valueStr)) {
			value = valueStr;
		}
		else {
			_logger.warn("Missing '" + key + "' property. Using default (" + defaultValue + ") in "+getClass().getSimpleName()+" configuration");
		}
		return value;
	}
	
}
