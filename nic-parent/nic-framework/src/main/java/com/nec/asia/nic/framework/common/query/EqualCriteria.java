package com.nec.asia.nic.framework.common.query;


/**
 * Criteria for specifying equals condition.<BR>
 * From BAF framework
 * 
 * @author WL
 */
public class EqualCriteria<T> extends HbmCriteria<T> implements ValueCriteria<T>{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The value. */
	private T value;


	/**
	 * Instantiates a new equal criteria.
	 */
	public EqualCriteria() {
		
	}
	
	/**
	 * Instantiates a new equal criteria.
	 *
	 * @param value the value
	 */
	public EqualCriteria(T value) {
		this.value=value;
	}
	
	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public T getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value the value to set
	 */
	public void setValue(T value) {
		this.value = value;
	}


}
