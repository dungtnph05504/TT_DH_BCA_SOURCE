package com.nec.asia.nic.framework.common.query;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.nec.asia.nic.framework.dao.DaoException;


/**
 * This class is used for building hibernate criteria.<BR>
 * From BAF framework
 *
 * @author WL
 */
public class HbmCriteriaBuilder {
	
	/** The logger. */
	private static Logger logger = Logger.getLogger(HbmCriteriaBuilder.class);
	
	/** The criteria. */
	private Criteria criteria;
	
	/** The has crietria. */
	private boolean hasCrietria;
	
	/** The alias list. */
	private Set<String> aliasList= new HashSet<String>();
	
	/**
	 * Instantiates a new hbm criteria builder.
	 *
	 * @param criteria the criteria
	 */
	public HbmCriteriaBuilder(Criteria criteria) {
		this.criteria = criteria;
	}

	
	public HbmCriteriaBuilder addCriteria(String fieldName, HbmCriteria hbmCriteria, String aliasName) throws DaoException{
		if(hbmCriteria!=null) {
			if(aliasName!=null) {
				if(!aliasList.contains(aliasName)) {
					aliasList.add(aliasName);
					logger.trace("*** aliasName: "+aliasName);
					criteria.createAlias(aliasName, aliasName);
				}
			}
			addCriteria(fieldName, hbmCriteria);
		}
		return this;
	}
	
	/**
	 * Adds criteria condition.
	 *
	 * @param fieldName the field name
	 * @param hbmCriteria the criteria dto
	 * @return reference to this HbmCriteriaBuilder instance
	 * @throws DaoException the dao exception
	 */
	public HbmCriteriaBuilder addCriteria(String fieldName, HbmCriteria<?> hbmCriteria) throws DaoException{
		if(hbmCriteria!=null) {
			hasCrietria = true;
			if(hbmCriteria instanceof ValueCriteria) {
				
				Object value = getCriteriaValue(fieldName, hbmCriteria);
				if(value==null) {
					throw new DaoException("Null value specified in value criteria for field: "+fieldName+", criteriaDto: "+ReflectionToStringBuilder.toString(hbmCriteria));
				}
				else if(isInstanceOf(EqualCriteria.class, hbmCriteria)) {
					criteria.add(Restrictions.eq(fieldName, value));
				}
				else if(isInstanceOf(NotEqualCriteria.class, hbmCriteria)) {
					criteria.add(Restrictions.ne(fieldName, value));
				}
				else if(isInstanceOf(LikeCriteria.class, hbmCriteria)) {
					criteria.add(Restrictions.like(fieldName, value));
				}
				else {
					throw new DaoException("Unknown value criteria type specified for field: "+fieldName+", criteriaDto: "+ReflectionToStringBuilder.toString(hbmCriteria));
				}
			}
			else if(hbmCriteria instanceof BetweenCriteria) {
				criteria.add(Restrictions.between(fieldName, ((BetweenCriteria<?>)hbmCriteria).getFromValue(), ((BetweenCriteria<?>)hbmCriteria).getToValue()));
			}
			else if(isInstanceOf(NullCriteria.class, hbmCriteria)) {
				criteria.add(Restrictions.isNull(fieldName));
			}			
			else if(isInstanceOf(NotNullCriteria.class, hbmCriteria)) {
				criteria.add(Restrictions.isNotNull(fieldName));
			}
			else if(isInstanceOf(InCriteria.class, hbmCriteria)) {
				criteria.add(Restrictions.in(fieldName, ((InCriteria<?>)hbmCriteria).getValue()));
			}
			else {
				throw new DaoException("Unknown criteria type specified for field: "+fieldName+", hbmCriteria: "+ReflectionToStringBuilder.toString(hbmCriteria));
			}
		}
		return this;
	}
	
	
	/**
	 * Gets the criteria.
	 *
	 * @return the criteria
	 */
	public Criteria getCriteria() {
		return criteria;
	}

	/**
	 * Checks if is checks for crietria.
	 *
	 * @return the hasCrietria
	 */
	public boolean isHasCrietria() {
		return hasCrietria;
	}
	
	/**
	 * Returns true of criteriaDto is instance of specified criteriaClass.
	 *
	 * @param criteriaClass the criteria class
	 * @param hbmCriteria the hbmCriteria
	 * @return true, if is instance of
	 */
	private boolean isInstanceOf(Class<?> criteriaClass, HbmCriteria<?> hbmCriteria) {
		if(criteriaClass.isInstance(hbmCriteria)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Gets the criteria value.
	 *
	 * @param fieldName the field name
	 * @param hbmCriteria the hbmCriteria
	 * @return the criteria value
	 * 
	 * @throws DaoException the dao exception
	 */
	private <T> T getCriteriaValue(String fieldName, HbmCriteria<?> hbmCriteria) throws DaoException{
		T value=null;
		if(hbmCriteria instanceof ValueCriteria) {
			value = (T) ((ValueCriteria) hbmCriteria).getValue();
		}
		else {
			throw new DaoException("Unknown criteria type specified for field: "+fieldName+", hbmCriteria: "+ReflectionToStringBuilder.toString(hbmCriteria));			
		}
		return value;
	}
}
