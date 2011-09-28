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

import net.mad.ads.common.util.StringValuedEnum;

/**
 * Utility class designed to inspect StringValuedEnums.
 */
public class StringValuedEnumReflect {
    
    /**
     * Don't let anyone instantiate this class.
     * @throws UnsupportedOperationException Always.
     */
    private StringValuedEnumReflect() {
        throw new UnsupportedOperationException("This class must not be instanciated.");
    }
    
    /**
     * All Enum constants (instances) declared in the specified class. 
     * @param enumClass Class to reflect
     * @return Array of all declared EnumConstants (instances).
     */
    private static <T extends Enum<T>> T[] 
            getValues(Class<T> enumClass){
        return enumClass.getEnumConstants();
    }
    
    /**
     * All possible string values of the string valued enum.
     * @param enumClass Class to reflect.
     * @return Available string values.
     */
    public static <T extends Enum<T> & StringValuedEnum> String[] 
            getStringValues(Class<T> enumClass){ 
        T[] values = getValues(enumClass);
        String[] result = new String[values.length];
        for (int i=0; i<values.length; i++){
            result[i] = values[i].getValue();
        }
        return result;
    }
    
    /**
     * Name of the enum instance which hold the especified string value.
     * If value has duplicate enum instances than returns the first occurency.
     * @param enumClass Class to inspect.
     * @param value String.
     * @return name of the enum instance.
     */
    public static <T extends Enum<T> & StringValuedEnum> String 
            getNameFromValue(Class<T> enumClass, String value){
        T[] values = getValues(enumClass);
        for (T v : values){
            if (v.getValue().equals(value)){
                return v.name();
            }
        }
        return "";
    }
    
}