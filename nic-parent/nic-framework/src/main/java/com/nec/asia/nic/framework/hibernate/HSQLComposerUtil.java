package com.nec.asia.nic.framework.hibernate;

import java.util.Collection;
import java.util.List;
import java.util.Set;


/**
 * The Class HSQLComposerUtil.
 *
 * @author Mahesh
 */
public class HSQLComposerUtil {
	
	/** The Constant IN_OPERATOR. */
	public static final int IN_OPERATOR = 1;
	
	/** The Constant NOT_IN_OPERATOR. */
	public static final int NOT_IN_OPERATOR = 2;
	
	/** The Constant EQUAL_OPERATOR. */
	public static final int EQUAL_OPERATOR = 3;
	
	/** The Constant NOT_EQUAL_OPERATOR. */
	public static final int NOT_EQUAL_OPERATOR = 4;
	
	/** The Constant LIKE_OPERATOR. */
	public static final int LIKE_OPERATOR = 5;
	
	/** The Constant GT_OPERATOR. */
	public static final int GT_OPERATOR = 6;
	
	/** The Constant LT_OPERATOR. */
	public static final int LT_OPERATOR = 7;
	
	/** The Constant GTOE_OPERATOR. */
	public static final int GTOE_OPERATOR = 8;
	
	/** The Constant LTOE_OPERATOR. */
	public static final int LTOE_OPERATOR = 9;
	
	/** The Constant ALIAS_SPACE. */
	public static final String ALIAS_SPACE = " ";
	
	/** The Constant ALIAS_SEPARATOR. */
	public static final String ALIAS_SEPARATOR = ".";
	
	/** The Constant DISTINCT_OPERATOR. */
	public static final String DISTINCT_OPERATOR = "distinct(";
	
	/** The Constant DISTINCT_CONSTANT. */
	public static final String DISTINCT_CONSTANT = "distinct";
	
	/** The Constant COUNT_OPERATOR. */
	public static final String COUNT_OPERATOR = "count(";
	
	/** The entity name. */
	public String entityName;
	
	/** The h sql condition with prev entities. */
	public String[] hSQLConditionWithPrevEntities;
	
	/** The selection hsql columns. */
	public String[] selectionHSQLColumns;
	
	/** The h sql conditions. */
	public String[] hSQLConditions;
	
	/** The binding param names. */
	public String[] bindingParamNames;
	
	/** The binding param values. */
	public Object[] bindingParamValues;
	
	/** The operators. */
	public int[] operators;
	
	/** The freelance hsql string. */
	public String freelanceHSQLString;
	
	/** The order by hsql column. */
	public String orderByHSQLColumn;
	
	/** The order by asc. */
	public boolean orderByAsc;
	
	/** TODO To be used in case of UNIQUE,DISTINCT,SUM etc Use this only if all entity match criteria is for same foreign key. */
	String exceptionalHSQLColumn;
	
	/**
	 * Instantiates a new hSQL composer util.
	 *
	 * @param entityName together with Alias if any
	 * @param selectionHSQLColumns the selection hsql columns
	 * @param hSQLConditionWithPrevEntities the h sql condition with prev entities
	 * @param hSQLConditions the h sql conditions
	 * @param bindingParamNames the binding param names
	 * @param bindingParamValues the binding param values
	 * @param operators the operators
	 */
	public HSQLComposerUtil(String entityName, String[] selectionHSQLColumns, String[] hSQLConditionWithPrevEntities,
			String[] hSQLConditions, String[] bindingParamNames, Object[] bindingParamValues,int[] operators) {
		this.bindingParamNames = bindingParamNames;
		this.bindingParamValues = bindingParamValues;
		this.operators = operators;
		this.entityName = entityName;
		this.hSQLConditions = hSQLConditions;
		this.hSQLConditionWithPrevEntities = hSQLConditionWithPrevEntities;
		this.selectionHSQLColumns = selectionHSQLColumns;
	}	
	
	/**
	 * Instantiates a new hSQL composer util.
	 *
	 * @param entityName the entity name
	 * @param selectionHSQLColumns the selection hsql columns
	 * @param hSQLConditionWithPrevEntities the h sql condition with prev entities
	 * @param hSQLConditions the h sql conditions
	 * @param bindingParamNames the binding param names
	 * @param bindingParamValues the binding param values
	 * @param operators the operators
	 * @param orderByColumn the order by column
	 * @param ascSortOrder the asc sort order
	 */
	public HSQLComposerUtil(String entityName, String[] selectionHSQLColumns, String[] hSQLConditionWithPrevEntities,
			String[] hSQLConditions, String[] bindingParamNames, Object[] bindingParamValues,int[] operators, String orderByColumn, boolean ascSortOrder) {
		this.bindingParamNames = bindingParamNames;
		this.bindingParamValues = bindingParamValues;
		this.operators = operators;
		this.entityName = entityName;
		this.hSQLConditions = hSQLConditions;
		this.hSQLConditionWithPrevEntities = hSQLConditionWithPrevEntities;
		this.selectionHSQLColumns = selectionHSQLColumns;
		this.orderByHSQLColumn = orderByColumn;
		this.orderByAsc = ascSortOrder;
	}		

	/**
	 * Checks if is all param values null or empty.
	 *
	 * @return true, if is all param values null or empty
	 */
	boolean isAllParamValuesNullOrEmpty() {
		if (bindingParamValues==null) 
			// return false here to accomodate the scenario
			// where we are interested for match in 'hSQLConditionWithPrevEntities' only
			// or interested for 'selectionHSQLColumns' only
			return false;
		
		for (int i = 0; i < bindingParamValues.length; i++) {
			
			Object paramValue = bindingParamValues[i];
			
			if(paramValue!=null) {
				Class objCls = paramValue.getClass();
				if(objCls.isArray()) {
					if(((Object[])paramValue).length>0) return false;
				}else if(paramValue instanceof List || paramValue instanceof Set) {
					if(((Collection)paramValue).size()>0) return false;
				}else {
					return false;
				}
			}
		}
		
		return true;
	}
	
	/**
	 * Gets the entity alias.
	 *
	 * @return the entity alias
	 */
	String getEntityAlias() {
		if(entityName!=null) {
			int i = entityName.lastIndexOf(ALIAS_SPACE);
			if(i>0) return entityName.substring(i+1);
		}
		return entityName;
	}
	
	/**
	 * Checks for exceptional column.
	 *
	 * @return true, if successful
	 */
	boolean hasExceptionalColumn() {
		if(selectionHSQLColumns!=null && selectionHSQLColumns.length>0) {
			for (int i = 0; i < selectionHSQLColumns.length; i++) {
				if(selectionHSQLColumns[i]!=null) {
					return selectionHSQLColumns[i].startsWith(HSQLComposerUtil.COUNT_OPERATOR) ||
					selectionHSQLColumns[i].startsWith(HSQLComposerUtil.DISTINCT_CONSTANT) ||
					selectionHSQLColumns[i].startsWith(HSQLComposerUtil.DISTINCT_OPERATOR);
				}
			}
		}
		return false;
	}
}