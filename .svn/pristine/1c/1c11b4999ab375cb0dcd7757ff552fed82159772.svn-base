package com.nec.asia.nic.utils.mappers;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

/**

 * @author Ambrocio,Paulo Johannes D.
 * @Company: NEC ASIA PACIFIC PTE
 * @since : Jun 17, 2013
 * <p>
 *	Maps the field of one object to another object using getter and setters.
 *  <br>
 *  Generic Params:
 * 	<li>T - the Type of  the object it is to be mapped to</li>
 *  <li>F - the Type  of the object to be mapped from </li>
 * </p>
 * 
 *
 */
public class ObjectMapper<F,T> {
	private static Logger logger = Logger.getLogger(ObjectMapper.class);
	

	
	public void mapObject(F from, T to,String ...ignoreFromFields){
			mapObject(from, to,null,ignoreFromFields);
	}
	
	public void mapObject(F from, T to,List<SpecialMapping<T,F>> specialMappings,String ...ignoreFromFields){
		
			Class fromClass = from.getClass();
			Class toClass = to.getClass();
			
			Field[] fromFields = fromClass.getDeclaredFields();
			for (Field field : fromFields) {
				if(ignoreFromFields==null || ignoreFromFields.length==0||!StringListUtils.isInList(field.getName(), ignoreFromFields)){
					mapField(from, to, fromClass, toClass, field);
				}
			}
			
			if(specialMappings!=null && specialMappings.size()>0){
				for (SpecialMapping<T,F> specialMapping : specialMappings) {
					try{
						specialMapping.mapPropertyTo(to, from, specialMapping.getToFieldName(), specialMapping.getFromFieldName());
					}catch(Exception e){
						logger.trace(e);
					}
				}
			}
		
	}

	private void mapField(F from, T to, Class fromClass, Class toClass,
			Field field) {
		try{
			Method getMethod=null;
			Method setMethod=null;
			String fieldName = field.getName();
			String getMethodName = null;
			if(isBoolean(field)){
				getMethodName = "is" + fieldName.replaceFirst(fieldName.substring(0,1), fieldName.substring(0,1).toUpperCase());
				getMethod = fromClass.getDeclaredMethod(getMethodName);
			}else{
				getMethodName = "get" + fieldName.replaceFirst(fieldName.substring(0,1), fieldName.substring(0,1).toUpperCase());
				getMethod = fromClass.getDeclaredMethod(getMethodName);
			}
			String setMethodName = "set" + fieldName.replaceFirst(fieldName.substring(0,1), fieldName.substring(0,1).toUpperCase());
			setMethod = toClass.getDeclaredMethod(setMethodName,field.getType());
			
			if(getMethod!=null && setMethod != null){
				setMethod.invoke(to, getMethod.invoke(from));
			}
		
		}catch(Exception e){
			logger.trace(e);
		}
	}
	
	private  boolean isBoolean(Field field){
		boolean res = false;
		if(field.getType().equals(boolean.class)||field.getType().equals(Boolean.class)){
			res = true;
		}
		return res;
	}
	
}
