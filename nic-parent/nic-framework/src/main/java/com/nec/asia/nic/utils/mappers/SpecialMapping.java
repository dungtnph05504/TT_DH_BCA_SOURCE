package com.nec.asia.nic.utils.mappers;

/**

 * @author Ambrocio,Paulo Johannes D.
 * @Company: NEC ASIA PACIFIC PTE
 * @since : Jun 17, 2013
 * <p>
 *	 Represent a special mapping to be done when tranfering the contents of one object to another
 *  <br>
 *  Generic Params:
 * 	<li>T - the Type of  the object it is to be mapped to</li>
 *  <li>F - the Type  of the object to be mapped from </li>
 * </p>
 * 
 *
 */
public interface SpecialMapping<F,T> {
	void mapPropertyTo(F from,T to,String fieldNameTo,String fieldNameFrom)throws Exception;
	void setToFieldName(String toFieldName);
	String getToFieldName();
	void setFromFieldName(String fromFieldName);
	String getFromFieldName();
}
