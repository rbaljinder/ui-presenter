package org.baljinder.presenter.dataacess.cvl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.baljinder.presenter.datamodel.config.ValidValueModel;
import org.baljinder.presenter.dataacess.IPersistenceManager;
import org.baljinder.presenter.util.ReflectionUtils;
import com.google.common.collect.Maps;

@SuppressWarnings("serial")
public class CodedValueListProvider {

	private Map<Class<?>, List<?>> cached = new ConcurrentHashMap<Class<?>, List<?>>();
	
	private Map<Class<?>, Map<String,String>> cachedValidValueMaps = new ConcurrentHashMap<Class<?>, Map<String,String>>();

	public static final String GET = "get";
	// TODO: remove this
	public Map<String, Map> dataMap = new HashMap<String, Map>() {
		public Map get(Object dataMapName) {
			return getDataMap((String) dataMapName);
		};
	};

	private IPersistenceManager persistenceManager;

	private Map<String, CodedValueDefinition> definitions = new HashMap<String, CodedValueDefinition>();

	private CodedValueDAO codedValueDAO;

	public void initialize() {
		codedValueDAO = (CodedValueDAO) persistenceManager.getDAO("org.baljinder.presenter.jsf.ui.dataacess.cvl.CodedValueDAO");
	}

	public IPersistenceManager getPersistenceManager() {
		return persistenceManager;
	}

	public void setPersistenceManager(IPersistenceManager persistenceManager) {
		this.persistenceManager = persistenceManager;
	}

	public CodedValueDAO getCodedValueDAO() {
		return codedValueDAO;
	}

	public Map<String, CodedValueDefinition> getDefinitions() {
		return definitions;
	}

	public void setDefinitions(Map<String, CodedValueDefinition> definitions) {
		this.definitions = definitions;
	}

	public void setCodedValueDAO(CodedValueDAO codedValueDAO) {
		this.codedValueDAO = codedValueDAO;
	}

	private List<?> getValidValueList(Class<?> className) {
		List<?> codedValueObject = cached.get(className);
		if (codedValueObject == null) {
			codedValueObject = codedValueDAO.getCodedValueObject(className);
			cached.put(className, codedValueObject);
		}
		return codedValueObject;
	}

	/**
	 * This uses annotation.. remove the other one
	 */
	//TODO: move this too to the cached stuff
	public Map<String,String> getValidValueMap(Class<?> codeValueObject) {
		Map<String,String> validValueMap = cachedValidValueMaps.get(codeValueObject);
		if(validValueMap != null)
			return validValueMap;
		Map<String,String> codedValueMap = Maps.newHashMap();
		ValidValueModel validValueModel = codeValueObject.getAnnotation(ValidValueModel.class);
		List<?> validValueObjects = getValidValueList(codeValueObject);
		if(validValueModel != null || validValueObjects != null){
			for(Object validValue : validValueObjects){
				String key = ReflectionUtils.getFieldAsString(validValue,validValueModel.Key());
				String desc = ReflectionUtils.getFieldAsString(validValue,validValueModel.Desc());
				codedValueMap.put(key, desc);
			}
		}	
		cachedValidValueMaps.put(codeValueObject, validValueMap);
		return codedValueMap;
	}
	@SuppressWarnings( { "unchecked" })
	public Map getValidValueMap(Class<?> codeValueObject, String keyColumn, String descriptionColumn) {
		Map codedValueMap = Maps.newHashMap();
		// Empty option
		codedValueMap.put("", "");
		List objectList = getValidValueList(codeValueObject);
		if (objectList == null)
			return codedValueMap;

		Iterator iterator = objectList.iterator();
		while (iterator.hasNext()) {
			Object codedValueObject = iterator.next();
			try {
				// what u want to display needs to be the key
				codedValueMap.put(ReflectionUtils.getFieldValue(codedValueObject, descriptionColumn), ReflectionUtils.getFieldValue(
						codedValueObject, keyColumn));
			} catch (Throwable th) {
				throw new PropertyResolvingException("Coded Value Proprty Exception [CodedValueObject]:" + codeValueObject + " [Key] " + keyColumn + " [Desc]"
						+ descriptionColumn, th);
			}
		}
		return codedValueMap;
	}

	public Map getDataMap(String codedValueOject) {
		CodedValueDefinition codeValueDefinition = definitions.get(codedValueOject);
		if (codeValueDefinition == null)
			return Maps.newHashMap();
		return getValidValueMap(codeValueDefinition.getClassName(), codeValueDefinition.getKeyColumn(), codeValueDefinition.getDescColumn());

	}

	public Map getDataMap() {
		return dataMap;
	}
}
