package com.nec.asia.nic.framework.job;


/**
 * The Class ExecutionMessage.
 *
 * @author Alvin Chua
 */
public class ExecutionMessage {
	
	/** The Constant INFO. */
	public static final String INFO="I";
	
	/** The Constant WARNING. */
	public static final String WARNING="W";
	
	/** The Constant ERROR. */
	public static final String ERROR="E";
	
	/** The message. */
	private String message;
	
	/** The messagetype. */
	private String messagetype;
	
	/** The message code. */
	private String messageCode;
	
	/** The ext ref id1. */
	private String extRefId1;
	
	/** The ext ref id2. */
	private String extRefId2;
	
	
	
	/**
	 * Gets the message code.
	 *
	 * @return the message code
	 */
	public String getMessageCode()
    {
        return messageCode;
    }
    
    /**
     * Sets the message code.
     *
     * @param messageCode the new message code
     */
    public void setMessageCode(String messageCode)
    {
        this.messageCode = messageCode;
    }
    
    /**
     * Gets the ext ref id1.
     *
     * @return the ext ref id1
     */
    public String getExtRefId1()
    {
        return extRefId1;
    }
    
    /**
     * Sets the ext ref id1.
     *
     * @param extRefId1 the new ext ref id1
     */
    public void setExtRefId1(String extRefId1)
    {
        this.extRefId1 = extRefId1;
    }
    
    /**
     * Gets the ext ref id2.
     *
     * @return the ext ref id2
     */
    public String getExtRefId2()
    {
        return extRefId2;
    }
    
    /**
     * Sets the ext ref id2.
     *
     * @param extRefId2 the new ext ref id2
     */
    public void setExtRefId2(String extRefId2)
    {
        this.extRefId2 = extRefId2;
    }
    
    /**
     * Gets the message.
     *
     * @return the message
     */
    public String getMessage() {
		return message;
	}
	
	/**
	 * Sets the message.
	 *
	 * @param message the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * Gets the messagetype.
	 *
	 * @return the messagetype
	 */
	public String getMessagetype() {
		return messagetype;
	}
	
	/**
	 * Sets the messagetype.
	 *
	 * @param messagetype the new messagetype
	 */
	public void setMessagetype(String messagetype) {
		this.messagetype = messagetype;
	}
}
