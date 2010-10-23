/**
 * 
 */
package org.baljinder.presenter.util;

import java.lang.reflect.Field;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author Baljinder Randhawa
 * 
 */
public class ReflectionUtils {

	public static Class<?> getFieldTypeOfClass(Class<?> clazz, String fieldName) {
		Class<?> typeToReturn = null;
		try {
			Field field = clazz.getDeclaredField(fieldName);
			typeToReturn = field.getType();
		} catch (Throwable th) {
			throw new RuntimeException("Field [" + fieldName + "] does not exist in Class[" + clazz + "]");
		}
		return typeToReturn;
	}

	public static void setFieldValue(Object object, String fieldName, Object value) {
		try {
			PropertyUtils.setSimpleProperty(object, fieldName, value);
		} catch (Throwable th) {
			throw new RuntimeException("Could not set field[" + fieldName + "] on Object[" + object + "]");
		}
	}

	public static Object getFieldValue(Object object, String fieldName) {
		Object value = null;
		try {
			value = PropertyUtils.getSimpleProperty(object, fieldName);
		} catch (Throwable th) {
			throw new RuntimeException("Could not get field[" + fieldName + "] of Object[" + object + "]");
		}
		return value;
	}
	public static String getFieldAsString(Object object, String fieldName) {
		Object  value = getFieldValue(object, fieldName);
		return value != null?value.toString(): StringUtils.EMPTY;
	}
		
}
