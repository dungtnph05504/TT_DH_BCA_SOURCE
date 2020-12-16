package com.nec.asia.nic.framework.job;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;


/**
 * The Class BatchJob.
 *
 * @author Alvin Chua
 */
public abstract class BatchJob {
    
    /** The Constant CONTEXT_EXECUTION_DETAILS_KEY. */
    protected static final String CONTEXT_EXECUTION_DETAILS_KEY="DETAILS";

    /** The hostname. */
    private static String hostname;
    
    /** The details. */
    private List<ExecutionMessage> details=null;    
    
    /**
     * Gets the hostname.
     *
     * @return the hostname
     * @throws UnknownHostException the unknown host exception
     */
    protected static String getHostname() throws UnknownHostException {
        if(hostname==null) {
            hostname = InetAddress.getLocalHost().getHostName().toUpperCase();
        }
        return hostname;
    }
    
    /**
     * Adds the execution error.
     *
     * @param message the message
     */
    public void addExecutionError(String message){
        ExecutionMessage executionMessage=new ExecutionMessage();
        executionMessage.setMessage(message);
        executionMessage.setMessagetype(ExecutionMessage.ERROR);
        if(details!=null) details.add(executionMessage);
    }

    /**
     * Adds the execution info.
     *
     * @param message the message
     */
    public void addExecutionInfo(String message){
        ExecutionMessage executionMessage=new ExecutionMessage();
        executionMessage.setMessage(message);
        executionMessage.setMessagetype(ExecutionMessage.INFO);
        if(details!=null) details.add(executionMessage);        
    }

    /**
     * Adds the execution warn.
     *
     * @param message the message
     */
    public void addExecutionWarn(String message){
        ExecutionMessage executionMessage=new ExecutionMessage();
        executionMessage.setMessage(message);
        executionMessage.setMessagetype(ExecutionMessage.WARNING);
        if(details!=null) details.add(executionMessage);                
    }

    /**
     * Gets the details.
     *
     * @return the details
     */
    public List<ExecutionMessage> getDetails()
    {
        return details;
    }

    /**
     * Sets the details.
     *
     * @param details the new details
     */
    public void setDetails(List<ExecutionMessage> details)
    {
        this.details = details;
    }
    
}
