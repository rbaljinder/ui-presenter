/**
 * 
 */
package org.baljinder.presenter.util;

import java.util.List;

import org.baljinder.presenter.dataacess.IDataController;

/**
 * @author Baljinder Randhawa
 * 
 */
public class Utils {

	public static final String GET = "get";
	
	public static final String SET = "set";

	public static String getModelName(List<Class<? extends Object>> modelList, Object model) {
		for (Class<? extends Object> clazz : modelList) {
			if (clazz.isAssignableFrom(model.getClass()))
				return getUncappedAlias(clazz);
		}
		return null;
	}

	public static String getShortName(Class<? extends Object> clazz) {
		return clazz.getName().substring(clazz.getName().lastIndexOf(".") + 1, clazz.getName().length());
	}

	public static String getUncappedAlias(Class<? extends Object> entity) {
		String modelName = entity.getName().substring(entity.getName().lastIndexOf(".") + 1, entity.getName().length());
		return modelName.substring(0, 1).toLowerCase() + modelName.substring(1);
	}

	public static String getAlias(Class<? extends Object> entity) {
		String modelName = entity.getName().substring(entity.getName().lastIndexOf(".") + 1);
		return modelName.substring(0, 1).toLowerCase() + modelName.substring(1);
	}

	public static String getFirstLetterCapped(String string) {
		return string.substring(0, 1).toUpperCase() + string.substring(1);
	}

	public static String getFirstLetterUnCapped(String string) {
		return string.substring(0, 1).toLowerCase() + string.substring(1);
	}

	public static String getUncapped(String property) {
		return property.toLowerCase();
	}

	public static Class<? extends Object> getClassNameForModel(List<Class<? extends Object>> modelList, Object model) {
		for (Class<? extends Object> clazz : modelList) {
			if (clazz.isAssignableFrom(model.getClass()))
				return clazz;
		}
		return null;
	}

	/**
	 * This seems tricky
	 * 
	 * @param dataController
	 * @param modelName
	 * @return
	 */
	public static Class<? extends Object> getModelClassOfDataControl(IDataController dataController, String modelName) {
		for (Class<? extends Object> clazz : dataController.getModelList()) {
			if (getShortName(clazz).equalsIgnoreCase(modelName))
				return clazz;
		}
		throw new RuntimeException("Could not resolve Class name from a supplied model name[" + modelName + "] in data control[" + dataController + "]");
	}

}