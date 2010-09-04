package org.baljinder.presenter.util;

import java.util.Locale;
import java.util.TimeZone;

public interface LocaleSupport
{
	public static String BEAN_NAME = "localeManager";
	
	public Locale getCurrentLocale();
	
	public TimeZone getTimeZone();
	
	public String getDatePattern();
	
	public String getDateTimePattern();
	
	public String getTimePattern();
}
