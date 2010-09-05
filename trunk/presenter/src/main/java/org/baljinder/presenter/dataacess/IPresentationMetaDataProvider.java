package org.baljinder.presenter.dataacess;

import java.util.Locale;

public interface IPresentationMetaDataProvider {

	public String getMessage(String key);
	
	public String getMessage(String key, Locale locale);
	
	public String getMessage(String key, Object[]param , Locale locale);
	
	public String getMessage(String key, Object[]param);
	
}
