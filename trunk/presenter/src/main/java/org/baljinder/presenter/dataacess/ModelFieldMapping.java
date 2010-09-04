/**
 * 
 */
package org.baljinder.presenter.dataacess;

import org.apache.commons.lang.StringUtils;

/**
 * @author Baljinder Randhawa
 * 
 */
public class ModelFieldMapping {

	private String modelName;

	private Class<?> modelClass;

	private String fieldName;

	private Class<?> fieldClass;

	private Object value;

	private boolean isPartOfRange;

	private String rangePart;

	public static final String FROM = "-from";

	public static final String TO = "-to";

	/**
	 * 
	 */
	public ModelFieldMapping(String modelName, String fieldName) {
		this.modelName = modelName;
		this.fieldName = fieldName;
		checkIfPartOfRangeAndModiy();
	}

	/**
	 * 
	 */
	private void checkIfPartOfRangeAndModiy() {
		if (StringUtils.indexOf(fieldName, FROM) != -1) {
			this.fieldName = StringUtils.substring(fieldName, 0, StringUtils.indexOf(fieldName, FROM));
			isPartOfRange = true;
			rangePart = FROM;
		}
		if (StringUtils.indexOf(fieldName, TO) != -1) {
			this.fieldName = StringUtils.substring(fieldName, 0, StringUtils.indexOf(fieldName, TO));
			isPartOfRange = true;
			rangePart = TO;
		}
	}

	/**
	 * @return the modelName
	 */
	public String getModelName() {
		return modelName;
	}

	/**
	 * @param modelName
	 *            the modelName to set
	 */
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	/**
	 * @return the modelClass
	 */
	public Class<? extends Object> getModelClass() {
		return modelClass;
	}

	/**
	 * @param modelClass
	 *            the modelClass to set
	 */
	public void setModelClass(Class<? extends Object> modelClass) {
		this.modelClass = modelClass;
	}

	/**
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * @param fieldName
	 *            the fieldName to set
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * @return the fieldClass
	 */
	public Class<? extends Object> getFieldClass() {
		return fieldClass;
	}

	/**
	 * @param fieldClass
	 *            the fieldClass to set
	 */
	public void setFieldClass(Class<? extends Object> fieldClass) {
		this.fieldClass = fieldClass;
	}

	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}
	
	/**
	 * @return the value
	 */
	public String getValueAsString() {
		if(value == null)
			return null ;
		else	
			return value.toString();
	}
	
	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * @param fieldName2
	 */
	public boolean isPartOfRange() {
		return isPartOfRange;
	}

	public String whichPartOfRange() {
		return rangePart;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ModelFieldMapping [fieldClass=" + fieldClass + ", fieldName=" + fieldName + ", modelClass=" + modelClass + ", modelName=" + modelName
				+ ", value=" + value + "]";
	}

}
