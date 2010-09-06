/**
 * 
 */
package org.baljinder.presenter.datamodel;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.baljinder.presenter.datamodel.config.Config.Case;
import org.baljinder.presenter.datamodel.config.DataModel;
import org.baljinder.presenter.datamodel.config.Field;
import org.baljinder.presenter.util.ClassUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @author Baljinder Randhawa
 * 
 */
public class DataModelMetadataProvider {

	private static Log logger = LogFactory.getLog(DataModelMetadataProvider.class) ; 
	
	private List<String> dataModelpackages = Lists.newArrayList();

	private Map<Class<?>, DataModelMetadata> metaData = Maps.newHashMap();
	
	public static DataModelMetadataProvider emptyInstance = new DataModelMetadataProvider();

	public void  loadMetaData() {
		logger.info("Loading Data Model metadata for packages["+dataModelpackages);
		for (String packageName : dataModelpackages) {
			List<Class<?>> classList = ClassUtils.getClassesForPackage(packageName);
			for (Class<?> clazz : classList)
				collectMetaData(clazz);
		}
	}

	/**
	 * @param clazz
	 */
	private void collectMetaData(Class<?> clazz) {
		if(!clazz.isAnnotationPresent(DataModel.class) || metaData.get(clazz)!= null)
			return ;
		DataModel dataModel = clazz.getAnnotation(DataModel.class);
		DataModelMetadata dataModelMetadata = new DataModelMetadata();
		dataModelMetadata.setClazz(clazz);
		dataModelMetadata.setClassName(clazz.getName());
		for(String fieldName : dataModel.upperCaseFields()){
			if(!StringUtils.isEmpty(fieldName)){
				FieldMetaData fieldMetaData = dataModelMetadata.getFieldMetaDataOrCreate(fieldName);
				fieldMetaData.setFieldCase(Case.UPPER);
			}	
		}	
		for(String fieldName : dataModel.lowerCaseFields()){
			if(!StringUtils.isEmpty(fieldName)){
				FieldMetaData fieldMetaData = dataModelMetadata.getFieldMetaDataOrCreate(fieldName);
				fieldMetaData.setFieldCase(Case.LOWER);
			}	
		}	
		java.lang.reflect.Field []fields = clazz.getDeclaredFields();
		for(java.lang.reflect.Field field :fields){
			if(field.isAnnotationPresent(Field.class)){
				Field fieldAnnotation = field.getAnnotation(Field.class);
				FieldMetaData fieldMetaData = dataModelMetadata.getFieldMetaDataOrCreate(field.getName(),field.getType());
				fieldMetaData.setFieldCase(fieldAnnotation.Case());
			}
		}	
		metaData.put(clazz, dataModelMetadata);
		collectMetaData(clazz.getSuperclass());
	}
	
	public boolean doesMetaDataExists(Class<?> dataModelClass){
		return metaData.get(dataModelClass)!= null? true: false;
	}
	
	/**
	 *  It does not do null checks for existance. so please check existance before using this
	 * @param dataModelClass
	 * @return
	 */
	public DataModelMetadata getDataModelMetaData(Class<?> dataModelClass){
		return metaData.get(dataModelClass);
	}
	
	public boolean doesFieldMetaDataExists(Class<?> dataModelClass,String fieldName){
		DataModelMetadata dataModelMetaData = metaData.get(dataModelClass);
		if(dataModelMetaData == null) 
			return false;
		return dataModelMetaData.getFieldMetaData(fieldName) != null?true:false;
	}
	
	/**
	 *  It does not do null checks for existance. so please check existance before using this
	 * @param dataModelClass
	 * @param fieldName
	 * @return
	 */
	public FieldMetaData getFieldMetaData(Class<?> dataModelClass,String fieldName){
		return metaData.get(dataModelClass).getFieldMetaData(fieldName);
	}
	
	public Map<Class<?>, DataModelMetadata> getMetaData(){
		return metaData;
	}
	
