/* Copyright 2006 NEC Singapore Pte Ltd. All Rights Reserved.
*
* This software is the proprietary information of NEC Singapore Pte Ltd.
* The use is subject to license terms from NEC Singapore Pte Ltd.
*
*/
package com.nec.asia.nic.framework.dataAccess;

import java.io.Serializable;

/**
 * Parent class for all Database Mapping Objects.
 *
 * @version $Revision: 1.1.1.1 $
 * @author $Author: david $
 */
public class Dbo implements Serializable {
	//To resolve unnecessary detach session exception caused by ReflectionToStringBuilder
	/*
	public String toString() {
       return ReflectionToStringBuilder.toString(this,ReflectionToStringStyle.CUSTOM_STYLE);
	}
	*/
}
