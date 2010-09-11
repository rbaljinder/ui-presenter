package org.baljinder.presenter.dataacess.util;
/**
 * 
 */


import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.trinidad.bean.FacesBean;
import org.apache.myfaces.trinidad.bean.PropertyKey;
import org.apache.myfaces.trinidad.component.UIXTable;
import org.baljinder.presenter.jsf.util.MessageBean;

/**
 * @author Baljinder Randhawa
 * 
 */
public class FacesUtils {

	public static final String _ROW_SELECTION_PROP_KEY = "rowSelection";

	private static Log logger = LogFactory.getLog(FacesUtils.class);
	
	static MessageBean messageBean ;

	public static MessageBean getMessageBean() {
		return messageBean;
	}

	public static void setMessageBean(MessageBean messageBean) {
		FacesUtils.messageBean = messageBean;
	}

	public static void addInfoMessage(String message) {
		messageBean.addInfoMessage(message);
	}

	public static void addErrorMessage(String message) {
		messageBean.addErrorMessage(message);
	}

	public static boolean isMultiSelect(UIXTable table) {
		Object value = getPropertyValue(_ROW_SELECTION_PROP_KEY, table);
		if (value != null)
			return "multiple".equalsIgnoreCase(value.toString());
		return false;
	}

	public static boolean isSingleSelect(UIXTable table) {
		Object value = getPropertyValue(_ROW_SELECTION_PROP_KEY, table);
		if (value != null)
			return "single".equalsIgnoreCase(value.toString());
		return false;
	}

	public static PropertyKey getProperty(String key, UIXTable table) {
		if (key == null || table == null)
			return null;
		FacesBean facesBean = table.getFacesBean();
		Set<PropertyKey> keys = facesBean.keySet();
		for(PropertyKey propertyKey :keys){
			if (key.equalsIgnoreCase(propertyKey.getName())) {
				if (logger.isDebugEnabled())
					logger.debug("Found UI table property " + key);
				return propertyKey;
			}
		}
		return null;
	}

	public static Object getPropertyValue(String key, UIXTable table) {
		if ((table != null) && (key != null)) {
			FacesBean facesBean = table.getFacesBean();
			PropertyKey propKey = getProperty(key, table);
			if (propKey != null) {
				return facesBean.getProperty(propKey);
			}
		}
		return null;
	}
}
