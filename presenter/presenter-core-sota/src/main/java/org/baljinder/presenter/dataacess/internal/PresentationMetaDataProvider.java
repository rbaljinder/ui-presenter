package org.baljinder.presenter.dataacess.internal;

import java.util.Locale;
import java.util.Map;

import org.baljinder.presenter.dataacess.IPresentationMetaDataProvider;
import org.springframework.context.MessageSource;

/**
 * Really basic metadata provider ... of a litte use. Spring MesssageSource can
 * be directly used instead
 * 
 * @author baljinder
 * 
 */
public class PresentationMetaDataProvider implements
		IPresentationMetaDataProvider {

	private MessageSource messageSource;

	public String getMessage(String key) {
		return getMessage(key, Locale.getDefault());
	}

	public String getMessage(String key, Locale locale) {
		return getMessage(key, new Object[0], locale);
	}

	public String getMessage(String key, Object[] param, Locale locale) {
		return messageSource.getMessage(key, param, locale);
	}

	public String getMessage(String key, Object[] param) {
		return getMessage(key, param, Locale.getDefault());
	}

	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	//TODO: implement it
	public Map<String, String> getMessage() {
		throw new UnsupportedOperationException();
	}

}