	public DataModelMetadata getMetaData(Class<?> clazz){
		return metaData.get(clazz);
	}
	
	/**
	 * @return the dataModelpackages
	 */
	public List<String> getDataModelpackages() {
		return dataModelpackages;
	}

	/**
	 * @param dataModelpackages the dataModelpackages to set
	 */
	public void setDataModelpackages(List<String> dataModelpackages) {
		this.dataModelpackages = dataModelpackages;
	}

	/**
	 * @param metaData the metaData to set
	 */
	public void setMetaData(Map<Class<?>, DataModelMetadata> metaData) {
		this.metaData = metaData;
	}
	
	public static class DataModelMetadata {

		private Class<?> clazz;

		private String className;
		
		private Map<String,FieldMetaData> fields= Maps.newHashMap(); 
		
		/**
		 * @return the className
		 */
		public String getClassName() {
			return className;
		}

		public Map<String,FieldMetaData> getFields(){
			return fields;
		}	

		public FieldMetaData getFieldMetaData(String fieldName) {
			return  fields.get(fieldName) ;
		}

		public FieldMetaData getFieldMetaDataOrCreate(String fieldName, Class<?> type) {
			FieldMetaData fieldMetaData = fields.get(fieldName) ;
			if(fieldMetaData== null){
				fieldMetaData =  new FieldMetaData(fieldName,type);
				fields.put(fieldName, fieldMetaData);
			}	
			return fieldMetaData;

		}

		public FieldMetaData getFieldMetaDataOrCreate(String fieldName) {
			FieldMetaData fieldMetaData = fields.get(fieldName) ;
			if(fieldMetaData== null){
				fieldMetaData =  new FieldMetaData(fieldName);
				fields.put(fieldName, fieldMetaData);
			}	
			return fieldMetaData;
		}

		/**
		 * @return the fieldCase
		 */
		public void addFieldMetaData(FieldMetaData fieldMetaData) {
			 fields.put(fieldMetaData.getName(),fieldMetaData);
		}

		/**
		 * @param className
		 *            the className to set
		 */
		public void setClassName(String className) {
			this.className = className;
		}

		/**
		 * @return the clazz
		 */
		public Class<?> getClazz() {
			return clazz;
		}

		/**
		 * @param clazz
		 *            the clazz to set
		 */
		public void setClazz(Class<?> clazz) {
			this.clazz = clazz;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "DataModelMetadata [className=" + className + ", clazz=" + clazz + ", fieldCase=" + fields + "]";
		}
	}

	public static class FieldMetaData{
		
		private String name ;
		
		private Class <?> type ;
		
		private Case fieldCase ;
		
		private Class <?> validation;
		
		private String value ;
		
		private String validatingMethod ;

		public FieldMetaData(String name){
			this.name = name ;
		}
		public FieldMetaData(String fieldName, Class<?> type) {
			this.name = fieldName ;
			this.type = type ;
		}
		/**
		 * The default value in the annotation is Object.class so just comparing with that
		 * @return
		 */
		public boolean hasValidation(){
			return validation != null && !(validation.equals(Object.class));
		}
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Class<?> getType() {
			return type;
		}

		public void setType(Class<?> type) {
			this.type = type;
		}

		public Case getFieldCase() {
			return fieldCase;
		}

		public void setFieldCase(Case fieldCase) {
			this.fieldCase = fieldCase;
		}

		public Class<?> getValidation() {
			return validation;
		}

		public void setValidation(Class<?> validation) {
			this.validation = validation;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getValidatingMethod() {
			return validatingMethod;
		}

		public void setValidatingMethod(String validatingMethod) {
			this.validatingMethod = validatingMethod;
		}

		@Override
		public String toString() {
			return "FieldMetaData [name=" + name + ", type=" + type
					+ ", fieldCase=" + fieldCase + ", validation=" + validation
					+ ", value=" + value + ", validatingMethod="
					+ validatingMethod + "]";
		}
	}
	
}
