/**
 * 
 */
package com.nec.asia.nic.framework.admin.code.domain.type;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

/**
 * Parameter Type's Hibernate user type.
 * 
 * @author chris_wong
 * @version 1.0
 */

public class ParameterTypeUserType implements UserType {
    private static final int[] SQL_TYPES = {
        Types.CHAR
    };

    private Class<? extends ParameterType> returnedClass;

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return ((Object) cached);
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return ((Serializable) value);
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        if (x instanceof ParameterType && y instanceof ParameterType) {
            return (((ParameterType) x).equals((ParameterType) y));
        }
        return false;
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }

    @Override
    public Class returnedClass() {
        return returnedClass;
    }

    @Override
    public int[] sqlTypes() {
        return SQL_TYPES;
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
        String res = rs.getString(names[0]);
        if (res == null || res.trim().length() == 0)
            return null;
        return (ParameterType.getInstance(res));
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
        if (value != null)
            st.setString(index, ((ParameterType) value).getKey());
        else
            st.setNull(index, Types.CHAR);
    }

}
