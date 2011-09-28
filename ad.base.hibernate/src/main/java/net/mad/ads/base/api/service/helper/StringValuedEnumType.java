/**
 * Mad-Advertisement
 * Copyright (C) 2011 Thorsten Marx <thmarx@gmx.net>
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.mad.ads.base.api.service.helper;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;
import java.lang.reflect.*;
 
import net.mad.ads.common.util.StringValuedEnum;

import org.hibernate.HibernateException;
import org.hibernate.usertype.EnhancedUserType;
import org.hibernate.usertype.ParameterizedType;
 
import static net.mad.ads.base.api.service.helper.StringValuedEnumReflect.*;
 
//Please notice the calls to getNameFromValue *************************
public class StringValuedEnumType <T extends Enum<T> & StringValuedEnum> 
        implements EnhancedUserType, ParameterizedType{
    
    /**
     * Enum class for this particular user type.
     */
    private Class<T> enumClass;
 
    /**
     * Value to use if null.
     */
    private String defaultValue;
    
    /** Creates a new instance of ActiveStateEnumType */
    public StringValuedEnumType() {
    }
    
    public void setParameterValues(Properties parameters) {
        String enumClassName = parameters.getProperty("enumClass");
        try {
            enumClass = (Class<T>) Class.forName(enumClassName).asSubclass(Enum.class).
                    asSubclass(StringValuedEnum.class); //Validates the class but does not eliminate the cast
        } catch (ClassNotFoundException cnfe) {
            throw new HibernateException("Enum class not found", cnfe);
        }
 
        setDefaultValue(parameters.getProperty("defaultValue"));
    }
 
    public String getDefaultValue() {
        return defaultValue;
    }
    
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
    
    /**
     * The class returned by <tt>nullSafeGet()</tt>.
     * @return Class
     */
    public Class returnedClass() {
        return enumClass;
    }
 
    public int[] sqlTypes() {
        return new int[] { Types.VARCHAR };
    }
    
    public boolean isMutable() {
        return false;
    }
 
    /**
     * Retrieve an instance of the mapped class from a JDBC resultset. Implementors
     * should handle possibility of null values.
     *
     * @param rs a JDBC result set
     * @param names the column names
     * @param owner the containing entity
     * @return Object
     * @throws HibernateException
     * @throws SQLException
     */
    public Object nullSafeGet(ResultSet rs, String[] names, Object owner)
            throws HibernateException, SQLException {
        String value = rs.getString( names[0] );
        if (value==null) {
            value = getDefaultValue();
            if (value==null){ //no default value
                return null;
            } 
        }
        String name = getNameFromValue(enumClass, value);
        Object res = rs.wasNull() ? null : Enum.valueOf(enumClass, name);
        
        return res;
    }
 
    /**
     * Write an instance of the mapped class to a prepared statement. Implementors
     * should handle possibility of null values. A multi-column type should be written
     * to parameters starting from <tt>index</tt>.
     *
     * @param st a JDBC prepared statement
     * @param value the object to write
     * @param index statement parameter index
     * @throws HibernateException
     * @throws SQLException
     */   
    public void nullSafeSet(PreparedStatement st, Object value, int index)
    throws HibernateException, SQLException {
        if (value==null) {
            st.setNull(index, Types.VARCHAR);
        } else {
            st.setString( index, ((T) value).getValue() );
        }
    }
    
    public Object assemble(Serializable cached, Object owner)
            throws HibernateException {
        return cached;
    }
    
    public Serializable disassemble(Object value) throws HibernateException {
        return (Enum) value;
    }
        
    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }
 
    public boolean equals(Object x, Object y) throws HibernateException {
        return x==y;
    }
    
    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }
 
    public Object replace(Object original, Object target, Object owner)
            throws HibernateException {
        return original;
    }
 
    public String objectToSQLString(Object value) {
        return '\'' + ((T) value).getValue() + '\'';
    }
    
    public String toXMLString(Object value) {
        return ((T) value).getValue();
    }
 
    public Object fromXMLString(String xmlValue) {
        String name = getNameFromValue(enumClass, xmlValue);
        return Enum.valueOf(enumClass, name);
    }
        
}