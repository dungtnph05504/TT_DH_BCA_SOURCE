package com.nec.asia.nic.framework.common.query;


/**
 * Criteria for specifying like condition.<BR>
 * From BAF framework
 * 
 * @author WL
 */
public class LikeCriteria<T> extends HbmCriteria<T> implements ValueCriteria<T> {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The value. */
	private T value;

	/**
	 * Instantiates a new like criteria.
	 */
	public LikeCriteria() {
		
	}
	
	/**
	 * Instantiates a new like criteria.
	 *
	 * @param value the value
	 */
	public LikeCriteria(T value) {
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
