package com.nec.asia.nic.framework.transaction;

/**
 * Transaction callback interface
 * 
 * @author bright_zheng
 *
 */
public interface TransactionCallback {
	/**
	 * We can implement this interface to customize transaction
	 * 
	 * @param clazz, current object, sometimes it's a controller instance 
	 * @param o, the input object, sometimes it's a domain pojo instance
	 * @return
	 * @throws TransactionException
	 */
	public Object process(Object clazz, Object o) throws TransactionException;
}
