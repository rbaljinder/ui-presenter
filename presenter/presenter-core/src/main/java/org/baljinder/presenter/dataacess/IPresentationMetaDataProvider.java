package org.baljinder.presenter.dataacess;

import java.util.Locale;
import java.util.Map;

public interface IPresentationMetaDataProvider {

	public static String PRESENTATION_METADATA_SERVICE_NAME ="genericPresentationMetaDataService";  
	
	public String getMessage(String key);
	
	public String getMessage(String key, Locale locale);
	
	public String getMessage(String key, Object[]param , Locale locale);
	
	public String getMessage(String key, Object[]param);

	public Map<String,String> getMessage();
	
}
