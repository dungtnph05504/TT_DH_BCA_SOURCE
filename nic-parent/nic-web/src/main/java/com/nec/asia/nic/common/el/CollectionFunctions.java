package com.nec.asia.nic.common.el;

import java.util.Collection;

/**

 * @author Ambrocio,Paulo Johannes D.
 * @Company: NEC ASIA PACIFIC PTE
 * @since : Jul 19, 2013
 * <p>
 *	elfunctions to check a collection contains as certain object
 * </p>
 * 
 *
 */
public class CollectionFunctions {

	private CollectionFunctions() {
	
	}

	public static boolean collectionContains(Object obj,Collection<?> c)
	{
		if(c==null)
			return false;
		return c.contains(obj);
	}
}
