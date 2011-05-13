package org.baljinder.presenter.jsf.util;

/**
 * 
 */

import java.util.Set;

/**
 * @author Baljinder Randhawa
 * 
 */
public class FacesUtils {

	public static final String _ROW_SELECTION_PROP_KEY = "rowSelection";

	static MessageBean messageBean;

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

}
