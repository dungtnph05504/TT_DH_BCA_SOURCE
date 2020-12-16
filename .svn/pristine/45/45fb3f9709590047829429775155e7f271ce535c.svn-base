package com.nec.asia.nic.framework.hibernate;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.id.UUIDGenerator;
import org.hibernate.type.StringType;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

/**
 * The Class AbstractHibernateDao.
 *
 * @author Alvin Chua
 * @deprecated to use GenericDao.java
 */
public abstract class AbstractHibernateDao extends HibernateDaoSupport{
    
    /**
     * Merge entity.
     *
     * @param entity the entity
     * @return the object
     */
    public Object mergeEntity (Object entity) {
        return this.getHibernateTemplate().merge(entity);
    }

    /**
     * Save entity.
     *
     * @param entity the entity
     * @return the object
     */
    public Object saveEntity (Object entity) {
        return this.getHibernateTemplate().save(entity);
    }
    
    /**
     * Delete entity.
     *
     * @param entity the entity
     */
    public void deleteEntity (Object entity) {
        this.getHibernateTemplate().delete(entity);
    }
    
    /**
     * Gets the all entity.
     *
     * @param entityClass the entity class
     * @return the all entity
     */
    public List getAllEntity (Class entityClass) {
        return getHibernateTemplate().loadAll(entityClass);
    }

    /**
     * Update entity.
     *
     * @param entity the entity
     */
    public void updateEntity (Object entity) {
        this.getHibernateTemplate().update(entity);
    }

    /**
     * Save or update entity.
     *
     * @param entity the entity
     */
    public void saveOrUpdateEntity (Object entity) {
        this.getHibernateTemplate().saveOrUpdate(entity);
    }

    /**
     * Gets the fixlength string.
     *
     * @param string the string
     * @param length the length
     * @return the fixlength string
     */
    public String getFixlengthString (String string, int length) {
        while (string.length()<length) string   =string+" ";
        if (string.length()>length) string  =string.substring(0,length);
        
        return string;
    }
    
    /**
     * Evict all.
     *
     * @param collObj the coll obj
     */
    protected void evictAll(Collection collObj) {
        HibernateTemplate hbTemplate = this.getHibernateTemplate();
        for(Iterator i = collObj.iterator();i.hasNext();) {
            hbTemplate.evict(i.next());
        }
    }
    
    protected String createUUID(){
        Properties props = new Properties(); 
        props.setProperty("separator", ""); 
        IdentifierGenerator generator = new UUIDGenerator(); 
        ((Configurable) generator ).configure(StringType.INSTANCE, props, null); 

        String id = (String) generator.generate(null, null);
        return id;
    }
    
}
