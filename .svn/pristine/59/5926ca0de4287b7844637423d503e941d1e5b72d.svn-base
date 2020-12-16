package com.nec.asia.nic.utils.mappers;

/**

 * @author Ambrocio,Paulo Johannes D.
 * @Company: NEC ASIA PACIFIC PTE
 * @since : Jun 17, 2013
 * <p>
 *	A base Implementation for the specialMapping interface
 * </p>
 * 
 *
 * @param <F> - the type of the object being mapped from
 * @param <T> - the type of the object being mapped to
 */
public abstract class BaseSpecialMapping<F,T> implements SpecialMapping<F, T>{
	private String toFieldName;
	private String fromFieldName;
	
	
	public BaseSpecialMapping(String toFieldName, String fromFieldName) {
		super();
		this.toFieldName = toFieldName;
		this.fromFieldName = fromFieldName;
	}
	
	
	
	@Override
	public void mapPropertyTo(F from, T to, String fieldNameTo,
			String fieldNameFrom) throws Exception {
		this.setFromFieldName(fieldNameFrom);
		this.setToFieldName(fieldNameTo);
		doMapping(from, to);
	}

	protected abstract void doMapping(F from, T to);


	public String getToFieldName() {
		return toFieldName;
	}
	public void setToFieldName(String toFieldName) {
		this.toFieldName = toFieldName;
	}
	public String getFromFieldName() {
		return fromFieldName;
	}
	public void setFromFieldName(String fromFieldName) {
		this.fromFieldName = fromFieldName;
	}
	
	

}
