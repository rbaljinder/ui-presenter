package org.baljinder.presenter.util;

import java.text.MessageFormat;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.context.MessageSource;


public class TokenizedMessageBean
{
	public static String BEAN_NAME = "tokenizedFacesMessage";

	private MessageSource _presentationMetadataProvider;

	public static TokenizedMessageBean getInstance()
	{
		return (TokenizedMessageBean)FacesContextUtil.getInstance().getManagedBean(TokenizedMessageBean.BEAN_NAME);
	}

	public MessageSource getPresentationMetadataProvider() {
		return _presentationMetadataProvider;
	}

	public void setPresentationMetadataProvider(
			MessageSource presentationMetadataProvider) {
		_presentationMetadataProvider = presentationMetadataProvider;
	}

	public LocaleSupport getLoacleSupport()
	{
		return (LocaleSupport)FacesContextUtil.getInstance().getManagedBean(LocaleSupport.BEAN_NAME);
	}

	public void addErrorMessage(String code)
	{
		addMessage(FacesMessage.SEVERITY_ERROR, code, null);
	}

	public void addInfoMessage(String code)
	{
		addMessage(FacesMessage.SEVERITY_INFO, code, null);
	}

	public void addWarningMessage(String code)
	{
		addMessage(FacesMessage.SEVERITY_WARN, code, null);
	}

	public void addErrorMessage(String code, Object[] params)
	{
		addMessage(FacesMessage.SEVERITY_ERROR, code, params);
	}

	public void addInfoMessage(String code, Object[] params)
	{
		addMessage(FacesMessage.SEVERITY_INFO, code, params);
	}

	public void addWarningMessage(String code, Object[] params)
	{
		addMessage(FacesMessage.SEVERITY_WARN, code, params);
	}

	public void addMessage(FacesMessage.Severity severity, String code, Object[] params)
	{
		String localizedMessage = getLocalizedMessage(code, params);
		FacesMessage facesMessage = new FacesMessage(severity, "", localizedMessage);
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}

	public String getLocalizedMessage(String code, Object[] params)
	{
		//TODO: is this correct
		String message = _presentationMetadataProvider.getMessage(code,params,getLoacleSupport().getCurrentLocale());//, arg1, arg2)// getMessage().get(code);  
		return message != null ? MessageFormat.format(message, params) : message;

	}
}
